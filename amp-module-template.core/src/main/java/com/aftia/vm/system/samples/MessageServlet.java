package com.aftia.vm.system.samples;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;

@Component(property = { "alias=/samples/message", "servlet-name=Message Sample" })
public class MessageServlet extends HttpServlet {

    @Override
    public void doPost(final HttpServletRequest req, final HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getOutputStream().println("Hello World!");
            resp.getOutputStream().flush();
            resp.getOutputStream().close();

        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new ServletException(e.getMessage(), e);
        }
    }
}
