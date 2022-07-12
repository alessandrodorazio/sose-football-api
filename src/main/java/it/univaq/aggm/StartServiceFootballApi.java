package it.univaq.aggm;

import java.util.List;

import javax.xml.ws.Endpoint;
import javax.xml.ws.handler.Handler;

import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.lifecycle.SingletonResourceProvider;

public class StartServiceFootballApi {

	public static void main(String[] args) {
		String restAddress = "http://0.0.0.0:8083/";
		String soapAddress = "http://0.0.0.0:8093/football";
		startRest(restAddress);
		startSoap(soapAddress);
	}
	
	public static void startRest(String restAddress) {
		JAXRSServerFactoryBean factoryBean = new JAXRSServerFactoryBean();
        factoryBean.setResourceClasses(MatchRepository.class);
        factoryBean.setResourceProvider(new SingletonResourceProvider(new MatchRepository()));
        factoryBean.setAddress(restAddress);
        Server server = factoryBean.create();
        System.out.println("Server ready...");
	}
	
	public static void startSoap(String soapAddress) {
		Endpoint.publish(soapAddress, new MatchRepository());
		System.out.println("SOAP server ready...");
	}

}
