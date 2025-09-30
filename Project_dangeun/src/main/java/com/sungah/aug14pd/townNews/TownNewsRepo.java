package com.sungah.aug14pd.townNews;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TownNewsRepo extends CrudRepository<TownNews, Integer> {
	//public abstract List<TownNews> findAll(Pageable currentPageable); // 게시판 테이블 전체조회
	public abstract List<TownNews> findBytownNewsMemberId(String s);
	public abstract void deleteBytownNewsNum(Integer i);
	
}