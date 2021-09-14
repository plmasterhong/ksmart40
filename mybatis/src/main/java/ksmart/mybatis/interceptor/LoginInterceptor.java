package ksmart.mybatis.interceptor;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginInterceptor implements HandlerInterceptor{
	
	
	private static final Logger log = LoggerFactory.getLogger(LoginInterceptor.class);

	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		
		
		//세션의 정보가 없으면 로그인페이지로 이동
		HttpSession session = request.getSession();
		
		// 아이디
		String sessionId = (String) session.getAttribute("SID");
		// 레벨정보
		Object sessionLv = session.getAttribute("SLEVEL");	
		
		int sessionLevel = 0;		
		if(sessionLv != null) sessionLevel = (int) session.getAttribute("SLEVEL");
		
		// URI 정보
		String requestUri = request.getRequestURI();
		
		log.info("sessionLevel : {}", sessionLevel);
		log.info("requestUri : {}", requestUri);
		
		if(sessionId == null || sessionLevel == 0) {
			if(requestUri.indexOf("/addMember") > -1) {
				return true;
			}else {
				response.sendRedirect("/login");
				return false;				
			}
		}else {
			requestUri	 = requestUri.trim();
			
			//권한에 따른 주소 설정 ( 권한에 해당하는 주소가 아닐 시 메인페이지로 이동 )
			if(sessionLevel == 2) {
				if( requestUri.indexOf("/addMember") 	> -1 ||
					requestUri.indexOf("/memberList") 	> -1 ||
					requestUri.indexOf("/modifyMember") > -1 ||
					requestUri.indexOf("/removeMember") > -1 ||
					requestUri.indexOf("/loginHistory") > -1 ) {
					
					response.sendRedirect("/");
					return false;
				}
			}
			
			if(sessionLevel == 3) {
				List<String> excludePathList = new ArrayList<String>();
				excludePathList.add("/addGoods");
				excludePathList.add("/goodsList");
				excludePathList.add("/modifyGoods");
				excludePathList.add("/removeGoods");
				
				if(excludePathList.contains(requestUri)) {
					response.sendRedirect("/");
					return false;
				}
				
			}
		}
		
		
		return true;
	}
}
