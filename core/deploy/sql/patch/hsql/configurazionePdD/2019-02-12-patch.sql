CREATE SEQUENCE seq_pd_auth_properties AS BIGINT START WITH 1 INCREMENT BY 1 ; -- (Scommentare in hsql 2.x) NO CYCLE;

CREATE TABLE pd_auth_properties
(
	id_porta BIGINT NOT NULL,
	nome VARCHAR(255) NOT NULL,
	valore VARCHAR(255) NOT NULL,
	-- fk/pk columns
	id BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 1),
	-- unique constraints
	CONSTRAINT uniq_pd_auth_props_1 UNIQUE (id_porta,nome,valore),
	-- fk/pk keys constraints
	CONSTRAINT fk_pd_auth_properties_1 FOREIGN KEY (id_porta) REFERENCES porte_delegate(id),
	CONSTRAINT pk_pd_auth_properties PRIMARY KEY (id)
);

-- index
CREATE INDEX INDEX_PD_AUTH_PROP ON pd_auth_properties (id_porta);
CREATE TABLE pd_auth_properties_init_seq (id BIGINT);
INSERT INTO pd_auth_properties_init_seq VALUES (NEXT VALUE FOR seq_pd_auth_properties);

CREATE SEQUENCE seq_pa_auth_properties AS BIGINT START WITH 1 INCREMENT BY 1 ; -- (Scommentare in hsql 2.x) NO CYCLE;

CREATE TABLE pa_auth_properties
(
	id_porta BIGINT NOT NULL,
	nome VARCHAR(255) NOT NULL,
	valore VARCHAR(255) NOT NULL,
	-- fk/pk columns
	id BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 1),
	-- unique constraints
	CONSTRAINT uniq_pa_auth_props_1 UNIQUE (id_porta,nome,valore),
	-- fk/pk keys constraints
	CONSTRAINT fk_pa_auth_properties_1 FOREIGN KEY (id_porta) REFERENCES porte_applicative(id),
	CONSTRAINT pk_pa_auth_properties PRIMARY KEY (id)
);

-- index
CREATE INDEX INDEX_PA_AUTH_PROP ON pa_auth_properties (id_porta);
CREATE TABLE pa_auth_properties_init_seq (id BIGINT);
INSERT INTO pa_auth_properties_init_seq VALUES (NEXT VALUE FOR seq_pa_auth_properties);





CREATE SEQUENCE seq_config_cache_regole AS BIGINT START WITH 1 INCREMENT BY 1 ; -- (Scommentare in hsql 2.x) NO CYCLE;

CREATE TABLE config_cache_regole
(
	status_min INT,
	status_max INT,
	fault INT,
	cache_seconds INT,
	-- fk/pk columns
	id BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 1),
	-- fk/pk keys constraints
	CONSTRAINT pk_config_cache_regole PRIMARY KEY (id)
);


ALTER TABLE config_cache_regole ALTER COLUMN fault SET DEFAULT 0;

CREATE TABLE config_cache_regole_init_seq (id BIGINT);
INSERT INTO config_cache_regole_init_seq VALUES (NEXT VALUE FOR seq_config_cache_regole);



CREATE SEQUENCE seq_pd_cache_regole AS BIGINT START WITH 1 INCREMENT BY 1 ; -- (Scommentare in hsql 2.x) NO CYCLE;

CREATE TABLE pd_cache_regole
(
	id_porta BIGINT NOT NULL,
	status_min INT,
	status_max INT,
	fault INT,
	cache_seconds INT,
	-- fk/pk columns
	id BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 1),
	-- fk/pk keys constraints
	CONSTRAINT fk_pd_cache_regole_1 FOREIGN KEY (id_porta) REFERENCES porte_delegate(id),
	CONSTRAINT pk_pd_cache_regole PRIMARY KEY (id)
);


ALTER TABLE pd_cache_regole ALTER COLUMN fault SET DEFAULT 0;

