package com.example.showcase.domain.location;

import com.example.showcase.domain.common.BaseEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "cities")
public class City extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String name;

    protected City(){}

    public City(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
