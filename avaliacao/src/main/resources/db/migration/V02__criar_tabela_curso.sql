CREATE TABLE curso (
	codigo BIGINT(20) NOT NULL PRIMARY KEY AUTO_INCREMENT,
	assunto VARCHAR(50) NOT NULL,
	data_inicio DATE NOT NULL,
	data_termino DATE NOT NULL,
	quantidade_alunos_por_turma INT NULL,
	codigo_categoria BIGINT(20) NOT NULL,
	FOREIGN KEY (codigo_categoria) REFERENCES categoria(codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
