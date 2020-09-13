package controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class CreaProgettoGui extends JFrame {

	JPanel contentPane;
	JTextField NomeProgetto_textField;
	JTextField NomePackage_textField;
	JTextField NomeClasse_textField;
	JTextField Metodo_textField;
	Controller ctrl;
	JLabel Cartelladirectory_Label;
	JComboBox<String> Utente_comboBox;
	JComboBox<String> Collaboratore_comboBox;

	public CreaProgettoGui(Controller ctrl) {
//		addWindowListener(new WindowAdapter() {
//			@Override
//			public void windowOpened(WindowEvent e) {
//				AggiornaComboBoxUtente();
//			}
//		});
		this.ctrl = ctrl;
		setTitle("Crea Progetto");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 589, 419);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Seleziona Utente");
		lblNewLabel.setBounds(10, 51, 101, 13);
		contentPane.add(lblNewLabel);

		Collaboratore_comboBox = new JComboBox<String>();
		Collaboratore_comboBox.setBounds(428, 47, 137, 21);
		contentPane.add(Collaboratore_comboBox);

		Utente_comboBox = new JComboBox<String>();
		AggiornaComboBoxUtente();
		String coll = (String) Utente_comboBox.getSelectedItem();
		AggiornaComboBoxCollaboratore(coll);
		Utente_comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//AggiornaComboBoxUtente();
				String coll = (String) Utente_comboBox.getSelectedItem();
				AggiornaComboBoxCollaboratore(coll);
			}
		});

		Utente_comboBox.setBounds(121, 47, 143, 21);
		contentPane.add(Utente_comboBox);

		JLabel lblNewLabel_1 = new JLabel("Seleziona Collaboratore");
		lblNewLabel_1.setBounds(274, 51, 137, 13);
		contentPane.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Nome Progetto");
		lblNewLabel_2.setBounds(10, 91, 101, 13);
		contentPane.add(lblNewLabel_2);

		NomeProgetto_textField = new JTextField();
		NomeProgetto_textField.setBounds(121, 88, 143, 19);
		contentPane.add(NomeProgetto_textField);
		NomeProgetto_textField.setColumns(10);

		JLabel lblNewLabel_3 = new JLabel("Dir:");
		lblNewLabel_3.setBounds(10, 136, 45, 13);
		contentPane.add(lblNewLabel_3);

		Cartelladirectory_Label = new JLabel("");
		Cartelladirectory_Label.setBounds(32, 136, 219, 13);
		contentPane.add(Cartelladirectory_Label);

		JButton btnNewButton = new JButton("Selezione Directory");
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
		btnNewButton.setBounds(274, 132, 180, 21);
		contentPane.add(btnNewButton);

		JLabel lblNewLabel_4 = new JLabel("Nome Package");
		lblNewLabel_4.setBounds(10, 184, 101, 13);
		contentPane.add(lblNewLabel_4);

		NomePackage_textField = new JTextField();
		NomePackage_textField.setBounds(121, 181, 143, 19);
		contentPane.add(NomePackage_textField);
		NomePackage_textField.setColumns(10);

		JLabel lblNewLabel_5 = new JLabel("Nome Classe");
		lblNewLabel_5.setBounds(10, 228, 101, 13);
		contentPane.add(lblNewLabel_5);

		NomeClasse_textField = new JTextField();
		NomeClasse_textField.setBounds(121, 225, 143, 19);
		contentPane.add(NomeClasse_textField);
		NomeClasse_textField.setColumns(10);

		JLabel lblNewLabel_6 = new JLabel("Metodo");
		lblNewLabel_6.setBounds(10, 267, 101, 13);
		contentPane.add(lblNewLabel_6);

		Metodo_textField = new JTextField();
		Metodo_textField.setBounds(121, 264, 290, 19);
		contentPane.add(Metodo_textField);
		Metodo_textField.setColumns(10);

		JButton btnNewButton_1 = new JButton("Crea");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ControlloCampi();
			}
		});
		btnNewButton_1.setBounds(404, 311, 85, 21);
		contentPane.add(btnNewButton_1);
		
		JLabel lblNewLabel_7 = new JLabel("Alla creazione di un progetto verr\u00E0 inserita la data attuale");
		lblNewLabel_7.setForeground(Color.RED);
		lblNewLabel_7.setBounds(10, 359, 555, 13);
		contentPane.add(lblNewLabel_7);
		
		JLabel lblNewLabel_8 = new JLabel(".java");
		lblNewLabel_8.setBounds(266, 228, 45, 13);
		contentPane.add(lblNewLabel_8);

	}

	public void AggiornaComboBoxUtente() {
		Utente_comboBox.removeAllItems();

		ResultSet rs = ctrl.repositorydao.StampaUtente();
		// ctrl.repositorydao.StampaUtente();
		try {

			while (rs.next()) {
				Utente_comboBox.addItem(rs.getString(1));
			}
		} catch (SQLException sq) {
			sq.printStackTrace();
		}
	}

	public void AggiornaComboBoxCollaboratore(String utente) {
		Collaboratore_comboBox.removeAllItems();
		ResultSet rs = ctrl.repositorydao.StampaCollaboratore(utente);
		try {

			while (rs.next()) {
				Collaboratore_comboBox.addItem(rs.getString(1));
			}
		} catch (SQLException sq) {
			sq.printStackTrace();
		}
	}

	private void ControlloCampi() {
		if ((NomeClasse_textField.getText().length()) > 0 && (NomeProgetto_textField.getText().length()) > 0
				&& (NomePackage_textField.getText().length()) > 0 && (Cartelladirectory_Label.getText().length()>0)) {
			int utente1 = ctrl.repositorydao.IndiceNome((String) Utente_comboBox.getSelectedItem());
			int utente2 = ctrl.repositorydao.IndiceNome((String) Collaboratore_comboBox.getSelectedItem());

			if (ctrl.repositorydao.CreaProgetto(utente1, Cartelladirectory_Label.getText(),
					NomeProgetto_textField.getText())) {
				ctrl.repositorydao.Collaboratore(utente2, NomeProgetto_textField.getText());

				ctrl.repositorydao.CreaPackage(NomeProgetto_textField.getText(), NomePackage_textField.getText(),
						Cartelladirectory_Label.getText());

				ctrl.repositorydao.CreaClasse(Cartelladirectory_Label.getText(), NomeClasse_textField.getText(),
						Metodo_textField.getText(), NomePackage_textField.getText());
				ctrl.repositorydao.UtenteAccesso(utente1, NomeClasse_textField.getText());
				ctrl.repositorydao.UtenteAccesso(utente2, NomeClasse_textField.getText());
				final JPanel panel = new JPanel();
				JOptionPane.showMessageDialog(panel, "Progetto Creato", "info", JOptionPane.INFORMATION_MESSAGE);
				NomeClasse_textField.setText("");
				NomePackage_textField.setText("");
				NomeProgetto_textField.setText("");
				Metodo_textField.setText("");
				Cartelladirectory_Label.setText("");

			} else {
				final JPanel panel = new JPanel();
				JOptionPane.showMessageDialog(panel, "Progetto esiste", "Errore", JOptionPane.ERROR_MESSAGE);
			}
		} else {
			final JPanel panel = new JPanel();
			JOptionPane.showMessageDialog(panel, "Alcuni campi sono vuoti", "Errore", JOptionPane.ERROR_MESSAGE);
		}
	}
}
