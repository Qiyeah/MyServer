package com.dishyan.qi.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.dishyan.qi.controller.Request;
import com.dishyan.qi.controller.Response;
import com.dishyan.qi.servlet.Servlet;

public class Server {
	private ServerSocket server ;
	/**
	 * @param args
	 */
	
	public static void main(String[] args) {
		Server server = new Server();
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
			//�ȴ��ͻ�������
			client = server.accept();
			//����һ�����տͻ���request��Ϣ���������˴����������ֻ��һ���Խ���
			byte[] data = new byte[20480];
			//��ȡ���յ���request�ĳ���
			int len = client.getInputStream().read(data);
			//��request��Ϣƴ���ַ���
			sb.append(new String(data,0,len));
			//ȥ���ո�
			sb.toString().trim();
			//�ڷ���˴�ӡrequest
			System.out.println(sb);
			Request request = new Request(client.getInputStream());
			
			
			Response response = new Response(client.getOutputStream());
			
			new Servlet().service(request, response);
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
