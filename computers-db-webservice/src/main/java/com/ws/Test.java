package com.ws;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

public class Test {
    
    public static void main(String[] args) throws MalformedURLException {
    	
    	URL url = new URL("http://localhost:8080/computers-db-webservice/computer?wsdl");
    	 
        //1st argument service URI, refer to wsdl document above
	//2nd argument is service name, refer to wsdl document above
        QName qname = new QName("http://ws.com/", "ComputerServiceImplService");
 
        Service service = Service.create(url, qname);
 
        ComputerService computerS = service.getPort(ComputerService.class);
 
        System.out.println(computerS.toString());        
        
//        PageWrapper page = new PageWrapper();
//		page.setPageNumber(1);
//		page.setComputerPerPage(20);
//		page.setOrderDirection("ASC");
//		page.setOrderBy("c.id");
//        computerS.readByPage(page);
//        
//        
//        System.out.println(page.toString());
    	
    }
}
