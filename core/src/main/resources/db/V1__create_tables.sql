create table if not exists gift_certificate
(
    id               int auto_increment
        primary key,
    name             varchar(45)              null,
    description      varchar(45)              null,
    price            double unsigned zerofill not null,
    duration         int                      null,
    create_date      datetime                 null,
    last_update_date datetime                 null
);

create table if not exists tag
(
    id   int auto_increment
        primary key,
    name varchar(45) null
);

create table if not exists gift_certificate_has_tag
(
    gift_certificate_id int not null,
    tag_id              int not null,
    primary key (gift_certificate_id, tag_id),
    constraint fk_gift_certificate_has_tag_gift_certificate
        foreign key (gift_certificate_id) references gift_certificate (id)
            on update cascade on delete cascade,
    constraint fk_gift_certificate_has_tag_tag1
        foreign key (tag_id) references tag (id)
            on update cascade on delete cascade
);

create index fk_gift_certificate_has_tag_gift_certificate_idx
    on gift_certificate_has_tag (gift_certificate_id);

create index fk_gift_certificate_has_tag_tag1_idx
    on gift_certificate_has_tag (tag_id);

create table if not exists user
(
    id       int auto_increment
        primary key,
    name     varchar(255) not null,
    username varchar(255) not null,
    password varchar(255) not null,
    role     varchar(255) not null,
    constraint username
        unique (username)
);

create table if not exists orders
(
    id                  int auto_increment
        primary key,
    cost                double unsigned zerofill not null,
    purchase_time       datetime                 null,
    gift_certificate_id int                      not null,
    user_id             int                      not null,
    constraint fk_order_gift_certificate1
        foreign key (gift_certificate_id) references gift_certificate (id),
    constraint fk_order_user1
        foreign key (user_id) references user (id)
);

create index fk_order_gift_certificate1_idx
    on orders (gift_certificate_id);

create index fk_order_user1_idx
    on orders (user_id);