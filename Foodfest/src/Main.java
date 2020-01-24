import java.util.*;


public class Main {
	static void recursie(List<List<Integer>> nieuwePrijzen, List<Integer> combinaties, int start, int tijd) {
		if(start == nieuwePrijzen.size()) {
			combinaties.add(tijd);
			return;
		}
		
		for(int i=0; i<nieuwePrijzen.get(start).size(); i++) {
			recursie(nieuwePrijzen, combinaties, start+1, tijd + nieuwePrijzen.get(start).get(i));
		}
	}

	public static void main(String[] args) {
		String lijn;
		Scanner sc = new Scanner(System.in);
		lijn = sc.nextLine();
		int aantalTestgevallen = Integer.parseInt(lijn);
		for(int i=0; i<aantalTestgevallen; i++) {
			List<Integer> bruikbareBedragen = new ArrayList<Integer>();
			List<List<Integer>> prijsGerecht = new ArrayList<List<Integer>>();
			List<Integer> combinaties = new ArrayList<Integer>();
			
			//inlezen gegevens
			lijn = sc.nextLine();
			String[] temp = lijn.split(" ");
			int aantalBudgetten = Integer.parseInt(temp[0]);
			int[] budgetten = new int[aantalBudgetten];
			for(int j=0; j<aantalBudgetten; j++) {
				budgetten[j] = Integer.parseInt(temp[j+1]);
			}
			lijn=sc.nextLine();
			int aantalTrucks = Integer.parseInt(lijn);
			for(int j=0; j<aantalTrucks; j++) {
				prijsGerecht.add(new ArrayList<Integer>());
				lijn=sc.nextLine();
				temp = lijn.split(" ");
				int aantalGerechten = Integer.parseInt(temp[0]);
				for(int w=0; w<aantalGerechten; w++) {
					prijsGerecht.get(j).add(Integer.parseInt(temp[w+1]));
				}
			}
			
			//start recursie methode
			recursie(prijsGerecht, combinaties, 0, 0);
			
			//controle of er een budget overeenstemt met alle combinaties + sorteren van klein naar groot
			for(int j=0; j<budgetten.length; j++) {
				if (combinaties.contains(budgetten[j])){
					bruikbareBedragen.add(budgetten[j]);
				}
			}
			Collections.sort(bruikbareBedragen);

			//Start uitprinten
			System.out.print(i+1);
			if(bruikbareBedragen.isEmpty()) {							
				System.out.print(" GEEN");
			} else {
				for(int j : bruikbareBedragen) {
					System.out.print(" " + j);
				}
			}

			System.out.println();
		}
		sc.close();
	}

}
