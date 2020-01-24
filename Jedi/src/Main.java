import java.util.*;

//https://www.geeksforgeeks.org/dynamic-programming-set-23-bellman-ford-algorithm/
//voor uitleg bellman-ford algoritme
class Edge{
	int start;
	int eind;
	int gewicht;
	
	Edge(){
		start=0;
		eind=0;
		gewicht=0;
	}
	
	public int getStart() {
		return start;
	}
	
	public int getEind() {
		return eind;
	}
	
	public int getGewicht() {
		return gewicht;
	}
}
class Jedi{
	int knoopPunten;
	int aantalEdges;
	Edge[] edge;
	
	public Jedi(int knoopPunten, int aantalEdges) {
		this.knoopPunten = knoopPunten;
		this.aantalEdges = aantalEdges;
		this.edge = new Edge[aantalEdges];
		for(int i=0; i<aantalEdges; i++){
			edge[i] = new Edge();
		}
	}
	
	public int getStart(int w) {
		return(this.edge[w].getStart());
	}
	
	public int getEind(int w) {
		return(this.edge[w].getEind());
	}
	
	public int getGewicht(int w) {
		return(this.edge[w].getGewicht());
	}
	
	public int bellmanFordAlgo(Jedi parcour) {
		int[] afstanden = new int[knoopPunten];
		
		for(int w=0; w<knoopPunten; w++) {
			afstanden[w] = Integer.MAX_VALUE;
		}
		afstanden[0] = 0;
		
		for(int i=1; i<knoopPunten; i++) {
			for(int w=0; w<aantalEdges; w++) {
				int van = parcour.getStart(w);
				int naar = parcour.getEind(w);
				int gewicht = parcour.getGewicht(w);
				
				if(afstanden[van-1] != Integer.MAX_VALUE && afstanden[van-1]+gewicht < afstanden[naar-1]) {
					afstanden[naar-1] = afstanden[van-1]+gewicht;
				}
			}
		}
		
		for (int w=0; w<aantalEdges; w++) {
			int van = parcour.getStart(w);
	        int naar = parcour.getEind(w);
	        int gewicht = parcour.getGewicht(w);
	
	        if (afstanden[van-1] != Integer.MAX_VALUE && afstanden[van-1]+gewicht < afstanden[naar-1] ){
	            return Integer.MIN_VALUE;
	        }
		}
	    return afstanden[afstanden.length-1];
	}
}


public class Main {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		//inlezen
		int aantalTestGevallen = Integer.parseInt(sc.nextLine());
		for(int i=0; i<aantalTestGevallen; i++) {
			int knoopPunten = Integer.parseInt(sc.next());
			int edges = Integer.parseInt(sc.next());
			
			Jedi parcour = new Jedi(knoopPunten, edges);
			
			for(int j=0; j<edges; j++) {
				int begin = Integer.parseInt(sc.next());
				int eind = Integer.parseInt(sc.next());
				int strafPunten = Integer.parseInt(sc.next());
				
				parcour.edge[j].start = begin;
				parcour.edge[j].eind = eind;
				parcour.edge[j].gewicht = strafPunten;
			}
			
			int oplossing = parcour.bellmanFordAlgo(parcour);
			switch(oplossing) {
				case Integer.MAX_VALUE:
					System.out.println(i+1 + " plus oneindig");
					break;
				case Integer.MIN_VALUE:
					System.out.println(i+1 + " min oneindig");
					break;
				default:
					System.out.println(i+1 + " " + oplossing);
					break;
			}
		}
		sc.close();
	}

}
