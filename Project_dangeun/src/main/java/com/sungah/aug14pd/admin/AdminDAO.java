package com.sungah.aug14pd.admin;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sungah.aug14pd.main.Product;
import com.sungah.aug14pd.main.ProductRepo;
import com.sungah.aug14pd.member.Member;
import com.sungah.aug14pd.member.MemberRepo;
import com.sungah.aug14pd.report.Report;
import com.sungah.aug14pd.report.ReportRepo;
import com.sungah.aug14pd.sell.Sell;
import com.sungah.aug14pd.sell.SellRepo;
import com.sungah.aug14pd.townNews.TownNews;
import com.sungah.aug14pd.townNews.TownNewsReply;
import com.sungah.aug14pd.townNews.TownNewsReplyRepo;
import com.sungah.aug14pd.townNews.TownNewsRepo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

@Service
public class AdminDAO {
	
	@Autowired
	private MemberRepo mr;
	
	@Autowired
	private AdminReportRepo rr;
	
	@Autowired
	private TownNewsRepo tr;
	
	@Autowired
	private TownNewsReplyRepo trr;
	
	@Autowired
	private ProductRepo pr;
	
	@Autowired
	private SellRepo sr;
	
	@Value("${file.dir}")
	private String fileDir;
	
	public List<Map<String, Object>> getMembersWithReportStatus(HttpSession session) {
		String search = (String) session.getAttribute("search");
		if (search == null) {
			search = "";
		}
        List<Member> members = mr.findByAdminAndIdContainingOrAdminAndNameContaining(0, search, 0, search);
        List<Map<String, Object>> membersWithStatus = new ArrayList<>();
        
        for (Member member : members) {
            Map<String, Object> memberInfo = new HashMap<>();
            memberInfo.put("member", member);
            
            List<AdminReport> reports = rr.findByreportedId(member.getId());
            boolean isReported = !reports.isEmpty();
            memberInfo.put("reported", isReported);
            
            membersWithStatus.add(memberInfo);
        }
        return membersWithStatus;
    }
	
	public void delete(Member m, HttpServletRequest req) {
		try {
			mr.deleteById(m.getId());
			req.setAttribute("result", "삭제 성공");
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("result", "삭제 실패");
		}
	}
	
	public void search(HttpServletRequest req, HttpSession session) {
		String search = req.getParameter("search");
//		System.out.println(search);
		session.setAttribute("search", search);
	}
	
	public void clearSearh(HttpServletRequest req, HttpSession session) {
		session.setAttribute("search", null);
	}
	
	public void getMemberProfile(Member m, HttpServletRequest req) {
		try {
			Optional<Member> optionalMember = mr.findById(m.getId());
			Member member = optionalMember.get();
			req.setAttribute("memberProfile", member);
//			System.out.println(member.getId());
//			System.out.println(member.getPhoto());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void getReportList(Member m, HttpServletRequest req) {
		try {
			List<AdminReport> reports = rr.findByreportedId(m.getId());
			req.setAttribute("reports", reports);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void getTownNews(Member m, HttpServletRequest req) {
		try {
			List<TownNews> adminTownNews = tr.findBytownNewsMemberId(m.getId());
			List<TownNewsReply> adminTownNewsReply =  trr.findAll();
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			for (TownNews adminTN : adminTownNews) {
	            LocalDateTime timestamp = adminTN.getTownNewsTimestamp();
	            String formattedDate = timestamp.format(formatter);
	            adminTN.setTownNewsTimestampFormatted(formattedDate);
	        }
			for (TownNewsReply adminTNR : adminTownNewsReply) {
	            LocalDateTime timestamp = adminTNR.getTownNewsReplyDate();
	            String formattedDate = timestamp.format(formatter);
	            adminTNR.setTownNewsReplyTimestampFormatted(formattedDate);
	        }
	        
	        req.setAttribute("adminTownNewsReply", adminTownNewsReply);
			req.setAttribute("adminTownNews", adminTownNews);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Transactional
	public void deleteTownNews(TownNews tn, HttpServletRequest req) {
		try {
			tr.deleteBytownNewsNum(tn.getTownNewsNum());
			req.setAttribute("result", "삭제 성공");
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("result", "삭제 실패");
		}
	}
	
	public void getProducts(Member m, HttpServletRequest req) {
		try {
			List<Product> adminProducts = pr.findBywriter(m.getId());
			req.setAttribute("adminProducts", adminProducts);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deleteProducts(Product p, HttpServletRequest req) {
		try {
			pr.deleteById(p.getProductId());
			req.setAttribute("result", "삭제 성공");
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("result", "삭제 실패");
		}
	}
	
	public void showPhoto(HttpServletRequest req) {
		try {
			String reportPhoto = req.getParameter("reportPhoto");
			req.setAttribute("reportPhoto", reportPhoto);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void getMemberProductDetail(Sell s, HttpServletRequest req) {
	      try {
	         Optional<Sell> optionalProduct = sr.findByIdEquals(s.getId());
	         Sell product = optionalProduct.get();
	         req.setAttribute("memberProductDetail", product);
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	   }

}
