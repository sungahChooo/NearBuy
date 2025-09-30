package com.sungah.aug14pd.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sungah.aug14pd.member.Member;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

@Service
public class WishProductDAO {

	@Autowired
	private WishProductRepo wr;
	
//	public boolean isProductWishlistdByUser(HttpServletRequest req, Product productId) {
//		Member m = (Member) req.getSession().getAttribute("loginMember");
//		List<WishList> ws= wr.findByMemberIdAndProductId(m.getId(), productId);
//		return !ws.isEmpty();
//	}
//	
//	
//	@Transactional
//	public void removeWishlist(HttpServletRequest req, Integer productId) {
//		Member m = (Member) req.getSession().getAttribute("loginMember");
//		try {
//			wr.deleteByMemberIdAndProductId(m.getId(), productId);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
	public void getWishlist(HttpServletRequest req, Member mId) {
		try {
			List<WishLists> w = wr.findByMemberId(mId);
			req.setAttribute("wishlist", w);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
