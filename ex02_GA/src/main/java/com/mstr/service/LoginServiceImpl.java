package com.mstr.service;

import org.springframework.stereotype.Service;

import com.mstr.domain.LoginVO;
import com.mstr.mapper.UserMapper;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;


@Log4j
@Service
@AllArgsConstructor
public class LoginServiceImpl implements LoginService{

	private UserMapper mapper;
	

	
	@Override
	public LoginVO get(String userid) {
		log.info("login get...." + userid);
		
		return mapper.read(userid);
		
	}
}
