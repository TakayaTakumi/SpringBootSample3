package com.example.domain.user.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.domain.user.model.MUser;
import com.example.domain.user.service.UserService;


/*ユーザー認証のサービスを作るためには、UserDetailsServiceを実装したクラスを用意します。
 * 
 * DI可能(@Autowired可能)なコンポーネントとして扱うことができるようになります。
主に@Controllerを付与したコントローラー層から呼び出して使用することになります。*/
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private UserService service;
	/*Exceptionクラス、またはその派生クラスを継承することで、独自の例外クラスを定義することは可能
	 * 
	 * そして、 loadUserByUsernameメソッドをオーバーライドします。
	 * このメソッドの戻り値は、UserDetails インターフェース です。このインターフェースは、ユーザーを表しています。*/
	
	@Override//loadUserByUsername. ユーザー名に基づいてユーザーを見つけます
	public UserDetails loadUserByUsername(String username)throws UsernameNotFoundException{
		
		/*UserServiceImpl.javaに記載されている*/
		//ユーザー情報取得
		MUser loginUser = service.getLoginUser(username);
		
		//ユーザーが存在しない場合
		if(loginUser == null) {
			/*例外が発生することを例外を投げる（スローする）といいます。
			throw new 例外クラス名(引数)でインスタンスを作成しています。*/
			throw new UsernameNotFoundException("user not found");
		}
		
		//権限List作成 GrantedAuthority(付与された権限という意味)
		GrantedAuthority authority = new SimpleGrantedAuthority(loginUser.getRole());
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(authority);
		
		
		//UaerDetails生成
		UserDetails userDetails = (UserDetails) new User(loginUser.getUserId(),loginUser.getPassword(),authorities);
		
		return userDetails;
	}
}