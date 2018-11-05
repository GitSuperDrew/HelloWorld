package com.drew.service;

import java.util.List;

import com.drew.pojo.BaseDict;
import com.drew.pojo.Customer;
import com.drew.pojo.QueryVo;



public interface CustomerService {

	public List<BaseDict> findDictByCode(String code);
	
	public List<Customer> findCustomerByVo(QueryVo vo);
	public Integer findCustomerByVoCount(QueryVo vo);
	
	public Customer findCustomerById(Long id);
	
	public void updateCustomerById(Customer customer);
	
	public void delCustomerById(Long id);
}
