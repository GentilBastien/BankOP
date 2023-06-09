INSERT INTO tables (id, id_category, name) VALUES (0, NULL, 'root');
INSERT INTO tables (id, id_category, name) VALUES (1, 0, 'vide');
INSERT INTO tables (id, id_category, name) VALUES (2, 0, 'scindes');
INSERT INTO tables (id, id_category, name) VALUES (3, 0, 'alimantation');
INSERT INTO tables (id, id_category, name) VALUES (4, 3, 'supermarches');
INSERT INTO tables (id, id_category, name) VALUES (5, 4, 'carrefour');
INSERT INTO tables (id, id_category, name) VALUES (6, 4, 'leclerc');
INSERT INTO tables (id, id_category, name) VALUES (7, 4, 'monoprix');
INSERT INTO tables (id, id_category, name) VALUES (8, 4, 'auchan');


INSERT INTO keywords (id, id_category, keyword) VALUES (0, 5, 'CARREFOUREXPRESS');
INSERT INTO keywords (id, id_category, keyword) VALUES (1, 5, 'CARREFOURDRIVE');
INSERT INTO keywords (id, id_category, keyword) VALUES (2, 5, 'CARREFOURPLUS');
INSERT INTO keywords (id, id_category, keyword) VALUES (3, 6, 'LECLERCMARKET');


INSERT INTO operations (id, id_mother, id_category, date, name, price, manually_categorized) VALUES (0, NULL, 3, '2022-05-02', 'VIR VIREMENT CREATION COMPTE', 60.0, 0);
INSERT INTO operations (id, id_mother, id_category, date, name, price, manually_categorized) VALUES (1, NULL, 3, '2022-05-02', 'VIR VIREMENT CREATION COMPTE', 60.0, 0);
INSERT INTO operations (id, id_mother, id_category, date, name, price, manually_categorized) VALUES (2, NULL, 3, '2022-05-02', 'VIR VIREMENT CREATION COMPTE', -9660.89, 0);
INSERT INTO operations (id, id_mother, id_category, date, name, price, manually_categorized) VALUES (3, NULL, 5, '2022-08-07', 'VIR Virement de Charles GATTACIECCA', 30.0, 0);
INSERT INTO operations (id, id_mother, id_category, date, name, price, manually_categorized) VALUES (4, NULL, 5, '2022-08-06', 'VIR Virement de Marie Pierre GATTACI', 100.0, 0);
INSERT INTO operations (id, id_mother, id_category, date, name, price, manually_categorized) VALUES (5, NULL, 5, '2022-07-18', '*FRAIS CB VISA WELCOME', -5.0, 0);
INSERT INTO operations (id, id_mother, id_category, date, name, price, manually_categorized) VALUES (6, NULL, 5, '2022-08-07', 'VIR Virement de Charles GATTACIECCA', 30.0, 0);
INSERT INTO operations (id, id_mother, id_category, date, name, price, manually_categorized) VALUES (7, NULL, 5, '2022-08-06', 'VIR Virement de Marie Pierre GATTACI', 100.0, 0);
INSERT INTO operations (id, id_mother, id_category, date, name, price, manually_categorized) VALUES (8, NULL, 2, '2022-07-18', 'CARREFOUR', -220.0, 0);
INSERT INTO operations (id, id_mother, id_category, date, name, price, manually_categorized) VALUES (9,    8, 0, '2022-07-18', 'CARREFOURDAC', -60.0, 0);
INSERT INTO operations (id, id_mother, id_category, date, name, price, manually_categorized) VALUES (10,   8, 5, '2022-07-18', 'CARREFOURALIM', -160.0, 0);