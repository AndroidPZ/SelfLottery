package zhcw.lottery.znzd.com.selflottery.util;

import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import zhcw.lottery.znzd.com.selflottery.ready_entity.bean_info.UserInfo;

/**
 * 作者：XPZ on 2018/8/20 16:33.
 */
public class SocketUtil {


    public static BufferedReader bfReader;
    public static BufferedWriter bfWriter;
    public static Socket clientSocket;

    public static void SendToken(final Context context) {
        if (SocketUtil.bfWriter != null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        try {
                            Thread.sleep(350);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        SocketUtil.bfWriter.write(String.format("token=%s\n", UserInfo.getToken()));
                        SocketUtil.bfWriter.write("CLOSE\n");
                        SocketUtil.bfWriter.flush();
//                        SocketUtil.bfWriter.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    public static class ExampleAsyncTask extends AsyncTask<Void, String, Integer> {
        private ExampleAsyncTask.ExampleAsyncTaskListener listener;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //设置代理
//            System.getProperties().setProperty("http.proxyHost", "120.92.34.155");
//            System.getProperties().setProperty("http.proxyPort", "8848");
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            if (isCancelled()) {
                return null;
            }
            try {//"socket_ip":"120.92.34.155","socket_port":8848
                clientSocket = new Socket(UserInfo.getSocket_ip(), UserInfo.getSocket_port());
                Logger.i("clientSocket: IP =" + UserInfo.getSocket_ip() + "  port =" + UserInfo.getSocket_port());
                //客户端建立与服务端socket的连接,"10.62.37.152"为我的局域网ip地址,读者按照自己的ip地址进行相应修改
                bfWriter = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                bfReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

//                clientSocket.setKeepAlive(true);
//                clientSocket.setSoTimeout(1000);
//                clientSocket.sendUrgentData(0xFF);
                String line = "请扫描取票";
                while ((line = bfReader.readLine()) != null) {
                    Logger.i("循环");
                    if ("CLOSE".equals(line)) {
                        publishProgress(line);
                        break;
                    }
                    publishProgress(line);//接收从服务端转发来的消息
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Integer aVoid) {
            System.out.println(aVoid);
            super.onPostExecute(aVoid);
        }

        @Override
        protected void onProgressUpdate(String... values) {
            Logger.i("后台发送 " + values[0]);
            if (isCancelled()) {
                return;
            }
            if (listener != null) {
                listener.onExampleAsyncTaskFinished(values[0]);
            }
            super.onProgressUpdate(values);
        }

        public void setListener(ExampleAsyncTask.ExampleAsyncTaskListener listener) {
            this.listener = listener;
        }

        public interface ExampleAsyncTaskListener {
            void onExampleAsyncTaskFinished(String value);
        }
    }

}
