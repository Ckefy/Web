package ru.itmo.wp.servlet;

import ru.itmo.wp.util.ImageUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Base64;
import java.util.Random;

public class CaptchaFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession session = request.getSession();
        if (session.getAttribute("uri") == null) {
            session.setAttribute("uri", request.getRequestURI());
        }
        if (session.getAttribute("CaptchaText") != null && session.getAttribute("CaptchaText").equals(request.getParameter("CaptchaTextUser"))) {
            //if user wrote the right CAPTCHA
            session.setAttribute("CaptchaCheck", "Passed");
        }
        if ("Passed".equals(session.getAttribute("CaptchaCheck"))) {
            //if user passed CAPTCHA
            chain.doFilter(request, response);
        } else {
            //if user didn`t pass CAPTCHA
            if (session.getAttribute("CaptchaCheck") == null || session.getAttribute("CaptchaCheck").equals("Not passed")) {
                session.setAttribute("CaptchaText", Integer.toString(new Random().nextInt(899) + 100));
                response.setContentType("text/html");
                session.setAttribute("CaptchaCheck", "Not passed");
                String imagine = Base64.getEncoder().encodeToString(ImageUtils.toPng(session.getAttribute("CaptchaText").toString()));
                response.getOutputStream().write(makePage(imagine).getBytes());
                response.getOutputStream().flush();
            }
        }
    }

    private String makePage(String imagine) throws IOException {
        String htmlStart = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Captcha</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <main>\n" +
                "        <div class=\"captcha\">\n" +
                "            <div class=\"captchaHeader\">\n" +
                "                <span>Enter the captcha</span>\n" +
                "            </div>\n" +
                "            <img src=\"data:image/png;base64, ";
        String htmlEnd = "\">\n" +
                "           <form name=\"captchaForm\" method=\"get\">" +
                "                <input type=\"text\" name=\"CaptchaTextUser\">\n" +
                "</form>" +
                "        </div>\n" +
                "    </main>\n" +
                "</body>\n" +
                "</html>";
        return htmlStart + imagine + htmlEnd;
    }
}


