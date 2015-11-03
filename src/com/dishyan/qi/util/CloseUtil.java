package com.dishyan.qi.util;

import java.io.Closeable;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;

public class CloseUtil {
	public static <T extends Closeable> void closeIO(T... io){
		try {
			for(Closeable temp:io){
				if(null!=temp){
					temp.close();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void closeSocket(Socket socket){
		try {
			if(null != socket){
				socket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void closeSocket(DatagramSocket socket){
		if(null != socket){
			socket.close();
		}
	}
}
