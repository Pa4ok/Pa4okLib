package ru.pa4ok.dbusing.data.entity;

public class SlotEntity
{
    private int id;
    private String title;
    private int price;

    public SlotEntity(int id, String title, int price) {
        this.id = id;
        this.title = title;
        this.price = price;
    }

    public SlotEntity(String title, int price) {
        this(-1, title, price);
    }

    @Override
    public String toString() {
        return "SlotEntity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", price=" + price +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
