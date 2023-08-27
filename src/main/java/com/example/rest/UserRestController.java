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

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private MessageSource messageSource;

	
	/**ユーザーを検索*/
	@GetMapping("/get/list")
	public List<MUser>getUserList(UserListForm form){
		
		//formをMUserクラスに変換
		MUser user = modelMapper.map(form, MUser.class);
		
		//ユーザー一覧取得
		List<MUser>userList = userService.getUsers(user);
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

			// エラーメッセージ 取得 

			for (FieldError error : bindingResult.getFieldErrors()) {
				String message = messageSource.getMessage(error, locale);
				errors.put(error.getField(), message);
			}

			// エラー 結果 の 返却 
			return new RestResult(90, errors);
		}

		// form を MUser クラス に 変換 
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
