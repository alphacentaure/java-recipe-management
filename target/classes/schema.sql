create table if not exists ingredients (
    id bigserial not null,
    version integer not null,
    name varchar not null,
    quantity varchar not null,
    createdon date DEFAULT NULL,
    updatedon date DEFAULT NULL,
    primary key (id)
);

create table if not exists recipes (
    id bigserial not null,    
    version integer not null,
    recipename varchar,
    description varchar,
    cookinginstruction varchar,
    createdon date DEFAULT NULL,
    updatedon date DEFAULT NULL,
    primary key (id)
);