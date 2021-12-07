package com.spring.web5.vo;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Component("customer")
public class CustomerVO {
	public CustomerVO() {
		// TODO Auto-generated constructor stub
	}
	private String custid;
	private String password;
	private String name;
	private String email;
	private String gender;
	private String address;
	private String ssn;

	

}
