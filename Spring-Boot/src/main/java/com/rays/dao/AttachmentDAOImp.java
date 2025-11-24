package com.rays.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.rays.dto.AttachmentDTO;

@Repository
public class AttachmentDAOImp implements AttachmentDAOInt{

	@PersistenceContext
	public EntityManager entitymanager ;
	
	@Override
	public long add(AttachmentDTO dto) {
		entitymanager.persist(dto);
		return dto.getId();
	}

	@Override
	public void update(AttachmentDTO dto) {
          entitymanager.merge(dto);
	}

	@Override
	public void delete(AttachmentDTO dto) {
		entitymanager.remove(dto);
	}

	@Override
	public AttachmentDTO findByPk(long pk) {
      AttachmentDTO dto = entitymanager.find(AttachmentDTO.class, pk);
		return dto;
	}

}
