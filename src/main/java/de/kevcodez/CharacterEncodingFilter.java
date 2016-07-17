package de.kevcodez;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

@WebFilter(urlPatterns = { "*" })
public class CharacterEncodingFilter implements javax.servlet.Filter {

  @Override
  public void doFilter(ServletRequest request, ServletResponse response,
    FilterChain filterChain) throws IOException, ServletException {
    response.setCharacterEncoding(StandardCharsets.UTF_8.name());
    filterChain.doFilter(request, response);
  }

  @Override
  public void destroy() {
    // not used
  }

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    // not used

  }
}
