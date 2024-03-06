package blackJack;

import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JTextField;

public class Steuerung {

//---------Deklaration_Assoziationen-----
	GUI dieGui;
	ArrayList<Karte> dasDeck = new ArrayList<>();
	ArrayList<Karte> dieKarten = new ArrayList<>();
	ArrayList<Spieler> dieSpieler = new ArrayList<>();
	Dealer derDealer;

//---------Deklaration_Variablen--------
	private final int DECK_GROESSE = 52;
	private String farben[] = { "Pik", "Herz", "Kreuz", "Karo" };
	private int anzKartenFarbe = DECK_GROESSE / 4;

	public int momentanerSpieler = 0;
	public final int MAX_SPIELER_ANZ = 4;
	public final int STARTGELD = 500;
	public int geld = 500;
	public int id = 0;
	public ArrayList<Integer> geldSpeicher = new ArrayList<>();

	private final int BLACKJACK = 21;
	private final int SOFT_STAND = 17;
	private final String busted = "Busted";
	private final String blackJack = "Black Jack";
	private final String lost = "Lost";
	private final String won = "Won";
	private final String push = "Push";

	private final String dealerBusted = "Dealer busted";
	private final String dealerWinsAll = "Dealer wins";
	private final String dealerLosesAll = "Dealer loses";

	public Steuerung(GUI dieGui) {
		this.dieGui = dieGui;
		initSpiel();
	}

//---------------INIT--------------------
	public void initSpiel() {
		initGeldVorrunde(geld);
		initSteuerungDaten();
//		initSpielerDaten();
		initDealerDaten();
		if (dasDeck.size() == 0) {
			initDeck();
		}
		initGui();
	}

	public void spielerGeldGespeichert() {
		for (int i = 0; i < dieSpieler.size(); i++) {
			if (dieSpieler.get(i) != null) {
				int j = dieSpieler.get(i).geld;
				dieSpieler.get(i).setGeldGespeichert(j);
			}
		}
	}

	private void initGui() {
		dieGui.datenReset();
	}

	public void initGeldVorrunde(int geld) {
		for (int i = 0; i < 4; i++) {
			geldSpeicher.add(geld);
		}
	}

	public void initSteuerungDaten() {
		momentanerSpieler = 0;
		id = 0;

		for (int i = 0; i < dieSpieler.size(); i++) {
			geldSpeicher.add(dieSpieler.get(i).geld);
		}

		dieSpieler.clear();
		dieKarten.clear();
	}

	private void initSpielerDaten() {
		for (int i = 0; i < dieSpieler.size(); i++) {
			if (dieSpieler.get(i) != null) {
				dieSpieler.get(i).datenReset();
				dieSpieler.get(i).initGeld();
			}
		}
	}

	private void initDealerDaten() {
		if (derDealer != null)
			derDealer.datenReset();
	}

