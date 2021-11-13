package com.project.bmi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    EditText edt_height, edt_weight;
    Button btn_bmi_click, btn_afresh;
    TextView txt_result;
    CardView card_result;
    Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("BMI");

        edt_height = findViewById(R.id.edt_height);
        edt_weight = findViewById(R.id.edt_weight);
        btn_bmi_click = findViewById(R.id.btn_bmi_click);
        btn_afresh = findViewById(R.id.btn_afresh);
        txt_result = findViewById(R.id.txt_result);
        card_result = findViewById(R.id.card_result);

        btn_bmi_click.setOnClickListener(bmi_clickListener);
        btn_afresh.setOnClickListener(afresh_clickListener);

        edt_height.requestFocusFromTouch();
    }

    //功能表
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_bmi:
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("BMI範圍")
                        .setMessage("BMI < 18.5，過輕!\n\n" +
                                "18.5 < BMI < 24，標準!\n\n" +
                                "BMI > 24，過重!\n\n" +
                                "27 < BMI < 30，輕度肥胖!\n\n" +
                                "30 < BMI < 35，中度肥胖!\n\n" +
                                "BMI > 35，重度肥胖!")
                        .setPositiveButton("關閉", new AlertDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    //取得功能表
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bmimenu, menu);
        return true;
    }

    //計算
    private Button.OnClickListener bmi_clickListener = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {
            String h = edt_height.getText().toString();
            String w = edt_weight.getText().toString();

            if(h.equals("0") || w.equals("0")) {
                if (toast != null)
                    toast.cancel();

                toast = Toast.makeText(MainActivity.this, "身高或體重不可為0", Toast.LENGTH_SHORT);
                toast.show();

            } else if(h.isEmpty() && w.isEmpty()) {
                if(toast != null)
                    toast.cancel();

                toast = Toast.makeText(MainActivity.this,"請輸入身高與體重",Toast.LENGTH_SHORT);
                toast.show();

            } else if(h.isEmpty() || w.isEmpty()) {
                if(toast != null)
                    toast.cancel();

                toast = Toast.makeText(MainActivity.this, "請輸入身高或體重", Toast.LENGTH_SHORT);
                toast.show();

            } else {
                if(toast != null)
                    toast.cancel();

                double height = Double.parseDouble(edt_height.getText().toString()) / 100;
                double weight = Double.parseDouble(edt_weight.getText().toString());
                double BMI = weight / (height * height);

                DecimalFormat df = new DecimalFormat("0.0");
                String s = "BMI=" + df.format(BMI);

                card_result.setVisibility(view.VISIBLE);

                double standard = height * height * 18.5;
                String standard_weight = "建議體重為：" + df.format(standard) + "kg";

                if (BMI > 24) {
                    if (BMI >= 24 && BMI < 27) {
                        txt_result.setText(s + "，過重!\n\n" + standard_weight);
                    } else if (BMI >= 27 && BMI < 30) {
                        txt_result.setText(s + "，輕度肥胖!\n\n" + standard_weight);
                    } else if (BMI >= 30 && BMI < 35) {
                        txt_result.setText(s + "，中度肥胖!\n\n" + standard_weight);
                    } else {
                        txt_result.setText(s + "，重度肥胖!\n\n" + standard_weight);
                    }
                } else if (BMI < 18.5) {
                    txt_result.setText(s + "，過輕!\n\n" + standard_weight);
                } else {
                    txt_result.setText(s + "，標準!");
                }
            }
        }
    };

    //重新計算
    Button.OnClickListener afresh_clickListener = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {
            card_result.setVisibility(view.INVISIBLE);
            edt_height.setText("");
            edt_weight.setText("");
            edt_height.requestFocus();
        }
    };
}
