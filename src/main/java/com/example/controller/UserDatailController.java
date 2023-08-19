package com.example.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.user.model.MUser;
import com.example.domain.user.service.UserService;
import com.example.form.UserDetailForm;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/user")
public class UserDatailController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	/*list.htmlの詳細ボタンを押すとgetMappingが発動する　detail.htmlが映る
	 * ユーザー詳細画面を表示*/
	@GetMapping("/detail/{userId:.+}")/*@PathVariable("userId")には{userId:.+}の変数名を入れる*/
	public String getUser(UserDetailForm form,Model model,@PathVariable("userId")String userId) {
		
		
		//ユーザーを1件取得
		MUser user = userService.getUserOne(userId);
		user.setPassword(null);/*passwordにnullが入っているのはセキュリティー上の保護のため表示されたらまずいから*/
		/*modelMapper.mapメソッドを使うことによりフィールドの内容を簡単にコピーできる
		 * 画面に変更があっても、サービスの修正が不要になる。他の画面からもサービスを再利用できるようになる。*/
		//MUserをformに変換
		form = modelMapper.map(user, UserDetailForm.class);
		form.setSalaryList(user.getSalaryList());//MUser.java(MUser userと定義している)→Salary.javaから給料情報をgetしている
												 //UserDetailForm.javaのsetSalaryListにsetしている
		
		//Modelに登録
		model.addAttribute("userDetailForm", form);
		
		//ユーザー詳細画面を表示
		return"user/detail";
	}
	
	/*params属性にはdetail.htmlのbuttonタグのname属性と結びつけることによって
	 * コントローラーで受け取るメソッドを変更することができる*/
	/**ユーザー更新処理*/
	@PostMapping(value ="/detail",params="update")//value属性にはurlを設定する
	public String updateUser(UserDetailForm form, Model model) {
		
		
		try {
		//ユーザーを更新
		userService.updateUserOne(form.getUserId(),
				form.getPassword(),
				form.getUserName());
		}catch(Exception e) {
			log.error("ユーザー更新でエラー",e);
		}
		
		//ユーザー一覧画面にリダイレクト
		return "redirect:/user/list";
	}
	
	/*params属性にはdetail.htmlのbuttonタグのname属性と結びつけることによって
	 * コントローラーで受け取るメソッドを変更することができる*/
	/**ユーザー削除処理*/
	@PostMapping(value = "/detail",params ="delete")//value属性にはurlを設定する
	public String deleteUser(UserDetailForm form, Model model) {
		
		//ユーザーを削除
		userService.deleteUserOne(form.getUserId());
		
		//ユーザー一覧画面にリダイレクト
		return "redirect:/user/list";
	}
}
