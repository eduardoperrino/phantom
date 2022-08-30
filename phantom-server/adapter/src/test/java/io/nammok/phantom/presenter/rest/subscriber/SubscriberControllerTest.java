package io.nammok.phantom.presenter.rest.subscriber;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.glytching.junit.extension.random.Random;
import io.github.glytching.junit.extension.random.RandomBeansExtension;
import io.nammok.phantom.core.domain.Identity;
import io.nammok.phantom.core.domain.NotFoundException;
import io.nammok.phantom.core.domain.Subscriber;
import io.nammok.phantom.core.usecase.subscriber.*;
import io.nammok.phantom.presenter.rest.circular.CircularController;
import io.nammok.phantom.presenter.rest.entity.*;
import io.nammok.phantom.presenter.usecase.UseCaseExecutorImpl;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(RandomBeansExtension.class)
@WebMvcTest(controllers = CircularController.class)
class SubscriberControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @SpyBean
    SubscriberMapper subscriberMapper;
    @SpyBean
    UseCaseExecutorImpl useCaseExecutor;

    @MockBean
    Logger logger;

    @MockBean
    CreateSubscriberUseCase createSubscriberUseCase;
    @MockBean
    ReadAllSubscriberUseCase readAllSubscriberUseCase;
    @MockBean
    ReadSubscriberUseCase readSubscriberUseCase;
    @MockBean
    DeleteAllSubscriberUseCase deleteAllSubscriberUseCase;
    @MockBean
    DeleteSubscriberUseCase deleteSubscriberUseCase;

    private MvcResult makeAsyncRequest(RequestBuilder request) throws Exception {
        return mockMvc.perform(request)
                .andExpect(request().asyncStarted())
                .andReturn();
    }

    private ResultActions getAsyncResponse(MvcResult result) throws Exception {
        return mockMvc.perform(asyncDispatch(result));
    }

    @TestConfiguration
    @ComponentScan(basePackages = {"io.nammok.phantom.presenter.rest.subscriber"})
    static class Config {
    }

    @SpringBootApplication
    static class PhantomServerApplication {
    }

    @Nested
    class Create {
        @Test
        void successWithAllProperties(@Random Subscriber subscriber) throws Exception {
            // Background
            CreateSubscriberUseCase.InputValues input = CreateSubscriberUseCase.InputValues.builder()
                    .withName(subscriber.getName())
                    .withEmail(subscriber.getEmail())
                    .build();
            CreateSubscriberUseCase.OutputValues output = CreateSubscriberUseCase.OutputValues.builder()
                    .withSubscriber(Subscriber.builder()
                            .withId(subscriber.getId())
                            .withName(subscriber.getName())
                            .withEmail(subscriber.getEmail())
                            .build())
                    .build();
            when(createSubscriberUseCase.execute(input)).thenReturn(output);

            // Given
            SubscriberRequest content = SubscriberRequest.builder()
                    .withName(subscriber.getName())
                    .withEmail(subscriber.getEmail())
                    .build();
            RequestBuilder request = post("/subscriber")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(content));

            // When
            MvcResult result = makeAsyncRequest(request);

            // Then
            SubscriberResponse expected = SubscriberResponse.builder()
                    .withId(subscriber.getId().getId())
                    .withName(subscriber.getName())
                    .withEmail(subscriber.getEmail())
                    .build();

            getAsyncResponse(result)
                    .andExpect(status().isCreated())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(actual -> {
                        assertThatJson(actual.getResponse().getContentAsString()).isEqualTo(objectMapper.writeValueAsString(expected));
                    });
        }

        @Test
        void successWithNoProperties(@Random Subscriber forFields) throws Exception {
            Subscriber subscriber = Subscriber.builder()
                    .withId(forFields.getId())
                    .build();

            // Background
            CreateSubscriberUseCase.InputValues input = CreateSubscriberUseCase.InputValues.builder()
                    .withName(subscriber.getName())
                    .withEmail(subscriber.getEmail())
                    .build();
            CreateSubscriberUseCase.OutputValues output = CreateSubscriberUseCase.OutputValues.builder()
                    .withSubscriber(Subscriber.builder()
                            .withId(subscriber.getId())
                            .withName(subscriber.getName())
                            .withEmail(subscriber.getEmail())
                            .build())
                    .build();
            when(createSubscriberUseCase.execute(input)).thenReturn(output);

            // Given
            SubscriberRequest content = SubscriberRequest.builder()
                    .withName(subscriber.getName())
                    .withEmail(subscriber.getEmail())
                    .build();
            RequestBuilder request = post("/subscriber")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(content));

            // When
            MvcResult result = makeAsyncRequest(request);

            // Then
            SubscriberResponse expected = SubscriberResponse.builder()
                    .withId(subscriber.getId().getId())
                    .withName(subscriber.getName())
                    .withEmail(subscriber.getEmail())
                    .build();

            getAsyncResponse(result)
                    .andExpect(status().isCreated())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(actual -> {
                        assertThatJson(actual.getResponse().getContentAsString()).isEqualTo(objectMapper.writeValueAsString(expected));
                    });
        }

        @Test
        void successWithOnlyName(@Random Subscriber forFields) throws Exception {
            Subscriber subscriber = Subscriber.builder()
                    .withId(forFields.getId())
                    .withName(forFields.getName())
                    .build();

            // Background
            CreateSubscriberUseCase.InputValues input = CreateSubscriberUseCase.InputValues.builder()
                    .withName(subscriber.getName())
                    .withEmail(subscriber.getEmail())
                    .build();
            CreateSubscriberUseCase.OutputValues output = CreateSubscriberUseCase.OutputValues.builder()
                    .withSubscriber(Subscriber.builder()
                            .withId(subscriber.getId())
                            .withName(subscriber.getName())
                            .withEmail(subscriber.getEmail())
                            .build())
                    .build();
            when(createSubscriberUseCase.execute(input)).thenReturn(output);

            // Given
            SubscriberRequest content = SubscriberRequest.builder()
                    .withName(subscriber.getName())
                    .withEmail(subscriber.getEmail())
                    .build();
            RequestBuilder request = post("/subscriber")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(content));

            // When
            MvcResult result = makeAsyncRequest(request);

            // Then
            SubscriberResponse expected = SubscriberResponse.builder()
                    .withId(subscriber.getId().getId())
                    .withName(subscriber.getName())
                    .withEmail(subscriber.getEmail())
                    .build();

            getAsyncResponse(result)
                    .andExpect(status().isCreated())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(actual -> {
                        assertThatJson(actual.getResponse().getContentAsString()).isEqualTo(objectMapper.writeValueAsString(expected));
                    });
        }

        @Test
        void successWithOnlyEmail(@Random Subscriber forFields) throws Exception {
            Subscriber subscriber = Subscriber.builder()
                    .withId(forFields.getId())
                    .withEmail(forFields.getEmail())
                    .build();

            // Background
            CreateSubscriberUseCase.InputValues input = CreateSubscriberUseCase.InputValues.builder()
                    .withName(subscriber.getName())
                    .withEmail(subscriber.getEmail())
                    .build();
            CreateSubscriberUseCase.OutputValues output = CreateSubscriberUseCase.OutputValues.builder()
                    .withSubscriber(Subscriber.builder()
                            .withId(subscriber.getId())
                            .withName(subscriber.getName())
                            .withEmail(subscriber.getEmail())
                            .build())
                    .build();
            when(createSubscriberUseCase.execute(input)).thenReturn(output);

            // Given
            SubscriberRequest content = SubscriberRequest.builder()
                    .withName(subscriber.getName())
                    .withEmail(subscriber.getEmail())
                    .build();
            RequestBuilder request = post("/subscriber")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(content));

            // When
            MvcResult result = makeAsyncRequest(request);

            // Then
            SubscriberResponse expected = SubscriberResponse.builder()
                    .withId(subscriber.getId().getId())
                    .withName(subscriber.getName())
                    .withEmail(subscriber.getEmail())
                    .build();

            getAsyncResponse(result)
                    .andExpect(status().isCreated())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(actual -> {
                        assertThatJson(actual.getResponse().getContentAsString()).isEqualTo(objectMapper.writeValueAsString(expected));
                    });
        }

        @Test
        void errorUnsupportedMediaType(@Random Subscriber subscriber) throws Exception {
            // Given
            SubscriberRequest content = SubscriberRequest.builder()
                    .withName(subscriber.getName())
                    .withEmail(subscriber.getEmail())
                    .build();
            RequestBuilder request = post("/subscriber")
                    .contentType(MediaType.TEXT_PLAIN_VALUE)
                    .content(objectMapper.writeValueAsString(content));

            // When
            mockMvc.perform(request)
                    // Then
                    .andExpect(status().isUnsupportedMediaType())
                    .andExpect(actual -> {
                        assertEquals(0, actual.getResponse().getContentLength());
                    })
                    .andReturn();
        }

        @Test
        void errorBadRequestNoBody() throws Exception {
            // Given
            RequestBuilder request = post("/subscriber")
                    .contentType(MediaType.APPLICATION_JSON_VALUE);

            // When
            mockMvc.perform(request)
                    // Then
                    .andExpect(status().isBadRequest())
                    .andExpect(actual -> {
                        assertEquals(0, actual.getResponse().getContentLength());
                    })
                    .andReturn();
        }
    }

    @Nested
    class ReadAll {
        @Test
        void successWithItems(@Random(size = 5, type = Subscriber.class) List<Subscriber> subscribers) throws Exception {
            // Background
            ReadAllSubscriberUseCase.InputValues input = ReadAllSubscriberUseCase.InputValues.builder().build();
            ReadAllSubscriberUseCase.OutputValues output = ReadAllSubscriberUseCase.OutputValues.builder()
                    .withSubscribers(subscribers)
                    .build();
            when(readAllSubscriberUseCase.execute(null)).thenReturn(output);

            // Given
            RequestBuilder request = get("/subscriber");

            // When
            MvcResult result = makeAsyncRequest(request);

            // Then
            List<SubscriberResponse> expected = subscribers.stream().map(s -> SubscriberResponse.builder()
                    .withId(s.getId().getId())
                    .withName(s.getName())
                    .withEmail(s.getEmail())
                    .build()).collect(Collectors.toList());

            getAsyncResponse(result)
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(actual -> {
                        assertThatJson(actual.getResponse().getContentAsString()).isEqualTo(objectMapper.writeValueAsString(expected));
                    });
        }

        @Test
        void successWithNoItems() throws Exception {
            // Background
            ReadAllSubscriberUseCase.InputValues input = ReadAllSubscriberUseCase.InputValues.builder().build();
            ReadAllSubscriberUseCase.OutputValues output = ReadAllSubscriberUseCase.OutputValues.builder()
                    .withSubscribers(Collections.emptyList())
                    .build();
            when(readAllSubscriberUseCase.execute(null)).thenReturn(output);

            // Given
            RequestBuilder request = get("/subscriber");

            // When
            MvcResult result = makeAsyncRequest(request);

            // Then
            List<SubscriberResponse> expected = Collections.emptyList();

            getAsyncResponse(result)
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(actual -> {
                        assertThatJson(actual.getResponse().getContentAsString()).isEqualTo(objectMapper.writeValueAsString(expected));
                    });
        }
    }

    @Nested
    class Read {
        @Test
        void successWithAllProperties(@Random Subscriber subscriber) throws Exception {
            // Background
            ReadSubscriberUseCase.InputValues input = ReadSubscriberUseCase.InputValues.builder()
                    .withIdentity(subscriber.getId())
                    .build();
            ReadSubscriberUseCase.OutputValues output = ReadSubscriberUseCase.OutputValues.builder()
                    .withSubscriber(subscriber)
                    .build();
            when(readSubscriberUseCase.execute(input)).thenReturn(output);

            // Given
            RequestBuilder request = get("/subscriber/{id}", subscriber.getId().getId());

            // When
            MvcResult result = makeAsyncRequest(request);

            // Then
            SubscriberResponse expected = SubscriberResponse.builder()
                    .withId(subscriber.getId().getId())
                    .withName(subscriber.getName())
                    .withEmail(subscriber.getEmail())
                    .build();

            getAsyncResponse(result)
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(actual -> {
                        assertThatJson(actual.getResponse().getContentAsString()).isEqualTo(objectMapper.writeValueAsString(expected));
                    });
        }

        @Test
        void errorNotFound(@Random String id) throws Exception {
            // Background
            ReadSubscriberUseCase.InputValues input = ReadSubscriberUseCase.InputValues.builder()
                    .withIdentity(Identity.of(id))
                    .build();
            when(readSubscriberUseCase.execute(input)).thenThrow(NotFoundException.of(id));

            // Given
            RequestBuilder request = get("/subscriber/{id}", id);

            // When
            MvcResult result = makeAsyncRequest(request);

            // Then
            HttpStatus httpStatus = HttpStatus.NOT_FOUND;
            ApiResponse expected = ApiResponse.builder()
                    .withTimestamp(null)
                    .withStatus(httpStatus.value())
                    .withReason(httpStatus.getReasonPhrase())
                    .withMessage(id)
                    .build();

            getAsyncResponse(result)
                    .andExpect(status().isNotFound())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(actual -> {
                        assertThatJson(actual.getResponse().getContentAsString()).whenIgnoringPaths("timestamp").isEqualTo(objectMapper.writeValueAsString(expected));
                    });
        }
    }

    @Nested
    class DeleteAll {
        @Test
        void success() throws Exception {
            // Background
            DeleteAllSubscriberUseCase.InputValues input = DeleteAllSubscriberUseCase.InputValues.builder().build();
            DeleteAllSubscriberUseCase.OutputValues output = DeleteAllSubscriberUseCase.OutputValues.builder().build();
            when(deleteAllSubscriberUseCase.execute(null)).thenReturn(output);

            // Given
            RequestBuilder request = delete("/subscriber");

            // When
            MvcResult result = makeAsyncRequest(request);

            // Then
            getAsyncResponse(result)
                    .andExpect(status().isNoContent());
        }
    }

    @Nested
    class Delete {
        @Test
        void success(@Random Identity id) throws Exception {
            // Background
            DeleteSubscriberUseCase.InputValues input = DeleteSubscriberUseCase.InputValues.builder()
                    .withIdentity(id)
                    .build();
            DeleteSubscriberUseCase.OutputValues output = DeleteSubscriberUseCase.OutputValues.builder().build();
            when(deleteSubscriberUseCase.execute(input)).thenReturn(output);

            // Given
            RequestBuilder request = delete("/subscriber/{id}", id.getId());

            // When
            MvcResult result = makeAsyncRequest(request);

            // Then
            getAsyncResponse(result)
                    .andExpect(status().isNoContent());
        }

        @Test
        void errorNotFound(@Random String id) throws Exception {
            // Background
            DeleteSubscriberUseCase.InputValues input = DeleteSubscriberUseCase.InputValues.builder()
                    .withIdentity(Identity.of(id))
                    .build();
            DeleteSubscriberUseCase.OutputValues output = DeleteSubscriberUseCase.OutputValues.builder().build();
            when(deleteSubscriberUseCase.execute(input)).thenThrow(NotFoundException.of(id));

            // Given
            RequestBuilder request = delete("/subscriber/{id}", id);

            // When
            MvcResult result = makeAsyncRequest(request);

            // Then
            HttpStatus httpStatus = HttpStatus.NOT_FOUND;
            ApiResponse expected = ApiResponse.builder()
                    .withTimestamp(null)
                    .withStatus(httpStatus.value())
                    .withReason(httpStatus.getReasonPhrase())
                    .withMessage(id)
                    .build();

            getAsyncResponse(result)
                    .andExpect(status().isNotFound())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(actual -> {
                        assertThatJson(actual.getResponse().getContentAsString()).whenIgnoringPaths("timestamp").isEqualTo(objectMapper.writeValueAsString(expected));
                    });
        }
    }
}