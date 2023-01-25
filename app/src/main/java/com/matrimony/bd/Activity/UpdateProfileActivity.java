package com.matrimony.bd.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.matrimony.bd.Fragments.ProfileFragments.Fragment1;
import com.matrimony.bd.Fragments.ProfileFragments.Fragment10;
import com.matrimony.bd.Fragments.ProfileFragments.Fragment11;
import com.matrimony.bd.Fragments.ProfileFragments.Fragment2;
import com.matrimony.bd.Fragments.ProfileFragments.Fragment3;
import com.matrimony.bd.Fragments.ProfileFragments.Fragment4;
import com.matrimony.bd.Fragments.ProfileFragments.Fragment5;
import com.matrimony.bd.Fragments.ProfileFragments.Fragment6;
import com.matrimony.bd.Fragments.ProfileFragments.Fragment7;
import com.matrimony.bd.Fragments.ProfileFragments.Fragment8;
import com.matrimony.bd.Fragments.ProfileFragments.Fragment9;
import com.matrimony.bd.R;
import com.shuhart.stepview.StepView;

public class UpdateProfileActivity extends AppCompatActivity {

    public StepView stepView;
    public CardView next;
    ImageView back;
    TextView name;

    CoordinatorLayout coordinatorLayout;

    public FrameLayout container, main_container;

