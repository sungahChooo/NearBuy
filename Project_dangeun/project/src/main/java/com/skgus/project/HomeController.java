package com.skgus.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.skgus.project.member.memberDAO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
	
	@Autowired
	memberDAO mDAO;
	
	@RequestMapping("/")
	public String home(HttpServletRequest req, HttpSession session) {
		mDAO.isLogined(req, session);
		return mDAO.showIndex(req, session);
	}

}
