package com.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity // Spring Securityを使うための設定
@Configuration //WebSecurityのconfigureメソッドでは全体に対するセキュリティ設定を行います
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/*ここではSpringSecurityの制限を無視してほしい場所の指定をします
	例： 静的ファイルなどを置いている場所
	例えばロゴなどをサイトのヘッダーに置いている時など、これがないとロゴが出なくなってしまいます！
	
	@Override/**セキュリティの対象外を設定*/
	public void configure(WebSecurity web) throws Exception {

		//セキュリティを適用しない
		web
				.ignoring()
				.antMatchers("/webjars/**")
				.antMatchers("/css/**")
				.antMatchers("/js/**")
				.antMatchers("/h2-console/**");
		//,でつなげてもいい
	}

	/**セキュリティの各種設定*/
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//ここでは認証のための設定をしています
		// アクセス権限に関する設定
		//ログイン不要ページの設定
		http
				// /はアクセス制限をかけない
				.authorizeRequests()
				.antMatchers("/login").permitAll()//直リンクOK
				.antMatchers("/user/signup").permitAll()//直リンクOK
				.antMatchers("/user/signup/rest").permitAll()//直リンクOK
				.antMatchers("/admin").hasAuthority("ROLE_ADMIN")//制限制御
				.anyRequest().authenticated();//それ以外直リンクNG
		/*antMatchers("/signin") ← ログイン画面
		ここには全てのユーザーがアクセス可
		antMatchers("/admin") ← 管理画面
		ここには"ADMIN"権限がないとアクセスできない
		Userテーブルのroleのカラムに入っている"ROLE_ADMIN＂で認証されます
		.anyRequest().authenticated()
		全てのURLリクエストは認証されているユーザーしかアクセスできないという記述です*/

		//ログイン処理
		http
				.formLogin()
				.loginProcessingUrl("/login")//ログイン処理のパス th:action="@{/login}"
				.loginPage("/login")//ログインページの指定 ます。 ログイン画面のコントローラーの@GetMapping("/login")の部分と、パスを一致させます。
				.failureUrl("/login?error")//ログイン失敗時の遷移先
				.usernameParameter("userId")//ログインページのユーザーID userNameParameter("ユーザーIDのname属性")ログイン画面のユーザーID入力欄のname属性を設定します。
				.passwordParameter("password")//ログインページのパスワード passwordParameter("パスワードのname属性")ログイン画面のパスワード入力欄のname属性を設定します。
				.defaultSuccessUrl("/user/list", true);//成功後の遷移先 第2引数にtrueを指定すると、第1引数のパスに強制的に遷移します。
		/*ここでは認証に関わるパラメータを指定してます
		.loginProcessingUrl("/login")
		ログインの処理をするURL
		.loginPage("/login")
		ログイン画面のURL
		.failureUrl(""/login?error"")
		ログインに失敗した時のURL
		?errorとつけておくとthymeleafの方でエラーのメッセージを出すときに便利です
		.usernameParameter("userId").passwordParameter("password")
		ログイン画面のhtmlのinputのname属性を見に行っている
		.defaultSuccessUrl("/user/list", true)
		ログインが成功した時のURL
		第2引数のboolean
		true : ログイン画面した後必ずlistにとばされる
		false : (認証されてなくて一度ログイン画面に飛ばされても)ログインしたら指定したURLに飛んでくれる*/

		//ログアウト処理
		http
				.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutUrl("/logout")
				.logoutSuccessUrl("/login?logout");

		/*.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		ログアウトのURL
		.logoutSuccessUrl("/login?logout")*/
		
		
		/*CSRF（クロスサイトリクエストフォージェリ）はWebシステムを悪用したサイバー攻撃の一種です*/
		//CSRF対策を無効を無効に設定(一時的)
		//http.csrf().disable();

	}

	/**認証の設定*/
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		PasswordEncoder encoder = passwordEncoder();

		/*インメモリ認証とは、仮のユーザーIDとパスワードを用意してログインできるようにする機能です。*/
		//インメモリ認証
		/*
		auth
		.inMemoryAuthentication()
		.withUser("user")//userを追加
		.password(encoder.encode("user"))
		.roles("GENERAL")
		.and()
		.withUser("admin")//adminを追加
		.password(encoder.encode("admin"))
		.roles("ADMIN");
		*/

		// ユーザー データ で 認証 
		auth
				.userDetailsService(userDetailsService)
				.passwordEncoder(encoder);

	}
}

/*セキュリティ設定クラスを用意するためには、以下の設定を行います。
 * @EnableWebSecurityアノテーションをクラスに付ける。
 * @Configurationアノテーションをクラスに付ける。
 * WebSecurityConfigurerAdapterクラスを継承する。
 * WebsecurityConfigurerAdapterのメソッドをオーバーライドすることで、セキュリティ設定を変更することができます。p393*/
