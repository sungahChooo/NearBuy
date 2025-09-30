package com.sungah.aug14pd.product;



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

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name="WISHLIST")
public class WishLists {
	
    @Id
    @SequenceGenerator(sequenceName = "wishlist_seq", name="ws", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ws")
	@Column(name = "w_id")
	private Integer wid;
	
    @ManyToOne
	@JoinColumn(name="w_m_id", referencedColumnName = "m_id")
	private Member memberId;
	
    @ManyToOne
	@JoinColumn(name="w_p_id", referencedColumnName = "p_id")
	private Sell productId;
}
