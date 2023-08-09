package com.example.form;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class SignupForm {
	
/*@NotBlank 文字列がnull、空文字、空白、スペースのみでないことをチェックする
 *@Email 文字列がメールアドレス形式かどうかチェックする*/	
@NotBlank(groups = ValidGroup1.class)
@Email(groups = ValidGroup2.class)
private String userId;

/*@Length 文字列の長さが指定した範囲内であるかをチェックする
 *@Pattern 指定した正規表現に一致する*/
@NotBlank(groups = ValidGroup1.class)
@Length(min=4,max=100,groups = ValidGroup2.class)
@Pattern(regexp ="^[a-zA-Z0-9]+$",groups = ValidGroup2.class)
private String password;


@NotBlank(groups = ValidGroup1.class)
private String userName;

/*日付情報をyyyy/mm/ddなどの指定したフォーマットに変換して表示するための補助クラスです。
 * LocalDateTimeクラスで日付時刻情報を取得し、DateTimeFormatterを使って画面に日付情報を表示する方式が一般的です。
 *@NotNull null出ないことをチェックする*/
@DateTimeFormat(pattern = "yyyy/MM/dd")
@NotNull(groups = ValidGroup1.class)
private Date birthday;

/*@Min 規定した値以上であるかチェックする
 *@Max 規定した値以下であるかチェックする*/
@Min(value = 20,groups = ValidGroup2.class)
@Max(value = 100,groups = ValidGroup2.class)
private Integer age;


@NotNull(groups = ValidGroup1.class)
private Integer gender;
}
