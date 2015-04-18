package com.yinzhiliang.planewar;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * @author yinzhiliang
 *
 */
public class MainActivity extends Activity implements View.OnClickListener {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ready);

        Button startBtn = (Button) findViewById(R.id.btn_start);
        startBtn.setOnClickListener(this);

        Button quitBtn = (Button) findViewById(R.id.btn_quit);
        quitBtn.setOnClickListener(this);
	}

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
            	setContentView(new MainView(this));
                break;
            case R.id.btn_quit:
                finish();
                break;
            default:
                break;
        }
    }
}
