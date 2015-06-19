package com.example.waldekd.contactsintent;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


/**
 * Created by waldekd on 2015-06-19.
 */
public class ServiceActivity extends Activity{
    EditText ed;
    TextView tv;
    Button st;
    boolean isBOund = false;
    private ServiceLocal myService;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            tv.setEnabled(true);
            ServiceLocal.LocalBiner bin = (ServiceLocal.LocalBiner)service;
            myService = bin.getService();
            isBOund = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBOund = false;
            st.setEnabled(true);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_layout);

        ed = (EditText)findViewById(R.id.edText);
        ed.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(myService != null) {
                    s = myService.makeUpper(s.toString());
                    tv.setText(s);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        tv = (TextView)findViewById(R.id.tvOutput);
        tv.setEnabled(false);

        st = (Button)findViewById(R.id.btnStartService);
        st.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ServiceActivity.this, ServiceLocal.class);
                ServiceActivity.this.bindService(i, connection, Context.BIND_AUTO_CREATE);
                v.setEnabled(false);
            }
        });

    }
}
