package com.sungah.aug14pd.main;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sungah.aug14pd.member.Member;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class WishlistDAO {

	@Autowired
	private WishlistRepo wr;

	public boolean isProductWishlistdByUser(HttpServletRequest req, Integer productId) {
		Member m = (Member) req.getSession().getAttribute("loginMember");
		List<Wishlist> ws= wr.findByUserAndProductId(m.getId(), productId);
		return !ws.isEmpty();
	}

	public void addWishlist(HttpServletRequest req, Integer productId) {
		Member m = (Member) req.getSession().getAttribute("loginMember");
		Wishlist w = new Wishlist();
		w.setUser(m.getId());
		w.setProductId(productId);
		wr.save(w);
		System.out.println(isProductWishlistdByUser(req, productId));
	}
	@Transactional
	public void removeWishlist(HttpServletRequest req, Integer productId) {
		Member m = (Member) req.getSession().getAttribute("loginMember");
		wr.deleteByUserAndProductId(m.getId(), productId);
	}
}
