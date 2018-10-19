-- **** Configurazione ****

CREATE SEQUENCE seq_registri start 1 increment 1 maxvalue 9223372036854775807 minvalue 1 cache 1 NO CYCLE;

CREATE TABLE registri
(
	nome VARCHAR(255) NOT NULL,
	location VARCHAR(255) NOT NULL,
	tipo VARCHAR(255) NOT NULL,
	utente VARCHAR(255),
	password VARCHAR(255),
	-- fk/pk columns
	id BIGINT DEFAULT nextval('seq_registri') NOT NULL,
	-- unique constraints
	CONSTRAINT unique_registri_1 UNIQUE (nome),
	-- fk/pk keys constraints
	CONSTRAINT pk_registri PRIMARY KEY (id)
);




CREATE SEQUENCE seq_routing start 1 increment 1 maxvalue 9223372036854775807 minvalue 1 cache 1 NO CYCLE;

CREATE TABLE routing
(
	tipo VARCHAR(255),
	nome VARCHAR(255),
	-- registro/gateway
	tiporotta VARCHAR(255) NOT NULL,
	tiposoggrotta VARCHAR(255),
	nomesoggrotta VARCHAR(255),
	-- foreign key per i registri(id)
	registrorotta BIGINT,
	is_default BIGINT NOT NULL,
	-- fk/pk columns
	id BIGINT DEFAULT nextval('seq_routing') NOT NULL,
	-- fk/pk keys constraints
	CONSTRAINT pk_routing PRIMARY KEY (id)
);




CREATE SEQUENCE seq_configurazione start 1 increment 1 maxvalue 9223372036854775807 minvalue 1 cache 1 NO CYCLE;

CREATE TABLE configurazione
(
	-- Cadenza inoltro Riscontri/BusteAsincrone
	cadenza_inoltro VARCHAR(255) NOT NULL,
	-- Validazione Buste
	validazione_stato VARCHAR(255),
	validazione_controllo VARCHAR(255),
	validazione_profilo VARCHAR(255),
	validazione_manifest VARCHAR(255),
	-- Validazione Contenuti Applicativi
	-- abilitato/disabilitato/warningOnly
	validazione_contenuti_stato VARCHAR(255),
	-- wsdl/interface/xsd
	validazione_contenuti_tipo VARCHAR(255),
	-- abilitato/disabilitato
	validazione_contenuti_mtom VARCHAR(255),
	-- Livello Log Messaggi Diagnostici
	msg_diag_severita VARCHAR(255) NOT NULL,
	msg_diag_severita_log4j VARCHAR(255) NOT NULL,
	-- Tracciamento Buste
	tracciamento_buste VARCHAR(255),
	tracciamento_esiti VARCHAR(255),
	-- Transazione
	transazioni_tempi VARCHAR(255),
	transazioni_token VARCHAR(255),
	-- Dump
	dump VARCHAR(255),
	dump_bin_pd VARCHAR(255),
	dump_bin_pa VARCHAR(255),
	-- Autenticazione IntegrationManager
	auth_integration_manager VARCHAR(255),
	-- Cache per l'accesso ai registri
	statocache VARCHAR(255),
	dimensionecache VARCHAR(255),
	algoritmocache VARCHAR(255),
	idlecache VARCHAR(255),
	lifecache VARCHAR(255),
	-- Cache per l'accesso alla configurazione
	config_statocache VARCHAR(255),
	config_dimensionecache VARCHAR(255),
	config_algoritmocache VARCHAR(255),
	config_idlecache VARCHAR(255),
	config_lifecache VARCHAR(255),
	-- Cache per l'accesso ai dati di autorizzazione
	auth_statocache VARCHAR(255),
	auth_dimensionecache VARCHAR(255),
	auth_algoritmocache VARCHAR(255),
	auth_idlecache VARCHAR(255),
	auth_lifecache VARCHAR(255),
	-- Cache per l'accesso ai dati di autenticazione
	authn_statocache VARCHAR(255),
	authn_dimensionecache VARCHAR(255),
	authn_algoritmocache VARCHAR(255),
	authn_idlecache VARCHAR(255),
	authn_lifecache VARCHAR(255),
	-- Cache per la gestione dei token
	token_statocache VARCHAR(255),
	token_dimensionecache VARCHAR(255),
	token_algoritmocache VARCHAR(255),
	token_idlecache VARCHAR(255),
	token_lifecache VARCHAR(255),
	-- connessione su cui vengono inviate le risposte
	-- reply: connessione esistente (es. http reply)
	-- new: nuova connessione
	mod_risposta VARCHAR(255),
	-- Gestione dell'indirizzo
	indirizzo_telematico VARCHAR(255),
	-- Gestione Manifest
	gestione_manifest VARCHAR(255),
	-- Routing Table
	routing_enabled VARCHAR(255) NOT NULL,
	-- Gestione errore di default per la Porta di Dominio
	-- FOREIGN KEY (id_ge_cooperazione) REFERENCES gestione_errore(id) Nota: indica un eventuale tipologia di gestione dell'errore di default durante l'utilizzo di un connettore
	id_ge_cooperazione BIGINT,
	-- FOREIGN KEY (id_ge_integrazione) REFERENCES gestione_errore(id) Nota: indica un eventuale tipologia di gestione dell'errore di default durante l'utilizzo di un connettore
	id_ge_integrazione BIGINT,
	-- Gestione MultiTenant
	multitenant_stato VARCHAR(255),
	multitenant_fruizioni VARCHAR(255),
	multitenant_erogazioni VARCHAR(255),
	-- fk/pk columns
	id BIGINT DEFAULT nextval('seq_configurazione') NOT NULL,
	-- fk/pk keys constraints
	CONSTRAINT pk_configurazione PRIMARY KEY (id)
);




