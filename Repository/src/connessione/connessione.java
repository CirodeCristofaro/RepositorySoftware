package connessione;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import oracle.jdbc.pool.OracleDataSource;


public class connessione {
private static Connection connection = null;
	
	public static Connection getConnection(String Schema,String servizio,String nome,String password) {
		
		if(connection == null){
			try {
				
				Class.forName("oracle.jdbc.driver.OracleDriver");	
			}
			catch(ClassNotFoundException e) {
				System.err.println("Errore caricamento Driver: "+e.getMessage());
			}
			try {
				connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","utente","123");
				
				System.out.println("Connessione avvenuta...");
			}
			catch(SQLException sql) {
				System.err.println("Errore connessione: " + sql.getMessage());
			}
		}
		return connection;
	}
	
	public static Connection agg(String host,int porta,String servizio,String utente,String schema,String passw) {
		if(connection == null){
			try {
				
				Class.forName("oracle.jdbc.driver.OracleDriver");	
			}
			catch(ClassNotFoundException e) {
				System.err.println("Errore caricamento Driver: "+e.getMessage());
			}
			try {
				OracleDataSource ods= new OracleDataSource();
				ods.setDriverType("thin");
				ods.setServerName(host);
				ods.setPortNumber(porta);
				ods.setDatabaseName(schema);
				ods.setServiceName(servizio);
				ods.setPassword(passw);
				ods.setUser(utente);
				connection=ods.getConnection();
				System.out.println("Connessione avvenuta...");
			}
			catch(SQLException sql) {
				JOptionPane optionPane = new JOptionPane("Uno o più campi sono sbagliati", JOptionPane.ERROR_MESSAGE);    
				JDialog dialog = optionPane.createDialog("Errore");
				dialog.setAlwaysOnTop(true);
				dialog.setVisible(true);
				//System.err.println("Errore connessione: " + sql.getMessage());
				
			}
		}
		return connection;
	}

}
