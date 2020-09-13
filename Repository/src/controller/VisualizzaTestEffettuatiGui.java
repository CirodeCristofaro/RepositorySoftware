package controller;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JTextField;
import com.toedter.calendar.JDateChooser;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Color;

public class VisualizzaTestEffettuatiGui extends JFrame {

	private JPanel contentPane;
	Controller ctrl;
	private JTable table;
	private DefaultTableModel mydata;
	private JTextField Utente_textField;
	private JTextField Progetto_textField;
	private JTextField Risultato_textField;
	private JTextField Cognome_textField;

	public VisualizzaTestEffettuatiGui(Controller ctrl) {
		setTitle("visulizza test effettuati");
//		addWindowListener(new WindowAdapter() {
//			@Override
//			public void windowActivated(WindowEvent e) {
//				AggiornaTable();
//			}
//		});
		this.ctrl = ctrl;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 757, 387);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JScrollPane scrollPane = new JScrollPane();

		table = new JTable();
		table.setEnabled(false);
		mydata = new DefaultTableModel();
		mydata.addColumn("UTENTE");
		mydata.addColumn("PATHNAME TEST");
		mydata.addColumn("Data Test");
		mydata.addColumn("Descrizione Test ");
		mydata.addColumn("Progetto");
		mydata.addColumn("Risultato Test");
		table.setModel(mydata);
		scrollPane.setViewportView(table);

		JLabel lblNewLabel = new JLabel("Utente");

		Utente_textField = new JTextField();
		Utente_textField.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("Progetto");

		Progetto_textField = new JTextField();
		Progetto_textField.setColumns(10);

		JLabel lblNewLabel_3 = new JLabel("Risultato");

