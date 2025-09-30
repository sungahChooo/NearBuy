package com.sungah.aug14pd.sell;

import java.time.LocalDateTime;
import java.util.Date;

import com.sungah.aug14pd.up.Profile;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "PRODUCT")
public class Sell {
	
	@Id
	@SequenceGenerator(sequenceName = "product_seq", name="ps", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ps")
	@Column(name = "p_id")
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "p_m_id", referencedColumnName = "m_id")
	private Profile memberId; //join할 때 String을 member JavaBean으로 바꿔야됌(안하면 터짐)
	
	@Column(name = "p_name")
	private String name;
	
	@Column(name = "p_price")
	private Integer price;
	
	@Column(name = "p_desc")
	private String description;
	
	@Column(name = "p_category")
	private String category;
	
	@Column(name = "p_view")
	private Integer view;
	
	@Column(name = "p_uploaddate")
	private LocalDateTime uploadDate;
	
	@Column(name = "p_updatedate")
	private LocalDateTime updateDate;
	
	@Column(name = "p_photo")
	private String photo;
	
	@Column(name = "p_loc")
	private String loc;
	
	@Column(name = "p_state")
	private String state;
	
	@PrePersist
    public void beforePersist() {
        LocalDateTime now = LocalDateTime.now();
        uploadDate = now;
        updateDate = now;
    }
	
}
