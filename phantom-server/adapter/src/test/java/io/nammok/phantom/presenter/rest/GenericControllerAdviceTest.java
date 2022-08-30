package io.nammok.phantom.presenter.rest;


import io.nammok.phantom.core.domain.DomainException;
import io.nammok.phantom.core.domain.NotFoundException;
import io.nammok.phantom.presenter.rest.circular.CircularControllerAdvice;
import io.nammok.phantom.presenter.rest.entity.ApiResponse;
import io.github.glytching.junit.extension.random.Random;
import io.github.glytching.junit.extension.random.RandomBeansExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(RandomBeansExtension.class)
@ExtendWith(MockitoExtension.class)
class GenericControllerAdviceTest {
    @Spy
    Logger logger;

    @InjectMocks
    CircularControllerAdvice cut;

    @Test
    void customNotFoundException(@Random String value) {
        // Given
        NotFoundException exception = new NotFoundException(value);

        // When
        ResponseEntity<ApiResponse> actual = cut.customNotFoundException(exception);

        // Then
        HttpStatus expectedHttpStatus = HttpStatus.NOT_FOUND;

        assertThat(actual.getStatusCode()).isEqualTo(expectedHttpStatus);
        assertThat(actual.getBody()).isNotNull().satisfies(apiResponse -> {
            assertThat(apiResponse.getTimestamp()).isNotNull();
            assertThat(apiResponse.getStatus()).isEqualTo(expectedHttpStatus.value());
            assertThat(apiResponse.getReason()).isEqualTo(expectedHttpStatus.getReasonPhrase());
            assertThat(apiResponse.getMessage()).contains(value);
        });

        verify(logger, times(1)).error(anyString(), eq(exception.toString()));
    }

    @Test
    void customDomainException(@Random String value) {
        // Given
        DomainException exception = new NotFoundException(value);

        // When
        ResponseEntity<ApiResponse> actual = cut.customDomainException(exception);

        // Then
        HttpStatus expectedHttpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        assertThat(actual.getStatusCode()).isEqualTo(expectedHttpStatus);
        assertThat(actual.getBody()).isNotNull().satisfies(apiResponse -> {
            assertThat(apiResponse.getTimestamp()).isNotNull();
            assertThat(apiResponse.getStatus()).isEqualTo(expectedHttpStatus.value());
            assertThat(apiResponse.getReason()).isEqualTo(expectedHttpStatus.getReasonPhrase());
            assertThat(apiResponse.getMessage()).contains(value);
        });

        verify(logger, times(1)).error(anyString(), eq(exception.toString()));
    }
}
