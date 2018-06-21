package net;

import java.util.*;

public class Conceptor extends Node {
	public String node1; 
	public ArrayList<String> receptors;
	public Integer Node_Type = 1;
	public int activated = 0;
	
	public Set<Integer> receptorSet = new HashSet<Integer>();
	public ArrayList<Node> receptorList = new ArrayList<Node>();
	
	public Conceptor(String node1, ArrayList<String> receptors)
    {
        this.node1 = node1;
        this.receptors = receptors;
    }
	
	public Conceptor setNode1(String node1) {
        this.node1 = node1;
        return this;
    }
	public String getNode1(){
		return this.node1;
	}
	public Conceptor setReceptors(ArrayList<Node> receptorList) {
        this.receptorList = receptorList;
        return this;
    }
	public ArrayList<Node> getReceptors() {
        return this.receptorList;
    }
	public Conceptor setReceptorSet(Set<Integer> receptorSet) {
        this.receptorSet = receptorSet;
        return this;
    }
	public Set<Integer> getReceptorSet() {
        return this.receptorSet;
    }
	public Conceptor setReceptorList(ArrayList<Node> receptorList) {
        this.receptorList = receptorList;
        return this;
    }
	public ArrayList<Node> getReceptorList() {
        return this.receptorList;
    }
	public void setActive(){
		this.activated = 1;
	}
	public void setInactive(){
		this.activated = 0;
	}
}
