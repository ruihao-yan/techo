package cc.runyan.techo.po;

public class TypeBean {
    private int id;
    private int kind; // 1表示收入， 0表示支出

    private String typename; // 表示类型名称

    private int imageId; // 未被选中图片id

    private int sImageId; // 被选中图片id

    public TypeBean(int id, int kind, String typename, int imageId, int simageId) {
        this.id = id;
        this.kind = kind;
        this.typename = typename;
        this.imageId = imageId;
        this.sImageId = simageId;
    }

    public TypeBean() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getKind() {
        return kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public int getSImageId() {
        return sImageId;
    }

    public void setSImageId(int sImageId) {
        this.sImageId = sImageId;
    }}
