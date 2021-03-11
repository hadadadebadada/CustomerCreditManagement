//Artur Pfeifer 879089

package awk.usecase.impl;

import java.util.Collection;

import awk.Auftrag;
import awk.Auftragsposition;
import awk.usecase.IKreditvertragVw;
import dao.IAuftragDAO;
import dao.impl.AuftragDAO;

public class KreditvertragVerwalter implements IKreditvertragVw {

	IAuftragDAO auftragDAO = new AuftragDAO();

	@Override
	public boolean auftragSpeichern(Auftrag aAuftrag) {
		return auftragDAO.speicherAuftrag(aAuftrag);

	}

	@Override
	public Collection<Auftrag> ladeAuftraegeVonKunde(int kdnr) {
		return auftragDAO.ladeAuftraegeVonKunde(kdnr);
	}

	@Override
	public Auftrag ladeAuftrag(int auftragNr) {
		return auftragDAO.ladeAuftrag(auftragNr);
	}

	@Override
	public boolean deleteAuftrag(Auftrag aAuftrag) {
		return auftragDAO.deleteAuftrag(aAuftrag);
	}

	@Override
	public boolean deleteAuftragspositionen(int auftrag_nr) {
		return auftragDAO.deleteAuftragspositionen(auftrag_nr);
	}

	@Override
	public Auftragsposition ladeAuftragsposition(int auftrag_nr) {
		return auftragDAO.ladeAuftragsposition(auftrag_nr);
	}

	@Override
	public Collection<Auftragsposition> ladeAuftragspositionenVonAuftrag(int auftragsNr) {
		return auftragDAO.ladeAuftragspositionenVonAuftrag(auftragsNr);
	}

}
