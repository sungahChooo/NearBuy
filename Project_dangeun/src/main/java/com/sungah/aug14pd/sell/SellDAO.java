package com.sungah.aug14pd.sell;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.UUID;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.sungah.aug14pd.main.Wishlist;
import com.sungah.aug14pd.main.WishlistRepo;
import com.sungah.aug14pd.member.Member;
import com.sungah.aug14pd.up.Profile;
import com.sungah.aug14pd.up.ProfileRepo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Service
public class SellDAO {
	
	@Autowired
	private SellRepo sr;
	
	@Autowired
	private ProfileRepo pr;
	
	@Value("${file.dir}")
	private String fileDir;
	
//	public void getDetail(HttpServletRequest req, Integer itemId) {
//		String itemId2 = itemId.toString();
//		
//        try {
//            Optional<Sell> sOptional = sr.findById(itemId);
//            if (sOptional.isPresent()) {
//                Sell sell = sOptional.get();
//
//                HttpSession session = req.getSession();
//                
//                // 이미 조회수를 증가한 상품인지 확인
//                Set<String> viewedProducts = (Set<String>) session.getAttribute("viewedProducts");
//                if (viewedProducts == null) {
//                    viewedProducts = new HashSet<>();
//                }
//                
//                if (!viewedProducts.contains(itemId)) {
//                    // 조회수 증가 처리
//                    sell.setView(sell.getView() + 1);
//                    sr.save(sell);
//
//                    // 이미 조회한 상품 목록에 추가
//                    viewedProducts.add(itemId2);
//                    session.setAttribute("viewedProducts", viewedProducts);
//                }
//
//                req.setAttribute("productDetail", sell);
//            } else {
//                // 상품이 없을 경우 처리
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

