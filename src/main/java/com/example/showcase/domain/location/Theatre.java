package com.example.showcase.domain.location;

import com.example.showcase.domain.common.BaseEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "theatres")
public class Theatre extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    private City city;

    protected Theatre(){}

    public Theatre(String name, City city){
        this.name = name;
        this.city = city;
    }

    public String getName(){
        return name;
    }

    public City getCity(){
        return city;
    }
}
