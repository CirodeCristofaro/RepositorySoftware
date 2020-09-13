package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TestGui extends JFrame {

	private JPanel contentPane;
	Controller ctrl;
	private JTextField DescrizioneTest_textField;
	private JComboBox<String> Utente_comboBox;
	private JComboBox<String> Progetto_comboBox;
	private JComboBox<String> VersioneTest_comboBox;
	private JComboBox<String> RisultatoTest_comboBox;
	private JLabel StatoVersioneAttuale_lblNewLabel;
	private JComboBox<String> StatoNuovoVerse_comboBox;
	private JLabel Cartelladirectory_Label;

	public TestGui(Controller ctrl) {
//		addWindowListener(new WindowAdapter() {
//			@Override
//			public void windowOpened(WindowEvent e) {
//				AggiornaComboBoxUtente();
//			}
//		});
		this.ctrl = ctrl;
		setTitle("Crea Test");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 506, 390);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		StatoVersioneAttuale_lblNewLabel = new JLabel("");
		StatoVersioneAttuale_lblNewLabel.setBounds(97, 110, 106, 13);
		contentPane.add(StatoVersioneAttuale_lblNewLabel);

		JLabel lblNewLabel = new JLabel("Utente:");
		lblNewLabel.setBounds(0, 14, 64, 13);
		contentPane.add(lblNewLabel);

		StatoNuovoVerse_comboBox = new JComboBox<String>();
		StatoNuovoVerse_comboBox
				.setModel(new DefaultComboBoxModel<String>(new String[] { "Eliminato", "Aggiornato", "Immutato" }));
		StatoNuovoVerse_comboBox.setBounds(125, 202, 114, 21);
		contentPane.add(StatoNuovoVerse_comboBox);

		RisultatoTest_comboBox = new JComboBox<String>();
		RisultatoTest_comboBox.setModel(new DefaultComboBoxModel<String>(new String[] { "Superato", "Fallito" }));
		RisultatoTest_comboBox.setBounds(125, 232, 114, 21);
		contentPane.add(RisultatoTest_comboBox);

		VersioneTest_comboBox = new JComboBox<String>();
		VersioneTest_comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// AggiornaComboBoxVersione((String)Utente_comboBox.getSelectedItem(),(String)
				// Progetto_comboBox.getSelectedItem());
				AggiornaComboBoxStatoVersione((String) Progetto_comboBox.getSelectedItem(),
						(String) VersioneTest_comboBox.getSelectedItem());
			}
		});
		VersioneTest_comboBox.setBounds(198, 83, 106, 21);
		contentPane.add(VersioneTest_comboBox);

		Progetto_comboBox = new JComboBox<String>();
		Progetto_comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AggiornaComboBoxVersione((String) Utente_comboBox.getSelectedItem(),
						(String) Progetto_comboBox.getSelectedItem());
				AggiornaComboBoxStatoVersione((String) Progetto_comboBox.getSelectedItem(),
						(String) VersioneTest_comboBox.getSelectedItem());
			}
		});
		Progetto_comboBox.setBounds(74, 52, 114, 21);
		contentPane.add(Progetto_comboBox);

		Utente_comboBox = new JComboBox<String>();
		AggiornaComboBoxUtente();
		AggiornaComboBoxProgetto((String) Utente_comboBox.getSelectedItem());
		AggiornaComboBoxVersione((String) Utente_comboBox.getSelectedItem(),
				(String) Progetto_comboBox.getSelectedItem());
		AggiornaComboBoxStatoVersione((String) Progetto_comboBox.getSelectedItem(),
				(String) VersioneTest_comboBox.getSelectedItem());
		Utente_comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				AggiornaComboBoxProgetto((String) Utente_comboBox.getSelectedItem());

				AggiornaComboBoxVersione((String) Utente_comboBox.getSelectedItem(),
						(String) Progetto_comboBox.getSelectedItem());
				AggiornaComboBoxStatoVersione((String) Progetto_comboBox.getSelectedItem(),
						(String) VersioneTest_comboBox.getSelectedItem());
				if (Progetto_comboBox.getSelectedItem() == null) {
					RisultatoTest_comboBox.setEnabled(false);
					StatoNuovoVerse_comboBox.setEnabled(false);
					StatoVersioneAttuale_lblNewLabel.setText("");
				} else {
					RisultatoTest_comboBox.setEnabled(true);
					StatoNuovoVerse_comboBox.setEnabled(true);

				}

			}
		});
		Utente_comboBox.setBounds(50, 10, 138, 21);
		contentPane.add(Utente_comboBox);
		
		Cartelladirectory_Label = new JLabel("");
		Cartelladirectory_Label.setBounds(229, 14, 219, 13);
		contentPane.add(Cartelladirectory_Label);

		
		JButton btnNewButton = new JButton(" Directory");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.setDialogTitle("Selezione Cartella");
				fc.setCurrentDirectory(new java.io.File("C:\\"));
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					Cartelladirectory_Label.setText(fc.getSelectedFile().getAbsolutePath());
				}
			}
		});
		btnNewButton.setBounds(389, 10, 114, 21);
		contentPane.add(btnNewButton);

		JLabel lblNewLabel_1 = new JLabel("Progetto:");
		lblNewLabel_1.setBounds(0, 56, 64, 17);
		contentPane.add(lblNewLabel_1);

		JLabel lblNewLabel_3 = new JLabel("Seleziona Versione per Test");
		lblNewLabel_3.setBounds(0, 87, 188, 13);
		contentPane.add(lblNewLabel_3);

		JLabel lblNewLabel_4 = new JLabel("Descrizione Test");
		lblNewLabel_4.setBounds(1, 133, 128, 13);
		contentPane.add(lblNewLabel_4);

		DescrizioneTest_textField = new JTextField();
		DescrizioneTest_textField.setBounds(1, 150, 481, 42);
		contentPane.add(DescrizioneTest_textField);
		DescrizioneTest_textField.setColumns(10);

		JLabel lblNewLabel_5 = new JLabel("Risultato test");
		lblNewLabel_5.setBounds(1, 240, 106, 13);
		contentPane.add(lblNewLabel_5);

		JButton ConfermaBottone_btnNewButton = new JButton("Conferma");
		ConfermaBottone_btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Progetto_comboBox.getSelectedItem() == null) {
					JOptionPane optionPane = new JOptionPane("L'utente " + (String) Utente_comboBox.getSelectedItem()
							+ " non partecipa a nessun progetto", JOptionPane.ERROR_MESSAGE);
					JDialog dialog = optionPane.createDialog("Errore");
					dialog.setAlwaysOnTop(true);
					dialog.setVisible(true);
				} else {
					if (DescrizioneTest_textField.getText().length() > 0 &&(Cartelladirectory_Label.getText().length()>0)) {
						ctrl.repositorydao.creaTest(DescrizioneTest_textField.getText(),
								(String) RisultatoTest_comboBox.getSelectedItem(),
								(String) Utente_comboBox.getSelectedItem(),
								(String) Progetto_comboBox.getSelectedItem(),
								(String) VersioneTest_comboBox.getSelectedItem(),
								(String) StatoNuovoVerse_comboBox.getSelectedItem(), 
								Cartelladirectory_Label.getText());
						int g = JOptionPane.showConfirmDialog(null, "Vuoi usare il Test per un altra Release?",
								"Ripetere Test", JOptionPane.YES_NO_OPTION);
						if (g == JOptionPane.NO_OPTION) {
							DescrizioneTest_textField.setText("");
						}
						if ((boolean) StatoNuovoVerse_comboBox.getSelectedItem().equals("Eliminato")
								&& (RisultatoTest_comboBox.getSelectedItem().equals("Fallito"))) {

						}
						AggiornaComboBoxVersione((String) Utente_comboBox.getSelectedItem(),
								(String) Progetto_comboBox.getSelectedItem());
						AggiornaComboBoxStatoVersione((String) Progetto_comboBox.getSelectedItem(),
								(String) VersioneTest_comboBox.getSelectedItem());
					} else {
						JOptionPane optionPane = new JOptionPane("inserire tutti i campi",
								JOptionPane.ERROR_MESSAGE);
						JDialog dialog = optionPane.createDialog("Errore");
						dialog.setAlwaysOnTop(true);
						dialog.setVisible(true);
					}

				}
				AggiornaComboBoxVersione((String) Utente_comboBox.getSelectedItem(),
						(String) Progetto_comboBox.getSelectedItem());
				AggiornaComboBoxStatoVersione((String) Progetto_comboBox.getSelectedItem(),
						(String) VersioneTest_comboBox.getSelectedItem());
			}
		});
		ConfermaBottone_btnNewButton.setBounds(342, 322, 106, 21);
		contentPane.add(ConfermaBottone_btnNewButton);
		JLabel lblNewLabel_7 = new JLabel("Stato nuova versione");
		lblNewLabel_7.setBounds(0, 206, 138, 13);
		contentPane.add(lblNewLabel_7);

		JLabel lblNewLabel_2 = new JLabel("Stato versione:");
		lblNewLabel_2.setBounds(0, 110, 107, 13);
		contentPane.add(lblNewLabel_2);

	}

	public void AggiornaComboBoxUtente() {
		Utente_comboBox.removeAllItems();
		ResultSet rs = ctrl.repositorydao.StampaUtente();
		try {

			while (rs.next()) {
				Utente_comboBox.addItem(rs.getString(1));
			}
		} catch (SQLException sq) {
			sq.printStackTrace();
		}
	}

	public void AggiornaComboBoxProgetto(String utente) {
		Progetto_comboBox.removeAllItems();
		ResultSet rs = ctrl.repositorydao.SelezionaProgetto(utente);
		try {

			while (rs.next()) {
				Progetto_comboBox.addItem(rs.getString(1));
			}
		} catch (SQLException sq) {
			sq.printStackTrace();
		}
	}

	public void AggiornaComboBoxVersione(String utente, String Progetto) {
		VersioneTest_comboBox.removeAllItems();
		ResultSet rs = ctrl.repositorydao.StampaVersioneProgetto(utente, Progetto);
		try {

			while (rs.next()) {
				VersioneTest_comboBox.addItem(rs.getString(1));
			}
		} catch (SQLException sq) {
			sq.printStackTrace();
		}
	}

	public void AggiornaComboBoxStatoVersione(String Progetto, String versione) {
		ResultSet rs = ctrl.repositorydao.StampaStatoVersioneProgetto(Progetto, versione);
		try {

			while (rs.next()) {

				StatoVersioneAttuale_lblNewLabel.setText(rs.getString(1));

				// StatutsVersione_comboBox.addItem(rs.getString(1));
			}

		} catch (SQLException sq) {
			sq.printStackTrace();
		}

	}
}
