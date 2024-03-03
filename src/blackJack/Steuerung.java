package blackJack;

import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JTextField;

public class Steuerung {

//---------Deklaration_Assoziationen-----
	GUI dieGui;
	ArrayList<Karte> dasDeck = new ArrayList<>();
	ArrayList<Karte> dieKarten = new ArrayList<>();
	Spieler dieSpieler[] = new Spieler[4];
	Dealer derDealer;

//---------Deklaration_Variablen--------
	private final int DECK_GROESSE = 52;
	private String farben[] = { "Pik", "Herz", "Kreuz", "Karo" };
	private int anzKartenFarbe = DECK_GROESSE / 4;

	public int momentanerSpieler = 0;
	public final int MAX_SPIELER_ANZ = 4;
	private final int STARTGELD = 500;
	public int id = 0;

	private final int BLACKJACK = 21;
	private final int SOFT_STAND = 17;
	private final String busted = "Busted";
	private final String blackJack = "Black Jack";
	private final String dealerBusted = "Dealer busted";
	
	public Steuerung(GUI dieGui) {
		this.dieGui = dieGui;
		initSpiel();
	}

//---------------INIT--------------------
	private void initSpiel() {
		initDeck();
	}

	private void initDeck() {
		// Erstelle alle Karten des Decks
		for (int i = 0; i < farben.length; i++) {
			for (int j = 1; j <= anzKartenFarbe; j++) {
				dasDeck.add(new Karte(j, farben[i]));
			}
		}
		// Mische das Deck
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < DECK_GROESSE; j++) {
				int randomZahl = (int) (Math.random() * dasDeck.size());

				dieKarten.add(dasDeck.get(randomZahl));
				dasDeck.remove(randomZahl);
			}
			for (int j = 0; j < DECK_GROESSE; j++) {
				int randomZahl = (int) (Math.random() * dieKarten.size());

				dasDeck.add(dieKarten.get(randomZahl));
				dieKarten.remove(randomZahl);
			}
		}
	}

	public void anzeigen() {
		dieGui.getTfSpielerKarten().get(momentanerSpieler).setText(" " + dieSpieler[momentanerSpieler].kartenWertGes);
	}

	public void anzeigenDealer() {
		dieGui.tfKartenWertD.setText(" " + derDealer.wertKarteGes + " ");
	}

	public void austeilen(ArrayList<JTextField> tfSKarten, JTextField tfDKarten) {
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < dieSpieler.length; j++) {
				dieSpieler[j].kartenWertGes = dasDeck.get(0).getWert();
				dieSpieler[j].kartenBekommen++;
				tfSKarten.get(j).setText(" " + dieSpieler[j].kartenWertGes + " ");
				dasDeck.remove(0);
			}
			if (i == 0) {
				derDealer.wertKarteGes = dasDeck.get(0).getWert();
				derDealer.wertKarteOffen = dasDeck.get(0).getWert();
				tfDKarten.setText(" " + derDealer.wertKarteOffen + " ");
				dasDeck.remove(0);
			}
			if (i == 1) {
				derDealer.wertKarteGes = dasDeck.get(0).getWert();
				derDealer.wertKarteVerdeckt = dasDeck.get(0).getWert();
				dasDeck.remove(0);
			}
		}
	}

	public void hinzufuegenSpieler(String name) {
		dieSpieler[id] = new Spieler(id, name, STARTGELD);
		id++;
	}

	public void hinzufuegenDealer() {
		derDealer = new Dealer();
	}

	public void showDown() {
		dieGui.tfKartenWertD.setText(" " + derDealer.wertKarteGes + " ");

		while (derDealer.wertKarteGes < SOFT_STAND) {
			derDealer.wertKarteGes += dasDeck.get(0).getWert();
			anzeigenDealer();
			if (derDealer.wertKarteGes > BLACKJACK) {
				dieGui.tfErgebnisSpiel.setText(dealerBusted);
			}
		}

	}

//-------------Actions-------------------
	public void setzen(ArrayList<JTextField> tfEinsatzA, int einsatz) {

		dieSpieler[momentanerSpieler].einsatz += einsatz;

		tfEinsatzA.get(momentanerSpieler).setText(" " + dieSpieler[momentanerSpieler].einsatz + " ");
	}

	public void neueKarte() {
		dieSpieler[momentanerSpieler].kartenWertGes += dasDeck.get(0).getWert();
		dasDeck.remove(0);
		anzeigen();
		if (dieSpieler[momentanerSpieler].kartenWertGes > BLACKJACK) {
			dieGui.tfStatusSpieler.get(momentanerSpieler).setText(busted);
			momentanerSpieler++;
			if (momentanerSpieler >= 4) {
				showDown();
			}
		} else if (dieSpieler[momentanerSpieler].kartenWertGes == BLACKJACK && dieSpieler[momentanerSpieler].kartenBekommen == 2) {
			dieGui.tfStatusSpieler.get(momentanerSpieler).setText(blackJack);
			momentanerSpieler++;
			if (momentanerSpieler >= 4) {
				showDown();
			}
		}

	}

}
