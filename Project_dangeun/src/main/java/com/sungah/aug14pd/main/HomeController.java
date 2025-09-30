package com.sungah.aug14pd.main;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sungah.aug14pd.admin.AdminDAO;
import com.sungah.aug14pd.member.MemberDAO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

	@Autowired
	private MemberDAO mDAO;

	@Autowired
	private ProductDAO pDAO;

	@Autowired
	private AddressDAO aDAO;
	
	@Autowired
	private AdminDAO adDAO;
	
	@Autowired
	private WishlistDAO wDAO;

	@RequestMapping(value="/")
	public String home(HttpServletRequest req, HttpSession session,String cgy,String search) {
		mDAO.isLogined(req, session);
		adDAO.clearSearh(req, session);
		List<Map<String, Object>> membersWithStatus = adDAO.getMembersWithReportStatus(session);
		pDAO.getCategory(req);
		pDAO.search(req);
		pDAO.get(req,search);
		req.setAttribute("membersWithStatus", membersWithStatus);
		req.setAttribute("adminContentPage", "adminHome.html");
		req.setAttribute("contentPage", "product.html");
		return mDAO.showIndex(req, session);

	}

	@RequestMapping(value="/get.allproducts")
	public String getAllProducts(HttpServletRequest req, HttpSession session,String cgy,String search) {
		mDAO.isLogined(req, session);
		adDAO.clearSearh(req, session);
		List<Map<String, Object>> membersWithStatus = adDAO.getMembersWithReportStatus(session);
		pDAO.getCategory(req);
		pDAO.search(req);
		pDAO.get(req,search);
		req.setAttribute("membersWithStatus", membersWithStatus);
		req.setAttribute("adminContentPage", "adminHome.html");
		req.setAttribute("contentPage", "allProducts.html");
		return mDAO.showIndex(req, session);
		
	}

	@RequestMapping(value = "/product.search")
	public String snsSearch(HttpServletRequest req,String search, HttpSession session) {
		mDAO.isLogined(req, session);
		pDAO.search(req);
		pDAO.getCategory(req);
		pDAO.get(req,search);
		req.setAttribute("contentPage", "product.html");
		return "index";
	}

	@RequestMapping(value = "/location.update.go")
	public String locationUpdateGo(HttpServletRequest req, HttpSession session) {
		mDAO.isLogined(req, session);
		pDAO.getCategory(req);
		req.setAttribute("contentPage", "location.html");
		return "index";
	}
	@RequestMapping(value ="/update.location")
	public void saveDistrict(@RequestParam String districtName, HttpServletRequest req) {
		aDAO.updateLoc(req,districtName);
	}
	
	@RequestMapping("/photo/{n}")
	public @ResponseBody Resource getPhoto(@PathVariable("n") String name) {
		return pDAO.getPhoto(name);
	}

	@RequestMapping(value = "/products")
	public String getByCategory(HttpServletRequest req, @RequestParam String category,
	 HttpSession session) {
		mDAO.isLogined(req, session);
		pDAO.getCategory(req);
		pDAO.getByCategory(req,category);
		req.setAttribute("contentPage", "product.html");
		return "index";
	}

}
