package com.dishyan.qi.server_old;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.dishyan.qi.controller.Request;
import com.dishyan.qi.controller.Response;

public class Server6 {
	private ServerSocket server ;
	/**
	 * @param args
	 */
	
	public static void main(String[] args) {
		Server6 server = new Server6();
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
			
			Request request = new Request(client.getInputStream());
			
			
			Response response = new Response(client.getOutputStream());
			response.println("<html><head><title>Dishyan server</title>");
			response.println("").println(request.getParameter("name")).println("");
			response.println("</head><body>hello Dishyan server!</body></html>");
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
