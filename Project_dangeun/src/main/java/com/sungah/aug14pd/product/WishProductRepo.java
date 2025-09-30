package com.sungah.aug14pd.product;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sungah.aug14pd.member.Member;
import com.sungah.aug14pd.sell.Sell;

import java.util.List;



@Repository
public interface WishProductRepo extends CrudRepository<WishLists, Integer>{
	public abstract List<WishLists> findByMemberIdAndProductId(Member memberId, Sell productId);
//	public abstract List<WishList> deleteByMemberIdAndProductId(Member memberId, Product productId);
//	public abstract List<WishList> findByMemberIdAndProductId(String memberId, Product productId);
//	public abstract List<WishList> deleteByMemberIdAndProductId(String memberId, Integer productId);
	public abstract List<WishLists> findByMemberId(Member memberId);

}
