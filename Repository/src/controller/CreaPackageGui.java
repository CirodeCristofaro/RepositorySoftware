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

public class CreaPackageGui extends JFrame {

	private JPanel contentPane;
	Controller ctrl;
	private JTextField Package_textField;
	JComboBox<String> Utente_comboBox;
	JComboBox<String> Progetto_comboBox;
	JComboBox<String> PackagePrincipale_comboBox;

	public CreaPackageGui(Controller ctrl) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				AggiornaComboBoxPackage((String)Utente_comboBox.getSelectedItem(),(String) Progetto_comboBox.getSelectedItem());
			}
		});
	
		setTitle("Crea Package");
		this.ctrl = ctrl;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 394, 279);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Utente");
		lblNewLabel.setBounds(10, 32, 58, 13);
		contentPane.add(lblNewLabel);
		PackagePrincipale_comboBox = new JComboBox<String>();
		PackagePrincipale_comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//AggiornaComboBoxPackage((String)Utente_comboBox.getSelectedItem(),(String) Progetto_comboBox.getSelectedItem());
			}
		});
		PackagePrincipale_comboBox.setBounds(112, 114, 152, 21);
		contentPane.add(PackagePrincipale_comboBox);

		Progetto_comboBox = new JComboBox<String>();
		Progetto_comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AggiornaComboBoxPackage((String)Utente_comboBox.getSelectedItem(),(String) Progetto_comboBox.getSelectedItem());
			
			}
		});
		Progetto_comboBox.setBounds(112, 72, 152, 21);
		contentPane.add(Progetto_comboBox);

		Utente_comboBox = new JComboBox<String>();
		Utente_comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String utente = (String) Utente_comboBox.getSelectedItem();
				AggiornaComboBoxProgetto(utente);
				AggiornaComboBoxPackage((String) Utente_comboBox.getSelectedItem(),
						(String) Progetto_comboBox.getSelectedItem());

			}
		});
		AggiornaComboBoxUtente();
		Utente_comboBox.setBounds(112, 28, 152, 21);
		contentPane.add(Utente_comboBox);

		JLabel lblNewLabel_1 = new JLabel("Progetto");
		lblNewLabel_1.setBounds(10, 76, 58, 13);
		contentPane.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Package:");
		lblNewLabel_2.setBounds(10, 148, 77, 13);
		contentPane.add(lblNewLabel_2);

		Package_textField = new JTextField();
		Package_textField.setBounds(112, 145, 152, 19);
		contentPane.add(Package_textField);
		Package_textField.setColumns(10);

		JButton btnNewButton = new JButton("Crea");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!Package_textField.getText().isEmpty()) {
					if (Progetto_comboBox.getSelectedItem() != null) {

						if (ctrl.repositorydao.InserimentoSottoPackage((String) Utente_comboBox.getSelectedItem(),
								(String) Progetto_comboBox.getSelectedItem(),
								(String) PackagePrincipale_comboBox.getSelectedItem(), Package_textField.getText())) {
							JOptionPane optionPane = new JOptionPane("Package creato con successo",
									JOptionPane.INFORMATION_MESSAGE);
							JDialog dialog = optionPane.createDialog("Successo");
							dialog.setAlwaysOnTop(true);
							dialog.setVisible(true);
							Package_textField.setText("");
						} else {
							JOptionPane optionPane = new JOptionPane("Esiste già un package con quel nome",
									JOptionPane.ERROR_MESSAGE);
							JDialog dialog = optionPane.createDialog("Errore");
							dialog.setAlwaysOnTop(true);
							dialog.setVisible(true);
						}
					}
				} else {
					JOptionPane optionPane = new JOptionPane("Inserire nome Package", JOptionPane.ERROR_MESSAGE);
					JDialog dialog = optionPane.createDialog("Errore");
					dialog.setAlwaysOnTop(true);
					dialog.setVisible(true);
				}
			}
		});
		btnNewButton.setBounds(266, 177, 85, 21);
		contentPane.add(btnNewButton);

		JLabel lblNewLabel_3 = new JLabel("Package Principale");
		lblNewLabel_3.setBounds(0, 118, 140, 13);
		contentPane.add(lblNewLabel_3);

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
		PackagePrincipale_comboBox.removeAllItems();
		ResultSet rs = ctrl.repositorydao.SelezionePackage(utente, progetto);
		try {

			while (rs.next()) {
				PackagePrincipale_comboBox.addItem(rs.getString(1));
			}
		} catch (SQLException sq) {
			sq.printStackTrace();
		}
	}
}
