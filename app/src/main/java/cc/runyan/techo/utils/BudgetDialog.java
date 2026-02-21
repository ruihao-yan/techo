package cc.runyan.techo.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import cc.runyan.techo.R;
import cc.runyan.techo.db.DBManager;
import cc.runyan.techo.po.Budget;

public class BudgetDialog extends Dialog implements View.OnClickListener {

    ImageView cancelTv;

    Button ensueBtn;

    EditText editText;


    public BudgetDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.dialog_budget);

        cancelTv = findViewById(R.id.dialog_budget_exit);

        ensueBtn = findViewById(R.id.dialog_budget_ensure);

        editText = findViewById(R.id.dialog_budget_et);


        ensueBtn.setOnClickListener(this);
        cancelTv.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == ensueBtn.getId()) {
            String budget = editText.getText().toString();

            if (budget.isEmpty()) {
                Toast.makeText(getContext(), "输入数据不能为空", Toast.LENGTH_SHORT).show();
                return;
            }


            float budgetFloat;
            try {
                budgetFloat = Float.parseFloat(budget);
            } catch (NumberFormatException e) {
                Toast.makeText(getContext(), "请输入正确格式的预算", Toast.LENGTH_SHORT).show();
                return;
            }
            Budget budgetObj = new Budget();
            budgetObj.setBudget(budgetFloat);
            budgetObj.setDay(Time.getMonthDay());
            budgetObj.setMonth(Time.getCurMonth());
            budgetObj.setYear(Time.getCurYear());
            DBManager.insertIntoBudget(budgetObj);
            this.cancel();
        } else if (v.getId() == cancelTv.getId()) {
            this.cancel();
        }
    }
}
