-- authentication data (username = 'user', password = 'password')
INSERT INTO users(username,password,enabled) VALUES ('user','$2a$10$XRKlq21QvHTnR2hDMptn6u8W9/AxA00upBpU4qd.fJsR2ROH4u2RO', TRUE);
INSERT INTO authorities(username,authority) VALUES('user','ROLE_USER');

-- application data
INSERT INTO domain(id, name,description,active,backup) VALUES (1, 'google.com','Main google domain',TRUE,FALSE);
-- password = pwd1
INSERT INTO mailbox(domain_id,email,password,active) VALUES(1,'clauper@google.com', '$2a$10$An2kX7cIv3SSbjx2/iE61OycYJ5ce.4j8XKevunJuaOHp5RGic.ba', TRUE);
-- password = pwd2
INSERT INTO mailbox(domain_id,email,password,active) VALUES(1,'gstefani@google.com', '$2a$10$jjT/kqFAbQvzUWduzM04Lual0NcOuZucl0LvtOu7GPtORP81Ru9Yq', TRUE);
-- password = pwd3
INSERT INTO mailbox(domain_id,email,password,active) VALUES(1,'dbowie@google.com', '$2a$10$3giLHuuZ/8L2eVxpG9jNWOFFYwn8mF5BnNM3lMzjnxBs3WK1C9yJ2', TRUE);
-- password = pwd4
INSERT INTO mailbox(domain_id,email,password,active) VALUES(1,'fmercury@google.com', '$2a$10$k1MpB95JoNZFl4QrU9Aef.lHU0UsXQXzLgv9w8/EZz5RnYAjK8Dky', TRUE);
INSERT INTO alias(domain_id,destination,redirect,active) VALUES(1,'nodoubt@google.com','gstefani@google.com', TRUE);
INSERT INTO alias(domain_id,destination,redirect,active) VALUES(1,'queen@google.com','fmercury@google.com', TRUE);

INSERT INTO domain(id,name,description,active,backup) VALUES (2, 'microsoft.com','Main microsoft domain',FALSE,TRUE);
INSERT INTO ALIAS(domain_id,destination,redirect,active) VALUES(2,'adm@microsoft.com','administration@microsoft.com',TRUE);
INSERT INTO ALIAS(domain_id,destination,redirect,active) VALUES(2,'ceo@microsoft.com','snadella@microsoft.com',TRUE);
INSERT INTO ALIAS(domain_id,destination,redirect,active) VALUES(2,'founders@microsoft.com','bgates@microsoft.com',TRUE);
INSERT INTO ALIAS(domain_id,destination,redirect,active) VALUES(2,'founders@microsoft.com','pallen@microsoft.com',TRUE);
