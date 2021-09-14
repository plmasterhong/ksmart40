package ksmart.mybatis.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ksmart.mybatis.domain.Goods;
import ksmart.mybatis.mapper.GoodsMapper;

@Service
@Transactional
public class GoodsService {

	private final GoodsMapper goodsMapper;
	
	public GoodsService(GoodsMapper goodsMapper){
		this.goodsMapper = goodsMapper;
	}
	
	public String getNewGoodsCode() {
		return goodsMapper.getNewGoodsCode();
	}
	
	public int addGoods(Goods goods) {
		return goodsMapper.addGoods(goods);
	}
	
	public List<Goods> getGoodsList(Map<String, Object> paramMap){
		String searchKey = (String) paramMap.get("searchKey");
		if(searchKey != null) {
			if("goodsCode".equals(searchKey)) {
				searchKey = "g_code";
			}else if("goodsName".equals(searchKey)) {
				searchKey = "g_name";
			}else {
				searchKey = "g_seller_id";				
			}
			paramMap.put("searchKey", searchKey);	
		}
		List<Goods> goodsList = goodsMapper.getGoodsList(paramMap);
		
		System.out.println("goodsList :" + goodsList);
		
		return goodsList;
	}
}
