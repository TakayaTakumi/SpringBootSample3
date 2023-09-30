'use strict';/*javascriptでuse strictを宣言すると、コードがstrict（厳格）モードで実行されるようになる。
               strictモードでは、より的確なエラーチェックが行われる。
               これまでエラーにならなかったような曖昧な実装がエラー扱いになります。*/


/*jQueryの(function($)の意味
これはjQueryを他のライブラリと干渉しないようにするためのカプセル化である*/
/** 画面 ロード 時 の 処理. */
jQuery(function($) {

	/* 【 対象要素.click( 関数 ) 】のように引数へクリック後に実行したい関数を指定します。
		(function(event) これはドラッグイベントが発生したときにブラウザが引数付きでfunctionを呼び出すのです。
	   その引数をプログラム側で受け取るために「event」という名前を付けているにすぎません。
	*/
	/** 更新 ボタン を 押し た とき の 処理. */
	$('#btn-update').click(function(event) {//detail.htmlの<button id="btn-update"と結びついている

		// ユーザー 更新 
		/**/
		updateUser();
	});

	/** 削除 ボタン を 押し た とき の 処理. */
	$('#btn-delete').click(function(event) {

		// ユーザー 削除 
		deleteUser();
	});
});

/*関数宣言　function 関数名(引数) { 処理 }で関数宣言している*/
/** ユーザー 更新 処理. */
function updateUser() {

	/*.serializeArray()   　　　　　　　serialize()もあるが今回は省く
	serializeArray()はJSON文字列を生成
	要素の追加などをしたい場合はserializeArray()が良い
	配列にhtml要素の name と value で構成されるオブジェクトが格納されて返却されます。*/
	// フォーム の 値 を 取得 detail.htmlのJS読込にあるform id="user-detail-form"の値を持ってきている
	var formData = $('#user-detail-form').serializeArray();//=代入演算子　javaと同じ
	//varはvariable、つまり変数の略で変数を宣言している

	//ajax通信
	$.ajax({
		type: "PUT",//どんな方法で？（GET / POSTなど）】GET	データ取得POST	データ作成 PUT	データ更新 DELETE	データ削除
		cache: false,//通信結果をキャッシュするか。デフォルトはtrue（ただし、dataType="script"/"jsonp"の場合はfalse）
		url: '/user/update',//リソースの URL を指定します。
		data: formData,//送信するデータ（「キー名: 値」のハッシュ形式）
		dataType: 'json',//応答データの種類（text、html、xml、json、jsonp、script）
	}).done(function(data) {/*成功処理のデータ　$.ajaxメソッドでダウンロードが失敗した時に実行するファンクションを示します。
	ファンクションのdateには、date.jsonのデータが保存されています。*/

		//ajax成功時の処理
		alert('ユーザーを更新しました')//alert( 画面に表示させたい値 )

		//ユーザー一覧画面にリダイレクト
		window.location.href = '/user/list';//現在表示しているページのアドレス（URL）を示す文字列です。 URL を示す文字列を代入すると、そのページにジャンプします。
	}).fail(function(jqXHR, textStatus, errorThrown) {/*エラー原因の取得は fail() の引数に以下の内容を入れることで取得できます。
	　　　　　　　　　　　　　　　　　　　　　　　　　　jqXHR
　　　　　　　　　　　　　　　　　　　　　　　　　　　　HTTPリクエストのステータスが取得できます
　　　　　　　　　　　　　　　　　　　　　　　　　　　　textStatus
　　　　　　　　　　　　　　　　　　　　　　　　　　　　タイムアウト、パースエラー（構文エラー）などのエラー情報が取得できます
　　　　　　　　　　　　　　　　　　　　　　　　　　　　errorThrown
　　　　　　　　　　　　　　　　　　　　　　　　　　　　例外情報が取得できます*/

		//ajax失敗時の処理
		alert('ユーザー更新に失敗しました');

	}).always(function() {

		//常に実行する処理

	});
}

/**ユーザー削除処理 */
function deleteUser() {
	//フォームの値を取得
	var formData = $('#user-detail-form').serializeArray();

	//ajax通信
	$.ajax({
		type: "DELETE",
		cache: false,
		url: 'user/delete',
		data: formData,
		dataType: 'json',
	}).done(function(data) {
		//ajax成功時の処理
		alert('ユーザーを削除しました');
		//ユーザー一覧画面
		window.location.href = '/user/list';
	}), fail(function(jqXHR, textStatus, errorThrown) {

		//ajax失敗時の処理
		alert('ユーザー削除に失敗しました');

	}).always(function() {
		//
	});
}