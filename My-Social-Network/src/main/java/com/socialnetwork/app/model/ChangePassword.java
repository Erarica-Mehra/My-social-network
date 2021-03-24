package com.socialnetwork.app.model;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ChangePassword implements Serializable{

	@NotNull
	@Size(min = 5, max = 50)
	private String currentPassword;

	@NotNull
	@Size(min = 5, max = 50)
	private String password;

	public String getCurrentPassword() {
		return currentPassword;
	}

	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public ChangePassword() {
		super();
		// TODO Auto-generated constructor stub
	}

}

