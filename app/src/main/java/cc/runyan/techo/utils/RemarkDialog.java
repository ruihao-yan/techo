package cc.runyan.techo.utils;

import static android.view.inputmethod.InputMethodManager.HIDE_NOT_ALWAYS;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;

import cc.runyan.techo.R;

public class RemarkDialog extends Dialog implements View.OnClickListener {

    private EditText editText;

    private Button cancelBtn, ensureBtn;

    public RemarkDialog(@NonNull Context context) {
        super(context);

        setContentView(R.layout.dialog_remark);
        editText = findViewById(R.id.dialog_remark_et);
        cancelBtn = findViewById(R.id.dialog_remark_cancel);
        ensureBtn = findViewById(R.id.dialog_remark_ensure);
        cancelBtn.setOnClickListener(this);
        ensureBtn.setOnClickListener(this);
    }

    private OnEnsureListener onEnsureListener;

    public interface OnEnsureListener {
        void onEnsure();
    }

    public void setOnEnsureListener(OnEnsureListener onEnsureListener) {
        this.onEnsureListener = onEnsureListener;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.dialog_remark_cancel) {
            cancel();
        } else if (v.getId() == R.id.dialog_remark_ensure) {
            onEnsureListener.onEnsure();
        }
    }

    public String getRemark() {
        return editText.getText().toString().trim();
    }

    /**
     * 设置 dialog 宽度与屏幕一致，并在显示时自动弹出键盘
     */
    public void setDialogSize() {
        // 获取 Dialog 的 Window
        Window window = getWindow();
        if (window != null) {
            // 获取屏幕宽度
            WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
            Display display = windowManager.getDefaultDisplay();

            // 设置 Dialog 宽度为屏幕宽度
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.width = display.getWidth();
            window.setAttributes(layoutParams);
            handler.sendEmptyMessageDelayed(1, 100);
        }
    }

    Handler handler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == 1) {
                // 自动弹出键盘
                InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.toggleSoftInput(0, HIDE_NOT_ALWAYS);
            }
        }
    };

}
