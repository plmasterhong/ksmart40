package ksmart.mybatis.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ksmart.mybatis.domain.Goods;
import ksmart.mybatis.service.GoodsService;

@Controller
public class GoodsController {
	
	private final GoodsService goodsService;
	
	public GoodsController(GoodsService goodsService) {
		this.goodsService = goodsService;
	}
	//상품수정, 상품삭제 이번주
	
	//상품목록 reusltMap assosiation ,paging, interceptor, cafe24, git
	
	
	@GetMapping("/goodsList")
	public String getGoodsList(@RequestParam(name="searchKey", required = false) String searchKey
							  ,@RequestParam(name="searchValue", required = false) String searchValue
							  ,Model model) {
		Map<String,Object> paramMap = new HashMap<String, Object>();
		paramMap.put("searchKey", searchKey);
		paramMap.put("searchValue", searchValue);		
		
		List<Goods> goodsList = goodsService.getGoodsList(paramMap);
		
		model.addAttribute("title", "상품목록");
		model.addAttribute("goodsList", goodsList);
		
		return "goods/goodsList";
	}
	

	//요청 request -> 유지 /addGoods 
	@GetMapping("/addGoods")
	public String addMember(Model model, HttpSession session) {
		//상품증가코드
		String newGoodsCode = goodsService.getNewGoodsCode();
		
		//아이디
		String sellerId = (String) session.getAttribute("SID");
		
		model.addAttribute("title", "상품등록");
		model.addAttribute("goodsCode", newGoodsCode);
		return "goods/addGoods";
	}
	
	@PostMapping("/addGoods")
	public String addMember(Goods goods) {
		
		System.out.println("화면에서 입력받은 값 Goods : " + goods);
		goodsService.addGoods(goods);
		
		return "redirect:/goodsList";
	}
}
