package com.zihuandrag.app;

import android.app.Service;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ViewOnItemClick, ViewOnItemLongClick {

    RecyclerView rvLeft, rvRight;
    LeftAdapter mLeftAdapter;
    RightAdapter mRightAdapter;
    List<LeftEntity> mLeft = new ArrayList<>();
    List<LeftEntity> mRight = new ArrayList<>();
    Vibrator vib;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.app_title);
        rvLeft = findViewById(R.id.rv_left);
        rvRight = findViewById(R.id.rv_right);
        vib = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);//震动70毫秒
        mLeftAdapter = new LeftAdapter(this);
        mRightAdapter = new RightAdapter(this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvLeft.setLayoutManager(manager);
        rvLeft.setAdapter(mLeftAdapter);
        LinearLayoutManager managerRight = new LinearLayoutManager(this);
        managerRight.setOrientation(LinearLayoutManager.VERTICAL);
        rvRight.setLayoutManager(managerRight);
        rvRight.setAdapter(mRightAdapter);
        mLeft.add(new LeftEntity("诗", 0, 1));
        mLeft.add(new LeftEntity("词", 0, 2));
        mLeft.add(new LeftEntity("歌", 0, 3));
        mLeft.add(new LeftEntity("赋", 0, 4));
        mLeftAdapter.update(mLeft);

        mRight.add(new LeftEntity("青青子衿", 0, 1));
        mRight.add(new LeftEntity("悠悠我心", 0, 1));
        mRight.add(new LeftEntity("纵我不往", 0, 1));
        mRight.add(new LeftEntity("子不嗣音", 0, 1));

        mRight.add(new LeftEntity("大江东去", 0, 2));
        mRight.add(new LeftEntity("浪淘尽", 0, 2));
        mRight.add(new LeftEntity("千古风流人物", 0, 2));
        mRight.add(new LeftEntity("故垒西边人道是", 0, 2));
        mRight.add(new LeftEntity("周郎赤壁", 0, 2));

        mRight.add(new LeftEntity("回眸一笑百媚生", 0, 3));
        mRight.add(new LeftEntity("六宫粉黛无颜色", 0, 3));
        mRight.add(new LeftEntity("一骑红尘妃子笑", 0, 3));
        mRight.add(new LeftEntity("无人知是荔枝来", 0, 3));

        mRight.add(new LeftEntity("渔舟唱晚", 0, 4));
        mRight.add(new LeftEntity("响穷彭蠡之滨", 0, 4));
        mRight.add(new LeftEntity("雁阵惊寒", 0, 4));
        mRight.add(new LeftEntity("声断衡阳之阜", 0, 4));

        rvLeft.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                final int action = event.getAction();
                switch (action) {
                    case DragEvent.ACTION_DRAG_STARTED: // 拖拽开始
                        return event.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN);
                    case DragEvent.ACTION_DRAG_ENTERED: // 被拖拽View进入目标区域
                        return true;
                    case DragEvent.ACTION_DRAG_LOCATION: // 被拖拽View在目标区域移动
                        View onTopOf = rvLeft.findChildViewUnder(event.getX(), event.getY());
                        int drag = rvLeft.getChildAdapterPosition(onTopOf);
                        mLeftAdapter.updateDrag(drag);
                        Log.e("目标区域移动", "" + drag);
                        return true;
                    case DragEvent.ACTION_DRAG_EXITED: // 被拖拽View离开目标区域
                        return true;
                    case DragEvent.ACTION_DROP: // 放开被拖拽View

                        View underView = rvLeft.findChildViewUnder(event.getX(), event.getY());
                        int underPos = rvLeft.getChildAdapterPosition(underView);
                        if (underPos < 0) return true;
                        Log.e("放开被拖拽View", "" + underPos);
                        mRight.remove(mEntities.get(pos));
                        mEntities.remove(pos);
                        mRightAdapter.update(mEntities);
                        mLeft.get(underPos).setCount(mLeft.get(underPos).getCount() + 1);
                        mLeftAdapter.update(mLeft);
                        String content = event.getClipData().getItemAt(0).getText().toString(); //接收数据
                        Toast.makeText(MainActivity.this, content, Toast.LENGTH_SHORT).show();
                        return true;
                    case DragEvent.ACTION_DRAG_ENDED: // 拖拽完成
                        mLeftAdapter.updateDrag(-1);
                        return true;
                }
                return false;
            }

        });
    }

    List<LeftEntity> mEntities = new ArrayList<>();
    int pos, clickPos;

    @Override
    public void setOnItemClickListener(View view, int postion) {
        switch (view.getId()) {
            case R.id.rl_left:
                clickPos = postion;
                mEntities.clear();
                for (LeftEntity entity : mRight) {
                    if (entity.getId() == mLeft.get(postion).getId()) {
                        mEntities.add(entity);
                    }
                }
                mRightAdapter.update(mEntities);
                break;
            case R.id.rl_right:
                break;
        }
    }

    @Override
    public void setOnItemLongClickListener(final RecyclerView.ViewHolder viewHolder, int postion) {
        if (viewHolder.itemView.getId() == R.id.rl_right) {
            pos = postion;
            ClipData.Item item = new ClipData.Item(mEntities.get(postion).getName());  //传过去的数据
            ClipData data = new ClipData(null, new String[]{ClipDescription.MIMETYPE_TEXT_PLAIN}, item);
            viewHolder.itemView.startDrag(data, new View.DragShadowBuilder(viewHolder.itemView), null, 0);
            //获取系统震动服务
            vib.vibrate(70);
        }
    }

}
