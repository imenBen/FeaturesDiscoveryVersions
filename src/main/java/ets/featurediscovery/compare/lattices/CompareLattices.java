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

public class CompareLattices {

	public static void main(String[] args) {
		String folderName = "Lucene2"
				+ "";
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

		// extraire et comparer les lattices
		for (int i = 0; i < files.size() - 1; i++) {

			List<Node> firstNodes = readJsonFile(folderName + "/" + files.get(i));
			List<Node> secondNodes = readJsonFile(folderName + "/" + files.get(i + 1));
			firstNodes = clean(firstNodes);
			secondNodes = clean(secondNodes);
			/*List<Node> common = createSharedListViaStream(firstNodes, secondNodes);
			List<Node> commonExtent = CommonExtentViaStream(firstNodes, secondNodes, 3);
			List<Node> commonIntent = CommonIntentViaStream(firstNodes, secondNodes, 3);
			List<Node> commonIntentExtentThreashold = CommonIntentExtentViaStream(firstNodes, secondNodes, 0.9);
			List<Node> commonIntentExtentIntersection = CommonIntentExtentIntersectionViaStream(firstNodes, secondNodes,
					0);
			List<Node> extentIncludedInNextVersion = extentIncludedInNextVersionViaStream(firstNodes, secondNodes);
			List<Node> extentIncludedInPreviousVersion = extentIncludedInPreviousVersionViaStream(firstNodes,
					secondNodes);
			List<Node> intentIncludedInPreviousVersion = intentIncludedInPreviousVersionViaStream(firstNodes,
					secondNodes);
			List<Node> intentIncludedInNextVersion = intentIncludedInNextVersionViaStream(firstNodes, secondNodes);
			List<Node> intentAndExtentIncludedInNextVersion = intentAndExtentIncludedInNextVersionViaStream(firstNodes,
					secondNodes);
			List<Node> intentAndExtentIncludedInPreviousVersion = intentAndExtentIncludedInPreviousVersionViaStream(
					firstNodes, secondNodes);
			List<Node> intentIncludedInNextAndExtentIncludedInPreviousVersion = intentIncludedInNextAndExtentIncludedInPreviousVersionViaStream(
					firstNodes, secondNodes);
			List<Node> extentIncludedInNextAndIntentIncludedInPreviousVersion = extentIncludedInNextAndIntentIncludedInPreviousVersionViaStream(
					firstNodes, secondNodes);
			List<Node[]> intentAndExtentIncludedInNextVersionViaStreamPairs = intentAndExtentIncludedInNextVersionViaStreamPairs(
					firstNodes, secondNodes);
			
			List<Node[]> intentAndExtentIncludedInPreviousVersionViaStreamPairs = intentAndExtentIncludedInPreviousVersionViaStreamPairs(
					firstNodes, secondNodes);*/
	//System.out.print("========" + files.get(i) + "   vs.    "
		//			+ files.get(i + 1) + ": ");
			/*System.out.println("=============common nodes ");
			System.out.println(common.size());
			System.out.println("=============extend intersection   ");
			System.out.println(commonExtent.size() - common.size());
			System.out.println("=============intent intersection   ");
			System.out.println(commonIntent.size() - common.size());
			System.out.println("=============intent and extent intersection threashold  ");
			System.out.println(commonIntentExtentThreashold.size() - common.size());
			System.out.println("=============intent and extent intersection   ");
			System.out.println(commonIntentExtentIntersection.size() - common.size());
			System.out.println("==================extentIncludedInNextVersion");
			System.out.println(extentIncludedInNextVersion.size() - common.size());
			System.out.println("==================extentIncludedInPreviousVersion");
			System.out.println(extentIncludedInPreviousVersion.size() - common.size());
			System.out.println("==================intentIncludedInPreviousVersion");
			System.out.println(intentIncludedInPreviousVersion.size() - common.size());
			System.out.println("==================intentIncludedInNextVersion");
			System.out.println(intentIncludedInNextVersion.size() - common.size());
			System.out.println("==================intentAndExtentIncludedInNextVersion");
			System.out.println(intentAndExtentIncludedInNextVersion.size() - common.size());
			System.out.println("==================intentAndExtentIncludedInPreviousVersion");
			System.out.println(intentAndExtentIncludedInPreviousVersion.size() - common.size());
			System.out.println("==================intentIncludedInNextAndExtentIncludedInPreviousVersion");
			System.out.println(intentIncludedInNextAndExtentIncludedInPreviousVersion.size() - common.size());
			System.out.println("==================extentIncludedInNextAndIntentIncludedInPreviousVersion");
			System.out.println(extentIncludedInNextAndIntentIncludedInPreviousVersion.size() - common.size());
			intentAndExtentIncludedInNextVersion.removeAll(common);
			intentAndExtentIncludedInPreviousVersion.removeAll(common);
			System.out.println("%%%%%%%%%%%%%%%%%%%%%%");
			System.out.println("==================intentAndExtentIncludedInNextVersion");
			System.out.println(intentAndExtentIncludedInNextVersion.size());
			System.out.println("==================intentAndExtentIncludedInPreviousVersion");
			System.out.println(intentAndExtentIncludedInPreviousVersion.size());*/
			/*
			 * for(Node node : intentAndExtentIncludedInNextVersion) {
			 * System.out.println(node.getId());
			 * System.out.println(node.getTypes().get(0).getFeatureTypeName()); }
			 * System.out.println("____________"); for(Node node :
			 * intentAndExtentIncludedInPreviousVersion) { System.out.println(node.getId());
			 * System.out.println(node.getTypes().get(0).getFeatureTypeName()); }
			 */
			/*System.out.println("commonIntentAndExtentPairs");
			for (Node[] node : commonIntentAndExtentPairs(firstNodes, secondNodes)) {
				if (!node[0].getTypes().get(0).getFeatureTypeName().equals(node[1].getTypes().get(0).getFeatureTypeName())) {
					System.out.println("node[0] " + node[0].getId());
					System.out.println("node[0] " + node[0].getTypes().get(0).getFeatureTypeName());
					System.out.println("node[1] " + node[1].getId());
					System.out.println("node[1] " + node[1].getTypes().get(0).getFeatureTypeName());
				}
			}
			System.out.println("commonIntentPairs");
			
			for (Node[] node : commonIntentPairs(firstNodes, secondNodes)) {
				if (!node[0].getTypes().get(0).getFeatureTypeName().equals(node[1].getTypes().get(0).getFeatureTypeName())) {
					System.out.println("node[0] " + node[0].getId());
					System.out.println("node[0] " + node[0].getTypes().get(0).getFeatureTypeName());
					System.out.println("node[1] " + node[1].getId());
					System.out.println("node[1] " + node[1].getTypes().get(0).getFeatureTypeName());
				}
			}
			System.out.println("commonExtentPairs");
			for (Node[] node : commonExtentPairs(firstNodes, secondNodes)) {
				if (!node[0].getTypes().get(0).getFeatureTypeName().equals(node[1].getTypes().get(0).getFeatureTypeName())) {
					System.out.println("node[0] " + node[0].getId());
					System.out.println("node[0] " + node[0].getTypes().get(0).getFeatureTypeName());
					System.out.println("node[1] " + node[1].getId());
					System.out.println("node[1] " + node[1].getTypes().get(0).getFeatureTypeName());
				}
			}*/
			
			/*System.out.println("previousIntentIncludedInNextIntentPairs");
			List<Node[]> nodes =  previousIntentIncludedInNextIntentPairs(firstNodes, secondNodes);
			nodes.removeAll(commonIntentPairs(firstNodes, secondNodes));	
			for (Node[] node : nodes) {
				if (!node[0].getTypes().get(0).getFeatureTypeName().equals(node[1].getTypes().get(0).getFeatureTypeName())) {
					System.out.println("node[0] " + node[0].getId());
					System.out.println("node[0] " + node[0].getTypes().get(0).getFeatureTypeName());
					System.out.println("node[1] " + node[1].getId());
					System.out.println("node[1] " + node[1].getTypes().get(0).getFeatureTypeName());
				}
			}*/
			
			List<Node> nodes2 =  commonNodesStream(firstNodes, secondNodes
					);
			System.out.println(nodes2.size());
			
			
			firstNodes.removeAll(nodes2);	
			/*for (Node[] node : nodes2) {
				if ((!node[0].getTypes().get(0).getFeatureTypeName().equals(node[1].getTypes().get(0).getFeatureTypeName()))
						&& node[0].getTypes().get(0)z.getFeatureTypeName().equals("ADHOC")
						&& !isChild(node[0], node[1])
						&& !isChild(node[1], node[0])){
					
						System.out.println("node[0] " + node[0].getId());
						System.out.println("node[0] " + node[0].getTypes().get(0).getFeatureTypeName());
				
						System.out.println("node[1] " + node[1].getId());
						System.out.println("node[1] " + node[1].getTypes().get(0).getFeatureTypeName());
						
					
				}
			}*/
			
			
			for (Node node : firstNodes) {
				
					
						System.out.println("node[0] " + node.getId());
						System.out.println("node[0] " + node.getTypes().get(0).getFeatureTypeName());
				
						
						
					
				}
			

		}

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
	
	public static List<Node[]> transformedAdhocNodes (List<Node> lattice1, List<Node> lattice2, double threashold){
		
		// We create a stream of elements from the first list.
				lattice1.removeAll(createSharedListViaStream(lattice1, lattice2));
				lattice2.removeAll(createSharedListViaStream(lattice1, lattice2));

				List<Node[]> pairs = lattice1.stream().flatMap(i -> lattice2.stream().map(j -> new Node[] { i, j }))
						.filter(t -> //split(t[0].getIntent()).containsAll(split(t[1].getIntent()))
						(intersection (split(t[0].getIntent()),split(t[1].getIntent())).size()
								/ Integer.min(split(t[0].getIntent()).size(), split(t[1].getIntent()).size())) > threashold
						
								&& (intersection (split(t[0].getExtent()),split(t[1].getExtent())).size()
								/ Integer.min(split(t[0].getExtent()).size(), split(t[1].getExtent()).size())) > threashold
								&& !t[0].getTypes().get(0).getFeatureTypeName().equals(t[1].getTypes().get(0).getFeatureTypeName())
								&& t[0].getTypes().get(0).getFeatureTypeName().equals("ADHOC")
							//	&& !isChild(t[0], t[1])
							//	&& !isChild(t[1], t[0])
								)
						.collect(Collectors.toList());

				return pairs;
		
	}
	
public static List<Node[]> transformedAhocNodesClassNames (List<Node> lattice1, List<Node> lattice2, double threashold){
		
		// We create a stream of elements from the first list.
				lattice1.removeAll(createSharedListViaStream(lattice1, lattice2));
				lattice2.removeAll(createSharedListViaStream(lattice1, lattice2));

				List<Node[]> pairs = lattice1.stream().flatMap(i -> lattice2.stream().map(j -> new Node[] { i, j }))
						.filter(t -> //t[0].getIntent().equals(t[1].getIntent())
						(intersection (split(t[0].getIntent()),split(t[1].getIntent())).size()
								/ Integer.min(split(t[0].getIntent()).size(), split(t[1].getIntent()).size())) > threashold
						//split(t[1].getIntent()).containsAll(split(t[0].getIntent()))
								&& (intersection (extractClassesNames(t[0].getExtent()),extractClassesNames(t[1].getExtent())).size()
								/ Integer.min(extractClassesNames(t[0].getExtent()).size(), extractClassesNames(t[1].getExtent()).size())) > threashold

								&& !t[0].getTypes().get(0).getFeatureTypeName().equals(t[1].getTypes().get(0).getFeatureTypeName())
								&& t[0].getTypes().get(0).getFeatureTypeName().equals("ADHOC")
								//&& !isChild(t[0], t[1])
								//&& !isChild(t[1], t[0])
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

	public static List<Node> CommonExtentViaStream(List<Node> lattice1, List<Node> lattice2, int threashold) {
		// We create a stream of elements from the first list.
		List<Node> listOneList = lattice1.stream()
				// We select any elements such that in the stream of elements from the second
				// list
				.filter(two -> lattice2.stream()
						// there is an element that has the same name and school as this element,
						.anyMatch(one -> intersection(split(one.getExtent()), split(two.getExtent()))
								.size() > threashold))
				// and collect all matching elements from the first list into a new list.
				.collect(Collectors.toList());
		// We return the collected list.
		return listOneList;
	}

	public static List<Node> CommonIntentViaStream(List<Node> lattice1, List<Node> lattice2, int threashold) {
		// We create a stream of elements from the first list.
		List<Node> listOneList = lattice1.stream()
				// We select any elements such that in the stream of elements from the second
				// list
				.filter(two -> lattice2.stream()
						// there is an element that has the same name and school as this element,
						.anyMatch(one -> intersection(split(one.getIntent()), split(two.getIntent()))
								.size() > threashold))
				// and collect all matching elements from the first list into a new list.
				.collect(Collectors.toList());
		// We return the collected list.
		return listOneList;
	}

	public static List<Node> CommonIntentExtentViaStream(List<Node> lattice1, List<Node> lattice2, double threashold) {
		// We create a stream of elements from the first list.
		List<Node> listOneList = lattice1.stream()
				// We select any elements such that in the stream of elements from the second
				// list
				.filter(two -> lattice2.stream()
						// there is an element that has the same name and school as this element,
						.anyMatch(one -> intersection(split(one.getIntent()), split(two.getIntent())).size()
								/ Integer.min(split(one.getIntent()).size(), split(two.getIntent()).size()) > threashold
								&& intersection(split(one.getExtent()), split(two.getExtent())).size() / Integer.min(
										split(one.getExtent()).size(), split(two.getExtent()).size()) > threashold))

				// and collect all matching elements from the first list into a new list.
				.collect(Collectors.toList());
		// We return the collected list.
		return listOneList;
	}

	public static List<Node> CommonIntentExtentIntersectionViaStream(List<Node> lattice1, List<Node> lattice2,
			double threashold) {
		// We create a stream of elements from the first list.
		List<Node> listOneList = lattice1.stream()
				// We select any elements such that in the stream of elements from the second
				// list
				.filter(two -> lattice2.stream()
						// there is an element that has the same name and school as this element,
						.anyMatch(one -> intersection(split(one.getIntent()), split(two.getIntent()))
								.size() > threashold
								&& intersection(split(one.getExtent()), split(two.getExtent())).size() > threashold))

				// and collect all matching elements from the first list into a new list.
				.collect(Collectors.toList());
		// We return the collected list.
		return listOneList;
	}

	public static List<Node> extentIncludedInNextVersionViaStream(List<Node> lattice1, List<Node> lattice2) {
		// We create a stream of elements from the first list.
		List<Node> listOneList = lattice1.stream()
				// We select any elements such that in the stream of elements from the second
				// list
				.filter(two -> lattice2.stream()
						// there is an element that has the same name and school as this element,
						.anyMatch(one -> split(two.getExtent()).containsAll(split(one.getExtent()))))
				// and collect all matching elements from the first list into a new list.
				.collect(Collectors.toList());
		// We return the collected list.
		return listOneList;
	}

	public static List<Node> extentIncludedInPreviousVersionViaStream(List<Node> lattice1, List<Node> lattice2) {
		// We create a stream of elements from the first list.
		List<Node> listOneList = lattice1.stream()
				// We select any elements such that in the stream of elements from the second
				// list
				.filter(two -> lattice2.stream()
						// there is an element that has the same name and school as this element,
						.anyMatch(one -> split(one.getExtent()).containsAll(split(two.getExtent()))))
				// and collect all matching elements from the first list into a new list.
				.collect(Collectors.toList());
		// We return the collected list.
		return listOneList;
	}

	public static List<Node> intentIncludedInPreviousVersionViaStream(List<Node> lattice1, List<Node> lattice2) {
		// We create a stream of elements from the first list.
		List<Node> listOneList = lattice1.stream()
				// We select any elements such that in the stream of elements from the second
				// list
				.filter(two -> lattice2.stream()
						// there is an element that has the same name and school as this element,
						.anyMatch(one -> split(one.getIntent()).containsAll(split(two.getIntent()))))
				// and collect all matching elements from the first list into a new list.
				.collect(Collectors.toList());
		// We return the collected list.
		return listOneList;
	}

	public static List<Node> intentIncludedInNextVersionViaStream(List<Node> lattice1, List<Node> lattice2) {
		// We create a stream of elements from the first list.
		List<Node> listOneList = lattice1.stream()
				// We select any elements such that in the stream of elements from the second
				// list
				.filter(two -> lattice2.stream()
						// there is an element that has the same name and school as this element,
						.anyMatch(one -> split(two.getIntent()).containsAll(split(one.getIntent()))))
				// and collect all matching elements from the first list into a new list.
				.collect(Collectors.toList());
		// We return the collected list.
		return listOneList;
	}

	public static List<Node> intentAndExtentIncludedInNextVersionViaStream(List<Node> lattice1, List<Node> lattice2) {
		// We create a stream of elements from the first list.
		List<Node> listOneList = lattice1.stream()
				// We select any elements such that in the stream of elements from the second
				// list
				.filter(two -> lattice2.stream()
						// there is an element that has the same name and school as this element,
						.anyMatch(one -> split(two.getIntent()).containsAll(split(one.getIntent()))
								&& split(two.getExtent()).containsAll(split(one.getExtent()))))
				// and collect all matching elements from the first list into a new list.
				.collect(Collectors.toList());
		// We return the collected list.
		return listOneList;
	}
	public static List<Node> commonNodesStream(List<Node> lattice1, List<Node> lattice2) {
		// We create a stream of elements from the first list.
		List<Node> listOneList = lattice1.stream()
				// We select any elements such that in the stream of elements from the second
				// list
				.filter(two -> lattice2.stream()
						// there is an element that has the same name and school as this element,
						.anyMatch(one -> split(two.getIntent()).containsAll(split(one.getIntent()))
								&& split(two.getExtent()).containsAll(split(one.getExtent()))
						&& split(one.getIntent()).containsAll(split(two.getIntent()))
						&& split(one.getExtent()).containsAll(split(two.getExtent()))))
				// and collect all matching elements from the first list into a new list.
				.collect(Collectors.toList());
		// We return the collected list.
		
	
		return listOneList;
		
		}
	
	
	public static List<Node[]> commonExtentAndIntent(List<Node> lattice1,
			List<Node> lattice2) {
		// We create a stream of elements from the first list.
		//lattice1.removeAll(createSharedListViaStream(lattice1, lattice2));
		//lattice2.removeAll(createSharedListViaStream(lattice1, lattice2));

		List<Node[]> pairs = lattice1.stream().flatMap(i -> lattice2.stream().map(j -> new Node[] { i, j }))
				.filter(t -> split(t[1].getIntent()).containsAll(split(t[0].getIntent()))
						&& extractClassesNames(t[1].getExtent()).containsAll(extractClassesNames(t[0].getExtent()))
						&& split(t[0].getIntent()).containsAll(split(t[1].getIntent()))
						&& extractClassesNames(t[0].getExtent()).containsAll(extractClassesNames(t[1].getExtent()))
						&& t[0].getTypes().get(0).getFeatureTypeName().equals(t[1].getTypes().get(0).getFeatureTypeName())
						//&& t[1].getTypes().get(0).getFeatureTypeName().equals("ADHOC")
						)
				.collect(Collectors.toList());

		return pairs;
	}

	public static List<Node[]> intentAndExtentIncludedInNextVersionViaStreamPairs(List<Node> lattice1,
			List<Node> lattice2) {
		// We create a stream of elements from the first list.
		lattice1.removeAll(createSharedListViaStream(lattice1, lattice2));
		lattice2.removeAll(createSharedListViaStream(lattice1, lattice2));

		List<Node[]> pairs = lattice1.stream().flatMap(i -> lattice2.stream().map(j -> new Node[] { i, j }))
				.filter(t -> split(t[1].getIntent()).containsAll(split(t[0].getIntent()))
						&& split(t[1].getExtent()).containsAll(split(t[0].getExtent())))
				.collect(Collectors.toList());

		return pairs;
	}
	
	public static List<Node[]> intentAndExtentIncludedInPreviousVersionViaStreamPairs(List<Node> lattice1,
			List<Node> lattice2) {
		// We create a stream of elements from the first list.
		lattice1.removeAll(createSharedListViaStream(lattice1, lattice2));
		lattice2.removeAll(createSharedListViaStream(lattice1, lattice2));

		List<Node[]> pairs = lattice1.stream().flatMap(i -> lattice2.stream().map(j -> new Node[] { i, j }))
				.filter(t -> split(t[0].getIntent()).containsAll(split(t[1].getIntent()))
						&& split(t[0].getExtent()).containsAll(split(t[1].getExtent())))
				.collect(Collectors.toList());

		return pairs;
	}
	
	
	
	public static List<Node[]> intentAndExtentIntersectionViaStreamPairs(List<Node> lattice1,
			List<Node> lattice2, double threashold) {
		// We create a stream of elements from the first list.
		lattice1.removeAll(createSharedListViaStream(lattice1, lattice2));
		lattice2.removeAll(createSharedListViaStream(lattice1, lattice2));

		List<Node[]> pairs = lattice1.stream().flatMap(i -> lattice2.stream().map(j -> new Node[] { i, j }))
				.filter(t -> intersection (split(t[0].getIntent()),split(t[1].getIntent())).size()
						/ Integer.min(split(t[0].getIntent()).size(), split(t[1].getIntent()).size()) > threashold
						&& intersection (split(t[0].getExtent()),split(t[1].getExtent())).size()
						/ Integer.min(split(t[0].getExtent()).size(), split(t[1].getExtent()).size()) > threashold)
				.collect(Collectors.toList());

		return pairs;
	}
	
	
	
	
	
	
	public static List<Node[]> commonIntentAndExtentPairs(List<Node> lattice1,
			List<Node> lattice2) {
		// We create a stream of elements from the first list.
		//lattice1.removeAll(createSharedListViaStream(lattice1, lattice2));
		//lattice2.removeAll(createSharedListViaStream(lattice1, lattice2));

		List<Node[]> pairs = lattice1.stream().flatMap(i -> lattice2.stream().map(j -> new Node[] { i, j }))
				.filter(t -> t[0].getIntent().equals(t[1].getIntent())
						&& t[0].getExtent().equals(t[1].getExtent()))
				.collect(Collectors.toList());

		return pairs;
	}

	public static List<Node[]> commonIntentPairs(List<Node> lattice1,
			List<Node> lattice2) {
		// We create a stream of elements from the first list.
		lattice1.removeAll(createSharedListViaStream(lattice1, lattice2));
		lattice2.removeAll(createSharedListViaStream(lattice1, lattice2));

		List<Node[]> pairs = lattice1.stream().flatMap(i -> lattice2.stream().map(j -> new Node[] { i, j }))
				.filter(t -> split(t[0].getIntent()).containsAll(split(t[1].getIntent()))
						&& split(t[1].getIntent()).containsAll(split(t[0].getIntent()))
						)
				.collect(Collectors.toList());

		return pairs;
	}
	
	
	public static List<Node[]> previousIntentIncludedInNextIntentPairs(List<Node> lattice1,
			List<Node> lattice2) {
		// We create a stream of elements from the first list.
		lattice1.removeAll(createSharedListViaStream(lattice1, lattice2));
		lattice2.removeAll(createSharedListViaStream(lattice1, lattice2));

		List<Node[]> pairs = lattice1.stream().flatMap(i -> lattice2.stream().map(j -> new Node[] { i, j }))
				.filter(t -> split(t[1].getIntent()).containsAll(split(t[0].getIntent()))
						)
				.collect(Collectors.toList());

		return pairs;
	}
	public static List<Node[]> nextIntentIncludedPreviousIntentPairs(List<Node> lattice1,
			List<Node> lattice2) {
		// We create a stream of elements from the first list.
		lattice1.removeAll(createSharedListViaStream(lattice1, lattice2));
		lattice2.removeAll(createSharedListViaStream(lattice1, lattice2));

		List<Node[]> pairs = lattice1.stream().flatMap(i -> lattice2.stream().map(j -> new Node[] { i, j }))
				.filter(t -> split(t[1].getIntent()).containsAll(split(t[0].getIntent()))
						)
				.collect(Collectors.toList());

		return pairs;
	}
	
	
	public static List<Node[]> commonExtentPairs(List<Node> lattice1,
			List<Node> lattice2) {
		// We create a stream of elements from the first list.
		lattice1.removeAll(createSharedListViaStream(lattice1, lattice2));
		lattice2.removeAll(createSharedListViaStream(lattice1, lattice2));

		List<Node[]> pairs = lattice1.stream().flatMap(i -> lattice2.stream().map(j -> new Node[] { i, j }))
				.filter(t -> split(t[0].getExtent()).containsAll(split(t[1].getExtent()))
						&& split(t[1].getExtent()).containsAll(split(t[0].getExtent()))
						)
				.collect(Collectors.toList());

		return pairs;
	}

	
	public static List<Node> intentAndExtentIncludedInPreviousVersionViaStream(List<Node> lattice1,
			List<Node> lattice2) {
		// We create a stream of elements from the first list.
		List<Node> listOneList = lattice1.stream()
				// We select any elements such that in the stream of elements from the second
				// list
				.filter(two -> lattice2.stream()
						// there is an element that has the same name and school as this element,
						.anyMatch(one -> split(one.getIntent()).containsAll(split(two.getIntent()))
								&& split(one.getExtent()).containsAll(split(two.getExtent()))))
				// and collect all matching elements from the first list into a new list.
				.collect(Collectors.toList());
		// We return the collected list.
		return listOneList;
	}

	public static List<Node> intentIncludedInNextAndExtentIncludedInPreviousVersionViaStream(List<Node> lattice1,
			List<Node> lattice2) {
		// We create a stream of elements from the first list.
		List<Node> listOneList = lattice1.stream()
				// We select any elements such that in the stream of elements from the second
				// list
				.filter(two -> lattice2.stream()
						// there is an element that has the same name and school as this element,
						.anyMatch(one -> split(two.getIntent()).containsAll(split(one.getIntent()))
								&& split(one.getExtent()).containsAll(split(two.getExtent()))))
				// and collect all matching elements from the first list into a new list.
				.collect(Collectors.toList());
		// We return the collected list.
		return listOneList;
	}

	public static List<Node> extentIncludedInNextAndIntentIncludedInPreviousVersionViaStream(List<Node> lattice1,
			List<Node> lattice2) {
		// We create a stream of elements from the first list.
		List<Node> listOneList = lattice1.stream()
				// We select any elements such that in the stream of elements from the second
				// list
				.filter(two -> lattice2.stream()
						// there is an element that has the same name and school as this element,
						.anyMatch(one -> split(one.getIntent()).containsAll(split(two.getIntent()))
								&& split(two.getExtent()).containsAll(split(one.getExtent()))))
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
	
	public static boolean isChild (Node nChild, Node n) {
		return extractClassesNames(n.getExtent()).containsAll(extractClassesNames(nChild.getExtent())) &&
				split(nChild.getIntent()).containsAll(split(n.getIntent()));
	}
	
	public static String extractClassName (String extent) {
		String [] elements = extent.split("\\.");
		if (elements.length>1)
			return elements[elements.length-1];
		else return "";
	}
	
	public static List<String> extractClassesNames (String extents) { 
		List<String> classNames = new ArrayList<String>();
		
		String [] extentsTab = extents.split(", ");
		for (int i=0 ; i < extentsTab.length ; i++ ) {
			classNames.add(extractClassName(extentsTab[i]));
		}
		return classNames;
	}

}
