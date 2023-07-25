'use strict';

/** 画面 ロード 時 の 処理. */
jQuery(function($) {

	/** 更新 ボタン を 押し た とき の 処理. */
	$('#btn-update').click(function(event) {

		// ユーザー 更新 

		updateUser();
	});

	/** 削除 ボタン を 押し た とき の 処理. */
	$('#btn-delete').click(function(event) {

		// ユーザー 削除 
		deleteUser();
	});
});
/** ユーザー 更新 処理. */
function updateUser() {
	// フォーム の 値 を 取得 
	var formData = $('#user-update-form').serializeArray();


	//ajax通信
	$.ajax({
		type: "PUT",
		cache: false,
		url: '/user/update',
		data: formData,
		dataType: 'json',
	}).done(function(data) {

		//ajax成功時の処理
		alert('ユーザーを更新しました')

		//ユーザー一覧画面にリダイレクト
		window.location.href = '/user/list';
	}).fail(function(jqXHR, textStatus, errorThrown) {

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