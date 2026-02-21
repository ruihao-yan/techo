package cc.runyan.techo.po;

public class Budget {
    private float budget;

    private int id;

    private int year;
    private int month;

    private int day;

    public float getBudget() {
        return budget;
    }

    public void setBudget(float budget) {
        this.budget = budget;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    @Override
    public String toString() {
        return "Budget{" +
                "budget=" + budget +
                ", id=" + id +
                ", year=" + year +
                ", month=" + month +
                ", day=" + day +
                '}';
    }
}
