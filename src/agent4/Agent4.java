package agent4;

import byteZipArchiver.ByteZipArchiver;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import net.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Agent4 extends AgentBase {
    private static final String INPUT_FILE_PATH = "files\\input.xml";
    private static final String OUTPUT_FILE_PATH = "files\\output.xml";

    //FOR DEBUGGING ONLY
    public static void main(String[] args) {
        initNet();
    }

    protected void setup() {
        AID id = new AID("agent4", AID.ISLOCALNAME);

        /*  RECEIVING NETWORK   */
        blockingReceive(); //Blocks agent until the message appear in queue
         //Receives message
        ACLMessage msg = receive(); //Receiving message
        if (msg != null) {
            //Message processing
            byte[] content = msg.getByteSequenceContent(); //Getting message content
            try {
                ByteZipArchiver.UnzipBytes(content, INPUT_FILE_PATH); //Unzip content to Input.xml
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        /*-------------------------------------*/



        //TODO All network processing stuff



        /*  SENDING RESULTED NETWORK    */
        byte[] result;
        try {
            result = ByteZipArchiver.ZipBytes(OUTPUT_FILE_PATH);
            ReplyFiles(msg, result); //Sending network to coordinator
        } catch (IOException e) {
            e.printStackTrace();
        }



        doDelete(); // Termination method. Calls takeDown() method
    }



    // Put agent clean-up operations here
    protected void takeDown() {

    }
    public static void initNet() {
        //etwork reading
        //Reading Conceptors
        ArrayList<Conceptor> conceptors = new ArrayList<>();
        NetReader.readConceptors(conceptors, INPUT_FILE_PATH);

        //Reading Edges
        ArrayList<Edge> edges = new ArrayList<>();
        NetReader.readEdges(edges, INPUT_FILE_PATH);

        //Reading All nodes
        Set<Node> nodes = new HashSet<>();
        NetReader.readNodes(nodes, INPUT_FILE_PATH);


        //Network Initialization
        Net net = Net.getInstance();
        /*
        INITIALIZATION OF NODES upperNodeSet and downNodeSet
            For each node take name
                for each edge
                    if name of node1 same as current node
                    then we set to current node upperNodeSet index of node name of which equal to node1
                    else if name of node2 same as current node
                    then we set to current node downNodeSet index of node name of which equal to node1
         */
        for (Node node : nodes) {
            String nodeName = node.getNodeName();
            for (Edge edge : edges) {
                if (edge.getNode1().equals(nodeName)) {
                    Integer nodeIndex = node.getNode(edge.getNode2(), nodes).getNodeIndex();
                    node.upperNodeSet.add(nodeIndex);
                    System.out.println(node.getNodeName() + " ==> " + node.getNode(edge.getNode2(), nodes).getNodeName());
                }
                else if (edge.getNode2().equals(nodeName)) {
                    Integer nodeIndex = node.getNode(edge.getNode1(), nodes).getNodeIndex();
                    node.downNodeSet.add(nodeIndex);
                    System.out.println(node.getNodeName() + " <== " + node.getNode(edge.getNode1(), nodes).getNodeName());
                }
            }
        }

    }

}