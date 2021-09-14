package ksmart.mybatis.domain;

public class MemberLevel {
	private String levelNum;
	private String levelName;
	
	public String getLevelNum() {
		return levelNum;
	}
	public void setLevelNum(String levelNum) {
		this.levelNum = levelNum;
	}
	public String getLevelName() {
		return levelName;
	}
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MemberLevel [levelNum=");
		builder.append(levelNum);
		builder.append(", levelName=");
		builder.append(levelName);
		builder.append("]");
		return builder.toString();
	}
	
	
}
