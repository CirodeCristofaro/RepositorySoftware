package controller;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class CreaUtenteGui extends JFrame {

	private JPanel contentPane;
	Controller ctrl;
	private JTextField UtenteString;
	private JComboBox<String> Progetto_comboBox;
	private JTextField Cognome_textField;

	/**
	 * Create the frame.
	 */
	public CreaUtenteGui(Controller ctrl) {
		setResizable(false);
		setTitle("Crea Utente");
		this.ctrl = ctrl;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 354, 250);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Nome Utente");
		lblNewLabel.setBounds(10, 36, 107, 13);
		contentPane.add(lblNewLabel);

		UtenteString = new JTextField();
		UtenteString.setBounds(141, 33, 118, 19);
		contentPane.add(UtenteString);
		UtenteString.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Partecipa ad un progetto?");
		lblNewLabel_1.setBounds(10, 98, 155, 13);
		contentPane.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Seleziona Progetto");
		lblNewLabel_2.setBounds(10, 135, 121, 13);
		lblNewLabel_2.setVisible(false);
		contentPane.add(lblNewLabel_2);

		JCheckBox chckbxNewCheckBox = new JCheckBox("SI");
		chckbxNewCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chckbxNewCheckBox.isSelected()) {
					Progetto_comboBox.setVisible(true);
					AggiornaComboBoxProgetto();
					lblNewLabel_2.setVisible(true);
				} else {

					Progetto_comboBox.setVisible(false);
					lblNewLabel_2.setVisible(false);

				}
			}
		});
		chckbxNewCheckBox.setBounds(177, 97, 93, 21);
		contentPane.add(chckbxNewCheckBox);

		Progetto_comboBox = new JComboBox<String>();
		Progetto_comboBox.setBounds(141, 131, 118, 21);
		contentPane.add(Progetto_comboBox);
		Progetto_comboBox.setVisible(false);

		JButton btnNewButton = new JButton("Conferma");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!UtenteString.getText().isEmpty()) {
					ctrl.repositorydao.CreaUtente(UtenteString.getText(), Cognome_textField.getText());
					if (chckbxNewCheckBox.isSelected()) {
						if (Progetto_comboBox.getSelectedItem() != null) {
							JOptionPane optionPane = new JOptionPane(
									"l'utente " + UtenteString.getText() + " partecipa al progetto"
											+ (String) Progetto_comboBox.getSelectedItem(),
									JOptionPane.INFORMATION_MESSAGE);
							JDialog dialog = optionPane.createDialog("Successo");
							dialog.setAlwaysOnTop(true);
							dialog.setVisible(true);
							ctrl.repositorydao.Partecipa(UtenteString.getText()+" "+Cognome_textField.getText(),
									(String) Progetto_comboBox.getSelectedItem());
							UtenteString.setText("");
							Cognome_textField.setText("");
						} else {
							JOptionPane optionPane = new JOptionPane("Non esiste nessun progetto",
									JOptionPane.ERROR_MESSAGE);
							JDialog dialog = optionPane.createDialog("Errore");
							dialog.setAlwaysOnTop(true);
							dialog.setVisible(true);
						}
					}
					JOptionPane optionPane = new JOptionPane("l'utente " + UtenteString.getText() + " creato ",
							JOptionPane.INFORMATION_MESSAGE);
					JDialog dialog = optionPane.createDialog("Successo");
					dialog.setAlwaysOnTop(true);
					dialog.setVisible(true);
					UtenteString.setText("");
					Cognome_textField.setText("");
				} else {
					JOptionPane optionPane = new JOptionPane("Inserire nome Utente", JOptionPane.ERROR_MESSAGE);
					JDialog dialog = optionPane.createDialog("Errore");
					dialog.setAlwaysOnTop(true);
					dialog.setVisible(true);
				}
			}
		});
		btnNewButton.setBounds(229, 158, 101, 21);
		contentPane.add(btnNewButton);

		JLabel lblNewLabel_3 = new JLabel("Cognome");
		lblNewLabel_3.setBounds(10, 62, 107, 13);
		contentPane.add(lblNewLabel_3);

		Cognome_textField = new JTextField();
		Cognome_textField.setBounds(141, 59, 118, 19);
		contentPane.add(Cognome_textField);
		Cognome_textField.setColumns(10);
	}

	public void AggiornaComboBoxProgetto() {
		Progetto_comboBox.removeAllItems();
		ResultSet rs = ctrl.repositorydao.stampaProgetti();
		try {

			while (rs.next()) {
				Progetto_comboBox.addItem(rs.getString(1));
			}
		} catch (SQLException sq) {
			sq.printStackTrace();
		}
	}
}
