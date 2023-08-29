package com.example.repository;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.domain.user.model.MUser;
/*JPAでリポジトリーを生成する場合、JpaRepositoryを継承します。JpaRepositoryのジェネリックスには、以下の型を指定します。*/
/*extends JpaRepository
クラスは、このように「JpaRepository」というインターフェイスを継承しています。
これは非常に重要です。これを継承することで、自動的に必要なメソッドなどが組み込まれるようになります。

<MUser, String>
 * < 扱うエンティティ・クラス , プライマリキーの型 >
この２種類のタイプを総称型で指定することで、そのエンティティに特化したリポジトリとして扱われるようになるのです。*/
public interface UserRepository extends JpaRepository<MUser, String> {
	/** ログイン ユーザー 検索 */
	@Query("select user"
			+ " from MUser user"
			+ " where userId = :userId")
	public MUser findLoginUser(@Param("userId") String userId);

	/** ユーザー 更新 */
	@Modifying
	@Query("update MUser"
			+ " set"
			+ " password = :password"
			+ " , userName = :userName"
			+ " where"
			+ " userId = :userId")

	public Integer updateUser(@Param("userId") String userId,
			@Param("password") String password,
			@Param("userName") String userName);
}
