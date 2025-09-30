package com.skgus.project.member;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

@Repository
public interface MemberRepo extends CrudRepository<Member, String> {
	public abstract List<Member> findAll();
	public abstract Member findByIdEquals(String s);
	public abstract void deleteById(String s);
	boolean existsById(String id);
	
}
