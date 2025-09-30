package com.skgus.project.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class memberController {
	
	@Autowired
	private memberDAO mDAO;
	
	@GetMapping("/checkMemberID")
	@ResponseBody
	public ResponseEntity<?> checkMemberID(@RequestParam("id") String id) {
		boolean exists = mDAO.checkMemberIDExists(id);
		return ResponseEntity.ok().body("{\"exists\": " + exists + "}");
	}
	
	@RequestMapping("/member.login.go")
	public String loginGo() {
		return "login";
	}
	
	@RequestMapping("/member.join.go")
	public String joinGo() {
		return "join";
	}
	
	@RequestMapping(value = "/member.join", method = RequestMethod.POST)
	public String memberJoin(@RequestParam("photoo") MultipartFile mf, Member m, HttpServletRequest req) {
		mDAO.join(mf, m, req);
		return "login";
	}
	
	@RequestMapping(value = "/member.login", method = RequestMethod.POST)
	public String memberLogin(Member m, HttpServletRequest req, HttpSession session) {
		mDAO.login(m, req, session);
		mDAO.isLogined(req, session);
		return mDAO.showIndex(req, session);
	}
	
	@RequestMapping(value = "/member.logout", method = RequestMethod.GET)
	public String memberLogout(Member m, HttpServletRequest req, HttpSession session) {
		mDAO.logout(req, session);
		mDAO.isLogined(req, session);
		return mDAO.showIndex(req, session);
	}
	
	@RequestMapping(value = "/member.info.go", method = RequestMethod.GET)
	public String memberInfoGo(Member m, HttpServletRequest req, HttpSession session) {
		if (mDAO.isLogined(req, session)) {
			mDAO.showInfo(req, session);
		}
		return "info";
	}
	
	@RequestMapping("/photo/{n}")
	public @ResponseBody Resource getPhoto(@PathVariable("n") String name) {
		return mDAO.getPhoto(name);
	}
	
	@RequestMapping(value = "/member.bye", method = RequestMethod.GET)
	public String memberBye(Member m, HttpServletRequest req, HttpSession session) {
		if (mDAO.isLogined(req, session)) {
			mDAO.bye(req, session);
		}
		return mDAO.showIndex(req, session);
	}
	
	@RequestMapping(value = "/member.info.update", method = RequestMethod.POST)
	public String memberInfoUpdate(@RequestParam("photoo") MultipartFile mf, Member m, HttpServletRequest req, HttpSession session) {
		String returnValue;
		
		if (mDAO.isLogined(req, session)) {
			mDAO.update(mf, m, req, session);
			mDAO.showInfo(req, session);
			returnValue = "info";
		} else {
			returnValue = mDAO.showIndex(req, session);
		}
		return returnValue;
	}
	
}
