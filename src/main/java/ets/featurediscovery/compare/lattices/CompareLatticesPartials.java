package ets.featurediscovery.compare.lattices;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import ca.uqam.latece.aspects.extractor.lattice.graph.model.Node;
import ca.uqam.latece.aspects.extractor.lattice.graph.model.NodeFeatureType;

public class CompareLatticesPartials {

	public static void main(String[] args) {
		String folderName = "Lucene"
				+ "" + "";
		System.out.println("Analysing  " + folderName + "   :-)");
		compareLatticesFolder(folderName);

	}

	public static void compareLatticesFolder(String folderName) {
		File folder = new File(folderName);
		File[] listOfFiles = folder.listFiles();
		// extraire les noms des fichier
		List<String> files = new ArrayList<String>(listOfFiles.length);
		for (int i = 0; i < listOfFiles.length; i++) {
			files.add(listOfFiles[i].getName());
		}
		// trier les noms des fichiers
		Collections.sort(files);
		
		int totalNodes=0;

		// extraire et comparer les lattices
		for (int i = 0; i < files.size() - 1; i++) {

			List<Node> firstNodes = readJsonFile(folderName + "/" + files.get(i));
			List<Node> secondNodes = readJsonFile(folderName + "/" + files.get(i + 1));
			firstNodes = clean(firstNodes);
			secondNodes = clean(secondNodes);

			List<Node[]> nodes2 = transformedAdhocNodes(firstNodes, secondNodes, 0);
			nodes2.addAll(transformedPartialBehavior(firstNodes, secondNodes, 0));
			System.out.println(nodes2.size());

			System.out.println("========" + files.get(i) + "   vs.    " + files.get(i + 1) + ": ");

			for (Node[] node : nodes2) {
				totalNodes++;
				// if
				// ((!node[0].getTypes().get(0).getFeatureTypeName().equals(node[1].getTypes().get(0).getFeatureTypeName()))
				// && node[0].getTypes().get(0).getFeatureTypeName().equals("ADHOC")
				// && !isChild(node[0], node[1])
				// && !isChild(node[1], node[0]))
				// {

				System.out.println("node[0] " + node[0].getId());
				System.out.println("node[0] " + node[0].getTypes().get(0).getFeatureTypeName());

				System.out.println("node[1] " + node[1].getId());
				System.out.println("node[1] " + node[1].getTypes().get(0).getFeatureTypeName());

			}

		}
		System.out.println("Number of nodes "+ totalNodes);
	}

