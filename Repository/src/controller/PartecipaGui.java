package controller;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.ResultSet;
import java.sql.SQLException;

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

public class PartecipaGui extends JFrame {

	private JPanel contentPane;
	private JComboBox<String> Progetto_comboBox;
	private JComboBox<String> utente_comboBox;
	Controller ctrl;
	public PartecipaGui(Controller ctrl) {
		this.ctrl=ctrl;
		setTitle("Partecipa");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 333, 251);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		utente_comboBox = new JComboBox<String>();
		utente_comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AggiornaComboBoxProgetto((String)utente_comboBox.getSelectedItem());
			}
		});
		utente_comboBox.setBounds(65, 28, 142, 21);
		contentPane.add(utente_comboBox);
		
		JLabel lblNewLabel = new JLabel("Utente");
		lblNewLabel.setBounds(10, 32, 45, 13);
		contentPane.add(lblNewLabel);
		Progetto_comboBox = new JComboBox<String>();
		Progetto_comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//AggiornaComboBoxUtente((String)Progetto_comboBox.getSelectedItem());
				 
			}
		});
		Progetto_comboBox.setBounds(65, 73, 142, 21);
		contentPane.add(Progetto_comboBox);
		
		 
		
		
		AggiornaComboBoxUtente((String)Progetto_comboBox.getSelectedItem());
		
		JLabel lblNewLabel_1 = new JLabel("progetto");
		lblNewLabel_1.setBounds(10, 77, 58, 13);
		contentPane.add(lblNewLabel_1);
		
		
		
		JButton btnNewButton = new JButton("Partecipa");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(Progetto_comboBox.getSelectedItem()!=null) {
				ctrl.repositorydao.Partecipa((String)utente_comboBox.getSelectedItem(),
						(String) Progetto_comboBox.getSelectedItem());
				JOptionPane optionPane = new JOptionPane(
						"l'utente " + (String)utente_comboBox.getSelectedItem() + " partecipa al progetto"
								+ (String) Progetto_comboBox.getSelectedItem(),
						JOptionPane.INFORMATION_MESSAGE);
				JDialog dialog = optionPane.createDialog("Successo");
				dialog.setAlwaysOnTop(true);
				dialog.setVisible(true);
				AggiornaComboBoxProgetto((String)utente_comboBox.getSelectedItem());
				}else {
					JOptionPane optionPane = new JOptionPane(
							"non esistono progetti",JOptionPane.ERROR_MESSAGE);
					JDialog dialog = optionPane.createDialog("errore");
					dialog.setAlwaysOnTop(true);
					dialog.setVisible(true);
				}
			}
		});
		btnNewButton.setBounds(189, 153, 85, 21);
		contentPane.add(btnNewButton);
	}
	public void AggiornaComboBoxProgetto(String nome) {
		Progetto_comboBox.removeAllItems();
		ResultSet rs = ctrl.repositorydao.stampaProgettoDiUtentinonPartecipanti(nome);
		try {

			while (rs.next()) {
				Progetto_comboBox.addItem(rs.getString(1));
			}
		} catch (SQLException sq) {
			sq.printStackTrace();
		}
	}
	public void AggiornaComboBoxUtente(String progetto) {
		utente_comboBox.removeAllItems();

		ResultSet rs = ctrl.repositorydao.stampaUtentiNonPartecipanti(progetto);
		// ctrl.repositorydao.StampaUtente();
		try {

			while (rs.next()) {
				utente_comboBox.addItem(rs.getString(1));
			}
		} catch (SQLException sq) {
			sq.printStackTrace();
		}
	}
}
