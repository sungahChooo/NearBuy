package com.sungah.aug14pd.main;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import com.sungah.aug14pd.member.Member;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class ProductDAO {
	@Value("${categories}")
	private String categories;

	@Value("${file.dir}")
	private String fileDir;

	@Autowired
	private ProductRepo pr;

	public void getCategory(HttpServletRequest req) {
		try {
			req.setCharacterEncoding("UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<String> categoryList = Arrays.asList(categories.split(";")).stream().map(category -> {
			try {
				// Decode UTF-8 encoded category
				return new String(category.getBytes("ISO-8859-1"), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				return category;
			}
		}).collect(Collectors.toList());
		req.setAttribute("cl", categoryList);

	}

	public Resource getPhoto(String name) {
		try {
			return new UrlResource("file:" + fileDir + "/" + name);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void search(HttpServletRequest req) {
		req.getSession().setAttribute("search", req.getParameter("search"));

	}

	public void get(HttpServletRequest req, String search) {
		Member m = (Member) req.getSession().getAttribute("loginMember");
		List<Product> products = new ArrayList<>();
		String loc;
		products = pr.findAll();
		for (Product product : products) {
			// 현재 날짜 가져오기
			Date currentDate = new Date();

			// 등록 날짜 가져오기
			Date registrationDate = product.getUploadDate();

			// 날짜 차이 계산
			long diffInMillies = Math.abs(currentDate.getTime() - registrationDate.getTime());
			String diffInDays = (diffInMillies / (24 * 60 * 60 * 1000)) + "일 전";
			if (diffInDays.equals("0일 전")) {
				diffInDays="오늘";
			}

			product.setDaysAgo(diffInDays);
		}

		if (m == null || m.getLoc() == null) {
			loc = null;
		} else {
			loc = m.getLoc();
		}
		if (loc != null && search == null) {
			products = pr.findByLocOrderByUpdateDateDesc(loc);
		} else if (loc != null && search != null) {
			products = pr.findByNameContainingAndLocOrderByUpdateDateDesc(search, loc);
		} else if (search != null) {
			products = pr.findByNameContainingOrderByUpdateDateDesc(search);
		} else {
			products = pr.findAllByOrderByUpdateDateDesc();
		}
		req.setAttribute("products", products);

	}

	public void getByCategory(HttpServletRequest req, String cgy) {
		String search=req.getParameter("search");
		Member m = (Member) req.getSession().getAttribute("loginMember");
		List<Product> products = new ArrayList<>();
		String loc;
		if (m == null || m.getLoc() == null) {
			loc = null;
		} else {
			loc = m.getLoc();
		}
		System.out.println(cgy);
		System.out.println(search);
		if (search == null && loc == null) {
			products = pr.findByCategoryOrderByUpdateDateDesc(cgy);
		} else if (search == null && loc != null) {
			products = pr.findByCategoryAndLocOrderByUpdateDateDesc(cgy, loc);
		} else if (search != null && loc == null) {
			products = pr.findByNameContainingAndCategoryOrderByUpdateDateDesc(search, cgy);
		} else {
			products = pr.findByCategoryAndNameContainingAndLocOrderByUpdateDateDesc(cgy, search, loc);
		}

		req.setAttribute("products", products);
	}

}
