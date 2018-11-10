package org.boban;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.vocabulary.VCARD4;

public class MainClass {

	public static void main(String[] args) {
		
		String personURI = "https://www.linkedin.com/in/boban-sugareski/";		
		Model model = ModelFactory.createDefaultModel();
		Resource bobz = model.createResource(personURI);
		
		addProperties(bobz);
		
		StmtIterator iter = model.listStatements();
		printStatements(iter);
		printWithLibraryWriters(model);
	}
	
	public static void addProperties(Resource bobz) {
		bobz.addProperty(VCARD4.fn, "Boban Sugareski");
		bobz.addProperty(VCARD4.bday, "16-11-1996");
		bobz.addProperty(VCARD4.email, "bobansugareski@gmail.com");
		bobz.addProperty(VCARD4.country_name, "Macedonia");
		bobz.addProperty(VCARD4.street_address, "Pekljane 69");
		bobz.addProperty(VCARD4.nickname, "Bobz");
		bobz.addProperty(VCARD4.note, "I am an Arsenal fan");
		bobz.addProperty(VCARD4.organization_name, "GSIX");
		bobz.addProperty(VCARD4.role, "Web Developer");
		bobz.addProperty(VCARD4.region, "Balkans");
		bobz.addProperty(VCARD4.category, "Engineer");
	}
	
	public static void printStatements(StmtIterator iter) {
		System.out.println("Printing with model.listStatements():");
		while (iter.hasNext()) {
			Statement statement = iter.nextStatement();
			Resource subject = statement.getSubject();
			Property predicate = statement.getPredicate();
			RDFNode object = statement.getObject();
			
			System.out.println(subject.toString() + " - " + predicate.toString() + " - \"" + object.toString() + "\"");
		}
	}
	
	public static void printWithLibraryWriters(Model model) {
		System.out.println("Printing with model.print(), in Turtle");
		model.write(System.out, "TURTLE");
		System.out.println("Printing with model.print(), in RDF/XML");
		model.write(System.out);
		System.out.println("Printing with model.print(), in pretty RDF/XML");
		model.write(System.out, "RDF/XML-ABBREV");
		System.out.println("Printing with model.print(), in N-Triples");
		model.write(System.out, "N-TRIPLES");
	}

	
}
