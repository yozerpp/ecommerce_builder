create type payment_type as enum('credit_card','paypal','cash');
create type order_status as enum('on payment', 'awaiting shipping', 'shipped', 'delivered', 'cancelled');
create table carts(
                      id          integer                 not null,
                      total       integer                 default 0,
                      ordered     bool                    default false not null,
                      inserted_at timestamptz not null default now(),
                      updated_at  timestamptz,
                      constraint pk_carts primary key (id)
);
create table sessions (
                          id          varchar(255)             not null,
                          data        jsonb                             ,
                          inserted_at timestamptz not null default now(),
                          updated_at  timestamptz,
                          constraint pk_sessions primary key (id)
)   ;
create table users (
                       id          serial                   not null     ,
                       email       varchar(255)             not null     ,
                       phone_number bigint                   not null    ,
                       first_name  varchar(255)             not null     ,
                       last_name   varchar(255)             not null     ,
                       active_session_id  varchar(255)           not null     ,
                       active_cart_id     integer           not null     ,
                       inserted_at timestamptz not null default now()    ,
                       updated_at  timestamptz     ,
                       constraint pk_users primary key (id),
                        constraint fk_users_cart foreign key (active_cart_id) references carts(id),
                        constraint fk_users_session foreign key(active_session_id) references sessions(id)
)   ;
create table sellers(
                        id          int                     not null,
                        id_verified bool                    not null default false,
                        address     address                 not null,
                        insertad_at timestamptz not null default now()    ,
                        updated_at  timestamptz                           ,
                        constraint fk_users_cart
                        constraint pk_sellers primary key (id),
                        constraint fk_sellers_users foreign key(id) references users(id),
                   )
create table roles (
                       id          serial                   not null,
                       name        varchar(255)             not null,
                       inserted_at timestamptz not null default now(),
                       updated_at  timestamptz,
                       constraint pk_roles primary key (id)
)   ;
create table user_roles (
                            user_id     integer                  not null,
                            role_id     integer                  not null,
                            inserted_at timestamptz not null default now(),
                            updated_at  timestamptz,
                            constraint pk_user_roles primary key (user_id,role_id),
                            constraint fk_user_roles_users foreign key (user_id) references users(id),
                            constraint fk_user_roles_roles foreign key (role_id) references roles(id)
)   ;


create table categories (
                            id          serial                   not null,
                            name        varchar(255)             not null,
                            parent_id   integer                          ,
                            specifications jsonb                 not null,
                            inserted_at timestamptz not null default now(),
                            updated_at  timestamptz,
                            constraint pk_categories primary key (id),
                            constraint fk_categories_categories foreign key (parent_id) references categories(id)
            );
create table products (
                          id                serial                   not null      ,
                          name              varchar(255)             not null      ,
                          taxable           bool                      default false,
                          specifications    jsonb                     not null     ,
                          inserted_at       timestamptz not null default now()     ,
                          updated_at        timestamptz                            ,
                          constraint pk_products primary key (id)
);

