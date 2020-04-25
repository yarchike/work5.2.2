package com.example.work522;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static Boolean memory = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn_login = findViewById(R.id.login_btn);
        Button btn_reg = findViewById(R.id.registration_btn);
        btn_login.setOnClickListener(this);
        btn_reg.setOnClickListener(this);
        memory = load();

        final CheckBox checkBoxMemory = findViewById(R.id.checkBoxMemory);
        checkBoxMemory.setChecked(memory);
        checkBoxMemory.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkBoxMemory.isChecked()) {
                    memory = true;
                } else {
                    memory = false;
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        EditText login = findViewById(R.id.login_text);
        EditText pass = findViewById(R.id.password_text);
        String lg = login.getText().toString();
        String ps = pass.getText().toString();
        CheckBox checkBoxMemory = findViewById(R.id.checkBoxMemory);

        switch (v.getId()) {
            case R.id.login_btn:
                if (lg.equals("")) {
                    Toast toast = Toast.makeText(this, getString(R.string.no_login), Toast.LENGTH_LONG);
                    toast.show();
                } else if (ps.equals("")) {
                    Toast toast = Toast.makeText(this, getString(R.string.no_pass), Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    String lgps = "";
                    if (checkBoxMemory.isChecked()) {
                        lgps = FileUtil.load(getApplicationContext());
                    } else {
                        lgps = FileUtil.readFile(getApplicationContext());
                    }
                    String[] lgpas = lgps.split(";");
                    String logn = "";
                    String pasw = "";
                    try {
                        logn = lgpas[0];
                        pasw = lgpas[1];
                    } catch (ArrayIndexOutOfBoundsException e) {
                        e.printStackTrace();
                    }

                    if (logn.equals(lg) && pasw.equals(ps)) {
                        Toast toast = Toast.makeText(this, getString(R.string.have_access), Toast.LENGTH_LONG);
                        toast.show();
                    } else {
                        Toast toast = Toast.makeText(this, getString(R.string.no_access), Toast.LENGTH_LONG);
                        toast.show();
                    }


                }

                break;
            case R.id.registration_btn:
                if (lg.equals("")) {
                    Toast toast = Toast.makeText(this, getString(R.string.no_login), Toast.LENGTH_LONG);
                    toast.show();
                } else if (ps.equals("")) {
                    Toast toast = Toast.makeText(this, getString(R.string.no_pass), Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    if (checkBoxMemory.isChecked()) {
                        FileUtil.write(getApplicationContext(), lg, ps);
                    } else {
                        FileUtil.writeFile(getApplicationContext(), lg, ps);
                    }
                    login.setText(null);
                    pass.setText(null);
                    Toast toast = Toast.makeText(this, getString(R.string.registration_successful), Toast.LENGTH_LONG);
                    toast.show();
                }

                break;
        }

    }

    public static void saveCheck(SharedPreferences.Editor myEditor) {
        myEditor.putBoolean("memory", memory);


    }

    @Override
    protected void onStop() {
        super.onStop();
        save();
    }

    private void save() {
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("check", memory);
        editor.commit();
    }

    private boolean load() {
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        return sharedPreferences.getBoolean("check", false);
    }
}
