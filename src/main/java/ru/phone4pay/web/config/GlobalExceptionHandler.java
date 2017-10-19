package ru.phone4pay.web.config;

//import com.netflix.hystrix.exception.HystrixRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ru.phone4pay.web.rest.exception.WebApiException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * Created by OAKutsenko on 09.10.2017.
 */
@ControllerAdvice
@Component
public class GlobalExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map handle(MethodArgumentNotValidException exception) {

        return error(exception.getBindingResult().getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList()));
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String exception(final HttpServletRequest request, final Throwable throwable, final Model model) {

        log.error("Exception during execution of SpringSecurity application", throwable);
        //log.error("--->> "+ messages.get("error.data", request.getParameter("lang")));

        String errorMessage = (throwable != null ? throwable.getMessage() : "");

        if (throwable instanceof ConstraintViolationException){

            errorMessage = ".data";

        }
        /*
        else if (throwable instanceof HystrixRuntimeException) {

            errorMessage = getReceivedErrorMsg(throwable.getCause().getMessage());

        } */
        else if (throwable instanceof MissingServletRequestParameterException){

            errorMessage = ".parameter";

        } else if (throwable instanceof WebApiException){

            errorMessage = ((WebApiException) throwable).getErrMsg();
        }
        model.addAttribute("prefix", errorMessage);
        return "error";
    }

    private Map error(Object message) {
        return Collections.singletonMap("error", message);
    }

    private String getReceivedErrorMsg(String error){

        final String errCode = error.replaceAll(".*\n*.*[errCode:|status]\\s(\\d{3}).*", "$1");
        String result = "";

        switch (errCode){

            case "128":
                result = ".wrong";
                break;

            case "401":
                result = ".unavailable";
                break;

            case "403":
                result = ".unavailable";
                break;

        }
        return result;
    }

    @InitBinder
    public void dataBinding(WebDataBinder binder, HttpServletRequest request) {


        if (request.isUserInRole("ROLE_WEB_USER")){

            log.error("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            binder.bind(new MutablePropertyValues(Collections.singletonMap(
                    "user", request.getUserPrincipal().getName())));

        }
    }
}
