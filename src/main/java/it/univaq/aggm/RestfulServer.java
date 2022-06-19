package it.univaq.aggm;

import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.lifecycle.SingletonResourceProvider;

public class RestfulServer {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JAXRSServerFactoryBean factoryBean = new JAXRSServerFactoryBean();
        factoryBean.setResourceClasses(MatchRepository.class);
        factoryBean.setResourceProvider(new SingletonResourceProvider(new MatchRepository()));
        factoryBean.setAddress("http://localhost:8081/");
        Server server = factoryBean.create();
        System.out.println("Server ready...");
	}

}
