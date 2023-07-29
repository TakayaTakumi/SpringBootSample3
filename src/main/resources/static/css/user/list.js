'use strict';


var userData = null; // ユーザー データ 
var table = null; // DataTables オブジェクト 

/** 画面 ロード 時 の 処理. */
jQuery(function($) {

	// DataTables の 初期化 
	createDataTables();

	/**検索ボタンを押したときの処理. */
	$('#btn-search').click(function(event) {

		// 検索 
		search();
	});
});

/** 検索 処理. */
function search() {

	// form の 値 を 取得 
	var formData = $('#user-search-form').serialize();

	// ajax 通信 
	$.ajax({
		type: "GET",
		url: '/user/get/list',
		data: formData,
		dataType: 'json',
		contentType: 'application/json;charset=UTF-8',
		cache: false,
		timeout: 5000,
	}).done(function(data) {

		// ajax 成功 時 の 処理 
		console.log(data);

		// JSON を 変数 に 入れる 
		userData = data;

		// DataTables 作成 
		createDataTables();
	}).fail(function(jqXHR, textStatus, errorThrown) {

		// ajax 失敗 時 の 処理 
		alert('検索処理に失敗しました');


	}).always(function() {

		// 常に 実行 する 処理( 特に なし) 
	});
}



/** DataTables 作成. */
function createDataTables() {

	// 既に DataTables が 作成 さ れ て いる 場合 
	if (table !== null) {

		// DataTables 破棄 
		table.destroy();
	}

	// DataTables 作成 
	table = $('#user-list-table').DataTable({

		// 日本語 化 
		language: { url: '/webjars/datatables-plugins/i18 n/Japanese.json' },

		//データ の セット 
		data: userData,

		//データ と 列 の マッピング 
		columns: [
			{ data: 'userId' },// ユーザー ID 
			{ data: 'userName' }, // ユーザー 名 
			{
				data: 'birthday', // 誕生日 
				render: function(data, type, row) {
					var date = new Date(data);
					var year = date.getFullYear();
					var month = date.getMonth() + 1;
					var date = date.getDate(); return year + '/' + month + '/' + date;
				}
			},
			{ data: 'age' }, // 年齢 
			{
				data: 'gender', // 性別 
				render: function(data, type, row) {
					var gender = '';
					if (data === 1) {
						gender = '男性';
					} else {
						gender = '女性';
					} return gender;
				}
			},
			{
				data: 'userId', // 詳細 画面 の URL 
				render: function(data, type, row) {
					var url = '<a href ="/user/detail/' + data + '">詳細 </a >';
					return url;
				}
			},
		]
	});
}