package cz.panjeskyne.controller;

import javax.transaction.Transactional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {

	@Transactional
	@RequestMapping
	public String main(Model model) {
		return "main";
	}
}
