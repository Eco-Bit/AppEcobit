package br.com.fatec.ecobit;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class FACActivity extends AppCompatActivity {
    private ImageView btnVoltar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fac);

        btnVoltar = findViewById(R.id.faq_arrow_0);

        btnVoltar.setOnClickListener(v->{
            finish();
        });

        setupFAQToggle(R.id.layout1, R.id.faq_answer_1, R.id.faq_arrow_1, R.id.faq_divider_1);
        setupFAQToggle(R.id.layout2, R.id.faq_answer_2, R.id.faq_arrow_2, R.id.faq_divider_2);
        setupFAQToggle(R.id.layout3, R.id.faq_answer_3, R.id.faq_arrow_3, R.id.faq_divider_3);
        setupFAQToggle(R.id.layout4, R.id.faq_answer_4, R.id.faq_arrow_4, R.id.faq_divider_4);
    }

    private void setupFAQToggle(int layoutId, int answerId, int arrowId, int dividerId) {
        View layout = findViewById(layoutId);
        final TextView answer = findViewById(answerId);
        final ImageView arrow = findViewById(arrowId);
        final View divider = findViewById(dividerId);

        layout.setOnClickListener(new View.OnClickListener() {
            private boolean isAnswerVisible = false;

            @Override
            public void onClick(View v) {
                if (isAnswerVisible) {
                    answer.setVisibility(View.GONE);
                    divider.setVisibility(View.GONE);
                    arrow.setImageResource(R.drawable.ic_arrow_down);
                } else {
                    answer.setVisibility(View.VISIBLE);
                    divider.setVisibility(View.VISIBLE);
                    arrow.setImageResource(R.drawable.ic_arrow_up);
                }
                isAnswerVisible = !isAnswerVisible;
            }
        });
    }
}