package com.sungah.aug14pd.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sungah.aug14pd.member.Member;
import com.sungah.aug14pd.member.MemberRepo;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class AddressDAO {
	
	@Autowired
	private MemberRepo mr;

	@Transactional
	public void updateLoc(HttpServletRequest req, String districtName) {
		Member m = (Member) req.getSession().getAttribute("loginMember");
		
		// 멤버의 위치 데이터를 업데이트
		Member memberForUpdate=new Member();
		memberForUpdate.setId(m.getId());
		memberForUpdate.setName(m.getName());
		memberForUpdate.setPw(m.getPw());
		memberForUpdate.setBirth(m.getBirth().substring(0,10));
		memberForUpdate.setLoc(m.getLoc());
		memberForUpdate.setPhoto(m.getPhoto());
		memberForUpdate.setAdmin(m.getAdmin());
		memberForUpdate.setAddr(m.getAddr());
		
		memberForUpdate.setLoc(districtName); // 변경된 부분: 멤버의 loc 필드에 주소 저장
	    
	    mr.save(memberForUpdate);
	    m.setLoc(districtName);
	    
	    req.getSession().setAttribute("loginMember", m);


	}
}
