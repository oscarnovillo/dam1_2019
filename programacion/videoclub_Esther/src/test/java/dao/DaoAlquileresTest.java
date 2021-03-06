package dao;

import dao.modelo.Alquiler;
import dao.modelo.Producto;
import dao.modelo.Socio;
import dao.modelo.Videojuego;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Given un alquiler")
class DaoAlquileresTest {

  DaoAlquileres dao;
  Alquiler alquilerEsperado;

  @BeforeAll
  void setupAll()
  {
    dao = new DaoAlquileres();
    Socio s = new Socio("koko","asd","asd","aasd",12);
    Producto p = new Videojuego("as",12,"asd","asd");
    alquilerEsperado =  new Alquiler(LocalDateTime.of(2000,1,1,1,1),s,p);

  }

  @BeforeEach
  void setUp() {


  }

  @AfterEach
  void tearDown() {
 //   dao.borrarAlquiler(alquiler);
  }

  @AfterAll
  void limpiar()
  {
    dao.borrarAlquiler(alquilerEsperado);
  }


  //ParametrizedTest
  @Test
  @Order(1)
  @DisplayName("when add aquiler then se añade")
  void addAlquiler() {
    //Given
    //when
    boolean resultado = dao.addAlquiler(alquilerEsperado);

    //Then
    assertThat(resultado).as("alquiler añadido").isTrue();
  }

  @Test
  @Order(2)
  @DisplayName("when add aquiler que ya existe then no se añade")
  void addAlquilerDeSocioExiste() {
    //Given


    //when
    boolean resultado = dao.addAlquiler(alquilerEsperado);

    //Then
    assertFalse(resultado);

  }

  @Test
  @Order(3)
  @DisplayName("when pido alquiler de socio then lo devuelve")
  void addAlquilerComprobarExiste() {
    //Given


    //when
    Alquiler a = dao.alquilerSocio("koko");

    //Then
    assertThat(a).isSameAs(alquilerEsperado);
  }



  @Test
  void borrarAlquiler() {


  }

  @Test
  @Order(4)
  void alquilerSocio() {
    //Given

    //when
    Alquiler alquilerActual  = dao.alquilerSocio("koko");
    // And
    Alquiler alquilerActual2 = dao.alquilerSocio("lll");

    //then
    assertEquals(alquilerActual,alquilerEsperado);
    //And
    assertNull(alquilerActual2);

  }


}