package com.polovyi.ivan.tutorials.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class Dictionary {

    private static MessageSource messageSource;

    @Autowired
    private Dictionary(MessageSource messageSource) {
        Dictionary.setMessageSource(messageSource);
    }

    public static String valueOf(String key, Object... args) {
        return getMessageSource().getMessage(key, args, LocaleContextHolder.getLocale());
    }

    private static MessageSource getMessageSource() {
        return messageSource;
    }

    private static void setMessageSource(MessageSource messageSource) {
        Dictionary.messageSource = messageSource;
    }

}
