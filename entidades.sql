CREATE TABLE usuario (
    id BIGINT GENERATED ALWAYS AS IDENTITY,
    nome_usuario VARCHAR(50) NOT NULL,
    nome_completo VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    senha VARCHAR(255) NOT NULL,
    data_cadastro TIMESTAMP NOT NULL,

    CONSTRAINT pk_usuario
        PRIMARY KEY (id),

    CONSTRAINT uq_usuario_email
        UNIQUE (email)

    -- Falta fazer validação (CHECK)
);

-- Exemplo
INSERT INTO usuario(nome_usuario, nome_completo, email, senha, data_cadastro)
VALUES('gabriel', 'Gabriel Jun', 'gabriel@gmail.com', '123', NOW());


CREATE TABLE projeto (
    id BIGINT GENERATED ALWAYS AS IDENTITY,
    nome VARCHAR(100) NOT NULL,
    descricao VARCHAR(500),
    tipo VARCHAR(50),
    data_criacao TIMESTAMP NOT NULL,
    data_ultima_edicao TIMESTAMP,
    usuario_id BIGINT,

    CONSTRAINT pk_projeto
        PRIMARY KEY (id),

    CONSTRAINT fk_projeto_usuario
        FOREIGN KEY (usuario_id)
        REFERENCES usuario(id)
);

-- Exemplo
INSERT INTO projeto(nome, data_criacao, usuario_id) VALUES('Meu projeto', NOW(), 1);


CREATE TABLE capitulo (
    id BIGINT GENERATED ALWAYS AS IDENTITY,
    numero INT NOT NULL,
    titulo VARCHAR(100) NOT NULL,
    texto TEXT,
    data_ultima_edicao TIMESTAMP,
    projeto_id BIGINT,

    CONSTRAINT pk_capitulo
        PRIMARY KEY(id),

    CONSTRAINT fk_capitulo_projeto
        FOREIGN KEY (projeto_id)
        REFERENCES projeto(id)

    -- Falta fazer validação (CHECK)
);

-- Exemplo
INSERT INTO capitulo(numero, titulo, texto, projeto_id) VALUES(1, 'O começo', 'Era uma vez', 1);



-- Buscar todos os projetos de um usuário
SELECT
    projeto.id,
    projeto.nome,
    projeto.descricao,
    projeto.tipo,
    projeto.data_criacao,
    projeto.data_ultima_edicao
FROM usuario INNER JOIN projeto ON projeto.usuario_id = usuario.id 
WHERE usuario.id=?;
