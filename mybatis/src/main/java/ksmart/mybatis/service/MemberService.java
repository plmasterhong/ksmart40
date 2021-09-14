package ksmart.mybatis.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ksmart.mybatis.domain.Member;
import ksmart.mybatis.domain.MemberLevel;
import ksmart.mybatis.mapper.MemberMapper;

@Service
@Transactional
public class MemberService {
	
	
	private static final Logger log = LoggerFactory.getLogger(MemberService.class);

	//@Transactional => 트랜잭션 처리 class위에 선언되었다면 class 포함하는 메소드 단위로 트랜잭션처리
	//A(원자성)C(일관성)I(고립성)D(영속성)
	//1.필드주입방식
	/**
	 *  @Autowired private MemberMapper memberMapper;
	 */
	//2.setter 메소드 주입방식
	/**
	 *  private MemberMapper memberMapper;
	 * 
	 *  @Autowired 
	 *  public void setMemberMapper(MemberMapper memberMapper) {
			this.memberMapper = memberMapper;
		}
	 */
	//3. 생성자 메소드 주입방식 @Autowired spring framework 4.3이후 생략가능
	private final MemberMapper memberMapper;

	@Autowired
	public MemberService(MemberMapper memberMapper) {
		this.memberMapper = memberMapper;
	}
	
	//로그인 이력 페이징
	public Map<String, Object> getLoginHistory(int currentPage){
		
		//1. 페이지 번호 초기화 
		int startPageNum = 1;
		int endPageNum = 10;
		
		//2. 페이지에 보여줄 행의 갯수  
		int rowPerPage = 5;
		
		//3. 총 행의 갯수 -- rowCount
		double rowCount = memberMapper.getLoginHistoryCount();
		
		//4. 마지막 페이지 -- lastPage
		int lastPage = (int) Math.ceil(rowCount/rowPerPage);
		
		//5. 페이지 알고리즘
		int rowNum = (currentPage-1) * rowPerPage;
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("rowNum"		, rowNum);
		paramMap.put("rowPerPage"	, rowPerPage);
		
		//6. 페이지별 로그인이력조회
		List<Map<String, Object>> loginHistory = memberMapper.getLoginHistory(paramMap);
		
		//map 초기화
		paramMap.clear();
		
		//동적페이지 번호 설정
		if(currentPage > 6) {
			startPageNum 	= currentPage - 5; //현재 페이지가 7페이지일 경우 : 2page
			endPageNum		= currentPage + 4; //현재 페이지가 7페이지일 경우 : 11page
			
			if(endPageNum >= lastPage) {
				startPageNum = lastPage - 9; //현재 페이지가 21-9 12
				endPageNum = lastPage;
			}
			
		}
		
		paramMap.put("loginHistory"		, loginHistory);
		paramMap.put("lastPage"			, lastPage);
		paramMap.put("startPageNum"		, startPageNum);
		paramMap.put("endPageNum"		, endPageNum);
				
		return paramMap;
	}
	
	//회원탈퇴 로직
	public String removeMember(String memberId, String memberPw) {
		String result = "회원삭제 실패";
		
		//입력받은 값 memberId 비밀번호 일치여부
		Member member = memberMapper.getMemberInfoById(memberId);
		if(member != null) {
			if(memberPw.equals(member.getMemberPw())) {
				//삭제 프로세스
				//회원 권한 
				int memberLevel = member.getMemberLevel();
				
				//판매자
				if(memberLevel == 2) {
					memberMapper.removeOrderBySellerId(memberId);
					memberMapper.removeGoods(memberId);
				}
				
				//구매자
				if(memberLevel == 3) {
					memberMapper.removeOrder(memberId);
				}
				
				memberMapper.removeLoginHistory(memberId);
				memberMapper.removeMember(memberId);
				
				result = "회원삭제 성공";
			}
		}
		return result;
	}
	
	public int modifyMember(Member member) {
		return memberMapper.modifyMember(member);
	}
	
	public Member getMemberInfobyId(String memberId) {
		return memberMapper.getMemberInfoById(memberId);
	}
	
	public int addMember(Member member) {
		return memberMapper.addMember(member);
	}
	
	public List<MemberLevel> getMemberLevelList(){
		List<MemberLevel> memberLevelList = memberMapper.getMemberLevelList();
		return memberLevelList;
	}
	
	
	public List<Member> getMemberList(Map<String,Object> paramMap){
		String searchKey = (String) paramMap.get("searchKey");
		
		if(searchKey != null) {
			//화면에서 전달된 값이 searchKey = memberId  memberId = 'id001'
			if("memberId".equals(searchKey)) {
				searchKey = "m_id";
			}else if("memberLevel".equals(searchKey)) {
				searchKey = "m_level";
			}else if("memberName".equals(searchKey)) {
				searchKey = "m_name";
			}else {
				searchKey = "m_email";
			}
			paramMap.put("searchKey", searchKey);
		}
		
		List<Member> memberList = memberMapper.getMemberList(paramMap);
		/**
		 * 권한에 따라 1:관리자, 2:판매자, 3:구매자 
		 */
		if(memberList != null) {
			//memberList의 객체 사이즈
			int memberListSize = memberList.size();

			for(int i=0; i<memberListSize; i++) {
				int memberLevel = memberList.get(i).getMemberLevel();
				
				switch (memberLevel) {
				case 1:
					memberList.get(i).setMemberLevelName("관리자");
					break;
				case 2:					
					memberList.get(i).setMemberLevelName("판매자");
					break;
				case 3:
					memberList.get(i).setMemberLevelName("구매자");
					break;
				default:
					memberList.get(i).setMemberLevelName("비회원");
					break;
				}
			}
		}
		
		log.info("memberList : {}", memberList);
		
		return memberList;
	}
}
