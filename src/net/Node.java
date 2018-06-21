package net;

import java.util.*;

public class Node { //implements Node{
	
	public String node_guid;
	public String node_name; 
	public Integer node_index;
	public String nClass;
	public int activated = 0; //default value = 0
	
	public Integer Node_Type; // 0 � Receptor;1 � Conceptor; 2 � Class
	
	public Set<Integer> receptorSet = new HashSet<Integer>();
	/* set of Node_index. 
	 * ��������� �� ����� ����� ������� � �������� �����. 
	 * ��� �������� �������� �������� ����� �� ���������. 
	 * ��� ������������� ����� ������������ 1 ��� �������� �����. 
	 * ��� ��������� �� �������*/
	//----------//
	public ArrayList<Node> receptorList = new ArrayList<Node>();
	/* ��������� ��������� Receptor_set.
	 *  ������� ��� ���������� ������� �� ������ ��������� 
	 *  �� ��������� ����� ������� ��������� �������� �����. 
	 *  ��� ��������� �� �������*/
	//----------//
	public Integer NodeLevel = receptorList.size();
	/* �������� ������� ������� ��������� � ������ Receptor_list. 
	 * ϳ��� ��������� ���������� ����� � �������� �����, 
	 * ���� ������ � ��������� ���� �����������, �� ������� �� Receptorlist.Count  
	 * ������ ������� ��������� ������ ����������. 
	 * Receptorlist �� Receptorset ��� ����� �� ���������*/
	//----------//
	public ArrayList<Node> conceptorList = new ArrayList<Node>();
	/* ��������� �������� ��� ���������. 
	 * ̳����� ������ �ᒺ��� � ����� �������� ��������.*/
	//----------//
	public Set<Integer> downNodeSet = new HashSet<Integer>();
	/*downNodeSet: set of node_index. 
	 * ������� Node � ���������� ��������� �����, 
	 * � ����� �������� ����� ������������� 璺�����.
	 * only for class nodes*/
	public Set<Integer> upperNodeSet = new HashSet<Integer>();
	/*upperNodeSet: set of node_index. 
	 * ������� Node � ������������ ��������� �����, 
	 * � ����� �������� ����� ������������� 璺�����.
	 * for elements whose name doesn't start with $ and aren't receptors*/
	//----------//
	public Node() {}
	public Node(String node_name, String node_guid, Integer NodeType){
		this.node_name = node_name;
		this.node_guid = node_guid;
		this.Node_Type = NodeType;
	}
	public Node(String node_name, String node_guid, String nClass, Integer index){
		this.node_name = node_name;
		this.node_guid = node_guid;
		this.nClass = nClass;
		this.node_index = index;
	}
	public Node(String node_name, String node_guid, Integer NodeType, ArrayList<Node> conc_list){
		this.node_name = node_name;
		this.node_guid = node_guid;
		this.Node_Type = NodeType;
		this.conceptorList = conc_list;
	}
	public Node(String node_name, String node_guid, Integer NodeType, ArrayList<Node> rec_list, HashSet<Integer> rec_set){
		this.node_name = node_name;
		this.node_guid = node_guid;
		this.Node_Type = NodeType;
		this.receptorList = rec_list;
		this.receptorSet = rec_set;
	}

	public void addDownNode(Node node) {

    }

    public void addUpperNode(Node node) {

    }

	public Node getNode(String Name, Set<Node> set){
		Node res = new Node();
		for (Node s : set) {
			if(s.node_name.equals(Name))
				{ res = s;}
		}
		return res;
	}
	public Node getNode(Integer Node_index, Set<Node> set){
		Node res = new Node();
		for (Node s : set) {
			if(s.node_index.equals(Node_index))
				{ res = s;}
		}
		return res;
	}
	//----------//
	public Node setGuid(String node_guid) {
        this.node_guid = node_guid;
        return this;
    }
	public String getGuid(){
		return this.node_guid;
	}
	public Node setNodeName(String node_name) {
		this.node_name = node_name;
        return this;
    }
	public String getNodeName(){
		return this.node_name;
	}
	public Node setNodeIndex(Integer node_index) {
		this.node_index = node_index;
        return this;
    }
	public Integer getNodeIndex(){
		return this.node_index;
	}
	public Node setNodeType(Integer node_type) {
		this.Node_Type = node_type;
        return this;
    }
	public Integer getNode_Type(){
		return this.Node_Type;
	}
	public String getNClass() {
        return nClass;
    }

    public Node setNClass(String nclass) {
        this.nClass = nclass;
        return this;
    }
	public void setActive(){
		this.activated = 1;
	}
	public void setInactive(){
		this.activated = 0;
	}
	public Node setConceptors(ArrayList<Node> conceptorList) {
		this.conceptorList = conceptorList;
        return this;
    }
	public ArrayList<Node> getConceptors() {
        return this.conceptorList;
    }
	public Node setReceptors(ArrayList<Node> receptorList) {
        this.receptorList = receptorList;
        return this;
    }
	public ArrayList<Node> getReceptors() {
        return this.receptorList;
    }
	public Node setReceptorSet(Set<Integer> receptorSet) {
        this.receptorSet = receptorSet;
        return this;
    }
	public Set<Integer> getReceptorSet() {
        return this.receptorSet;
    }
}
