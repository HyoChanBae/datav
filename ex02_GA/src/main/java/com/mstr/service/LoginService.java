package com.mstr.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.mstr.domain.LoginVO;


public interface LoginService {
	
	public LoginVO get(String userid);
	
}
