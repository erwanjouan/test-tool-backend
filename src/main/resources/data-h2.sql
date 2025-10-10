--
-- task_template
--
insert into task_template(id, title, content, category)
values ('1', 'list files', 'ls', 'system');

--
-- Execution
--
insert into Execution(name, description, start_time, end_time, status)
values ('Exec1', 'Desc1', '2022-12-31 23.59.59', '2022-12-31 23.59.59', 'COMPLETED');

insert into Execution(name, description, start_time, end_time, status)
values ('Exec2', 'Desc2', '2022-12-31 23.59.59', '2022-12-31 23.59.59', 'COMPLETED');

insert into Execution(name, description, start_time, end_time, status)
values ('Exec', 'Desc1', '2022-12-31 23.59.59', '2022-12-31 23.59.59', 'COMPLETED');

insert into Execution(name, description, start_time, end_time, status)
values ('Exec4', 'Desc3', '2022-12-31 23.59.59', '2022-12-31 23.59.59', 'COMPLETED');

insert into Execution(name, description, start_time, end_time, status)
values ('Exec5', 'Desc1', '2022-12-31 23.59.59', '2022-12-31 23.59.59', 'COMPLETED');

insert into Execution(name, description, start_time, end_time, status)
values ('Exec6', 'Desc2', '2022-12-31 23.59.59', '2022-12-31 23.59.59', 'COMPLETED');

insert into Execution(name, description, start_time, end_time, status)
values ('Exec7', 'Desc1', '2022-12-31 23.59.59', '2022-12-31 23.59.59', 'COMPLETED');

insert into Execution(name, description, start_time, end_time, status)
values ('Exec8', 'Desc3', '2022-12-31 23.59.59', '2022-12-31 23.59.59', 'COMPLETED');

insert into Execution(name, description, start_time, end_time, status)
values ('Exec9', 'Desc1', '2022-12-31 23.59.59', '2022-12-31 23.59.59', 'COMPLETED');

insert into Execution(name, description, start_time, end_time, status)
values ('Exec10', 'Desc2', '2022-12-31 23.59.59', '2022-12-31 23.59.59', 'COMPLETED');

insert into Execution(name, description, start_time, end_time, status)
values ('Exec11', 'Desc1', '2022-12-31 23.59.59', '2022-12-31 23.59.59', 'COMPLETED');

insert into Execution(name, description, start_time, end_time, status)
values ('Exec12', 'Desc3', '2022-12-31 23.59.59', '2022-12-31 23.59.59', 'COMPLETED');
--
-- Task
--
insert into Task(execution_id, task_template_id)
values (1, '1');

--
-- Param
--
insert into Param(task_id, content)
values (1, '/tmp');
--
-- insert into Param(task_id, value)
-- values (2, 'ABST1234567890');
--