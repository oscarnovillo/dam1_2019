package gui.controllers;

import com.github.javafaker.Faker;
import dao.modelo.FormatoPelicula;
import dao.modelo.Pelicula;
import dao.modelo.Socio;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.controlsfx.tools.ValueExtractor;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import org.controlsfx.validation.decoration.StyleClassValidationDecoration;
import servicios.ServiciosVideoclub;

import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class CrearSocioController implements Initializable {

  @FXML
  private ListView<String> list;
  @FXML
  private ComboBox combo;
  ValidationSupport validationSupport;
  @FXML
  private TextField textDni;
  @FXML
  private TextField textNombre;
  @FXML
  private TextField textDireccion;
  @FXML
  private TextField textTelefono;
  @FXML
  private TextField textEdad;
  private Alert a;

  public void setTextDni(String texto) {
    this.textDni.setText(texto);
  }

  private void configuracionValidacion() {

    combo.getItems().add("primero");
    list.getItems().add("list");
    validationSupport = new ValidationSupport();
    validationSupport.setErrorDecorationEnabled(false);
    validationSupport.setValidationDecorator(new StyleClassValidationDecoration("error", "warning"));

    validationSupport.registerValidator(textDni, Validator.createEmptyValidator("dni no vacio"));
    //validationSupport.registerValidator(list, Validator.createEmptyValidator("list no vacio"));
    ValueExtractor.addObservableValueExtractor(c -> c == list,
        c -> ((ListView) c).getSelectionModel().selectedItemProperty());
    validationSupport.registerValidator(list, Validator.createPredicateValidator(o -> o!= null ,"list view sin seleccion"));

    validationSupport.registerValidator(textEdad, Validator.combine(
        Validator.createEmptyValidator("edad: Tiene q tener texto"),
        Validator.createPredicateValidator(o ->
            ((String) o).chars().allMatch(Character::isDigit)
                , "edad: solo numeros")));

  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    a = new Alert(Alert.AlertType.INFORMATION);
    configuracionValidacion();
  }


  private String comprobarSocioGUI() {
    validationSupport.setErrorDecorationEnabled(true);
    if (!validationSupport.getValidationResult().getMessages().isEmpty()) {

      return validationSupport.getValidationResult().getMessages()
          .stream()
          .map(validationMessage -> validationMessage.getText())
          .collect(Collectors.joining("\n"));

    }
    return null;

//    if (!textDni.getText().isEmpty() && !textNombre.getText().isEmpty()
//        && !textTelefono.getText().isEmpty() && !textEdad.getText().isEmpty()) {
//
//      textEdad.getText().chars().allMatch(Character::isDigit);
//
//      try {
//        Double.parseDouble(textEdad.getText());
//      } catch (Exception e) {
//        return "Edad no es un numero";
//      }
//    } else {
//      return "algun campo vacio";
//    }

  }

  @FXML
  private void crearSocio(ActionEvent actionEvent) {
    ServiciosVideoclub sv = new ServiciosVideoclub();
    String creado = "";

    String error = comprobarSocioGUI();
    if (error == null) {
      creado = sv.addSocio(null);
      creado = sv.addSocio(new Socio(textDni.getText(), textNombre.getText(), textDireccion.getText(),
          textTelefono.getText(), Integer.parseInt(textEdad.getText())));
      if (creado.isEmpty()) {
        a.setContentText("creado");
      } else {
        a.setContentText(creado);
      }
    } else {
      a.setContentText(error);
    }
    a.showAndWait();
  }

  @FXML
  private void limpiar(ActionEvent actionEvent) {
    textDni.setText("");
    textNombre.setText("");
    textDireccion.setText("");
    textTelefono.setText("");
    textEdad.setText("");
  }

  public void rellenarConFaker(ActionEvent actionEvent) {
    Faker f = new Faker();
    String dni = f.phoneNumber().extension();
    String nombre = f.gameOfThrones().character();
    String direccion = f.gameOfThrones().city();
    String tel = f.phoneNumber().toString();
    int edad = f.number().numberBetween(1, 99);
    textDni.setText(dni);
    textNombre.setText(nombre);
    textDireccion.setText(direccion);
    textTelefono.setText(tel);
    textEdad.setText("" + edad);

  }
}
