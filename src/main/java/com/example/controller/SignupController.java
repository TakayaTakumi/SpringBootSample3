package com.example.controller;

import java.util.Locale;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.applicatioln.service.UserApplicationService;
import com.example.domain.user.model.MUser;
import com.example.domain.user.service.UserService;
import com.example.form.GroupOrder;
import com.example.form.SignupForm;

import lombok.extern.slf4j.Slf4j;

/*@Componentアノテーションを付けたクラスはコンポーネントして扱われる
 * すなわち,DIコンテナーによってインスタンス化され、シングルトンインスタンスとして管理*/
/*@RequestMappingとは[http://ドメイン/user/○○○]　主にクラスとクラスパスを紐づける役割を担います*/
/*@Slf4j ログを出力するアノテーション*/
@Controller
@RequestMapping("/user")
@Slf4j
public class SignupController {

	/*@Autowiredを書くことによりnewを使った記述をしてクラスを呼ばなくてもすむ*/
	@Autowired
	private UserApplicationService userApplicationService;

	/*UserServiceimpl.javaのModelMapperを呼び出している*/
	@Autowired
	private UserService userService;

	/*JavaConfig.javaのModelMapperを呼び出してる*/
	@Autowired
	private ModelMapper modelMapper;

	/*コントローラークラスの引数には渡される値など、色々なクラスの値を設定できる*/
	/*@ModelAttribute SignupForm formを追加するとmodel.addAttribute("signupForm",form)を自動で追加しているイメージ signupForm.java*/
	//ユーザー登録画面を表示
	@GetMapping("/signup")
	public String getSignup(Model model, Locale locale, @ModelAttribute SignupForm form) {

		/*　genderMapに変数userApplicationServiceに格納されているgetGenderMap()を読込*/
		//性別を取得
		Map<String, Integer> genderMap = userApplicationService.getGenderMap(locale);
		model.addAttribute("genderMap", genderMap);//modelにgenderMapを登録している

		//ユーザー登録画面に遷移
		return "user/signup";
	}

	/*メソッドとPOSTの処理を行うURLを紐づける役割　
	 * POSTはDBにデータを登録するためのアノテーション
	 * @Validatedを追加することで登録画面で入力ミスが起きた時にエラー内容が表示される　
	 * バリデーションの結果はBindingResultに格納される
	 * @Validated内に(GroupOrder.class)を追加することによりGroupOrder.javaのバリデーションの順番を反映できる*/
	/**ユーザー登録処理*/
	@PostMapping("/signup")
	public String postSignup(Model model, Locale locale, @ModelAttribute @Validated(GroupOrder.class) SignupForm form,
			BindingResult bindingResult) {

		/*BindingResultメソッドではバインドエラーやバリデーションエラーが発生
		 * hasErrors()の結果がtrueだった場合上記エラーが発生している*/
		//入力チェック結果　バリデーションエラーの場合
		if (bindingResult.hasErrors()) {
			//NG:ユーザー登録画面に戻ります
			return getSignup(model, locale, form);
		}

		/*Signupformで入力する値がlogで表示される　
		 *form.toString()は@ModelAttribute SignupForm formのformを書く*/
		log.info(form.toString());

		/*mapメソッドはModelMapperクラスのインスタンスメソッドでインスタンスフィールドの値をコピーしてくれる機能を持っています*/

		/*1.、mapメソッドを用いてフォームの内容から第二引数（今回はMUserクラス）のインスタンス(user)を新しく生成。
		 *2.userの各インスタンスフィールドについて、同名のものが第一引数（今回であればform）に存在するかどうかをチェック。
		 *3.もし存在していれば、formからuserに値をコピーする。
		 *  ちなみに、この処理は内部的にはゲッターとセッターを用いて行っているそうなので、
		 *  あらかじめ両者(formとuser)にゲッターとセッターが定義されている必要があります。*/
		//formをMUserクラスに変換
		MUser user = modelMapper.map(form, MUser.class);//form(会員登録のエラー), MUser.class会員登録の入力で格納される場所
		/*4.後の、足りない部分の情報を補う処理とDBへの保存は*/
		//ユーザー登録
		userService.signup(user);
		//ログイン画面にリダイレクト
		return "redirect:/login";
	}

	/**データベース関連の例外処理*/
	@ExceptionHandler(DataAccessException.class)
	public String dataAccessExceptionHandler(DataAccessException e, Model model) {

		//空文字をセット
		model.addAttribute("error", "");

		//メッセージをModelに登録
		model.addAttribute("message", "SignupControllerで例外が発生しました");

		//HTTPのえらコード(500)をModelに登録
		model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR);

		return "error";
	}

	/**その他の例外処理*/
	@ExceptionHandler(Exception.class)
	public String exceptionHandler(Exception e, Model model) {

		//空文字をセット
		model.addAttribute("error", "");

		//メッセージをModelに登録
		model.addAttribute("message", "SignupControllerで例外が発生しました");

		//HTTPのエラーコード(500)をModelに登録
		model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR);

		return "error";
	}
}
