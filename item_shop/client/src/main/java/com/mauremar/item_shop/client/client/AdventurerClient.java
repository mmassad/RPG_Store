package com.mauremar.item_shop.client.client;

import com.mauremar.item_shop.client.dto.AdventurerDto;
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
import java.util.*;
import java.util.logging.Level;

@Component
public class AdventurerClient {
    private WebTarget endpoint;
    private WebTarget singleTemplateEndpoint;
    private WebTarget activeEndpoint;


    public AdventurerClient(@Value("${rest-server.url}") String serverUrl) {
        var client = ClientBuilder.newClient().register(LoggingFeature.builder().level(Level.ALL).build());
        endpoint = client.target(serverUrl + "/api/v1/adventurers");
        singleTemplateEndpoint = endpoint.path("/{id}");
    }

    public void setActiveAdventurer(Integer id) {
        activeEndpoint = singleTemplateEndpoint.resolveTemplate("id", id);
    }

    public AdventurerDto create(AdventurerDto adv) {
        return endpoint
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity(adv, MediaType.APPLICATION_JSON_TYPE), AdventurerDto.class);
    }

    public Optional<AdventurerDto> readOne() {
        var response = activeEndpoint.request(MediaType.APPLICATION_JSON_TYPE).get();
        if (response.getStatus() == 404)
            return Optional.empty();
        if (response.getStatus() == 200)
            return Optional.of(response.readEntity(AdventurerDto.class));
        throw new RuntimeException(response.getStatusInfo().getReasonPhrase());
    }

    public Collection<AdventurerDto> readAll() {
        AdventurerDto[] advsArray = endpoint.request(MediaType.APPLICATION_JSON_TYPE).get(AdventurerDto[].class);
        return Arrays.asList(advsArray);
    }

    public void updateOne(AdventurerDto adv) {
        var response = endpoint
                .request(MediaType.APPLICATION_JSON_TYPE)
                .put(Entity.entity(adv, MediaType.APPLICATION_JSON_TYPE));
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

    public List<ItemDto> readOneItem() {
        var response = activeEndpoint.request(MediaType.APPLICATION_JSON_TYPE).get();
        if (response.getStatus() == 404)
            return null;
        if (response.getStatus() == 200)
            return response.readEntity(AdventurerDto.class).getItems();
        throw new RuntimeException(response.getStatusInfo().getReasonPhrase());
    }

    public void removeItem(Integer adventurerId, Integer itemId) {
        var path = "/" + adventurerId + "/items/" + itemId;
        var itemRemovalEndpoint = endpoint.path(path);

        var response = itemRemovalEndpoint.request(MediaType.APPLICATION_JSON_TYPE).delete();

        if(response.getStatus() >= 400)
            throw new RuntimeException(response.getStatusInfo().getReasonPhrase());
    }

    public List<ItemDto> bestItem(Integer adventurerId, Integer shopId) {
        var path = "/" + adventurerId + "/shops/" + shopId;
        var itemBestEndpoint = endpoint.path(path);

        var response = itemBestEndpoint
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get();

        if(response.getStatus() >= 400)
            throw new RuntimeException(response.getStatusInfo().getReasonPhrase());

        ItemDto[] itemsArray = response.readEntity(ItemDto[].class);
        return Arrays.asList(itemsArray);
    }

    public void addItem(Integer advId, Integer itemId) {
        var path = "/" + advId + "/items/" + itemId;
        var itemAddEndpoint = endpoint.path(path);

        var response = itemAddEndpoint
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.json("{}"));
        if(response.getStatus() == 409) //Conflict
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Item already with adventurer");
        if(response.getStatus() >= 400)
            throw new RuntimeException(response.getStatusInfo().getReasonPhrase());
    }
}
