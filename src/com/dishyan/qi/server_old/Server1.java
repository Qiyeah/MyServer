package com.dishyan.qi.server_old;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server1 {
	private ServerSocket server ;
	private BufferedReader bufr = null;
	/**
	 * @param args
	 */
	
	public static void main(String[] args) {
		Server1 server = new Server1();
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
			client = server.accept();
			bufr = new BufferedReader(
					new InputStreamReader(client.getInputStream()));
			while(null!=(msg = bufr.readLine())){
				sb.append(msg);
				sb.append("\r\n");
				if(null==msg){
					break;
				}
			}
			//sb.toString().trim();
			System.out.println(sb);
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
