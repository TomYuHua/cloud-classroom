package cloud.classroom.app.ui.Interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cloud.classroom.app.ui.annotation.Permission;
import cloud.classroom.app.ui.service.UserService;
import cloud.entity.classroom.every.User;

/**
 * @author Administrator
 *
 *         拦截器实现了对每一个请求处理之前和之后进行相关的处理,类似于Servlet的filter;
 *         可以实现HandlerInterceptor接口或者继承HandlerInterceptorAdapter类;
 *         继承HandlerInterceptorAdapter类,因为使用接口要实现接口的所有方法;
 *
 *         通常来说，用户通过浏览器发起Request进入服务器后侧处理顺序如下： 引用 Client -> Listener
 *         ->ServletContainer -> Filter -> Servlet -> Interceptor
 * 
 *
 */
public class AppInterceptor implements HandlerInterceptor
{

	private static Logger log = LoggerFactory.getLogger(AppInterceptor.class);

	private static final String userInfo = "userInfoId";

	@Autowired
	UserService userService;

	private boolean isLoginRequired(Method method)
	{
		// if (!env.equals("prod"))
		// { // 只有生产环境才需要登录
		// return false;
		// }

		boolean result = true;
		if (method.isAnnotationPresent(Permission.class))
		{
			result = method.getAnnotation(Permission.class).loginReqired();
		}

		return result;
	}

	/*
	 * （非 Javadoc） 在请求处理之前进行调用（Controller方法调用之前)
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
	{

		System.out.println("request开始前");
		long startTime = System.currentTimeMillis();
		request.setAttribute("startTime", startTime);

		if (!handler.getClass().isAssignableFrom(HandlerMethod.class))
		{
			return true;
		}

		final HandlerMethod handlerMethod = (HandlerMethod) handler;
		final Method method = handlerMethod.getMethod();
		final Class<?> clazz = method.getDeclaringClass();
		if (clazz.isAnnotationPresent(Permission.class) || method.isAnnotationPresent(Permission.class))
		{
			// if (request.getAttribute(USER_CODE_SESSION_KEY) == null)
			
			HttpSession session = request.getSession();
			 User user = (User) session.getAttribute(userInfo);
			 
			if (user == null)
			{
				response.sendRedirect("/userui/loginPage");
				return false;
				// throw new Exception();

			} else
			{
				return true;
			}
		}

		//
		// // preHandle方法需要返回值，如果是false
		// // 则不往下执行。因为拦截器是SpringMvc的，必须DispatcherServlet请求的才能拦截到。
		// // TODO 自动生成的方法存根
		// String requestUrl = request.getRequestURL().toString();
		// String contextPath = request.getContextPath();
		// String url = requestUrl.substring(contextPath.length());
		//
		// String method = request.getMethod();
		// String uri = request.getRequestURI();
		// String queryString = request.getQueryString();
		// System.out.println(request.getParameterMap());
		// log.info(String.format("请求参数, url: %s, method: %s, uri: %s, params:
		// %s", url, method, uri, queryString));
		// HttpSession session = request.getSession();
		// User user = (User) session.getAttribute(userInfo);
		// if (user != null)
		// {
		// Cookie[] cookies = request.getCookies();
		// if (cookies != null && cookies.length > 0)
		// {
		// for (Cookie cookie : cookies)
		// {
		// if (userInfo.equals(cookie.getName()))
		// {
		// // String name = cookie.getValue();
		// // String[] ss = name.split(",");
		// // if (userService.exsit("name", ss[0].trim(), "pwd",
		// // ss[1].trim()))
		// // {
		// // user = userService.findEntity("name", ss[0].trim(),
		// // "pwd", ss[1].trim());
		// // session.setAttribute(userInfo, user);
		// // break;
		// // }
		// }
		// }
		// }
		// } else
		// {
		// response.sendRedirect("/userui/loginPage");
		// //return false;
		// }

		// HandlerMethod method = (HandlerMethod) handler;
		// Perm perm = method.getMethodAnnotation(Perm.class);
		// if (perm == null)
		// {
		// return true;
		// }
		// List ur = userRoleRelService.findByProperty("id.userId", u.getId());
		// for (UserRoleRel userRoleRel : ur)
		// {
		// List rp = rolesPermissionRelService.findByProperty("id.roleId",
		// userRoleRel.getId().getRoleId());
		// for (RolesPermissionRel rolesPermissionRel : rp)
		// {
		// Permission permission =
		// permissionService.find(rolesPermissionRel.getId().getPermissionId());
		// if (perm.privilegeValue().equals(permission.getPermissionCode()))
		// {
		// return true;
		// }
		// }
		// }
		// request.getRequestDispatcher("/error/noSecurity.jsp").forward(request,
		// response);
		//
		// return false;
		// response.sendRedirect("/SpringBootWebDemo/web/static/forward");
		return true;
	}

	/*
	 * （非 Javadoc） 请求处理之后进行调用，但是在视图被渲染之前(Controller方法调用之后)
	 * 
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception
	{
		try
		{
			System.out.println("request结束后");
			long startTime = (Long) request.getAttribute("startTime");
			request.removeAttribute("startTime");
			long endTime = System.currentTimeMillis();
			request.setAttribute("handlingTime", endTime - startTime);

		} catch (Exception e)
		{
			// TODO: handle exception
		}

		// TODO 自动生成的方法存根

	}

	/*
	 * （非 Javadoc）
	 * 
	 * 在整个请求结束之后被调用，也就是在DispatcherServlet渲染了对应的视图之后执行 整个请求处理完毕回调方法，即在视图渲染完毕时回调。
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception
	{

		// TODO 自动生成的方法存根

	}

}
