package de.kevcodez.metronom.rest.filter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/**
 * Filter to ensure UTF-8 encoding.
 * 
 * @author Kevin Grüneberg
 *
 */
@WebFilter(urlPatterns = { "*" })
public class CharacterEncodingFilter implements javax.servlet.Filter {

  @Override
  public void doFilter(ServletRequest request, ServletResponse response,
    FilterChain filterChain) throws IOException, ServletException {
    request.setCharacterEncoding(getUtf8Charset());
    response.setCharacterEncoding(getUtf8Charset());
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

  private String getUtf8Charset() {
    return StandardCharsets.UTF_8.name();
  }

}
