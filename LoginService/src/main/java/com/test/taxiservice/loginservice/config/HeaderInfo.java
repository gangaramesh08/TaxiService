/*
 * © 2021 SAP SE or an SAP affiliate company. All rights reserved.
 */
package com.test.taxiservice.loginservice.config;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class HeaderInfo {

	/* Keep the list of headers */
	private final Map<String, String> responseHeaders = new HashMap<>();


	public HttpHeaders getResponseHeaders() {

		HttpHeaders headers = new HttpHeaders();
		responseHeaders.entrySet().stream().forEach(e -> headers.set(e.getKey(), e.getValue()));

		return headers;
	}

}
