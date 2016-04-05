package com.saltside.bootrest.bird;

import org.springframework.test.util.ReflectionTestUtils;

import com.saltside.bootrest.model.Birds;

/**
 * @author Petri Kainulainen
 */
class BirdsBuilder {

    private String name;
    private String id;
    private String family = "NOT_IMPORTANT";

    BirdsBuilder() {

    }

    BirdsBuilder name(String name) {
        this.name = name;
        return this;
    }

    BirdsBuilder id(String id) {
        this.id = id;
        return this;
    }

    BirdsBuilder family(String family) {
        this.family = family;
        return this;
    }

    Birds build() {
        Birds birds = Birds.getBuilder()
                .family(family)
                .name(name)
                .id(id)
                .build();

        ReflectionTestUtils.setField(birds, "id", id);

        return birds;
    }
}
