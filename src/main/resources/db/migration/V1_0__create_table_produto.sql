create table if not exists "produto"
(
    id        uuid primary key,
    nome      text    not null,
    preco     numeric not null,
    categoria text    not null
);

alter table produto
    add constraint unique_produti unique (nome, preco, categoria);