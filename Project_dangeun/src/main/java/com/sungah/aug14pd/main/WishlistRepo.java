package com.sungah.aug14pd.main;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface WishlistRepo extends CrudRepository<Wishlist, Integer>{
	public abstract List<Wishlist> findByUserAndProductId(String user,Integer productId);
	public abstract void deleteByUserAndProductId(String user,Integer productId);
}
