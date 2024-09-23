package com.mstr.service;

import java.io.Serializable;
import java.util.Locale;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class MstrLoginVO implements Serializable {
	  private String id;                    /*사원아이디*/
	    private String nm;                    /*이름*/
	    private Locale locale;                  /*다국어 로케일 코드*/
	    private String dept_cd;               /*부서코드*/
	    private String dept_nm;               /*부서명*/
	    private String ip;                      /*접속 IP*/
	    private String mstrSession;              /*MSTR세션값*/
	    private String idToken;              /*idToken*/
	    private boolean admin = false;
	    private boolean bbsAdmin = false;
	    private boolean ossAuth = false;
	    private boolean ssomode = false;
	    @JsonIgnore
	    private String dummy;
}
