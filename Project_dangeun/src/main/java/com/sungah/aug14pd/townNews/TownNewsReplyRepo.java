package com.sungah.aug14pd.townNews;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TownNewsReplyRepo extends CrudRepository<TownNewsReply, Integer>{
	public abstract List<TownNewsReply> findAll(); // 게시판 테이블 전체조회
}
