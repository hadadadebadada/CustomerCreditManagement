//Artur Pfeifer 879089

package awk;


public class Kunde {

	private int kunde_nr;
	private String kundenSuche;
	private String name;
	private String vorname;
	private String gebDatum;

//	private IKundeDAO einKundeDAO;

	public Kunde(String name, String vorname, String gebDatum) {
		super();
		this.name = name;
		this.vorname = vorname;
		this.gebDatum = gebDatum;
	}

	public Kunde(String name, String vorname, String gebDatum, String kundenSuche) {
		super();
		this.name = name;
		this.vorname = vorname;
		this.gebDatum = gebDatum;
		this.kundenSuche = kundenSuche;
	}

	public int getKunde_nr() {
		return kunde_nr;
	}

	public void setKunde_nr(int kunde_nr) {
		this.kunde_nr = kunde_nr;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVorname() {
		return vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public String getGebDatum() {
		return gebDatum;
	}

	public void setGebDatum(String gebDatum) {
		this.gebDatum = gebDatum;
	}

	public String toString() {
		String str = this.kunde_nr + " " + this.name + " " + this.vorname + " " + this.gebDatum;
		return str = "[" + this.kunde_nr + " " + this.name + " " + this.vorname + "  " + this.gebDatum + "]";
	}

	public void save() {

	}

	public String getKundenSuche() {
		return kundenSuche;
	}

	public void setKundenSuche(String kundenSuche) {
		this.kundenSuche = kundenSuche;
	}
}
