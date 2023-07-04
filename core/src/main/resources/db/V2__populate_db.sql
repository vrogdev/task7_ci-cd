INSERT INTO `rest-gift-certificates-security`.tag (id, name) VALUES (1, 'tag_1');
INSERT INTO `rest-gift-certificates-security`.tag (id, name) VALUES (2, 'tag_2');
INSERT INTO `rest-gift-certificates-security`.tag (id, name) VALUES (3, 'tag_3');
INSERT INTO `rest-gift-certificates-security`.tag (id, name) VALUES (4, 'tag_4');
INSERT INTO `rest-gift-certificates-security`.tag (id, name) VALUES (5, 'tag_5');
INSERT INTO `rest-gift-certificates-security`.tag (id, name) VALUES (6, 'tag_5');
INSERT INTO `rest-gift-certificates-security`.tag (id, name) VALUES (7, 'tag_1');
INSERT INTO `rest-gift-certificates-security`.tag (id, name) VALUES (8, 'tag_6');
INSERT INTO `rest-gift-certificates-security`.tag (id, name) VALUES (9, 'tag_8');
INSERT INTO `rest-gift-certificates-security`.tag (id, name) VALUES (15, 'tag_10');

INSERT INTO `rest-gift-certificates-security`.gift_certificate (id, name, description, price, duration, create_date, last_update_date) VALUES (1, 'new certificate 1', 'some description', 252.5, 4, '2023-03-08 22:07:52', '2023-03-08 22:07:52');
INSERT INTO `rest-gift-certificates-security`.gift_certificate (id, name, description, price, duration, create_date, last_update_date) VALUES (9, 'new certificate 2', 'some description', 252.5, 4, '2023-03-09 00:21:17', '2023-03-09 00:21:17');
INSERT INTO `rest-gift-certificates-security`.gift_certificate (id, name, description, price, duration, create_date, last_update_date) VALUES (12, 'new certificate 3', 'some description', 252.5, 4, '2023-03-09 00:30:24', '2023-03-09 00:30:24');
INSERT INTO `rest-gift-certificates-security`.gift_certificate (id, name, description, price, duration, create_date, last_update_date) VALUES (14, 'new certificate 5', 'some description', 252.5, 4, '2023-03-09 00:32:39', '2023-03-09 00:32:39');
INSERT INTO `rest-gift-certificates-security`.gift_certificate (id, name, description, price, duration, create_date, last_update_date) VALUES (15, 'new certificate 6', 'some description', 252.5, 4, '2023-03-09 15:25:53', '2023-03-09 15:25:53');
INSERT INTO `rest-gift-certificates-security`.gift_certificate (id, name, description, price, duration, create_date, last_update_date) VALUES (17, 'new certificate 6', 'some description', 252.5, 4, '2023-03-09 15:29:03', '2023-03-09 15:29:03');
INSERT INTO `rest-gift-certificates-security`.gift_certificate (id, name, description, price, duration, create_date, last_update_date) VALUES (20, 'new certificate 6', 'some description', 252.5, 20, '2023-04-04 22:58:59', '2023-04-04 22:59:00');
INSERT INTO `rest-gift-certificates-security`.gift_certificate (id, name, description, price, duration, create_date, last_update_date) VALUES (27, 'new certificate 10', 'some description', 300, 4, '2023-04-06 00:10:24', '2023-04-06 00:10:24');
INSERT INTO `rest-gift-certificates-security`.gift_certificate (id, name, description, price, duration, create_date, last_update_date) VALUES (28, 'new certificate 10', 'some description', 300, 4, '2023-04-09 21:15:40', '2023-04-09 21:15:40');
INSERT INTO `rest-gift-certificates-security`.gift_certificate (id, name, description, price, duration, create_date, last_update_date) VALUES (29, 'new certificate 11', 'some description', 300, 4, '2023-04-09 21:31:11', '2023-04-09 21:31:11');

