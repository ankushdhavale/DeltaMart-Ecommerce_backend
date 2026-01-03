package com.app.entities;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="users")
@ToString
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;
	
	private String firstname;
	private String lastname;
	private String mobileNumber;
	
	@Column(unique = true)
	private String email;
	private String password;
		
	private String city;
	private String state;
	private String country;
	
	@Enumerated(EnumType.STRING)
	private RoleEnum role;
	
	
//	@OneToOne(cascade = CascadeType.REMOVE,orphanRemoval = true)
//	@JoinColumn(name = "address_id")
//	private Address addresses;
	
	@JsonIgnore
	@OneToOne(mappedBy = "user", cascade = {CascadeType.REMOVE,CascadeType
					.PERSIST,CascadeType.MERGE},orphanRemoval = true)
	private Cart cart;
	
	@JsonIgnore
	@ManyToMany
	@JoinTable(name="user_favorite_products",joinColumns = @JoinColumn(name="user_id"),
			inverseJoinColumns = @JoinColumn(name="product_id"))
	private Set<Products> favoriteProducts;
}
