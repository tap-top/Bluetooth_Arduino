package com.boe.tww.bluetooth;

import android.app.Application;
import android.bluetooth.BluetoothSocket;

/**
 * Created by 10064275 on 2018/3/27.
 */

public class application extends Application {
    // 获取到选中设备的客户端串口，全局变量，否则连接在方法执行完就结束了
    private BluetoothSocket clientSocket;

    public BluetoothSocket getClientSocket() {
        return clientSocket;
    }

    public void setClientSocket(BluetoothSocket clientSocket) {
        this.clientSocket = clientSocket;
    }
}
