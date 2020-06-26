package treinamentorestassured;

import io.restassured.http.Header;
import io.restassured.http.Headers;
import org.testng.annotations.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;

public class TreinamentoRestAssured {

    @Test
    public void helloWorld() {
        given().
                baseUri("http://petstore.swagger.io/v2").
                basePath("/pet/{petId}").
                pathParam("petId", 99998).
                when().
                get().
                then().
                statusCode(200);
    }

    @Test
    public void TesteComHeaders(){
        //forma simples de add o header
        given().header("content-type", "application/json").
                header("Authorization", "bearer ey9659....");

        //forma com objeto MAP - fica mais facil de acompanhar futuramente
        Map<String, Object> headersMap = new HashMap<String, Object>();
        headersMap.put("content-type", "application/json");
        headersMap.put("Authorization", "bearer ey541564...");

        given().headers(headersMap);

        //forma com objetos header e headers do RESTASSURED
        Header header1 = new Header("content-type", "application/json");
        given().header(header1);

        Header header2 = new Header("Authorization", "bearer ey1351435...");
        given().header(header1).header(header2);

        List<Header> headerList = new ArrayList<>();
        headerList.add(header1);
        headerList.add(header2);

        Headers headers = new Headers(headerList);
        given().headers(headers);
    }

    @Test
    public void testeComCookie(){
        given().cookie("isso é um cookie").
                cookie("isso é outro cookie");
    }

    @Test
    public void testeComAutenticacao(){
        given().auth().basic("usuario", "senha");
        given().auth().oauth2("token oauth2");
        given().auth().form("usuario", "senha");
    }

    @Test
    public void testeComPathParameter(){
        given().pathParam("petId", 99998);
    }

    @Test
    public void testeComQueryParameter(){
        given().queryParam("status", "available"); // somente um valor para status
        given().queryParam("status", "available", "sold", "pending"); //mais de um valor para status

        //com Map Object
        Map<String, Object> queryParamsMap = new HashMap<String, Object>();
        queryParamsMap.put("status", "available");
        queryParamsMap.put("tag", "amarelo");

        given().queryParams(queryParamsMap);
    }

    @Test
    public void testeComFormParameter(){
        given().header("content-type", "multpart/formdata").
                formParam("chave", "valor");
        given().header("content-type", "multpart/formdata").
                multiPart("chave", "valor");
        given().header("content-type", "multpart/formdata").
                multiPart("arquivo", new File("src/test/java/org/example/AppTest.java")); //upload de arquivo
    }

    @Test
    public void testeComBodyParameterEmObjetoJAva(){
        Pet pet = new Pet();
        pet.setId(99998);

        Category category = new Category();
        category.setId(99998);
        category.setName("felino");

        pet.setCategory(category);
        pet.setName("shepherd");

        String[] photoUrls = new String[]{"http://fotosdegato.com.br/foto1.png", "http://fotosdegato.com.br/foto2.png"};
        pet.setPhotoUrls(photoUrls);

        Tag tag1 = new Tag();
        tag1.setId(99998);
        tag1.setName("Sem raça definida");

        Tag tag2 = new Tag();
        tag2.setId(9999);
        tag2.setName("Amarelo");

        Tag[] tags = new Tag[]{tag1, tag2};

        pet.setTags(tags);

        pet.setStatus("Available");

        given().body(pet);
    }
}
