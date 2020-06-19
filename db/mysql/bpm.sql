SET SESSION FOREIGN_KEY_CHECKS=0;

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
	category_code varchar(64) NOT NULL COMMENT '流程分类',
	parent_code varchar(64) NOT NULL COMMENT '父级编号',
	parent_codes varchar(1000) NOT NULL COMMENT '所有父级编号',
	tree_sort decimal(10) NOT NULL COMMENT '本级排序号（升序）',
	tree_sorts varchar(1000) NOT NULL COMMENT '所有级别排序号',
	tree_leaf char(1) NOT NULL COMMENT '是否最末级',
	tree_level decimal(4) NOT NULL COMMENT '层次级别',
	tree_names varchar(1000) NOT NULL COMMENT '全节点名',
	category_name varchar(64) NOT NULL COMMENT '分类名称',
	status char(1) DEFAULT '0' NOT NULL COMMENT '状态（0正常 1删除 2停用）',
	create_by varchar(64) NOT NULL COMMENT '创建者',
	create_date datetime NOT NULL COMMENT '创建时间',
	update_by varchar(64) NOT NULL COMMENT '更新者',
	update_date datetime NOT NULL COMMENT '更新时间',
	remarks varchar(500) COMMENT '备注信息',
	corp_code varchar(64) DEFAULT '0' NOT NULL COMMENT '租户代码',
	corp_name varchar(100) DEFAULT 'JeeSite' NOT NULL COMMENT '租户名称',
	PRIMARY KEY (category_code)
) COMMENT = '流程分类';


-- 流程事件
CREATE TABLE js_bpm_event
(
	event_id varchar(64) NOT NULL COMMENT '事件编号',
	form_id varchar(64) NOT NULL COMMENT '表单编号',
	event_name varchar(100) NOT NULL COMMENT '事件名称',
	event_type varchar(100) NOT NULL COMMENT '事件类型',
	event_script text NOT NULL COMMENT '事件脚本',
	event_async char(1) NOT NULL COMMENT '是否异步',
	event_sort numeric(10) NOT NULL COMMENT '事件排序',
	status char(1) DEFAULT '0' NOT NULL COMMENT '状态（0正常 1删除 2停用）',
	create_by varchar(64) NOT NULL COMMENT '创建者',
	create_date datetime NOT NULL COMMENT '创建时间',
	update_by varchar(64) NOT NULL COMMENT '更新者',
	update_date datetime NOT NULL COMMENT '更新时间',
	remarks varchar(500) COMMENT '备注信息',
	PRIMARY KEY (event_id)
) COMMENT = '流程事件';


-- 流程表单
CREATE TABLE js_bpm_form
(
	form_id varchar(64) NOT NULL COMMENT '表单编号',
	parent_code varchar(64) NOT NULL COMMENT '父级编号',
	parent_codes varchar(1000) NOT NULL COMMENT '所有父级编号',
	tree_sort decimal(10) NOT NULL COMMENT '本级排序号（升序）',
	tree_sorts varchar(1000) NOT NULL COMMENT '所有级别排序号',
	tree_leaf char(1) NOT NULL COMMENT '是否最末级',
	tree_level decimal(4) NOT NULL COMMENT '层次级别',
	tree_names varchar(1000) NOT NULL COMMENT '全节点名',
	form_name varchar(100) NOT NULL COMMENT '表单名称',
	form_key varchar(100) NOT NULL COMMENT '表单Key',
	form_version decimal(10) NOT NULL COMMENT '表单版本',
	form_type varchar(10) NOT NULL COMMENT '表单类型',
	form_title varchar(255) COMMENT '表单标题',
	pc_url varchar(255) COMMENT 'PC表单地址',
	mobile_url varchar(255) COMMENT '手机表单地址',
	proc_def_key varchar(64) NOT NULL COMMENT '流程定义Key',
	proc_act_codes text COMMENT '活动编码',
	proc_act_names text COMMENT '活动名称',
	options text COMMENT '表单选项',
	status char(1) DEFAULT '0' NOT NULL COMMENT '状态（0正常 1删除 2停用）',
	create_by varchar(64) NOT NULL COMMENT '创建者',
	create_date datetime NOT NULL COMMENT '创建时间',
	update_by varchar(64) NOT NULL COMMENT '更新者',
	update_date datetime NOT NULL COMMENT '更新时间',
	remarks varchar(500) COMMENT '备注信息',
	corp_code varchar(64) DEFAULT '0' NOT NULL COMMENT '租户代码',
	corp_name varchar(100) DEFAULT 'JeeSite' NOT NULL COMMENT '租户名称',
	PRIMARY KEY (form_id)
) COMMENT = '流程表单';


