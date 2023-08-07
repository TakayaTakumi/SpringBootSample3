package com.example.hello;

import lombok.Data;

@Data//@RequiredArgsConstructor、@Getter、@Setter、@ToString、@EqualsAndHashCodeをまとめて宣言したい場合に使用する
public class Employee {
 private String employeeId;//String型のemployeeIdが格納されるフィールド（クラス内で値(情報)を保持するために使用)
 private String employeeName;//String型のemployeeNameが格納されるフィールド
 private int employeeAge;//int型のemployeeAgeが格納されるフィールド
}
