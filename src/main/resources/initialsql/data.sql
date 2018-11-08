/******主要用于超级管理员相关的账号、后台用户、角色、系统管理权限的数据预插入******/

/*1、sysuser（角色权限必须表，账号表）,创建超级管理员账号
账户：admin 密码：admin123456，对应的BCrypt加密后的字符串：$2a$10$wMXfjHv18MgZnBLTj2UypO6RBvsvl6GCkydpdXNiY5CN9xaHqnXPC
*/
INSERT INTO sysuser(uuid,password,projectname,username) select '4028468166e821400166e824c7bd0001','$2a$10$wMXfjHv18MgZnBLTj2UypO6RBvsvl6GCkydpdXNiY5CN9xaHqnXPC','sparrow','admin' from dual where not EXISTS (select 1 from sysuser where uuid='4028468166e821400166e824c7bd0001');

/*2、sysrole（角色权限必须表，角色表），创建超级管理员这一角色*/
INSERT INTO sysrole(uuid,name) select '4028468165d287df0165d290bcf10003','超级管理员' from dual  where not EXISTS(select 1 from sysrole where uuid='4028468165d287df0165d290bcf10003');

/*3、sysuser_sys_roles（角色权限必须表，账号角色关联表），创建超级管理员的账号和角色关联关系*/
INSERT INTO sysuser_sys_roles(sys_user_uuid,sys_roles_uuid) select '4028468166e821400166e824c7bd0001','4028468165d287df0165d290bcf10003' from dual  where not EXISTS(select * from sysuser_sys_roles where sys_user_uuid='4028468166e821400166e824c7bd0001');

/*4、sysauth（角色权限必须表，权限表），创建系统管理相应的全部权限*/
INSERT INTO sysauth(uuid,name,id,pid,treename) select '4028808b6459dad6016459db8e460001','系统管理',1,0,'系统管理' from dual where not EXISTS(select * from sysauth where uuid='4028808b6459dad6016459db8e460001');
INSERT INTO sysauth(uuid,name,id,pid,treename) select '4028808b6459dad6016459dda5760003','系统管理_角色权限',11,1,'角色权限' from dual where not EXISTS(select * from sysauth where uuid='4028808b6459dad6016459dda5760003');
INSERT INTO sysauth(uuid,name,id,pid,treename) select '4028808b6459dad6016459ddffce0004','系统管理_Druid数据分析',12,1,'Druid数据分析' from dual where not EXISTS(select * from sysauth where uuid='4028808b6459dad6016459ddffce0004');
INSERT INTO sysauth(uuid,name,id,pid,treename) select '4028468165cbac0d0165cbacc6280000','系统管理_系统日志',13,1,'系统日志' from dual where not EXISTS(select * from sysauth where uuid='4028468165cbac0d0165cbacc6280000');
INSERT INTO sysauth(uuid,name,id,pid,treename) select '4028808b6459dad6016459de48550005','系统管理_角色权限_新增角色',111,11,'新增角色' from dual where not EXISTS(select * from sysauth where uuid='4028808b6459dad6016459de48550005');
INSERT INTO sysauth(uuid,name,id,pid,treename) select '4028808b6459dad6016459de7abc0006','系统管理_角色权限_删除角色',112,11,'删除角色' from dual where not EXISTS(select * from sysauth where uuid='4028808b6459dad6016459de7abc0006');
INSERT INTO sysauth(uuid,name,id,pid,treename) select '4028808b6459dad6016459dec5e40007','系统管理_角色权限_管理一级功能',113,11,'管理一级功能' from dual where not EXISTS(select * from sysauth where uuid='4028808b6459dad6016459dec5e40007');
INSERT INTO sysauth(uuid,name,id,pid,treename) select '4028808b6459dad6016459deef580008','系统管理_角色权限_保存设置',114,11,'保存设置' from dual where not EXISTS(select * from sysauth where uuid='4028808b6459dad6016459deef580008');
INSERT INTO sysauth(uuid,name,id,pid,treename) select '4028808b6459e833016459e9c8430000','系统管理_角色权限_新增权限',115,11,'新增权限' from dual where not EXISTS(select * from sysauth where uuid='4028808b6459e833016459e9c8430000');
INSERT INTO sysauth(uuid,name,id,pid,treename) select '4028808b6459e833016459ea06ce0001','系统管理_角色权限_删除权限',116,11,'删除权限' from dual where not EXISTS(select * from sysauth where uuid='4028808b6459e833016459ea06ce0001');

/*5、userinfo（后台用户表），创建超级管理员用户*/
INSERT INTO userinfo(uuid,birthdate,depname,depuuid,email,homeaddress,mobile,nativeplace,password,position,realname,roleuuid,sextype,status,upload,username,sysuser_id) select '4028468166e821400166e824c7be0002','1976-06-28','北京信息技术研发部','','393829394@qq.com','北京市海淀区学清路8号科技财富中心B座1102','18600955349','北京.海淀','$2a$10$wMXfjHv18MgZnBLTj2UypO6RBvsvl6GCkydpdXNiY5CN9xaHqnXPC','系统管理员','江成军','4028468165d287df0165d290bcf10003','男','启用','无','admin','4028468166e821400166e824c7bd0001' from dual where not EXISTS(select * from userinfo where uuid='4028468166e821400166e824c7be0002');
