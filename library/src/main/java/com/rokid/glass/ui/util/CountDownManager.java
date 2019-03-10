package com.rokid.glass.ui.util;

import android.os.CountDownTimer;

/**
 * @author jian.yang
 * @date 2019/2/25
 */

public class CountDownManager {
    private CountDownTimer countDownTimer;
    private long millisInFuture;
    private long countDownInterval;
    private CountDownListener countDownListener;

    public CountDownManager(Builder builder) {
        this.millisInFuture = builder.millisInFuture;
        this.countDownInterval = builder.countDownInterval;
        this.countDownListener = builder.countDownListener;

        countDownTimer = new CountDownTimer(millisInFuture + 100, countDownInterval) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (null != countDownListener) {
                    countDownListener.onTick(millisUntilFinished);
                }
            }

            @Override
            public void onFinish() {
                if (null != countDownListener) {
                    countDownListener.onFinish();
                }
            }
        };
    }

    public void cancel() {
        if (null != countDownTimer) {
            countDownTimer.cancel();
        }
    }

    public void start() {
        if (null != countDownTimer) {
            countDownTimer.start();
        }
    }

    public static class Builder {
        private long millisInFuture;
        private long countDownInterval;
        private CountDownListener countDownListener;

        public Builder setMillisInFuture(long millisInFuture) {
            this.millisInFuture = millisInFuture;
            return this;
        }

        public Builder setCountDownInterval(long countDownInterval) {
            this.countDownInterval = countDownInterval;
            return this;
        }

        public Builder setCountDownListener(CountDownListener countDownListener) {
            this.countDownListener = countDownListener;
            return this;
        }

        public CountDownManager build() {
            return new CountDownManager(this);
        }
    }

    public interface CountDownListener {
        void onTick(long millisUntilFinished);

        void onFinish();
    }
}
