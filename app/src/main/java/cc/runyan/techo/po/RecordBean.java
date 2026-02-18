package cc.runyan.techo.po;

public class RecordBean {
    private int id;
    // 类型名称
    private String typename;
    // 备注
    private String remark;
    // 被选中图像
    private int sImageId;
    // 金额
    private float money;
    // 时间
    private String time;
    // 类型
    private int kind;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getsImageId() {
        return sImageId;
    }

    public void setsImageId(int sImageId) {
        this.sImageId = sImageId;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getKind() {
        return kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }

    public RecordBean() {
    }

    public RecordBean(int id, String typename, String remark, int sImageId, float money, String time, int kind) {
        this.id = id;
        this.typename = typename;
        this.remark = remark;
        this.sImageId = sImageId;
        this.money = money;
        this.time = time;
        this.kind = kind;
    }


    @Override
    public String toString() {
        return "RecordBean{" +
                "id=" + id +
                ", typename='" + typename + '\'' +
                ", remark='" + remark + '\'' +
                ", sImageId=" + sImageId +
                ", money=" + money +
                ", time='" + time + '\'' +
                ", kind=" + kind +
                '}';
    }
}
