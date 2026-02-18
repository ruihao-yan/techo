package cc.runyan.techo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.applySystemBarInsets(findViewById(R.id.activity_main_root));


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
}