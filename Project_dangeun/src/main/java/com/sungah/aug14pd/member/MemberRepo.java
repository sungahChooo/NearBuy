package com.sungah.aug14pd.member;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;



@Repository
public interface MemberRepo extends CrudRepository<Member, String> {
	public abstract List<Member> findAll();
	public abstract List<Member> findByAdminAndIdContainingOrAdminAndNameContaining(Integer i1, String s1, Integer i2, String s2);
	public abstract Member findByIdEquals(String s);
	public abstract void deleteById(String s);
	public abstract boolean existsById(String id);
	public abstract Optional<Member> findById(String s);
	public abstract Optional<Member> findById(Member m);
}
