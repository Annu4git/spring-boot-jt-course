package com.anurag.CourseService.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServiceResponse<T> {

    private HttpStatus httpStatus;

    private T response;

    private List<String> errorMessages;

    public ServiceResponse(HttpStatus httpStatus, T response) {
        this.httpStatus = httpStatus;
        this.response = response;
    }
}