-- 流程表单与活动关系表
CREATE TABLE js_bpm_form_act
(
	form_id varchar(64) NOT NULL COMMENT '表单编号',
	proc_act_code varchar(64) NOT NULL COMMENT '活动编码',
	proc_act_name varchar(200) COMMENT '活动名称',
	PRIMARY KEY (form_id, proc_act_code)
) COMMENT = '流程表单与活动关系表';


-- 脚本管理
CREATE TABLE js_bpm_script
(
	id varchar(64) NOT NULL COMMENT '编号',
	script_lang varchar(30) NOT NULL COMMENT '脚本语言',
	script_type varchar(20) NOT NULL COMMENT '脚本类别',
	script_name varchar(100) NOT NULL COMMENT '脚本名称',
	script_text text NOT NULL COMMENT '脚本内容',
	status char(1) DEFAULT '0' NOT NULL COMMENT '状态（0正常 1删除 2停用）',
	create_by varchar(64) NOT NULL COMMENT '创建者',
	create_date datetime NOT NULL COMMENT '创建时间',
	update_by varchar(64) NOT NULL COMMENT '更新者',
	update_date datetime NOT NULL COMMENT '更新时间',
	remarks varchar(500) COMMENT '备注信息',
	PRIMARY KEY (id)
) COMMENT = '脚本管理';


-- 请假申请
CREATE TABLE js_oa_leave
(
	id varchar(64) NOT NULL COMMENT '编号',
	user_code varchar(64) COMMENT '请假人',
	user_name varchar(100) COMMENT '请假人名称',
	office_code varchar(64) COMMENT '部门编码',
	office_name varchar(100) COMMENT '部门名称',
	start_time datetime COMMENT '开始时间',
	end_time datetime COMMENT '结束时间',
	leave_days numeric(10,2) COMMENT '请假天数',
	leave_type varchar(5) COMMENT '请假类型',
	leave_reason varchar(500) COMMENT '请假原因',
	status char(1) DEFAULT '0' NOT NULL COMMENT '状态（0正常 1删除 2停用）',
	create_by varchar(64) NOT NULL COMMENT '创建者',
	create_date datetime NOT NULL COMMENT '创建时间',
	update_by varchar(64) NOT NULL COMMENT '更新者',
	update_date datetime NOT NULL COMMENT '更新时间',
	remarks varchar(500) COMMENT '备注信息',
	corp_code varchar(64) DEFAULT '0' NOT NULL COMMENT '租户代码',
	corp_name varchar(100) DEFAULT 'JeeSite' NOT NULL COMMENT '租户名称',
	PRIMARY KEY (id)
) COMMENT = '请假申请';



/* Create Indexes */

CREATE INDEX idx_bpm_event_fi ON js_bpm_event (form_id ASC);
CREATE INDEX idx_bpm_form_cc ON js_bpm_form (corp_code ASC);
CREATE INDEX idx_bpm_form_pc ON js_bpm_form (parent_code ASC);
CREATE INDEX idx_bpm_form_pcs ON js_bpm_form (parent_codes ASC);
CREATE INDEX idx_bpm_form_fkv ON js_bpm_form (form_key ASC, form_version ASC);
CREATE INDEX idx_bpm_form_status ON js_bpm_form (status ASC);
CREATE INDEX idx_bpm_form_pdk ON js_bpm_form (proc_def_key ASC);
CREATE INDEX idx_bpm_script_lang ON js_bpm_script (script_lang ASC);
CREATE INDEX idx_bpm_script_status ON js_bpm_script (status ASC);



