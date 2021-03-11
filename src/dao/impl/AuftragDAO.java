//Artur Pfeifer 879089

package dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;

import awk.Auftrag;
import awk.Auftragsposition;
import awk.Kunde;
import dao.IAuftragDAO;
import dao.IKundeDAO;
import dao.Persistence;

public class AuftragDAO implements IAuftragDAO {

	@Override
	public boolean speicherAuftrag(Auftrag aAuftrag) {

		DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

		String sqlString = "INSERT INTO HA2_Kreditvertrag (ANR, KDNR, ADATUM) VALUES ( ";

		if (aAuftrag.getAuftrag_nr() > 0) {
			sqlString = "UPDATE HA2_auftrag SET KDNR = " + aAuftrag.getKunde().getKunde_nr() + ", ADATUM = to_date('"
					+ dateFormat.format(aAuftrag.getaDatum()) + "','dd.MM.yyyy')" + " WHERE ANR = "
					+ aAuftrag.getAuftrag_nr();

		} else {
			sqlString = "INSERT INTO HA2_Kreditvertrag (ANR, KDNR, ADATUM) VALUES ( ";
			sqlString = sqlString + " HA2_kunde_seq.nextval, ";
			sqlString = sqlString + aAuftrag.getKunde().getKunde_nr() + ",  to_date('"
					+ dateFormat.format(aAuftrag.getaDatum()) + "','dd.MM.yyyy'))";
		}
//		System.out.println(sqlString);

		boolean ret = false;
		Connection aConnection = dao.Persistence.getConnection();

		try {
			Persistence.executeUpdateStatement(aConnection, sqlString);
			ret = true;
		} catch (SQLException e) {
			e.printStackTrace();
			ret = false;
		}

		// Wenn Auftrag neu --> kein AuftragNr --> diese wurde via SEQ erzeugt
		// --> letzten Wert aus DB abfragen
		if (aAuftrag.getAuftrag_nr() == 0) {
			int auftragsNr = 0;
			ResultSet aResult = null;
			try {
				aResult = Persistence.executeQueryStatement(aConnection,
						"SELECT HA2_kunde_seq.currval as value FROM dual");
				while (aResult.next()) {
					try {
						auftragsNr = aResult.getInt("VALUE");
						aAuftrag.setAuftrag_nr(auftragsNr);
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}

		if (aAuftrag.getAuftrag_nr() > 0) {
			/* Alle Auftragspositionen loeschen */
			deleteAuftragspositionen(aAuftrag.getAuftrag_nr());
			System.out.println("Anzahl Positionen: " + aAuftrag.getAuftragspositionen());
			for (Auftragsposition aPos : aAuftrag.getAuftragspositionen()) {
				sqlString = "INSERT INTO HA2_kreditvertragspos (POSNR, NR_IM_VERTRAG, ANR, "
						+ "VERTRAGSART, KREDITBETRAG, ZINSBINDUNGSDAUER,VEREINBARTERSOLLZINS,TILGUNGSRATE, GRUNDBUCHEINTRAG, FLURPARZELLE,NAMEDESKONSUMGUTS) VALUES ( ";

				sqlString = sqlString + " HA2_kreditvertrags_pos_seq.nextval, ";
				sqlString = sqlString + aPos.getNr_im_auftrag() + ", ";
				sqlString = sqlString + aPos.getAuftrag().getAuftrag_nr() + ", '";
				sqlString = sqlString + aPos.getVertragsart() + "', ";
				sqlString = sqlString + aPos.getKreditbetrag() + ", ";
				sqlString = sqlString + aPos.getZinsbindung() + ", ";
				sqlString = sqlString + aPos.getSollzins() + ", ";
				sqlString = sqlString + aPos.getTilgungsrate() + ", ";
				sqlString = sqlString + aPos.getGrundbuch() + ", ";
				sqlString = sqlString + aPos.getFlurparzelle() + ", ";

				sqlString = sqlString + aPos.getKonsumgut() + ")";

				try {
					Persistence.executeUpdateStatement(aConnection, sqlString);
					ret = true;
				} catch (SQLException e) {
					e.printStackTrace();
					ret = false;
				}
			} // for (Auftragsposition ...
		} // if (aAuftrag.getAuftrag_nr >0

		try {
			aConnection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ret;
	}

	public boolean deleteAuftragspositionen(int auftrag_nr) {
		boolean ret = false;
		Connection aConnection = dao.Persistence.getConnection();

		try {
			Persistence.executeQueryStatement(aConnection,
					"DELETE from HA2_kreditvertragspos where anr = " + auftrag_nr);
			ret = true;
			System.out.println("Auftragspositionen von Auftrag " + auftrag_nr + " geloescht");
		} catch (SQLException e) {
			e.printStackTrace();
			ret = false;
		}

		Persistence.closeConnection(aConnection);

		return ret;

	}

	public boolean deleteAuftrag(Auftrag auftrag) { // test mit kdnr
		boolean ret = false;
		Connection aConnection = dao.Persistence.getConnection();

		try {
			Persistence.executeQueryStatement(aConnection,
					"DELETE from HA2_kreditvertragspos where anr = " + auftrag.getAuftrag_nr());
			ret = true;
			System.out.println("Kreditvertragspositionen von Kreditvertrag " + auftrag.getAuftrag_nr() + " geloescht");
		} catch (SQLException e) {
			e.printStackTrace();
			ret = false;
		}

		try {
			Persistence.executeQueryStatement(aConnection,
					"DELETE from HA2_kreditvertrag where anr = " + auftrag.getAuftrag_nr());
			System.out.println("Auftrag " + auftrag.getAuftrag_nr() + " geloescht");
		} catch (SQLException e) {
			e.printStackTrace();
			ret = false;
		}

		Persistence.closeConnection(aConnection);

		return ret;

	}

	@Override
	public Auftragsposition ladeAuftragsposition(int auftragsNr) { // TESTMETHODE

		Auftragsposition aAuftragsposition = null;
		Connection aConnection = dao.Persistence.getConnection();
		ResultSet resultSet = null;

		try {
			resultSet = Persistence.executeQueryStatement(aConnection,
					"SELECT * from HA2_Kreditvertragspos where anr = " + auftragsNr);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// IKundeDAO einKundenVerwalter = new KundeDAO();
		IAuftragDAO einAuftragsVerwalter = new AuftragDAO();
		try {
			while (resultSet.next()) {

				Auftrag aAuftrag = einAuftragsVerwalter.ladeAuftrag(resultSet.getInt("ANR"));

				// System.out.println("anr: "+resultSet.getInt("ANR"));

				aAuftragsposition = new Auftragsposition(
						// ladeAuftrag(resultSet.getInt("ANR")), //bzw aAuftrag laden
						aAuftrag, resultSet.getInt("NR_IM_VERTRAG"), resultSet.getString("VERTRAGSART"),
						resultSet.getInt("KREDITBETRAG"), resultSet.getString("ZINSBINDUNGSDAUER"),
						resultSet.getDouble("VEREINBARTERSOLLZINS"), resultSet.getDouble("TILGUNGSRATE"),
						resultSet.getString("GRUNDBUCHEINTRAG"), resultSet.getInt("FLURPARZELLE"),
						resultSet.getString("NAMEDESKONSUMGUTS"));
				// aAuftrag.addAuftragsposition(aAuftragsposition);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(aAuftragsposition.toString());
		return aAuftragsposition;
	}

	@Override
	public Collection<Auftragsposition> ladeAuftragspositionenVonAuftrag(int auftragsNr) { // TESTMETHODE

		Collection<Auftragsposition> ret = new ArrayList<Auftragsposition>();

		Connection aConnection = dao.Persistence.getConnection();
		ResultSet resultSet = null;

		/*
		 * Auftraege von KundenNr laden
		 */
		try {
			resultSet = Persistence.executeQueryStatement(aConnection,
					"SELECT * FROM HA2_Kreditvertragspos WHERE anr = " + auftragsNr); 
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			while (resultSet.next()) {
				int anr = resultSet.getInt("ANR");
				Auftragsposition aAuftragsposition = ladeAuftragsposition(anr);
				ret.add(aAuftragsposition);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		Persistence.closeConnection(aConnection);

		return ret;
	}

	@Override
	public Collection<Auftrag> ladeAuftraegeVonKunde(int kunde_nr) {

		Collection<Auftrag> ret = new ArrayList<Auftrag>();

		Connection aConnection = dao.Persistence.getConnection();
		ResultSet resultSet = null;

		/*
		 * Auftraege von KundenNr laden
		 */
		try {
			resultSet = Persistence.executeQueryStatement(aConnection,
					"SELECT * FROM HA2_Kreditvertrag WHERE kdnr = " + kunde_nr);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			while (resultSet.next()) {
				int anr = resultSet.getInt("ANR");
				Auftrag aAuftrag = ladeAuftrag(anr);
				ret.add(aAuftrag);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		Persistence.closeConnection(aConnection);

		return ret;
	}

	@Override
	public Auftrag ladeAuftrag(int auftragsNr) {
		Auftrag aAuftrag = null;
		Connection aConnection = dao.Persistence.getConnection();
		ResultSet resultSet = null;

		/*
		 * Auftrag laden
		 */
		try {
			resultSet = Persistence.executeQueryStatement(aConnection,
					"SELECT * from HA2_Kreditvertrag where anr = " + auftragsNr);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		IKundeDAO einKundenVerwalter = new KundeDAO();
		try {
			while (resultSet.next()) {
//				System.out.println("anr: "+resultSet.getInt("ANR"));
//				System.out.println("knr: "+resultSet.getInt("KDNR"));
//				System.out.println("adatum: "+resultSet.getDate("ADATUM"));
				Kunde kunde = einKundenVerwalter.ladeKunde(resultSet.getInt("KDNR"));
				aAuftrag = new Auftrag(resultSet.getInt("ANR"), kunde, resultSet.getDate("ADATUM"),
						new ArrayList<Auftragsposition>());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		/*
		 * / Alle Auftragspositionen eines Auftrags laden
		 */
		resultSet = null;

		try {
			resultSet = Persistence.executeQueryStatement(aConnection,
					"SELECT * from HA2_kreditvertragspos where anr = " + auftragsNr);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			while (resultSet.next()) {
//				System.out.println("anr: "+resultSet.getInt("ANR"));
//				System.out.println("knr: "+resultSet.getInt("KDNR"));
//				System.out.println("adatum: "+resultSet.getDate("ADATUM"));
				Auftragsposition aPos = new Auftragsposition(

						aAuftrag, resultSet.getInt("NR_IM_VERTRAG"), resultSet.getString("VERTRAGSART"),
						resultSet.getInt("KREDITBETRAG"), resultSet.getString("ZINSBINDUNGSDAUER"),
						resultSet.getDouble("VEREINBARTERSOLLZINS"), resultSet.getDouble("TILGUNGSRATE"),
						resultSet.getString("GRUNDBUCHEINTRAG"), resultSet.getInt("FLURPARZELLE"),
						resultSet.getString("NAMEDESKONSUMGUTS"));
				aAuftrag.addAuftragsposition(aPos);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		Persistence.closeConnection(aConnection);

		System.out.println("Auftrag " + aAuftrag.getAuftrag_nr() + " mit " + aAuftrag.getAuftragspositionen().size()
				+ " Auftragspositionen geladen.");

		return aAuftrag;
	}

}
