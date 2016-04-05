package com.saltside.bootrest.bird;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.annotation.ExceptionHandlerMethodResolver;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod;

import com.saltside.bootrest.controller.BirdsController;
import com.saltside.bootrest.error.RestErrorHandler;
import com.saltside.bootrest.service.BirdsService;


@SuppressWarnings("unused")
@RunWith(MockitoJUnitRunner.class)
public class BirdsControllerTest {

    private static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8")
    );

    private static final String NAME = "name";
    private static final String ID = "id";
    private static final String FAMILY = "family";

    private static final int MAX_LENGTH_DESCRIPTION = 500;
    private static final int MAX_LENGTH_TITLE = 100;

    @Mock
    private BirdsService service;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new BirdsController(service))
                .setHandlerExceptionResolvers(withExceptionControllerAdvice())
                .build();
    }

    /**
     * For some reason this does not work. The correct error handler method is invoked but when it tries
     * to return the validation errors as json, the following error appears to log:
     *
     * Failed to invoke @ExceptionHandler method:
     * public com.javaadvent.bootrest.error.ValidationErrorDTO com.javaadvent.bootrest.error.RestErrorHandler.processValidationError(org.springframework.web.bind.MethodArgumentNotValidException)
     * org.springframework.web.HttpMediaTypeNotAcceptableException: Could not find acceptable representation
     *
     * I have to figure out how to fix this before I can write the unit tests that ensure that validation is working.
     */
    private ExceptionHandlerExceptionResolver withExceptionControllerAdvice() {
        final ExceptionHandlerExceptionResolver exceptionResolver = new ExceptionHandlerExceptionResolver() {
            @Override
            protected ServletInvocableHandlerMethod getExceptionHandlerMethod(final HandlerMethod handlerMethod,
                                                                              final Exception exception) {
                Method method = new ExceptionHandlerMethodResolver(RestErrorHandler.class).resolveMethod(exception);
                if (method != null) {
                    ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
                    messageSource.setBasename("messages");
                    return new ServletInvocableHandlerMethod(new RestErrorHandler(messageSource), method);
                }
                return super.getExceptionHandlerMethod(handlerMethod, exception);
            }
        };
        exceptionResolver.afterPropertiesSet();
        return exceptionResolver;
    }