CREATE TABLE pd_cache_regole_init_seq (id BIGINT);
INSERT INTO pd_cache_regole_init_seq VALUES (NEXT VALUE FOR seq_pd_cache_regole);


CREATE SEQUENCE seq_pa_cache_regole AS BIGINT START WITH 1 INCREMENT BY 1 ; -- (Scommentare in hsql 2.x) NO CYCLE;

CREATE TABLE pa_cache_regole
(
	id_porta BIGINT NOT NULL,
	status_min INT,
	status_max INT,
	fault INT,
	cache_seconds INT,
	-- fk/pk columns
	id BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 1),
	-- fk/pk keys constraints
	CONSTRAINT fk_pa_cache_regole_1 FOREIGN KEY (id_porta) REFERENCES porte_applicative(id),
	CONSTRAINT pk_pa_cache_regole PRIMARY KEY (id)
);


ALTER TABLE pa_cache_regole ALTER COLUMN fault SET DEFAULT 0;

CREATE TABLE pa_cache_regole_init_seq (id BIGINT);
INSERT INTO pa_cache_regole_init_seq VALUES (NEXT VALUE FOR seq_pa_cache_regole);


ALTER TABLE configurazione ADD COLUMN response_cache_hash_hdr_list VARCHAR(65535); 
ALTER TABLE configurazione ADD COLUMN response_cache_control_nocache INT;
ALTER TABLE configurazione ADD COLUMN response_cache_control_maxage INT;
ALTER TABLE configurazione ADD COLUMN response_cache_control_nostore INT;

ALTER TABLE porte_applicative ADD COLUMN response_cache_hash_hdr_list VARCHAR(65535); 
ALTER TABLE porte_applicative ADD COLUMN response_cache_control_nocache INT;
ALTER TABLE porte_applicative ADD COLUMN response_cache_control_maxage INT;
ALTER TABLE porte_applicative ADD COLUMN response_cache_control_nostore INT;

ALTER TABLE porte_delegate ADD COLUMN response_cache_hash_hdr_list VARCHAR(65535); 
ALTER TABLE porte_delegate ADD COLUMN response_cache_control_nocache INT;
ALTER TABLE porte_delegate ADD COLUMN response_cache_control_maxage INT;
ALTER TABLE porte_delegate ADD COLUMN response_cache_control_nostore INT;



CREATE SEQUENCE seq_pd_transform AS BIGINT START WITH 1 INCREMENT BY 1 ; -- (Scommentare in hsql 2.x) NO CYCLE;

CREATE TABLE pd_transform
(
	id_porta BIGINT NOT NULL,
	nome VARCHAR(255) NOT NULL,
	posizione INT NOT NULL,
	applicabilita_azioni VARCHAR(65535),
	applicabilita_ct VARCHAR(65535),
	applicabilita_pattern VARCHAR(65535),
	req_conversione_enabled INT NOT NULL,
	req_conversione_tipo VARCHAR(255),
	-- In hsql 2.x usare il tipo BLOB al posto di VARBINARY
	req_conversione_template VARBINARY(1073741823),
	req_content_type VARCHAR(255),
	rest_transformation INT NOT NULL,
	rest_method VARCHAR(255),
	rest_path VARCHAR(255),
	soap_transformation INT NOT NULL,
	soap_version VARCHAR(255),
	soap_action VARCHAR(255),
	soap_envelope INT NOT NULL,
	soap_envelope_as_attach INT NOT NULL,
	soap_envelope_tipo VARCHAR(255),
	-- In hsql 2.x usare il tipo BLOB al posto di VARBINARY
	soap_envelope_template VARBINARY(1073741823),
	-- fk/pk columns
	id BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 1),
	-- unique constraints
	CONSTRAINT unique_pd_transform_1 UNIQUE (id_porta,nome),
	CONSTRAINT unique_pd_transform_2 UNIQUE (id_porta,posizione),
	-- fk/pk keys constraints
	CONSTRAINT fk_pd_transform_1 FOREIGN KEY (id_porta) REFERENCES porte_delegate(id),
	CONSTRAINT pk_pd_transform PRIMARY KEY (id)
);

