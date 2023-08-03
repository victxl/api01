CREATE TABLE Pessoa (
                           id BIGSERIAL PRIMARY KEY,
                           nome VARCHAR(50) NOT NULL,
                           ativo BOOLEAN,
                           cep varchar(15) not null,
                           logradouro varchar(100),
                           complemento varchar(100),
                           bairro varchar(100),
                           cidade varchar(100),
                           estado varchar(100)

);




