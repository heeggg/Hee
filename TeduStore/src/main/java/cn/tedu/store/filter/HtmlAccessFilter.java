package cn.tedu.store.filter;

import java.io.IOException;
import java.util.ArrayList;
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

public class HtmlAccessFilter implements Filter{
	
	private List<String> accessibleHtml;
	//初始化方法
	public void init(FilterConfig arg0) throws ServletException {
		
		System.out.println("HtmlAccessFilter.init()");
		//穷举允许直接访问的html文件
		accessibleHtml = new ArrayList<String>();
		accessibleHtml.add("register.html");
		accessibleHtml.add("login.html");
		accessibleHtml.add("index.html");
		accessibleHtml.add("goods_details.html");
	}

	//执行过滤的方法
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain chain) throws IOException, ServletException {
		System.out.println("HtmlAccessFilter.doFilter()");
		
		HttpServletRequest request = (HttpServletRequest)arg0;
		String uri = request.getRequestURI();
		String[] paraArray = uri.split("/");
		String file = paraArray[paraArray.length-1];
		System.out.println("file="+file);
		//判断当前页面是否直接放行
		if(accessibleHtml.contains(file)) {
			System.out.println("放行");
			//继续执行过滤器链
			chain.doFilter(arg0, arg1);
			return;
		}
		System.out.println("拦截");
		HttpSession session = request.getSession();
		if(session.getAttribute("uid")!=null) {
			System.out.println("\t已经登录，放行");
			chain.doFilter(arg0, arg1);
			return;
		}
		
		HttpServletResponse response = (HttpServletResponse)arg1;
		String loginURI = request.getContextPath()+"/web/login.html";
		System.out.println("重定向到："+loginURI);
		response.sendRedirect(loginURI);
		
	}

	public void destroy() {
		//销毁方法
	}

}
