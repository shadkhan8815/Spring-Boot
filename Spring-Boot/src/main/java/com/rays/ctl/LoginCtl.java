package com.rays.ctl;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rays.common.BaseCtl;
import com.rays.common.ORSResponse;
import com.rays.dto.UserDTO;
import com.rays.form.LoginForm;
import com.rays.form.UserRegistrationForm;
import com.rays.service.UserServiceImp;
import com.rays.service.UserServiceInt;

@RestController
@RequestMapping(value = "Auth")
public class LoginCtl extends BaseCtl {

	@Autowired
	public UserServiceImp service;

	@PostMapping("login")
	public ORSResponse login(@RequestBody @Valid LoginForm form, BindingResult bindingResult, HttpSession session) {

		ORSResponse res = validate(bindingResult);

		if (!res.isSuccess()) {
			return res;
		}

		UserDTO dto = service.authenticate(form.getLoginId(), form.getPassword());
		if (dto != null) {
			session.setAttribute("user", dto);
			res.addData(dto);
			res.addMessage("user login successfully..!!");
			res.setSuccess(true);
		} else {
			res.addMessage("Login ID & Password is invalid..!!");
			res.setSuccess(false);
		}
		return res;
	}

	@PostMapping("signUp")
	public ORSResponse signUp(@RequestBody @Valid UserRegistrationForm form, BindingResult bindingResult) {

		ORSResponse res = validate(bindingResult);

		if (!res.isSuccess()) {
			return res;
		}

		UserDTO dto = new UserDTO();

		dto = (UserDTO) form.getDto();

		/*
		 * dto.setFirstName(form.getFirstName()); dto.setLastName(form.getLastName());
		 * dto.setLoginId(form.getLoginId()); dto.setPassword(form.getPassword());
		 * dto.setDob(form.getDob()); dto.setRoleId(2L);
		 */

		long pk = service.add(dto);

		res.addData(pk);
		res.addMessage("Data Registered Successfully..!!");
		return res;
	}

	@GetMapping("logout")
	public ORSResponse logout(HttpSession session) {

		ORSResponse res = new ORSResponse();

		if (session.getId() != null) {
			session.invalidate();
			res.addMessage("user logout successfully");
			res.setSuccess(true);
			return res;
		}

		return res;

	}
}
