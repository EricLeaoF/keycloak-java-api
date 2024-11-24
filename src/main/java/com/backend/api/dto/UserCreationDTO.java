package com.backend.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserCreationDTO {
	
	@NotBlank(message = "Param firstName must be filled")
  private String firstName;
	@NotBlank(message = "Param lastName must be filled")
  private String lastName;
	@NotBlank(message = "Param username must be filled")
  private String username;
	@NotBlank(message = "Param email must be filled")
	@Email(message = "Param email must be valid")
  private String email;
	@NotBlank(message = "Param password must be filled")
  private String password;

  public UserCreationDTO(String firstName, String lastName, String username, String email, String password) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.username = username;
    this.email = email;
    this.password = password;
  }

	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
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
