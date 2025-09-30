package com.sungah.aug14pd.up;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "MEMBER")
public class Profile {
	
	@Id
	@Column(name = "m_id")
	private String id;
	
	@Column(name = "m_name")
	private String name;
	
	@Column(name = "m_birth")
	private Date birth;
	
	@Column(name = "m_loc")
	private String loc;
	
	@Column(name = "m_pw")
	private String pw;
	
	@Column(name = "m_photo")
	private String photo;
	
	@Column(name = "m_admin")
	private Integer admin;
	
	@Column(name = "m_addr")
	private String addr;
}
