package Dao;

import java.sql.Date;
import java.sql.ResultSet;

public interface RepositoryDao {
	public void CreaUtente(String Utente, String Cognome);

	public boolean CreaProgetto(int utente, String Pathname, String NomeProgetto);

	public ResultSet indicePackage(String NomeProgetto);

	public int IndiceNome(String nome);

	public ResultSet StampaCollaboratore(String utente);

	public ResultSet StampaUtente();

	public ResultSet stampaProgetti();

	public boolean CreaPackage(String NomeProgetto, String NomePackage, String pathPackage);

	public boolean CreaClasse(String NomeProgetto, String NomeClasse, String Metodo, String NomePackage);

	public void Collaboratore(int collaboratore, String NomeProgetto);

	public void UtenteAccesso(int utente, String NomeClasse);

	public ResultSet SelezionaProgetto(String utente);

	public ResultSet IndiceOnlyPackage(String utente, String Progetto, String Package);

	public ResultSet SelezionePackage(String utente, String Progetto);

	public boolean InserimentoSottoPackage(String utente, String Progetto, String Package, String sottoPackage);

	public ResultSet PathnamePackage(String Package);

	public ResultSet OnlyNameClasse(String Package);

	public ResultSet IndiceOnlyClasse(String Classe);

	public int indiceRelease(String Progetto, String Versione);

	public void SottoMetodo(String nomeClasse, String sottoMetodo);

	public String DataAperturaClasse(String nomeClasse);

	public String DataAperturaProgetto(String nomeProgetto);

	public ResultSet StampaClasse(String Utente, String Progetto);

	void SettaDataChiusura(Date DataChiusura, String nomeClasse);

	public void settaDatachiusuraProgetto(String nomeProgetto, Date dataChiusura);

	public ResultSet StampaTuttoProgetto();

	public ResultSet StampaVClasseTutti();

	public ResultSet StampaVersioneProgetto(String Utente, String Progetto);

	public ResultSet StampaStatoVersioneProgetto(String Progetto, String Versione);

	public ResultSet StampaTestEffettuati();

	public void creaTest(String Descrizione, String Risultato, String Utente, String Progetto, String Versione,
			String Stato, String pathname);

	public ResultSet StampaRicercaTestTramiteNome(String nome);

	public ResultSet StampaRicercaTestTramiteProgetto(String Progetto);

	public ResultSet StampaRicercaTestTramiteRisultato(String risultato);

	public ResultSet StampaRicercaTestTramiteTutto(String nome, String progetto, String risultato, String Cognome);

	public ResultSet StampaRicercaTestTramiteNomeAndProgetto(String nome, String progetto);

	public ResultSet StampaRicercaTestTramiteNomeAndRisultato(String nome, String risultato);

	public ResultSet StampaRicercaTestTramiteProgettoAndRisultato(String progetto, String risultato);

	public ResultSet StampaRicercaTestTramiteCognome(String cognome);

	public ResultSet StampaRicercaTestTramiteCognomeAndNome(String cognome, String nome);

	public ResultSet StampaRicercaTestTramiteCognomeAndProgetto(String cognome, String progetto);

	public ResultSet StampaRicercaTestTramiteCognomeAndRisultato(String cognome, String risultato);

	public ResultSet StampaRicercaTestTramiteCognomeAndUtenteAndProgetto(String cognome, String utente,
			String progetto);

	public ResultSet StampaRicercaTestTramiteCognomeAndUtenteAndRisultato(String cognome, String utente,
			String Risultato);

	public ResultSet StampaRicercaTestTramiteUtenteAndProgettoAndRisultato(String utente, String progetto,
			String Risultato);

	public ResultSet StampaRicercaTestTramiteCognomeAndProgettoAndRisultato(String cognome, String progetto,
			String Risultato);

	public ResultSet StampaRicercaVProgettoTramiteNome(String nome);

	public ResultSet StampaRicercaVProgettoTramiteProgetto(String Progetto);

	public ResultSet StampaRicercaVProgettoTramiteRelease(String release);

	public ResultSet StampaRicercaVProgettoTramiteNomeAndProgetto(String nome, String progetto);

	public ResultSet StampaRicercaVProgettoTramiteNomeAndRelease(String nome, String release);

	public ResultSet StampaRicercaVProgettoTramiteProgettoAndRelease(String progetto, String release);

	public ResultSet StampaRicercaVProgettoTramiteTutto(String nome, String progetto, String release);

	public ResultSet StampaRicercaVProgettoTramiteCognome(String cognome);

	public ResultSet StampaRicercaVProgettoTramiteCognomeAndRelease(String cognome, String release);

	public ResultSet StampaRicercaVProgettoTramiteCognomeAndProgetto(String cognome, String progetto);

	public ResultSet StampaRicercaVProgettoTramiteNomeandCognome(String nome, String cognome);

	public ResultSet StampaRicercaVProgettoTramiteNomeAndProgettoAndRelease(String nome, String progetto,
			String release);

	public ResultSet StampaRicercaVProgettoTramiteCognomeAndReleaseAndProgetto(String cognome, String release,
			String Progetto);

	public ResultSet StampaRicercaVProgettoTramiteCognomeAndReleaseAndNome(String cognome, String release, String nome);

	public void Partecipa(String nome, String progetto);

	public ResultSet StampaRicercaVClasseTramiteProgetto(String progetto);

	public ResultSet StampaRicercaVClasseTramitePackage(String Package);

	public ResultSet StampaRicercaVClasseTramiteClasse(String classe);

	public ResultSet StampaRicercaVClasseTramiteTutto(String progetto, String Package, String classe);

	public ResultSet StampaRicercaVClasseTramiteProgettoAndPackage(String progetto, String Package);

	public ResultSet StampaRicercaVClasseTramiteProgettoAndClasse(String progetto, String classe);

	public ResultSet StampaRicercaTestTramitePackageAndClasse(String Package, String classe);

	public String SommaUtenti();

	public String SommaProgettiAttivi();

	public String SommaProgettiConclusi();
	
	public ResultSet stampaUtentiNonPartecipanti(String Progetto);
	public ResultSet stampaProgettoDiUtentinonPartecipanti(String nome);

}
