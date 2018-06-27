package com.example.tbarea.area;

import android.text.TextUtils;

import com.example.tbarea.APP;
import com.example.tbarea.R;
import com.example.tbarea.utils.StreamUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Description : 地址解析器
 *
 * @author WSoBan
 * @date 2018/05/03
 */
public class AreaParser {

    public static String provinceLevel = "1";

    private static AreaParser mInstance;
    private List<AreaBean> allList;
    private List<AreaBean> provinceList;

    private AreaParser() {
        String data = StreamUtils.get(APP.getInstance(), R.raw.area);
        Gson gson = new Gson();
        Type type = new TypeToken<List<AreaBean>>() {
        }.getType();
        allList = new ArrayList<>();
        allList = gson.fromJson(data, type);
        provinceList = new ArrayList<>();
        for (AreaBean areaBean : allList) {
            if (provinceLevel.equals(areaBean.getLevel())) {
                provinceList.add(areaBean);
            }
        }
    }

    public static synchronized AreaParser getInstance() {
        if (mInstance == null) {
            mInstance = new AreaParser();
        }
        return mInstance;
    }

    public List<AreaBean> getProvinceList() {
        if (provinceList == null) {
            return new ArrayList<>();
        }
        return provinceList;
    }

    public List<AreaBean> getChildList(int tid) {
        String id = String.valueOf(tid);
        List<AreaBean> childList = new ArrayList<>();
        if (TextUtils.isEmpty(id)) {
            return childList;
        }
        for (AreaBean areaBean : allList) {
            if (id.equals(areaBean.getPid())) {
                childList.add(areaBean);
            }
        }
        return childList;
    }

    public int getChoosePos(List<AreaBean> list) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).check.get()) {
                return i;
            }
        }
        return 0;
    }
}
