create table data_jpa.goods_info
(
    id          bigint       not null
        primary key,
    goods_name  varchar(255) null,
    create_by   varchar(255) null,
    update_by   varchar(255) null,
    create_time timestamp    null,
    update_time timestamp    null
);

