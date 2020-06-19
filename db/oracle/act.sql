
-- Changeset com/jeesite/test/db/BpmModelerDbChangeLog.xml::0::flowable
DROP TABLE ACT_DE_MODEL_RELATION;

DROP TABLE ACT_DE_MODEL_HISTORY;

DROP TABLE ACT_DE_MODEL;

-- Changeset com/jeesite/test/db/BpmModelerDbChangeLog.xml::1::flowable
CREATE TABLE ACT_DE_MODEL (id VARCHAR2(255) NOT NULL, name VARCHAR2(400) NOT NULL, model_key VARCHAR2(400) NOT NULL, description VARCHAR2(4000), model_comment VARCHAR2(4000), created TIMESTAMP(6), created_by VARCHAR2(255), last_updated TIMESTAMP(6), last_updated_by VARCHAR2(255), version INTEGER, model_editor_json CLOB, thumbnail BLOB, model_type INTEGER, CONSTRAINT PK_ACT_DE_MODEL PRIMARY KEY (id));

CREATE INDEX idx_proc_mod_created ON ACT_DE_MODEL(created_by);

CREATE TABLE ACT_DE_MODEL_HISTORY (id VARCHAR2(255) NOT NULL, name VARCHAR2(400) NOT NULL, model_key VARCHAR2(400) NOT NULL, description VARCHAR2(4000), model_comment VARCHAR2(4000), created TIMESTAMP(6), created_by VARCHAR2(255), last_updated TIMESTAMP(6), last_updated_by VARCHAR2(255), removal_date TIMESTAMP(6), version INTEGER, model_editor_json CLOB, model_id VARCHAR2(255) NOT NULL, model_type INTEGER, CONSTRAINT PK_ACT_DE_MODEL_HISTORY PRIMARY KEY (id));

CREATE INDEX idx_proc_mod_history_proc ON ACT_DE_MODEL_HISTORY(model_id);

CREATE TABLE ACT_DE_MODEL_RELATION (id VARCHAR2(255) NOT NULL, parent_model_id VARCHAR2(255), model_id VARCHAR2(255), relation_type VARCHAR2(255), CONSTRAINT PK_ACT_DE_MODEL_RELATION PRIMARY KEY (id));

ALTER TABLE ACT_DE_MODEL_RELATION ADD CONSTRAINT fk_relation_parent FOREIGN KEY (parent_model_id) REFERENCES ACT_DE_MODEL (id);

ALTER TABLE ACT_DE_MODEL_RELATION ADD CONSTRAINT fk_relation_child FOREIGN KEY (model_id) REFERENCES ACT_DE_MODEL (id);

-- Changeset com/jeesite/test/db/BpmModelerDbChangeLog.xml::3::flowable
ALTER TABLE ACT_DE_MODEL ADD tenant_id VARCHAR2(255);

ALTER TABLE ACT_DE_MODEL_HISTORY ADD tenant_id VARCHAR2(255);

