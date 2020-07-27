-- **** Repository Buste ****

CREATE TABLE REPOSITORY_BUSTE
(
	ID_MESSAGGIO VARCHAR(255) NOT NULL,
	TIPO VARCHAR(255) NOT NULL,
	MITTENTE VARCHAR(255),
	IDPORTA_MITTENTE VARCHAR(255),
	TIPO_MITTENTE VARCHAR(255),
	IND_TELEMATICO_MITT VARCHAR(255),
	DESTINATARIO VARCHAR(255),
	IDPORTA_DESTINATARIO VARCHAR(255),
	TIPO_DESTINATARIO VARCHAR(255),
	IND_TELEMATICO_DEST VARCHAR(255),
	VERSIONE_SERVIZIO VARCHAR(255),
	SERVIZIO VARCHAR(255),
	TIPO_SERVIZIO VARCHAR(255),
	AZIONE VARCHAR(255),
	PROFILO_DI_COLLABORAZIONE VARCHAR(255),
	SERVIZIO_CORRELATO VARCHAR(255),
	TIPO_SERVIZIO_CORRELATO VARCHAR(255),
	COLLABORAZIONE VARCHAR(255),
	SEQUENZA INT,
	INOLTRO_SENZA_DUPLICATI INT NOT NULL,
	CONFERMA_RICEZIONE INT NOT NULL,
	ORA_REGISTRAZIONE TIMESTAMP,
	TIPO_ORA_REGISTRAZIONE VARCHAR(255),
	RIFERIMENTO_MESSAGGIO VARCHAR(255),
	SCADENZA_BUSTA TIMESTAMP NOT NULL,
	DUPLICATI INT NOT NULL,
	-- Dati di integrazione
	LOCATION_PD VARCHAR(255),
	SERVIZIO_APPLICATIVO VARCHAR(255),
	MODULO_IN_ATTESA VARCHAR(255),
	SCENARIO VARCHAR(255),
	PROTOCOLLO VARCHAR(255) NOT NULL,
	-- Booleani che indicano l'attuali modalita' di utilizzo del repository:
	--  HISTORY: Busta usata per funzionalita di confermaRicezione(OUTBOX)/FiltroDuplicati(INBOX)
	--  PROFILI: Busta usata per funzionalita di profili di collaborazione
	--  PDD:     Busta usata eventualmente da un PdD
	--  
	--  DEFAULT CONTROLLER: 3 interi con semantica booleana (1->true, 0->false)
	HISTORY INT NOT NULL DEFAULT 0,
	PROFILO INT NOT NULL DEFAULT 0,
	PDD INT NOT NULL DEFAULT 0,
	REPOSITORY_ACCESS INT NOT NULL DEFAULT 0,
	DATA_REGISTRAZIONE TIMESTAMP NOT NULL,
	-- fk/pk columns
	-- check constraints
	CONSTRAINT chk_REPOSITORY_BUSTE_1 CHECK (TIPO IN ('INBOX','OUTBOX')),
	-- fk/pk keys constraints
	CONSTRAINT pk_REPOSITORY_BUSTE PRIMARY KEY (ID_MESSAGGIO,TIPO)
);

-- index
CREATE INDEX REP_BUSTE_SEARCH ON REPOSITORY_BUSTE (SCADENZA_BUSTA,TIPO,HISTORY,PROFILO,PDD);
CREATE INDEX REP_BUSTE_SEARCH_RA ON REPOSITORY_BUSTE (SCADENZA_BUSTA,TIPO,REPOSITORY_ACCESS);
CREATE INDEX REP_BUSTE_SEARCH_TIPO ON REPOSITORY_BUSTE (TIPO,HISTORY,PROFILO,PDD);
CREATE INDEX REP_BUSTE_SEARCH_TIPO_RA ON REPOSITORY_BUSTE (TIPO,REPOSITORY_ACCESS);
CREATE INDEX REP_BUSTE_DATAREG ON REPOSITORY_BUSTE (DATA_REGISTRAZIONE,TIPO,HISTORY,PROFILO,PDD);
CREATE INDEX REP_BUSTE_DATAREG_RA ON REPOSITORY_BUSTE (DATA_REGISTRAZIONE,TIPO,REPOSITORY_ACCESS);

