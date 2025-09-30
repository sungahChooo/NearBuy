package com.sungah.aug14pd.up;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sungah.aug14pd.member.MemberDAO;
import com.sungah.aug14pd.sell.SellDAO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class ProfileController {

	@Autowired
	private MemberDAO mDAO;

	@Autowired
	private ProfileDAO pDAO;

	@Autowired
	private SellDAO sDAO;

	@RequestMapping("/go.profile")
	public String goProfile(HttpServletRequest req, String id, Profile pid, HttpSession session) {
		if (mDAO.isLogined(req, session)) {
			sDAO.getAll(req, pid);
			pDAO.get(req, id);
			req.setAttribute("contentPage", "userProfile.html");
			return "index";
		}
		req.setAttribute("contentPage", "product.html");
		return "index";
	}

	@RequestMapping("/go.selling")
	public String getSelling(HttpServletRequest req, String id, Profile pid, HttpSession session) {
		if (mDAO.isLogined(req, session)) {
			sDAO.getSelling(req, pid);
			pDAO.get(req, id);
			req.setAttribute("contentPage", "userProfile.html");
			return "index";
		}
		req.setAttribute("contentPage", "product.html");
		return "index";
	}

	@RequestMapping("/go.sold")
	public String getSold(HttpServletRequest req, String id, Profile pid, HttpSession session) {
		if (mDAO.isLogined(req, session)) {
			sDAO.getSold(req, pid);
			pDAO.get(req, id);
			req.setAttribute("contentPage", "userProfile.html");
			return "index";
		}
		req.setAttribute("contentPage", "product.html");
		return "index";
	}
}
