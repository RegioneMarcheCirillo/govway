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
package org.openspcoop2.pdd.core.behaviour;

import org.openspcoop2.core.config.PortaApplicativaBehaviour;
import org.openspcoop2.core.config.PortaApplicativaServizioApplicativoConnettore;
import org.openspcoop2.core.config.Proprieta;

/**
 * PropertiesUtils
 *
 * @author Andrea Poli (apoli@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class BehaviourPropertiesUtils {

	public static void addProprieta(PortaApplicativaBehaviour paBehaviour, String nome, String valore) {
		if(paBehaviour.sizeProprietaList()>0) {
			int index =0;
			for (Proprieta p : paBehaviour.getProprietaList()) {
				if(p.getNome().equals(nome)) {
					paBehaviour.removeProprieta(index);
					break;
				}
				index++;
			}
		}
		paBehaviour.addProprieta(newP(nome, valore));
	}
	
	public static void addProprieta(PortaApplicativaServizioApplicativoConnettore pasaConnnettore, String nome, String valore) {
		if(pasaConnnettore.sizeProprietaList()>0) {
			int index =0;
			for (Proprieta p : pasaConnnettore.getProprietaList()) {
				if(p.getNome().equals(nome)) {
					pasaConnnettore.removeProprieta(index);
					break;
				}
				index++;
			}
		}
		pasaConnnettore.addProprieta(newP(nome, valore));
	}
	
	private static Proprieta newP(String nome, String valore) {
		Proprieta p = new Proprieta();
		p.setNome(nome);
		p.setValore(valore);
		return p;
	}
}
