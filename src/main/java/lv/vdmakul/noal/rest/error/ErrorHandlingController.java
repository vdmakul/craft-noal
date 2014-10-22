package lv.vdmakul.noal.rest.error;

import lv.vdmakul.noal.service.application.analyser.RiskAnalysisFailException;
import lv.vdmakul.noal.service.loan.LoanNotFoundException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ErrorHandlingController {

    @ExceptionHandler(RiskAnalysisFailException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    @ResponseBody
    public ErrorInfo handleRiskAnalysisFailException(HttpServletRequest req, RiskAnalysisFailException ex) {
        return new ErrorInfo(ex.getMessage(), ex.getErrorCode());
    }

    @ExceptionHandler(LoanNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorInfo handleLoanNotFoundException(HttpServletRequest req, LoanNotFoundException ex) {
        return new ErrorInfo(ex.getMessage(), HttpStatus.NOT_FOUND.toString());
    }

    @ExceptionHandler(TypeMismatchException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorInfo handleTypeMismatchException(HttpServletRequest req, TypeMismatchException ex) {
        return new ErrorInfo(ex.getMessage(), HttpStatus.BAD_REQUEST.toString());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorInfo handleAnyException(HttpServletRequest req, Exception ex) {
        return new ErrorInfo(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), HttpStatus.INTERNAL_SERVER_ERROR.toString());
    }
}
