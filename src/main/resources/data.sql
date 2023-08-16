--INSERT INTOにつ続けて追加したいテーブル名を決める。テーブル名に続けてレコードを登録する列を指定　3章3
INSERT INTO employee
(
   id,
   name,
   age
)
--VALUESの後に登録したい値を指定する 3章3
VALUES
(
   '1',
   'Tom',
   30
);
/*ユーザーマスタ*/ INSERT INTO m_user
(
   user_id,
   password,
   user_name,
   birthday,
   age,
   gender,
   department_id,
   role/*役割の意味を持つ　ADMINやUSERが入る　UserServiceImpl.java ユーザーの部署や役割などの情報
   		roleについてわからなくなったら解体新書p226を見るといい*/
)
VALUES
(
   'system@co.jp',
   '$2a$10$LfINHF8oD5gEqyDTUVDYb.eWiuwS.XekWSU2oRnbG9U8tGd9n0gDq',
   'システム管理者',
   '2000-01-01',
   21,
   1,
   1,
   'ROLE_ADMIN'
),

(
   'user@co.jp',
   '$2a$10$LfINHF8oD5gEqyDTUVDYb.eWiuwS.XekWSU2oRnbG9U8tGd9n0gDq',
   'ユーザー1',
   '2000-01-01',
   21,
   2,
   2,
   'ROLE_GENERAL'
);
/*部署マスタ*/ INSERT INTO m_department
(
   department_id,
   department_name
)
VALUES
(
   1,
   'システム管理部'
),

(
   2,
   '営業部'
);
/*給料テーブル*/ INSERT INTO t_salary
(
   user_id,
   year_month,
   salary
)
VALUES
(
   'user@co.jp',
   '2020/11',
   280000
),

(
   'user@co.jp',
   '2020/12',
   290000
),

(
   'user@co.jp',
   '2021/01',
   300000
);