package com.example.domain.user.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

/*登録用　エンティティクラスは，データベースのテーブルのレコードをJavaのオブジェクトとして扱うためのクラスです*/
/*@Data クラスをアノテートすると、以下のアノテーションを全て設定したのと同じ効果を得られる。
@ToString
@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode*/
/*@Entity テーブルのカラムを宣言する場所がエンティティ*/

//エンティティクラスです
@Data
@Entity /*データベースのテーブルとマッピングするクラスに@Entityアノテーションを付けます。クラス名と同じテーブル名を生成してくれたりします。*/
@Table(name = "m_user") /*エンティティに対応付けるDBのテーブル名を指す。 name属性に、マッピングしたいテーブル名を設定します。*/
public class MUser {
	@Id //主キーのフィールドに付けます。そうすることで、このフィールドが主キーであることを、JPAが認識してくれます。
	private String userId;
	private String password;
	private String userName;
	private Date birthday;
	private Integer age;
	private Integer gender;
	private Integer departmentId;
	private String role;
	/*多対一の関係を表す場合つけるアノテーション今回の場合はユーザーマスタが多で部署マスタが一
	 *の関係のため@ManyToOneアノテーションをつける　optional=trueだった場合nullも含める　
	 *　MUserがDepartment departmentを参照している*/
	@ManyToOne(optional = true)
	
	/*結合するカラムを指定する場合に、使用する　name属性には結合先のカラム名を記載する
	 * insertable = false, updatable = false　はinsert、updatに含めるかどうかを指定する　今回の場合は含めないということにしている*/
	@JoinColumn(insertable = false, updatable = false, name = "departmentId")
	private Department department;/*Department.javaからDepartmentクラスを持ってきている*/
	@Transient // O/Rマッピングをしたくないフィールドには、@Transientアノテーションを付けます。
	private List<Salary> salaryList;
}
/*なぜ追加していくのか別クラスのインターフェイスを作成することにより可読性をよくしている、その他のメソッドの記載があると見にくいため*/
/*エンティティクラスは、データベースのテーブルのレコード(データそのものを指す)をjavaのオブジェクトとして扱う*/