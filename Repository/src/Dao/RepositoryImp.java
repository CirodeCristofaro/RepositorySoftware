package Dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import controller.Controller;

public class RepositoryImp implements RepositoryDao {
	Controller ctrl;
	Connection connection;

	public RepositoryImp(Controller ctrl, Connection con) {
		this.ctrl = ctrl;
		this.connection = con;
	}

	@Override
	public void CreaUtente(String Utente, String Cognome) {
		PreparedStatement u;
		try {
			u = connection.prepareStatement(
					"INSERT INTO UTENTE(IDUTENTE,NOME,COGNOME) " + "VALUES(null,:1,:2)");
			u.setString(1, Utente);
			u.setString(2, Cognome);
			u.executeUpdate();
		} catch (SQLException sq) {
			sq.printStackTrace();
		}

	}

	@Override
	public boolean CreaProgetto(int utente, String Pathname, String NomeProgetto) {
		PreparedStatement prog;
		PreparedStatement partecipa;
		try {

			partecipa = connection.prepareStatement("INSERT INTO PARTECIPA(IDUTENTE,NOMEPROGETTO)" + "VALUES(:1,:2)");
//			prog = connection.prepareStatement("INSERT INTO PROGETTO(NOMEPROGETTO,PATHNAME,DATAAPERTURA)" + "VALUES(:1,:2,:3)");
//			prog.setString(1, NomeProgetto);
//			prog.setString(2, Pathname);
//			prog.setDate(3, getCurrentDate());
//			prog.executeUpdate();
			prog = connection.prepareStatement(
					"INSERT INTO VIEWPROGETTO(NOMEPROGETTO,PATHNAME,DATAAPERTURA)" + " VALUES(:1,:2,:3)");
			prog.setString(1, NomeProgetto);
			prog.setString(2, Pathname);
			prog.setDate(3, getCurrentDate());
			prog.executeUpdate();
			partecipa.setInt(1, utente);
			partecipa.setString(2, NomeProgetto);
			partecipa.executeUpdate();
			return true;
		} catch (SQLException sq) {
			sq.printStackTrace();
			return false;
		}

	}

	@Override
	public int IndiceNome(String nome) {
		PreparedStatement ind;
		ResultSet rs;
		int a;
		try {
			ind = connection.prepareStatement("select u.IDUTENTE FROM UTENTE u " + "WHERE u.NOME||' '||u.COGNOME =:1");
			ind.setString(1, nome);
			rs = ind.executeQuery();
			while (rs.next()) {
				a = rs.getInt(1);
				return a;
			}
			// a=rs.getInt(1);

		} catch (SQLException sq) {
			sq.printStackTrace();
		}
		return 0;
	}

