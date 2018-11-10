package org.boban;

import java.io.InputStream;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.util.FileManager;
import org.apache.jena.vocabulary.VCARD4;

public class ReadingDataClass {

	public static void main(String[] args) {
		String inputFileName = "/home/boban/eclipse-workspace/data.nt";
		String personURI = "https://www.linkedin.com/in/boban-sugareski/";	
		
		Model model = ModelFactory.createDefaultModel();
		InputStream in = FileManager.get().open(inputFileName);
		
		if (in == null) {
			System.out.println("file not found");
			return;
		}
		
		model.read(in, null, "N-TRIPLES");
		model.write(System.out, "N-TRIPLES");
		
		Resource bobz = model.getResource(personURI);
		String fullName = bobz.getProperty(VCARD4.fn).getString();
		String birthDay = bobz.getProperty(VCARD4.bday).getString();
		String email = bobz.getProperty(VCARD4.email).getString();
		System.out.println("My full name: " + fullName);
		System.out.println("My birthday: " + birthDay);
		System.out.println("My email: " + email);
	}

}
 