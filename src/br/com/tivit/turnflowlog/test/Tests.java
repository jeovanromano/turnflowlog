/**
 * 
 */
package br.com.tivit.turnflowlog.test;

import java.net.URL;

/**
 * @author Jeovan Romano
 *
 */
public class Tests {
	
	
	    public static void main(String... args) throws Exception {
	        URL location = Tests.class.getProtectionDomain().getCodeSource().getLocation();
	        System.out.println(location.getFile());
	    }
	

}
