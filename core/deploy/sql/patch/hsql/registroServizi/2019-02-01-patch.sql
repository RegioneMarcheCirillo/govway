ALTER TABLE soggetti ALTER COLUMN utente VARCHAR(2800);
ALTER TABLE soggetti ALTER COLUMN subject VARCHAR(2800);
ALTER TABLE soggetti ADD COLUMN cn_subject VARCHAR(255);
ALTER TABLE soggetti ADD COLUMN issuer VARCHAR(2800);
ALTER TABLE soggetti ADD COLUMN cn_issuer VARCHAR(255);
ALTER TABLE soggetti ADD COLUMN certificate VARBINARY(16777215);
ALTER TABLE soggetti ADD COLUMN cert_strict_verification INT;
