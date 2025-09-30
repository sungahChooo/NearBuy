package com.sungah.aug14pd.admin;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "REPORT")
public class AdminReport {

	@Id
	@Column(name = "r_id")
	private Integer id;
	
	@Column(name = "r_m_writerid")
	private String writerId;
	
	@Column(name = "r_m_reportedid")
	private String reportedId;
	
	@Column(name = "r_title")
	private String title;
	
	@Column(name = "r_category")
	private String category;
	
	@Column(name = "r_text")
	private String text;
	
	@Column(name = "r_photo")
	private String photo;
	
	@Column(name = "r_date")
	private Date date;
}
