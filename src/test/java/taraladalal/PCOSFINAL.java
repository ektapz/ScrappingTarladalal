package taraladalal;



	
		import java.io.FileOutputStream;
		import java.io.IOException;
		import java.util.ArrayList;
		import java.util.List;
		import java.time.Duration;
		import org.apache.poi.xssf.usermodel.XSSFRow;
		import org.apache.poi.xssf.usermodel.XSSFSheet;
		import org.apache.poi.xssf.usermodel.XSSFWorkbook;
		import org.openqa.selenium.By;
		
		import org.openqa.selenium.WebDriver;
		import org.openqa.selenium.WebElement;
		import org.openqa.selenium.chrome.ChromeDriver;
		import org.openqa.selenium.chrome.ChromeOptions;

import dev.failsafe.internal.util.Durations;
import io.github.bonigarcia.wdm.WebDriverManager;
		import org.apache.commons.lang3.StringUtils;
		public class PCOSFINAL {

			static WebDriver driver;
			public static ArrayList recipeNameList = new ArrayList();
			public static ArrayList recipeid = new ArrayList();
			public static ArrayList ingredientList = new ArrayList();
			public static ArrayList prepTimeList = new ArrayList();
			public static ArrayList cookTimeList = new ArrayList();
			public static ArrayList prepMethodList = new ArrayList();
			public static ArrayList NutrientList = new ArrayList();
			public static ArrayList RecipeUrlList = new ArrayList();
			public static ArrayList <String>eliminatedList = new ArrayList<String>();
			public static ArrayList <String>addElementList = new ArrayList<String>();
			public static ArrayList toaddingredientList = new ArrayList();
			
			public static void main(String[] args) throws IOException, InterruptedException {
				
				WebDriverManager.chromedriver().setup();			 	   
		        ChromeOptions ops = new ChromeOptions();
		        ops.setAcceptInsecureCerts(true);
		        ops.addArguments("--remote-allow-origins=*"); 
		        driver = new ChromeDriver(ops);    
		        driver.manage().window().maximize();   
		        driver.navigate().to("https://www.tarladalal.com");
		        
		        driver.findElement(By.name("ctl00$txtsearch")).sendKeys("PCOS");
		        driver.findElement(By.id("ctl00_imgsearch")).click();
		        driver.findElement(By.xpath("//div[@id='maincontent']/div[1]/div[1]/div[1]/ul[1]/li[1]/a[1] ")).click();
		        driver.manage().deleteAllCookies();
		        
		        
		        
		        int item_size=driver.findElements(By.xpath("//article[@class='rcc_recipecard']")).size(); 
		    	System.out.println("Item size: "+item_size);
		        Thread.sleep(2000);
		     // for(int i=1;i<=item_size;i++)
		  	  for(int i=1;i<=item_size;i++) 
		  	  { 
		  	  String item_name=driver.findElement(
		  	  By.xpath("(//article[@class='rcc_recipecard'])["+i+"]//span[@class='rcc_recipename']//a")).getText(); 
		  	  //System.out.println(item_name);
		  	  recipeNameList.add(item_name);
		  	  }
		  	  
		       
		       List<WebElement> ReceipeList = driver.findElements(By.xpath("//article[@class='rcc_recipecard']"));
		        
		        //System.out.println("Total Recipe in page 1 :"+totalRecipe);
		       // List of recipeid
		        for (int j = 1; j <= ReceipeList.size(); j++)
		    { 
		        	WebElement r_id =
		  			  driver.findElement(By.xpath("(//div[@class='rcc_rcpno'])[" + j + "]//span"));
		  			   
		        	String s = r_id.getText();
		        	String s1 = r_id.getAttribute("innerHTML");
		    		String formattedrecipeid=StringUtils.substringBetween(s1, ";", "<");
		    		recipeid.add(formattedrecipeid.trim());
		    		//System.out.println(formattedrecipeid);
		    		}
		        Thread.sleep(2000);
		        	//String formattedrecipeid =  s.substring(8, s.length()-9);
		        	//recipeid.add(formattedrecipeid.trim());
		        	
		        	//System.out.println(formattedrecipeid); 
		        	//System.out.println(s); 
		//}
		        //List of recipename
//		        for (int i=1; i<=ReceipeList.size(); i++) {
//		        	    	
//		       	WebElement recipeName = driver.findElement(By.xpath("//div[@class='rcc_recipecard']["+i+"]//span[@class='rcc_recipename']/a"));
//		        	System.out.println("Name of Recipe : "+recipeName.getText());
//		        	recipeNameList.add(recipeName.getText());
//		     
//		        
//		        }
		      ReceipeList.size();
		        for ( int k=1; k<=ReceipeList.size(); k++)  {
		        	driver.findElement(By.xpath("//article[@class='rcc_recipecard']["+k+"]//span[@class='rcc_recipename']/a")).click();
		        	Thread.sleep(1000);
		        	WebElement Ingrediants = driver.findElement(By.xpath("//div[@id='rcpinglist']"));
		       	ingredientList.add(Ingrediants.getText());
		       	//System.out.println("Ingrediants are : "+Ingrediants.getText());
		        	Thread.sleep(2000);
		        	
		        	WebElement PrepTime = driver.findElement(By.xpath("//p//time[1]"));
		        	prepTimeList.add(PrepTime.getText());      	
		        	//System.out.println("Preperation Time is : "+ PrepTime.getText());
		        	Thread.sleep(2000);
		       	
		        	WebElement CookTime = driver.findElement(By.xpath("//p//time[2]"));
		        	cookTimeList.add(CookTime.getText());
		       	//System.out.println("Cooking Time is : "+CookTime.getText());
		       	Thread.sleep(2000);
		        	
		        	WebElement PrepMethod = driver.findElement(By.xpath("//div[@id='recipe_small_steps']"));
		        	prepMethodList.add(PrepMethod.getText());
		        	//System.out.println("Preperation Time is : "+PrepMethod.getText());
		        	
		        	WebElement Nutrients = driver.findElement(By.id("rcpnutrients"));
		        	NutrientList.add(Nutrients.getText());
		        	System.out.println("Nutrient Values are : "+Nutrients.getText());
		        	
		        	
		        	
		        	Thread.sleep(2000);
		        	driver.navigate().back();
		        	Thread.sleep(1000);
		        }
		    		
		    			XSSFWorkbook workbook = new XSSFWorkbook();
		    			XSSFSheet sheet = workbook.createSheet("Recipes Data");
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
		    			
		    			int rowno=1;

		    			for(int i = 0; i < recipeNameList.size(); i++)
		    			{
		    				XSSFRow row=sheet.createRow(rowno++);
		    				row.createCell(0).setCellValue(recipeid.get(i).toString())	;
		    				row.createCell(1).setCellValue(recipeNameList.get(i).toString());
		    				row.createCell(4).setCellValue(ingredientList.get(i).toString());
		    				row.createCell(5).setCellValue(prepTimeList.get(i).toString());
		    				row.createCell(6).setCellValue(cookTimeList.get(i).toString());
		    				row.createCell(7).setCellValue(prepMethodList.get(i).toString());
		    				//row.createCell(8).setCellValue(NutrientList.get(i).toString());
		    				
		    			}
		    			
		    			FileOutputStream FOS = new FileOutputStream(".\\datafiles\\Recipe1.xlsx");
		    			workbook.write(FOS);
		    			FOS.close();
		    			    		     
		       }

			}

