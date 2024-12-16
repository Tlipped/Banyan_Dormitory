create table account_password
(
    account  varchar(100) not null,
    password varchar(100) not null,
    style    varchar(10)  not null
);

INSERT INTO banyan_dormitory.account_password (account, password, style) VALUES ('12345', '123456', '管理员');
INSERT INTO banyan_dormitory.account_password (account, password, style) VALUES ('123', '123456', '学生');
