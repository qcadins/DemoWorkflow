import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.configuration.RunConfiguration as RunConfiguration
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.By as By
import org.openqa.selenium.WebDriver as WebDriver
import org.testng.Assert as Assert
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory

HashMap<String, ArrayList> chromePrefs = new HashMap<String, ArrayList>()

chromePrefs.put('download.default_directory', System.getProperty('user.dir') + '\\TC')

RunConfiguration.setWebDriverPreferencesProperty('prefs', chromePrefs)

String userDir = System.getProperty('user.dir')

String filePath = userDir + '\\TC'

WebDriver driver = DriverFactory.getWebDriver()

'Wait for Some time so that file gets downloaded and Stored in user defined path'
WebUI.delay(5)

'Verifying the file is download in the User defined Path'
isFileDownloaded(filePath)

try {
	WebUI.callTestCase(findTestCase('ViewTabTCBNI'), [:], FailureHandling.STOP_ON_FAILURE)
	
	WebUI.callTestCase(findTestCase('ViewTabTCBNI'), [:], FailureHandling.CONTINUE_ON_FAILURE)
	
}catch (Exception e) {
                KeywordUtil.markFailed(e.printStackTrace())

        
            } 

boolean isFileDownloaded(String downloadPath) {
    File file = new File(downloadPath)

    Boolean isDownloaded = false

    for (File f : file.listFiles()) {
        if (!(f.isDirectory())) {
            if (f.exists()) {
                isDownloaded = true
            }
            
            f.delete()
        }
    }
    
    WebUI.verifyEqual(isDownloaded, true)
}

//public checkVerifyEqualOrMatch(Boolean isMatch){
//	if(isMatch==false && GlobalVariable.FlagFailed==0){
//
//		'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedVerifyEqualOrMatch'
//		CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('1.TabCustomerMainData', GlobalVariable.NumofColm, GlobalVariable.StatusFailed, GlobalVariable.ReasonFailedVerifyEqualOrMatch)
//
//		GlobalVariable.FlagFailed=1
//	}
//}