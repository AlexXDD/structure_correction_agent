package net;

import java.util.*;

public class Net{
	/*Net � ���������� ������.*/
	private static Net net_singleton = null;
	private Set<Node> nodeSet = new HashSet<Node>();
	//�������� ������� ��� (��� �����-�����) ����� �����
	private Set<Node> classNodeSet = new HashSet<Node>();
	//������� �����-����� �����
	private Set<Node> layerSet = new HashSet<Node>();
	//������� �����-���� �����
	private ArrayList<ArrayList<Node>> levelNodeList = new ArrayList<ArrayList<Node>>();
	//list for lists of levels?
	//-----------------------------//
	//public ArrayList <levelNodeList> Levels = new ArrayList <levelNodeList>();
	/*��� �����  Net ��� �������� ���� ���� �� ������      
	 * ArrayList <levelNodeList>,
        ���� ��  levelNodeList ���� �������� ������ - ������, 
        ������ ����� � � �������� � �����.
        � ����� ����� ������������� ��� ��������� ���������� � XML �����.
		� � ��� ��, �������� � ������, ����������� ����� 1.*/
	//������� ����� �� ������(����������� �� ����� ������� ��������� �����)
	
	private Net(){}
	//public Net(Set<Node> nodeSet, Set<Node> classNodeSet, Set<Node> layerSet, ArrayList<Node> LevelNodeList){
		//this.nodeSet = nodeSet;
		//this.classNodeSet = classNodeSet;
		//this.layerSet = layerSet;
		//this.levelNodeList = LevelNodeList;
	//}
	public static Net getInstance( ) {
		if(net_singleton == null) {
			net_singleton = new Net();
	      }
	      return net_singleton;
	   }
	public void addNodeByLevel(int level, Node node) {
		levelNodeList.get(level).add(node);
	}
	public void addNode(Node node){
		nodeSet.add(node);
	}
	public void addClassNode(Node node){
		classNodeSet.add(node);
	}
	public String getNodeName(Integer node_index){
		Node[] arr = nodeSet.toArray(new Node[nodeSet.size()]);
		String res = new String();
		for (int i = 0; i < nodeSet.size(); i++) 
		{
			if(arr[i].node_index == node_index)
				{ res = arr[i].node_name;}
		}
		return res;
	}
}
