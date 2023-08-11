package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.extern.slf4j.Slf4j;


/*@Controller　フロント(外部)とビジネスロジックを繋げる場所
 *@Slf4j ログを出力するアノテーション */
@Controller
@Slf4j
public class LogoutControler {


	/*@PostMappingはメソッドとPOSTの処理を行うURLを紐づける役割を担います。
	  POSTは「データの登録」として理解すると良いでしょう。会員登録フォームから情報を入力して、DBに登録する際に利用します。*/
	/**ログイン画面にリダイレクト*/
	@PostMapping("/logout")
	public String postLogout() {
		log.info("ログアウト");
		return "redirect:/login";
	}
}
