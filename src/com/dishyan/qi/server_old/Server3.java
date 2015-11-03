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
			responseContext = new StringBuffer();
			//��Ӧ��response
			//��Ӧ����
			responseContext.append("<html><head><title>Dishyan server</title></head><body>hello Dishyan server!</body></html>");
			
			
			response = new StringBuilder();
			//��ӦЭ�� ��Ӧ�� ��Ӧ��Ϣ
			response.append("http/1.0.1").append(BLANK).append("200").append(BLANK).append("���ӳɹ�").append(CRLF);
			//��Ӧͷ
			response.append("server:Dishyan Server/1.0.1 ").append(CRLF);
			response.append("Date:").append(new Date(System.currentTimeMillis())).append(CRLF);
			response.append("Content-type:text/html��charset=utf-8").append(CRLF);
										 //:text/plain �ı�
			response.append("Context-Lenth:").append(responseContext.toString().getBytes().length).append(CRLF);
			
			response.append(CRLF);
			
			//��Ӧ����
			response.append(responseContext);
			
			//����
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
