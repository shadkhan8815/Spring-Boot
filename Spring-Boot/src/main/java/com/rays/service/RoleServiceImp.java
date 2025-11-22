package com.rays.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.rays.dao.RoleDAOInt;
import com.rays.dto.RoleDTO;

@Service
@Transactional
public class RoleServiceImp implements RoleServiceInt {
	
	@Autowired
	public RoleDAOInt roledao ;

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public long add(RoleDTO dto) {
		long pk = roledao.add(dto);
		return pk;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void update(RoleDTO dto) {
     roledao.update(dto);		
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void delete(long id) {
		try {
			RoleDTO dto = findById(id) ;
			roledao.delete(dto);
			
		} catch (Exception e) {
		}
	}

	@Transactional(readOnly = true)
	@Override
	public RoleDTO findById(long pk) {
		RoleDTO dto = roledao.findByPk(pk);
		return dto;
	}

	@Transactional(readOnly = true)
	@Override
	public List search(RoleDTO dto, int pageNo, int pageSize) {
       List list = roledao.search(dto, pageNo, pageSize);
		return list;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public long save(RoleDTO dto) {
        Long id = dto.getId();
        
        if (id != null && id > 0) {
			update(dto);
		}else {
			id = add(dto);
		}
		return id;
	}

}
