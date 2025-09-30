package com.sungah.aug14pd.report;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sungah.aug14pd.member.Member;



@Repository
public interface ReportRepo extends CrudRepository<Report, String>{
public abstract List<Report> findByWriterId(Member writerId);
	
	public abstract Optional<Report> findById(Integer id);
}
