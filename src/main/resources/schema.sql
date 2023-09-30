CREATE TABLE IF NOT EXISTS employee /*テーブルがない場合は作成しなさいという意味 3章3*/
(
   id VARCHAR (50) PRIMARY KEY,
   -- 列名 データ型 PRIMARY KEY   PRIMARY KEYは重複しないもの　3章3
   name VARCHAR (50),
   --VARCHER(50)の様に定義した場合は50以下の長さであれば文字列を格納できる　3章3
   age INT
);
/*CREATE TABLE IF NOT EXISTS　テーブルがない場合は作成しなさいということ
ユーザーマスタ*/ 
CREATE TABLE IF NOT EXISTS m_user
(
   user_id VARCHAR (50) PRIMARY KEY,
   password VARCHAR (100),
   user_name VARCHAR (50),
   birthday DATE,/*DATE型　日付を格納するのに使用するデータ型です*/
   age INT,
   gender INT,
   department_id INT,
   role VARCHAR (50)
);
/*部署マスタ*/ CREATE TABLE IF NOT EXISTS m_department
(
   department_id INT PRIMARY KEY,
   department_name VARCHAR (50)
);
/*給料テーブル*/ CREATE TABLE IF NOT EXISTS t_salary
(
   user_id VARCHAR (50),
   year_month VARCHAR (50),
   salary INT,
   PRIMARY KEY(user_id,year_month)/*PRIMARY KEY(user_id,year_month)　('user@co.jp','2020/11'),(user@co.jp','2020/12')があるがidとmonth二つでをワンセットで扱う*/
);