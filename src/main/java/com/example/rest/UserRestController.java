package com.example.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.domain.user.service.UserService;
import com.example.form.UserDetailForm;

@RestController
@RequestMapping("/user")
public class UserRestController {

	@Autowired
	private UserService userService;

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
