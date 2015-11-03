package com.dishyan.qi.test_UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

public class Client {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			DatagramSocket client = new DatagramSocket(8899);
			String data = "hello sever!";
			byte[] brf = data.getBytes();
			DatagramPacket dp = new DatagramPacket(brf,brf.length,new InetSocketAddress("localhost",8800));
			client.send(dp);
			client.close();
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
