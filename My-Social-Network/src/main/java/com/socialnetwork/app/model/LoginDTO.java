package com.socialnetwork.app.model;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class LoginDTO implements Serializable {

	@NotNull
	@Size(min = 5, max = 50)
	private String email;

	@NotNull
	@Size(min = 5, max = 50)
	private String password;

	public LoginDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


}
