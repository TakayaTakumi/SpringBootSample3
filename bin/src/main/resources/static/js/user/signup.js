'use strict';
/** 画面 ロード 時 の 処理. */
jQuery(function($) {

	/** 登録 ボタン を 押し た とき の 処理. */
	$('#btn-signup').click(function(event) {

		// ユーザー 登録 
		signupUser();
	});
});

/** ユーザー 登録 処理 */
function signupUser() {

	// バリデーション 結果 を クリア 
	removeValidResult();

	// フォーム の 値 を 取得 
	var formData = $('#signup-form').serializeArray();

	// ajax 通信 
	$.ajax({
		type: "POST",
		cache: false,
		url: '/user/signup/rest',
		data: formData,
		dataType: 'json',
	}).done(function(data) {

		// ajax 成功 時 の 処理 
		console.log(data);


		if (data.result === 90) {

			// validation エラー 時 の 処理 
			$.each(data.errors,function(key, value) {
				reflectValidResult(key, value)
			});
		} else if (data.result === 0) {
			alert('ユーザーを登録しました');

			// ログイン 画面 に リダイレクト 
			window.location.href = '/login';
		}
	}).fail(function(jqXHR, textStatus, errorThrown) {

		// ajax 失敗 時 の 処理 
		alert('ユーザー登録に失敗しまし た');

	}).always(function() {

		// 常に 実行 する 処理 
	});
}

/** バリデーション 結果 を クリア */
function removeValidResult() {
	$('.is-invalid').removeClass('is-invalid');
	$('.invalid-feedback').remove();
	$('.text-danger').remove();
}

/** バリデーション 結果 の 反映 */
function reflectValidResult(key, value) {

	// エラーメッセージ 追加 
	if (key === 'gender') {// 性別 の 場合 
		// CSS 適用 
		$('input[name=' + key + ']').addClass('is-invalid');

		// エラーメッセージ 追加 
		$('input[name=' + key + ']')
			.parent().parent()
			.append('<div class="text-danger">' + value + '</div>');

	} else { // 性別 以外 の 場合 
		// CSS 適用 
		$('input[id=' + key + ']').addClass('is-invalid');

		// エラーメッセージ 追加 
		$('input[id=' + key + ']')
			.after('<div class="invalid-feedback">' + value + '</div>');
	}
}
