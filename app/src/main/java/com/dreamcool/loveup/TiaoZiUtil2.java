package com.dreamcool.loveup;

import android.content.Context;
import android.widget.TextView;

public class TiaoZiUtil2 {
    private static TextView textView1;
    private static String context1;
    private static TextView textView2;
    private static String context2;
    private static int length1;
    private static long time;
    private Context context;
    static int n = 0;
    private static int nn;
    TiaoZiUtil3 tiaoZiUtil2;

    public TiaoZiUtil2(long time,Context context) {
        this.time = time;//间隔时间
        this.context = context;
    }

    public void start() {
        startTv(n, context);//开启线程
    }

    public void setTextView1(TextView textView1) {
        this.textView1 = textView1;
    }

    public void setContext1(String context1) {
        this.context1 = context1;
        this.length1 = context1.length();
    }

    public void setTextView2(TextView textView2) {
        this.textView2 = textView2;
    }

    public void setContext2(String context2) {
        this.context2 = context2;
    }

    public static void startTv(final int n, final Context context) {

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
                                startTv(nn, context);
                            } else {
                                if ((textView1.getText() + "").length() == context1.length()) {
                                    TiaoZiUtil3 tiaoZiUtil2 = new TiaoZiUtil3(200,context);
                                    tiaoZiUtil2.setContext1(context2);
                                    tiaoZiUtil2.setTextView1(textView2);
                                    tiaoZiUtil2.start();
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
