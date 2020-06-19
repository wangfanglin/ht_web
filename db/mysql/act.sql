
--  Changeset com/jeesite/test/db/BpmModelerDbChangeLog.xml::0::flowable
DROP TABLE ACT_DE_MODEL_RELATION;

DROP TABLE ACT_DE_MODEL_HISTORY;

DROP TABLE ACT_DE_MODEL;

--  Changeset com/jeesite/test/db/BpmModelerDbChangeLog.xml::1::flowable
CREATE TABLE ACT_DE_MODEL (id VARCHAR(255) NOT NULL, name VARCHAR(400) NOT NULL, model_key VARCHAR(400) NOT NULL, description VARCHAR(4000) NULL, model_comment VARCHAR(4000) NULL, created datetime(6) NULL, created_by VARCHAR(255) NULL, last_updated datetime(6) NULL, last_updated_by VARCHAR(255) NULL, version INT NULL, model_editor_json LONGTEXT NULL, thumbnail LONGBLOB NULL, model_type INT NULL, CONSTRAINT PK_ACT_DE_MODEL PRIMARY KEY (id));

CREATE INDEX idx_proc_mod_created ON ACT_DE_MODEL(created_by);

CREATE TABLE ACT_DE_MODEL_HISTORY (id VARCHAR(255) NOT NULL, name VARCHAR(400) NOT NULL, model_key VARCHAR(400) NOT NULL, description VARCHAR(4000) NULL, model_comment VARCHAR(4000) NULL, created datetime(6) NULL, created_by VARCHAR(255) NULL, last_updated datetime(6) NULL, last_updated_by VARCHAR(255) NULL, removal_date datetime(6) NULL, version INT NULL, model_editor_json LONGTEXT NULL, model_id VARCHAR(255) NOT NULL, model_type INT NULL, CONSTRAINT PK_ACT_DE_MODEL_HISTORY PRIMARY KEY (id));

CREATE INDEX idx_proc_mod_history_proc ON ACT_DE_MODEL_HISTORY(model_id);

CREATE TABLE ACT_DE_MODEL_RELATION (id VARCHAR(255) NOT NULL, parent_model_id VARCHAR(255) NULL, model_id VARCHAR(255) NULL, relation_type VARCHAR(255) NULL, CONSTRAINT PK_ACT_DE_MODEL_RELATION PRIMARY KEY (id));

ALTER TABLE ACT_DE_MODEL_RELATION ADD CONSTRAINT fk_relation_parent FOREIGN KEY (parent_model_id) REFERENCES ACT_DE_MODEL (id);

ALTER TABLE ACT_DE_MODEL_RELATION ADD CONSTRAINT fk_relation_child FOREIGN KEY (model_id) REFERENCES ACT_DE_MODEL (id);

--  Changeset com/jeesite/test/db/BpmModelerDbChangeLog.xml::3::flowable
ALTER TABLE ACT_DE_MODEL ADD tenant_id VARCHAR(255) NULL;

ALTER TABLE ACT_DE_MODEL_HISTORY ADD tenant_id VARCHAR(255) NULL;

