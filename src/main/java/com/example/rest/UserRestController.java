package com.example.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.domain.user.model.MUser;
import com.example.domain.user.service.UserService;
import com.example.form.GroupOrder;
import com.example.form.SignupForm;
import com.example.form.UserDetailForm;
import com.example.form.UserListForm;

/*　@RestControllerアノテーション「コントローラークラスのメソッドで処理した結果を、
 *そのままレスポンスとしてブラウザへ送信する」ことを表すアノテーションです。
 *　本来は JSONや XMLなどを返す「 RESTインターフェース」で使うものですが、
 *「テキストを返す」機能を流用できるので、このアノテーションを利用します*/
@RestController
@RequestMapping("/user")
public class UserRestController {

	@Autowired
	private UserService userService;

	/*JavaConfig.javaのModelMapperを呼び出してる*/
	@Autowired
	private ModelMapper modelMapper;
	/*messages. propertiesから値を受け取る*/
	@Autowired
	private MessageSource messageSource;

	/**ユーザーを検索*/
	@GetMapping("/get/list")
	public List<MUser> getUserList(UserListForm form) {

		
		/*Mapper の map メソッドを使ってBeanマッピングを行います。
		メソッドを実行した後、MUserオブジェクトが新たに作成され、formの各フィールドの値が作成されたMUserオブジェクトにコピーされます
		https://qiita.com/euledge/items/482a113589015590cf19*/
		
		//formをMUserクラスに変換
		MUser user = modelMapper.map(form, MUser.class);//MUserだとほかの画面からもサービスを利用しやすいため

		//ユーザー一覧取得
		List<MUser> userList = userService.getUsers(user);
		return userList;
	}

	/**ユーザーを登録*/
	@PostMapping("/signup/rest")
	public RestResult postSignup(@Validated(GroupOrder.class) SignupForm form,
			BindingResult bindingResult, Locale locale) {

		// 入力 チェック 結果
		if (bindingResult.hasErrors()) {

			// チェック 結果: NG 
			Map<String, String> errors = new HashMap<>();

			
			/*BindingResultのgetFieldErrors()により、フィールド名とエラーメッセージのセットを取得できます。*/
			// エラーメッセージ 取得 
			for (FieldError error : bindingResult.getFieldErrors()) {/*バリデーション結果がNGとなったフィールドの名称は、
				　　　　　　　　　　　　　　　　　　　　　　　　　　　FieldErrorクラスから取得することができます。単項目チェックに引っかかったフィールド*/
				
				String message = messageSource.getMessage(error, locale);//getMessage( キー 名, 埋め込み パラメーター, ロ ケール)p121
				errors.put(error.getField(), message);//error.getField()フィールドの内容を返している
			}

			// エラー 結果 の 返却 
			return new RestResult(90, errors);//p487 ポイント2
		}

		// form を MUser クラス に 変換 
		//Mapper の map メソッドを使ってBeanマッピングを行います。
		//メソッドを実行した後、MUserオブジェクトが新たに作成され、formの各フィールドの値が作成されたMUserオブジェクトにコピーされます
		//https://qiita.com/euledge/items/482a113589015590cf19
		MUser user = modelMapper.map(form, MUser.class); 
		

		// ユーザー 登録 
		userService.signup(user);

		// 結果 の 返却 
		return new RestResult(0, null);
	}

	/** ユーザー を 更新 */
	@PutMapping("/update")
	public int updateUser(UserDetailForm form) {

		// ユーザー を 更新 
		userService.updateUserOne(form.getUserId(), form.getPassword(), form.getUserName());
		return 0;
	}

	/** ユーザー を 削除 */
	@DeleteMapping("/delete")
	public int deleteUser(UserDetailForm form) {

		// ユーザー を 削除 
		userService.deleteUserOne(form.getUserId());
		return 0;
	}
}
