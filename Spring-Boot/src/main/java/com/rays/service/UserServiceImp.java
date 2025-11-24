package com.rays.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.rays.dao.UserDAOInt;
import com.rays.dto.RoleDTO;
import com.rays.dto.UserDTO;

@Service
@Transactional
public class UserServiceImp implements UserServiceInt {
	
	@PersistenceContext
	public EntityManager entityManager;

	@Autowired
	public UserDAOInt userdao;


	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public long add(UserDTO dto) {
		long pk = userdao.add(dto);
		return pk;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void update(UserDTO dto) {
      userdao.update(dto);		
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void delete(long id) {
		try {
			UserDTO dto = findById(id);
			userdao.delete(dto);
		} catch (RuntimeException e) {
			System.out.println(e.getMessage());
		}	
	}

	@Transactional(readOnly = true)
	@Override
	public UserDTO findById(long pk) {
		UserDTO dto = userdao.findByPk(pk);
		return dto;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public long save(UserDTO dto) {
		Long id = dto.getId();
		if (id != null && id > 0) {
			update(dto);
		} else {
			id = add(dto);
		}
		return id;
	}

	@Transactional(readOnly = true)
	public List search(UserDTO dto, int pageNo, int pageSize) {
		List list = userdao.search(dto, pageNo, pageSize);
		return list;
	}
}
