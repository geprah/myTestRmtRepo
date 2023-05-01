package com.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bean.Login;
import com.repository.LoginRepository;

@Service
public class LoginService {

	@Autowired
	LoginRepository loginRepository;
	
	
	public String signUp(Login login) {
		if(login.getTypeofuser().equalsIgnoreCase("admin")) {
			return "Admin account can't create";
		}else {
			Optional<Login> result = loginRepository.findById(login.getEmailid());
			if(result.isPresent()) {
				return "Account already present";
			}else {
				loginRepository.save(login);
				return "Account created successfully";
			}
		}
	}
	
	
	public String signIn(Login login) {
		//Login ll = loginRepository.signInWithNative(login.getEmailid(), login.getPassword(), login.getTypeofuser());
		Login ll = loginRepository.signInWithNative(login.getEmailid(), login.getPassword());
		if(ll==null) {
			return "Invalid emailid or password";
		}else {
			login.setTypeofuser(ll.getTypeofuser());
			if(ll.getTypeofuser().equalsIgnoreCase("admin")) {
				//System.out.println("Login service: "+ ll.getTypeofuser());
				return "Admin login successfully";
			}else {
				return "Customer login successfully";
			}
		}
	}
}