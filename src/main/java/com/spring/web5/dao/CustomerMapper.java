package com.spring.web5.dao;

import com.spring.web5.vo.CustomerVO;

public interface CustomerMapper {
	//아이디 중복 확인
	public CustomerVO selectCustomer(String custid);
	
	//회원가입 처리
	public int insertCustomer(CustomerVO customer);
	
	//회원정보 수정
	public int updateCustomer(CustomerVO customer);
	

}


