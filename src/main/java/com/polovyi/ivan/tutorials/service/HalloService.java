package com.polovyi.ivan.tutorials.service;

import com.polovyi.ivan.tutorials.exception.UnprocessableEntityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Slf4j
@Service
public class HalloService {

    public String generateGreetingMessage(String name, LocalDate birthDate) {

        if (!birthDate.isBefore(LocalDate.now())) {
            log.error("[HalloService] The field birthday has to be before today!!!");
            throw new UnprocessableEntityException("4");
        }

        return "Hello " + name + ". You are " + ChronoUnit.YEARS.between(birthDate, LocalDate.now()) + " years old :)";
    }
}
