INSERT INTO users (id, date_of_birth, name, password)
VALUES (1, '12-12-1990 12:00:00', 'Петров Пётр Петрович', '123');


INSERT INTO users (id, date_of_birth, name, password)
VALUES (2, '12-12-2000 12:00:00', 'Александров Александр Александрович', '234');


INSERT INTO users (id, date_of_birth, name, password)
VALUES (3, '12-12-2010 12:00:00', 'Иванов Иван Иванович', '345');


INSERT INTO user_roles (user_id, roles)
VALUES (1, 'USER');

INSERT INTO user_roles (user_id, roles)
VALUES (2, 'VISITOR');

INSERT INTO user_roles (user_id, roles)
VALUES (3, 'ADMIN');