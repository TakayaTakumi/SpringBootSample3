package com.example.applicatioln.service.LogAspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class LogAspect {

	/**
	 * サービスの実行前にログ出力する
	 * 対象:[UserService]をクラス名に含んでいる
	 */

	@Before("execution(* *..*.*UserService.*(..))")
	public void startLog(JoinPoint jp) {
		log.info("メソッド開始:" + jp.getSignature());
	}

	/** 
	 * サービスの実行後にログ出力する. 
	 * 対象:[UserService]をクラス名に含んでいる. 
	 */
	@After("execution(* *..*.*UserService.*(..))")
	public void endLog(JoinPoint jp) {
		log.info("メソッド終了:" + jp.getSignature());
	}

	/**コントローラーの実行前後にログを出力する*/
	//@Around("bean(*Controller)")
	//@Around("@annotation(org.springframework.web.bind.annotation.GetMapping)")
	@Around("@within(org.springframework.stereotype.Controller)")
	public Object startLog(ProceedingJoinPoint jp) throws Throwable {

		// 開始 ログ 出力 
		log.info("メソッド開始:" + jp.getSignature());
		try {
			// メソッド 実行 
			Object result = jp.proceed();
			
			// 終了 ログ 出力 
			log.info("メソッド終了:" + jp.getSignature());
			
			// 実行 結果 を 呼び出し 元 に 返却 
			
			return result;
		
		} catch (Exception e) {
		
			// エラー ログ 出力 
			
			log.error("メソッド異常終了:" + jp.getSignature());
			
			// エラー の 再 スロー 
			
			throw e;
		}

	}

}
