//Artur Pfeifer 879089

package dao;

import java.util.Collection;

import awk.Auftrag;
import awk.Auftragsposition;

public interface IAuftragDAO {

	public abstract boolean speicherAuftrag(Auftrag aAuftrag);

	public Auftrag ladeAuftrag(int id);

	public Collection<Auftrag> ladeAuftraegeVonKunde(int kunde_nr);

	public abstract boolean deleteAuftrag(Auftrag aAuftrag);

	public abstract boolean deleteAuftragspositionen(int auftrag_nr);

	public abstract Auftragsposition ladeAuftragsposition(int auftragsNr);

	public abstract Collection<Auftragsposition> ladeAuftragspositionenVonAuftrag(int auftragsNr);
}