-- **** Protocolli ****

CREATE SEQUENCE seq_config_protocolli start 1 increment 1 maxvalue 9223372036854775807 minvalue 1 cache 1 NO CYCLE;

CREATE TABLE config_protocolli
(
	nome VARCHAR(255) NOT NULL,
	url_pd VARCHAR(255),
	url_pa VARCHAR(255),
	-- fk/pk columns
	id BIGINT DEFAULT nextval('seq_config_protocolli') NOT NULL,
	-- unique constraints
	CONSTRAINT unique_config_protocolli_1 UNIQUE (nome),
	-- fk/pk keys constraints
	CONSTRAINT pk_config_protocolli PRIMARY KEY (id)
);




-- **** Messaggi diagnostici Appender ****

CREATE SEQUENCE seq_msgdiag_appender start 1 increment 1 maxvalue 9223372036854775807 minvalue 1 cache 1 NO CYCLE;

CREATE TABLE msgdiag_appender
(
	tipo VARCHAR(255) NOT NULL,
	-- fk/pk columns
	id BIGINT DEFAULT nextval('seq_msgdiag_appender') NOT NULL,
	-- fk/pk keys constraints
	CONSTRAINT pk_msgdiag_appender PRIMARY KEY (id)
);




CREATE SEQUENCE seq_msgdiag_appender_prop start 1 increment 1 maxvalue 9223372036854775807 minvalue 1 cache 1 NO CYCLE;

CREATE TABLE msgdiag_appender_prop
(
	id_appender BIGINT NOT NULL,
	nome VARCHAR(255) NOT NULL,
	valore VARCHAR(255) NOT NULL,
	-- fk/pk columns
	id BIGINT DEFAULT nextval('seq_msgdiag_appender_prop') NOT NULL,
	-- unique constraints
	CONSTRAINT uniq_msgdiag_app_prop_1 UNIQUE (id_appender,nome,valore),
	-- fk/pk keys constraints
	CONSTRAINT fk_msgdiag_appender_prop_1 FOREIGN KEY (id_appender) REFERENCES msgdiag_appender(id),
	CONSTRAINT pk_msgdiag_appender_prop PRIMARY KEY (id)
);




-- **** Tracciamento Appender ****

