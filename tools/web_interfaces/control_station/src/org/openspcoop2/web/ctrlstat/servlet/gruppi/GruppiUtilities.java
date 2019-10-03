/*
 * GovWay - A customizable API Gateway 
 * http://www.govway.org
 *
 * from the Link.it OpenSPCoop project codebase
 * 
 * Copyright (c) 2005-2019 Link.it srl (http://link.it). 
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 3, as published by
 * the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.openspcoop2.web.ctrlstat.servlet.gruppi;

import java.util.HashMap;
import java.util.List;

import org.openspcoop2.core.commons.ErrorsHandlerCostant;
import org.openspcoop2.core.id.IDAccordo;
import org.openspcoop2.core.id.IDGruppo;
import org.openspcoop2.core.registry.Gruppo;
import org.openspcoop2.core.registry.beans.AccordoServizioParteComuneSintetico;
import org.openspcoop2.core.registry.beans.GruppoSintetico;
import org.openspcoop2.core.registry.driver.FiltroRicercaAccordi;
import org.openspcoop2.protocol.engine.utils.DBOggettiInUsoUtils;
import org.openspcoop2.web.ctrlstat.servlet.apc.AccordiServizioParteComuneCore;

/**
 * GruppiUtilities
 * 
 * @author Andrea Poli (apoli@link.it)
 * @author Giuliano Pintori (pintori@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 * 
 */
public class GruppiUtilities {

	public static void findOggettiDaAggiornare(IDGruppo oldIdGruppo, Gruppo gruppoNEW, GruppiCore gruppiCore, List<Object> listOggettiDaAggiornare) throws Exception {

		// Cerco se utilizzato in Apc
		AccordiServizioParteComuneCore apcCore = new AccordiServizioParteComuneCore(gruppiCore);
		FiltroRicercaAccordi filtroRicercaAccordi = new FiltroRicercaAccordi();
		filtroRicercaAccordi.setIdGruppo(oldIdGruppo);
		List<IDAccordo> listAccordi = apcCore.getAllIdAccordiServizio(filtroRicercaAccordi);
		if(listAccordi!=null && listAccordi.size()>0){
			for (IDAccordo idAccordoWithGruppo : listAccordi) {
				AccordoServizioParteComuneSintetico accordoServizioSintetico = apcCore.getAccordoServizioSintetico(idAccordoWithGruppo);
				if(accordoServizioSintetico.getGruppo() != null) {
					for (GruppoSintetico gruppoAccordo : accordoServizioSintetico.getGruppo()) {
						if(gruppoAccordo.getNome().equals(oldIdGruppo.getNome())){
							gruppoAccordo.setNome(gruppoNEW.getNome());
						}
					}
				}
				listOggettiDaAggiornare.add(accordoServizioSintetico);
			}

		}
	}


	public static void deleteGruppo(Gruppo gruppo, String userLogin, GruppiCore gruppiCore, GruppiHelper gruppiHelper, StringBuffer inUsoMessage, String newLine) throws Exception {
		HashMap<ErrorsHandlerCostant, List<String>> whereIsInUso = new HashMap<ErrorsHandlerCostant, List<String>>();
		boolean normalizeObjectIds = !gruppiHelper.isModalitaCompleta();
		boolean gruppoInUso = gruppiCore.isGruppoInUso(gruppo.getNome(),whereIsInUso,normalizeObjectIds);

		if (gruppoInUso) {
			inUsoMessage.append(DBOggettiInUsoUtils.toString(new IDGruppo(gruppo.getNome()), whereIsInUso, true, newLine));
			inUsoMessage.append(newLine);

		} else {
			gruppiCore.performDeleteOperation(userLogin, gruppiHelper.smista(), gruppo);
		}
	}
}