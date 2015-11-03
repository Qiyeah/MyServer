package com.dishyan.qi.test_UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Server {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			DatagramSocket server =new DatagramSocket(8800);
			byte[] data = new byte[1024];
			DatagramPacket dp = new DatagramPacket(data,0,data.length);
			server.receive(dp);
			data = dp.getData();
			int len = dp.getLength();
			System.out.println(new String(data,0,len));
			server.close();
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
