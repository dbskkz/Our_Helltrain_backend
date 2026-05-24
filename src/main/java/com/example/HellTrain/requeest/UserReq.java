package com.example.HellTrain.requeest;

import com.example.HellTrain.constant.ValidMessage;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

public class UserReq {

	@Valid
	@NotBlank(message = ValidMessage.NAME_ERROR)
	private String name;

	@Valid
	@NotBlank(message = ValidMessage.PASSWORD_ERROR)
	@Size(min = 8, message = ValidMessage.PASSWORD_LONG_ERROR)
	private String password;

	@Valid
	@NotBlank(message=ValidMessage.Email_ERROR)
	@Email(message=ValidMessage.Email_ERROR)
	private String Email;

	@Valid
	@NotBlank(message = ValidMessage.LOCATION_IS_NULL)
	private String location;

	@Valid
	@NotBlank(message = ValidMessage.School_IS_NULL)
	private String School;
	
	private String phone;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getSchool() {
		return School;
	}

	public void setSchool(String school) {
		School = school;
	}

}
