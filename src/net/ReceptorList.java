package net;

public class ReceptorList {
	//public Node conc_node;
	public Conceptor conc;
	
	public ReceptorList(){}
	//public ReceptorList(Node n){
		//this.conc_node = n;
	//}
	public ReceptorList(Conceptor c){
		this.conc = c;
	}
	public String[] getReceptors(){
		//String[] arr = new String[this.conc.receptors.size()];
		//arr = this.conc.receptors.toArray(arr);
		return this.conc.receptors.toArray(new String[]{});
	}
}
