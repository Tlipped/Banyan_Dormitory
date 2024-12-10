create table visitor
(
    id         int          null,
    name       varchar(255) null,
    phone_num  varchar(255) null,
    visit_time datetime     null,
    content    varchar(255) null,
    userid     varchar(18)  null
);

INSERT INTO banyan_dormitory.visitor (id, name, phone_num, visit_time, content, userid) VALUES (1, '张三', '13433333333', '2023-10-29 17:34:52', '探访孩子', '123456789101112131');
INSERT INTO banyan_dormitory.visitor (id, name, phone_num, visit_time, content, userid) VALUES (2, '李四', '15722222222', '2023-11-01 17:08:06', '运送快递', '123456789101112132');
INSERT INTO banyan_dormitory.visitor (id, name, phone_num, visit_time, content, userid) VALUES (3, '啊啊', '13255555555', '2023-12-25 16:41:21', '运送食品', '123456789101112133');
