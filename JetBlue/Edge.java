import java.util.LinkedList;
import java.util.List;

public class Edge {

  public double price;
  public Vertex source;
  public Vertex target;
  public List<Flight> possibleFlights;

  public Edge(Vertex vertex1, Vertex vertex2) {
    source = vertex1;
    target = vertex2;
    possibleFlights = new LinkedList<Flight>();
  	}
  
  public void addFlight(Flight myFlight){
	  possibleFlights.add(myFlight);
  }

  public String toString() {
    String myString = source + " - " + target + ": ";
    for(Flight m: possibleFlights)
    	myString += m.toString();
    return myString;
  }
  public void remove(Flight myFlight){
	  possibleFlights.get(possibleFlights.indexOf(myFlight)).lazyDelete = true;
  }
  
}