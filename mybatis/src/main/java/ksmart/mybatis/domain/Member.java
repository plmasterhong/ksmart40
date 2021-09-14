package ksmart.mybatis.domain;

import java.util.List;

public class Member {
	private String memberId;
	private String memberPw;
	private String memberName;
	private int memberLevel;
	private String memberLevelName;
	private String memberAddr;
	private String memberEmail;
	private String memberRegDate;
	private List<Goods> productList;
	
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getMemberPw() {
		return memberPw;
	}
	public void setMemberPw(String memberPw) {
		this.memberPw = memberPw;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public int getMemberLevel() {
		return memberLevel;
	}
	public void setMemberLevel(int memberLevel) {
		this.memberLevel = memberLevel;
	}
	public String getMemberLevelName() {
		return memberLevelName;
	}
	public void setMemberLevelName(String memberLevelName) {
		this.memberLevelName = memberLevelName;
	}
	public String getMemberAddr() {
		return memberAddr;
	}
	public void setMemberAddr(String memberAddr) {
		this.memberAddr = memberAddr;
	}
	public String getMemberEmail() {
		return memberEmail;
	}
	public void setMemberEmail(String memberEmail) {
		this.memberEmail = memberEmail;
	}
	public String getMemberRegDate() {
		return memberRegDate;
	}
	public void setMemberRegDate(String memberRegDate) {
		this.memberRegDate = memberRegDate;
	}
	public List<Goods> getProductList() {
		return productList;
	}
	public void setProductList(List<Goods> productList) {
		this.productList = productList;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Member [memberId=");
		builder.append(memberId);
		builder.append(", memberPw=");
		builder.append(memberPw);
		builder.append(", memberName=");
		builder.append(memberName);
		builder.append(", memberLevel=");
		builder.append(memberLevel);
		builder.append(", memberLevelName=");
		builder.append(memberLevelName);
		builder.append(", memberAddr=");
		builder.append(memberAddr);
		builder.append(", memberEmail=");
		builder.append(memberEmail);
		builder.append(", memberRegDate=");
		builder.append(memberRegDate);
		builder.append(", productList=");
		builder.append(productList);
		builder.append("]");
		return builder.toString();
	}
	
	
	
}
