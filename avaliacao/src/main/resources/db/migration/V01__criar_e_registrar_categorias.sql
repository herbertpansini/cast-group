CREATE TABLE categoria (
	codigo BIGINT(20) NOT NULL PRIMARY KEY AUTO_INCREMENT,
	descricao VARCHAR(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO categoria (descricao) VALUES ('Comportamental');
INSERT INTO categoria (descricao) VALUES ('Programação');
INSERT INTO categoria (descricao) VALUES ('Qualidade');
INSERT INTO categoria (descricao) VALUES ('Processos');
