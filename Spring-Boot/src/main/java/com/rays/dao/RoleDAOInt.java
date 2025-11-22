package com.rays.dao;

import java.util.List;

import com.rays.dto.RoleDTO;

public interface RoleDAOInt {

	public long add(RoleDTO dto);

	public void update(RoleDTO dto);

	public void delete(RoleDTO dto);

	public RoleDTO findByPk(long pk);

	public List search(RoleDTO dto, int pageNo, int pageSize);

}
