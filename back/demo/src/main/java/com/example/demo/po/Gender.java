package com.example.demo.po;

import lombok.Getter;

@Getter
public enum Gender {
    male("male",1), female("female",2 ),other("other",3);

    private final Integer index;
    Gender(String name, Integer idx) {
        this.index = idx;
    }

    public String getName() {
        return this.name();
    }

}
