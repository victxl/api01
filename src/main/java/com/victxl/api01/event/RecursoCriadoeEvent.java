package com.victxl.api01.event;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationEvent;

public class RecursoCriadoeEvent extends ApplicationEvent {

    private static final long seralVersionUID = 1L;

    private HttpServletResponse respose;
    private Long id;

    public RecursoCriadoeEvent(Object source, HttpServletResponse response, Long id) {
        super(source);
        this.respose = response;
        this.id = id;
    }

    public HttpServletResponse getRespose() {
        return respose;
    }

    public Long getId() {
        return id;
    }
}