CREATE SEQUENCE seq_tracce_appender start 1 increment 1 maxvalue 9223372036854775807 minvalue 1 cache 1 NO CYCLE;

CREATE TABLE tracce_appender
(
	tipo VARCHAR(255) NOT NULL,
	-- fk/pk columns
	id BIGINT DEFAULT nextval('seq_tracce_appender') NOT NULL,
	-- fk/pk keys constraints
	CONSTRAINT pk_tracce_appender PRIMARY KEY (id)
);




CREATE SEQUENCE seq_tracce_appender_prop start 1 increment 1 maxvalue 9223372036854775807 minvalue 1 cache 1 NO CYCLE;

CREATE TABLE tracce_appender_prop
(
	id_appender BIGINT NOT NULL,
	nome VARCHAR(255) NOT NULL,
	valore VARCHAR(255) NOT NULL,
	-- fk/pk columns
	id BIGINT DEFAULT nextval('seq_tracce_appender_prop') NOT NULL,
	-- unique constraints
	CONSTRAINT uniq_tracce_app_prop_1 UNIQUE (id_appender,nome,valore),
	-- fk/pk keys constraints
	CONSTRAINT fk_tracce_appender_prop_1 FOREIGN KEY (id_appender) REFERENCES tracce_appender(id),
	CONSTRAINT pk_tracce_appender_prop PRIMARY KEY (id)
);




-- **** Dump Appender ****

CREATE SEQUENCE seq_dump_config start 1 increment 1 maxvalue 9223372036854775807 minvalue 1 cache 1 NO CYCLE;

CREATE TABLE dump_config
(
	proprietario VARCHAR(255) NOT NULL,
	id_proprietario BIGINT NOT NULL,
	dump_realtime VARCHAR(255),
	id_richiesta_ingresso BIGINT NOT NULL,
	id_richiesta_uscita BIGINT NOT NULL,
	id_risposta_ingresso BIGINT NOT NULL,
	id_risposta_uscita BIGINT NOT NULL,
	-- fk/pk columns
	id BIGINT DEFAULT nextval('seq_dump_config') NOT NULL,
	-- fk/pk keys constraints
	CONSTRAINT pk_dump_config PRIMARY KEY (id)
);




CREATE SEQUENCE seq_dump_config_regola start 1 increment 1 maxvalue 9223372036854775807 minvalue 1 cache 1 NO CYCLE;

CREATE TABLE dump_config_regola
(
	body VARCHAR(255) NOT NULL,
	attachments VARCHAR(255) NOT NULL,
	headers VARCHAR(255) NOT NULL,
	-- fk/pk columns
	id BIGINT DEFAULT nextval('seq_dump_config_regola') NOT NULL,
	-- fk/pk keys constraints
	CONSTRAINT pk_dump_config_regola PRIMARY KEY (id)
);




CREATE SEQUENCE seq_dump_appender start 1 increment 1 maxvalue 9223372036854775807 minvalue 1 cache 1 NO CYCLE;

CREATE TABLE dump_appender
(
	tipo VARCHAR(255) NOT NULL,
	-- fk/pk columns
	id BIGINT DEFAULT nextval('seq_dump_appender') NOT NULL,
	-- fk/pk keys constraints
	CONSTRAINT pk_dump_appender PRIMARY KEY (id)
);




CREATE SEQUENCE seq_dump_appender_prop start 1 increment 1 maxvalue 9223372036854775807 minvalue 1 cache 1 NO CYCLE;

CREATE TABLE dump_appender_prop
(
	id_appender BIGINT NOT NULL,
	nome VARCHAR(255) NOT NULL,
	valore VARCHAR(255) NOT NULL,
	-- fk/pk columns
	id BIGINT DEFAULT nextval('seq_dump_appender_prop') NOT NULL,
	-- unique constraints
	CONSTRAINT uniq_dump_app_prop_1 UNIQUE (id_appender,nome,valore),
	-- fk/pk keys constraints
	CONSTRAINT fk_dump_appender_prop_1 FOREIGN KEY (id_appender) REFERENCES dump_appender(id),
	CONSTRAINT pk_dump_appender_prop PRIMARY KEY (id)
);




