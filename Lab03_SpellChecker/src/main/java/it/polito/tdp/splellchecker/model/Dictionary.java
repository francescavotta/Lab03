package it.polito.tdp.splellchecker.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Dictionary {

	private String lingua;
	private Set <String> dizionario;

	public Dictionary() {
		this.lingua = null;
		this.dizionario = new HashSet<String>();
	}
	
	public void loadDictionary(String languages) {
		String lingua = languages.toLowerCase();
		if(lingua.equals("english")) {
			this.lingua = "english";
			try {
				FileReader fr = new FileReader("src/main/resources/English.txt");
				BufferedReader br = new BufferedReader(fr);
				String word;
				while((word= br.readLine())!=null) {
					dizionario.add(word);
				}
				br.close();
			}catch(IOException e) {
				System.out.print("Errore lettura da file");
				return;
			}
		}
		else if(lingua.equals("italian")) {
			this.lingua = "italian";
			try {
				FileReader fr = new FileReader("src/main/resources/Italian.txt");
				BufferedReader br = new BufferedReader(fr);
				String word;
				while((word= br.readLine())!=null) {
					dizionario.add(word);
				}
				br.close();
			}catch(IOException e) {
				System.out.print("Errore lettura da file");
				return;
			}
		}
	}
	
	
	public List <RichWord> spellCheckTest (List<String> inputText){
		List <RichWord> listaStampa = new LinkedList<RichWord>();
		
		for(String i:inputText) {
			RichWord rw = new RichWord(i);
			
			if(dizionario.contains(i)) {
				rw.setCorrect(true);
			}else {
				rw.setCorrect(false);
			}
			
			
			listaStampa.add(rw);
		}
		
		return listaStampa;
	}

	public String stampaErrate(List<RichWord> lista) {
		String stampa = "";
		for(RichWord rw: lista) {
			if(rw.isCorrect()==false)
				stampa += rw.getParola()+"\n";
		}
		return stampa;
	}

	public int numErrori(List<RichWord> lista) {
		int conteggio = 0;
		for(RichWord rw: lista) {
			if(rw.isCorrect()==false)
				conteggio ++;
		}
		return conteggio;
	}
	
	public List <RichWord> spellCheckTestLinear (List<String> inputText){
		List <RichWord> lista = new LinkedList<RichWord>();
		//iterare su ogni valore del dizionario
		for(String s: inputText) {
			RichWord rw = new RichWord(s);
			for(String d : this.dizionario) {
				if(d.equals(s)) {
					rw.setCorrect(true);
					break;
				}else {
					rw.setCorrect(false);
				}
			}
			lista.add(rw);
		}
		return lista;
	}
	
	public List <RichWord> spellCheckTestDichotomic(List<String> inputText){
		List <RichWord> lista = new LinkedList<RichWord>();
		for(String s: inputText) {
			
			List <String> sottolista = new LinkedList <String>(dizionario);
			Collections.sort(sottolista);
			//List <String> dizionario2= new LinkedList <String>(dizionario);
			RichWord rw = new RichWord(s);
			lista.add(rw);
			
			while(sottolista.size()!=0 && !rw.isCorrect())
			{
				int indiceMin=0;
				int indiceMax= sottolista.size()-1;
				int indice = ((indiceMax-indiceMin)/2);//+indiceMin;
				String confronto = sottolista.get(indice);
				if(s.equals(confronto))
				{
					rw.setCorrect(true);
					break;
				}else if(s.compareTo(confronto)<0) {
					sottolista = sottolista.subList(0, indice+1);
					indiceMax = indice;
				}else {
					sottolista = sottolista.subList(indice, sottolista.size()-1);//cambiare indice!
					indiceMin = indice;
					//System.out.println(sottolista.size()+ " " + sottolista.get(0)+" " + sottolista.get(indiceMax));
				}
			}
		}
		return lista;
	}
}
