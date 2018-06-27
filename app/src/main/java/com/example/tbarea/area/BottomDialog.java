package com.example.tbarea.area;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;

import com.example.tbarea.R;
import com.example.tbarea.databinding.LayoutBottomSheetDialogBinding;
import com.example.tbarea.utils.TabLayoutUtil;

import java.util.Map;
import java.util.TreeMap;

/**
 * Description :
 *
 * @author WSoBan
 * @date 2018/05/03
 */
public class BottomDialog extends Dialog {

    private OnSelectedResultCallBack resultCallBack;
    private LayoutBottomSheetDialogBinding mDialogBinding;

    private AreaAdapter mAdapter;
    private Map<Integer, AreaBean> currentMap = new TreeMap<>();

    public BottomDialog(Context context) {
        super(context, R.style.bottom_dialog);
        init(context);
    }

    private void init(Context context) {
        mDialogBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),
                R.layout.layout_bottom_sheet_dialog, null, false);
        setContentView(mDialogBinding.getRoot());
        initView();

        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
//        params.height = DensityUtils.dp2px(context, 400);
        window.setAttributes(params);

        window.setGravity(Gravity.BOTTOM);
    }

    private void initView() {
        mDialogBinding.ivClose.setOnClickListener(v -> dismiss());

        mAdapter = new AreaAdapter(R.layout.item_area);
        mAdapter.setOnSelectedListener((map, pos) -> {
            if (pos >= 4) {
                if (resultCallBack != null) {
                    resultCallBack.onResult(currentMap.get(pos).getNames());
                }
                dismiss();
            } else {
                currentMap = map;
                mDialogBinding.tlTitle.removeAllTabs();
                for (Integer in : map.keySet()) {
                    mDialogBinding.tlTitle.addTab(
                            mDialogBinding.tlTitle.newTab().setText(map.get(in).getName()));
                }
                addChooseTab();
            }
        });
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mDialogBinding.rv.setLayoutManager(manager);
        mDialogBinding.rv.addItemDecoration(new LineAreaItemDecoration(getContext(), 2));
        mDialogBinding.rv.setAdapter(mAdapter);

        mDialogBinding.tlTitle.setTabMode(TabLayout.MODE_SCROLLABLE);
        mDialogBinding.tlTitle.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos = tab.getPosition();
                if (pos == 0) {
                    mAdapter.setData(pos, AreaParser.getInstance().getProvinceList());
                } else {
                    mAdapter.setData(pos, AreaParser.getInstance().getChildList(currentMap.get(pos - 1).getTid()));
                }
                //移动到指定位置
                mAdapter.moveToPosition(manager);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        addChooseTab();
    }

    private void addChooseTab() {
        mDialogBinding.tlTitle.addTab(mDialogBinding.tlTitle.newTab().setText("请选择"), true);
        TabLayoutUtil.reflex(mDialogBinding.tlTitle);
    }

    public BottomDialog(Context context, int themeResId) {
        super(context, themeResId);
        init(context);
    }

    public BottomDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init(context);
    }

    public void setResultCallBack(OnSelectedResultCallBack resultCallBack) {
        this.resultCallBack = resultCallBack;
    }

    public interface OnSelectedResultCallBack {

        void onResult(String result);
    }
}
