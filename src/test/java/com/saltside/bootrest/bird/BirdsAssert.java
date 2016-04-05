package com.saltside.bootrest.bird;

import org.assertj.core.api.AbstractAssert;

import com.saltside.bootrest.model.Birds;

import static org.assertj.core.api.Assertions.assertThat;


class BirdsAssert extends AbstractAssert<BirdsAssert, Birds> {

    private BirdsAssert(Birds actual) {
        super(actual, BirdsAssert.class);
    }

    static BirdsAssert assertThatBirds(Birds actual) {
        return new BirdsAssert(actual);
    }

    BirdsAssert hasDescription(String expectedName) {
        isNotNull();

        String actualName = actual.getName();
        assertThat(actualName)
                .overridingErrorMessage("Expected description to be <%s> but was <%s>",
                        expectedName,
                        actualName
                )
                .isEqualTo(expectedName);

        return this;
    }

    BirdsAssert hasId(String expectedId) {
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

    BirdsAssert hasNoName() {
        isNotNull();

        String actualName = actual.getName();
        assertThat(actualName)
                .overridingErrorMessage("Expected name to be <null> but was <%s>", actualName)
                .isNull();

        return this;
    }

    BirdsAssert hasNoId() {
        isNotNull();

        String actualId = actual.getId();
        assertThat(actualId)
                .overridingErrorMessage("Expected id to be <null> but was <%s>", actualId)
                .isNull();

        return this;
    }

    BirdsAssert hasFamily(String expectedFamily) {
        isNotNull();

        String actualFamily = actual.getFamily();
        assertThat(actualFamily)
                .overridingErrorMessage("Expected title to be <%s> but was <%s>",
                        expectedFamily,
                        actualFamily
                )
                .isEqualTo(expectedFamily);

        return this;
    }
}
