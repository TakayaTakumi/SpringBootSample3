package com.example.form;

import java.util.Date;
import java.util.List;

import com.example.domain.user.model.Department;
import com.example.domain.user.model.Salary;

import lombok.Data;
	/*UserDetailForm　訳　ユーザー詳細フォーム
	 * ユーザー詳細画面のフォームクラス MUserとは違う
	 * MUserから	＠Dataを使って持ってきている*/
@Data
public class UserDetailForm {

	private String userId;
	private String password;
	private String userName;
	private Date birthday;
	private Integer age;
	private Integer gender;
	private Department department;
	private List<Salary> salaryList;
}
