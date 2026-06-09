package com.example.HellTrain;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class HellTrainApplicationTests {
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	@Test
	void contextLoads() {
	}
	
	@Test
	void pwdGenerator() { // 改為 void
		String pwd = "l123456";
		String encodedPwd = encoder.encode(pwd);
		System.out.println("加密後的密碼： " + encodedPwd);
	}

}