create table product_seller (
                                 id          serial                  not null,
                                 product_id  integer                  not null,
                                 seller_id   integer                  not null,
                                 stock             int               not null,
                                 description       text                      ,
                                 regular_price     numeric           not null,
                                 discount          numeric           default 0,
                                 discounted_price numeric generated always as (discount /100  * regular_price) stored,
                                 in_stock          bool               not null,
                                 inserted_at timestamptz not null default now(),
                                 updated_at  timestamptz,
                                 constraint pk_sellers primary key (id),
                                 constraint fk_sellers_products foreign key (product_id) references products(id),
                                 constraint fk_sellers_sellers foreign key (seller_id) references users(id)
);
create table tags (
                      id          serial                   not null,
                      name        varchar(255)             not null,
                      inserted_at timestamptz not null default now(),
                      updated_at  timestamptz,
                      constraint pk_tags primary key (id)
);
create table payment(
    id serial not null,
    completed bool default false,
    successful bool default false,
    type payment_type not null,
    constraint pk_payment primary key (id)
);
create table card_payment (
                                 id                 int                   not null,
                                 code               varchar(255)                     ,
                                 transdate          timestamptz         ,
                                 processor          varchar(255)             not null,
                                 processor_trans_id varchar(255)             not null,
                                 amount             numeric                  not null,
                                 cc_num             varchar(255)                     ,
                                 cc_type            varchar(255)                     ,
                                 response           text                             ,
                                 inserted_at        timestamptz not null default now(),
                                 updated_at         timestamptz not null,
                                 constraint pk_cc_transactions primary key (id),
                                 constraint fk_transactions_orders foreign key (id) references payment(id)
)   ;
create table orders (
                              id          serial                   not null,
                              total       numeric                  not null,
                              payment_id  int                      not null,
                              status      order_status      not null default 'on payment',
                              cart_id     int                      not null,
                              user_id     integer                  not null,
                              inserted_at timestamptz not null default now(),
                              updated_at  timestamptz,
                              constraint pk_orders primary key (id),
                              constraint fk_orders_user foreign key (user_id) references users(id),
                              constraint fk_orders_session foreign key(cart_id) references carts(id)
)   ;

CREATE TABLE shipments (
                           tracking_id SERIAL ,
                           order_id INT NOT NULL,
                           product_id int not null,
                           seller_id int not null,
                           ship_date DATE NOT NULL,
                           delivered_date DATE,
                           delivery_status VARCHAR(20) NOT NULL,
                           delivery_address VARCHAR(50) NOT NULL,
                           constraint pk_shippings primary key (tracking_id),
                           CONSTRAINT fk_orders FOREIGN KEY (order_id) REFERENCES orders(id),
                           constraint fk_sellers foreign key (seller_id) references users(id),
                            constraint fk_products foreign key (product_id) references products(id)
);
create table coupons (
                         id          serial                   not null      ,
                         code        varchar(255)             not null      ,
                         description text                                   ,
                         active      bool                      default true ,
                         seller_id   int                      not null      ,
                         value       numeric(3,1)                            ,
                         multiple    bool                      default false,
                         start_date  timestamp with time zone               ,
                         end_date    timestamp with time zone               ,
                         inserted_at timestamptz not null default now()      ,
                         updated_at  timestamptz      ,
                         constraint pk_coupons primary key (id),
                         constraint fk_sellers foreign key (seller_id) references users(id)
)   ;
create table product_tags (
                              product_id  integer                  not null,
                              tag_id      integer                  not null,
                              inserted_at timestamptz not null default now(),
                              updated_at  timestamptz,
                              constraint pk_product_tags primary key (product_id,tag_id),
                              constraint fk_product_tags_product foreign key (product_id) references products(id),
                              constraint fk_product_tags_tag foreign key (tag_id) references tags(id)
)   ;
create table product_categories (
                                    category_id integer                  not null,
                                    product_id  integer                  not null,
                                    specifications jsonb                 not null,
                                    inserted_at timestamptz not null default now(),
                                    updated_at  timestamptz,
                                    constraint pk_product_categories primary key (category_id,product_id),
                                    constraint fk_category_id foreign key (category_id) references categories(id),
                                    constraint fk_product_id foreign key  (product_id) references products(id)
)   ;
create table cart_product (
                                id          serial                   not null,
                                cart_id    integer                          ,
                                seller_id  integer           not null,
                                name        varchar(255)             not null,
                                coupon_id   integer                          ,
                                description text                             ,
                                price       numeric                  not null,
                                quantity    integer                  not null,
                                subtotal    numeric                  not null,
                                inserted_at timestamptz not null default now(),
                                updated_at  timestamptz,
                                constraint pk_order_products primary key (id),
                                constraint fk_order_products_product foreign key(seller_id) references product_seller(id),
                                constraint fk_order_products_coupons foreign key (coupon_id) references coupons(id),
                                constraint fk_order_products_order foreign key (cart_id) references carts(id)
)   ;