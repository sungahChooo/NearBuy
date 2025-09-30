package com.sungah.aug14pd.townnewsyun;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class TownNewsReplyDAO {

	@Autowired
	private TownNewsReplyRepos tnrr;

	// 게시판 댓글 등록 메소드
	@Transactional
	public void regTownNewsReply(TownNewsReplys tnr, HttpServletRequest req) {
		try {
			tnrr.save(tnr); // 등

			req.setAttribute("result", "등록성공");
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("result", "등록실패");
		}
	}

//	public TownNewsReply addReplyToTownNews(Integer townNewNum, TownNewsReply reply) {
//	    Optional<TownNews> optionalTownNews = TownNewsRepo.findById(townNewNum); // findById의 반환값은 Optional<TownNews> 타입
//
//	    if (optionalTownNews.isPresent()) {
//	        TownNews townNews = optionalTownNews.get();
//	        reply.setTownnews(townNews);
//	        return TownNewsReplyRepo.save(reply);
//	    }
//
//	    return null;
//	}	

}
