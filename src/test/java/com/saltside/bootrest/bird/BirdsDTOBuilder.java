package com.saltside.bootrest.bird;

import com.saltside.bootrest.bird.BirdsDTO;

/**
 * @author Petri Kainulainen
 */
class BirdsDTOBuilder {

    private String name;
    private String id;
    private String family;

    BirdsDTOBuilder() {

    }

    BirdsDTOBuilder name(String name) {
        this.name = name;
        return this;
    }

    BirdsDTOBuilder id(String id) {
        this.id = id;
        return this;
    }

    BirdsDTOBuilder family(String family) {
        this.family = family;
        return this;
    }

    BirdsDTO build() {
        BirdsDTO dto = new BirdsDTO();

        dto.setName(name);
        dto.setId(id);
        dto.setFamily(family);

        return dto;
    }
}
