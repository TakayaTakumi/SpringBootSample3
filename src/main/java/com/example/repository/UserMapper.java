package com.example.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.domain.user.model.MUser;

/*1@Mapperアノテーションを付けたインタフェースのメソッドは、実際のSQLクエリやデータベース操作と対応しているとみなされます。
 *2 SQLをXMLファイルに記述し、Javaのインターフェースのメソッドを実行すると、メソッド名に対応するSQLが実行されます。
 *3MyBatisは、一般的なO/Rマッパーのようにデータベースのテーブル構造とオブジェクトをマッピングするのではなく、
 *　「SQL文とオブジェクトをマッピングする」点が特徴
 *4mybatisでリポジトリを作成するためにつける*/

@Mapper
public interface UserMapper {
	/*引数MUserはMUser.javaから引っ張ってきている()*/
	/**ユーザー登録*/
	public int insertOne(MUser user);

	/*public List<MUser> findMany();　select文の実行結果が複数になる場合はメソッドの戻り値をListにする*/
	/* 検索条件で一致するユーザーを取得 */
	/**ユーザー取得*/
	public List<MUser> findMany(MUser user);

	/*ユーザー一覧から選択したユーザーの詳細を別画面で表示*/
	/**ユーザー取得（1件）*/
	public MUser findOne(String userId);

	/*@Paramでパラメータのみ記述する。パラメータが1つの場合は、@Paramの記述は不要。
	2つ以上の場合は、SQLクエリ内のパラメータとメソッド引数の対応関係の設定のために記述必須。　
	@Paramアノテーションを付けることで指定した値をSQL内に埋め込むことができます。
	また、xmlファイルに#{パラメータ名}と指定することで、メソッドの引数とsql内のパラメーターとマッピングできるp266*/
	/**ユーザー更新(1件)*/
	public void updateOne(@Param("userId") String userId,
			@Param("password") String password,
			@Param("userName") String userName);

	/**ユーザー削除(1件)*/
	public int deleteOne(@Param("userId") String userId);

	/**ログインユーザー取得*/
	public MUser findLoginUser(String userId);
}
