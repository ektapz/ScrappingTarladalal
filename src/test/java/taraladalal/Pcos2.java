package taraladalal;


	import java.io.FileOutputStream;
	import java.io.IOException;
	import java.time.Duration;
	import java.util.ArrayList;
	import java.util.Arrays;
	import java.util.Iterator;

	import org.apache.commons.lang3.StringUtils;
	import org.apache.poi.ss.usermodel.Workbook;
	import org.apache.poi.xssf.usermodel.XSSFRow;
	import org.apache.poi.xssf.usermodel.XSSFSheet;
	import org.apache.poi.xssf.usermodel.XSSFWorkbook;
	import org.openqa.selenium.By;
	import org.openqa.selenium.WebDriver;
	import org.openqa.selenium.WebElement;
	import org.openqa.selenium.chrome.ChromeDriver;
	import org.openqa.selenium.support.ui.ExpectedConditions;
	import org.openqa.selenium.support.ui.WebDriverWait;

	import io.github.bonigarcia.wdm.WebDriverManager;

	public class Pcos2 {

	  static WebDriver driver;
	  public static ArrayList recipeNameList = new ArrayList();
	  public static ArrayList recipeid = new ArrayList();
	  public static ArrayList ingredientList = new ArrayList();
	  public static ArrayList prepTimeList = new ArrayList();
	  public static ArrayList cookTimeList = new ArrayList();
	  public static ArrayList prepMethodList = new ArrayList();
	  public static ArrayList NutrientList = new ArrayList();
	  public static ArrayList RecipeUrlList = new ArrayList();
	  public static ArrayList PaginationList = new ArrayList();
	  static ArrayList < String > eliminatedList= new ArrayList<String>();

	   static ArrayList < String > IngredientsEliminationList= new ArrayList<String>();
	  public static ArrayList < String > addedElementList= new ArrayList();
	  public static ArrayList < String > AllergyList= new ArrayList();
	  
	  
	  public static ArrayList IngredientsAdditionList= new ArrayList();
	  public static ArrayList AllergyIngredientsList= new ArrayList();
	  
	  static int item_size = 0;
	  
	  public static void setup() {
		  
		  WebDriverManager.chromedriver().setup();
		    driver = new ChromeDriver();
		    driver.navigate().to("https://www.tarladalal.com/");
		    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		    driver.manage().window().maximize();
		 
		    WebElement searchTxt = driver.findElement(By.id("ctl00_txtsearch"));
		    searchTxt.sendKeys("PCOS");

		    WebElement searchBtn = driver.findElement(By.id("ctl00_imgsearch"));
		    searchBtn.click();

		    driver.findElement(By.xpath("//div[@id='maincontent']/div[1]/div[1]/div[1]/ul[1]/li[1]/a[1] ")).click();
		    driver.manage().deleteAllCookies();
		    System.out.println("Inside setup end");
		    
	  }
	  
	  public static void eliminatedList() throws InterruptedException, IOException {
		  int pgSize = driver.findElements(By.xpath("//div[@id='pagination']/a")).size();
		    System.out.println("Pagination size:" + pgSize);
		    
		    Thread.sleep(1000);
		    recipeNameList.clear();
		    recipeid.clear();
		    ingredientList.clear();
		    prepTimeList.clear();
		    cookTimeList.clear();
		    prepMethodList.clear();
		    NutrientList.clear();
		    RecipeUrlList.clear();
		  //  IngredientsAdditionList.clear();
		  //  AllergyIngredientsList.clear();


		    for (int x = 1; x <= pgSize; x++) {
		     
		      try {
		        WebElement pagei = driver.findElement(By.xpath("(//div[@id='pagination']/a)[" + x + "]"));
		        pagei.click();

		        item_size = driver.findElements(By.xpath("//article[@class='rcc_recipecard']")).size();
		        System.out.println("Item size: " + item_size);

		      } catch (Exception e) {
		        e.printStackTrace();
		      };

		      for (int j = 1; j <= item_size; j++) {
		        Thread.sleep(1000);
		        String item_name = driver.findElement(
		        By.xpath("(//article[@class='rcc_recipecard'])[" + j + "]//span[@class='rcc_recipename']//a")).getText();
		        System.out.println("Receipt Name**********:" + item_name);

		        WebElement r_id = driver.findElement(By.xpath("(//div[@class='rcc_rcpno'])[" + j + "]//span"));
		        String s = r_id.getText();
		        String s1 = r_id.getAttribute("innerHTML");
		        String formattedrecipeid = StringUtils.substringBetween(s1, ";", "<");

		        driver.findElement(By.xpath("//article[@class='rcc_recipecard'][" + j + "]//span[@class='rcc_recipename']/a")).click();
		        WebElement Ingredients = driver.findElement(By.xpath("//div[@id='rcpinglist']"));
		        String ingredientsList = Ingredients.getText().toLowerCase().trim();
		        boolean isMatchedInElimination = checkIgredients(ingredientsList);
		        System.out.println("isMatchedInElimination :" + isMatchedInElimination);
		        if (!isMatchedInElimination) {
		            recipeid.add(formattedrecipeid.trim());
		            recipeNameList.add(item_name);
		            ingredientList.add(ingredientsList);

		            WebElement PrepTime = driver.findElement(By.xpath("//p//time[1]"));
		            prepTimeList.add(PrepTime.getText());
		            try {
		              WebElement CookTime = driver.findElement(By.xpath("//p//time[2]"));
		              cookTimeList.add(CookTime.getText());
		            } catch (Exception e) {
		              cookTimeList.add("NA");
		            };

		            WebElement PrepMethod = driver.findElement(By.xpath("//div[@id='recipe_small_steps']"));
		            prepMethodList.add(PrepMethod.getText());
		            Thread.sleep(4000);
		            try {
		              WebElement Nutrients = driver.findElement(By.id("rcpnutrients"));
		              NutrientList.add(Nutrients.getText());
		            } catch (Exception e) {
		              System.out.println("Nutrient Values are : ");
		              NutrientList.add("NA");
		            };

		            String strUrl = driver.getCurrentUrl();
		            RecipeUrlList.add(strUrl);
		            System.out.println("Recipe URL : " + strUrl);

		          }
		        String url =  "https://www.tarhttps://www.tarladalal.com/recipes-for-pcos-1040?pageindex="+x;
		        Thread.sleep(3000);
		        driver.get(url);

		      }
		    }

		    System.out.println("XSS write : recipeNameList.size" + recipeNameList.size());
		    XSSFWorkbook workbook = new XSSFWorkbook();
		    XSSFSheet sheet = workbook.createSheet("Recipes Data");
		    sheet.createRow(0);
		    
		    sheet.getRow(0).createCell(0).setCellValue("Serial No");
		    sheet.getRow(0).createCell(1).setCellValue("RecipeId");
		    sheet.getRow(0).createCell(2).setCellValue("Recipe Name");
		    //sheet.getRow(0).createCell(3).setCellValue("Recipe Category(Breakfast/lunch/snack/dinner)");
		    //sheet.getRow(0).createCell(4).setCellValue("Food Category(Veg/non-veg/vegan/Jain)");
		    sheet.getRow(0).createCell(5).setCellValue("Ingredients");
		    sheet.getRow(0).createCell(6).setCellValue("Preparation Time");
		    sheet.getRow(0).createCell(7).setCellValue("Cooking Time");
		    sheet.getRow(0).createCell(8).setCellValue("Preparation method");
		    sheet.getRow(0).createCell(9).setCellValue("Nutrient values");
		    //sheet.getRow(0).createCell(10).setCellValue("Targetted morbid conditions (Diabeties/Hypertension/Hypothyroidism)");
		    sheet.getRow(0).createCell(11).setCellValue("Recipe URL");
		   // sheet.getRow(0).createCell(12).setCellValue("To Add Ingredient");
		   // sheet.getRow(0).createCell(13).setCellValue("Allergy Ingredient");
		    
		    int rowno = 1;

		    for (int i = 0; i < recipeNameList.size(); i++) {
		      {
		        XSSFRow row = sheet.createRow(rowno++);
		        System.out.println("rowno : " + rowno);
		        row.createCell(0).setCellValue(rowno-1);
		        row.createCell(1).setCellValue(recipeid.get(i).toString());
		        row.createCell(2).setCellValue(recipeNameList.get(i).toString());
		        row.createCell(5).setCellValue(ingredientList.get(i).toString());
		        row.createCell(6).setCellValue(prepTimeList.get(i).toString());
		        row.createCell(7).setCellValue(cookTimeList.get(i).toString());
		        row.createCell(8).setCellValue(prepMethodList.get(i).toString());
		        row.createCell(9).setCellValue(NutrientList.get(i).toString());
		        row.createCell(11).setCellValue(RecipeUrlList.get(i).toString());
		       
		     //   row.createCell(12).setCellValue(IngredientsAdditionList.get(i).toString());
		     //   row.createCell(13).setCellValue(AllergyIngredientsList.get(i).toString());
		        
		        System.out.println("XSS write : " + i);
		      }

		      FileOutputStream FOS = new FileOutputStream(".\\datafiles\\HypoThyroidismElimination.xlsx");
		      System.out.println("ingredientList  :==>" + ingredientList.toString());

		      workbook.write(FOS);

		      FOS.close();
		    }
	 }

	  public static void addonFilter() throws InterruptedException, IOException {
		  System.out.println("Inside addonFilter");
		  int pgSize = driver.findElements(By.xpath("//div[@id='pagination']/a")).size();
		  System.out.println("Inside addonFilter after pgSize");
		    System.out.println("Pagination size:" + pgSize);
		    
		    Thread.sleep(1000);
		    recipeNameList.clear();
		    recipeid.clear();
		    ingredientList.clear();
		    prepTimeList.clear();
		    cookTimeList.clear();
		    prepMethodList.clear();
		    NutrientList.clear();
		    RecipeUrlList.clear();
		    IngredientsAdditionList.clear();
		  //  AllergyIngredientsList.clear();


		    for (int x = 1; x <= pgSize; x++) {
		     
		      try {
		        WebElement pagei = driver.findElement(By.xpath("(//div[@id='pagination']/a)[" + x + "]"));
		        pagei.click();

		        item_size = driver.findElements(By.xpath("//article[@class='rcc_recipecard']")).size();
		        System.out.println("Item size: " + item_size);

		      } catch (Exception e) {
		        e.printStackTrace();
		      };

		      for (int j = 1; j <= item_size; j++) {
		        Thread.sleep(3000);
		        String item_name = driver.findElement(
		        By.xpath("//article["+j+"]//div[@class='rcc_rcpcore']/span[1]/a")).getText();
		        System.out.println("Receipt Name**********:" + item_name);

		        WebElement r_id = driver.findElement(By.xpath("(//div[@class='rcc_rcpno'])[" + j + "]//span"));
		        String s = r_id.getText();
		        String s1 = r_id.getAttribute("innerHTML");
		        String formattedrecipeid = StringUtils.substringBetween(s1, ";", "<");

		        driver.findElement(By.xpath("//article[@class='rcc_recipecard'][" + j + "]//span[@class='rcc_recipename']/a")).click();
		        WebElement Ingredients = driver.findElement(By.xpath("//div[@id='rcpinglist']"));
		        String ingredientsList = Ingredients.getText().toLowerCase().trim();
		        boolean isMatchedInElimination = checkIgredients(ingredientsList);
		      // System.out.println("ingredientsList  >>>>>>>>>>"+ ingredientsList.toString());
		        if (!isMatchedInElimination) {
		        	//System.out.println("isMatchedInElimination - true:");
		        	  String addedIngredient = checkAddedIngredients(ingredientsList);
		        	
		            if (addedIngredient.length() > 0) {
		            	System.out.println("addedIngredient > 0:"+addedIngredient );
		        	
		            recipeid.add(formattedrecipeid.trim());
		            recipeNameList.add(item_name);
		            ingredientList.add(ingredientsList);
		            IngredientsAdditionList.add(addedIngredient);
		            
		            WebElement PrepTime = driver.findElement(By.xpath("//p//time[1]"));
		            prepTimeList.add(PrepTime.getText());
		            try {
		              WebElement CookTime = driver.findElement(By.xpath("//p//time[2]"));
		              cookTimeList.add(CookTime.getText());
		            } catch (Exception e) {
		              cookTimeList.add("NA");
		            };

		            WebElement PrepMethod = driver.findElement(By.xpath("//div[@id='recipe_small_steps']"));
		            prepMethodList.add(PrepMethod.getText());
		            Thread.sleep(4000);
		            try {
		              WebElement Nutrients = driver.findElement(By.id("rcpnutrients"));
		              NutrientList.add(Nutrients.getText());
		            } catch (Exception e) {
		              System.out.println("Nutrient Values are : ");
		              NutrientList.add("NA");
		            };

		            String strUrl = driver.getCurrentUrl();
		            RecipeUrlList.add(strUrl);
		            System.out.println("Recipe URL : " + strUrl);
		            }
		          }
		        String url = "https://www.tarladalal.com/recipes-for-pcos-1040?pageindex="+x;
		        Thread.sleep(3000);
		        driver.get(url);

		      }
		    
		    }
		    System.out.println("XSS write : recipeNameList.size" + recipeNameList.size());
		    XSSFWorkbook workbook = new XSSFWorkbook();
		    XSSFSheet sheet = workbook.createSheet("Recipes Data");
		    sheet.createRow(0);
		    
		    sheet.getRow(0).createCell(0).setCellValue("Serial No");
		    sheet.getRow(0).createCell(1).setCellValue("RecipeId");
		    sheet.getRow(0).createCell(2).setCellValue("Recipe Name");
		    //sheet.getRow(0).createCell(3).setCellValue("Recipe Category(Breakfast/lunch/snack/dinner)");
		    //sheet.getRow(0).createCell(4).setCellValue("Food Category(Veg/non-veg/vegan/Jain)");
		    sheet.getRow(0).createCell(5).setCellValue("Ingredients");
		    sheet.getRow(0).createCell(6).setCellValue("Preparation Time");
		    sheet.getRow(0).createCell(7).setCellValue("Cooking Time");
		    sheet.getRow(0).createCell(8).setCellValue("Preparation method");
		    sheet.getRow(0).createCell(9).setCellValue("Nutrient values");
		    //sheet.getRow(0).createCell(10).setCellValue("Targetted morbid conditions (Diabeties/Hypertension/Hypothyroidism)");
		    sheet.getRow(0).createCell(11).setCellValue("Recipe URL");
		    sheet.getRow(0).createCell(12).setCellValue("To Add Ingredient");
		   // sheet.getRow(0).createCell(13).setCellValue("Allergy Ingredient");
		    
		    int rowno = 1;

		    for (int i = 0; i < recipeNameList.size(); i++) {
		      {
		        XSSFRow row = sheet.createRow(rowno++);
		        System.out.println("rowno : " + rowno);
		        row.createCell(0).setCellValue(rowno-1);
		        row.createCell(1).setCellValue(recipeid.get(i).toString());
		        row.createCell(2).setCellValue(recipeNameList.get(i).toString());
		        row.createCell(5).setCellValue(ingredientList.get(i).toString());
		        row.createCell(6).setCellValue(prepTimeList.get(i).toString());
		        row.createCell(7).setCellValue(cookTimeList.get(i).toString());
		        row.createCell(8).setCellValue(prepMethodList.get(i).toString());
		        row.createCell(9).setCellValue(NutrientList.get(i).toString());
		        row.createCell(11).setCellValue(RecipeUrlList.get(i).toString());
		       
		        row.createCell(12).setCellValue(IngredientsAdditionList.get(i).toString());
		     //   row.createCell(13).setCellValue(AllergyIngredientsList.get(i).toString());
		        
		        System.out.println("XSS write : " + i);
		      }

		      FileOutputStream FOS = new FileOutputStream(".\\datafiles\\pcos.xlsx");
		      System.out.println("ingredientList  :==>" + ingredientList.toString());

		      workbook.write(FOS);

		      FOS.close();
		    }
	 }
	  
	  public static void allergyFilter() throws InterruptedException, IOException {
		  System.out.println("Inside allergyFilter");
		  int pgSize = driver.findElements(By.xpath("//div[@id='pagination']/a")).size();
		  System.out.println("Inside allergyFilter after pgSize");
		    System.out.println("Pagination size:" + pgSize);
		    
		    Thread.sleep(1000);
		    recipeNameList.clear();
		    recipeid.clear();
		    ingredientList.clear();
		    prepTimeList.clear();
		    cookTimeList.clear();
		    prepMethodList.clear();
		    NutrientList.clear();
		    RecipeUrlList.clear();
		    IngredientsAdditionList.clear();
		    AllergyIngredientsList.clear();


		    for (int x = 1; x <= pgSize; x++) {
		     
		      try {
		        WebElement pagei = driver.findElement(By.xpath("(//div[@id='pagination']/a)[" + x + "]"));
		        pagei.click();

		        item_size = driver.findElements(By.xpath("//article[@class='rcc_recipecard']")).size();
		        System.out.println("Item size: " + item_size);

		      } catch (Exception e) {
		        e.printStackTrace();
		      };

		      for (int j = 1; j <= item_size; j++) {
		        Thread.sleep(1000);
		        String item_name = driver.findElement(
		        By.xpath("(//article[@class='rcc_recipecard'])[" + j + "]//span[@class='rcc_recipename']//a")).getText();
		        System.out.println("Receipt Name**********:" + item_name);

		        WebElement r_id = driver.findElement(By.xpath("(//div[@class='rcc_rcpno'])[" + j + "]//span"));
		        String s = r_id.getText();
		        String s1 = r_id.getAttribute("innerHTML");
		        String formattedrecipeid = StringUtils.substringBetween(s1, ";", "<");

		        driver.findElement(By.xpath("//article[@class='rcc_recipecard'][" + j + "]//span[@class='rcc_recipename']/a")).click();
		        WebElement Ingredients = driver.findElement(By.xpath("//div[@id='rcpinglist']"));
		        String ingredientsList = Ingredients.getText().toLowerCase().trim();
		        boolean isMatchedInElimination = checkIgredients(ingredientsList);
		      
		        if (!isMatchedInElimination) {
		        	//System.out.println("isMatchedInElimination - true:");
		        	  String allergyIngredient = checkAllergyIngredients(ingredientsList);
		        	  
		            if (allergyIngredient.length() > 0) {
		            	System.out.println("allergyIngredient > 0:"+allergyIngredient );
		        	
		            recipeid.add(formattedrecipeid.trim());
		            recipeNameList.add(item_name);
		            ingredientList.add(ingredientsList);
		            
		            AllergyIngredientsList.add(allergyIngredient);
		            
		            
		            WebElement PrepTime = driver.findElement(By.xpath("//p//time[1]"));
		            prepTimeList.add(PrepTime.getText());
		            try {
		              WebElement CookTime = driver.findElement(By.xpath("//p//time[2]"));
		              cookTimeList.add(CookTime.getText());
		            } catch (Exception e) {
		              cookTimeList.add("NA");
		            };

		            WebElement PrepMethod = driver.findElement(By.xpath("//div[@id='recipe_small_steps']"));
		            prepMethodList.add(PrepMethod.getText());
		            Thread.sleep(4000);
		            try {
		              WebElement Nutrients = driver.findElement(By.xpath("//div[@id='rcpnuts']"));
		              NutrientList.add(Nutrients.getText());
		            } catch (Exception e) {
		              System.out.println("Nutrient Values are : ");
		              NutrientList.add("NA");
		            };

		            String strUrl = driver.getCurrentUrl();
		            RecipeUrlList.add(strUrl);
		            System.out.println("Recipe URL : " + strUrl);
		            }
		          }
		        String url = "https://www.tarladalal.com/recipes-for-pcos-1040?pageindex="+x;
		        Thread.sleep(3000);
		        driver.get(url);

		      }
		    
		    }
		    System.out.println("XSS write : recipeNameList.size" + recipeNameList.size());
		    XSSFWorkbook workbook = new XSSFWorkbook();
		    XSSFSheet sheet = workbook.createSheet("Recipes Data");
		    sheet.createRow(0);
		    
		    sheet.getRow(0).createCell(0).setCellValue("Serial No");
		    sheet.getRow(0).createCell(1).setCellValue("RecipeId");
		    sheet.getRow(0).createCell(2).setCellValue("Recipe Name");
		    //sheet.getRow(0).createCell(3).setCellValue("Recipe Category(Breakfast/lunch/snack/dinner)");
		    //sheet.getRow(0).createCell(4).setCellValue("Food Category(Veg/non-veg/vegan/Jain)");
		    sheet.getRow(0).createCell(5).setCellValue("Ingredients");
		    sheet.getRow(0).createCell(6).setCellValue("Preparation Time");
		    sheet.getRow(0).createCell(7).setCellValue("Cooking Time");
		    sheet.getRow(0).createCell(8).setCellValue("Preparation method");
		    sheet.getRow(0).createCell(9).setCellValue("Nutrient values");
		    //sheet.getRow(0).createCell(10).setCellValue("Targetted morbid conditions (Diabeties/Hypertension/Hypothyroidism)");
		    sheet.getRow(0).createCell(11).setCellValue("Recipe URL");
		   // sheet.getRow(0).createCell(12).setCellValue("To Add Ingredient");
		    sheet.getRow(0).createCell(13).setCellValue("Allergy Ingredient");
		    
		    int rowno = 1;

		    for (int i = 0; i < recipeNameList.size(); i++) {
		      {
		        XSSFRow row = sheet.createRow(rowno++);
		        System.out.println("rowno : " + rowno);
		        row.createCell(0).setCellValue(rowno-1);
		        row.createCell(1).setCellValue(recipeid.get(i).toString());
		        row.createCell(2).setCellValue(recipeNameList.get(i).toString());
		        row.createCell(5).setCellValue(ingredientList.get(i).toString());
		        row.createCell(6).setCellValue(prepTimeList.get(i).toString());
		        row.createCell(7).setCellValue(cookTimeList.get(i).toString());
		        row.createCell(8).setCellValue(prepMethodList.get(i).toString());
		        row.createCell(9).setCellValue(NutrientList.get(i).toString());
		        row.createCell(11).setCellValue(RecipeUrlList.get(i).toString());
		       
		       // row.createCell(12).setCellValue(IngredientsAdditionList.get(i).toString());
		        row.createCell(13).setCellValue(AllergyIngredientsList.get(i).toString());
		        System.out.println("XSS write : " + i);
		      }

		      FileOutputStream FOS = new FileOutputStream(".\\datafiles\\pcos.xlsx");
		      
		      workbook.write(FOS);

		      FOS.close();
		    }
		  
	  }
	  
	  public static void main(String[] args) throws InterruptedException, IOException {
		  System.out.println("Inside main");
			setup();
			eliminatedList();
		//	addonFilter();
		//	allergyFilter();
		}
	  
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
	  
	  public static String checkAllergyIngredients(String ingredientsList) {
		    String isAllergyIngredient = "";

		    try {

		    	AllergyList = new ArrayList < String > (Arrays.asList("milk","soy","egg","sesame","peanuts","walnut","almond","hazelnut",
		    		  "pecan","cashew","pistachio","shell fish","seafood"));
		      for (String allergyingredient: AllergyList)

		      {
		        if (ingredientsList.contains(allergyingredient)) //p_Ingredient
		        {
		          System.out.println("allergyingredient::" + ingredientsList + "because of " + allergyingredient + "recepid" + "");

		          isAllergyIngredient = allergyingredient;

		          break;
		        }
		      }
		    } catch (Exception e) {
		      // TODO Auto-generated catch block
		      e.printStackTrace();
		    }

		    return isAllergyIngredient;

		  }
	  
	}

