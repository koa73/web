package ru.phone4pay.web.rest.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.http.HttpStatus;

/**
 *
 * Created by OAKutsenko on 12.10.2017.
 */

public class WebApiException extends Exception {

    @JsonIgnoreProperties(value =
            {"cause", "stackTrace", "localizedMessage", "suppressed", "message", "status"},
            ignoreUnknown=true)

    private final HttpStatus status = HttpStatus.BAD_REQUEST;

    private int resultCode;

    private String errMsg;

    private String causeReason;


    public WebApiException (){}

    public WebApiException (int resultCode, String errMsg){
        this.resultCode = resultCode;
        this.errMsg = errMsg;
        this.causeReason = null;
    }

    public WebApiException (int resultCode, String errMsg, String causeReason){
        this.resultCode = resultCode;
        this.errMsg = errMsg;
        this.causeReason = causeReason;
    }

    public String getCauseReason() {
        return causeReason;
    }

    public String getErrMsg() {
        return errMsg;
    }

    @Override
    public String toString() {

        return "{"+
                "code='"+resultCode+
                ", error='"+errMsg +
                ", causeReason='"+causeReason +
                "'}";
    }

}
