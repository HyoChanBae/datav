package com.mstr.service;

import java.io.Serializable;
import java.util.Locale;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class MstrLoginVO implements Serializable {
	  private String id;                    /*������̵�*/
	    private String nm;                    /*�̸�*/
	    private Locale locale;                  /*�ٱ��� ������ �ڵ�*/
	    private String dept_cd;               /*�μ��ڵ�*/
	    private String dept_nm;               /*�μ���*/
	    private String ip;                      /*���� IP*/
	    private String mstrSession;              /*MSTR���ǰ�*/
	    private String idToken;              /*idToken*/
	    private boolean admin = false;
	    private boolean bbsAdmin = false;
	    private boolean ossAuth = false;
	    private boolean ssomode = false;
	    @JsonIgnore
	    private String dummy;
}
