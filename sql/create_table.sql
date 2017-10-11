CREATE TABLE hck_customer (
   cst_id NUMERIC(5),
   cst_name VARCHAR(100),
   cst_email VARCHAR(100),
   PRIMARY KEY( cst_id )
);


insert into hck_customer (cst_id, cst_name, cst_email) values (1, 'wesley menezes', 'wesley.menezes@ericsson.com');

select * from hck_customer;

drop table hck_customer;