-- index
CREATE UNIQUE INDEX index_pd_transform_1 ON pd_transform (id_porta,nome);
CREATE UNIQUE INDEX index_pd_transform_2 ON pd_transform (id_porta,posizione);

ALTER TABLE pd_transform ALTER COLUMN req_conversione_enabled SET DEFAULT 0;
ALTER TABLE pd_transform ALTER COLUMN rest_transformation SET DEFAULT 0;
ALTER TABLE pd_transform ALTER COLUMN soap_transformation SET DEFAULT 0;
ALTER TABLE pd_transform ALTER COLUMN soap_envelope SET DEFAULT 0;
ALTER TABLE pd_transform ALTER COLUMN soap_envelope_as_attach SET DEFAULT 0;

CREATE TABLE pd_transform_init_seq (id BIGINT);
INSERT INTO pd_transform_init_seq VALUES (NEXT VALUE FOR seq_pd_transform);



CREATE SEQUENCE seq_pd_transform_sa AS BIGINT START WITH 1 INCREMENT BY 1 ; -- (Scommentare in hsql 2.x) NO CYCLE;

CREATE TABLE pd_transform_sa
(
	id_trasformazione BIGINT NOT NULL,
	id_servizio_applicativo BIGINT NOT NULL,
	-- fk/pk columns
	id BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 1),
	-- unique constraints
	CONSTRAINT unique_pd_transform_sa_1 UNIQUE (id_trasformazione,id_servizio_applicativo),
	-- fk/pk keys constraints
	CONSTRAINT fk_pd_transform_sa_1 FOREIGN KEY (id_servizio_applicativo) REFERENCES servizi_applicativi(id),
	CONSTRAINT fk_pd_transform_sa_2 FOREIGN KEY (id_trasformazione) REFERENCES pd_transform(id),
	CONSTRAINT pk_pd_transform_sa PRIMARY KEY (id)
);

-- index
CREATE UNIQUE INDEX index_pd_transform_sa_1 ON pd_transform_sa (id_trasformazione,id_servizio_applicativo);
CREATE TABLE pd_transform_sa_init_seq (id BIGINT);
INSERT INTO pd_transform_sa_init_seq VALUES (NEXT VALUE FOR seq_pd_transform_sa);



CREATE SEQUENCE seq_pd_transform_hdr AS BIGINT START WITH 1 INCREMENT BY 1 ; -- (Scommentare in hsql 2.x) NO CYCLE;

CREATE TABLE pd_transform_hdr
(
	id_trasformazione BIGINT NOT NULL,
	tipo VARCHAR(255) NOT NULL,
	nome VARCHAR(255) NOT NULL,
	valore VARCHAR(65535),
	-- fk/pk columns
	id BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 1),
	-- fk/pk keys constraints
	CONSTRAINT fk_pd_transform_hdr_1 FOREIGN KEY (id_trasformazione) REFERENCES pd_transform(id),
	CONSTRAINT pk_pd_transform_hdr PRIMARY KEY (id)
);

-- index
CREATE INDEX idx_pd_trasf_hdr_1 ON pd_transform_hdr (id_trasformazione);
CREATE TABLE pd_transform_hdr_init_seq (id BIGINT);
INSERT INTO pd_transform_hdr_init_seq VALUES (NEXT VALUE FOR seq_pd_transform_hdr);



CREATE SEQUENCE seq_pd_transform_url AS BIGINT START WITH 1 INCREMENT BY 1 ; -- (Scommentare in hsql 2.x) NO CYCLE;

