package cc.runyan.techo.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import cc.runyan.techo.R;
import cc.runyan.techo.exceptions.InvalidArgumentException;

public class SelectTimeDialog extends Dialog implements View.OnClickListener {


    private EditText hourText, minText;

    private Button ensureBtn, cancelBtn;

    private DatePicker datePicker;

    private OnEnsureListener onEnsureListener;


    public SelectTimeDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.dialog_time_ensure) {
            int year = datePicker.getYear();
            int month = datePicker.getMonth() + 1; // getMonth() 返回 0-11，需要 +1
            int day = datePicker.getDayOfMonth();
            String hour = hourText.getText().toString();
            String mine = minText.getText().toString();
            try {
                checkHourAndMine(hour, mine);
            } catch (RuntimeException e) {
                return;
            }
            String time = formatTime(String.valueOf(year), String.valueOf(month), String.valueOf(day), hour, mine);
            Log.d("SelectTimeDialog", "时间为" + time);
            onEnsureListener.onEnsure(time);
            this.cancel();
        } else if (v.getId() == R.id.dialog_time_cancel) {
            this.cancel();
        }
    }

    private void checkHourAndMine(String hour, String mine) {
        try {
            int hourInteger = Integer.parseInt(hour);
            int mineInteger = Integer.parseInt(mine);

            if (hourInteger < 0 || hourInteger >= 24 || mineInteger < 0 || mineInteger >= 60) {
                throw new InvalidArgumentException("不正确的时间输入");
            }

        } catch (NumberFormatException numberFormatException) { // 无法转换为整数
            Toast.makeText(getContext(), "请输入正确格式的时间", Toast.LENGTH_SHORT).show();
            throw new RuntimeException(numberFormatException.getMessage());
        } catch (InvalidArgumentException invalidArgumentException) { // 不是正确的小时和分钟格式
            Toast.makeText(getContext(), "请输入正确的小时和分钟格式", Toast.LENGTH_SHORT).show();
            throw new RuntimeException(invalidArgumentException.getMessage());
        }
    }

    private String formatTime(String year, String month, String day, String hour, String min) {
        if (month.length() == 1) {
            month = "0" + month;
        }
        if (day.length() == 1) {
            day = "0" + day;
        }

        return year +
                "年" +
                month +
                "月" +
                day +
                "日" +
                " " +
                formatHourAndMine(hour) +
                ":" +
                formatHourAndMine(min);
    }

    private String formatHourAndMine(String hOM) {
        if (hOM.isEmpty()) {
            return "00";
        } else if (hOM.length() == 1) {
            return "0" + hOM;
        } else {
            return hOM;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_time);
        ensureBtn = findViewById(R.id.dialog_time_ensure);
        cancelBtn = findViewById(R.id.dialog_time_cancel);
        datePicker = findViewById(R.id.dialog_time_dp);
        hourText = findViewById(R.id.dialog_time_hour);
        minText = findViewById(R.id.dialog_time_min);
        ensureBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);
        hideDatePickerHeader();
    }


    private void hideDatePickerHeader() {
        ViewGroup rootView = (ViewGroup) datePicker.getChildAt(0);
        if (rootView == null) {
            return;
        }
        View headerView = rootView.getChildAt(0);
        if (headerView == null) {
            return;
        }
        //5.0+
        int headerId = getContext().getResources().getIdentifier("day_picker_selector_layout", "id", "android");
        if (headerId == headerView.getId()) {
            headerView.setVisibility(View.GONE);

            ViewGroup.LayoutParams layoutParamsRoot = rootView.getLayoutParams();
            layoutParamsRoot.width = ViewGroup.LayoutParams.WRAP_CONTENT;
            rootView.setLayoutParams(layoutParamsRoot);

            ViewGroup animator = (ViewGroup) rootView.getChildAt(1);
            ViewGroup.LayoutParams layoutParamsAnimator = animator.getLayoutParams();
            layoutParamsAnimator.width = ViewGroup.LayoutParams.WRAP_CONTENT;
            animator.setLayoutParams(layoutParamsAnimator);

            View child = animator.getChildAt(0);
            ViewGroup.LayoutParams layoutParamsChild = child.getLayoutParams();
            layoutParamsChild.width = ViewGroup.LayoutParams.WRAP_CONTENT;
            child.setLayoutParams(layoutParamsChild);
            return;
        }
        //6.0+
        headerId = getContext().getResources().getIdentifier("date_picker_header", "id", "android");
        if (headerId == headerView.getId()) {
            headerView.setVisibility(View.GONE);
        }
    }

    public void setOnEnsureListener(OnEnsureListener onEnsureListener) {
        this.onEnsureListener = onEnsureListener;
    }

    public interface OnEnsureListener {
        void onEnsure(String time);
    }
}
