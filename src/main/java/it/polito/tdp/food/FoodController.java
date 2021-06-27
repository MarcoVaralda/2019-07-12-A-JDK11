/**
 * Sample Skeleton for 'Food.fxml' Controller Class
 */

package it.polito.tdp.food;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.food.model.Food;
import it.polito.tdp.food.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FoodController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtPorzioni"
    private TextField txtPorzioni; // Value injected by FXMLLoader

    @FXML // fx:id="txtK"
    private TextField txtK; // Value injected by FXMLLoader

    @FXML // fx:id="btnAnalisi"
    private Button btnAnalisi; // Value injected by FXMLLoader

    @FXML // fx:id="btnCalorie"
    private Button btnCalorie; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimula"
    private Button btnSimula; // Value injected by FXMLLoader

    @FXML // fx:id="boxFood"
    private ComboBox<Food> boxFood; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	String numero = this.txtPorzioni.getText();
    	int N = 0;
    	try {
    		N = Integer.parseInt(numero);
    	}
    	catch(NumberFormatException nbe) {
    		this.txtResult.setText("Devi inserire un numero intero di porzioni!");
    		return;
    	}
    	
    	this.txtResult.setText(this.model.creaGrafo(N));
    	
    	this.boxFood.getItems().addAll(this.model.getVertici());
    }
    
    @FXML
    void doCalorie(ActionEvent event) {
    	Food scelto = this.boxFood.getValue();
    	if(scelto==null) {
    		this.txtResult.setText("Devi inserire un cibo!");
    		return;
    	}
    	this.txtResult.appendText(this.model.getAdiacenti(scelto));
    }

    @FXML
    void doSimula(ActionEvent event) {
    	String numero = this.txtK.getText();
    	int K = 0;
    	try {
    		K = Integer.parseInt(numero);
    	}
    	catch(NumberFormatException nbe) {
    		this.txtResult.setText("Devi inserire un numero intero K!");
    		return ;
    	}
    	
    	Food scelto = this.boxFood.getValue();
    	if(scelto==null) {
    		this.txtResult.setText("Devi inserire un cibo!");
    		return;
    	}
    	
    	this.txtResult.appendText(this.model.simula(K, scelto));
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtPorzioni != null : "fx:id=\"txtPorzioni\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtK != null : "fx:id=\"txtK\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnAnalisi != null : "fx:id=\"btnAnalisi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnCalorie != null : "fx:id=\"btnCalorie\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Food.fxml'.";
        assert boxFood != null : "fx:id=\"boxFood\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Food.fxml'.";
    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
