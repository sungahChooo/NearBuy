package com.sungah.aug14pd.main;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sungah.aug14pd.member.MemberDAO;
import com.sungah.aug14pd.sell.SellDAO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class WishlistController {

	@Autowired
	private WishlistDAO wDAO;
	
	@Autowired
	private ProductDAO pDAO;

	@Autowired
	private MemberDAO mDAO;
	
	@Autowired
	private SellDAO sDAO;
	
	@GetMapping("/wishlist.add")
	public String addWishlist(HttpServletRequest req, @RequestParam Integer productId,String search,HttpSession session) {
		mDAO.isLogined(req, session);
		wDAO.addWishlist(req,productId);
		pDAO.getCategory(req);
		pDAO.search(req);
		pDAO.get(req,search);
		req.setAttribute("contentPage", "product.html");
		return "index";
	}

	@GetMapping("/wishlist.remove")
	public String removeWishlist(HttpServletRequest req, @RequestParam Integer productId,String search,HttpSession session) {
		mDAO.isLogined(req, session);
		if (wDAO.isProductWishlistdByUser(req, productId)) {
			wDAO.removeWishlist(req, productId);
		}
		pDAO.getCategory(req);
		pDAO.search(req);
		pDAO.get(req,search);
		req.setAttribute("contentPage", "product.html");
		return "index";
	}
	
	@GetMapping("/productDetail.wishlist.add")
	public String addProductDetailWishlist(@RequestParam("id") Integer itemId,@RequestParam("category") String category, HttpServletRequest req, String search,HttpSession session) {
		mDAO.isLogined(req, session);
	//	Integer productId = Integer.parseInt(itemId);
		wDAO.addWishlist(req,itemId);
		pDAO.getCategory(req);
		pDAO.search(req);
		sDAO.getDetail(req, itemId);
		category = category.replace("\"", "");
		sDAO.getByCategory(category, req);
		req.setAttribute("contentPage", "productDetail.html");
		return "index";
	}

	@GetMapping("/productDetail.wishlist.remove")
	public String removeProductDetailWishlist(@RequestParam("id") Integer itemId,@RequestParam("category") String category, HttpServletRequest req,String search,HttpSession session) {
		mDAO.isLogined(req, session);
		//Integer productId = Integer.parseInt(itemId);
		if (wDAO.isProductWishlistdByUser(req, itemId)) {
			wDAO.removeWishlist(req, itemId);
		}
		pDAO.getCategory(req);
		pDAO.search(req);
		sDAO.getDetail(req, itemId);
		category = category.replace("\"", "");
		sDAO.getByCategory(category, req);
		req.setAttribute("contentPage", "productDetail.html");
		return "index";
	}

}
