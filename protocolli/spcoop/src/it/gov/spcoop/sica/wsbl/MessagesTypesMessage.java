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
package it.gov.spcoop.sica.wsbl;

import java.io.Serializable;


/** <p>Java class MessagesTypesMessage.
 * 
 * @version $Rev$, $Date$
 * 
 * @author Poli Andrea (apoli@link.it)
 * @author $Author$
 */

public class MessagesTypesMessage extends org.openspcoop2.utils.beans.BaseBean implements Serializable , Cloneable {
  private Long id;

  protected String name;

  protected String type;

  protected String source;

  protected String target;


  public MessagesTypesMessage() {
  }

  public Long getId() {
    if(this.id!=null)
		return this.id;
	else
		return new Long(-1);
  }

  public void setId(Long id) {
    if(id!=null)
		this.id=id;
	else
		this.id=new Long(-1);
  }

  public String getName() {
    if(this.name!=null && ("".equals(this.name)==false)){
		return this.name.trim();
	}else{
		return null;
	}

  }

  public void setName(String name) {
    this.name = name;
  }

  public String getType() {
    if(this.type!=null && ("".equals(this.type)==false)){
		return this.type.trim();
	}else{
		return null;
	}

  }

  public void setType(String type) {
    this.type = type;
  }

  public String getSource() {
    if(this.source!=null && ("".equals(this.source)==false)){
		return this.source.trim();
	}else{
		return null;
	}

  }

  public void setSource(String source) {
    this.source = source;
  }

  public String getTarget() {
    if(this.target!=null && ("".equals(this.target)==false)){
		return this.target.trim();
	}else{
		return null;
	}

  }

  public void setTarget(String target) {
    this.target = target;
  }

  private static final long serialVersionUID = 1L;

	@Override
	public String serialize(org.openspcoop2.utils.beans.WriteToSerializerType type) throws org.openspcoop2.utils.UtilsException {
		if(type!=null && org.openspcoop2.utils.beans.WriteToSerializerType.JAXB.equals(type)){
			throw new org.openspcoop2.utils.UtilsException("Jaxb annotations not generated");
		}
		else{
			return super.serialize(type);
		}
	}
	@Override
	public String toXml_Jaxb() throws org.openspcoop2.utils.UtilsException {
		throw new org.openspcoop2.utils.UtilsException("Jaxb annotations not generated");
	}

  public static final String NAME = "name";

  public static final String TYPE = "type";

  public static final String SOURCE = "source";

  public static final String TARGET = "target";

}
