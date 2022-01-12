package com.reinstein.heberth.address;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String streetName;

    @Column(nullable = false)
    private String number;

    private String complement;

    @Column(nullable = false)
    private String neighbourhood;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String zipcode;

    private String longitude;

    private String latitude;

    @Override
    public String toString() {
        return complement + ", "
                + number + ", "
                + streetName + ", "
                + neighbourhood + ", "
                + city + ", "
                + state + ", "
                + country + ", "
                + zipcode;
    }
}