	public void getDetail(HttpServletRequest req, Integer itemId) {
	    try {
	        // Sell 엔티티의 ID를 사용하여 상품을 구별
	        Optional<Sell> sOptional = sr.findById(itemId);
	        if (sOptional.isPresent()) {
	            Sell sell = sOptional.get();

	            HttpSession session = req.getSession();
	            
	            // 이미 조회수를 증가한 상품인지 확인
	            Set<Integer> viewedProducts = (Set<Integer>) session.getAttribute("viewedProducts");
	            if (viewedProducts == null) {
	                viewedProducts = new HashSet<>();
	            }
	            
	            if (!viewedProducts.contains(itemId)) {
	                // 조회수 증가 처리
	                sell.setView(sell.getView() + 1);
	                sr.save(sell);

	                // 이미 조회한 상품 목록에 추가
	                viewedProducts.add(itemId);
	                session.setAttribute("viewedProducts", viewedProducts);
	            }

	            req.setAttribute("productDetail", sell);
	        } else {
	            // 상품이 없을 경우 처리
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	
	public void goUpdate(HttpServletRequest req, Integer itemId) {
		try {
			Optional<Sell> sOptional = sr.findById(itemId);
			if (sOptional.isPresent()) {
				Sell sell = sOptional.get();
				req.setAttribute("updateProduct", sell);
			} else {
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Resource getPhoto(Integer name) {
		//String name2 = name.toString();
		try {
			return new UrlResource("file:"+fileDir+"/"+name);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public ResponseEntity<Resource> getFile(String name) {
		try {
			UrlResource ur = new UrlResource("file:"+fileDir+"/"+name);
			String header = "attachment; filename=\"" + URLEncoder.encode(name,"utf-8") + "\"";
			return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, header).body(ur);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private String getFileName(MultipartFile mf) {
	    String fileName = mf.getOriginalFilename();
	    String type = "";

	    if (fileName != null && fileName.contains(".")) {
	        type = fileName.substring(fileName.lastIndexOf("."));
	        fileName = fileName.replace(type, "");
	    } else {
	        return null;  // Return null if there is no filename or no extension
	    }

	    String uuid = UUID.randomUUID().toString();
	    return fileName + "_" + uuid + type;
	}
	
	public void upload(MultipartFile mf, Sell s, HttpServletRequest req) {
		try {
			String fileName = getFileName(mf);
			File f = new File(fileDir+"/"+fileName);
			mf.transferTo(f);
			s.setPhoto(fileName);
			sr.save(s);
			req.setAttribute("result", "등록성공");
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("result", "등록실패");
		}
		
	}


	public void get(HttpServletRequest req) {
		try {
			Pageable p = PageRequest.of(0, 4);
			List<Sell> sellProduct = sr.findAll(p);
			req.setAttribute("sellProduct", sellProduct);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void getAll(HttpServletRequest req,Profile id) {
		try {
			
			Pageable p = PageRequest.of(0, 4);
			//List<Sell> sellProduct = sr.findAll(p);
			List<Sell> sellProduct = sr.findByMemberId(id,p);
			req.setAttribute("sellProduct", sellProduct);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void getSelling(HttpServletRequest req, Profile id) {
		try {
			Pageable p = PageRequest.of(0, 4);
			//List<Sell> sellingProduct = sr.findByState("판매중",p);
			List<Sell> sellingProduct = sr.findByMemberIdAndState(id, "판매중",p);
			req.setAttribute("sellProduct", sellingProduct);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void getSold(HttpServletRequest req, Profile id) {
		try {
			Pageable p = PageRequest.of(0, 4);
			//List<Sell> soldProduct = sr.findByState("판매완료",p);
			List<Sell> soldProduct = sr.findByMemberIdAndState(id, "판매완료",p);
			req.setAttribute("sellProduct", soldProduct);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	public void getByCategory(String category,HttpServletRequest req) {
		Pageable p = PageRequest.of(0, 4);
		List<Sell> getByCategory;
		try {
			//getByCategory = sr.findByCategoryOrderByViewDesc(category, p);
			getByCategory = sr.findByCategoryAndStateOrderByViewDesc(category, "판매중", p);
			req.setAttribute("getByCategory", getByCategory);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void delete(Sell s, HttpServletRequest req) {
		try {
			sr.deleteById(s.getId());
			req.setAttribute("result", "삭제성공");
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("result", "삭제실패");
		}
	}
	
//	public void update(MultipartFile mf ,Sell s, HttpServletRequest req) {
//		try {
//			s= sr.findById(s.getId());
//			req.setAttribute("update", s);
//			String fileName = getFileName(mf);
//			File f = new File(fileDir+"/"+fileName);
//			mf.transferTo(f);
//			s.setPhoto(fileName);
//			sr.save(s);
//			req.setAttribute("result", "수정성공");
//		} catch (Exception e) {
//			req.setAttribute("result", "수정실패");
//			e.printStackTrace();
//		}
//	}
	public void update(MultipartFile mf, Sell s, HttpServletRequest req, Integer itemId) {
		
		try {
	        // 1. 기존에 존재하는 Sell 엔터티를 Optional로 조회합니다.
	        Optional<Sell> existingSellOptional = sr.findById(itemId);
	        
	        if (existingSellOptional.isPresent()) {
	            Sell existingSell = existingSellOptional.get();
	            
	            // 2. MultipartFile을 이용하여 새로운 사진을 업로드하고 파일 이름을 얻습니다.
	            String fileName = getFileName(mf);
	            
	            if (!mf.isEmpty()) {
	            	File f = new File(fileDir + "/" + fileName);
	            	mf.transferTo(f);
	            	existingSell.setPhoto(fileName);
				}
	            
	            // 다른 필드도 필요한 경우 업데이트
	            existingSell.setName(s.getName());
	            existingSell.setCategory(s.getCategory());
	            existingSell.setPrice(s.getPrice());
	            existingSell.setDescription(s.getDescription());
	            
	            // 4. 업데이트된 Sell 엔터티를 저장합니다.
	            sr.save(existingSell);
	            
	            req.setAttribute("result", "수정성공");
	        } else {
	            req.setAttribute("result", "수정실패: 해당 상품을 찾을 수 없습니다.");
	        }
	    } catch (Exception e) {
	        req.setAttribute("result", "수정실패");
	        e.printStackTrace();
	    }
	}
	
	public void updateDate(Sell s, HttpServletRequest req, Integer itemId) {
		try {
			Optional<Sell> existingSellOptional = sr.findById(itemId);
			
			if (existingSellOptional.isPresent()) {
				Sell existingSell = existingSellOptional.get();
				 
				
				LocalDateTime now = LocalDateTime.now();
		        existingSell.setUpdateDate(now);
		        
		        sr.save(existingSell);
				
		        req.setAttribute("result", "끌올 성공");
			}
		} catch (Exception e) {
			req.setAttribute("result", "끌올 실패");
			e.printStackTrace();
		}
	}

}
