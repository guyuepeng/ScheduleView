package gg.scheduleview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import gg.bean.CourseBean;
import gg.view.ScheduleView;

public class MainActivity extends AppCompatActivity {

    private ScheduleView mScheduleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        mScheduleView= (ScheduleView) findViewById(R.id.sv);

        List<CourseBean> mList = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            mList.add(new CourseBean());
        }
        mScheduleView.fillData(new int[]{2016,11,29},5,mList);
    }
}
