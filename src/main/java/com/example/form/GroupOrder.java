package com.example.form;

import javax.validation.GroupSequence;

/*@GroupSequence　をつけることでバリデーションの順番を左から設定されたインターフェースの順番でバリデーションしていく*/
@GroupSequence({ValidGroup1.class,ValidGroup2.class})
public interface GroupOrder {

}
