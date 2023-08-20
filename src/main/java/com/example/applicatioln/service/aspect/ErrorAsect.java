package com.example.applicatioln.service.aspect;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

//Aspect(アスペクト) 複数のクラスにまたがる関心事をモジュール化したもの(まとめたもの)。
//@Componentアノテーションを付けたクラスはコンポーネントして扱われる
//@Slf4j ログを出力するアノテーション
@Aspect
@Component
@Slf4j
public class ErrorAsect {
	/*//value属性にはurlを設定する*/
	/*@AfterThrowing 例外発生時のAOPを実装できる*/ //@throwing 例外クラスのメソッドの引数を設定するthrowingNull(DataAccessException ex)のex
	
	/* *: 戻り値の型を指定。* は任意の型を表すワイルドカード。
       *..*..*: メソッド名を表す部分。* は任意の文字列。.. は0回以上の任意のパッケージ階層。
       (..): メソッドの引数を表す部分。(..) は任意の引数を受け入れることを意味する。
       &&はAかつBの意味*/
	
	/*(bean(*Controller) || bean(*Service) || bean(*Repository)):
      この部分は、特定のSpring Bean名に基づいて対象を絞り込むためのポイントカット表現です。
      (bean()): 特定のBeanを指定する部分。
      *Controller, *Service, *Repository: ワイルドカードを使用したBean名。* は任意の文字列を表す。*/
	
	@AfterThrowing(value = "execution(* *..*..*(..))&&"
			+ "(bean(*Controller) || bean(*Service) || bean(*Repository))", throwing = "ex")
	public void throwingNull(DataAccessException ex) {

		//例外処理の内容(ログ出力)
		log.error("DataAccessExceptionが発生しました");
	}
}
//例外処理を共通化している