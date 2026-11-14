CREATE TABLE IF NOT EXISTS tb_livro (
    id BIGINT NOT NULL AUTO_INCREMENT,
    autor VARCHAR(100) NOT NULL,
    titulo VARCHAR(150) NOT NULL,
    genero VARCHAR(100) NOT NULL,
    editora VARCHAR(100) NOT NULL,
    condicao VARCHAR(20) NOT NULL,
    status VARCHAR(20) NOT NULL,
    usuario_id BIGINT NOT NULL,
    CONSTRAINT pk_tb_livro PRIMARY KEY (id),
    CONSTRAINT fk_tb_livro_usuario FOREIGN KEY (usuario_id)
        REFERENCES tb_usuario (id) ON DELETE CASCADE
);
