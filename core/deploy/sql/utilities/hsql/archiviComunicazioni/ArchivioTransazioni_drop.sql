-- Gli indici vengono eliminati automaticamente una volta eliminata la tabella
-- DROP INDEX index_credenziale_mittente_1;
-- DROP INDEX index_dump_contenuti_1;
-- DROP INDEX index_dump_header_allegato_1;
-- DROP INDEX index_dump_allegati_1;
-- DROP INDEX index_dump_header_trasporto_1;
-- DROP INDEX index_dump_multipart_header_1;
-- DROP INDEX index_dump_messaggi_3;
-- DROP INDEX index_dump_messaggi_2;
-- DROP INDEX index_dump_messaggi_1;
-- DROP INDEX INDEX_TR_FILTROD_RES_2;
-- DROP INDEX INDEX_TR_FILTROD_REQ_2;
-- DROP INDEX INDEX_TR_FILTROD_RES;
-- DROP INDEX INDEX_TR_FILTROD_REQ;
-- DROP INDEX INDEX_TR_STATS;
-- DROP INDEX INDEX_TR_SEARCH;
-- DROP INDEX INDEX_TR_FULL;
-- DROP INDEX INDEX_TR_MEDIUM;
-- DROP INDEX INDEX_TR_ENTRY;
DROP TABLE dump_contenuti_init_seq;
DROP TABLE dump_contenuti;
DROP TABLE dump_header_allegato_init_seq;
DROP TABLE dump_header_allegato;
DROP TABLE dump_allegati_init_seq;
DROP TABLE dump_allegati;
DROP TABLE dump_header_trasporto_init_seq;
DROP TABLE dump_header_trasporto;
DROP TABLE dump_multipart_header_init_seq;
DROP TABLE dump_multipart_header;
DROP TABLE dump_messaggi_init_seq;
DROP TABLE dump_messaggi;
DROP TABLE transazioni_export_init_seq;
DROP TABLE transazioni_export;
DROP TABLE transazioni_info;
DROP TABLE transazioni;
DROP TABLE credenziale_mittente_init_seq;
DROP TABLE credenziale_mittente;
DROP SEQUENCE seq_dump_contenuti;
DROP SEQUENCE seq_dump_header_allegato;
DROP SEQUENCE seq_dump_allegati;
DROP SEQUENCE seq_dump_header_trasporto;
DROP SEQUENCE seq_dump_multipart_header;
DROP SEQUENCE seq_dump_messaggi;
DROP SEQUENCE seq_transazioni_export;
DROP SEQUENCE seq_credenziale_mittente;
