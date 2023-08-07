package com.example.hello;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/*@RepositoryはDBとやりとりするクラス、つまりDAOクラスに付与するアノテーションです。
@Componentと同じく、Spirngのコンポーネントとして認識され、ApplicationContextに登録されることで、DI対象のクラスとなります。

DOAクラスとはデータベースへのアクセスを行うクラスを作り、そのクラスを通してデータベースへアクセスするデザインパターンです。 */
@Repository
public class HelloRepository { //DB操作のための関数を、HelloRepositoryクラスのインスタンスメソッドとして定義

	@Autowired //これを記述するだけでほかのクラスを呼び出すことができる
	private JdbcTemplate jdbcTemplate;//jdbcとはJavaのプログラムとデータベースの間に入って仲立ちをしてくれる部品（に関する取り決め）インスタンスフィールド定義

	//SELECT文
	//結果をMapで取得する。
	//MapのキーにはSELECT結果のカラム名、Mapの値にはSELECT結果の値が入る
	public Map<String, Object> findById(String id) {

		//SELECT
		/*SELECT文の構文
		SELECT [取り出す列]
		FROM [テーブル名]
		WHERE [検索条件]
		GROUP BY [グループ化項目]
		HAVING [グループ化の条件]
		ORDER BY [並べる形式（昇順、降順など）]
		/＊employeeテーブルに対してidで検索をかけています。
		 * ちなみに、FROMとWHEREの前（もしくは*とemployeeのあと）には半角スペースが必須です。
		 * これがないと「employeeとwhere」や「*とFROM」が一続きの単語として扱われるので、
		 * クエリの対象がemployeeWHEREテーブルという実際には存在しないテーブルになります。
		*/

		String query = "SELECT *"
				+ " FROM employee"
				+ " WHERE id=?";

		//検索実行
		/*インスタンスメソッドの引数として与えたidと上記のquery文をもとにemployeeインスタンスを検索し、
		 * employeeという変数に代入しています。
		 * queryForMapはJdbcTemplateクラスのインスタンスメソッドの一つで、Mapを取得するためのものです。
		 */
		Map<String, Object> employee = jdbcTemplate.queryForMap(query, id);

		return employee;
	}
}
