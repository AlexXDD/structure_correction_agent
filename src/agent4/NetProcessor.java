package agent4;

import net.*;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class NetProcessor {
    public void processNetwork() {
        Net pyramidNetwork = Net.getInstance();

        //<<------------------ step1
        ArrayList<Conceptor> conceptorslist = new ArrayList<>();
        NetReader.readConceptors(conceptorslist, "files\\input.xml");
        ArrayList<String> stringsToDelete = new ArrayList<String>();

        for (int i = 0; i < conceptorslist.size(); i++) {
            for (int j = 0; (i != j) && j < conceptorslist.size(); j++) {
                if (conceptorslist.get(j).getNode1().toString().startsWith("$") || conceptorslist.get(i).getNode1().toString().startsWith("$")) {
                    if (CompareTwoArrays(conceptorslist.get(i).receptors, conceptorslist.get(j).receptors) && !conceptorslist.get(i).getNode1().equals(conceptorslist.get(j).getNode1())) {
                        String conceptor1 = conceptorslist.get(i).getNode1().toString();
                        String conceptor2 = conceptorslist.get(j).getNode1().toString();

                        Integer intConceptor1 = tryParse(conceptor1.substring(1));
                        Integer intConceptor2 = tryParse(conceptor2.substring(1));

                        if ((conceptor1.startsWith("$")) && (conceptor2.startsWith("$"))) {
                            if (intConceptor1 > intConceptor2) {
                                // conceptors.values().removeIf(v -> v.equals(conceptor1));
                                stringsToDelete.add(conceptor1);
                                System.out.println("conceptor1 : " + conceptor1 + " equals : " + conceptor2);
                            } else {
                                //conceptors.values().removeIf(v -> v.equals(conceptor2));
                                stringsToDelete.add(conceptor2);
                                System.out.println("conceptor2 : " + conceptor2 + " equals : " + conceptor1);
                            }
                        } else if ((conceptor1.startsWith("$")) && (!conceptor2.startsWith("$"))) {
                            //conceptors.values().removeIf(v -> v.equals(conceptor2));
                            stringsToDelete.add(conceptor1);
                        } else if ((conceptor2.startsWith("$")) && (!conceptor1.startsWith("$"))) {
                            stringsToDelete.add(conceptor2);
                        }

                    }
                }
            }
        }

        ArrayList<Edge> edgeslist = new ArrayList<>();
        Set<Node> nodeslist = new HashSet<>();
        NetReader.readEdges(edgeslist, "files\\input.xml");
        NetReader.readNodes(nodeslist, "files\\input.xml");
        /*
        ArrayList<Node> level_1 = new ArrayList<Node>();

        for (int i = 0; i < nodeslist.size(); i++) {
            level_1.add((Node) nodeslist.get(i));
            pyramidNetwork.addNode((Node) nodeslist.get(i));
        }
        //---end--of--first--step---//
        */

        //----------step 2? --------//
        String graphGuid = UUID.randomUUID().toString();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = factory.newDocumentBuilder();

            // graph
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("newgraph");
            doc.appendChild(rootElement);
            {
                Attr attr = doc.createAttribute("nodesize");
                attr.setValue(String.valueOf(10));
                rootElement.setAttributeNode(attr);
            }
            {
                Attr attr = doc.createAttribute("vspacing");
                attr.setValue("3");
                rootElement.setAttributeNode(attr);
            }
            {
                Attr attr = doc.createAttribute("hspacing");
                attr.setValue("20");
                rootElement.setAttributeNode(attr);
            }
            {
                Attr attr = doc.createAttribute("padding");
                attr.setValue("4");
                rootElement.setAttributeNode(attr);
            }
            {
                Attr attr = doc.createAttribute("showarrows");
                attr.setValue("true");
                rootElement.setAttributeNode(attr);
            }
            {
                Attr attr = doc.createAttribute("guid");
                attr.setValue(graphGuid);
                rootElement.setAttributeNode(attr);
            }
            {
                Attr attr = doc.createAttribute("searchurl");
                attr.setValue("");
                rootElement.setAttributeNode(attr);
            }


            Element datagroups = doc.createElement("datagroups");
            rootElement.appendChild(datagroups);

            Element nodes = doc.createElement("Node");
            rootElement.appendChild(nodes);

            Element linkgroups = doc.createElement("Linkgroups");
            rootElement.appendChild(linkgroups);

            {
                Element groups = doc.createElement("Group");
                {
                    Attr attr = doc.createAttribute("name");
                    attr.setValue("Part_of");
                    groups.setAttributeNode(attr);
                }
                linkgroups.appendChild(groups);
            }
            /*
            Element receptorlist = doc.createElement("Receptorlist");
            rootElement.appendChild(receptorlist);

            for (int i = 0; i < nodeslist.size(); i++) {
                Node node = (Node) nodeslist.get(i);
                ;
                Element nodeElement = doc.createElement("Node");
                {
                    Attr attr = doc.createAttribute("guid");
                    attr.setValue(node.node_guid);
                    nodeElement.setAttributeNode(attr);
                }
                {
                    Attr attr = doc.createAttribute("nodeName");
                    attr.setValue(node.node_name);
                    nodeElement.setAttributeNode(attr);
                }
                {
                    Attr attr = doc.createAttribute("nclass");
                    attr.setValue(node.nClass);
                    nodeElement.setAttributeNode(attr);
                }
                nodes.appendChild(nodeElement);

                if (!node.nClass.isEmpty()) {
                    Element groups = doc.createElement("Group");
                    {
                        Attr attr = doc.createAttribute("name");
                        attr.setValue(node.nClass);
                        groups.setAttributeNode(attr);
                    }
                    linkgroups.appendChild(groups);
                }
            }
            */
            Element conceptors = doc.createElement("ReceptorList");
            rootElement.appendChild(conceptors);

            for (int i = 0; i < conceptorslist.size(); i++) {
                for (int iter = 0; iter < conceptorslist.get(i).getReceptors().size(); iter++) {
                    Conceptor conceptor = conceptorslist.get(i);
                    Element edgeElement = doc.createElement("Conceptor");
                    {
                        Attr attr = doc.createAttribute("node1");
                        attr.setValue(conceptor.node1);
                        edgeElement.setAttributeNode(attr);
                    }
                    {
                        Attr attr = doc.createAttribute("node2");
                        attr.setValue(conceptor.receptors.get(iter));
                        edgeElement.setAttributeNode(attr);
                    }

                    conceptors.appendChild(edgeElement);
                }
            }
            //--------edges-------//
            Element edges = doc.createElement("Edges");
            rootElement.appendChild(edges);

            for (int i = 0; i < edgeslist.size(); i++) {
                Edge edge = edgeslist.get(i);
                Element edgeElement = doc.createElement("Edge");
                {
                    Attr attr = doc.createAttribute("guid");
                    attr.setValue(edge.guid);
                    edgeElement.setAttributeNode(attr);
                }
                {
                    Attr attr = doc.createAttribute("node1");
                    attr.setValue(edge.getNode1());
                    edgeElement.setAttributeNode(attr);
                }
                {
                    Attr attr = doc.createAttribute("node2");
                    attr.setValue(edge.getNode2());
                    edgeElement.setAttributeNode(attr);
                }
                {
                    Attr attr = doc.createAttribute("group");
                    attr.setValue(edge.getGroup());
                    edgeElement.setAttributeNode(attr);
                }
                {
                    Attr attr = doc.createAttribute("istwoway");
                    attr.setValue("false");
                    edgeElement.setAttributeNode(attr);
                }
                edges.appendChild(edgeElement);
            }
            //-----------------------------------------------------------------------//
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("res/Output.xml"));
            transformer.setOutputProperty(OutputKeys.ENCODING, "Windows-1251");
            transformer.transform(source, result);
            //--------------file record-----------//


        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
        System.out.println("Finish");
    }

    public static Boolean CompareTwoArrays(ArrayList<String> firstArray, ArrayList<String> secondArray) {

        if (firstArray.size() != secondArray.size()) {
            return false;
        }

        for (int i = 0; i < firstArray.size(); i++) {
            if (!firstArray.get(i).equals(secondArray.get(i))) {
                return false;
            }
        }

        return true;
    }

    public static Integer tryParse(String text) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
