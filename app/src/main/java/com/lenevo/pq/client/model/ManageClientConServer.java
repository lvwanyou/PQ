package com.lenevo.pq.client.model;
import java.util.HashMap;

public class ManageClientConServer {
	private static HashMap hm=new HashMap<Integer,ClientConServerThread>();

	public static void addClientConServerThread(int account,ClientConServerThread ccst){
		hm.put(account, ccst);
	}
	

	public static ClientConServerThread getClientConServerThread(int i){
		return (ClientConServerThread)hm.get(i);
	}
}
