package taraladalal;



	
		import java.io.FileOutputStream;
		import java.io.IOException;
		import java.util.ArrayList;
		import java.util.List;
		import org.apache.poi.xssf.usermodel.XSSFRow;
		import org.apache.poi.xssf.usermodel.XSSFSheet;
		import org.apache.poi.xssf.usermodel.XSSFWorkbook;
		import org.openqa.selenium.By;
		import org.openqa.selenium.JavascriptExecutor;
		import org.openqa.selenium.WebDriver;
		import org.openqa.selenium.WebElement;
		import org.openqa.selenium.chrome.ChromeDriver;
		import org.openqa.selenium.chrome.ChromeOptions;
		import io.github.bonigarcia.wdm.WebDriverManager;
		import org.apache.commons.lang3.StringUtils;
		public class Try {

			static WebDriver driver;
			public static ArrayList recipeNameList = new ArrayList();
			public static ArrayList recipeid = new ArrayList();
			public static ArrayList ingredientList = new ArrayList();
			public static ArrayList prepTimeList = new ArrayList();
			public static ArrayList cookTimeList = new ArrayList();
			public static ArrayList prepMethodList = new ArrayList();
			public static ArrayList NutrientList = new ArrayList();
			public static String strUrl;
			
			public static void main(String[] args) throws IOException, InterruptedException {
				
				WebDriverManager.chromedriver().setup();			 	   
		        ChromeOptions ops = new ChromeOptions();
		        ops.setAcceptInsecureCerts(true);
		        ops.addArguments("--remote-allow-origins=*"); 
		        driver = new ChromeDriver(ops);    
		        driver.manage().window().maximize();   
		        driver.get("https://www.tarladalal.com");
		        
		        driver.findElement(By.name("ctl00$txtsearch")).sendKeys("PCOS");
		        driver.findElement(By.id("ctl00_imgsearch")).click();
		        driver.findElement(By.xpath("//div[@id='maincontent']/div[1]/div[1]/div[1]/ul[1]/li[1]/a[1] ")).click();
		        
		        int pgSize = driver.findElements(By.xpath("//div[@id='pagination']/a")).size();
		    	System.out.println("Pagination size:"+pgSize);
		    	
		    	
		    	  for (int x = 1; x <= pgSize; x++) {
		    		  Thread.sleep(1000);
		    		  
		    			try {
		    			WebElement pagei = driver.findElement(By.xpath("(//div[@id='pagination']/a)[" + x + "]"));
		    			pagei.click();
		    			
		    			pgSize=driver.findElements(By.xpath("//article[@class='rcc_recipecard']")).size(); 
		    			System.out.println("Item size: "+pgSize);
		    			
		    			} catch (Exception e) {e.printStackTrace();};
		    			
		    			for (int j = 1; j <= pgSize; j++) {
		    				
		    				try {
		    			//	for (int j = 1; j <= item_size; j++) {
		    				Thread.sleep(1000);
		    			  String item_name=driver.findElement(
		    			  By.xpath("(//article[@class='rcc_recipecard'])["+j+"]//span[@class='rcc_recipename']//a")).getText(); 
		    			  System.out.println("Receipt Name**********:"+item_name);
		    			  recipeNameList.add(item_name);
		    		
		    				WebElement r_id = driver.findElement(By.xpath("(//div[@class='rcc_rcpno'])[" + j + "]//span"));
		    				String s = r_id.getText();
		    				String s1 = r_id.getAttribute("innerHTML");
		    				String formattedrecipeid=StringUtils.substringBetween(s1, ";", "<");
		    				recipeid.add(formattedrecipeid.trim());
		    				System.out.println(formattedrecipeid);

		    		    	driver.findElement(By.xpath("//article[@class='rcc_recipecard']["+j+"]//span[@class='rcc_recipename']/a")).click();
		    		    	Thread.sleep(1000);
		    		    	WebElement Ingredients = driver.findElement(By.xpath("//div[@id='rcpinglist']"));
		    		    	ingredientList.add(Ingredients.getText());
		    		    	System.out.println("******************** Ingredients are : \n"+Ingredients.getText());
		    		    	
		    		    	WebElement PrepTime = driver.findElement(By.xpath("//p//time[1]"));
		    		    	prepTimeList.add(PrepTime.getText());      	
		    		    	System.out.println("Preparation Time is : "+ PrepTime.getText());
		    		    	
		    		    	try {
		    		        	WebElement CookTime = driver.findElement(By.xpath("//p//time[2]"));
		    		        	cookTimeList.add(CookTime.getText());
		    		        	System.out.println("Cooking Time is : "+CookTime.getText());
		    		        	}catch (Exception e ) {cookTimeList.add("NA");};
		    		    	
		    		    	WebElement PrepMethod = driver.findElement(By.xpath("//div[@id='recipe_small_steps']"));
		    		    	prepMethodList.add(PrepMethod.getText());
		    		    	System.out.println("Preparation Method is : "+PrepMethod.getText());
		    		    	Thread.sleep(4000);
		    	        	try
		    	        	{
		    	        	WebElement Nutrients = driver.findElement(By.xpath("//div[@id='rcpnuts']"));
		    	        	NutrientList.add(Nutrients.getText());
		    	        	System.out.println("Nutrient Values are : "+Nutrients.getText());
		    	        	} catch (Exception e) {
		    	        		System.out.println("Nutrient Values are : ");
		    	        		NutrientList.add("NA");};
		    		
		    			} catch (Exception e) {
		    				System.out.println("outer exception : ");
		    				e.printStackTrace();};
		    			
		    		    	String url = "https://www.tarladalal.com/recipes-for-pcos-1040?pageindex="+x;
		    				//"https://tarladalal.com/RecipeSearch.aspx?term=hypothyroidism&pageindex="+j;
		    				//https://www.tarladalal.com/recipes-for-hypothyroidism-veg-diet-indian-recipes-849?pageindex=2
		    				Thread.sleep(3000);
		    				driver.get(url);
		    		    
		    			}
		    			
		    	  }
		    	  	System.out.println("XSS write : recipeNameList.size"+recipeNameList.size());
		    			XSSFWorkbook workbook = new XSSFWorkbook();
		    			XSSFSheet sheet = workbook.createSheet("Recipes Data");
		    			sheet.createRow(0);
		    			sheet.getRow(0).createCell(0).setCellValue("RecipeId");
		    			sheet.getRow(0).createCell(1).setCellValue("Recipe Name");
		    			//sheet.getRow(0).createCell(2).setCellValue("Recipe Category(Breakfast/lunch/snack/dinner)");
		    			//sheet.getRow(0).createCell(3).setCellValue("Food Category(Veg/non-veg/vegan/Jain)");
		    			sheet.getRow(0).createCell(4).setCellValue("Ingredients");
		    			sheet.getRow(0).createCell(5).setCellValue("Preparation Time");
		    			sheet.getRow(0).createCell(6).setCellValue("Cooking Time");
		    			sheet.getRow(0).createCell(7).setCellValue("Preparation method");
		    			sheet.getRow(0).createCell(8).setCellValue("Nutrient values");
		    			//sheet.getRow(0).createCell(9).setCellValue("Targetted morbid conditions (Diabeties/Hypertension/Hypothyroidism)");
		    			//sheet.getRow(0).createCell(10).setCellValue("Recipe URL");
		    			
		        int rowno=1;

		    			//for(int i = 0; i < recipeNameList.size(); i++)
		        
		        for (int i=0; i < recipeNameList.size(); i++) {
		    			{
		    				XSSFRow row=sheet.createRow(rowno++);
		    				System.out.println("rowno : "+rowno);
		    				row.createCell(0).setCellValue(recipeid.get(i).toString())	;
		    				row.createCell(1).setCellValue(recipeNameList.get(i).toString());
		    				row.createCell(4).setCellValue(ingredientList.get(i).toString());
		    				row.createCell(5).setCellValue(prepTimeList.get(i).toString());
		    				row.createCell(6).setCellValue(cookTimeList.get(i).toString());
		    				row.createCell(7).setCellValue(prepMethodList.get(i).toString());
		    				row.createCell(8).setCellValue(NutrientList.get(i).toString()); 
		    				
		    				System.out.println("XSS write : "+i);
		    			 }
		    	
		    			FileOutputStream FOS = new FileOutputStream(".\\datafiles\\HypoThyroidismRecipe.xlsx");
		    			workbook.write(FOS);
		    			FOS.close();
		         
		        }

			}
			
		}
