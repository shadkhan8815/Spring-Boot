package com.rays.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rays.dto.RoleDTO;
import com.rays.dto.UserDTO;

@Repository
public class UserDAOImp implements UserDAOInt{

	@PersistenceContext
	public EntityManager entityManager;

	@Autowired
	public RoleDAOInt roledao;
	
	@Override
	public void populate(UserDTO dto) {
          RoleDTO Rdto = roledao.findByPk(dto.getRoleId());
          dto.setRoleName(Rdto.getName());
          
          if (dto.getId() != null && dto.getId() > 0) {
			UserDTO userdto = findByPk(dto.getId());
		}
	}

	@Override
	public long add(UserDTO dto) {
		populate(dto);
		entityManager.persist(dto);
		return dto.getId();
	}

	@Override
	public void update(UserDTO dto) {
        populate(dto);
        entityManager.merge(dto);
	}

	@Override
	public void delete(UserDTO dto) {
		entityManager.remove(dto);
	}

	@Override
	public UserDTO findByPk(long pk) {
		UserDTO dto = entityManager.find(UserDTO.class, pk);
		return dto;
	}

}
