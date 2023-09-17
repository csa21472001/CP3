--liquibase formatted sql

--changeset csa21472001:SPG-1
create index student_name_index on student(name)