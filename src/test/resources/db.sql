drop table if exists visitlog;
create table visitlog(
	id varchar(50)  not null,
	ip varchar(20) ,
	browser varchar(200) ,
	os varchar(200) ,
	time int,
	primary key (id)
) engine=innodb;