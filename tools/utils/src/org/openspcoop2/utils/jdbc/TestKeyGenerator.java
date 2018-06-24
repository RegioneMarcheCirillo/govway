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
package org.openspcoop2.utils.jdbc;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import org.apache.logging.log4j.Level;
import org.openspcoop2.utils.LoggerWrapperFactory;
import org.openspcoop2.utils.TipiDatabase;
import org.openspcoop2.utils.date.DateManager;
import org.openspcoop2.utils.resources.ClassLoaderUtilities;
import org.slf4j.Logger;

/**
 * TestKeyGenerator
 *
 *
 * @author Andrea Poli (apoli@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class TestKeyGenerator {

	private static Logger log = null;
	
	public static void main(String[] args) throws Exception{
		
		/**
		 * Tabelle
		 * 
		 * ---------------------------
		 * postgresql (non supporta getGeneratedKeys)
		 * CREATE SEQUENCE seq_prova start 1 increment 1 maxvalue 9223372036854775807 minvalue 1 cache 1 NO CYCLE;
		 * CREATE TABLE prova (
		 *      descrizione VARCHAR(255) NOT NULL,
		 * 		id BIGINT DEFAULT nextval('seq_prova') NOT NULL
		 * );
		 * ---------------------------
		 * 
		 * ---------------------------
		 * mysql (supporta getGeneratedKeys)
		 * CREATE TABLE prova (
		 *      descrizione VARCHAR(255) NOT NULL,
		 * 		id BIGINT AUTO_INCREMENT PRIMARY KEY
		 * );
		 * ---------------------------
		 * 
		 * ---------------------------
		 * oracle (non supporta getGeneratedKeys)
		 * CREATE SEQUENCE seq_prova MINVALUE 1 MAXVALUE 9223372036854775807 START WITH 1 INCREMENT BY 1 CACHE 2 NOCYCLE;
		 * CREATE TABLE prova (
		 *      descrizione VARCHAR(255) NOT NULL,
		 * 		id NUMBER
		 * );
		 * CREATE TRIGGER trg_prova
		 * BEFORE
		 * insert on prova
		 * for each row
		 * begin
		 * 	IF (:new.id IS NULL) THEN
		 * 		SELECT seq_prova.nextval INTO :new.id
		 * 			FROM DUAL;
		 * 	END IF;
		 * end;
		 * /
		 * ---------------------------
		 * 
		 * ---------------------------
		 * hsql (non supporta getGeneratedKeys)
		 * CREATE SEQUENCE seq_prova AS BIGINT START WITH 1 INCREMENT BY 1 ; -- (Scommentare in hsql 2.x) NO CYCLE;
		 * CREATE TABLE prova (
		 *      descrizione VARCHAR(255) NOT NULL,
		 * 		id BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 1)
		 * );
		 * CREATE TABLE prova_init_seq (id BIGINT);
		 * INSERT INTO prova_init_seq VALUES (NEXT VALUE FOR seq_prova);
		 * ---------------------------
		 * 
		 * ---------------------------
		 * sqlserver (supporta getGeneratedKeys)
		 * CREATE TABLE prova (
		 *      descrizione VARCHAR(255) NOT NULL,
		 * 		id BIGINT IDENTITY
		 * );
		 * ---------------------------
		 * 
		 * ---------------------------
		 * db2 (supporta getGeneratedKeys)
		 * CREATE TABLE prova (
		 *      descrizione VARCHAR(255) NOT NULL,
		 * 		id BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1 NO CYCLE NO CACHE)
		 * );
		 * ---------------------------
		 */
		
		File logFile = File.createTempFile("runKeyGeneratorTest_", ".log");
		System.out.println("LogMessages write in "+logFile.getAbsolutePath());
		LoggerWrapperFactory.setDefaultLogConfiguration(Level.ALL, false, null, logFile, "%m %n");
		log = LoggerWrapperFactory.getLogger(TestKeyGenerator.class);
		
		DateManager.initializeDataManager(org.openspcoop2.utils.date.SystemDate.class.getName(), new Properties(), log);
		
		String url = null;
		String driver = null;
		String userName = null;
		String password = null;
		TipiDatabase tipoDatabase = TipiDatabase.toEnumConstant(args[0].trim());
		IKeyGeneratorObject keyGeneratorObject = null;
		
		switch (tipoDatabase) {
		case POSTGRESQL:
			url = "jdbc:postgresql://localhost/prova";
			driver = "org.postgresql.Driver";
			userName = "openspcoop2";
			password = "openspcoop2";
			keyGeneratorObject = new CustomKeyGeneratorObject("prova", "id","seq_prova", "prova_init_seq");
			break;
		case MYSQL:
			url = "jdbc:mysql://localhost/prova";
			driver = "com.mysql.jdbc.Driver";
			userName = "openspcoop2";
			password = "openspcoop2";
			keyGeneratorObject = new CustomKeyGeneratorObject("prova", "id","seq_prova", "prova_init_seq");
			break;
		case ORACLE:
			url = "jdbc:oracle:thin:@localhost:1521:XE";
			driver = "oracle.jdbc.OracleDriver";
			userName = "prova";
			password = "prova";
			keyGeneratorObject = new CustomKeyGeneratorObject("prova", "id","seq_prova", "prova_init_seq");
			break;
		case HSQL:
			url = "jdbc:hsqldb:hsql://localhost:9001/";
			driver = "org.hsqldb.jdbcDriver";
			userName = "sa";
			password = "";
			keyGeneratorObject = new CustomKeyGeneratorObject("prova", "id","seq_prova", "prova_init_seq");
			break;
		case SQLSERVER:
			url = "jdbc:sqlserver://localhost:1433;databaseName=prova";
			driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
			userName = "openspcoop2";
			password = "openspcoop2";
			keyGeneratorObject = new CustomKeyGeneratorObject("prova", "id","seq_prova", "prova_init_seq");
			break;
		case DB2:
			url = "jdbc:db2://127.0.0.1:50000/prova";
			driver = "com.ibm.db2.jcc.DB2Driver";
			userName = "openspcoop2";
			password = "openspcoop2";
			keyGeneratorObject = new CustomKeyGeneratorObject("prova", "id","seq_prova", "prova_init_seq");
			break;
		default:
			break;
		}
		if(args.length>1){
			String urlCustom = args[1].trim();
			if(!"${url}".equals(urlCustom)){
				url = urlCustom;
			}
		}
		if(args.length>2){
			String usernameCustom = args[2].trim();
			if(!"${username}".equals(usernameCustom)){
				userName = usernameCustom;
			}
		}
		if(args.length>3){
			String passwordCustom = args[3].trim();
			if(!"${password}".equals(passwordCustom)){
				password = passwordCustom;
			}
		}

		
		ClassLoaderUtilities.newInstance(driver);
	    Connection con = null;
	    Statement stmtDelete = null;
	    Statement stmtQuery = null;
	    ResultSet rsQuery = null;
	    try{
	    	con = DriverManager.getConnection(url, userName, password);
	    	
	    	// step1. Elimino tutte le entries
	    	String delete = "delete from prova";
	    	stmtDelete = con.createStatement();
	    	stmtDelete.execute(delete);
	    	
	    	// step2. Aggiungo prima entry
	    	long id1 = InsertAndGeneratedKey.insertAndReturnGeneratedKey(con, tipoDatabase, keyGeneratorObject, 
	    			new InsertAndGeneratedKeyObject("descrizione", "esempio1", InsertAndGeneratedKeyJDBCType.STRING));
	    	if(id1<=0){
	    		throw new Exception("Prima insert non effettuata?? Return id is ["+id1+"]");
	    	}
	    	
	    	// step3. Aggiungo seconda entry
	    	long id2 = InsertAndGeneratedKey.insertAndReturnGeneratedKey(con, tipoDatabase, keyGeneratorObject, 
	    			new InsertAndGeneratedKeyObject("descrizione", "esempio2", InsertAndGeneratedKeyJDBCType.STRING));
	    	if(id2<=0){
	    		throw new Exception("Seconda insert non effettuata?? Return id is ["+id2+"]");
	    	}
	    	
	    	// check
	    	if(id1==id2){
	    		throw new Exception("Successive insert hanno prodotto lo stesso primary key id?? ["+id1+"]");
	    	}
	    	
	    	// count.
	    	String count = "select count(*) from prova";
	    	stmtQuery = con.createStatement();
	    	rsQuery = stmtQuery.executeQuery(count);
	    	if(rsQuery.next()){
	    		long conteggio = rsQuery.getLong(1);
	    		if(conteggio!=2){
	    			throw new Exception("Risultato ("+conteggio+") differente da quello atteso (2)");
	    		}
	    	}else{
	    		throw new Exception("Nessuna insert effettuata");
	    	}
	    	
	    	log.info("Test effettuato correttamente (id generati: first["+id1+"] second["+id2+"])");

	    	
	    }finally{
	    	try{
	    		stmtDelete.close();
	    	}catch(Exception eClose){}
	    	try{
	    		rsQuery.close();
	    	}catch(Exception eClose){}
	    	try{
	    		stmtQuery.close();
	    	}catch(Exception eClose){}
	    	try{
	    		con.close();
	    	}catch(Exception eClose){}
	    }
	}

}