CREATE TABLE pd_transform_url
(
	id_trasformazione BIGINT NOT NULL,
	tipo VARCHAR(255) NOT NULL,
	nome VARCHAR(255) NOT NULL,
	valore VARCHAR(65535),
	-- fk/pk columns
	id BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 1),
	-- fk/pk keys constraints
	CONSTRAINT fk_pd_transform_url_1 FOREIGN KEY (id_trasformazione) REFERENCES pd_transform(id),
	CONSTRAINT pk_pd_transform_url PRIMARY KEY (id)
);

-- index
CREATE INDEX idx_pd_trasf_url_1 ON pd_transform_url (id_trasformazione);
CREATE TABLE pd_transform_url_init_seq (id BIGINT);
INSERT INTO pd_transform_url_init_seq VALUES (NEXT VALUE FOR seq_pd_transform_url);



CREATE SEQUENCE seq_pd_transform_risp AS BIGINT START WITH 1 INCREMENT BY 1 ; -- (Scommentare in hsql 2.x) NO CYCLE;

CREATE TABLE pd_transform_risp
(
	id_trasformazione BIGINT NOT NULL,
	nome VARCHAR(255) NOT NULL,
	posizione INT NOT NULL,
	applicabilita_status_min INT,
	applicabilita_status_max INT,
	applicabilita_ct VARCHAR(65535),
	applicabilita_pattern VARCHAR(65535),
	conversione_enabled INT NOT NULL,
	conversione_tipo VARCHAR(255),
	-- In hsql 2.x usare il tipo BLOB al posto di VARBINARY
	conversione_template VARBINARY(1073741823),
	content_type VARCHAR(255),
	return_code INT,
	soap_envelope INT NOT NULL,
	soap_envelope_as_attach INT NOT NULL,
	soap_envelope_tipo VARCHAR(255),
	-- In hsql 2.x usare il tipo BLOB al posto di VARBINARY
	soap_envelope_template VARBINARY(1073741823),
	-- fk/pk columns
	id BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 1),
	-- unique constraints
	CONSTRAINT uniq_pd_trasf_resp_1 UNIQUE (id_trasformazione,nome),
	CONSTRAINT uniq_pd_trasf_resp_2 UNIQUE (id_trasformazione,posizione),
	-- fk/pk keys constraints
	CONSTRAINT fk_pd_transform_risp_1 FOREIGN KEY (id_trasformazione) REFERENCES pd_transform(id),
	CONSTRAINT pk_pd_transform_risp PRIMARY KEY (id)
);

-- index
CREATE UNIQUE INDEX idx_pd_trasf_resp_1 ON pd_transform_risp (id_trasformazione,nome);
CREATE UNIQUE INDEX idx_pd_trasf_resp_2 ON pd_transform_risp (id_trasformazione,posizione);

ALTER TABLE pd_transform_risp ALTER COLUMN conversione_enabled SET DEFAULT 0;
ALTER TABLE pd_transform_risp ALTER COLUMN soap_envelope SET DEFAULT 0;
ALTER TABLE pd_transform_risp ALTER COLUMN soap_envelope_as_attach SET DEFAULT 0;

CREATE TABLE pd_transform_risp_init_seq (id BIGINT);
INSERT INTO pd_transform_risp_init_seq VALUES (NEXT VALUE FOR seq_pd_transform_risp);



CREATE SEQUENCE seq_pd_transform_risp_hdr AS BIGINT START WITH 1 INCREMENT BY 1 ; -- (Scommentare in hsql 2.x) NO CYCLE;

CREATE TABLE pd_transform_risp_hdr
(
	id_transform_risp BIGINT NOT NULL,
	tipo VARCHAR(255) NOT NULL,
	nome VARCHAR(255) NOT NULL,
	valore VARCHAR(65535),
	-- fk/pk columns
	id BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 1),
	-- fk/pk keys constraints
	CONSTRAINT fk_pd_transform_risp_hdr_1 FOREIGN KEY (id_transform_risp) REFERENCES pd_transform_risp(id),
	CONSTRAINT pk_pd_transform_risp_hdr PRIMARY KEY (id)
);

