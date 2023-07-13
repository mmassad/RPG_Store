package com.mauremar.item_shop.client.client;

import com.mauremar.item_shop.client.dto.ItemDto;
import com.mauremar.item_shop.client.dto.ShopDto;
import org.glassfish.jersey.logging.LoggingFeature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;

@Component
public class ItemClient {
    private WebTarget endpoint;
    private WebTarget singleTemplateEndpoint;
    private WebTarget activeEndpoint;


    public ItemClient(@Value("${rest-server.url}") String serverUrl) {
        var client = ClientBuilder.newClient().register(LoggingFeature.builder().level(Level.ALL).build());
        endpoint = client.target(serverUrl + "/api/v1/items");
        singleTemplateEndpoint = endpoint.path("/{id}");
    }

    public void setActiveItem(Integer id) {
        activeEndpoint = singleTemplateEndpoint.resolveTemplate("id", id);
    }

    public ItemDto create(ItemDto item) {
        return endpoint
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity(item, MediaType.APPLICATION_JSON_TYPE), ItemDto.class);
    }

    public Optional<ItemDto> readOne() {
        var response = activeEndpoint.request(MediaType.APPLICATION_JSON_TYPE).get();
        if (response.getStatus() == 404)
            return Optional.empty();
        if (response.getStatus() == 200)
            return Optional.of(response.readEntity(ItemDto.class));
        throw new RuntimeException(response.getStatusInfo().getReasonPhrase());
    }

    public Collection<ItemDto> readAll() {
        ItemDto[] itemsArray = endpoint.request(MediaType.APPLICATION_JSON_TYPE).get(ItemDto[].class);
        return Arrays.asList(itemsArray);
    }

    public void updateOne(ItemDto item) {
        var response = endpoint
                .request(MediaType.APPLICATION_JSON_TYPE)
                .put(Entity.entity(item, MediaType.APPLICATION_JSON_TYPE));
        if(response.getStatus() >= 400)
            throw new BadRequestException(response.getStatusInfo().getReasonPhrase());
    }

    public void delete()
    {
        var response = activeEndpoint
                .request()
                .delete();
        if(response.getStatus() == 404)
            throw new ResponseStatusException((HttpStatus.NOT_FOUND));
    }
}
