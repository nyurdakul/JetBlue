import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Calendar;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;

public class Dijkstra {

  // Keep a fast index to nodes in the map
  private static Map<String, Vertex> vertexNames;

  /**
   * Construct an empty Dijkstra with a map. The map's key is the name of a vertex
   * and the map's value is the vertex object.
   */
  public Dijkstra() {
    vertexNames = new HashMap<String, Vertex>();
  }

  /**
   * Adds a vertex to the dijkstra. Throws IllegalArgumentException if two vertices
   * with the same name are added.
   * 
   * @param v
   *          (Vertex) vertex to be added to the dijkstra
   */
  public void addVertex(Vertex v) {
    if (vertexNames.containsKey(v.name))
      throw new IllegalArgumentException("Cannot create new vertex with existing name.");
    vertexNames.put(v.name, v);
  }

  /**
   * Gets a collection of all the vertices in the dijkstra
   * 
   * @return (Collection<Vertex>) collection of all the vertices in the dijkstra
   */
  public static Collection<Vertex> getVertices() {
    return vertexNames.values();
  }

  /**
   * Gets the vertex object with the given name
   * 
   * @param name
   *          (String) name of the vertex object requested
   * @return (Vertex) vertex object associated with the name
   */
  public static Vertex getVertex(String name) {
    return vertexNames.get(name);
  }


  // STUDENT CODE STARTS HERE
  /**
   * Computes the euclidean distance between two points as described by their
   * coordinates
   * 
   * @param ux
   *          (double) x coordinate of point u
   * @param uy
   *          (double) y coordinate of point u
   * @param vx
   *          (double) x coordinate of point v
   * @param vy
   *          (double) y coordinate of point v
   * @return (double) distance between the two points
   */
  /*public double computeEuclideanDistance(double ux, double uy, double vx, double vy) {
        double dist;
        double xdist = Math.abs(ux-vx);
        double ydist = Math.abs(uy-vy);
        dist = Math.sqrt((xdist*xdist + ydist*ydist));
        return dist;
  }*/
  /**
   * Calculates the euclidean distance for all edges in the map using the
   * computeEuclideanCost method.
   */
  /*public void computeAllEuclideanDistances() {
	  Collection<Vertex> vertexList = getVertices();
	  for (Vertex elements : vertexList) {
		  for(Edge edges: elements.adjacentEdges){
			  edges.distance = computeEuclideanDistance(edges.source.x, edges.source.y, edges.target.x, edges.target.y);
		  }
	  }
  }*/
  /**
   * Dijkstra's Algorithm. 
   * 
   * @param s
   *          (String) starting city name
   */
  /*public void doDijkstra(String s) {
	  	boolean noUnknowns = false;
	    Vertex start = getVertex(s);
	    start.distance = 0;
	    start.known = true;
	    while(!noUnknowns){
	    	//loop through all edges
	    	for(Edge edges: start.adjacentEdges){
		    	if(edges.target.known == false){
		    		//check to see if distance needs to be changed 
		    		double newDist = edges.distance + edges.source.distance;
		    		if(edges.target.distance ==0 || newDist< edges.target.distance){
		    			//change distance change previous
		    			edges.target.prev = start;
				    	edges.target.distance = newDist;
		    		}
		    	}
		    }
	    	start = findMin();
	    	if(start ==null){
	    		noUnknowns = true;
	    		break;
	    	}
	    	System.out.println("Minimum " + start.name + " " + start.distance);
	    	start.known = true;
	    }
  }*/
  public void doDijkstra(String s, int maxPrice) {
      //purgePrices(maxPrice);
	  	boolean noUnknowns = false;
	    Vertex start = getVertex(s);
	    start.price = 0;
	    start.known = true;
	    while(!noUnknowns){
	    	//loop through all edges
	    	for(Edge edges: start.adjacentEdges){
          findBestFlight(edges); 
		    	if(edges.target.known == false){
		    		//check to see if distance needs to be changed 
		    		double newDist = edges.price + edges.source.price;
                        System.out.println(edges.source.price);
            System.out.println(edges.price);

            System.out.println(newDist);
		    		if(edges.target.price == Double.POSITIVE_INFINITY || newDist < edges.target.price){
		    			//change distance change previous
		    			edges.target.prev = start;
				    	edges.target.price = newDist;
		    		}
		    	}
		    }
	    	start = findMin();
	    	if(start == null){
	    		noUnknowns = true;
	    		break;
	    	}
	    	System.out.println("Minimum " + start.name + " " + start.price);
	    	start.known = true;
	    }
  }
  public Flight findBestFlight(Edge myEdge){
    Flight min = new Flight();
    min.price = Double.POSITIVE_INFINITY;
    for(Flight myFlights: myEdge.possibleFlights){
      Flight temp = myFlights;
      if(temp.price < min.price)
        min = temp;        
    }
    return min;
  }
  public Vertex findMin(){
	 Vertex min = null;
	 Collection<Vertex> vertexList = getVertices();
 	  	for (Vertex elements : vertexList) {
 	  		if(elements.known == false){
 	  			if(elements.price != 0){
 	  				if(min == null)
 	            min = elements;
 	  				if(min.price < elements.price)
 	  	        min = elements;
          }
 	  		}
 	  	}
  	return min;
  }

