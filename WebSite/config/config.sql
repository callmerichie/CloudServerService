USE Cloud_Project;

CREATE TABLE users(
	iduser INT(11) AUTO_INCREMENT NOT NULL,
	username VARCHAR(50) NOT NULL UNIQUE,
	email VARCHAR(50) NOT NULL UNIQUE,
	password VARCHAR(255) NOT NULL,
    PRIMARY KEY(iduser)
);

CREATE TABLE files(
	idfile INT(11) AUTO_INCREMENT NOT NULL,
	filename VARCHAR(30) NOT NULL,
	extension VARCHAR(6) NOT NULL,
	MIME VARCHAR(30),
	pathfile VARCHAR(100) NOT NULL,
	idusername INT(11) NOT NULL, PRIMARY KEY (idfile), FOREIGN KEY (idusername) REFERENCES users(iduser),
    creationdate DATE NOT NULL,
    creationhour TIME NOT NULL
);
