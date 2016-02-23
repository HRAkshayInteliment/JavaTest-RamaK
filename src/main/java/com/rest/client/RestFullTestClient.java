package com.rest.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;


/**
 * 
 * @author rama krishna
 *
 *	This class used to initilize the basic url/authenticaion credentails
 *  having the method to read the input REST request for paragraph reading.
 *  Returns Json  (e.g: {"counts":{"123":"0","Sed":"3","Donec":"4","Augue":"0","Pellentesque":"4","Duis":"8"}}
 */

public class RestFullTestClient {
	
	private static final Logger logger = LoggerFactory.getLogger(RestFullTestClient.class);
	
	private  String uri;
	
	private  String auth;
	
	private  String authValue;
	
	public  String getParagraph() {
		logger.info("Inside getParagraph() method...");
		StringBuilder output = new StringBuilder();
		try {
			HttpClient httpClient = HttpClientBuilder.create().build();
			HttpGet getRequest = new HttpGet(uri);
			
			BasicHeader basicHeader = new BasicHeader(HTTP.CONTENT_TYPE,
	                MediaType.APPLICATION_XML_VALUE);
			getRequest.addHeader(basicHeader);
			getRequest.addHeader(auth, authValue);

			
			HttpResponse response = httpClient.execute(getRequest);

			// Check for HTTP response code: 200 = success
			if (response.getStatusLine().getStatusCode() != 200) {
				logger.error("Failed : HTTP error code : "
						+ response.getStatusLine().getStatusCode());
				throw new RuntimeException("Failed : HTTP error code : "
						+ response.getStatusLine().getStatusCode());
			}
			
			BufferedReader br = new BufferedReader(new InputStreamReader(
					(response.getEntity().getContent())));
			String line;
			
			
			while ((line = br.readLine()) != null) {
				output.append(line);
			}
			logger.debug("Paragraph : ["+output.toString()+"]");
		} catch (ClientProtocolException e) {
			logger.error("Error occured in ClientProtocolException",e);
		} catch (IOException e) {
			logger.error("Error occured in IOException",e);
		}
		return output.toString();
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	public String getAuthValue() {
		return authValue;
	}

	public void setAuthValue(String authValue) {
		this.authValue = authValue;
	}
	
	
}
