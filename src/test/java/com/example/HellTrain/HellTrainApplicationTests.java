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
	void generate(){

	    BCryptPasswordEncoder encoder =
	        new BCryptPasswordEncoder();


	    String[] pwds = {
	        "a12345678",
	        "b12345678",
	        "c12345678",
	        "d12345678",
	        "e12345678",
	        "f12345678",
	        "g12345678",
	        "h12345678",
	        "i12345678",
	        "j12345678",
	        "k12345678",
	        "l12345678"
	    };


	    for(String pwd : pwds){
	        System.out.println(
	            pwd + " => " + encoder.encode(pwd)
	        );
	    }
	}

	@Test
	void checkPwd(){

	    BCryptPasswordEncoder encoder =
	            new BCryptPasswordEncoder();


	    String password = "a123456";

	    String hash =
	        "$2a$10$ForR9FXEjZIGZwA02M8jH.sSQeXQvq45Sn8bI/eDiInVhZ2uqhCBG";


	    System.out.println(
	        encoder.matches(password, hash)
	    );
	}
}