INSERT INTO `rest-gift-certificates-security`.gift_certificate_has_tag (gift_certificate_id, tag_id) VALUES (1, 1);
INSERT INTO `rest-gift-certificates-security`.gift_certificate_has_tag (gift_certificate_id, tag_id) VALUES (1, 2);
INSERT INTO `rest-gift-certificates-security`.gift_certificate_has_tag (gift_certificate_id, tag_id) VALUES (9, 4);
INSERT INTO `rest-gift-certificates-security`.gift_certificate_has_tag (gift_certificate_id, tag_id) VALUES (12, 4);
INSERT INTO `rest-gift-certificates-security`.gift_certificate_has_tag (gift_certificate_id, tag_id) VALUES (14, 5);
INSERT INTO `rest-gift-certificates-security`.gift_certificate_has_tag (gift_certificate_id, tag_id) VALUES (15, 6);
INSERT INTO `rest-gift-certificates-security`.gift_certificate_has_tag (gift_certificate_id, tag_id) VALUES (17, 7);
INSERT INTO `rest-gift-certificates-security`.gift_certificate_has_tag (gift_certificate_id, tag_id) VALUES (20, 7);
INSERT INTO `rest-gift-certificates-security`.gift_certificate_has_tag (gift_certificate_id, tag_id) VALUES (27, 8);
INSERT INTO `rest-gift-certificates-security`.gift_certificate_has_tag (gift_certificate_id, tag_id) VALUES (27, 15);

INSERT INTO `rest-gift-certificates-security`.user (id, name, username, password, role) VALUES (1, 'Administrator', 'admin', '$2y$12$KFn4Gb1egS/XYyyVJOiIMeYWzCrM.E4Fcqwhe7kZisks0cx7Z1y0q', 'ADMIN');
INSERT INTO `rest-gift-certificates-security`.user (id, name, username, password, role) VALUES (2, 'User', 'user', '$2y$12$VZCQawLnR/DGEty8ccjGq.CdezeyZ8.ciW97qwlENfWvtRKaUkEkm','USER');
INSERT INTO `rest-gift-certificates-security`.user (id, name, username, password, role) VALUES (3, 'Dmytro', 'dmytro','$2y$12$6rctOhP4.omnKnwRSKIcPuXcy7COXGGO4T1B7ytHNVWVAHkxo7mP2','USER');
INSERT INTO `rest-gift-certificates-security`.user (id, name, username, password, role) VALUES (4, 'Oleksandr', 'oleksandr','$2y$12$SSAGUvwlALmKqW77sPqgKexVrgXUnQ9Xggew4WBU0BJDZlF4NVczu','USER');
INSERT INTO `rest-gift-certificates-security`.user (id, name, username, password, role) VALUES (5, 'Kyrylo', 'kyrylo','$2y$12$7PLOF5fzLFyVI1Wm1iRusu53Z4YKJJ/r.ATsaSSkTo45DfGz6EFCi','USER');
INSERT INTO `rest-gift-certificates-security`.user (id, name, username, password, role) VALUES (6, 'Ivan', 'ivan','$2y$12$dJYcxuts39fZcLzu.ocAF.ZQrVht3dTYP.fnRg01wNDJlyeHD5WCa','USER');
INSERT INTO `rest-gift-certificates-security`.user (id, name, username, password, role) VALUES (7, 'Username1', 'username1','$2y$12$xUltThCQQA.GX9IMrHU.R.iF8NeA1CaFcUf0ZgQejDBoZBkaOzLAO','USER');
INSERT INTO `rest-gift-certificates-security`.user (id, name, username, password, role) VALUES (8, 'Username2', 'username2','$2y$12$AJXoqvkJbZy2purW6EUTK.tYhP75KW7bpuWCRVdLwhvVI10XkhI.a','USER');

INSERT INTO `rest-gift-certificates-security`.orders (id, cost, purchase_time, gift_certificate_id, user_id) VALUES (1, 252.5, '2023-04-05 03:14:38', 1, 1);
INSERT INTO `rest-gift-certificates-security`.orders (id, cost, purchase_time, gift_certificate_id, user_id) VALUES (2, 252.5, '2023-04-05 03:17:00', 9, 1);
INSERT INTO `rest-gift-certificates-security`.orders (id, cost, purchase_time, gift_certificate_id, user_id) VALUES (3, 252.5, '2023-04-05 03:19:34', 9, 2);
INSERT INTO `rest-gift-certificates-security`.orders (id, cost, purchase_time, gift_certificate_id, user_id) VALUES (4, 252.5, '2023-04-05 03:20:45', 12, 3);
INSERT INTO `rest-gift-certificates-security`.orders (id, cost, purchase_time, gift_certificate_id, user_id) VALUES (5, 252.5, '2023-04-05 03:23:06', 12, 2);
INSERT INTO `rest-gift-certificates-security`.orders (id, cost, purchase_time, gift_certificate_id, user_id) VALUES (7, 252.5, '2023-04-05 03:25:00', 12, 4);
INSERT INTO `rest-gift-certificates-security`.orders (id, cost, purchase_time, gift_certificate_id, user_id) VALUES (8, 252.5, '2023-04-13 22:35:28', 9, 4);