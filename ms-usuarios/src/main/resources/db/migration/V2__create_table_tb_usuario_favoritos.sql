CREATE TABLE IF NOT EXISTS tb_usuario_generos_favoritos (
    usuario_id BIGINT NOT NULL,
    genero VARCHAR(100) NOT NULL,
    CONSTRAINT fk_generos_favoritos_usuario FOREIGN KEY (usuario_id)
        REFERENCES tb_usuario (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS tb_usuario_autores_favoritos (
    usuario_id BIGINT NOT NULL,
    autor VARCHAR(100) NOT NULL,
    CONSTRAINT fk_autores_favoritos_usuario FOREIGN KEY (usuario_id)
        REFERENCES tb_usuario (id) ON DELETE CASCADE
);
