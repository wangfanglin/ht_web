
/* Drop Tables */

DROP TABLE IF EXISTS js_bpm_category;
DROP TABLE IF EXISTS js_bpm_event;
DROP TABLE IF EXISTS js_bpm_form;
DROP TABLE IF EXISTS js_bpm_form_act;
DROP TABLE IF EXISTS js_bpm_script;
DROP TABLE IF EXISTS js_oa_leave;




/* Create Tables */

-- 流程分类
CREATE TABLE js_bpm_category
(
	category_code varchar(64) NOT NULL,
	parent_code varchar(64) NOT NULL,
	parent_codes varchar(1000) NOT NULL,
	tree_sort decimal(10) NOT NULL,
	tree_sorts varchar(1000) NOT NULL,
	tree_leaf char(1) NOT NULL,
	tree_level decimal(4) NOT NULL,
	tree_names varchar(1000) NOT NULL,
	category_name varchar(64) NOT NULL,
	status char(1) DEFAULT '0' NOT NULL,
	create_by varchar(64) NOT NULL,
	create_date timestamp NOT NULL,
	update_by varchar(64) NOT NULL,
	update_date timestamp NOT NULL,
	remarks varchar(500),
	corp_code varchar(64) DEFAULT '0' NOT NULL,
	corp_name varchar(100) DEFAULT 'JeeSite' NOT NULL,
	PRIMARY KEY (category_code)
) WITHOUT OIDS;


-- 流程事件
CREATE TABLE js_bpm_event
(
	event_id varchar(64) NOT NULL,
	form_id varchar(64) NOT NULL,
	event_name varchar(100) NOT NULL,
	event_type varchar(100) NOT NULL,
	event_script text NOT NULL,
	event_async char(1) NOT NULL,
	event_sort numeric(10) NOT NULL,
	status char(1) DEFAULT '0' NOT NULL,
	create_by varchar(64) NOT NULL,
	create_date timestamp NOT NULL,
	update_by varchar(64) NOT NULL,
	update_date timestamp NOT NULL,
	remarks varchar(500),
	PRIMARY KEY (event_id)
) WITHOUT OIDS;


-- 流程表单
CREATE TABLE js_bpm_form
(
	form_id varchar(64) NOT NULL,
	parent_code varchar(64) NOT NULL,
	parent_codes varchar(1000) NOT NULL,
	tree_sort decimal(10) NOT NULL,
	tree_sorts varchar(1000) NOT NULL,
	tree_leaf char(1) NOT NULL,
	tree_level decimal(4) NOT NULL,
	tree_names varchar(1000) NOT NULL,
	form_name varchar(100) NOT NULL,
	form_key varchar(100) NOT NULL,
	form_version decimal(10) NOT NULL,
	form_type varchar(10) NOT NULL,
	form_title varchar(255),
	pc_url varchar(255),
	mobile_url varchar(255),
	proc_def_key varchar(64) NOT NULL,
	proc_act_codes text,
	proc_act_names text,
	options text,
	status char(1) DEFAULT '0' NOT NULL,
	create_by varchar(64) NOT NULL,
	create_date timestamp NOT NULL,
	update_by varchar(64) NOT NULL,
	update_date timestamp NOT NULL,
	remarks varchar(500),
	corp_code varchar(64) DEFAULT '0' NOT NULL,
	corp_name varchar(100) DEFAULT 'JeeSite' NOT NULL,
	PRIMARY KEY (form_id)
) WITHOUT OIDS;


-- 流程表单与活动关系表
CREATE TABLE js_bpm_form_act
(
	form_id varchar(64) NOT NULL,
	proc_act_code varchar(64) NOT NULL,
	proc_act_name varchar(200),
	PRIMARY KEY (form_id, proc_act_code)
) WITHOUT OIDS;


-- 脚本管理
CREATE TABLE js_bpm_script
(
	id varchar(64) NOT NULL,
	script_lang varchar(30) NOT NULL,
	script_type varchar(20) NOT NULL,
	script_name varchar(100) NOT NULL,
	script_text text NOT NULL,
	status char(1) DEFAULT '0' NOT NULL,
	create_by varchar(64) NOT NULL,
	create_date timestamp NOT NULL,
	update_by varchar(64) NOT NULL,
	update_date timestamp NOT NULL,
	remarks varchar(500),
	PRIMARY KEY (id)
) WITHOUT OIDS;


