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
import com.toedter.calendar.JDateChooser;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ChiudiProgettoGui extends JFrame {

	private JPanel contentPane;
	Controller ctrl;
	private	JComboBox<String> Utente_comboBox;
	private JComboBox<String> Progetto_comboBox;
	private JLabel DataApertura_Label;
	private JDateChooser Calander_dateChooser;
	public ChiudiProgettoGui(Controller ctrl) {
		this.ctrl=ctrl;
		setResizable(false);
		setTitle("Chiudi progetto");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 381, 286);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Utente");
		lblNewLabel.setBounds(10, 33, 75, 13);
		contentPane.add(lblNewLabel);

		 Progetto_comboBox = new JComboBox<String>();
		 Progetto_comboBox.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		AggiornaDateApertura((String) Progetto_comboBox.getSelectedItem());
		 	}
		 });
		Progetto_comboBox.setBounds(116, 70, 126, 21);
		contentPane.add(Progetto_comboBox);

		 DataApertura_Label= new JLabel("");
		DataApertura_Label.setBounds(116, 113, 126, 13);
		contentPane.add(DataApertura_Label);
		
		
		Utente_comboBox = new JComboBox<String>();
		Utente_comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AggiornaComboBoxProgetto((String) Utente_comboBox.getSelectedItem());
				AggiornaDateApertura((String) Progetto_comboBox.getSelectedItem());
			}
		});
		Utente_comboBox.setBounds(116, 29, 126, 21);
		contentPane.add(Utente_comboBox);
		AggiornaComboBoxUtente();
		AggiornaComboBoxProgetto((String) Utente_comboBox.getSelectedItem());
		AggiornaDateApertura((String) Progetto_comboBox.getSelectedItem());
		JLabel lblNewLabel_1 = new JLabel("Progetto");
		lblNewLabel_1.setBounds(10, 74, 75, 13);
		contentPane.add(lblNewLabel_1);
		
		
		JLabel lblNewLabel_2 = new JLabel("Data Apertura");
		lblNewLabel_2.setBounds(10, 113, 105, 13);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Data Chiusura");
		lblNewLabel_3.setBounds(10, 154, 105, 13);
		contentPane.add(lblNewLabel_3);
		
		 Calander_dateChooser = new JDateChooser();
		Calander_dateChooser.setBounds(116, 148, 126, 19);
		contentPane.add(Calander_dateChooser);
		
		JButton btnNewButton = new JButton("Conferma");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				try {
					if(Progetto_comboBox.getSelectedItem()!=null) {
					Date data=Calander_dateChooser .getDate();
					Date sql= new java.sql.Date(data.getTime());
					Date support;
					support = new SimpleDateFormat("yyyy-MM-dd").parse(DataApertura_Label.getText());
					if(data.after(support)){
						// da fare ctrl.repositorydao.SettaDataChiusura((java.sql.Date) sql, (String)Classe_comboBox.getSelectedItem());
						ctrl.repositorydao.settaDatachiusuraProgetto((String)Progetto_comboBox.getSelectedItem(),(java.sql.Date) sql);
						System.out.println("si");
						JOptionPane optionPane = new JOptionPane("Progetto chiuso con successo ", JOptionPane.INFORMATION_MESSAGE);    
						JDialog dialog = optionPane.createDialog("Conferma");
						dialog.setAlwaysOnTop(true);
						dialog.setVisible(true);
						AggiornaComboBoxProgetto((String)Utente_comboBox.getSelectedItem());
				// da fare		AggiornaComboBoxClasse((String)Utente_comboBox.getSelectedItem(),(String) Progetto_comboBox.getSelectedItem());
					Calander_dateChooser.setDate(null);
					}else {
						JOptionPane optionPane = new JOptionPane("Data Chiusura sbagliata ", JOptionPane.ERROR_MESSAGE);    
						JDialog dialog = optionPane.createDialog("Errore");
						dialog.setAlwaysOnTop(true);
						dialog.setVisible(true);
						
					}
					}else {
						JOptionPane optionPane = new JOptionPane("l'utente non partecipa a nessun progetto ", JOptionPane.ERROR_MESSAGE);    
						JDialog dialog = optionPane.createDialog("Errore");
						dialog.setAlwaysOnTop(true);
						dialog.setVisible(true);
					}
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(217, 193, 100, 21);
		contentPane.add(btnNewButton);
		
		
	}
	private void AggiornaDateApertura(String nomeProgetto) {
		String data = ctrl.repositorydao.DataAperturaProgetto(nomeProgetto);
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

}
