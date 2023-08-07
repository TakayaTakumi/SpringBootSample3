package com.example.applicatioln.service;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public class UserApplicationService {

	
	/*@Autowired　DIコンテナへ利用したいクラスに@Component,@Contrler,@Service.@Repositoryをつける必要あり、
	 *DIコンテナに登録されているクラスが@Autowiredで引き出せる*/
	/*messages.propertiesから値を取得するためには、MessageSourceを使用する
	 * MessageSourceのgetMessageメソッドを使用すれば値を取得できる*/
	@Autowired
	private MessageSource messageSource;

	/**性別のMap生成する*/
	public Map<String, Integer> getGenderMap(Locale locale) {
		/*LinkedHashMapはHashMapと基本的な性質は似ていますが、先程と大きく異なるのは順番が保証されている点です。
		 * 特にLinkedHashMapは要素を追加した順番に出力されます。*/
		Map<String, Integer> genderMap = new LinkedHashMap<>();
		
		String male = messageSource.getMessage("male", null, locale);
		String female = messageSource.getMessage("female", null, locale);

		/*LinkedHashMapの場合(キー名,value)になる今回の場合 th:value="${item.value}"に(キー名,value)のvalueが来る*/
		genderMap.put(male, 1);
		genderMap.put(female, 2);
		return genderMap;
	}
}
