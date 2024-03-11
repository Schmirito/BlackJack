package blackJack;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GUI {

	private JFrame frame;
	private JTextField tfSetzen;
	private JTextField tfGeld;
	private JTextField tfDealer;
	private JTextField tfDeck;
	private JTextField tfSpieler1;
	private JTextField tfSpieler2;
	private JTextField tfSpieler3;
	private JTextField tfVerlauf;
	private JTextField tfSpieler4;

	public ArrayList<JTextField> tfSpielerEinsatz = new ArrayList<>();
	public ArrayList<JTextField> tfSpielerKarten = new ArrayList<>();
	public ArrayList<JTextField> tfStatusSpieler = new ArrayList<>();
	public ArrayList<JTextField> tfSpieler = new ArrayList<>();
	public ArrayList<JButton> btSetzen = new ArrayList<>();
	public ArrayList<JButton> btAction = new ArrayList<>();
	ArrayList<Integer> geld = new ArrayList<>();
	public JButton btNeueSpieler;
	public JButton btNaechsterSpieler;
	
	public boolean spielStart = true;
	static public boolean spielBeendet = false;

	public String state = "default";

	private JTextField tfEinsatzS1;
	private JTextField tfEinsatzS2;
	private JTextField tfEinsatzS3;
	private JTextField tfEinsatzS4;
	private JTextField tfKartenWertS2;
	private JTextField tfKartenWertS1;
	private JTextField tfKartenWertS3;
	private JTextField tfKartenWertS4;
	public JTextField tfKartenWertD;

	Steuerung dieSteuerung;
	private JTextField tfStatusS1;
	private JTextField tfStatusS2;
	private JTextField tfStatusS3;
	private JTextField tfStatusS4;
	public JTextField tfErgebnisSpiel;
	public JTextField tfAktiverSpieler;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frame.setVisible(true);
					window.frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	/**
	 * Create the application.
	 */
	public GUI() {
		dieSteuerung = new Steuerung(this);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 780, 450);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		frame.setResizable(false);

		JPanel pAction = new JPanel();
		frame.getContentPane().add(pAction, BorderLayout.SOUTH);

		JButton btVersicherung = new JButton("Versicherung");
		btAction.add(btVersicherung);
		btVersicherung.setEnabled(false);
		btVersicherung.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dieSteuerung.anzeigenGeldSteuerung();
			}
		});
		pAction.add(btVersicherung);

		JButton btStand = new JButton("Stand");
		btAction.add(btStand);
		btStand.setEnabled(false);
		btStand.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (dieSteuerung.momentanerSpieler < 3) {
					dieSteuerung.momentanerSpieler++;
					anzeigenMomSpieler();
					dieSteuerung.anzeigenGeldSteuerung();
				} else if (state == "austeilen") {
					state = "showDown";
					dieSteuerung.showDown();
					dieSteuerung.momentanerSpieler = 0;
				}
				tfAktiverSpieler.setText("Mom. Spieler: " + tfSpieler.get(dieSteuerung.momentanerSpieler).getText());
			}
		});
		pAction.add(btStand);

		JButton btDouble = new JButton("Double");
		btAction.add(btDouble);
		btDouble.setEnabled(false);
		btDouble.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dieSteuerung.doubleDown();
				dieSteuerung.anzeigenGeldSteuerung();
				tfAktiverSpieler.setText("Mom. Spieler: " + tfSpieler.get(dieSteuerung.momentanerSpieler).getText());
				anzeigenMomSpieler();
			}
		});
		pAction.add(btDouble);

		JButton btHit = new JButton("Hit");
		btAction.add(btHit);
		btHit.setEnabled(false);
		btHit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (dieSteuerung.momentanerSpieler < 4 && state == "austeilen") {
					dieSteuerung.neueKarte();
					dieSteuerung.anzeigenGeldSteuerung();
					anzeigenMomSpieler();
				} else {
					state = "showDown";
					dieSteuerung.momentanerSpieler = 0;
					dieSteuerung.showDown();
					tfAktiverSpieler.setText("Mom. Spieler: " + tfSpieler.get(dieSteuerung.momentanerSpieler).getText());
				}
			}
		});
		pAction.add(btHit);

		JPanel pSetzen = new JPanel();
		frame.getContentPane().add(pSetzen, BorderLayout.WEST);
		GridBagLayout gbl_pSetzen = new GridBagLayout();
		gbl_pSetzen.columnWidths = new int[] { 86, 0 };
		gbl_pSetzen.rowHeights = new int[] { 20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_pSetzen.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gbl_pSetzen.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		pSetzen.setLayout(gbl_pSetzen);

		tfSetzen = new JTextField();
		tfSetzen.setEditable(false);
		tfSetzen.setHorizontalAlignment(SwingConstants.CENTER);
		tfSetzen.setText("Setzen:");
		GridBagConstraints gbc_tfSetzen = new GridBagConstraints();
		gbc_tfSetzen.insets = new Insets(0, 0, 5, 0);
		gbc_tfSetzen.anchor = GridBagConstraints.NORTH;
		gbc_tfSetzen.gridx = 0;
		gbc_tfSetzen.gridy = 0;
		pSetzen.add(tfSetzen, gbc_tfSetzen);
		tfSetzen.setColumns(10);

		JButton btFuenf = new JButton("5");
		btSetzen.add(btFuenf);
		btFuenf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (state == "setzen") {
					dieSteuerung.setzen(tfSpielerEinsatz, 5);
				}
			}
		});
		GridBagConstraints gbc_btFuenf = new GridBagConstraints();
		gbc_btFuenf.fill = GridBagConstraints.HORIZONTAL;
		gbc_btFuenf.insets = new Insets(0, 0, 5, 0);
		gbc_btFuenf.gridx = 0;
		gbc_btFuenf.gridy = 1;
		pSetzen.add(btFuenf, gbc_btFuenf);

		JButton btZehn = new JButton("10");
		btSetzen.add(btZehn);
		btZehn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (state == "setzen") {
					dieSteuerung.setzen(tfSpielerEinsatz, 10);
				}
			}
		});
		GridBagConstraints gbc_btZehn = new GridBagConstraints();
		gbc_btZehn.fill = GridBagConstraints.HORIZONTAL;
		gbc_btZehn.insets = new Insets(0, 0, 5, 0);
		gbc_btZehn.gridx = 0;
		gbc_btZehn.gridy = 2;
		pSetzen.add(btZehn, gbc_btZehn);

		JButton btFuenfUndZwanzig = new JButton("25");
		btSetzen.add(btFuenfUndZwanzig);
		btFuenfUndZwanzig.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (state == "setzen") {
					dieSteuerung.setzen(tfSpielerEinsatz, 25);
				}
			}
		});
		GridBagConstraints gbc_btFuenfUndZwanzig = new GridBagConstraints();
		gbc_btFuenfUndZwanzig.fill = GridBagConstraints.HORIZONTAL;
		gbc_btFuenfUndZwanzig.insets = new Insets(0, 0, 5, 0);
		gbc_btFuenfUndZwanzig.gridx = 0;
		gbc_btFuenfUndZwanzig.gridy = 3;
		pSetzen.add(btFuenfUndZwanzig, gbc_btFuenfUndZwanzig);

		JButton btFuenfzig = new JButton("50");
		btSetzen.add(btFuenfzig);
		btFuenfzig.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (state == "setzen") {
					dieSteuerung.setzen(tfSpielerEinsatz, 50);
				}
			}
		});
		GridBagConstraints gbc_btFuenfzig = new GridBagConstraints();
		gbc_btFuenfzig.insets = new Insets(0, 0, 5, 0);
		gbc_btFuenfzig.fill = GridBagConstraints.HORIZONTAL;
		gbc_btFuenfzig.gridx = 0;
		gbc_btFuenfzig.gridy = 4;
		pSetzen.add(btFuenfzig, gbc_btFuenfzig);

		JButton btHundert = new JButton("100");
		btSetzen.add(btHundert);
		btHundert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (state == "setzen") {
					dieSteuerung.setzen(tfSpielerEinsatz, 100);
				}
			}
		});
		GridBagConstraints gbc_btHundert = new GridBagConstraints();
		gbc_btHundert.insets = new Insets(0, 0, 5, 0);
		gbc_btHundert.fill = GridBagConstraints.HORIZONTAL;
		gbc_btHundert.gridx = 0;
		gbc_btHundert.gridy = 5;
		pSetzen.add(btHundert, gbc_btHundert);

		JButton btZweihundertFuenfzig = new JButton("250");
		btSetzen.add(btZweihundertFuenfzig);
		btZweihundertFuenfzig.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (state == "setzen") {
					dieSteuerung.setzen(tfSpielerEinsatz, 250);
				}
			}
		});
		GridBagConstraints gbc_btZweihundertFuenfzig = new GridBagConstraints();
		gbc_btZweihundertFuenfzig.insets = new Insets(0, 0, 5, 0);
		gbc_btZweihundertFuenfzig.fill = GridBagConstraints.HORIZONTAL;
		gbc_btZweihundertFuenfzig.gridx = 0;
		gbc_btZweihundertFuenfzig.gridy = 6;
		pSetzen.add(btZweihundertFuenfzig, gbc_btZweihundertFuenfzig);

		JButton btFuenfHundert = new JButton("500");
		btSetzen.add(btFuenfHundert);
		btFuenfHundert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (state == "setzen") {
					dieSteuerung.setzen(tfSpielerEinsatz, 500);
				}
			}
		});
		GridBagConstraints gbc_btFuenfHundert = new GridBagConstraints();
		gbc_btFuenfHundert.fill = GridBagConstraints.HORIZONTAL;
		gbc_btFuenfHundert.insets = new Insets(0, 0, 5, 0);
		gbc_btFuenfHundert.gridx = 0;
		gbc_btFuenfHundert.gridy = 7;
		pSetzen.add(btFuenfHundert, gbc_btFuenfHundert);

		JButton btTausend = new JButton("1.000");
		btSetzen.add(btTausend);
		btTausend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (state == "setzen") {
					dieSteuerung.setzen(tfSpielerEinsatz, 1000);
				}
			}
		});
		GridBagConstraints gbc_btTausend = new GridBagConstraints();
		gbc_btTausend.insets = new Insets(0, 0, 5, 0);
		gbc_btTausend.fill = GridBagConstraints.HORIZONTAL;
		gbc_btTausend.gridx = 0;
		gbc_btTausend.gridy = 8;
		pSetzen.add(btTausend, gbc_btTausend);

		JButton btZweiTausendFuenfHundert = new JButton("2.500");
		btSetzen.add(btZweiTausendFuenfHundert);
		btZweiTausendFuenfHundert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (state == "setzen") {
					dieSteuerung.setzen(tfSpielerEinsatz, 2500);
				}
			}
		});
		GridBagConstraints gbc_btZweiTausendFuenfHundert = new GridBagConstraints();
		gbc_btZweiTausendFuenfHundert.insets = new Insets(0, 0, 5, 0);
		gbc_btZweiTausendFuenfHundert.anchor = GridBagConstraints.NORTH;
		gbc_btZweiTausendFuenfHundert.fill = GridBagConstraints.HORIZONTAL;
		gbc_btZweiTausendFuenfHundert.gridx = 0;
		gbc_btZweiTausendFuenfHundert.gridy = 9;
		pSetzen.add(btZweiTausendFuenfHundert, gbc_btZweiTausendFuenfHundert);

		JButton btFuenfTausend = new JButton("5.000");
		btSetzen.add(btFuenfTausend);
		btFuenfTausend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (state == "setzen") {
					dieSteuerung.setzen(tfSpielerEinsatz, 5000);
				}
			}
		});
		GridBagConstraints gbc_btFuenfTausend = new GridBagConstraints();
		gbc_btFuenfTausend.insets = new Insets(0, 0, 5, 0);
		gbc_btFuenfTausend.anchor = GridBagConstraints.NORTH;
		gbc_btFuenfTausend.fill = GridBagConstraints.HORIZONTAL;
		gbc_btFuenfTausend.gridx = 0;
		gbc_btFuenfTausend.gridy = 10;
		pSetzen.add(btFuenfTausend, gbc_btFuenfTausend);

		setzenButtonEnable(btSetzen, false);

		JPanel pMain = new JPanel();
		frame.getContentPane().add(pMain, BorderLayout.CENTER);
		pMain.setLayout(null);

		tfGeld = new JTextField();
		tfGeld.setHorizontalAlignment(SwingConstants.LEFT);
		tfGeld.setEditable(false);
		tfGeld.setText("Geld:");
		tfGeld.setBounds(10, 11, 97, 20);
		pMain.add(tfGeld);
		tfGeld.setColumns(10);

		tfDealer = new JTextField();
		tfDealer.setHorizontalAlignment(SwingConstants.CENTER);
		tfDealer.setEditable(false);
		tfDealer.setText("Dealer");
		tfDealer.setBounds(217, 40, 77, 20);
		pMain.add(tfDealer);
		tfDealer.setColumns(10);

		tfDeck = new JTextField();
		tfDeck.setHorizontalAlignment(SwingConstants.LEFT);
		tfDeck.setEditable(false);
		tfDeck.setText("Deck:");
		tfDeck.setBounds(450, 11, 77, 20);
		pMain.add(tfDeck);
		tfDeck.setColumns(10);

		tfSpieler1 = new JTextField();
		tfSpieler.add(tfSpieler1);
		tfSpieler1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		tfSpieler1.setHorizontalAlignment(SwingConstants.CENTER);
		tfSpieler1.setText("Teilnehmen");
		tfSpieler1.setBounds(30, 218, 77, 20);
		pMain.add(tfSpieler1);
		tfSpieler1.setColumns(10);

		tfSpieler2 = new JTextField();
		tfSpieler.add(tfSpieler2);
		tfSpieler2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		tfSpieler2.setHorizontalAlignment(SwingConstants.CENTER);
		tfSpieler2.setText("Teilnehmen");
		tfSpieler2.setBounds(144, 271, 77, 20);
		pMain.add(tfSpieler2);
		tfSpieler2.setColumns(10);

		tfSpieler3 = new JTextField();
		tfSpieler3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		tfSpieler3.setText("Teilnehmen");
		tfSpieler.add(tfSpieler3);
		tfSpieler3.setHorizontalAlignment(SwingConstants.CENTER);
		tfSpieler3.setBounds(289, 271, 77, 20);
		pMain.add(tfSpieler3);
		tfSpieler3.setColumns(10);

		tfSpieler4 = new JTextField();
		tfSpieler4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		tfSpieler4.setText("Teilnehmen");
		tfSpieler.add(tfSpieler4);
		tfSpieler4.setHorizontalAlignment(SwingConstants.CENTER);
		tfSpieler4.setBounds(405, 218, 77, 20);
		pMain.add(tfSpieler4);
		tfSpieler4.setColumns(10);

		tfEinsatzS1 = new JTextField();
		tfEinsatzS1.setHorizontalAlignment(SwingConstants.CENTER);
		tfEinsatzS1.setEditable(false);
		tfEinsatzS1.setBounds(30, 236, 77, 20);
		pMain.add(tfEinsatzS1);
		tfEinsatzS1.setColumns(10);
		tfSpielerEinsatz.add(tfEinsatzS1);

		tfEinsatzS2 = new JTextField();
		tfEinsatzS2.setHorizontalAlignment(SwingConstants.CENTER);
		tfEinsatzS2.setEditable(false);
		tfEinsatzS2.setBounds(144, 290, 77, 20);
		pMain.add(tfEinsatzS2);
		tfEinsatzS2.setColumns(10);
		tfSpielerEinsatz.add(tfEinsatzS2);

		tfEinsatzS3 = new JTextField();
		tfEinsatzS3.setHorizontalAlignment(SwingConstants.CENTER);
		tfEinsatzS3.setEditable(false);
		tfEinsatzS3.setBounds(289, 290, 77, 20);
		pMain.add(tfEinsatzS3);
		tfEinsatzS3.setColumns(10);
		tfSpielerEinsatz.add(tfEinsatzS3);

		tfEinsatzS4 = new JTextField();
		tfEinsatzS4.setHorizontalAlignment(SwingConstants.CENTER);
		tfEinsatzS4.setEditable(false);
		tfEinsatzS4.setBounds(405, 236, 77, 20);
		pMain.add(tfEinsatzS4);
		tfEinsatzS4.setColumns(10);
		tfSpielerEinsatz.add(tfEinsatzS4);

		tfKartenWertS1 = new JTextField();
		tfKartenWertS1.setEditable(false);
		tfKartenWertS1.setHorizontalAlignment(SwingConstants.CENTER);
		tfKartenWertS1.setBounds(30, 169, 77, 38);
		pMain.add(tfKartenWertS1);
		tfKartenWertS1.setColumns(10);
		tfSpielerKarten.add(tfKartenWertS1);

		tfKartenWertS2 = new JTextField();
		tfKartenWertS2.setHorizontalAlignment(SwingConstants.CENTER);
		tfKartenWertS2.setEditable(false);
		tfKartenWertS2.setBounds(144, 222, 77, 38);
		pMain.add(tfKartenWertS2);
		tfKartenWertS2.setColumns(10);
		tfSpielerKarten.add(tfKartenWertS2);

		tfKartenWertS3 = new JTextField();
		tfKartenWertS3.setHorizontalAlignment(SwingConstants.CENTER);
		tfKartenWertS3.setEditable(false);
		tfKartenWertS3.setBounds(289, 221, 77, 39);
		pMain.add(tfKartenWertS3);
		tfKartenWertS3.setColumns(10);
		tfSpielerKarten.add(tfKartenWertS3);

		tfKartenWertS4 = new JTextField();
		tfKartenWertS4.setHorizontalAlignment(SwingConstants.CENTER);
		tfKartenWertS4.setEditable(false);
		tfKartenWertS4.setBounds(405, 165, 77, 42);
		pMain.add(tfKartenWertS4);
		tfKartenWertS4.setColumns(10);
		tfSpielerKarten.add(tfKartenWertS4);

		tfKartenWertD = new JTextField();
		tfKartenWertD.setHorizontalAlignment(SwingConstants.CENTER);
		tfKartenWertD.setEditable(false);
		tfKartenWertD.setBounds(217, 71, 77, 38);
		pMain.add(tfKartenWertD);
		tfKartenWertD.setColumns(10);

		tfStatusS1 = new JTextField();
		tfStatusS1.setHorizontalAlignment(SwingConstants.CENTER);
		tfStatusS1.setEditable(false);
		tfStatusS1.setBounds(30, 260, 77, 20);
		pMain.add(tfStatusS1);
		tfStatusS1.setColumns(10);
		tfStatusSpieler.add(tfStatusS1);

		tfStatusS2 = new JTextField();
		tfStatusS2.setHorizontalAlignment(SwingConstants.CENTER);
		tfStatusS2.setEditable(false);
		tfStatusS2.setBounds(144, 314, 77, 20);
		pMain.add(tfStatusS2);
		tfStatusS2.setColumns(10);
		tfStatusSpieler.add(tfStatusS2);

		tfStatusS3 = new JTextField();
		tfStatusS3.setHorizontalAlignment(SwingConstants.CENTER);
		tfStatusS3.setEditable(false);
		tfStatusS3.setBounds(289, 314, 77, 20);
		pMain.add(tfStatusS3);
		tfStatusS3.setColumns(10);
		tfStatusSpieler.add(tfStatusS3);

		tfStatusS4 = new JTextField();
		tfStatusS4.setHorizontalAlignment(SwingConstants.CENTER);
		tfStatusS4.setEditable(false);
		tfStatusS4.setBounds(405, 260, 77, 20);
		pMain.add(tfStatusS4);
		tfStatusS4.setColumns(10);
		tfStatusSpieler.add(tfStatusS4);

		tfErgebnisSpiel = new JTextField();
		tfErgebnisSpiel.setHorizontalAlignment(SwingConstants.CENTER);
		tfErgebnisSpiel.setEditable(false);
		tfErgebnisSpiel.setBounds(185, 120, 149, 38);
		pMain.add(tfErgebnisSpiel);
		tfErgebnisSpiel.setColumns(10);

		tfAktiverSpieler = new JTextField();
		tfAktiverSpieler.setEditable(false);
		tfAktiverSpieler.setText("Mom. Spieler: ");
		tfAktiverSpieler.setBounds(10, 314, 124, 20);
		pMain.add(tfAktiverSpieler);
		tfAktiverSpieler.setColumns(10);

		JPanel pTools = new JPanel();
		frame.getContentPane().add(pTools, BorderLayout.NORTH);

		btNaechsterSpieler = new JButton("Naechster Spieler");
		if (spielStart) {
			btNaechsterSpieler.setEnabled(false);
		}

		btNaechsterSpieler.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (dieSteuerung.momentanerSpieler < 4
						&& dieSteuerung.dieSpieler.get(dieSteuerung.momentanerSpieler).einsatz != 0) {
					dieSteuerung.momentanerSpieler++;
					anzeigenGeld(dieSteuerung.getMomentanesSpielerGeld());

					if (dieSteuerung.momentanerSpieler >= 4) {
						state = "austeilen";

						btNaechsterSpieler.setEnabled(false);
						btDouble.setEnabled(true);
						btHit.setEnabled(true);
						btVersicherung.setEnabled(true);
						btStand.setEnabled(true);

						dieSteuerung.austeilen(tfSpielerKarten, tfKartenWertD);
						dieSteuerung.momentanerSpieler = 0;
						anzeigenGeld(dieSteuerung.getMomentanesSpielerGeld());
					}
				}
				tfAktiverSpieler.setText("Mom. Spieler: " + tfSpieler.get(dieSteuerung.momentanerSpieler).getText());
			}

		});

		JButton btSpielStarten = new JButton("Spiel starten");
		btSpielStarten.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!spielStart) {
					geld.clear();
					for (int i = 0; i < dieSteuerung.dieSpieler.size(); i++)
						geld.add(dieSteuerung.dieSpieler.get(i).geld);

					dieSteuerung.dieSpieler.clear();
					for (int i = 0; i < tfSpielerEinsatz.size(); i++) {

						String name = tfSpieler.get(i).getText();
						dieSteuerung.hinzufuegenSpieler(name, geld.get(i));

					}
					
					tfSpieler1.setEditable(false);
					tfSpieler2.setEditable(false);
					tfSpieler3.setEditable(false);
					tfSpieler4.setEditable(false);

					btSpielStarten.setEnabled(false);
					btNaechsterSpieler.setEnabled(true);
					state = "setzen";
					anzeigenGeld(dieSteuerung.getMomentanesSpielerGeld());

					setzenButtonEnable(btSetzen, true);

					if (btNeueSpieler != null)
						btNeueSpieler.setEnabled(false);
				}

				if (spielStart) {

					for (int i = 0; i < tfSpieler.size(); i++) {

						String name = tfSpieler.get(i).getText();
						if (name != "Teilnehmen") {
							dieSteuerung.hinzufuegenSpieler(name, dieSteuerung.STARTGELD);
						} else if (name == "Teilnehmen") {
							dieSteuerung.id++;
						}
					}
					dieSteuerung.hinzufuegenDealer();

					tfSpieler1.setEditable(false);
					tfSpieler2.setEditable(false);
					tfSpieler3.setEditable(false);
					tfSpieler4.setEditable(false);

					spielStart = false;
					btSpielStarten.setEnabled(false);
					btNaechsterSpieler.setEnabled(true);
					state = "setzen";
					anzeigenGeld(dieSteuerung.getMomentanesSpielerGeld());

					setzenButtonEnable(btSetzen, true);
				}
				tfAktiverSpieler.setText("Mom. Spieler: " + tfSpieler.get(dieSteuerung.momentanerSpieler).getText());
				btNeueSpieler.setEnabled(false);
			}
		});

		btNeueSpieler = new JButton("Neue Spieler");
		btNeueSpieler.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				neueNutzerGUI(true);
			}
		});
		pTools.add(btNeueSpieler);
		pTools.add(btSpielStarten);
		pTools.add(btNaechsterSpieler);

		JButton btNeuesSpiel = new JButton("Neues Spiel");
		btNeuesSpiel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btSpielStarten.setEnabled(true);
				btNeueSpieler.setEnabled(true);
				datenReset();
				dieSteuerung.momentanerSpieler = 0;
				dieSteuerung.derDealer.datenReset();
				anzeigenGeld(dieSteuerung.momentanerSpieler);
				for (int i = 0; i < dieSteuerung.dieSpieler.size(); i++) {
					dieSteuerung.dieSpieler.get(i).datenReset();
				}

			}
		});
		pTools.add(btNeuesSpiel);

		JButton btSpielBeenden = new JButton("Spiel beenden");
		btSpielBeenden.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dieSteuerung.spielerGeldGespeichert();
				frame.dispose();
			}
		});
		pTools.add(btSpielBeenden);

		JPanel pVerlauf = new JPanel();
		frame.getContentPane().add(pVerlauf, BorderLayout.EAST);
		GridBagLayout gbl_pVerlauf = new GridBagLayout();
		gbl_pVerlauf.columnWidths = new int[] { 86, 0 };
		gbl_pVerlauf.rowHeights = new int[] { 20, 0, 0 };
		gbl_pVerlauf.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_pVerlauf.rowWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		pVerlauf.setLayout(gbl_pVerlauf);

		tfVerlauf = new JTextField();
		tfVerlauf.setHorizontalAlignment(SwingConstants.CENTER);
		tfVerlauf.setEditable(false);
		tfVerlauf.setText("Verlauf:");
		GridBagConstraints gbc_tfVerlauf = new GridBagConstraints();
		gbc_tfVerlauf.insets = new Insets(0, 0, 5, 0);
		gbc_tfVerlauf.anchor = GridBagConstraints.NORTHWEST;
		gbc_tfVerlauf.gridx = 0;
		gbc_tfVerlauf.gridy = 0;
		pVerlauf.add(tfVerlauf, gbc_tfVerlauf);
		tfVerlauf.setColumns(10);

	}

	public void deckUpdate() {
		if (tfDeck != null)
			tfDeck.setText("Deck: " + dieSteuerung.dasDeck.size() + " ");
	}

	public void datenReset() {
		state = "default";

		for (JTextField jTextField : tfSpielerEinsatz) {
			jTextField.setText("");
		}
		for (JTextField jTextField : tfSpielerKarten) {
			jTextField.setText("");
		}
		for (JTextField jTextField : tfStatusSpieler) {
			jTextField.setText("");
		}
		if (tfKartenWertD != null)
			tfKartenWertD.setText("");

		if (tfErgebnisSpiel != null)
			tfErgebnisSpiel.setText("");

		if (tfDeck != null)
			tfDeck.setText("Deck: " + dieSteuerung.dasDeck.size() + " ");
	}

	public void anzeigenGeld(int geld) {
		tfGeld.setText("Geld: " + geld);
	}

	public void setzenButtonEnable(ArrayList<JButton> btSetzen, boolean aktiv) {
		for (JButton jButton : btSetzen) {
			jButton.setEnabled(aktiv);
		}
	}

	public void setActionButtonEnable(boolean aktiv) {
		for (JButton jButton : btAction) {
			jButton.setEnabled(aktiv);
		}
	}

	public void neueNutzerGUI(boolean aktiv) {
		dieSteuerung.setGeldSTARTGELD();
		
		tfSpieler1.setEditable(aktiv);
		tfSpieler2.setEditable(aktiv);
		tfSpieler3.setEditable(aktiv);
		tfSpieler4.setEditable(aktiv);

		btNeueSpieler.setEnabled(!aktiv);
	}

	public void anzeigenMomSpieler() {
		tfAktiverSpieler.setText("Mom. Spieler: " + tfSpieler.get(dieSteuerung.momentanerSpieler).getText());
		
	}
	
}