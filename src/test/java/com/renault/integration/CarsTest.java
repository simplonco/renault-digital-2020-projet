package com.renault.integration;

import com.renault.CarsApplication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonString;
import javax.json.JsonValue;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = CarsApplication.class, webEnvironment = RANDOM_PORT)
public class CarsTest extends AbstractIntegration {

    @Test
    public void should_GET_cars_brands_returns_all_brands() {
        // bdd
        Set<String> brands = getBrands();

        // http
        JsonReader response = get("cars/brands");
        JsonArray responseBrands = response.readArray();

        // assert
        assertEquals(brands.size(), responseBrands.size());
        responseBrands.stream()
                .map(jsonValue -> ((JsonString) jsonValue))
                .map(JsonString::getString)
                .forEach(brand -> assertTrue(brands.contains(brand)));
    }

    @Test
    public void should_GET_cars_by_brands_returns_cars_for_given_brand() {
        // bdd
        String brand = getBrands().stream().findFirst().orElseThrow();
        List<String> carModelsForBrand = getCarModelsForBrand(brand);

        // http
        JsonReader response = get(format("cars/brands/%s", brand));
        JsonArray responseCars = response.readArray();

        // assert
        assertEquals(carModelsForBrand.size(), responseCars.size());
        responseCars.stream()
                .map(JsonValue::asJsonObject)
                .map(jsonCar -> jsonCar.getString("model"))
                .forEach(model -> assertTrue(carModelsForBrand.contains(model)));
    }

    @Test
    public void should_POST_root_adds_new_car() {
        // http
        String ferrariBrand = "Ferrari";
        String ferrariModel = "F12";
        JsonObject ferrariF12 = Json.createObjectBuilder()
                .add("brand", ferrariBrand)
                .add("model", ferrariModel)
                .build();
        post("cars", ferrariF12);

        // bdd
        String brand = getBrands().stream()
                .filter(b -> b.equals(ferrariBrand))
                .findFirst()
                .orElseThrow();
        List<String> carModelsForBrand = getCarModelsForBrand(brand);

        // assert
        assertEquals(1, carModelsForBrand.size());
        assertTrue(carModelsForBrand.contains(ferrariModel));
    }

    @Test
    public void should_DELETE_root_removes_existing_car() {
        // bdd
        String brand = getBrands().stream().findFirst().orElseThrow();
        Integer idBrand = getCarIdsForBrand(brand).stream().findFirst().orElseThrow();

        // http
        delete(format("cars/%s", idBrand));

        // bdd
        Optional<Integer> idExists = getCarIdsForBrand(brand).stream()
                .filter(id -> id.equals(idBrand))
                .findFirst();

        // assert
        assertTrue(idExists.isEmpty());
    }

}
