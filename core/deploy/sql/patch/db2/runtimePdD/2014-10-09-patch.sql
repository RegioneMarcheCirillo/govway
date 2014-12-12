CREATE TABLE ID_MESSAGGIO_RELATIVO
(
        COUNTER BIGINT NOT NULL,
        PROTOCOLLO VARCHAR(255) NOT NULL,
        INFO_ASSOCIATA VARCHAR(255) NOT NULL,
        -- fk/pk columns
        -- fk/pk keys constraints
        CONSTRAINT pk_ID_MESSAGGIO_RELATIVO PRIMARY KEY (COUNTER,PROTOCOLLO,INFO_ASSOCIATA)
);


CREATE TABLE ID_MESSAGGIO_PRG
(
        PROGRESSIVO VARCHAR(255) NOT NULL,
        PROTOCOLLO VARCHAR(255) NOT NULL,
        -- fk/pk columns
        -- fk/pk keys constraints
        CONSTRAINT pk_ID_MESSAGGIO_PRG PRIMARY KEY (PROGRESSIVO,PROTOCOLLO)
);


CREATE TABLE ID_MESSAGGIO_RELATIVO_PRG
(
        PROGRESSIVO VARCHAR(255) NOT NULL,
        PROTOCOLLO VARCHAR(255) NOT NULL,
        INFO_ASSOCIATA VARCHAR(255) NOT NULL,
        -- fk/pk columns
        -- fk/pk keys constraints
        CONSTRAINT pk_ID_MESSAGGIO_RELATIVO_PRG PRIMARY KEY (PROGRESSIVO,PROTOCOLLO,INFO_ASSOCIATA)
);
