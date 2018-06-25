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

package org.openspcoop2.web.ctrlstat.servlet.login;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.openspcoop2.web.ctrlstat.core.ControlStationCore;
import org.openspcoop2.web.ctrlstat.costanti.CostantiControlStation;
import org.openspcoop2.web.ctrlstat.servlet.ConsoleHelper;
import org.openspcoop2.web.ctrlstat.servlet.GeneralHelper;
import org.openspcoop2.web.lib.mvc.Costanti;
import org.openspcoop2.web.lib.mvc.ForwardParams;
import org.openspcoop2.web.lib.mvc.GeneralData;
import org.openspcoop2.web.lib.mvc.MessageType;
import org.openspcoop2.web.lib.mvc.PageData;
import org.openspcoop2.web.lib.mvc.Parameter;
import org.openspcoop2.web.lib.mvc.ServletUtils;

/**     
 * MessagePage
 *
 * @author Poli Andrea (poli@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class MessagePage extends Action {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {


		HttpSession session = request.getSession(true);

		GeneralHelper generalHelper = new GeneralHelper(session);

		// Inizializzo PageData
		PageData pd = generalHelper.initPageData();

		// Inizializzo GeneralData
		GeneralData gd = generalHelper.initGeneralData(request);

		try {
			ConsoleHelper consoleHelper = new ConsoleHelper(request, pd, session);
			
			consoleHelper.makeMenu();
			
			String messageText = consoleHelper.getParameter(CostantiControlStation.PARAMETER_MESSAGE_TEXT);
			if(messageText == null) {
				messageText = Costanti.MESSAGGIO_SISTEMA_NON_DISPONIBILE;
			}
			String messageType = consoleHelper.getParameter(CostantiControlStation.PARAMETER_MESSAGE_TYPE);
			MessageType mt = MessageType.ERROR;
			if(messageType != null) {
				try {
					mt = MessageType.fromValue(messageType);
					if(mt == null)
						mt = MessageType.ERROR;
				}catch(Exception e) {
					mt= MessageType.ERROR;
				}
			}
			String messageTitle = consoleHelper.getParameter(CostantiControlStation.PARAMETER_MESSAGE_TITLE);
			String messageBreadcrumbs = consoleHelper.getParameter(CostantiControlStation.PARAMETER_MESSAGE_BREADCRUMB);
			
			if(messageBreadcrumbs!= null) {
				// setto la barra del titolo
				List<Parameter> lstParam = new ArrayList<Parameter>();
				lstParam.add(new Parameter(messageBreadcrumbs, null));
				ServletUtils.setPageDataTitle(pd, lstParam);
			}
			
			// imposto il messaggio da visualizzare
			pd.setMessage(messageText, messageTitle, mt);
			
			ServletUtils.setGeneralAndPageDataIntoSession(session, gd, pd);
			
			// Forward control to the specified success URI
			return ServletUtils.getStrutsForwardEditModeFinished(mapping, LoginCostanti.OBJECT_NAME_MESSAGE_PAGE, ForwardParams.OTHER(""));

		} catch (Exception e) {
			return ServletUtils.getStrutsForwardError(ControlStationCore.getLog(), e, pd, session, gd, mapping, LoginCostanti.OBJECT_NAME_MESSAGE_PAGE, ForwardParams.OTHER(""));
		}
	}
}