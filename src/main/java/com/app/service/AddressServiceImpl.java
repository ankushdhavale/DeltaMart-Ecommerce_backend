package com.app.service;

import org.springframework.stereotype.Service;

import com.app.entities.Address;
import com.app.repository.AddressRepo;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AddressServiceImpl implements AddressService{

	private AddressRepo addressRepo;
	@Override
	public void saveAddress(Address address) {
		addressRepo.save(address);
	}

}
