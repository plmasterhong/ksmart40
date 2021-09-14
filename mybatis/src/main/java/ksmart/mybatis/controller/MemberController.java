package ksmart.mybatis.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ksmart.mybatis.domain.Member;
import ksmart.mybatis.domain.MemberLevel;
import ksmart.mybatis.service.MemberService;

@Controller
public class MemberController {
	
	private final MemberService memberService;
	
	public MemberController(MemberService memberService) {
		this.memberService = memberService;
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/login";
	}
	
	@PostMapping("/login")
	public String login(@RequestParam(name="memberId", required = false) String memberId
			           ,@RequestParam(name="memberPw", required = false) String memberPw
			           ,HttpSession session
			           ,RedirectAttributes redirectAttr) {
		//로그인 -> DB 회원테이블 있는 ID만 회원로그인 가능해야한다.
		//1.회원이 있다면
		if(memberId != null && !"".equals(memberId) &&
		   memberPw != null && !"".equals(memberPw)) {
			Member member = memberService.getMemberInfobyId(memberId);
			if(member != null) {
				if(memberPw.equals(member.getMemberPw())) {					
					session.setAttribute("SID"		, memberId);
					session.setAttribute("SLEVEL"	, member.getMemberLevel());
					session.setAttribute("SNAME"	, member.getMemberName());
					return "redirect:/";
				}
			}
		}
		//2.회원이 없다면 redirect:/login  result => 등록된 정보가 없습니다.
		redirectAttr.addAttribute("result", "등록된 정보가 없습니다.");
		return "redirect:/login";
	}
	
	@GetMapping("/login")
	public String login(Model model
					   ,@RequestParam(name="result", required = false) String result) {
		model.addAttribute("title", "로그인");
		if(result != null) model.addAttribute("result", result);
		return "login/login";
	}
	
	@PostMapping("/removeMember")
	public String removeMember(@RequestParam(name="memberId", required = false) String memberId
			                  ,@RequestParam(name="memberPw", required = false) String memberPw
			                  ,RedirectAttributes redirectAttr) {
		
		
		String result = memberService.removeMember(memberId, memberPw);
		
		if("회원삭제 실패".equals(result)) {
			redirectAttr.addAttribute("memberId", memberId);
			redirectAttr.addAttribute("result", "회원 비밀번호가 일치하지 않습니다.");
			
			//Get방식 localhost/removeMember?memberId=id012&result=회원 비밀번호가 일치하지 않습니다.
			return "redirect:/removeMember";
		}
		
		System.out.println(result);
		
		return "redirect:/memberList";
	}	
	
	@GetMapping("/removeMember")
	public String removeMember(@RequestParam(name="memberId", required = false) String memberId
							  ,@RequestParam(name="result", required = false) String result
							  ,Model model){
		
		
		model.addAttribute("title", "회원탈퇴");
		model.addAttribute("memberId", memberId);
		if(result != null) model.addAttribute("result", result);
		return "member/removeMember";
	}

	@PostMapping("/modifyMember")
	public String modifyMember(Member member) {
		//화면에서 form http 메소드 방식 post
		//키:값 => 잘만들어진 dto 생성해주면서 값을 바인딩 해준다. => 커맨드객체
		//모든 요소의 속성 name=> 키, dto 프로퍼티 == name, get, set 메소드를 통해서
		
		System.out.println("화면에서 입력받은 값:" + member);
		if(member != null && member.getMemberId() != null) {			
			memberService.modifyMember(member);
		}
		
		return "redirect:/memberList";
	}
	
	
	/**
	 * 
	 * String memberId = request.getParameter("memberId");
	 * -> @RequestParam(name="memberId", required = false) String memberId
	 * @return
	 */
	@GetMapping("/modifyMember")
	public String modifyMember(@RequestParam(name="memberId", required = false) String memberId
							   ,Model model) {
		
		Member member = memberService.getMemberInfobyId(memberId);
		List<MemberLevel> memberLevelList = memberService.getMemberLevelList();
		
		model.addAttribute("member", member);
		model.addAttribute("memberLevelList", memberLevelList);
		
		return "member/modifyMember";
	}
	
	
	/**
	 * Member member => 커맨드객체
	 * 쿼리스트링(파라미터) 키와 dto 멤버변수의 이름과 일치하면 매핑된 기준으로 값을 바인딩하는 객체
	 */
	@PostMapping("/addMember")
	public String addMember(Member member) {
		
		if(member != null)	memberService.addMember(member);
		
		return "redirect:/memberList";
	}
	
	/**
	 * @RequestMapping 모든 방식의 주소요청
	 * value=주소요청 값, method = http 주소요청 방식
	 * @GetMapping("/addMember")
	 */
	@RequestMapping(value = "/addMember", method = RequestMethod.GET)
	public String addMember(Model model) {
		
		List<MemberLevel> memberLevelList = memberService.getMemberLevelList();
		
		model.addAttribute("title", "회원등록");
		model.addAttribute("memberLevelList", memberLevelList);
		
		return "member/addMember";
	}
	
	@GetMapping("/memberList")
	public String getMemberList(@RequestParam(name="searchKey", required = false) String searchKey
			   				   ,@RequestParam(name="searchValue", required = false) String searchValue
			   				   ,Model model) {
		
		
		//map 자료구조 여러가지 자료 key value
		Map<String, Object> paramMap= new HashMap<String, Object>();
		paramMap.put("searchKey", searchKey);
		paramMap.put("searchValue", searchValue);
		
		//비즈니스 계층 호출
		List<Member> memberList = memberService.getMemberList(paramMap);

		//결과 Model 객체 세팅
		model.addAttribute("title", "회원목록");
		model.addAttribute("memberList", memberList);
		
		return "member/memberList";
	}
	
	
	
	
}