	private void initDeck() {
		dasDeck.clear();
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

	public void pruefeSpielerStatus() {
		for (int i = 0; i < dieSpieler.size(); i++) {
			if (dieSpieler.get(i).kartenWertGes > BLACKJACK) {
				dieGui.tfStatusSpieler.get(i).setText(busted);
				dieSpieler.get(i).status = busted;
			} else if (dieSpieler.get(i).kartenWertGes == derDealer.wertKarteGes) {
				dieGui.tfStatusSpieler.get(i).setText(push);
				dieSpieler.get(i).status = push;
			} else if (dieSpieler.get(i).kartenWertGes < derDealer.wertKarteGes
					&& derDealer.wertKarteGes <= BLACKJACK) {
				dieGui.tfStatusSpieler.get(i).setText(lost);
				dieSpieler.get(i).status = lost;
			} else if (dieSpieler.get(i).kartenWertGes > derDealer.wertKarteGes
					&& dieSpieler.get(i).kartenWertGes < BLACKJACK) {
				dieGui.tfStatusSpieler.get(i).setText(won);
				dieSpieler.get(i).status = won;
			} else {
				dieGui.tfStatusSpieler.get(i).setText(won);
			}
			dieSpieler.get(i).setStatus();
			
		}
	}

	public void pruefeDealerStatus() {
		int statusSpieler = 0;
		for (int i = 0; i < dieSpieler.size(); i++) {
			if (dieSpieler.get(i).status == "Lose") {
				if (statusSpieler == dieSpieler.size()) {
					dieGui.tfErgebnisSpiel.setText(dealerWinsAll);
				} else if (statusSpieler == 1) {
					dieGui.tfErgebnisSpiel.setText(" " + dieSpieler.get(statusSpieler) + " wins");
				} else if (statusSpieler == 2) {
					dieGui.tfErgebnisSpiel.setText(
							" " + dieSpieler.get(statusSpieler - 1) + ", " + dieSpieler.get(statusSpieler) + " win");
				} else {
					dieGui.tfErgebnisSpiel.setText(" " + dieSpieler.get(statusSpieler - 2) + ", "
							+ dieSpieler.get(statusSpieler - 1) + ", " + dieSpieler.get(statusSpieler) + " win");
				}
				statusSpieler++;
			} else {
				statusSpieler++;
			}
		}
		if (statusSpieler == 0) {
			dieGui.tfErgebnisSpiel.setText(dealerLosesAll);
		}
		statusSpieler = 0;
	}

	public void anzeigenKarten() {
		for (int i = 0; i < dieSpieler.size(); i++) {
			dieGui.tfSpielerKarten.get(i).setText(" " + dieSpieler.get(i).kartenWertGes + " ");
		}

	}

	public void anzeigenEinsatz() {
		dieGui.tfSpielerEinsatz.get(momentanerSpieler).setText(" " + dieSpieler.get(momentanerSpieler).einsatz + " ");
	}

	public void anzeigenDealer() {
		dieGui.tfKartenWertD.setText(" " + derDealer.wertKarteGes + " ");
	}

	public void austeilen(ArrayList<JTextField> tfSKarten, JTextField tfDKarten) {
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < dieSpieler.size(); j++) {
				dieSpieler.get(j).kartenWertGes += dasDeck.get(0).getWert();
				dieSpieler.get(j).kartenBekommen++;
				tfSKarten.get(j).setText(" " + dieSpieler.get(j).kartenWertGes + " ");
				dasDeck.remove(0);
				dieGui.deckUpdate();

				if (dieSpieler.get(j).kartenBekommen == 2 && dieSpieler.get(j).kartenWertGes == BLACKJACK) {
					dieGui.tfStatusSpieler.get(momentanerSpieler).setText(blackJack);
					dieSpieler.get(j).status = blackJack;
					momentanerSpieler++;
				}
			}
			if (i == 0) {
				derDealer.wertKarteGes += dasDeck.get(0).getWert();
				derDealer.wertKarteOffen = dasDeck.get(0).getWert();
				derDealer.kartenBekommen++;
				tfDKarten.setText(" " + derDealer.wertKarteOffen + " ");
				dasDeck.remove(0);
				dieGui.deckUpdate();
			}
			if (i == 1) {
				derDealer.wertKarteGes += dasDeck.get(0).getWert();
				derDealer.wertKarteVerdeckt = dasDeck.get(0).getWert();
				derDealer.kartenBekommen++;
				dasDeck.remove(0);
				dieGui.deckUpdate();
			}
		}
	}

	public void hinzufuegenSpieler(String name, int geld) {
		dieSpieler.add(new Spieler(id, name, geld));
		id++;
	}

	public void neueNutzer() {
		for (int i = 0; i < dieSpieler.size(); i++) {
			
			dieSpieler.get(i).geld = STARTGELD;
			initSpielerDaten();
		}
		dieGui.neueNutzerGUI(false);
	}

	public void hinzufuegenDealer() {
		derDealer = new Dealer();
	}

	public void showDown() {
		dieGui.setActionButtonEnable(false);
		for (int i = 0; i < dieSpieler.size(); i++) {
			dieSpieler.get(i).kartenWertGes += dieSpieler.get(i).karteDoubleDown;
		}
		anzeigenKarten();
		dieGui.tfKartenWertD.setText(" " + derDealer.wertKarteGes + " ");

		while (derDealer.wertKarteGes < SOFT_STAND) {
			derDealer.wertKarteGes += dasDeck.get(0).getWert();
			derDealer.kartenBekommen++;
			anzeigenDealer();
			if (derDealer.wertKarteGes > BLACKJACK) {
				dieGui.tfErgebnisSpiel.setText(dealerBusted);
			}
		}
		pruefeSpielerStatus();
		auszahlenSpieler();
		pruefeDealerStatus();
		anzeigenGeldSteuerung();
		spielerGeldGespeichert();
	}

	private void auszahlenSpieler() {
		for (int i = 0; i < dieSpieler.size(); i++) {
			if (dieSpieler.get(i).status == "Black Jack") {
				dieSpieler.get(i).geld += (int) dieSpieler.get(i).einsatz * 2.5;
			} else if (dieSpieler.get(i).gewonnen) {
				dieSpieler.get(i).geld += dieSpieler.get(i).einsatz * 2;
			}
		}
		dieGui.anzeigenGeld(dieSpieler.get(momentanerSpieler).geld);
		
	}

	public int getMomentanesSpielerGeld() {
		int i = 0;
		if (momentanerSpieler < 4)
			i = dieSpieler.get(momentanerSpieler).geld;

		return i;
	}

	public void setKarteDoubleDown() {
		dieSpieler.get(momentanerSpieler).karteDoubleDown = dasDeck.get(0).getWert();
		dasDeck.remove(0);
		dieGui.deckUpdate();
	}

	public int gibGeldVorrunde(int i) {
		return geldSpeicher.get(i);
	}

