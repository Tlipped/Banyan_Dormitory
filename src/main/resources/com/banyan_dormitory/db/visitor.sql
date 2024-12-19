create table visitor
(
    id           int auto_increment
        primary key,
    name         varchar(255) null,
    visitor_id   varchar(18)  null,
    phone_number varchar(11)  null,
    date         date         null,
    time         time         null,
    reason       varchar(255) null
);