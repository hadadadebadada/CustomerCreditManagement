//Artur Pfeifer 879089

package awk.usecase.impl;

import java.util.Collection;

import awk.Kunde;
import awk.usecase.IKundenVw;
import dao.IKundeDAO;
import dao.impl.KundeDAO;

public class KundenVerwalter implements IKundenVw{

	IKundeDAO kundeDAO = new KundeDAO();
	
	@Override
	public Collection<Kunde> ladeAlleKunden() {
				return kundeDAO.kundenlisteLaden();
	}

	@Override
	public boolean speicherKunde(Kunde aKunde) {
		return kundeDAO.kundeSpeichern(aKunde);
	}

	@Override
	public boolean deleteKunde(int kd_nr) {
		return kundeDAO.deleteKunde(kd_nr);
	}

	@Override
	public Kunde ladeKunde(int kd_nr) {
	return kundeDAO.ladeKunde(kd_nr);
	}

	@Override
	public boolean kundenDatenAendern(Kunde aKunde, int kd_nr) {
		return kundeDAO.kundenDatenAendern(aKunde, kd_nr);
	}


	
	

}
