create table user
(
    id           int                      not null
        primary key,
    password     varchar(255)             null,
    school       varchar(255)             null,
    score        varchar(255)             null,
    room         varchar(255)             null,
    bed          varchar(255)             null,
    name         varchar(255)             null,
    user_id      varchar(18) default '无' null,
    phone_number varchar(11) default '无' null
);
