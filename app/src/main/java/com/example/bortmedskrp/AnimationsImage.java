package com.example.bortmedskrp;

import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.util.Random;

/**
 * Created by  on 2021-10-19.
 * Hanterar alla drawable animationer.
 */

class AnimationsImage {

    ImageView fishView;
    AnimationDrawable animationCountDown;
    CustomAnimationDrawableNew customAnimation;

    public AnimationsImage(ImageView fishView){
        this.fishView = fishView;
    }

    public void setStartFish(){
        fishView.setBackgroundResource(R.drawable.fish1_middle);
    }

    /**
     * Tittar upp och sen slupas om fisken också ska blinka. Binkar 1 av 3 gånger.
     */
    public void fishEyeUp(){

        fishView.setBackground(null);
        fishView.setBackgroundResource(R.drawable.animation_eye_up);
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

        fishView.setBackgroundDrawable(customAnimation);
        customAnimation.setOneShot(true);
        customAnimation.start();

    }

    public void fishEyeClosed(){
        fishView.setBackground(null);
        fishView.setBackgroundResource(R.drawable.animation_eye_closed);
        AnimationDrawable animationClosed = (AnimationDrawable) fishView.getBackground();
        animationClosed.start();
    }

    public void finishRainbow(SaveTheOcean saveTheOcean, int displayWidth){
        ImageView finishImageView = new ImageView(saveTheOcean);
        finishImageView.setBackgroundResource(R.drawable.rainbow4);
        saveTheOcean.constraintLayout.addView(finishImageView);
        finishImageView.setY((Integer)(- (displayWidth / 2)));
        finishImageView.setX(displayWidth);

        Animation animation1 = AnimationUtils.loadAnimation(saveTheOcean.getApplicationContext(), R.anim.animation_finish_rainbow);
        finishImageView.startAnimation(animation1);
    }

    public void countDownAnimation(SaveTheOcean saveTheOcean, ImageView imageView){
        imageView.setBackground(null);
        imageView.setBackgroundResource(R.drawable.animation_count_down_text);
        animationCountDown = (AnimationDrawable) imageView.getBackground();

        customAnimation = new CustomAnimationDrawableNew(animationCountDown) {
            @Override
            public void onAnimationStart() {
            }

            @Override
            public void onAnimationFinish() {
                saveTheOcean.ifCountdownDone = true;
                saveTheOcean.randomSpanTimeItem();
                imageView.setVisibility(View.INVISIBLE);
            }
        };

        imageView.setBackgroundDrawable(customAnimation);
        customAnimation.setOneShot(true);
        customAnimation.start();
    }

    public void stopCountDownAnimation(){

        customAnimation.stop();
    }

}
