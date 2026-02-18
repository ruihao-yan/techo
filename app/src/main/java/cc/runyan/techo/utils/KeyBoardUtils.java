package cc.runyan.techo.utils;

import android.text.Editable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridLayout;

public class KeyBoardUtils implements View.OnClickListener {


    private GridLayout gridLayout;

    private EditText editText;

    public interface ClickListener {
        void onClick();
    }

    private ClickListener clickListener;

    public KeyBoardUtils(GridLayout gridLayout, EditText editText, ClickListener clickListener) {
        this.gridLayout = gridLayout;
        this.editText = editText;
        this.clickListener = clickListener;
        initKeyBoard();
    }

    private void initKeyBoard() {
        // 屏蔽系统键盘
        editText.setShowSoftInputOnFocus(false);
        if (gridLayout != null) {
            ViewGroup viewGroup = gridLayout;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                viewGroup.getChildAt(i).setOnClickListener(this);
            }
        }
    }


    @Override
    public void onClick(View v) {

        Object tagObj = v.getTag();
        if (tagObj == null) return;

        int tag = Integer.parseInt(tagObj.toString());
        Editable editable = editText.getText();
        int cursorPos = editText.getSelectionStart();

        switch (tag) {

            case -5:  // 删除
                if (editable != null && editable.length() > 0 && cursorPos > 0) {
                    editable.delete(cursorPos - 1, cursorPos);
                }
                break;

            case -3:  // 清零
                editText.setText("");
                break;

            case -4:  // 确定
                if (clickListener != null) {
                    clickListener.onClick();
                }
                break;

            case 46:  // 小数点 "."
                String currentText = editable.toString();
                if (!currentText.contains(".")) {
                    if (currentText.isEmpty()) {
                        editable.insert(cursorPos, "0.");
                    } else {
                        editable.insert(cursorPos, ".");
                    }
                }
                break;

            default:  // 数字输入
                editable.insert(cursorPos, String.valueOf((char) tag));
                break;
        }
    }


    public void showKeyBoard() {
        int visibility = gridLayout.getVisibility();
        if (visibility == View.INVISIBLE || visibility == View.GONE) {
            gridLayout.setVisibility(View.VISIBLE);
        }
    }

    public void hideKeyBoard() {
        int visibility = gridLayout.getVisibility();
        if (visibility == View.VISIBLE) {
            gridLayout.setVisibility(View.INVISIBLE);
        }
    }
}
