package cc.runyan.techo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cc.runyan.techo.R;
import cc.runyan.techo.po.TypeBean;

/**
 * girdView 中设置view
 */
public class TypeBeanAdapter extends BaseAdapter {
    public TypeBeanAdapter(Context context, List<TypeBean> typeBeanList) {
        this.context = context;
        this.typeBeanList = typeBeanList;
    }

    public void setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
    }

    private Context context;
    private List<TypeBean> typeBeanList;

    private int selectPosition = 0;

    @Override
    public int getCount() {
        return typeBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return typeBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_recordfrag_gv, parent, false);
        }

        ImageView iv = convertView.findViewById(R.id.item_recordfrag_gv_iv);
        TextView tv = convertView.findViewById(R.id.item_recordfrag_gv_tv);

        TypeBean typeBean = typeBeanList.get(position);
        tv.setText(typeBean.getTypename());
        if (selectPosition == position) { // 如果被选中
            iv.setImageResource(typeBean.getSImageId());
        } else {
            iv.setImageResource(typeBean.getImageId());
        }

        return convertView;
    }
}
