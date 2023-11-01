package com.fomich.netdata.http.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice(basePackages = "com.fomich.netdata.http.rest") // чтобы был глобальный для всех rest контроллеров
public class RestControllerExceptionHandler extends ResponseEntityExceptionHandler {


}
