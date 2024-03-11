package blackJack;

public class Spieler {
	
	public int id;
	public int geld;
	public String name;
	public int einsatz = 0;
	public int kartenWertGes = 0;
	public int kartenBekommen = 0;
	public boolean assBekommen = false;
	public int asseInsgesamt = 0;
	public int kartenWertMitAss = 0;
	public boolean gewonnen;
	public int gespeicherterGeldWert;
	public boolean doubleDown = false;
	public int karteDoubleDown = 0;
	public String status = "default";
	
	public Spieler(int id,String name,int startGeld) {
		this.id = id;
		this.name = name;
		
		if(gespeicherterGeldWert == 0)
		geld = startGeld;
	}
	public void datenReset() {
		einsatz = 0;
		kartenWertGes = 0;
		kartenBekommen = 0;
		doubleDown = false;
		karteDoubleDown = 0;
		status = "";
	}
	public void setGewonnen(boolean status) {
		gewonnen = status;
	}
	public void initGeld() {
		geld = gespeicherterGeldWert;
	}
	public void setGeldGespeichert(int gespeicherterWert) {
		geld = gespeicherterWert;
	}
	public void setStatus() {
		switch (status) {
		case "Busted":
			status = "Lost";
			break;
		case "Won":
			setGewonnen(true);
			break;
		default:
			break;
		}
	}
}
