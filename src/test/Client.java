//package test;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.net.Socket;
//import java.net.UnknownHostException;
//
//public class Client {
//
//	public Socket client;
//	public PrintWriter out;
//	public String IP;
//	public int port;
//	public volatile boolean stop;
//	
//	public Client(String ip, int port) {
//	this.IP= ip;
//	this.port=port;
//	stop=false;
//	}
//	
//	public void stop() {
//		out.println("bye");
//		out.flush();
//		stop = true;
//		
//	}
//	
//
//	public void start() {
//		new Thread (()-> {
//			try {
//				runClient();
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}).start();	
//	}
//	
////	private void runClient(){
////		while(!stop){
////			try {
////				this.client = new Socket(IP,this.port);
////				this.out = new PrintWriter(this.client.getOutputStream());
////				while(!stop){
////					out.println(simX+","+simY+","+simZ);
////					out.flush();
////					try {Thread.sleep(100);} catch (InterruptedException e1) {}
////				}
////				out.close();
////				interpreter.close();
////			} catch (IOException e) {
////				try {Thread.sleep(1000);} catch (InterruptedException e1) {}
////			}
////		}
////	}
//	
//	public void runClient() {
//
//			try {
//				this.client = new Socket(IP,this.port);
//				this.out = new PrintWriter(this.client.getOutputStream());
//
//			} catch (UnknownHostException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			while (!stop) {
//				
//			}
//			this.out.close();
//
//			try {
//				this.client.close();
//				System.out.println("****************");
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//	}
//			
//	}


package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

	public Socket client;
	public PrintWriter out;
	public volatile boolean stop;
	public BufferedReader br;
	
	public Client(String ip, int port) {
	stop=false;
	try {
		this.client = new Socket(ip,port);
		this.out = new PrintWriter(this.client.getOutputStream());
		this.br = new BufferedReader( new InputStreamReader(client.getInputStream()));

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void stop() {
		stop = true;
	}
	
	
	public void Send(String s) {
		this.out.println(s);
		//System.out.println(s);  /// 
		this.out.flush();
	}
	
}

