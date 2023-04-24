package taraladalal;



	
		import java.util.ArrayList;

		public class elmination {
			
		public static ArrayList<String> eliminatedList= new ArrayList<String>();

		


		public static void main(String[] args) {
			

		
				
					

					String elim = "rice fruits";
					//System.out.println("igredeintes coming"+p_Ingredient);
					
					eliminatedList.add("cake");
					eliminatedList.add("pastries");
					eliminatedList.add("fried food");
					eliminatedList.add("white bread");
					eliminatedList.add("pizza");
					eliminatedList.add("burger");
					eliminatedList.add("carbonated beverages");
					eliminatedList.add("sweets");
					eliminatedList.add("soda");
					eliminatedList.add("icecream");
					eliminatedList.add("gatorade");
					eliminatedList.add("apple juice");
					eliminatedList.add("orange juice");
					eliminatedList.add("pomegranate juice");
					eliminatedList.add("red meat");
					eliminatedList.add("processed meatt");
					eliminatedList.add("milk");
					eliminatedList.add("curd");
					eliminatedList.add("dahi");
					eliminatedList.add("paneer");
					eliminatedList.add("soy products");
					eliminatedList.add("gluten");
					eliminatedList.add("pasta");
					eliminatedList.add("white rice");
					eliminatedList.add("donuts");
					eliminatedList.add("fries");
					eliminatedList.add("coffee");
					eliminatedList.add("vegetable oil");
					eliminatedList.add("soybean oil");
					eliminatedList.add("canola oil");
					eliminatedList.add("rapeseed oil");
					eliminatedList.add("sunflower oil");
					eliminatedList.add("safflower oil");
					eliminatedList.add("doughnuts");
					//ing compare with arraylist
					boolean isIngrediant1=true;
					
					for(String v:eliminatedList)
					
					{
						
						
						
						if (elim.contains(v)) //ing=v
						{
							System.out.println("Eliminated::"+v);
							
							isIngrediant1=false;
							
							
							break;
						}
					}
					if(isIngrediant1==true)
					{
						System.out.println("print in xl");
						
					}
					
			}
				
					
		

	}


