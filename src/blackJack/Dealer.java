package blackJack;

public class Dealer {

	public int wertKarteVerdeckt = 0;
	public int wertKarteOffen = 0;
	public int wertKarteGes = 0;
	public int kartenBekommen = 0;
	
	public Dealer() {
		
	}
	public void datenReset() {
		wertKarteGes = 0;
		wertKarteOffen = 0;
		wertKarteVerdeckt = 0;
		kartenBekommen = 0;
	}
}
