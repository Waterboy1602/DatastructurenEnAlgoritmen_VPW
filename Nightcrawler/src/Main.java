import java.util.*;

class Nightcrawler{
	static int oneindig = 999_999_999;
	int[][] stratenPlan;
	int maxAfstand;
	int aantalKruispunten;
	int aantalStraten;
	List<List<Integer>> oplossingen = new ArrayList<List<Integer>>();
	int besteTotaalAfstand;

	
	Nightcrawler(int[][] stratenPlanC, int maxAfstandC, int aantalKruispuntenC, int aantalStratenC){
		stratenPlan=stratenPlanC;
		maxAfstand=maxAfstandC;
		aantalKruispunten=aantalKruispuntenC;
		aantalStraten=aantalStratenC;
		besteTotaalAfstand=oneindig;
	}
	
	void alleKortsteRoutes() {
		for(int i=0; i<aantalKruispunten; i++) {
			for( int j=0; j<aantalKruispunten; j++) {
				for(int k=0; k<aantalKruispunten; k++) {
					stratenPlan[j][k] = Math.min(stratenPlan[j][k], stratenPlan[j][i] + stratenPlan[i][k]);
				}
			}
		}
	}
	
	void bereken(List<Integer> stapel) {
		Map<Integer, Integer> afstanden = new HashMap<Integer, Integer>();
		for(int i=0; i<aantalKruispunten; i++) {
			afstanden.put(i, oneindig);
		}
		
		for(int i : stapel) {
			for(int j=0; j< aantalKruispunten; j++) {
				afstanden.replace(j, Math.min(afstanden.get(j), stratenPlan[i][j]));
			}
		}
		
		int max = Collections.max(afstanden.values());
		
		if(max>maxAfstand) return;
		
		int totaalAfstand=0;
		for(int i : afstanden.keySet()) {
			totaalAfstand += afstanden.get(i);
		}
		
		if(totaalAfstand>besteTotaalAfstand) return;
		if(totaalAfstand<besteTotaalAfstand) oplossingen.clear();
		List<Integer> tijd = new ArrayList<Integer>();

		for(Integer k : stapel) {
			tijd.add(k);
		}
		
		oplossingen.add(tijd);
		besteTotaalAfstand = totaalAfstand;
	}
	
	void oplossen(int teller, int start, List<Integer> stapel){
		if(teller==0) { 
			bereken(stapel);
			return;
		}

		for(int i = start; i < aantalKruispunten; i++) {
			stapel.add(i);
			oplossen(teller-1, i+1, stapel);
			stapel.remove(Integer.valueOf(i));
		}
	}
	
	List<List<Integer>> getOplossingen(){
		return oplossingen;
	}
}

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int oneindig = 999_999_999;
		
		//inlezen
		int aantalTestGevallen = Integer.parseInt(sc.nextLine());
		for(int i=0; i<aantalTestGevallen; i++) {
			
			int maxAfstand = Integer.parseInt(sc.next());
			int aantalKruispunten = Integer.parseInt(sc.next());
			int aantalStraten = Integer.parseInt(sc.next());

			int[][] stratenPlan = new int[aantalKruispunten][aantalKruispunten];
			for(int j=0; j<aantalKruispunten; j++) {
				for(int k=0; k<aantalKruispunten; k++) {
					stratenPlan[j][k]=oneindig;
				}
			}
			
			for(int j=0; j<aantalKruispunten; j++) {
				stratenPlan[j][j] = 0;
			}
			
			for(int j=0; j<aantalStraten; j++) {
				int x = Integer.parseInt(sc.next());
				int y = Integer.parseInt(sc.next());
				int gewicht = Integer.parseInt(sc.next());
				stratenPlan[x-1][y-1] = gewicht;
				stratenPlan[y-1][x-1] = gewicht;
			}
			
			//if(i==111) {
				Nightcrawler graaf = new Nightcrawler(stratenPlan, maxAfstand, aantalKruispunten, aantalStraten);
				graaf.alleKortsteRoutes();
				for(int j=1; j<=aantalKruispunten && graaf.getOplossingen().size()==0; j++) {
					List<Integer> tijd = new ArrayList<Integer>();
					graaf.oplossen(j, 0, tijd);
				}
				
				List<String> output = new ArrayList<String>();
				for(List<Integer> k : graaf.getOplossingen()) {
					String lijn = Integer.toString(i+1);
					for(Integer l : k) {
						lijn += " " + Integer.toString(l+1);
					}
					output.add(lijn);					
				}
				
				Collections.sort(output);
				
				for(String s : output) {
					System.out.println(s);
				}
			}
			
		//}
		
		sc.close();
	}

}
