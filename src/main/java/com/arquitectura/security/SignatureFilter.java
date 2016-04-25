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
 
public class SignatureFilter implements Filter {
	
	class FilteredRequest extends HttpServletRequestWrapper {
 
		private String signature;
		private String _body;

		
    	/* These are the characters allowed by the Javascript validation */
    	public FilteredRequest(ServletRequest request) throws IOException {
    		super((HttpServletRequest)request);
            _body = "";
            try (BufferedReader bufferedReader = request.getReader())
            {
                String line;
                while ((line = bufferedReader.readLine()) != null)
                    _body += line;
                
                String trama = _body.substring(2, _body.indexOf("signature") - 1);
                signature = _body.substring(_body.indexOf("signature") + 13, _body.length() - 2);
                System.out.println(signature + " ---- signature " + trama);
                
                MessageDigest md = MessageDigest.getInstance("SHA-256");
    			md.update(trama.getBytes());

    			byte byteData[] = md.digest();

    			//convert the byte to hex format method 1
    			StringBuffer sb = new StringBuffer();
    			for (int i = 0; i < byteData.length; i++) {
    				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
    			}
    			
    			System.out.println(signature + " --- " + sb.toString());
    			if (signature.equals(sb.toString())) {
    				_body = _body.substring(0, _body.length() - 1);
    				_body += ", \"isValid\":" + "\"true\" }";
    			} else {
    				_body = _body.substring(0, _body.length() - 1);
    				_body += ", \"isValid\":" + "\"false\" }";
    			}
                
                System.out.println(_body + " body");
            } catch (Exception e) {
			}
    	}
 
    	@Override
        public ServletInputStream getInputStream() throws IOException
        {
            final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(_body.getBytes());
            return new ServletInputStream()
            {
                public int read() throws IOException
                {
                    return byteArrayInputStream.read();
                }

				@Override
				public boolean isFinished() {
					// TODO Auto-generated method stub
					return false;
				}

				@Override
				public boolean isReady() {
					// TODO Auto-generated method stub
					return false;
				}

				@Override
				public void setReadListener(ReadListener listener) {
					// TODO Auto-generated method stub
					
				}
            };
        }

        @Override
        public BufferedReader getReader() throws IOException
        {
            return new BufferedReader(new InputStreamReader(this.getInputStream()));
        }
    }
 
	public void init(FilterConfig config) throws ServletException {
	}
 
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws java.io.IOException, ServletException {
		chain.doFilter(new FilteredRequest(request), response);
	}
 
	public void destroy() {
	}
 
}
