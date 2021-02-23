package de.fiducia.bestellungen.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import de.fiducia.bestellungen.repositories.BestellRepository;

@ExtendWith(MockitoExtension.class)
public class BestellServiceImplTest {
	@Mock
	private BestellRepository bestellRepository;

	@Mock
	private SolvenzService solvenzService;
	@InjectMocks
	private BestellServiceImpl bestellServiceImpl;

}
