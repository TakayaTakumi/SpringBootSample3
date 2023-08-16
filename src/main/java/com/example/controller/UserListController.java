package com.example.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.user.model.MUser;
import com.example.domain.user.service.UserService;
import com.example.form.UserListForm;


/*@Controller　フロント(外部)とビジネスロジックを繋げる場所
 *@RequestMapping 全体の処理（テーマパーク）の入り口 サイト全体の入り口部分*/
@Controller
@RequestMapping("/user")
public class UserListController {

	/*UserService.javaから*/
	@Autowired
	private UserService userService;
	/*javaConfig.javaから*/
	@Autowired
	private ModelMapper modelMapper;
	
	/*@GetMapping GETというのは文字通り「取得」の役割をはたす部分です。登録されているデータを取って来たりします。*/
	/**ユーザー一覧画面*/
	@GetMapping("/list")
	public String getUserList(@ModelAttribute UserListForm form,Model model){
		
		/*1.、mapメソッドを用いてフォームの内容から第二引数（今回はMUserクラス）のインスタンス(user)を新しく生成。
		 *2.userの各インスタンスフィールドについて、同名のものが第一引数（今回であればform）に存在するかどうかをチェック。
		 *3.もし存在していれば、formからuserに値をコピーする。
		 *  ちなみに、この処理は内部的にはゲッターとセッターを用いて行っているそうなので、
		 *  あらかじめ両者(formとuser)にゲッターとセッターが定義されている必要があります。*/
		//formをMUserクラスに変換
		MUser user = modelMapper.map(form, MUser.class);
		/*UserService.javaからユーザーの一覧を取得している*/
		//ユーザー検索
		List<MUser>userList = userService.getUsers(user);
		/*List<MUser>userListを("キー名",変数)で登録している　引数にModel modelを登録して言うr*/
		//Modelに登録
		model.addAttribute("userList",userList);//コントローラからビューにデータを渡すために使用
		
		//ユーザー一覧画面を表示
		return "user/list";
	}
	
	/**ユーザー検索処理*/
	@PostMapping("/list")
	public String postUserList(@ModelAttribute UserListForm form, Model model) {
		
		//formをMUserクラスに変換
		MUser user = modelMapper.map(form, MUser.class);
		
		//ユーザー検索
		List<MUser>userList = userService.getUsers(user);
		
		//Modelに登録
		model.addAttribute("userList",userList);
		
		//ユーザー一覧画面を表示
		return "user/list";
	}
}
