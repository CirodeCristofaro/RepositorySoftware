package controller;

import java.sql.Connection;

import Dao.RepositoryDao;
import Dao.RepositoryImp;
import connessione.connessione;

public class Controller {
	HomeGui home;
	private Connection connection;
	CreaProgettoGui progetto;
	CreaUtenteGui utenteGui;
	CreaPackageGui packageGui;
	CreaClasseGui classeGui;
	AggiungiMetodoGui metodoGui;
	ChiudiClasseGui chiudiClasseGui;
	ChiudiProgettoGui chiudiProgettoGui;
	VisualizzaProgettoGui visualProgettoGui;
	VisualizzaClasseGui visualClasseGui;
	TestGui testGui;
	VisualizzaTestEffettuatiGui visualTestEffetuatiGui;
	login login;
	PartecipaGui partecipagui;
	RepositoryDao repositorydao;

	public static void main(String[] args) {
		Controller TheController = new Controller();
	}

	public Controller() {
		login = new login(this);
		login.setVisible(true);

	}

	public void ConnessioneEffettuata(String Host, int Porta, String Servizio, String utente, String schema,
			String passw) {
		connection = connessione.agg(Host, Porta, Servizio, utente, schema, passw);
		if(connection!=null){
			login.setVisible(false);
		repositorydao = new RepositoryImp(this, connection);
		home = new HomeGui(this);
		home.setVisible(true);
		}
	}

	public void ApriProgetto() {
		progetto = new CreaProgettoGui(this);
		progetto.setVisible(true);
	}

	public void ApriCreazioneUtente() {
		utenteGui = new CreaUtenteGui(this);
		utenteGui.setVisible(true);
	}

	public void CreaPackage() {
		packageGui = new CreaPackageGui(this);
		packageGui.setVisible(true);
	}

	public void CreaClasse() {
		classeGui = new CreaClasseGui(this);
		classeGui.setVisible(true);
	}

	public void CreaMetodo() {
		metodoGui = new AggiungiMetodoGui(this);
		metodoGui.setVisible(true);
	}

	public void ChiudiClasseGui() {
		chiudiClasseGui = new ChiudiClasseGui(this);
		chiudiClasseGui.setVisible(true);
	}

	public void ChiudiProgettoGui() {
		chiudiProgettoGui = new ChiudiProgettoGui(this);
		chiudiProgettoGui.setVisible(true);
	}

	public void VisualProgettoGui() {
		visualProgettoGui = new VisualizzaProgettoGui(this);
		visualProgettoGui.setVisible(true);
	}

	public void VisualClasseGui() {
		visualClasseGui = new VisualizzaClasseGui(this);
		visualClasseGui.setVisible(true);
	}

	public void TestGui() {
		testGui = new TestGui(this);
		testGui.setVisible(true);
	}

	public void VisualTestEffettuati() {
		visualTestEffetuatiGui = new VisualizzaTestEffettuatiGui(this);
		visualTestEffetuatiGui.setVisible(true);
	}
	public void PartecipaProgetto() {
		partecipagui= new PartecipaGui(this);
		partecipagui.setVisible(true);
	}
}
