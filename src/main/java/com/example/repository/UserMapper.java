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
public interface UserMapper{

	/**ユーザー登録*/
	public int insertOne(MUser user);
	
	
	/**ユーザー取得*/
	public List<MUser> findMany(MUser user);
	
	/**ユーザー取得（1件）*/
	public MUser findOne(String userId);
	
	/**ユーザー更新(1件)*/
	public void updateOne(@Param("userId") String userId,
			@Param("password") String password, 
			@Param("userName") String userName);
	
	/**ユーザー削除(1件)*/
	public int deleteOne(@Param("userId") String userId);


	/**ログインユーザー取得*/
	public MUser findLoginUser(String userId);
}
