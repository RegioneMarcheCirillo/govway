/*
 * OpenSPCoop v2 - Customizable SOAP Message Broker 
 * http://www.openspcoop2.org
 * 
 * Copyright (c) 2005-2014 Link.it srl (http://link.it). All rights reserved. 
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
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


package org.openspcoop2.web.loader.servlet;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

/**
 * FileUploadForm
 * 
 * @author Andrea Poli (apoli@link.it)
 * @author Stefano Corallo (corallo@link.it)
 * @author Sandra Giangrandi (sandra@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 * 
 */
@SuppressWarnings("serial")
public class FileUploadForm extends ActionForm
{
	private FormFile theFile;

	/**
	 * @return Returns the theFile.
	 */
	public FormFile getTheFile() {
		return this.theFile;
	}
	/**
	 * @param theFile The FormFile to set.
	 */
	public void setTheFile(FormFile theFile) {
		this.theFile = theFile;
	}
}


