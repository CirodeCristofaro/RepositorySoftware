package controller;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import com.toedter.calendar.JDateChooser;

public class ChiudiClasseGui extends JFrame {

	private JPanel contentPane;
	Controller ctrl;
	private JLabel DataApertura_Label;
	private JComboBox<String> Utente_comboBox;
	private JComboBox<String> Progetto_comboBox;
	private JComboBox<String> Classe_comboBox;
	private JDateChooser Calander_dateChooser;

	/**
	 * Create the frame.
	 */
	public ChiudiClasseGui(Controller ctrl) {
		setResizable(false);
		setTitle("Chiusura Classe");
		this.ctrl = ctrl;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 370, 288);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		DataApertura_Label = new JLabel("");
		DataApertura_Label.setBounds(104, 131, 135, 13);
		contentPane.add(DataApertura_Label);

		JLabel lblNewLabel = new JLabel("Utente");
		lblNewLabel.setBounds(10, 32, 84, 13);
		contentPane.add(lblNewLabel);

		Classe_comboBox = new JComboBox<String>();
		Classe_comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AggiornaDateApertura((String) Classe_comboBox.getSelectedItem());
			}
		});
		Classe_comboBox.setBounds(104, 90, 135, 21);
		contentPane.add(Classe_comboBox);

		Progetto_comboBox = new JComboBox<String>();
		Progetto_comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AggiornaComboBoxClasse((String) Utente_comboBox.getSelectedItem(),
						(String) Progetto_comboBox.getSelectedItem());
				AggiornaDateApertura((String) Classe_comboBox.getSelectedItem());
			}
		});
		Progetto_comboBox.setBounds(104, 59, 135, 21);
		contentPane.add(Progetto_comboBox);

		Utente_comboBox = new JComboBox<String>();
		Utente_comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AggiornaComboBoxProgetto((String) Utente_comboBox.getSelectedItem());
				AggiornaComboBoxClasse((String) Utente_comboBox.getSelectedItem(),
						(String) Progetto_comboBox.getSelectedItem());
				AggiornaDateApertura((String) Classe_comboBox.getSelectedItem());
			}
		});
		Utente_comboBox.setBounds(104, 28, 135, 21);
		contentPane.add(Utente_comboBox);
		AggiornaComboBoxUtente();
		AggiornaComboBoxProgetto((String) Utente_comboBox.getSelectedItem());
		AggiornaComboBoxClasse((String) Utente_comboBox.getSelectedItem(),
				(String) Progetto_comboBox.getSelectedItem());
		AggiornaDateApertura((String) Classe_comboBox.getSelectedItem());
		JLabel lblNewLabel_1 = new JLabel("Progetto");
		lblNewLabel_1.setBounds(10, 67, 84, 13);
		contentPane.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Classe");
		lblNewLabel_2.setBounds(10, 98, 84, 13);
		contentPane.add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("Data Apertura");
		lblNewLabel_3.setBounds(10, 131, 84, 13);
		contentPane.add(lblNewLabel_3);

		JLabel lblNewLabel_4 = new JLabel("Data Chiusura");
		lblNewLabel_4.setBounds(10, 155, 84, 13);
		contentPane.add(lblNewLabel_4);

		JButton btnNewButton = new JButton("Conferma");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					if (Progetto_comboBox.getSelectedItem() != null) {
						Date data = Calander_dateChooser.getDate();
						Date sql = new java.sql.Date(data.getTime());
						Date support;
						support = new SimpleDateFormat("yyyy-MM-dd").parse(DataApertura_Label.getText());
						if (data.after(support)) {
							ctrl.repositorydao.SettaDataChiusura((java.sql.Date) sql,
									(String) Classe_comboBox.getSelectedItem());

							JOptionPane optionPane = new JOptionPane("Classe chiusa con successo ",
									JOptionPane.INFORMATION_MESSAGE);
							JDialog dialog = optionPane.createDialog("Conferma");
							dialog.setAlwaysOnTop(true);
							dialog.setVisible(true);
							AggiornaComboBoxClasse((String) Utente_comboBox.getSelectedItem(),
									(String) Progetto_comboBox.getSelectedItem());
							Calander_dateChooser.setDate(null);
						} else {
							JOptionPane optionPane = new JOptionPane("Data Chiusura sbagliata ",
									JOptionPane.ERROR_MESSAGE);
							JDialog dialog = optionPane.createDialog("Errore");
							dialog.setAlwaysOnTop(true);
							dialog.setVisible(true);

						}
					} else {
						JOptionPane optionPane = new JOptionPane("l'utente non partecipa a nessun progetto ",
								JOptionPane.ERROR_MESSAGE);
						JDialog dialog = optionPane.createDialog("Errore");
						dialog.setAlwaysOnTop(true);
						dialog.setVisible(true);
					}
				} catch (ParseException e1) {

					e1.printStackTrace();
				}

			}
		});
		btnNewButton.setBounds(234, 200, 85, 21);
		contentPane.add(btnNewButton);

		Calander_dateChooser = new JDateChooser();
		Calander_dateChooser.setBounds(104, 154, 135, 19);

		contentPane.add(Calander_dateChooser);
	}

	private void AggiornaDateApertura(String NomeClasse) {
		String data = ctrl.repositorydao.DataAperturaClasse(NomeClasse);
		DataApertura_Label.setText(data);
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

	public void AggiornaComboBoxClasse(String Utente, String Progetto) {
		Classe_comboBox.removeAllItems();
		ResultSet rs = ctrl.repositorydao.StampaClasse(Utente, Progetto);
		try {
			while (rs.next()) {
				Classe_comboBox.addItem(rs.getString(1));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
