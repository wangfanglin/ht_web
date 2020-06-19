
-- Changeset com/jeesite/test/db/BpmModelerDbChangeLog.xml::0::flowable
DROP TABLE [ACT_DE_MODEL_RELATION];

DROP TABLE [ACT_DE_MODEL_HISTORY];

DROP TABLE [ACT_DE_MODEL];

-- Changeset com/jeesite/test/db/BpmModelerDbChangeLog.xml::1::flowable
CREATE TABLE [ACT_DE_MODEL] ([id] [varchar](255) NOT NULL, [name] [varchar](400) NOT NULL, [model_key] [varchar](400) NOT NULL, [description] [varchar](4000), [model_comment] [varchar](4000), [created] [datetime], [created_by] [varchar](255), [last_updated] [datetime], [last_updated_by] [varchar](255), [version] [int], [model_editor_json] [varchar](MAX), [thumbnail] [varbinary](MAX), [model_type] [int], CONSTRAINT [PK_ACT_DE_MODEL] PRIMARY KEY ([id]));

CREATE NONCLUSTERED INDEX idx_proc_mod_created ON [ACT_DE_MODEL]([created_by]);

CREATE TABLE [ACT_DE_MODEL_HISTORY] ([id] [varchar](255) NOT NULL, [name] [varchar](400) NOT NULL, [model_key] [varchar](400) NOT NULL, [description] [varchar](4000), [model_comment] [varchar](4000), [created] [datetime], [created_by] [varchar](255), [last_updated] [datetime], [last_updated_by] [varchar](255), [removal_date] [datetime], [version] [int], [model_editor_json] [varchar](MAX), [model_id] [varchar](255) NOT NULL, [model_type] [int], CONSTRAINT [PK_ACT_DE_MODEL_HISTORY] PRIMARY KEY ([id]));

CREATE NONCLUSTERED INDEX idx_proc_mod_history_proc ON [ACT_DE_MODEL_HISTORY]([model_id]);

CREATE TABLE [ACT_DE_MODEL_RELATION] ([id] [varchar](255) NOT NULL, [parent_model_id] [varchar](255), [model_id] [varchar](255), [relation_type] [varchar](255), CONSTRAINT [PK_ACT_DE_MODEL_RELATION] PRIMARY KEY ([id]));

ALTER TABLE [ACT_DE_MODEL_RELATION] ADD CONSTRAINT [fk_relation_parent] FOREIGN KEY ([parent_model_id]) REFERENCES [ACT_DE_MODEL] ([id]);

ALTER TABLE [ACT_DE_MODEL_RELATION] ADD CONSTRAINT [fk_relation_child] FOREIGN KEY ([model_id]) REFERENCES [ACT_DE_MODEL] ([id]);

-- Changeset com/jeesite/test/db/BpmModelerDbChangeLog.xml::2::flowable
ALTER TABLE [ACT_DE_MODEL] ALTER COLUMN [created] [datetime2](7);

ALTER TABLE [ACT_DE_MODEL] ALTER COLUMN [last_updated] [datetime2](7);

ALTER TABLE [ACT_DE_MODEL_HISTORY] ALTER COLUMN [created] [datetime2](7);

ALTER TABLE [ACT_DE_MODEL_HISTORY] ALTER COLUMN [last_updated] [datetime2](7);

ALTER TABLE [ACT_DE_MODEL_HISTORY] ALTER COLUMN [removal_date] [datetime2](7);

-- Changeset com/jeesite/test/db/BpmModelerDbChangeLog.xml::3::flowable
ALTER TABLE [ACT_DE_MODEL] ADD [tenant_id] [varchar](255);

ALTER TABLE [ACT_DE_MODEL_HISTORY] ADD [tenant_id] [varchar](255);

