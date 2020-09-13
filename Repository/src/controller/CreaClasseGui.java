package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Color;

public class CreaClasseGui extends JFrame {

	private JPanel contentPane;
	Controller ctrl;
	private JTextField Classe_textField;
	private JTextField Metodo_TextField;
	JComboBox<String> Utente_comboBox;
	JComboBox<String> Progetto_comboBox;
	JComboBox<String> Package_comboBox;
	private JLabel lblNewLabel_5;
	private JLabel lblNewLabel_6;

	public CreaClasseGui(Controller ctrl) {
		// addWindowListener(new WindowAdapter() {

//			@Override
//			public void windowOpened(WindowEvent e) {
//				AggiornaComboBoxUtente();
//			}
//		});
		setTitle("Crea Classe");
		this.ctrl = ctrl;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 391, 306);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Utente");
		lblNewLabel.setBounds(10, 24, 62, 13);
		contentPane.add(lblNewLabel);

		Progetto_comboBox = new JComboBox<String>();
		Progetto_comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AggiornaComboBoxPackage((String)Utente_comboBox.getSelectedItem(),(String) Progetto_comboBox.getSelectedItem());
			}
		});
		Progetto_comboBox.setBounds(82, 51, 139, 21);
		contentPane.add(Progetto_comboBox);

		Package_comboBox = new JComboBox<String>();
		Package_comboBox.setBounds(82, 88, 139, 21);
		contentPane.add(Package_comboBox);
		Utente_comboBox = new JComboBox<String>();

		Utente_comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AggiornaComboBoxProgetto((String) Utente_comboBox.getSelectedItem());
				AggiornaComboBoxPackage((String) Utente_comboBox.getSelectedItem(),
						(String) Progetto_comboBox.getSelectedItem());
			}
		});
		AggiornaComboBoxUtente();
		Utente_comboBox.setBounds(82, 20, 139, 21);
		contentPane.add(Utente_comboBox);

		JLabel lblNewLabel_1 = new JLabel("Progetto");
		lblNewLabel_1.setBounds(10, 59, 68, 13);
		contentPane.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Package");
		lblNewLabel_2.setBounds(10, 92, 62, 13);
		contentPane.add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("Classe");
		lblNewLabel_3.setBounds(10, 130, 62, 13);
		contentPane.add(lblNewLabel_3);

		Classe_textField = new JTextField();
		Classe_textField.setBounds(82, 127, 139, 19);
		contentPane.add(Classe_textField);
		Classe_textField.setColumns(10);

		JLabel lblNewLabel_4 = new JLabel("Metodo");
		lblNewLabel_4.setBounds(10, 165, 45, 13);
		contentPane.add(lblNewLabel_4);

		Metodo_TextField = new JTextField();
		Metodo_TextField.setBounds(82, 162, 139, 19);
		contentPane.add(Metodo_TextField);
		Metodo_TextField.setColumns(10);

		JButton btnNewButton = new JButton("Crea");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if ((!Classe_textField.getText().isEmpty()) && (!Metodo_TextField.getText().isEmpty())) {
					if (Progetto_comboBox.getSelectedItem() == null) {
						JOptionPane optionPane = new JOptionPane("l'utente "
								+ (String) Utente_comboBox.getSelectedItem() + " non partecipa a nessun progetto",
								JOptionPane.ERROR_MESSAGE);
						JDialog dialog = optionPane.createDialog("Errore");
						dialog.setAlwaysOnTop(true);
						dialog.setVisible(true);
					} else {
						if (ctrl.repositorydao.CreaClasse((String) Progetto_comboBox.getSelectedItem(),
								Classe_textField.getText(), Metodo_TextField.getText(),
								(String) Package_comboBox.getSelectedItem())) {
							JOptionPane optionPane = new JOptionPane("Classe creata con successo",
									JOptionPane.INFORMATION_MESSAGE);
							JDialog dialog = optionPane.createDialog("Successo");
							dialog.setAlwaysOnTop(true);
							dialog.setVisible(true);
							Metodo_TextField.setText("");
							Classe_textField.setText("");
						} else {
							JOptionPane optionPane = new JOptionPane("Classe già esistente", JOptionPane.ERROR_MESSAGE);
							JDialog dialog = optionPane.createDialog("Errore");
							dialog.setAlwaysOnTop(true);
							dialog.setVisible(true);
						}

					}
				} else {
					if (Progetto_comboBox.getSelectedItem() != null) {
						JOptionPane optionPane = new JOptionPane("completare tutti i campi", JOptionPane.ERROR_MESSAGE);
						JDialog dialog = optionPane.createDialog("Errore");
						dialog.setAlwaysOnTop(true);
						dialog.setVisible(true);
					} else {
						if ((Classe_textField.getText().isEmpty()) && (Metodo_TextField.getText().isEmpty())
								&& (Progetto_comboBox.getSelectedItem() == null)) {
							JOptionPane optionPane = new JOptionPane(
									"completare tutti i campi e/o l utente non partecipa ad nessun progeto",
									JOptionPane.ERROR_MESSAGE);
							JDialog dialog = optionPane.createDialog("Errore");
							dialog.setAlwaysOnTop(true);
							dialog.setVisible(true);
						} else {
							JOptionPane optionPane = new JOptionPane(
									"completare tutti i campi e/o l utente non partecipa ad nessun progeto",
									JOptionPane.ERROR_MESSAGE);
							JDialog dialog = optionPane.createDialog("Errore");
							dialog.setAlwaysOnTop(true);
							dialog.setVisible(true);
						}
					}
				}
			}
		});
		btnNewButton.setBounds(220, 208, 85, 21);
		contentPane.add(btnNewButton);

		lblNewLabel_5 = new JLabel("Alla creazione di una classe verr\u00E0 inserita la data attuale");
		lblNewLabel_5.setForeground(Color.RED);
		lblNewLabel_5.setBounds(0, 246, 367, 13);
		contentPane.add(lblNewLabel_5);
		
		lblNewLabel_6 = new JLabel(",java");
		lblNewLabel_6.setBounds(225, 130, 45, 13);
		contentPane.add(lblNewLabel_6);
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

	public void AggiornaComboBoxPackage(String utente, String progetto) {
		Package_comboBox.removeAllItems();
		ResultSet rs = ctrl.repositorydao.SelezionePackage(utente, progetto);
		try {

			while (rs.next()) {
				Package_comboBox.addItem(rs.getString(1));
			}
		} catch (SQLException sq) {
			sq.printStackTrace();
		}
	}

}
