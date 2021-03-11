//Artur Pfeifer 879089

package awk.usecase;

import java.util.Collection;

import awk.Auftrag;
import awk.Auftragsposition;

public interface IKreditvertragVw {

	public boolean auftragSpeichern(Auftrag auftrag);

	public Auftrag ladeAuftrag(int auftragNr);

	public Collection<Auftrag> ladeAuftraegeVonKunde(int kdnr);

	public abstract boolean deleteAuftrag(Auftrag auftrag);
	
	public abstract boolean deleteAuftragspositionen(int auftrag_nr);

	public abstract	Auftragsposition ladeAuftragsposition(int auftrag_nr);

	public abstract	Collection<Auftragsposition> ladeAuftragspositionenVonAuftrag(int auftragsNr);

}
