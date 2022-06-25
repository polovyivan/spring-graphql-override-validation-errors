package com.polovyi.ivan.tutorials.controller;

import com.polovyi.ivan.tutorials.service.HalloService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HelloController {

    private final HalloService halloService;

    @QueryMapping
    public String hello(@NotNull(message = "3") @Argument String name,
                        @NotNull(message = "3") @Argument  LocalDate birthDate) {
        return halloService.generateGreetingMessage(name, birthDate);
    }

}
