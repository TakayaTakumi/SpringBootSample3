package com.example.domain.user.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;


/*エンティティクラスは，データベースのテーブルのレコードをJavaのオブジェクトとして扱うためのクラスです*/
/*@Data クラスをアノテートすると、以下のアノテーションを全て設定したのと同じ効果を得られる。
@ToString
@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode*/
/*@Entity テーブルのカラムを宣言する場所がエンティティ*/
@Data
@Entity
@Table(name="t_salry")
public class Salary {
	//private String userId;
	//private String yearMonth;
	@EmbeddedId
	private SalaryKey salaryKey;
	private Integer salary;
}
//作成したら大本(インターフェース)になるMUser.javaに追加する　