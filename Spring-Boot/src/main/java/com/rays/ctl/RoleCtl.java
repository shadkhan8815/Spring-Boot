package com.rays.ctl;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rays.common.BaseCtl;
import com.rays.common.ORSResponse;
import com.rays.dto.RoleDTO;
import com.rays.form.RoleForm;
import com.rays.service.RoleServiceImp;

@RestController
@RequestMapping(value = "Role")
public class RoleCtl extends BaseCtl {

	@Autowired
	public RoleServiceImp roleService;

	@PostMapping("save")
	public ORSResponse save(@RequestBody @Valid RoleForm form, BindingResult bindingResult) {

		ORSResponse ors = validate(bindingResult);

		if (!ors.isSuccess()) {
			return ors;
		}

		RoleDTO dto = (RoleDTO) form.getDto();

		long pk = roleService.add(dto);

		ors.addData(pk);
		ors.addMessage("Role addedd successfully..!!!");

		return ors;

	}
	
	@PostMapping("update")
	public ORSResponse update(@RequestBody @Valid RoleForm form, BindingResult bindingResult) {

		ORSResponse ors = validate(bindingResult);

		if (!ors.isSuccess()) {
			return ors;
		}

		RoleDTO dto = (RoleDTO) form.getDto();

		roleService.update(dto);

		ors.addMessage("Role update successfully..!!!");

		return ors;

	}
	
	@PostMapping("delete/{ids}")
	public ORSResponse delete(@PathVariable long[] ids) {

		ORSResponse ors = new ORSResponse();

		for (long id : ids) {
			roleService.delete(id);
		}

		ors.addMessage("data deleted successfully");
		ors.setSuccess(true);

		return ors;
	}
	
	@GetMapping("get/{id}")
	public ORSResponse get(@PathVariable long id) {

		ORSResponse ors = new ORSResponse();

		RoleDTO dto = roleService.findById(id);

		if (dto != null) {
			ors.setSuccess(true);
		}

		ors.addData(dto);
		return ors;
	}

}
