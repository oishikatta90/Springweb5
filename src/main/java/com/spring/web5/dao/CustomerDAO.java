package com.spring.web5.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.spring.web5.vo.CustomerVO;

@Repository
public class CustomerDAO {
	@Autowired
	SqlSession sqlSession;
	
	public CustomerVO selectCustomer(String custid) {
		CustomerMapper mapper = sqlSession.getMapper(CustomerMapper.class);
		CustomerVO c = mapper.selectCustomer(custid);
		return c;
	}
	
	public int insertCustomer(CustomerVO customer) {
		CustomerMapper mapper = sqlSession.getMapper(CustomerMapper.class);
		int result = 0;
		//데이터베이스에 customer 데이터 저장
		result = mapper.insertCustomer(customer);
		return result;
	}
	
	public CustomerVO loginById(CustomerVO customerVO) throws DataAccessException {
		CustomerVO vo = sqlSession.selectOne("mapper.customer.loginById", customerVO);
		return vo;
	}
	
	public int  updateCustomer(CustomerVO customer) throws DataAccessException {
		int result = 0;
		CustomerMapper mapper = sqlSession.getMapper(CustomerMapper.class);
		result = mapper.updateCustomer(customer);
		return result;
	}

}
