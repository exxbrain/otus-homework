create sequence hibernate_sequence start 1 increment 1;
create table association_value_entry
(
    id                int8         not null,
    association_key   varchar(255) not null,
    association_value varchar(255),
    saga_id           varchar(255) not null,
    saga_type         varchar(255),
    primary key (id)
);
create table domain_event_entry
(
    global_index         int8         not null,
    event_identifier     varchar(255) not null,
    meta_data            oid,
    payload              oid          not null,
    payload_revision     varchar(255),
    payload_type         varchar(255) not null,
    time_stamp           varchar(255) not null,
    aggregate_identifier varchar(255) not null,
    sequence_number      int8         not null,
    type                 varchar(255),
    primary key (global_index)
);
create table order_item_view
(
    id            uuid         not null,
    price         numeric(19, 2),
    product_id    varchar(255) not null,
    product_name  varchar(255) not null,
    quantity      int4         not null,
    order_view_id uuid         not null,
    primary key (id)
);
create table order_view
(
    id          uuid         not null,
    created_at  timestamp    not null,
    customer_id varchar(255) not null,
    state       varchar(255) not null,
    total       numeric(19, 2),
    primary key (id)
);
create table saga_entry
(
    saga_id         varchar(255) not null,
    revision        varchar(255),
    saga_type       varchar(255),
    serialized_saga oid,
    primary key (saga_id)
);
create table snapshot_event_entry
(
    aggregate_identifier varchar(255) not null,
    sequence_number      int8         not null,
    type                 varchar(255) not null,
    event_identifier     varchar(255) not null,
    meta_data            oid,
    payload              oid          not null,
    payload_revision     varchar(255),
    payload_type         varchar(255) not null,
    time_stamp           varchar(255) not null,
    primary key (aggregate_identifier, sequence_number, type)
);
create table token_entry
(
    processor_name varchar(255) not null,
    segment        int4         not null,
    owner          varchar(255),
    timestamp      varchar(255) not null,
    token          oid,
    token_type     varchar(255),
    primary key (processor_name, segment)
);
create index IDXk45eqnxkgd8hpdn6xixn8sgft on association_value_entry (saga_type, association_key, association_value);
create index IDXgv5k1v2mh6frxuy5c0hgbau94 on association_value_entry (saga_id, saga_type);
alter table if exists domain_event_entry
    add constraint UK8s1f994p4la2ipb13me2xqm1w unique (aggregate_identifier, sequence_number);
alter table if exists domain_event_entry
    add constraint UK_fwe6lsa8bfo6hyas6ud3m8c7x unique (event_identifier);
create index totalIndex on order_view (total);
create index createdAtIndex on order_view (created_at);
create index stateCreatedAtIndex on order_view (state, created_at);
create index stateCreatedAtDESCIndex on order_view (state, created_at desc);
create index stateTotalIndex on order_view (state, total);
create index stateTotalDescIndex on order_view (state, total desc);
alter table if exists snapshot_event_entry
    add constraint UK_e1uucjseo68gopmnd0vgdl44h unique (event_identifier);
alter table if exists order_item_view
    add constraint FKfpd5ymxmxmccsrpj5sch1o1eq foreign key (order_view_id) references order_view;
