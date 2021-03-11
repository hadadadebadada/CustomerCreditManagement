//Artur Pfeifer 879089

package awk;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import dao.IAuftragDAO;
import dao.impl.AuftragDAO;

public class Auftrag {

	private int auftrag_nr;
	private Kunde kunde;
	private Date aDatum;
	private Collection<Auftragsposition> auftragspositionen;

	private IAuftragDAO kreditvertragsverwalter;

	public Auftrag() {
		kreditvertragsverwalter = new AuftragDAO();
		auftragspositionen = new ArrayList<Auftragsposition>();
	}

	public Auftrag(Kunde kunde, Date aDatum, Collection<Auftragsposition> auftragspositionen) {
		// super();
		kreditvertragsverwalter = new AuftragDAO();
		this.kunde = kunde;
		this.aDatum = aDatum;
		this.auftragspositionen = auftragspositionen;
	}

	public Auftrag(int anr, Kunde kunde, Date aDatum, Collection<Auftragsposition> auftragspositionen) {
		// super();
		kreditvertragsverwalter = new AuftragDAO();
		this.auftrag_nr = anr;
		this.kunde = kunde;
		this.aDatum = aDatum;
		this.auftragspositionen = auftragspositionen;
	}

	public Kunde getKunde() {
		return kunde;
	}

	public void setKunde(Kunde kunde) {
		this.kunde = kunde;
	}

	public Date getaDatum() {
		return aDatum;
	}

	public void setaDatum(Date aDatum) {
		this.aDatum = aDatum;
	}

	public Collection<Auftragsposition> getAuftragspositionen() {
		return auftragspositionen;
	}

	public void setAuftragspositionen(Collection<Auftragsposition> auftragspositionen) {
		this.auftragspositionen = auftragspositionen;
	}

	public void addAuftragsposition(Auftragsposition aPos) {
		this.auftragspositionen.add(aPos);
	}

	public void save() {
		this.kreditvertragsverwalter.speicherAuftrag(this);

	}

	public int getAuftrag_nr() {
		return auftrag_nr;
	}

	public void setAuftrag_nr(int auftrag_nr) {
		this.auftrag_nr = auftrag_nr;
	}

	public String toString() {
		String ret = "";
		ret = "Auftragsnummer: "+ this.auftrag_nr + " | Auftragsgeber:  " + this.kunde.getName() + " " + " " + this.kunde.getVorname() + " | Datum: "+ this.aDatum + "\n";
		for (Auftragsposition aPos : this.auftragspositionen) {
			ret = ret + aPos.getNr_im_auftrag() + " " + aPos.getVertragsart() + " " + aPos.getKreditbetrag() + " "
					+ aPos.getZinsbindung() + " " + aPos.getSollzins() + " " + aPos.getTilgungsrate() + " "
					+ aPos.getGrundbuch() + " " + aPos.getFlurparzelle() + " " + aPos.getKonsumgut() + "\n";
		}

		return ret;

	}

}
