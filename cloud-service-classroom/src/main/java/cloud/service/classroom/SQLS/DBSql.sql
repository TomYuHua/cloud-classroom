/*
Navicat MySQL Data Transfer

Source Server         : 192.168.1.201
Source Server Version : 50718
Source Host           : 192.168.1.201:3306
Source Database       : uva_class_room

Target Server Type    : MYSQL
Target Server Version : 50718
File Encoding         : 65001

Date: 2017-09-14 15:37:53
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for Application
-- ----------------------------
DROP TABLE IF EXISTS `Application`;
CREATE TABLE "Application" (
  "ApplicationID" int(11) NOT NULL AUTO_INCREMENT,
  "ApplicationCode" varchar(20) DEFAULT NULL,
  "ApplicationName" varchar(20) DEFAULT NULL,
  "Description" varchar(200) DEFAULT NULL,
  "ShowInMenu" varchar(20) DEFAULT NULL,
  PRIMARY KEY ("ApplicationID")
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='应用表，或者模块表';

-- ----------------------------
-- Table structure for Button
-- ----------------------------
DROP TABLE IF EXISTS `Button`;
CREATE TABLE "Button" (
  "ButtonID" int(11) NOT NULL,
  "MenuID" int(11) DEFAULT NULL,
  "ButtonName" varchar(20) DEFAULT NULL,
  "ButtonNo" varchar(200) DEFAULT NULL,
  "ButtonClass" varchar(20) DEFAULT NULL,
  "ButtonIcon" varchar(200) DEFAULT NULL,
  "ButtonScript" varchar(200) DEFAULT NULL,
  "MenuNO" varchar(200) DEFAULT NULL,
  "InitStatus" int(11) DEFAULT NULL,
  "SeqNo" varchar(200) DEFAULT NULL,
  PRIMARY KEY ("ButtonID")
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ChapterDirectories
-- ----------------------------
DROP TABLE IF EXISTS `ChapterDirectories`;
CREATE TABLE "ChapterDirectories" (
  "DirectoriesId" int(11) NOT NULL AUTO_INCREMENT,
  "ParentId" int(11) DEFAULT NULL,
  "name" varchar(200) DEFAULT NULL,
  "Types" int(11) DEFAULT NULL COMMENT '1视频 2文档 3电子书',
  "Icon" varchar(200) DEFAULT NULL,
  "IsTextbook" tinyint(1) DEFAULT NULL,
  PRIMARY KEY ("DirectoriesId")
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for class_info
-- ----------------------------
DROP TABLE IF EXISTS `class_info`;
CREATE TABLE "class_info" (
  "id" int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id，自增',
  "schoolNo" varchar(20) DEFAULT NULL COMMENT '学校编码',
  "yearSemesterId" int(11) DEFAULT NULL COMMENT '学年学期id',
  "gradeId" int(11) DEFAULT NULL COMMENT '学校年级Id',
  "classNo" varchar(16) DEFAULT NULL COMMENT '班级编号',
  "className" varchar(16) DEFAULT NULL COMMENT '班级名称',
  "aliasName" varchar(16) DEFAULT NULL COMMENT '班级别名',
  "classProperty" varchar(8) DEFAULT NULL COMMENT '班级性质，来自字典表(普通、文、理 )',
  "classTeacherId" int(11) DEFAULT NULL,
  "classTeacher" varchar(30) DEFAULT NULL COMMENT '班主任id，班主任id来自行政管理系统教职工表',
  "maxMember" int(11) DEFAULT NULL,
  "learnStage" varchar(30) DEFAULT NULL COMMENT '学段',
  "grade" varchar(30) DEFAULT NULL COMMENT '年级',
  PRIMARY KEY ("id")
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='班级信息表';

-- ----------------------------
-- Table structure for class_student
-- ----------------------------
DROP TABLE IF EXISTS `class_student`;
CREATE TABLE "class_student" (
  "id" int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id，自增',
  "schoolNo" varchar(20) NOT NULL COMMENT '学校编码',
  "yearSemesterId" int(11) NOT NULL COMMENT '学年学期id',
  "studentNo" varchar(200) NOT NULL,
  "gradeId" int(11) DEFAULT NULL,
  "gradeName" varchar(20) DEFAULT NULL,
  "classInfoId" int(11) NOT NULL COMMENT '年级班级信息表id',
  "className" varchar(20) DEFAULT NULL,
  "studentId" int(11) NOT NULL COMMENT '学生基本信息表id，一并获取了姓名、性别、出生日期、籍贯四个冗余字段，每次批量向学生管理系统询问，这四个字段是否更新',
  "name" varchar(20) NOT NULL COMMENT '学生姓名，冗余字段，来自学生基本信息表',
  "sex" varchar(2) NOT NULL COMMENT '学生性别，冗余字段，来自学生基本信息表',
  "birthday" date NOT NULL COMMENT '出生日期，冗余字段，来自学生基本信息表',
  "isFailure" int(11) NOT NULL COMMENT '学生跟班级的联系是否失效 0：未失效  1：失效  默认0',
  "volunteer" varchar(20) DEFAULT NULL,
  "speciality" varchar(20) DEFAULT NULL,
  "nativePlace" varchar(50) DEFAULT NULL,
  "score" double DEFAULT NULL,
  "idNumber" varchar(50) DEFAULT NULL,
  "passStatus" varchar(4) DEFAULT NULL COMMENT '学生绑定班级信息确认（0 否，1 是）；例如班主任角色',
  "createTime" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY ("id"),
  KEY "classInfoId" ("classInfoId"),
  KEY "t_cb_tm_class_student_ibfk_1" ("studentId")
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='班级学生表';

-- ----------------------------
-- Table structure for Collections
-- ----------------------------
DROP TABLE IF EXISTS `Collections`;
CREATE TABLE "Collections" (
  "Id" int(11) NOT NULL,
  "resourceId" int(11) DEFAULT NULL,
  "userId" int(11) DEFAULT NULL,
  "types" int(11) DEFAULT NULL,
  PRIMARY KEY ("Id")
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for course
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE "course" (
  "id" int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  "schoolNo" varchar(20) NOT NULL COMMENT '学校编码',
  "stdName" varchar(20) NOT NULL COMMENT '课程名称',
  "shortName" varchar(10) DEFAULT NULL COMMENT '课程简称',
  "category" varchar(4) NOT NULL COMMENT '课程性质  0:表示基础课程  1：表示特色教程',
  "state" varchar(2) NOT NULL COMMENT '停用启用标志 0：停用 1：启用 ，新增默认启用',
  PRIMARY KEY ("id")
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='课程表';

-- ----------------------------
-- Table structure for dictionary
-- ----------------------------
DROP TABLE IF EXISTS `dictionary`;
CREATE TABLE "dictionary" (
  "id" int(11) NOT NULL COMMENT 'id',
  "typeValue" varchar(30) DEFAULT NULL COMMENT 'typeValue',
  "dicName" varchar(30) NOT NULL COMMENT 'dicName',
  "dicValue" varchar(8) DEFAULT NULL COMMENT 'dicValue',
  "remark" varchar(200) DEFAULT NULL,
  "sort" int(3) DEFAULT NULL COMMENT 'sort',
  PRIMARY KEY ("id")
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='字典表数据';

-- ----------------------------
-- Table structure for dictionary_type
-- ----------------------------
DROP TABLE IF EXISTS `dictionary_type`;
CREATE TABLE "dictionary_type" (
  "id" int(11) NOT NULL COMMENT 'id',
  "typeName" varchar(30) DEFAULT NULL COMMENT '类型名',
  "typeValue" varchar(30) NOT NULL COMMENT '类型值',
  PRIMARY KEY ("id")
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数字字典类型';

-- ----------------------------
-- Table structure for grade
-- ----------------------------
DROP TABLE IF EXISTS `grade`;
CREATE TABLE "grade" (
  "id" int(11) NOT NULL AUTO_INCREMENT,
  "schoolNo" varchar(20) DEFAULT NULL,
  "learnStage" varchar(8) NOT NULL COMMENT '学段，小学，初中，高中',
  "grade" varchar(8) NOT NULL COMMENT '年级 跟学段一起生成一字符串，小学一年级，初中一年级，高中一年级等等',
  "gradeNo" varchar(20) DEFAULT NULL,
  "gradeAlias" varchar(20) DEFAULT NULL,
  "gradeHeadId" int(11) DEFAULT NULL,
  "gradeHead" varchar(20) DEFAULT NULL,
  "sort" int(11) DEFAULT NULL,
  PRIMARY KEY ("id")
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='学校年级表，学段与年级云后台维护';

-- ----------------------------
-- Table structure for Menu
-- ----------------------------
DROP TABLE IF EXISTS `Menu`;
CREATE TABLE "Menu" (
  "MenuID" int(11) NOT NULL AUTO_INCREMENT,
  "ApplicationID" int(11) DEFAULT NULL,
  "MenuNO" varchar(20) DEFAULT NULL,
  "ApplicationCode" varchar(20) DEFAULT NULL,
  "MenuParentNo" varchar(20) DEFAULT NULL,
  "MenuOrder" int(11) DEFAULT NULL,
  "MenuName" varchar(20) DEFAULT NULL,
  "MenuUrl" varchar(200) DEFAULT NULL,
  "MenuIcon" varchar(200) DEFAULT NULL,
  "IsVisible" tinyint(1) DEFAULT NULL,
  PRIMARY KEY ("MenuID")
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for organization
-- ----------------------------
DROP TABLE IF EXISTS `organization`;
CREATE TABLE "organization" (
  "Id" int(11) NOT NULL,
  "ParentId" int(11) DEFAULT NULL,
  "Code" varchar(20) DEFAULT NULL,
  "FullName" varchar(20) DEFAULT NULL,
  "ShortName" varchar(20) DEFAULT NULL,
  "CategoryCode" varchar(20) DEFAULT NULL,
  "OuterPhone" varchar(20) DEFAULT NULL,
  "InnerPhone" varchar(20) DEFAULT NULL,
  "Fax" varchar(20) DEFAULT NULL,
  "Postalcode" varchar(20) DEFAULT NULL,
  "Address" varchar(100) DEFAULT NULL,
  "SortCode" int(11) DEFAULT NULL,
  "Description" varchar(200) DEFAULT NULL,
  "Enabled" tinyint(1) DEFAULT NULL,
  "CreateUserId" int(11) DEFAULT NULL,
  "CreateTime" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY ("Id")
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for Privilege
-- ----------------------------
DROP TABLE IF EXISTS `Privilege`;
CREATE TABLE "Privilege" (
  "PrivilegeId" int(11) NOT NULL AUTO_INCREMENT,
  "PrivilegeMaster" varchar(200) DEFAULT NULL COMMENT '主体为 1用户 或者2角色，或者某个3部门',
  "PrivilegeMasterValue" varchar(200) DEFAULT NULL,
  "PrivilegeAccess" varchar(200) DEFAULT NULL COMMENT '1 Menu 2 Button',
  "PrivilegeAccessValue" varchar(200) DEFAULT NULL COMMENT 'MenuNo or ButtonNo',
  "PrivilegeOperation" varchar(20) DEFAULT NULL COMMENT '禁用 某个主体（用户 或者角色）设置为disabled。有使用权限设置成enabled',
  PRIMARY KEY ("PrivilegeId")
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8 COMMENT='哪个主体 在 哪个地方 有 哪些权限';

-- ----------------------------
-- Table structure for ResourceBrowse
-- ----------------------------
DROP TABLE IF EXISTS `ResourceBrowse`;
CREATE TABLE "ResourceBrowse" (
  "Id" int(11) NOT NULL AUTO_INCREMENT,
  "ResourceId" int(11) DEFAULT NULL,
  "ResourceType" int(11) DEFAULT NULL,
  "UserId" bigint(20) DEFAULT NULL,
  "CreateTime" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY ("Id")
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ResourceComments
-- ----------------------------
DROP TABLE IF EXISTS `ResourceComments`;
CREATE TABLE "ResourceComments" (
  "Id" int(11) NOT NULL AUTO_INCREMENT,
  "ResourceId" int(11) DEFAULT NULL,
  "ParentId" int(11) DEFAULT NULL,
  "Content" varchar(200) DEFAULT NULL,
  "UserId" int(11) DEFAULT NULL,
  "CreateTime" datetime DEFAULT NULL,
  "ReplyTo" varchar(45) DEFAULT NULL,
  "Score" int(11) DEFAULT NULL,
  PRIMARY KEY ("Id")
) ENGINE=InnoDB AUTO_INCREMENT=229 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ResourceDownload
-- ----------------------------
DROP TABLE IF EXISTS `ResourceDownload`;
CREATE TABLE "ResourceDownload" (
  "id" int(11) NOT NULL,
  "ResourceId" int(11) DEFAULT NULL,
  "UserId" int(11) DEFAULT NULL,
  "CreateTime" datetime DEFAULT NULL,
  "ResourceType" int(11) DEFAULT NULL,
  PRIMARY KEY ("id")
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for resources
-- ----------------------------
DROP TABLE IF EXISTS `resources`;
CREATE TABLE "resources" (
  "Id" int(11) NOT NULL AUTO_INCREMENT,
  "ParentId" int(11) DEFAULT NULL,
  "IsDocument" tinyint(1) DEFAULT NULL COMMENT '0文件，1文件夹 ',
  "name" varchar(200) DEFAULT NULL,
  "Types" int(11) DEFAULT NULL COMMENT '1视频 2文档 3电子书',
  "Icon" varchar(200) DEFAULT NULL,
  "Contents" mediumtext,
  "CreateAuthor" varchar(50) DEFAULT NULL,
  "UserId" int(11) DEFAULT NULL,
  "ResourcePath" varchar(250) DEFAULT NULL,
  "describes" varchar(250) DEFAULT NULL,
  "scores" float DEFAULT NULL,
  "ClickCount" int(11) DEFAULT NULL,
  "IsOpen" tinyint(1) DEFAULT NULL,
  "imgsrc" varchar(300) DEFAULT NULL,
  "status" tinyint(4) DEFAULT NULL,
  "CreateTIme" date DEFAULT NULL,
  PRIMARY KEY ("Id")
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for Resources
-- ----------------------------
DROP TABLE IF EXISTS `Resources`;
CREATE TABLE "Resources" (
  "ResourceId" int(11) NOT NULL AUTO_INCREMENT,
  "DirectoriesId" int(11) DEFAULT NULL,
  "ResourceName" varchar(100) DEFAULT NULL,
  "Contents" mediumtext,
  "CreateAuthor" varchar(50) DEFAULT NULL,
  "UserId" int(11) DEFAULT NULL,
  "ResourcePath" varchar(250) DEFAULT NULL,
  "ResourceType" int(11) DEFAULT NULL COMMENT '1视频 2文档 3电子书',
  "describes" varchar(250) DEFAULT NULL,
  "scores" float DEFAULT NULL,
  "ClickCount" int(11) DEFAULT NULL,
  "IsTextbook" tinyint(1) DEFAULT NULL,
  "IsOpen" tinyint(1) DEFAULT NULL,
  "imgsrc" varchar(300) DEFAULT NULL,
  "status" tinyint(4) DEFAULT NULL,
  "CreateTIme" date DEFAULT NULL,
  PRIMARY KEY ("ResourceId")
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for Role
-- ----------------------------
DROP TABLE IF EXISTS `Role`;
CREATE TABLE "Role" (
  "RoleId" int(11) NOT NULL AUTO_INCREMENT,
  "RoleName" varchar(200) DEFAULT NULL,
  "CreateTime" datetime DEFAULT NULL,
  "Describes" varchar(200) DEFAULT NULL,
  PRIMARY KEY ("RoleId")
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for Role_Privilege
-- ----------------------------
DROP TABLE IF EXISTS `Role_Privilege`;
CREATE TABLE "Role_Privilege" (
  "ID" int(11) NOT NULL,
  "RoleId" int(11) DEFAULT NULL,
  "PrivilegeId" int(11) DEFAULT NULL,
  "CreateUserId" int(11) DEFAULT NULL,
  "CreateTime" datetime DEFAULT NULL,
  PRIMARY KEY ("ID")
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for school
-- ----------------------------
DROP TABLE IF EXISTS `school`;
CREATE TABLE "school" (
  "id" int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id，自增',
  "schoolNo" varchar(15) NOT NULL COMMENT '学校编码',
  "legalCertificateCode" varchar(30) DEFAULT NULL COMMENT '法人证书号',
  "schoolCode" varchar(20) NOT NULL COMMENT '学校代码',
  "schoolName" varchar(60) NOT NULL COMMENT '学校名称',
  "postcode" varchar(6) DEFAULT NULL COMMENT '邮政编码',
  "headmasters" varchar(20) DEFAULT NULL COMMENT '校长',
  "schoolDate" date DEFAULT NULL COMMENT '建校时间',
  "partyPerson" varchar(20) DEFAULT NULL COMMENT '党委负责人',
  "showName" varchar(20) DEFAULT NULL COMMENT '显示名称',
  "celebrationDate" date DEFAULT NULL COMMENT '校庆日',
  "phone" varchar(30) DEFAULT NULL COMMENT '联系电话',
  "schoolType" varchar(20) DEFAULT NULL COMMENT '办校类型',
  "fax" varchar(30) DEFAULT NULL COMMENT '传真',
  "organizer" varchar(20) DEFAULT NULL COMMENT '举办者',
  "email" varchar(30) DEFAULT NULL COMMENT '电子邮箱',
  "organizationCode" varchar(50) DEFAULT NULL COMMENT '组织机构代码',
  "competentDepart" varchar(20) DEFAULT NULL COMMENT '主管部门',
  "representative" varchar(20) DEFAULT NULL COMMENT '法人代表',
  "province" varchar(10) DEFAULT NULL COMMENT '所在省',
  "provinceName" varchar(50) DEFAULT NULL,
  "city" varchar(20) DEFAULT NULL COMMENT '所在市',
  "cityName" varchar(50) DEFAULT NULL,
  "sanjak" varchar(50) DEFAULT NULL COMMENT '所属区(县)',
  "sanjakName" varchar(100) DEFAULT NULL,
  "street" varchar(400) DEFAULT NULL COMMENT '所属街道',
  "intro" varchar(2000) DEFAULT NULL COMMENT '学校介绍',
  "schoolNature" varchar(30) NOT NULL COMMENT '办学类型',
  "schoolWebsite" varchar(100) NOT NULL COMMENT '学校网址',
  "eleSchool" varchar(30) NOT NULL COMMENT '小学学制',
  "junSchool" varchar(30) NOT NULL COMMENT '初中学制',
  "higSchool" varchar(30) NOT NULL COMMENT '高中学制',
  PRIMARY KEY ("id")
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='学校信息表';

-- ----------------------------
-- Table structure for student_base
-- ----------------------------
DROP TABLE IF EXISTS `student_base`;
CREATE TABLE "student_base" (
  "id" int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id，自增',
  "state" varchar(20) DEFAULT NULL COMMENT '学生状态：(字典)在读、毕业、休学、转学（转出）、退学、开除、死亡、失踪、出国、其他离校',
  "schoolNo" varchar(20) DEFAULT NULL COMMENT '学校编码(学校编码表)',
  "studentNo" varchar(20) DEFAULT NULL COMMENT '学号，学号+学校编码不能重复',
  "typeId" varchar(10) DEFAULT NULL COMMENT '证件类型：居民身份证、香港特区护照/身份证、澳门特区护照/身份证、台湾居民来往大陆通行证、护照、境外永久居住证、其他',
  "idNumber" varchar(20) DEFAULT NULL,
  "name" varchar(20) DEFAULT NULL COMMENT '姓名',
  "sex" varchar(2) DEFAULT NULL COMMENT '性别',
  "birthday" date DEFAULT NULL COMMENT '出生日期',
  "birthplace" varchar(256) DEFAULT NULL COMMENT '出生地',
  "hometown" varchar(100) DEFAULT NULL COMMENT '籍贯：(字典)省市区，如湖南省长沙市岳麓区等，按国标来设计',
  "nation" varchar(20) DEFAULT NULL COMMENT '民族：(字典)汉族、满族、壮族、傣族等，按国标设计。',
  "nationality" varchar(100) DEFAULT NULL COMMENT '国籍/地区',
  "overseasChinese" varchar(20) DEFAULT NULL COMMENT '港澳台侨外：否、香港同胞、香港同胞亲属、澳门同胞、澳门同胞亲属、台湾同胞、台湾同胞亲属、华侨、华眷、归侨、,归侨子女、归国留学人员、非华裔中国人、外籍华裔人、外国人、其他',
  "politicalStatus" varchar(20) DEFAULT NULL COMMENT '政治面貌：(字典)中共党员、中共预备党员、共青团员、群众',
  "health" varchar(20) DEFAULT NULL COMMENT '健康状态：(字典)健康或良好、一般或较弱、有慢性病、有生理缺陷、残疾',
  "pic" varchar(256) DEFAULT NULL COMMENT '照片地址',
  "spell" varchar(20) DEFAULT NULL COMMENT '拼音',
  "formerName" varchar(20) DEFAULT NULL COMMENT '曾用名',
  "valid" varchar(20) DEFAULT NULL COMMENT '证件有效期',
  "anmelden" varchar(256) DEFAULT NULL COMMENT '户口所在地',
  "category" varchar(20) DEFAULT NULL COMMENT '户口性质：(字典)农业户口、非农业户口',
  "faith" varchar(20) DEFAULT NULL COMMENT '宗教信仰：(字典)无宗教信仰、佛教、喇嘛教、道教、天主教、基督教、东正教、伊斯兰教、其他',
  "bloodType" varchar(5) DEFAULT NULL COMMENT '血型：(字 典)A、B、AB、O',
  "specialty" varchar(265) DEFAULT NULL COMMENT '特长',
  "studentCode" varchar(20) DEFAULT NULL COMMENT '学籍号',
  "codeAuxiliary" varchar(20) DEFAULT NULL COMMENT '学籍辅号',
  "entranceDate" date DEFAULT NULL COMMENT '入学日期',
  "studentLevel" varchar(20) DEFAULT NULL COMMENT '学生类别：(字典)小学生、初中生、高中生',
  "entranceWay" varchar(20) DEFAULT NULL COMMENT '入学方式：(字典)就近入学、统一招生考试/普通入学、民族班、体育特招、艺术特招、外校转入、恢复入学资格、其他\n            ',
  "source" varchar(20) DEFAULT NULL COMMENT '学生来源：(字典)正常入学、借读、其他',
  "studyWay" varchar(20) DEFAULT NULL COMMENT '就读方式：(字典)走读、住校、借宿、其他',
  "addr" varchar(256) DEFAULT NULL COMMENT '现居地',
  "mailAddr" varchar(256) DEFAULT NULL COMMENT '通信地址',
  "homeAddr" varchar(256) DEFAULT NULL COMMENT '家庭地址',
  "phone" varchar(15) DEFAULT NULL COMMENT '联系电话',
  "zipCode" int(8) DEFAULT NULL COMMENT '邮政编码',
  "email" varchar(20) DEFAULT NULL COMMENT '邮箱',
  "description" varchar(512) DEFAULT NULL COMMENT '个性签名',
  "isOnlyChild" varchar(10) DEFAULT NULL COMMENT '是否独生子女：是、否',
  "isFloatHuman" varchar(10) DEFAULT NULL COMMENT '是否流动人口：是、否',
  "isPreschoolEdu" varchar(10) DEFAULT NULL COMMENT '是否受过学前教育：是、否',
  "isLeftoverChild" varchar(10) DEFAULT NULL COMMENT '是否留守儿童：是、否',
  "isAmountSought" varchar(10) DEFAULT NULL COMMENT '是否需求申请资助：是、否',
  "isSubsidy" varchar(10) DEFAULT NULL COMMENT '是否享受一补(补助寄宿生生活费)：是、否',
  "isOrphan" varchar(10) DEFAULT NULL COMMENT '是否孤儿：是、否',
  "isMartyrChild" varchar(10) DEFAULT NULL COMMENT '是否烈士或优抚子女：是、否',
  "isLocal" varchar(10) DEFAULT NULL COMMENT '是否本地生源：是、否',
  "isMigrantWorkerChild" varchar(10) DEFAULT NULL COMMENT '是否进城务工人员子女：是、否',
  "isGovBuyDeg" varchar(10) DEFAULT NULL COMMENT '是否由政府购买学位：是、否',
  "disabledType" varchar(20) DEFAULT NULL COMMENT '残疾人类型：(字典)无残疾、视力残疾、听力残疾、言语残疾、肢体残疾、智力残疾、精神残疾、多重残疾、其他残疾\n            ',
  "schoolDistance" double DEFAULT NULL COMMENT '上下学距离(公里)',
  "tripWay" varchar(20) DEFAULT NULL COMMENT '上下学方式：(典)步行、校车、自行车、公共交通、家长接送、其他\n            ',
  "isRideSchoolBus" varchar(10) DEFAULT NULL COMMENT '是否需要乘坐校车：是、否',
  "isLogicDel" varchar(10) DEFAULT NULL COMMENT '是否逻辑删除:是 /否 ',
  "account" varchar(30) DEFAULT NULL COMMENT '账号',
  "gradeName" varchar(30) DEFAULT NULL COMMENT '年纪名称',
  "className" varchar(30) DEFAULT NULL COMMENT '班级名称',
  "yearSemesterName" varchar(30) DEFAULT NULL COMMENT '学年学期名称',
  "gradeId" int(11) DEFAULT NULL COMMENT '年纪id',
  "classId" int(11) DEFAULT NULL COMMENT '班级id',
  "pas" varchar(30) DEFAULT '888888',
  "relationStatus" tinyint(4) DEFAULT NULL COMMENT '关系确认（0 否，1 是）',
  "province" varchar(30) DEFAULT NULL,
  "city" varchar(30) DEFAULT NULL,
  "area" varchar(30) DEFAULT NULL,
  "joinYear" varchar(45) DEFAULT NULL,
  PRIMARY KEY ("id")
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8 COMMENT='学生基本信息表';

-- ----------------------------
-- Table structure for teaching_resourse
-- ----------------------------
DROP TABLE IF EXISTS `teaching_resourse`;
CREATE TABLE "teaching_resourse" (
  "id" int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id，自增',
  "Res_ResourceId" int(11) DEFAULT NULL,
  "TeacherId" int(11) DEFAULT NULL COMMENT '教职工id',
  "ResourceId" varchar(100) DEFAULT NULL COMMENT '课程主题',
  "set_permissions" char(1) DEFAULT NULL COMMENT '设置开发权限(0所有人可见，1指定人员可见)',
  "create_man" varchar(32) DEFAULT NULL COMMENT '创建人',
  "create_time" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  "downloads_num" int(11) DEFAULT NULL,
  PRIMARY KEY ("id"),
  KEY "t_c_id" ("TeacherId")
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='教师资源表，文档文件资源等';

-- ----------------------------
-- Table structure for Teachingstaff
-- ----------------------------
DROP TABLE IF EXISTS `Teachingstaff`;
CREATE TABLE "Teachingstaff" (
  "TeacherId" int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  "schoolCode" varchar(20) DEFAULT NULL COMMENT '学校编码',
  "name" varchar(30) DEFAULT NULL COMMENT '姓名',
  "jobNum" varchar(20) DEFAULT NULL COMMENT '职工号',
  "highDegree" varchar(20) DEFAULT NULL COMMENT '数据字典   最高学历  学历  选项：博士后、博士、研究生、本科、大专、中专、高中、初中、小学',
  "phone" varchar(20) DEFAULT NULL COMMENT '手机号码',
  "tel" varchar(20) DEFAULT NULL COMMENT '联系方式',
  "email" varchar(30) DEFAULT NULL COMMENT '电子邮箱',
  "sex" char(2) DEFAULT NULL COMMENT '数据字典  性别：选项男、女、未知',
  "birthday" date DEFAULT NULL COMMENT '出生日期',
  "jobTitle" varchar(20) DEFAULT NULL COMMENT '数据字典  职称   选项：正高级教师、特级教师、高级教师、中教一级、中教二级、中教三级、初级教师',
  "politVisage" varchar(10) DEFAULT NULL COMMENT '数据字典  政治面貌  选项：党员、预备党员、共青团员、群众',
  "province" varchar(10) DEFAULT NULL COMMENT '所属省份',
  "city" varchar(10) DEFAULT NULL COMMENT '所属市',
  "area" varchar(50) DEFAULT NULL COMMENT '所属区(县)',
  "addr" varchar(400) DEFAULT NULL COMMENT '所属街道',
  "certiNum" varchar(30) DEFAULT NULL COMMENT '证件号',
  "certiType" varchar(20) DEFAULT NULL COMMENT '数据字典  证件类型  选项：居民身份证、士兵证、军官证、警官证、护照、其他',
  "prepareType" varchar(20) DEFAULT NULL COMMENT '数据字典  编制类型   选项：事业编、合同编、其他',
  "igeUrl" varchar(250) DEFAULT NULL COMMENT '照片',
  "formerName" varchar(30) DEFAULT NULL COMMENT '曾用名',
  "nativePlace" varchar(20) DEFAULT NULL COMMENT '籍贯',
  "nation" varchar(30) DEFAULT NULL COMMENT '民族',
  "maritalSta" varchar(10) DEFAULT NULL COMMENT '数据字典  婚姻状况   选项：未婚、已婚、离婚、丧偶、其他',
  "healthSta" varchar(20) DEFAULT NULL COMMENT '数据字典  健康状态  选项：健康、一般、有慢性病、有生理缺陷、有严重疾病、其他',
  "faith" varchar(20) DEFAULT NULL COMMENT '信仰宗教',
  "nationality" varchar(30) DEFAULT NULL COMMENT '国籍',
  "bloodType" varchar(10) DEFAULT NULL COMMENT '数据字典  血型  选项：A、B、AB、O',
  "jobState" varchar(10) DEFAULT NULL COMMENT '在职状态',
  "teacherIntroduction" varchar(400) DEFAULT NULL COMMENT '教师简介',
  "teachingSkills" varchar(400) DEFAULT NULL COMMENT '教学特长',
  "researchResults" varchar(400) DEFAULT NULL COMMENT '研究成果',
  "specialist" char(2) DEFAULT NULL,
  "major" varchar(400) DEFAULT NULL,
  "top_time" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  "courseId" int(1) DEFAULT NULL,
  "top" char(2) DEFAULT NULL,
  "idNumber" varchar(50) DEFAULT NULL,
  PRIMARY KEY ("TeacherId"),
  KEY "t_cb_am_staff_ibfk_1" ("schoolCode")
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='教职工信息表';

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE "user" (
  "UserId" int(10) unsigned zerofill NOT NULL AUTO_INCREMENT,
  "UserName" varchar(80) DEFAULT NULL,
  "Password" varchar(80) DEFAULT NULL,
  "Sex" int(11) DEFAULT NULL,
  "Phone" varchar(15) DEFAULT NULL,
  "Email" varchar(80) DEFAULT NULL,
  "LoginTime" datetime DEFAULT NULL,
  "LoginCount" int(11) DEFAULT NULL,
  "UserType" int(11) DEFAULT NULL,
  "FieldTargetId" int(11) DEFAULT NULL,
  "NickName" varchar(80) DEFAULT NULL,
  "CreateTime" timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  "state" int(11) DEFAULT NULL COMMENT '0 未验证，1通过 2验证失败  3禁用 ',
  "imgsrc" varchar(300) DEFAULT NULL,
  "name" varchar(45) DEFAULT NULL,
  PRIMARY KEY ("UserId")
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for User_Role
-- ----------------------------
DROP TABLE IF EXISTS `User_Role`;
CREATE TABLE "User_Role" (
  "ID" int(11) NOT NULL,
  "UserId" int(11) DEFAULT NULL,
  "RoleId" int(11) DEFAULT NULL,
  "CreateTime" datetime DEFAULT NULL,
  "CreateUserId" int(11) DEFAULT NULL,
  PRIMARY KEY ("ID"),
  UNIQUE KEY "UserId" ("UserId")
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for year_semester
-- ----------------------------
DROP TABLE IF EXISTS `year_semester`;
CREATE TABLE "year_semester" (
  "id" int(11) NOT NULL AUTO_INCREMENT COMMENT '主键，自增',
  "schoolNo" varchar(20) DEFAULT NULL COMMENT '学校编码',
  "schoolYear" varchar(16) DEFAULT NULL COMMENT '学年(来自字典表，由于数据不会修改，这里存字典表的值)',
  "semester" varchar(8) DEFAULT NULL COMMENT '学期(来自字典表，学期的数字字段，秋季学期，春季学期等等，由于数据不会修改，这里可存字典表的值)，一个学年学期不能重复',
  "ordinal" int(1) DEFAULT NULL COMMENT '学期序数，秋季学期是1，春季学期是2，夏季学期是3，其他序数是4',
  "stdName" varchar(20) DEFAULT NULL COMMENT '显示名称',
  "beginDate" date DEFAULT NULL COMMENT '开始时间',
  "endDate" date DEFAULT NULL COMMENT '结束时间',
  "isCurrent" varchar(2) DEFAULT NULL COMMENT '是否当前学年学期标志，0表示否，1表示是当前学年学期。一个学校里面只有一个当前学年学期',
  "seq" int(11) DEFAULT NULL,
  PRIMARY KEY ("id")
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='学年学期';

-- ----------------------------
-- Function structure for getChildList
-- ----------------------------
DROP FUNCTION IF EXISTS `getChildList`;
DELIMITER ;;
CREATE DEFINER="sa"@"%" FUNCTION "getChildList"(rootid INT) RETURNS varchar(8000) CHARSET utf8
begin
declare sTemp VARCHAR(4000);
declare sTempChd VARCHAR(4000);

SET sTemp = '$';
SET sTempChd = cast(rootid as char);

WHILE sTempChd is not NULL DO
SET sTemp = CONCAT(sTemp,',',sTempChd);
SELECT group_concat(DirectoriesId) INTO sTempChd FROM ChapterDirectories where FIND_IN_SET(ParentId,sTempChd)>0;
END WHILE;
return sTemp;
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for getChildLst
-- ----------------------------
DROP FUNCTION IF EXISTS `getChildLst`;
DELIMITER ;;
CREATE DEFINER="sa"@"%" FUNCTION "getChildLst"(rootId INT) RETURNS varchar(1000) CHARSET utf8
BEGIN
   DECLARE sTemp VARCHAR(1000);
   DECLARE sTempChd VARCHAR(1000);
  
    SET sTemp = '$';
    SET sTempChd =cast(rootId as CHAR);
  
   WHILE sTempChd is not null DO
    SET sTemp = concat(sTemp,',',sTempChd);
    SELECT group_concat(DirectoriesId) INTO sTempChd FROM Resources  where FIND_IN_SET(ParentId,sTempChd)>0;
   END WHILE;
    RETURN sTemp;
   END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for GetChildNodeList
-- ----------------------------
DROP FUNCTION IF EXISTS `GetChildNodeList`;
DELIMITER ;;
CREATE DEFINER="sa"@"%" FUNCTION "GetChildNodeList"(rootid INT) RETURNS varchar(8000) CHARSET utf8
BEGIN
DECLARE sTemp VARCHAR(8000);
DECLARE sTempChd VARCHAR(8000);

SET sTemp = '$';
SET sTempChd = cast(rootid as char);

WHILE sTempChd is not NULL DO
SET sTemp = CONCAT(sTemp,',',sTempChd);
SELECT group_concat(Id) INTO sTempChd FROM resources where FIND_IN_SET(parentId,sTempChd)>0;
END WHILE;
return sTemp;
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for GetNLevelChildNode
-- ----------------------------
DROP FUNCTION IF EXISTS `GetNLevelChildNode`;
DELIMITER ;;
CREATE DEFINER="sa"@"%" FUNCTION "GetNLevelChildNode"(rootid INT,n INT) RETURNS varchar(8000) CHARSET utf8
begin
declare sTemp VARCHAR(4000);
declare sTempChd VARCHAR(4000);
declare i int;
set i=0;
SET sTemp = '$';
SET sTempChd = cast(rootid as char);
 
WHILE sTempChd is not NULL DO
SET sTemp = CONCAT(sTemp,',',sTempChd);
if i<n then
 
SELECT group_concat(Id) INTO sTempChd FROM resources where FIND_IN_SET(ParentId,sTempChd)>0;
ELSE  
set sTempChd=NULL;
 
end if;
set i=i+1;
  
END WHILE;
return sTemp;
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for getTwoChildList
-- ----------------------------
DROP FUNCTION IF EXISTS `getTwoChildList`;
DELIMITER ;;
CREATE DEFINER="sa"@"%" FUNCTION "getTwoChildList"(rootid INT) RETURNS varchar(8000) CHARSET utf8
begin
declare sTemp VARCHAR(4000);
declare sTempChd VARCHAR(4000);
declare i int;
set i=0;
SET sTemp = '$';
SET sTempChd = cast(rootid as char);
 
 
WHILE sTempChd is not NULL DO
SET sTemp = CONCAT(sTemp,',',sTempChd);
if i<2 then
 
SELECT group_concat(DirectoriesId) INTO sTempChd FROM ChapterDirectories where FIND_IN_SET(ParentId,sTempChd)>0;
ELSE  
set sTempChd=NULL;
 
end if;
set i=i+1;
  
END WHILE;
return sTemp;
END
;;
DELIMITER ;
