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
package it.gov.fatturapa.sdi.fatturapa.v1_1.constants;

import java.io.Serializable;
import java.util.List;

import org.openspcoop2.generic_project.beans.IEnumeration;

/**     
 * Enumeration dell'elemento NaturaType xsd (tipo:string) 
 *
 * @author Poli Andrea (poli@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
@javax.xml.bind.annotation.XmlType(name = "NaturaType")
@javax.xml.bind.annotation.XmlEnum(String.class)
public enum NaturaType implements IEnumeration , Serializable , Cloneable {

	@javax.xml.bind.annotation.XmlEnumValue("N1")
	N1 ("N1"),
	@javax.xml.bind.annotation.XmlEnumValue("N2")
	N2 ("N2"),
	@javax.xml.bind.annotation.XmlEnumValue("N3")
	N3 ("N3"),
	@javax.xml.bind.annotation.XmlEnumValue("N4")
	N4 ("N4"),
	@javax.xml.bind.annotation.XmlEnumValue("N5")
	N5 ("N5"),
	@javax.xml.bind.annotation.XmlEnumValue("N6")
	N6 ("N6");
	
	
	/** Value */
	private String value;
	@Override
	public String getValue()
	{
		return this.value;
	}


	/** Official Constructor */
	NaturaType(String value)
	{
		this.value = value;
	}


	
	@Override
	public String toString(){
		return this.value;
	}
	public boolean equals(NaturaType object){
		if(object==null)
			return false;
		if(object.getValue()==null)
			return false;
		return object.getValue().equals(this.getValue());	
	}
	public boolean equals(String object){
		if(object==null)
			return false;
		return object.equals(this.getValue());	
	}
	
		
	
	/** compatibility with the generated bean (reflection) */
	public boolean equals(Object object,List<String> fieldsNotCheck){
		if( !(object instanceof NaturaType) ){
			throw new RuntimeException("Wrong type: "+object.getClass().getName());
		}
		return this.equals(((NaturaType)object));
	}
	public String toString(boolean reportHTML){
		return toString();
	}
  	public String toString(boolean reportHTML,List<String> fieldsNotIncluded){
  		return toString();
  	}
  	public String diff(Object object,StringBuffer bf,boolean reportHTML){
		return bf.toString();
	}
	public String diff(Object object,StringBuffer bf,boolean reportHTML,List<String> fieldsNotIncluded){
		return bf.toString();
	}
	
	
	/** Utilities */
	
	public static String[] toArray(){
		String[] res = new String[values().length];
		int i=0;
		for (NaturaType tmp : values()) {
			res[i]=tmp.getValue();
			i++;
		}
		return res;
	}	
	public static String[] toStringArray(){
		String[] res = new String[values().length];
		int i=0;
		for (NaturaType tmp : values()) {
			res[i]=tmp.toString();
			i++;
		}
		return res;
	}
	public static String[] toEnumNameArray(){
		String[] res = new String[values().length];
		int i=0;
		for (NaturaType tmp : values()) {
			res[i]=tmp.name();
			i++;
		}
		return res;
	}
	
	public static boolean contains(String value){
		return toEnumConstant(value)!=null;
	}
	
	public static NaturaType toEnumConstant(String value){
		NaturaType res = null;
		if(NaturaType.N1.getValue().equals(value)){
			res = NaturaType.N1;
		}else if(NaturaType.N2.getValue().equals(value)){
			res = NaturaType.N2;
		}else if(NaturaType.N3.getValue().equals(value)){
			res = NaturaType.N3;
		}else if(NaturaType.N4.getValue().equals(value)){
			res = NaturaType.N4;
		}else if(NaturaType.N5.getValue().equals(value)){
			res = NaturaType.N5;
		}else if(NaturaType.N6.getValue().equals(value)){
			res = NaturaType.N6;
		}
		return res;
	}
	
	public static IEnumeration toEnumConstantFromString(String value){
		NaturaType res = null;
		if(NaturaType.N1.toString().equals(value)){
			res = NaturaType.N1;
		}else if(NaturaType.N2.toString().equals(value)){
			res = NaturaType.N2;
		}else if(NaturaType.N3.toString().equals(value)){
			res = NaturaType.N3;
		}else if(NaturaType.N4.toString().equals(value)){
			res = NaturaType.N4;
		}else if(NaturaType.N5.toString().equals(value)){
			res = NaturaType.N5;
		}else if(NaturaType.N6.toString().equals(value)){
			res = NaturaType.N6;
		}
		return res;
	}
}
