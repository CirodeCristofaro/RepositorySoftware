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

public class AggiungiMetodoGui extends JFrame {

	private JPanel contentPane;
	Controller ctrl;
	private JTextField metodo_textField;
	private JComboBox<String> Utente_comboBox;
	private JComboBox<String> Progetto_comboBox;
	private JComboBox<String> Package_comboBox;
	private JComboBox<String> Classe_comboBox;

	public AggiungiMetodoGui(Controller ctrl) {
//		addWindowListener(new WindowAdapter() {
//			@Override
//			public void windowOpened(WindowEvent e) {
//				AggiornaComboBoxUtente();
//			}
//		});
		setTitle("Aggiungi Metodo");
		this.ctrl = ctrl;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 391, 306);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		Classe_comboBox = new JComboBox<String>();
		Classe_comboBox.setBounds(82, 126, 139, 21);
		contentPane.add(Classe_comboBox);

		Package_comboBox = new JComboBox<String>();
		Package_comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AggiornaComboBoxClasse((String) Package_comboBox.getSelectedItem());
			}
		});
		Package_comboBox.setBounds(82, 88, 139, 21);
		contentPane.add(Package_comboBox);

		JLabel lblNewLabel = new JLabel("Utente");
		lblNewLabel.setBounds(10, 24, 62, 13);
		contentPane.add(lblNewLabel);

		Progetto_comboBox = new JComboBox<String>();
		Progetto_comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AggiornaComboBoxPackage((String) Utente_comboBox.getSelectedItem(),
						(String) Progetto_comboBox.getSelectedItem());
				AggiornaComboBoxClasse((String) Package_comboBox.getSelectedItem());
			}
		});
		Progetto_comboBox.setBounds(82, 51, 139, 21);
		contentPane.add(Progetto_comboBox);
		Utente_comboBox = new JComboBox<String>();
		AggiornaComboBoxUtente();
		AggiornaComboBoxProgetto((String) Utente_comboBox.getSelectedItem());
		AggiornaComboBoxPackage((String) Utente_comboBox.getSelectedItem(),
				(String) Progetto_comboBox.getSelectedItem());
		AggiornaComboBoxClasse((String) Package_comboBox.getSelectedItem());
		Utente_comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AggiornaComboBoxProgetto((String) Utente_comboBox.getSelectedItem());
				AggiornaComboBoxPackage((String) Utente_comboBox.getSelectedItem(),
						(String) Progetto_comboBox.getSelectedItem());
				AggiornaComboBoxClasse((String) Package_comboBox.getSelectedItem());
			}
		});
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

		JLabel lblNewLabel_4 = new JLabel("Metodo");
		lblNewLabel_4.setBounds(10, 165, 45, 13);
		contentPane.add(lblNewLabel_4);

		metodo_textField = new JTextField();
		metodo_textField.setBounds(82, 162, 139, 19);
		contentPane.add(metodo_textField);
		metodo_textField.setColumns(10);

		JButton btnNewButton = new JButton("Aggiungi");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (metodo_textField.getText().length() > 0 && Progetto_comboBox.getSelectedItem() != null) {
					ctrl.repositorydao.SottoMetodo((String) Classe_comboBox.getSelectedItem(),
							metodo_textField.getText());
					JOptionPane optionPane = new JOptionPane("Metodo creato", JOptionPane.INFORMATION_MESSAGE);
					JDialog dialog = optionPane.createDialog("Successo");
					dialog.setAlwaysOnTop(true);
					dialog.setVisible(true);
					metodo_textField.setText("");
				} else {
					JOptionPane optionPane = new JOptionPane(
							"Inserire tutti i campi e/o l utente non partecipa ad nessun progetto",
							JOptionPane.ERROR_MESSAGE);
					JDialog dialog = optionPane.createDialog("Errore");
					dialog.setAlwaysOnTop(true);
					dialog.setVisible(true);

				}
			}
		});
		btnNewButton.setBounds(220, 208, 85, 21);
		contentPane.add(btnNewButton);

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

	public void AggiornaComboBoxClasse(String Package) {
		Classe_comboBox.removeAllItems();
		ResultSet rs = ctrl.repositorydao.OnlyNameClasse(Package);
		try {
			while (rs.next()) {
				Classe_comboBox.addItem(rs.getString(1));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
