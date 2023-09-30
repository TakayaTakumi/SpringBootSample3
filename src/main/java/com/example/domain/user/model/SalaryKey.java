package com.example.domain.user.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.Data;
/*埋め込みクラスであることを示すアノテーションです。
 埋め込みクラスとは，エンティティクラス内にフィールドとして埋め込むことができるクラスのことです。
 適用可能要素は，クラスです。
 ＊主キーのカラムが2つ以上ある場合、JPAでは主キーのクラスを作る必要があります。*/
@Embeddable
@Data
public class SalaryKey implements Serializable {
	private String userId;
	private String yearMonth;
}