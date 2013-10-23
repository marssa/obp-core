create table LogEntry (
  id bigint primary key auto_increment ,
  timestamp bigint not null ,
  level tinyint not null ,
  origin varchar(10) not null ,
  message varchar(255) not null
);