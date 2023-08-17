package com.example.domain.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.user.model.MUser;
import com.example.domain.user.service.UserService;
import com.example.repository.UserMapper;

/*世間一般にいうServiceクラスは「ドメイン知識を持った手続きクラス」を指すことが多いです*/
@Service
public class UserServiceImpl implements UserService {
	/*レポジトリー(UserMapper.java)のDIを注入*/
	/*UserMapperはクラス名またはインターフェイス名*/
	@Autowired
	private UserMapper mapper;

	@Autowired
	private PasswordEncoder encoder;

	/*MUaerはクラス名*/
	/**ユーザー登録*/
	@Override
	public void signup(MUser user) {
		/*　データの挿入
		 *　ユーザーのアプリケーション内での権限や責任を示すために使用されることが多いです。
		 *ここでは、一般的なユーザー役割を指定している可能性があります。*/
		user.setDepartmentId(1);//部署
		user.setRole("ROLE_GENERAL");//ロール

		//パスワード暗号化
		String rawPassword = user.getPassword();
		user.setPassword(encoder.encode(rawPassword));

		mapper.insertOne(user);
	}

	/*UserService.javaから持ってきている
	 * UserMapper.xmlからid=findMany()と結びつけることによりデータベースに格納されている値を持ってくる*/
	/**ユーザー取得*/
	@Override
	public List<MUser> getUsers(MUser user) {
		return mapper.findMany(user);
	}

	/*MUser.javaを呼び出し、returnでusermapper.xmlに移行(ユーザー一覧から選択したユーザーの詳細を別画面で表示)*/
	/**ユーザー取得(一件)*/
	@Override
	public MUser getUserOne(String userId) {
		return mapper.findOne(userId);
	}

	/**ユーザー更新(1件)*/
	@Transactional
	@Override
	public void updateUserOne(String userId,
			String password,
			String userName) {

		//パスワード暗号化

		String encryptPassword = encoder.encode(password);
		
		/*usermapper.xmlでupdateOneの値を格納する*/
		mapper.updateOne(userId, password, userName);

		//例外を発生させる
		//int i = 1/0;
	}
	/* deleteUserOneが発動するとmapper.deleteOneが発動すしcountに格納される*/
	/**ユーザー削除(1件)*/
	@Override
	public void deleteUserOne(String userId) {
		int count = mapper.deleteOne(userId);
	}

	/**ログインユーザー情報取得*/
	@Override
	public MUser getLoginUser(String userId) {
		return mapper.findLoginUser(userId);
	}
}
