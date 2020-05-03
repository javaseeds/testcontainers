CREATE table funk (
    id INTEGER primary key auto_increment,
    name varchar(200),
    tstamp timestamp
);

INSERT INTO funk (name, tstamp) 
values 
('bob', current_timestamp),
('sam', null);
