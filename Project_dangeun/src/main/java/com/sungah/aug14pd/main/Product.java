package com.sungah.aug14pd.main;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name="product")
public class Product {
	@Id
	@Column(name="p_id")
	private Integer productId;

	@Column(name="p_m_id")
	private String writer;
	
	@Column(name="p_name")
	private String name;
	
	@Column(name="p_price")
	private Integer price;
	
	
	@Column(name="p_desc")
	private String description;
	
	@Column(name="p_category")
	private String category;
	
	@Column(name="p_view")
	private Integer view;
	
	@Column(name="p_uploaddate")
	private Date uploadDate;
	
	@Column(name="p_updatedate")
	private Date updateDate;
	
	@Column(name="p_photo")
	private String photo;
	
	@Column(name="p_state")
	private String state;
	
	@Column(name="p_loc")
	private String loc;
	
	@Transient
	private String daysAgo;
	
}
