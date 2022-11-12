package com.example.healthapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

/* A class made to test animations
 */

/*
    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);



 */

public class testActivity extends AppCompatActivity {


    private Animation clickEffect;
    private Animation slideOutLeft;
    private Animation rotateRight;
    private Animation rotateLeft;
    private Animation zoomIn;
    private Animation zoomOut;
    private Animation blink;
    private Animation expand;
    private Animation rotateEffect;
    private Animation yoyo;

    private Animation zrf;
    private Animation slideOutRightFall;

    private int currentEnter;
    private int currentExit;

    //Enter 0, Exit 1
    private int setEnterExitMode;

    private FragmentManager fragmentManager;

    private ChallengesActivityFragmentAccept acceptFragment;
    private ChallengesActivityFragmentSend sendFragment;
    private Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        initializeAnimations();
        initializeFragments();

        currentEnter = R.anim.slide_in_left;
        currentExit = R.anim.slide_out_right;

        setEnterExitMode = 0;

        TextView status = findViewById(R.id.setModeId);
        Button enterExitButton = findViewById(R.id.setEnterExitButton);
        enterExitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(setEnterExitMode == 0){
                    setEnterExitMode = 1;
                    status.setText("SETTING EXIT");
                    enterExitButton.setText("Set Enter");

                } else {
                    setEnterExitMode = 0;
                    status.setText("SETTING ENTER");
                    enterExitButton.setText("Set Exit");
                }
            }
        });

        Button clickButton = findViewById(R.id.testButton1);
        clickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAnimation(R.anim.click_effect);
                view.startAnimation(clickEffect); //animate button
            }
        });

        //NOTE IF WANT TO MOVE BUTTON
        //CANT JUST USE ANIMATION SINCE ANIMATIONS
        //DOES NOT UPDATE POSITION
        Button slideButton = findViewById(R.id.slideLeft);
        slideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAnimation(R.anim.slide_out_left);
                view.startAnimation(slideOutLeft); //animate button
            }
        });

        Button rotateRightButton = findViewById(R.id.rotateRightButton);
        rotateRightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAnimation(R.anim.rotate_right);
                view.startAnimation(rotateRight); //animate button
            }
        });

        Button rotateLeftButton = findViewById(R.id.rotateLeftButton);
        rotateLeftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAnimation(R.anim.rotate_left);
                view.startAnimation(rotateLeft); //animate button
            }
        });

        Button zoomInButton = findViewById(R.id.zoomInButton);
        zoomInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAnimation(R.anim.zoom_in);
                v.startAnimation(zoomIn);
            }
        });

        Button zoomOutButton = findViewById(R.id.zoomOutButton);
        zoomOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAnimation(R.anim.zoom_out);
                v.startAnimation(zoomOut);
            }
        });

        Button blinkButton = findViewById(R.id.blinkButton);
        blinkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAnimation(R.anim.blink_effect);
                v.startAnimation(blink);
            }
        });

        Button expandButton = findViewById(R.id.expandButton);
        expandButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAnimation(R.anim.expand_effect);
                v.startAnimation(expand);
            }
        });

        Button rotateEffectButton = findViewById(R.id.rotateEffectButton);
        rotateEffectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAnimation(R.anim.rotate_effect);
                v.startAnimation(rotateEffect);
            }
        });

        Button yoyoButton = findViewById(R.id.yoyoButton);
        yoyoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAnimation(R.anim.yoyo);
                v.startAnimation(yoyo);
            }
        });

        Button t = findViewById(R.id.testButton);
        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(yoyo);
            }
        });

        Button zrf_button = findViewById(R.id.zrf_button);
        zrf_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAnimation(R.anim.zoomin_rotate_fadein);
                v.startAnimation(zrf);
            }
        });

        Button intermediateButton = findViewById(R.id.intermediateButton);
        intermediateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //v.startAnimation(expand);
                Intent intent = new Intent(testActivity.this, testIntermediateActivity.class);
                startActivity(intent);
                overridePendingTransition(currentEnter, currentExit); //-1 no animation
            }
        });

        Button slideOut_R_FallButton = findViewById(R.id.slideROutJumpButton);
        slideOut_R_FallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAnimation(R.anim.slideout_rightfall);
                v.startAnimation(expand);
            }
        });


        expand.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
               //do nothing
            }
            @Override
            public void onAnimationEnd(Animation animation) {
//                Intent intent = new Intent(testActivity.this, testIntermediateActivity.class);
 //               startActivity(intent);
  //              overridePendingTransition(-1, -1); //-1 no animation
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
                //do nothing
            }
        });


    }



    public void initializeAnimations(){
        clickEffect = AnimationUtils.loadAnimation(this,R.anim.click_effect);
        slideOutLeft = AnimationUtils.loadAnimation(this,R.anim.slide_out_left);
        rotateRight = AnimationUtils.loadAnimation(this,R.anim.rotate_right);
        rotateLeft = AnimationUtils.loadAnimation(this,R.anim.rotate_left);
        zoomIn = AnimationUtils.loadAnimation(this,R.anim.zoom_in);
        zoomOut = AnimationUtils.loadAnimation(this,R.anim.zoom_out);
        blink = AnimationUtils.loadAnimation(this,R.anim.blink_effect);
        expand = AnimationUtils.loadAnimation(this,R.anim.expand_effect);
        rotateEffect = AnimationUtils.loadAnimation(this,R.anim.rotate_effect);
        yoyo = AnimationUtils.loadAnimation(this,R.anim.yoyo);
        zrf = AnimationUtils.loadAnimation(this,R.anim.zoomin_rotate_fadein);
        slideOutRightFall = AnimationUtils.loadAnimation(this,R.anim.slideout_rightfall);
    }

    public void initializeFragments() {
        acceptFragment = new ChallengesActivityFragmentAccept();
        sendFragment = new ChallengesActivityFragmentSend();

        fragmentManager = getSupportFragmentManager();

        //replace default container from xml with new container
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer,acceptFragment, null)
                .setReorderingAllowed(true)
                .commit();

        //set starting fragment to fragment replaced container with
        currentFragment = acceptFragment;

        //add sendFragment to fragmentManager
        //hide it so acceptFragment only one shown
        fragmentManager.beginTransaction()
                .add(R.id.fragmentContainer,sendFragment, null)
                .setReorderingAllowed(true)
                .hide(sendFragment)
                .commit();

        //Change Fragment buttons
        //updates currentFragment depending on selected fragment
        Button goAcceptFragment = findViewById(R.id.goAcceptFragment);
        goAcceptFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager.beginTransaction()
                        .setCustomAnimations(
                                currentEnter,
                                currentExit //Exit
                        )
                        .hide(currentFragment)
                        .show(acceptFragment)
                        .setReorderingAllowed(true)
                        .commit();
                currentFragment = acceptFragment;
                view.startAnimation(clickEffect); //animate button
            }
        });
/*
    private Animation clickEffect;
    private Animation slideOutLeft;
    private Animation rotateRight;
    private Animation rotateLeft;
    private Animation zoomIn;
    private Animation zoomOut;
    private Animation blink;
    private Animation expand;
    private Animation rotateEffect;
 */
        Button goSendFragment = findViewById(R.id.goSendFragment);
        goSendFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager.beginTransaction()
                        .setCustomAnimations(
                                currentEnter,  //Enter
                                currentExit
                       )
                        .hide(currentFragment)
                        .show(sendFragment)
                        .setReorderingAllowed(true)
                        .commit();
                currentFragment = sendFragment;
                view.startAnimation(clickEffect); //animate button
            }
        });
    }

    void setAnimation(int animationNum){
        if(setEnterExitMode == 0){
            currentEnter = animationNum;
        } else { //Mode == 1
            currentExit = animationNum;
        }
    }
}
