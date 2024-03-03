package blackJack;

public class Spieler {
	
	public int id;
	public int geld;
	public String name;
	public int einsatz = 0;
	public int kartenWertGes = 0;
	public int kartenBekommen = 0;
	
	public Spieler(int id,String name,int startGeld) {
		this.id = id;
		this.name = name;
		geld = startGeld;
	}
}
