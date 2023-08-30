package com.example.domain.user.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.domain.user.model.MUser;
import com.example.domain.user.service.UserService;
import com.example.repository.UserRepository;

@Service
@Primary//同じインターフェースを実装したクラスがあるとどのクラスを実装したらいいのかわからなくなるためにつける。これをつけると優先してDIされる
public class UserServiceImpl2 implements UserService {

	//UserRepository.java
	@Autowired
	private UserRepository repository;

	/*SecurityConfig.java */
	@Autowired
	private PasswordEncoder encoder;

	/** ユーザー 登録 */
	@Transactional//ORマッピングしたくないフィールドにつける
	@Override
	public void signup(MUser user) {

		// 存在 チェック existsById:指定された ID を持つエンティティが存在するかどうかを返します
		boolean exists = repository.existsById(user.getUserId());

		if (exists) {
			throw new DataAccessException("ユーザーが既に存在") {//throw new～でインスタンスを生成している
			};
		}

		user.setDepartmentId(1);
		user.setRole("ROLE_GENERAL");

		// パスワード 暗号化 SecurityConfig.javaに記載
		String rawPassword = user.getPassword();
		user.setPassword(encoder.encode(rawPassword));

		// insert 
		repository.save(user);//save()　キーが存在する場合は更新、存在しない場合は登録
	}

	/** ユーザー 取得 */
	@Override
	public List<MUser> getUsers(MUser user) {
		
		//検索条件
				ExampleMatcher matcher = ExampleMatcher.matching()//and条件
						.withStringMatcher(StringMatcher.CONTAINING)//Like句
						.withIgnoreCase();//大文字・小文字の両方
				
		return repository.findAll(Example.of(user,matcher));
	}

	/** ユーザー 取得( 1 件) */
	@Override
	public MUser getUserOne(String userId) {
		//Optionalクラス（java.utilパッケージ）は、nullチェックを簡単化し、NullPointerExceptionの発生を未然に防ぐためのクラスです
		Optional<MUser> option = repository.findById(userId);
		MUser user = option.orElse(null);
		return user;
	}

	/** ユーザー 更新( 1 件) */
	@Transactional
	@Override
	public void updateUserOne(String userId, String password, String userName) {

		//パスワード暗号化
		String encryptPassword = encoder.encode(password);

		//ユーザー更新
		repository.updateUser(userId, encryptPassword, userName);
	}

	/** ユーザー 削除( 1 件) */
	@Transactional
	@Override
	public void deleteUserOne(String userId) {
		repository.deleteById(userId);
	}

	/** ログイン ユーザー 取得 */
	@Override
	public MUser getLoginUser(String userId) {
		
		//Optionalクラス（java.utilパッケージ）は、nullチェックを簡単化し、NullPointerExceptionの発生を未然に防ぐためのクラスです
		//Optional < MUser > option = repository.findById(userId);
		
		//Optionalに設定した値がnullでなければその値を、nullであればorElseで設定した値を取得します。
		//MUser user = option. orElse( null);
		return repository.findLoginUser(userId);

	}
}