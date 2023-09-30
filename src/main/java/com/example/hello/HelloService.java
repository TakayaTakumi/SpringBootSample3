package com.example.hello;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*@Serviceはビジネスロジックを記述するクラスに付与する　ＳpringＢootアプリケーションの起動時に、コンポネントスキャンによって認識され、DIコンテナに登録されます。
 * DI可能(@Autowired可能)なコンポーネントとして扱うことができるようになります。
　 主に@Controllerを付与したコントローラー層から呼び出して使用することになります。
 * */
@Service
public class HelloService {

	/*クラスHelloRepositoryから値を変数repositoryに格納
	*/
	@Autowired
	private HelloRepository repository;

	//コントローラーで呼び出される
	/**従業員を一人追加する*/
	public Employee getEmployee(String id) {

		/*Map<String, Object>とは
		 * {性別="男性", 身長=171, 体重=61, スリーサイズ=**}」の場合
		 * 「性別」「身長」「体重」「スリーサイズ」はString型で、
		「"男性"」「171」「61」「**」はObject型（すべての型が入ることが許される）
		と解釈される*/
		/*private HelloRepository repositoryに格納されているfindBy(id)をmapに格納している*/
		//検索
		Map<String, Object> map = repository.findById(id);

		//Mapから値を取得
		String employeeId = (String) map.get("id");

		String name = (String) map.get("name");

		int age = (Integer) map.get("age");

		//employeeという新しい変数を宣言している
		//Employeeクラスに上記mapで取得した値をセット(1,Tom,30)
		Employee employee = new Employee();
		//String employeeId = (String) map.get("id");

		employee.setEmployeeId(employeeId);
		//String name = (String) map.get("name");

		employee.setEmployeeName(name);
		//int age = (Integer) map.get("age");

		employee.setEmployeeAge(age);

		return employee;
	}

}
/*上記のgetEmployeeメソッドは、基本的にコントローラで呼び出されます。

何をしているのかというと、ビューに渡すためのインスタンスを作成しているんですね。

例えば、employeeテーブルに(id, name, age) がそれぞれ("1", "Tom", 30)であるレコードがあったとします。

これを表示するために、まずは空のEmployeeインスタンスを作ってemployeeという変数に代入します。

そして、findByIdメソッドで取得したid, name, ageをそれぞれセッターで埋め込むことによって、employeeというインスタンスが完成します。*/