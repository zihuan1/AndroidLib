package com.zihuan.custon.view;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.zihuan.custon.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpinnerScrollView extends ScrollView {
    private Context mContext;
    int postion = 0;
    //    存放承载同一组数据的view
    Map<TextView, EditText> mViewData = new HashMap<>();
    // 存在view上的数据
    Map<String, String> mapData = new HashMap<>();
    //    存放构造好的数据
    List<SpinnerEntity> mSpinners = new ArrayList<>();
    LinearLayout mLLContents;
    LinearLayout mLLRoot;
    FrameLayout mFLButton;

    public SpinnerScrollView(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    public SpinnerScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();

    }

    public SpinnerScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }


    private void initView() {
        setVerticalScrollBarEnabled(false);
        View view = View.inflate(mContext, R.layout.ssv_layout, null);
        addView(view);
        mLLRoot = view.findViewById(R.id.ll_root);
        mLLContents = view.findViewById(R.id.ll_content);
        mFLButton = view.findViewById(R.id.fl_button);
        mFLButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                addSView(1);
            }
        });
    }


    // 添加一个view
    private void addSView(int rbt) {
        final View content = View.inflate(mContext, R.layout.rv_retrieve_screening, null);
        mLLContents.addView(content);
        AnimUtils.transitionAnim(SpinnerScrollView.this, content, true);
        final TextView textView = content.findViewById(R.id.tv_sr_hint);
        TextView tv_remove = content.findViewById(R.id.tv_remove);
        EditText et_keywords = content.findViewById(R.id.et_keywords);
//        设置同一tag用来确定是同一对数据
        textView.setTag(postion);
        et_keywords.setTag(postion);
        //把两个带参数的view放入集合中
        mViewData.put(textView, et_keywords);
        final RecyclerView recyclerView = content.findViewById(R.id.rv_text);
        XRecyclerAdapter.ViewOnItemClick viewOnItemClick = new XRecyclerAdapter.ViewOnItemClick() {
            @Override
            public void setOnItemClickListener(View view, int postion) {
                textView.setText(mSprinnData.get(postion));
                mapData.put(mSprinnData.get(postion), mSprinnData.get(postion));
                textView.setTextColor(getResources().getColor(R.color.colorPrimary));
                AnimUtils.transitionAnim(SpinnerScrollView.this, recyclerView, false);
            }
        };
        initRecycle(recyclerView, viewOnItemClick);
        textView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimUtils.transitionAnim(SpinnerScrollView.this, recyclerView, true);

            }
        });
//        移除当前view
        tv_remove.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimUtils.transitionAnim(SpinnerScrollView.this, content, false);
                mLLContents.removeView(content);
                mViewData.remove(textView);
            }
        });
//        移除按钮是否显示
        if (rbt == 1) {
            tv_remove.setVisibility(VISIBLE);
        }
        handler.postDelayed(runnable, 200);
        postion++;
    }

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            //        滚动到底部
            fullScroll(ScrollView.FOCUS_DOWN);
        }
    };


    /**
     * 返回数据
     *
     * @return
     */
    public List<SpinnerEntity> getData() {
        mapData.clear();
        mSpinners.clear();
        for (TextView view : mViewData.keySet()) {
//            检索项和关键词都不能为空
            if (!TextUtils.isEmpty(view.getText().toString()) && !TextUtils.isEmpty(mViewData.get(view).getText().toString())) {
                mapData.put(view.getText().toString(), mViewData.get(view).getText().toString());
            }
        }
        for (String str : mSprinnData) {
            if (mapData.containsKey(str)) {
                SpinnerEntity entity = new SpinnerEntity();
                entity.setKey(str);
                entity.setValue(mapData.get(str));
                mSpinners.add(entity);
            }
        }

        return mSpinners;
    }

    List<String> mSprinnData = new ArrayList<>();

    //设置数据
    public void setSprinnData(List<String> list) {
        mSprinnData.addAll(list);
        addSView(0);
    }

    private void initRecycle(RecyclerView recyclerView, XRecyclerAdapter.ViewOnItemClick viewOnItemClick) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        SpinnerAdapter spinnergAdapter = new SpinnerAdapter(this, viewOnItemClick, mSprinnData);
        recyclerView.setAdapter(spinnergAdapter);
    }

    //    添加尾部布局
    public void addFootView(View view) {
        mLLRoot.addView(view);
    }


}