	@Override
	public ResultSet StampaCollaboratore(String utente) {
		ResultSet rs;
		PreparedStatement coll;
		try {

			coll = connection
					.prepareStatement(" SELECT NOME ||' '|| COGNOME FROM UTENTE" + " WHERE NOME ||' '|| COGNOME !=:1");
			coll.setString(1, utente);
			rs = coll.executeQuery();

			// hasNext=rs.next();
			// if(!hasNext) {
			// JOptionPane.showMessageDialog(new JFrame(), "Il DataBase è vuoto!", "DataBase
			// Vuoto", JOptionPane.ERROR_MESSAGE);
			// }else {
			return rs;// }

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ResultSet StampaUtente() {
		String query = "SELECT  NOME ||' '|| COGNOME FROM UTENTE  ORDER BY IDUTENTE ";
		ResultSet rs;
		Statement s;
		try {
			s = connection.createStatement();
			rs = s.executeQuery(query);

			// hasNext=rs.next();
			// if(!hasNext) {
			// JOptionPane.showMessageDialog(new JFrame(), "Il DataBase è vuoto!", "DataBase
			// Vuoto", JOptionPane.ERROR_MESSAGE);
			// }else {
			return rs;// }

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean CreaPackage(String NomeProgetto, String NomePackage, String pathPackage) {
		PreparedStatement Package;
		try {
			Package = connection.prepareStatement("INSERT INTO PACKAGE(NOMEPACKAGE,PATHNAME,NOMEPROGETTO,IDPACKAGE)"
					+ "VALUES(:1,:2,:3,null)");

			Package.setString(1, NomePackage);
			String pathnamePackage = pathPackage + "\\" + NomePackage;
			Package.setString(2, pathnamePackage);
			Package.setString(3, NomeProgetto);
			Package.executeQuery();
			return true;
		} catch (SQLException sq) {
			System.out.println("errore creazione package");
			sq.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean CreaClasse(String NomeProgetto, String NomeClasse, String Metodo, String NomePackage) {
		PreparedStatement classe;
		ResultSet pathapckage;
		PreparedStatement utentes;
		ResultSet cu;
		try {
			pathapckage = PathnamePackage(NomePackage);
			classe = connection.prepareStatement("INSERT INTO CLASSI(PATHNAME,NOMECLASSE,METODO,IDPACKAGE,IDCLASSE)"
					+ "VALUES(:1,:2,:3,:4,null)");
			// String PathnameClasse = PathnameProgetto + "\\" + NomePackage;
			while (pathapckage.next())
				classe.setString(1, pathapckage.getString(1));
			classe.setString(2, NomeClasse + ".java");
			classe.setString(3, Metodo);
			ResultSet indice = indicePackage(NomePackage);
			while (indice.next())
				classe.setInt(4, indice.getInt(1));
			classe.executeQuery();
			utentes = connection
					.prepareStatement("select  pa.idutente from utente u join partecipa pa on u.idutente=pa.idutente"
							+ " where pa.nomeprogetto=:1");
			utentes.setString(1, NomeProgetto);
			cu = utentes.executeQuery();
			while (cu.next()) {
				UtenteAccesso(cu.getInt(1), NomeClasse);
			}
			return true;
		} catch (SQLException sq) {
			System.out.println("errore creazione classe");
			sq.printStackTrace();
			return false;
		}

	}

	@Override
	public void Collaboratore(int collaboratore, String NomeProgetto) {
		PreparedStatement coll;
		try {
			coll = connection.prepareStatement("INSERT INTO PARTECIPA(IDUTENTE,NOMEPROGETTO)" + "VALUES(:1,:2)");
			coll.setInt(1, collaboratore);
			coll.setString(2, NomeProgetto);
			coll.executeUpdate();

		} catch (SQLException sq) {
			sq.printStackTrace();
		}

	}

	@Override
	public void UtenteAccesso(int utente, String NomeClasse) {
		PreparedStatement utea;
		PreparedStatement idc;
		ResultSet rs;
		try {
			utea = connection.prepareStatement(
					"INSERT INTO ACCEDE(IDUTENTE,DATAAPERTURA,DATACHIUSURA,IDCLASSE)" + "VALUES(:1,:2,:3,:4)");
			idc = connection.prepareStatement("select c.idclasse from classi c " + "where c.nomeclasse=:1");
			idc.setString(1, NomeClasse + ".java");
			rs = idc.executeQuery();
			utea.setInt(1, utente);

			utea.setDate(2, getCurrentDate());
			utea.setDate(3, null);
			while (rs.next())
				utea.setInt(4, rs.getInt(1));
			utea.executeUpdate();

		} catch (SQLException sq) {
			sq.printStackTrace();
		}

	}

	private static java.sql.Date getCurrentDate() {
		java.util.Date today = new java.util.Date();
		return new java.sql.Date(today.getTime());
	}

	@Override
	public ResultSet SelezionaProgetto(String utente) {
		ResultSet rs;
		PreparedStatement coll;
		try {

			coll = connection.prepareStatement(
					" SELECT P.NOMEPROGETTO FROM UTENTE U JOIN PARTECIPA P ON U.IDUTENTE=P.IDUTENTE join progetto pr on pr.nomeprogetto=p.nomeprogetto"
							+ " WHERE  U.NOME ||' '|| U.COGNOME=:1 and pr.datachiusura is null");
			coll.setString(1, utente);
			rs = coll.executeQuery();

			// hasNext=rs.next();
			// if(!hasNext) {
			// JOptionPane.showMessageDialog(new JFrame(), "Il DataBase è vuoto!", "DataBase
			// Vuoto", JOptionPane.ERROR_MESSAGE);
			// }else {
			return rs;// }

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ResultSet indicePackage(String NomeProgetto) {
		PreparedStatement indi;
		try {
			indi = connection.prepareStatement("SELECT IDPACKAGE FROM PACKAGE WHERE NOMEPACKAGE=:1");
			indi.setString(1, NomeProgetto);
			return indi.executeQuery();

		} catch (SQLException sq) {
			sq.printStackTrace();
		}

		return null;
	}

	@Override
	public ResultSet IndiceOnlyPackage(String utente, String Progetto, String Package) {
		PreparedStatement onlpack;
		ResultSet rs;
		try {
			onlpack = connection.prepareStatement(
					"SELECT pac.idpackage FROM UTENTE U JOIN PARTECIPA P ON u.idutente=p.idutente  JOIN PROGETTO PRO ON p.nomeprogetto=pro.nomeprogetto JOIN  PACKAGE PAC ON pac.nomeprogetto=pro.nomeprogetto"
							+ " where u.NOME ||' '|| u.COGNOME=:1 and pro.nomeprogetto=:2  and pac.nomepackage=:3");
			onlpack.setString(1, utente);
			onlpack.setString(2, Progetto);
			onlpack.setString(3, Package);
			rs = onlpack.executeQuery();
			return rs;
		} catch (SQLException sq) {
			System.out.println("errore indiceonlypackage");
			sq.printStackTrace();
		}

		return null;
	}

	@Override
	public ResultSet SelezionePackage(String utente, String Progetto) {
		PreparedStatement spackage;
		ResultSet rs;
		try {
			spackage = connection.prepareStatement(
					"SELECT pac.nomepackage FROM UTENTE U JOIN PARTECIPA P ON u.idutente=p.idutente  JOIN PROGETTO PRO ON p.nomeprogetto=pro.nomeprogetto JOIN  PACKAGE PAC ON pac.nomeprogetto=pro.nomeprogetto"
							+ " where  u.NOME ||' '|| u.COGNOME=:1 and pro.nomeprogetto=:2 ORDER BY pac.idpackage");
			spackage.setString(1, utente);
			spackage.setString(2, Progetto);
			rs = spackage.executeQuery();
			return rs;

		} catch (SQLException sq) {

			sq.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean InserimentoSottoPackage(String utente, String Progetto, String Package, String sottoPackage) {
		PreparedStatement insPack;
		PreparedStatement pathpackage;
		ResultSet pathnamePackagePrinciaple;
		ResultSet IndicePackagePrincipale;
		ResultSet indiceSottoPackage;
		boolean b = false;
		try {
			pathpackage = connection.prepareStatement("SELECT pac.pathname\r\n"
					+ "FROM UTENTE U JOIN PARTECIPA P ON u.idutente=p.idutente  JOIN PROGETTO PRO ON p.nomeprogetto=pro.nomeprogetto JOIN  PACKAGE PAC ON pac.nomeprogetto=pro.nomeprogetto"
					+ "   where U.NOME ||' '|| U.COGNOME=:1 and pro.nomeprogetto=:2 and pac.nomepackage=:3");
			pathpackage.setString(1, utente);
			pathpackage.setString(2, Progetto);
			pathpackage.setString(3, Package);
			pathnamePackagePrinciaple = pathpackage.executeQuery();
			while (pathnamePackagePrinciaple.next()) {
				if (CreaPackage(Progetto, sottoPackage, pathnamePackagePrinciaple.getString(1))) {
					IndicePackagePrincipale = IndiceOnlyPackage(utente, Progetto, Package);
					indiceSottoPackage = IndiceOnlyPackage(utente, Progetto, sottoPackage);
					insPack = connection
							.prepareStatement("INSERT INTO SOTTOPACKAGE(IDPACKAGE,SOTTOPACKAGE)" + "VALUES(:1,:2)");
					while (IndicePackagePrincipale.next() && indiceSottoPackage.next()) {
						insPack.setInt(1, IndicePackagePrincipale.getInt(1));
						insPack.setInt(2, indiceSottoPackage.getInt(1));
						insPack.executeUpdate();
					}
					b = true;
					return true;
				} else {
					b = false;
					return false;
				}

			}
			return b;

		} catch (SQLException sq) {
			System.out.println("Inseerimento sottoPackage");
			sq.printStackTrace();
			return false;
		}

	}

	@Override
	public ResultSet PathnamePackage(String Package) {
		PreparedStatement path;
		ResultSet rs;
		try {
			path = connection.prepareStatement("Select p.pathname from package p " + "where p.nomepackage=:1");
			path.setString(1, Package);
			rs = path.executeQuery();
			return rs;

		} catch (SQLException sq) {
			sq.printStackTrace();
			System.out.println("errore pathpackage");
		}
		return null;
	}

	@Override
	public ResultSet OnlyNameClasse(String Package) {
		PreparedStatement classenam;
		ResultSet rs;
		try {
			classenam = connection.prepareStatement(
					"select c.nomeclasse from classi c join package on package.idpackage = c.idpackage"
							+ " where package.nomepackage=:1");
			classenam.setString(1, Package);
			rs = classenam.executeQuery();
			return rs;
		} catch (SQLException sq) {
			sq.printStackTrace();
		}
		return null;
	}

	@Override
	public ResultSet IndiceOnlyClasse(String Classe) {
		PreparedStatement cl;
		ResultSet rs;
		try {
			cl = connection.prepareStatement("Select c.idclasse from classi c " + " where c.nomeclasse=:1");
			cl.setString(1, Classe);
			rs = cl.executeQuery();
			return rs;

		} catch (SQLException sq) {
			sq.printStackTrace();
		}
		return null;
	}

	@Override
	public void SottoMetodo(String nomeClasse, String sottoMetodo) {
		PreparedStatement insM;
		ResultSet rs;
		try {
			rs = IndiceOnlyClasse(nomeClasse);
			insM = connection.prepareStatement("INSERT INTO SOTTOMETODO " + "VALUES(:1,:2)");
			while (rs.next())
				insM.setInt(1, rs.getInt(1));
			insM.setString(2, sottoMetodo);
			insM.executeUpdate();
		} catch (SQLException sq) {
			sq.printStackTrace();
		}

	}

	@Override
	public String DataAperturaClasse(String NomeClasse) {
		PreparedStatement pr;
		String data;
		ResultSet rs;
		// ResultSet idc;
		try {
			// idc=IndiceOnlyClasse(NomeClasse);
			pr = connection.prepareStatement(
					"SELECT acc.dataapertura  FROM ACCEDE ACC JOIN CLASSI CL ON acc.idclasse=cl.idclasse\r\n"
							+ "    WHERE cl.nomeclasse=:1");
			pr.setString(1, NomeClasse);
			rs = pr.executeQuery();
			while (rs.next()) {
				data = rs.getString(1);
				return data;
			}

		} catch (SQLException sq) {
			sq.printStackTrace();
		}
		return null;
	}

	@Override
	public ResultSet StampaClasse(String Utente, String Progetto) {
		PreparedStatement stampaC;
		ResultSet rs;
		try {
			stampaC = connection.prepareStatement(
					"SELECT cla.nomeclasse FROM UTENTE U JOIN PARTECIPA PAR ON u.idutente=par.idutente JOIN \r\n"
							+ "PROGETTO PROG ON PAR.NOMEPROGETTO=prog.nomeprogetto JOIN PACKAGE PACK on prog.nomeprogetto=pack.nomeprogetto FULL OUTER JOIN SOTTOPACKAGE STPACK ON pack.idpackage=stpack.idpackage JOIN CLASSI \r\n"
							+ "CLA ON cla.idpackage=pack.idpackage join accede acc on acc.idclasse=cla.idclasse and acc.idutente=u.idutente\r\n"
							+ "WHERE u.NOME ||' '|| u.COGNOME=:1 and prog.nomeprogetto=:2 and acc.datachiusura is null");
			stampaC.setString(1, Utente);
			stampaC.setString(2, Progetto);
			rs = stampaC.executeQuery();
			return rs;

		} catch (SQLException sq) {
			sq.printStackTrace();
		}
		return null;
	}

	@Override
	public void SettaDataChiusura(java.sql.Date DataChiusura, String nomeClasse) {
		PreparedStatement sq;
		ResultSet rs;
		try {
			rs = IndiceOnlyClasse(nomeClasse);
			sq = connection.prepareStatement("UPDATE ACCEDE SET DATACHIUSURA =:1" + " WHERE idclasse=:2");

			sq.setDate(1, DataChiusura);
			while (rs.next())
				sq.setInt(2, rs.getInt(1));

			sq.executeUpdate();

		} catch (SQLException s) {
			s.printStackTrace();
		}

	}

	@Override
	public String DataAperturaProgetto(String nomeProgetto) {
		PreparedStatement pr;
		String data;
		ResultSet rs;
		// ResultSet idc;
		try {
			// idc=IndiceOnlyClasse(NomeClasse);
			pr = connection
					.prepareStatement("Select pro.dataapertura from progetto pro " + " where  pro.nomeprogetto=:1");
			pr.setString(1, nomeProgetto);
			rs = pr.executeQuery();
			while (rs.next()) {
				data = rs.getString(1);
				return data;
			}

		} catch (SQLException sq) {
			sq.printStackTrace();
		}
		return null;
	}

	@Override
	public void settaDatachiusuraProgetto(String nomeProgetto, Date dataChiusura) {
		PreparedStatement sq;
		try {

			sq = connection.prepareStatement("UPDATE viewprogetto SET DATACHIUSURA =:1" + " WHERE nomeprogetto=:2");
			sq.setDate(1, dataChiusura);
			sq.setString(2, nomeProgetto);

			sq.executeUpdate();

		} catch (SQLException s) {
			s.printStackTrace();
		}

	}

	@Override
	public ResultSet StampaTuttoProgetto() {
		PreparedStatement st;
		ResultSet rs;
		try {
			st = connection.prepareStatement(
					"  select  u.idutente, u.nome||' '||u.cognome,pro.PATHNAME, pro.nomeprogetto,pro.dataapertura,pro.datachiusura,rs.versione from utente u join partecipa p on u.idutente=p.idutente join progetto pro on p.nomeprogetto=pro.nomeprogetto join release rs on rs.nomeprogetto=pro.nomeprogetto  ORDER BY PRO.NOMEPROGETTO,U.IDUTENTE,RS.VERSIONE ");
			rs = st.executeQuery();
			return rs;
		} catch (SQLException sql) {
			sql.printStackTrace();
		}
		return null;
	}

	@Override
	public ResultSet StampaVClasseTutti() {
		PreparedStatement st;
		ResultSet rs;
		try {
			st = connection.prepareStatement(
					"select  Distinct pro.nomeprogetto,pc.pathname,pc.nomepackage,cl.pathname,cl.nomeclasse,cl.metodo,st.sottometodo,ac.dataapertura,ac.datachiusura from progetto pro join package pc on pro.nomeprogetto=pc.nomeprogetto join classi cl on cl.idpackage=pc.idpackage full outer join sottometodo st on cl.idclasse=st.idclasse join accede ac on ac.idclasse=cl.idclasse ORDER by pro.nomeprogetto ");
			rs = st.executeQuery();
			return rs;
		} catch (SQLException sql) {
			sql.printStackTrace();
		}
		return null;
	}

	@Override
	public ResultSet StampaVersioneProgetto(String Utente, String Progetto) {
		PreparedStatement st;
		int utente;
		ResultSet rs;
		try {
			utente = IndiceNome(Utente);
			st = connection.prepareStatement(
					"Select rs.versione from utente u join partecipa p on u.idutente=p.idutente join progetto pro on p.nomeprogetto=pro.nomeprogetto join release rs on pro.nomeprogetto=rs.nomeprogetto "
							+ " where u.idutente=:1 and pro.nomeprogetto=:2 and pro.datachiusura is null ORDER BY RS.VERSIONE ");
			st.setInt(1, utente);
			st.setString(2, Progetto);
			rs = st.executeQuery();
			return rs;
		} catch (SQLException sql) {
			sql.printStackTrace();
		}
		return null;
	}

	@Override
	public ResultSet StampaStatoVersioneProgetto(String Progetto, String Versione) {
		PreparedStatement st;
		ResultSet rs;
		try {
			st = connection.prepareStatement(
					"Select rs.status  from  release rs " + "where rs.nomeprogetto=:1 and rs.versione=:2");
			st.setString(1, Progetto);
			st.setString(2, Versione);
			rs = st.executeQuery();
			return rs;

		} catch (SQLException sql) {
			sql.printStackTrace();
		}
		return null;
	}

	@Override
	public void creaTest(String Descrizione, String Risultato, String Utente, String Progetto, String Versione,
			String Stato, String pathname) {
		PreparedStatement ins;
		PreparedStatement insUtenteAvvia;
		PreparedStatement insTestano;
		PreparedStatement insRelease;
		try {
			if (Risultato.equals("Fallito") || Stato.equals("Eliminato")) {
				System.out.println(Risultato + " " + Stato);
				insRelease = connection
						.prepareStatement("UPDATE RELEASE SET STATUS=:1 " + " WHERE NOMEPROGETTO=:2 AND VERSIONE=:3");
				insRelease.setString(1, Stato.toUpperCase());
				insRelease.setString(2, Progetto);
				insRelease.setString(3, Versione);
				insRelease.executeUpdate();

				ins = connection
						.prepareStatement("INSERT INTO TESTRELEASE(IDTEST,DATATEST,DESCRIZIONE,RISULTATO,PATHNAME) "
								+ "VALUES(null,:1,:2,:3,:4)");
				ins.setDate(1, getCurrentDate());
				ins.setString(2, Descrizione);
				ins.setString(3, Risultato.toUpperCase());
				ins.setString(4, pathname);
				ins.executeUpdate();

				int indiceU = IndiceNome(Utente);
				insUtenteAvvia = connection
						.prepareStatement("INSERT INTO AVVIA(IDUTENTE,IDTEST) " + "VALUES(:1,IDTESTINC.currval)");
				insUtenteAvvia.setInt(1, indiceU);
				insUtenteAvvia.executeUpdate();

				int indiceR = indiceRelease(Progetto, Versione);
				insTestano = connection
						.prepareStatement("INSERT INTO TESTANO(IDRELEASE,IDTEST)" + " VALUES(:1,IDTESTINC.currval)");
				insTestano.setInt(1, indiceR);
				insTestano.executeUpdate();
			} else {
				ins = connection
						.prepareStatement("INSERT INTO TESTRELEASE(IDTEST,DATATEST,DESCRIZIONE,RISULTATO,PATHNAME) "
								+ "VALUES(null,:1,:2,:3,:4)");
				ins.setDate(1, getCurrentDate());
				ins.setString(2, Descrizione);
				ins.setString(3, Risultato.toUpperCase());
				ins.setString(4, pathname);
				ins.executeUpdate();

				int indiceU = IndiceNome(Utente);
				insUtenteAvvia = connection
						.prepareStatement("INSERT INTO AVVIA(IDUTENTE,IDTEST) " + "VALUES(:1,IDTESTINC.currval)");
				insUtenteAvvia.setInt(1, indiceU);
				insUtenteAvvia.executeUpdate();

				int indiceR = indiceRelease(Progetto, Versione);
				insTestano = connection
						.prepareStatement("INSERT INTO TESTANO(IDRELEASE,IDTEST)" + " VALUES(:1,IDTESTINC.currval)");
				insTestano.setInt(1, indiceR);
				insTestano.executeUpdate();

				int versione = Integer.parseInt(Versione);
				versione++;
				insRelease = connection.prepareStatement("INSERT INTO RELEASE(STATUS,NOMEPROGETTO,IDRELEASE,VERSIONE)"
						+ "VALUES(:1,:2,null,:3) ");
				insRelease.setString(1, Stato.toUpperCase());
				insRelease.setString(2, Progetto);
				insRelease.setInt(3, versione);
				insRelease.executeUpdate();
			}

		} catch (SQLException sq) {
			sq.printStackTrace();
		}

	}

	@Override
	public int indiceRelease(String Progetto, String Versione) {
		PreparedStatement ins;
		ResultSet rs;
		try {
			ins = connection.prepareStatement(
					"SELECT RS.IDRELEASE FROM RELEASE RS " + "WHERE RS.NOMEPROGETTO=:1 AND RS.VERSIONE=:2");
			ins.setString(1, Progetto);
			ins.setString(2, Versione);
			rs = ins.executeQuery();
			while (rs.next()) {
				int ind = rs.getInt(1);
				return ind;
			}
		} catch (SQLException sql) {
			sql.printStackTrace();
		}
		return 0;

	}

	@Override
	public ResultSet StampaTestEffettuati() {
		PreparedStatement pr;
		ResultSet rs;
		try {
			pr = connection.prepareStatement("SELECT DISTINCT u.nome||' '||u.cognome,testr.pathname,testr.datatest,testr.descrizione,pro.nomeprogetto,testr.risultato FROM UTENTE U JOIN PARTECIPA P ON u.idutente=p.idutente JOIN PROGETTO PRO ON pro.nomeprogetto=p.nomeprogetto JOIN AVVIA A ON u.idutente=a.idutente JOIN testrelease TESTR ON testr.idtest=a.idtest JOIN TESTANO TESTA ON testr.idtest=testa.idtest JOIN RELEASE RS ON rs.idrelease=testa.idrelease AND rs.nomeprogetto=pro.nomeprogetto ");
			rs = pr.executeQuery();
			return rs;
		} catch (SQLException sql) {
			sql.printStackTrace();
		}
		return null;
	}

	@Override
	public ResultSet StampaRicercaTestTramiteNome(String nome) {
		PreparedStatement pr;
		ResultSet rs;
		try {
			pr = connection.prepareStatement(
					"SELECT DISTINCT u.nome||' '||u.cognome,testr.pathname,testr.datatest,testr.descrizione,pro.nomeprogetto,testr.risultato FROM UTENTE U JOIN PARTECIPA P ON u.idutente=p.idutente JOIN PROGETTO PRO ON pro.nomeprogetto=p.nomeprogetto JOIN AVVIA A ON u.idutente=a.idutente JOIN testrelease TESTR ON testr.idtest=a.idtest JOIN TESTANO TESTA ON testr.idtest=testa.idtest JOIN RELEASE RS ON rs.idrelease=testa.idrelease AND rs.nomeprogetto=pro.nomeprogetto " + " where u.nome=:1");
			pr.setString(1, nome);
			rs = pr.executeQuery();
			return rs;
		} catch (SQLException sql) {
			sql.printStackTrace();
		}

		return null;
	}

	@Override
	public ResultSet StampaRicercaTestTramiteProgetto(String Progetto) {
		PreparedStatement pr;
		ResultSet rs;
		try {
			pr = connection.prepareStatement(
					"SELECT DISTINCT u.nome||' '||u.cognome,testr.pathname,testr.datatest,testr.descrizione,pro.nomeprogetto,testr.risultato FROM UTENTE U JOIN PARTECIPA P ON u.idutente=p.idutente JOIN PROGETTO PRO ON pro.nomeprogetto=p.nomeprogetto JOIN AVVIA A ON u.idutente=a.idutente JOIN testrelease TESTR ON testr.idtest=a.idtest JOIN TESTANO TESTA ON testr.idtest=testa.idtest JOIN RELEASE RS ON rs.idrelease=testa.idrelease AND rs.nomeprogetto=pro.nomeprogetto"+ " where p.nomeprogetto=:1");
			pr.setString(1, Progetto);
			rs = pr.executeQuery();
			return rs;
		} catch (SQLException sql) {
			sql.printStackTrace();
		}
		return null;
	}

	@Override
	public ResultSet StampaRicercaTestTramiteRisultato(String risultato) {
		PreparedStatement pr;
		ResultSet rs;
		try {
			pr = connection.prepareStatement(
					"SELECT DISTINCT u.nome||' '||u.cognome,testr.pathname,testr.datatest,testr.descrizione,pro.nomeprogetto,testr.risultato FROM UTENTE U JOIN PARTECIPA P ON u.idutente=p.idutente JOIN PROGETTO PRO ON pro.nomeprogetto=p.nomeprogetto JOIN AVVIA A ON u.idutente=a.idutente JOIN testrelease TESTR ON testr.idtest=a.idtest JOIN TESTANO TESTA ON testr.idtest=testa.idtest JOIN RELEASE RS ON rs.idrelease=testa.idrelease AND rs.nomeprogetto=pro.nomeprogetto"+
							 " where testr.risultato=:1");
			pr.setString(1, risultato);
			rs = pr.executeQuery();
			return rs;
		} catch (SQLException sql) {
			sql.printStackTrace();
		}
		return null;
	}

	@Override
	public ResultSet StampaRicercaTestTramiteTutto(String nome, String progetto, String risultato, String Cognome) {
		PreparedStatement pr;
		ResultSet rs;
		try {
			pr = connection.prepareStatement(
					"SELECT DISTINCT u.nome||' '||u.cognome,testr.pathname,testr.datatest,testr.descrizione,pro.nomeprogetto,testr.risultato FROM UTENTE U JOIN PARTECIPA P ON u.idutente=p.idutente JOIN PROGETTO PRO ON pro.nomeprogetto=p.nomeprogetto JOIN AVVIA A ON u.idutente=a.idutente JOIN testrelease TESTR ON testr.idtest=a.idtest JOIN TESTANO TESTA ON testr.idtest=testa.idtest JOIN RELEASE RS ON rs.idrelease=testa.idrelease AND rs.nomeprogetto=pro.nomeprogetto"
							+ " where u.nome=:1 and p.nomeprogetto=:2 and testr.risultato=:3 and u.cognome=:4");
			pr.setString(1, nome);
			pr.setString(2, progetto);
			pr.setString(3, risultato);
			pr.setString(4, Cognome);
			rs = pr.executeQuery();
			return rs;
		} catch (SQLException sql) {
			sql.printStackTrace();
		}
		return null;
	}

	@Override
	public ResultSet StampaRicercaTestTramiteNomeAndProgetto(String nome, String progetto) {
		PreparedStatement pr;
		ResultSet rs;
		try {
			pr = connection.prepareStatement(
					"SELECT DISTINCT u.nome||' '||u.cognome,testr.pathname,testr.datatest,testr.descrizione,pro.nomeprogetto,testr.risultato FROM UTENTE U JOIN PARTECIPA P ON u.idutente=p.idutente JOIN PROGETTO PRO ON pro.nomeprogetto=p.nomeprogetto JOIN AVVIA A ON u.idutente=a.idutente JOIN testrelease TESTR ON testr.idtest=a.idtest JOIN TESTANO TESTA ON testr.idtest=testa.idtest JOIN RELEASE RS ON rs.idrelease=testa.idrelease AND rs.nomeprogetto=pro.nomeprogetto"
							+ " where u.nome=:1 and p.nomeprogetto=:2");
			pr.setString(1, nome);
			pr.setString(2, progetto);
			rs = pr.executeQuery();
			return rs;
		} catch (SQLException sql) {
			sql.printStackTrace();
		}
		return null;
	}

	@Override
	public ResultSet StampaRicercaTestTramiteNomeAndRisultato(String nome, String risultato) {
		PreparedStatement pr;
		ResultSet rs;
		try {
			pr = connection.prepareStatement(
					"SELECT DISTINCT u.nome||' '||u.cognome,testr.pathname,testr.datatest,testr.descrizione,pro.nomeprogetto,testr.risultato FROM UTENTE U JOIN PARTECIPA P ON u.idutente=p.idutente JOIN PROGETTO PRO ON pro.nomeprogetto=p.nomeprogetto JOIN AVVIA A ON u.idutente=a.idutente JOIN testrelease TESTR ON testr.idtest=a.idtest JOIN TESTANO TESTA ON testr.idtest=testa.idtest JOIN RELEASE RS ON rs.idrelease=testa.idrelease AND rs.nomeprogetto=pro.nomeprogetto"+ " where u.nome=:1 and testr.risultato=:2");
			pr.setString(1, nome);
			pr.setString(2, risultato);
			rs = pr.executeQuery();
			return rs;
		} catch (SQLException sql) {
			sql.printStackTrace();
		}
		return null;
	}

	@Override
	public ResultSet StampaRicercaTestTramiteProgettoAndRisultato(String progetto, String risultato) {
		PreparedStatement pr;
		ResultSet rs;
		try {
			pr = connection.prepareStatement(
					"SELECT DISTINCT u.nome||' '||u.cognome,testr.pathname,testr.datatest,testr.descrizione,pro.nomeprogetto,testr.risultato FROM UTENTE U JOIN PARTECIPA P ON u.idutente=p.idutente JOIN PROGETTO PRO ON pro.nomeprogetto=p.nomeprogetto JOIN AVVIA A ON u.idutente=a.idutente JOIN testrelease TESTR ON testr.idtest=a.idtest JOIN TESTANO TESTA ON testr.idtest=testa.idtest JOIN RELEASE RS ON rs.idrelease=testa.idrelease AND rs.nomeprogetto=pro.nomeprogetto"+ " where p.nomeprogetto=:1 and testr.risultato=:2");
			pr.setString(1, progetto);
			pr.setString(2, risultato);
			rs = pr.executeQuery();
			return rs;
		} catch (SQLException sql) {
			sql.printStackTrace();
		}
		return null;
	}

	@Override
	public ResultSet StampaRicercaVProgettoTramiteRelease(String release) {
		PreparedStatement st;
		ResultSet rs;
		try {
			st = connection.prepareStatement(
					"  select  u.idutente, u.NOME ||' '|| u.COGNOME,pro.pathname, pro.nomeprogetto,pro.dataapertura,pro.datachiusura,rs.versione from utente u join partecipa p on u.idutente=p.idutente join progetto pro on p.nomeprogetto=pro.nomeprogetto join release rs on rs.nomeprogetto=pro.nomeprogetto where rs.versione=:1 ORDER BY PRO.NOMEPROGETTO,U.IDUTENTE,RS.VERSIONE ");
			st.setString(1, release);
			rs = st.executeQuery();
			return rs;
		} catch (SQLException sql) {
			sql.printStackTrace();
		}
		return null;
	}

	@Override
	public ResultSet StampaRicercaVProgettoTramiteNomeAndRelease(String nome, String release) {
		PreparedStatement st;
		ResultSet rs;
		try {
			st = connection.prepareStatement(
					"  select  u.idutente, u.NOME ||' '|| u.COGNOME,pro.pathname, pro.nomeprogetto,pro.dataapertura,pro.datachiusura,rs.versione from utente u join partecipa p on u.idutente=p.idutente join progetto pro on p.nomeprogetto=pro.nomeprogetto join release rs on rs.nomeprogetto=pro.nomeprogetto where u.nome=:1 and rs.versione=:2 ORDER BY PRO.NOMEPROGETTO,U.IDUTENTE,RS.VERSIONE ");
			st.setString(1, nome);
			st.setString(2, release);
			rs = st.executeQuery();
			return rs;
		} catch (SQLException sql) {
			sql.printStackTrace();
		}
		return null;
	}

	@Override
	public ResultSet StampaRicercaVProgettoTramiteProgettoAndRelease(String progetto, String release) {
		PreparedStatement st;
		ResultSet rs;
		try {
			st = connection.prepareStatement(
					"  select  u.idutente,u.NOME ||' '|| u.COGNOME,pro.pathname, pro.nomeprogetto,pro.dataapertura,pro.datachiusura,rs.versione from utente u join partecipa p on u.idutente=p.idutente join progetto pro on p.nomeprogetto=pro.nomeprogetto join release rs on rs.nomeprogetto=pro.nomeprogetto where pro.nomeprogetto=:1 and rs.versione=:2 ORDER BY PRO.NOMEPROGETTO,U.IDUTENTE,RS.VERSIONE ");
			st.setString(1, progetto);
			st.setString(2, release);
			rs = st.executeQuery();
			return rs;
		} catch (SQLException sql) {
			sql.printStackTrace();
		}
		return null;
	}

	@Override
	public ResultSet StampaRicercaVProgettoTramiteNome(String nome) {
		PreparedStatement st;
		ResultSet rs;
		try {
			st = connection.prepareStatement(
					"  select  u.idutente, u.NOME ||' '|| u.COGNOME, pro.pathname,pro.nomeprogetto,pro.dataapertura,pro.datachiusura,rs.versione from utente u join partecipa p on u.idutente=p.idutente join progetto pro on p.nomeprogetto=pro.nomeprogetto join release rs on rs.nomeprogetto=pro.nomeprogetto where u.nome=:1 ORDER BY PRO.NOMEPROGETTO,U.IDUTENTE,RS.VERSIONE ");
			st.setString(1, nome);
			rs = st.executeQuery();
			return rs;
		} catch (SQLException sql) {
			sql.printStackTrace();
		}
		return null;
	}

	@Override
	public ResultSet StampaRicercaVProgettoTramiteProgetto(String Progetto) {
		PreparedStatement st;
		ResultSet rs;
		try {
			st = connection.prepareStatement(
					"  select  u.idutente, u.NOME ||' '|| u.COGNOME,pro.pathname, pro.nomeprogetto,pro.dataapertura,pro.datachiusura,rs.versione from utente u join partecipa p on u.idutente=p.idutente join progetto pro on p.nomeprogetto=pro.nomeprogetto join release rs on rs.nomeprogetto=pro.nomeprogetto where pro.nomeprogetto=:1 ORDER BY PRO.NOMEPROGETTO,U.IDUTENTE,RS.VERSIONE ");
			st.setString(1, Progetto);
			rs = st.executeQuery();
			return rs;
		} catch (SQLException sql) {
			sql.printStackTrace();
		}
		return null;
	}

	@Override
	public ResultSet StampaRicercaVProgettoTramiteNomeAndProgetto(String nome, String progetto) {
		PreparedStatement st;
		ResultSet rs;
		try {
			st = connection.prepareStatement(
					"  select  u.idutente, u.NOME ||' '|| u.COGNOME,pro.pathname, pro.nomeprogetto,pro.dataapertura,pro.datachiusura,rs.versione from utente u join partecipa p on u.idutente=p.idutente join progetto pro on p.nomeprogetto=pro.nomeprogetto join release rs on rs.nomeprogetto=pro.nomeprogetto where u.nome=:1 and pro.nomeprogetto=:2 ORDER BY PRO.NOMEPROGETTO,U.IDUTENTE,RS.VERSIONE ");
			st.setString(1, nome);
			st.setString(2, progetto);
			rs = st.executeQuery();
			return rs;
		} catch (SQLException sql) {
			sql.printStackTrace();
		}
		return null;
	}

	@Override
	public ResultSet StampaRicercaVProgettoTramiteTutto(String nome, String progetto, String release) {
		PreparedStatement st;
		ResultSet rs;
		try {
			st = connection.prepareStatement(
					"  select  u.idutente,u.NOME ||' '|| u.COGNOME,pro.pathname, pro.nomeprogetto,pro.dataapertura,pro.datachiusura,rs.versione from utente u join partecipa p on u.idutente=p.idutente join progetto pro on p.nomeprogetto=pro.nomeprogetto join release rs on rs.nomeprogetto=pro.nomeprogetto where u.nome=:1 and pro.nomeprogetto=:2 and  rs.versione=:3 ORDER BY PRO.NOMEPROGETTO,U.IDUTENTE,RS.VERSIONE ");
			st.setString(1, nome);
			st.setString(2, progetto);
			st.setString(3, release);
			rs = st.executeQuery();
			return rs;
		} catch (SQLException sql) {
			sql.printStackTrace();
		}
		return null;
	}

	@Override
	public void Partecipa(String nome, String progetto) {
		int indiceNome;
		PreparedStatement ins;
		ResultSet rs;
		PreparedStatement acc;
		try {
			indiceNome = IndiceNome(nome);
			// collabora e metodo accesso in classe
			Collaboratore(indiceNome, progetto);
			ins = connection.prepareStatement(
					"Select Distinct cl.idclasse,ac.dataapertura from progetto prog join package pack on prog.nomeprogetto=pack.nomeprogetto join classi cl on pack.idpackage=cl.idpackage join accede ac on cl.idclasse = ac.idclasse where prog.nomeprogetto=:1 and ac.datachiusura is null");
			ins.setString(1, progetto);
			rs = ins.executeQuery();
			acc = connection.prepareStatement(
					"INSERT INTO ACCEDE(IDUTENTE,DATAAPERTURA,DATACHIUSURA,IDCLASSE) " + "VALUES(:1,:2,null,:3)");
			while (rs.next()) {
				acc.setInt(1, indiceNome);
				acc.setDate(2, rs.getDate(2));
				acc.setInt(3, rs.getInt(1));

			}
			acc.executeQuery();
		} catch (SQLException sql) {
			sql.printStackTrace();
		}
	}

	@Override
	public ResultSet stampaProgetti() {
		PreparedStatement ins;
		ResultSet rs;
		try {
			ins = connection
					.prepareStatement("Select pro.nomeprogetto from progetto pro where pro.datachiusura is null");
			rs = ins.executeQuery();
			return rs;
		} catch (SQLException sql) {
			sql.printStackTrace();
		}
		return null;
	}

	@Override
	public ResultSet StampaRicercaVClasseTramiteProgetto(String progetto) {
		PreparedStatement st;
		ResultSet rs;
		try {
			st = connection.prepareStatement(
					"select  distinct pro.nomeprogetto,pc.pathname,pc.nomepackage,cl.pathname,cl.nomeclasse,cl.metodo,st.sottometodo,ac.dataapertura,ac.datachiusura from progetto pro join package pc on pro.nomeprogetto=pc.nomeprogetto join classi cl on cl.idpackage=pc.idpackage full outer join sottometodo st on cl.idclasse=st.idclasse join accede ac on ac.idclasse=cl.idclasse  where pro.nomeprogetto=:1 ORDER by pro.nomeprogetto ");
			st.setString(1, progetto);
			rs = st.executeQuery();
			return rs;
		} catch (SQLException sql) {
			sql.printStackTrace();
		}
		return null;
	}

	@Override
	public ResultSet StampaRicercaVClasseTramitePackage(String Package) {
		PreparedStatement st;
		ResultSet rs;
		try {
			st = connection.prepareStatement(
					"select  Distinct pro.nomeprogetto,pc.pathname,pc.nomepackage,cl.pathname,cl.nomeclasse,cl.metodo,st.sottometodo,ac.dataapertura,ac.datachiusura from progetto pro join package pc on pro.nomeprogetto=pc.nomeprogetto join classi cl on cl.idpackage=pc.idpackage full outer join sottometodo st on cl.idclasse=st.idclasse join accede ac on ac.idclasse=cl.idclasse where pc.nomepackage=:1 ORDER by pro.nomeprogetto ");
			st.setString(1, Package);
			rs = st.executeQuery();
			return rs;
		} catch (SQLException sql) {
			sql.printStackTrace();
		}
		return null;
	}

	@Override
	public ResultSet StampaRicercaVClasseTramiteClasse(String classe) {
		PreparedStatement st;
		ResultSet rs;
		try {
			st = connection.prepareStatement(
					"select  Distinct pro.nomeprogetto,pc.pathname,pc.nomepackage,cl.pathname,cl.nomeclasse,cl.metodo,st.sottometodo,ac.dataapertura,ac.datachiusura from progetto pro join package pc on pro.nomeprogetto=pc.nomeprogetto join classi cl on cl.idpackage=pc.idpackage full outer join sottometodo st on cl.idclasse=st.idclasse join accede ac on ac.idclasse=cl.idclasse where cl.nomeclasse=:1 ORDER by pro.nomeprogetto ");
			st.setString(1, classe);
			rs = st.executeQuery();
			return rs;
		} catch (SQLException sql) {
			sql.printStackTrace();
		}
		return null;
	}

	@Override
	public ResultSet StampaRicercaVClasseTramiteTutto(String progetto, String Package, String classe) {
		PreparedStatement st;
		ResultSet rs;
		try {
			st = connection.prepareStatement(
					"select  Distinct pro.nomeprogetto,pc.pathname,pc.nomepackage,cl.pathname,cl.nomeclasse,cl.metodo,st.sottometodo,ac.dataapertura,ac.datachiusura from progetto pro join package pc on pro.nomeprogetto=pc.nomeprogetto join classi cl on cl.idpackage=pc.idpackage full outer join sottometodo st on cl.idclasse=st.idclasse join accede ac on ac.idclasse=cl.idclasse where pro.nomeprogramma=:1 and pc.nomepackage=:2 cl.nomeclasse=:3 ORDER by pro.nomeprogetto ");
			st.setString(1, progetto);
			st.setString(2, Package);
			st.setString(3, classe);
			rs = st.executeQuery();
			return rs;
		} catch (SQLException sql) {
			sql.printStackTrace();
		}
		return null;
	}

	@Override
	public ResultSet StampaRicercaVClasseTramiteProgettoAndPackage(String progetto, String Package) {
		PreparedStatement st;
		ResultSet rs;
		try {
			st = connection.prepareStatement(
					"select  Distinct pro.nomeprogetto,pc.pathname,pc.nomepackage,cl.pathname,cl.nomeclasse,cl.metodo,st.sottometodo,ac.dataapertura,ac.datachiusura from progetto pro join package pc on pro.nomeprogetto=pc.nomeprogetto join classi cl on cl.idpackage=pc.idpackage full outer join sottometodo st on cl.idclasse=st.idclasse join accede ac on ac.idclasse=cl.idclasse where  pro.nomeprogetto=:1 and pc.nomepackage=:2 ORDER by pro.nomeprogetto ");
			st.setString(1, progetto);
			st.setString(2, Package);
			rs = st.executeQuery();
			return rs;
		} catch (SQLException sql) {
			sql.printStackTrace();
		}
		return null;
	}

	@Override
	public ResultSet StampaRicercaVClasseTramiteProgettoAndClasse(String progetto, String classe) {
		PreparedStatement st;
		ResultSet rs;
		try {
			st = connection.prepareStatement(
					"select  Distinct pro.nomeprogetto,pc.pathname,pc.nomepackage,cl.pathname,cl.nomeclasse,cl.metodo,st.sottometodo,ac.dataapertura,ac.datachiusura from progetto pro join package pc on pro.nomeprogetto=pc.nomeprogetto join classi cl on cl.idpackage=pc.idpackage full outer join sottometodo st on cl.idclasse=st.idclasse join accede ac on ac.idclasse=cl.idclasse where  pro.nomeprogetto=:1 and cl.nomeclasse=:2 ORDER by pro.nomeprogetto ");
			st.setString(1, progetto);
			st.setString(2, classe);
			rs = st.executeQuery();
			return rs;
		} catch (SQLException sql) {
			sql.printStackTrace();
		}
		return null;
	}

	@Override
	public ResultSet StampaRicercaTestTramitePackageAndClasse(String Package, String classe) {
		PreparedStatement st;
		ResultSet rs;
		try {
			st = connection.prepareStatement(
					"select  Distinct pro.nomeprogetto,pc.nomepackage,cl.nomeclasse,cl.metodo,st.sottometodo,ac.dataapertura,ac.datachiusura from progetto pro join package pc on pro.nomeprogetto=pc.nomeprogetto join classi cl on cl.idpackage=pc.idpackage full outer join sottometodo st on cl.idclasse=st.idclasse join accede ac on ac.idclasse=cl.idclasse where  pc.nomepackage=:1 and cl.nomeclasse=:2 ORDER by pro.nomeprogetto ");
			st.setString(1, Package);
			st.setString(2, classe);
			rs = st.executeQuery();
			return rs;
		} catch (SQLException sql) {
			sql.printStackTrace();
		}
		return null;
	}

	@Override
	public String SommaUtenti() {
		PreparedStatement conta;
		ResultSet rs;
		try {
			conta = connection.prepareStatement("Select count(U.idutente)from utente u");
			rs = conta.executeQuery();
			while (rs.next()) {
				return rs.getString(1);
			}

		} catch (SQLException sql) {

		}
		return null;
	}

	@Override
	public String SommaProgettiAttivi() {
		PreparedStatement conta;
		ResultSet rs;
		try {
			conta = connection
					.prepareStatement("Select count(p.nomeprogetto)from progetto p where p.datachiusura is null");
			rs = conta.executeQuery();
			while (rs.next()) {
				return rs.getString(1);
			}

		} catch (SQLException sql) {

		}
		return null;
	}

	@Override
	public String SommaProgettiConclusi() {
		PreparedStatement conta;
		ResultSet rs;
		try {
			conta = connection
					.prepareStatement("Select count(p.nomeprogetto)from progetto p where p.datachiusura is not null");
			rs = conta.executeQuery();
			while (rs.next()) {
				return rs.getString(1);
			}

		} catch (SQLException sql) {

		}
		return null;
	}

	@Override
	public ResultSet StampaRicercaVProgettoTramiteCognome(String cognome) {
		PreparedStatement st;
		ResultSet rs;
		try {
			st = connection.prepareStatement(
					"  select  u.idutente,u.NOME ||' '|| u.COGNOME,pro.pathname, pro.nomeprogetto,pro.dataapertura,pro.datachiusura,rs.versione from utente u join partecipa p on u.idutente=p.idutente join progetto pro on p.nomeprogetto=pro.nomeprogetto join release rs on rs.nomeprogetto=pro.nomeprogetto where u.cognome=:1 ORDER BY PRO.NOMEPROGETTO,U.IDUTENTE,RS.VERSIONE ");
			st.setString(1, cognome);

			rs = st.executeQuery();
			return rs;
		} catch (SQLException sql) {
			sql.printStackTrace();
		}
		return null;
	}

	@Override
	public ResultSet StampaRicercaVProgettoTramiteCognomeAndRelease(String cognome, String release) {
		PreparedStatement st;
		ResultSet rs;
		try {
			st = connection.prepareStatement(
					"  select  u.idutente,u.NOME ||' '|| u.COGNOME,pro.pathname, pro.nomeprogetto,pro.dataapertura,pro.datachiusura,rs.versione from utente u join partecipa p on u.idutente=p.idutente join progetto pro on p.nomeprogetto=pro.nomeprogetto join release rs on rs.nomeprogetto=pro.nomeprogetto where u.cognome=:1 and rs.versione=:2 ORDER BY PRO.NOMEPROGETTO,U.IDUTENTE,RS.VERSIONE ");
			st.setString(1, cognome);
			st.setString(2, release);
			rs = st.executeQuery();
			return rs;
		} catch (SQLException sql) {
			sql.printStackTrace();
		}
		return null;
	}

	@Override
	public ResultSet StampaRicercaVProgettoTramiteCognomeAndProgetto(String cognome, String progetto) {
		PreparedStatement st;
		ResultSet rs;
		try {
			st = connection.prepareStatement(
					"  select  u.idutente,u.NOME ||' '|| u.COGNOME,pro.pathname, pro.nomeprogetto,pro.dataapertura,pro.datachiusura,rs.versione from utente u join partecipa p on u.idutente=p.idutente join progetto pro on p.nomeprogetto=pro.nomeprogetto join release rs on rs.nomeprogetto=pro.nomeprogetto where u.cognome=:1 and pro.nomeprogetto=:2 ORDER BY PRO.NOMEPROGETTO,U.IDUTENTE,RS.VERSIONE ");
			st.setString(1, cognome);
			st.setString(2, progetto);
			rs = st.executeQuery();
			return rs;
		} catch (SQLException sql) {
			sql.printStackTrace();
		}
		return null;
	}

	@Override
	public ResultSet StampaRicercaVProgettoTramiteNomeandCognome(String nome, String cognome) {
		PreparedStatement st;
		ResultSet rs;
		try {
			st = connection.prepareStatement(
					"  select  u.idutente,u.NOME ||' '|| u.COGNOME,pro.pathname, pro.nomeprogetto,pro.dataapertura,pro.datachiusura,rs.versione from utente u join partecipa p on u.idutente=p.idutente join progetto pro on p.nomeprogetto=pro.nomeprogetto join release rs on rs.nomeprogetto=pro.nomeprogetto where u.nome=:1 and u.cognome=:2 ORDER BY PRO.NOMEPROGETTO,U.IDUTENTE,RS.VERSIONE ");
			st.setString(1, nome);
			st.setString(2, cognome);
			rs = st.executeQuery();
			return rs;
		} catch (SQLException sql) {
			sql.printStackTrace();
		}
		return null;
	}

	@Override
	public ResultSet StampaRicercaVProgettoTramiteNomeAndProgettoAndRelease(String nome, String progetto,
			String release) {
		PreparedStatement st;
		ResultSet rs;
		try {
			st = connection.prepareStatement(
					"  select  u.idutente,u.NOME ||' '|| u.COGNOME,pro.pathname, pro.nomeprogetto,pro.dataapertura,pro.datachiusura,rs.versione from utente u join partecipa p on u.idutente=p.idutente join progetto pro on p.nomeprogetto=pro.nomeprogetto join release rs on rs.nomeprogetto=pro.nomeprogetto where u.nome=:1 and pro.nomeprogetto=:2 and rs.versione=:3 ORDER BY PRO.NOMEPROGETTO,U.IDUTENTE,RS.VERSIONE ");
			st.setString(1, nome);
			st.setString(2, progetto);
			st.setString(3, release);
			rs = st.executeQuery();
			return rs;
		} catch (SQLException sql) {
			sql.printStackTrace();
		}
		return null;
	}

	@Override
	public ResultSet StampaRicercaVProgettoTramiteCognomeAndReleaseAndProgetto(String cognome, String release,
			String Progetto) {
		PreparedStatement st;
		ResultSet rs;
		try {
			st = connection.prepareStatement(
					"  select  u.idutente,u.NOME ||' '|| u.COGNOME,pro.pathname, pro.nomeprogetto,pro.dataapertura,pro.datachiusura,rs.versione from utente u join partecipa p on u.idutente=p.idutente join progetto pro on p.nomeprogetto=pro.nomeprogetto join release rs on rs.nomeprogetto=pro.nomeprogetto where u.cognome=:1 and rs.versione=:2 and pro.nomeprogetto=:3 ORDER BY PRO.NOMEPROGETTO,U.IDUTENTE,RS.VERSIONE ");
			st.setString(1, cognome);
			st.setString(2, release);
			st.setString(3, Progetto);
			rs = st.executeQuery();
			return rs;
		} catch (SQLException sql) {
			sql.printStackTrace();
		}
		return null;
	}

	@Override
	public ResultSet StampaRicercaVProgettoTramiteCognomeAndReleaseAndNome(String cognome, String release,
			String nome) {
		PreparedStatement st;
		ResultSet rs;
		try {
			st = connection.prepareStatement(
					"  select  u.idutente,u.NOME ||' '|| u.COGNOME,pro.pathname, pro.nomeprogetto,pro.dataapertura,pro.datachiusura,rs.versione from utente u join partecipa p on u.idutente=p.idutente join progetto pro on p.nomeprogetto=pro.nomeprogetto join release rs on rs.nomeprogetto=pro.nomeprogetto where u.cognome=:1 and rs.versione=:2 and u.nome=:3 ORDER BY PRO.NOMEPROGETTO,U.IDUTENTE,RS.VERSIONE ");
			st.setString(1, cognome);
			st.setString(2, release);
			st.setString(3, nome);
			rs = st.executeQuery();
			return rs;
		} catch (SQLException sql) {
			sql.printStackTrace();
		}
		return null;
	}

	@Override
	public ResultSet StampaRicercaTestTramiteCognome(String cognome) {
		PreparedStatement pr;
		ResultSet rs;
		try {
			pr = connection.prepareStatement(
					"SELECT u.NOME ||' '|| u.COGNOME,TESTR.PATHNAME,TESTR.DATATEST,TESTR.DESCRIZIONE,P.NOMEPROGETTO,TESTR.RISULTATO FROM UTENTE U JOIN AVVIA A ON U.IDUTENTE=A.IDUTENTE JOIN TESTRELEASE TESTR ON A.IDTEST=TESTR.IDTEST JOIN PARTECIPA P ON U.IDUTENTE=P.IDUTENTE"
							+ " where u.cognome=:1");
			pr.setString(1, cognome);

			rs = pr.executeQuery();
			return rs;
		} catch (SQLException sql) {
			sql.printStackTrace();
		}
		return null;
	}

	@Override
	public ResultSet StampaRicercaTestTramiteCognomeAndNome(String cognome, String nome) {
		PreparedStatement pr;
		ResultSet rs;
		try {
			pr = connection.prepareStatement(
					"SELECT u.nome||' '||u.cognome,testr.pathname,testr.datatest,testr.descrizione,pro.nomeprogetto,testr.risultato FROM UTENTE U JOIN PARTECIPA P ON u.idutente=p.idutente JOIN PROGETTO PRO ON pro.nomeprogetto=p.nomeprogetto JOIN AVVIA A ON u.idutente=a.idutente JOIN testrelease TESTR ON testr.idtest=a.idtest JOIN TESTANO TESTA ON testr.idtest=testa.idtest JOIN RELEASE RS ON rs.idrelease=testa.idrelease AND rs.nomeprogetto=pro.nomeprogetto" + " where u.cognome=:1 and u.nome=:2");
			pr.setString(1, cognome);
			pr.setString(2, nome);
			rs = pr.executeQuery();
			return rs;
		} catch (SQLException sql) {
			sql.printStackTrace();
		}
		return null;
	}

	@Override
	public ResultSet StampaRicercaTestTramiteCognomeAndProgetto(String cognome, String progetto) {
		PreparedStatement pr;
		ResultSet rs;
		try {
			pr = connection.prepareStatement(
					"SELECT u.nome||' '||u.cognome,testr.pathname,testr.datatest,testr.descrizione,pro.nomeprogetto,testr.risultato FROM UTENTE U JOIN PARTECIPA P ON u.idutente=p.idutente JOIN PROGETTO PRO ON pro.nomeprogetto=p.nomeprogetto JOIN AVVIA A ON u.idutente=a.idutente JOIN testrelease TESTR ON testr.idtest=a.idtest JOIN TESTANO TESTA ON testr.idtest=testa.idtest JOIN RELEASE RS ON rs.idrelease=testa.idrelease AND rs.nomeprogetto=pro.nomeprogetto"+ " where u.cognome=:1 and p.nomeprogetto=:2");
			pr.setString(1, cognome);
			pr.setString(2, progetto);
			rs = pr.executeQuery();
			return rs;
		} catch (SQLException sql) {
			sql.printStackTrace();
		}
		return null;
	}

	@Override
	public ResultSet StampaRicercaTestTramiteCognomeAndRisultato(String cognome, String risultato) {
		PreparedStatement pr;
		ResultSet rs;
		try {
			pr = connection.prepareStatement(
					"SELECT u.nome||' '||u.cognome,testr.pathname,testr.datatest,testr.descrizione,pro.nomeprogetto,testr.risultato FROM UTENTE U JOIN PARTECIPA P ON u.idutente=p.idutente JOIN PROGETTO PRO ON pro.nomeprogetto=p.nomeprogetto JOIN AVVIA A ON u.idutente=a.idutente JOIN testrelease TESTR ON testr.idtest=a.idtest JOIN TESTANO TESTA ON testr.idtest=testa.idtest JOIN RELEASE RS ON rs.idrelease=testa.idrelease AND rs.nomeprogetto=pro.nomeprogetto"+ " where u.cognome=:1 and testr.risultato=:2");
			pr.setString(1, cognome);
			pr.setString(2, risultato);
			rs = pr.executeQuery();
			return rs;
		} catch (SQLException sql) {
			sql.printStackTrace();
		}
		return null;
	}

	@Override
	public ResultSet StampaRicercaTestTramiteCognomeAndUtenteAndProgetto(String cognome, String utente,
			String progetto) {
		PreparedStatement pr;
		ResultSet rs;
		try {
			pr = connection.prepareStatement(
					"SELECT u.nome||' '||u.cognome,testr.pathname,testr.datatest,testr.descrizione,pro.nomeprogetto,testr.risultato FROM UTENTE U JOIN PARTECIPA P ON u.idutente=p.idutente JOIN PROGETTO PRO ON pro.nomeprogetto=p.nomeprogetto JOIN AVVIA A ON u.idutente=a.idutente JOIN testrelease TESTR ON testr.idtest=a.idtest JOIN TESTANO TESTA ON testr.idtest=testa.idtest JOIN RELEASE RS ON rs.idrelease=testa.idrelease AND rs.nomeprogetto=pro.nomeprogetto"+ " where u.cognome=:1 and u.nome=:2 and p.nomeprogetto=:3");
			pr.setString(1, cognome);
			pr.setString(2, utente);
			pr.setString(3, progetto);
			rs = pr.executeQuery();
			return rs;
		} catch (SQLException sql) {
			sql.printStackTrace();
		}
		return null;
	}

	@Override
	public ResultSet StampaRicercaTestTramiteCognomeAndUtenteAndRisultato(String cognome, String utente,
			String Risultato) {
		PreparedStatement pr;
		ResultSet rs;
		try {
			pr = connection.prepareStatement(
					"SELECT u.nome||' '||u.cognome,testr.pathname,testr.datatest,testr.descrizione,pro.nomeprogetto,testr.risultato FROM UTENTE U JOIN PARTECIPA P ON u.idutente=p.idutente JOIN PROGETTO PRO ON pro.nomeprogetto=p.nomeprogetto JOIN AVVIA A ON u.idutente=a.idutente JOIN testrelease TESTR ON testr.idtest=a.idtest JOIN TESTANO TESTA ON testr.idtest=testa.idtest JOIN RELEASE RS ON rs.idrelease=testa.idrelease AND rs.nomeprogetto=pro.nomeprogetto"+ " where u.cognome=:1 and u.nome=:2 and testr.risultato=:3");
			pr.setString(1, cognome);
			pr.setString(2, utente);
			pr.setString(3, Risultato);
			rs = pr.executeQuery();
			return rs;
		} catch (SQLException sql) {
			sql.printStackTrace();
		}
		return null;
	}

	@Override
	public ResultSet StampaRicercaTestTramiteUtenteAndProgettoAndRisultato(String utente, String progetto,
			String Risultato) {
		PreparedStatement pr;
		ResultSet rs;
		try {
			pr = connection.prepareStatement(
					"SELECT u.nome||' '||u.cognome,testr.pathname,testr.datatest,testr.descrizione,pro.nomeprogetto,testr.risultato FROM UTENTE U JOIN PARTECIPA P ON u.idutente=p.idutente JOIN PROGETTO PRO ON pro.nomeprogetto=p.nomeprogetto JOIN AVVIA A ON u.idutente=a.idutente JOIN testrelease TESTR ON testr.idtest=a.idtest JOIN TESTANO TESTA ON testr.idtest=testa.idtest JOIN RELEASE RS ON rs.idrelease=testa.idrelease AND rs.nomeprogetto=pro.nomeprogetto"+ " where u.nome=:1 and p.nomeprogetto=:2 and TESTR.RISULTATO=:3");
			pr.setString(1, utente);
			pr.setString(2, progetto);
			pr.setString(3, Risultato);
			rs = pr.executeQuery();
			return rs;
		} catch (SQLException sql) {
			sql.printStackTrace();
		}
		return null;
	}

	@Override
	public ResultSet StampaRicercaTestTramiteCognomeAndProgettoAndRisultato(String cognome, String progetto,
			String Risultato) {
		PreparedStatement pr;
		ResultSet rs;
		try {
			pr = connection.prepareStatement(
					"SELECT u.nome||' '||u.cognome,testr.pathname,testr.datatest,testr.descrizione,pro.nomeprogetto,testr.risultato FROM UTENTE U JOIN PARTECIPA P ON u.idutente=p.idutente JOIN PROGETTO PRO ON pro.nomeprogetto=p.nomeprogetto JOIN AVVIA A ON u.idutente=a.idutente JOIN testrelease TESTR ON testr.idtest=a.idtest JOIN TESTANO TESTA ON testr.idtest=testa.idtest JOIN RELEASE RS ON rs.idrelease=testa.idrelease AND rs.nomeprogetto=pro.nomeprogetto"+ " where u.cognome=:1 and p.nomeprogetto=:2 and TESTR.RISULTATO=:3");
			pr.setString(1, cognome);
			pr.setString(2, progetto);
			pr.setString(3, Risultato);
			rs = pr.executeQuery();
			return rs;
		} catch (SQLException sql) {
			sql.printStackTrace();
		}
		return null;
	}

	@Override
	public ResultSet stampaUtentiNonPartecipanti(String Progetto) {
		PreparedStatement pr;
		ResultSet rs;
		try {
			pr=connection.prepareStatement("SELECT u.NOME ||' '|| u.COGNOME FROM UTENTE u   WHERE u.NOME ||' '|| u.COGNOME  not in(select u.NOME ||' '|| u.COGNOME \r\n" + 
					"from partecipa p \r\n" + 
					"where p.idutente=u.idutente and p.nomeprogetto=:1) ");
			pr.setString(1, Progetto);
			rs=pr.executeQuery();
			return rs;
			
		}catch(SQLException sql) {
			sql.printStackTrace();
		}
		return null;
	}

	@Override
	public ResultSet stampaProgettoDiUtentinonPartecipanti(String nome) {
		PreparedStatement pr;
		ResultSet rs;
		int indice;
		try {
			indice=IndiceNome(nome);
			pr=connection.prepareStatement("SELECT pro.nomeprogetto FROM progetto pro WHERE  pro.nomeprogetto not in(select p.nomeprogetto from partecipa p where p.nomeprogetto=pro.nomeprogetto and p.idutente=:1) ");
			pr.setInt(1, indice);
			rs=pr.executeQuery();
			return rs;
			
		}catch(SQLException sql) {
			sql.printStackTrace();
		}
		return null;
	}

}
