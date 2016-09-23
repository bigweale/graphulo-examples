package edu.mit.ll.graphulo.experiment.load;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.accumulo.core.client.Connector;
import org.apache.accumulo.core.client.ZooKeeperInstance;
import org.apache.accumulo.core.client.security.tokens.AuthenticationToken;
import org.apache.accumulo.core.client.security.tokens.PasswordToken;

import edu.mit.ll.graphulo.Graphulo;
import edu.mit.ll.graphulo.util.TripleFileWriter;

/**
 * Loads the Enron data set into Graphulo.
 * 
 * @author ti26350
 *
 */
public class BasicIncGraph {

	public static void main(String[] args) throws IOException {
       	
		BufferedReader br = new BufferedReader(new FileReader("/Users/ti26350/data/sample.graph"));
		PrintWriter pwV = new PrintWriter("verts.txt");
		PrintWriter pwE = new PrintWriter("edges.txt");
		
		String str = br.readLine();
		boolean comma = false;
		while(str != null) {
			if(str.indexOf('#') == -1) {
				if(comma) {
					pwV.print(",");
					pwE.print(",");
				}
				else {
					comma = true;
				}

				String[] arr = str.split("\t");
				pwV.print(arr[0]);
				pwE.print(arr[1]);
			}
			str = br.readLine();
		}
		br.close();
		pwV.close();
		pwE.close();
		Connector conn = null;
       	AuthenticationToken authToken = null;
       	
    	try { 
			//Read Accumulo Configuration Information
			String instance = "graphuloLocal";
	    	String zkServers = "localhost";
	
	    	String principal = "root";
	    	authToken = new PasswordToken("graphuloLocal");
//			String instance = "txg-classdb04";
//	    	String zkServers = "txg-classdb04.cloud.llgrid.ll.mit.edu";
	
//	    	String principal = "AccumuloUser";
//	    	authToken = new PasswordToken("K-Yt5H9LkweiZ_EbQCxz5p4jm");
	
	    	ZooKeeperInstance inst = new ZooKeeperInstance(instance, zkServers);
	    	conn = inst.getConnector(principal, authToken);
    	} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	// Set up files
    	File verts = new File("verts.txt");
    	File edges = new File("edges.txt");
    	File vals = null;
    	System.out.println("WRITING");
    	Graphulo g = new Graphulo(conn, authToken);
    	TripleFileWriter tfw = new TripleFileWriter(conn);
    	tfw.writeTripleFile_Incidence(verts, edges, vals, ",", "GPH_Simple", true, false, -1);
	}

}
