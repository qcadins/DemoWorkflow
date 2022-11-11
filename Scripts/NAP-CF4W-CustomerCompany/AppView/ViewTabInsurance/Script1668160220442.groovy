import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import org.openqa.selenium.By as By
import org.openqa.selenium.WebDriver as WebDriver
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import groovy.sql.Sql as Sql
import internal.GlobalVariable as GlobalVariable

'Assign directori file excel ke global variabel'
String userDir = System.getProperty('user.dir')

'Assign directori file excel ke global variabel'
String filePath = userDir + GlobalVariable.PathAppInquiryCompany

'Assign directori file excel ke global variabel'
GlobalVariable.DataFilePath = filePath

GlobalVariable.FlagWarning = 0

String servername = findTestData('Login/Login').getValue(1, 9)

String instancename = findTestData('Login/Login').getValue(2, 9)

String username = findTestData('Login/Login').getValue(3, 9)

String password = findTestData('Login/Login').getValue(4, 9)

String database = findTestData('Login/Login').getValue(5, 9)

String driverclassname = findTestData('Login/Login').getValue(6, 9)

String url = (((servername + ';instanceName=') + instancename) + ';databaseName=') + database

'connect DB'
Sql sqlconnection = CustomKeywords.'dbconnection.connectDB.connect'(url, username, password, driverclassname)

'click tab Insurance'
WebUI.click(findTestObject('Object Repository/AppView/Insurance/InsuranceTab'))

'Verif tidak ada alert yang muncul'
if(WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerPersonal/div_erroralert'), 2)==false){
	GlobalVariable.FlagWarning = 1
	CustomKeywords.'checkSaveProcess.checkSaveProcess.writeWarningAppView'(GlobalVariable.NumofColm,'TC')
}

appno = WebUI.getText(findTestObject('Object Repository/AppView/MainInformation/Label App No'))

'get insured by from db'
String resultInsuredBy = CustomKeywords.'dbconnection.VerifyAppView.checkInsuredBy'(sqlconnection, appno)

println(resultInsuredBy)

if(resultInsuredBy.equalsIgnoreCase('Customer')){
	'get arraylist Insurance Customer from db'
	ArrayList<String> resultInsuranceCustomer = CustomKeywords.'dbconnection.VerifyAppView.checkInsuranceCustomer'(sqlconnection, appno)
	
}else if(resultInsuredBy.equalsIgnoreCase('Multifinance')){

'get arraylist Insurance Multifinance from db'
ArrayList<String> resultInsuranceMultifinance = CustomKeywords.'dbconnection.VerifyAppView.checkInsuranceMultifinance'(sqlconnection, appno)

'get arraylist Insurance summary from db'
ArrayList<String> resultInsuranceSummary = CustomKeywords.'dbconnection.VerifyAppView.checkInsuranceSummary'(sqlconnection, appno)

}else if(resultInsuredBy.equalsIgnoreCase('Customer - Multifinance')){

}
index = 0



if ((GlobalVariable.FlagWarning == 0) && (GlobalVariable.FlagFailed == 0)) {
	new writetoexcel.writeToExcel().writeToExcelFunction(GlobalVariable.DataFilePath, '3. Guarantor', 0, GlobalVariable.NumofColm -
		1, GlobalVariable.StatusSuccess)
}

def checkVerifyEqualOrMatch(Boolean isMatch) {
    if ((isMatch == false) && (GlobalVariable.FlagFailed == 0)) {
        new writetoexcel.writeToExcel().writeToExcelFunction(GlobalVariable.DataFilePath, '3. Guarantor', 0, GlobalVariable.NumofColm - 
            1, GlobalVariable.StatusFailed)

        new writetoexcel.writeToExcel().writeToExcelFunction(GlobalVariable.DataFilePath, '3. Guarantor', 1, GlobalVariable.NumofColm - 
            1, GlobalVariable.ReasonFailedVerifyEqualOrMatch)

        GlobalVariable.FlagFailed = 1
    }
}

