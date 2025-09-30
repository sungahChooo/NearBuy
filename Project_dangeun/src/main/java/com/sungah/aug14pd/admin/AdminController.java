package com.sungah.aug14pd.admin;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sungah.aug14pd.main.Product;
import com.sungah.aug14pd.member.Member;
import com.sungah.aug14pd.member.MemberDAO;
import com.sungah.aug14pd.sell.Sell;
import com.sungah.aug14pd.townNews.TownNews;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class AdminController {
	
	@Autowired
	private MemberDAO mDAO;
	
	@Autowired
	private AdminDAO aDAO;
	
	@RequestMapping(value = "/member.delete", method = RequestMethod.GET)
	public String memberDelete(Member m, HttpServletRequest req, HttpSession session) {
		if (mDAO.isLogined(req, session)) {
			aDAO.delete(m, req);
			List<Map<String, Object>> membersWithStatus = aDAO.getMembersWithReportStatus(session);
	        req.setAttribute("membersWithStatus", membersWithStatus);
			req.setAttribute("adminContentPage", "adminHome.html");
		}
		return mDAO.showIndex(req, session);
	}
	
	@RequestMapping(value = "/member.search", method = RequestMethod.GET)
	public String memberSearch(HttpServletRequest req, HttpSession session) {
		if (mDAO.isLogined(req, session)) {
			aDAO.search(req, session);
			List<Map<String, Object>> membersWithStatus = aDAO.getMembersWithReportStatus(session);
	        req.setAttribute("membersWithStatus", membersWithStatus);
			req.setAttribute("adminContentPage", "adminHome.html");
		}
		return mDAO.showIndex(req, session);
	}
	
	@RequestMapping(value = "/member.profile.show", method = RequestMethod.GET)
	public String memberProfileShow(Member m, HttpServletRequest req, HttpSession session) {
		if (mDAO.isLogined(req, session)) {
			aDAO.getMemberProfile(m, req);
			aDAO.getReportList(m, req);
			aDAO.getProducts(m, req);
			req.setAttribute("adminContentPage", "memberProfileForAdmin.html");
			req.setAttribute("adminProfileContentPage", "memberRegistered1.html");
		}
		return mDAO.showIndex(req, session);
	}
	
	@RequestMapping(value = "/show.image", method = RequestMethod.GET)
    public String reportPhotoGet(HttpServletRequest req, HttpSession session) {
		if (mDAO.isLogined(req, session)) {
			aDAO.showPhoto(req);
			return "reportPhoto";
		} else {
			return "index";
		}
    }
	
	@RequestMapping(value = "/member.products.show", method = RequestMethod.GET)
	public String memberProductsShow(Member m, HttpServletRequest req, HttpSession session) {
		if (mDAO.isLogined(req, session)) {
			aDAO.getMemberProfile(m, req);
			aDAO.getReportList(m, req);
			aDAO.getProducts(m, req);
			req.setAttribute("adminContentPage", "memberProfileForAdmin.html");
			req.setAttribute("adminProfileContentPage", "memberRegistered1.html");
		}
		return mDAO.showIndex(req, session);
	}
	
	@RequestMapping(value = "/member.townNews.show", method = RequestMethod.GET)
	public String memberTownNewsShow(Member m, HttpServletRequest req, HttpSession session) {
		if (mDAO.isLogined(req, session)) {
			aDAO.getMemberProfile(m, req);
			aDAO.getReportList(m, req);
			aDAO.getTownNews(m, req);
			req.setAttribute("adminContentPage", "memberProfileForAdmin.html");
			req.setAttribute("adminProfileContentPage", "memberRegistered2.html");
		}
		return mDAO.showIndex(req, session);
	}
	
	@RequestMapping(value = "/member.townNews.delete", method = RequestMethod.GET)
	public String memberTownNewsDelete(Member m, TownNews t, HttpServletRequest req, HttpSession session) {
		if (mDAO.isLogined(req, session)) {
			aDAO.deleteTownNews(t, req);
			aDAO.getMemberProfile(m, req);
			aDAO.getReportList(m, req);
			aDAO.getTownNews(m, req);
			req.setAttribute("adminContentPage", "memberProfileForAdmin.html");
			req.setAttribute("adminProfileContentPage", "memberRegistered2.html");
		}
		return mDAO.showIndex(req, session);
	}
	
	@RequestMapping(value = "/member.products.delete", method = RequestMethod.GET)
	public String memberProductsDelete(Product p, Member m, HttpServletRequest req, HttpSession session) {
		if (mDAO.isLogined(req, session)) {
			aDAO.deleteProducts(p, req);
			aDAO.getMemberProfile(m, req);
			aDAO.getReportList(m, req);
			aDAO.getProducts(m, req);
			req.setAttribute("adminContentPage", "memberProfileForAdmin.html");
			req.setAttribute("adminProfileContentPage", "memberRegistered1.html");
		}
		return mDAO.showIndex(req, session);
	}
	
	@RequestMapping(value = "/member.productDetail.show", method = RequestMethod.GET)
	   public String memberProductDetailShow(Sell s, Member m, HttpServletRequest req, HttpSession session) {
	      if (mDAO.isLogined(req, session)) {
	         aDAO.getMemberProductDetail(s, req);
	         req.setAttribute("adminContentPage", "memberProductDetail.html");
	      }
	      return mDAO.showIndex(req, session);
	   }
	

}
