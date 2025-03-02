--
-- NR_Test
--
insert into NR_Test(priorite, description, reference, titre, actif)
values (1, 'Description P1/Test1', 'SC01', 'Titre1', true);
insert into NR_Test(priorite, description, reference, titre, actif)
values (1, 'Description P1/Test2', 'SC02', 'Titre2', true);
insert into NR_Test(priorite, description, reference, titre, actif)
values (1, 'Description P1/Test3', 'SC03', 'Titre3', false);
--
insert into NR_Test(priorite, description, reference, titre, actif)
values (2, 'Description P2/Test1', 'SC04', 'Titre4', true);
insert into NR_Test(priorite, description, reference, titre, actif)
values (2, 'Description P2/Test2', 'SC05', 'Titre5', false);
insert into NR_Test(priorite, description, reference, titre, actif)
values (2, 'Description P2/Test3', 'SC06', 'Titre6', false);
--
insert into NR_Test(priorite, description, reference, titre, actif)
values (3, 'Description P3/Test1', 'SC07', 'Titre7', true);
insert into NR_Test(priorite, description, reference, titre, actif)
values (3, 'Description P3/Test2', 'SC08', 'Titre8', false);
insert into NR_Test(priorite, description, reference, titre, actif)
values (3, 'Description P3/Test3', 'SC09', 'Titre9', false);
--
-- NR_Execution
--
insert into NR_Execution(name, description, status)
values ('Exec1', 'Desc1', 'OK');
--
insert into Nr_Execution_Param(nr_execution_id, nr_Test_Id, reference)
values (1, 1, 'ABSI1234567890');
--
insert into Nr_Execution_Param(nr_execution_id, nr_Test_Id, reference)
values (1, 2, 'ABST1234567890');
--
--
insert into NR_Execution(name, description, status)
values ('Exec2', 'Desc2', 'KO');
--
insert into Nr_Execution_Param(nr_execution_id, nr_Test_Id, reference)
values (2, 3, 'ABSI9876543210');
--
insert into Nr_Execution_Param(nr_execution_id, nr_Test_Id, reference)
values (2, 4, 'ABST9876543210');
