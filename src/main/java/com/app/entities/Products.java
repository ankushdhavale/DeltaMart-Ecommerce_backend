package com.app.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="Products")
public class Products {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long productId;
	
	private String productName;
	
	private String description;
	private String imgUrl;
	
	private Integer quantity;
	
	private double price;
	
	@ManyToOne
	@JoinColumn(name="seller_id")
	private User seller;
	
	@ManyToOne
	@JoinColumn(name="category_id")
	private Category category;
	
	@OneToMany(mappedBy = "product",cascade = {CascadeType.PERSIST,CascadeType.MERGE})
	private List<OrderItem> orderItems = new ArrayList<>();
	
	@ManyToMany(mappedBy = "favoriteProducts")
	private Set<User> users;
}
