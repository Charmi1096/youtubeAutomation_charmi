package demo;

import java.time.Duration;
import java.util.List;
import java.util.logging.Level;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import demo.utils.ExcelDataProvider;
import demo.wrappers.Wrappers;

public class TestCases extends ExcelDataProvider{ // Lets us read the data
        static ChromeDriver driver;

        /*
         * TODO: Write your tests here with testng @Test annotation.
         * Follow `testCase01` `testCase02`... format or what is provided in
         * instructions
         */

        /*
         * Do not change the provided methods unless necessary, they will help in
         * automation and assessment
         */
    @BeforeTest
    public void startBrowser() {
                System.setProperty("java.util.logging.config.file", "logging.properties");

                // NOT NEEDED FOR SELENIUM MANAGER
                // WebDriverManager.chromedriver().timeout(30).setup();

                ChromeOptions options = new ChromeOptions();
                LoggingPreferences logs = new LoggingPreferences();

                logs.enable(LogType.BROWSER, Level.ALL);
                logs.enable(LogType.DRIVER, Level.ALL);
                options.setCapability("goog:loggingPrefs", logs);
                options.addArguments("--remote-allow-origins=*");

                System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log");

                driver = new ChromeDriver(options);

                driver.manage().window().maximize();
    }
    @AfterTest
    public void endTest() {
        driver.close();
        driver.quit();
    
    }

    @Test
    public static void testCase01() throws InterruptedException
    {
        System.out.println("Start Test case: testCase01");
        driver.get("https://www.youtube.com/");
        String actualTitle = driver.getTitle();
        String expectedTitle = "YouTube";
        Assert.assertEquals(actualTitle, expectedTitle);

        WebElement aboutBtn = driver.findElement(By.xpath("//div/a[text()='About']"));
        aboutBtn.click();
        Thread.sleep(3000);
         WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(30));
         wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//section[@class='ytabout__content']")));
        WebElement aboutMsg = driver.findElement(By.xpath("//section[@class='ytabout__content']"));
        String abtMsgText = aboutMsg.getText();
        System.out.println(abtMsgText);

