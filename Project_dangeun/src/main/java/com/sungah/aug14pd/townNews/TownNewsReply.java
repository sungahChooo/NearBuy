package com.sungah.aug14pd.townNews;

import java.time.LocalDateTime;

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
import lombok.Data;

@Entity(name="townnews_reply")
@Data
public class TownNewsReply {
	@Id
    @SequenceGenerator(sequenceName = "townnews_seq", name = "stn", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "stn")	
	@Column(name = "tr_id") 
	private Integer townNewsReplyNum;
	
	@ManyToOne
	@JoinColumn(name = "tr_t_id", referencedColumnName = "t_id") 
	private TownNews townnews;
	
	@Column(name = "tr_m_id") 
	private String townNewsReplyWriterId;
	
	@Column(name = "tr_text") 
	private String townNewsReplyText;
	
	@Column(name = "tr_date") 
	private LocalDateTime townNewsReplyDate;
	
	@Transient // 데이터베이스에 매핑하지 않음
    private String townNewsReplyTimestampFormatted;
	
	
    @PrePersist
    public void beforePersist() {
        LocalDateTime now = LocalDateTime.now();
        townNewsReplyDate = now;
    }
}
