package com.polovyi.ivan.tutorials.exception.handler;

import com.polovyi.ivan.tutorials.exception.BadRequestException;
import com.polovyi.ivan.tutorials.exception.UnprocessableEntityException;
import graphql.GraphQLError;
import graphql.language.SourceLocation;
import graphql.schema.DataFetchingEnvironment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.execution.DataFetcherExceptionResolver;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import reactor.core.publisher.Mono;

import javax.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class GraphQLExceptionHandler implements DataFetcherExceptionResolver {

    @Override
    public Mono<List<GraphQLError>> resolveException(Throwable exception, DataFetchingEnvironment environment) {

        log.info("[GraphQLExceptionHandler] Exception type {}", exception);
        List<SourceLocation> sourceLocation = List.of(environment.getField().getSourceLocation());

        if (exception instanceof ConstraintViolationException) {
            log.info("[GraphQLExceptionHandler] ConstraintViolationException type");
            return Mono.just(handleConstraintViolationException((ConstraintViolationException) exception,
                    sourceLocation));
        }

        if (exception instanceof UnprocessableEntityException) {
            log.info("[GraphQLExceptionHandler] UnprocessableEntityException type");
            UnprocessableEntityException unprocessableEntityException = (UnprocessableEntityException) exception;
            unprocessableEntityException.setLocations(sourceLocation);
            return Mono.just(Collections.singletonList(unprocessableEntityException));
        }

        return Mono.just(List.of());
    }

    private List<GraphQLError> handleConstraintViolationException(ConstraintViolationException exception,
            List<SourceLocation> locations) {
        log.info("[GraphQLExceptionHandler] Creating lis of BadRequestException...");
        return exception.getConstraintViolations().stream()
                .map(constraint -> new BadRequestException(constraint.getMessageTemplate(), locations))
                .map(badRequestException -> (GraphQLError) badRequestException)
                .collect(Collectors.toList());
    }
}