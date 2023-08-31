package com.example.domain.user.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;


/*エンティティクラスは，データベースのテーブルのレコードをJavaのオブジェクトとして扱うためのクラスです*/
/*@Data クラスをアノテートすると、以下のアノテーションを全て設定したのと同じ効果を得られる。
@ToString
@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode*/

/*@Entity テーブルのカラムを宣言する場所がエンティティ
 * @Entity/*データベースのテーブルとマッピングするクラスに@Entityアノテーションを付けます。クラス名と同じテーブル名を生成してくれたりします。*/

/*エンティティに対応付けるDBのテーブル名を指す。 name属性に、マッピングしたいテーブル名を設定します。*/
@Data
@Entity
@Table(name="m_department")/*エンティティに対応付けるDBのテーブル名を指す。*/
public class Department {
	@Id
	private Integer departmentId;
	private String departmentName;
}
