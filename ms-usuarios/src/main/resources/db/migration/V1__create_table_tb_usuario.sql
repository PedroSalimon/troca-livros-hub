CREATE TABLE IF NOT EXISTS tb_usuario (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    idade INT NOT NULL,
    endereco VARCHAR(255) NOT NULL,
    cidade VARCHAR(100) NOT NULL,
    estado VARCHAR(2) NOT NULL,
    data_cadastro DATE,
    CONSTRAINT pk_tb_usuario PRIMARY KEY (id),
    CONSTRAINT uk_tb_usuario_email UNIQUE (email)
);
