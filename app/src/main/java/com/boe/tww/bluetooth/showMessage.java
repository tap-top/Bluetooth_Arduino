package com.boe.tww.bluetooth;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by 10064275 on 2018/3/27.
 */

public class showMessage extends Activity {
    private Button button;
    // 服务端利用线程不断接受客户端信息
    private AcceptThread thread;
    private InputStream is;
    private ArrayAdapter<String> mAdapter;
    // ListView组件
    private ListView lvMessage;

    private Button button_submit=null;
    private TextView textView=null;
    private String content=null;
    private Handler handler=null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        application application = (application)getApplication();
        setContentView(R.layout.show_message);
        //创建属于主线程的handler
        handler=new Handler();
        // 实例接收客户端传过来的数据线程
        thread = new AcceptThread();
        // 线程开始
        thread.start();

        try {
            is  = application.getClientSocket()
                    .getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        button = (Button) findViewById(R.id.returnButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                thread.interrupt();
                Intent intent = new Intent(showMessage.this, MainActivity.class);
                startActivity(intent);
            }
        });

        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1);
        lvMessage = ((ListView) findViewById(R.id.show_message));
        lvMessage.setAdapter(mAdapter);
    }

    // 服务端接收信息线程
    public class AcceptThread extends Thread {
        @Override
        public void run() {
            int ret = 0;
            byte[] rsp = null;
            try {
                while(true){

                    try {
                        rsp = new byte[is.available()];
                        ret = is.read(rsp);
                        System.out.println(ret);
                        BufferedReader bf = new BufferedReader(new InputStreamReader(is));
                        final StringBuffer buffer = new StringBuffer();
                        String line = "";
                        while ((line = bf.readLine()) != null) {
                            buffer.setLength(0); //情况buffer
                            buffer.append(line);
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    if(mAdapter.getCount()>20){
                                        mAdapter.clear();
                                    }
                                    mAdapter.add(buffer.toString());
                                }
                            });
//                            mAdapter.clear();
//                            mAdapter.add("aa"+"\naa");
//                            mAdapter.notifyDataSetChanged();

                            try{
                                Thread.sleep(20);
                            }catch(Exception e){
                                e.printStackTrace();
                            }
                        }
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }

        }
    }

}