  /**
   * Returns a list of edges for a path from city s to city t. This will be the
   * shortest path from s to t as prescribed by Dijkstra's algorithm
   * 
   * @param s
   *          (String) starting city name
   * @param t
   *          (String) ending city name
   * @return (List<Edge>) list of edges from s to t
   */
public void purgePrices(int highPrice){
	 Collection<Vertex> vertexList = getVertices();
	  	for (Vertex elements : vertexList) {
	  		for(Edge myEdges : elements.adjacentEdges){
	  			for(Flight g : myEdges.possibleFlights){
	  				if(g.price > highPrice){
	  					myEdges.remove(g);
	  				}
	  			}
  			}
  		}
}
  public List<Edge> getDijkstraPath(String s, String t, int price) {
	washVertexes();
    doDijkstra(s, price);
    List<Edge> myList = new LinkedList<Edge>();
    Vertex end = getVertex(t);
    while(!end.name.equals(s)){
    	Edge current = null;
        for(Edge edges: end.adjacentEdges){
          //System.out.println(edges.target.toString());
          //System.out.println(end.prev);
        	if(edges.target.equals(end.prev)){
            System.out.println("please");
        		current = edges;
        	}
        }
    	myList.add(0, current);
    	System.out.println(end.name);
    	end = current.target;
    }
    return myList;
 }
   public void washVertexes(){
    	Collection<Vertex> vertexList = getVertices();
  	  	for (Vertex elements : vertexList) {
  	  		elements.price = Double.POSITIVE_INFINITY;
  	  		elements.known = false;
  	  	}
  }
  // STUDENT CODE ENDS HERE
  /**
   * Prints out the adjacency list of the dijkstra for debugging
   */
  /*public void printAdjacencyList() {
    for (String u : vertexNames.keySet()) {
      StringBuilder sb = new StringBuilder();
      sb.append(u);
      sb.append(" -> [ ");
      for (Edge e : vertexNames.get(u).adjacentEdges) {
        sb.append(e.target.name);
        sb.append("(");
        sb.append(e.distance);
        sb.append(") ");
      }
      sb.append("]");
      System.out.println(sb.toString());
    }
  }*/
  /** 
   * A main method that illustrates how the GUI uses Dijkstra.java to 
   * read a map and represent it as a graph. 
   * You can modify this method to test your code on the command line. 
   */
  public static Edge findEdge(String s, String t){
	  Edge myEdge = null;
	  Collection<Vertex> vertexList = getVertices();
	  	for (Vertex elements : vertexList) {
	  		for(Edge myEdges : elements.adjacentEdges){
	  			if(myEdges.source.name.equals(s))
	  				if(myEdges.target.name.equals(t))
	  					myEdge = myEdges;
	  		}
	  	}
  	return myEdge;
  }
  public static Calendar convertTime(String time) {
    Calendar cal = Calendar.getInstance();
	  String[] array = time.split("\\W+");
    //cal.set(2017, 5, 24, 15, 45);
    cal.set(Integer.parseInt(array[2]), Integer.parseInt(array[0])-1, Integer.parseInt(array[1]), Integer.parseInt(array[3]), Integer.parseInt(array[4]));
	  //System.out.println(cal.getTime());
	  return cal;
	  }


