-- Gli indici vengono eliminati automaticamente una volta eliminata la tabella
-- DROP INDEX index_credenziale_mittente_1 ON credenziale_mittente ;
-- DROP INDEX index_dump_contenuti_1 ON dump_contenuti ;
-- DROP INDEX index_dump_header_allegato_1 ON dump_header_allegato ;
-- DROP INDEX index_dump_allegati_1 ON dump_allegati ;
-- DROP INDEX index_dump_header_trasporto_1 ON dump_header_trasporto ;
-- DROP INDEX index_dump_multipart_header_1 ON dump_multipart_header ;
-- DROP INDEX index_dump_messaggi_3 ON dump_messaggi ;
-- DROP INDEX index_dump_messaggi_2 ON dump_messaggi ;
-- DROP INDEX index_dump_messaggi_1 ON dump_messaggi ;
-- DROP INDEX INDEX_TR_RIF_RICHIESTA ON transazioni ;
-- DROP INDEX INDEX_TR_COLLABORAZIONE ON transazioni ;
-- DROP INDEX INDEX_TR_FILTROD_RES_2 ON transazioni ;
-- DROP INDEX INDEX_TR_FILTROD_REQ_2 ON transazioni ;
-- DROP INDEX INDEX_TR_FILTROD_RES ON transazioni ;
-- DROP INDEX INDEX_TR_FILTROD_REQ ON transazioni ;
-- DROP INDEX INDEX_TR_STATS ON transazioni ;
-- DROP INDEX INDEX_TR_SEARCH ON transazioni ;
-- DROP INDEX INDEX_TR_FULL ON transazioni ;
-- DROP INDEX INDEX_TR_MEDIUM ON transazioni ;
-- DROP INDEX INDEX_TR_ENTRY ON transazioni ;
DROP TABLE dump_contenuti;
DROP TABLE dump_header_allegato;
DROP TABLE dump_allegati;
DROP TABLE dump_header_trasporto;
DROP TABLE dump_multipart_header;
DROP TABLE dump_messaggi;
DROP TABLE transazioni_export;
DROP TABLE transazioni_info;
DROP TABLE transazioni;
DROP TABLE credenziale_mittente;
