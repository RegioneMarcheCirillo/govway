-- Gli indici vengono eliminati automaticamente una volta eliminata la tabella
-- DROP INDEX index_registro_plug_jar_1;
-- DROP INDEX index_generic_property_1;
DROP TRIGGER trg_registro_plug_jar;
DROP TRIGGER trg_registro_plugins;
DROP TRIGGER trg_generic_property;
DROP TRIGGER trg_generic_properties;
DROP TRIGGER trg_pdd_sys_props;
DROP TRIGGER trg_servizi_pdd_filtri;
DROP TRIGGER trg_servizi_pdd;
DROP TRIGGER trg_tracce_ds_prop;
DROP TRIGGER trg_tracce_ds;
DROP TRIGGER trg_msgdiag_ds_prop;
DROP TRIGGER trg_msgdiag_ds;
DROP TRIGGER trg_dump_appender_prop;
DROP TRIGGER trg_dump_appender;
DROP TRIGGER trg_dump_config_regola;
DROP TRIGGER trg_dump_config;
DROP TRIGGER trg_tracce_appender_prop;
DROP TRIGGER trg_tracce_appender;
DROP TRIGGER trg_msgdiag_appender_prop;
DROP TRIGGER trg_msgdiag_appender;
DROP TRIGGER trg_config_url_regole;
DROP TRIGGER trg_config_url_invocazione;
DROP TRIGGER trg_configurazione;
DROP TRIGGER trg_config_cache_regole;
DROP TRIGGER trg_routing;
DROP TRIGGER trg_registri;
DROP TABLE registro_plug_jar;
DROP TABLE registro_plugins;
DROP TABLE generic_property;
DROP TABLE generic_properties;
DROP TABLE pdd_sys_props;
DROP TABLE servizi_pdd_filtri;
DROP TABLE servizi_pdd;
DROP TABLE tracce_ds_prop;
DROP TABLE tracce_ds;
DROP TABLE msgdiag_ds_prop;
DROP TABLE msgdiag_ds;
DROP TABLE dump_appender_prop;
DROP TABLE dump_appender;
DROP TABLE dump_config_regola;
DROP TABLE dump_config;
DROP TABLE tracce_appender_prop;
DROP TABLE tracce_appender;
DROP TABLE msgdiag_appender_prop;
DROP TABLE msgdiag_appender;
DROP TABLE config_url_regole;
DROP TABLE config_url_invocazione;
DROP TABLE configurazione;
DROP TABLE config_cache_regole;
DROP TABLE routing;
DROP TABLE registri;
DROP SEQUENCE seq_registro_plug_jar;
DROP SEQUENCE seq_registro_plugins;
DROP SEQUENCE seq_generic_property;
DROP SEQUENCE seq_generic_properties;
DROP SEQUENCE seq_pdd_sys_props;
DROP SEQUENCE seq_servizi_pdd_filtri;
DROP SEQUENCE seq_servizi_pdd;
DROP SEQUENCE seq_tracce_ds_prop;
DROP SEQUENCE seq_tracce_ds;
DROP SEQUENCE seq_msgdiag_ds_prop;
DROP SEQUENCE seq_msgdiag_ds;
DROP SEQUENCE seq_dump_appender_prop;
DROP SEQUENCE seq_dump_appender;
DROP SEQUENCE seq_dump_config_regola;
DROP SEQUENCE seq_dump_config;
DROP SEQUENCE seq_tracce_appender_prop;
DROP SEQUENCE seq_tracce_appender;
DROP SEQUENCE seq_msgdiag_appender_prop;
DROP SEQUENCE seq_msgdiag_appender;
DROP SEQUENCE seq_config_url_regole;
DROP SEQUENCE seq_config_url_invocazione;
DROP SEQUENCE seq_configurazione;
DROP SEQUENCE seq_config_cache_regole;
DROP SEQUENCE seq_routing;
DROP SEQUENCE seq_registri;
