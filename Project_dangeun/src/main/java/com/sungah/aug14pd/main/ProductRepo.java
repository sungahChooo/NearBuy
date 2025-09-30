package com.sungah.aug14pd.main;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepo extends CrudRepository<Product, Integer> {
	public abstract List<Product> findAllByOrderByUpdateDateDesc();
	public abstract List<Product> findByNameContainingOrderByUpdateDateDesc(String search);
	public abstract List<Product> findByNameContainingAndLocOrderByUpdateDateDesc(String search,String loc);
	public abstract List<Product> findByLocOrderByUpdateDateDesc(String location);
	public abstract List<Product> findAll();
	
	////////////////
	public abstract List<Product> findByCategoryOrderByUpdateDateDesc(String category);
	public abstract List<Product> findByCategoryAndLocOrderByUpdateDateDesc(String cgy,String loc);
	public abstract List<Product> findByNameContainingAndCategoryOrderByUpdateDateDesc(String search,String cgy);
	public abstract List<Product> findByCategoryAndNameContainingAndLocOrderByUpdateDateDesc(String cgy,String search,String loc);
	
	////////////////////////
	public abstract List<Product> findBywriter(String s);
	public abstract void deleteById(Integer i);
	
}
