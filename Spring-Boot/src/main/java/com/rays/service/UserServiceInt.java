package com.rays.service;

import com.rays.dto.UserDTO;

public interface UserServiceInt {

	public long add(UserDTO dto);

	public void update(UserDTO dto);

	public void delete(long id);

	public UserDTO findById(long pk);
	
	public long save (UserDTO dto);

}
