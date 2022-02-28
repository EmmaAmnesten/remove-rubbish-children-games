package com.example.bortmedskrp;

import android.graphics.drawable.AnimationDrawable;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.util.Random;

/**
 * Created by  on 2021-10-19.
 * Class for all drawable animation.
 */

class AnimationsImage {

    ImageView fishView;
    boolean ifCountDownStopped;
    boolean ifCountDownDone;
    boolean ifCountDownIsRunning;

    public AnimationsImage(ImageView fishView){
        this.fishView = fishView;
        ifCountDownStopped = false;
        ifCountDownDone = false;
        ifCountDownIsRunning = false;
    }

    public void setStartFish(){
        fishView.setBackgroundResource(R.drawable.bigfish_eyes_middle);
    }

    /**
     * Fish looks up and random if fish also blinks. Blinks 1 av 3 times.
     */
    public void fishEyeUp(){

        fishView.setBackground(null);
        fishView.setBackgroundResource(R.drawable.anim_bigfish_eye_up);
        AnimationDrawable animationEyeUp = (AnimationDrawable) fishView.getBackground();

        CustomAnimationDrawableNew customAnimation = new CustomAnimationDrawableNew(animationEyeUp) {
            @Override
            public void onAnimationStart() {
            }

            @Override
            public void onAnimationFinish() {
                int randomNum = new Random().nextInt(3);
                if (randomNum == 1){
                    fishEyeClosed();
                }
            }
        };

        fishView.setBackground(customAnimation);
        customAnimation.setOneShot(true);
        customAnimation.start();

    }

    public void fishEyeClosed(){
        fishView.setBackground(null);
        fishView.setBackgroundResource(R.drawable.anim_bigfish_eyes_closed);
        AnimationDrawable animationClosed = (AnimationDrawable) fishView.getBackground();
        animationClosed.start();
    }

    public void finishRainbow(SaveTheOcean saveTheOcean, int displayWidth){
        ImageView finishImageView = new ImageView(saveTheOcean);
        finishImageView.setBackgroundResource(R.drawable.end_rainbow);
        saveTheOcean.constraintLayout.addView(finishImageView);
        finishImageView.setY((- (displayWidth >> 1)));
        finishImageView.setX(displayWidth);

        Animation animation1 = AnimationUtils.loadAnimation(saveTheOcean.getApplicationContext(), R.anim.animation_finish_rainbow);
        finishImageView.startAnimation(animation1);
    }
}
