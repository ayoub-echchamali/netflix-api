package fr.polytech.netflix.config;

import fr.polytech.netflix.dtos.ErrorDto;
import fr.polytech.netflix.exceptions.InvalidValueException;
import fr.polytech.netflix.exceptions.ResourceAlreadyExistException;
import fr.polytech.netflix.exceptions.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
@Slf4j
public class CustomHTTPException {

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody ErrorDto internalServerError(Exception ex) {
        ex.printStackTrace();
        return ErrorDto.builder().code("INTERNAL_ERROR").message("An internal error occurred").build();
    }

    @ExceptionHandler(value = ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody ErrorDto resourceNotFound(ResourceNotFoundException ex) {
        return ErrorDto.builder().code("NOT_FOUND").message(ex.getMessage()).build();
    }

    @ExceptionHandler(value = ResourceAlreadyExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody ErrorDto resourceNotFound(ResourceAlreadyExistException ex) {
        return ErrorDto.builder().code("RESOURCE_ALREADY_EXIST").message(ex.getMessage()).build();
    }

    @ExceptionHandler(value = InvalidValueException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody ErrorDto resourceNotFound(InvalidValueException ex) {
        return ErrorDto.builder().code("RESOURCE_ALREADY_EXIST").message(ex.getMessage()).build();
    }
}