package com.springboot.sample.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.springboot.sample.entity.Result;
import com.springboot.sample.entity.User;
import com.springboot.sample.service.IUserService;
import com.springboot.sample.utils.JwtUtil;
import com.springboot.sample.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 引入com.auth0.java-jwt来实现JWT
 */
@RestController
@RequestMapping("/")
public class LoginController {

	@Autowired
	private IUserService userService;
	
	 /**
     * 登陆接口
     *
     * @return token
     */
    @GetMapping(value = "/login")
    public Result login(String loginName, String password) {
        //身份验证是否成功
        boolean isSuccess = userService.checkUser(loginName, password);
        if (isSuccess) {
            User user = userService.getUserByLoginName(loginName);
            if (user != null) {
                //返回token
                String token = JwtUtil.sign(user.getName(), user.getId());
                if (token != null) {
                    return ResultUtil.success(token);
                }
            }
        }
        
        //返回登陆失败消息
        return ResultUtil.fail("登录失败");
    }
    
    /**
     * token的检验，每次请求都要在http的请求头上带上自定义的token 的key和value值，否则会无法访问到相应的应用
     * @param req
     * @return
     */
    @PostMapping(value = "/getUsername")
    public Result getUsername(HttpServletRequest req) {
        String tokenStr = req.getHeader("accessToken");
        String loginName = getLoginName(tokenStr);
        String userId = getUserId(tokenStr);
        return ResultUtil.success("成功获得用户名:" + loginName + ", 用户id为：" + userId);
    }
    
    /**
     * 获得token中的信息无需secret解密也能获得
     * 
     * @param token
     * @return token中包含的用户名
     */
    private String getLoginName(String token) {
    	try {
    		DecodedJWT jwt = JWT.decode(token);
        	return jwt.getClaim("loginName").asString();
    	} catch(JWTDecodeException e){
    		return null;
    	}
    } 
    
    /**
     * 获得token中的信息无需secret解密也能获得
     * 
     * @param token
     * @return token中包含的用户id
     */
    private String getUserId(String token) {
    	try {
    		DecodedJWT jwt = JWT.decode(token);
        	return jwt.getClaim("userId").asString();
    	} catch(JWTDecodeException e){
    		return null;
    	}
    } 
    
}
