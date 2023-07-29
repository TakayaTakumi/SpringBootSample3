package com.example.repository;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.domain.user.model.MUser;

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
