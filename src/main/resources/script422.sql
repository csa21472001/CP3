create table car(
	id serial primary key,
	make varchar(30),
	model varchar(30),
	price numeric(10,2)
	);
create table car_owner(
    id serial primary key,
    name varchar(50) not null,
    age integer not null,
    has_license boolean default false,
    car_id serial references  car(id)
)