package com.victxl.api01.event.listener;

import com.victxl.api01.event.RecursoCriadoeEvent;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;


@Component
public class RecursoCriadoListener implements ApplicationListener<RecursoCriadoeEvent> {

    @Override
    public void onApplicationEvent(RecursoCriadoeEvent recursoCriadoeEvent){
        HttpServletResponse response = recursoCriadoeEvent.getRespose();
        Long id = recursoCriadoeEvent.getId();

        adicionarHeaderLocation(response, id);


    }

    private static void adicionarHeaderLocation(HttpServletResponse response, Long id) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(id).toUri();
        response.setHeader("Location", uri.toASCIIString());
    }
}
