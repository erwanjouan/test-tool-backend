--
-- task_template
--
insert into task_template(id, title, content, category)
values ('1', 'sleep', 'sleep 5', 'system');

--
-- Execution
--
insert into Execution(name, description, start_time, end_time, status)
values ('Exec1', 'Desc1', '2022-12-31 23.59.59', '2022-12-31 23.59.59', 'COMPLETED');
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