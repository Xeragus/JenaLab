package org.boban;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.LinkedList;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.ResIterator;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.util.FileManager;
import org.apache.jena.vocabulary.RDFS;
import java.util.Collections;


public class ModelQueryOperator {
	public static void main(String[] args) {
		
		String inputFileName = "/home/boban/eclipse/jee-2018-09/hifm-dataset.ttl";
		Model model = ModelFactory.createDefaultModel();
		
		InputStream in = FileManager.get().open(inputFileName);
		if (in == null) {
			System.out.println("File not found");
			return;
		}
		
	    model.read("/home/boban/eclipse/jee-2018-09/hifm-dataset.ttl", "TTL");
	    
	    // TODO: add user input for drugEndpointID
		String drugEndpointID = "985147";

		printDrugNames(model);
		printRelationsValuesBySubjectId(model, drugEndpointID);
		printAllSimilarBySubjectId(model, drugEndpointID);
		printInfoForSimilarDrugs(model, drugEndpointID);
	}
	
	public static void printDrugNames(Model model) {
		System.out.println("Listing all drug names:");
		
		LinkedList<String> drugNames = new LinkedList<>();
		ResIterator iter = model.listSubjectsWithProperty(RDFS.label);
		while (iter.hasNext()) {
			String drugName = iter.nextResource().getProperty(RDFS.label).getString();
			drugNames.add(drugName);
		}
		
		Collections.sort(drugNames);
		System.out.println(drugNames);
	}
	
	public static void printRelationsValuesBySubjectId(Model model, String id) {
		System.out.println("\n" + "Listing all relations and values for subject: " + id);
		
		StmtIterator iter = model.listStatements();
		LinkedList<String> predicateList = new LinkedList();
		LinkedList<String> valueList = new LinkedList();
		
		while (iter.hasNext()) {
			Statement statement = iter.nextStatement();
			Resource subject = statement.getSubject();
			Property predicate = statement.getPredicate();
			RDFNode object = statement.getObject();
			
			if (subject.toString().endsWith(id)) {
				predicateList.add(predicate.toString());
				valueList.add(object.toString());
			}
		}
		
		System.out.println(predicateList);
		System.out.println(valueList);
	}
	
	public static void printAllSimilarBySubjectId(Model model, String id) {
		System.out.println("\n" + "Listing all similarTo relations for: " + id);
		
		LinkedList<String> similarDrugsList = new LinkedList();
		StmtIterator statements = model.listStatements();
		
		while (statements.hasNext()) {
			Statement statement = statements.nextStatement();
			Resource subject = statement.getSubject();
			Property predicate = statement.getPredicate();
			RDFNode object = statement.getObject();
			
			if (subject.toString().endsWith(id) && predicate.toString().endsWith("similarTo")) {
				similarDrugsList.add(object.toString());
			}
		}
		
		System.out.println(similarDrugsList);
	}
	
	public static void printInfoForSimilarDrugs(Model model, String id) {
		System.out.println("\n" + "Listing names and prices of similar drugs to: " + id);
		
		LinkedList<RDFNode> similarDrugsList = new LinkedList();
		StmtIterator statements = model.listStatements();
		
		while (statements.hasNext()) {
			Statement statement = statements.nextStatement();
			Resource subject = statement.getSubject();
			Property predicate = statement.getPredicate();
			RDFNode object = statement.getObject();
			
			if (subject.toString().endsWith(id) && predicate.toString().endsWith("similarTo")) {
				similarDrugsList.add(object);
			}
		}
		
		for (RDFNode rdfNode : similarDrugsList) {

		}
	}
}
