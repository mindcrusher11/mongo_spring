package com.saltside.bootrest.bird;

import static com.saltside.bootrest.bird.BirdsAssert.assertThatBirds;

import org.junit.Test;

import com.saltside.bootrest.model.Birds;


public class BirdsTest {

    private static final String NAME = "name";
    private static final String FAMILY = "family";

    private static final int MAX_LENGTH_DESCRIPTION = 500;
    private static final int MAX_LENGTH_TITLE = 100;

    private static final String UPDATED_DESCRIPTION = "updatedDescription";
    private static final String UPDATED_TITLE = "updatedTitle";

    @Test(expected = NullPointerException.class)
    public void build_TitleIsNull_ShouldThrowException() {
        Birds.getBuilder()
                .family(null)
                .name(NAME)
                .build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void build_TitleIsEmpty_ShouldThrowException() {
        Birds.getBuilder()
                .family("")
                .name(NAME)
                .build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void build_TitleIsTooLong_ShouldThrowException() {
        String tooLongTitle = StringTestUtil.createStringWithLength(MAX_LENGTH_TITLE + 1);
        Birds.getBuilder()
                .family(tooLongTitle)
                .name(NAME)
                .build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void build_DescriptionIsTooLong_ShouldThrowException() {
        String tooLongDescription = StringTestUtil.createStringWithLength(MAX_LENGTH_DESCRIPTION + 1);
        Birds.getBuilder()
                .family(FAMILY)
                .name(tooLongDescription)
                .build();
    }

    @Test
    public void build_WithoutDescription_ShouldCreateNewTodoEntryWithCorrectTitle() {
        Birds build = Birds.getBuilder()
                .family(FAMILY)
                .build();

        assertThatBirds(build)
                .hasNoId()
                .hasFamily(FAMILY)
                .hasNoName();
    }

    @Test
    public void build_WithTitleAndDescription_ShouldCreateNewTodoEntryWithCorrectTitleAndDescription() {
        Birds build = Birds.getBuilder()
                .family(FAMILY)
                .name(NAME)
                .build();

        assertThatBirds(build)
                .hasNoId()
                .hasFamily(FAMILY)
                .hasDescription(NAME);
    }

    @Test
    public void build_WithMaxLengthTitleAndDescription_ShouldCreateNewTodoEntryWithCorrectTitleAndDescription() {
        String maxLengthTitle = StringTestUtil.createStringWithLength(MAX_LENGTH_TITLE);
        String maxLengthDescription = StringTestUtil.createStringWithLength(MAX_LENGTH_DESCRIPTION);

        Birds build = Birds.getBuilder()
                .family(maxLengthTitle)
                .name(maxLengthDescription)
                .build();

        assertThatBirds(build)
                .hasNoId()
                .hasFamily(maxLengthTitle)
                .hasDescription(maxLengthDescription);
    }

    @Test(expected = NullPointerException.class)
    public void update_TitleIsNull_ShouldThrowException() {
        Birds updated = Birds.getBuilder()
                .family(FAMILY)
                .name(NAME)
                .build();

        updated.update(null, UPDATED_DESCRIPTION);
    }

    @Test(expected = IllegalArgumentException.class)
    public void update_TitleIsEmpty_ShouldThrowException() {
        Birds updated = Birds.getBuilder()
                .family(FAMILY)
                .name(NAME)
                .build();

        updated.update("", UPDATED_DESCRIPTION);
    }

    @Test(expected = IllegalArgumentException.class)
    public void update_TitleIsTooLong_ShouldThrowException() {
        Birds updated = Birds.getBuilder()
                .family(FAMILY)
                .name(NAME)
                .build();

        String tooLongTitle = StringTestUtil.createStringWithLength(MAX_LENGTH_TITLE + 1);
        updated.update(tooLongTitle, UPDATED_DESCRIPTION);
    }

    @Test(expected = IllegalArgumentException.class)
    public void update_DescriptionIsTooLong_ShouldThrowException() {
        Birds updated = Birds.getBuilder()
                .family(FAMILY)
                .name(NAME)
                .build();

        String tooLongDescription = StringTestUtil.createStringWithLength(MAX_LENGTH_DESCRIPTION + 1);
        updated.update(UPDATED_TITLE, tooLongDescription);
    }

    @Test
    public void update_DescriptionIsNull_ShouldUpdateTitleAndDescription() {
        Birds updated = Birds.getBuilder()
                .family(FAMILY)
                .name(NAME)
                .build();

        updated.update(UPDATED_TITLE, null);

        assertThatBirds(updated)
                .hasFamily(UPDATED_TITLE)
                .hasNoName();
    }

    @Test
    public void update_MaxLengthTitleAndDescription_ShouldUpdateTitleAndDescription() {
        Birds updated = Birds.getBuilder()
                .family(FAMILY)
                .name(NAME)
                .build();

        String maxLengthfamily = StringTestUtil.createStringWithLength(MAX_LENGTH_TITLE);
        String maxLengthDescription = StringTestUtil.createStringWithLength(MAX_LENGTH_DESCRIPTION);

        updated.update(maxLengthfamily, maxLengthDescription);

        assertThatBirds(updated)
                .hasFamily(maxLengthfamily)
                .hasDescription(maxLengthDescription);
    }
}
