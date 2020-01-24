import java.util.*;

//DIJKSTRA
//https://www.geeksforgeeks.org/dijkstras-shortest-path-algorithm-in-java-using-priorityqueue/

class Pad implements Comparable<Pad> {
    public int som;
    public int afstand;
    public int x;
    public int y;

    Pad(int somC, int afstandC, int xC, int yC){
		som=somC;
		afstand=afstandC;
		x=xC;
		y=yC;
	}
    
    @Override
    public int compareTo(Pad that) {
        return this.som != that.som ? Integer.compare(this.som, that.som)
                                      : Integer.compare(this.afstand, that.afstand);
    }
}

class Vergelijk{
	boolean compareTo(Pad links, Pad rechts) {
		return links.som != rechts.som ? links.som > rechts.som : links.afstand > rechts.afstand;
	}
}

class Mijnenveld{
	int[][]veld;
	int rijen;
	int kolommen;
	int lengte;
	int aantalMijnen;
	
	Mijnenveld(int[][] veld, int rijen, int kolommen){
		this.veld=veld;
		this.rijen=rijen;
		this.kolommen=kolommen;
	}
	
	Pad dijkstra() {
		boolean[][] alGebruikt = new boolean[rijen][kolommen];
		PriorityQueue<Pad> prioriteit = new PriorityQueue<Pad>();
		for(int i=0; i<rijen; i++) {
			prioriteit.add(new Pad(0, 0, -1, i));
		}
		
		while(prioriteit.peek().x != kolommen-1) {
			Pad p = prioriteit.peek();
			prioriteit.poll();
			
			if (p.x < 0 || !alGebruikt[p.y][p.x]) {
				if(p.x >= 0) alGebruikt[p.y][p.x] = true;

				prioriteit.add(								
						new Pad(p.som + veld[p.y][p.x + 1], p.afstand + 1, p.x + 1, p.y));
				if (p.x > 0) prioriteit.add(
						new Pad(p.som + veld[p.y][p.x - 1], p.afstand + 1, p.x - 1, p.y));
				if (p.x >= 0 && p.y > 0) prioriteit.add(
						new Pad(p.som + veld[p.y - 1][p.x], p.afstand + 1, p.x, p.y - 1));
				if (p.x >= 0 && p.y < rijen - 1) prioriteit.add(
						new Pad(p.som + veld[p.y + 1][p.x], p.afstand + 1, p.x, p.y + 1));
			}
		}
		return prioriteit.peek();
	}
}

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		//inlezen
		int aantalTestGevallen = Integer.parseInt(sc.nextLine());
		for(int i=0; i<aantalTestGevallen; i++) {
			int rijen = Integer.parseInt(sc.next());
			int kolommen = Integer.parseInt(sc.next());
			int[][] veld = new int[rijen][kolommen];
			
			for(int r=0; r<rijen; r++) {
				for(int k=0; k<kolommen; k++) {
					veld[r][k] = Integer.parseInt(sc.next());
				}
			}
			
			//start berekenen
			Mijnenveld mijnen = new Mijnenveld(veld, rijen, kolommen);
			Pad oplossing = mijnen.dijkstra();
			System.out.println(i+1 + " " + oplossing.afstand + " " + oplossing.som);
		}
		
		sc.close();
	}

}