-- **** Datasources dove reperire i messaggi diagnostici salvati ****

CREATE SEQUENCE seq_msgdiag_ds start 1 increment 1 maxvalue 9223372036854775807 minvalue 1 cache 1 NO CYCLE;

CREATE TABLE msgdiag_ds
(
	nome VARCHAR(255) NOT NULL,
	nome_jndi VARCHAR(255) NOT NULL,
	tipo_database VARCHAR(255) NOT NULL,
	-- fk/pk columns
	id BIGINT DEFAULT nextval('seq_msgdiag_ds') NOT NULL,
	-- unique constraints
	CONSTRAINT unique_msgdiag_ds_1 UNIQUE (nome),
	-- fk/pk keys constraints
	CONSTRAINT pk_msgdiag_ds PRIMARY KEY (id)
);




CREATE SEQUENCE seq_msgdiag_ds_prop start 1 increment 1 maxvalue 9223372036854775807 minvalue 1 cache 1 NO CYCLE;

CREATE TABLE msgdiag_ds_prop
(
	id_prop BIGINT NOT NULL,
	nome VARCHAR(255) NOT NULL,
	valore VARCHAR(255) NOT NULL,
	-- fk/pk columns
	id BIGINT DEFAULT nextval('seq_msgdiag_ds_prop') NOT NULL,
	-- unique constraints
	CONSTRAINT unique_msgdiag_ds_prop_1 UNIQUE (id_prop,nome,valore),
	-- fk/pk keys constraints
	CONSTRAINT fk_msgdiag_ds_prop_1 FOREIGN KEY (id_prop) REFERENCES msgdiag_ds(id),
	CONSTRAINT pk_msgdiag_ds_prop PRIMARY KEY (id)
);




-- **** Datasources dove reperire le tracce salvate ****

CREATE SEQUENCE seq_tracce_ds start 1 increment 1 maxvalue 9223372036854775807 minvalue 1 cache 1 NO CYCLE;

CREATE TABLE tracce_ds
(
	nome VARCHAR(255) NOT NULL,
	nome_jndi VARCHAR(255) NOT NULL,
	tipo_database VARCHAR(255) NOT NULL,
	-- fk/pk columns
	id BIGINT DEFAULT nextval('seq_tracce_ds') NOT NULL,
	-- unique constraints
	CONSTRAINT unique_tracce_ds_1 UNIQUE (nome),
	-- fk/pk keys constraints
	CONSTRAINT pk_tracce_ds PRIMARY KEY (id)
);




CREATE SEQUENCE seq_tracce_ds_prop start 1 increment 1 maxvalue 9223372036854775807 minvalue 1 cache 1 NO CYCLE;

CREATE TABLE tracce_ds_prop
(
	id_prop BIGINT NOT NULL,
	nome VARCHAR(255) NOT NULL,
	valore VARCHAR(255) NOT NULL,
	-- fk/pk columns
	id BIGINT DEFAULT nextval('seq_tracce_ds_prop') NOT NULL,
	-- unique constraints
	CONSTRAINT unique_tracce_ds_prop_1 UNIQUE (id_prop,nome,valore),
	-- fk/pk keys constraints
	CONSTRAINT fk_tracce_ds_prop_1 FOREIGN KEY (id_prop) REFERENCES tracce_ds(id),
	CONSTRAINT pk_tracce_ds_prop PRIMARY KEY (id)
);




-- **** Stato dei servizi attivi sulla Porta di Dominio ****

CREATE SEQUENCE seq_servizi_pdd start 1 increment 1 maxvalue 9223372036854775807 minvalue 1 cache 1 NO CYCLE;

