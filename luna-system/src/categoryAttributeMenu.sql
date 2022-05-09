-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('产品属性分类', '1074', '1', 'categoryAttribute', 'product/categoryAttribute/index', 1, 0, 'C', '0', '0', 'product:categoryAttribute:list', '#', 'admin', sysdate(), '', null, '产品属性分类菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('产品属性分类查询', @parentId, '1',  '#', '', 1, 0, 'F', '0', '0', 'product:categoryAttribute:query',        '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('产品属性分类新增', @parentId, '2',  '#', '', 1, 0, 'F', '0', '0', 'product:categoryAttribute:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('产品属性分类修改', @parentId, '3',  '#', '', 1, 0, 'F', '0', '0', 'product:categoryAttribute:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('产品属性分类删除', @parentId, '4',  '#', '', 1, 0, 'F', '0', '0', 'product:categoryAttribute:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('产品属性分类导出', @parentId, '5',  '#', '', 1, 0, 'F', '0', '0', 'product:categoryAttribute:export',       '#', 'admin', sysdate(), '', null, '');