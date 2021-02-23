package de.fiducia.bestellungen.service;

import de.fiducia.bestellungen.models.Bestellung;
import de.fiducia.bestellungen.repositories.BestellRepository;

public class BestellServiceImpl {
	
	private final BestellRepository bestellRepository;
	private final SolvenzService solvenzService;
	
	public BestellServiceImpl(BestellRepository bestellRepository, SolvenzService solvenzService) {
		
		this.bestellRepository = bestellRepository;
		this.solvenzService = solvenzService;
	}
	// Creditkarte = Entweder M oder V direkt gefolgt von exakt 10 ziffern, also 11 Stellen
	/*
	 * Nullprüfung -> BestellException
	 * Format der Karte ist falsch -> BestellException
	 * Wenn einer der Services nicht erreichbar -> BestellException
	 * Wenn pleite PleiteException
	 * Erst Solvenzprüfung, dann Bestellung speichern
	 */
	public void bestellungErfassen(Bestellung bestellung, String creditkarte, double saldo) throws BestellServiceException, KundeIstPleitException{
		
	}

}
