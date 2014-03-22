package co.id.keda87.webcache.filter;

import java.io.IOException;
import java.util.Calendar;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * @author  : Adiyat Mubarak
 * @email   : adiyatmubarak@gmail.com
 * @blog    : www.keda87.wordpress.com
 * @github  : www.github.com/Keda87
 */
public class MyCacheControlFilter implements Filter {

    private int cacheMaxAgeInSecond = 0;
    
    public void init(FilterConfig filterConfig) throws ServletException {
        String cacheMaxAge = filterConfig.getInitParameter("cacheMaxAgeInSecond");
        if (cacheMaxAge != null) {
            cacheMaxAgeInSecond = Integer.parseInt(cacheMaxAge);
        }
    }

    public void doFilter(ServletRequest request, ServletResponse response
            , FilterChain chain) throws IOException, ServletException {
        if(response instanceof HttpServletResponse) {
            HttpServletResponse resp = (HttpServletResponse) response;
            Calendar calendar = Calendar.getInstance();
            resp.setDateHeader("Last-Modified", calendar.getTimeInMillis());
            if(cacheMaxAgeInSecond > 0) {
                calendar.add(Calendar.SECOND, cacheMaxAgeInSecond);
                resp.setDateHeader("Expires", calendar.getTimeInMillis());
                resp.setHeader("Cache-Control", "max-age-" + cacheMaxAgeInSecond);
            }
            chain.doFilter(request, response);
        }
    }

    public void destroy() {
    }

}
