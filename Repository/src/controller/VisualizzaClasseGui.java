package controller;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;


import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class VisualizzaClasseGui extends JFrame {

	private JPanel contentPane;
	Controller ctrl;
	private JTable Classe_table;
	private DefaultTableModel mydata;
	private JTextField Progetto_textField;
	private JTextField Package_textField;
	private JTextField CLasse_textField;
	
	public VisualizzaClasseGui(Controller ctrl) {
//		addWindowListener(new WindowAdapter() {
//			@Override
//			public void windowOpened(WindowEvent e) {
//				AggiornaTable();
//			}
//		});
		setTitle("Visualizza Classe");
		this.ctrl=ctrl;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 676, 431);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JScrollPane scrollPane = new JScrollPane();
		
		Classe_table = new JTable();
		Classe_table.setRowSelectionAllowed(false);
		Classe_table.setEnabled(false);
		mydata = new DefaultTableModel();
		mydata.addColumn("PROGETTO");
		mydata.addColumn("PATHNAME PACKAGE");
		mydata.addColumn("Package");
		mydata.addColumn("PATHNAME CLASSE");
		mydata.addColumn("Classe");
		mydata.addColumn("Metodo");
		mydata.addColumn("Sotto Metodo");
		mydata.addColumn("Data Apertura");
		mydata.addColumn("Data Chisura");
		AggiornaTable();
		Classe_table.setModel(mydata);
		scrollPane.setViewportView(Classe_table);
		
		JLabel lblNewLabel = new JLabel("Progetto");
		
		Progetto_textField = new JTextField();
		Progetto_textField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Package");
		
		Package_textField = new JTextField();
		Package_textField.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Classe");
		
		CLasse_textField = new JTextField();
		CLasse_textField.setColumns(10);
		
		JButton Cerca_btnNewButton = new JButton("Cerca");
		Cerca_btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if((Progetto_textField.getText().length()>0) && (Package_textField.getText().length()>0 ) && (CLasse_textField.getText().length()>0 ) ) {
					//metodo con tutti i campi
					AggiornaTableTutto(Progetto_textField.getText(), Package_textField.getText(), CLasse_textField.getText());
				}else {
					if((Progetto_textField.getText().length()>0) && (Package_textField.getText().isEmpty()) && (CLasse_textField.getText().isEmpty())) {
					//metodo solo con progetto	
						AggiornaTableTramiteProgetto(Progetto_textField.getText());
					}else {
						if((Progetto_textField.getText().length()>0) && (Package_textField.getText().length()>0) &&(CLasse_textField.getText().isEmpty()) ) {
							//metodo con progetto e package
							AggiornaTableTramiteProgettoAndPackage(Progetto_textField.getText(), Package_textField.getText());
						}else {
							if((Progetto_textField.getText().length()>0) && (Package_textField.getText().isEmpty()) && (CLasse_textField.getText().length()>0)) {
								//metodo con progetto e classe
								AggiornaTableTramiteProgettoAndClasse(Progetto_textField.getText(), CLasse_textField.getText());
							}else {
								if((Progetto_textField.getText().isEmpty()) && (Package_textField.getText().length()>0) && (CLasse_textField.getText().isEmpty())) {
									//metodo solo packade
									AggiornaTableTramitePackage(Package_textField.getText());
								}else {
									if((Progetto_textField.getText().isEmpty()) && (Package_textField.getText().length()>0) &&(CLasse_textField.getText().length()>0)) {
										//metodo package e classe
										AggiornaTableTramitePackageAndClasse(Package_textField.getText(), CLasse_textField.getText());
									}else {
										if((Progetto_textField.getText().isEmpty()) && (Package_textField.getText().isEmpty()) &&(CLasse_textField.getText().length()>0)) {
											//metodo  solo classe
											AggiornaTableTramiteClasse(CLasse_textField.getText());
										}else {
										//tutti vuoti
										AggiornaTable();
										}
									}
								}
							}
						}
					}
				}
			}
		});
		
		JLabel lblNewLabel_3 = new JLabel("Ogni campo \u00E8 facoltativo");
		lblNewLabel_3.setForeground(Color.RED);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(5)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblNewLabel_3, GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE)
							.addGap(425))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(54)
									.addComponent(Progetto_textField))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE)
									.addGap(95)))
							.addGap(10)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(57)
									.addComponent(Package_textField))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(lblNewLabel_1, GroupLayout.DEFAULT_SIZE, 63, Short.MAX_VALUE)
									.addGap(90)))
							.addGap(10)
							.addComponent(lblNewLabel_2, GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
							.addGap(11)
							.addComponent(CLasse_textField, GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)
							.addGap(48)
							.addComponent(Cerca_btnNewButton, GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
							.addGap(39))
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 651, Short.MAX_VALUE)))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(5)
					.addComponent(lblNewLabel_3, GroupLayout.DEFAULT_SIZE, 13, Short.MAX_VALUE)
					.addGap(34)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(2)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(Progetto_textField)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(3)
									.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addGap(3))))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(2)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(Package_textField)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(3)
									.addComponent(lblNewLabel_1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addGap(3))))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(5)
							.addComponent(lblNewLabel_2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGap(3))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(2)
							.addComponent(CLasse_textField))
						.addComponent(Cerca_btnNewButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addGap(7)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE)
					.addGap(3))
		);
		contentPane.setLayout(gl_contentPane);
	}
	public void AggiornaTable() {
		ResultSet rs;
		rs=ctrl.repositorydao.StampaVClasseTutti();
		//mydata.setRowCount(0);
		mydata.getDataVector().removeAllElements();

		try {
					
			while(rs.next()) {
				
				Object[] data = {rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9)};
				mydata.addRow(data);
				
			}
			mydata.fireTableDataChanged();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	public void AggiornaTableTutto(String progetto,String Package,String Classe) {
		ResultSet rs;
		rs=ctrl.repositorydao.StampaRicercaVClasseTramiteTutto(progetto, Package, Classe);
		//mydata.setRowCount(0);
		mydata.getDataVector().removeAllElements();

		try {
					
			while(rs.next()) {
				
				Object[] data = {rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9)};
				mydata.addRow(data);
				
			}
			mydata.fireTableDataChanged();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	public void AggiornaTableTramiteProgetto(String Progetto) {
		ResultSet rs;
		rs=ctrl.repositorydao.StampaRicercaVClasseTramiteProgetto(Progetto);
		//mydata.setRowCount(0);
		mydata.getDataVector().removeAllElements();

		try {
					
			while(rs.next()) {
				
				Object[] data = {rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9)};
				mydata.addRow(data);
				
			}
			mydata.fireTableDataChanged();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	public void AggiornaTableTramiteProgettoAndPackage(String Progetto,String Package) {
		ResultSet rs;
		rs=ctrl.repositorydao.StampaRicercaVClasseTramiteProgettoAndPackage(Progetto, Package);
		//mydata.setRowCount(0);
		mydata.getDataVector().removeAllElements();

		try {
					
			while(rs.next()) {
				
				Object[] data = {rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9)};
				mydata.addRow(data);
				
			}
			mydata.fireTableDataChanged();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	public void AggiornaTableTramiteProgettoAndClasse(String Progetto,String Classe) {
		ResultSet rs;
		rs=ctrl.repositorydao.StampaRicercaVClasseTramiteProgettoAndClasse(Progetto, Classe);
		//mydata.setRowCount(0);
		mydata.getDataVector().removeAllElements();

		try {
					
			while(rs.next()) {
				
				Object[] data = {rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9)};
				mydata.addRow(data);
				
			}
			mydata.fireTableDataChanged();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	public void AggiornaTableTramitePackage(String Package) {
		ResultSet rs;
		rs=ctrl.repositorydao.StampaRicercaVClasseTramitePackage(Package);
		//mydata.setRowCount(0);
		mydata.getDataVector().removeAllElements();

		try {
					
			while(rs.next()) {
				
				Object[] data = {rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9)};
				mydata.addRow(data);
				
			}
			mydata.fireTableDataChanged();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	public void AggiornaTableTramitePackageAndClasse(String Package,String Classe) {
		ResultSet rs;
		rs=ctrl.repositorydao.StampaRicercaTestTramitePackageAndClasse(Package, Classe);
		//mydata.setRowCount(0);
		mydata.getDataVector().removeAllElements();

		try {
					
			while(rs.next()) {
				
				Object[] data = {rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9)};
				mydata.addRow(data);
				
			}
			mydata.fireTableDataChanged();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	public void AggiornaTableTramiteClasse(String Classe) {
		ResultSet rs;
		rs=ctrl.repositorydao.StampaRicercaVClasseTramiteClasse(Classe);
		//mydata.setRowCount(0);
		mydata.getDataVector().removeAllElements();

		try {
					
			while(rs.next()) {
				
				Object[] data = {rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9)};
				mydata.addRow(data);
				
			}
			mydata.fireTableDataChanged();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
}
