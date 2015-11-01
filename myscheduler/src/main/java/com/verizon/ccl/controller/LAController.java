package com.verizon.ccl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class LAController {
	
	@RequestMapping(value="/loadLA")
	public void loadLA() {
	}

}
