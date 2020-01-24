import java.util.*;

class Reizen{
	int[][] netwerk;
	int[] steden;
	static int oneindig=9999999;
	
	Reizen(int[][] netwerkC, int[] stedenC){
		this.netwerk = netwerkC;
		this.steden = stedenC;
	}
	
	void alleKortsteRoutes() {
		for(int i=0; i<netwerk.length; i++) {
			for( int j=0; j<netwerk.length; j++) {
				for(int k=0; k<netwerk.length; k++) {
					netwerk[j][k] = Math.min(netwerk[j][k], netwerk[j][i] + netwerk[i][k]);
				}
			}
		}
	}
	
	int berekenen(int positie, int tijd, List<Integer> voetbal, int teller) {
		if(teller==steden.length) {
			if(voetbal.size()==0) {
				return -1;
			} else {
				return -oneindig;
			}
		}
		
		int oplossing = -oneindig;
		
		for(Integer x : voetbal) {
			List<Integer> tijdVoetbal = new ArrayList<Integer>();
			for(int j=0; j<voetbal.size(); j++) {
				if(voetbal.get(j)!=x) {
					tijdVoetbal.add(voetbal.get(j));
				}
			}
			oplossing = Math.max(oplossing, berekenen(x, tijd + netwerk[positie][x], tijdVoetbal, teller));
		}
		
		for(int i=teller; i<steden.length; i++) {
			if (tijd + netwerk[positie][steden[i]] <= i + 1) {
				oplossing = Math.max(oplossing, berekenen(steden[i], i + 1, voetbal, i + 1) + 1);
			}
		}
		
		
		return oplossing;
	}
}

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int oneindig = 999999;
		
		//inlezen
		int aantalTestGevallen = Integer.parseInt(sc.nextLine());
		for(int i=0; i<aantalTestGevallen; i++) {
			int aantalSteden = Integer.parseInt(sc.next());
			int start = Integer.parseInt(sc.next());
			start--;
			int eind = Integer.parseInt(sc.next());
			eind--;
			int overnachtingen = Integer.parseInt(sc.next());
			int[] steden = new int[overnachtingen+1];			
			for(int j=0; j<overnachtingen; j++) {
				steden[j] = Integer.parseInt(sc.next())-1;
			}
			steden[overnachtingen]=eind;
			
			int aantalVoetbal = Integer.parseInt(sc.next());
			List<Integer> voetbal = new ArrayList<Integer>();
			for(int j=0; j<aantalVoetbal; j++) {
				voetbal.add(Integer.parseInt(sc.next())-1);
			}
			
			int knooppunten = Integer.parseInt(sc.next());
			int[][] netwerk = new int[aantalSteden][aantalSteden];
			
			for(int j=0; j<aantalSteden; j++) {
				for(int k=0; k<aantalSteden; k++) {
					netwerk[j][k]=oneindig;
				}
			}
			
			for(int j=0; j<aantalSteden; j++) {
				netwerk[j][j]=0;
			}
			
			for(int j=0; j<knooppunten; j++) {
				int x = Integer.parseInt(sc.next());
				int y = Integer.parseInt(sc.next());
				netwerk[x-1][y-1]=1;
				netwerk[y-1][x-1]=1;
			}
			
			//berekenen
			Reizen reis = new Reizen(netwerk, steden);
			reis.alleKortsteRoutes();
			
			int oplossing = reis.berekenen(start, 0, voetbal, 0);
			if(oplossing<0) {
				System.out.println(i+1 + " onmogelijk");
			} else {
				System.out.println(i+1 + " " + oplossing);
			}
			
		}
		sc.close();
	}

}
