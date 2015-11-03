package com.dishyan.qi.controller;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.sql.Date;

import com.dishyan.qi.util.CloseUtil;

public class Response {
	private BufferedWriter bufw = null;
	// 存储头信息
	private StringBuilder headInfo;
	// 两个常量
	private static final String CRLF = "\r\n";
	private static final String BLANK = " ";
	// 正文长度
	private int len = 0;
	// 正文信息
	private StringBuilder contextInfo;

	public Response() {
		headInfo = new StringBuilder();
		contextInfo = new StringBuilder();
	}

	public Response(Socket client) {
		this();
		try {
			bufw = new BufferedWriter(new OutputStreamWriter(client
					.getOutputStream(), "utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			headInfo = null;
		}
	}

	public Response(OutputStream ots) {
		this();
		try {
			bufw = new BufferedWriter(new OutputStreamWriter(ots, "utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public void createHeadInfo(int code) {
		// 响应协议 响应码 响应信息
		headInfo.append("http/1.0.1").append(BLANK);

		switch (code) {
		case 200:
			headInfo.append(code).append(BLANK).append("OK").append(CRLF);
		case 400:
			headInfo.append(code).append(BLANK).append("CLIENT ERROR").append(
					CRLF);
		case 404:
			headInfo.append(code).append(BLANK).append("NOT FOUND")
					.append(CRLF);
		case 500:
			headInfo.append(code).append(BLANK).append("SEVER ERROR").append(
					CRLF);
		}
		// 响应头
		headInfo.append("server:Dishyan Server/1.0.1 ").append(CRLF);
		headInfo.append("Date:").append(new Date(System.currentTimeMillis()))
				.append(CRLF);
		headInfo.append("Content-type:text/html；charset=utf-8").append(CRLF);
		// :text/plain 文本
		headInfo.append("Context-Lenth:").append(len).append(CRLF);
		headInfo.append(CRLF);

		// responseContext.toString().getBytes().length
	}

	public Response print(String info) {
		contextInfo.append(info);
		len += info.getBytes().length;
		return this;
	}

	public Response println(String info) {
		contextInfo.append(info).append(CRLF);
		len += (info + CRLF).getBytes().length;
		return this;
	}

	public void pushToClient(int code) {
		try {
			if (null == headInfo) {
				code = 500;
			}
			createHeadInfo(code);
			//头信息
			bufw.append(headInfo.toString());
			//正文
			bufw.append(contextInfo.toString());
			bufw.flush();
            close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void close() {
		CloseUtil.closeIO(bufw);
	}
}
