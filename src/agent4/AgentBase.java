package agent4;

import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.ControllerException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public abstract class AgentBase extends Agent {

    private String hostAddress;

    //Has to be used as a parameter for any message type declaration
    public static class MessageType{
        public static String SEND= "SEND";
        public static String REPLY= "REPLY";
    }


    public AgentBase(){
        try {
            this.hostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public AgentBase(String hostAddress){
        this.hostAddress = hostAddress;
    }


    //-----------------------AGENT INTERFACES -------------------------

    public void SendFiles(byte[] data,AID agentID){
        ACLMessage msg = FromMessageWithParameters(MessageType.SEND);
        msg.addReceiver(agentID);
        msg.setByteSequenceContent(data);
        send(msg);
    }

    public void SendMessage(String message,AID agentID){
        ACLMessage msg = FromMessageWithParameters(MessageType.SEND);
        msg.addReceiver(agentID);
        msg.setContent(message);
        send(msg);
    }

    public void ReplyMessage(String message,AID agentID){
        ACLMessage msg = FromMessageWithParameters(MessageType.REPLY);

        msg.addReceiver( agentID);
        msg.setContent(message);
        send(msg);
    }

    public void ReplyFiles(byte[] data,AID agentID){
        ACLMessage msg = FromMessageWithParameters(MessageType.REPLY);

        msg.addReceiver( agentID);
        msg.setByteSequenceContent(data);
        send(msg);
    }

    public void ReplyMessage(ACLMessage aclMessage, String message){
        ReplyMessage(message,GenerateAid(aclMessage));
    }

    public void ReplyFiles(ACLMessage aclMessage,byte[] data){
        ReplyFiles(data,GenerateAid(aclMessage));
    }

    //------------------------------END ------------------------------


    //----------------------------AGENT SDK ----------------------------
    public static String GetMessageType(ACLMessage message){
        return message.getUserDefinedParameter("TYPE");
    }

    public static boolean IsMessageOfType(ACLMessage message, String type){
        return GetMessageType(message)==type;
    }

    public static AID GenerateAid(String agentName,String hostAddress){
        AID aid = new AID(agentName+"@"+hostAddress+":1099/JADE",AID.ISGUID);
        aid.addAddresses("http://"+hostAddress+":7778/acc");

        return aid;
    }

    public static AID GenerateAid(ACLMessage msg){
        String name = msg.getUserDefinedParameter("NAME");
        String ipAddress = msg.getUserDefinedParameter("IP");

        AID aid = GenerateAid(name,ipAddress);
        return aid;
    }

    public static AgentController AcceptNewAgent(Agent agent, String agentName, ContainerController containerController) throws ControllerException {
        containerController.acceptNewAgent(agentName,agent);
        AgentController agentController = containerController.getAgent(agentName);
        agentController.start();
        return agentController;
    }

    //---------------------------------END--------------------------------

    //Help
    private ACLMessage FromMessageWithParameters(String type){
        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
        msg.addUserDefinedParameter("IP",hostAddress);
        msg.addUserDefinedParameter("NAME",this.getLocalName());
        msg.addUserDefinedParameter("TYPE",type);

        return msg;
    }


}