/*    @Test
    public void create_BirdsEntryWithOnlyTitle_ShouldCreateNewBirdsEntryWithoutDescription() throws Exception {
        BirdsDTO newBirdsEntry = new BirdsDTOBuilder()
                .family(FAMILY)
                .build();

        mockMvc.perform(post("/api/birds")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(WebTestUtil.convertObjectToJsonBytes(newBirdsEntry))
        );

        ArgumentCaptor<BirdsDTO> createdArgument = ArgumentCaptor.forClass(BirdsDTO.class);
        verify(service, times(1)).create(createdArgument.capture());
        verifyNoMoreInteractions(service);

        BirdsDTO created = createdArgument.getValue();
        assertThatBirdsDTO(created)
                .hasNoId()
                .hasFamily(FAMILY)
                .hasNoName();
    }

    @Test
    public void create_BirdsEntryWithOnlyTitle_ShouldReturnResponseStatusCreated() throws Exception {
        BirdsDTO newBirdsEntry = new BirdsDTOBuilder()
                .family(FAMILY)
                .build();

        when(service.create(isA(BirdsDTO.class))).then(invocationOnMock -> {
            BirdsDTO saved = (BirdsDTO) invocationOnMock.getArguments()[0];
            saved.setId(ID);
            return saved;
        });

        mockMvc.perform(post("/api/birds")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(WebTestUtil.convertObjectToJsonBytes(newBirdsEntry))
        )
                .andExpect(status().isCreated());
    }

    @Test
    public void create_BirdsEntryWithOnlyTitle_ShouldReturnTheInformationOfCreatedBirdsEntryAsJSon() throws Exception {
        BirdsDTO newBirdsEntry = new BirdsDTOBuilder()
                .family(FAMILY)
                .build();

        when(service.create(isA(BirdsDTO.class))).then(invocationOnMock -> {
            BirdsDTO saved = (BirdsDTO) invocationOnMock.getArguments()[0];
            saved.setId(ID);
            return saved;
        });

        mockMvc.perform(post("/api/birds")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(WebTestUtil.convertObjectToJsonBytes(newBirdsEntry))
        )
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(ID)))
                .andExpect(jsonPath("$.family", is(FAMILY)))
                .andExpect(jsonPath("$.name", isEmptyOrNullString()));
    }

    @Test
    public void create_BirdsEntryWithMaxLengthTitleAndDescription_ShouldCreateNewBirdsEntryWithCorrectInformation() throws Exception {
        String maxLengthTitle = StringTestUtil.createStringWithLength(MAX_LENGTH_TITLE);
        String maxLengthDescription = StringTestUtil.createStringWithLength(MAX_LENGTH_DESCRIPTION);

        BirdsDTO newBirdsEntry = new BirdsDTOBuilder()
                .family(maxLengthTitle)
                .name(maxLengthDescription)
                .build();

        mockMvc.perform(post("/api/birds")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(WebTestUtil.convertObjectToJsonBytes(newBirdsEntry))
        );

        ArgumentCaptor<BirdsDTO> createdArgument = ArgumentCaptor.forClass(BirdsDTO.class);
        verify(service, times(1)).create(createdArgument.capture());
        verifyNoMoreInteractions(service);

        BirdsDTO created = createdArgument.getValue();
        assertThatBirdsDTO(created)
                .hasNoId()
                .hasTitle(maxLengthTitle)
                .hasDescription(maxLengthDescription);
    }

    @Test
    public void create_BirdsEntryWithMaxLengthTitleAndDescription_ShouldReturnResponseStatusCreated() throws Exception {
        String maxLengthTitle = StringTestUtil.createStringWithLength(MAX_LENGTH_TITLE);
        String maxLengthDescription = StringTestUtil.createStringWithLength(MAX_LENGTH_DESCRIPTION);

        BirdsDTO newBirdsEntry = new BirdsDTOBuilder()
                .family(maxLengthTitle)
                .name(maxLengthDescription)
                .build();

        when(service.create(isA(BirdsDTO.class))).then(invocationOnMock -> {
            BirdsDTO saved = (BirdsDTO) invocationOnMock.getArguments()[0];
            saved.setId(ID);
            return saved;
        });

        mockMvc.perform(post("/api/birds")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(WebTestUtil.convertObjectToJsonBytes(newBirdsEntry))
        )
                .andExpect(status().isCreated());
    }

    @Test
    public void create_BirdsEntryWithMaxLengthTitleAndDescription_ShouldReturnTheInformationOfCreatedBirdsEntryAsJson() throws Exception {
        String maxLengthTitle = StringTestUtil.createStringWithLength(MAX_LENGTH_TITLE);
        String maxLengthDescription = StringTestUtil.createStringWithLength(MAX_LENGTH_DESCRIPTION);

        BirdsDTO newBirdsEntry = new BirdsDTOBuilder()
                .family(maxLengthTitle)
                .name(maxLengthDescription)
                .build();

        when(service.create(isA(BirdsDTO.class))).then(invocationOnMock -> {
            BirdsDTO saved = (BirdsDTO) invocationOnMock.getArguments()[0];
            saved.setId(ID);
            return saved;
        });

        mockMvc.perform(post("/api/birds")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(WebTestUtil.convertObjectToJsonBytes(newBirdsEntry))
        )
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(ID)))
                .andExpect(jsonPath("$.family", is(maxLengthTitle)))
                .andExpect(jsonPath("$.name", is(maxLengthDescription)));
    }

    @Test
    public void delete_BirdsEntryNotFound_ShouldReturnResponseStatusNotFound() throws Exception {
        when(service.delete(ID)).thenThrow(new BirdsNotFoundException(ID));

        mockMvc.perform(delete("/api/birds/{id}", ID))
                .andExpect(status().isNotFound());
    }

    @Test
    public void delete_BirdsEntryFound_ShouldReturnResponseStatusOk() throws Exception {
        BirdsDTO deleted = new BirdsDTOBuilder()
                .id(ID)
                .build();

        when(service.delete(ID)).thenReturn(deleted);

        mockMvc.perform(delete("/api/birds/{id}", ID))
                .andExpect(status().isOk());
    }

    @Test
    public void delete_BirdsEntryFound_ShouldTheInformationOfDeletedBirdsEntryAsJson() throws Exception {
        BirdsDTO deleted = new BirdsDTOBuilder()
                .id(ID)
                .family(FAMILY)
                .name(NAME)
                .build();

        when(service.delete(ID)).thenReturn(deleted);

        mockMvc.perform(delete("/api/birds/{id}", ID))
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(ID)))
                .andExpect(jsonPath("$.family", is(FAMILY)))
                .andExpect(jsonPath("$.name", is(NAME)));
    }

    @Test
    public void findAll_ShouldReturnResponseStatusOk() throws Exception {
        mockMvc.perform(get("/api/birds"))
                .andExpect(status().isOk());
    }

    @Test
    public void findAll_OneBirdsEntryFound_ShouldReturnListThatContainsOneBirdsEntryAsJson() throws Exception {
        BirdsDTO found = new BirdsDTOBuilder()
                .id(ID)
                .family(FAMILY)
                .name(NAME)
                .build();

        when(service.findAll()).thenReturn(Arrays.asList(found));

        mockMvc.perform(get("/api/birds"))
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(ID)))
                .andExpect(jsonPath("$[0].family", is(FAMILY)))
                .andExpect(jsonPath("$[0].name", is(NAME)));
    }

    @Test
    public void findById_BirdsEntryFound_ShouldReturnResponseStatusOk() throws Exception {
        BirdsDTO found = new BirdsDTOBuilder().build();

        when(service.findById(ID)).thenReturn(found);

        mockMvc.perform(get("/api/birds/{id}", ID))
                .andExpect(status().isOk());
    }

    @Test
    public void findById_BirdsEntryFound_ShouldTheInformationOfFoundBirdsEntryAsJson() throws Exception {
        BirdsDTO found = new BirdsDTOBuilder()
                .id(ID)
                .family(FAMILY)
                .name(NAME)
                .build();

        when(service.findById(ID)).thenReturn(found);

        mockMvc.perform(get("/api/birds/{id}", ID))
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(ID)))
                .andExpect(jsonPath("$.family", is(FAMILY)))
                .andExpect(jsonPath("$.name", is(NAME)));
    }

    @Test
    public void findById_BirdsEntryNotFound_ShouldReturnResponseStatusNotFound() throws Exception {
        when(service.findById(ID)).thenThrow(new BirdsNotFoundException(ID));

        mockMvc.perform(get("/api/birds/{id}", ID))
                .andExpect(status().isNotFound());
    }

    @Test
    public void update_BirdsEntryWithOnlyTitle_ShouldUpdateTheInformationOfBirdsEntry() throws Exception {
        BirdsDTO updatedBirdsEntry = new BirdsDTOBuilder()
                .id(ID)
                .family(FAMILY)
                .build();

        mockMvc.perform(put("/api/birds/{id}", ID)
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(WebTestUtil.convertObjectToJsonBytes(updatedBirdsEntry))
        );

        ArgumentCaptor<BirdsDTO> updatedArgument = ArgumentCaptor.forClass(BirdsDTO.class);
        verify(service, times(1)).update(updatedArgument.capture());
        verifyNoMoreInteractions(service);

        BirdsDTO updated = updatedArgument.getValue();
        assertThatBirdsDTO(updated)
                .hasId(ID)
                .hasTitle(FAMILY)
                .hasNoDescription();
    }

    @Test
    public void update_BirdsntryWithOnlyTitle_ShouldReturnResponseStatusOk() throws Exception {
        BirdsDTO updatedBirdsEntry = new BirdsDTOBuilder()
                .id(ID)
                .family(FAMILY)
                .build();

        when(service.update(isA(BirdsDTO.class))).then(invocationOnMock -> (BirdsDTO) invocationOnMock.getArguments()[0]);

        mockMvc.perform(put("/api/birds/{id}", ID)
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(WebTestUtil.convertObjectToJsonBytes(updatedBirdsEntry))
        )
                .andExpect(status().isOk());
    }

    @Test
    public void update_BirdsEntryWithOnlyTitle_ShouldReturnTheInformationOfUpdatedBirdsEntryAsJSon() throws Exception {
        BirdsDTO updatedBirdsEntry = new BirdsDTOBuilder()
                .id(ID)
                .family(FAMILY)
                .build();

        when(service.update(isA(BirdsDTO.class))).then(invocationOnMock ->  (BirdsDTO) invocationOnMock.getArguments()[0]);

        mockMvc.perform(put("/api/birds/{id}", ID)
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(WebTestUtil.convertObjectToJsonBytes(updatedBirdsEntry))
        )
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(ID)))
                .andExpect(jsonPath("$.family", is(FAMILY)))
                .andExpect(jsonPath("$.name", isEmptyOrNullString()));
    }

    @Test
    public void update_BirdsEntryWithMaxLengthTitleAndDescription_ShouldUpdateTheInformationOfBirdsEntry() throws Exception {
        String maxLengthTitle = StringTestUtil.createStringWithLength(MAX_LENGTH_TITLE);
        String maxLengthDescription = StringTestUtil.createStringWithLength(MAX_LENGTH_DESCRIPTION);

        BirdsDTO updatedBirdsEntry = new BirdsDTOBuilder()
                .id(ID)
                .family(maxLengthTitle)
                .name(maxLengthDescription)
                .build();

        mockMvc.perform(put("/api/birds/{id}", ID)
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(WebTestUtil.convertObjectToJsonBytes(updatedBirdsEntry))
        );

        ArgumentCaptor<BirdsDTO> updatedArgument = ArgumentCaptor.forClass(BirdsDTO.class);
        verify(service, times(1)).update(updatedArgument.capture());
        verifyNoMoreInteractions(service);

        BirdsDTO updated = updatedArgument.getValue();
        assertThatBirdsDTO(updated)
                .hasId(ID)
                .hasTitle(maxLengthTitle)
                .hasDescription(maxLengthDescription);
    }

    @Test
    public void update_BirdsEntryWithMaxLengthTitleAndDescription_ShouldReturnResponseStatusOk() throws Exception {
        String maxLengthTitle = StringTestUtil.createStringWithLength(MAX_LENGTH_TITLE);
        String maxLengthDescription = StringTestUtil.createStringWithLength(MAX_LENGTH_DESCRIPTION);

        BirdsDTO updatedBirdsEntry = new BirdsDTOBuilder()
                .id(ID)
                .family(maxLengthTitle)
                .name(maxLengthDescription)
                .build();

        when(service.create(isA(BirdsDTO.class))).then(invocationOnMock -> (BirdsDTO) invocationOnMock.getArguments()[0]);

        mockMvc.perform(put("/api/birds/{id}", ID)
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(WebTestUtil.convertObjectToJsonBytes(updatedBirdsEntry))
        )
                .andExpect(status().isOk());
    }

    @Test
    public void update_BirdsEntryWithMaxLengthTitleAndDescription_ShouldReturnTheInformationOfCreatedUpdatedBirdsEntryAsJson() throws Exception {
        String maxLengthTitle = StringTestUtil.createStringWithLength(MAX_LENGTH_TITLE);
        String maxLengthDescription = StringTestUtil.createStringWithLength(MAX_LENGTH_DESCRIPTION);

        BirdsDTO updatedBirdsEntry = new BirdsDTOBuilder()
                .id(ID)
                .family(maxLengthTitle)
                .name(maxLengthDescription)
                .build();

        when(service.update(isA(BirdsDTO.class))).then(invocationOnMock -> (BirdsDTO) invocationOnMock.getArguments()[0]);

        mockMvc.perform(put("/api/birds/{id}", ID)
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(WebTestUtil.convertObjectToJsonBytes(updatedBirdsEntry))
        )
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(ID)))
                .andExpect(jsonPath("$.family", is(maxLengthTitle)))
                .andExpect(jsonPath("$.name", is(maxLengthDescription)));
    }*/
}
