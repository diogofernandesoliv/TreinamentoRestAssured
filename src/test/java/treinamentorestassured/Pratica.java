package treinamentorestassured;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.*;

public class Pratica {
    JSONObject pet1 = new JSONObject();
    JSONObject category1 = new JSONObject();
    JSONObject tag1 = new JSONObject();
    JSONObject tag2 = new JSONObject();
    JSONArray photoUrls1 = new JSONArray();
    JSONArray tags1 = new JSONArray();

    JSONObject pet2 = new JSONObject();
    JSONObject category2 = new JSONObject();
    JSONObject tag3 = new JSONObject();
    JSONObject tag4 = new JSONObject();
    JSONArray photoUrls2 = new JSONArray();
    JSONArray tags2 = new JSONArray();

    JSONObject order = new JSONObject();

    public Pratica(){
        //POST
        pet1.put("id", 9900);

        category1.put("id", 900);
        category1.put("name", "ave");
        pet1.put("category", category1);

        pet1.put("name", "Mordecai");

        photoUrls1.add("http://fotosdeave.com.br/foto1.png");
        photoUrls1.add("http://fotodeave.com.br/foto2.png");
        pet1.put("photoUrls", photoUrls1);

        tag1.put("id", 90);
        tag1.put("name", "Gaio-azul");
        tag2.put("id", 91);
        tag2.put("name", "Azul");
        tags1.add(tag1);
        tags1.add(tag2);
        pet1.put("tags", tags1);

        pet1.put("status", "pending");

        //PUT
        pet2.put("id", 9901);

        category2.put("id", 901);
        category2.put("name", "ave");
        pet2.put("category", category2);

        pet2.put("name", "Papaleguas");

        photoUrls2.add("http://fotosdeave.com.br/foto5.png");
        photoUrls2.add("http://fotodeave.com.br/foto6.png");
        pet2.put("photoUrls", photoUrls2);

        tag3.put("id", 92);
        tag3.put("name", "Cuco-da-Terra Californiano");
        tag4.put("id", 93);
        tag4.put("name", "Marrom");
        tags2.add(tag3);
        tags2.add(tag4);
        pet2.put("tags", tags2);

        pet2.put("status", "available");

        order.put("id", 99900);
        order.put("petId", 9900);
        order.put("quantity", 1);
        order.put("shipDate", "2020-07-01T01:42:16.953Z");
        order.put("status", "placed");
        order.put("complete", true);
    }

    @Test
    public void pesquisarPetInexistente(){
        given()
                .baseUri("http://petstore.swagger.io/v2")
                .basePath("/pet/{petId}")
                .pathParam("petId", 50000)
                .header("content-type", "application/json")
        .when()
                .get()
        .then()
                .statusCode(404)
                .body("code", equalTo(1),
                        "type", equalTo("error"),
                        "message", equalTo("Pet not found"));
    }

    @Test
    public void pesquisarPetsComStatusPending(){
        given()
                .baseUri("https://petstore.swagger.io/v2")
                .basePath("/pet/findByStatus")
                .header("content-type", "application/json")
                .queryParam("status", "pending")
        .when()
                .get()
        .then()
                .statusCode(200)
                .body("status", hasItem(hasEntry("status", "pending")));
    }

    @Test
    public void AtualizarDadosPetInexistente()
    {
        given()
                .baseUri("https://petstore.swagger.io/v2")
                .basePath("/pet")
                .header("content-type", "application/json")
                .body("{}")
                .when()
                .put()
                .then()
                .statusCode(200)
                .body("id", equalTo("2671912896705030996"));
    }

    @Test
    public void RealizarRequisicaoMetodoInvalido()
    {
        given()
                .baseUri("https://petstore.swagger.io/v2")
                .basePath("/pet/all")
                .header("content-type", "application/json")
                .when()
                .get()
                .then()
                .statusCode(404);
        //Método na API é referente ao GET, PUT, POST, PATHC
    }

    @Test
    public void CadastrarNovoPedidoPet()
    {
        given()
                .baseUri("https://petstore.swagger.io/v2")
                .basePath("/store/order")
                .header("content-type", "application/json")
                .body(pet1)
        .when()
                .post()
        .then()
                .statusCode(200)
                .body("id", equalTo(99900),
                        "pet.id", equalTo(9900),
                        "quantity", equalTo("1"),
                        "shipDate", equalTo("2020-07-01T01:42:16.953Z"),
                        "status", equalTo("placed"),
                        "complete", equalTo(true));
    }

    @Test
    public void AtualizarDadosPetExistenteComSucesso()
    {
        given()
                .baseUri("https://petstore.swagger.io/v2")
                .basePath("/pet")
                .header("content-type", "application/json")
                .body(pet2)
        .when()
                .put()
        .then()
                .statusCode(200)
                .body("id", equalTo(9901),
                        "category.id", equalTo(901),
                        "category.name", equalTo("ave"),
                        "name", equalTo("Papaleguas"),
                        "photoUrls[0]", equalTo("http://fotosdeave.com.br/foto5.png"),
                        "photoUrls[1]", equalTo("http://fotodeave.com.br/foto6.png"),
                        "tags[0].id", equalTo(92),
                        "tags[0].name", equalTo("Cuco-da-Terra Californiano"),
                        "tags[1].id", equalTo(93),
                        "tags[1].name", equalTo("Marrom"),
                        "status", equalTo("available"));
    }

    //Pesquisar por um pet inexistente (GET /pet/{petId})
    //Pesquisar por pets com status “pending” (GET /pet/findByStatus)public void
    //Cadastrar novo pedido de pet com sucesso (POST /store/order)
    //Atualizar dados de um pet existente (PUT /pet)
    //Atualizar dados de um pet informando id inválido (PUT /pet)
    //Realizar requisição “/pet” informando método inválido
}