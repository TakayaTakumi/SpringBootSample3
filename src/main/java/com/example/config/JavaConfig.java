package com.example.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/* クラスが 1 つ以上の @Bean メソッドを宣言し、Spring コンテナーによって処理されて、
 *実行時にこれらのBeanのBean定義とサービスリクエストを生成できることを示します。
 * 設定を記述するクラスに付与。
  このクラスは@Beanを付与したメソッドで構成される事になる。*/
@Configuration
public class JavaConfig {

	/* メソッド単位で付。@Configurationクラスに使用。
      DataSourceやJdbcTemplateといったクラスが戻り値のメソッドに付与。*/
	@Bean
	public ModelMapper modelMapper() {
		
		/*ModelMapperインスタンスを生成している*/
		return new ModelMapper();
	}
}
