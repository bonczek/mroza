/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mroza.handlers;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class LocalDateAttributeConverter implements AttributeConverter<LocalDate, Timestamp> {
    
    @Override
    public Timestamp convertToDatabaseColumn(LocalDate locDate) {        
        return (locDate == null ? null : Timestamp.valueOf(locDate.atStartOfDay()));
    }

    @Override
    public LocalDate convertToEntityAttribute(Timestamp dbData) {
        return (dbData == null ? null : dbData.toLocalDateTime().toLocalDate());
    }
}
