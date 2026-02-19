package cc.runyan.techo.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import cc.runyan.techo.R;
import cc.runyan.techo.po.RecordBean;

public class RecordAdapter extends BaseAdapter {

    private Context context;
    private List<RecordBean> recordBeanList;
    private LayoutInflater inflater;

    public RecordAdapter(Context context, List<RecordBean> recordBeanList) {
        this.context = context;
        this.recordBeanList = recordBeanList;
        this.inflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return recordBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return recordBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_mainlv, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        RecordBean recordBean = recordBeanList.get(position);
        viewHolder.setText(viewHolder.imageView.getId(), String.valueOf(recordBean.getsImageId()));
        viewHolder.setText(viewHolder.moneyView.getId(), String.valueOf(recordBean.getMoney()));
        viewHolder.setText(viewHolder.remarkView.getId(), recordBean.getRemark());
        viewHolder.setText(viewHolder.timeView.getId(), recordBean.getTime());
        viewHolder.setText(viewHolder.typeView.getId(), recordBean.getTypename());
        return convertView;
    }


    private class ViewHolder {
        private ImageView imageView;
        private TextView typeView, remarkView, timeView, moneyView;


        public ViewHolder(View v) {
            imageView = v.findViewById(R.id.item_mainlv_iv);
            typeView = v.findViewById(R.id.item_mainlv_title);
            remarkView = v.findViewById(R.id.item_mainlv_remark);
            moneyView = v.findViewById(R.id.item_mainlv_money);
            timeView = v.findViewById(R.id.item_mainlv_time);
            v.findViewById(R.id.item_mainlv_time);
        }

        public void setText(int id, String text) {
            if (id == imageView.getId()) {
                imageView.setImageResource(Integer.parseInt(text));
            } else if (id == typeView.getId()) {
                typeView.setText(text);
            } else if (id == remarkView.getId()) {
                remarkView.setText(text);
            } else if (id == timeView.getId()) {
                timeView.setText(formatTime(text));
            } else if (id == moneyView.getId()) {
                moneyView.setText("￥ " + text);
            } else {
                Log.e("RecordAdapter", "不正确的 id");
                return;
            }
        }

        private String formatTime(String time) {
            // time 格式: yyyy年MM月dd日 HH:mm
            String datePart = time.substring(0, time.indexOf("日") + 1); // yyyy年MM月dd日
            String timePart = time.substring(time.indexOf("日") + 1);   //  HH:mm
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日");
            LocalDate recordDate = LocalDate.parse(datePart, formatter);
            LocalDate today = LocalDate.now();
            if (recordDate.equals(today)) {
                return "今天" + timePart;
            } else if (recordDate.equals(today.minusDays(1))) {
                return "昨天" + timePart;
            }
            return time;
        }
    }
}
