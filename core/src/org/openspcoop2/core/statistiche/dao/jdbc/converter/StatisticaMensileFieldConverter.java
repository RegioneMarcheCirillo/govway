/*
 * OpenSPCoop - Customizable API Gateway 
 * http://www.openspcoop2.org
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
package org.openspcoop2.core.statistiche.dao.jdbc.converter;

import org.openspcoop2.generic_project.beans.IField;
import org.openspcoop2.generic_project.beans.IModel;
import org.openspcoop2.generic_project.exception.ExpressionException;
import org.openspcoop2.generic_project.expression.impl.sql.AbstractSQLFieldConverter;
import org.openspcoop2.utils.TipiDatabase;

import org.openspcoop2.core.statistiche.StatisticaMensile;


/**     
 * StatisticaMensileFieldConverter
 *
 * @author Poli Andrea (poli@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class StatisticaMensileFieldConverter extends AbstractSQLFieldConverter {

	private TipiDatabase databaseType;
	
	public StatisticaMensileFieldConverter(String databaseType){
		this.databaseType = TipiDatabase.toEnumConstant(databaseType);
	}
	public StatisticaMensileFieldConverter(TipiDatabase databaseType){
		this.databaseType = databaseType;
	}


	@Override
	public IModel<?> getRootModel() throws ExpressionException {
		return StatisticaMensile.model();
	}
	
	@Override
	public TipiDatabase getDatabaseType() throws ExpressionException {
		return this.databaseType;
	}
	


	@Override
	public String toColumn(IField field,boolean returnAlias,boolean appendTablePrefix) throws ExpressionException {
		
		// In the case of columns with alias, using parameter returnAlias​​, 
		// it is possible to drive the choice whether to return only the alias or 
		// the full definition of the column containing the alias
		
		if(field.equals(StatisticaMensile.model().STATISTICA_BASE.DATA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".data";
			}else{
				return "data";
			}
		}
		if(field.equals(StatisticaMensile.model().STATISTICA_BASE.ID_PORTA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".id_porta";
			}else{
				return "id_porta";
			}
		}
		if(field.equals(StatisticaMensile.model().STATISTICA_BASE.TIPO_PORTA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".tipo_porta";
			}else{
				return "tipo_porta";
			}
		}
		if(field.equals(StatisticaMensile.model().STATISTICA_BASE.TIPO_MITTENTE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".tipo_mittente";
			}else{
				return "tipo_mittente";
			}
		}
		if(field.equals(StatisticaMensile.model().STATISTICA_BASE.MITTENTE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".mittente";
			}else{
				return "mittente";
			}
		}
		if(field.equals(StatisticaMensile.model().STATISTICA_BASE.TIPO_DESTINATARIO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".tipo_destinatario";
			}else{
				return "tipo_destinatario";
			}
		}
		if(field.equals(StatisticaMensile.model().STATISTICA_BASE.DESTINATARIO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".destinatario";
			}else{
				return "destinatario";
			}
		}
		if(field.equals(StatisticaMensile.model().STATISTICA_BASE.TIPO_SERVIZIO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".tipo_servizio";
			}else{
				return "tipo_servizio";
			}
		}
		if(field.equals(StatisticaMensile.model().STATISTICA_BASE.SERVIZIO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".servizio";
			}else{
				return "servizio";
			}
		}
		if(field.equals(StatisticaMensile.model().STATISTICA_BASE.VERSIONE_SERVIZIO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".versione_servizio";
			}else{
				return "versione_servizio";
			}
		}
		if(field.equals(StatisticaMensile.model().STATISTICA_BASE.AZIONE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".azione";
			}else{
				return "azione";
			}
		}
		if(field.equals(StatisticaMensile.model().STATISTICA_BASE.SERVIZIO_APPLICATIVO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".servizio_applicativo";
			}else{
				return "servizio_applicativo";
			}
		}
		if(field.equals(StatisticaMensile.model().STATISTICA_BASE.ESITO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".esito";
			}else{
				return "esito";
			}
		}
		if(field.equals(StatisticaMensile.model().STATISTICA_BASE.ESITO_CONTESTO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".esito_contesto";
			}else{
				return "esito_contesto";
			}
		}
		if(field.equals(StatisticaMensile.model().STATISTICA_BASE.NUMERO_TRANSAZIONI)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".richieste";
			}else{
				return "richieste";
			}
		}
		if(field.equals(StatisticaMensile.model().STATISTICA_BASE.DIMENSIONI_BYTES_BANDA_COMPLESSIVA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".bytes_banda_complessiva";
			}else{
				return "bytes_banda_complessiva";
			}
		}
		if(field.equals(StatisticaMensile.model().STATISTICA_BASE.DIMENSIONI_BYTES_BANDA_INTERNA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".bytes_banda_interna";
			}else{
				return "bytes_banda_interna";
			}
		}
		if(field.equals(StatisticaMensile.model().STATISTICA_BASE.DIMENSIONI_BYTES_BANDA_ESTERNA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".bytes_banda_esterna";
			}else{
				return "bytes_banda_esterna";
			}
		}
		if(field.equals(StatisticaMensile.model().STATISTICA_BASE.LATENZA_TOTALE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".latenza_totale";
			}else{
				return "latenza_totale";
			}
		}
		if(field.equals(StatisticaMensile.model().STATISTICA_BASE.LATENZA_PORTA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".latenza_porta";
			}else{
				return "latenza_porta";
			}
		}
		if(field.equals(StatisticaMensile.model().STATISTICA_BASE.LATENZA_SERVIZIO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".latenza_servizio";
			}else{
				return "latenza_servizio";
			}
		}
		if(field.equals(StatisticaMensile.model().STATISTICA_MENSILE_CONTENUTI.DATA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".data";
			}else{
				return "data";
			}
		}
		if(field.equals(StatisticaMensile.model().STATISTICA_MENSILE_CONTENUTI.RISORSA_NOME)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".risorsa_nome";
			}else{
				return "risorsa_nome";
			}
		}
		if(field.equals(StatisticaMensile.model().STATISTICA_MENSILE_CONTENUTI.RISORSA_VALORE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".risorsa_valore";
			}else{
				return "risorsa_valore";
			}
		}
		if(field.equals(StatisticaMensile.model().STATISTICA_MENSILE_CONTENUTI.FILTRO_NOME_1)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".filtro_nome_1";
			}else{
				return "filtro_nome_1";
			}
		}
		if(field.equals(StatisticaMensile.model().STATISTICA_MENSILE_CONTENUTI.FILTRO_VALORE_1)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".filtro_valore_1";
			}else{
				return "filtro_valore_1";
			}
		}
		if(field.equals(StatisticaMensile.model().STATISTICA_MENSILE_CONTENUTI.FILTRO_NOME_2)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".filtro_nome_2";
			}else{
				return "filtro_nome_2";
			}
		}
		if(field.equals(StatisticaMensile.model().STATISTICA_MENSILE_CONTENUTI.FILTRO_VALORE_2)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".filtro_valore_2";
			}else{
				return "filtro_valore_2";
			}
		}
		if(field.equals(StatisticaMensile.model().STATISTICA_MENSILE_CONTENUTI.FILTRO_NOME_3)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".filtro_nome_3";
			}else{
				return "filtro_nome_3";
			}
		}
		if(field.equals(StatisticaMensile.model().STATISTICA_MENSILE_CONTENUTI.FILTRO_VALORE_3)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".filtro_valore_3";
			}else{
				return "filtro_valore_3";
			}
		}
		if(field.equals(StatisticaMensile.model().STATISTICA_MENSILE_CONTENUTI.FILTRO_NOME_4)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".filtro_nome_4";
			}else{
				return "filtro_nome_4";
			}
		}
		if(field.equals(StatisticaMensile.model().STATISTICA_MENSILE_CONTENUTI.FILTRO_VALORE_4)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".filtro_valore_4";
			}else{
				return "filtro_valore_4";
			}
		}
		if(field.equals(StatisticaMensile.model().STATISTICA_MENSILE_CONTENUTI.FILTRO_NOME_5)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".filtro_nome_5";
			}else{
				return "filtro_nome_5";
			}
		}
		if(field.equals(StatisticaMensile.model().STATISTICA_MENSILE_CONTENUTI.FILTRO_VALORE_5)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".filtro_valore_5";
			}else{
				return "filtro_valore_5";
			}
		}
		if(field.equals(StatisticaMensile.model().STATISTICA_MENSILE_CONTENUTI.FILTRO_NOME_6)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".filtro_nome_6";
			}else{
				return "filtro_nome_6";
			}
		}
		if(field.equals(StatisticaMensile.model().STATISTICA_MENSILE_CONTENUTI.FILTRO_VALORE_6)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".filtro_valore_6";
			}else{
				return "filtro_valore_6";
			}
		}
		if(field.equals(StatisticaMensile.model().STATISTICA_MENSILE_CONTENUTI.FILTRO_NOME_7)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".filtro_nome_7";
			}else{
				return "filtro_nome_7";
			}
		}
		if(field.equals(StatisticaMensile.model().STATISTICA_MENSILE_CONTENUTI.FILTRO_VALORE_7)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".filtro_valore_7";
			}else{
				return "filtro_valore_7";
			}
		}
		if(field.equals(StatisticaMensile.model().STATISTICA_MENSILE_CONTENUTI.FILTRO_NOME_8)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".filtro_nome_8";
			}else{
				return "filtro_nome_8";
			}
		}
		if(field.equals(StatisticaMensile.model().STATISTICA_MENSILE_CONTENUTI.FILTRO_VALORE_8)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".filtro_valore_8";
			}else{
				return "filtro_valore_8";
			}
		}
		if(field.equals(StatisticaMensile.model().STATISTICA_MENSILE_CONTENUTI.FILTRO_NOME_9)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".filtro_nome_9";
			}else{
				return "filtro_nome_9";
			}
		}
		if(field.equals(StatisticaMensile.model().STATISTICA_MENSILE_CONTENUTI.FILTRO_VALORE_9)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".filtro_valore_9";
			}else{
				return "filtro_valore_9";
			}
		}
		if(field.equals(StatisticaMensile.model().STATISTICA_MENSILE_CONTENUTI.FILTRO_NOME_10)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".filtro_nome_10";
			}else{
				return "filtro_nome_10";
			}
		}
		if(field.equals(StatisticaMensile.model().STATISTICA_MENSILE_CONTENUTI.FILTRO_VALORE_10)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".filtro_valore_10";
			}else{
				return "filtro_valore_10";
			}
		}
		if(field.equals(StatisticaMensile.model().STATISTICA_MENSILE_CONTENUTI.NUMERO_TRANSAZIONI)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".richieste";
			}else{
				return "richieste";
			}
		}
		if(field.equals(StatisticaMensile.model().STATISTICA_MENSILE_CONTENUTI.DIMENSIONI_BYTES_BANDA_COMPLESSIVA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".bytes_banda_complessiva";
			}else{
				return "bytes_banda_complessiva";
			}
		}
		if(field.equals(StatisticaMensile.model().STATISTICA_MENSILE_CONTENUTI.DIMENSIONI_BYTES_BANDA_INTERNA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".bytes_banda_interna";
			}else{
				return "bytes_banda_interna";
			}
		}
		if(field.equals(StatisticaMensile.model().STATISTICA_MENSILE_CONTENUTI.DIMENSIONI_BYTES_BANDA_ESTERNA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".bytes_banda_esterna";
			}else{
				return "bytes_banda_esterna";
			}
		}
		if(field.equals(StatisticaMensile.model().STATISTICA_MENSILE_CONTENUTI.LATENZA_TOTALE)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".latenza_totale";
			}else{
				return "latenza_totale";
			}
		}
		if(field.equals(StatisticaMensile.model().STATISTICA_MENSILE_CONTENUTI.LATENZA_PORTA)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".latenza_porta";
			}else{
				return "latenza_porta";
			}
		}
		if(field.equals(StatisticaMensile.model().STATISTICA_MENSILE_CONTENUTI.LATENZA_SERVIZIO)){
			if(appendTablePrefix){
				return this.toAliasTable(field)+".latenza_servizio";
			}else{
				return "latenza_servizio";
			}
		}


		return super.toColumn(field,returnAlias,appendTablePrefix);
		
	}
	
	@Override
	public String toTable(IField field,boolean returnAlias) throws ExpressionException {
		
		// In the case of table with alias, using parameter returnAlias​​, 
		// it is possible to drive the choice whether to return only the alias or 
		// the full definition of the table containing the alias
		
		if(field.equals(StatisticaMensile.model().STATISTICA_BASE.DATA)){
			return this.toTable(StatisticaMensile.model().STATISTICA_BASE, returnAlias);
		}
		if(field.equals(StatisticaMensile.model().STATISTICA_BASE.ID_PORTA)){
			return this.toTable(StatisticaMensile.model().STATISTICA_BASE, returnAlias);
		}
		if(field.equals(StatisticaMensile.model().STATISTICA_BASE.TIPO_PORTA)){
			return this.toTable(StatisticaMensile.model().STATISTICA_BASE, returnAlias);
		}
		if(field.equals(StatisticaMensile.model().STATISTICA_BASE.TIPO_MITTENTE)){
			return this.toTable(StatisticaMensile.model().STATISTICA_BASE, returnAlias);
		}
		if(field.equals(StatisticaMensile.model().STATISTICA_BASE.MITTENTE)){
			return this.toTable(StatisticaMensile.model().STATISTICA_BASE, returnAlias);
		}
		if(field.equals(StatisticaMensile.model().STATISTICA_BASE.TIPO_DESTINATARIO)){
			return this.toTable(StatisticaMensile.model().STATISTICA_BASE, returnAlias);
		}
		if(field.equals(StatisticaMensile.model().STATISTICA_BASE.DESTINATARIO)){
			return this.toTable(StatisticaMensile.model().STATISTICA_BASE, returnAlias);
		}
		if(field.equals(StatisticaMensile.model().STATISTICA_BASE.TIPO_SERVIZIO)){
			return this.toTable(StatisticaMensile.model().STATISTICA_BASE, returnAlias);
		}
		if(field.equals(StatisticaMensile.model().STATISTICA_BASE.SERVIZIO)){
			return this.toTable(StatisticaMensile.model().STATISTICA_BASE, returnAlias);
		}
		if(field.equals(StatisticaMensile.model().STATISTICA_BASE.VERSIONE_SERVIZIO)){
			return this.toTable(StatisticaMensile.model().STATISTICA_BASE, returnAlias);
		}
		if(field.equals(StatisticaMensile.model().STATISTICA_BASE.AZIONE)){
			return this.toTable(StatisticaMensile.model().STATISTICA_BASE, returnAlias);
		}
		if(field.equals(StatisticaMensile.model().STATISTICA_BASE.SERVIZIO_APPLICATIVO)){
			return this.toTable(StatisticaMensile.model().STATISTICA_BASE, returnAlias);
		}
		if(field.equals(StatisticaMensile.model().STATISTICA_BASE.ESITO)){
			return this.toTable(StatisticaMensile.model().STATISTICA_BASE, returnAlias);
		}
		if(field.equals(StatisticaMensile.model().STATISTICA_BASE.ESITO_CONTESTO)){
			return this.toTable(StatisticaMensile.model().STATISTICA_BASE, returnAlias);
		}
		if(field.equals(StatisticaMensile.model().STATISTICA_BASE.NUMERO_TRANSAZIONI)){
			return this.toTable(StatisticaMensile.model().STATISTICA_BASE, returnAlias);
		}
		if(field.equals(StatisticaMensile.model().STATISTICA_BASE.DIMENSIONI_BYTES_BANDA_COMPLESSIVA)){
			return this.toTable(StatisticaMensile.model().STATISTICA_BASE, returnAlias);
		}
		if(field.equals(StatisticaMensile.model().STATISTICA_BASE.DIMENSIONI_BYTES_BANDA_INTERNA)){
			return this.toTable(StatisticaMensile.model().STATISTICA_BASE, returnAlias);
		}
		if(field.equals(StatisticaMensile.model().STATISTICA_BASE.DIMENSIONI_BYTES_BANDA_ESTERNA)){
			return this.toTable(StatisticaMensile.model().STATISTICA_BASE, returnAlias);
		}
		if(field.equals(StatisticaMensile.model().STATISTICA_BASE.LATENZA_TOTALE)){
			return this.toTable(StatisticaMensile.model().STATISTICA_BASE, returnAlias);
		}
		if(field.equals(StatisticaMensile.model().STATISTICA_BASE.LATENZA_PORTA)){
			return this.toTable(StatisticaMensile.model().STATISTICA_BASE, returnAlias);
		}
		if(field.equals(StatisticaMensile.model().STATISTICA_BASE.LATENZA_SERVIZIO)){
			return this.toTable(StatisticaMensile.model().STATISTICA_BASE, returnAlias);
		}
		if(field.equals(StatisticaMensile.model().STATISTICA_MENSILE_CONTENUTI.DATA)){
			return this.toTable(StatisticaMensile.model().STATISTICA_MENSILE_CONTENUTI, returnAlias);
		}
		if(field.equals(StatisticaMensile.model().STATISTICA_MENSILE_CONTENUTI.RISORSA_NOME)){
			return this.toTable(StatisticaMensile.model().STATISTICA_MENSILE_CONTENUTI, returnAlias);
		}
		if(field.equals(StatisticaMensile.model().STATISTICA_MENSILE_CONTENUTI.RISORSA_VALORE)){
			return this.toTable(StatisticaMensile.model().STATISTICA_MENSILE_CONTENUTI, returnAlias);
		}
		if(field.equals(StatisticaMensile.model().STATISTICA_MENSILE_CONTENUTI.FILTRO_NOME_1)){
			return this.toTable(StatisticaMensile.model().STATISTICA_MENSILE_CONTENUTI, returnAlias);
		}
		if(field.equals(StatisticaMensile.model().STATISTICA_MENSILE_CONTENUTI.FILTRO_VALORE_1)){
			return this.toTable(StatisticaMensile.model().STATISTICA_MENSILE_CONTENUTI, returnAlias);
		}
		if(field.equals(StatisticaMensile.model().STATISTICA_MENSILE_CONTENUTI.FILTRO_NOME_2)){
			return this.toTable(StatisticaMensile.model().STATISTICA_MENSILE_CONTENUTI, returnAlias);
		}
		if(field.equals(StatisticaMensile.model().STATISTICA_MENSILE_CONTENUTI.FILTRO_VALORE_2)){
			return this.toTable(StatisticaMensile.model().STATISTICA_MENSILE_CONTENUTI, returnAlias);
		}
		if(field.equals(StatisticaMensile.model().STATISTICA_MENSILE_CONTENUTI.FILTRO_NOME_3)){
			return this.toTable(StatisticaMensile.model().STATISTICA_MENSILE_CONTENUTI, returnAlias);
		}
		if(field.equals(StatisticaMensile.model().STATISTICA_MENSILE_CONTENUTI.FILTRO_VALORE_3)){
			return this.toTable(StatisticaMensile.model().STATISTICA_MENSILE_CONTENUTI, returnAlias);
		}
		if(field.equals(StatisticaMensile.model().STATISTICA_MENSILE_CONTENUTI.FILTRO_NOME_4)){
			return this.toTable(StatisticaMensile.model().STATISTICA_MENSILE_CONTENUTI, returnAlias);
		}
		if(field.equals(StatisticaMensile.model().STATISTICA_MENSILE_CONTENUTI.FILTRO_VALORE_4)){
			return this.toTable(StatisticaMensile.model().STATISTICA_MENSILE_CONTENUTI, returnAlias);
		}
		if(field.equals(StatisticaMensile.model().STATISTICA_MENSILE_CONTENUTI.FILTRO_NOME_5)){
			return this.toTable(StatisticaMensile.model().STATISTICA_MENSILE_CONTENUTI, returnAlias);
		}
		if(field.equals(StatisticaMensile.model().STATISTICA_MENSILE_CONTENUTI.FILTRO_VALORE_5)){
			return this.toTable(StatisticaMensile.model().STATISTICA_MENSILE_CONTENUTI, returnAlias);
		}
		if(field.equals(StatisticaMensile.model().STATISTICA_MENSILE_CONTENUTI.FILTRO_NOME_6)){
			return this.toTable(StatisticaMensile.model().STATISTICA_MENSILE_CONTENUTI, returnAlias);
		}
		if(field.equals(StatisticaMensile.model().STATISTICA_MENSILE_CONTENUTI.FILTRO_VALORE_6)){
			return this.toTable(StatisticaMensile.model().STATISTICA_MENSILE_CONTENUTI, returnAlias);
		}
		if(field.equals(StatisticaMensile.model().STATISTICA_MENSILE_CONTENUTI.FILTRO_NOME_7)){
			return this.toTable(StatisticaMensile.model().STATISTICA_MENSILE_CONTENUTI, returnAlias);
		}
		if(field.equals(StatisticaMensile.model().STATISTICA_MENSILE_CONTENUTI.FILTRO_VALORE_7)){
			return this.toTable(StatisticaMensile.model().STATISTICA_MENSILE_CONTENUTI, returnAlias);
		}
		if(field.equals(StatisticaMensile.model().STATISTICA_MENSILE_CONTENUTI.FILTRO_NOME_8)){
			return this.toTable(StatisticaMensile.model().STATISTICA_MENSILE_CONTENUTI, returnAlias);
		}
		if(field.equals(StatisticaMensile.model().STATISTICA_MENSILE_CONTENUTI.FILTRO_VALORE_8)){
			return this.toTable(StatisticaMensile.model().STATISTICA_MENSILE_CONTENUTI, returnAlias);
		}
		if(field.equals(StatisticaMensile.model().STATISTICA_MENSILE_CONTENUTI.FILTRO_NOME_9)){
			return this.toTable(StatisticaMensile.model().STATISTICA_MENSILE_CONTENUTI, returnAlias);
		}
		if(field.equals(StatisticaMensile.model().STATISTICA_MENSILE_CONTENUTI.FILTRO_VALORE_9)){
			return this.toTable(StatisticaMensile.model().STATISTICA_MENSILE_CONTENUTI, returnAlias);
		}
		if(field.equals(StatisticaMensile.model().STATISTICA_MENSILE_CONTENUTI.FILTRO_NOME_10)){
			return this.toTable(StatisticaMensile.model().STATISTICA_MENSILE_CONTENUTI, returnAlias);
		}
		if(field.equals(StatisticaMensile.model().STATISTICA_MENSILE_CONTENUTI.FILTRO_VALORE_10)){
			return this.toTable(StatisticaMensile.model().STATISTICA_MENSILE_CONTENUTI, returnAlias);
		}
		if(field.equals(StatisticaMensile.model().STATISTICA_MENSILE_CONTENUTI.NUMERO_TRANSAZIONI)){
			return this.toTable(StatisticaMensile.model().STATISTICA_MENSILE_CONTENUTI, returnAlias);
		}
		if(field.equals(StatisticaMensile.model().STATISTICA_MENSILE_CONTENUTI.DIMENSIONI_BYTES_BANDA_COMPLESSIVA)){
			return this.toTable(StatisticaMensile.model().STATISTICA_MENSILE_CONTENUTI, returnAlias);
		}
		if(field.equals(StatisticaMensile.model().STATISTICA_MENSILE_CONTENUTI.DIMENSIONI_BYTES_BANDA_INTERNA)){
			return this.toTable(StatisticaMensile.model().STATISTICA_MENSILE_CONTENUTI, returnAlias);
		}
		if(field.equals(StatisticaMensile.model().STATISTICA_MENSILE_CONTENUTI.DIMENSIONI_BYTES_BANDA_ESTERNA)){
			return this.toTable(StatisticaMensile.model().STATISTICA_MENSILE_CONTENUTI, returnAlias);
		}
		if(field.equals(StatisticaMensile.model().STATISTICA_MENSILE_CONTENUTI.LATENZA_TOTALE)){
			return this.toTable(StatisticaMensile.model().STATISTICA_MENSILE_CONTENUTI, returnAlias);
		}
		if(field.equals(StatisticaMensile.model().STATISTICA_MENSILE_CONTENUTI.LATENZA_PORTA)){
			return this.toTable(StatisticaMensile.model().STATISTICA_MENSILE_CONTENUTI, returnAlias);
		}
		if(field.equals(StatisticaMensile.model().STATISTICA_MENSILE_CONTENUTI.LATENZA_SERVIZIO)){
			return this.toTable(StatisticaMensile.model().STATISTICA_MENSILE_CONTENUTI, returnAlias);
		}


		return super.toTable(field,returnAlias);
		
	}

	@Override
	public String toTable(IModel<?> model,boolean returnAlias) throws ExpressionException {
		
		// In the case of table with alias, using parameter returnAlias​​, 
		// it is possible to drive the choice whether to return only the alias or 
		// the full definition of the table containing the alias
		
		if(model.equals(StatisticaMensile.model())){
			return "statistiche_mensili";
		}
		if(model.equals(StatisticaMensile.model().STATISTICA_BASE)){
			return "statistiche_mensili";
		}
		if(model.equals(StatisticaMensile.model().STATISTICA_MENSILE_CONTENUTI)){
			return "stat_mensili_contenuti";
		}


		return super.toTable(model,returnAlias);
		
	}

}
