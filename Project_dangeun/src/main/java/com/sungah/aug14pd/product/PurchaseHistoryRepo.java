package com.sungah.aug14pd.product;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sungah.aug14pd.member.Member;

import java.util.List;


public interface PurchaseHistoryRepo extends JpaRepository<PurchaseHistory, Integer>{

	public abstract List<PurchaseHistory> findByBuyer(Member buyer);

}
