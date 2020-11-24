package com.dreamcool.loveup;

import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.widget.TextView;

public class TiaoZiUtil3 {
    private static TextView textView1;
    private static String context1;
    private static int length1;
    private static long time;
    static int n = 0;
    private Context context;
    private static int nn;


    public TiaoZiUtil3(long time,Context context) {
        this.time = time;//间隔时间
        this.context = context;
    }

    public void start(){
        startTv(n,context);//开启线程
    }

    public  void setTextView1(TextView textView1) {
        this.textView1 = textView1;
    }

    public  void setContext1(String context1) {
        this.context1 = context1;
        this.length1 = context1.length();
    }

    public static void  startTv(final int n, final Context context) {

        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            final String stv = context1.substring(0, n);//截取要填充的字符串
                            textView1.post(new Runnable() {
                                @Override
                                public void run() {
                                    textView1.setText(stv);
                                }
                            });
                            Thread.sleep(time);//休息片刻
                            nn = n + 1;//n+1；多截取一个
                            if (nn <= length1) {//如果还有汉字，那么继续开启线程，相当于递归的感觉
                                startTv(nn,context);
                            }else {
                                if ((textView1.getText() + "").length() == context1.length()) {

                                    Thread.sleep(5200);

                                    Intent intent = new Intent();
                                    intent.setAction("com.dreamcool.loveup.SHOW_DIALOG");
                                    context.sendBroadcast(intent);
                                }
                            }

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }


                    }
                }

        ).start();
    }
}
