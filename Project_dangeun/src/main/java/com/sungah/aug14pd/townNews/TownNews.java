package com.sungah.aug14pd.townNews;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name="townnews")
@Data	
@NoArgsConstructor
public class TownNews {
	
	@Id
    @SequenceGenerator(sequenceName = "townnews_seq", name = "stn", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "stn")
	@Column(name = "t_id") 
	private Integer townNewsNum;
	
	@Column(name = "t_m_id") 
	private String townNewsMemberId;
	
	@Column(name = "t_title") 
	private String townNewsTitle;
	
	@Column(name = "t_text") 
	private String townNewsText;
	
	@Column(name = "t_photo") 	
	private String townNewsPhoto;
	
    @Column(name = "t_date")
    private LocalDateTime townNewsTimestamp; // 날짜와 시간 정보를 모두 다룸
    
    @OneToMany(mappedBy = "townnews", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TownNewsReply> replies = new ArrayList<>();
    
    @Transient // 데이터베이스에 매핑하지 않음
    private String townNewsTimestampFormatted;
    
    @PrePersist
    public void beforePersist() {
        LocalDateTime now = LocalDateTime.now();
        townNewsTimestamp = now;
    }
	
}