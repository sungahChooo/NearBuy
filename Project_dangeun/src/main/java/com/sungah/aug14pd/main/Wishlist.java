package com.sungah.aug14pd.main;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "wishlist")
public class Wishlist {
	@Id
	@SequenceGenerator(sequenceName = "wishlist_seq", name = "ws", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ws")
	@Column(name = "w_id")
	private Integer id;

	@Column(name = "w_m_id")
	private String user;

	@Column(name = "w_p_id")
	private Integer productId;
}
