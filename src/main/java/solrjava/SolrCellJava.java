package solrjava;

import java.io.File;
import java.io.IOException;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.request.AbstractUpdateRequest;
import org.apache.solr.client.solrj.request.ContentStreamUpdateRequest;
import org.apache.solr.client.solrj.response.QueryResponse;


public class SolrCellJava {

	public static void main(String[] args) throws Exception{
		try {
		      //Solr cell can also index MS file (2003 version and 2007 version) types.
		      String fileName = "/Users/arafath/Documents/workspace/SolrPOC/solr-word.pdf"; 
		      //this will be unique Id used by Solr to index the file contents.
		      String solrId = "solrword"; 
		      
		      indexFilesSolrCell(fileName, solrId);
		      
		    } catch (Exception ex) {
		      System.out.println(ex.toString());
		    }

	}

	private static void indexFilesSolrCell(String fileName, String solrId) throws IOException, SolrServerException {
		String urlString = "http://ec2-52-25-63-109.us-west-2.compute.amazonaws.com:8983/solr/hdp1"; 
	    SolrServer solr = new HttpSolrServer(urlString);
	    
	    ContentStreamUpdateRequest up 
	      = new ContentStreamUpdateRequest("/update/extract");
	    
	    up.addFile(new File(fileName),"application/pdf");
	    
	    up.setParam("literal.id", solrId);
	    up.setParam("uprefix", "attr_");
	    //up.setParam("fmap.content", "attr_content");
	    
	    
	    up.setAction(AbstractUpdateRequest.ACTION.COMMIT, true, true);
	    
	    solr.request(up);
	    
	    QueryResponse rsp = solr.query(new SolrQuery("*:*"));
	    
	    System.out.println(rsp);
		
	}

}
