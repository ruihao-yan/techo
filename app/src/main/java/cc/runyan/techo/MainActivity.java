package cc.runyan.techo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cc.runyan.techo.adapter.RecordAdapter;
import cc.runyan.techo.constant.Constants;
import cc.runyan.techo.db.DBManager;
import cc.runyan.techo.dto.AmountCome;
import cc.runyan.techo.po.RecordBean;
import cc.runyan.techo.utils.Time;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    // activity 生命周期
    // onCreate  创建 Activity, 加载布局
    // onStart   设置监听器
    // onResume  进入前台，可点击
    // onPause   dialog弹出
    // onStop    activity 销毁

    private ListView listView;
    private int year, month, day;
    private List<RecordBean> recordBeanList;
    private RecordAdapter recordAdapter;
    private View headerView;

    private TextView topOutTv, topInTv, topBudgetTv, topGraph, topDayInAndOut;
    private ImageView topShowIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 初始化时间
        initTime();
        // 设置系统顶部状态栏
        this.applySystemBarInsets(findViewById(R.id.activity_main_root));
        // 设置 listView 适配器
        recordBeanList = DBManager.getRecords(year, month, day);
        recordAdapter = new RecordAdapter(this, recordBeanList);
        listView = findViewById(R.id.main_lv);
        listView.setAdapter(recordAdapter);

        // 设置长按删除回调
        recordAdapter.setOnDeleteButtonListener(id -> {
            DBManager.deleteRecord(id);
            loadDate();
            initOrUpdateInAndOutText();
        });

        // 设置 头部的header
        addHeaderView();

        initOrUpdateInAndOutText();

    }

    /**
     * 初始化或者更新收入和支出
     */
    private void initOrUpdateInAndOutText() {
        // 获取当前月份的支出和收入金额
        AmountCome allInAndOutByMonth = DBManager.getAllAmountByTime(Time.getCurYear(), Time.getCurMonth(), null);

        topOutTv.setText("￥" + allInAndOutByMonth.getOutCome());
        topInTv.setText("￥" + allInAndOutByMonth.getInCome());


        // 获取当日的支出和收入金额
        float dayOut = 0;
        float dayIn = 0;
        int curDay = Time.getMonthDay();
        Pattern pattern = Pattern.compile("月(\\d+)日");
        for (RecordBean recordBean : allInAndOutByMonth.getRecordBeanList()) {
            String time = recordBean.getTime();
            Matcher matcher = pattern.matcher(time);
            if (matcher.find()) {
                int recordDay = Integer.parseInt(matcher.group(1));
                if (recordDay == curDay) {
                    if (recordBean.getKind() == Constants.OUTCOME_KIND) {
                        dayOut += recordBean.getMoney();
                    } else {
                        dayIn += recordBean.getMoney();
                    }
                }
            }
        }

        topDayInAndOut.setText("今日支出 ￥" + dayOut + "  收入 ￥" + dayIn);
    }


    public void addHeaderView() {
        headerView = getLayoutInflater().inflate(R.layout.item_mainlv_top, null);

        listView.addHeaderView(headerView);
        topOutTv = headerView.findViewById(R.id.item_mainlv_top_expense);
        topInTv = headerView.findViewById(R.id.item_mainlv_top_income);
        topBudgetTv = headerView.findViewById(R.id.item_mainlv_top_budget);
        topGraph = headerView.findViewById(R.id.item_mainlv_graph);
        topShowIv = headerView.findViewById(R.id.item_mainlv_top_show);

        topDayInAndOut = headerView.findViewById(R.id.item_mainlv_in_out);

        topShowIv.setOnClickListener(this);
        topBudgetTv.setOnClickListener(this);

    }

    public void toRecord(View v) {
        int id = v.getId();
        if (id == R.id.activity_main_to_record) { // 跳转到记一笔
            Intent intent = new Intent(this, RecordActivity.class); //
            startActivity(intent);
        } else if (id == R.id.main_morebtn) { // 跳转更多
            // TODO
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

        loadDate();
        initOrUpdateInAndOutText();
    }

    private void loadDate() {
        //  recordBeanList = DBManager.getRecords(year, month, day);  // ← 创建了一个新的 List 对象，导致适配器还是引用旧的对象
        recordBeanList.clear();
        recordBeanList.addAll(DBManager.getRecords(year, month, day));
        recordAdapter.notifyDataSetChanged();
    }


    private void initTime() {
        Calendar instance = Calendar.getInstance();
        year = instance.get(Calendar.YEAR);
        month = instance.get(Calendar.MONTH) + 1;
        day = instance.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.item_mainlv_top_budget) { // 点击预算


        } else if (v.getId() == R.id.item_mainlv_top_show) { // 是否显示 消费余额

        }
    }
}