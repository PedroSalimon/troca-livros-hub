CREATE TABLE IF NOT EXISTS tb_troca (
    id BIGINT NOT NULL AUTO_INCREMENT,
    usuario_solicitante_id BIGINT NOT NULL,
    usuario_destinatario_id BIGINT NOT NULL,
    livro_ofertado_id BIGINT NOT NULL,
    livro_desejado_id BIGINT NOT NULL,
    mensagem VARCHAR(255),
    data_proposta DATE NOT NULL,
    status VARCHAR(35) NOT NULL,
    CONSTRAINT pk_tb_troca PRIMARY KEY (id)
);
