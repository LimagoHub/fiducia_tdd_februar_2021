package de.fiducia.tiere;

public class Schwein {
	
	public static final int MINDESTGEWICHT = 10;
	
	private String name; // nicht null, nicht Elsa
	private int gewicht; // mindestens 10;
	
	public Schwein() {
		this("nobody");
	}
	
	public Schwein(String name) {
		setName(name);
		setGewicht(MINDESTGEWICHT);
	}
	
	
	public String getName() {
		return name;
	}
	public final void setName(String name) { // nicht null, nicht Elsa -> IllegalArgumentException auslösen
		this.name = name;
	}
	public int getGewicht() {
		return gewicht;
	}
	private void setGewicht(int gewicht) {
		this.gewicht = gewicht;
	}
	
	public void fressen() {
		setGewicht(getGewicht() + 1);
	}
	

	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Schwein [name=");
		builder.append(name);
		builder.append(", gewicht=");
		builder.append(gewicht);
		builder.append("]");
		return builder.toString();
	}
	

}