		Risultato_textField = new JTextField();
		Risultato_textField.setColumns(10);
		AggiornaTable();
		JButton Cerca_btnNewButton = new JButton("Cerca");
		Cerca_btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Utente_textField.getText().length() > 0 && Progetto_textField.getText().length() > 0
						&& Risultato_textField.getText().length() > 0 && Cognome_textField.getText().length()>0) {
					// metodo per tutti i campi
					AggiornaTableTutto(Utente_textField.getText(), Progetto_textField.getText(),
							Risultato_textField.getText().toUpperCase(),Cognome_textField.getText());
				} else {
					if (Utente_textField.getText().isEmpty() && Progetto_textField.getText().length() > 0
							&& Risultato_textField.getText().isEmpty() && Cognome_textField.getText().isEmpty()) {
						// metodo via progetto;
						AggiornaTableProgetto(Progetto_textField.getText());
					} else {
						if (Utente_textField.getText().length() > 0 && Progetto_textField.getText().isEmpty()
								&& Risultato_textField.getText().isEmpty() && Cognome_textField.getText().isEmpty() ) {
							// metodo via nome
							AggiornaTableNome(Utente_textField.getText());
						} else {
							if (Utente_textField.getText().isEmpty() && Progetto_textField.getText().isEmpty()
									&& Risultato_textField.getText().length() > 0 && Cognome_textField.getText().isEmpty()) {
								// metodo via risultato
								AggiornaTableRisultato(Risultato_textField.getText().toUpperCase());
							} else {
								if (Utente_textField.getText().length() > 0 && Progetto_textField.getText().length() > 0
										&& Risultato_textField.getText().isEmpty() && Cognome_textField.getText().isEmpty()) {
									// metodo via utente e progetto
									AggiornaTableUtenteProgetto(Utente_textField.getText(),
											Progetto_textField.getText());
								} else {
									if (Utente_textField.getText().length() > 0
											&& Progetto_textField.getText().isEmpty()
											&& Risultato_textField.getText().length() > 0 && Cognome_textField.getText().isEmpty()) {
										// metodo via utente e risultato
										AggiornaTableUtenteRisultato(Utente_textField.getText(),
												Risultato_textField.getText().toUpperCase());
									} else {
										if (Utente_textField.getText().isEmpty()
												&& Progetto_textField.getText().length() > 0
												&& Risultato_textField.getText().length() > 0 && Cognome_textField.getText().isEmpty()) {
											// metodo via progetto e risultato
											AggiornaTableProgettoRisultato(Progetto_textField.getText(),
													Risultato_textField.getText().toUpperCase());
										} else {
											if(Utente_textField.getText().isEmpty()
												&& Progetto_textField.getText().isEmpty()
												&& Risultato_textField.getText().isEmpty() && Cognome_textField.getText().length()>0) {
												//solo cognome
												AggiornaTableProgettoCognome(Cognome_textField.getText());
											}else {
												if(Utente_textField.getText().length()>0
														&& Progetto_textField.getText().isEmpty()
														&& Risultato_textField.getText().isEmpty() && Cognome_textField.getText().length()>0) {
													//utente cognome
													AggiornaTableProgettoCognomeAndNome(Cognome_textField.getText(),Utente_textField.getText());
												}else {
													if(Utente_textField.getText().isEmpty()
												&& Progetto_textField.getText().length()>0
												&& Risultato_textField.getText().isEmpty() && Cognome_textField.getText().length()>0) {
														//progetto cognome
														AggiornaTableProgettoCognomeAndProgetto(Cognome_textField.getText(),Progetto_textField.getText());
													}else{
														if(Utente_textField.getText().isEmpty()
												&& Progetto_textField.getText().isEmpty()
												&& Risultato_textField.getText().length()>0 && Cognome_textField.getText().length()>0) {
															//cognome risultato
															AggiornaTableProgettoCognomeAndRisultato(Cognome_textField.getText(),Risultato_textField.getText().toUpperCase());
														}else {
															if(Utente_textField.getText().length()>0
												&& Progetto_textField.getText().length()>0
												&& Risultato_textField.getText().isEmpty() && Cognome_textField.getText().length()>0) {
																//utente progetto cognome
																AggiornaTableProgettoCognomeAndUtenteAndProgetto(Cognome_textField.getText(),Utente_textField.getText(),Progetto_textField.getText());
															}else {
																if(Utente_textField.getText().isEmpty()
												&& Progetto_textField.getText().length()>0
												&& Risultato_textField.getText().length()>0 && Cognome_textField.getText().length()>0) {
																//cognome progetto risultato
																	AggiornaTableProgettoCognomeAndProgettoAndRisultato(Cognome_textField.getText(),Progetto_textField.getText(),Risultato_textField.getText().toUpperCase());
																}else {
																	if(Utente_textField.getText().length()>0
												&& Progetto_textField.getText().length()>0
												&& Risultato_textField.getText().length()>0 && Cognome_textField.getText().isEmpty()) {
																		//nome progetto release
																		AggiornaTableProgettoUtenteAndProgettoAndRisultato(Utente_textField.getText(),Progetto_textField.getText(),Risultato_textField.getText().toUpperCase());
																		}else {
																		if(Utente_textField.getText().length()>0
																				&& Progetto_textField.getText().isEmpty()
																				&& Risultato_textField.getText().length()>0 && Cognome_textField.getText().length()>0) {
																			//utente cognome risultato
																			AggiornaTableProgettoCognomeAndUtenteAndRisultato(Cognome_textField.getText(),Utente_textField.getText(),Risultato_textField.getText().toUpperCase());
																			//AggiornaTableProgettoUtenteAndProgettoAndRisultato(Utente_textField.getText(),Progetto_textField.getText(),Risultato_textField.getText());
																		}else {
																		AggiornaTable();
																		}
																	}
																	
																}
																
															}
															
														}
														
													}
												}
													
											}
											
										}
									}
								}
							}

						}

					}
				}
			}
		});
		
		JLabel lblNewLabel_1 = new JLabel("Per la ricerca ogni campo \u00E8 facoltativo");
		lblNewLabel_1.setForeground(Color.RED);
		
		JLabel lblNewLabel_4 = new JLabel("Cognome");
		
		Cognome_textField = new JTextField();
		Cognome_textField.setColumns(10);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(lblNewLabel_1, GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE)
					.addGap(432))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(5)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
							.addGap(82))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(42)
							.addComponent(Utente_textField, GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)))
					.addGap(10)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(61)
							.addComponent(Cognome_textField))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblNewLabel_4, GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE)
							.addGap(81)))
					.addGap(10)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblNewLabel_2, GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
							.addGap(84))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(67)
							.addComponent(Progetto_textField, GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)))
					.addGap(10)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblNewLabel_3, GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE)
							.addGap(74))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(64)
							.addComponent(Risultato_textField)))
					.addGap(10)
					.addComponent(Cerca_btnNewButton, GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
					.addGap(7))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(5)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 728, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(lblNewLabel_1, GroupLayout.DEFAULT_SIZE, 13, Short.MAX_VALUE)
					.addGap(54)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(1)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(3)
									.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addGap(3))
								.addComponent(Utente_textField))
							.addGap(1))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(1)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(Cognome_textField)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(3)
									.addComponent(lblNewLabel_4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addGap(3)))
							.addGap(1))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(1)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(3)
									.addComponent(lblNewLabel_2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addGap(3))
								.addComponent(Progetto_textField))
							.addGap(1))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(1)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(3)
									.addComponent(lblNewLabel_3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addGap(3))
								.addComponent(Risultato_textField))
							.addGap(1))
						.addComponent(Cerca_btnNewButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addGap(4)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE)
					.addGap(22))
		);
		contentPane.setLayout(gl_contentPane);
	}

	public void AggiornaTable() {
		ResultSet rs;
		rs = ctrl.repositorydao.StampaTestEffettuati();
		// mydata.setRowCount(0);
		mydata.getDataVector().removeAllElements();

		try {

			while (rs.next()) {

				Object[] data = { rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),rs.getString(6) };
				mydata.addRow(data);

			}
			mydata.fireTableDataChanged();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void AggiornaTableNome(String nome) {
		ResultSet rs;
		rs = ctrl.repositorydao.StampaRicercaTestTramiteNome(nome);
		// mydata.setRowCount(0);
		mydata.getDataVector().removeAllElements();

		try {

			while (rs.next()) {

				Object[] data = { rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),rs.getString(6) };
				mydata.addRow(data);

			}
			mydata.fireTableDataChanged();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void AggiornaTableProgetto(String progetto) {
		ResultSet rs;
		rs = ctrl.repositorydao.StampaRicercaTestTramiteProgetto(progetto);
		// mydata.setRowCount(0);
		mydata.getDataVector().removeAllElements();

		try {

			while (rs.next()) {

				Object[] data = { rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),rs.getString(6) };
				mydata.addRow(data);

			}
			mydata.fireTableDataChanged();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void AggiornaTableRisultato(String risultato) {
		ResultSet rs;
		rs = ctrl.repositorydao.StampaRicercaTestTramiteRisultato(risultato);
		// mydata.setRowCount(0);
		mydata.getDataVector().removeAllElements();

		try {

			while (rs.next()) {

				Object[] data = { rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),rs.getString(6) };
				mydata.addRow(data);

			}
			mydata.fireTableDataChanged();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void AggiornaTableTutto(String nome, String progetto, String risultato, String Cognome) {
		ResultSet rs;
		rs = ctrl.repositorydao.StampaRicercaTestTramiteTutto(nome, progetto, risultato,Cognome);
		// mydata.setRowCount(0);
		mydata.getDataVector().removeAllElements();

		try {

			while (rs.next()) {

				Object[] data = { rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),rs.getString(6) };
				mydata.addRow(data);

			}
			mydata.fireTableDataChanged();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void AggiornaTableUtenteProgetto(String nome, String progetto) {
		ResultSet rs;
		// rs = ctrl.repositorydao.StampaRicercaTramiteTutto(nome, progetto, risultato);
		rs = ctrl.repositorydao.StampaRicercaTestTramiteNomeAndProgetto(nome, progetto);
		// mydata.setRowCount(0);
		mydata.getDataVector().removeAllElements();

		try {

			while (rs.next()) {

				Object[] data = { rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),rs.getString(6) };
				mydata.addRow(data);

			}
			mydata.fireTableDataChanged();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void AggiornaTableUtenteRisultato(String nome, String risultato) {
		ResultSet rs;
		rs = ctrl.repositorydao.StampaRicercaTestTramiteNomeAndRisultato(nome, risultato);
		// mydata.setRowCount(0);
		mydata.getDataVector().removeAllElements();

		try {

			while (rs.next()) {

				Object[] data = { rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),rs.getString(6) };
				mydata.addRow(data);

			}
			mydata.fireTableDataChanged();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void AggiornaTableProgettoRisultato(String progetto, String risultato) {
		ResultSet rs;
		// rs=ctrl.repositorydao.StampaRicercaTramiteNomeAndRisultato(nome, risultato);
		// mydata.setRowCount(0);
		rs = ctrl.repositorydao.StampaRicercaTestTramiteProgettoAndRisultato(progetto, risultato);
		mydata.getDataVector().removeAllElements();

		try {

			while (rs.next()) {

				Object[] data = { rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),rs.getString(6) };
				mydata.addRow(data);

			}
			mydata.fireTableDataChanged();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void AggiornaTableProgettoCognome(String cognome) {
		ResultSet rs;
		// rs=ctrl.repositorydao.StampaRicercaTramiteNomeAndRisultato(nome, risultato);
		// mydata.setRowCount(0);
		rs = ctrl.repositorydao.StampaRicercaTestTramiteCognome(cognome);
		mydata.getDataVector().removeAllElements();

		try {

			while (rs.next()) {

				Object[] data = { rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),rs.getString(6) };
				mydata.addRow(data);

			}
			mydata.fireTableDataChanged();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void AggiornaTableProgettoCognomeAndNome(String cognome, String nome) {
		ResultSet rs;
		// rs=ctrl.repositorydao.StampaRicercaTramiteNomeAndRisultato(nome, risultato);
		// mydata.setRowCount(0);
		rs = ctrl.repositorydao.StampaRicercaTestTramiteCognomeAndNome(cognome, nome);
		mydata.getDataVector().removeAllElements();

		try {

			while (rs.next()) {

				Object[] data = { rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),rs.getString(6) };
				mydata.addRow(data);

			}
			mydata.fireTableDataChanged();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void AggiornaTableProgettoCognomeAndProgetto(String cognome, String progetto) {
		ResultSet rs;
		// rs=ctrl.repositorydao.StampaRicercaTramiteNomeAndRisultato(nome, risultato);
		// mydata.setRowCount(0);
		rs = ctrl.repositorydao.StampaRicercaTestTramiteCognomeAndProgetto(cognome, progetto);
		mydata.getDataVector().removeAllElements();

		try {

			while (rs.next()) {

				Object[] data = { rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),rs.getString(6) };
				mydata.addRow(data);

			}
			mydata.fireTableDataChanged();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void AggiornaTableProgettoCognomeAndRisultato(String cognome, String risultato) {
		ResultSet rs;
		// rs=ctrl.repositorydao.StampaRicercaTramiteNomeAndRisultato(nome, risultato);
		// mydata.setRowCount(0);
		rs = ctrl.repositorydao.StampaRicercaTestTramiteCognomeAndRisultato(cognome, risultato);
		mydata.getDataVector().removeAllElements();

		try {

			while (rs.next()) {

				Object[] data = { rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),rs.getString(6) };
				mydata.addRow(data);

			}
			mydata.fireTableDataChanged();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void AggiornaTableProgettoCognomeAndUtenteAndProgetto(String cognome, String utente,String progetto) {
		ResultSet rs;
		// rs=ctrl.repositorydao.StampaRicercaTramiteNomeAndRisultato(nome, risultato);
		// mydata.setRowCount(0);
		rs = ctrl.repositorydao.StampaRicercaTestTramiteCognomeAndUtenteAndProgetto(cognome,utente, progetto);
		mydata.getDataVector().removeAllElements();

		try {

			while (rs.next()) {

				Object[] data = { rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),rs.getString(6) };
				mydata.addRow(data);

			}
			mydata.fireTableDataChanged();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void AggiornaTableProgettoCognomeAndUtenteAndRisultato(String cognome, String utente,String Risultato) {
		ResultSet rs;
		// rs=ctrl.repositorydao.StampaRicercaTramiteNomeAndRisultato(nome, risultato);
		// mydata.setRowCount(0);
		rs = ctrl.repositorydao.StampaRicercaTestTramiteCognomeAndUtenteAndRisultato(cognome,utente, Risultato);
		mydata.getDataVector().removeAllElements();

		try {

			while (rs.next()) {

				Object[] data = { rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),rs.getString(6) };
				mydata.addRow(data);

			}
			mydata.fireTableDataChanged();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void AggiornaTableProgettoUtenteAndProgettoAndRisultato(String utente, String progetto,String Risultato) {
		ResultSet rs;
		// rs=ctrl.repositorydao.StampaRicercaTramiteNomeAndRisultato(nome, risultato);
		// mydata.setRowCount(0);
		rs = ctrl.repositorydao.StampaRicercaTestTramiteUtenteAndProgettoAndRisultato( utente,  progetto, Risultato);
		mydata.getDataVector().removeAllElements();

		try {

			while (rs.next()) {

				Object[] data = { rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),rs.getString(6) };
				mydata.addRow(data);

			}
			mydata.fireTableDataChanged();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void AggiornaTableProgettoCognomeAndProgettoAndRisultato(String cognome, String progetto,String Risultato) {
		ResultSet rs;
		// rs=ctrl.repositorydao.StampaRicercaTramiteNomeAndRisultato(nome, risultato);
		// mydata.setRowCount(0);
		rs = ctrl.repositorydao.StampaRicercaTestTramiteCognomeAndProgettoAndRisultato( cognome,  progetto, Risultato);
		mydata.getDataVector().removeAllElements();

		try {

			while (rs.next()) {

				Object[] data = { rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),rs.getString(6) };
				mydata.addRow(data);

			}
			mydata.fireTableDataChanged();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
