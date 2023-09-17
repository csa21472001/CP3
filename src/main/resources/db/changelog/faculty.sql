--liquibase formatted sql

--changeset csa21472001:SPG-2
create index name_color_index on faculty(name,color)