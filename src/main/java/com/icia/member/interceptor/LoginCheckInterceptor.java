package com.icia.member.interceptor;

import com.icia.member.common.SessionConst;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

// 인터셉터 클래스는 HandlerInterceptor 를 implements 함.
public class LoginCheckInterceptor implements HandlerInterceptor {

    // preHandle : 사전에 조작하다.
    // 컨트롤러에 가기 전 Interceptor에서 캐치해서 다른 작업을 수행하게 보냄.

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        // HttpServletRequest : 각종 클래스를 요청하게 해주는 메서드. 스프링에서는 굳이 안써도 다 잡아주지만 근본적으로는 이 메서드가 필수이다.
        // 사용자가 요청한 주소
        String requestURI = request.getRequestURI();
        System.out.println("requestURI = " + requestURI);
        // 세션 가져옴
        HttpSession session = request.getSession();
        // 세션에 로그인 정보가 있는지 확인
        if (session.getAttribute(SessionConst.LOGIN_EMAIL)==null) {
            // 미 로그인 상태
            // 로그인을 하지 않은 경우 바로 로그인페이지로 보내고, 로그인을 하면 요청한 화면을 보여줌.
            session.setAttribute("redirectURL", requestURI);
            response.sendRedirect("/member/login");
            return false;
        } else {
            // 로그인 상태
            return true;
        }
    }

}