    public int stepIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        stepView = findViewById(R.id.step_view);
        next = findViewById(R.id.next);
        back = findViewById(R.id.back);
        name = findViewById(R.id.name);
        main_container = findViewById(R.id.main_container);
        container = findViewById(R.id.container);
        coordinatorLayout = findViewById(R.id.coordinatorLayout);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container,
                    new Fragment1()).commit();
        }
        name.setText("নাম");

        stepView.getState().animationType(StepView.ANIMATION_ALL)
                .stepsNumber(11)
                .animationDuration(getResources().getInteger(android.R.integer.config_shortAnimTime))
                .commit();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextSteps();
            }
        });


    }

    @Override
    public void onBackPressed() {
        if (stepIndex > 0) {
            stepIndex = stepIndex - 1;
            stepView.done(false);
        }

        Fragment selectedFragment = null;
        switch (stepIndex) {
            case 0:
                selectedFragment = new Fragment1();
                getSupportFragmentManager().beginTransaction().replace(R.id.container,
                        selectedFragment).commit();
                name.setText("নাম");
                break;
            case 1:
                selectedFragment = new Fragment2();
                getSupportFragmentManager().beginTransaction().replace(R.id.container,
                        selectedFragment).commit();
                name.setText("সাধারণ তথ্য");
                break;

            case 2:
                selectedFragment = new Fragment3();
                getSupportFragmentManager().beginTransaction().replace(R.id.container,
                        selectedFragment).commit();
                name.setText("ঠিকানা");
                break;

            case 3:
                selectedFragment = new Fragment4();
                getSupportFragmentManager().beginTransaction().replace(R.id.container,
                        selectedFragment).commit();
                name.setText("শিক্ষাগত যোগ্যতা");
                break;
            case 4:
                selectedFragment = new Fragment5();
                getSupportFragmentManager().beginTransaction().replace(R.id.container,
                        selectedFragment).commit();
                name.setText("পারিবারিক তথ্য");
                break;
            case 5:
                selectedFragment = new Fragment6();
                getSupportFragmentManager().beginTransaction().replace(R.id.container,
                        selectedFragment).commit();
                name.setText("ব্যক্তিগত তথ্য");
                break;
            case 6:
                selectedFragment = new Fragment7();
                getSupportFragmentManager().beginTransaction().replace(R.id.container,
                        selectedFragment).commit();
                name.setText("বিয়ে সংক্রান্ত তথ্য");
                break;
            case 7:
                selectedFragment = new Fragment8();
                getSupportFragmentManager().beginTransaction().replace(R.id.container,
                        selectedFragment).commit();
                name.setText("অন্যান্য তথ্য");
                break;
            case 8:
                selectedFragment = new Fragment9();
                getSupportFragmentManager().beginTransaction().replace(R.id.container,
                        selectedFragment).commit();
                name.setText("যেমন জীবনসঙ্গী আশা করেন");
                break;
            case 9:
                selectedFragment = new Fragment10();
                getSupportFragmentManager().beginTransaction().replace(R.id.container,
                        selectedFragment).commit();
                name.setText("কর্তৃপক্ষের জিজ্ঞাসা");
                break;
            case 10:
                selectedFragment = new Fragment11();
                getSupportFragmentManager().beginTransaction().replace(R.id.container,
                        selectedFragment).commit();
                name.setText("যোগাযোগ");
                break;
        }

        stepView.go(stepIndex, true);
        if (stepIndex == 0) {
            stepView.done(false);
        }
        Log.d("STEPS", "onClick: " + stepIndex);
//        super.onBackPressed();
    }

    public void nextSteps() {
        stepIndex = stepView.getCurrentStep();
        if (stepIndex < 11) {
            stepIndex = stepIndex+1;
        }

        Fragment selectedFragment = null;
        switch (stepIndex) {
            case 0:
                selectedFragment = new Fragment1();
                getSupportFragmentManager().beginTransaction().replace(R.id.container,
                        selectedFragment).commit();
                name.setText("নাম");
                break;
            case 1:
                selectedFragment = new Fragment2();
                getSupportFragmentManager().beginTransaction().replace(R.id.container,
                        selectedFragment).commit();
                name.setText("সাধারণ তথ্য");
                break;

            case 2:
                selectedFragment = new Fragment3();
                getSupportFragmentManager().beginTransaction().replace(R.id.container,
                        selectedFragment).commit();
                name.setText("ঠিকানা");
                break;

            case 3:
                selectedFragment = new Fragment4();
                getSupportFragmentManager().beginTransaction().replace(R.id.container,
                        selectedFragment).commit();
                name.setText("শিক্ষাগত যোগ্যতা");
                break;
            case 4:
                selectedFragment = new Fragment5();
                getSupportFragmentManager().beginTransaction().replace(R.id.container,
                        selectedFragment).commit();
                name.setText("পারিবারিক তথ্য");
                break;
            case 5:
                selectedFragment = new Fragment6();
                getSupportFragmentManager().beginTransaction().replace(R.id.container,
                        selectedFragment).commit();
                name.setText("ব্যক্তিগত তথ্য");
                break;
            case 6:
                selectedFragment = new Fragment7();
                getSupportFragmentManager().beginTransaction().replace(R.id.container,
                        selectedFragment).commit();
                name.setText("বিয়ে সংক্রান্ত তথ্য");
                break;
            case 7:
                selectedFragment = new Fragment8();
                getSupportFragmentManager().beginTransaction().replace(R.id.container,
                        selectedFragment).commit();
                name.setText("অন্যান্য তথ্য");
                break;
            case 8:
                selectedFragment = new Fragment9();
                getSupportFragmentManager().beginTransaction().replace(R.id.container,
                        selectedFragment).commit();
                name.setText("যেমন জীবনসঙ্গী আশা করেন");
                break;
            case 9:
                selectedFragment = new Fragment10();
                getSupportFragmentManager().beginTransaction().replace(R.id.container,
                        selectedFragment).commit();
                name.setText("কর্তৃপক্ষের জিজ্ঞাসা");
                break;
            case 10:
                selectedFragment = new Fragment11();
                getSupportFragmentManager().beginTransaction().replace(R.id.container,
                        selectedFragment).commit();
                name.setText("যোগাযোগ");
                break;
            case 11:
                Toast.makeText(this, "Profile Updated!", Toast.LENGTH_LONG).show();
                finish();
                break;
        }

        stepView.go(stepIndex, true);

        if (stepIndex == 11) {
            stepView.done(true);
        }

        Log.d("STEPS", "onClick: " + stepIndex);
    }
}