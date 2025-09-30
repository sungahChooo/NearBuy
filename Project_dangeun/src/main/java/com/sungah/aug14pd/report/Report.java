package com.sungah.aug14pd.report;

import java.time.LocalDateTime;
import java.util.Date;

import com.sungah.aug14pd.member.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "report")
public class Report {
	
	
	@Id
	@SequenceGenerator(sequenceName = "report_seq", name = "rs", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rs")
	@Column(name = "r_id")
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "r_m_writerid", referencedColumnName = "m_id")
	private Member writerId;
	
	@ManyToOne
	@JoinColumn(name = "r_m_reportedid", referencedColumnName = "m_id")
	private Member reportedId;
	
	@Column(name = "r_title")
	private String title;
	
	@Column(name = "r_category")
	private String category;
	
	@Column(name = "r_text")
	private String text;
	
	@Column(name = "r_photo")
	private String photo;
	
	@Column(name = "r_date")
	private LocalDateTime date;
	
	@Transient // 데이터베이스에 매핑하지않음
	private String reportTimestampForMatted;
	
	@PrePersist
	public void beforePersist() {
		LocalDateTime now = LocalDateTime.now();
		date = now;
	}
	
}
