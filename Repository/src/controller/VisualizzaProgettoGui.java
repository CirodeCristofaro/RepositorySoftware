package controller;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Color;

public class VisualizzaProgettoGui extends JFrame {

	private JPanel contentPane;
	private JTable progetto_table;
	Controller ctrl;
	private DefaultTableModel mydata;
	private JTextField Utente_textField;
	private JTextField Progetto_textField;
	private JTextField Release_textField;
	private JTextField Cognome_textField;
	public VisualizzaProgettoGui(Controller ctrl) {
//		addWindowListener(new WindowAdapter() {
//			@Override
//			public void windowActivated(WindowEvent e) {
//				AggiornaTable();
//			}
//		});
		setTitle("Visualizza Progetto");
		this.ctrl=ctrl;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 777, 453);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JScrollPane scrollPane = new JScrollPane();
		
		progetto_table = new JTable();
		progetto_table.setEnabled(false);
		progetto_table.setRowSelectionAllowed(false);
		mydata = new DefaultTableModel();
		mydata.addColumn("ID");
		mydata.addColumn("UTENTE");
		mydata.addColumn("PATHNAME");
		mydata.addColumn("PROGETTO");
		mydata.addColumn("DATA APERTURA");
		mydata.addColumn("DATA CHIUSURA");
		mydata.addColumn("RELEASE");
		AggiornaTable();
		progetto_table.setModel(mydata);
		scrollPane.setViewportView(progetto_table);
		JLabel lblNewLabel = new JLabel("Nome");

		Utente_textField = new JTextField();
		Utente_textField.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("Progetto");

		Progetto_textField = new JTextField();
		Progetto_textField.setColumns(10);

		JLabel lblNewLabel_3 = new JLabel("Release");

		Release_textField = new JTextField();
		Release_textField.setColumns(10);