-- 请假申请
CREATE TABLE js_oa_leave
(
	id varchar(64) NOT NULL,
	user_code varchar(64),
	user_name varchar(100),
	office_code varchar(64),
	office_name varchar(100),
	start_time timestamp,
	end_time timestamp,
	leave_days numeric(10,2),
	leave_type varchar(5),
	leave_reason varchar(500),
	status char(1) DEFAULT '0' NOT NULL,
	create_by varchar(64) NOT NULL,
	create_date timestamp NOT NULL,
	update_by varchar(64) NOT NULL,
	update_date timestamp NOT NULL,
	remarks varchar(500),
	corp_code varchar(64) DEFAULT '0' NOT NULL,
	corp_name varchar(100) DEFAULT 'JeeSite' NOT NULL,
	PRIMARY KEY (id)
) WITHOUT OIDS;



/* Create Indexes */

CREATE INDEX idx_bpm_event_fi ON js_bpm_event (form_id);
CREATE INDEX idx_bpm_form_cc ON js_bpm_form (corp_code);
CREATE INDEX idx_bpm_form_pc ON js_bpm_form (parent_code);
CREATE INDEX idx_bpm_form_pcs ON js_bpm_form (parent_codes);
CREATE INDEX idx_bpm_form_fkv ON js_bpm_form (form_key, form_version);
CREATE INDEX idx_bpm_form_status ON js_bpm_form (status);
CREATE INDEX idx_bpm_form_pdk ON js_bpm_form (proc_def_key);
CREATE INDEX idx_bpm_script_lang ON js_bpm_script (script_lang);
CREATE INDEX idx_bpm_script_status ON js_bpm_script (status);



/* Comments */

