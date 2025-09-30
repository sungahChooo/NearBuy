package com.sungah.aug14pd.admin;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sungah.aug14pd.report.Report;

@Repository
public interface AdminReportRepo extends CrudRepository<AdminReport, String>{

	public abstract List<AdminReport> findByreportedId(String s);
	public abstract boolean existsByreportedId(String s);
}
