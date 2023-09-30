package com.example.applicatioln.service.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect //Aspect(アスペクト) 複数のクラスにまたがる関心事をモジュール化したもの(まとめたもの)。
@Component //Spring MVCに限らず、SpringのDIコンテナにbeanとして登録したいクラスへ付与する。
@Slf4j
public class LogAspect {

	/**
	 * サービスの実行前にログ出力する
	 * 対象:[UserService]をクラス名に含んでいる
	 */

	/*∗ (*)アスタリスクを使用すると、任意の文字列を表します。*/
	/*.. (ドット 2 文字) パッケージ部分では、ドット2文字が0個以上のパッケージを表します。
	 * メソッドの引数部分では、ドット2文字が0個以上の引数を表します。*/

	/*@Beforeが付与されたクラスは、execution内の条件に当てはまる場合、対象の処理の実行前に@Beforeメソッドが実行されます。*/
	@Before("execution(* *..*.*UserService.*(..))") //対象:[UserService]をクラス名に含んでいる

	/*JoinPoint(ジョインポイント)　Advice(下記参照)を挿入する場所。場所といってもソースの特定の位置というわけではなく、
	 * メソッド(やコンストラクタ)の実行前、メソッド(やコンストラクタ)の実行後、、といったように実行されるタイミングのこと。*/
	/*ジョインポイントを指定する場合、メソッドにJoinPointのアノテーションをつける必要がある 
	 * @Before @After @AfterReturning @Around @AfterThrowing*/
	public void startLog(JoinPoint jp) /*JoinPoint jp は、Spring AOPにおいてアスペクトが
										アドバイス（Advice）を適用するポイントを表すオブジェクトを指します。*/ {
		log.info("メソッド開始:" + jp.getSignature());
	}

	/** 
	 * サービスの実行後にログ出力する. 
	 * 対象:[UserService]をクラス名に含んでいる. */

	/*戻り値*：全ての戻り値を指定
	パッケー名∗..∗：全てのパッケージが対象
	クラス名∗Controller：末尾にControllerと付くクラスが対象
	メソッド名*：全ての戻り値を指定
	引数..：全ての引数が対象*/
	@After("execution(* *..*.*UserService.*(..))")
	public void endLog(JoinPoint jp) {
		log.info("メソッド終了:" + jp.getSignature());
	}

	//@AroundAOP実行対象の前後に処理のをいれることできる　
	/**コントローラーの実行前後にログを出力する*/
	//@Around("bean(*Controller)")//beanの指定 DIコンテナーに登録されているBean名でAOPの対象を指定できる

	/*@annotation Pointcut(実行対象)に[@annotation]を使うことにより指定されているメソッドがAOPの対象になる
	 * アノテーション名にパッケージ名を含める　
	 * @GetMappingがるいているメソッドをAOPの対象にしている*/
	//@Around("@annotation(org.springframework.web.bind.annotation.GetMapping)")

	/*@within　が指定しているアノテーションがついているクラスのすべてがAOPの対象になる(アノテーション名にはパッケージ名が含まれる)*/
	@Around("@within(org.springframework.stereotype.Controller)")
	public Object startLog(ProceedingJoinPoint jp) throws Throwable {

		// 開始 ログ 出力 
		log.info("メソッド開始:" + jp.getSignature());
		try {

			//@Around メソッド 実行 Object result = jp.proceed();とreturn result;左の処理を記載さいないとメソッドが正常に実行されない
			Object result = jp.proceed();

			// 終了 ログ 出力 
			log.info("メソッド終了:" + jp.getSignature());

			// 実行 結果 を 呼び出し 元 に 返却 

			return result;

		} catch (Exception e) {

			// エラー ログ 出力 

			log.error("メソッド異常終了:" + jp.getSignature());

			// エラー の 再 スロー 

			throw e;//エラーが出たらそのエラーを出力する
		}

	}

}
