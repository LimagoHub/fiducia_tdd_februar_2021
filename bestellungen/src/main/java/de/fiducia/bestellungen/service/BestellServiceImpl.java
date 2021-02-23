package de.fiducia.bestellungen.service;

import java.rmi.RemoteException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.fiducia.bestellungen.models.Bestellung;
import de.fiducia.bestellungen.repositories.BestellRepository;

public class BestellServiceImpl
{

    private static final String PATTERN_KREDITKARTE = "^(M|V)(\\d{10})$";
    private final BestellRepository bestellRepository;
    private final SolvenzService solvenzService;

    public BestellServiceImpl(BestellRepository bestellRepository, SolvenzService solvenzService)
    {

        this.bestellRepository = bestellRepository;
        this.solvenzService = solvenzService;
    }
    // Creditkarte = Entweder M oder V direkt gefolgt von exakt 10 ziffern, also 11 Stellen
    /*
     * Nullprüfung -> BestellException DONE
     * Format der Karte ist falsch -> BestellException DONE
     * Wenn einer der Services nicht erreichbar -> BestellException
     * Wenn pleite PleiteException
     * Erst Solvenzprüfung, dann Bestellung speichern
     * Saldo muss >= 0 sein -> BestellException
     */
    public void bestellungErfassen(Bestellung bestellung, String creditkarte, double saldo)
        throws BestellServiceException, KundeIstPleitException
    {

        try
        {
            bestellungErfassenImpl(bestellung, creditkarte, saldo);
        }

        catch (RemoteException e)
        {
            throw new BestellServiceException("SolvenzService nicht verfügbar.", e);
        }
        catch (RuntimeException e)
        {
            throw new BestellServiceException("Repo nicht verfügbar.", e);
        }
    }
    private void bestellungErfassenImpl(Bestellung bestellung, String creditkarte, double saldo)
        throws BestellServiceException, RemoteException, KundeIstPleitException
    {
        final Pattern pattern = Pattern.compile(PATTERN_KREDITKARTE);

        if (null == bestellung)
        {
            throw new BestellServiceException("Bestellung darf nicht null sein.");
        }
        if (null == creditkarte)
        {
            throw new BestellServiceException("Kreditkarte darf nicht null sein.");
        }
        if (saldo < 0)
        {
            throw new BestellServiceException("Saldo darf nicht kleiner 0 sein.");
        }


        final Matcher matcher = pattern.matcher(creditkarte);
        if (!matcher.matches())
        {
            throw new BestellServiceException(
                "Kreditkartenname muss mit M oder V beginnen, danach genau 10 Ziffern enthalten.");
        }
        final String type = matcher.group(1).equals("M") ? "Master" : "Visa";
        final String number = matcher.group(2);
        if (!solvenzService.check(type, number, saldo))
        {
            throw new KundeIstPleitException("Kunde ist pleite");
        }
        bestellRepository.save(bestellung);
    }

}