-- index
CREATE INDEX idx_pd_trasf_hdr_resp_1 ON pd_transform_risp_hdr (id_transform_risp);
CREATE TABLE pd_transform_risp_hdr_init_seq (id BIGINT);
INSERT INTO pd_transform_risp_hdr_init_seq VALUES (NEXT VALUE FOR seq_pd_transform_risp_hdr);



CREATE SEQUENCE seq_pa_transform AS BIGINT START WITH 1 INCREMENT BY 1 ; -- (Scommentare in hsql 2.x) NO CYCLE;

CREATE TABLE pa_transform
(
	id_porta BIGINT NOT NULL,
	nome VARCHAR(255) NOT NULL,
	posizione INT NOT NULL,
	applicabilita_azioni VARCHAR(65535),
	applicabilita_ct VARCHAR(65535),
	applicabilita_pattern VARCHAR(65535),
	req_conversione_enabled INT NOT NULL,
	req_conversione_tipo VARCHAR(255),
	-- In hsql 2.x usare il tipo BLOB al posto di VARBINARY
	req_conversione_template VARBINARY(1073741823),
	req_content_type VARCHAR(255),
	rest_transformation INT NOT NULL,
	rest_method VARCHAR(255),
	rest_path VARCHAR(255),
	soap_transformation INT NOT NULL,
	soap_version VARCHAR(255),
	soap_action VARCHAR(255),
	soap_envelope INT NOT NULL,
	soap_envelope_as_attach INT NOT NULL,
	soap_envelope_tipo VARCHAR(255),
	-- In hsql 2.x usare il tipo BLOB al posto di VARBINARY
	soap_envelope_template VARBINARY(1073741823),
	-- fk/pk columns
	id BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 1),
	-- unique constraints
	CONSTRAINT unique_pa_transform_1 UNIQUE (id_porta,nome),
	CONSTRAINT unique_pa_transform_2 UNIQUE (id_porta,posizione),
	-- fk/pk keys constraints
	CONSTRAINT fk_pa_transform_1 FOREIGN KEY (id_porta) REFERENCES porte_applicative(id),
	CONSTRAINT pk_pa_transform PRIMARY KEY (id)
);

-- index
CREATE UNIQUE INDEX index_pa_transform_1 ON pa_transform (id_porta,nome);
CREATE UNIQUE INDEX index_pa_transform_2 ON pa_transform (id_porta,posizione);

ALTER TABLE pa_transform ALTER COLUMN req_conversione_enabled SET DEFAULT 0;
ALTER TABLE pa_transform ALTER COLUMN rest_transformation SET DEFAULT 0;
ALTER TABLE pa_transform ALTER COLUMN soap_transformation SET DEFAULT 0;
ALTER TABLE pa_transform ALTER COLUMN soap_envelope SET DEFAULT 0;
ALTER TABLE pa_transform ALTER COLUMN soap_envelope_as_attach SET DEFAULT 0;

CREATE TABLE pa_transform_init_seq (id BIGINT);
INSERT INTO pa_transform_init_seq VALUES (NEXT VALUE FOR seq_pa_transform);



CREATE SEQUENCE seq_pa_transform_soggetti AS BIGINT START WITH 1 INCREMENT BY 1 ; -- (Scommentare in hsql 2.x) NO CYCLE;

CREATE TABLE pa_transform_soggetti
(
	id_trasformazione BIGINT NOT NULL,
	tipo_soggetto VARCHAR(255) NOT NULL,
	nome_soggetto VARCHAR(255) NOT NULL,
	-- fk/pk columns
	id BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 1),
	-- unique constraints
	CONSTRAINT unique_pa_transform_soggetti_1 UNIQUE (id_trasformazione,tipo_soggetto,nome_soggetto),
	-- fk/pk keys constraints
	CONSTRAINT fk_pa_transform_soggetti_1 FOREIGN KEY (id_trasformazione) REFERENCES pa_transform(id),
	CONSTRAINT pk_pa_transform_soggetti PRIMARY KEY (id)
);

