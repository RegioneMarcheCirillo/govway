ALTER TABLE MSG_SERVIZI_APPLICATIVI ADD LOCK_CONSEGNA DATETIME2;
ALTER TABLE MSG_SERVIZI_APPLICATIVI ADD CLUSTER_ID VARCHAR(255);
ALTER TABLE MSG_SERVIZI_APPLICATIVI ADD ATTESA_ESITO INT;
ALTER TABLE MSG_SERVIZI_APPLICATIVI ADD ERRORE_PROCESSAMENTO_COMPACT VARCHAR(255);

CREATE INDEX MSG_SERV_APPL_ACQUIRE_SEND ON MSG_SERVIZI_APPLICATIVI (ID_MESSAGGIO,TIPO_CONSEGNA,ERRORE_PROCESSAMENTO_COMPACT,RISPEDIZIONE,LOCK_CONSEGNA,ATTESA_ESITO);
CREATE INDEX MSG_SERV_APPL_RELEASE_SEND ON MSG_SERVIZI_APPLICATIVI (CLUSTER_ID,LOCK_CONSEGNA);
