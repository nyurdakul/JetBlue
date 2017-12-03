import java.util.Calendar;

public class Flight {
	Calendar time;
	double price;
	boolean points;
	boolean lazyDelete;
	public Flight(Calendar x, double y, boolean p){
		time = x;
		price = y;
		points = p;
		lazyDelete = false;
	}

	public Flight(){
	}

	public String toString(){
		if(points){
			return "POINTS " + price + " " + time.getTime(); 
		}
		else
			return "MONEY " + price + " " + time.getTime(); 
	}
}
