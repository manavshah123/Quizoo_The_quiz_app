package com.app.quizoofinal;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.animation.Animator;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.quizoofinal.model.QuizModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class QuestionActivity extends AppCompatActivity {

    FirebaseDatabase database= FirebaseDatabase.getInstance();
    DatabaseReference myRef;

    private String quizcat;
    private LinearLayout optioncontainer;
    private TextView que;
    private Button o1, o2, o3, o4, next;
    private int count = 0;
    private List<QuizModel> list;
    private int postion = 0;
    Integer correct = 0;
    Integer wrong = 0;
    Integer points = 0;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        Window window = this.getWindow();
        window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.themedarkcolor));

        que = findViewById(R.id.question);
        o1 = findViewById(R.id.option1);
        o2 = findViewById(R.id.option2);
        o3 = findViewById(R.id.option3);
        o4 = findViewById(R.id.option4);
        next = findViewById(R.id.next);
        optioncontainer = findViewById(R.id.option_container);

        quizcat = getIntent().getStringExtra("EXTRA_SESSION_ID");


        list = new ArrayList<>();

        myRef = FirebaseDatabase.getInstance().getReference("Que").child(quizcat);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    list.add(dataSnapshot.getValue(QuizModel.class));
                }
                if (list.size() > 0){
                    for (int i = 0; i < 4; i++) {
                        optioncontainer.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                            @Override
                            public void onClick(View v) {
                                checkAnswer((Button)v);
                            }
                        });
                    }

                    count = 0;
                    playAnim(que, 0, list.get(postion).getQ1());

                    next.setOnClickListener(new View.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                        @Override
                        public void onClick(View v) {
                            postion ++;
                            enableOption(true);

                            if (postion == list.size()){
                                Intent intent= new Intent(QuestionActivity.this, ScoreBoard.class);
                                intent.putExtra("correct", correct.toString());
                                intent.putExtra("wrong", wrong.toString() );
                                intent.putExtra("points", points.toString() );
                                startActivity(intent);
                            }
                            count = 0;
                            playAnim(que, 0, list.get(postion).getQ1());
                        }
                    });
                }else {
                    finish();
                    Toast.makeText(QuestionActivity.this, "no que", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(QuestionActivity.this, "error", Toast.LENGTH_SHORT).show();
            }
        });




    }


    private void playAnim(final View view, final int value, final String data) {
        view.animate().alpha(value).scaleX(value).scaleY(value).setDuration(500).setStartDelay(100)
                .setInterpolator(new DecelerateInterpolator()).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                if (value == 0 && count < 4) {
                    String option = "";
                    if (count == 0) {
                        option = list.get(postion).getO1();
                    } else if (count == 1) {
                        option = list.get(postion).getO2();
                    } else if (count == 2) {
                        option = list.get(postion).getO3();
                    } else if (count == 3) {
                        option = list.get(postion).getO4();
                    }

                    playAnim(optioncontainer.getChildAt(count), 0, option);
                    count++;
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {

                if (value == 0) {
                    try {
                        ((TextView) view).setText(data);
                    }catch (ClassCastException ex){
                        ((Button) view).setText(data);
                    }
                    view.setTag(data);
                    playAnim(view, 1, data);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void checkAnswer(Button selectedOption) {
        enableOption(false);
        if(selectedOption.getText().toString().equals(list.get(postion).getAns())){
            selectedOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF018786")));
            correct ++;
            points = points + 10;

            //correct
        }
        else{
            selectedOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#EE6868")));
                wrong++;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void enableOption(boolean enable) {
        for (int i = 0; i < 4; i++) {
            optioncontainer.getChildAt(i).setEnabled(enable);
            if (enable) {
                optioncontainer.getChildAt(i).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFFFF")));
            }
        }
    }
}