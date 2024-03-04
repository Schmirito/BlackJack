package blackJack;

public class Spieler {
	
	public int id;
	public int geld;
	public String name;
	public int einsatz = 0;
	public int kartenWertGes = 0;
	public int kartenBekommen = 0;
	public boolean gewonnen;
	public int gespeicherterGeldWert;
	
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
	}
	public void initGeld() {
		geld = gespeicherterGeldWert;
	}
	public void setGeldGespeichert(int gespeicherterWert) {
		geld = gespeicherterWert;
	}
	public void setGewonnen() {
		
	}
}
