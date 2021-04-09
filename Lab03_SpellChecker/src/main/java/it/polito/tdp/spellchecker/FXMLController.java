package it.polito.tdp.spellchecker;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.splellchecker.model.Dictionary;
import it.polito.tdp.splellchecker.model.RichWord;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class FXMLController {
	Dictionary dizionario = new Dictionary();
	private String linguaScelta=null;
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> lingua;

    @FXML
    private TextArea testoDaCorreggere;

    @FXML
    private Button butCheck;

    @FXML
    private TextArea txtRisultato;

    @FXML
    private Label txtErrori;

    @FXML
    private Button butClear;

    @FXML
    private Label labSecondi;

    @FXML
    void clearAll(ActionEvent event) {
    	this.lingua.setDisable(false);
    	txtRisultato.clear();
    	testoDaCorreggere.clear();
    	this.txtErrori.setText("Il numero di errori è:  " );
    	
    }

    @FXML
    void controlloParole(ActionEvent event) {
    	
    	txtRisultato.clear();
    	
    	String linguaScelta = this.lingua.getValue();
    	String testo = testoDaCorreggere.getText();
    	
    	String testoSenzaCaratteriSpeciali = testo.toLowerCase().replaceAll("[.?,\\/#!$%\\^&\\*;:{}=\\-_()']","");
    	
    	if(linguaScelta==null) {
    		txtRisultato.setText("Scegliere la lingua");
    		return;
    	}
    	//this.lingua.setDisable(true);
    	dizionario.loadDictionary(linguaScelta);
    	
    	List <String> parole = new LinkedList<String>();
    	String[] paroleSplit = testoSenzaCaratteriSpeciali.split(" ");
    	for(int i= 0; i< paroleSplit.length; i++) {
    		parole.add(paroleSplit[i]);
    	}
    	long timeStart = System.nanoTime();
    	List <RichWord> stampa = this.dizionario.spellCheckTest(parole);
    	long timeEnd = System.nanoTime();
		float Millisecondi = ((timeEnd-timeStart)/1000000);
    	
		txtRisultato.setText(dizionario.stampaErrate(stampa));
    	
    	int numErr= dizionario.numErrori(stampa);
    	if(numErr==0)
    		txtRisultato.setText("Esatto!");
    	this.txtErrori.setText("Il numero di errori è: " + numErr);
    	this.labSecondi.setText("MILLIsecondi: " + Millisecondi);
    }

    @FXML
    void initialize() {
        assert lingua != null : "fx:id=\"lingua\" was not injected: check your FXML file 'Scene.fxml'.";
        assert testoDaCorreggere != null : "fx:id=\"testoDaCorreggere\" was not injected: check your FXML file 'Scene.fxml'.";
        assert butCheck != null : "fx:id=\"butCheck\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtRisultato != null : "fx:id=\"txtRisultato\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtErrori != null : "fx:id=\"txtErrori\" was not injected: check your FXML file 'Scene.fxml'.";
        assert butClear != null : "fx:id=\"butClear\" was not injected: check your FXML file 'Scene.fxml'.";
        assert labSecondi != null : "fx:id=\"labSecondi\" was not injected: check your FXML file 'Scene.fxml'.";

        lingua.setPromptText("Selezionare lingua");
        this.lingua.getItems().addAll("English","Italian");
    }

	public void setModel(Dictionary model) {
		
		this.dizionario = model;
		
	}

	
}



