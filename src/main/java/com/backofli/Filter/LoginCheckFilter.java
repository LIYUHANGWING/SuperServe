package com.backofli.Filter;

import com.alibaba.fastjson.JSONObject;
import com.backofli.pojo.Result;
import com.backofli.utils.JwtUtils;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.IOException;

@Slf4j
/*@WebFilter(urlPatterns = "/*")*/
public class LoginCheckFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req=(HttpServletRequest) servletRequest;
        HttpServletResponse resp=(HttpServletResponse) servletResponse;
        //获取请求url
        String url=req.getRequestURI().toString();
        log.info("请求的url",url);
        //判断url中是否有login如果是：放行
        if(url.contains("login")){
            log.info("登录操作");
            filterChain.doFilter(servletRequest,servletResponse);
            return;
        }
        //3.获取请求头中的令牌(token)
   //     String jwt=req.getHeader("token");
        //4.判断令牌是否存在
  //     if(!StringUtils.hasLength(jwt)){
  //          log.info("token为空，返回未登录信息");
  //          Result error=Result.error("NOT_LOGIN");
   //         //手动转换成jsom--->阿里json包
   //         String notLogin=JSONObject.toJSONString(error);
   //         resp.getWriter().write(notLogin);
   //         return;
   //     }
        //5.校验jwt
  //    try{
  //        JwtUtils.parseJWT(jwt);
  //    }catch (Exception e){
   //       e.printStackTrace();
   //       log.info("解析失败，返回");
    //      Result error=Result.error("NOT_LOGIN");
    //      String notLogin=JSONObject.toJSONString(error);
   //       resp.getWriter().write(notLogin);
  //    }
      //放行
        log.info("令牌好，放行");
      filterChain.doFilter(servletRequest,servletResponse);


    }
}
