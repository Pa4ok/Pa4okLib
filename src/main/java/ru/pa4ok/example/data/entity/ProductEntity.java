package ru.pa4ok.example.data.entity;

/*
    Класс описывающий сущность услуги
 */
public class ProductEntity
{
    private int id;
    private String title;
    private double cost;
    private String description;
    private String imgPath;
    private boolean active;
    private String manufacturer;

    public ProductEntity(int id, String title, double cost, String description, String imgPath, int active, String manufacturer) {
        this.id = id;
        this.title = title;
        this.cost = cost;
        this.description = description;
        this.imgPath = imgPath;
        if(active == 1) {
            this.active = true;
        } else {
            this.active = false;
        }
        this.manufacturer = manufacturer;
    }

    @Override
    public String toString() {
        return "ProductEntity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", cost=" + cost +
                ", description='" + description + '\'' +
                ", imgPath='" + imgPath + '\'' +
                ", active=" + active +
                ", manufacturerId=" + manufacturer +
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

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }
}
