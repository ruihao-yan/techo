package cc.runyan.techo;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = getWindow();

        // ① 允许内容延伸到系统栏区域（Edge-to-edge）
        WindowCompat.setDecorFitsSystemWindows(window, false);

        // ② 让状态栏透明（可选，但通常配合 edge-to-edge 使用）
        window.setStatusBarColor(Color.TRANSPARENT);

        // ③ 状态栏图标/文字变黑（浅色状态栏）
        View decorView = window.getDecorView();
        WindowInsetsControllerCompat controller =
                WindowCompat.getInsetsController(window, decorView);
        controller.setAppearanceLightStatusBars(true); // true = 黑色图标；false = 白色图标
    }

    /**
     * 在 setContentView 之后调用，让根布局自动“退让”状态栏/导航栏高度
     *
     * @param root 你的页面根 View（通常是 content 的根布局）
     */
    protected void applySystemBarInsets(View root) {
        ViewCompat.setOnApplyWindowInsetsListener(root, (v, insets) -> {
            Insets sysBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            // 给内容加 padding，避免被状态栏/导航栏覆盖
            v.setPadding(sysBars.left, sysBars.top, sysBars.right, sysBars.bottom);
            return insets;
        });
        // 触发一次 inset 分发
        ViewCompat.requestApplyInsets(root);
    }

}
