package com.dishyan.qi.server_old;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.dishyan.qi.controller.Response;

public class Server4 {
	private ServerSocket server ;
	/**
	 * @param args
	 */
	
	public static void main(String[] args) {
		Server4 server = new Server4();
		server.start();
		server.stop();
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
			Response response = new Response(client.getOutputStream());
			response.println("<html><head><title>Dishyan server</title>" +
					"</head><body>hello Dishyan server!</body></html>");
			response.pushToClient(200);
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
