package com.sungah.aug14pd.up;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class ProfileDAO {
	
	@Autowired
	private ProfileRepo pr;
	
	public void get(HttpServletRequest req,String id) {
        try {
            Optional<Profile> pOptional = pr.findById(id);
            if (pOptional.isPresent()) {
                Profile profile = pOptional.get();
                req.setAttribute("ppp", profile);
            } else {
                // 프로필이 없는 경우에 대한 처리
                // 예를 들어, 오류 메시지를 설정하거나 다른 작업을 수행
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
//	public void get(HttpServletRequest req, String id) {
//		try {
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
	
}
