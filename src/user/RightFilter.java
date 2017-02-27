package user;

import hibernate.User;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.DBoperate;

public class RightFilter implements Filter {
	private static final String LOGIN_URI = "LOGIN_URI";
	private static final String REG_URI = "REG_URI";

	private String login_page;
	private String reg_page;
	private String grade = "superAdmin"; // 超级管理员的权限字段
	private String superAdmin_page = "/configAdmin.jsp"; // 仅有超级管理员可访问的页面

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		resp.setContentType("text/html;");
		resp.setCharacterEncoding("utf-8");
		HttpSession session = req.getSession();
		PrintWriter out = resp.getWriter();
		// 得到用户请求的URI
		String request_uri = req.getRequestURI();// ="/JSPWork/index.jsp"

		// 得到web应用程序的上下文路径
		String ctxPath = req.getContextPath();// ="/JSPWork"
		// 去除上下文路径，得到剩余部分的路径
		String uri = request_uri.substring(ctxPath.length());// ="index.jsp"
		// 判断用户访问的是否是登录页面
		if (uri.equals(login_page) || uri.equals(reg_page)) {// 注册和登录页面不过滤，如果不这么写，什么页面也进入不了
			chain.doFilter(request, response);
			return;
		} else {
			// 如果访问的不是登录页面，则判断用户是否已经登录
			if (null != session.getAttribute("userId")
					&& "" != session.getAttribute("userId")) {
				if (uri.equals(superAdmin_page)) {
					// Integer userId = (Integer)session.getAttribute("userId");
					String userId = (String) session.getAttribute("userId");
					// DBoperate query=new DBoperate();
					// query.initialize();
					User user = (User) DBoperate.queryUser(userId);
					// query.endClose();
					// System.out.println(user.getGrade());
					if (null != user && grade.equals(user.getGrade())) {
						chain.doFilter(request, response);
						return;
					} else {
						resp.sendRedirect(req.getContextPath() + "/main.jsp");
						return;
					}
				} else {
					chain.doFilter(request, response);
					return;
				}

			} else {
				resp.sendRedirect(req.getContextPath() + "/login.jsp");
				return;
			}
		}
	}

	public void init(FilterConfig config) throws ServletException {
		// TODO Auto-generated method stub
		// 从部署描述符中获取登录页面和首页的URI
		login_page = config.getInitParameter(LOGIN_URI);
		reg_page = config.getInitParameter(REG_URI);
		// System.out.println(logon_page);
		if (null == login_page || null == reg_page) {
			throw new ServletException("没有找到登录页面或主页");
		}
	}

}
