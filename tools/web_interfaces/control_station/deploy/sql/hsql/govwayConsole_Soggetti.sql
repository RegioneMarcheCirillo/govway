-- **** Soggetti ****

CREATE SEQUENCE seq_soggetti AS BIGINT START WITH 1 INCREMENT BY 1 ; -- (Scommentare in hsql 2.x) NO CYCLE;

CREATE TABLE soggetti
(
	nome_soggetto VARCHAR(255) NOT NULL,
	tipo_soggetto VARCHAR(255) NOT NULL,
	descrizione VARCHAR(255),
	identificativo_porta VARCHAR(255),
	-- 1/0 (true/false) Indicazione se il soggetto svolge è quello di default per il protocollo
	is_default INT,
	-- 1/0 (true/false) svolge attivita di router
	is_router INT,
	id_connettore BIGINT NOT NULL,
	superuser VARCHAR(255),
	server VARCHAR(255),
	-- 1/0 (true/false) indica se il soggetto e' privato/pubblico
	privato INT,
	ora_registrazione TIMESTAMP,
	profilo VARCHAR(255),
	codice_ipa VARCHAR(255) NOT NULL,
	tipoauth VARCHAR(255),
	utente VARCHAR(255),
	password VARCHAR(255),
	subject VARCHAR(255),
	pd_url_prefix_rewriter VARCHAR(255),
	pa_url_prefix_rewriter VARCHAR(255),
	-- fk/pk columns
	id BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 1),
	-- unique constraints
	CONSTRAINT unique_soggetti_1 UNIQUE (nome_soggetto,tipo_soggetto),
	CONSTRAINT unique_soggetti_2 UNIQUE (codice_ipa),
	-- fk/pk keys constraints
	CONSTRAINT fk_soggetti_1 FOREIGN KEY (id_connettore) REFERENCES connettori(id),
	CONSTRAINT pk_soggetti PRIMARY KEY (id)
);

-- index
CREATE UNIQUE INDEX index_soggetti_1 ON soggetti (nome_soggetto,tipo_soggetto);
CREATE UNIQUE INDEX index_soggetti_2 ON soggetti (codice_ipa);

ALTER TABLE soggetti ALTER COLUMN is_default SET DEFAULT 0;
ALTER TABLE soggetti ALTER COLUMN is_router SET DEFAULT 0;
ALTER TABLE soggetti ALTER COLUMN privato SET DEFAULT 0;
ALTER TABLE soggetti ALTER COLUMN ora_registrazione SET DEFAULT CURRENT_TIMESTAMP;

CREATE TABLE soggetti_init_seq (id BIGINT);
INSERT INTO soggetti_init_seq VALUES (NEXT VALUE FOR seq_soggetti);



CREATE SEQUENCE seq_soggetti_ruoli AS BIGINT START WITH 1 INCREMENT BY 1 ; -- (Scommentare in hsql 2.x) NO CYCLE;

CREATE TABLE soggetti_ruoli
(
	id_soggetto BIGINT NOT NULL,
	id_ruolo BIGINT NOT NULL,
	-- fk/pk columns
	id BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 1),
	-- unique constraints
	CONSTRAINT unique_soggetti_ruoli_1 UNIQUE (id_soggetto,id_ruolo),
	-- fk/pk keys constraints
	CONSTRAINT fk_soggetti_ruoli_1 FOREIGN KEY (id_soggetto) REFERENCES soggetti(id),
	CONSTRAINT fk_soggetti_ruoli_2 FOREIGN KEY (id_ruolo) REFERENCES ruoli(id),
	CONSTRAINT pk_soggetti_ruoli PRIMARY KEY (id)
);

-- index
CREATE UNIQUE INDEX index_soggetti_ruoli_1 ON soggetti_ruoli (id_soggetto,id_ruolo);
CREATE TABLE soggetti_ruoli_init_seq (id BIGINT);
INSERT INTO soggetti_ruoli_init_seq VALUES (NEXT VALUE FOR seq_soggetti_ruoli);


