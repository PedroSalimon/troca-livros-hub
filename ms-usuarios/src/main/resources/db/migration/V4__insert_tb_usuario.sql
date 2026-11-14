INSERT INTO tb_usuario (nome, email, idade, endereco, cidade, estado, data_cadastro) VALUES
('Ana Souza', 'ana.souza@email.com', 28, 'Rua das Flores, 120', 'São Paulo', 'SP', '2026-01-10'),
('Bruno Lima', 'bruno.lima@email.com', 34, 'Avenida Paulista, 1000', 'São Paulo', 'SP', '2026-01-12'),
('Carla Mendes', 'carla.mendes@email.com', 41, 'Rua da Bahia, 45', 'Belo Horizonte', 'MG', '2026-01-15');

INSERT INTO tb_usuario_generos_favoritos (usuario_id, genero) VALUES
(1, 'Ficção Científica'),
(1, 'Fantasia'),
(2, 'Romance'),
(2, 'Fantasia'),
(3, 'Fantasia');

INSERT INTO tb_usuario_autores_favoritos (usuario_id, autor) VALUES
(1, 'Isaac Asimov'),
(2, 'J.R.R. Tolkien'),
(3, 'J.R.R. Tolkien');
