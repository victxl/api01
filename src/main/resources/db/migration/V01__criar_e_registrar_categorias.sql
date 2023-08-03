CREATE TABLE categoria (
                           id BIGSERIAL PRIMARY KEY,
                           nome VARCHAR(50) NOT NULL
);



INSERT INTO categoria (nome) values ('Lazer');
INSERT INTO categoria (nome) values ('Alimento');
INSERT INTO categoria (nome) values ('Supermedado');
INSERT INTO categoria (nome) values ('Farmacia');
INSERT INTO categoria (nome) values ('Outros');