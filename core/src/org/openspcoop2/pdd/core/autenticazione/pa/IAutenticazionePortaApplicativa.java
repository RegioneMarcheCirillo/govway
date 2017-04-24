/*
 * OpenSPCoop - Customizable API Gateway 
 * http://www.openspcoop2.org
 * 
 * Copyright (c) 2005-2017 Link.it srl (http://link.it). 
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



package org.openspcoop2.pdd.core.autenticazione.pa;

import org.openspcoop2.pdd.core.autenticazione.AutenticazioneException;
import org.openspcoop2.pdd.core.autenticazione.IAutenticazione;

/**
 * Interfaccia che definisce un processo di autenticazione per servizi applicativi che invocano porte delegate.
 *
 * @author Andrea Poli <apoli@link.it>
 * @author $Author: apoli $
 * @version $Rev: 12564 $, $Date: 2017-01-11 14:31:31 +0100 (Wed, 11 Jan 2017) $
 */

public interface IAutenticazionePortaApplicativa extends IAutenticazione {


    /**
     * Avvia il processo di autorizzazione.
     *
     * @param datiInvocazione Dati di invocazione
     * @return Esito dell'autorizzazione.
     * 
     */
    public EsitoAutenticazionePortaApplicativa process(DatiInvocazionePortaApplicativa datiInvocazione) throws AutenticazioneException;
    
 
    /**
     * Permette di personalizzare la chiave utilizzata per salvare il risultato nella cache
     * 
     * @param datiInvocazione Dati di invocazione
     * @return Suffisso che viene aggiunto alla chiave
     */
    public String getSuffixKeyAuthenticationResultInCache(DatiInvocazionePortaApplicativa datiInvocazione);
}
