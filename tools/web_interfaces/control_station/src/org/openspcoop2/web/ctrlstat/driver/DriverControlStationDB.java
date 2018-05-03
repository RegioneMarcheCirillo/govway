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


package org.openspcoop2.web.ctrlstat.driver;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.openspcoop2.core.commons.DBOggettiInUsoUtils;
import org.openspcoop2.core.commons.DBUtils;
import org.openspcoop2.core.commons.ErrorsHandlerCostant;
import org.openspcoop2.core.commons.ISearch;
import org.openspcoop2.core.commons.Liste;
import org.openspcoop2.core.commons.search.utils.RegistroCore;
import org.openspcoop2.core.config.driver.db.DriverConfigurazioneDB;
import org.openspcoop2.core.constants.CostantiDB;
import org.openspcoop2.core.controllo_congestione.AttivazionePolicy;
import org.openspcoop2.core.controllo_congestione.AttivazionePolicyFiltro;
import org.openspcoop2.core.controllo_congestione.AttivazionePolicyRaggruppamento;
import org.openspcoop2.core.controllo_congestione.ConfigurazioneGenerale;
import org.openspcoop2.core.controllo_congestione.ConfigurazionePolicy;
import org.openspcoop2.core.controllo_congestione.IdActivePolicy;
import org.openspcoop2.core.controllo_congestione.IdPolicy;
import org.openspcoop2.core.controllo_congestione.beans.InfoPolicy;
import org.openspcoop2.core.controllo_congestione.constants.RuoloPolicy;
import org.openspcoop2.core.controllo_congestione.constants.TipoApplicabilita;
import org.openspcoop2.core.controllo_congestione.constants.TipoControlloPeriodo;
import org.openspcoop2.core.controllo_congestione.constants.TipoRisorsa;
import org.openspcoop2.core.controllo_congestione.dao.IDBAttivazionePolicyServiceSearch;
import org.openspcoop2.core.controllo_congestione.dao.IDBConfigurazionePolicyServiceSearch;
import org.openspcoop2.core.controllo_congestione.dao.jdbc.JDBCServiceManager;
import org.openspcoop2.core.id.IDPortaApplicativa;
import org.openspcoop2.core.id.IDPortaDelegata;
import org.openspcoop2.core.id.IDRuolo;
import org.openspcoop2.core.id.IDServizio;
import org.openspcoop2.core.id.IDSoggetto;
import org.openspcoop2.core.mapping.DBMappingUtils;
import org.openspcoop2.core.mapping.MappingErogazionePortaApplicativa;
import org.openspcoop2.core.mapping.MappingFruizionePortaDelegata;
import org.openspcoop2.core.registry.AccordoServizioParteComune;
import org.openspcoop2.core.registry.Soggetto;
import org.openspcoop2.core.registry.constants.CostantiRegistroServizi;
import org.openspcoop2.core.registry.driver.IDAccordoCooperazioneFactory;
import org.openspcoop2.core.registry.driver.IDAccordoFactory;
import org.openspcoop2.core.registry.driver.db.DriverRegistroServiziDB;
import org.openspcoop2.core.registry.driver.db.DriverRegistroServiziDB_LIB;
import org.openspcoop2.generic_project.beans.NonNegativeNumber;
import org.openspcoop2.generic_project.exception.NotFoundException;
import org.openspcoop2.generic_project.expression.IExpression;
import org.openspcoop2.generic_project.expression.IPaginatedExpression;
import org.openspcoop2.generic_project.expression.LikeMode;
import org.openspcoop2.generic_project.expression.SortOrder;
import org.openspcoop2.generic_project.utils.ServiceManagerProperties;
import org.openspcoop2.protocol.engine.archive.UtilitiesMappingFruizioneErogazione;
import org.openspcoop2.utils.sql.ISQLQueryObject;
import org.openspcoop2.utils.sql.SQLObjectFactory;
import org.openspcoop2.web.ctrlstat.core.ControlStationLogger;
import org.openspcoop2.web.ctrlstat.core.Search;
import org.openspcoop2.web.ctrlstat.dao.PdDControlStation;
import org.openspcoop2.web.ctrlstat.dao.SoggettoCtrlStat;
import org.openspcoop2.web.lib.audit.DriverAudit;
import org.openspcoop2.web.lib.audit.DriverAuditDBAppender;
import org.openspcoop2.web.lib.users.DriverUsersDB;
import org.slf4j.Logger;

/**
 * DriverControlStationDB
 * 
 * @author Andrea Poli (apoli@link.it)
 * @author Stefano Corallo (corallo@link.it)
 * @author Sandra Giangrandi (sandra@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 * 
 */
public class DriverControlStationDB  {

	/*  F I E L D S P R I V A T I */

	/** Indicazione di una corretta creazione */
	public boolean create = false;

	// Datasource per la connessione al DB
	public DataSource datasource = null;

	// Connection passata al momento della creazione dell'oggetto
	private Connection globalConnection = null;
	// Variabile di controllo del tipo di operazione da effettuare
	// l'autoCommit viene gestito internamente a questa classe
	private boolean atomica = true;

	/** Logger utilizzato per debug. */
	private org.slf4j.Logger log = null;

	// Tipo database passato al momento della creazione dell'oggetto
	private String tipoDB = null;

	/**
	 * Questo driver incapsula i driver DriverConfiguarazioneDB e
	 * DriverRegistroDB utilizza i loro metodi e ne implementa di nuovi
	 */

	private DriverConfigurazioneDB configDB = null;
	private DriverRegistroServiziDB regservDB = null;
	private DriverUsersDB usersDB = null;
	private DriverAudit auditDB = null;
	private DriverAuditDBAppender auditDBappender = null;
	private JDBCServiceManager jdbcServiceManagerControlloCongestione = null;

	private IDAccordoFactory idAccordoFactory = null;
	@SuppressWarnings("unused")
	private IDAccordoCooperazioneFactory idAccordoCooperazioneFactory = null;
	
	public String getTipoDatabase() {
		return this.tipoDB;
	}

	public DriverConfigurazioneDB getDriverConfigurazioneDB() {
		return this.configDB;
	}

	public DriverRegistroServiziDB getDriverRegistroServiziDB() {
		return this.regservDB;
	}

	public DriverUsersDB getDriverUsersDB() {
		return this.usersDB;
	}

	public DriverAudit getDriverAuditDB() {
		return this.auditDB;
	}

	public DriverAuditDBAppender getDriverAuditDBAppender() {
		return this.auditDBappender;
	}
	
	public JDBCServiceManager getJdbcServiceManagerControlloCongestione() {
		return this.jdbcServiceManagerControlloCongestione;
	}

	public DriverControlStationDB(Connection connection, Properties context, String tipoDB) throws DriverControlStationException {
		this.log = ControlStationLogger.getDriverDBPddConsoleLogger();

		if (connection == null) {
			this.create = false;
			throw new DriverControlStationException("[DriverControlStationDB::DriverControlStation(Connection con, Properties context, String tipoDB) La connection non puo essere null.");
		}

		if (tipoDB == null) {
			this.create = false;
			throw new DriverControlStationException("[DriverControlStationDB::DriverControlStation(Connection con, Properties context, String tipoDB) Il tipoDatabase non puo essere null.");
		}

		// InitialContext initCtx = new InitialContext(context);
		this.globalConnection = connection;
		this.create = true;
		this.atomica = false;
		this.tipoDB = tipoDB;

		try {
			this.configDB = new DriverConfigurazioneDB(connection, this.tipoDB);
			this.regservDB = new DriverRegistroServiziDB(connection, this.tipoDB);
			this.usersDB = new DriverUsersDB(connection, this.tipoDB, this.log);
			this.auditDB = new DriverAudit(connection, this.tipoDB);
			this.auditDBappender = new DriverAuditDBAppender(connection, this.tipoDB);
			ServiceManagerProperties properties = new ServiceManagerProperties();
			properties.setDatabaseType(this.tipoDB);
			properties.setShowSql(true);
			this.jdbcServiceManagerControlloCongestione = new org.openspcoop2.core.controllo_congestione.dao.jdbc.JDBCServiceManager(connection, properties, this.log);
			this.idAccordoFactory = IDAccordoFactory.getInstance();
			this.idAccordoCooperazioneFactory = IDAccordoCooperazioneFactory.getInstance();
		} catch (Exception e) {
			throw new DriverControlStationException("[DriverControlStationDB::DriverControlStation(Connection con, Properties context, String tipoDB) Errore creando i driver ausiliari.");
		}

		// Setto il tipoDB anche in DriverControlStationDB_LIB
		DriverControlStationDB_LIB.setTipoDB(tipoDB);

		this.log.info("Creato DriverControlStationDB");
	}

	
	
	
	
	/* ***** PDD **** */
	
	public void createPdDControlStation(PdDControlStation pdd) throws DriverControlStationException {
		String nomeMetodo = "createPdd";

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet risultato = null;

		if (this.atomica) {
			try {
				con = this.datasource.getConnection();

			} catch (SQLException e) {
				throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo + "] SQLException accedendo al datasource :" + e.getMessage());

			}

		} else {
			con = this.globalConnection;
		}

		this.log.debug("operazione this.atomica = " + this.atomica);

