package de.fiducia.bestellungen.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.rmi.RemoteException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import de.fiducia.bestellungen.models.Bestellung;
import de.fiducia.bestellungen.repositories.BestellRepository;

@ExtendWith(MockitoExtension.class)
public class BestellServiceImplTest
{
    private static final String INVALID_KREDITKARTE_BUCHSTABE_IM_ZIFFERNBEREICH = "M123456789A";

    private static final String INVALID_KREDITKARTE_FALSCHER_ANFANGSBUCHSTABE = "S1234567890";

    private static final String EXCEPTION_MESSAGE_FALSCHES_FORMAT_KREDITKARTE = "Kreditkartenname muss mit M oder V beginnen, danach genau 10 Ziffern enthalten.";

    private static final String INVALID_KREDITKARTE_ZU_LANG = "M12345678901";

    private static final String INVALID_KREDITKARTE_ZU_KURZ = "M123456789";

    private static final double INVALID_SALDO = -1.5;

    private static final Bestellung VALID_BESTELLUNG = new Bestellung();

    private static final double VALID_SALDO = 10.0;

    private static final String VALID_MASTERCARD = "M1234567890";

    @Mock
    private BestellRepository bestellRepositoryMock;

    @Mock
    private SolvenzService solvenzServiceMock;

    @InjectMocks
    private BestellServiceImpl serviceUnderTest;


    @Test
    void bestellungErfassen_mitBestellungNull_throwsBestellException()
    {
        BestellServiceException ex = assertThrows(BestellServiceException.class,
            () -> serviceUnderTest.bestellungErfassen(null, VALID_MASTERCARD, VALID_SALDO));
        assertEquals("Bestellung darf nicht null sein.", ex.getMessage());
    }

    @Test
    void bestellungErfassen_mitKreditkarteNull_throwsBestellException()
    {
        BestellServiceException ex = assertThrows(BestellServiceException.class,
            () -> serviceUnderTest.bestellungErfassen(VALID_BESTELLUNG, null, VALID_SALDO));
        assertEquals("Kreditkarte darf nicht null sein.", ex.getMessage());
    }

    @Test
    void bestellungErfassen_mitSaldoNegativ_throwsBestellException()
    {
        BestellServiceException ex = assertThrows(BestellServiceException.class,
            () -> serviceUnderTest.bestellungErfassen(VALID_BESTELLUNG, VALID_MASTERCARD, INVALID_SALDO));
        assertEquals("Saldo darf nicht kleiner 0 sein.", ex.getMessage());
    }

    @Test
    void bestellungErfassen_mitKreditkarteLaengeKleinerElf_throwsBestellException()
    {
        BestellServiceException ex = assertThrows(BestellServiceException.class,
            () -> serviceUnderTest.bestellungErfassen(VALID_BESTELLUNG, INVALID_KREDITKARTE_ZU_KURZ, VALID_SALDO));
        assertEquals(EXCEPTION_MESSAGE_FALSCHES_FORMAT_KREDITKARTE, ex.getMessage());
    }

    @Test
    void bestellungErfassen_mitKreditkarteLaengeZuLang_throwsBestellException()
    {
        BestellServiceException ex = assertThrows(BestellServiceException.class,
            () -> serviceUnderTest.bestellungErfassen(VALID_BESTELLUNG, INVALID_KREDITKARTE_ZU_LANG, VALID_SALDO));
        assertEquals(EXCEPTION_MESSAGE_FALSCHES_FORMAT_KREDITKARTE, ex.getMessage());
    }

    @Test
    void bestellungErfassen_mitKreditkarteFalscherAnfangsbuchstabe_throwsBestellException()
    {
        BestellServiceException ex = assertThrows(BestellServiceException.class,
            () -> serviceUnderTest.bestellungErfassen(VALID_BESTELLUNG, INVALID_KREDITKARTE_FALSCHER_ANFANGSBUCHSTABE,
                VALID_SALDO));
        assertEquals(EXCEPTION_MESSAGE_FALSCHES_FORMAT_KREDITKARTE, ex.getMessage());
    }

    @Test
    void bestellungErfassen_mitKreditkarteBuchstabeInZiffernbereich_throwsBestellException()
    {
        BestellServiceException ex = assertThrows(BestellServiceException.class,
            () -> serviceUnderTest.bestellungErfassen(VALID_BESTELLUNG, INVALID_KREDITKARTE_BUCHSTABE_IM_ZIFFERNBEREICH,
                VALID_SALDO));
        assertEquals(EXCEPTION_MESSAGE_FALSCHES_FORMAT_KREDITKARTE, ex.getMessage());
    }

    @Test
    void bestellungErfassen_solvenzServiceNichtErreichbar_throwsBestellException() throws Exception
    {
        doThrow(new RemoteException("Service nicht verfügbar.")).when(solvenzServiceMock).check(anyString(),
            anyString(), anyDouble());
        BestellServiceException ex = assertThrows(BestellServiceException.class,
            () -> serviceUnderTest.bestellungErfassen(VALID_BESTELLUNG, VALID_MASTERCARD, VALID_SALDO));
        assertEquals("SolvenzService nicht verfügbar.", ex.getMessage());
    }

    @Test
    void bestellungErfassen_solvenzServiceWithValidParameters_parametersPassedToService() throws Exception
    {
        when(solvenzServiceMock.check(anyString(), anyString(), anyDouble())).thenReturn(true);

        serviceUnderTest.bestellungErfassen(VALID_BESTELLUNG, VALID_MASTERCARD, VALID_SALDO);

        verify(solvenzServiceMock).check("Master", "1234567890", VALID_SALDO);
    }

    @Test
    void bestellungErfassen_kundeIstPleite_throwsKundeIstPleiteException() throws Exception
    {
        when(solvenzServiceMock.check(anyString(), anyString(), anyDouble())).thenReturn(false);

        KundeIstPleitException ex = assertThrows(KundeIstPleitException.class,
            () -> serviceUnderTest.bestellungErfassen(VALID_BESTELLUNG, VALID_MASTERCARD, VALID_SALDO));

        assertEquals("Kunde ist pleite", ex.getMessage());
    }

    @Test
    void bestellungErfassen_repoNichtVerfuegbar_throwsBestellException() throws Exception
    {
        doThrow(new RuntimeException()).when(bestellRepositoryMock).save(any());
        when(solvenzServiceMock.check(anyString(), anyString(), anyDouble())).thenReturn(true);

        BestellServiceException ex = assertThrows(BestellServiceException.class,
            () -> serviceUnderTest.bestellungErfassen(VALID_BESTELLUNG, VALID_MASTERCARD, VALID_SALDO));

        assertEquals("Repo nicht verfügbar.", ex.getMessage());

    }

    @Test
    void bestellungErfassen_korrekteParameter_BestellungWirdAnRepoUebergeben() throws Exception
    {
        when(solvenzServiceMock.check(anyString(), anyString(), anyDouble())).thenReturn(true);

        serviceUnderTest.bestellungErfassen(VALID_BESTELLUNG, VALID_MASTERCARD, VALID_SALDO);

        InOrder inOrder = Mockito.inOrder(solvenzServiceMock, bestellRepositoryMock);
        inOrder.verify(solvenzServiceMock, times(1)).check("Master", "1234567890", VALID_SALDO);
        inOrder.verify(bestellRepositoryMock, times(1)).save(VALID_BESTELLUNG);

    }

}
