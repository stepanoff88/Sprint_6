package ru.practicum.scooter.testdata;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public final class OrderData {

    private final String firstName;
    private final String lastName;
    private final String address;
    private final String metroStation;
    private final String phone;
    private final LocalDate deliveryDate;
    private final String rentalPeriod;
    private final ScooterColor color;
    private final String comment;

    public OrderData(String firstName, String lastName, String address, String metroStation, String phone,
                     LocalDate deliveryDate, String rentalPeriod, ScooterColor color, String comment) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.deliveryDate = deliveryDate;
        this.rentalPeriod = rentalPeriod;
        this.color = color;
        this.comment = comment;
    }

    public static List<OrderData> testData() {
        LocalDate today = LocalDate.now();
        return Arrays.asList(
                new OrderData(
                        "Иван",
                        "Петров",
                        "Москва, ул. Ленина, 1",
                        "Сокольники",
                        "+79991234567",
                        today.plusDays(1),
                        "сутки",
                        ScooterColor.BLACK,
                        "Позвоните за час до доставки"
                ),
                new OrderData(
                        "Анна",
                        "Сидорова",
                        "Москва, проспект Мира, 10",
                        "Театральная",
                        "+79997654321",
                        today.plusDays(12),
                        "двое суток",
                        ScooterColor.GREY,
                        ""
                )
        );
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getMetroStation() {
        return metroStation;
    }

    public String getPhone() {
        return phone;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public String getRentalPeriod() {
        return rentalPeriod;
    }

    public ScooterColor getColor() {
        return color;
    }

    public String getComment() {
        return comment;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + ", " + metroStation + ", " + deliveryDate
                + ", " + rentalPeriod + ", " + color.getLabel();
    }
}
