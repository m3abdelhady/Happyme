package com.happy.me.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class CustomRequestHandler extends HandlerInterceptorAdapter {

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
//		long startTime = System.currentTimeMillis();
//		request.setAttribute("startTime", startTime);
		System.out.println("CustomRequestHandler.preHandle()");
		return true;
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		System.out.println("CustomRequestHandler.postHandle()");
//		long startTime = (Long) request.getAttribute("startTime");
//		request.removeAttribute("startTime");
//
//		long endTime = System.currentTimeMillis();
//		modelAndView.addObject("totalTime", endTime - startTime);
//
//		System.out.println("Request Prcessing Time :: " + (endTime - startTime));
	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception exceptionIfAny) throws Exception {
		System.out.println("View Rendered !!");
	}
}
