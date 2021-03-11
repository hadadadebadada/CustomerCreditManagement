//Artur Pfeifer 879089

package dao;

import java.util.Collection;

import awk.Kunde;

public interface IKundeDAO {

	public boolean kundeSpeichern(Kunde aKunde);

	public Kunde ladeKunde(int kunde_nr);

	public Collection<Kunde> kundenlisteLaden();

	public boolean deleteKunde(int kunde_nr);

	boolean kundenDatenAendern(Kunde aKunde, int kunde_nr);

}
