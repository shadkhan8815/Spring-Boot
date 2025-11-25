package com.rays.common;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class FrontCtl extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		HttpSession session = request.getSession();
		String path = request.getServletPath();

		if (!path.startsWith("/Auth/")) {

			if (session.getAttribute("user") == null) {

				response.setContentType("application/json");
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

				PrintWriter out = response.getWriter();
				out.print("{\"success\": false, \"error\": \"Session Expired\"}");
				out.close();

				System.out.println("No session → returning false");
				return false;
			}
		}
		return true;
	}

}
