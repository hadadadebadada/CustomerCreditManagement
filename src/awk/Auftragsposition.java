//Artur Pfeifer 879089

package awk;


public class Auftragsposition {

	private Auftrag auftrag;
	private int nr_im_auftrag;
	private String vertragsart;
	private int kreditbetrag;
	private String zinsbindung;
	private double sollzins;
	private double tilgungsrate;
	private String grundbuch;
	private int flurparzelle;
	private String konsumgut;

	public Auftragsposition() {
	}

	public Auftragsposition(Auftrag auftrag, int nr_im_auftrag, String vertragsart, int kreditbetrag,
			String zinsbindung, double sollzins, double tilgungsrate, String grundbuch, int flurparzelle,
			String konsumgut) {
		super();
		this.auftrag = auftrag;
		this.nr_im_auftrag = nr_im_auftrag;
		this.vertragsart = vertragsart;
		this.kreditbetrag = kreditbetrag;
		this.zinsbindung = zinsbindung;
		this.sollzins = sollzins;
		this.tilgungsrate = tilgungsrate;
		this.grundbuch = grundbuch;
		this.flurparzelle = flurparzelle;
		this.konsumgut = konsumgut;

	}

	public Auftrag getAuftrag() {
		return auftrag;
	}

	public void setAuftrag(Auftrag auftrag) {
		this.auftrag = auftrag;
	}

	public int getNr_im_auftrag() {
		return nr_im_auftrag;
	}

	public void setNr_im_auftrag(int nr_im_auftrag) {
		this.nr_im_auftrag = nr_im_auftrag;
	}

	public String getVertragsart() {
		return vertragsart;
	}

	public void setVertragsart(String vertragsart) {
		this.vertragsart = vertragsart;
	}

	public int getKreditbetrag() {
		return kreditbetrag;
	}

	public void setKreditbetrag(int kreditbetrag) {
		this.kreditbetrag = kreditbetrag;
	}

	public String getZinsbindung() {
		return zinsbindung;
	}

	public void setZinsbindung(String zinsbindung) {
		this.zinsbindung = zinsbindung;
	}

	public double getSollzins() {
		return sollzins;
	}

	public void setSollzins(double sollzins) {
		this.sollzins = sollzins;
	}

	public double getTilgungsrate() {
		return tilgungsrate;
	}

	public void setTilgungsrate(double tilgungsrate) {
		this.tilgungsrate = tilgungsrate;
	}

	public String getGrundbuch() {
		return grundbuch;
	}

	public void setGrundbuch(String grundbuch) {
		this.grundbuch = grundbuch;
	}

	public int getFlurparzelle() {
		return flurparzelle;
	}

	public void setFlurparzelle(int flurparzelle) {
		this.flurparzelle = flurparzelle;
	}

	public String getKonsumgut() {
		return konsumgut;
	}

	public void setKonsumgut(String konsumgut) {
		this.konsumgut = konsumgut;
	}

	public String toString() {
		String ret = "";

		ret = this.nr_im_auftrag + " " + this.vertragsart + " " + this.kreditbetrag + " " + this.zinsbindung + " "
				+ this.sollzins + " " + this.tilgungsrate + " " + this.grundbuch + " " + this.flurparzelle + " "
				+ this.konsumgut;

		return ret;

	}

}
