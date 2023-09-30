package com.example.applicatioln.service.aspect;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalControllAdvice {

	/*ControllerAdviceは、エラー処理などをコントローラーの共通処理としてまとめて定義するためのものです。
	 * ただし、以下のいずれかのアノテーションが付いたメソッドのみ、コントローラー間で共有できます。
	@ExceptionHandler @InitBinder @ModelAttribute*/
	/**データベース関連の例外処理*/
	@ExceptionHandler(DataAccessException.class)// ErrorAsect.java
	public String dataAccessExceptionHandler(DataAccessException e, Model model) {
		
		//空文字をセット
		model.addAttribute("error","");
		
		//メッセージをModelに登録
		model.addAttribute("message", "DataAccessExceptionが発生しました");
		
		//HTTPのエラーコード(500)をModelに登録
		model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR);
		
		return"error";
		
	}
	
	/**その他の例外処理*/
	@ExceptionHandler(Exception.class)//LogAspect.java
	public String exceptionHandler(Exception e, Model model) {
		
		//空文字をセット
		model.addAttribute("error","");
		
		//メッセージをModelに登録
		model.addAttribute("message", "Exceptionが発生しました");
		
		//Httpのエラーコード(500)をModelに登録
		model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR);
		
		return"error";
	}
}
