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

CREATE TABLE app_user (
  id SERIAL PRIMARY KEY
, username VARCHAR(50) NOT NULL
, password VARCHAR(50) NOT NULL
, user_role VARCHAR(10) DEFAULT 'USER' CHECK(user_role IN ('USER','ADMIN'))
);

INSERT INTO app_user (
  username
, password
, user_role
)
VALUES (
  'ADMIN'
, 'ADMIN'
, 'ADMIN'
);

INSERT INTO app_user (
  username
, password
, user_role
)
VALUES (
  'USER1'
, 'USER1'
, 'USER'
);

COMMIT;
