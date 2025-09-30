package com.sungah.aug14pd.product;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.sungah.aug14pd.member.Member;
import com.sungah.aug14pd.member.MemberRepo;
import com.sungah.aug14pd.sell.Sell;
import com.sungah.aug14pd.up.Profile;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Service
public class ProductDAOs {

	@Autowired
	private ProductRepos pr;

	@Autowired
	private PurchaseHistoryRepo phr;

	@Autowired
	private MemberRepo mr;

	@Value("${file.dir}")
	private String fileDir;

	public List<Sell> getAllProducts() {
		return pr.findAll();
	}

	// 전체 상품조회
	public void get(HttpServletRequest req) {
		try {
			List<Sell> products = pr.findAll();
			req.setAttribute("product", products);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Resource getPhoto(String name) {
		try {
			return new UrlResource("file:" + fileDir + "/" + name);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public ResponseEntity<Resource> getFile(String name) {
		try {
			UrlResource ur = new UrlResource("file:" + fileDir + "/" + name);
			String header = "attachment; filename=\"" + URLEncoder.encode(name, "utf-8") + "\"";
			return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, header).body(ur);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getFileName(MultipartFile mf) {
		String fileName = mf.getOriginalFilename();
		String type = "";

		if (fileName != null && fileName.contains(".")) {
			type = fileName.substring(fileName.lastIndexOf("."));
			fileName = fileName.replace(type, "");
		} else {
			return null;
		}

		String uuid = UUID.randomUUID().toString();
		return fileName + "_" + uuid + type;
	}

	// 판매중인 상품 조회
	public void getSale(HttpServletRequest req, Profile mId) {
		try {
			List<Sell> sellingProducts = pr.findByStateAndMemberId("판매중", mId);
			req.setAttribute("product", sellingProducts);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// 판매중인 상품 조회
//	public void getSales(HttpServletRequest req) {
//		Member m = (Member) req.getSession().getAttribute("loginMember");
//		try {
//			List<Sell> sellingProducts = pr.findByStateAndMemberId("판매중", m);
//			req.setAttribute("product", sellingProducts);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	// 판매완료인 상품 조회
	public void getSold(HttpServletRequest req, Profile mId) {
		try {
			List<Sell> soldoutProducts = pr.findByStateAndMemberId("판매완료", mId);
			req.setAttribute("product", soldoutProducts);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// 판매완료인 상품 조회
//	public void getSoldOut(HttpServletRequest req) {
//		Member m = (Member) req.getSession().getAttribute("loginMember");
//		try {
//			List<Sell> soldoutProducts = pr.findByStateAndMemberId("판매완료", m);
//			req.setAttribute("product", soldoutProducts);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	// 구매자 확정페이지에서 상태변경 및 구매자 선택 리스트 불러오기
	public void confirm(HttpServletRequest req, Integer itemId) {
		try {
			Optional<Sell> product = pr.findById(itemId);
			if (product.isPresent()) {
				Sell foundProduct = product.get();
				req.setAttribute("ppproduct", foundProduct);
				
				// 지금은 전체 멤버목록을 불러오고 
				List<Member> allMembers = mr.findAll();
				
	            // Product와 관련된 멤버를 필터링합니다.
				// (아직 필터링이 덜 됨 > 리스트에서 자신의 아이디는 제외하고 출력해야됨)
	            List<Member> members = allMembers.stream()
	                    .filter(member -> !member.getId().equals(foundProduct.getMemberId()))
	                    .collect(Collectors.toList());
	            req.setAttribute("members", members);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 상품 상태 변경 및 구매내역에 저장 
	public void soldOut(HttpServletRequest req,@RequestParam("buyer") String buyerId, @RequestParam("productId") Integer productId) {
		Optional<Sell> soldoutProductOptional = pr.findById(productId);
		Optional<Member> buyerOptional = mr.findById(buyerId);
		try {
			if (buyerOptional.isPresent()) {
				Member buyer = buyerOptional.get();
				
				if (soldoutProductOptional.isPresent()) {
					Sell soldoutProduct = soldoutProductOptional.get();
					soldoutProduct.setState("판매완료");
					pr.save(soldoutProduct);
				
					
					PurchaseHistory ph = new PurchaseHistory();
	                ph.setProduct(soldoutProduct);
	                ph.setBuyer(buyer);
	                phr.save(ph);
	                System.out.println(ph);
	            }
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void getPurchaseHistory(HttpServletRequest req) {
		Member m = (Member) req.getSession().getAttribute("loginMember");
		try {
			List<PurchaseHistory> ph = phr.findByBuyer(m);
			req.setAttribute("purchase", ph);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	public void confirmPurchase(Integer productId, String buyerId) {
		Sell product = pr.findById(productId).orElse(null);
		Member buyer = mr.findById(buyerId).orElse(null);

		if (product != null && buyer != null) {
			product.setState("판매완료");
			pr.save(product);

			PurchaseHistory purchaseHistory = new PurchaseHistory();
			purchaseHistory.setProduct(product);
			purchaseHistory.setBuyer(buyer);
			phr.save(purchaseHistory);
		}
	}


}