		JButton Cerca_btnNewButton = new JButton("Cerca");
		Cerca_btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Utente_textField.getText().length() > 0 && Progetto_textField.getText().length() > 0
						&& Release_textField.getText().length() > 0 && Cognome_textField.getText().length()>0) {
					// metodo per tutti i campi
					AggiornaTableTutto(Utente_textField.getText(), Progetto_textField.getText(),
							Release_textField.getText());
				} else {
					if (Utente_textField.getText().isEmpty() && Progetto_textField.getText().length() > 0
							&& Release_textField.getText().isEmpty() && Cognome_textField.getText().isEmpty()) {
						// metodo via progetto;
						AggiornaTableProgetto(Progetto_textField.getText());
					} else {
						if (Utente_textField.getText().length() > 0 && Progetto_textField.getText().isEmpty()
								&& Release_textField.getText().isEmpty() && Cognome_textField.getText().isEmpty()) {
							// metodo via nome
							AggiornaTableNome(Utente_textField.getText());
						} else {
							if (Utente_textField.getText().isEmpty() && Progetto_textField.getText().isEmpty()
									&& Release_textField.getText().length() > 0 && Cognome_textField.getText().isEmpty()) {
								// metodo via risultato
								AggiornaTableRelease(Release_textField.getText());
							} else {
								if (Utente_textField.getText().length() > 0 && Progetto_textField.getText().length() > 0
										&& Release_textField.getText().isEmpty() && Cognome_textField.getText().isEmpty()) {
									// metodo via utente e progetto
									AggiornaTableUtenteProgetto(Utente_textField.getText(),
											Progetto_textField.getText());
								} else {
									if (Utente_textField.getText().length() > 0
											&& Progetto_textField.getText().isEmpty()
											&& Release_textField.getText().length() > 0 && Cognome_textField.getText().isEmpty()) {
										// metodo via utente e risultato
										AggiornaTableUtenteRelease(Utente_textField.getText(),
												Release_textField.getText());
									} else {
										if (Utente_textField.getText().isEmpty()
												&& Progetto_textField.getText().length() > 0
												&& Release_textField.getText().length() > 0 && Cognome_textField.getText().isEmpty()) {
											// metodo via progetto e risultato
											AggiornaTableProgettoRelease(Progetto_textField.getText(),
													Release_textField.getText());
										} else {
											if(Utente_textField.getText().length()>0
												&& Progetto_textField.getText().isEmpty()
												&& Release_textField.getText().isEmpty() && Cognome_textField.getText().length()>0) {
												//metodo via utente cognome
												AggiornaTableProgettoUtenteandCognome(Utente_textField.getText(),Cognome_textField.getText());
											}else {
												if(Utente_textField.getText().isEmpty()
												&& Progetto_textField.getText().length()>0
												&& Release_textField.getText().isEmpty() && Cognome_textField.getText().length()>0){
												//METODO VIA PROGETTO E COGNOME
													AggiornaTableProgettoCognomeAndProgetto(Cognome_textField.getText(), Progetto_textField.getText());
												}else {
													if(Utente_textField.getText().isEmpty()
												&& Progetto_textField.getText().isEmpty()
												&& Release_textField.getText().length() > 0 && Cognome_textField.getText().length()>0) {
														//metodo via release e cognome
														AggiornaTableProgettoCognomeAndRelease(Cognome_textField.getText(),Release_textField.getText());
													}else {
														if(Utente_textField.getText().isEmpty()
												&& Progetto_textField.getText().isEmpty()
												&& Release_textField.getText().isEmpty() && Cognome_textField.getText().length()>0) {
															//metodo via cognome
															AggiornaTableProgettoCognome(Cognome_textField.getText());
														}else {
															if(Utente_textField.getText().isEmpty()
												&& Progetto_textField.getText().length()>0
												&& Release_textField.getText().length()>0 && Cognome_textField.getText().length()>0) {
																//metodo progetto release cognome
																AggiornaTableProgettoCognomeAndReleaseAndProgetto(Cognome_textField.getText(),Release_textField.getText(),Progetto_textField.getText());
															}else {
																if(Utente_textField.getText().length()>0
																		&& Progetto_textField.getText().length()>0
																		&& Release_textField.getText().length()>0 && Cognome_textField.getText().isEmpty()) {
																	//metodo utente progetto release
																	AggiornaTableProgettoNomeAndProgettoAndRelease(Utente_textField.getText(),Progetto_textField.getText(),Release_textField.getText());
																}else {
																	if(Utente_textField.getText().length()>0
																			&& Progetto_textField.getText().isEmpty()
																			&& Release_textField.getText().length()>0 && Cognome_textField.getText().length()>0) {
																		//nome cognome release
																		AggiornaTableProgettoNomeAndCognomeAndRelease(Utente_textField.getText(),Cognome_textField.getText(),Release_textField.getText());
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
		});
		contentPane.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Per la ricerca ogni campo \u00E8 facoltativo");
		lblNewLabel_1.setForeground(Color.RED);
		
		JLabel lblNewLabel_4 = new JLabel("Cognome");
		
		Cognome_textField = new JTextField();
		Cognome_textField.setColumns(10);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(5)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblNewLabel_1, GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
							.addGap(535))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(42)
									.addComponent(Utente_textField, GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
									.addGap(83)))
							.addGap(10)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(lblNewLabel_4, GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
									.addGap(76))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(60)
									.addComponent(Cognome_textField)))
							.addGap(10)
							.addComponent(lblNewLabel_2, GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
							.addComponent(Progetto_textField, GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)
							.addGap(14)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(61)
									.addComponent(Release_textField))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(lblNewLabel_3, GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE)
									.addGap(71)))
							.addGap(19)
							.addComponent(Cerca_btnNewButton, GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE)
							.addGap(10))
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 753, Short.MAX_VALUE)))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(5)
					.addComponent(lblNewLabel_1, GroupLayout.DEFAULT_SIZE, 13, Short.MAX_VALUE)
					.addGap(38)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(Utente_textField, GroupLayout.DEFAULT_SIZE, 21, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(3)
							.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 15, Short.MAX_VALUE)
							.addGap(3))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(1)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(3)
									.addComponent(lblNewLabel_4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addGap(3))
								.addComponent(Cognome_textField))
							.addGap(1))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(3)
							.addComponent(lblNewLabel_2, GroupLayout.DEFAULT_SIZE, 15, Short.MAX_VALUE)
							.addGap(3))
						.addComponent(Progetto_textField, GroupLayout.DEFAULT_SIZE, 21, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(1)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(Release_textField)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(2)
									.addComponent(lblNewLabel_3, GroupLayout.DEFAULT_SIZE, 15, Short.MAX_VALUE)
									.addGap(2)))
							.addGap(1))
						.addComponent(Cerca_btnNewButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addGap(7)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE)
					.addGap(21))
		);
		contentPane.setLayout(gl_contentPane);
		
	}
	public void AggiornaTable() {
		ResultSet rs;
		rs=ctrl.repositorydao.StampaTuttoProgetto();
		//mydata.setRowCount(0);
		mydata.getDataVector().removeAllElements();

		try {
					
			while(rs.next()) {
				
				Object[] data = {rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7)};
				mydata.addRow(data);
				
			}
			mydata.fireTableDataChanged();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void AggiornaTableNome(String nome) {
		ResultSet rs;
		rs = ctrl.repositorydao.StampaRicercaVProgettoTramiteNome(nome);
		// mydata.setRowCount(0);
		mydata.getDataVector().removeAllElements();

		try {

			while (rs.next()) {

				Object[] data = { rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7) };
				mydata.addRow(data);

			}
			mydata.fireTableDataChanged();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void AggiornaTableProgetto(String progetto) {
		ResultSet rs;
		rs = ctrl.repositorydao.StampaRicercaVProgettoTramiteProgetto(progetto);
		// mydata.setRowCount(0);
		mydata.getDataVector().removeAllElements();

		try {

			while (rs.next()) {

				Object[] data = { rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7) };
				mydata.addRow(data);

			}
			mydata.fireTableDataChanged();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void AggiornaTableRelease(String release) {
		ResultSet rs;
		rs = ctrl.repositorydao.StampaRicercaVProgettoTramiteRelease(release);
		// mydata.setRowCount(0);
		mydata.getDataVector().removeAllElements();

		try {

			while (rs.next()) {

				Object[] data = { rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7) };
				mydata.addRow(data);

			}
			mydata.fireTableDataChanged();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void AggiornaTableTutto(String nome, String progetto, String release) {
		ResultSet rs;
		rs = ctrl.repositorydao.StampaRicercaVProgettoTramiteTutto(nome, progetto, release);
		// mydata.setRowCount(0);
		mydata.getDataVector().removeAllElements();

		try {

			while (rs.next()) {

				Object[] data = { rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7) };
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
		rs = ctrl.repositorydao.StampaRicercaVProgettoTramiteNomeAndProgetto(nome, progetto);
		// mydata.setRowCount(0);
		mydata.getDataVector().removeAllElements();

		try {

			while (rs.next()) {

				Object[] data = { rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7) };
				mydata.addRow(data);

			}
			mydata.fireTableDataChanged();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void AggiornaTableUtenteRelease(String nome, String release) {
		ResultSet rs;
		rs = ctrl.repositorydao.StampaRicercaVProgettoTramiteNomeAndRelease(nome, release);
		// mydata.setRowCount(0);
		mydata.getDataVector().removeAllElements();

		try {

			while (rs.next()) {

				Object[] data = { rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7) };
				mydata.addRow(data);

			}
			mydata.fireTableDataChanged();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void AggiornaTableProgettoRelease(String progetto, String release) {
		ResultSet rs;
		
		rs = ctrl.repositorydao.StampaRicercaVProgettoTramiteProgettoAndRelease(progetto, release);
		mydata.getDataVector().removeAllElements();

		try {

			while (rs.next()) {

				Object[] data = {rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7) };
				mydata.addRow(data);

			}
			mydata.fireTableDataChanged();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void AggiornaTableProgettoUtenteandCognome(String utente, String cognome) {
		ResultSet rs;
		
		rs = ctrl.repositorydao.StampaRicercaVProgettoTramiteNomeandCognome(utente, cognome);
		mydata.getDataVector().removeAllElements();

		try {

			while (rs.next()) {

				Object[] data = {rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7) };
				mydata.addRow(data);

			}
			mydata.fireTableDataChanged();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void AggiornaTableProgettoCognome(String cognome) {
		ResultSet rs;
		
		rs = ctrl.repositorydao.StampaRicercaVProgettoTramiteCognome(cognome);
		mydata.getDataVector().removeAllElements();

		try {

			while (rs.next()) {

				Object[] data = {rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7) };
				mydata.addRow(data);

			}
			mydata.fireTableDataChanged();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void AggiornaTableProgettoCognomeAndProgetto(String cognome, String progetto) {
		ResultSet rs;
		
		rs = ctrl.repositorydao.StampaRicercaVProgettoTramiteCognomeAndProgetto(cognome, progetto);
		mydata.getDataVector().removeAllElements();

		try {

			while (rs.next()) {

				Object[] data = {rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7) };
				mydata.addRow(data);

			}
			mydata.fireTableDataChanged();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void AggiornaTableProgettoCognomeAndRelease(String cognome, String release) {
		ResultSet rs;
		
		rs = ctrl.repositorydao.StampaRicercaVProgettoTramiteCognomeAndRelease(cognome, release);
		mydata.getDataVector().removeAllElements();

		try {

			while (rs.next()) {

				Object[] data = {rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7) };
				mydata.addRow(data);

			}
			mydata.fireTableDataChanged();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void AggiornaTableProgettoCognomeAndReleaseAndProgetto(String cognome, String release,String Progetto) {
		ResultSet rs;
		
		rs = ctrl.repositorydao.StampaRicercaVProgettoTramiteCognomeAndReleaseAndProgetto(cognome,release,Progetto);
		mydata.getDataVector().removeAllElements();

		try {

			while (rs.next()) {

				Object[] data = {rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7) };
				mydata.addRow(data);

			}
			mydata.fireTableDataChanged();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void AggiornaTableProgettoNomeAndProgettoAndRelease(String nome, String progetto,String release) {
		ResultSet rs;
		
		rs = ctrl.repositorydao.StampaRicercaVProgettoTramiteNomeAndProgettoAndRelease(nome,progetto,release);
		mydata.getDataVector().removeAllElements();

		try {

			while (rs.next()) {

				Object[] data = {rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7) };
				mydata.addRow(data);

			}
			mydata.fireTableDataChanged();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void AggiornaTableProgettoNomeAndCognomeAndRelease(String nome, String cognome,String release) {
		ResultSet rs;
		
		rs = ctrl.repositorydao.StampaRicercaVProgettoTramiteCognomeAndReleaseAndNome( cognome,  release,  nome);
		mydata.getDataVector().removeAllElements();

		try {

			while (rs.next()) {

				Object[] data = {rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7) };
				mydata.addRow(data);

			}
			mydata.fireTableDataChanged();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
