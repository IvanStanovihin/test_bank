INSERT INTO users (id, date_of_birth, name, password)
VALUES (111, '12-11-1990 12:00:00', 'Петров Пётр Петрович', '123');


INSERT INTO users (id, date_of_birth, name, password)
VALUES (222, '12-11-2000 12:00:00', 'Александров Александр Александрович', '234');


INSERT INTO users (id, date_of_birth, name, password)
VALUES (333, '12-11-2010 12:00:00', 'Иванов Иван Иванович', '345');


INSERT INTO user_roles (user_id, roles)
VALUES (111, 'USER');

INSERT INTO user_roles (user_id, roles)
VALUES (222, 'VISITOR');

INSERT INTO user_roles (user_id, roles)
VALUES (333, 'ADMIN');