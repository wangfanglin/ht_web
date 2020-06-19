
/* Drop Tables */

IF OBJECT_ID('[js_bpm_category]') IS NOT NULL DROP TABLE [js_bpm_category];
IF OBJECT_ID('[js_bpm_event]') IS NOT NULL DROP TABLE [js_bpm_event];
IF OBJECT_ID('[js_bpm_form]') IS NOT NULL DROP TABLE [js_bpm_form];
IF OBJECT_ID('[js_bpm_form_act]') IS NOT NULL DROP TABLE [js_bpm_form_act];
IF OBJECT_ID('[js_bpm_script]') IS NOT NULL DROP TABLE [js_bpm_script];
IF OBJECT_ID('[js_oa_leave]') IS NOT NULL DROP TABLE [js_oa_leave];




/* Create Tables */

-- 流程分类
CREATE TABLE [js_bpm_category]
(
	[category_code] varchar(64) NOT NULL,
	[parent_code] varchar(64) NOT NULL,
	[parent_codes] varchar(1000) NOT NULL,
	[tree_sort] decimal(10) NOT NULL,
	[tree_sorts] varchar(1000) NOT NULL,
	[tree_leaf] char(1) NOT NULL,
	[tree_level] decimal(4) NOT NULL,
	[tree_names] varchar(1000) NOT NULL,
	[category_name] varchar(64) NOT NULL,
	[status] char(1) DEFAULT '0' NOT NULL,
	[create_by] varchar(64) NOT NULL,
	[create_date] datetime NOT NULL,
	[update_by] varchar(64) NOT NULL,
	[update_date] datetime NOT NULL,
	[remarks] nvarchar(500),
	[corp_code] varchar(64) DEFAULT '0' NOT NULL,
	[corp_name] nvarchar(100) DEFAULT 'JeeSite' NOT NULL,
	PRIMARY KEY ([category_code])
);


-- 流程事件
CREATE TABLE [js_bpm_event]
(
	[event_id] varchar(64) NOT NULL,
	[form_id] varchar(64) NOT NULL,
	[event_name] nvarchar(100) NOT NULL,
	[event_type] varchar(100) NOT NULL,
	[event_script] text NOT NULL,
	[event_async] char(1) NOT NULL,
	[event_sort] numeric(10) NOT NULL,
	[status] char(1) DEFAULT '0' NOT NULL,
	[create_by] varchar(64) NOT NULL,
	[create_date] datetime NOT NULL,
	[update_by] varchar(64) NOT NULL,
	[update_date] datetime NOT NULL,
	[remarks] nvarchar(500),
	PRIMARY KEY ([event_id])
);


-- 流程表单
CREATE TABLE [js_bpm_form]
(
	[form_id] varchar(64) NOT NULL,
	[parent_code] varchar(64) NOT NULL,
	[parent_codes] varchar(1000) NOT NULL,
	[tree_sort] decimal(10) NOT NULL,
	[tree_sorts] varchar(1000) NOT NULL,
	[tree_leaf] char(1) NOT NULL,
	[tree_level] decimal(4) NOT NULL,
	[tree_names] varchar(1000) NOT NULL,
	[form_name] nvarchar(100) NOT NULL,
	[form_key] varchar(100) NOT NULL,
	[form_version] decimal(10) NOT NULL,
	[form_type] varchar(10) NOT NULL,
	[form_title] nvarchar(255),
	[pc_url] nvarchar(255),
	[mobile_url] nvarchar(255),
	[proc_def_key] varchar(64) NOT NULL,
	[proc_act_codes] text,
	[proc_act_names] text,
	[options] text,
	[status] char(1) DEFAULT '0' NOT NULL,
	[create_by] varchar(64) NOT NULL,
	[create_date] datetime NOT NULL,
	[update_by] varchar(64) NOT NULL,
	[update_date] datetime NOT NULL,
	[remarks] nvarchar(500),
	[corp_code] varchar(64) DEFAULT '0' NOT NULL,
	[corp_name] nvarchar(100) DEFAULT 'JeeSite' NOT NULL,
	PRIMARY KEY ([form_id])
);


-- 流程表单与活动关系表
CREATE TABLE [js_bpm_form_act]
(
	[form_id] varchar(64) NOT NULL,
	[proc_act_code] varchar(64) NOT NULL,
	[proc_act_name] nvarchar(200),
	PRIMARY KEY ([form_id], [proc_act_code])
);


-- 脚本管理
CREATE TABLE [js_bpm_script]
(
	[id] varchar(64) NOT NULL,
	[script_lang] varchar(30) NOT NULL,
	[script_type] varchar(20) NOT NULL,
	[script_name] nvarchar(100) NOT NULL,
	[script_text] text NOT NULL,
	[status] char(1) DEFAULT '0' NOT NULL,
	[create_by] varchar(64) NOT NULL,
	[create_date] datetime NOT NULL,
	[update_by] varchar(64) NOT NULL,
	[update_date] datetime NOT NULL,
	[remarks] nvarchar(500),
	PRIMARY KEY ([id])
);


-- 请假申请
CREATE TABLE [js_oa_leave]
(
	[id] varchar(64) NOT NULL,
	[user_code] varchar(64),
	[user_name] nvarchar(100),
	[office_code] varchar(64),
	[office_name] nvarchar(100),
	[start_time] datetime,
	[end_time] datetime,
	[leave_days] numeric(10,2),
	[leave_type] varchar(5),
	[leave_reason] nvarchar(500),
	[status] char(1) DEFAULT '0' NOT NULL,
	[create_by] varchar(64) NOT NULL,
	[create_date] datetime NOT NULL,
	[update_by] varchar(64) NOT NULL,
	[update_date] datetime NOT NULL,
	[remarks] nvarchar(500),
	[corp_code] varchar(64) DEFAULT '0' NOT NULL,
	[corp_name] nvarchar(100) DEFAULT 'JeeSite' NOT NULL,
	PRIMARY KEY ([id])
);



/* Create Indexes */

CREATE INDEX [idx_bpm_event_fi] ON [js_bpm_event] ([form_id]);
CREATE INDEX [idx_bpm_form_cc] ON [js_bpm_form] ([corp_code]);
CREATE INDEX [idx_bpm_form_pc] ON [js_bpm_form] ([parent_code]);
CREATE INDEX [idx_bpm_form_pcs] ON [js_bpm_form] ([parent_codes]);
CREATE INDEX [idx_bpm_form_fkv] ON [js_bpm_form] ([form_key], [form_version]);
CREATE INDEX [idx_bpm_form_status] ON [js_bpm_form] ([status]);
CREATE INDEX [idx_bpm_form_pdk] ON [js_bpm_form] ([proc_def_key]);
CREATE INDEX [idx_bpm_script_lang] ON [js_bpm_script] ([script_lang]);
CREATE INDEX [idx_bpm_script_status] ON [js_bpm_script] ([status]);



