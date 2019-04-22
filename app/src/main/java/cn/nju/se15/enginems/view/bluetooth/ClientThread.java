package cn.nju.se15.enginems.view.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

/**
 * @ProjectName: BluetoothPart
 * @Package: com.example.bluetoothpart
 * @ClassName: ClientThread
 * @Description: 客户端线程，发起配对和数据传输的设备为客户端线程
 * @Author: zhuyingshan
 * @CreateDate: 2019/4/13 下午 06:11
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/4/13 下午 06:11
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */

public class ClientThread implements Runnable {

    final String TAG = "ClientThread";

    BluetoothAdapter bluetoothAdapter;
    BluetoothDevice device;

    Handler uiHandler;
    Handler writeHandler;

    BluetoothSocket socket;
    OutputStream out;
    InputStream in;
    BufferedReader reader;


    public ClientThread(BluetoothAdapter bluetoothAdapter, BluetoothDevice device,
                        Handler handler) {
        this.bluetoothAdapter = bluetoothAdapter;
        this.device = device;
        this.uiHandler = handler;
        BluetoothSocket tmp = null;
        try {
            tmp = device.createRfcommSocketToServiceRecord(UUID.fromString(Params.UUID));
        } catch (IOException e) {
            e.printStackTrace();
        }
        socket = tmp;
    }

    @Override
    public void run() {

        Log.e(TAG, "----------------- do client thread run()");
        if (bluetoothAdapter.isDiscovering()) {
            bluetoothAdapter.cancelDiscovery();
        }

        try {
            socket.connect();
            out = socket.getOutputStream();
            in = socket.getInputStream();
            //reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));

            new Thread(new Runnable() {
                @Override
                public void run() {
                    Log.e(TAG, "-----------do client read run()");

                    byte[] buffer = new byte[1024];
                    int len;
                    String content;
                    try {
                        while ((len=in.read(buffer)) != -1) {
                            content=new String(buffer, 0, len);
                            Message message = new Message();
                            message.what = Params.MSG_CLIENT_REV_NEW;
                            message.obj = content;
                            uiHandler.sendMessage(message);
                            Log.e(TAG, "------------- client read data in while ,send msg ui" + content);
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

//            Looper.prepare();
//            writeHandler = new Handler() {
//                @Override
//                public void handleMessage(Message msg) {
//                    switch (msg.what) {
//                        case Params.MSG_CLIENT_WRITE_NEW:
//                            String data = msg.obj.toString() + "\n";
//                            try {
//                                out.write(data.getBytes("utf-8"));
//                                Log.e(TAG, "-------------client write data " + data);
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                            break;
//
//                    }
//                }
//            };
//            Looper.loop();

        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "-------------- exception");
        }
    }


    public void write(String data){
//        data = data+"\r\n";
        try {
            Log.d("zhuyingshan",data);
            out.write(data.getBytes("utf-8"));
            Log.e(TAG, "---------- write data ok "+data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