create sequence hibernate_sequence start 1 increment 1
create table association_value_entry (id int8 not null, association_key varchar(255) not null, association_value varchar(255), saga_id varchar(255) not null, saga_type varchar(255), primary key (id))
create table domain_event_entry (global_index int8 not null, event_identifier varchar(255) not null, meta_data oid, payload oid not null, payload_revision varchar(255), payload_type varchar(255) not null, time_stamp varchar(255) not null, aggregate_identifier varchar(255) not null, sequence_number int8 not null, type varchar(255), primary key (global_index))
create table order_item_view (id uuid not null, price numeric(19, 2), product_id varchar(255) not null, product_name varchar(255) not null, quantity int4 not null, order_view_id uuid not null, primary key (id))
create table order_view (id uuid not null, created_at timestamp not null, customer_id varchar(255) not null, state varchar(255) not null, total numeric(19, 2), primary key (id))
create table saga_entry (saga_id varchar(255) not null, revision varchar(255), saga_type varchar(255), serialized_saga oid, primary key (saga_id))
create table snapshot_event_entry (aggregate_identifier varchar(255) not null, sequence_number int8 not null, type varchar(255) not null, event_identifier varchar(255) not null, meta_data oid, payload oid not null, payload_revision varchar(255), payload_type varchar(255) not null, time_stamp varchar(255) not null, primary key (aggregate_identifier, sequence_number, type))
create table token_entry (processor_name varchar(255) not null, segment int4 not null, owner varchar(255), timestamp varchar(255) not null, token oid, token_type varchar(255), primary key (processor_name, segment))
create index IDXk45eqnxkgd8hpdn6xixn8sgft on association_value_entry (saga_type, association_key, association_value)
create index IDXgv5k1v2mh6frxuy5c0hgbau94 on association_value_entry (saga_id, saga_type)
alter table if exists domain_event_entry add constraint UK8s1f994p4la2ipb13me2xqm1w unique (aggregate_identifier, sequence_number)
alter table if exists domain_event_entry add constraint UK_fwe6lsa8bfo6hyas6ud3m8c7x unique (event_identifier)
create index totalIndex on order_view (total)
create index createdAtIndex on order_view (created_at)
create index stateCreatedAtIndex on order_view (state, created_at)
create index stateCreatedAtDESCIndex on order_view (state, created_at desc)
create index stateTotalIndex on order_view (state, total)
create index stateTotalDescIndex on order_view (state, total desc)
alter table if exists snapshot_event_entry add constraint UK_e1uucjseo68gopmnd0vgdl44h unique (event_identifier)
alter table if exists order_item_view add constraint FKfpd5ymxmxmccsrpj5sch1o1eq foreign key (order_view_id) references order_view
create sequence hibernate_sequence start 1 increment 1
create table association_value_entry (id int8 not null, association_key varchar(255) not null, association_value varchar(255), saga_id varchar(255) not null, saga_type varchar(255), primary key (id))
create table domain_event_entry (global_index int8 not null, event_identifier varchar(255) not null, meta_data oid, payload oid not null, payload_revision varchar(255), payload_type varchar(255) not null, time_stamp varchar(255) not null, aggregate_identifier varchar(255) not null, sequence_number int8 not null, type varchar(255), primary key (global_index))
create table order_item_view (id uuid not null, price numeric(19, 2), product_id varchar(255) not null, product_name varchar(255) not null, quantity int4 not null, order_view_id uuid not null, primary key (id))
create table order_view (id uuid not null, created_at timestamp not null, customer_id varchar(255) not null, state varchar(255) not null, total numeric(19, 2), primary key (id))
create table saga_entry (saga_id varchar(255) not null, revision varchar(255), saga_type varchar(255), serialized_saga oid, primary key (saga_id))
create table snapshot_event_entry (aggregate_identifier varchar(255) not null, sequence_number int8 not null, type varchar(255) not null, event_identifier varchar(255) not null, meta_data oid, payload oid not null, payload_revision varchar(255), payload_type varchar(255) not null, time_stamp varchar(255) not null, primary key (aggregate_identifier, sequence_number, type))
create table token_entry (processor_name varchar(255) not null, segment int4 not null, owner varchar(255), timestamp varchar(255) not null, token oid, token_type varchar(255), primary key (processor_name, segment))
create index IDXk45eqnxkgd8hpdn6xixn8sgft on association_value_entry (saga_type, association_key, association_value)
create index IDXgv5k1v2mh6frxuy5c0hgbau94 on association_value_entry (saga_id, saga_type)
alter table if exists domain_event_entry add constraint UK8s1f994p4la2ipb13me2xqm1w unique (aggregate_identifier, sequence_number)
alter table if exists domain_event_entry add constraint UK_fwe6lsa8bfo6hyas6ud3m8c7x unique (event_identifier)
create index totalIndex on order_view (total)
create index createdAtIndex on order_view (created_at)
create index stateCreatedAtIndex on order_view (state, created_at)
create index stateCreatedAtDESCIndex on order_view (state, created_at desc)
create index stateTotalIndex on order_view (state, total)
create index stateTotalDescIndex on order_view (state, total desc)
alter table if exists snapshot_event_entry add constraint UK_e1uucjseo68gopmnd0vgdl44h unique (event_identifier)
alter table if exists order_item_view add constraint FKfpd5ymxmxmccsrpj5sch1o1eq foreign key (order_view_id) references order_view
create sequence hibernate_sequence start 1 increment 1
create table association_value_entry (id int8 not null, association_key varchar(255) not null, association_value varchar(255), saga_id varchar(255) not null, saga_type varchar(255), primary key (id))
create table domain_event_entry (global_index int8 not null, event_identifier varchar(255) not null, meta_data oid, payload oid not null, payload_revision varchar(255), payload_type varchar(255) not null, time_stamp varchar(255) not null, aggregate_identifier varchar(255) not null, sequence_number int8 not null, type varchar(255), primary key (global_index))
create table order_item_view (id uuid not null, price numeric(19, 2), product_id varchar(255) not null, product_name varchar(255) not null, quantity int4 not null, order_view_id uuid not null, primary key (id))
create table order_view (id uuid not null, created_at timestamp not null, customer_id varchar(255) not null, state varchar(255) not null, total numeric(19, 2), primary key (id))
create table saga_entry (saga_id varchar(255) not null, revision varchar(255), saga_type varchar(255), serialized_saga oid, primary key (saga_id))
create table snapshot_event_entry (aggregate_identifier varchar(255) not null, sequence_number int8 not null, type varchar(255) not null, event_identifier varchar(255) not null, meta_data oid, payload oid not null, payload_revision varchar(255), payload_type varchar(255) not null, time_stamp varchar(255) not null, primary key (aggregate_identifier, sequence_number, type))
create table token_entry (processor_name varchar(255) not null, segment int4 not null, owner varchar(255), timestamp varchar(255) not null, token oid, token_type varchar(255), primary key (processor_name, segment))
create index IDXk45eqnxkgd8hpdn6xixn8sgft on association_value_entry (saga_type, association_key, association_value)
create index IDXgv5k1v2mh6frxuy5c0hgbau94 on association_value_entry (saga_id, saga_type)
alter table if exists domain_event_entry add constraint UK8s1f994p4la2ipb13me2xqm1w unique (aggregate_identifier, sequence_number)
alter table if exists domain_event_entry add constraint UK_fwe6lsa8bfo6hyas6ud3m8c7x unique (event_identifier)
create index totalIndex on order_view (total)
create index createdAtIndex on order_view (created_at)
create index stateCreatedAtIndex on order_view (state, created_at)
create index stateCreatedAtDESCIndex on order_view (state, created_at desc)
create index stateTotalIndex on order_view (state, total)
create index stateTotalDescIndex on order_view (state, total desc)
alter table if exists snapshot_event_entry add constraint UK_e1uucjseo68gopmnd0vgdl44h unique (event_identifier)
alter table if exists order_item_view add constraint FKfpd5ymxmxmccsrpj5sch1o1eq foreign key (order_view_id) references order_view
create sequence hibernate_sequence start 1 increment 1
create table association_value_entry (id int8 not null, association_key varchar(255) not null, association_value varchar(255), saga_id varchar(255) not null, saga_type varchar(255), primary key (id))
create table domain_event_entry (global_index int8 not null, event_identifier varchar(255) not null, meta_data oid, payload oid not null, payload_revision varchar(255), payload_type varchar(255) not null, time_stamp varchar(255) not null, aggregate_identifier varchar(255) not null, sequence_number int8 not null, type varchar(255), primary key (global_index))
create table order_item_view (id uuid not null, price numeric(19, 2), product_id varchar(255) not null, product_name varchar(255) not null, quantity int4 not null, order_view_id uuid not null, primary key (id))
create table order_view (id uuid not null, created_at timestamp not null, customer_id varchar(255) not null, state varchar(255) not null, total numeric(19, 2), primary key (id))
create table saga_entry (saga_id varchar(255) not null, revision varchar(255), saga_type varchar(255), serialized_saga oid, primary key (saga_id))
create table snapshot_event_entry (aggregate_identifier varchar(255) not null, sequence_number int8 not null, type varchar(255) not null, event_identifier varchar(255) not null, meta_data oid, payload oid not null, payload_revision varchar(255), payload_type varchar(255) not null, time_stamp varchar(255) not null, primary key (aggregate_identifier, sequence_number, type))
create table token_entry (processor_name varchar(255) not null, segment int4 not null, owner varchar(255), timestamp varchar(255) not null, token oid, token_type varchar(255), primary key (processor_name, segment))
create index IDXk45eqnxkgd8hpdn6xixn8sgft on association_value_entry (saga_type, association_key, association_value)
create index IDXgv5k1v2mh6frxuy5c0hgbau94 on association_value_entry (saga_id, saga_type)
alter table if exists domain_event_entry add constraint UK8s1f994p4la2ipb13me2xqm1w unique (aggregate_identifier, sequence_number)
alter table if exists domain_event_entry add constraint UK_fwe6lsa8bfo6hyas6ud3m8c7x unique (event_identifier)
create index totalIndex on order_view (total)
create index createdAtIndex on order_view (created_at)
create index stateCreatedAtIndex on order_view (state, created_at)
create index stateCreatedAtDESCIndex on order_view (state, created_at desc)
create index stateTotalIndex on order_view (state, total)
create index stateTotalDescIndex on order_view (state, total desc)
alter table if exists snapshot_event_entry add constraint UK_e1uucjseo68gopmnd0vgdl44h unique (event_identifier)
alter table if exists order_item_view add constraint FKfpd5ymxmxmccsrpj5sch1o1eq foreign key (order_view_id) references order_view
create sequence hibernate_sequence start 1 increment 1
create table association_value_entry (id int8 not null, association_key varchar(255) not null, association_value varchar(255), saga_id varchar(255) not null, saga_type varchar(255), primary key (id))
create table domain_event_entry (global_index int8 not null, event_identifier varchar(255) not null, meta_data oid, payload oid not null, payload_revision varchar(255), payload_type varchar(255) not null, time_stamp varchar(255) not null, aggregate_identifier varchar(255) not null, sequence_number int8 not null, type varchar(255), primary key (global_index))
create table order_item_view (id uuid not null, price numeric(19, 2), product_id varchar(255) not null, product_name varchar(255) not null, quantity int4 not null, order_view_id uuid not null, primary key (id))
create table order_view (id uuid not null, created_at timestamp not null, customer_id varchar(255) not null, state varchar(255) not null, total numeric(19, 2), primary key (id))
create table saga_entry (saga_id varchar(255) not null, revision varchar(255), saga_type varchar(255), serialized_saga oid, primary key (saga_id))
create table snapshot_event_entry (aggregate_identifier varchar(255) not null, sequence_number int8 not null, type varchar(255) not null, event_identifier varchar(255) not null, meta_data oid, payload oid not null, payload_revision varchar(255), payload_type varchar(255) not null, time_stamp varchar(255) not null, primary key (aggregate_identifier, sequence_number, type))
create table token_entry (processor_name varchar(255) not null, segment int4 not null, owner varchar(255), timestamp varchar(255) not null, token oid, token_type varchar(255), primary key (processor_name, segment))
create index IDXk45eqnxkgd8hpdn6xixn8sgft on association_value_entry (saga_type, association_key, association_value)
create index IDXgv5k1v2mh6frxuy5c0hgbau94 on association_value_entry (saga_id, saga_type)
alter table if exists domain_event_entry add constraint UK8s1f994p4la2ipb13me2xqm1w unique (aggregate_identifier, sequence_number)
alter table if exists domain_event_entry add constraint UK_fwe6lsa8bfo6hyas6ud3m8c7x unique (event_identifier)
create index totalIndex on order_view (total)
create index createdAtIndex on order_view (created_at)
create index stateCreatedAtIndex on order_view (state, created_at)
create index stateCreatedAtDESCIndex on order_view (state, created_at desc)
create index stateTotalIndex on order_view (state, total)
create index stateTotalDescIndex on order_view (state, total desc)
alter table if exists snapshot_event_entry add constraint UK_e1uucjseo68gopmnd0vgdl44h unique (event_identifier)
alter table if exists order_item_view add constraint FKfpd5ymxmxmccsrpj5sch1o1eq foreign key (order_view_id) references order_view
create sequence hibernate_sequence start 1 increment 1
create table association_value_entry (id int8 not null, association_key varchar(255) not null, association_value varchar(255), saga_id varchar(255) not null, saga_type varchar(255), primary key (id))
create table domain_event_entry (global_index int8 not null, event_identifier varchar(255) not null, meta_data oid, payload oid not null, payload_revision varchar(255), payload_type varchar(255) not null, time_stamp varchar(255) not null, aggregate_identifier varchar(255) not null, sequence_number int8 not null, type varchar(255), primary key (global_index))
create table order_item_view (id uuid not null, price numeric(19, 2), product_id varchar(255) not null, product_name varchar(255) not null, quantity int4 not null, order_view_id uuid not null, primary key (id))
create table order_view (id uuid not null, created_at timestamp not null, customer_id varchar(255) not null, state varchar(255) not null, total numeric(19, 2), primary key (id))
create table saga_entry (saga_id varchar(255) not null, revision varchar(255), saga_type varchar(255), serialized_saga oid, primary key (saga_id))
create table snapshot_event_entry (aggregate_identifier varchar(255) not null, sequence_number int8 not null, type varchar(255) not null, event_identifier varchar(255) not null, meta_data oid, payload oid not null, payload_revision varchar(255), payload_type varchar(255) not null, time_stamp varchar(255) not null, primary key (aggregate_identifier, sequence_number, type))
create table token_entry (processor_name varchar(255) not null, segment int4 not null, owner varchar(255), timestamp varchar(255) not null, token oid, token_type varchar(255), primary key (processor_name, segment))
create index IDXk45eqnxkgd8hpdn6xixn8sgft on association_value_entry (saga_type, association_key, association_value)
create index IDXgv5k1v2mh6frxuy5c0hgbau94 on association_value_entry (saga_id, saga_type)
alter table if exists domain_event_entry add constraint UK8s1f994p4la2ipb13me2xqm1w unique (aggregate_identifier, sequence_number)
alter table if exists domain_event_entry add constraint UK_fwe6lsa8bfo6hyas6ud3m8c7x unique (event_identifier)
create index totalIndex on order_view (total)
create index createdAtIndex on order_view (created_at)
create index stateCreatedAtIndex on order_view (state, created_at)
create index stateCreatedAtDESCIndex on order_view (state, created_at desc)
create index stateTotalIndex on order_view (state, total)
create index stateTotalDescIndex on order_view (state, total desc)
alter table if exists snapshot_event_entry add constraint UK_e1uucjseo68gopmnd0vgdl44h unique (event_identifier)
alter table if exists order_item_view add constraint FKfpd5ymxmxmccsrpj5sch1o1eq foreign key (order_view_id) references order_view
create sequence hibernate_sequence start 1 increment 1
create table association_value_entry (id int8 not null, association_key varchar(255) not null, association_value varchar(255), saga_id varchar(255) not null, saga_type varchar(255), primary key (id))
create table domain_event_entry (global_index int8 not null, event_identifier varchar(255) not null, meta_data oid, payload oid not null, payload_revision varchar(255), payload_type varchar(255) not null, time_stamp varchar(255) not null, aggregate_identifier varchar(255) not null, sequence_number int8 not null, type varchar(255), primary key (global_index))
create table order_item_view (id uuid not null, price numeric(19, 2), product_id varchar(255) not null, product_name varchar(255) not null, quantity int4 not null, order_view_id uuid not null, primary key (id))
create table order_view (id uuid not null, created_at timestamp not null, customer_id varchar(255) not null, state varchar(255) not null, total numeric(19, 2), primary key (id))
create table saga_entry (saga_id varchar(255) not null, revision varchar(255), saga_type varchar(255), serialized_saga oid, primary key (saga_id))
create table snapshot_event_entry (aggregate_identifier varchar(255) not null, sequence_number int8 not null, type varchar(255) not null, event_identifier varchar(255) not null, meta_data oid, payload oid not null, payload_revision varchar(255), payload_type varchar(255) not null, time_stamp varchar(255) not null, primary key (aggregate_identifier, sequence_number, type))
create table token_entry (processor_name varchar(255) not null, segment int4 not null, owner varchar(255), timestamp varchar(255) not null, token oid, token_type varchar(255), primary key (processor_name, segment))
create index IDXk45eqnxkgd8hpdn6xixn8sgft on association_value_entry (saga_type, association_key, association_value)
create index IDXgv5k1v2mh6frxuy5c0hgbau94 on association_value_entry (saga_id, saga_type)
alter table if exists domain_event_entry add constraint UK8s1f994p4la2ipb13me2xqm1w unique (aggregate_identifier, sequence_number)
alter table if exists domain_event_entry add constraint UK_fwe6lsa8bfo6hyas6ud3m8c7x unique (event_identifier)
create index totalIndex on order_view (total)
create index createdAtIndex on order_view (created_at)
create index stateCreatedAtIndex on order_view (state, created_at)
create index stateCreatedAtDESCIndex on order_view (state, created_at desc)
create index stateTotalIndex on order_view (state, total)
create index stateTotalDescIndex on order_view (state, total desc)
alter table if exists snapshot_event_entry add constraint UK_e1uucjseo68gopmnd0vgdl44h unique (event_identifier)
alter table if exists order_item_view add constraint FKfpd5ymxmxmccsrpj5sch1o1eq foreign key (order_view_id) references order_view
create sequence hibernate_sequence start 1 increment 1
create table association_value_entry (id int8 not null, association_key varchar(255) not null, association_value varchar(255), saga_id varchar(255) not null, saga_type varchar(255), primary key (id))
create table domain_event_entry (global_index int8 not null, event_identifier varchar(255) not null, meta_data oid, payload oid not null, payload_revision varchar(255), payload_type varchar(255) not null, time_stamp varchar(255) not null, aggregate_identifier varchar(255) not null, sequence_number int8 not null, type varchar(255), primary key (global_index))
create table order_item_view (id uuid not null, price numeric(19, 2), product_id varchar(255) not null, product_name varchar(255) not null, quantity int4 not null, order_view_id uuid not null, primary key (id))
create table order_view (id uuid not null, created_at timestamp not null, customer_id varchar(255) not null, state varchar(255) not null, total numeric(19, 2), primary key (id))
create table saga_entry (saga_id varchar(255) not null, revision varchar(255), saga_type varchar(255), serialized_saga oid, primary key (saga_id))
create table snapshot_event_entry (aggregate_identifier varchar(255) not null, sequence_number int8 not null, type varchar(255) not null, event_identifier varchar(255) not null, meta_data oid, payload oid not null, payload_revision varchar(255), payload_type varchar(255) not null, time_stamp varchar(255) not null, primary key (aggregate_identifier, sequence_number, type))
create table token_entry (processor_name varchar(255) not null, segment int4 not null, owner varchar(255), timestamp varchar(255) not null, token oid, token_type varchar(255), primary key (processor_name, segment))
create index IDXk45eqnxkgd8hpdn6xixn8sgft on association_value_entry (saga_type, association_key, association_value)
create index IDXgv5k1v2mh6frxuy5c0hgbau94 on association_value_entry (saga_id, saga_type)
alter table if exists domain_event_entry add constraint UK8s1f994p4la2ipb13me2xqm1w unique (aggregate_identifier, sequence_number)
alter table if exists domain_event_entry add constraint UK_fwe6lsa8bfo6hyas6ud3m8c7x unique (event_identifier)
create index totalIndex on order_view (total)
create index createdAtIndex on order_view (created_at)
create index stateCreatedAtIndex on order_view (state, created_at)
create index stateCreatedAtDESCIndex on order_view (state, created_at desc)
create index stateTotalIndex on order_view (state, total)
create index stateTotalDescIndex on order_view (state, total desc)
alter table if exists snapshot_event_entry add constraint UK_e1uucjseo68gopmnd0vgdl44h unique (event_identifier)
alter table if exists order_item_view add constraint FKfpd5ymxmxmccsrpj5sch1o1eq foreign key (order_view_id) references order_view
