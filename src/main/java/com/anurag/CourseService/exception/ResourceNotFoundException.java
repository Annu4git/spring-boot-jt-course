package com.anurag.CourseService.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResourceNotFoundException extends RuntimeException {

    private String field;

    private String value;

    @Override
    public String toString() {
        return field + " not found with value : " + value;
    }
}
