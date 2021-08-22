/*
 *   Developed by Andrei Muryn© 2021
 */

package com.mastery.java.task.model.converter;

import com.mastery.java.task.model.NameParameter;
import com.mastery.java.task.model.converter.exception.ConverterException;

import javax.persistence.AttributeConverter;
import java.util.EnumSet;

import static java.lang.String.format;

public abstract class AbstractEnumConverter<T extends Enum<T> & NameParameter> implements AttributeConverter<T, String> {

    private static final String ERROR_MESSAGE = "Failed to convert [%s] to [%s]";

    private final Class<T> clazz;

    public AbstractEnumConverter(final Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public String convertToDatabaseColumn(final T value) {
        return value.getName();
    }

    @Override
    public T convertToEntityAttribute(final String stringValue) {
        return EnumSet.allOf(clazz).stream()
                .filter(e -> e.getName().equals(stringValue))
                .findFirst()
                .orElseThrow(() -> new ConverterException(format(ERROR_MESSAGE, stringValue, clazz.getName())));
    }


}
