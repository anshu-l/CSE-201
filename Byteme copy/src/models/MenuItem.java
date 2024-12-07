package models;

public class MenuItem {
    private String name;
    private double price;
    private String category;
    private boolean isAvailable;

    public MenuItem(String name, double price, String category, boolean isAvailable) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.isAvailable = isAvailable;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @Override
    public String toString() {
        return name + " (₹" + price + ", " + category + ", Available: " + isAvailable + ")";
    }
}