  public static void main(String[] argv) throws IOException {
    String edgeFile = "LowestFares.csv"; 
    String vertexFile = "vertex.txt";
    String dealFile = "Deals.csv";
    String startCity = "ABQ";
    String endCity = "SAN";
    int stops = 5;
    int startDate = 20171228;
    int endDate = 20180109;
    int priceMax = 500;
    int layoverTimes = 2;
    Dijkstra dijkstra = new Dijkstra();
    String line;
    //read in vertices
    BufferedReader vertexFileBr = new BufferedReader(new FileReader(vertexFile));
    while ((line = vertexFileBr.readLine()) != null) {
      String[] parts = line.split(",");
      if (parts.length != 1) {
        vertexFileBr.close();
        throw new IOException("Invalid line in edge file " + line);
      }
      Vertex v = new Vertex(parts[0]);
      dijkstra.addVertex(v);
    }
    vertexFileBr.close();
    // Read in the edges for lowestFares
    BufferedReader edgeFileBr = new BufferedReader(new FileReader(edgeFile));
    while ((line = edgeFileBr.readLine()) != null) {
      String[] parts = line.split(",");
      if (parts.length != 11) {
        edgeFileBr.close();
        throw new IOException("Invalid line in vertex file " + line);
      }
      String source = parts[0];
      String target = parts[1];
      Edge currentEdge = findEdge(source, target);
      if(currentEdge == null){
    	  Edge x = new Edge(getVertex(source), getVertex(target));
    	  getVertex(source).addEdge(x);
    	  double price = Double.parseDouble(parts[5]) + Double.parseDouble(parts[6]);
    	  Calendar cal = convertTime(parts[2]);
    	  boolean isPoints;
    	  if(parts[4].equals("POINTS"))
    		  isPoints = true;
    	  else
    		  isPoints = false;
    	  Flight y = new Flight(cal, price, isPoints);
    	  x.addFlight(y);
      }
      else{
    	  double price = Double.parseDouble(parts[5]) + Double.parseDouble(parts[6]);
    	  Calendar cal = convertTime(parts[2]);
    	  boolean isPoints;
    	  if(parts[4].equals("POINTS"))
    		  isPoints = true;
    	  else
    		  isPoints = false;
    	  Flight x = new Flight(cal, price, isPoints);
    	  currentEdge.addFlight(x);
      }
    }
    edgeFileBr.close();
    /*
    int increment = 0;
    BufferedReader dealFileBr = new BufferedReader(new FileReader(dealFile));
    while ((line = dealFileBr.readLine()) != null) {
        String[] parts = line.split(",");
        /*if (parts.length != 22) {
          dealFileBr.close();
          throw new IOException("Invalid line in edge file " + line);
        }*//*
        String source = parts[1];
        String target = parts[2];
        Edge currentEdge = findEdge(source, target);
        if(currentEdge == null){
      	  Edge x = new Edge(getVertex(source), getVertex(target));
      	  getVertex(source).addEdge(x);
      	  double price = Double.parseDouble(parts[8]) + Double.parseDouble(parts[9]);
      	//  Calendar cal = convertTime(parts[3]);
      	  boolean isPoints;
      	  if(parts[6].equals("POINTS"))
      		  isPoints = true;
      	  else
      		  isPoints = false;
      	  //Flight y = new Flight(cal, price, isPoints);
      	  //x.addFlight(y);
        }
        else{
      	  double price = Double.parseDouble(parts[8]) + Double.parseDouble(parts[9]);
      	//  Calendar cal = convertTime(parts[3]);
      	  boolean isPoints;
      	  if(parts[6].equals("POINTS"))
      		  isPoints = true;
      	  else
      		  isPoints = false;
      	  //Flight x = new Flight(cal, price, isPoints);
      	  //currentEdge.addFlight(x);
        }
      }
    dealFileBr.close();*/
    // Compute distances. 
    // This is what happens when you click on the "Compute All Euclidean Distances" button.
   // dijkstra.computeAllEuclideanDistances();
    
    // print out an adjacency list representation of the graph
    //dijkstra.printAdjacencyList();

    // This is what happens when you click on the "Draw Dijkstra's Path" button.

    // In the GUI, these are set through the drop-down menus.
    //String startCity = "SanFrancisco";
    //String endCity = "Boston";

    // Get weighted shortest path between start and end city. 
    List<Edge> path = dijkstra.getDijkstraPath(startCity, endCity, priceMax);
    
    System.out.print("Shortest path between "+startCity+" and "+endCity+": ");
    System.out.println(path);
  }
}
