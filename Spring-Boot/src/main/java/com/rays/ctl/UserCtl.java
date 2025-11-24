package com.rays.ctl;

import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rays.common.BaseCtl;
import com.rays.common.ORSResponse;
import com.rays.dto.AttachmentDTO;
import com.rays.dto.UserDTO;
import com.rays.form.UserForm;
import com.rays.service.AttachmentServiceInt;
import com.rays.service.UserServiceImp;

@RestController
@RequestMapping(value = "User")
public class UserCtl extends BaseCtl {

	@Autowired
	public UserServiceImp userService;

//	@Autowired
//	public RoleServiceImp roleService;
//	
	@Autowired
	public AttachmentServiceInt attachmentService ;

	@PostMapping("save")
	public ORSResponse save(@RequestBody @Valid UserForm form, BindingResult bindingResult) {

		ORSResponse res = validate(bindingResult);

		if (!res.isSuccess()) {
			return res;
		}

		UserDTO dto = (UserDTO) form.getDto();

		if (dto.getId() != null && dto.getId() > 0) {
			userService.update(dto);
			res.addData(dto.getId());
			res.addMessage("Data Updated Successfully..!!");
			res.setSuccess(true);
		} else {
			long pk = userService.add(dto);
			res.addData(pk);
			res.addMessage("Data added Successfully..!!");
			res.setSuccess(true);
		}
		return res;
	}

	@GetMapping("delete/{ids}")
	public ORSResponse delete(@PathVariable long[] ids) {

		ORSResponse res = new ORSResponse();

		for (long id : ids) {
			userService.delete(id);
		}

		res.addMessage("data deleted successfully");
		res.setSuccess(true);

		return res;
	}

	@GetMapping("get/{id}")
	public ORSResponse get(@PathVariable long id) {

		ORSResponse res = new ORSResponse();

		UserDTO dto = userService.findById(id);

		if (dto != null) {
			res.setSuccess(true);
		}

		res.addData(dto);

		return res;
	}
	
	@PostMapping("search/{pageNo}")
	public ORSResponse search(@RequestBody UserForm form, @PathVariable int pageNo) {

		ORSResponse res = new ORSResponse();

		UserDTO dto = (UserDTO) form.getDto();

		int pageSize = 5;

		List list = userService.search(dto, pageNo, pageSize);

		if (list != null && list.size() > 0) {
			res.setSuccess(true);
		}

		res.addData(list);

		return res;

	}
	
	@PostMapping("/profilePic/{userId}")
	public ORSResponse uploadPic(@PathVariable Long userId, @RequestParam("file") MultipartFile file, HttpServletRequest req) {
		
		AttachmentDTO attachdto = new AttachmentDTO(file);
		attachdto.setDescription("profile pic");
		attachdto.setUserId(userId);
		
		UserDTO userdto = userService.findById(userId);
		
		if (userdto.getImageId() != null && userdto.getImageId() > 0) {
			attachdto.setId(userdto.getImageId());
		}
		
		Long imageId = attachmentService.save(attachdto);
		
		if (userdto.getImageId() == null) {
			userdto.setImageId(imageId);
			userService.update(userdto);
		}
		
		ORSResponse ors = new ORSResponse();
		ors.addResult("imageId", imageId);
		ors.setSuccess(true);
		
		return ors ;
	}
	
	@GetMapping("/profilePic/{userId}")
	public @ResponseBody void downloadPic(@PathVariable Long userId, HttpServletResponse response) {

		try {

			UserDTO userDto = userService.findById(userId);

			AttachmentDTO attachDTO = null;

			if (userDto != null) {
				attachDTO = attachmentService.findById(userDto.getImageId());
			}

			if (attachDTO != null) {
				response.setContentType(attachDTO.getType());
				OutputStream out = response.getOutputStream();
				out.write(attachDTO.getDoc());
				out.close();
			} else {
				response.getWriter().write("ERROR: File not found");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}