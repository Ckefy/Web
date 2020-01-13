package ru.itmo.wp.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

public class StaticServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String uri = request.getRequestURI();
        String[] arrUri = uri.split("[+]");
        response.setContentType(getContentTypeFromName(arrUri[0]));
        for (String curUri : arrUri) {
            /*if (curUri.charAt(0) != '/'){
                curUri = '/' + curUri;
            }*/
            //File file = new File("./src/main/webapp/static" + curUri);
            //File file = new File(new File("./src/main/webapp/static"), curUri);
            File file = new File(new File(getServletContext().getRealPath("."), "../../src/main/webapp/static"), curUri);
            if (!file.isFile()) file = new File(getServletContext().getRealPath("/static" + curUri));
            if (file.isFile()) {
                OutputStream outputStream = response.getOutputStream();
                Files.copy(file.toPath(), outputStream);
                outputStream.flush();
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                break;
            }
        }
    }

    private String getContentTypeFromName(String name) {
        name = name.toLowerCase();

        if (name.endsWith(".png")) {
            return "image/png";
        }

        if (name.endsWith(".jpg")) {
            return "image/jpeg";
        }

        if (name.endsWith(".html")) {
            return "text/html";
        }

        if (name.endsWith(".css")) {
            return "text/css";
        }

        if (name.endsWith(".js")) {
            return "application/javascript";
        }

        throw new IllegalArgumentException("Can't find content type for '" + name + "'.");
    }
}