-- index
CREATE UNIQUE INDEX index_pa_transform_soggetti_1 ON pa_transform_soggetti (id_trasformazione,tipo_soggetto,nome_soggetto);
CREATE TABLE pa_transform_soggetti_init_seq (id BIGINT);
INSERT INTO pa_transform_soggetti_init_seq VALUES (NEXT VALUE FOR seq_pa_transform_soggetti);



CREATE SEQUENCE seq_pa_transform_sa AS BIGINT START WITH 1 INCREMENT BY 1 ; -- (Scommentare in hsql 2.x) NO CYCLE;

CREATE TABLE pa_transform_sa
(
	id_trasformazione BIGINT NOT NULL,
	id_servizio_applicativo BIGINT NOT NULL,
	-- fk/pk columns
	id BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 1),
	-- unique constraints
	CONSTRAINT unique_pa_transform_sa_1 UNIQUE (id_trasformazione,id_servizio_applicativo),
	-- fk/pk keys constraints
	CONSTRAINT fk_pa_transform_sa_1 FOREIGN KEY (id_servizio_applicativo) REFERENCES servizi_applicativi(id),
	CONSTRAINT fk_pa_transform_sa_2 FOREIGN KEY (id_trasformazione) REFERENCES pa_transform(id),
	CONSTRAINT pk_pa_transform_sa PRIMARY KEY (id)
);

-- index
CREATE UNIQUE INDEX index_pa_transform_sa_1 ON pa_transform_sa (id_trasformazione,id_servizio_applicativo);
CREATE TABLE pa_transform_sa_init_seq (id BIGINT);
INSERT INTO pa_transform_sa_init_seq VALUES (NEXT VALUE FOR seq_pa_transform_sa);



CREATE SEQUENCE seq_pa_transform_hdr AS BIGINT START WITH 1 INCREMENT BY 1 ; -- (Scommentare in hsql 2.x) NO CYCLE;

CREATE TABLE pa_transform_hdr
(
	id_trasformazione BIGINT NOT NULL,
	tipo VARCHAR(255) NOT NULL,
	nome VARCHAR(255) NOT NULL,
	valore VARCHAR(65535),
	-- fk/pk columns
	id BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 1),
	-- fk/pk keys constraints
	CONSTRAINT fk_pa_transform_hdr_1 FOREIGN KEY (id_trasformazione) REFERENCES pa_transform(id),
	CONSTRAINT pk_pa_transform_hdr PRIMARY KEY (id)
);

-- index
CREATE INDEX idx_pa_trasf_hdr_1 ON pa_transform_hdr (id_trasformazione);
CREATE TABLE pa_transform_hdr_init_seq (id BIGINT);
INSERT INTO pa_transform_hdr_init_seq VALUES (NEXT VALUE FOR seq_pa_transform_hdr);



CREATE SEQUENCE seq_pa_transform_url AS BIGINT START WITH 1 INCREMENT BY 1 ; -- (Scommentare in hsql 2.x) NO CYCLE;

CREATE TABLE pa_transform_url
(
	id_trasformazione BIGINT NOT NULL,
	tipo VARCHAR(255) NOT NULL,
	nome VARCHAR(255) NOT NULL,
	valore VARCHAR(65535),
	-- fk/pk columns
	id BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 1),
	-- fk/pk keys constraints
	CONSTRAINT fk_pa_transform_url_1 FOREIGN KEY (id_trasformazione) REFERENCES pa_transform(id),
	CONSTRAINT pk_pa_transform_url PRIMARY KEY (id)
);

