package com.daehan.board.security;

import java.io.IOException;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private String errorPage;

    @Override
    public void handle(HttpServletRequest httpServletRequest,
                       HttpServletResponse httpServletResponse,
                       AccessDeniedException e) throws IOException, ServletException {

        String deniedUrl = errorPage + "?exception=" + e.getMessage();
        httpServletResponse.sendRedirect(deniedUrl);
    }

    public void setErrorPage(String errorPage){
        this.errorPage = errorPage;
    }
}