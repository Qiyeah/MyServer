package com.dishyan.qi.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class Request {
	// 请求方式
	private String method;
	// 请求资源
	private String url;
	// 请求参数
	private Map<String, List<String>> parameterMapValue;
	// 内部参数
	private static final String CRLF = "\r\n";
	private static final String BLANK = " ";
	private InputStream is;
	private String requestInfo;
	private int len;

	public Request() {
		method = "";
		url = "";
		parameterMapValue = new HashMap<String, List<String>>();

	}

	public Request(InputStream is) {
		this();
		this.is = is;
		byte[] data = new byte[1024];
		try {
			len = is.read(data);
			requestInfo = new String(data, 0, len);
		} catch (IOException e) {
			return;
		}
		parseRequestInfo();
		
	}

	private void parseRequestInfo() {
		if (null == requestInfo || (requestInfo.trim()).equals("")) {
			return;
		}
		String paramString = "";// 专门接收请求参数

		// 获取请求方式
		String firstLine = requestInfo.substring(0, requestInfo.indexOf(CRLF));
		int index = requestInfo.indexOf("/");
		this.method = firstLine.substring(0, index).trim();
		String urlStr = firstLine.substring(index, firstLine.indexOf("HTTP/"))
				.trim();
		if (this.method.equalsIgnoreCase("post")) {
			this.url = urlStr;
			paramString = requestInfo.substring(requestInfo.lastIndexOf(CRLF))
					.trim();
		} else if (this.method.equalsIgnoreCase("get")) {
			if (urlStr.contains("?")) {// 是否存在参数
				String[] urlArray = urlStr.split("\\?");
				this.url = urlArray[0];
				paramString = urlArray[1];
			} else {
				this.url = urlStr;
			}
		}
		// 封装请求参数
		if (paramString.equals("")) {
			return;
		} else {
			parseParaString(paramString);
		}
	}

	private void parseParaString(String para) {
		// 分割
		StringTokenizer token = new StringTokenizer(para, "&");
		while (token.hasMoreTokens()) {
			String keyValue = token.nextToken();
			String[] keyValues = keyValue.split("=");
			if (keyValues.length == 1) {
				keyValues = Arrays.copyOf(keyValues, 2);
				keyValues = null;
			}
			String key = keyValues[0];
			String value = null == keyValues[1] ? null :decode(keyValues[1].trim(),"utf-8");

			if (!parameterMapValue.containsKey(key)) {
				parameterMapValue.put(key, new ArrayList<String>());
			}
			List<String> values = parameterMapValue.get(key);
			values.add(value);
		}
	}

	/**
	 * 根据页面的name 获取对应的value
	 * 
	 * @param args
	 */
	public String[] getParameterValues(String name) {
		List<String> values = null;
		if(null==(values=parameterMapValue.get(name))){
			return null;
		}else{
			return values.toArray(new String[0]);
		}
	}

	public String getParameter(String name) {
		String[] values = getParameterValues(name);
		if(null==values){
			return null;
		}else{
			return values[0];
		}
	}
	/*
	 * 解决中文乱码
	 */
	private String decode(String value,String code){
		try {
			return java.net.URLDecoder.decode(value, code);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static void main(String[] args) {
		new Request().parseParaString("");
	}
}
