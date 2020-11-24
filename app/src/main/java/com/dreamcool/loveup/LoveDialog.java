package com.dreamcool.loveup;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * @author Tom.Cai
 * @Function: 自定义对话框
 * @Date: 2013-10-28
 * @Time: 下午12:37:43
 */
public class LoveDialog extends Dialog {
    private RelativeLayout rlDialog;
    private RelativeLayout relativeLayout1;
    private RelativeLayout relativeLayout2;
    private ImageView ivYes;
    private Context context;
    private TextView tvTime;
    private TextView tvText;
    private Timer timer;
    private TimerTask task;
    private int height;

    public LoveDialog(Context context,int height) {
        super(context, R.style.dialog_style2);
        this.context = context;
        this.height = height;
        setCustomDialog();
    }

    //发送短信
    /**public void sendMessage() {
        BmobSMS.requestSMSCode("13192592373", "智能社团", new QueryListener<Integer>() {
            @Override
            public void done(Integer smsId, BmobException e) {
                if (e == null) {
                    Log.d("sendMessage", "发送验证码成功，短信ID：" + smsId + "\n");
                } else {
                    Log.d("sendMessage", "发送验证码失败：" + e.getErrorCode() + "-" + e.getMessage() + "\n");
                }
            }
        });
    }*/

    private void setCustomDialog() {
        View mView = View.inflate(context, R.layout.love_dialog, null);
        rlDialog = mView.findViewById(R.id.rl_dialog);
        relativeLayout1 = mView.findViewById(R.id.relative_layout1);
        relativeLayout2 = mView.findViewById(R.id.relative_layout2);
        tvTime = mView.findViewById(R.id.tv_time);
        tvText = mView.findViewById(R.id.tv_text);
        ivYes = mView.findViewById(R.id.iv_yes);
        ivYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                sendMessage();
                playAnimation3(relativeLayout1);

            }
        });

        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent();
                intent.setAction("com.dreamcool.loveup.UP_DIALOG_TIME");
                context.sendBroadcast(intent);
            }
        };
        super.setContentView(mView);
    }

    public void setTime(String time) {
        tvTime.setText(time + "");
    }

    public void playAnimation(View view, final int type) {
        List<Animator> animators = new ArrayList<>();//设置一个装动画的集合
        final ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 0f, 1f);
        scaleX.setDuration(2000);//设置持续时间
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 0f, 1);
        scaleY.setDuration(2000);//设置持续时间
        ObjectAnimator alpha = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f);
        alpha.setDuration(2000);//设置持续时间

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

                if (type == 1)
                    playAnimation4(tvText);

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

    public void playAnimation2(View view) {

        Log.d("高度",height*0.7+"");

        List<Animator> animators = new ArrayList<>();//设置一个装动画的集合
        final ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 0f, 1f);
        scaleX.setDuration(5200);//设置持续时间
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 0f, 1);
        scaleY.setDuration(5200);//设置持续时间
        ObjectAnimator alpha = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f);
        alpha.setDuration(5200);//设置持续时间
        ObjectAnimator translationY = ObjectAnimator.ofFloat(view, "translationY", (float) (height*0.7), 0f);
        translationY.setDuration(5200);//设置持续时间

        translationY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //底部列表的translationY

            }
        });
        translationY.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                ivYes.setVisibility(View.VISIBLE);
                playAnimation(ivYes, 0);
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
        animators.add(translationY);
        AnimatorSet btnSexAnimatorSet = new AnimatorSet();//动画集
        btnSexAnimatorSet.playTogether(animators);//设置一起播放
        btnSexAnimatorSet.start();//开始播放
    }

    public void playAnimation3(final View view) {
        List<Animator> animators = new ArrayList<>();//设置一个装动画的集合
        final ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1f, 0f);
        scaleX.setDuration(2000);//设置持续时间
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1f, 0);
        scaleY.setDuration(2000);//设置持续时间
        ObjectAnimator alpha = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f);
        alpha.setDuration(2000);//设置持续时间
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
                view.setVisibility(View.GONE);
                relativeLayout2.setVisibility(View.VISIBLE);
                playAnimation(relativeLayout2, 1);
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

    public void playAnimation4(final View view) {
        tvText.setVisibility(View.VISIBLE);
        List<Animator> animators = new ArrayList<>();//设置一个装动画的集合
        ObjectAnimator alpha = ObjectAnimator.ofFloat(view, "alpha", 0f,1f);
        alpha.setDuration(2000);//设置持续时间
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
                tvTime.setVisibility(View.VISIBLE);
                timer.schedule(task, 0, 80);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }

        });
        animators.add(alpha);
        AnimatorSet btnSexAnimatorSet = new AnimatorSet();//动画集
        btnSexAnimatorSet.playTogether(animators);//设置一起播放
        btnSexAnimatorSet.start();//开始播放
    }

    @Override
    public void show() {
        super.show();
        playAnimation2(rlDialog);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        task.cancel();
    }
}