CREATE TABLE servizi_pdd
(
	componente VARCHAR(255) NOT NULL,
	-- Stato dei servizi attivi sulla Porta di Dominio
	stato INT DEFAULT 1,
	-- fk/pk columns
	id BIGINT DEFAULT nextval('seq_servizi_pdd') NOT NULL,
	-- unique constraints
	CONSTRAINT unique_servizi_pdd_1 UNIQUE (componente),
	-- fk/pk keys constraints
	CONSTRAINT pk_servizi_pdd PRIMARY KEY (id)
);




CREATE SEQUENCE seq_servizi_pdd_filtri start 1 increment 1 maxvalue 9223372036854775807 minvalue 1 cache 1 NO CYCLE;

CREATE TABLE servizi_pdd_filtri
(
	id_servizio_pdd BIGINT NOT NULL,
	tipo_filtro VARCHAR(255) NOT NULL,
	tipo_soggetto_fruitore VARCHAR(255),
	soggetto_fruitore VARCHAR(255),
	identificativo_porta_fruitore VARCHAR(255),
	tipo_soggetto_erogatore VARCHAR(255),
	soggetto_erogatore VARCHAR(255),
	identificativo_porta_erogatore VARCHAR(255),
	tipo_servizio VARCHAR(255),
	servizio VARCHAR(255),
	versione_servizio INT,
	azione VARCHAR(255),
	-- fk/pk columns
	id BIGINT DEFAULT nextval('seq_servizi_pdd_filtri') NOT NULL,
	-- check constraints
	CONSTRAINT chk_servizi_pdd_filtri_1 CHECK (tipo_filtro IN ('abilitazione','disabilitazione')),
	-- fk/pk keys constraints
	CONSTRAINT fk_servizi_pdd_filtri_1 FOREIGN KEY (id_servizio_pdd) REFERENCES servizi_pdd(id),
	CONSTRAINT pk_servizi_pdd_filtri PRIMARY KEY (id)
);




-- **** PddSystemProperties ****

CREATE SEQUENCE seq_pdd_sys_props start 1 increment 1 maxvalue 9223372036854775807 minvalue 1 cache 1 NO CYCLE;

CREATE TABLE pdd_sys_props
(
	nome VARCHAR(255) NOT NULL,
	valore VARCHAR(255) NOT NULL,
	-- fk/pk columns
	id BIGINT DEFAULT nextval('seq_pdd_sys_props') NOT NULL,
	-- unique constraints
	CONSTRAINT unique_pdd_sys_props_1 UNIQUE (nome,valore),
	-- fk/pk keys constraints
	CONSTRAINT pk_pdd_sys_props PRIMARY KEY (id)
);




-- **** Proprieta Generiche ****

CREATE SEQUENCE seq_generic_properties start 1 increment 1 maxvalue 9223372036854775807 minvalue 1 cache 1 NO CYCLE;

CREATE TABLE generic_properties
(
	nome VARCHAR(255) NOT NULL,
	descrizione VARCHAR(255),
	tipologia VARCHAR(255) NOT NULL,
	tipo VARCHAR(255) NOT NULL,
	-- fk/pk columns
	id BIGINT DEFAULT nextval('seq_generic_properties') NOT NULL,
	-- unique constraints
	CONSTRAINT unique_generic_properties_1 UNIQUE (tipologia,nome),
	-- fk/pk keys constraints
	CONSTRAINT pk_generic_properties PRIMARY KEY (id)
);




CREATE SEQUENCE seq_generic_property start 1 increment 1 maxvalue 9223372036854775807 minvalue 1 cache 1 NO CYCLE;

CREATE TABLE generic_property
(
	id_props BIGINT NOT NULL,
	nome VARCHAR(255) NOT NULL,
	valore VARCHAR(4000) NOT NULL,
	-- fk/pk columns
	id BIGINT DEFAULT nextval('seq_generic_property') NOT NULL,
	-- unique constraints
	CONSTRAINT unique_generic_property_1 UNIQUE (id_props,nome),
	-- fk/pk keys constraints
	CONSTRAINT fk_generic_property_1 FOREIGN KEY (id_props) REFERENCES generic_properties(id),
	CONSTRAINT pk_generic_property PRIMARY KEY (id)
);



