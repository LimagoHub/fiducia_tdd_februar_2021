package de.fiducia.bestellungen.service;

import java.rmi.RemoteException;

public interface SolvenzService {
	
	// Type = Master oder Visa
	// cardnumber = 10 ziffern
	// saldo
	boolean check(String type, String cardnumber, double saldo) throws RemoteException;

}
