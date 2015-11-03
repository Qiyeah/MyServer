package com.dishyan.qi.server_old;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Date;

public class Server3 {
	private ServerSocket server ;
	private BufferedReader bufr = null;
	private BufferedWriter bufw = null;
	private StringBuilder response = null;
	private StringBuffer responseContext = null;
	private static final String CRLF = "\r\n";
	private static final String BLANK = " ";
	/**
	 * @param args
	 */
	
	public static void main(String[] args) {
		Server3 server = new Server3();
		server.start();
		
	}

	public  void start() {
		try {
			server = new ServerSocket(8899);
			
			this.receive();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	private  void receive(){
		String msg = null;
		StringBuffer sb = new StringBuffer();
		Socket client;
		try {
			//等待客户端连接
			client = server.accept();
			//定义一个接收客户端request信息的容器，此处定义的容器只能一次性接收
			byte[] data = new byte[20480];
			//获取接收到的request的长度
			int len = client.getInputStream().read(data);
			//把request信息拼成字符串
			sb.append(new String(data,0,len));
			//去除空格
			sb.toString().trim();
			//在服务端打印request
			System.out.println(sb);
			responseContext = new StringBuffer();
			//响应：response
			//响应正文
			responseContext.append("<html><head><title>Dishyan server</title></head><body>hello Dishyan server!</body></html>");
			
			
			response = new StringBuilder();
			//响应协议 响应码 响应信息
			response.append("http/1.0.1").append(BLANK).append("200").append(BLANK).append("连接成功").append(CRLF);
			//响应头
			response.append("server:Dishyan Server/1.0.1 ").append(CRLF);
			response.append("Date:").append(new Date(System.currentTimeMillis())).append(CRLF);
			response.append("Content-type:text/html；charset=utf-8").append(CRLF);
										 //:text/plain 文本
			response.append("Context-Lenth:").append(responseContext.toString().getBytes().length).append(CRLF);
			
			response.append(CRLF);
			
			//响应正文
			response.append(responseContext);
			
			//传输
			/*bufw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream(),"utf-8"));
			bufw.write(response.toString().trim());
			bufw.flush();
			bufw.close();
			*/
			OutputStream ots = client.getOutputStream();
			ots.write(response.toString().getBytes());
			ots.flush();
			ots.close();
			
			System.out.println(response);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public  void stop(){
		try {
			server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
