package com.boe.tww.bluetooth;

import android.app.Application;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

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

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            com.scichart.charting.visuals.SciChartSurface.setRuntimeLicenseKey(
                    "<LicenseContract>" +
                            "  <Customer>1451548858@qq.com</Customer>" +
                            "  <OrderId>Trial</OrderId>" +
                            "  <LicenseCount>1</LicenseCount>" +
                            "  <IsTrialLicense>true</IsTrialLicense>" +
                            "  <SupportExpires>04/29/2018 00:00:00</SupportExpires>" +
                            "  <ProductCode>SC-ANDROID-2D-ENTERPRISE-SRC</ProductCode>" +
                            "  <KeyCode>0b412cfc057a4ebfddb5a13edb03980ca92e8dfaee1b5e07923cc6180bef9dd9e4b68a6853af6cbeb93a692c60966bd45415513db4a4acf5a2308a198a9af9263837a02f98ebd5e4212e01cef152887b701b881b17260ae6796f3f727f166ce78b8076f54034c370827f8d0690e2b3861f73e47999459f03dd70e5b286dc0857bd9b1bde6fad427ac2bac928ffe704df8e9e5e4df8a55213142c811db2e09dd65f2848e3f466bd5fbcc46f</KeyCode>" +
                            "</LicenseContract>");
        } catch (Exception e) {
            Log.e("SciChart", "Error when setting the license", e);
        }
    }
}
