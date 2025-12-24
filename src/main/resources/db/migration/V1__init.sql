create table beer (
                      id varchar(36) not null,
                      beer_name varchar(50) not null,
                      beer_style varchar(255) not null,
                      created_time datetime(6),
                      price decimal(38,2) not null,
                      quantity_on_hand integer,
                      upd varchar(50) not null,
                      updated_time datetime(6),
                      version integer,
                      primary key (id)
) engine=InnoDB;

create table customer (
                          id varchar(36) not null,
                          created_time datetime(6),
                          customer_name varchar(255),
                          updated_time datetime(6),
                          version integer,
                          primary key (id)
) engine=InnoDB;
