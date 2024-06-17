INSERT INTO domain(id, name,description,active,backup) VALUES (1, 'google.com','Main google domain',TRUE,FALSE);
INSERT INTO mailbox(domain_id,email,password,active) VALUES(1,'clauper@google.com', 'pwd1', TRUE);
INSERT INTO mailbox(domain_id,email,password,active) VALUES(1,'gstefani@google.com', 'pwd2', TRUE);
INSERT INTO mailbox(domain_id,email,password,active) VALUES(1,'dbowie@google.com', 'pwd3', TRUE);
INSERT INTO mailbox(domain_id,email,password,active) VALUES(1,'fmercury@google.com', 'pwd4', TRUE);
INSERT INTO alias(domain_id,destination,redirect,active) VALUES(1,'nodoubt@google.com','gstefani@google.com', TRUE);
INSERT INTO alias(domain_id,destination,redirect,active) VALUES(1,'queen@google.com','fmercury@google.com', TRUE);

INSERT INTO domain(id,name,description,active,backup) VALUES (2, 'microsoft.com','Main microsoft domain',FALSE,TRUE);
INSERT INTO ALIAS(domain_id,destination,redirect,active) VALUES(2,'adm@microsoft.com','administration@microsoft.com',TRUE);
INSERT INTO ALIAS(domain_id,destination,redirect,active) VALUES(2,'ceo@microsoft.com','snadella@microsoft.com',TRUE);
INSERT INTO ALIAS(domain_id,destination,redirect,active) VALUES(2,'founders@microsoft.com','bgates@microsoft.com',TRUE);
INSERT INTO ALIAS(domain_id,destination,redirect,active) VALUES(2,'founders@microsoft.com','pallen@microsoft.com',TRUE);
