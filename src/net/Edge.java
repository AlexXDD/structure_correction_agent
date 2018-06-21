package net;

public class Edge {
	 public String guid;
	    private String node1;
	    private String node2;
	    private String group;

	    public Edge( String guid, String node1, String node2, String group)
	    {
	        this.group = group;
	        this.guid = guid;
	        this.node1 = node1;
	        this.node2 = node2;
	    }

	    public Edge( String guid, String node1, String node2)
	    {
	        this.group = "Part_of";
	        this.guid = guid;
	        this.node1 = node1;
	        this.node2 = node2;
	    }

	    public String getGuid() {
	        return guid;
	    }

	    public Edge setGuid(String guid) {
	        this.guid = guid;
	        return this;
	    }

	    public String getNode1() {
	        return node1;
	    }

	    public Edge setNode1(String node1) {
	        this.node1 = node1;
	        return this;
	    }

	    public String getNode2() {
	        return node2;
	    }

	    public Edge setNode2(String node2) {
	        this.node2 = node2;
	        return this;
	    }

	    public String getGroup() {
	        return group;
	    }

	    public Edge setGroup(String group) {
	        this.group = group;
	        return this;
	    }
}
