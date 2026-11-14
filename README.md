# Troca de Livros - Microsserviços

## Integrantes

- Nome: Pedro Salimon  RM: 555038
- Nome: Lucas Moura Machado  RM: 557431

## Problema

Muitos livros ficam parados nas estantes enquanto outras pessoas procuram exatamente por eles. Falta um jeito simples de encontrar leitores próximos e trocar livros.

## Solução

App de troca de livros. O usuário se cadastra (nome, idade, endereço, e-mail, gêneros e autores favoritos) e cadastra seus livros (autor, título, gênero, editora e condição: NOVO, SEMINOVO, BOM, REGULAR ou DESGASTADO). O app lista os livros disponíveis na mesma cidade que combinam com seus favoritos, e o usuário pode propor uma troca.

Arquitetura (Java 25, Spring Boot 4.0.9, MySQL, Flyway):

- eureka-server (porta 8761): registro dos serviços.
- api-gateway (porta 8082): entrada única e Swagger de todos os serviços.
- ms-usuarios (porta aleatória, banco "usuarios"): usuários e livros.
- ms-trocas (porta aleatória, banco "trocas"): propostas e confirmação de trocas.

Comunicação síncrona: ao confirmar uma troca, o ms-trocas chama o ms-usuarios (OpenFeign + Eureka + Load Balancer, sem URL fixa). O ms-usuarios troca o dono dos dois livros e muda o status deles para TROCADO. Se o ms-usuarios estiver fora do ar, o Circuit Breaker ativa o fallback: a troca fica CONFIRMACAO_PENDENTE e a API responde 503.

## Pré-requisitos

Java 25, Maven e Docker instalados. Portas livres: 3306, 8761 e 8082.

## Como executar

Abra um terminal 

1. MySQL (aguarde uns 30 segundos até ficar pronto): Abra um terminal e execute os códigos

        cd compose
        docker compose up -d

Agora você deve abrir a classe principal de cada módulo e rode a Application, Use EurekaServerApplication, MsUsuariosApplication, MsTrocasApplication e ApiGatewayApplication, nesta ordem.

Os bancos "usuarios" e "trocas" e as tabelas com dados iniciais são criados automaticamente pelo Flyway (usuário root, senha root).

Para subir outra instância de um serviço, rode o mesmo comando em outro terminal (a porta é aleatória).

## Como testar

- Eureka: http://localhost:8761
- Swagger (todos os serviços, escolha no seletor do topo): http://localhost:8082/swagger-ui.html

Roteiro:

1. ms-usuarios, GET /api/v1/usuarios/1/livros-recomendados: lista os livros próximos que combinam com os favoritos da Ana.
2. ms-trocas, GET /api/v1/trocas: duas trocas iniciais com status PROPOSTA.
3. ms-trocas, PATCH /api/v1/trocas/1/confirmar: retorna 200 com status CONCLUIDA.
4. ms-usuarios, GET /api/v1/livros: os livros 2 e 3 agora estão TROCADO e com os donos invertidos.
5. Teste do fallback: pare o ms-usuarios (Ctrl+C) e chame PATCH /api/v1/trocas/2/confirmar. Retorna 503 com status CONFIRMACAO_PENDENTE.

Chamadas pelo gateway seguem o formato http://localhost:8082/ms-usuarios/api/v1/usuarios e http://localhost:8082/ms-trocas/api/v1/trocas.

## Recomeçar do zero

Pare tudo, execute "docker compose down" na pasta compose e apague a pasta compose/.data.
