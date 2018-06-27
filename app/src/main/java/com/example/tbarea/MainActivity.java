package com.example.tbarea;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;

import com.example.tbarea.area.BottomDialog;
import com.example.tbarea.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.activity_main, null, false);
        setContentView(mBinding.getRoot());
        initView();
    }

    private void initView() {
        BottomDialog dialog = new BottomDialog(this);
        dialog.setResultCallBack(result -> mBinding.tv.setText(String.format("地区：%s", result)));
        mBinding.btn.setOnClickListener(v -> dialog.show());
    }
}
