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

package org.openspcoop2.example.pdd.server.trasparente.richiestastatofamiglia;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * RichiestaStatoFamiglia_RichiestaStatoFamigliaInterfaceEndpointMultiBinding_Server
 * 
 * @author Poli Andrea (apoli@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class RichiestaStatoFamiglia_RichiestaStatoFamigliaInterfaceEndpointMultiBinding_Server{

	private ClassPathXmlApplicationContext context = null;
	
    protected RichiestaStatoFamiglia_RichiestaStatoFamigliaInterfaceEndpointMultiBinding_Server() throws java.lang.Exception {
        System.out.println("Starting Server");
        this.context = new ClassPathXmlApplicationContext("configurazionePdD/server/cxf.xml");
        this.context.toString();
    }
    
    public void close(){
    	this.context.close();
    }
    
    public static void main(String args[]) throws java.lang.Exception { 
    	RichiestaStatoFamiglia_RichiestaStatoFamigliaInterfaceEndpointMultiBinding_Server server = new RichiestaStatoFamiglia_RichiestaStatoFamigliaInterfaceEndpointMultiBinding_Server();
        System.out.println("Server ready..."); 
        
        Thread.sleep(5 * 60 * 1000); 
        System.out.println("Server exiting");
        server.close();
        System.exit(0);
    }
}
