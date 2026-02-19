package cc.runyan.techo.frag_record;

import static cc.runyan.techo.db.DBManager.getListByKind;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import cc.runyan.techo.R;
import cc.runyan.techo.adapter.TypeBeanAdapter;
import cc.runyan.techo.constant.Constants;
import cc.runyan.techo.db.DBManager;
import cc.runyan.techo.po.RecordBean;
import cc.runyan.techo.po.TypeBean;
import cc.runyan.techo.utils.KeyBoardUtils;
import cc.runyan.techo.utils.RemarkDialog;
import cc.runyan.techo.utils.SelectTimeDialog;

public abstract class BaseRecordFragment extends Fragment implements View.OnClickListener {

    // 键盘
    private GridLayout keyboard;

    // 表示金额的 EditText
    private EditText moneyEdit;

    //
    private ImageView imageView;

    private TextView typeTv, remarkTv, timeTv;

    // 表示选项
    private GridView gridView;

    private TypeBeanAdapter adapter;

    private List<TypeBean> typeBeanList;


    private RecordBean recordBean;

    /**
     * 子类实现此方法，返回类型的 kind 值
     *
     * @return Constants.OUTCOME_KIND 或 Constants.INCOME_KIND
     */
    protected abstract int getKind();

    public BaseRecordFragment() {
        // Required empty public constructor
    }

    // onCreate 进行数据初始化
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // 将 xml 文件转换为 view对象
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment, 和xml文件设置关联
        return inflater.inflate(R.layout.fragment_out_come, container, false);
    }

    // onViewCreated 操作组件进行初始化
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        // 增加 keyboard监听器
        initKeyBoard(view);
        // 初始化界面 view
        initView(view);
        // 添加元素到 gv 中
        loadDataToGv();
        // 设置 gv 监听器
        setGVListener();
        // 设置系统时间 yyyy年mm月dd日 hh:mm
        String time = getTime();
        timeTv.setText(time);

        // 初始化顶部类型
        if (getKind() == Constants.INCOME_KIND) {
            imageView.setImageResource(R.mipmap.in_qt_fs);
        }

        initRecordBean(time);
    }


    /**
     * 获取 yyyyy年MM月dd日 HH:mm 格式时间
     *
     * @return
     */
    private static String getTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm");
        return now.format(formatter);
    }

    private void initRecordBean(String time) {
        // 初始化 recordBean 对象
        this.recordBean = new RecordBean();
        recordBean.setKind(getKind());
        recordBean.setTime(time);
        recordBean.setTypename("其他");
        recordBean.setsImageId(getKind() == Constants.OUTCOME_KIND ? R.mipmap.ic_qita_fs : R.mipmap.ic_qita);
    }

    private void initKeyBoard(@NonNull View view) {
        new KeyBoardUtils(view.findViewById(R.id.frag_out_keyboard), view.findViewById(R.id.frag_out_money),
                () -> {
                    String money = moneyEdit.getEditableText().toString();
                    if (money.isEmpty()) {
                        Toast.makeText(getActivity(), "请输入数字", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (money.equals("0")) {
                        Toast.makeText(getActivity(), "金额不能为0", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    // 设置金额
                    recordBean.setMoney(Float.parseFloat(money));
                    // 设置备注
                    String remark = (String) remarkTv.getText();
                    if (!remark.equals("添加备注")) {
                        recordBean.setRemark(remark);
                    }
                    insertIntoDB();
                    this.getActivity().finish();
                });
    }

    public RecordBean getRecordBean() {
        return this.recordBean;
    }

    private void loadDataToGv() {

        typeBeanList = getListByKind(getKind());

        adapter = new TypeBeanAdapter(getContext(), typeBeanList);

        gridView.setAdapter(adapter);
    }

    private void initView(View view) {
        keyboard = view.findViewById(R.id.frag_out_keyboard);
        timeTv = view.findViewById(R.id.frag_out_time);
        remarkTv = view.findViewById(R.id.frag_out_remark);
        moneyEdit = view.findViewById(R.id.frag_out_money);
        gridView = view.findViewById(R.id.frag_out_gv);
        imageView = view.findViewById(R.id.frag_out_iv);
        typeTv = view.findViewById(R.id.frag_out_type);

        timeTv.setOnClickListener(this);
        remarkTv.setOnClickListener(this);


    }

    private void setGVListener() {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.setSelectPosition(position);
                adapter.notifyDataSetInvalidated();

                // 设置顶部 view内容改变
                TypeBean typeBean = typeBeanList.get(position);
                imageView.setImageResource(typeBean.getSImageId());
                typeTv.setText(typeBean.getTypename());

                recordBean.setTypename(typeBean.getTypename());
                recordBean.setsImageId(typeBean.getSImageId());
            }
        });
    }

    private void insertIntoDB() {
        DBManager.insertIntoRecord(this.recordBean);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.frag_out_time) {// 点击时间
            SelectTimeDialog selectTimeDialog = new SelectTimeDialog(getContext());

            // 设置时间选择的监听器
            selectTimeDialog.setOnEnsureListener((String time) -> {
                this.recordBean.setTime(time);
                this.timeTv.setText(time);
                Log.d("techo", "时间为" + time);
            });

            selectTimeDialog.show();
        } else if (v.getId() == R.id.frag_out_remark) { // 点击备注
            final RemarkDialog remarkDialog = new RemarkDialog(getContext());
            remarkDialog.show();
            remarkDialog.setDialogSize();
            remarkDialog.setOnEnsureListener(new RemarkDialog.OnEnsureListener() {
                @Override
                public void onEnsure() {
                    String remark = remarkDialog.getRemark();
                    if (!remark.isEmpty()) {
                        recordBean.setRemark(remark);
                        remarkTv.setText(remark);
                    }
                    remarkDialog.cancel();
                }
            });
        }
    }
}
