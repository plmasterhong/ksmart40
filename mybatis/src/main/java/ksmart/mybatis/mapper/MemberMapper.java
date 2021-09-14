package ksmart.mybatis.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import ksmart.mybatis.domain.Member;
import ksmart.mybatis.domain.MemberLevel;

@Mapper
public interface MemberMapper {
	//인터페이스 추상메소드, 상수만 선언할 수 있다. 선언
	
	//로그인 이력 총 행의 갯수
	public int getLoginHistoryCount();
	//로그인 이력 조회
	public List<Map<String, Object>> getLoginHistory(Map<String, Object> paramMap);
	
	//판매자의 상품을 구매한 이력 삭제
	public int removeOrderBySellerId(String memberId);
	//판매자가 등록한 상품 삭제
	public int removeGoods(String memberId);
	//구매자가 구매한 이력삭제
	public int removeOrder(String memberId);
	//모든회원의 로그인 이력삭제
	public int removeLoginHistory(String memberId);
	//회원삭제
	public int removeMember(String memberId);
	
	//회원정보수정
	public int modifyMember(Member member); 
	
	//회원 정보 조회
	public Member getMemberInfoById(String memberId);
	
	//회원 등록
	public int addMember(Member member);
	
	//회원목록 조회
	public List<Member> getMemberList(Map<String, Object> paramMap);
	
	//회원 등급 조회
	public List<MemberLevel> getMemberLevelList();
}
