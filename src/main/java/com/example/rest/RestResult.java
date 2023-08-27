package com.example.rest;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor//アノテートすることで、全てのフィールドを引数に受け取るコンストラクタを自動生成できる。
public class RestResult {

	/**リターンコード*/
	private int result;
	
	
	/**エラーマップ
	 * key:フィールド名
	 *value:エラーメッセージ
	 */
	private Map<String,String> errors;
	
	
}
