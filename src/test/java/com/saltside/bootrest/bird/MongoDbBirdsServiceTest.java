package com.saltside.bootrest.bird;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.saltside.bootrest.model.Birds;

@SuppressWarnings("unused")
@RunWith(MockitoJUnitRunner.class)
class MongoDbBirdsServiceTest {

    private static final String NAME = "name";
    private static final String ID = "id";
    private static final String FAMILY = "family";

    @Mock
    private BirdsRepository repository;

    //private MongoDbBirdsService service;

    @Before
    public void setUp() {
        //this.service = new MongoDBBirdsService(repository);
    }
/*
    @Test
    public void create_ShouldSaveNewTodoEntry() {
        BirdsDTO newTodo = new BirdsDTOBuilder()
                .family(FAMILY)
                .name(NAME)
                .build();

        when(repository.save(isA(Birds.class))).thenAnswer(invocation -> (Birds) invocation.getArguments()[0]);

        //service.create(newTodo);

        ArgumentCaptor<Birds> savedTodoArgument = ArgumentCaptor.forClass(Birds.class);

        verify(repository, times(1)).save(savedTodoArgument.capture());
        verifyNoMoreInteractions(repository);

        Birds savedTodo = savedTodoArgument.getValue();
        assertThatBirds(savedTodo)
                .hasFamily(FAMILY)
                .hasDescription(NAME);
    }

    @Test
    public void create_ShouldReturnTheInformationOfCreatedTodoEntry() {
        BirdsDTO newTodo = new BirdsDTOBuilder()
                .family(FAMILY)
                .name(NAME)
                .build();

        when(repository.save(isA(Birds.class))).thenAnswer(invocation -> {
            Birds persisted = (Birds) invocation.getArguments()[0];
            ReflectionTestUtils.setField(persisted, "id", ID);
            return persisted;
        });

        BirdsDTO returned = service.create(newTodo);

        assertThatBirdsDTO(returned)
                .hasId(ID)
                .hasFamily(FAMILY)
                .hasDescription(NAME);
    }

    @Test(expected = BirdsNotFoundException.class)
    public void delete_TodoEntryNotFound_ShouldThrowException() {
        when(repository.findOne(ID)).thenReturn(Optional.empty());

        service.findById(ID);
    }

    @Test
    public void delete_TodoEntryFound_ShouldDeleteTheFoundTodoEntry() {
        Birds deleted = new BirdsBuilder()
                .id(ID)
                .build();

        when(repository.findOne(ID)).thenReturn(Optional.of(deleted));

        service.delete(ID);

        verify(repository, times(1)).delete(deleted);
    }

    @Test
    public void delete_TodoEntryFound_ShouldReturnTheDeletedTodoEntry() {
        Birds deleted = new BirdsBuilder()
                .id(ID)
                .family(FAMILY)
                .name(NAME)
                .build();

        when(repository.findOne(ID)).thenReturn(Optional.of(deleted));

        BirdsDTO returned = service.delete(ID);

        assertThatBirdsDTO(returned)
                .hasId(ID)
                .hasFamily(FAMILY)
                .hasDescription(NAME);
    }

    @Test
    public void findAll_OneTodoEntryFound_ShouldReturnTheInformationOfFoundTodoEntry() {
        Birds expected = new BirdsBuilder()
                .id(ID)
                .family(FAMILY)
                .name(NAME)
                .build();

        when(repository.findAll()).thenReturn(Arrays.asList(expected));

        List<BirdsDTO> todoEntries = service.findAll();
        assertThat(todoEntries).hasSize(1);

        BirdsDTO actual = todoEntries.iterator().next();
        assertThatBirdsDTO(actual)
                .hasId(ID)
                .hasFamily(FAMILY)
                .hasDescription(NAME);
    }

    @Test(expected = BirdsNotFoundException.class)
    public void findById_TodoEntryNotFound_ShouldThrowException() {
        when(repository.findOne(ID)).thenReturn(Optional.empty());

        service.findById(ID);
    }

    @Test
    public void findById_TodoEntryFound_ShouldReturnTheInformationOfFoundTodoEntry() {
        Birds found = new BirdsBuilder()
                .id(ID)
                .family(FAMILY)
                .name(NAME)
                .build();

        when(repository.findOne(ID)).thenReturn(Optional.of(found));

        BirdsDTO returned = service.findById(ID);

        assertThatBirdsDTO(returned)
                .hasId(ID)
                .hasFamily(FAMILY)
                .hasDescription(NAME);
    }

    @Test(expected = BirdsNotFoundException.class)
    public void update_UpdatedTodoEntryNotFound_ShouldThrowException() {
        when(repository.findOne(ID)).thenReturn(Optional.empty());

        BirdsDTO updated = new BirdsDTOBuilder()
                .id(ID)
                .build();

        service.update(updated);
    }

    @Test
    public void update_UpdatedTodoEntryFound_ShouldSaveUpdatedTodoEntry() {
        Birds existing = new BirdsBuilder()
                .id(ID)
                .build();

        when(repository.findOne(ID)).thenReturn(Optional.of(existing));
        when(repository.save(existing)).thenReturn(existing);

        BirdsDTO updated = new BirdsDTOBuilder()
                .id(ID)
                .family(FAMILY)
                .name(NAME)
                .build();

        service.update(updated);

        verify(repository, times(1)).save(existing);
        assertThatBirds(existing)
                .hasId(ID)
                .hasFamily(FAMILY)
                .hasDescription(NAME);
    }

    @Test
    public void update_UpdatedTodoEntryFound_ShouldReturnTheInformationOfUpdatedTodoEntry() {
        Birds existing = new BirdsBuilder()
                .id(ID)
                .build();

        when(repository.findOne(ID)).thenReturn(Optional.of(existing));
        when(repository.save(existing)).thenReturn(existing);

        BirdsDTO updated = new BirdsDTOBuilder()
                .id(ID)
                .family(FAMILY)
                .name(NAME)
                .build();

        BirdsDTO returned = service.update(updated);
        assertThatBirdsDTO(returned)
                .hasId(ID)
                .hasFamily(FAMILY)
                .hasDescription(NAME);
    }*/
}
