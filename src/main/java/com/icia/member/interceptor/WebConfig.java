package com.icia.member.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// @Configuration : 설정정보를 스프링 실행 시 등록해줌.
// 차후에 확인할 때 불편하다면 아래 @Configuration 을 주석처리하면 된다.ㄴ
//@Configuration
public class WebConfig implements WebMvcConfigurer {

    // 로그인 여부에 따른 접속 가능 페이지 구분
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginCheckInterceptor()) // 만든 LoginCheckInterceptor 클래스 내용을 넘김
                .order(1) // 해당 인터셉터가 적용되는 순서
                .addPathPatterns("/**") // 해당 프로젝트의 모든 주소에 대해 인터셉터를 적용
                .excludePathPatterns("/", "/member/save", "/member/login","/member/logout", "/css/**"); // 그중에 이 주소는 제외
    }

}
