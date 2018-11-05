package com.drew.dao;

import java.util.List;

import com.drew.pojo.BaseDict;



public interface DictMapper {

	public List<BaseDict> findDictByCode(String code);
}
