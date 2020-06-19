
-- Changeset com/jeesite/test/db/BpmModelerDbChangeLog.xml::0::flowable
DROP TABLE ACT_DE_MODEL_RELATION;

DROP TABLE ACT_DE_MODEL_HISTORY;

DROP TABLE ACT_DE_MODEL;

-- Changeset com/jeesite/test/db/BpmModelerDbChangeLog.xml::1::flowable
CREATE TABLE ACT_DE_MODEL (id VARCHAR(255) NOT NULL, name VARCHAR(400) NOT NULL, model_key VARCHAR(400) NOT NULL, description VARCHAR(4000), model_comment VARCHAR(4000), created TIMESTAMP(6) WITHOUT TIME ZONE, created_by VARCHAR(255), last_updated TIMESTAMP(6) WITHOUT TIME ZONE, last_updated_by VARCHAR(255), version INT, model_editor_json TEXT, thumbnail BYTEA, model_type INT, CONSTRAINT PK_ACT_DE_MODEL PRIMARY KEY (id));

CREATE INDEX idx_proc_mod_created ON ACT_DE_MODEL(created_by);

CREATE TABLE ACT_DE_MODEL_HISTORY (id VARCHAR(255) NOT NULL, name VARCHAR(400) NOT NULL, model_key VARCHAR(400) NOT NULL, description VARCHAR(4000), model_comment VARCHAR(4000), created TIMESTAMP(6) WITHOUT TIME ZONE, created_by VARCHAR(255), last_updated TIMESTAMP(6) WITHOUT TIME ZONE, last_updated_by VARCHAR(255), removal_date TIMESTAMP(6) WITHOUT TIME ZONE, version INT, model_editor_json TEXT, model_id VARCHAR(255) NOT NULL, model_type INT, CONSTRAINT PK_ACT_DE_MODEL_HISTORY PRIMARY KEY (id));

CREATE INDEX idx_proc_mod_history_proc ON ACT_DE_MODEL_HISTORY(model_id);

CREATE TABLE ACT_DE_MODEL_RELATION (id VARCHAR(255) NOT NULL, parent_model_id VARCHAR(255), model_id VARCHAR(255), relation_type VARCHAR(255), CONSTRAINT PK_ACT_DE_MODEL_RELATION PRIMARY KEY (id));

ALTER TABLE ACT_DE_MODEL_RELATION ADD CONSTRAINT fk_relation_parent FOREIGN KEY (parent_model_id) REFERENCES ACT_DE_MODEL (id);

ALTER TABLE ACT_DE_MODEL_RELATION ADD CONSTRAINT fk_relation_child FOREIGN KEY (model_id) REFERENCES ACT_DE_MODEL (id);

-- Changeset com/jeesite/test/db/BpmModelerDbChangeLog.xml::3::flowable
ALTER TABLE ACT_DE_MODEL ADD tenant_id VARCHAR(255);

ALTER TABLE ACT_DE_MODEL_HISTORY ADD tenant_id VARCHAR(255);

