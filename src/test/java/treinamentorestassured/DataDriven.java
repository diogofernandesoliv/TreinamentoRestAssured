package treinamentorestassured;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Iterator;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class DataDriven {
    public DataDriven(){
        enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @DataProvider(name="dataPetObjectPrvider")
    public Object[] dataPetProvider(){
        Pet pet1 = new Pet(11111,
                new Category(11111, "felino"),
                "Shepherd",
                new String[]{"http://photogato.com/foto1"},
                new Tag[]{new Tag(11111, "Amarelo")},
                "Available");

        Pet pet2 = new Pet(22222,
                new Category(22222, "catioro"),
                "Auau",
                new String[]{"http://photocatioro.com/foto1"},
                new Tag[]{new Tag(22222, "Caramelo")},
                "Pending");

        Pet pet3 = new Pet(33333,
                new Category(33333, "ave"),
                "Fenix",
                new String[]{"http://photoave.com/foto1"},
                new Tag[]{new Tag(33333, "Azul")},
                "Available");

        return new Pet[]{pet1, pet2, pet3};
    }

    @DataProvider(name="petDataProviderCSV")
    public Iterator<Object []> petDataProviderCSV(){
        return Utils.csvProvider("src/test/java/org/example/petDataCSV.csv");
    }

    @DataProvider(name="dataUserObjectPrvider")
    public Object[] dataUserProvider(){
        User user1 = new User(999,
                "usuariodp01",
                "Joao",
                "Silva",
                "usuariodp01@gmail.com",
                "123456",
                "3199887766",
                1);

        User user2 = new User(888,
                "usuariodp02",
                "Maria",
                "Ferreira",
                "usuariodp02@gmail.com",
                "abcdef",
                "3166554433",
                2);

        User user3 = new User(777,
                "usuariodp03",
                "Kleber",
                "Antonio",
                "usuariodp03@gmail.com",
                "1a2b3c",
                "3131313131",
                3);

        User user4 = new User(666,
                "usuariodp04",
                "Lais",
                "Gomes",
                "usuariodp04@gmail.com",
                "!@#$%",
                "3100000000",
                4);

        return new User[]{user1, user2, user3, user4};
    }

    @Test(dataProvider = "dataPetObjectPrvider")
    public void cadastrarPetComSucesso1(Pet petData){
        given()
                .baseUri("http://petstore.swagger.io/v2")
                .basePath("/pet")
                .header("content-type", "application/json")
                .body(petData)
                .when()
                .post()
                .then()
                .statusCode(200)
                .body("id", equalTo(petData.getId()),
                        "category.id", equalTo(petData.getCategory().getId()),
                        "category.name", equalTo(petData.getCategory().getName()),
                        "name", equalTo(petData.getName()),
                        "photoUrls[0]", equalTo(petData.getPhotoUrls()[0]),
                        "tags[0].id", equalTo(petData.getTags()[0].getId()),
                        "tags[0].name", equalTo(petData.getTags()[0].getName()),
                        "status", equalTo(petData.getStatus()));
    }

    @Test(dataProvider = "petDataProviderCSV")
    public void cadastrarPetComSucesso(String[] petData){
        Pet pet = new Pet(Integer.parseInt(petData[0]),
                new Category(Integer.parseInt(petData[1]), petData[2]),
                petData[3],
                new String[]{petData[4]},
                new Tag[]{new Tag(Integer.parseInt(petData[5]), petData[6])},
                petData[7]);

        given()
                .baseUri("http://petstore.swagger.io/v2")
                .basePath("/pet")
                .header("content-type", "application/json")
                .body(pet)
                .when()
                .post()
                .then()
                .statusCode(200)
                .body("id", equalTo(Integer.parseInt(petData[0])),
                        "category.id", equalTo(Integer.parseInt(petData[1])),
                        "category.name", equalTo(petData[2]),
                        "name", equalTo(petData[3]),
                        "photoUrls[0]", equalTo(petData[4]),
                        "tags[0].id", equalTo(Integer.parseInt(petData[5])),
                        "tags[0].name", equalTo(petData[6]),
                        "status", equalTo(petData[7]));
    }

    @Test(dataProvider = "dataUserObjectPrvider")
    public void cadastrarUserComSucesso(User userData){
        given()
                .baseUri("http://petstore.swagger.io/v2")
                .basePath("/user")
                .header("content-type", "application/json")
                .body(userData)
                .when()
                .post()
                .then()
                .statusCode(200);
    }


}