		try {

			DriverControlStationDB_LIB.CRUDPdd(CostantiDB.CREATE, pdd, con);

		} catch (Exception se) {
			throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo + "] Exception: " + se.getMessage(),se);
		} finally {
			// Chiudo statement and resultset
			try {
				if (risultato != null) {
					risultato.close();
				}
				if (stmt != null) {
					stmt.close();
				}
			} catch (Exception e) {
				// ignore
			}
			try {
				if (this.atomica) {
					this.log.debug("rilascio connessioni al db...");
					con.close();
				}
			} catch (Exception e) {
				// ignore exception
			}
		}

	}

	public void deletePdDControlStation(PdDControlStation pdd) throws DriverControlStationException {
		String nomeMetodo = "deletePdd";

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet risultato = null;

		if (this.atomica) {
			try {
				con = this.datasource.getConnection();

			} catch (SQLException e) {
				throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo + "] SQLException accedendo al datasource :" + e.getMessage());

			}

		} else {
			con = this.globalConnection;
		}

		this.log.debug("operazione this.atomica = " + this.atomica);

		try {

			DriverControlStationDB_LIB.CRUDPdd(CostantiDB.DELETE, pdd, con);

		} catch (Exception se) {
			throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo + "] Exception: " + se.getMessage(),se);
		} finally {

			// Chiudo statement and resultset
			try {
				if (risultato != null) {
					risultato.close();
				}
				if (stmt != null) {
					stmt.close();
				}
			} catch (Exception e) {
				// ignore
			}

			try {
				if (this.atomica) {
					this.log.debug("rilascio connessioni al db...");
					con.close();
				}
			} catch (Exception e) {
				// ignore exception
			}
		}

	}

	public void updatePdDControlStation(PdDControlStation pdd) throws DriverControlStationException {
		String nomeMetodo = "updatePdd";

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet risultato = null;

		if (this.atomica) {
			try {
				con = this.datasource.getConnection();

			} catch (SQLException e) {
				throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo + "] SQLException accedendo al datasource :" + e.getMessage());

			}

		} else {
			con = this.globalConnection;
		}

		this.log.debug("operazione this.atomica = " + this.atomica);

		try {

			DriverControlStationDB_LIB.CRUDPdd(CostantiDB.UPDATE, pdd, con);

		} catch (Exception se) {
			throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo + "] Exception: " + se.getMessage(),se);
		} finally {
			// Chiudo statement and resultset
			try {
				if (risultato != null) {
					risultato.close();
				}
				if (stmt != null) {
					stmt.close();
				}
			} catch (Exception e) {
				// ignore
			}
			try {
				if (this.atomica) {
					this.log.debug("rilascio connessioni al db...");
					con.close();
				}
			} catch (Exception e) {
				// ignore exception
			}
		}

	}
	
	public List<PdDControlStation> pddList(String superuser, ISearch ricerca) throws DriverControlStationException {
		String nomeMetodo = "pddList";
		int idLista = Liste.PDD;
		int offset;
		int limit;
		String search;
		String queryString;

		limit = ricerca.getPageSize(idLista);
		offset = ricerca.getIndexIniziale(idLista);
		search = (org.openspcoop2.core.constants.Costanti.SESSION_ATTRIBUTE_VALUE_RICERCA_UNDEFINED.equals(ricerca.getSearchString(idLista)) ? "" : ricerca.getSearchString(idLista));
		ricerca.getSearchString(idLista);

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet risultato = null;

		if (this.atomica) {
			try {
				con = this.datasource.getConnection();

			} catch (SQLException e) {
				throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo + "] SQLException accedendo al datasource :" + e.getMessage());

			}

		} else {
			con = this.globalConnection;
		}

		try {

			if (!search.equals("")) {
				// query con search
				ISQLQueryObject sqlQueryObject = SQLObjectFactory.createSQLQueryObject(this.tipoDB);
				sqlQueryObject.addFromTable(CostantiDB.PDD);
				sqlQueryObject.addSelectCountField("*", "cont");
				if (superuser!=null && (!superuser.equals("")))
					sqlQueryObject.addWhereCondition("superuser = ?");
				sqlQueryObject.addWhereLikeCondition("nome", search, true, true);
				sqlQueryObject.setANDLogicOperator(true);
				queryString = sqlQueryObject.createSQLQuery();
			} else {
				ISQLQueryObject sqlQueryObject = SQLObjectFactory.createSQLQueryObject(this.tipoDB);
				sqlQueryObject.addFromTable(CostantiDB.PDD);
				sqlQueryObject.addSelectCountField("*", "cont");
				if (superuser!=null && (!superuser.equals("")))
					sqlQueryObject.addWhereCondition("superuser = ?");
				queryString = sqlQueryObject.createSQLQuery();
			}
			stmt = con.prepareStatement(queryString);
			if (superuser!=null && (!superuser.equals("")))
				stmt.setString(1, superuser);
			risultato = stmt.executeQuery();
			if (risultato.next()) {
				ricerca.setNumEntries(idLista, risultato.getInt(1));
			}
			risultato.close();
			stmt.close();

			// ricavo le entries
			if (limit == 0) {
				limit = ISQLQueryObject.LIMIT_DEFAULT_VALUE;
			}
			if (!search.equals("")) {
				ISQLQueryObject sqlQueryObject = SQLObjectFactory.createSQLQueryObject(this.tipoDB);
				sqlQueryObject.addFromTable(CostantiDB.PDD);
				sqlQueryObject.addSelectField("id");
				sqlQueryObject.addSelectField("nome");
				if (superuser!=null && (!superuser.equals("")))
					sqlQueryObject.addWhereCondition("superuser = ?");
				sqlQueryObject.addWhereLikeCondition("nome", search, true, true);
				sqlQueryObject.setANDLogicOperator(true);
				sqlQueryObject.addOrderBy("nome");
				sqlQueryObject.setSortType(true);
				sqlQueryObject.setLimit(limit);
				sqlQueryObject.setOffset(offset);
				queryString = sqlQueryObject.createSQLQuery();
			} else {
				// senza search
				ISQLQueryObject sqlQueryObject = SQLObjectFactory.createSQLQueryObject(this.tipoDB);
				sqlQueryObject.addFromTable(CostantiDB.PDD);
				sqlQueryObject.addSelectField("id");
				sqlQueryObject.addSelectField("nome");
				if (superuser!=null && (!superuser.equals("")))
					sqlQueryObject.addWhereCondition("superuser = ?");
				sqlQueryObject.addOrderBy("nome");
				sqlQueryObject.setSortType(true);
				sqlQueryObject.setLimit(limit);
				sqlQueryObject.setOffset(offset);
				queryString = sqlQueryObject.createSQLQuery();
			}
			stmt = con.prepareStatement(queryString);
			if (superuser!=null && (!superuser.equals("")))
				stmt.setString(1, superuser);
			risultato = stmt.executeQuery();

			ArrayList<PdDControlStation> lista = new ArrayList<PdDControlStation>();
			PdDControlStation pdd = null;
			while (risultato.next()) {
				long id = risultato.getLong("id");
				pdd = this.getPdDControlStation(id);
				lista.add(pdd);
			}
			risultato.close();
			stmt.close();

			return lista;

		} catch (Exception se) {

			throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo + "] Exception: " + se.getMessage(),se);
		} finally {

			// Chiudo statement and resultset
			try {
				if (risultato != null) {
					risultato.close();
				}
				if (stmt != null) {
					stmt.close();
				}
			} catch (Exception e) {
				// ignore
			}

			try {
				if (this.atomica) {
					this.log.debug("rilascio connessioni al db...");
					con.close();
				}
			} catch (Exception e) {
				// ignore exception
			}
		}

	}

	/**
	 * 
	 * @param idPdd
	 * @return Pdd
	 * @throws DriverControlStationException
	 */
	public PdDControlStation getPdDControlStation(long idPdd) throws DriverControlStationException, DriverControlStationNotFound {
		String nomeMetodo = "pddList(int idPdd)";

		String queryString;

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet risultato = null;

		if (this.atomica) {
			try {
				con = this.datasource.getConnection();

			} catch (SQLException e) {
				throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo + "] SQLException accedendo al datasource :" + e.getMessage());

			}

		} else {
			con = this.globalConnection;
		}

		try {

			PdDControlStation pdd = new PdDControlStation();

			ISQLQueryObject sqlQueryObject = SQLObjectFactory.createSQLQueryObject(this.tipoDB);
			sqlQueryObject.addFromTable(CostantiDB.PDD);
			sqlQueryObject.addSelectField("*");
			sqlQueryObject.addWhereCondition("id = ?");
			queryString = sqlQueryObject.createSQLQuery();
			stmt = con.prepareStatement(queryString);
			stmt.setLong(1, idPdd);
			risultato = stmt.executeQuery();
			if (risultato.next()) {
				pdd.setId(risultato.getLong("id"));
				pdd.setNome(risultato.getString("nome"));
				pdd.setDescrizione(risultato.getString("descrizione"));

				pdd.setIp(risultato.getString("ip"));
				pdd.setPorta(risultato.getInt("porta"));
				pdd.setIpGestione(risultato.getString("ip_gestione"));
				pdd.setPortaGestione(risultato.getInt("porta_gestione"));
				pdd.setProtocollo(risultato.getString("protocollo"));
				pdd.setProtocolloGestione(risultato.getString("protocollo_gestione"));

				pdd.setTipo(risultato.getString("tipo"));
				pdd.setImplementazione(risultato.getString("implementazione"));

				pdd.setPassword(risultato.getString("password"));
				pdd.setSubject(risultato.getString("subject"));
				pdd.setClientAuth(DriverRegistroServiziDB_LIB.getEnumStatoFunzionalita(risultato.getString("client_auth")));

				// Ora Registrazione
				if (risultato.getTimestamp("ora_registrazione") != null) {
					pdd.setOraRegistrazione(new Date(risultato.getTimestamp("ora_registrazione").getTime()));
				}

				pdd.setSuperUser(risultato.getString("superuser"));

			} else {
				throw new DriverControlStationNotFound("Porta di dominio con id " + idPdd + " non trovata.");
			}

			risultato.close();
			stmt.close();

			return pdd;

		} catch (DriverControlStationNotFound e) {
			throw e;
		} catch (Exception se) {

			throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo + "] Exception: " + se.getMessage(),se);
		} finally {
			// Chiudo statement and resultset
			try {
				if (risultato != null) {
					risultato.close();
				}
				if (stmt != null) {
					stmt.close();
				}
			} catch (Exception e) {
				// ignore
			}
			try {
				if (this.atomica) {
					this.log.debug("rilascio connessioni al db...");
					con.close();
				}

			} catch (Exception e) {
				// ignore exception
			}
		}

	}

	public List<org.openspcoop2.core.config.Soggetto> pddSoggettiList(int idPdd, ISearch ricerca) throws DriverControlStationException {
		String nomeMetodo = "pddSoggettiList";
		int idLista = Liste.PDD_SOGGETTI;
		int offset;
		int limit;
		String search;
		String queryString;

		limit = ricerca.getPageSize(idLista);
		offset = ricerca.getIndexIniziale(idLista);
		search = (org.openspcoop2.core.constants.Costanti.SESSION_ATTRIBUTE_VALUE_RICERCA_UNDEFINED.equals(ricerca.getSearchString(idLista)) ? "" : ricerca.getSearchString(idLista));
		ricerca.getSearchString(idLista);

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet risultato = null;

		if (this.atomica) {
			try {
				con = this.datasource.getConnection();

			} catch (SQLException e) {
				throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo + "] SQLException accedendo al datasource :" + e.getMessage());

			}

		} else {
			con = this.globalConnection;
		}

		ArrayList<org.openspcoop2.core.config.Soggetto> lista = null;

		try {

			// Prendo il nome del pdd
			String nomePdd = "";
			ISQLQueryObject sqlQueryObject = SQLObjectFactory.createSQLQueryObject(this.tipoDB);
			sqlQueryObject.addFromTable(CostantiDB.PDD);
			sqlQueryObject.addSelectField("*");
			sqlQueryObject.addWhereCondition("id = ?");
			queryString = sqlQueryObject.createSQLQuery();
			stmt = con.prepareStatement(queryString);
			stmt.setInt(1, idPdd);
			risultato = stmt.executeQuery();
			if (risultato.next()) {
				nomePdd = risultato.getString("nome");
			}
			risultato.close();
			stmt.close();

			if (!search.equals("")) {
				// query con search
				sqlQueryObject = SQLObjectFactory.createSQLQueryObject(this.tipoDB);
				sqlQueryObject.addFromTable(CostantiDB.SOGGETTI);
				sqlQueryObject.addSelectCountField("*", "cont");
				sqlQueryObject.addWhereCondition("server = ?");
				sqlQueryObject.addWhereCondition(false, sqlQueryObject.getWhereLikeCondition("nome_soggetto", search, true, true), sqlQueryObject.getWhereLikeCondition("tipo_soggetto", search, true, true));
				sqlQueryObject.setANDLogicOperator(true);
				queryString = sqlQueryObject.createSQLQuery();
			} else {
				sqlQueryObject = SQLObjectFactory.createSQLQueryObject(this.tipoDB);
				sqlQueryObject.addFromTable(CostantiDB.SOGGETTI);
				sqlQueryObject.addSelectCountField("*", "cont");
				sqlQueryObject.addWhereCondition("server = ?");
				queryString = sqlQueryObject.createSQLQuery();
			}
			stmt = con.prepareStatement(queryString);
			stmt.setString(1, nomePdd);
			risultato = stmt.executeQuery();
			if (risultato.next()) {
				ricerca.setNumEntries(idLista, risultato.getInt(1));
			}
			risultato.close();
			stmt.close();

			// ricavo le entries
			if (limit == 0) {
				limit = ISQLQueryObject.LIMIT_DEFAULT_VALUE;
			}
			if (!search.equals("")) {
				sqlQueryObject = SQLObjectFactory.createSQLQueryObject(this.tipoDB);
				sqlQueryObject.addFromTable(CostantiDB.SOGGETTI);
				sqlQueryObject.addSelectField("server");
				sqlQueryObject.addSelectField("nome_soggetto");
				sqlQueryObject.addSelectField("tipo_soggetto");
				sqlQueryObject.addSelectField("id");
				sqlQueryObject.addSelectField("descrizione");
				sqlQueryObject.addSelectField("identificativo_porta");
				sqlQueryObject.addWhereCondition("server = ?");
				sqlQueryObject.addWhereCondition(false, sqlQueryObject.getWhereLikeCondition("nome_soggetto", search, true, true), sqlQueryObject.getWhereLikeCondition("tipo_soggetto", search, true, true));
				sqlQueryObject.setANDLogicOperator(true);
				sqlQueryObject.addOrderBy("tipo_soggetto");
				sqlQueryObject.addOrderBy("nome_soggetto");
				sqlQueryObject.setSortType(true);
				sqlQueryObject.setLimit(limit);
				sqlQueryObject.setOffset(offset);
				queryString = sqlQueryObject.createSQLQuery();
			} else {
				// senza search
				sqlQueryObject = SQLObjectFactory.createSQLQueryObject(this.tipoDB);
				sqlQueryObject.addFromTable(CostantiDB.SOGGETTI);
				sqlQueryObject.addSelectField("server");
				sqlQueryObject.addSelectField("nome_soggetto");
				sqlQueryObject.addSelectField("tipo_soggetto");
				sqlQueryObject.addSelectField("id");
				sqlQueryObject.addSelectField("descrizione");
				sqlQueryObject.addSelectField("identificativo_porta");
				sqlQueryObject.addWhereCondition("server = ?");
				sqlQueryObject.addOrderBy("tipo_soggetto");
				sqlQueryObject.addOrderBy("nome_soggetto");
				sqlQueryObject.setSortType(true);
				sqlQueryObject.setLimit(limit);
				sqlQueryObject.setOffset(offset);
				queryString = sqlQueryObject.createSQLQuery();
			}
			stmt = con.prepareStatement(queryString);
			stmt.setString(1, nomePdd);
			risultato = stmt.executeQuery();

			lista = new ArrayList<org.openspcoop2.core.config.Soggetto>();
			org.openspcoop2.core.config.Soggetto sog = null;
			while (risultato.next()) {
				sog = new org.openspcoop2.core.config.Soggetto();

				sog.setId(risultato.getLong("id"));
				sog.setNome(risultato.getString("nome_soggetto"));
				sog.setTipo(risultato.getString("tipo_soggetto"));
				sog.setDescrizione(risultato.getString("descrizione"));
				sog.setIdentificativoPorta(risultato.getString("identificativo_porta"));

				lista.add(sog);
			}

			return lista;

		} catch (Exception se) {

			throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo + "] Exception: " + se.getMessage(),se);
		} finally {
			// Chiudo statement and resultset
			try {
				if (risultato != null) {
					risultato.close();
				}
				if (stmt != null) {
					stmt.close();
				}
			} catch (Exception e) {
				// ignore
			}
			try {
				if (this.atomica) {
					this.log.debug("rilascio connessioni al db...");
					con.close();
				}
			} catch (Exception e) {
				// ignore exception
			}
		}

	}


	/**
	 * Ritorna una Porta di Dominio tramite il parametro passato
	 * 
	 * @param nomePdd
	 * @return Pdd se esiste una Porta di Dominio con il nome passato, null
	 *         altrimenti
	 * @throws DriverControlStationException
	 */
	public PdDControlStation getPdDControlStation(String nomePdd) throws DriverControlStationException, DriverControlStationNotFound {
		String nomeMetodo = "pddList(String nomePdd)";

		String queryString;

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet risultato = null;

		if (this.atomica) {
			try {
				con = this.datasource.getConnection();

			} catch (SQLException e) {
				throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo + "] SQLException accedendo al datasource :" + e.getMessage());

			}

		} else {
			con = this.globalConnection;
		}

		try {

			PdDControlStation pdd = null;

			ISQLQueryObject sqlQueryObject = SQLObjectFactory.createSQLQueryObject(this.tipoDB);
			sqlQueryObject.addFromTable(CostantiDB.PDD);
			sqlQueryObject.addSelectField("*");
			sqlQueryObject.addWhereCondition("nome = ?");
			queryString = sqlQueryObject.createSQLQuery();
			stmt = con.prepareStatement(queryString);
			stmt.setString(1, nomePdd);
			risultato = stmt.executeQuery();
			if (risultato.next()) {
				long id = risultato.getLong("id");
				pdd = this.getPdDControlStation(id);
			} else {
				throw new DriverControlStationNotFound("Porta di dominio con nome " + nomePdd + " non trovata.");
			}

			risultato.close();
			stmt.close();

			return pdd;

		} catch (DriverControlStationNotFound e) {
			throw e;
		} catch (Exception se) {

			throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo + "] Exception: " + se.getMessage(),se);
		} finally {
			// Chiudo statement and resultset
			try {
				if (risultato != null) {
					risultato.close();
				}
				if (stmt != null) {
					stmt.close();
				}
			} catch (Exception e) {
				// ignore
			}
			try {
				if (this.atomica) {
					this.log.debug("rilascio connessioni al db...");
					con.close();
				}

			} catch (Exception e) {
				// ignore exception
			}
		}
	}
	
	public List<SoggettoCtrlStat> soggettiWithServer(String nomePdD) throws DriverControlStationException {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet risultato = null;
		String queryString = "";

		if (this.atomica) {
			try {
				con = this.datasource.getConnection();

			} catch (SQLException e) {
				throw new DriverControlStationException(e);

			}

		} else {
			con = this.globalConnection;
		}

		this.log.debug("operazione this.atomica = " + this.atomica);

		try {
			SoggettoCtrlStat scs = null;
			ISQLQueryObject sqlQueryObject = SQLObjectFactory.createSQLQueryObject(this.tipoDB);
			sqlQueryObject.addFromTable(CostantiDB.SOGGETTI);
			sqlQueryObject.addSelectField("*");
			sqlQueryObject.addWhereCondition("server=?");
			queryString = sqlQueryObject.createSQLQuery();
			stmt = con.prepareStatement(queryString);
			stmt.setString(1, nomePdD);
			List<SoggettoCtrlStat> scsList = new ArrayList<SoggettoCtrlStat>();
			risultato = stmt.executeQuery();
			while (risultato.next()) {
				org.openspcoop2.core.config.Soggetto soggConf = this.getDriverConfigurazioneDB().getSoggetto(risultato.getLong("id"));
				org.openspcoop2.core.registry.Soggetto soggReg = this.getDriverRegistroServiziDB().getSoggetto(risultato.getLong("id"));
				scs = new SoggettoCtrlStat(soggReg, soggConf);
				scsList.add(scs);
			}
			risultato.close();
			stmt.close();

			return scsList;

		} catch (Exception se) {
			throw new DriverControlStationException(se);
		} finally {
			// Chiudo statement and resultset
			try {
				if (risultato != null) {
					risultato.close();
				}
				if (stmt != null) {
					stmt.close();
				}
			} catch (Exception e) {
				// ignore
			}
			try {
				if (this.atomica) {
					this.log.debug("rilascio connessioni al db...");
					con.close();
				}

			} catch (Exception e) {
				// ignore exception
			}
		}
	}

	
	
	
	
	
	
	
	
	/* ***** MAPPING **** */
	
	public void createMappingFruizionePortaDelegata(MappingFruizionePortaDelegata mapping) throws DriverControlStationException {
		String nomeMetodo = "createMappingFruizionePortaDelegata";

		Connection con = null;

		if (this.atomica) {
			try {
				con = this.datasource.getConnection();

			} catch (SQLException e) {
				throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo + "] SQLException accedendo al datasource :" + e.getMessage());

			}

		} else {
			con = this.globalConnection;
		}

		this.log.debug("operazione this.atomica = " + this.atomica);

		try {

			DBMappingUtils.createMappingFruizione(mapping.getNome(), mapping.isDefault(), mapping.getIdServizio(), mapping.getIdFruitore(), mapping.getIdPortaDelegata(), con, this.tipoDB);

		} catch (Exception se) {
			throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo + "] Exception: " + se.getMessage(),se);
		} finally {
			try {
				if (this.atomica) {
					this.log.debug("rilascio connessioni al db...");
					con.close();
				}
			} catch (Exception e) {
				// ignore exception
			}
		}

		mapping.setTableId(this.getTableIdMappingFruizionePortaDelegata(mapping));
	}
	
	public void deleteMappingFruizionePortaDelegata(MappingFruizionePortaDelegata mapping) throws DriverControlStationException {
		String nomeMetodo = "deleteMappingFruizionePortaDelegata";

		Connection con = null;

		if (this.atomica) {
			try {
				con = this.datasource.getConnection();

			} catch (SQLException e) {
				throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo + "] SQLException accedendo al datasource :" + e.getMessage());

			}

		} else {
			con = this.globalConnection;
		}

		this.log.debug("operazione this.atomica = " + this.atomica);

		try {

			DBMappingUtils.deleteMappingFruizione(mapping.getIdServizio(), mapping.getIdFruitore(), mapping.getIdPortaDelegata(), con, this.tipoDB);

		} catch (Exception se) {
			throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo + "] Exception: " + se.getMessage(),se);
		} finally {
			try {
				if (this.atomica) {
					this.log.debug("rilascio connessioni al db...");
					con.close();
				}
			} catch (Exception e) {
				// ignore exception
			}
		}

	}
	
	public IDPortaDelegata getIDPortaDelegataAssociataDefault(IDServizio idServizio,IDSoggetto idFruitore) throws DriverControlStationException {
		String nomeMetodo = "getIDPortaDelegataAssociataDefault";

		Connection con = null;

		if (this.atomica) {
			try {
				con = this.datasource.getConnection();

			} catch (SQLException e) {
				throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo + "] SQLException accedendo al datasource :" + e.getMessage());

			}

		} else {
			con = this.globalConnection;
		}

		this.log.debug("operazione this.atomica = " + this.atomica);

		try {

			return DBMappingUtils.getIDPortaDelegataAssociataDefault(idServizio, idFruitore, con, this.tipoDB);

		} catch (Exception se) {
			throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo + "] Exception: " + se.getMessage(),se);
		} finally {
			try {
				if (this.atomica) {
					this.log.debug("rilascio connessioni al db...");
					con.close();
				}
			} catch (Exception e) {
				// ignore exception
			}
		}

	}
	
	public IDPortaDelegata getIDPortaDelegataAssociataAzione(IDServizio idServizio,IDSoggetto idFruitore) throws DriverControlStationException {
		String nomeMetodo = "getIDPortaDelegataAssociataAzione";

		Connection con = null;

		if (this.atomica) {
			try {
				con = this.datasource.getConnection();

			} catch (SQLException e) {
				throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo + "] SQLException accedendo al datasource :" + e.getMessage());

			}

		} else {
			con = this.globalConnection;
		}

		this.log.debug("operazione this.atomica = " + this.atomica);

		try {

			return DBMappingUtils.getIDPortaDelegataAssociataAzione(idServizio, idFruitore, con, this.tipoDB);

		} catch (Exception se) {
			throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo + "] Exception: " + se.getMessage(),se);
		} finally {
			try {
				if (this.atomica) {
					this.log.debug("rilascio connessioni al db...");
					con.close();
				}
			} catch (Exception e) {
				// ignore exception
			}
		}

	}
	
	public List<IDPortaDelegata> getIDPorteDelegateAssociate(IDServizio idServizio,IDSoggetto idFruitore) throws DriverControlStationException {
		String nomeMetodo = "getIDPorteDelegateAssociate";

		Connection con = null;

		if (this.atomica) {
			try {
				con = this.datasource.getConnection();

			} catch (SQLException e) {
				throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo + "] SQLException accedendo al datasource :" + e.getMessage());

			}

		} else {
			con = this.globalConnection;
		}

		this.log.debug("operazione this.atomica = " + this.atomica);

		try {

			return DBMappingUtils.getIDPorteDelegateAssociate(idServizio, idFruitore, con, this.tipoDB);

		} catch (Exception se) {
			throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo + "] Exception: " + se.getMessage(),se);
		} finally {
			try {
				if (this.atomica) {
					this.log.debug("rilascio connessioni al db...");
					con.close();
				}
			} catch (Exception e) {
				// ignore exception
			}
		}

	}
	
	public long getTableIdMappingFruizionePortaDelegata(MappingFruizionePortaDelegata mapping) throws DriverControlStationException {
		String nomeMetodo = "getTableIdMappingFruizionePortaDelegata";

		Connection con = null;

		if (this.atomica) {
			try {
				con = this.datasource.getConnection();

			} catch (SQLException e) {
				throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo + "] SQLException accedendo al datasource :" + e.getMessage());

			}

		} else {
			con = this.globalConnection;
		}

		this.log.debug("operazione this.atomica = " + this.atomica);

		try {

			return DBMappingUtils.getTableIdMappingFruizione(mapping.getIdServizio(), mapping.getIdFruitore(), mapping.getIdPortaDelegata(), con, this.tipoDB);

		} catch (Exception se) {
			throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo + "] Exception: " + se.getMessage(),se);
		} finally {
			try {
				if (this.atomica) {
					this.log.debug("rilascio connessioni al db...");
					con.close();
				}
			} catch (Exception e) {
				// ignore exception
			}
		}

	}
	
	public boolean existsMappingFruizionePortaDelegata(MappingFruizionePortaDelegata mapping) throws DriverControlStationException {
		String nomeMetodo = "existsMappingFruizionePortaDelegata";

		Connection con = null;

		if (this.atomica) {
			try {
				con = this.datasource.getConnection();

			} catch (SQLException e) {
				throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo + "] SQLException accedendo al datasource :" + e.getMessage());

			}

		} else {
			con = this.globalConnection;
		}

		this.log.debug("operazione this.atomica = " + this.atomica);

		try {

			return DBMappingUtils.existsMappingFruizione(mapping.getIdServizio(), mapping.getIdFruitore(), mapping.getIdPortaDelegata(), con, this.tipoDB);

		} catch (Exception se) {
			throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo + "] Exception: " + se.getMessage(),se);
		} finally {
			try {
				if (this.atomica) {
					this.log.debug("rilascio connessioni al db...");
					con.close();
				}
			} catch (Exception e) {
				// ignore exception
			}
		}

	}
	
	
	public void createMappingErogazionePortaApplicativa(MappingErogazionePortaApplicativa mapping) throws DriverControlStationException {
		String nomeMetodo = "createMappingErogazionePortaApplicativa";

		Connection con = null;

		if (this.atomica) {
			try {
				con = this.datasource.getConnection();

			} catch (SQLException e) {
				throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo + "] SQLException accedendo al datasource :" + e.getMessage());

			}

		} else {
			con = this.globalConnection;
		}

		this.log.debug("operazione this.atomica = " + this.atomica);

		try {

			DBMappingUtils.createMappingErogazione(mapping.getNome(), mapping.isDefault(), mapping.getIdServizio(), mapping.getIdPortaApplicativa(), con, this.tipoDB);

		} catch (Exception se) {
			throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo + "] Exception: " + se.getMessage(),se);
		} finally {
			try {
				if (this.atomica) {
					this.log.debug("rilascio connessioni al db...");
					con.close();
				}
			} catch (Exception e) {
				// ignore exception
			}
		}

		mapping.setTableId(mapping.getTableId());
	}
	
	
	public void deleteMappingErogazionePortaApplicativa(MappingErogazionePortaApplicativa mapping) throws DriverControlStationException {
		String nomeMetodo = "deleteMappingErogazionePortaApplicativa";

		Connection con = null;

		if (this.atomica) {
			try {
				con = this.datasource.getConnection();

			} catch (SQLException e) {
				throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo + "] SQLException accedendo al datasource :" + e.getMessage());

			}

		} else {
			con = this.globalConnection;
		}

		this.log.debug("operazione this.atomica = " + this.atomica);

		try {

			DBMappingUtils.deleteMappingErogazione(mapping.getIdServizio(), mapping.getIdPortaApplicativa(), con, this.tipoDB);

		} catch (Exception se) {
			throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo + "] Exception: " + se.getMessage(),se);
		} finally {
			try {
				if (this.atomica) {
					this.log.debug("rilascio connessioni al db...");
					con.close();
				}
			} catch (Exception e) {
				// ignore exception
			}
		}

	}
	
	public IDPortaApplicativa getIDPortaApplicativaAssociataDefault(IDServizio idServizio) throws DriverControlStationException {
		String nomeMetodo = "getIDPortaApplicativaAssociataDefault";

		Connection con = null;

		if (this.atomica) {
			try {
				con = this.datasource.getConnection();

			} catch (SQLException e) {
				throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo + "] SQLException accedendo al datasource :" + e.getMessage());

			}

		} else {
			con = this.globalConnection;
		}

		this.log.debug("operazione this.atomica = " + this.atomica);

		try {

			return DBMappingUtils.getIDPortaApplicativaAssociataDefault(idServizio, con, this.tipoDB);

		} catch (Exception se) {
			throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo + "] Exception: " + se.getMessage(),se);
		} finally {
			try {
				if (this.atomica) {
					this.log.debug("rilascio connessioni al db...");
					con.close();
				}
			} catch (Exception e) {
				// ignore exception
			}
		}

	}
	
	public IDPortaApplicativa getIDPortaApplicativaAssociataAzione(IDServizio idServizio) throws DriverControlStationException {
		String nomeMetodo = "getIDPortaApplicativaAssociataAzione";

		Connection con = null;

		if (this.atomica) {
			try {
				con = this.datasource.getConnection();

			} catch (SQLException e) {
				throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo + "] SQLException accedendo al datasource :" + e.getMessage());

			}

		} else {
			con = this.globalConnection;
		}

		this.log.debug("operazione this.atomica = " + this.atomica);

		try {

			return DBMappingUtils.getIDPortaApplicativaAssociataAzione(idServizio, con, this.tipoDB);

		} catch (Exception se) {
			throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo + "] Exception: " + se.getMessage(),se);
		} finally {
			try {
				if (this.atomica) {
					this.log.debug("rilascio connessioni al db...");
					con.close();
				}
			} catch (Exception e) {
				// ignore exception
			}
		}

	}
	
	public List<IDPortaApplicativa> getIDPorteApplicativeAssociate(IDServizio idServizio) throws DriverControlStationException {
		String nomeMetodo = "getIDPorteApplicativeAssociate";

		Connection con = null;

		if (this.atomica) {
			try {
				con = this.datasource.getConnection();

			} catch (SQLException e) {
				throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo + "] SQLException accedendo al datasource :" + e.getMessage());

			}

		} else {
			con = this.globalConnection;
		}

		this.log.debug("operazione this.atomica = " + this.atomica);

		try {

			return DBMappingUtils.getIDPorteApplicativeAssociate(idServizio, con, this.tipoDB);

		} catch (Exception se) {
			throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo + "] Exception: " + se.getMessage(),se);
		} finally {
			try {
				if (this.atomica) {
					this.log.debug("rilascio connessioni al db...");
					con.close();
				}
			} catch (Exception e) {
				// ignore exception
			}
		}

	}
	
	public long getTableIdMappingErogazionePortaApplicativa(MappingErogazionePortaApplicativa mapping) throws DriverControlStationException {
		String nomeMetodo = "getTableIdMappingErogazionePortaApplicativa";

		Connection con = null;

		if (this.atomica) {
			try {
				con = this.datasource.getConnection();

			} catch (SQLException e) {
				throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo + "] SQLException accedendo al datasource :" + e.getMessage());

			}

		} else {
			con = this.globalConnection;
		}

		this.log.debug("operazione this.atomica = " + this.atomica);

		try {

			return DBMappingUtils.getTableIdMappingErogazione(mapping.getIdServizio(), mapping.getIdPortaApplicativa(), con, this.tipoDB);

		} catch (Exception se) {
			throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo + "] Exception: " + se.getMessage(),se);
		} finally {
			try {
				if (this.atomica) {
					this.log.debug("rilascio connessioni al db...");
					con.close();
				}
			} catch (Exception e) {
				// ignore exception
			}
		}

	}
	
	public boolean existsMappingErogazionePortaApplicativa(MappingErogazionePortaApplicativa mapping) throws DriverControlStationException {
		String nomeMetodo = "existsMappingErogazionePortaApplicativa";

		Connection con = null;

		if (this.atomica) {
			try {
				con = this.datasource.getConnection();

			} catch (SQLException e) {
				throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo + "] SQLException accedendo al datasource :" + e.getMessage());

			}

		} else {
			con = this.globalConnection;
		}

		this.log.debug("operazione this.atomica = " + this.atomica);

		try {

			return DBMappingUtils.existsMappingErogazione(mapping.getIdServizio(), mapping.getIdPortaApplicativa(), con, this.tipoDB);

		} catch (Exception se) {
			throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo + "] Exception: " + se.getMessage(),se);
		} finally {
			try {
				if (this.atomica) {
					this.log.debug("rilascio connessioni al db...");
					con.close();
				}
			} catch (Exception e) {
				// ignore exception
			}
		}

	}
	
	public void initMappingErogazione(Logger log) throws DriverControlStationException {
		this._initMapping(true,false,log);
	}	
	public void initMappingErogazione(boolean forceMapping, Logger log) throws DriverControlStationException {
		this._initMapping(true,forceMapping,log);
	}
	public void initMappingFruizione(Logger log) throws DriverControlStationException {
		this._initMapping(false,false,log);
	}	
	public void initMappingFruizione(boolean forceMapping, Logger log) throws DriverControlStationException {
		this._initMapping(false,forceMapping,log);
	}	
	private void _initMapping(boolean erogazione, boolean forceMapping, Logger log) throws DriverControlStationException {
		String nomeMetodo = "initMappingErogazione";
		if(!erogazione){
			nomeMetodo = "initMappingFruizione";
		}

		Connection con = null;
		boolean error = false;

		if (this.atomica) {
			try {
				con = this.datasource.getConnection();
				con.setAutoCommit(false);
			} catch (SQLException e) {
				throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo + "] SQLException accedendo al datasource :" + e.getMessage());

			}

		} else {
			con = this.globalConnection;
		}

		log.debug("operazione this.atomica = " + this.atomica);

		try {

			int countMapping = -1;
			if(erogazione){
				countMapping = DBMappingUtils.countMappingErogazione(con, this.tipoDB);
			}
			else{
				countMapping = DBMappingUtils.countMappingFruizione(con, this.tipoDB);
			}
			log.debug("Count ["+nomeMetodo+"]: "+countMapping);
			if(countMapping<=0 || forceMapping){
				
				log.debug("Controllo Consistenza Dati ["+nomeMetodo+"] ...");
				
				DriverRegistroServiziDB driverRegistry = new DriverRegistroServiziDB(con, this.tipoDB);
				DriverConfigurazioneDB driverConfig = new DriverConfigurazioneDB(con, this.tipoDB);
				
				UtilitiesMappingFruizioneErogazione utilities = new UtilitiesMappingFruizioneErogazione(driverConfig, driverRegistry, log);
				if(erogazione){
					utilities.initMappingErogazione();
				}else{
					utilities.initMappingFruizione();
				}
	
				log.debug("Controllo Consistenza Dati ["+nomeMetodo+"] terminato");
			}			
			
		} catch (Throwable se) {
			log.error("Controllo Consistenza Dati ["+nomeMetodo+"] terminato con errore: "+se.getMessage(),se);
			error = true;
			throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo + "] Exception: " + se.getMessage(),se);
		} finally {
			try {
				if (error && this.atomica) {
					this.log.debug("eseguo rollback a causa di errori e rilascio connessioni...");
					con.rollback();
					con.setAutoCommit(true);
					con.close();

				} else if (!error && this.atomica) {
					this.log.debug("eseguo commit e rilascio connessioni...");
					con.commit();
					con.setAutoCommit(true);
					con.close();
				}
			} catch (Exception e) {
				// ignore exception
			}
		}

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	/* *********** DB UTILS ****************** */
	
	public long getTableIdSoggetto(IDSoggetto idSoggetto) throws DriverControlStationException {
		String nomeMetodo = "getTableIdSoggetto";

		Connection con = null;

		if (this.atomica) {
			try {
				con = this.datasource.getConnection();

			} catch (SQLException e) {
				throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo + "] SQLException accedendo al datasource :" + e.getMessage());

			}

		} else {
			con = this.globalConnection;
		}

		this.log.debug("operazione this.atomica = " + this.atomica);

		try {

			return DBUtils.getIdSoggetto(idSoggetto.getNome(), idSoggetto.getTipo(), con, this.tipoDB);

		} catch (Exception se) {
			throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo + "] Exception: " + se.getMessage(),se);
		} finally {
			try {
				if (this.atomica) {
					this.log.debug("rilascio connessioni al db...");
					con.close();
				}
			} catch (Exception e) {
				// ignore exception
			}
		}

	}
	
	public long getTableIdAccordoServizioParteSpecifica(IDServizio idAccordoServizioParteSpecifica) throws DriverControlStationException {
		String nomeMetodo = "getTableIdAccordoServizioParteSpecifica";

		Connection con = null;

		if (this.atomica) {
			try {
				con = this.datasource.getConnection();

			} catch (SQLException e) {
				throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo + "] SQLException accedendo al datasource :" + e.getMessage());

			}

		} else {
			con = this.globalConnection;
		}

		this.log.debug("operazione this.atomica = " + this.atomica);

		try {

			return DBUtils.getIdAccordoServizioParteSpecifica(idAccordoServizioParteSpecifica, con, this.tipoDB);

		} catch (Exception se) {
			throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo + "] Exception: " + se.getMessage(),se);
		} finally {
			try {
				if (this.atomica) {
					this.log.debug("rilascio connessioni al db...");
					con.close();
				}
			} catch (Exception e) {
				// ignore exception
			}
		}

	}
	
	public long getTableIdPortaDelegata(IDPortaDelegata idPD) throws DriverControlStationException {
		String nomeMetodo = "getTableIdPortaDelegata";

		Connection con = null;

		if (this.atomica) {
			try {
				con = this.datasource.getConnection();

			} catch (SQLException e) {
				throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo + "] SQLException accedendo al datasource :" + e.getMessage());

			}

		} else {
			con = this.globalConnection;
		}

		this.log.debug("operazione this.atomica = " + this.atomica);

		try {

			return DBUtils.getIdPortaDelegata(idPD.getNome(), con, this.tipoDB);

		} catch (Exception se) {
			throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo + "] Exception: " + se.getMessage(),se);
		} finally {
			try {
				if (this.atomica) {
					this.log.debug("rilascio connessioni al db...");
					con.close();
				}
			} catch (Exception e) {
				// ignore exception
			}
		}
		
	}
	
	public long getTableIdPortaApplicativa(IDPortaApplicativa idPA) throws DriverControlStationException {
		String nomeMetodo = "getTableIdPortaApplicativa";

		Connection con = null;

		if (this.atomica) {
			try {
				con = this.datasource.getConnection();

			} catch (SQLException e) {
				throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo + "] SQLException accedendo al datasource :" + e.getMessage());

			}

		} else {
			con = this.globalConnection;
		}

		this.log.debug("operazione this.atomica = " + this.atomica);

		try {

			return DBUtils.getIdPortaApplicativa(idPA.getNome(), con, this.tipoDB);

		} catch (Exception se) {
			throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo + "] Exception: " + se.getMessage(),se);
		} finally {
			try {
				if (this.atomica) {
					this.log.debug("rilascio connessioni al db...");
					con.close();
				}
			} catch (Exception e) {
				// ignore exception
			}
		}

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/* *********** METODI IS IN USO ****************** */

	
	
	public boolean isAccordoInUso(AccordoServizioParteComune as, Map<ErrorsHandlerCostant, List<String>> whereIsInUso) throws DriverControlStationException {
		String nomeMetodo = "isAccordoInUso";

		Connection con = null;

		if (this.atomica) {
			try {
				con = this.datasource.getConnection();

			} catch (SQLException e) {
				throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo + "] SQLException accedendo al datasource :" + e.getMessage());

			}

		} else {
			con = this.globalConnection;
		}

		try {
			return DBOggettiInUsoUtils.isAccordoServizioParteComuneInUso(con, this.tipoDB, this.idAccordoFactory.getIDAccordoFromAccordo(as), whereIsInUso);

		} catch (Exception se) {
			throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo + "] Exception: " + se.getMessage(),se);
		} finally {
			try {
				if (this.atomica) {
					this.log.debug("rilascio connessioni al db...");
					con.close();
				}
			} catch (Exception e) {
				// ignore exception
			}
		}
		
	}

	public boolean isPddInUso(PdDControlStation pdd, List<String> whereIsInUso) throws DriverControlStationException {
		String nomeMetodo = "pddInUso";

		Connection con = null;
		
		if (this.atomica) {
			try {
				con = this.datasource.getConnection();

			} catch (SQLException e) {
				throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo + "] SQLException accedendo al datasource :" + e.getMessage());

			}

		} else {
			con = this.globalConnection;
		}

		try {

			return DBOggettiInUsoUtils.isPddInUso(con, this.tipoDB, pdd.getNome(), whereIsInUso);

		} catch (Exception se) {
			throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo + "] Exception: " + se.getMessage(),se);
		} finally {
			try {
				if (this.atomica) {
					this.log.debug("rilascio connessioni al db...");
					con.close();
				}
			} catch (Exception e) {
				// ignore exception
			}
		}
	}

	public boolean isSoggettoInUso(org.openspcoop2.core.config.Soggetto soggettoConfig, Map<ErrorsHandlerCostant, List<String>> whereIsInUso) throws DriverControlStationException {
		return isSoggettoInUso(soggettoConfig,null,whereIsInUso);
	}
	public boolean isSoggettoInUso(Soggetto soggettoRegistro, Map<ErrorsHandlerCostant, List<String>> whereIsInUso) throws DriverControlStationException {
		return isSoggettoInUso(null,soggettoRegistro,whereIsInUso);
	}
	private boolean isSoggettoInUso(org.openspcoop2.core.config.Soggetto soggettoConfig, Soggetto soggettoRegistro, Map<ErrorsHandlerCostant, List<String>> whereIsInUso) throws DriverControlStationException {
		String nomeMetodo = "isSoggettoInUso";

		Connection con = null;
		if (this.atomica) {
			try {
				con = this.datasource.getConnection();

			} catch (SQLException e) {
				throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo + "] SQLException accedendo al datasource :" + e.getMessage());

			}

		} else {
			con = this.globalConnection;
		}

		this.log.debug("operazione this.atomica = " + this.atomica);

		try {

			String tipoSoggetto = null;
			String nomeSoggetto = null;
			if(soggettoRegistro!=null){
				tipoSoggetto = soggettoRegistro.getTipo();
				nomeSoggetto = soggettoRegistro.getNome();
			}else{
				tipoSoggetto = soggettoConfig.getTipo();
				nomeSoggetto = soggettoConfig.getNome();
			}
			IDSoggetto idSoggetto = new IDSoggetto(tipoSoggetto, nomeSoggetto);
			
			if(soggettoRegistro!=null){
				
				return DBOggettiInUsoUtils.isSoggettoRegistryInUso(con, this.tipoDB, idSoggetto, whereIsInUso);
			
			}
			else{
				
				return DBOggettiInUsoUtils.isSoggettoConfigInUso(con, this.tipoDB, idSoggetto, whereIsInUso);
				
			}

		} catch (Exception se) {
			throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo + "] Exception: " + se.getMessage(),se);
		} finally {
			try {
				if (this.atomica) {
					this.log.debug("rilascio connessioni al db...");
					con.close();
				}
			} catch (Exception e) {
				// ignore exception
			}
		}
	}
	
	public boolean isRuoloInUso(IDRuolo idRuolo, Map<ErrorsHandlerCostant, List<String>> whereIsInUso) throws DriverControlStationException {
		String nomeMetodo = "isRuoloInUso";

		Connection con = null;
		if (this.atomica) {
			try {
				con = this.datasource.getConnection();

			} catch (SQLException e) {
				throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo + "] SQLException accedendo al datasource :" + e.getMessage());

			}

		} else {
			con = this.globalConnection;
		}

		this.log.debug("operazione this.atomica = " + this.atomica);

		try {

			return DBOggettiInUsoUtils.isRuoloInUso(con, this.tipoDB, idRuolo, whereIsInUso);
			
		} catch (Exception se) {
			throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo + "] Exception: " + se.getMessage(),se);
		} finally {
			try {
				if (this.atomica) {
					this.log.debug("rilascio connessioni al db...");
					con.close();
				}
			} catch (Exception e) {
				// ignore exception
			}
		}
	}
	
	public boolean isRuoloConfigInUso(IDRuolo idRuolo, Map<ErrorsHandlerCostant, List<String>> whereIsInUso) throws DriverControlStationException {
		String nomeMetodo = "isRuoloConfigInUso";

		Connection con = null;
		if (this.atomica) {
			try {
				con = this.datasource.getConnection();

			} catch (SQLException e) {
				throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo + "] SQLException accedendo al datasource :" + e.getMessage());

			}

		} else {
			con = this.globalConnection;
		}

		this.log.debug("operazione this.atomica = " + this.atomica);

		try {

			return DBOggettiInUsoUtils.isRuoloConfigInUso(con, this.tipoDB, idRuolo, whereIsInUso);
			
		} catch (Exception se) {
			throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo + "] Exception: " + se.getMessage(),se);
		} finally {
			try {
				if (this.atomica) {
					this.log.debug("rilascio connessioni al db...");
					con.close();
				}
			} catch (Exception e) {
				// ignore exception
			}
		}
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	/* *********** METODI EXISTS ****************** */
	
	public long existServizio(String nomeServizio, String tipoServizio, int versioneServizio, long idSoggettoErogatore) throws DriverControlStationException {
				String nomeMetodo = "existServizio";
		
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet risultato = null;
		String queryString;
		
		if (this.atomica) {
			try {
				con = this.datasource.getConnection();
			} catch (SQLException e) {
				throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo + "] SQLException accedendo al datasource :" + e.getMessage());
			}
		} else {
			con = this.globalConnection;
		}
		
		try {
		
			ISQLQueryObject sqlQueryObject = SQLObjectFactory.createSQLQueryObject(this.tipoDB);
			sqlQueryObject.addFromTable(CostantiDB.SERVIZI);
			sqlQueryObject.addSelectField("*");
			sqlQueryObject.addWhereCondition("id_soggetto = ?");
			sqlQueryObject.addWhereCondition("nome_servizio = ?");
			sqlQueryObject.addWhereCondition("tipo_servizio = ?");
			sqlQueryObject.addWhereCondition("versione_servizio = ?");
			sqlQueryObject.setANDLogicOperator(true);
			queryString = sqlQueryObject.createSQLQuery();
		
			stmt = con.prepareStatement(queryString);
			stmt.setLong(1, idSoggettoErogatore);
			stmt.setString(2, nomeServizio);
			stmt.setString(3, tipoServizio);
			stmt.setInt(4, versioneServizio);
		
			risultato = stmt.executeQuery();
			if (risultato.next()) {
				return risultato.getLong("id");
			}
		
			return 0;
		} catch (Exception se) {
			throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo + "] Exception: " + se.getMessage(),se);
		} finally {
			// Chiudo statement and resultset
			try {
				if (risultato != null) {
					risultato.close();
				}
				if (stmt != null) {
					stmt.close();
				}
			} catch (Exception e) {
				// ignore
			}
			try {
				if (this.atomica) {
					this.log.debug("rilascio connessioni al db...");
					con.close();
				}
		
			} catch (Exception e) {
				// ignore exception
			}
		}
	}
		
	public long existServizio_implementazioneAccordoCheck(String nomeServizio, String tipoServizio, long idSoggettoErogatore, long idAccordo, boolean servizioCorrelato) throws DriverControlStationException {
		String nomeMetodo = "existServizio_implementazioneAccordoCheck";
		
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet risultato = null;
		String queryString;
		
		if (this.atomica) {
			try {
				con = this.datasource.getConnection();
			} catch (SQLException e) {
				throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo + "] SQLException accedendo al datasource :" + e.getMessage());
		
			}
		} else {
			con = this.globalConnection;
		}
		
		try {
		
			ISQLQueryObject sqlQueryObject = SQLObjectFactory.createSQLQueryObject(this.tipoDB);
			sqlQueryObject.addFromTable(CostantiDB.SERVIZI);
			sqlQueryObject.addSelectField("*");
			sqlQueryObject.addWhereCondition("id_soggetto = ?");
			sqlQueryObject.addWhereCondition("servizio_correlato = ?");
			sqlQueryObject.addWhereCondition("id_accordo = ?");
			sqlQueryObject.setANDLogicOperator(true);
			queryString = sqlQueryObject.createSQLQuery();
		
			stmt = con.prepareStatement(queryString);
			stmt.setLong(1, idSoggettoErogatore);
			stmt.setString(2, (servizioCorrelato ? CostantiRegistroServizi.ABILITATO.toString() : CostantiRegistroServizi.DISABILITATO.toString()));
			stmt.setLong(3, idAccordo);
		
			risultato = stmt.executeQuery();
			while (risultato.next()) {
				if (tipoServizio.equals(risultato.getString("tipo_servizio")) && nomeServizio.equals(risultato.getString("nome_servizio")))
					continue;
				try{
					return risultato.getLong("id");
				}finally{
					try {
						if (risultato != null) {
							risultato.close();
						}
					} catch (Exception e) {
						// ignore
					}
				}
			}
		
			return 0;
		} catch (Exception se) {
			throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo + "] Exception: " + se.getMessage(),se);
		} finally {
			// Chiudo statement and resultset
			try {
				if (risultato != null) {
					risultato.close();
				}
				if (stmt != null) {
					stmt.close();
				}
			} catch (Exception e) {
				// ignore
			}
			try {
				if (this.atomica) {
					this.log.debug("rilascio connessioni al db...");
					con.close();
				}
		
			} catch (Exception e) {
				// ignore exception
			}
		}
	}

	
	public long existPdd(String nomePdd) throws DriverControlStationException {
		String nomeMetodo = "existPdd";

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet risultato = null;
		String queryString = "";

		if (this.atomica) {
			try {
				con = this.datasource.getConnection();

			} catch (SQLException e) {
				throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo + "] SQLException accedendo al datasource :" + e.getMessage());

			}

		} else {
			con = this.globalConnection;
		}

		try {

			ISQLQueryObject sqlQueryObject = SQLObjectFactory.createSQLQueryObject(this.tipoDB);
			sqlQueryObject.addFromTable(CostantiDB.PDD);
			sqlQueryObject.addSelectField("*");
			sqlQueryObject.addWhereCondition("nome = ?");
			queryString = sqlQueryObject.createSQLQuery();

			stmt = con.prepareStatement(queryString);
			stmt.setString(1, nomePdd);
			risultato = stmt.executeQuery();
			if (risultato.next()) {
				return risultato.getLong("id");
			}

			return 0;
		} catch (Exception se) {
			throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo + "] Exception: " + se.getMessage(),se);
		} finally {
			// Chiudo statement and resultset
			try {
				if (risultato != null) {
					risultato.close();
				}
				if (stmt != null) {
					stmt.close();
				}
			} catch (Exception e) {
				// ignore
			}
			try {
				if (this.atomica) {
					this.log.debug("rilascio connessioni al db...");
					con.close();
				}

			} catch (Exception e) {
				// ignore exception
			}
		}
	}
	

	// Controllo Congestione
	/**
	 * Restituisce la configurazione generale della Porta di Dominio
	 * 
	 * @return Configurazione
	 * 
	 */
	public ConfigurazioneGenerale getConfigurazioneControlloCongestione() throws DriverControlStationException,DriverControlStationNotFound {
		String nomeMetodo = "getConfigurazioneControlloCongestione";
		// ritorna la configurazione controllo congestione della PdD
		Connection con = null;

		if (this.atomica) {
			try {
				con = this.datasource.getConnection();

			} catch (SQLException e) {
				throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo + "] SQLException accedendo al datasource :" + e.getMessage());

			}

		} else {
			con = this.globalConnection;
		}

		this.log.debug("operazione this.atomica = " + this.atomica);
		ConfigurazioneGenerale config = null;
		try {
			
			ServiceManagerProperties properties = new ServiceManagerProperties();
			properties.setDatabaseType(this.tipoDB);
			properties.setShowSql(true);
			org.openspcoop2.core.controllo_congestione.dao.jdbc.JDBCServiceManager serviceManager = new org.openspcoop2.core.controllo_congestione.dao.jdbc.JDBCServiceManager(con, properties, this.log);
			
			try {
				config = serviceManager.getConfigurazioneGeneraleServiceSearch().get();
			}catch (NotFoundException e) {
				throw new DriverControlStationNotFound("[DriverControlStationDB::" + nomeMetodo + "] Configurazione non presente.");
			}
		}catch (Exception se) {
			throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo + "] Exception: " + se.getMessage(),se);
		} finally {
			try {
				if (this.atomica) {
					this.log.debug("rilascio connessioni al db...");
					con.close();
				}
			} catch (Exception e) {
				// ignore exception
			}
		}

		return config;
	}

	/***
	 * Aggiorna la configurazione del controllo congestione
	 * 
	 * @param configurazioneControlloCongestione
	 * @throws DriverControlStationException
	 */
	public void updateConfigurazioneControlloCongestione(ConfigurazioneGenerale configurazioneControlloCongestione) throws DriverControlStationException {
		String nomeMetodo = "updateConfigurazioneControlloCongestione";
		Connection con = null;
		if (this.atomica) {
			try {
				con = this.datasource.getConnection();

			} catch (SQLException e) {
				throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo + "] SQLException accedendo al datasource :" + e.getMessage());

			}

		} else {
			con = this.globalConnection;
		}

		this.log.debug("operazione this.atomica = " + this.atomica);

		try {

			ServiceManagerProperties properties = new ServiceManagerProperties();
			properties.setDatabaseType(this.tipoDB);
			properties.setShowSql(true);
			org.openspcoop2.core.controllo_congestione.dao.jdbc.JDBCServiceManager serviceManager = new org.openspcoop2.core.controllo_congestione.dao.jdbc.JDBCServiceManager(con, properties, this.log);
			
			serviceManager.getConfigurazioneGeneraleService().update(configurazioneControlloCongestione);
		} catch (Exception se) {
			throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo + "] Exception: " + se.getMessage(),se);
		} finally {
			try {
				if (this.atomica) {
					this.log.debug("rilascio connessioni al db...");
					con.close();
				}
			} catch (Exception e) {
				// ignore exception
			}
		}
	}
	
	/**
	 * Restituisce il numero di Configurazione Policy
	 * 
	 * @return Configurazione
	 * 
	 */
	public long countConfigurazioneControlloCongestioneConfigurazionePolicy(ISearch ricerca) throws DriverControlStationException {
		String nomeMetodo = "countConfigurazioneControlloCongestioneConfigurazionePolicy";
		// ritorna la configurazione controllo congestione della PdD
		Connection con = null;
		int idLista = Liste.CONFIGURAZIONE_CONTROLLO_CONGESTIONE_CONFIGURAZIONE_POLICY;
		String search = null;

		if(ricerca != null) {
			search = (org.openspcoop2.core.constants.Costanti.SESSION_ATTRIBUTE_VALUE_RICERCA_UNDEFINED.equals(ricerca.getSearchString(idLista)) ? "" : ricerca.getSearchString(idLista));
		}

		if (this.atomica) {
			try {
				con = this.datasource.getConnection();

			} catch (SQLException e) {
				throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo + "] SQLException accedendo al datasource :" + e.getMessage());

			}

		} else {
			con = this.globalConnection;
		}

		this.log.debug("operazione this.atomica = " + this.atomica);
		long count = 0;
		try {
			
			ServiceManagerProperties properties = new ServiceManagerProperties();
			properties.setDatabaseType(this.tipoDB);
			properties.setShowSql(true);
			org.openspcoop2.core.controllo_congestione.dao.jdbc.JDBCServiceManager serviceManager = new org.openspcoop2.core.controllo_congestione.dao.jdbc.JDBCServiceManager(con, properties, this.log);
			
			IExpression expr = serviceManager.getConfigurazionePolicyServiceSearch().newExpression();
			
			if(search != null  && !"".equals(search)){
				expr.ilike(ConfigurazionePolicy.model().ID_POLICY, search, LikeMode.ANYWHERE);
			}
			
			NonNegativeNumber nnn = serviceManager.getConfigurazionePolicyServiceSearch().count(expr);
			
			count = nnn != null ? nnn.longValue() : 0;
		}catch (Exception se) {
			throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo + "] Exception: " + se.getMessage(),se);
		} finally {
			try {
				if (this.atomica) {
					this.log.debug("rilascio connessioni al db...");
					con.close();
				}
			} catch (Exception e) {
				// ignore exception
			}
		}

		return count;
	}
	
	/**
	 * Restituisce il numero di Attivazione Policy
	 * 
	 * @return Configurazione
	 * 
	 */
	public long countConfigurazioneControlloCongestioneAttivazionePolicy(ISearch ricerca) throws DriverControlStationException {
		String nomeMetodo = "countConfigurazioneControlloCongestioneAttivazionePolicy";
		// ritorna la configurazione controllo congestione della PdD
		Connection con = null;
		int idLista = Liste.CONFIGURAZIONE_CONTROLLO_CONGESTIONE_ATTIVAZIONE_POLICY;
		String search = null;

		if(ricerca != null) {
			search = (org.openspcoop2.core.constants.Costanti.SESSION_ATTRIBUTE_VALUE_RICERCA_UNDEFINED.equals(ricerca.getSearchString(idLista)) ? "" : ricerca.getSearchString(idLista));
		}

		if (this.atomica) {
			try {
				con = this.datasource.getConnection();

			} catch (SQLException e) {
				throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo + "] SQLException accedendo al datasource :" + e.getMessage());

			}

		} else {
			con = this.globalConnection;
		}

		this.log.debug("operazione this.atomica = " + this.atomica);
		long count = 0;
		try {
			
			ServiceManagerProperties properties = new ServiceManagerProperties();
			properties.setDatabaseType(this.tipoDB);
			properties.setShowSql(true);
			org.openspcoop2.core.controllo_congestione.dao.jdbc.JDBCServiceManager serviceManager = new org.openspcoop2.core.controllo_congestione.dao.jdbc.JDBCServiceManager(con, properties, this.log);
			
			IExpression expr = serviceManager.getAttivazionePolicyServiceSearch().newExpression();
			
			if(search != null  && !"".equals(search)){
				expr.ilike(AttivazionePolicy.model().ID_POLICY, search, LikeMode.ANYWHERE);
			}
			NonNegativeNumber nnn = serviceManager.getAttivazionePolicyServiceSearch().count(expr);
			
			count = nnn != null ? nnn.longValue() : 0;
		}catch (Exception se) {
			throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo + "] Exception: " + se.getMessage(),se);
		} finally {
			try {
				if (this.atomica) {
					this.log.debug("rilascio connessioni al db...");
					con.close();
				}
			} catch (Exception e) {
				// ignore exception
			}
		}

		return count;
	}

	public List<ConfigurazionePolicy> configurazioneControlloCongestioneConfigurazionePolicyList(Search ricerca) throws DriverControlStationException{
		String nomeMetodo = "configurazioneControlloCongestioneConfigurazionePolicyList";
		// ritorna la configurazione controllo congestione della PdD
		Connection con = null;
		int idLista = Liste.CONFIGURAZIONE_CONTROLLO_CONGESTIONE_CONFIGURAZIONE_POLICY;
		String search = null;
		int offset = 0;
		int limit =0 ;

		if(ricerca != null) {
			search = (org.openspcoop2.core.constants.Costanti.SESSION_ATTRIBUTE_VALUE_RICERCA_UNDEFINED.equals(ricerca.getSearchString(idLista)) ? "" : ricerca.getSearchString(idLista));
			limit = ricerca.getPageSize(idLista);
			offset = ricerca.getIndexIniziale(idLista);
		}
		if (limit == 0) // con limit
			limit = ISQLQueryObject.LIMIT_DEFAULT_VALUE;
		
		if (this.atomica) {
			try {
				con = this.datasource.getConnection();

			} catch (SQLException e) {
				throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo + "] SQLException accedendo al datasource :" + e.getMessage());

			}

		} else {
			con = this.globalConnection;
		}

		this.log.debug("operazione this.atomica = " + this.atomica);
		List<ConfigurazionePolicy> listaPolicy = new ArrayList<ConfigurazionePolicy>();
		
		try {
			
			ServiceManagerProperties properties = new ServiceManagerProperties();
			properties.setDatabaseType(this.tipoDB);
			properties.setShowSql(true);
			org.openspcoop2.core.controllo_congestione.dao.jdbc.JDBCServiceManager serviceManager = new org.openspcoop2.core.controllo_congestione.dao.jdbc.JDBCServiceManager(con, properties, this.log);
			
			IExpression expr = serviceManager.getConfigurazionePolicyServiceSearch().newExpression();
			
			if(search != null  && !"".equals(search)){
				expr.ilike(ConfigurazionePolicy.model().ID_POLICY, search, LikeMode.ANYWHERE);
			}
			
			IPaginatedExpression pagExpr = serviceManager.getConfigurazionePolicyServiceSearch().toPaginatedExpression(expr);
			pagExpr.offset(offset).limit(limit);
			pagExpr.sortOrder(SortOrder.ASC);
			pagExpr.addOrder(ConfigurazionePolicy.model().ID_POLICY);
			
			listaPolicy = serviceManager.getConfigurazionePolicyServiceSearch().findAll(pagExpr);
		} catch (Exception qe) {
			throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo +"] Errore : " + qe.getMessage(),qe);
		} finally {
			try {
				if (this.atomica) {
					this.log.debug("rilascio connessioni al db...");
					con.close();
				}
			} catch (Exception e) {
				// ignore exception
			}
		}
		
		return listaPolicy;
	}

	public List<AttivazionePolicy> configurazioneControlloCongestioneAttivazionePolicyList(Search ricerca) throws DriverControlStationException{
		String nomeMetodo = "configurazioneControlloCongestioneAttivazionePolicyList";
		// ritorna la configurazione controllo congestione della PdD
		Connection con = null;
		int idLista = Liste.CONFIGURAZIONE_CONTROLLO_CONGESTIONE_ATTIVAZIONE_POLICY;
		String search = null;
		int offset = 0;
		int limit =0 ;

		if(ricerca != null) {
			search = (org.openspcoop2.core.constants.Costanti.SESSION_ATTRIBUTE_VALUE_RICERCA_UNDEFINED.equals(ricerca.getSearchString(idLista)) ? "" : ricerca.getSearchString(idLista));
			limit = ricerca.getPageSize(idLista);
			offset = ricerca.getIndexIniziale(idLista);
		}
		if (limit == 0) // con limit
			limit = ISQLQueryObject.LIMIT_DEFAULT_VALUE;
		
		if (this.atomica) {
			try {
				con = this.datasource.getConnection();

			} catch (SQLException e) {
				throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo + "] SQLException accedendo al datasource :" + e.getMessage());

			}

		} else {
			con = this.globalConnection;
		}

		this.log.debug("operazione this.atomica = " + this.atomica);
		List<AttivazionePolicy> listaPolicy = new ArrayList<AttivazionePolicy>();
		
		try {
			
			ServiceManagerProperties properties = new ServiceManagerProperties();
			properties.setDatabaseType(this.tipoDB);
			properties.setShowSql(true);
			org.openspcoop2.core.controllo_congestione.dao.jdbc.JDBCServiceManager serviceManager = new org.openspcoop2.core.controllo_congestione.dao.jdbc.JDBCServiceManager(con, properties, this.log);
			
			IExpression expr = serviceManager.getAttivazionePolicyServiceSearch().newExpression();
			
			if(search != null  && !"".equals(search)){
				expr.ilike(AttivazionePolicy.model().ID_POLICY, search, LikeMode.ANYWHERE);
			}
		
			IPaginatedExpression pagExpr = serviceManager.getAttivazionePolicyServiceSearch().toPaginatedExpression(expr);
			pagExpr.offset(offset).limit(limit);
			pagExpr.sortOrder(SortOrder.ASC);
			pagExpr.addOrder(AttivazionePolicy.model().ALIAS);
			pagExpr.addOrder(AttivazionePolicy.model().ID_POLICY);
			
			listaPolicy = serviceManager.getAttivazionePolicyServiceSearch().findAll(pagExpr);
		} catch (Exception qe) {
			throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo +"] Errore : " + qe.getMessage(),qe);
		} finally {
			try {
				if (this.atomica) {
					this.log.debug("rilascio connessioni al db...");
					con.close();
				}
			} catch (Exception e) {
				// ignore exception
			}
		}
		
		return listaPolicy;
	}
	
	public List<InfoPolicy> getInfoPolicyList(String idPolicyParam) throws DriverControlStationException{
		String nomeMetodo = "getInfoPolicyList";
		// ritorna la configurazione controllo congestione della PdD
		Connection con = null;
		int offset = 0;
		int limit = ISQLQueryObject.LIMIT_DEFAULT_VALUE;
		
		if (this.atomica) {
			try {
				con = this.datasource.getConnection();

			} catch (SQLException e) {
				throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo + "] SQLException accedendo al datasource :" + e.getMessage());

			}

		} else {
			con = this.globalConnection;
		}

		this.log.debug("operazione this.atomica = " + this.atomica);
		List<InfoPolicy> listaPolicy = new ArrayList<InfoPolicy>();
		
		try {
			
			ServiceManagerProperties properties = new ServiceManagerProperties();
			properties.setDatabaseType(this.tipoDB);
			properties.setShowSql(true);
			org.openspcoop2.core.controllo_congestione.dao.jdbc.JDBCServiceManager serviceManager = new org.openspcoop2.core.controllo_congestione.dao.jdbc.JDBCServiceManager(con, properties, this.log);
			
			IExpression expr = serviceManager.getConfigurazionePolicyServiceSearch().newExpression();
			
			if(idPolicyParam != null  && !"".equals(idPolicyParam)){
				expr.equals(ConfigurazionePolicy.model().ID_POLICY, idPolicyParam);
			}
			
			IPaginatedExpression pagExpr = serviceManager.getConfigurazionePolicyServiceSearch().toPaginatedExpression(expr);
			pagExpr.offset(offset).limit(limit);
			pagExpr.sortOrder(SortOrder.ASC);
			pagExpr.addOrder(ConfigurazionePolicy.model().ID_POLICY);
			
			List<java.util.Map<String, Object>> mapList = null;
			try{
				mapList = serviceManager.getConfigurazionePolicyServiceSearch().select(pagExpr, 
						ConfigurazionePolicy.model().ID_POLICY,
						ConfigurazionePolicy.model().RISORSA,
						ConfigurazionePolicy.model().DESCRIZIONE,
						ConfigurazionePolicy.model().VALORE,
						ConfigurazionePolicy.model().SIMULTANEE,
						ConfigurazionePolicy.model().MODALITA_CONTROLLO,
						ConfigurazionePolicy.model().TIPO_APPLICABILITA,
						ConfigurazionePolicy.model().APPLICABILITA_DEGRADO_PRESTAZIONALE,
						ConfigurazionePolicy.model().DEGRADO_AVG_TIME_MODALITA_CONTROLLO
						);
			}catch(NotFoundException notFound){}
			if(mapList!=null){
				// caso get puntuale
				if(idPolicyParam != null  && !"".equals(idPolicyParam) && mapList.size()>1){
					throw new Exception("More than one results ("+mapList.size()+")");
				}
				
				
				for (java.util.Map<String, Object> map : mapList) {
					String idPolicy = (String) map.get(ConfigurazionePolicy.model().ID_POLICY.getFieldName());
					String resource = (String) map.get(ConfigurazionePolicy.model().RISORSA.getFieldName());
					InfoPolicy info = new InfoPolicy();
					info.setIdPolicy(idPolicy);
					info.setTipoRisorsa(TipoRisorsa.toEnumConstant(resource, true));
					
					Object descr =  map.get(ConfigurazionePolicy.model().DESCRIZIONE.getFieldName());
					if(descr!=null && descr instanceof String){
						info.setDescrizione((String)descr);
					}
					
					Object v =  map.get(ConfigurazionePolicy.model().VALORE.getFieldName());
					if(v!=null && v instanceof Long){
						info.setValore((Long)v);
					}
					
					Object simultanee =  map.get(ConfigurazionePolicy.model().SIMULTANEE.getFieldName());
					if(simultanee!=null && simultanee instanceof Boolean){
						boolean simultaneeBoolean = (Boolean) simultanee;
						info.setCheckRichiesteSimultanee(simultaneeBoolean);
						if(!simultaneeBoolean){
							Object controllo =  map.get(ConfigurazionePolicy.model().MODALITA_CONTROLLO.getFieldName());
                        	if(controllo!=null && controllo instanceof String){
                        		TipoControlloPeriodo tipo = TipoControlloPeriodo.toEnumConstant((String)controllo, true);
                        		info.setIntervalloUtilizzaRisorseStatistiche(TipoControlloPeriodo.STATISTIC.equals(tipo));
                        		info.setIntervalloUtilizzaRisorseRealtime(TipoControlloPeriodo.REALTIME.equals(tipo));
                        	}
						}
					}
										
						
					// verifico anche degrado prestazionale
                    Object app =  map.get(ConfigurazionePolicy.model().TIPO_APPLICABILITA.getFieldName());
                    if(app!=null && app instanceof String){
                        TipoApplicabilita tipoApplicabilita = TipoApplicabilita.toEnumConstant((String)app, true);
                        if(TipoApplicabilita.CONDIZIONALE.equals(tipoApplicabilita)){
                        	Object enabled =  map.get(ConfigurazionePolicy.model().APPLICABILITA_DEGRADO_PRESTAZIONALE.getFieldName());
                            boolean degrado = false;   
                            if(enabled!=null && (enabled instanceof Boolean)){
                            	degrado = (Boolean)enabled;
                            }
                            if(degrado){
                            	Object controllo =  map.get(ConfigurazionePolicy.model().DEGRADO_AVG_TIME_MODALITA_CONTROLLO.getFieldName());
                            	if(controllo!=null && controllo instanceof String){
                            		TipoControlloPeriodo tipo = TipoControlloPeriodo.toEnumConstant((String)controllo, true);
                            		info.setDegradoPrestazionaleUtilizzaRisorseStatistiche(TipoControlloPeriodo.STATISTIC.equals(tipo));
                            		info.setDegradoPrestazionaleUtilizzaRisorseRealtime(TipoControlloPeriodo.REALTIME.equals(tipo));
                            	}
                            }
                        }
                    }

					
                    listaPolicy.add(info);
				}
			}
		} catch (Exception qe) {
			throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo +"] Errore : " + qe.getMessage(),qe);
		} finally {
			try {
				if (this.atomica) {
					this.log.debug("rilascio connessioni al db...");
					con.close();
				}
			} catch (Exception e) {
				// ignore exception
			}
		}
		
		return listaPolicy;
	}
	
	public List<AttivazionePolicy> findInUseAttivazioni(String idPolicy, boolean escludiDisabilitate) throws DriverControlStationException{
		String nomeMetodo = "findInUseAttivazioni";
		// ritorna la configurazione controllo congestione della PdD
		Connection con = null;
		int offset = 0;
		int limit = 10000 ;  // valore altissimo che non dovrebbe accadare
		
		if (this.atomica) {
			try {
				con = this.datasource.getConnection();

			} catch (SQLException e) {
				throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo + "] SQLException accedendo al datasource :" + e.getMessage());

			}

		} else {
			con = this.globalConnection;
		}

		this.log.debug("operazione this.atomica = " + this.atomica);
		List<AttivazionePolicy> listaPolicy = new ArrayList<AttivazionePolicy>();
		
		try {
			
			ServiceManagerProperties properties = new ServiceManagerProperties();
			properties.setDatabaseType(this.tipoDB);
			properties.setShowSql(true);
			org.openspcoop2.core.controllo_congestione.dao.jdbc.JDBCServiceManager serviceManager = new org.openspcoop2.core.controllo_congestione.dao.jdbc.JDBCServiceManager(con, properties, this.log);
			
			IExpression expr = serviceManager.getAttivazionePolicyServiceSearch().newExpression();
			
			expr.equals(AttivazionePolicy.model().ID_POLICY, idPolicy);
			if(escludiDisabilitate) {
				expr.equals(AttivazionePolicy.model().ENABLED, true);
			}
		
			IPaginatedExpression pagExpr = serviceManager.getAttivazionePolicyServiceSearch().toPaginatedExpression(expr);
			pagExpr.offset(offset).limit(limit);
			pagExpr.sortOrder(SortOrder.ASC);
			pagExpr.addOrder(AttivazionePolicy.model().ALIAS);
			pagExpr.addOrder(AttivazionePolicy.model().ID_POLICY);
			
			listaPolicy = serviceManager.getAttivazionePolicyServiceSearch().findAll(pagExpr);
		} catch (Exception qe) {
			throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo +"] Errore : " + qe.getMessage(),qe);
		} finally {
			try {
				if (this.atomica) {
					this.log.debug("rilascio connessioni al db...");
					con.close();
				}
			} catch (Exception e) {
				// ignore exception
			}
		}
		
		return listaPolicy;
	}
	
	public long countInUseAttivazioni(String idPolicy, boolean escludiDisabilitate) throws DriverControlStationException{
		String nomeMetodo = "countInUseAttivazioni";
		// ritorna la configurazione controllo congestione della PdD
		Connection con = null;

		if (this.atomica) {
			try {
				con = this.datasource.getConnection();

			} catch (SQLException e) {
				throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo + "] SQLException accedendo al datasource :" + e.getMessage());

			}

		} else {
			con = this.globalConnection;
		}

		this.log.debug("operazione this.atomica = " + this.atomica);
		try {
			
			ServiceManagerProperties properties = new ServiceManagerProperties();
			properties.setDatabaseType(this.tipoDB);
			properties.setShowSql(true);
			org.openspcoop2.core.controllo_congestione.dao.jdbc.JDBCServiceManager serviceManager = new org.openspcoop2.core.controllo_congestione.dao.jdbc.JDBCServiceManager(con, properties, this.log);
			
			IExpression expr = serviceManager.getAttivazionePolicyServiceSearch().newExpression();
			
			expr.equals(AttivazionePolicy.model().ID_POLICY, idPolicy);
			if(escludiDisabilitate) {
				expr.equals(AttivazionePolicy.model().ENABLED, true);
			}
			
			NonNegativeNumber nnn = serviceManager.getAttivazionePolicyServiceSearch().count(expr);
			return nnn != null ? nnn.longValue(): 0;
		} catch (Exception qe) {
			throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo +"] Errore : " + qe.getMessage(),qe);
		} finally {
			try {
				if (this.atomica) {
					this.log.debug("rilascio connessioni al db...");
					con.close();
				}
			} catch (Exception e) {
				// ignore exception
			}
		}
	}
	
	public ConfigurazionePolicy getConfigurazionePolicy(long id) throws DriverControlStationException,DriverControlStationNotFound{
		String nomeMetodo = "getConfigurazionePolicy";
		Connection con = null;
		if (this.atomica) {
			try {
				con = this.datasource.getConnection();

			} catch (SQLException e) {
				throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo + "] SQLException accedendo al datasource :" + e.getMessage());

			}

		} else {
			con = this.globalConnection;
		}

		this.log.debug("operazione this.atomica = " + this.atomica);
		ConfigurazionePolicy policy = null;
		
		try {
			
			ServiceManagerProperties properties = new ServiceManagerProperties();
			properties.setDatabaseType(this.tipoDB);
			properties.setShowSql(true);
			org.openspcoop2.core.controllo_congestione.dao.jdbc.JDBCServiceManager serviceManager = new org.openspcoop2.core.controllo_congestione.dao.jdbc.JDBCServiceManager(con, properties, this.log);
			policy = ((IDBConfigurazionePolicyServiceSearch)serviceManager.getConfigurazionePolicyServiceSearch()).get(id);
		}catch (NotFoundException e) {
			throw new DriverControlStationNotFound("[DriverControlStationDB::" + nomeMetodo + "] Configurazione Policy non presente.");
		} catch (Exception qe) {
			throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo +"] Errore : " + qe.getMessage(),qe);
		} finally {
			try {
				if (this.atomica) {
					this.log.debug("rilascio connessioni al db...");
					con.close();
				}
			} catch (Exception e) {
				// ignore exception
			}
		}
		
		return policy;
	}
	
	public ConfigurazionePolicy getConfigurazionePolicy(String nomePolicy) throws DriverControlStationException,DriverControlStationNotFound{
		String nomeMetodo = "getConfigurazionePolicy";
		Connection con = null;
		if (this.atomica) {
			try {
				con = this.datasource.getConnection();

			} catch (SQLException e) {
				throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo + "] SQLException accedendo al datasource :" + e.getMessage());

			}

		} else {
			con = this.globalConnection;
		}

		this.log.debug("operazione this.atomica = " + this.atomica);
		ConfigurazionePolicy policy = null;
		
		try {
			
			ServiceManagerProperties properties = new ServiceManagerProperties();
			properties.setDatabaseType(this.tipoDB);
			properties.setShowSql(true);
			org.openspcoop2.core.controllo_congestione.dao.jdbc.JDBCServiceManager serviceManager = new org.openspcoop2.core.controllo_congestione.dao.jdbc.JDBCServiceManager(con, properties, this.log);
			
			IdPolicy id = new IdPolicy();
			id.setNome(nomePolicy);
			policy = serviceManager.getConfigurazionePolicyServiceSearch().get(id);
		}catch (NotFoundException e) {
			throw new DriverControlStationNotFound("[DriverControlStationDB::" + nomeMetodo + "] Configurazione Policy non presente.");
		} catch (Exception qe) {
			throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo +"] Errore : " + qe.getMessage(),qe);
		} finally {
			try {
				if (this.atomica) {
					this.log.debug("rilascio connessioni al db...");
					con.close();
				}
			} catch (Exception e) {
				// ignore exception
			}
		}
		
		return policy;
	}

	public void createConfigurazionePolicy(ConfigurazionePolicy policy) throws DriverControlStationException{
		String nomeMetodo = "createConfigurazionePolicy";
		Connection con = null;
		if (this.atomica) {
			try {
				con = this.datasource.getConnection();

			} catch (SQLException e) {
				throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo + "] SQLException accedendo al datasource :" + e.getMessage());

			}

		} else {
			con = this.globalConnection;
		}

		this.log.debug("operazione this.atomica = " + this.atomica);
		try {
			
			ServiceManagerProperties properties = new ServiceManagerProperties();
			properties.setDatabaseType(this.tipoDB);
			properties.setShowSql(true);
			org.openspcoop2.core.controllo_congestione.dao.jdbc.JDBCServiceManager serviceManager = new org.openspcoop2.core.controllo_congestione.dao.jdbc.JDBCServiceManager(con, properties, this.log);
			
			serviceManager.getConfigurazionePolicyService().create(policy); 
			
		} catch (Exception qe) {
			throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo +"] Errore : " + qe.getMessage(),qe);
		} finally {
			try {
				if (this.atomica) {
					this.log.debug("rilascio connessioni al db...");
					con.close();
				}
			} catch (Exception e) {
				// ignore exception
			}
		}	
	}

	public void createAttivazionePolicy(AttivazionePolicy policy) throws DriverControlStationException {
		String nomeMetodo = "createAttivazionePolicy";
		Connection con = null;
		if (this.atomica) {
			try {
				con = this.datasource.getConnection();

			} catch (SQLException e) {
				throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo + "] SQLException accedendo al datasource :" + e.getMessage());

			}

		} else {
			con = this.globalConnection;
		}

		this.log.debug("operazione this.atomica = " + this.atomica);
		try {
			
			ServiceManagerProperties properties = new ServiceManagerProperties();
			properties.setDatabaseType(this.tipoDB);
			properties.setShowSql(true);
			org.openspcoop2.core.controllo_congestione.dao.jdbc.JDBCServiceManager serviceManager = new org.openspcoop2.core.controllo_congestione.dao.jdbc.JDBCServiceManager(con, properties, this.log);
			
			serviceManager.getAttivazionePolicyService().create(policy);
			
		} catch (Exception qe) {
			throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo +"] Errore : " + qe.getMessage(),qe);
		} finally {
			try {
				if (this.atomica) {
					this.log.debug("rilascio connessioni al db...");
					con.close();
				}
			} catch (Exception e) {
				// ignore exception
			}
		}	
	}

	public void updateConfigurazionePolicy(ConfigurazionePolicy policy)throws DriverControlStationException,DriverControlStationNotFound{
		String nomeMetodo = "updateConfigurazionePolicy";
		Connection con = null;
		if (this.atomica) {
			try {
				con = this.datasource.getConnection();

			} catch (SQLException e) {
				throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo + "] SQLException accedendo al datasource :" + e.getMessage());

			}

		} else {
			con = this.globalConnection;
		}

		this.log.debug("operazione this.atomica = " + this.atomica);
		try {

			ServiceManagerProperties properties = new ServiceManagerProperties();
			properties.setDatabaseType(this.tipoDB);
			properties.setShowSql(true);
			org.openspcoop2.core.controllo_congestione.dao.jdbc.JDBCServiceManager serviceManager = new org.openspcoop2.core.controllo_congestione.dao.jdbc.JDBCServiceManager(con, properties, this.log);
			
			IdPolicy idToUpdate = new IdPolicy();
			
			if(policy.getOldIdPolicy()!=null){
				idToUpdate.setNome(policy.getOldIdPolicy().getNome());
			}
			else{
				idToUpdate.setNome(policy.getIdPolicy());
			}
			
			// ElencoPolicyModificareInSeguitoModificaSogliaPolicy
			List<AttivazionePolicy> listPolicyAttiveConStatoDisabilitato = this.findInUseAttivazioni(idToUpdate.getNome(), true);
			
			if(listPolicyAttiveConStatoDisabilitato !=null && listPolicyAttiveConStatoDisabilitato.size()>0) {
				//System.out.println("UPDATEEEEEEE SOGLIA ("+listPolicyAttiveConStatoDisabilitato.size()+") DISABILITO");
				Hashtable<String, Boolean> mapStati = new Hashtable<String, Boolean>();
				// NOTA: Devo fare la dobbia modifica perche' c'e' un controllo sul livello db layer che verifica se ci sono state modifiche
				for (AttivazionePolicy ap : listPolicyAttiveConStatoDisabilitato) {
					IdActivePolicy idActivePolicy = new IdActivePolicy();
					idActivePolicy.setNome(ap.getIdActivePolicy());
					mapStati.put(ap.getIdActivePolicy(), ap.isEnabled());
					//System.out.println("SALVATO ["+ap.getIdActivePolicy()+"] = ["+ap.isEnabled()+"]");
					ap.setEnabled(false);
					//System.out.println("UPDATEEEEEEE SOGLIA ("+ap.getIdActivePolicy()+") CON STATO=FALSE");
					serviceManager.getAttivazionePolicyService().update(idActivePolicy,ap);
				}
				
				for (AttivazionePolicy ap : listPolicyAttiveConStatoDisabilitato) {
					IdActivePolicy idActivePolicy = new IdActivePolicy();
					idActivePolicy.setNome(ap.getIdActivePolicy());
					//System.out.println("RIPRISTINO ["+ap.getIdActivePolicy()+"] = ["+mapStati.get(ap.getIdActivePolicy())+"]");
					ap.setEnabled(mapStati.get(ap.getIdActivePolicy()));
					//System.out.println("UPDATE SOGLIA ("+ap.getIdActivePolicy()+") CON STATO=true");
					serviceManager.getAttivazionePolicyService().update(idActivePolicy,ap);
				}
			}
			
			serviceManager.getConfigurazionePolicyService().update(idToUpdate, policy); 
			
		}catch (NotFoundException e) {
			throw new DriverControlStationNotFound("[DriverControlStationDB::" + nomeMetodo + "] Configurazione Policy non presente.");
		} catch (Exception qe) {
			throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo +"] Errore : " + qe.getMessage(),qe);
		} finally {
			try {
				if (this.atomica) {
					this.log.debug("rilascio connessioni al db...");
					con.close();
				}
			} catch (Exception e) {
				// ignore exception
			}
		}	
	}

	public void updateAttivazionePolicy(AttivazionePolicy policy) throws DriverControlStationException,DriverControlStationNotFound{
		String nomeMetodo = "updateAttivazionePolicy";
		Connection con = null;
		if (this.atomica) {
			try {
				con = this.datasource.getConnection();

			} catch (SQLException e) {
				throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo + "] SQLException accedendo al datasource :" + e.getMessage());

			}

		} else {
			con = this.globalConnection;
		}

		this.log.debug("operazione this.atomica = " + this.atomica);
		try {
			
			ServiceManagerProperties properties = new ServiceManagerProperties();
			properties.setDatabaseType(this.tipoDB);
			properties.setShowSql(true);
			org.openspcoop2.core.controllo_congestione.dao.jdbc.JDBCServiceManager serviceManager = new org.openspcoop2.core.controllo_congestione.dao.jdbc.JDBCServiceManager(con, properties, this.log);
			
			IdActivePolicy id = new IdActivePolicy();
			id.setNome(policy.getIdActivePolicy()); 
			serviceManager.getAttivazionePolicyService().update(id, policy); 
			
		}catch (NotFoundException e) {
			throw new DriverControlStationNotFound("[DriverControlStationDB::" + nomeMetodo + "] Configurazione Policy non presente.");
		} catch (Exception qe) {
			throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo +"] Errore : " + qe.getMessage(),qe);
		} finally {
			try {
				if (this.atomica) {
					this.log.debug("rilascio connessioni al db...");
					con.close();
				}
			} catch (Exception e) {
				// ignore exception
			}
		}	
	}

	public void deleteConfigurazionePolicy(ConfigurazionePolicy policy) throws DriverControlStationException {
		String nomeMetodo = "deleteConfigurazionePolicy";
		Connection con = null;
		if (this.atomica) {
			try {
				con = this.datasource.getConnection();

			} catch (SQLException e) {
				throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo + "] SQLException accedendo al datasource :" + e.getMessage());

			}

		} else {
			con = this.globalConnection;
		}

		this.log.debug("operazione this.atomica = " + this.atomica);
		try {
			
			ServiceManagerProperties properties = new ServiceManagerProperties();
			properties.setDatabaseType(this.tipoDB);
			properties.setShowSql(true);
			org.openspcoop2.core.controllo_congestione.dao.jdbc.JDBCServiceManager serviceManager = new org.openspcoop2.core.controllo_congestione.dao.jdbc.JDBCServiceManager(con, properties, this.log);
			
			serviceManager.getConfigurazionePolicyService().delete(policy); 
			
		} catch (Exception qe) {
			throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo +"] Errore : " + qe.getMessage(),qe);
		} finally {
			try {
				if (this.atomica) {
					this.log.debug("rilascio connessioni al db...");
					con.close();
				}
			} catch (Exception e) {
				// ignore exception
			}
		}
	}

	public void deleteAttivazionePolicy(AttivazionePolicy policy) throws DriverControlStationException,DriverControlStationNotFound{
		String nomeMetodo = "deleteAttivazionePolicy";
		Connection con = null;
		if (this.atomica) {
			try {
				con = this.datasource.getConnection();

			} catch (SQLException e) {
				throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo + "] SQLException accedendo al datasource :" + e.getMessage());

			}

		} else {
			con = this.globalConnection;
		}

		this.log.debug("operazione this.atomica = " + this.atomica);
		try {
			
			ServiceManagerProperties properties = new ServiceManagerProperties();
			properties.setDatabaseType(this.tipoDB);
			properties.setShowSql(true);
			org.openspcoop2.core.controllo_congestione.dao.jdbc.JDBCServiceManager serviceManager = new org.openspcoop2.core.controllo_congestione.dao.jdbc.JDBCServiceManager(con, properties, this.log);
			
			serviceManager.getAttivazionePolicyService().delete(policy);
			
		} catch (Exception qe) {
			throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo +"] Errore : " + qe.getMessage(),qe);
		} finally {
			try {
				if (this.atomica) {
					this.log.debug("rilascio connessioni al db...");
					con.close();
				}
			} catch (Exception e) {
				// ignore exception
			}
		}
	}
	
	public AttivazionePolicy getAttivazionePolicy(long id) throws DriverControlStationException,DriverControlStationNotFound{
		String nomeMetodo = "getAttivazionePolicy"; 
		Connection con = null;
		if (this.atomica) {
			try {
				con = this.datasource.getConnection();

			} catch (SQLException e) {
				throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo + "] SQLException accedendo al datasource :" + e.getMessage());

			}

		} else {
			con = this.globalConnection;
		}

		this.log.debug("operazione this.atomica = " + this.atomica);
		AttivazionePolicy policy = null;
		
		try {
			ServiceManagerProperties properties = new ServiceManagerProperties();
			properties.setDatabaseType(this.tipoDB);
			properties.setShowSql(true);
			org.openspcoop2.core.controllo_congestione.dao.jdbc.JDBCServiceManager serviceManager = new org.openspcoop2.core.controllo_congestione.dao.jdbc.JDBCServiceManager(con, properties, this.log);
			policy = ((IDBAttivazionePolicyServiceSearch)serviceManager.getAttivazionePolicyServiceSearch()).get(id);
		}catch (NotFoundException e) {
			throw new DriverControlStationNotFound("[DriverControlStationDB::" + nomeMetodo + "] Configurazione Policy non presente.");
		} catch (Exception qe) {
			throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo +"] Errore : " + qe.getMessage(),qe);
		} finally {
			try {
				if (this.atomica) {
					this.log.debug("rilascio connessioni al db...");
					con.close();
				}
			} catch (Exception e) {
				// ignore exception
			}
		}
		
		return policy;
	}
	
	public AttivazionePolicy getAttivazionePolicy(String nomePolicy) throws DriverControlStationException,DriverControlStationNotFound{
		String nomeMetodo = "getAttivazionePolicy"; 
		Connection con = null;
		if (this.atomica) {
			try {
				con = this.datasource.getConnection();

			} catch (SQLException e) {
				throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo + "] SQLException accedendo al datasource :" + e.getMessage());

			}

		} else {
			con = this.globalConnection;
		}

		this.log.debug("operazione this.atomica = " + this.atomica);
		AttivazionePolicy policy = null;
		
		try {
			ServiceManagerProperties properties = new ServiceManagerProperties();
			properties.setDatabaseType(this.tipoDB);
			properties.setShowSql(true);
			org.openspcoop2.core.controllo_congestione.dao.jdbc.JDBCServiceManager serviceManager = new org.openspcoop2.core.controllo_congestione.dao.jdbc.JDBCServiceManager(con, properties, this.log);
			IdActivePolicy idPolicy = new IdActivePolicy();
			idPolicy.setNome(nomePolicy);
			policy = serviceManager.getAttivazionePolicyServiceSearch().get(idPolicy);
			
		}catch (NotFoundException e) {
			throw new DriverControlStationNotFound("[DriverControlStationDB::" + nomeMetodo + "] Configurazione Policy non presente.");
		} catch (Exception qe) {
			throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo +"] Errore : " + qe.getMessage(),qe);
		} finally {
			try {
				if (this.atomica) {
					this.log.debug("rilascio connessioni al db...");
					con.close();
				}
			} catch (Exception e) {
				// ignore exception
			}
		}
		
		return policy;
	}
	
	public Integer getFreeCounterForGlobalPolicy(String policyId) throws DriverControlStationException{
		String nomeMetodo = "getFreeCounterForGlobalPolicy"; 
		Connection con = null;
		if (this.atomica) {
			try {
				con = this.datasource.getConnection();

			} catch (SQLException e) {
				throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo + "] SQLException accedendo al datasource :" + e.getMessage());

			}

		} else {
			con = this.globalConnection;
		}

		this.log.debug("operazione this.atomica = " + this.atomica);
		
		try{
			ServiceManagerProperties properties = new ServiceManagerProperties();
			properties.setDatabaseType(this.tipoDB);
			properties.setShowSql(true);
			org.openspcoop2.core.controllo_congestione.dao.jdbc.JDBCServiceManager serviceManager = new org.openspcoop2.core.controllo_congestione.dao.jdbc.JDBCServiceManager(con, properties, this.log);
			
			IPaginatedExpression pagExpr = serviceManager.getAttivazionePolicyServiceSearch().newPaginatedExpression();
			pagExpr.and();
			pagExpr.equals(AttivazionePolicy.model().ID_POLICY, policyId);
			
			pagExpr.addOrder(AttivazionePolicy.model().ID_ACTIVE_POLICY, SortOrder.DESC);
			pagExpr.limit(1);
			
			try{
				List<Object> list = serviceManager.getAttivazionePolicyServiceSearch().select(pagExpr, AttivazionePolicy.model().ID_ACTIVE_POLICY);
				if(list!=null && list.size()>0){
					Object r = list.get(0); // limit 1
					if(r instanceof String){
						String s = (String)r;
						if(s.contains(":")){
							int last = s.lastIndexOf(":");
							if(last<(s.length()-1)){
								return Integer.parseInt(s.substring(s.lastIndexOf(":")+1,s.length())) + 1;
							}
						}
					}
				}
			}catch(NotFoundException notF){
				
			}
			return 1;
			
		} catch (Exception qe) {
			throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo +"] Errore : " + qe.getMessage(),qe);
		} finally {
			try {
				if (this.atomica) {
					this.log.debug("rilascio connessioni al db...");
					con.close();
				}
			} catch (Exception e) {
				// ignore exception
			}
		}
	}
	
	public AttivazionePolicy getGlobalPolicy(String policyId, AttivazionePolicyFiltro filtro, AttivazionePolicyRaggruppamento groupBy) throws DriverControlStationException,DriverControlStationNotFound{
		String nomeMetodo = "getFreeCounterForGlobalPolicy"; 
		Connection con = null;
		if (this.atomica) {
			try {
				con = this.datasource.getConnection();

			} catch (SQLException e) {
				throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo + "] SQLException accedendo al datasource :" + e.getMessage());

			}

		} else {
			con = this.globalConnection;
		}

		this.log.debug("operazione this.atomica = " + this.atomica);
		try{
			ServiceManagerProperties properties = new ServiceManagerProperties();
			properties.setDatabaseType(this.tipoDB);
			properties.setShowSql(true);
			org.openspcoop2.core.controllo_congestione.dao.jdbc.JDBCServiceManager serviceManager = new org.openspcoop2.core.controllo_congestione.dao.jdbc.JDBCServiceManager(con, properties, this.log);
			IExpression expression = serviceManager.getAttivazionePolicyServiceSearch().newExpression();
			
			expression.and();
			expression.equals(AttivazionePolicy.model().ID_POLICY, policyId);
			
			expression.equals(AttivazionePolicy.model().FILTRO.ENABLED, filtro.isEnabled());
			if(filtro.isEnabled()){
				if(filtro.getRuoloPorta()!=null){
					expression.equals(AttivazionePolicy.model().FILTRO.RUOLO_PORTA, filtro.getRuoloPorta());
				}
				else{
					expression.equals(AttivazionePolicy.model().FILTRO.RUOLO_PORTA, RuoloPolicy.ENTRAMBI);
				}
					
				if(filtro.getProtocollo()!=null){
					expression.equals(AttivazionePolicy.model().FILTRO.PROTOCOLLO, filtro.getProtocollo());
				}
				else{
					expression.isNull(AttivazionePolicy.model().FILTRO.PROTOCOLLO);
				}
				
				if(filtro.getTipoErogatore()!=null){
					expression.equals(AttivazionePolicy.model().FILTRO.TIPO_EROGATORE, filtro.getTipoErogatore());
				}
				else{
					expression.isNull(AttivazionePolicy.model().FILTRO.TIPO_EROGATORE);
				}
				if(filtro.getNomeErogatore()!=null){
					expression.equals(AttivazionePolicy.model().FILTRO.NOME_EROGATORE, filtro.getNomeErogatore());
				}
				else{
					expression.isNull(AttivazionePolicy.model().FILTRO.NOME_EROGATORE);
				}
								
				if(filtro.getTipoServizio()!=null){
					expression.equals(AttivazionePolicy.model().FILTRO.TIPO_SERVIZIO, filtro.getTipoServizio());
				}
				else{
					expression.isNull(AttivazionePolicy.model().FILTRO.TIPO_SERVIZIO);
				}
				if(filtro.getNomeServizio()!=null){
					expression.equals(AttivazionePolicy.model().FILTRO.NOME_SERVIZIO, filtro.getNomeServizio());
				}
				else{
					expression.isNull(AttivazionePolicy.model().FILTRO.NOME_SERVIZIO);
				}
				
				if(filtro.getAzione()!=null){
					expression.equals(AttivazionePolicy.model().FILTRO.AZIONE, filtro.getAzione());
				}
				else{
					expression.isNull(AttivazionePolicy.model().FILTRO.AZIONE);
				}
				
				if(filtro.getServizioApplicativoErogatore()!=null){
					expression.equals(AttivazionePolicy.model().FILTRO.SERVIZIO_APPLICATIVO_EROGATORE, filtro.getServizioApplicativoErogatore());
				}
				else{
					expression.isNull(AttivazionePolicy.model().FILTRO.SERVIZIO_APPLICATIVO_EROGATORE);
				}
				
				if(filtro.getTipoFruitore()!=null){
					expression.equals(AttivazionePolicy.model().FILTRO.TIPO_FRUITORE, filtro.getTipoFruitore());
				}
				else{
					expression.isNull(AttivazionePolicy.model().FILTRO.TIPO_FRUITORE);
				}
				if(filtro.getNomeFruitore()!=null){
					expression.equals(AttivazionePolicy.model().FILTRO.NOME_FRUITORE, filtro.getNomeFruitore());
				}
				else{
					expression.isNull(AttivazionePolicy.model().FILTRO.NOME_FRUITORE);
				}
				
				if(filtro.getServizioApplicativoFruitore()!=null){
					expression.equals(AttivazionePolicy.model().FILTRO.SERVIZIO_APPLICATIVO_FRUITORE, filtro.getServizioApplicativoFruitore());
				}
				else{
					expression.isNull(AttivazionePolicy.model().FILTRO.SERVIZIO_APPLICATIVO_FRUITORE);
				}
				
				expression.equals(AttivazionePolicy.model().FILTRO.INFORMAZIONE_APPLICATIVA_ENABLED, filtro.isInformazioneApplicativaEnabled());
				if(filtro.isInformazioneApplicativaEnabled() &&
						filtro.getInformazioneApplicativaTipo()!=null &&
						filtro.getInformazioneApplicativaNome()!=null &&
						filtro.getInformazioneApplicativaValore()!=null){
					expression.equals(AttivazionePolicy.model().FILTRO.INFORMAZIONE_APPLICATIVA_TIPO, filtro.getInformazioneApplicativaTipo());
					expression.like(AttivazionePolicy.model().FILTRO.INFORMAZIONE_APPLICATIVA_NOME, filtro.getInformazioneApplicativaNome(), LikeMode.EXACT); // Colonna CLOB
					expression.equals(AttivazionePolicy.model().FILTRO.INFORMAZIONE_APPLICATIVA_VALORE, filtro.getInformazioneApplicativaValore());
				}
			}
			
			expression.equals(AttivazionePolicy.model().GROUP_BY.ENABLED, groupBy.isEnabled());
			if(groupBy.isEnabled()){
				
				expression.equals(AttivazionePolicy.model().GROUP_BY.RUOLO_PORTA, groupBy.isRuoloPorta());
				
				expression.equals(AttivazionePolicy.model().GROUP_BY.PROTOCOLLO, groupBy.isProtocollo());
				
				expression.equals(AttivazionePolicy.model().GROUP_BY.EROGATORE, groupBy.isErogatore());
				
				expression.equals(AttivazionePolicy.model().GROUP_BY.SERVIZIO, groupBy.isServizio());
				
				expression.equals(AttivazionePolicy.model().GROUP_BY.AZIONE, groupBy.isAzione());
				
				expression.equals(AttivazionePolicy.model().GROUP_BY.SERVIZIO_APPLICATIVO_EROGATORE, groupBy.isServizioApplicativoErogatore());
					
				expression.equals(AttivazionePolicy.model().GROUP_BY.FRUITORE, groupBy.isFruitore());
				
				expression.equals(AttivazionePolicy.model().GROUP_BY.SERVIZIO_APPLICATIVO_FRUITORE, groupBy.isServizioApplicativoFruitore());

				expression.equals(AttivazionePolicy.model().GROUP_BY.INFORMAZIONE_APPLICATIVA_ENABLED, groupBy.isInformazioneApplicativaEnabled());
				if(groupBy.isInformazioneApplicativaEnabled() &&
						groupBy.getInformazioneApplicativaTipo()!=null &&
						groupBy.getInformazioneApplicativaNome()!=null){
					expression.equals(AttivazionePolicy.model().GROUP_BY.INFORMAZIONE_APPLICATIVA_TIPO, groupBy.getInformazioneApplicativaTipo());
					expression.like(AttivazionePolicy.model().GROUP_BY.INFORMAZIONE_APPLICATIVA_NOME, groupBy.getInformazioneApplicativaNome(), LikeMode.EXACT); // Colonna CLOB
				}
			}
			
			return serviceManager.getAttivazionePolicyServiceSearch().find(expression);
		}catch (NotFoundException e) {
			throw new DriverControlStationNotFound("[DriverControlStationDB::" + nomeMetodo + "] Attivazione Policy non presente.");	
		} catch (Exception qe) {
			throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo +"] Errore : " + qe.getMessage(),qe);
		} finally {
			try {
				if (this.atomica) {
					this.log.debug("rilascio connessioni al db...");
					con.close();
				}
			} catch (Exception e) {
				// ignore exception
			}
		}
	}
	
	public AttivazionePolicy getGlobalPolicyByAlias(String alias) throws DriverControlStationException,DriverControlStationNotFound{
		String nomeMetodo = "getFreeCounterForGlobalPolicy"; 
		Connection con = null;
		if (this.atomica) {
			try {
				con = this.datasource.getConnection();

			} catch (SQLException e) {
				throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo + "] SQLException accedendo al datasource :" + e.getMessage());

			}

		} else {
			con = this.globalConnection;
		}

		this.log.debug("operazione this.atomica = " + this.atomica);
		try{
			ServiceManagerProperties properties = new ServiceManagerProperties();
			properties.setDatabaseType(this.tipoDB);
			properties.setShowSql(true);
			org.openspcoop2.core.controllo_congestione.dao.jdbc.JDBCServiceManager serviceManager = new org.openspcoop2.core.controllo_congestione.dao.jdbc.JDBCServiceManager(con, properties, this.log);
			IExpression expression = serviceManager.getAttivazionePolicyServiceSearch().newExpression();
			
			expression.and();
			expression.equals(AttivazionePolicy.model().ALIAS, alias);
		
			return serviceManager.getAttivazionePolicyServiceSearch().find(expression);
		}catch (NotFoundException e) {
			throw new DriverControlStationNotFound("[DriverControlStationDB::" + nomeMetodo + "] Attivazione Policy non presente.");
		} catch (Exception qe) {
			throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo +"] Errore : " + qe.getMessage(),qe);
		} finally {
			try {
				if (this.atomica) {
					this.log.debug("rilascio connessioni al db...");
					con.close();
				}
			} catch (Exception e) {
				// ignore exception
			}
		}
	}
	
	public List<String> getSoggettiErogatori(String protocollo) throws DriverControlStationException{
		String nomeMetodo = "getSoggettiErogatori"; 
		Connection con = null;
		if (this.atomica) {
			try {
				con = this.datasource.getConnection();

			} catch (SQLException e) {
				throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo + "] SQLException accedendo al datasource :" + e.getMessage());

			}

		} else {
			con = this.globalConnection;
		}

		this.log.debug("operazione this.atomica = " + this.atomica);
		
		try{
			org.openspcoop2.core.commons.search.dao.jdbc.JDBCServiceManager serviceManager = RegistroCore.getServiceManager(this.log, this.tipoDB, con); 
			
			return RegistroCore.getSoggettiErogatori(serviceManager, protocollo);
		}catch (Exception qe) {
			throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo +"] Errore : " + qe.getMessage(),qe);
		} finally {
			try {
				if (this.atomica) {
					this.log.debug("rilascio connessioni al db...");
					con.close();
				}
			} catch (Exception e) {
				// ignore exception
			}
		}
	}
	public List<String> getServizi(String protocollo, String tipoErogatore, String nomeErogatore) throws DriverControlStationException{
		String nomeMetodo = "getServizi"; 
		Connection con = null;
		if (this.atomica) {
			try {
				con = this.datasource.getConnection();

			} catch (SQLException e) {
				throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo + "] SQLException accedendo al datasource :" + e.getMessage());

			}

		} else {
			con = this.globalConnection;
		}

		this.log.debug("operazione this.atomica = " + this.atomica);
		try{
			org.openspcoop2.core.commons.search.dao.jdbc.JDBCServiceManager serviceManager = RegistroCore.getServiceManager(this.log, this.tipoDB, con);
			return RegistroCore.getServizi(serviceManager, protocollo, tipoErogatore, nomeErogatore);
		}catch (Exception qe) {
			throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo +"] Errore : " + qe.getMessage(),qe);
		} finally {
			try {
				if (this.atomica) {
					this.log.debug("rilascio connessioni al db...");
					con.close();
				}
			} catch (Exception e) {
				// ignore exception
			}
		}
	}
	public List<String> getAzioni(String protocollo, String tipoErogatore, String nomeErogatore, String tipoServizio, String nomeServizio) throws DriverControlStationException{
		String nomeMetodo = "getAzioni"; 
		Connection con = null;
		if (this.atomica) {
			try {
				con = this.datasource.getConnection();

			} catch (SQLException e) {
				throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo + "] SQLException accedendo al datasource :" + e.getMessage());

			}

		} else {
			con = this.globalConnection;
		}

		this.log.debug("operazione this.atomica = " + this.atomica);
		try{
			org.openspcoop2.core.commons.search.dao.jdbc.JDBCServiceManager serviceManager = RegistroCore.getServiceManager(this.log, this.tipoDB, con);
			return RegistroCore.getAzioni(serviceManager, protocollo, tipoErogatore, nomeErogatore, tipoServizio, nomeServizio);
		}catch (Exception qe) {
			throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo +"] Errore : " + qe.getMessage(),qe);
		} finally {
			try {
				if (this.atomica) {
					this.log.debug("rilascio connessioni al db...");
					con.close();
				}
			} catch (Exception e) {
				// ignore exception
			}
		}
	}
	public List<String> getServiziApplicativiErogatori(String protocollo, String tipoErogatore, String nomeErogatore, String tipoServizio, String nomeServizio, String azione) throws DriverControlStationException {
		String nomeMetodo = "getServiziApplicativiErogatori"; 
		Connection con = null;
		if (this.atomica) {
			try {
				con = this.datasource.getConnection();

			} catch (SQLException e) {
				throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo + "] SQLException accedendo al datasource :" + e.getMessage());

			}

		} else {
			con = this.globalConnection;
		}

		this.log.debug("operazione this.atomica = " + this.atomica);
		try{
			org.openspcoop2.core.commons.search.dao.jdbc.JDBCServiceManager serviceManager = RegistroCore.getServiceManager(this.log, this.tipoDB, con);
			return RegistroCore.getServiziApplicativiErogatori(serviceManager, protocollo, tipoErogatore, nomeErogatore, tipoServizio, nomeServizio, azione);
		}catch (Exception qe) {
			throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo +"] Errore : " + qe.getMessage(),qe);
		} finally {
			try {
				if (this.atomica) {
					this.log.debug("rilascio connessioni al db...");
					con.close();
				}
			} catch (Exception e) {
				// ignore exception
			}
		}
	}
	public List<String> getSoggettiFruitori(String protocollo, String tipoErogatore, String nomeErogatore, String tipoServizio, String nomeServizio) throws DriverControlStationException{
		String nomeMetodo = "getSoggettiFruitori"; 
		Connection con = null;
		if (this.atomica) {
			try {
				con = this.datasource.getConnection();

			} catch (SQLException e) {
				throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo + "] SQLException accedendo al datasource :" + e.getMessage());

			}

		} else {
			con = this.globalConnection;
		}

		this.log.debug("operazione this.atomica = " + this.atomica);
		try{
			org.openspcoop2.core.commons.search.dao.jdbc.JDBCServiceManager serviceManager = RegistroCore.getServiceManager(this.log, this.tipoDB, con);
			return RegistroCore.getSoggettiFruitori(serviceManager, protocollo, tipoErogatore, nomeErogatore, tipoServizio, nomeServizio);
		}catch (Exception qe) {
			throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo +"] Errore : " + qe.getMessage(),qe);
		} finally {
			try {
				if (this.atomica) {
					this.log.debug("rilascio connessioni al db...");
					con.close();
				}
			} catch (Exception e) {
				// ignore exception
			}
		}
	}
	public List<String> getServiziApplicativiFruitore(String protocollo, String tipoFruitore, String nomeFruitore,	String tipoErogatore, String nomeErogatore, String tipoServizio, String nomeServizio, String azione) throws DriverControlStationException{
		String nomeMetodo = "getServiziApplicativiFruitore"; 
		Connection con = null;
		if (this.atomica) {
			try {
				con = this.datasource.getConnection();

			} catch (SQLException e) {
				throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo + "] SQLException accedendo al datasource :" + e.getMessage());

			}

		} else {
			con = this.globalConnection;
		}

		this.log.debug("operazione this.atomica = " + this.atomica);
		try{
			org.openspcoop2.core.commons.search.dao.jdbc.JDBCServiceManager serviceManager = RegistroCore.getServiceManager(this.log, this.tipoDB, con);
			return RegistroCore.getServiziApplicativiFruitore(serviceManager, protocollo, tipoFruitore, nomeFruitore, tipoErogatore, nomeErogatore, tipoServizio, nomeServizio, azione);
		}catch (Exception qe) {
			throw new DriverControlStationException("[DriverControlStationDB::" + nomeMetodo +"] Errore : " + qe.getMessage(),qe);
		} finally {
			try {
				if (this.atomica) {
					this.log.debug("rilascio connessioni al db...");
					con.close();
				}
			} catch (Exception e) {
				// ignore exception
			}
		}
	}
}
