package com.example.domain.user.service;

import java.util.List;

import com.example.domain.user.model.MUser;
/*UserService.javaはユーザーサービスの操作を定義するインターフェースであり、UserServiceImpl.javaはその具体的な実装を提供するクラスです。
 * インターフェースと実装クラスの分離により、プログラムの柔軟性、拡張性、テスト容易性などの利点が得られます。*/
public interface UserService {
/**ユーザー登録*/
	public void signup(MUser user);
	/*xmlからの値を格納する( resultType="MUser"と結びついている)*/
	/**ユーザー取得*/
	public List<MUser>getUsers(MUser user);
	
	/**ユーザー取得(一件)*/
	public MUser getUserOne(String userId);
	
	/**ユーザ更新(1件)*/
	public void updateUserOne(String userId,
			String password,
			String userName);
	
	/**ユーザー削除(1件)*/
	public void deleteUserOne(String userId);
	
	/**ログインユーザー情報取得*/
	public MUser getLoginUser(String userId);
}