-- index
CREATE INDEX idx_pa_trasf_url_1 ON pa_transform_url (id_trasformazione);
CREATE TABLE pa_transform_url_init_seq (id BIGINT);
INSERT INTO pa_transform_url_init_seq VALUES (NEXT VALUE FOR seq_pa_transform_url);



CREATE SEQUENCE seq_pa_transform_risp AS BIGINT START WITH 1 INCREMENT BY 1 ; -- (Scommentare in hsql 2.x) NO CYCLE;

CREATE TABLE pa_transform_risp
(
	id_trasformazione BIGINT NOT NULL,
	nome VARCHAR(255) NOT NULL,
	posizione INT NOT NULL,
	applicabilita_status_min INT,
	applicabilita_status_max INT,
	applicabilita_ct VARCHAR(65535),
	applicabilita_pattern VARCHAR(65535),
	conversione_enabled INT NOT NULL,
	conversione_tipo VARCHAR(255),
	-- In hsql 2.x usare il tipo BLOB al posto di VARBINARY
	conversione_template VARBINARY(1073741823),
	content_type VARCHAR(255),
	return_code INT,
	soap_envelope INT NOT NULL,
	soap_envelope_as_attach INT NOT NULL,
	soap_envelope_tipo VARCHAR(255),
	-- In hsql 2.x usare il tipo BLOB al posto di VARBINARY
	soap_envelope_template VARBINARY(1073741823),
	-- fk/pk columns
	id BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 1),
	-- unique constraints
	CONSTRAINT uniq_pa_trasf_resp_1 UNIQUE (id_trasformazione,nome),
	CONSTRAINT uniq_pa_trasf_resp_2 UNIQUE (id_trasformazione,posizione),
	-- fk/pk keys constraints
	CONSTRAINT fk_pa_transform_risp_1 FOREIGN KEY (id_trasformazione) REFERENCES pa_transform(id),
	CONSTRAINT pk_pa_transform_risp PRIMARY KEY (id)
);

-- index
CREATE UNIQUE INDEX idx_pa_trasf_resp_1 ON pa_transform_risp (id_trasformazione,nome);
CREATE UNIQUE INDEX idx_pa_trasf_resp_2 ON pa_transform_risp (id_trasformazione,posizione);

ALTER TABLE pa_transform_risp ALTER COLUMN conversione_enabled SET DEFAULT 0;
ALTER TABLE pa_transform_risp ALTER COLUMN soap_envelope SET DEFAULT 0;
ALTER TABLE pa_transform_risp ALTER COLUMN soap_envelope_as_attach SET DEFAULT 0;

CREATE TABLE pa_transform_risp_init_seq (id BIGINT);
INSERT INTO pa_transform_risp_init_seq VALUES (NEXT VALUE FOR seq_pa_transform_risp);



CREATE SEQUENCE seq_pa_transform_risp_hdr AS BIGINT START WITH 1 INCREMENT BY 1 ; -- (Scommentare in hsql 2.x) NO CYCLE;

CREATE TABLE pa_transform_risp_hdr
(
	id_transform_risp BIGINT NOT NULL,
	tipo VARCHAR(255) NOT NULL,
	nome VARCHAR(255) NOT NULL,
	valore VARCHAR(65535),
	-- fk/pk columns
	id BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 1),
	-- fk/pk keys constraints
	CONSTRAINT fk_pa_transform_risp_hdr_1 FOREIGN KEY (id_transform_risp) REFERENCES pa_transform_risp(id),
	CONSTRAINT pk_pa_transform_risp_hdr PRIMARY KEY (id)
);

-- index
CREATE INDEX idx_pa_trasf_hdr_resp_1 ON pa_transform_risp_hdr (id_transform_risp);
CREATE TABLE pa_transform_risp_hdr_init_seq (id BIGINT);
INSERT INTO pa_transform_risp_hdr_init_seq VALUES (NEXT VALUE FOR seq_pa_transform_risp_hdr);




-- Inizializzo per avere nuove visualizzazioni
delete from users_stati ;
