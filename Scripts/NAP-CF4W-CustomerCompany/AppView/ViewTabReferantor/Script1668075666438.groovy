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
import org.openqa.selenium.WebElement as WebElement

'get data file path'
GlobalVariable.DataFilePath = CustomKeywords.'dbconnection.connectDB.getExcelPath'(GlobalVariable.PathAppInquiryCompany)

'connect DB LOS'
Sql sqlconnectionLOS = CustomKeywords.'dbconnection.connectDB.connectLOS'()

GlobalVariable.FlagWarning = 0
 
'Klik tab referantor'
WebUI.click(findTestObject('Object Repository/AppView/Referantor/Referantor Tab'))

'Verif tidak ada alert yang muncul'
if(WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerPersonal/div_erroralert'), 2)==false){
	GlobalVariable.FlagWarning = 1
	
	'write status warning'
	CustomKeywords.'checkSaveProcess.checkSaveProcess.writeWarningAppView'(GlobalVariable.NumofColm,'4. Referantor')
}

appno = WebUI.getText(findTestObject('Object Repository/AppView/MainInformation/Label App No'))

'get referantor data arraylist from db'
ArrayList<String> resultReferantor = CustomKeywords.'dbconnection.VerifyAppView.checkReferantor'(sqlconnectionLOS, appno)

'count ref table'
variableData = DriverFactory.getWebDriver().findElements(By.cssSelector('#ViewReferantor > div > table > tbody > tr'))

Refindex = 1

for (dbindex = 0; dbindex < resultReferantor.size(); dbindex++) {
    'modify object ref category'
    modifyNewRefCat = WebUI.modifyObjectProperty(findTestObject('AppView/Referantor/ModifyObj'), 'xpath', 'equals', ('//*[@id=\'ViewReferantor\']/div/table/tbody/tr[' + 
        Refindex) + ']/td[2]', true)

    'modify object ref name'
    modifyNewRefName = WebUI.modifyObjectProperty(findTestObject('AppView/Referantor/ModifyObj'), 'xpath', 'equals', ('//*[@id=\'ViewReferantor\']/div/table/tbody/tr[' + 
        Refindex) + ']/td[3]', true)

    'modify object ref type'
    modifyNewRefType = WebUI.modifyObjectProperty(findTestObject('AppView/Referantor/ModifyObj'), 'xpath', 'equals', ('//*[@id=\'ViewReferantor\']/div/table/tbody/tr[' + 
        Refindex) + ']/td[4]', true)

    'modify object bank acc'
    modifyNewBankAcc = WebUI.modifyObjectProperty(findTestObject('AppView/Referantor/ModifyObj'), 'xpath', 'equals', ('//*[@id=\'ViewReferantor\']/div/table/tbody/tr[' + 
        Refindex) + ']/td[5]', true)

    'modify object tax calc method'
    modifyNewTaxCalc = WebUI.modifyObjectProperty(findTestObject('AppView/Referantor/ModifyObj'), 'xpath', 'equals', ('//*[@id=\'ViewReferantor\']/div/table/tbody/tr[' + 
        Refindex) + ']/td[6]', true)

    'modify object vat'
    modifyNewVat = WebUI.modifyObjectProperty(findTestObject('AppView/Referantor/ModifyObj'), 'xpath', 'equals', ('//*[@id=\'ViewReferantor\']/div/table/tbody/tr[' + 
        Refindex) + ']/td[7]', true)

    'verify referantor category'
    checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewRefCat).toUpperCase(), (resultReferantor[dbindex]).toUpperCase(), 
            false))

    dbindex++

    'verify referantor name'
    checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewRefName).toUpperCase(), (resultReferantor[dbindex]).toUpperCase(), 
            false))

    dbindex++

    'verify referantor type'
    checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewRefType).toUpperCase(), (resultReferantor[dbindex]).toUpperCase(), 
            false))

    dbindex++

    'verify bank account'
    checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewBankAcc).toUpperCase(), (resultReferantor[dbindex]).toUpperCase(), 
            false))

    dbindex++

    'verify tax calculation method'
    checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewTaxCalc).toUpperCase(), (resultReferantor[dbindex]).toUpperCase(), 
            false))

    dbindex++

    'verify vat'
    checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewVat).toUpperCase(), (resultReferantor[dbindex]).toUpperCase(), 
            false))

    Refindex++
}

if ((GlobalVariable.FlagWarning == 0) && (GlobalVariable.FlagFailed == 0)) {
	new customizeKeyword.writeToExcel().writeToExcel(GlobalVariable.DataFilePath, '4. Referantor', 0, GlobalVariable.NumofColm -
		1, GlobalVariable.StatusSuccess)
}

def checkVerifyEqualOrMatch(Boolean isMatch) {
    if ((isMatch == false) && (GlobalVariable.FlagFailed == 0)) {
        new customizeKeyword.writeToExcel().writeToExcel(GlobalVariable.DataFilePath, '4. Referantor', 0, GlobalVariable.NumofColm - 
            1, GlobalVariable.StatusFailed)

        new customizeKeyword.writeToExcel().writeToExcel(GlobalVariable.DataFilePath, '4. Referantor', 1, GlobalVariable.NumofColm - 
            1, GlobalVariable.ReasonFailedVerifyEqualOrMatch)

        GlobalVariable.FlagFailed = 1
    } 
}