CREATE TABLE LISTA_RISCONTRI
(
	ID_MESSAGGIO VARCHAR(255) NOT NULL,
	TIPO VARCHAR(255) NOT NULL,
	ID_RISCONTRO VARCHAR(255),
	RICEVUTA CLOB,
	ORA_REGISTRAZIONE TIMESTAMP,
	TIPO_ORA_REGISTRAZIONE VARCHAR(255),
	DATA_REGISTRAZIONE TIMESTAMP NOT NULL,
	-- fk/pk columns
	id BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1 CYCLE NO CACHE),
	-- check constraints
	CONSTRAINT chk_LISTA_RISCONTRI_1 CHECK (TIPO IN ('INBOX','OUTBOX')),
	-- fk/pk keys constraints
	CONSTRAINT fk_LISTA_RISCONTRI_1 FOREIGN KEY (ID_MESSAGGIO,TIPO) REFERENCES REPOSITORY_BUSTE(ID_MESSAGGIO,TIPO) ON DELETE CASCADE,
	CONSTRAINT pk_LISTA_RISCONTRI PRIMARY KEY (id)
);

-- index
CREATE INDEX LISTA_RISC_ID ON LISTA_RISCONTRI (ID_MESSAGGIO,TIPO);



CREATE TABLE LISTA_TRASMISSIONI
(
	ID_MESSAGGIO VARCHAR(255) NOT NULL,
	TIPO VARCHAR(255) NOT NULL,
	ORIGINE VARCHAR(255),
	TIPO_ORIGINE VARCHAR(255),
	DESTINAZIONE VARCHAR(255),
	TIPO_DESTINAZIONE VARCHAR(255),
	ORA_REGISTRAZIONE TIMESTAMP,
	TIPO_ORA_REGISTRAZIONE VARCHAR(255),
	DATA_REGISTRAZIONE TIMESTAMP NOT NULL,
	-- fk/pk columns
	id BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1 CYCLE NO CACHE),
	-- check constraints
	CONSTRAINT chk_LISTA_TRASMISSIONI_1 CHECK (TIPO IN ('INBOX','OUTBOX')),
	-- fk/pk keys constraints
	CONSTRAINT fk_LISTA_TRASMISSIONI_1 FOREIGN KEY (ID_MESSAGGIO,TIPO) REFERENCES REPOSITORY_BUSTE(ID_MESSAGGIO,TIPO) ON DELETE CASCADE,
	CONSTRAINT pk_LISTA_TRASMISSIONI PRIMARY KEY (id)
);

-- index
CREATE INDEX LISTA_TRASM_ID ON LISTA_TRASMISSIONI (ID_MESSAGGIO,TIPO);



CREATE TABLE LISTA_ECCEZIONI
(
	ID_MESSAGGIO VARCHAR(255) NOT NULL,
	TIPO VARCHAR(255) NOT NULL,
	VALIDAZIONE INT,
	CONTESTO VARCHAR(255),
	CODICE VARCHAR(255),
	RILEVANZA VARCHAR(255),
	POSIZIONE CLOB,
	DATA_REGISTRAZIONE TIMESTAMP NOT NULL,
	-- fk/pk columns
	id BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1 CYCLE NO CACHE),
	-- check constraints
	CONSTRAINT chk_LISTA_ECCEZIONI_1 CHECK (TIPO IN ('INBOX','OUTBOX')),
	-- fk/pk keys constraints
	CONSTRAINT fk_LISTA_ECCEZIONI_1 FOREIGN KEY (ID_MESSAGGIO,TIPO) REFERENCES REPOSITORY_BUSTE(ID_MESSAGGIO,TIPO) ON DELETE CASCADE,
	CONSTRAINT pk_LISTA_ECCEZIONI PRIMARY KEY (id)
);

-- index
CREATE INDEX LISTA_ECC_ID ON LISTA_ECCEZIONI (ID_MESSAGGIO,TIPO);
CREATE INDEX LISTA_ECC_VALIDAZIONE ON LISTA_ECCEZIONI (ID_MESSAGGIO,TIPO,VALIDAZIONE);



CREATE TABLE LISTA_EXT_PROTOCOL_INFO
(
	ID_MESSAGGIO VARCHAR(255) NOT NULL,
	TIPO VARCHAR(255) NOT NULL,
	NOME VARCHAR(255) NOT NULL,
	VALORE CLOB NOT NULL,
	DATA_REGISTRAZIONE TIMESTAMP NOT NULL,
	-- fk/pk columns
	id BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1 CYCLE NO CACHE),
	-- check constraints
	CONSTRAINT chk_LISTA_EXT_PROTOCOL_INFO_1 CHECK (TIPO IN ('INBOX','OUTBOX')),
	-- fk/pk keys constraints
	CONSTRAINT fk_LISTA_EXT_PROTOCOL_INFO_1 FOREIGN KEY (ID_MESSAGGIO,TIPO) REFERENCES REPOSITORY_BUSTE(ID_MESSAGGIO,TIPO) ON DELETE CASCADE,
	CONSTRAINT pk_LISTA_EXT_PROTOCOL_INFO PRIMARY KEY (id)
);

-- index
CREATE INDEX LISTA_EXT_ID ON LISTA_EXT_PROTOCOL_INFO (ID_MESSAGGIO,TIPO);