//-------------Actions-------------------
	public void setzen(ArrayList<JTextField> tfEinsatzA, int einsatz) {
		if (dieSpieler.get(momentanerSpieler).geld >= einsatz) {
			dieSpieler.get(momentanerSpieler).geld -= einsatz;
			dieSpieler.get(momentanerSpieler).einsatz += einsatz;
			anzeigenGeldSteuerung();
			tfEinsatzA.get(momentanerSpieler).setText(" " + dieSpieler.get(momentanerSpieler).einsatz + " ");
		}
	}

	public void anzeigenGeldSteuerung() {
		dieGui.anzeigenGeld(dieSpieler.get(momentanerSpieler).geld);
	}

	public void neueKarte() {
		dieSpieler.get(momentanerSpieler).kartenWertGes += dasDeck.get(0).getWert();
		dieSpieler.get(momentanerSpieler).kartenBekommen++;
		dasDeck.remove(0);
		anzeigenKarten();
		dieGui.deckUpdate();

		if (dieSpieler.get(momentanerSpieler).kartenWertGes > BLACKJACK) {
			dieGui.tfStatusSpieler.get(momentanerSpieler).setText(busted);
			momentanerSpieler++;
			if (momentanerSpieler >= 4) {
				momentanerSpieler = 0;
				showDown();
			}
		}
		if (dieSpieler.get(momentanerSpieler).kartenWertGes == BLACKJACK) {
			momentanerSpieler++;
			if (momentanerSpieler >= 4) {
				momentanerSpieler = 0;
				showDown();
			}
		}
	}

	public void doubleDown() {
		if (dieSpieler.get(momentanerSpieler).doubleDown == false && momentanerSpieler < 4
				&& dieSpieler.get(momentanerSpieler).kartenBekommen == 2) {
			setKarteDoubleDown();
			dieSpieler.get(momentanerSpieler).doubleDown = true;
			dieSpieler.get(momentanerSpieler).einsatz = dieSpieler.get(momentanerSpieler).einsatz * 2;
			anzeigenEinsatz();
			momentanerSpieler++;
			if (momentanerSpieler == 4) {
				showDown();
			}
		} else if (momentanerSpieler >= 4) {
			dieGui.state = "showDown";
		}
	}

}
