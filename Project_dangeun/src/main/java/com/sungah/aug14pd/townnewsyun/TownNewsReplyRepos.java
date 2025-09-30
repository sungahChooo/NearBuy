package com.sungah.aug14pd.townnewsyun;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface TownNewsReplyRepos extends CrudRepository<TownNewsReplys, Integer>{
	public abstract List<TownNewsReplys> findAll(); // 게시판 테이블 전체조회
}
