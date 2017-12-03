import java.util.LinkedList;
import java.util.List;

public class Vertex {

  public String name;
  public boolean known;
  public double price; // total price from origin point
  public Vertex prev;
  public List<Edge> adjacentEdges;

  public Vertex(String name) {
    this.name = name;
    price = Double.POSITIVE_INFINITY;
    // by default java sets uninitialized boolean to false and double to 0
    // hence known == false and dist == 0.0
    adjacentEdges = new LinkedList<Edge>();
    prev = null;
  }

  @Override
  public int hashCode() {
    // we assume that each vertex has a unique name
    return name.hashCode();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null) {
      return false;
    }
    if (!(o instanceof Vertex)) {
      return false;
    }
    Vertex oVertex = (Vertex) o;

    return name.equals(oVertex.name);
  }

  public void addEdge(Edge edge) {
    adjacentEdges.add(edge);
  }

  public String toString() {
    return name;
  }

}