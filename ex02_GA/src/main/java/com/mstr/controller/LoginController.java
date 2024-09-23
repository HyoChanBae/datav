package com.mstr.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mstr.domain.LoginVO;
import com.mstr.service.LoginService;
import com.mstr.service.MstrSessionService;
import com.mstr.util.Utils;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@AllArgsConstructor
public class LoginController {
	
	@Autowired
	private LoginService loginService;
	
	@RequestMapping("/loginPage")
	public String loginPage(HttpServletRequest request, HttpSession session, ModelMap model) {
		
		LoginVO	loginVO = (LoginVO) session.getAttribute("loginVO");
		
		return "/login/login";
	}
	@RequestMapping("/login")
	public String login(HttpServletRequest request, HttpSession session, ModelMap model) {
		Map<String, Object> 	param = new ReqData(request).getParams();
		String					id = (String) param.get("userid");
		String					pwd = (String) param.get("userpw");
		String					ip = Utils.getClientIP(request);
		LoginVO					loginVO = new LoginVO();
		
		LoginVO user = loginService.get(id);
		
		MstrSessionService.getSession(id,pwd,ip);
		
		return "redirect:/main.do";
	}

	private static final class ReqData {
		
		private final Map<String, Object> params = new HashMap<String, Object>();

		public ReqData(HttpServletRequest request) {
			Map<String, String[]> map = request.getParameterMap();

			for (String key : map.keySet()) {
				String[] val = map.get(key);

				if (val.length > 0) {
					params.put(key, val[0]);
				}
			}
		}

		public Map<String, Object> getParams() {
			return params;
		}
	}
}