COMMENT ON TABLE js_bpm_category IS '流程分类';
COMMENT ON COLUMN js_bpm_category.category_code IS '流程分类';
COMMENT ON COLUMN js_bpm_category.parent_code IS '父级编号';
COMMENT ON COLUMN js_bpm_category.parent_codes IS '所有父级编号';
COMMENT ON COLUMN js_bpm_category.tree_sort IS '本级排序号（升序）';
COMMENT ON COLUMN js_bpm_category.tree_sorts IS '所有级别排序号';
COMMENT ON COLUMN js_bpm_category.tree_leaf IS '是否最末级';
COMMENT ON COLUMN js_bpm_category.tree_level IS '层次级别';
COMMENT ON COLUMN js_bpm_category.tree_names IS '全节点名';
COMMENT ON COLUMN js_bpm_category.category_name IS '分类名称';
COMMENT ON COLUMN js_bpm_category.status IS '状态（0正常 1删除 2停用）';
COMMENT ON COLUMN js_bpm_category.create_by IS '创建者';
COMMENT ON COLUMN js_bpm_category.create_date IS '创建时间';
COMMENT ON COLUMN js_bpm_category.update_by IS '更新者';
COMMENT ON COLUMN js_bpm_category.update_date IS '更新时间';
COMMENT ON COLUMN js_bpm_category.remarks IS '备注信息';
COMMENT ON COLUMN js_bpm_category.corp_code IS '租户代码';
COMMENT ON COLUMN js_bpm_category.corp_name IS '租户名称';
COMMENT ON TABLE js_bpm_event IS '流程事件';
COMMENT ON COLUMN js_bpm_event.event_id IS '事件编号';
COMMENT ON COLUMN js_bpm_event.form_id IS '表单编号';
COMMENT ON COLUMN js_bpm_event.event_name IS '事件名称';
COMMENT ON COLUMN js_bpm_event.event_type IS '事件类型';
COMMENT ON COLUMN js_bpm_event.event_script IS '事件脚本';
COMMENT ON COLUMN js_bpm_event.event_async IS '是否异步';
COMMENT ON COLUMN js_bpm_event.event_sort IS '事件排序';
COMMENT ON COLUMN js_bpm_event.status IS '状态（0正常 1删除 2停用）';
COMMENT ON COLUMN js_bpm_event.create_by IS '创建者';
COMMENT ON COLUMN js_bpm_event.create_date IS '创建时间';
COMMENT ON COLUMN js_bpm_event.update_by IS '更新者';
COMMENT ON COLUMN js_bpm_event.update_date IS '更新时间';
COMMENT ON COLUMN js_bpm_event.remarks IS '备注信息';
COMMENT ON TABLE js_bpm_form IS '流程表单';
COMMENT ON COLUMN js_bpm_form.form_id IS '表单编号';
COMMENT ON COLUMN js_bpm_form.parent_code IS '父级编号';
COMMENT ON COLUMN js_bpm_form.parent_codes IS '所有父级编号';
COMMENT ON COLUMN js_bpm_form.tree_sort IS '本级排序号（升序）';
COMMENT ON COLUMN js_bpm_form.tree_sorts IS '所有级别排序号';
COMMENT ON COLUMN js_bpm_form.tree_leaf IS '是否最末级';
COMMENT ON COLUMN js_bpm_form.tree_level IS '层次级别';
COMMENT ON COLUMN js_bpm_form.tree_names IS '全节点名';
COMMENT ON COLUMN js_bpm_form.form_name IS '表单名称';
COMMENT ON COLUMN js_bpm_form.form_key IS '表单Key';
COMMENT ON COLUMN js_bpm_form.form_version IS '表单版本';
COMMENT ON COLUMN js_bpm_form.form_type IS '表单类型';
COMMENT ON COLUMN js_bpm_form.form_title IS '表单标题';
COMMENT ON COLUMN js_bpm_form.pc_url IS 'PC表单地址';
COMMENT ON COLUMN js_bpm_form.mobile_url IS '手机表单地址';
COMMENT ON COLUMN js_bpm_form.proc_def_key IS '流程定义Key';
COMMENT ON COLUMN js_bpm_form.proc_act_codes IS '活动编码';
COMMENT ON COLUMN js_bpm_form.proc_act_names IS '活动名称';
COMMENT ON COLUMN js_bpm_form.options IS '表单选项';
COMMENT ON COLUMN js_bpm_form.status IS '状态（0正常 1删除 2停用）';
COMMENT ON COLUMN js_bpm_form.create_by IS '创建者';
COMMENT ON COLUMN js_bpm_form.create_date IS '创建时间';
COMMENT ON COLUMN js_bpm_form.update_by IS '更新者';
COMMENT ON COLUMN js_bpm_form.update_date IS '更新时间';
COMMENT ON COLUMN js_bpm_form.remarks IS '备注信息';
COMMENT ON COLUMN js_bpm_form.corp_code IS '租户代码';
COMMENT ON COLUMN js_bpm_form.corp_name IS '租户名称';
COMMENT ON TABLE js_bpm_form_act IS '流程表单与活动关系表';
COMMENT ON COLUMN js_bpm_form_act.form_id IS '表单编号';
COMMENT ON COLUMN js_bpm_form_act.proc_act_code IS '活动编码';
COMMENT ON COLUMN js_bpm_form_act.proc_act_name IS '活动名称';
COMMENT ON TABLE js_bpm_script IS '脚本管理';
COMMENT ON COLUMN js_bpm_script.id IS '编号';
COMMENT ON COLUMN js_bpm_script.script_lang IS '脚本语言';
COMMENT ON COLUMN js_bpm_script.script_type IS '脚本类别';
COMMENT ON COLUMN js_bpm_script.script_name IS '脚本名称';
COMMENT ON COLUMN js_bpm_script.script_text IS '脚本内容';
COMMENT ON COLUMN js_bpm_script.status IS '状态（0正常 1删除 2停用）';
COMMENT ON COLUMN js_bpm_script.create_by IS '创建者';
COMMENT ON COLUMN js_bpm_script.create_date IS '创建时间';
COMMENT ON COLUMN js_bpm_script.update_by IS '更新者';
COMMENT ON COLUMN js_bpm_script.update_date IS '更新时间';
COMMENT ON COLUMN js_bpm_script.remarks IS '备注信息';
COMMENT ON TABLE js_oa_leave IS '请假申请';
COMMENT ON COLUMN js_oa_leave.id IS '编号';
COMMENT ON COLUMN js_oa_leave.user_code IS '请假人';
COMMENT ON COLUMN js_oa_leave.user_name IS '请假人名称';
COMMENT ON COLUMN js_oa_leave.office_code IS '部门编码';
COMMENT ON COLUMN js_oa_leave.office_name IS '部门名称';
COMMENT ON COLUMN js_oa_leave.start_time IS '开始时间';
COMMENT ON COLUMN js_oa_leave.end_time IS '结束时间';
COMMENT ON COLUMN js_oa_leave.leave_days IS '请假天数';
COMMENT ON COLUMN js_oa_leave.leave_type IS '请假类型';
COMMENT ON COLUMN js_oa_leave.leave_reason IS '请假原因';
COMMENT ON COLUMN js_oa_leave.status IS '状态（0正常 1删除 2停用）';
COMMENT ON COLUMN js_oa_leave.create_by IS '创建者';
COMMENT ON COLUMN js_oa_leave.create_date IS '创建时间';
COMMENT ON COLUMN js_oa_leave.update_by IS '更新者';
COMMENT ON COLUMN js_oa_leave.update_date IS '更新时间';
COMMENT ON COLUMN js_oa_leave.remarks IS '备注信息';
COMMENT ON COLUMN js_oa_leave.corp_code IS '租户代码';
COMMENT ON COLUMN js_oa_leave.corp_name IS '租户名称';



