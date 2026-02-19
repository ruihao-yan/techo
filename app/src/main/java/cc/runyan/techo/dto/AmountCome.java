package cc.runyan.techo.dto;

import java.util.List;

import cc.runyan.techo.po.RecordBean;

public class AmountCome {
    private float inCome;
    private float outCome;

    private List<RecordBean> recordBeanList;

    public List<RecordBean> getRecordBeanList() {
        return recordBeanList;
    }

    public void setRecordBeanList(List<RecordBean> recordBeanList) {
        this.recordBeanList = recordBeanList;
    }

    public float getInCome() {
        return inCome;
    }

    public void setInCome(float inCome) {
        this.inCome = inCome;
    }

    public float getOutCome() {
        return outCome;
    }

    public void setOutCome(float outCome) {
        this.outCome = outCome;
    }
}
