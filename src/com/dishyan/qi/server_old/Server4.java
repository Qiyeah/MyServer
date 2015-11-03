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
