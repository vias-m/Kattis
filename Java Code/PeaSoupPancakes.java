import java.util.*;


class PeaSoupPancakes{
	
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();

		for(; n>0; n--){
			boolean ps = false, pc = false;
			int k = sc.nextInt();
			sc.nextLine();
			String name = sc.nextLine();

			for(; k>0; k--){
				String dish = sc.nextLine();
				if(dish.equals("pea soup")){
					ps = true;
				}else if (dish.equals("pancakes")){
					pc = true;
				}
			}
			if(ps && pc){
				System.out.println(name);
				break;
			}else if(n==1){
				System.out.println("Anywhere is fine I guess");
				break;
			}
		}
	}
}
