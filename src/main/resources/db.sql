
-- user_account table
create table user_account
(
    user_id    bigserial    not null
        constraint user_accounts_pk
            primary key,
    password   varchar(200) not null,
    name       varchar(50)  not null,
    email      varchar(50)  not null
        constraint ukhl02wv5hym99ys465woijmfib
            unique,
    created_at varchar      not null,
    updated_at varchar      not null
);

alter table user_account
    owner to postgres;

create unique index user_accounts_user_id_uindex
    on user_account (user_id);



CREATE TABLE user_roles (
  user_id bigint NOT NULL,
  role_id bigint NOT NULL,
  PRIMARY KEY (user_id,role_id),
  CONSTRAINT fk_user_role_role_id FOREIGN KEY (role_id) REFERENCES roles (id),
  CONSTRAINT fk_user_role_user_id FOREIGN KEY (user_id) REFERENCES user_account (user_id)
);

create sequence user_account_user_id_seq;

alter sequence user_account_user_id_seq owner to postgres;



-- user_roles table
create table user_roles
(
    user_id bigint not null
        constraint fk10xshnvsvc4prd1ldgoh0v8oa
            references user_account,
    role_id bigint not null
        constraint fk_user_role_role_id
            references roles,
    constraint user_roles_pkey
        primary key (user_id, role_id)
);

alter table user_roles
    owner to postgres;

-- roles table
create table roles
(
    id   bigserial   not null
        constraint roles_pk
            primary key,
    name varchar(60) not null
        constraint uk_nb4h0p6txrmfc0xbrd1kglp9t
            unique
);

comment on table roles is '권한';

alter table roles
    owner to postgres;

create unique index roles_name_uindex
    on roles (name);

create sequence roles_id_seq;

alter sequence roles_id_seq owner to postgres;

-- coupons table
create table coupons
(
    coupon_id                 bigserial             not null
        constraint coupon_pk
            primary key
        constraint fkb25rge7aqhbrp3bs69xmhsrsq
            references coupons,
    coupon_code               varchar(100)          not null,
    created_at                timestamp             not null,
    updated_at                timestamp             not null,
    coupon_hash               bigint                not null,
    coupon_utilization_status boolean default false not null,
    coupon_expiration_date    varchar               not null
);

comment on table coupons is '쿠폰 테이블';

alter table coupons
    owner to postgres;

create unique index coupon_coupon_id_uindex
    on coupons (coupon_id);

create unique index coupons_coupon_hash_uindex
    on coupons (coupon_hash);

create unique index coupons_coupon_number_uindex
    on coupons (coupon_code);

create sequence coupon_coupon_id_seq;

alter sequence coupon_coupon_id_seq owner to postgres;


-- user_coupons table
create table user_coupons
(
    user_coupon_id     bigserial             not null
        constraint user_coupons_pk
            primary key,
    user_id            bigint                not null,
    utilization_status boolean default false not null,
    expiration_date    varchar               not null,
    coupon_id          bigint
        constraint user_coupons_coupons_coupon_id_fk
            references coupons,
    created_at         timestamp             not null,
    updated_at         timestamp             not null
);

comment on table user_coupons is '유저 쿠폰 테이블';

alter table user_coupons
    owner to postgres;

create unique index user_coupons_user_coupon_id_uindex
    on user_coupons (user_coupon_id);

create unique index user_coupons_user_coupon_id_uindex_2
    on user_coupons (user_coupon_id);

create sequence user_coupons_user_coupon_id_seq;

alter sequence user_coupons_user_coupon_id_seq owner to postgres;



-- coupon_message table
create table coupon_message
(
    coupon_message_id      bigserial             not null
        constraint coupon_message_pk
            primary key,
    coupon_message_checked boolean default false not null,
    created_at             timestamp             not null,
    updated_at             timestamp             not null,
    user_id                bigint                not null,
    coupon_id              bigint                not null
);

comment on table coupon_message is '쿠폰 알람 메세지 테이블';

alter table coupon_message
    owner to postgres;

create unique index coupon_message_coupon_message_id_uindex
    on coupon_message (coupon_message_id);

create sequence coupon_message_coupon_message_id_seq;

alter sequence coupon_message_coupon_message_id_seq owner to postgres;

