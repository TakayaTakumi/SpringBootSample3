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
	
	
	@Autowired
	private UserService userService;
	
	
	@Autowired
	private ModelMapper modelMapper;
	
	/*コントローラークラスの引数には渡される値など、色々なクラスの値を設定できる*/
	/*@ModelAttribute SignupForm formを追加するとmodel.addAttribute("signupForm",form)を自動で追加しているイメージ*/
	//ユーザー登録画面を表示
	@GetMapping("/signup")
	public String getSignup(Model model,Locale locale,@ModelAttribute SignupForm form) {
		
		/*　genderMapに変数userApplicationServiceに格納されているgetGenderMap()を読込*/
		//性別を取得
		Map<String,Integer> genderMap = userApplicationService.getGenderMap(locale);
		model.addAttribute("genderMap",genderMap);//modelにgenderMapを登録している
		
		
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
	public String postSignup(Model model, Locale locale,@ModelAttribute @Validated(GroupOrder.class) SignupForm form,BindingResult bindingResult) {
		
		/*BindingResultメソッドではバインドエラーやバリデーションエラーが発生
		 * hasErrors()の結果がtrueだった場合上記エラーが発生している*/
		//入力チェック結果　バリデーションエラーの場合
		if(bindingResult.hasErrors()) {
			//NG:ユーザー登録画面に戻ります
			return getSignup(model,locale,form);
		}
		
		/*Signupformで入力する値がlogで表示される　
		 *form.toString()は@ModelAttribute SignupForm formのformを書く*/
		log.info(form.toString());
		
		
		//formをMUserクラスに変換
		MUser user = modelMapper.map(form, MUser.class);
		
		
		//ユーザー登録
		userService.signup(user);
		//ログイン画面にリダイレクト
		return "redirect:/login";
	}
	
	/**デーｔベース関連の例外処理*/
	@ExceptionHandler(DataAccessException.class)
	public String dataAccessExceptionHandler(DataAccessException e,Model model) {
		
		//空文字をセット
		model.addAttribute("error","");
		
		//メッセージをModelに登録
		model.addAttribute("message","SignupControllerで例外が発生しました");
		
		//HTTPのえらコード(500)をModelに登録
		model.addAttribute("status",HttpStatus.INTERNAL_SERVER_ERROR);
		
		return"error";
	}
	
	
	/**その他の例外処理*/
	@ExceptionHandler(Exception.class)
	public String exceptionHandler(Exception e,Model model) {
		
		//空文字をセット
		model.addAttribute("error","");
		
		//メッセージをModelに登録
		model.addAttribute("message","SignupControllerで例外が発生しました");
		
		//HTTPのエラーコード(500)をModelに登録
		model.addAttribute("status",HttpStatus.INTERNAL_SERVER_ERROR);
		
		return"error";
	}
}
