package ksmart.mybatis.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ksmart.mybatis.service.MemberService;

@Controller
public class MainController {
	
	private final MemberService memberService;
	
	public MainController(MemberService memberService) {
		this.memberService = memberService;
	}

	@GetMapping("/")
	public String main() {
		return "main";
	}
	/**
	 * /loginHistory
	 * @param model
	 * @return
	 */
	
	@GetMapping("/loginHistory")
	public String loginHistory(Model model
							  ,@RequestParam(name="currentPage", required = false, defaultValue = "1") int currentPage) {
		
		Map<String, Object> resultMap = memberService.getLoginHistory(currentPage);
				
		model.addAttribute("title"			, "로그인 이력");
		model.addAttribute("loginHistory"	, resultMap.get("loginHistory"));
		model.addAttribute("currentPage"	, currentPage);
		model.addAttribute("lastPage"		, resultMap.get("lastPage"));
		model.addAttribute("startPageNum"	, resultMap.get("startPageNum"));
		model.addAttribute("endPageNum"		, resultMap.get("endPageNum"));
		
		return "login/loginHistory";
	}
	
	
	
	
	
	
	
	
	
	
	
}
