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

/**
 * EsitoAggiornamentoUtenteAsincronoSimmetricoWDLResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.openspcoop2.ValidazioneContenutiWS.Service.types;

/**
*
* @author Andrea Poli <apoli@link.it>
 * @author $Author$
 * @version $Rev$, $Date$
*/
@SuppressWarnings({ "rawtypes" })
public class EsitoAggiornamentoUtenteAsincronoSimmetricoWDLResponse  implements java.io.Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -6216953680999778868L;
	private java.lang.String ackRichiestaAsincrona;

    public EsitoAggiornamentoUtenteAsincronoSimmetricoWDLResponse() {
    }

    public EsitoAggiornamentoUtenteAsincronoSimmetricoWDLResponse(
           java.lang.String ackRichiestaAsincrona) {
           this.ackRichiestaAsincrona = ackRichiestaAsincrona;
    }


    /**
     * Gets the ackRichiestaAsincrona value for this EsitoAggiornamentoUtenteAsincronoSimmetricoWDLResponse.
     * 
     * @return ackRichiestaAsincrona
     */
    public java.lang.String getAckRichiestaAsincrona() {
        return this.ackRichiestaAsincrona;
    }


    /**
     * Sets the ackRichiestaAsincrona value for this EsitoAggiornamentoUtenteAsincronoSimmetricoWDLResponse.
     * 
     * @param ackRichiestaAsincrona
     */
    public void setAckRichiestaAsincrona(java.lang.String ackRichiestaAsincrona) {
        this.ackRichiestaAsincrona = ackRichiestaAsincrona;
    }

    private java.lang.Object __equalsCalc = null;
    @Override
	public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof EsitoAggiornamentoUtenteAsincronoSimmetricoWDLResponse)) return false;
        EsitoAggiornamentoUtenteAsincronoSimmetricoWDLResponse other = (EsitoAggiornamentoUtenteAsincronoSimmetricoWDLResponse) obj;
        if (this == obj) return true;
        if (this.__equalsCalc != null) {
            return (this.__equalsCalc == obj);
        }
        this.__equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.ackRichiestaAsincrona==null && other.getAckRichiestaAsincrona()==null) || 
             (this.ackRichiestaAsincrona!=null &&
              this.ackRichiestaAsincrona.equals(other.getAckRichiestaAsincrona())));
        this.__equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    @Override
	public synchronized int hashCode() {
        if (this.__hashCodeCalc) {
            return 0;
        }
        this.__hashCodeCalc = true;
        int _hashCode = 1;
        if (getAckRichiestaAsincrona() != null) {
            _hashCode += getAckRichiestaAsincrona().hashCode();
        }
        this.__hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(EsitoAggiornamentoUtenteAsincronoSimmetricoWDLResponse.class, true);

    static {
        EsitoAggiornamentoUtenteAsincronoSimmetricoWDLResponse.typeDesc.setXmlType(new javax.xml.namespace.QName("http://openspcoop2.org/ValidazioneContenutiWS/Service/types", ">esitoAggiornamentoUtenteAsincronoSimmetricoWDLResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ackRichiestaAsincrona");
        elemField.setXmlName(new javax.xml.namespace.QName("http://openspcoop2.org/ValidazioneContenutiWS/Service/types", "ack-richiesta-asincrona"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        EsitoAggiornamentoUtenteAsincronoSimmetricoWDLResponse.typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return EsitoAggiornamentoUtenteAsincronoSimmetricoWDLResponse.typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, EsitoAggiornamentoUtenteAsincronoSimmetricoWDLResponse.typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, EsitoAggiornamentoUtenteAsincronoSimmetricoWDLResponse.typeDesc);
    }

}
