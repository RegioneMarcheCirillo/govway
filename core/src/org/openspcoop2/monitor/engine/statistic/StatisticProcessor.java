/*
 * GovWay - A customizable API Gateway 
 * http://www.govway.org
 * 
 * from the Link.it OpenSPCoop project codebase
 * 
 * Copyright (c) 2005-2018 Link.it srl (http://link.it).
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
package org.openspcoop2.monitor.engine.statistic;

import org.apache.logging.log4j.Level;
import org.openspcoop2.monitor.engine.config.LoggerManager;

import org.openspcoop2.utils.LoggerWrapperFactory;
import org.slf4j.Logger;

/**
 * StatisticProcessor
 *
 * @author Poli Andrea (apoli@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class StatisticProcessor {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Logger logCore = null;
		try {
			LoggerWrapperFactory.setDefaultConsoleLogConfiguration(Level.ERROR);
						
			LoggerManager.initLogger();
			
			logCore = LoggerWrapperFactory.getLogger("org.openspcoop2.monitor.engine.statistic");
			
			logCore.info("Avvio thread TimerStatisticheThread ...");
			TimerStatisticheThread statThread = new TimerStatisticheThread(new StatisticsConfig(true));
			statThread.start();
			logCore.info("TimerStatisticheThread avviato con successo");
		} catch (Exception e) {
			if(logCore==null){
				logCore = LoggerWrapperFactory.getLogger(StatisticProcessor.class);
			}
			logCore.error("TimerStatisticheThread ha riscontrato un errore: "+e.getMessage(),e);
		}
	}

}
