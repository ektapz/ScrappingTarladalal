package taraladalal;


	import java.io.FileOutputStream;
	import java.io.IOException;
	import java.time.Duration;
	import java.util.ArrayList;
	import java.util.List;

	import org.apache.poi.xssf.usermodel.XSSFRow;
	import org.apache.poi.xssf.usermodel.XSSFSheet;
	import org.apache.poi.xssf.usermodel.XSSFWorkbook;
	import org.openqa.selenium.By;
	import org.openqa.selenium.WebDriver;
	import org.openqa.selenium.WebElement;
	import org.openqa.selenium.chrome.ChromeDriver;
	import org.openqa.selenium.chrome.ChromeOptions;

	import io.github.bonigarcia.wdm.WebDriverManager;

	public class Final {

		static WebDriver driver;
		public static ArrayList recipeNameList = new ArrayList();
		public static ArrayList recipeid = new ArrayList();
		public static ArrayList ingredientList = new ArrayList();
		public static ArrayList prepTimeList = new ArrayList();
		public static ArrayList cookTimeList = new ArrayList();
		public static ArrayList prepMethodList = new ArrayList();
		public static ArrayList NutrientList = new ArrayList();
		public static ArrayList RecipeUrlList = new ArrayList();
		public static ArrayList<String> eliminatedList= new ArrayList<String>();
		public static ArrayList<String> addedElementList= new ArrayList<String>();	
		public static ArrayList toaddingredientList = new ArrayList();
		
		
		
		public static void setup() {
			
			WebDriverManager.chromedriver().setup();			 	   
	        ChromeOptions ops = new ChromeOptions();
	        ops.setAcceptInsecureCerts(true);
	        ops.addArguments("--remote-allow-origins=*"); 
	        driver = new ChromeDriver(ops);    
	        driver.manage().window().maximize();
	        driver.navigate().to("https://www.tarladalal.com/");
	    	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
	    	
	    	
	    	driver.findElement(By.name("ctl00$txtsearch")).sendKeys("PCOS");
	        driver.findElement(By.id("ctl00_imgsearch")).click();
	        driver.findElement(By.xpath("//div[@id='maincontent']/div[1]/div[1]/div[1]/ul[1]/li[1]/a[1] ")).click();
	      
	        //driver.get("https://tarladalal.com/recipes-for-indian-diabetic-recipes-370");
			driver.manage().deleteAllCookies();
			
		}
		
		public static void recipedetails() throws InterruptedException, IOException {
			
//			for ( int p =1 ; p <=23 ; p++)
//	        {
//	        	WebElement Page = driver.findElement(By.xpath("//div[@id='cardholder']//div//a[contains(text(),'"+p+"')]"));
//	        	Page.click();
//	        	System.out.println("onpage"+p);
//	        	
//	        }
			 for ( int p =1 ; p <=6 ; p++)
		        {
				
				 
				 
				 
					String url = "https://www.tarladalal.com/recipes-for-pcos-1040?pageindex="+p;//;
		        	//Thread.sleep(3000);
		        	driver.get(url);
			//driver.get("https://tarladalal.com/RecipeSearch.aspx?term=diabetic&pageindex="+p);
	        List<WebElement> RecipeList = driver.findElements(By.xpath("//article[@class='rcc_recipecard']"));
		    System.out.println("Recipe list count is: "+RecipeList.size());
			
		    
		    for ( int k=1; k<=RecipeList.size(); k++)  {
		    	String s_recipeName ;
		    	String s_recipe_id ; 
		    	String s_Ingredients ;
		    	String formattedrecipeid;
			System.out.println("valueok"+k);
				
			try {
		    	WebElement recipe_id =
			  			  driver.findElement(By.xpath("//article["+k+"]//div[@class='rcc_rcpno']/span")); 
		  
				
		    	
		    	
		    	//recipeNameList.add(recipeName.getText());
		    	
		    											//article[3]//div[@class='rcc_rcpno']/span
		    		s_recipe_id = recipe_id.getText();
			        	//System.out.println(s_recipe_id);
			        	
			        	 formattedrecipeid =  s_recipe_id.substring(8, s_recipe_id.length()-9);
			        	//recipeid.add(formattedrecipeid.trim());	        	
			        	System.out.println(formattedrecipeid); 
				}
				catch(Exception e)
				{
					continue;
				}
			
			try
			{
			WebElement recipeName = driver.findElement
					(By.xpath("//article["+k+"]//div[@class='rcc_rcpcore']/span[1]/a"));
	    	//System.out.println("Name of Recipe : "+recipeName.getText());
			s_recipeName = recipeName.getText();
			recipeName.click();
			}
			catch (Exception e)
			{
				continue;
			}
			
			
		        	 // click recipeName
		        	
		        	//driver.findElement(By.xpath("//div[@class='rcc_recipecard']["+k+"]//span[@class='rcc_recipename']/a")).click();
		        	Thread.sleep(1000);
		        	WebElement Ingrediants = driver.findElement(By.xpath("//div[@id='rcpinglist']"));
		        	s_Ingredients = Ingrediants.getText();
		        	boolean s = checkIgredients(s_Ingredients);
		        	if(s)
		        	{
		        String addedIngrdient = checkAddedIngredients ( s_Ingredients);
		        	if ( addedIngrdient.length()>0)
		        	{
		        		recipeNameList.add(s_recipeName);
		        		recipeid.add(formattedrecipeid.trim());
			        	ingredientList.add(s_Ingredients);
			        	toaddingredientList.add(addedIngrdient);
			        	
			        	
			        	
			        	
			        	System.out.println("Ingrediants are : "+Ingrediants.getText());
			        	
			        	WebElement PrepTime = driver.findElement(By.xpath("//p//time[1]"));
			        	prepTimeList.add(PrepTime.getText());      	
			        	System.out.println("Preperation Time is : "+ PrepTime.getText());
			        	try {
			        	WebElement CookTime = driver.findElement(By.xpath("//p//time[2]"));
			        	cookTimeList.add(CookTime.getText());
			        	System.out.println("Cooking Time is : "+CookTime.getText());
			        	}catch (Exception e ) {cookTimeList.add("NA");};
			        	
			        	WebElement PrepMethod = driver.findElement(By.xpath("//div[@id='recipe_small_steps']"));
			        	prepMethodList.add(PrepMethod.getText());
			        	System.out.println("Preperation Method is : "+PrepMethod.getText());
			        	
			        	Thread.sleep(4000);
			        	try
			        	{
			        	WebElement Nutrients = driver.findElement(By.xpath("//div/span//table[@id='rcpnutrients']"));
			        	NutrientList.add(Nutrients.getText());
			        	System.out.println("Nutrient Values are : "+Nutrients.getText());
			        	} catch (Exception e) {NutrientList.add("NA");};
			        	String strUrl = driver.getCurrentUrl();
			        	//String strUrl = driver.getCurrentUrl();
			        	RecipeUrlList.add(strUrl);
			        	System.out.println("Recipe URL : "+strUrl);
		        	}
		        	}
		        	
//		        	
		        	
		        	//Thread.sleep(7000);
		        	//driver.navigate().back();
		        	String url1 = "https://www.tarladalal.com/recipes-for-pcos-1040?pageindex="+p;//;
		        	
		        	driver.get(url1);
		        	Thread.sleep(5000);
		        	//Thread.sleep(8000);
		        	
			}
		    

			 
			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet sheet = workbook.createSheet("PCOS_ToAdd_Recipes");
			
			sheet.createRow(0);
			sheet.getRow(0).createCell(0).setCellValue("RecipeId");
			sheet.getRow(0).createCell(1).setCellValue("Recipe Name");
			sheet.getRow(0).createCell(2).setCellValue("Recipe Category(Breakfast/lunch/snack/dinner)");
			sheet.getRow(0).createCell(3).setCellValue("Food Category(Veg/non-veg/vegan/Jain)");
			sheet.getRow(0).createCell(4).setCellValue("Ingredients");
			sheet.getRow(0).createCell(5).setCellValue("Preparation Time");
			sheet.getRow(0).createCell(6).setCellValue("Cooking Time");
			sheet.getRow(0).createCell(7).setCellValue("Preparation method");
			sheet.getRow(0).createCell(8).setCellValue("Nutrient values");
			sheet.getRow(0).createCell(9).setCellValue("Targetted morbid conditions (Diabeties/Hypertension/Hypothyroidism)");
			sheet.getRow(0).createCell(10).setCellValue("Recipe URL");
			sheet.getRow(0).createCell(11).setCellValue("toaddingredient");
			
			int rowno=1;
	//
			for(int i = 0; i <recipeNameList.size() ; i++)
			{
				XSSFRow row=sheet.createRow(rowno++);
				row.createCell(0).setCellValue(recipeid.get(i).toString())	;
				row.createCell(1).setCellValue(recipeNameList.get(i).toString());
				row.createCell(4).setCellValue(ingredientList.get(i).toString());
				row.createCell(5).setCellValue(prepTimeList.get(i).toString());
				row.createCell(6).setCellValue(cookTimeList.get(i).toString());
				row.createCell(7).setCellValue(prepMethodList.get(i).toString());
				row.createCell(8).setCellValue(NutrientList.get(i).toString());
				//row.createCell(9).setCellValue(NutrientList.get(i).toString());
				row.createCell(10).setCellValue(RecipeUrlList.get(i).toString());
				row.createCell(11).setCellValue(toaddingredientList.get(i).toString());
			}
			System.out.println("Total Recipe Count"+recipeNameList.size());
			FileOutputStream FOS = new FileOutputStream(".\\datafiles\\PCOSfinal.xlsx");
			//if(FOS.e)
			workbook.write(FOS);
			FOS.close();
		       }
			 
			 
		}
		
		/*
		 * Method to check ingredeint List
		 */
		public  static  boolean checkIgredients(String i_Ingredient)
		{
			
			

			String p_Ingredient = i_Ingredient.toLowerCase();
			System.out.println("igredeintes coming"+p_Ingredient);
			
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
			eliminatedList.add("cream");
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
			eliminatedList.add("ghee");
			//ing compare with arraylist
			boolean isIngrediant=true;
			
			for(String v:eliminatedList)
			
			{
				
				
				
				if (p_Ingredient.contains(v)) //p_Ingredient
				{
					System.out.println("Eliminated::"+p_Ingredient+"because of "+v+"recepid"+"");
					
					isIngrediant=false;
					
					
					break;
				}
			}


			return isIngrediant;
				
				
		
		}
		public  static  String checkAddedIngredients(String i_Ingredient)
		{
			
			

			String p_Ingredient = i_Ingredient.toLowerCase();
			System.out.println("igredeintes coming"+p_Ingredient);
			
			addedElementList.add("broccoli");
			addedElementList.add("peas");
			addedElementList.add("palak");
			addedElementList.add("methi ");
			addedElementList.add("cauliflower");
			addedElementList.add("flaxseed");
			addedElementList.add("apples");
			addedElementList.add("beetroot");
			addedElementList.add("capsicum");
			addedElementList.add("almond");
			addedElementList.add("kokum");
			addedElementList.add("chiaseed");
			addedElementList.add("almonds");
			addedElementList.add("walnuts");
			addedElementList.add("pistachios");
			addedElementList.add("mushroom");
			addedElementList.add("carrot");
			addedElementList.add("tomato");
			addedElementList.add("cucumber");
			addedElementList.add("kale");
			addedElementList.add("black grape");
			
			//ing compare with arraylist
			String isIngrediant="";
			
			for(String toaddingredient:addedElementList)
			
			{
				
				
				
				if (p_Ingredient.contains(toaddingredient)) //p_Ingredient
				{
					System.out.println("addedingredeint::"+p_Ingredient+"because of "+toaddingredient+"recepid"+"");
					
					isIngrediant=toaddingredient;
					
					
					break;
				}
			}


			return isIngrediant;
				
				
		
		}
		
		
		public static void main(String[] args) throws InterruptedException, IOException {
			setup();
			recipedetails();
									
			
		}
		
		
	}


