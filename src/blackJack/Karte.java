package blackJack;

public class Karte {
	
	private int wert;
	private String farbe;
	private String bezeichnung;
	
	public Karte(int wert, String farbe) {
		this.wert = wert;
		this.farbe = farbe;
		setBezeichnung();
	}
	public int getWert() {
		
		return wert;
	}
	public String getFarbe() {
		
		return farbe;
	}
	private void setBezeichnung() {
		switch (wert) {
		case 1:
			bezeichnung = "Ass "+farbe;
			break;
		case 11:
			bezeichnung = "Bube "+farbe;
			wert = 10;
			break;
		case 12:
			bezeichnung = "Dame "+farbe;
			wert = 10;
			break;
		case 13:
			bezeichnung = "Koenig "+farbe;
			wert = 10;
			break;
		default:
			bezeichnung = wert+" "+farbe;
			break;
		}
	}
	public String getBezeichnung() {
		
		return bezeichnung;
	}
}
