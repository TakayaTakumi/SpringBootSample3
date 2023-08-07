package com.example.hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller //ユーザーによるテキストボックスへの入力やボタン押下名ｄのイベントに対するハンドラとなり、該当のモデルのメソッドを呼び出す
public class HelloController {

	@Autowired
	private HelloService service;

	@GetMapping("/hello") //URLの最後(/hello)http～/hello　@GetMappingはメソッドとGETの処理を行うURLを紐づける役割を担います。
	public String getHello() {
		//hello.htmlに画面遷移
		return "hello"; //正常に動いたら"hello" hello.htmlに遷移し画面を移す
	}

	/*@PostMappingはメソッドとPOSTの処理を行うURLを行うURLを紐付ける役割 
	 *POSTはデータの登録、会員登録フォームから情報を入力してDBに登録する*/
	@PostMapping("/hello")
	public String postRequest(@RequestParam("text1") String str,
			Model model) { /*@RequestParam(text1)でhello.htmlのname=text1と一致するようにする
							*と結びつき入力された値が@RequestParamに渡され変数strに格納される
							 ModelクラスはControllerからView（画面）へ変数を渡すためのもの*/

		//画面から受け取った文字列をModelに登録	
		/*@RequestParam(text1)に渡された値をstrに渡し
		キー名のsampleが呼ばれるとstrに格納されている値が出力される*/
		model.addAttribute("sample", str);
		//response.htmに画面遷移
		return "hello/response";
	}

	@PostMapping("/hello/db")
	public String postDbRequest(@RequestParam("text2") String id, Model model) {

		//1件検索

		Employee employee = service.getEmployee(id);

		//検索結果をModelに登録

		model.addAttribute("employee", employee);

		//db.htmlに画面を遷移
		return "hello/db";
	}
}
