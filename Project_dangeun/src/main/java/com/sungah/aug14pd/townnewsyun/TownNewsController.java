package com.sungah.aug14pd.townnewsyun;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.sungah.aug14pd.member.Member;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class TownNewsController {

	@Autowired
	private TownNewsDAO tnDAO;

	@Autowired
	private TownNewsFileDAO tnfDAO;

	@Autowired
	private TownNewsReplyDAO tnrDAO;
	
	@Autowired
	private com.sungah.aug14pd.member.MemberDAO mDAO;

	
	

	// 게시판 조회
	@RequestMapping("/townnews.go")
	public String townNewsPage(@RequestParam(name = "page", defaultValue = "1") int page,
		TownNewses tn, HttpServletRequest req, HttpSession session) {
		if(mDAO.isLogined(req, session)) {
			tnDAO.getTownsNewsPage2(req);
			req.setAttribute("contentPage", "townNews.html");
			return "index";
		}
		req.setAttribute("contentPage", "product.html");
		return "index";	
	}
	
	@RequestMapping("/townnewsUpload.go")
	public String townNewsUpload(HttpServletRequest req, HttpSession session, TownNewses tn) {
		if (mDAO.isLogined(req, session)) {
			req.setAttribute("contentPage", "townNewsUpload.html");
			return "index";
		}
		req.setAttribute("contentPage", "product.html");
		return "index";
	}
	
	@RequestMapping("/townnewsUpload.do")
	public String townNewsUploadDo(@RequestParam("photoo") MultipartFile mf,HttpServletRequest req, HttpSession session, TownNewses tn) {
		if (mDAO.isLogined(req, session)) {
			tnDAO.regTownNews(tn, req,mf);
			tnDAO.getTownsNewsPage2(req);			
			req.setAttribute("contentPage", "townNews.html");
			return "index";
		}
		req.setAttribute("contentPage", "product.html");
		return "index";
	}

	
	@RequestMapping("/townnewsUpdate.go")
	public String townNewsUpdate(HttpServletRequest req, HttpSession session, TownNewses tn) {
		if (mDAO.isLogined(req, session)) {
			req.setAttribute("contentPage", "townNewsUpdate.html");
			return "index";
		}
		req.setAttribute("contentPage", "product.html");
		return "index";
	}
	
	
	// 게시판 이미지 띄우기
	@GetMapping("/photo/{n}")
	@ResponseBody
	public Resource getTownNewsFile(@PathVariable("n") String name) {
		return tnfDAO.getTownNewsPhoto(name);
	}


	// 게시판 수정
	@GetMapping("/townnews/edit")
	public String townNewsEdit(TownNewses tn, HttpServletRequest req, HttpSession session) {
		if (mDAO.isLogined(req, session)) {
			tnDAO.getTownNewsContents(req);
			return "redirect:/townnews.go";	
		}
		return "redirect:/member.login.go";		
	}

	// 게시판 삭제
	@PostMapping("/townnews/delete")
	public String townNewsDelete(TownNewses tn, HttpServletRequest req, HttpSession session) {
		if (mDAO.isLogined(req, session)) {
			tnDAO.townNewsDelete(tn, req);
			req.setAttribute("contentPage", "townNews.html");
			return "redirect:/townnews.go";	
		}
		return "redirect:/member.login.go";
	}

	// 댓글 작성
	@PostMapping("/townnews/reply")
	public String townNewsReplyPage(TownNewsReplys tnr, HttpServletRequest req, HttpSession session, Member m) {
		if(mDAO.isLogined(req, session)) {
			tnrDAO.regTownNewsReply(tnr, req);			
			req.setAttribute("contentPage", "townNews.html");
			return "redirect:/townnews.go";	
		}
		return "redirect:/member.login.go";
	}
}
