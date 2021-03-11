//Artur Pfeifer 879089

package awk.usecase;

import java.util.Collection;

import awk.Kunde;

public interface IKundenVw {

	public abstract Collection<Kunde> ladeAlleKunden();

	public abstract boolean speicherKunde(Kunde aKunde);

	public abstract boolean deleteKunde(int kd_nr);

	public abstract Kunde ladeKunde(int kd_nr);

	public abstract boolean kundenDatenAendern(Kunde aKunde, int kd_nr);
}
