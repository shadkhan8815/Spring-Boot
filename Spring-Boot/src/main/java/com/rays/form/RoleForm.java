package com.rays.form;

import javax.validation.constraints.NotEmpty;

import com.rays.common.BaseDTO;
import com.rays.common.BaseForm;
import com.rays.dto.RoleDTO;

public class RoleForm extends BaseForm {
	
	@NotEmpty(message = "name is required")
	private String name;

	@NotEmpty(message = "description is required")
	private String description;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public BaseDTO getDto() {
		RoleDTO dto = (RoleDTO) initDTO(new RoleDTO());
		dto.setName(name);
		dto.setDescription(description);
		return dto;
	}

}