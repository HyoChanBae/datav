package com.mstr.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.mstr.domain.LoginVO;


@Mapper
public interface LoginMapper {
	
	public LoginVO read(Long userno);
}
