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
public class ShopClient {
    private WebTarget endpoint;
    private WebTarget singleTemplateEndpoint;
    private WebTarget activeEndpoint;


    public ShopClient(@Value("${rest-server.url}") String serverUrl) {
        var client = ClientBuilder.newClient().register(LoggingFeature.builder().level(Level.ALL).build());
        endpoint = client.target(serverUrl + "/api/v1/shops");
        singleTemplateEndpoint = endpoint.path("/{id}");
    }

    public void setActiveShop(Integer id) {
        activeEndpoint = singleTemplateEndpoint.resolveTemplate("id", id);
    }

    public ShopDto create(ShopDto shop) {
        return endpoint
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity(shop, MediaType.APPLICATION_JSON_TYPE), ShopDto.class);
    }

    public Optional<ShopDto> readOne() {
        var response = activeEndpoint.request(MediaType.APPLICATION_JSON_TYPE).get();
        if (response.getStatus() == 404)
            return Optional.empty();
        if (response.getStatus() == 200)
            return Optional.of(response.readEntity(ShopDto.class));
        throw new RuntimeException(response.getStatusInfo().getReasonPhrase());
    }

    public Collection<ShopDto> readAll() {
        ShopDto[] shopsArray = endpoint.request(MediaType.APPLICATION_JSON_TYPE).get(ShopDto[].class);
        return Arrays.asList(shopsArray);
    }

    public void updateOne(ShopDto shop) {
        var response = endpoint
                .request(MediaType.APPLICATION_JSON_TYPE)
                .put(Entity.entity(shop, MediaType.APPLICATION_JSON_TYPE));
        if(response.getStatus() > 200)
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

    public List<ItemDto> readOneItem() {
        var response = activeEndpoint.request(MediaType.APPLICATION_JSON_TYPE).get();
        if (response.getStatus() == 404)
            return null;
        if (response.getStatus() == 200)
            return response.readEntity(ShopDto.class).getItems();
        throw new RuntimeException(response.getStatusInfo().getReasonPhrase());
    }

    public void removeItem(Integer shopId, Integer itemId) {
        var path = "/" + shopId + "/items/" + itemId;
        var itemRemovalEndpoint = endpoint.path(path);

        var response = itemRemovalEndpoint.request(MediaType.APPLICATION_JSON_TYPE).delete();

        if(response.getStatus() >= 400)
            throw new RuntimeException(response.getStatusInfo().getReasonPhrase());
    }

    public void addItem(Integer shopId, Integer itemId) {
        var path = "/" + shopId + "/items/" + itemId;
        var itemAddEndpoint = endpoint.path(path);

        var response = itemAddEndpoint
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.json("{}"));
        if(response.getStatus() == 409) //Conflict
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Item already in shop");
        if(response.getStatus() >= 400)
            throw new RuntimeException(response.getStatusInfo().getReasonPhrase());
    }
}
