package com.skgus.project.member;

import java.io.File;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Service
public class memberDAO {
	
	@Autowired
	private MemberRepo mr;
	
	@Value("${file.dir}")
	private String fileDir;
	
	public boolean checkMemberIDExists(String id) {
		return mr.existsById(id);
	}
	
	private String getFileName(MultipartFile mf) {
		String fileName = mf.getOriginalFilename();
		String type = fileName.substring(fileName.lastIndexOf("."));
		fileName = fileName.replace(type, "");
		String uuid = UUID.randomUUID().toString();
		return fileName + uuid + type;
	}
	
	public void join(MultipartFile mf, Member m, HttpServletRequest req) {
		try {
			if (mf.isEmpty()) {
				if (m.getPhoto() == null) {
					m.setPhoto("logo.png");
				}
			} else {
				System.out.println(mf);
				String file = getFileName(mf);
				File f = new File(fileDir + "/" + file);
				mf.transferTo(f); // 실제업로드
				m.setPhoto(file);
			}
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("result", "가입 실패(파일)");
			return;
		}
		
		try {
			m.setId(req.getParameter("id"));
			m.setName(req.getParameter("name"));
			m.setPw(req.getParameter("pw"));
			m.setName(req.getParameter("name"));
			String addr1 = req.getParameter("address1");
			String addr2 = req.getParameter("address2");
			String addr3 = req.getParameter("address3");
			m.setAddr(addr2 + "!" + addr3 + "!" + addr1);
			m.setAdmin(0);
			String birth = req.getParameter("birth");
			System.out.println(birth);
			m.setBirth(birth);
			
			mr.save(m);
			req.setAttribute("result", "가입성공");
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("result", "가입 실패");
		}
	}
	
	public void login(Member inputMember, HttpServletRequest req, HttpSession session) {
		try {
			Member dbMember = mr.findByIdEquals(inputMember.getId());

			if (dbMember != null) {
				if (inputMember.getPw().equals(dbMember.getPw())) {
					session.setAttribute("loginMember", dbMember);
					session.setMaxInactiveInterval(60); // 10분
				} else {
					req.setAttribute("result", "로그인 실패(pw오류)");
				}
			} else {
				req.setAttribute("result", "로그인 실패(없는 계정)");
			}
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("result", "로그인 실패(DB)");
		}
	}
	
	public String showIndex(HttpServletRequest req, HttpSession session) {
		Member m = (Member) session.getAttribute("loginMember");
		if (m != null) {
			if (m.getAdmin() == 1) {
				return "adminIndex";
			} else {
				return "index";
			}
		} else {
			return "index";
		}
	}

	public boolean isLogined(HttpServletRequest req, HttpSession session) {
		Member m = (Member) session.getAttribute("loginMember");
		if (m != null) {
			if (m.getAdmin() != 1) {
				req.setAttribute("loginPage", "logined.html");
			} else {
				req.setAttribute("adminContentPage", "adminMain.html");
			}
			return true;
		} else {
			req.setAttribute("loginPage", "unLogined.html");
			return false;
		}
	}

	public void logout(HttpServletRequest req, HttpSession session) {
		session.setAttribute("loginMember", null);
	}
	
	public void showInfo(HttpServletRequest req, HttpSession session) {
		Member m = (Member) session.getAttribute("loginMember");
		
//		System.out.println(m.getBirth());
		String[] address = m.getAddr().split("!");
		req.setAttribute("address1", address[2]);
		req.setAttribute("address2", address[0]);
		req.setAttribute("address3", address[1]);
		
		String dateString = m.getBirth();
//		System.out.println(dateString);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date = dateFormat.parse(dateString);
			req.setAttribute("date", date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public Resource getPhoto(String name) {
		try {
			return new UrlResource("file:" + fileDir + "/" + name);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void bye(HttpServletRequest req, HttpSession session) {
		try {
			Member m = (Member) session.getAttribute("loginMember");
			mr.deleteById(m.getId());
			
			String file = m.getPhoto();
			new File(fileDir + "/" + file).delete();
			
			req.setAttribute("result", "탈퇴 성공");

			logout(req, session);
			isLogined(req, session);
			
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("result", "탈퇴 실패");
		}
	}
	
	public void update(MultipartFile mf, Member newMember, HttpServletRequest req, HttpSession session) {
		Member oldMember = (Member) session.getAttribute("loginMember");
		String oldFile = oldMember.getPhoto();
		System.out.println(oldFile);
		try {
			System.out.println(mf);
			if (mf.isEmpty()) {
				newMember.setPhoto(oldFile);
			} else {
				String file = getFileName(mf);
				File f = new File(fileDir + "/" + file);
				mf.transferTo(f); // 실제업로드
				newMember.setPhoto(file);
			}
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("result", "수정 실패(파일)");
			return;
		}
		
		String newFile = newMember.getPhoto();
		System.out.println(newFile);
		try {
			String pw = req.getParameter("pw");
			String name = req.getParameter("name");
			String address1 = req.getParameter("address1");
			String address2 = req.getParameter("address2");
			String address3 = req.getParameter("address3");
			String address = address2 + "!" + address3 + "!" + address1;
			
			newMember.setId(oldMember.getId());
			newMember.setPw(pw);
			newMember.setName(name);
			newMember.setAddr(address);
			newMember.setAdmin(oldMember.getAdmin());
			
			String birthString = oldMember.getBirth();
			birthString = birthString.substring(0, 10);
//			System.out.println(birthString);
			newMember.setBirth(birthString);
			
			mr.save(newMember);
			req.setAttribute("result", "수정 성공");
			if (!oldFile.equals(newFile) && !oldFile.equals("logo.png")) {
				String photo = oldMember.getPhoto();
				new File(fileDir + "/" + photo).delete();
			}
			session.setAttribute("loginMember", newMember);
		} catch (Exception e) {
			req.setAttribute("result", "수정 실패");
			if (!oldFile.equals(newFile)) {
				String photo = newMember.getPhoto();
				new File(fileDir + "/" + photo).delete();
			}
		}
	}
	
	
	
	
}