	public static List<Node> readJsonFile(File file) {

		ObjectMapper mapper = new ObjectMapper();

		CollectionType listType = mapper.getTypeFactory().constructCollectionType(ArrayList.class, Node.class);
		List<Node> nodes = new ArrayList<Node>();
		try {
			nodes = mapper.readValue(file, listType);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return nodes;

	}

	public static List<Node> readJsonFile(String file) {

		return readJsonFile(Paths.get(file).toFile());

	}

	public static List<Node[]> transformedAdhocNodes(List<Node> lattice1, List<Node> lattice2, double threashold) {

		// We create a stream of elements from the first list.
		lattice1.removeAll(createSharedListViaStream(lattice1, lattice2));
		lattice2.removeAll(createSharedListViaStream(lattice1, lattice2));

		
		//lattice1.removeAll(sameExtentIntentTypeFeatures(lattice1, lattice2));
		//lattice2.removeAll(sameExtentIntentTypeFeatures(lattice1, lattice2));
		
		
		
		
		List<Node[]> pairs = lattice1.stream().flatMap(i -> lattice2.stream().map(j -> new Node[] { i, j })).filter(t -> // t[0].getIntent().equals(t[1].getIntent())
		// (intersection (split(t[0].getIntent()),split(t[1].getIntent())).size()
		// / Integer.min(split(t[0].getIntent()).size(),
		// split(t[1].getIntent()).size())) > threashold
		split(t[1].getIntent()).containsAll(split(t[0].getIntent()))
			&& split(t[0].getIntent()).containsAll(split(t[1].getIntent()))
				&& (intersection(extractClassesNames(t[0].getExtent()), extractClassesNames(t[1].getExtent())).size()
						/ Integer.min(extractClassesNames(t[0].getExtent()).size(),
								extractClassesNames(t[1].getExtent()).size())) >= threashold
				//&& (intersection(extractClassesNames(t[0].getIntent()), extractClassesNames(t[1].getIntent())).size()
				//		/ Integer.min(extractClassesNames(t[0].getExtent()).size(),
				//				extractClassesNames(t[1].getIntent()).size())) >= threashold

				// &&
				//&& !t[0].getTypes().get(0).getFeatureTypeName().equals(t[1].getTypes().get(0).getFeatureTypeName())
				&& t[0].getTypes().get(0).getFeatureTypeName().equals("ADHOC")
				&& !t[1].getTypes().get(0).getFeatureTypeName().equals("ADHOC")
				//&& anchorInAdhocExtent(t[0], t[1])
				//&& isFeatureEvolutionPartialToFull(t[0].getTypes(), t[1].getTypes())
		// && !isChild(t[0], t[1])
		// && !isChild(t[1], t[0])
		// && split(t[0].getIntent()).size() > 4
		).collect(Collectors.toList());

		return pairs;

	}

	/*
	 * from partial behavior to partial or full behavior same extent
	 */
	public static List<Node[]> transformedFullBehavior(List<Node> lattice1, List<Node> lattice2, double threashold) {

		lattice1.removeAll(createSharedListViaStream(lattice1, lattice2));
		lattice2.removeAll(createSharedListViaStream(lattice1, lattice2));

		
		// We create a stream of elements from the first list.
		//lattice1.removeAll(sameExtentIntentTypeFeatures(lattice1, lattice2));
		//lattice2.removeAll(sameExtentIntentTypeFeatures(lattice1, lattice2));
		
		
		List<Node[]> pairs = lattice1.stream().flatMap(i -> lattice2.stream().map(j -> new Node[] { i, j })).filter(t -> // t[0].getIntent().equals(t[1].getIntent())
		// (intersection (split(t[0].getIntent()),split(t[1].getIntent())).size()
		// / Integer.min(split(t[0].getIntent()).size(),
		// split(t[1].getIntent()).size())) > threashold
		split(t[1].getIntent()).containsAll(split(t[0].getIntent()))
				&& split(t[0].getIntent()).containsAll(split(t[1].getIntent()))
				&& (intersection(extractClassesNames(t[0].getExtent()), extractClassesNames(t[1].getExtent())).size()
						/ Integer.min(extractClassesNames(t[0].getExtent()).size(),
								extractClassesNames(t[1].getExtent()).size())) >= threashold

				// &&
				//&& !t[0].getTypes().get(0).getFeatureTypeName().equals(t[1].getTypes().get(0).getFeatureTypeName())
				&& !t[0].getTypes().get(0).getFeatureTypeName().equals("ADHOC")
				&& !t[1].getTypes().get(0).getFeatureTypeName().equals("ADHOC")
				&& isFeatureEvolutionFullToPartial(t[0].getTypes(), t[1].getTypes())
		// && !isChild(t[0], t[1])
		// && !isChild(t[1], t[0])
		// && split(t[0].getIntent()).size() > 4
		)

				.collect(Collectors.toList());

		return pairs;

	}

	public static List<Node[]> transformedPartialBehavior(List<Node> lattice1, List<Node> lattice2, double threashold) {

		// We create a stream of elements from the first list.
		lattice1.removeAll(createSharedListViaStream(lattice1, lattice2));
		lattice2.removeAll(createSharedListViaStream(lattice1, lattice2));
		
		//lattice1.removeAll(sameExtentIntentTypeFeatures(lattice1, lattice2));
		//lattice2.removeAll(sameExtentIntentTypeFeatures(lattice1, lattice2));
		
		

		List<Node[]> pairs = lattice1.stream().flatMap(i -> lattice2.stream().map(j -> new Node[] { i, j })).filter(t -> // t[0].getIntent().equals(t[1].getIntent())
		// (intersection (split(t[0].getIntent()),split(t[1].getIntent())).size()
		// / Integer.min(split(t[0].getIntent()).size(),
		// split(t[1].getIntent()).size())) > threashold
		split(t[1].getIntent()).containsAll(split(t[0].getIntent()))
				&& split(t[0].getIntent()).containsAll(split(t[1].getIntent()))
				&& (intersection(extractClassesNames(t[0].getExtent()), extractClassesNames(t[1].getExtent())).size()
						/ Integer.min(extractClassesNames(t[0].getExtent()).size(),
								extractClassesNames(t[1].getExtent()).size())) >= threashold

				// &&
				//&& !t[0].getTypes().get(0).getFeatureTypeName().equals(t[1].getTypes().get(0).getFeatureTypeName())
				&& !t[0].getTypes().get(0).getFeatureTypeName().equals("ADHOC")
				&& !t[1].getTypes().get(0).getFeatureTypeName().equals("ADHOC")
				&& isFeatureEvolutionPartialToFull(t[0].getTypes(), t[1].getTypes())
		// && !isChild(t[0], t[1])
		// && !isChild(t[1], t[0])
		// && split(t[0].getIntent()).size() > 4
		)

				.collect(Collectors.toList());

		return pairs;

	}

	public static List<Node> createSharedListViaStream(List<Node> lattice1, List<Node> lattice2) {
		// We create a stream of elements from the first list.

		List<Node> listOneList = lattice1.stream()
				// We select any elements such that in the stream of elements from the second
				// list
				.filter(two -> lattice2.stream()
						// there is an element that has the same name and school as this element,
						.anyMatch(one -> one.getId() == two.getId()))
				// and collect all matching elements from the first list into a new list.
				.collect(Collectors.toList());
		// We return the collected list.
		return listOneList;
	}
	
	public static List<Node> sameExtentIntentTypeFeatures(List<Node> lattice1, List<Node> lattice2) {
		// We create a stream of elements from the first list.

		List<Node> listOneList = lattice1.stream()
				// We select any elements such that in the stream of elements from the second
				// list
				.filter(two -> lattice2.stream()
						// there is an element that has the same name and school as this element,
						.anyMatch(one -> one.getTypes().equals(two.getTypes())
									&& extractClassesNames(one.getExtent()).equals(extractClassesNames(two.getExtent()))
									&& extractClassesNames(one.getIntent()).equals(extractClassesNames(two.getIntent()))				
						))
				// and collect all matching elements from the first list into a new list.
				.collect(Collectors.toList());
		// We return the collected list.
		return listOneList;
	}

	public static List<String> split(String extentIntent) {
		List<String> listStrings = new ArrayList<String>();
		// remove [ ] in the first and last position
		if (extentIntent != null && extentIntent.length() > 2) {
			String subString = extentIntent.substring(0, extentIntent.length() - 1);
			listStrings = Arrays.asList(subString.split(", "));

		}
		return listStrings;
	}

	public static List<String> intersection(List<String> list1, List<String> list2) {
		List<String> list = new ArrayList<String>();

		for (String t : list1) {
			if (list2.contains(t)) {
				list.add(t);
			}
		}

		return list;
	}

	public static List<Node> clean(List<Node> nodes) {
		List<Node> clean = new ArrayList<Node>();
		for (Node node : nodes) {
			if (node != null && node.getExtent() != null && node.getIntent() != null)
				clean.add(node);

		}
		return clean;
	}

	public static boolean isChild(Node nChild, Node n) {
		return extractClassesNames(n.getExtent()).containsAll(extractClassesNames(nChild.getExtent()))
				&& split(nChild.getIntent()).containsAll(split(n.getIntent()));
	}

	public static String extractClassName(String extent) {
		String[] elements = extent.split("\\.");
		if (elements.length > 1)
			return elements[elements.length - 1];
		else
			return "";
	}

	public static List<String> extractClassesNames(String extents) {
		List<String> classNames = new ArrayList<String>();

		String[] extentsTab = extents.split(", ");
		for (int i = 0; i < extentsTab.length; i++) {
			classNames.add(extractClassName(extentsTab[i]));
		}
		return classNames;
	}

	public static boolean isFeatureEvolutionPartialToFull(List<NodeFeatureType> type_0, List<NodeFeatureType> type_1) {
		boolean isEvolution = false;
		// un type devient full

		for (int i = 0; i < type_0.size(); i++) {
			for (int j = 0; j < type_1.size(); j++) {
				if (extractClassName(type_0.get(i).getAnchor()).equals(extractClassName(type_1.get(j).getAnchor()))) {
					if ((type_0.get(i).getFeatureTypeName().startsWith("PARTIAL_EXTENT"))
							&& (type_1.get(j).getFeatureTypeName().startsWith("FULL_EXTENT"))) {
						if (type_0.get(i).getFeatureTypeName().contains("INTERFACE")
								&& type_1.get(j).getFeatureTypeName().contains("INTERFACE")) {
							isEvolution = true;
							break;
						}
						if (type_0.get(i).getFeatureTypeName().contains("AGGREGATIONS")
								&& type_1.get(j).getFeatureTypeName().contains("AGGREGATIONS")) {
							isEvolution = true;
							break;
						}
						if (type_0.get(i).getFeatureTypeName().contains("CLASS_SUBCLASS")
								&& type_1.get(j).getFeatureTypeName().contains("CLASS_SUBCLASS")) {
							isEvolution = true;
							break;
						}
						// break;
					}

				}
			}
		}
		// un type additionnel ajouté
		if (type_1.containsAll(type_0) && type_0.size() < type_1.size()) {
			type_1.removeAll(type_0);
			for (NodeFeatureType type : type_1) {
				if (type.getFeatureTypeName().startsWith("FULL_EXTENT")) {
					isEvolution = true;
					break;
				}
			}
		}
		return isEvolution;
	}
	public static boolean isFeatureEvolutionFullToPartial (List<NodeFeatureType> type_0, List<NodeFeatureType> type_1 ) { 
		boolean isEvolution = false;
		//un type devient full
		
		for (int i = 0; i < type_0.size() ; i++) {
			for (int j = 0; j < type_1.size() ; j++) {
				if (extractClassName(type_0.get(i).getAnchor()).equals(extractClassName(type_1.get(j).getAnchor()))){
					if ((type_0.get(i).getFeatureTypeName().startsWith("FULL_EXTENT")) &&
							(type_1.get(j).getFeatureTypeName().startsWith("PARTIAL_EXTENT"))	) {
								if (type_0.get(i).getFeatureTypeName().contains("INTERFACE") && 	
									type_1.get(j).getFeatureTypeName().contains("INTERFACE")) {
										isEvolution = true;
										break;
										}
								if (type_0.get(i).getFeatureTypeName().contains("AGGREGATIONS") && 	
										type_1.get(j).getFeatureTypeName().contains("AGGREGATIONS")) {
											isEvolution = true;
											break;
											}
								if (type_0.get(i).getFeatureTypeName().contains("CLASS_SUBCLASS") && 	
										type_1.get(j).getFeatureTypeName().contains("CLASS_SUBCLASS")) {
											isEvolution = true;
											break;
											}
									//break;
					}
				}
			}
		}
		return isEvolution;
		
	}
	
	public static boolean anchorInAdhocExtent(Node adhocNode, Node deliberateNode) {
		
		boolean anchorInAdhocExtent = false;
		
		List<NodeFeatureType> deliberateTypes = deliberateNode.getTypes();
		NodeFeatureType adhocType = adhocNode.getTypes().get(0);
		if (adhocType.getFeatureTypeName().equals("ADHOC")) {
			for (NodeFeatureType type : deliberateTypes) {
				if (extractClassesNames(adhocNode.getExtent()).stream().map(t -> t.contains(extractClassName(type.getAnchor()))).collect((Collectors.toList())).size()>0) {
					anchorInAdhocExtent = true;
					break;
				}
				
			}
		}
		return anchorInAdhocExtent;
		
	}

}
