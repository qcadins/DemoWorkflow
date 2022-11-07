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

String servername = findTestData('Login/Login').getValue(1, 9)

String instancename = findTestData('Login/Login').getValue(2, 9)

String username = findTestData('Login/Login').getValue(3, 9)

String password = findTestData('Login/Login').getValue(4, 9)

String database = findTestData('Login/Login').getValue(5, 9)

String driverclassname = findTestData('Login/Login').getValue(6, 9)

String url = (((servername + ';instanceName=') + instancename) + ';databaseName=') + database

'connect DB'
Sql sqlconnection = CustomKeywords.'dbconnection.connectDB.connect'(url, username, password, driverclassname)

appno = findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofColm, 
    13)



ArrayList<String> resultCustomerMainData = CustomKeywords.'dbconnection.VerifyAppView.checkCustomerMainData'(sqlconnection, appno)

//'ganti value null > "" (String kosong)'
//for (i = 0; i < resultCustomerMainData.size(); i++) {
//    if ((resultCustomerMainData[i]) == null) {
//        (resultCustomerMainData[i]) = '-'
//    }
//}

int index = 0

println(resultCustomerMainData)

'verify cust name'
WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/CustomerMainData/CustomerName')).toString().toUpperCase(), 
    (resultCustomerMainData[index++]).toString().toUpperCase(), false, FailureHandling.OPTIONAL)

'verify industry type'
WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/CustomerMainData/IndustryType')).toString().toUpperCase(),
	(resultCustomerMainData[index++]).toString().toUpperCase(), false, FailureHandling.OPTIONAL)

'verify num of employee'
WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/CustomerMainData/NumofEmployee')).toString().toUpperCase(),
	(resultCustomerMainData[index++]).toString().toUpperCase(), false, FailureHandling.OPTIONAL)

'verify affiliation MF'
WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/CustomerMainData/AffiliationMF')).toString().toUpperCase(),
	(resultCustomerMainData[index++]).toString().toUpperCase(), false, FailureHandling.OPTIONAL)

'verify is VIP'
WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/CustomerMainData/isVIP')).toString().toUpperCase(),
	(resultCustomerMainData[index++]).toString().toUpperCase(), false, FailureHandling.OPTIONAL)

'verify Cust model'
WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/CustomerMainData/CustomerModel')).toString().toUpperCase(), (resultCustomerMainData[
                                                                                                                                                              index++]).toString().toUpperCase(), false, FailureHandling.OPTIONAL)

'verify company type'
WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/CustomerMainData/CompanyType')).toString().toUpperCase(),
		(resultCustomerMainData[index++]).toString().toUpperCase(), false, FailureHandling.OPTIONAL)

'verify NPWP'
WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/CustomerMainData/NPWP')).toString().toUpperCase(),
		(resultCustomerMainData[index++]).toString().toUpperCase(), false, FailureHandling.OPTIONAL)

'verify Establishment date'
WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/CustomerMainData/EstablishmentDate')).toString().toUpperCase(),
		(resultCustomerMainData[index++]).toString().toUpperCase(), false, FailureHandling.OPTIONAL)