-- **** Riscontri ****

CREATE TABLE RISCONTRI_DA_RICEVERE
(
	ID_MESSAGGIO VARCHAR(255) NOT NULL,
	DATA_INVIO TIMESTAMP NOT NULL,
	-- fk/pk columns
	-- fk/pk keys constraints
	CONSTRAINT pk_RISCONTRI_DA_RICEVERE PRIMARY KEY (ID_MESSAGGIO)
);

-- index
CREATE INDEX RISCONTRI_NON_RICEVUTI ON RISCONTRI_DA_RICEVERE (DATA_INVIO);

-- **** Sequenze ****

CREATE TABLE SEQUENZA_DA_INVIARE
(
	MITTENTE VARCHAR(255) NOT NULL,
	TIPO_MITTENTE VARCHAR(255) NOT NULL,
	DESTINATARIO VARCHAR(255) NOT NULL,
	TIPO_DESTINATARIO VARCHAR(255) NOT NULL,
	SERVIZIO VARCHAR(255) NOT NULL,
	TIPO_SERVIZIO VARCHAR(255) NOT NULL,
	AZIONE VARCHAR(255) NOT NULL DEFAULT '',
	PROSSIMA_SEQUENZA INT NOT NULL,
	ID_COLLABORAZIONE VARCHAR(255) NOT NULL,
	DATA_REGISTRAZIONE TIMESTAMP NOT NULL,
	-- fk/pk columns
	-- fk/pk keys constraints
	CONSTRAINT pk_SEQUENZA_DA_INVIARE PRIMARY KEY (MITTENTE,TIPO_MITTENTE,DESTINATARIO,TIPO_DESTINATARIO,SERVIZIO,TIPO_SERVIZIO,AZIONE)
);


CREATE TABLE SEQUENZA_DA_RICEVERE
(
	ID_COLLABORAZIONE VARCHAR(255) NOT NULL,
	SEQUENZA_ATTESA INT NOT NULL,
	-- le informazioni su mitt/dest/servizio/azione servono per un controllo di validazione sulla collaborazione
	MITTENTE VARCHAR(255) NOT NULL,
	TIPO_MITTENTE VARCHAR(255) NOT NULL,
	DESTINATARIO VARCHAR(255) NOT NULL,
	TIPO_DESTINATARIO VARCHAR(255) NOT NULL,
	SERVIZIO VARCHAR(255) NOT NULL,
	TIPO_SERVIZIO VARCHAR(255) NOT NULL,
	AZIONE VARCHAR(255),
	DATA_REGISTRAZIONE TIMESTAMP NOT NULL,
	-- fk/pk columns
	-- fk/pk keys constraints
	CONSTRAINT pk_SEQUENZA_DA_RICEVERE PRIMARY KEY (ID_COLLABORAZIONE)
);


-- **** Asincroni ****

CREATE TABLE ASINCRONO
(
	ID_MESSAGGIO VARCHAR(255) NOT NULL,
	TIPO VARCHAR(255) NOT NULL,
	ORA_REGISTRAZIONE TIMESTAMP NOT NULL,
	RICEVUTA_ASINCRONA INT NOT NULL,
	TIPO_SERVIZIO_CORRELATO VARCHAR(255),
	SERVIZIO_CORRELATO VARCHAR(255),
	VERSIONE_SERVIZIO_CORRELATO INT,
	-- per diversificare il flusso di richiesta/ricevuta da risposta(richiestaStato)/ricevuta(Risposta)
	IS_RICHIESTA INT NOT NULL,
	ID_ASINCRONO VARCHAR(255) NOT NULL,
	ID_COLLABORAZIONE VARCHAR(255),
	-- 1 se la ricevuta applicativa e' abilitata, 0 se non lo e'
	RICEVUTA_APPLICATIVA INT NOT NULL,
	-- serve per la re-spedizione di una risposta asincrona
	BACKUP_ID_RICHIESTA VARCHAR(255),
	-- fk/pk columns
	-- check constraints
	CONSTRAINT chk_ASINCRONO_1 CHECK (TIPO IN ('INBOX','OUTBOX')),
	-- fk/pk keys constraints
	CONSTRAINT pk_ASINCRONO PRIMARY KEY (ID_MESSAGGIO,TIPO)
);

-- index
CREATE INDEX ASINCRONO_BACKUP_ID ON ASINCRONO (BACKUP_ID_RICHIESTA);
CREATE INDEX ASINCRONO_IS_RICEVUTA ON ASINCRONO (ID_MESSAGGIO,TIPO,RICEVUTA_ASINCRONA);
CREATE INDEX ASINCRONO_NON_RICEVUTE ON ASINCRONO (ORA_REGISTRAZIONE,TIPO,RICEVUTA_ASINCRONA,RICEVUTA_APPLICATIVA);
