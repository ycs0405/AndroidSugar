package com.seven.library.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.seven.library.R;


/**
 * loading对话框
 * Created by sunnybear on 2015/3/9.
 */
public class LoadingHUD extends Dialog {
    public static final int FADED_ROUND_SPINNER = 0;
    public static final int GEAR_SPINNER = 1;
    public static final int SIMPLE_ROUND_SPINNER = 2;

    static LoadingHUD instance;
    View view;
    TextView tvMessage;
    ImageView iv_icon;
    AnimationDrawable adProgressSpinner;
    Context context;
    private RotateAnimation mRotateAnimation;//旋转动画

    OnDialogDismiss onDialogDismiss;

    public OnDialogDismiss getOnDialogDismiss() {
        return onDialogDismiss;
    }

    public void setOnDialogDismiss(OnDialogDismiss onDialogDismiss) {
        this.onDialogDismiss = onDialogDismiss;
    }

    public static LoadingHUD getInstance(Context context) {
//        if (instance == null) {
        instance = new LoadingHUD(context);
//        }
        return instance;
    }

    public LoadingHUD(Context context) {
        super(context, R.style.DialogTheme);
        this.setCanceledOnTouchOutside(false);
        this.context = context;
        view = getLayoutInflater().inflate(R.layout.widget_loading_dialog_progress, null);
        iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
        setSpinnerType(FADED_ROUND_SPINNER);
        this.setContentView(view);
    }

    /**
     * 初始化旋转动画
     */
    private void initAnimation() {
        mRotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5F, Animation.RELATIVE_TO_SELF, 0.5F);
        mRotateAnimation.setInterpolator(new LinearInterpolator());
        mRotateAnimation.setDuration(1000);
        mRotateAnimation.setRepeatCount(Animation.INFINITE);
    }

    public void setSpinnerType(int spinnerType) {
        initAnimation();
//        switch (spinnerType) {
//            case 0:
//                ivProgressSpinner.setImageResource(R.anim.round_spinner_fade);
//                break;
//            case 1:
//                ivProgressSpinner.setImageResource(R.anim.gear_spinner);
//                break;
//            case 2:
//                ivProgressSpinner.setImageResource(R.anim.round_spinner);
//                break;
//            default:
//                ivProgressSpinner.setImageResource(R.anim.round_spinner_fade);
//        }
//        adProgressSpinner = (AnimationDrawable) iv_icon.getDrawable();
    }

//    public void setMessage(String message) {
//        tvMessage.setText(message);
//    }

    @Override
    public void show() {
        try {
            if (this.isShowing()) return;
            if (!((Activity) context).isFinishing()) {
                super.show();
                iv_icon.startAnimation(mRotateAnimation);
            } else
                instance = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    public void dismissWithSuccess() {
//        tvMessage.setText("Success");
//        showSuccessImage();

        if (onDialogDismiss != null) {
            this.setOnDismissListener(new OnDismissListener() {

                @Override
                public void onDismiss(DialogInterface dialog) {
                    onDialogDismiss.onDismiss();
                }
            });
        }
        dismissHUD();
    }

    public void dismissWithSuccess(String message) {
//        showSuccessImage();
        if (message != null) {
            tvMessage.setText(message);
        } else {
            tvMessage.setText("");
        }

        if (onDialogDismiss != null) {
            this.setOnDismissListener(new OnDismissListener() {

                @Override
                public void onDismiss(DialogInterface dialog) {
                    onDialogDismiss.onDismiss();
                }
            });
        }
        dismissHUD();
    }

    public void dismissWithFailure() {
//        showFailureImage();
//        tvMessage.setText("Failure");
        if (onDialogDismiss != null) {
            this.setOnDismissListener(new OnDismissListener() {

                @Override
                public void onDismiss(DialogInterface dialog) {
                    onDialogDismiss.onDismiss();
                }
            });
        }
        dismissHUD();
    }

    public void dismissWithFailure(String message) {
//        showFailureImage();
        if (message != null) {
            tvMessage.setText(message);
        } else {
            tvMessage.setText("");
        }
        if (onDialogDismiss != null) {
            this.setOnDismissListener(new OnDismissListener() {

                @Override
                public void onDismiss(DialogInterface dialog) {
                    onDialogDismiss.onDismiss();
                }
            });
        }
        dismissHUD();
    }

//   protected void showSuccessImage() {
//        ivProgressSpinner.setVisibility(View.GONE);
//        ivSuccess.setVisibility(View.VISIBLE);
//    }
//
//    protected void showFailureImage() {
//        ivProgressSpinner.setVisibility(View.GONE);
//        ivFailure.setVisibility(View.VISIBLE);
//    }
//
//    protected void reset() {
//        ivProgressSpinner.setVisibility(View.VISIBLE);
//        ivFailure.setVisibility(View.GONE);
//        ivSuccess.setVisibility(View.GONE);
//        tvMessage.setText("Loading ...");
//    }

    protected void dismissHUD() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mRotateAnimation.cancel();
                dismiss();
            }
        }, 500);
    }

//    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//        iv_icon.post(new Runnable() {
//
//            @Override
//            public void run() {
//                adProgressSpinner.start();
//
//            }
//        });
//    }

    /**
     * 关闭加载框回调
     */
    public interface OnDialogDismiss {
        void onDismiss();
    }
}