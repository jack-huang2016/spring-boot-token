package com.springboot.sample.interceptor;

import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import com.springboot.sample.entity.Result;
import com.springboot.sample.utils.JwtUtil;
import com.springboot.sample.utils.ResultUtil;

/**
 * 自定义token拦截器
 *
 * @author huang.yj
 * @date 2019/06/03
 */
public class TokenInterceptor implements HandlerInterceptor {
	 @Override
	    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
	        response.setCharacterEncoding("utf-8");
	        String token = request.getHeader("access_token");
	        
	        if (null != token) {
	            //验证token是否正确
	            boolean result = JwtUtil.verify(token);
	            if (result) {
	                return true;
	            }
	        }
	        
	        //token不存在
	        Result result = ResultUtil.fail("请求失败!");
	        responseMessage(response, response.getWriter(), result);
	        return false;
	    }

	    @Override
	    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

	    }

	    @Override
	    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

	    }

	    /**
	     * 返回信息给客户端
	     *
	     * @param response
	     * @param out
	     * @param apiResponse
	     * @throws JSONException 
	     */
	    private void responseMessage(HttpServletResponse response, PrintWriter out, Result result) throws JSONException {
	        response.setContentType("application/json; charset=utf-8");
	        JSONObject jObj = new JSONObject();
	        jObj.put("result", result);
	        out.print(jObj.toString());
	        out.flush();
	        out.close();
	    }
}
