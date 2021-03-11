//Artur Pfeifer 879089

package dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import awk.Kunde;
import dao.Persistence;
import dao.IKundeDAO;

public class KundeDAO implements IKundeDAO {

	@Override
	public boolean kundeSpeichern(Kunde aKunde) {
		boolean ret = false;

		Connection aConnection = dao.Persistence.getConnection();

		String insertString = "";
		if (aKunde.getKunde_nr() > 0) {
			insertString = "UPDATE HA2_Kunde set name = '" + aKunde.getName() + "', VORNAME= '" + aKunde.getVorname()
					+ "',GEBDATUM= '" + aKunde.getGebDatum() + "' WHERE KUNDENNR = " + aKunde.getKunde_nr();
		} else
			insertString = "INSERT INTO HA2_Kunde (NAME, VORNAME, GEBDATUM,KUNDENNR) VALUES(" + "'" + aKunde.getName()
					+ "'," + "'" + aKunde.getVorname() + "'," + "'" + aKunde.getGebDatum() + "',";
		insertString = insertString + "HA2_kunde_seq.nextval)";

		System.out.println(insertString);

		try {
			Persistence.executeUpdateStatement(aConnection, insertString);
			ret = true;
		} catch (SQLException e) {
			e.printStackTrace();
			ret = false;
		} finally {
			Persistence.closeConnection(aConnection);
		}

		return ret;
	}

	@Override
	public boolean kundenDatenAendern(Kunde aKunde, int kunde_nr) {
		boolean ret = false;

		Connection aConnection = dao.Persistence.getConnection();

		String insertString = "";
		insertString = "UPDATE HA2_Kunde set name = '" + aKunde.getName() + "', VORNAME= '" + aKunde.getVorname()
				+ "',GEBDATUM= '" + aKunde.getGebDatum() + "' WHERE KUNDENNR = " + aKunde.getKundenSuche();

		System.out.println(insertString);

		try {
			Persistence.executeUpdateStatement(aConnection, insertString);
			ret = true;
		} catch (SQLException e) {
			e.printStackTrace();
			ret = false;
		} finally {
			Persistence.closeConnection(aConnection);
		}

		return ret;

	}

	public boolean deleteKunde(int kunde_nr) {

		boolean ret = false;
		Connection aConnection = dao.Persistence.getConnection();

		try {
			Persistence.executeQueryStatement(aConnection, "DELETE from HA2_kunde where kundennr = " + kunde_nr);
			ret = true;
		} catch (SQLException e) {
			e.printStackTrace();
			ret = false;
		}
		return ret;
	}

	@Override
	public Kunde ladeKunde(int kunde_nr) {
		Kunde einKunde = null;

		Connection aConnection = dao.Persistence.getConnection();
		ResultSet resultSet = null;
		try {
			resultSet = Persistence.executeQueryStatement(aConnection,
					"SELECT * from HA2_kunde where kundennr = " + kunde_nr);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			while (resultSet.next()) {
				einKunde = new Kunde(resultSet.getString("NAME"), resultSet.getString("VORNAME"),
						resultSet.getString("GEBDATUM"));
				einKunde.setKunde_nr(resultSet.getInt("KUNDENNR"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return einKunde;
	}

	@Override
	public Collection<Kunde> kundenlisteLaden() {

		Collection<Kunde> ret = new ArrayList<Kunde>();

		Connection aConnection = dao.Persistence.getConnection();
		ResultSet resultSet = null;
		try {
			resultSet = Persistence.executeQueryStatement(aConnection, "SELECT * from HA2_kunde");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			while (resultSet.next()) {
				int kdnr = resultSet.getInt("KUNDENNR");
				Kunde aKunde = ladeKunde(kdnr);
				ret.add(aKunde);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		Persistence.closeConnection(aConnection);

		return ret;

	}

}
