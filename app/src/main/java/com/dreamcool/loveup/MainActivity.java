package com.dreamcool.loveup;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PointF;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sdsmdg.tastytoast.TastyToast;
import com.tencent.bugly.crashreport.CrashReport;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

public class MainActivity extends AppCompatActivity {

    private ImageView ivClickHeart;
    private ImageView ivHeart;
    private RelativeLayout relativeLayout;
    private RelativeLayout rlLefts;
    private View vLeft;
    private View vRight;
    private Integer width;
    private Integer height;
    private MediaPlayer mediaPlayer;
    private AnimationDrawable animationDrawable;
    private ImageView ivTree;
    private RelativeLayout rlTree;
    private TextView tvContext;
    private MyBroadCastReceiver yBroadCastReceiver;
    private boolean isFast = true;
    boolean isOne = true;
    private TextView tvTo;
    private TextView tvBottom;
    private TextView tvTime;
    private TextView tvPoint;
    int count = 0;
    int screenWidth;
    int screenHeight;
    long day = 0;
    long hour = 0;
    long minute = 0;
    long second = 0;
    private View vBackground;
    private boolean mIsDestoryed = false;
    private LoveDialog loveDialog;
    boolean isText = true;

    Handler handlerTime = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 10) {

                ++second;

                if (second == 60) {
                    ++minute;
                    second = 0;
                }

                if (minute == 60) {
                    ++hour;
                    minute = 0;
                }

                if (hour == 24) {
                    day++;
                    hour = 0;
                }


                tvTime.setText(Html.fromHtml(
                        "<font><small><small><small>" + "和你认识第" + "</small></small></small></font> " + day +
                                " <font><small><small><small>" + "天" + "</small></small></small></font> " + hour +
                                " <font><small><small><small>" + "小时" + "</small></small></small></font> " + minute +
                                " <font><small><small><small>" + "分" + "</small></small></small></font> " + second +
                                " <font><small><small><small>" + "秒" + "</small></small></small></font>"
                ));

                Message message = handlerTime.obtainMessage(0);
                handlerTime.sendMessageDelayed(message, 1000);
            } else if (msg.arg1 == 0) {
                timeDifference("2015-09-01 12:10:10");
                tvTime.setText(Html.fromHtml(
                        "<font><small><small>" + "和你认识第" + "</small></small></font> " + day +
                                " <font><small><small>" + "天" + "</small></small></font> " + hour +
                                " <font><small><small>" + "小时" + "</small></small></font> " + minute +
                                " <font><small><small>" + "分" + "</small></small></font> " + second +
                                " <font><small><small>" + "秒" + "</small></small></font>"
                ));

                Message message = handlerTime.obtainMessage(0);
                handlerTime.sendMessageDelayed(message, 1000);
            }

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bmob.initialize(this, "5c4b9d8e3d1fbaf2ca3f6088ab4ac35b");
        setContentView(R.layout.activity_main);

        yBroadCastReceiver = new MyBroadCastReceiver();

        //实例化过滤器并设置要过滤的广播
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.dreamcool.loveup.UP_DIALOG_TIME");
        intentFilter.addAction("com.dreamcool.loveup.SHOW_DIALOG");
        registerReceiver(yBroadCastReceiver, intentFilter);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);   //应用运行时，保持屏幕高亮，不锁屏
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        width = dm.widthPixels;         // 屏幕宽度（像素）
        height = dm.heightPixels;       // 屏幕高度（像素）
        float density = dm.density;         // 屏幕密度（0.75 / 1.0 / 1.5）
        int densityDpi = dm.densityDpi;     // 屏幕密度dpi（120 / 160 / 240）
        // 屏幕宽度算法:屏幕宽度（像素）/屏幕密度
        screenWidth = (int) (width / density);  // 屏幕宽度(dp)
        screenHeight = (int) (height / density);// 屏幕高度(dp)


        ivClickHeart = findViewById(R.id.iv_chick_heart);
        ivHeart = findViewById(R.id.iv_heart);
        relativeLayout = findViewById(R.id.relativeLayout);
        vLeft = findViewById(R.id.v_left);
        vRight = findViewById(R.id.v_right);
        ivTree = findViewById(R.id.iv_tree);
        rlTree = findViewById(R.id.rl_tree);
        tvContext = findViewById(R.id.tv_context);
        tvTo = findViewById(R.id.tv_to);
        tvBottom = findViewById(R.id.tv_bottom);
        tvTime = findViewById(R.id.tv_time);
        rlLefts = findViewById(R.id.rlLefts);
        vBackground = findViewById(R.id.vBackground);
        tvPoint = findViewById(R.id.tvPiontMe);

        ivClickHeart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isFast)
                    return;
                CrashReport.testJavaCrash();
                playAnimation(ivClickHeart);
                playAnimation0(tvPoint);
                isFast = false;
            }
        });

    }

    //计算时间差
    public void timeDifference(String startDate) {
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date startTime = df.parse(startDate);
            Date endTime = df.parse(df.format(new Date()));

            long diff = endTime.getTime() - startTime.getTime();
            day = diff / (1000 * 60 * 60 * 24);
            hour = (diff - day * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
            minute = (diff - day * (1000 * 60 * 60 * 24) - hour * (1000 * 60 * 60)) / (1000 * 60);
            second = (diff - day * (1000 * 60 * 60 * 24) - hour * (1000 * 60 * 60) - minute * (1000 * 60)) / 1000;
        } catch (Exception e) {
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(this, R.raw.music);
            mediaPlayer.setLooping(false);
            mediaPlayer.seekTo(30000);
            mediaPlayer.start();
        } else {
            if (mediaPlayer.getDuration() != mediaPlayer.getCurrentPosition()) {
                mediaPlayer.seekTo(mediaPlayer.getCurrentPosition());
                mediaPlayer.start();
            }
        }

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if (count > 620)
                    finish();
                else {
                    if (!mediaPlayer.isPlaying()) {
                        mediaPlayer.seekTo(30000);
                        mediaPlayer.start();
                    }
                }
            }
        });

    }

    //弹出弹窗
    public void showDialog() {
        loveDialog = new LoveDialog(MainActivity.this);
        loveDialog.setCanceledOnTouchOutside(false);

        loveDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode== KeyEvent.KEYCODE_BACK&&event.getRepeatCount()==0)
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }
        });

        WindowManager.LayoutParams params = loveDialog.getWindow().getAttributes();
        loveDialog.getWindow().setGravity(Gravity.CENTER);
        params.width = (int) (screenWidth * 2);
        params.height = (int) (screenHeight * 2.5);
        loveDialog.getWindow().setAttributes(params);
        loveDialog.show();

    }

    //点击后消失动画
    public void playAnimation(View view) {
        List<Animator> animators = new ArrayList<>();//设置一个装动画的集合
        final ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1f, 0f);
        scaleX.setDuration(2000);//设置持续时间
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1f, 0f);
        scaleY.setDuration(2000);//设置持续时间
        ObjectAnimator alpha = ObjectAnimator.ofFloat(view, "alpha", 1.0f, 0.0f);
        alpha.setDuration(2000);//设置持续时间
        alpha.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //底部列表的

            }
        });
        alpha.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {

                relativeLayout.removeView(ivClickHeart);
                ivHeart.setVisibility(View.VISIBLE);
                playAnimation2(ivHeart);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }

        });
        animators.add(scaleX);
        animators.add(scaleY);
        animators.add(alpha);
        AnimatorSet btnSexAnimatorSet = new AnimatorSet();//动画集
        btnSexAnimatorSet.playTogether(animators);//设置一起播放
        btnSexAnimatorSet.start();//开始播放
    }

    public void playAnimation0(View view) {
        List<Animator> animators = new ArrayList<>();//设置一个装动画的集合
        final ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1f, 0f);
        scaleX.setDuration(500);//设置持续时间
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1f, 0f);
        scaleY.setDuration(500);//设置持续时间
        ObjectAnimator alpha = ObjectAnimator.ofFloat(view, "alpha", 1.0f, 0.0f);
        alpha.setDuration(500);//设置持续时间
        alpha.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //底部列表的

            }
        });
        alpha.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }

        });
        animators.add(scaleX);
        animators.add(scaleY);
        animators.add(alpha);
        AnimatorSet btnSexAnimatorSet = new AnimatorSet();//动画集
        btnSexAnimatorSet.playTogether(animators);//设置一起播放
        btnSexAnimatorSet.start();//开始播放
    }

    //小爱心出现动画
    public void playAnimation2(View view) {
        List<Animator> animators = new ArrayList<>();//设置一个装动画的集合
        final ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 0f, 0.3f);
        scaleX.setDuration(1000);//设置持续时间
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 0f, 0.3f);
        scaleY.setDuration(1000);//设置持续时间
        ObjectAnimator alpha = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f);
        alpha.setDuration(1000);//设置持续时间
        alpha.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //底部列表的translationY

            }
        });
        alpha.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                playAnimation3(ivHeart);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }

        });
        animators.add(scaleX);
        animators.add(scaleY);
        animators.add(alpha);
        AnimatorSet btnSexAnimatorSet = new AnimatorSet();//动画集
        btnSexAnimatorSet.playTogether(animators);//设置一起播放
        btnSexAnimatorSet.start();//开始播放
    }

    //小小爱心下落动画
    public void playAnimation3(View view) {
        List<Animator> animators = new ArrayList<>();//设置一个装动画的集合
        ObjectAnimator translationY = ObjectAnimator.ofFloat(view, "translationY", 0f, screenHeight * 1.7f);
        translationY.setDuration(4000);//设置持续时间

        translationY.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                playAnimation4(vLeft, -screenWidth * 2f);
                playAnimation4(vRight, screenWidth * 2f);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                relativeLayout.removeView(ivHeart);

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }

        });
        animators.add(translationY);
        AnimatorSet btnSexAnimatorSet = new AnimatorSet();//动画集
        btnSexAnimatorSet.playTogether(animators);//设置一起播放
        btnSexAnimatorSet.start();//开始播放
    }

    //线条出现动画
    public void playAnimation4(View view, float value) {
        List<Animator> animators = new ArrayList<>();//设置一个装动画的集合
        ObjectAnimator translationX = ObjectAnimator.ofFloat(view, "translationX", 0f, value);
        translationX.setDuration(4000);//设置持续时间
        translationX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //底部列表的translationY

            }
        });
        translationX.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                playAnimation5(ivTree);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }

        });
        animators.add(translationX);
        AnimatorSet btnSexAnimatorSet = new AnimatorSet();//动画集
        btnSexAnimatorSet.playTogether(animators);//设置一起播放
        btnSexAnimatorSet.start();//开始播放
    }

    //爱心树生长动画
    public void playAnimation5(ImageView view) {
        view.setImageDrawable(getDrawable(R.drawable.frame_anim));
        view.setVisibility(View.VISIBLE);

        //通过设置android:src时，得到AnimationDrawable 用如下方法
        animationDrawable = (AnimationDrawable) view.getDrawable();
        animationDrawable.start();

        int duration = 0;
        for (int i = 0; i < animationDrawable.getNumberOfFrames(); i++) {
            duration += animationDrawable.getDuration(i);

        }

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            public void run() {
                playAnimation6(rlTree);
            }

        }, duration);
    }

    //爱心树移动动画
    public void playAnimation6(View view) {
        List<Animator> animators = new ArrayList<>();//设置一个装动画的集合
        ObjectAnimator translationX = ObjectAnimator.ofFloat(view, "translationX", 0, screenWidth - 100);
        translationX.setDuration(4000);//设置持续时间
        translationX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //底部列表的translationY

            }
        });
        translationX.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {

                String text0 = "立丰国际砥砺奋进代理费覆盖单个电饭锅的覆盖的单个个的吧对吧的个是个是个是 说得 是多少 是大V是v是的v是多少大V说得v是大V说得是 根本 他额的发是v 播放对吧 对吧的吧的";
                String text1 = "To傻猪：";
                String text2 = "        傻猪，生日快乐呀！你别管我怎么知道你生日，你只需要知道我对你的感情就像种子一样在我心中发芽了！如今已经长成了一棵爱的大树！我不知道你有没有感觉到。" +
                        "但是，我对你是真心的。想想看，这么久以来。你是那么关心我。肯听我发牢骚，肯安慰我，要我多爱自己……我现在才发现你是多么好的一个女孩啊！" +
                        "你是那样耐心地听一个喝醉的人说傻话。不行了！我真的好爱，好爱，好爱啊！希望你能让我这个无聊的宅男程序员守候在你身边好吗？让我转正吧，Boss！所以……";
                String text3 = "— —泪&累";
                TiaoZiUtil tiaoZiUtil = new TiaoZiUtil(200, MainActivity.this);
                tiaoZiUtil.setContext1(text1);
                tiaoZiUtil.setTextView1(tvTo);
                tiaoZiUtil.setContext2(text2);
                tiaoZiUtil.setTextView2(tvContext);
                tiaoZiUtil.setContext3(text3);
                tiaoZiUtil.setTextView3(tvBottom);
                tiaoZiUtil.start();

                playAnimation7(tvTime);
                playAnimation8();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }

        });
        animators.add(translationX);
        AnimatorSet btnSexAnimatorSet = new AnimatorSet();//动画集
        btnSexAnimatorSet.playTogether(animators);//设置一起播放
        btnSexAnimatorSet.start();//开始播放
    }

    //时间标语出现动画
    public void playAnimation7(View view) {
        tvTime.setVisibility(View.VISIBLE);

        List<Animator> animators = new ArrayList<>();//设置一个装动画的集合
        ObjectAnimator translationX = ObjectAnimator.ofFloat(view, "translationX", -screenWidth * 2f, 1);
        translationX.setDuration(4000);//设置持续时间
        translationX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //底部列表的translationY

            }
        });
        translationX.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                Message message = new Message();
                message.arg1 = 0;
                handlerTime.sendMessage(message);
            }

            @Override
            public void onAnimationEnd(Animator animation) {


            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }

        });
        animators.add(translationX);
        AnimatorSet btnSexAnimatorSet = new AnimatorSet();//动画集
        btnSexAnimatorSet.playTogether(animators);//设置一起播放
        btnSexAnimatorSet.start();//开始播放
    }

    //树叶飘落动画
    public void playAnimation8() {


        new Thread() {
            @Override
            public void run() {
                if (mIsDestoryed)
                    // 页面销毁直接返回
                    return;
                while (true) {
                    if (mIsDestoryed)
                        // 页面销毁直接返回
                        return;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            addLeft();
                        }
                    });
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

    }

    private void addLeft() {

        try {
            // 四张不同形状的叶子
            Drawable[] mLeafs = new Drawable[]{getDrawable(R.drawable.leaf1),
                    getDrawable(R.drawable.leaf2),
                    getDrawable(R.drawable.leaf3),
                    getDrawable(R.drawable.leaf4),
                    getDrawable(R.drawable.leaf5),
                    getDrawable(R.drawable.leaf6)
            };

            // 四个不同的补间器
            Interpolator[] mInterpolator = new Interpolator[]{new AccelerateDecelerateInterpolator(),
                    new AccelerateInterpolator(),
                    new DecelerateInterpolator(),
                    new LinearInterpolator()};


            final ImageView mLeaf = new ImageView(MainActivity.this);
            Random random = new Random();
            mLeaf.setImageDrawable(mLeafs[random.nextInt(6)]);

            float leafX = random.nextInt(screenWidth / 3) + screenWidth * 1.8f;

            float leafY;

            if (leafX > screenWidth / 2) {
                leafY = screenHeight * 1.0f / screenWidth * leafX - screenHeight * 1.8f;
            } else {
                leafY = -screenHeight * 1.0f / screenWidth * leafX + screenHeight * 1.8f;
            }

            // 设置落叶起点，添加到布局
            mLeaf.setX(random.nextInt(screenWidth ) + screenWidth*1.7f);
            mLeaf.setY(random.nextInt(screenHeight) + screenHeight / 8);

            rlLefts.addView(mLeaf);

            // 设置树叶刚开始出现的动画
            ObjectAnimator alpha = ObjectAnimator.ofFloat(mLeaf, "alpha", 0f, 1);
            ObjectAnimator scaleX = ObjectAnimator.ofFloat(mLeaf, "scaleX", 0f, 0.3f);
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(mLeaf, "scaleY", 0f, 0.3f);
            ObjectAnimator rotation = ObjectAnimator.ofFloat(mLeaf, "rotation", random.nextInt(90), random.nextInt(360));
            AnimatorSet set = new AnimatorSet();
            set.playTogether(alpha, scaleX, scaleY, rotation);
            set.setDuration(1000);

            // 树叶落下的起点
            PointF pointF0 = new PointF(mLeaf.getX(), mLeaf.getY());

            // 树叶落下经过的第二个点
            PointF pointF1 = new PointF(screenWidth + random.nextInt((int) (screenWidth * 1.0)+1),
                    leafY * 2 + random.nextInt((int) (leafY * 1.5)+1));

            // 树叶落下经过的第三个点
            PointF pointF2 = new PointF(screenWidth + random.nextInt((int) (screenWidth * 1.0)+1),
                    leafY * 3 + random.nextInt((int) (leafY * 2.5)+1));

            // 树叶落下的终点
            PointF pointF3 = new PointF(random.nextInt(screenWidth), screenHeight * 3);

            // 通过自定义的贝塞尔估值器算出途经的点的想x，y坐标
            final BazierTypeEvaluator bazierTypeEvaluator = new BazierTypeEvaluator(pointF1, pointF2);

            // 设置值动画
            ValueAnimator bazierAnimator = ValueAnimator.ofObject(bazierTypeEvaluator, pointF0, pointF3);
            bazierAnimator.setTarget(mLeaf);
            bazierAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    final PointF pointF = (PointF) animation.getAnimatedValue();
                    ViewCompat.setX(mLeaf, pointF.x);
                    ViewCompat.setY(mLeaf, pointF.y);
                    ViewCompat.setAlpha(mLeaf, 1 - animation.getAnimatedFraction() / 2);
                }
            });
            bazierAnimator.setDuration(18000);

            // 将以上动画添加到动画集合
            AnimatorSet allSet = new AnimatorSet();
            allSet.play(set).before(bazierAnimator);

            // 随机设置一个补间器
            allSet.setInterpolator(mInterpolator[random.nextInt(4)]);
            allSet.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    rlLefts.removeView(mLeaf);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            allSet.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(yBroadCastReceiver);
        mIsDestoryed = true;
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer = null;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK||keyCode==KeyEvent.KEYCODE_HOME){
            Toast toast = TastyToast.makeText(MainActivity.this, "别走吗！看完先好吗？\n          ╥﹏╥", TastyToast.LENGTH_LONG, TastyToast.ERROR);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
        return false;
    }

    class MyBroadCastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("com.dreamcool.loveup.SHOW_DIALOG") && isOne) {
                vBackground.setVisibility(View.VISIBLE);
                showDialog();
                isOne = false;
            } else if (intent.getAction().equals("com.dreamcool.loveup.UP_DIALOG_TIME")) {
                count++;
                if (count == 550)
                    loveDialog.setTime("爱你");
                else if (count == 580)
                    loveDialog.setTime("白白");
                else if (count <= 520)
                    loveDialog.setTime(count + "");

                if (count > 620) {
                    if (loveDialog.isShowing()){
                        loveDialog.dismiss();
                        vBackground.setVisibility(View.GONE);
                        Toast toast = TastyToast.makeText(MainActivity.this, "听我唱完这首歌我就走了\n         (ﾉ´▽｀)ﾉ♪", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();

                    }
                }
            }
        }
    }
}
