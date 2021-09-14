package ksmart.mybatis.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import ksmart.mybatis.domain.Goods;

@Mapper
public interface GoodsMapper {
	//상품등록
	public int addGoods(Goods goods);
	
	//상품증가코드조회
	public String getNewGoodsCode();
	
	//상품조회
	public List<Goods> getGoodsList(Map<String, Object> paramMap);
}
