package ksmart.mybatis.interceptor;

import java.util.Set;
import java.util.StringJoiner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class CommonInterceptor implements HandlerInterceptor{
	
	/**
	 * componentscan 대상 : @controller, @service, @mapper, @configuration, @Bean, @Component
	 */	
	
	private static final Logger log = LoggerFactory.getLogger(CommonInterceptor.class);

	/**
	 * preHandle :  controller 진입 전에 실행 메소드
	 * HandlerMapping이 핸들러 객체를 결정한 후 HandlerAdapter가 핸들러를 호출하기 전에 호출되는 메소드
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		HandlerMethod handlerObj = (HandlerMethod) handler;
		
		Set<String> paramKeySet = request.getParameterMap().keySet();
		
		StringJoiner param = new StringJoiner(", ");
		
		for( String key : paramKeySet) {
			param.add(key + ": " + request.getParameter(key));
		}
		
		
		log.info("CommonInterceptor======================================================START");
		log.info("ACCESS INFO======================================================START");
		log.info("PORT		::::::		{}", 			request.getLocalPort());
		log.info("SERVER NAME		::::::		{}", 	request.getServerName());
		log.info("HTTP METHOD		::::::		{}", 	request.getMethod());
		log.info("URI			::::::		{}", 			request.getRequestURI());
		log.info("CONTROLLER		::::::		{}", 	handlerObj.getBean().getClass().getSimpleName());
		log.info("PARAMETER		::::::		{}", 		param);
		log.info("ACCESS INFO======================================================END");
		
		//return HandlerInterceptor.super.preHandle(request, response, handler);
		return true;
	}
	
	/**
	 * postHandle: 컨트롤러 특정 메소드 실행 직후(view에 도달 전, view 랜더링 전)
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		log.info("CommonInterceptor======================================================POST");
		
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}
	
	/**
	 * afterCompletion : view 랜더링 후
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
		log.info("CommonInterceptor======================================================AFTER");
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}
}
