package com.rays.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.rays.dto.RoleDTO;

@Repository
public class RoleDAOImp implements RoleDAOInt {
	
	@PersistenceContext
	public EntityManager entityManager;

	@Override
	public long add(RoleDTO dto) {
		entityManager.persist(dto);
		return dto.getId();
	}

	@Override
	public void update(RoleDTO dto) {
        entityManager.merge(dto);		
	}

	@Override
	public void delete(RoleDTO dto) {
         entityManager.remove(dto);		
	}

	@Override
	public RoleDTO findByPk(long pk) {
		RoleDTO dto = entityManager.find(RoleDTO.class, pk);
		return dto;
	}

	@Override
	public List search(RoleDTO dto, int pageNo, int pageSize) {
         
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<RoleDTO> cq = builder.createQuery(RoleDTO.class);
		Root<RoleDTO> root = cq.from(RoleDTO.class);
		List<Predicate> predicatelist = new ArrayList<Predicate>();
		
		if (dto != null) {
			if(dto.getName() != null && dto.getName().length() > 0) {
				predicatelist.add(builder.like(root.get("name"), dto.getName() + "%"));
			}
		}
		cq.where(predicatelist.toArray(new Predicate[predicatelist.size()]));
		TypedQuery<RoleDTO> query = entityManager.createQuery(cq);
		
		if (pageSize > 0) {
			query.setFirstResult(pageNo * pageSize);
			query.setMaxResults(pageSize);
		}
		List<RoleDTO> list = query.getResultList();
		return list;
	}

}
