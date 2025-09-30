package com.sungah.aug14pd.product;

import java.time.LocalDateTime;

import com.sungah.aug14pd.member.Member;
import com.sungah.aug14pd.sell.Sell;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="purchase_history")
public class PurchaseHistory {
	@Id
    @SequenceGenerator(sequenceName = "purchase_seq", name="ps", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ps")
	@Column(name = "ph_id")
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "ph_p_id")
	private Sell product;

	@ManyToOne
	@JoinColumn(name = "ph_m_id")
	private Member buyer;
}
