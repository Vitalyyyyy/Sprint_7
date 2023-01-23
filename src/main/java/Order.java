public class Order {private String firstName; // Имя заказчика, записывается в поле firstName таблицы Orders
    private String lastName; // Фамилия заказчика, записывается в поле lastName таблицы Orders
    private String address; // Адрес заказчика, записывается в поле address таблицы Orders
    private String metroStation; // Ближайшая к заказчику станция метро, записывается в поле metroStation таблицы Orders
    private String phone; // Телефон заказчика, записывается в поле phone таблицы Orders
    private int rentTime; // Количество дней аренды, записывается в поле rentTime таблицы Orders
    private String deliveryDate; // Дата доставки, записывается в поле deliveryDate таблицы Orders
    private String comment; // Комментарий от заказчика, записывается в поле comment таблицы Orders
    private String[] color; // Предпочитаемые цвета, записываются в поле color таблицы Orders

    public Order(String[] color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    public String getAddress() {
        return address;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMetroStation() {
        return metroStation;
    }

    public void setMetroStation(String metroStation) {
        this.metroStation = metroStation;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getRentTime() {
        return rentTime;
    }

    public void setRentTime(int rentTime) {
        this.rentTime = rentTime;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String[] getColor() {
        return color;
    }

    public void setColor(String[] color) {
        this.color = color;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
