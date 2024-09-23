package de.mayer.penandpaperdmhelper.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Cors implements Filter {


    private static final Logger log = LogManager.getLogger(Cors.class);

    public void init(FilterConfig config) throws ServletException {
        log.trace("Cors init");
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        ((HttpServletResponse)response).setHeader("Access-Control-Allow-Origin", "*");
        ((HttpServletResponse)response).setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        chain.doFilter(request, response);
    }
}
