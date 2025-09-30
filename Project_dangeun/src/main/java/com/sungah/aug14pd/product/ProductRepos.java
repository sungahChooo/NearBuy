package com.sungah.aug14pd.product;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sungah.aug14pd.member.Member;
import com.sungah.aug14pd.sell.Sell;
import com.sungah.aug14pd.up.Profile;


@Repository
public interface ProductRepos extends CrudRepository<Sell, Integer>{

	public abstract List<Sell> findAll();
	public abstract List<Sell> findByState(String state);
	public abstract List<Sell> findAllByState(String state);
	public abstract List<Sell> findByStateAndMemberId(String state, Profile memberId);
	public abstract List<Sell> findByIdAndMemberId(Sell id, Member memberId);
	public abstract Optional<Sell> findById(Sell id);
	public abstract Optional<Sell> findById(Integer id);
//	public abstract List<Product> findById(Long id);
	
//	public abstract List<Product> findByMemberId(List<Member> members);
//	public abstract List<Product> findBy

	
}