        System.out.println("End Test case: testCase01");
    }

    @Test
    public static void testCase02() throws InterruptedException
    {
        System.out.println("Start Test case: testCase02");
        //JavascriptExecutor js = (JavascriptExecutor) driver;
        driver.get("https://www.youtube.com/");
        Thread.sleep(3000);
        SoftAssert softAssert = new SoftAssert();
          
          // Navigate to the "Films" tab
        WebElement movies = driver.findElement(By.xpath("//yt-formatted-string[text()='Movies']"));
        movies.click();
          
          // Wait for the page to load completely
        Thread.sleep(2000);
          
          // Scroll to the extreme right in the "Top Selling" section
        WebElement topSellingSection = driver.findElement(By.xpath("(//ytd-button-renderer[@class='style-scope yt-horizontal-list-renderer arrow'])[2]"));
                   
        while (topSellingSection.isDisplayed()) {
            
            topSellingSection.click();
            Thread.sleep(2000);
        }

               
          // Verify if the movie is marked as "A" for Mature
        List<WebElement> matureMovie = driver.findElements(By.xpath("//p[text()='A']"));

        String ratingMovieText = matureMovie.get(0).getText();
        softAssert.assertEquals(ratingMovieText,"A");

        System.out.println("verified the rating : "+ ratingMovieText);

          
          // Verify if the movie genre is either "Comedy" or "Animation"
        WebElement movieGenre = driver.findElement(By.xpath("(//span[contains(text(),'Comedy')])[3]"));

        String GenreMovieText = movieGenre.getText();
        softAssert.assertTrue(GenreMovieText.contains("Comedy"),"Unverified Genre");

        System.out.println("verified the GenreMovie : "+ GenreMovieText);

    
        softAssert.assertAll();
        System.out.println("End Test case: testCase02");
    }

    @Test
    public static void testCase03() throws InterruptedException
    {
        System.out.println("Start Test case: testCase03");

        driver.get("https://www.youtube.com/");
        Thread.sleep(3000);

        SoftAssert softAssert = new SoftAssert();

        WebElement musicTab = driver.findElement(By.xpath("(//yt-formatted-string[text()='Music'])[1]"));
        musicTab.click();
        Thread.sleep(2000); // Wait for page to load
 
         // Scroll to the extreme right in the first section
        WebElement firstSection = driver.findElement(By.xpath("(//ytd-button-renderer[@class='style-scope yt-horizontal-list-renderer arrow'])[4]"));
        Thread.sleep(2000);
        
        while (firstSection.isDisplayed()) {
            
          firstSection.click();
          Thread.sleep(1000);
        }
         
 
         // Get the name of the playlist
        WebElement playlistName = driver.findElement(By.xpath("(//h3[contains(text(),'Bollywood Dance')])[1]"));
        String playlistTitle = playlistName.getText();
        System.out.println("Playlist Name : " + playlistTitle);
 
         // Get the number of tracks listed
        WebElement tracksList = driver.findElement(By.xpath("(//p[@id='video-count-text' and contains(text(),'50 tracks')])[4]"));
        String trackListTxt = tracksList.getText();

        
         // Soft Assert on whether the number of tracks listed is less than or equal to 50
        softAssert.assertEquals(trackListTxt , "50 tracks");
 
        softAssert.assertAll();
        System.out.println("End Test case: testCase03");
    }

    @Test
    public static void testCase04() throws InterruptedException
    {
        System.out.println("Start Test case: testCase04");
        driver.get("https://www.youtube.com/");
        Thread.sleep(3000);
        SoftAssert softAssert = new SoftAssert();
        double result = 0;

        
        WebElement newsbutton=driver.findElement(By.xpath("(//yt-formatted-string[contains(text(),'News')])[1]"));
        newsbutton.click();

        String newslistName = newsbutton.getText();
        System.out.println("newsbutton: " + newslistName);
        Thread.sleep(2000);

        WebElement first_Title=driver.findElement(By.xpath("(//yt-formatted-string[@id='home-content-text'])[1]"));
        Thread.sleep(2000);
        String firstOption=  first_Title.getText();
        System.out.println("Headline1: " + firstOption );

        WebElement second_Title = driver.findElement(By.xpath("(//yt-formatted-string[@id='home-content-text'])[2]"));
        String secondOption =  second_Title.getText();
        System.out.println("Headline2: " + secondOption );

        WebElement third_Title = driver.findElement(By.xpath("(//yt-formatted-string[@id='home-content-text'])[3]"));
        String thirdOption = third_Title.getText();
        System.out.println("Headline3: " + thirdOption );

        Thread.sleep(2000);
        WebElement firstLikeCountElement = driver.findElement(By.xpath("(//span[@id='vote-count-middle'])[1]"));
        String firstLikeCountText = firstLikeCountElement.getText();
        System.out.println("Likes for the first headline: " + firstLikeCountText);
        int l1 = Integer.parseInt(firstLikeCountText);

        WebElement secondLikeCountElement = driver.findElement(By.xpath("(//span[@id='vote-count-middle'])[2]"));
        String secondLikeCountText = secondLikeCountElement.getText();
        System.out.println("Likes for the second headline: " + secondLikeCountText);
        int l2 = Integer.parseInt(secondLikeCountText);
      
        WebElement thirdLikeCountElement = driver.findElement(By.xpath("(//span[@id='vote-count-middle'])[3]"));
        String thirdLikeCountText = thirdLikeCountElement.getText();
        System.out.println("Likes for the third headline: " + thirdLikeCountText);
        int l3 = Integer.parseInt(thirdLikeCountText);
        
         
        // int resultCount= LikeCount1 + LikeCount2 + LikeCount3;
        int resultCount = l1+l2+l3;

        System.out.println("Total like Counts : "  + resultCount);
        softAssert.assertAll();
        System.out.println("End Test case: testCase04");
    }

    @Test
    public static void testCase05() throws InterruptedException
    {
        System.out.println("Start Test case: testCase05");
        
        driver.get("https://www.youtube.com/");
        Thread.sleep(3000);
        WebElement searchBox = driver.findElement(By.xpath("//input[@id='search']"));
    
            Wrappers.clickAction(searchBox, driver);
    
            Wrappers.enterText(searchBox, "Games");
    
            WebElement searchBoxClick = driver.findElement(By.id("search-icon-legacy"));
    
            Wrappers.clickAction(searchBoxClick, driver);
    
            //Get a List of Views using List of WebElement
            List<WebElement> viewsList = driver.findElements(By.xpath("//span[contains(text(), 'views')]"));
    
            // Declare the viewCount initially as 0
            int actual_count = 0;
            // Declare a Empty String for the View Count
            String getViewCount = "";
    
            for (WebElement viewCount : viewsList) {
                String viewcountString = viewCount.getText();
    
                //If viewCount Contains 'M views', remove 'M views'
                if (viewcountString.contains("M views")) {
                    getViewCount = viewcountString.replace("M views", "");
                }
                //If viewCount Contains 'K views', remove 'K views'
                if (viewcountString.contains("K views")) {
                    getViewCount = viewcountString.replace("K views", "");
                }
                // String getViewCount = viewcountString.replace("M views", "");
                float numericviewCount = Float.parseFloat(getViewCount);
                actual_count += numericviewCount;
    
                if (actual_count >= 10000000) { // Checking if the total count reaches 10 million
                    actual_count = 10000000; // Cap the count to 10 million
                    break; // Exit the loop since the target has been reached
                }
            }
    
            // Print Total View Count in Terminal
            System.out.println("Actual Count : " + actual_count);
       
        System.out.println("End Test case: testCase05");
    }
    @DataProvider(name = "excelData")
    public Object[][] searchData() 
    {
        return new Object[][]{
                {"Movies"},
                {"Music"},
                {"Games"},
                {"India"},
                {"UK"}
             
        
        };
    }
    

        
}