DROP TABLE product;

CREATE TABLE product (
  id SERIAL PRIMARY KEY
, name VARCHAR(50) NOT NULL
, price FLOAT DEFAULT 0
, add_date TIMESTAMP DEFAULT NOW()
, picture_path VARCHAR(256)
);

INSERT INTO product (
  name
, price
, picture_path
)
VALUES (
   'Телевизор'
 , 18999
 , 'https://i1.rozetka.ua/goods/1732934/samsung_ue24h4070auxua_images_1732934935.jpg'
);

INSERT INTO product (
  name
, price
, picture_path
)
VALUES (
   'Утюг'
 , 3599
 , 'https://i1.rozetka.ua/goods/1841534/13777255_images_1841534676.jpg'
);

INSERT INTO product (
  name
, price
, picture_path
)
VALUES (
   'Холодильник'
 , 21999
 , 'https://i1.rozetka.ua/goods/3672683/indesit_ibs_15_aa_ua_images_3672683056.jpg'
);

INSERT INTO product (
  name
, price
, picture_path
)
VALUES (
   'Пылесос'
 , 699
 , 'https://img.mvideo.ru/Pdb/20037982b.jpg'
);


COMMIT;

DROP TABLE app_user;

CREATE TABLE app_user (
  id SERIAL PRIMARY KEY
, username VARCHAR(50) NOT NULL
, password VARCHAR(32) NOT NULL
, salt VARCHAR(36)
, user_role VARCHAR(10) DEFAULT 'USER' CHECK(user_role IN ('USER','ADMIN'))
);

INSERT INTO app_user (
  username
, password
, salt
, user_role
)
VALUES (
  'ADMIN'
, '208566b8daa47833891acc0c0a8ed38e' /* admin */
, '8265837c-62a3-4275-8c44-59812f2a338f'
, 'ADMIN'
);

INSERT INTO app_user (
  username
, password
, salt
, user_role
)
VALUES (
  'USER1'
, '46af3806bc674638632ca17f789bd5ad' /* user1 */
, 'afbf8dd1-4f79-4d70-b929-2decfaafc3f1'
, 'USER'
);

COMMIT;
