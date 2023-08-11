package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/*@controllerを付けたコンポーネントとして扱われる
 * DIコンテナーによってインスタンス化され、シングルトンインスタンスとして管理されるようになる*/
@Controller
public class LoginController {

	/*RequestMappingアノテーションのGETリクエスト用のアノテーション*/
	/*login配下にあるlogin.htmlを表示*/
	/**ログイン画面を表示*/
	@GetMapping("/login")
	public String getLogin() {
		
		return "login/login";
	}
	
	/*@PostMapping 新しいデータを登録する役割を果たす。*/
	/**ユーザー一覧画面にリダイレクト*/
	@PostMapping("/login")
	public String postLogin() {
		return "redirect:/user/list";
	}
	
}
