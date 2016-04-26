/**
 * 
 */
package com.arquitectura.security;
 
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ReadListener;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SignatureFilter implements Filter {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FilteredRequest.class);
	
	class FilteredRequest extends HttpServletRequestWrapper {
 
		
		private String signature;
		private String body;

		
    	/* These are the characters allowed by the Javascript validation */
    	public FilteredRequest(ServletRequest request) throws IOException {
    		super((HttpServletRequest)request);
    		body = "";
            try (BufferedReader bufferedReader = request.getReader())
            {
                String line;
                while ((line = bufferedReader.readLine()) != null)
                	body += line;
                
                String trama = body.substring(2, body.indexOf("signature") - 1);
                signature = body.substring(body.indexOf("signature") + 13, body.length() - 2);                
                MessageDigest md = MessageDigest.getInstance("SHA-256");
    			md.update(trama.getBytes());

    			byte[] byteData = md.digest();

    			//convert the byte to hex format method 1
    			StringBuilder sb = new StringBuilder();
    			for (int i = 0; i < byteData.length; i++) {
    				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
    			}
    			
    			System.out.println(signature + " --- " + sb.toString());
    			if (signature.equals(sb.toString())) {
    				body = body.substring(0, body.length() - 1);
    				body += ", \"isValid\":" + "\"true\" }";
    			} else {
    				body = body.substring(0, body.length() - 1);
    				body += ", \"isValid\":" + "\"false\" }";
    			}
            } catch (Exception e) {
            	 LOGGER.error("Error filter [{}]", e);
			}
    	}
 
    	@Override
        public ServletInputStream getInputStream() throws IOException
        {
            final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body.getBytes());
            return new ServletInputStream()
            {   
            	@Override
                public int read() throws IOException{
                    return byteArrayInputStream.read();
                }
				@Override
				public boolean isFinished() {
					return false;
				}
				@Override
				public boolean isReady() {
					return false;
				}
				@Override
				public void setReadListener(ReadListener listener) {
					// Do nothing because
				}
            };
        }

        @Override
        public BufferedReader getReader() throws IOException
        {
            return new BufferedReader(new InputStreamReader(this.getInputStream()));
        }
    }
    
	@Override
	public void init(FilterConfig config) throws ServletException {
		// Do nothing because
	}
    
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws java.io.IOException, ServletException {
		              chain.doFilter(new FilteredRequest(request), response);
	}
    
	@Override
	public void destroy() {
		// Do nothing because
	}
 
}
