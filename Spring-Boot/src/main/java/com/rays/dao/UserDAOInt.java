package com.rays.dao;

import com.rays.dto.UserDTO;

public interface UserDAOInt {
	
	public void populate(UserDTO dto) ;
	
	public long add (UserDTO dto);
	
	public void update(UserDTO dto);
	
	public void delete(UserDTO dto);
	
	public UserDTO findByPk(long pk);

}
