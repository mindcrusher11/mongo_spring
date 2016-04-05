package com.saltside.bootrest.bird;

import org.assertj.core.api.AbstractAssert;

import com.saltside.bootrest.bird.BirdsDTO;

import static org.assertj.core.api.Assertions.assertThat;

class BirdsDTOAssert extends AbstractAssert<BirdsDTOAssert, BirdsDTO> {

    private BirdsDTOAssert(BirdsDTO actual) {
        super(actual, BirdsDTOAssert.class);
    }

    static BirdsDTOAssert assertThatBirdsDTO(BirdsDTO actual) {
        return new BirdsDTOAssert(actual);
    }

    public BirdsDTOAssert hasName(String expectedName) {
        isNotNull();

        String actualName = actual.getName();
        assertThat(actualName)
                .overridingErrorMessage("Expected Name to be <%s> but was <%s>",
                        expectedName,
                        actualName
                )
                .isEqualTo(expectedName);

        return this;
    }

    public BirdsDTOAssert hasId(String expectedId) {
        isNotNull();

        String actualId = actual.getId();
        assertThat(actualId)
                .overridingErrorMessage("Expected id to be <%s> but was <%s>",
                        expectedId,
                        actualId
                )
                .isEqualTo(expectedId);

        return this;
    }

    public BirdsDTOAssert hasNoName() {
        isNotNull();

        String actualName = actual.getName();
        assertThat(actualName)
                .overridingErrorMessage("expected Name to be <null> but was <%s>", actualName)
                .isNull();

        return this;
    }

    public BirdsDTOAssert hasNoId() {
        isNotNull();

        String actualId = actual.getId();
        assertThat(actualId)
                .overridingErrorMessage("Expected id to be <null> but was <%s>", actualId)
                .isNull();

        return this;
    }

    public BirdsDTOAssert hasTitle(String expectedFamily) {
        isNotNull();

        String actualFamily = actual.getFamily();
        assertThat(actualFamily)
                .overridingErrorMessage("Expected Family to be <%s> but was <%s>",
                        expectedFamily,
                        actualFamily
                )
                .isEqualTo(expectedFamily);

        return this;
    }
}
