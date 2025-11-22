package com.rays.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public class BaseCtl {

	public ORSResponse validate(BindingResult bindingresult) {

		ORSResponse ors = new ORSResponse(true);

		if (bindingresult.hasErrors()) {
			ors.setSuccess(false);

			Map<String, String> errors = new HashMap<String, String>();

			List<FieldError> list = bindingresult.getFieldErrors();

			list.forEach(e -> {
				errors.put(e.getField(), e.getDefaultMessage());
			});

			ors.addInputError(errors);
		}
		return ors;
	}
}
