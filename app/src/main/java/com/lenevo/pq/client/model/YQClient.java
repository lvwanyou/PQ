package com.lenevo.pq.client.model;

import android.content.Context;

import com.lenevo.pq.common.User;
import com.lenevo.pq.common.YQMessage;
import com.lenevo.pq.common.YQMessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class YQClient {
	private Context context;
	public Socket s;
	public YQClient(Context context){
		this.context=context;
	}
	public boolean sendLoginInfo(Object obj){
		boolean b=false;
		try {
			s=new Socket();
			try{
				//169.254.98.160
				s.connect(new InetSocketAddress("localhost",5469),2000);
			}catch(SocketTimeoutException e){

				return false;
			}
			ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
			oos.writeObject(obj);
			ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
			YQMessage ms=(YQMessage)ois.readObject();
			if(ms.getType().equals(YQMessageType.SUCCESS)){

				com.lenevo.pq.client.view.MainActivity.myInfo=ms.getContent();

				ClientConServerThread ccst=new ClientConServerThread(context,s);

				ccst.start();

				ManageClientConServer.addClientConServerThread(((User)obj).getAccount(), ccst);
				b=true;
			}else if(ms.getType().equals(YQMessageType.FAIL)){
				b=false;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return b;
	}
	
	public boolean sendRegisterInfo(Object obj){
		boolean b=false;
		try {
			s=new Socket();
			try{
				s.connect(new InetSocketAddress("10.0.2.2",5469),2000);
			}catch(SocketTimeoutException e){

				return false;
			}
			ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
			oos.writeObject(obj);
			ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
			YQMessage ms=(YQMessage)ois.readObject();
			if(ms.getType().equals(YQMessageType.SUCCESS)){
				b=true;
			}else if(ms.getType().equals(YQMessageType.FAIL)){
				b=false;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return b;
	}
}
