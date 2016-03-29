package com.yimayhd.sellerAdmin.controller.main;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public String toMain(Model model){
//		UserDO user = sessionManager.getUser();
//		List<HaMenuDO> haMenuDOList = userService.getMenuListByUserId(user.getId());
//		model.addAttribute("menuList", haMenuDOList);
//		model.addAttribute("userNickName", user.getNickname());
		return "/layout/layout";
	}
}
