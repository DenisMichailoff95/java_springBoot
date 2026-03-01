DELETE FROM employee;
DELETE FROM company;

INSERT INTO company(id, name) VALUES (1, 'Apple');
INSERT INTO company(id, name) VALUES (2, 'Google');

INSERT INTO employee(id, first_name, last_name, birth_date, salary, company_id)
VALUES (1, 'Ivan', 'Ivanov', '1990-01-01', 100000, 1);
INSERT INTO employee(id, first_name, last_name, birth_date, salary, company_id)
VALUES (2, 'Petr', 'Petrov', '1991-02-02', 120000, 2);