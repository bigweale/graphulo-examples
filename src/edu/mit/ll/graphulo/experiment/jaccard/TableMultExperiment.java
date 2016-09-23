package edu.mit.ll.graphulo.experiment.jaccard;

import org.apache.accumulo.core.client.Connector;
import org.apache.accumulo.core.client.ZooKeeperInstance;
import org.apache.accumulo.core.client.security.tokens.AuthenticationToken;
import org.apache.accumulo.core.client.security.tokens.PasswordToken;

import edu.mit.ll.graphulo.Graphulo;

/**
 * Experiment to test the overhead of implementing a TableMult experiment in one-table vs. an adjacency/incidence representation.
 * 
 * 
 * @author ti26350
 *
 */
public class TableMultExperiment {

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
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
	    	
	    	Graphulo g = new Graphulo(conn, authToken);

	    	String tmp = g.SingleBFS("GPH_Simple_OneTableSingle", "edge", '|', "2", 2, "GPH_Simple_OneTableSingle_BFS", "GPH_Simple_OneTableSingle", "deg", true,
	    			    true, null, null, 0, Integer.MAX_VALUE, null, true, null, null);
	    	//long tmp = g.TableMult("SimpleT", "Simple", "SimpleR", "SimpleRT", null, null);
	    	//long tmp = g.TableMult("GPH_Simple_OneTableSingle", "GPH_Simple_OneTableSingle", "GPH_Simple_OneTableSingleR", "GPH_Simple_OneTableSingleRT", null, null);
	    	System.out.println(tmp);
	    	
    	} catch (Exception e) {
			e.printStackTrace();
		}


	}

}
