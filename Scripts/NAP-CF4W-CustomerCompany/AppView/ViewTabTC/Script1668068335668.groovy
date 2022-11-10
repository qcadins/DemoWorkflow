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

String servername = findTestData('Login/Login').getValue(1, 9)

String instancename = findTestData('Login/Login').getValue(2, 9)

String username = findTestData('Login/Login').getValue(3, 9)

String password = findTestData('Login/Login').getValue(4, 9)

String database = findTestData('Login/Login').getValue(5, 9)

String driverclassname = findTestData('Login/Login').getValue(6, 9)

String url = (((servername + ';instanceName=') + instancename) + ';databaseName=') + database

'connect DB'
Sql sqlconnection = CustomKeywords.'dbconnection.connectDB.connect'(url, username, password, driverclassname)

'click tab TC'
WebUI.click(findTestObject('Object Repository/AppView/TermCondition/TermAndCondition Tab'))

'verify alert'
WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerPersonal/div_erroralert'), 5, FailureHandling.OPTIONAL)

appno = WebUI.getText(findTestObject('Object Repository/AppView/MainInformation/Label App No'))

'get arraylist term and condition from db'
ArrayList<String> resultTC = CustomKeywords.'dbconnection.VerifyAppView.checkTermandCondition'(sqlconnection, appno)

'count TC table'
variableData = DriverFactory.getWebDriver().findElements(By.cssSelector('#viewAppTcInfo > lib-ucgridview > div > table > tbody tr'))

index = 0

for (TCindex = 1; TCindex <= variableData.size(); TCindex++) {
    'modify object document Name'
    modifyNewDocumentName = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 'equals', 
        ('//*[@id="viewAppTcInfo"]/lib-ucgridview/div/table/tbody/tr[' + TCindex) + ']/td[2]', true)

    'modify object Prior to'
    modifyNewPriorTo = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 'equals', 
        ('//*[@id="viewAppTcInfo"]/lib-ucgridview/div/table/tbody/tr[' + TCindex) + ']/td[3]', true)

    'modify object Check'
    modifyNewCheck = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 'equals', 
        ('//*[@id="viewAppTcInfo"]/lib-ucgridview/div/table/tbody/tr[' + TCindex) + ']/td[4]', true)

    'modify object Waived'
    modifyNewWaived = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 'equals', 
        ('//*[@id="viewAppTcInfo"]/lib-ucgridview/div/table/tbody/tr[' + TCindex) + ']/td[5]', true)

    'modify object Promise Date'
    modifyNewPromiseDate = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 'equals', 
        ('//*[@id="viewAppTcInfo"]/lib-ucgridview/div/table/tbody/tr[' + TCindex) + ']/td[6]', true)

    'modify object Expired Date'
    modifyNewExpiredDate = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 'equals', 
        ('//*[@id="viewAppTcInfo"]/lib-ucgridview/div/table/tbody/tr[' + TCindex) + ']/td[7]', true)

    'modify object Notes'
    modifyNewNotes = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 'equals', 
        ('//*[@id="viewAppTcInfo"]/lib-ucgridview/div/table/tbody/tr[' + TCindex) + ']/td[8]', true)

	'verify document name'
    checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewDocumentName).toUpperCase(), (resultTC[index++]).toUpperCase(), 
            false))

	'verify prior to'
    checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewPriorTo).toUpperCase(), (resultTC[index++]).toUpperCase(), 
            false))

	'verify checked'
    checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewCheck).toUpperCase(), (resultTC[index++]).toUpperCase(), 
            false))

	'verify Waived'
    checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewWaived).toUpperCase(), (resultTC[index++]).toUpperCase(), 
            false))

	'verify promised date'
    checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewPromiseDate).toUpperCase(), (resultTC[index++]).toUpperCase(), 
            false))

	'verify expired date'
    checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewExpiredDate).toUpperCase(), (resultTC[index++]).toUpperCase(), 
            false))

	'verify notes'
    checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewNotes).toUpperCase(), (resultTC[index++]).toUpperCase(), 
            false))
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

