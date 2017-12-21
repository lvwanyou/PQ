
package com.lenevo.pq.client.model;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

//import com.lenevo.pq.client.view.BuddyActivity;
//import com.lenevo.pq.client.view.GroupActivity;
import com.lenevo.pq.common.YQMessage;
import com.lenevo.pq.common.YQMessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientConServerThread extends Thread {
	private Context context;
	private  Socket s;
	public Socket getS() {return s;}
	public ClientConServerThread(Context context, Socket s){
		this.context=context;
		this.s=s;
	}
	
	@Override
	public void run() {
		while(true){
			ObjectInputStream ois = null;
			YQMessage m;
			try {
				ois = new ObjectInputStream(s.getInputStream());
				m=(YQMessage) ois.readObject();
				if(m.getType().equals(YQMessageType.COM_MES)|| m.getType().equals(YQMessageType.GROUP_MES)){//???????
                    //????????????????
					Intent intent = new Intent("org.yhn.yq.mes");//???
					String[] message=new String[]{
						m.getSender()+"",
						m.getSenderNick(),
						m.getSenderAvatar()+"",
						m.getContent(),
						m.getSendTime(),
						m.getType()};
					Log.i("--", message.toString());
					intent.putExtra("message", message);
					context.sendBroadcast(intent);
				}else if(m.getType().equals(YQMessageType.RET_ONLINE_FRIENDS)){
					String s[] = m.getContent().split(",");
					//Log.i("", "--"+s[0]);
					//Log.i("", "--"+s[1]);
//					BuddyActivity.buddyStr=s[0];
//					GroupActivity.groupStr=s[1];
				}
				if(m.getType().equals(YQMessageType.SUCCESS)){
					Toast.makeText(context, "????!", Toast.LENGTH_SHORT);
				}
			} catch (Exception e) {
				//e.printStackTrace();
				try {
					if(s!=null){
						s.close();
					}
				} catch (IOException e1) {
					//e1.printStackTrace();
				}
			}
		}
	}
	
}
