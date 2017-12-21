package com.lenevo.pq.client.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;


import com.lenevo.pq.R;
import com.lenevo.pq.client.model.YQClient;
import com.lenevo.pq.common.MyTime;
import com.lenevo.pq.common.User;

public class RegisterActivity extends Activity {
	String sex="��";
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.activity_register);
		
		findViewById(R.id.rigister_btn_register).setOnClickListener(new OnClickListener(){
			public void onClick(View arg0) {
				//��д���£�
				EditText accountEt=(EditText) findViewById(R.id.register_account);
				EditText passwordEt=(EditText) findViewById(R.id.register_password);
				EditText nickEt=(EditText) findViewById(R.id.register_nick);
				RadioGroup group = (RadioGroup)findViewById(R.id.register_radiogroup);
				group.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					public void onCheckedChanged(RadioGroup arg0,int id) {
						if(id==R.id.register_radio_nv){
							sex="Ů";
						}
					}
				});
				if(accountEt.getText().equals("") || passwordEt.getText().equals("")){
					Toast.makeText(RegisterActivity.this, "�˺Ż����벻��Ϊ�գ�", Toast.LENGTH_SHORT).show();
				}else {
					User user=new User();
					user.setAccount(Integer.parseInt(accountEt.getText().toString()));
					user.setPassword(passwordEt.getText().toString());
					user.setNick(nickEt.getText().toString());
					user.setTrends("���û���ʱû�ж�̬");
					user.setSex(sex);
					user.setAvatar(4);
					user.setLev(0);
					user.setAge(0);
					user.setTime(MyTime.geTimeNoS());
					user.setOperation("register");
					boolean b=new YQClient(RegisterActivity.this).sendRegisterInfo(user);
					if(b){
						//ע��ɹ���ת����½
						Toast.makeText(RegisterActivity.this, "��ϲ�㣬ע��ɹ� ��", Toast.LENGTH_SHORT).show();
						startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
					}
				}
			}
		});
	}
}
