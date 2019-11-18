1. Install postgres and create the following table , please refer the application.properties file for the table name and other credentials

ddl script for create table 
-- Table: public.hierarchy

-- DROP TABLE public.hierarchy;

CREATE TABLE public.hierarchy
(
    id character varying(255) COLLATE pg_catalog."default" NOT NULL,
    height integer NOT NULL,
    node_id character varying(255) COLLATE pg_catalog."default",
    parent_id character varying(255) COLLATE pg_catalog."default",
    root_id character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT hierarchy_pkey PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.hierarchy
    OWNER to siddharthyadav;

    
select * from hierarchy;

-- This is what we are building initially.

    
    root
	/  \
   1    2
  / \   /\
 3	 4 5  6
          /  \
/        8   9
7


insert into hierarchy values('100',0,'root','','root');
insert into hierarchy values('101',1,'1','root','root');
insert into hierarchy values('102',1,'2','root','root');
insert into hierarchy values('103',2,'3','1','root');
insert into hierarchy values('104',2,'4','1','root');
insert into hierarchy values('105',3,'7','3','root');
insert into hierarchy values('106',2,'5','2','root');
insert into hierarchy values('107',2,'6','2','root');
insert into hierarchy values('108',3,'8','6','root');
insert into hierarchy values('109',3,'9','6','root');
  