package net;

import java.util.*;

public class Receptor extends Node {
	public String name; 
	public Integer Node_index;
	public Integer Node_Type = 0;
	public int activated = 0;
	
	public ArrayList<Node> conceptorList = new ArrayList<Node>();
	/* ��������� �������� ��� ���������. 
	 * ̳����� ������ �ᒺ��� � ����� �������� ��������.*/
	
	public Receptor(String name, Integer Node_index){
		this.name = name;
		this.Node_index = Node_index;
	}
	public Receptor(String name, Integer Node_index, ArrayList<Node> conceptorList){
		this.name = name;
		this.Node_index = Node_index;
		this.conceptorList = conceptorList;
	}
	
	public Receptor setConceptorList(ArrayList<Node> conceptorList) {
        this.conceptorList = conceptorList;
        return this;
    }
	public ArrayList<Node> getConceptorList() {
        return this.conceptorList;
    }
	public void setActive(){
		this.activated = 1;
	}
	public void setInactive(){
		this.activated = 0;
	}
}
