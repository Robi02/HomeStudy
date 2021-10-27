package com.de4bi.study.jpa.jpashop;

import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class JpashopApplication {

	public static void main(String[] args) {

		SpringApplication.run(JpashopApplication.class, args);
	}

	// V1 API를 위한 코드.
	// Jackson으로 변환할 때 무한루프를 막거나 (1)
	// Join이 필요한 필드에 null이 입력되는것을 해결한다. (2)
	// [!] 사실 이 방법보다는 별도의 DTO를 만드는 방식(V2)를 쓰는것이 좋다.
	@Bean
	Hibernate5Module hibernate5Module() {
		Hibernate5Module module = new Hibernate5Module(); // (1)
		// module.configure(Hibernate5Module.Feature.FORCE_LAZY_LOADING, true); // (2)
		return module;
	}
}
