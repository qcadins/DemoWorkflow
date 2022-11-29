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

'get data file path'
GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'(GlobalVariable.PathAppInquiryPersonal)

GlobalVariable.FlagWarning = 0

'connect DB LOS'
Sql sqlconnection = CustomKeywords.'dbConnection.connectDB.connectLOS'()

'click tab guarantor'
WebUI.click(findTestObject('Object Repository/AppView/Guarantor/TabGuarantor'))

'Verif tidak ada alert yang muncul'
if(WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerPersonal/div_erroralert'), 2)==false){
	GlobalVariable.FlagWarning = 1
	'write to excel status warning'
	CustomKeywords.'checkSaveProcess.checkSaveProcess.writeWarningAppView'(GlobalVariable.NumofColm,'3. Guarantor')
}

'get appno from confins'
appno = WebUI.getText(findTestObject('Object Repository/AppView/MainInformation/Label App No'))

'get arraylist guarantor from db'
ArrayList<String> resultGuar = CustomKeywords.'appView.verifyAppView.checkGuarantor'(sqlconnection, appno)

'count guarantor table'
variableData = DriverFactory.getWebDriver().findElements(By.cssSelector('#guaAll > table > tbody tr'))

index = 0

'looping guarantor from db'
for (dbindex = 0; dbindex < resultGuar.size(); dbindex++) {
	'looping guarantor confins table'
    for (Guarindex = 1; Guarindex <= variableData.size(); Guarindex++) {
        'modify object guar Name'
        modifyNewGuarName = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 'equals', 
            ('//*[@id="guaAll"]/table/tbody/tr[' + Guarindex) + ']/td[1]', true)

        'modify object Guar Type'
        modifyNewGuarType = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 'equals', 
            ('//*[@id="guaAll"]/table/tbody/tr[' + Guarindex) + ']/td[2]', true)

        'modify object relationship'
        modifyNewRelationship = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 
            'equals', ('//*[@id="guaAll"]/table/tbody/tr[' + Guarindex) + ']/td[3]', true)

        'modify object Mobile Phone'
        modifyNewMobilePhone = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 
            'equals', ('//*[@id="guaAll"]/table/tbody/tr[' + Guarindex) + ']/td[4]', true)

		'Pengecekan jika guar name sesuai dengan db'
        if (WebUI.verifyMatch(WebUI.getText(modifyNewGuarName).toUpperCase(), (resultGuar[dbindex]).toUpperCase(), false, 
            FailureHandling.OPTIONAL)) {
            'verify Guarantor name'
            checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewGuarName).toUpperCase(), (resultGuar[dbindex]).toUpperCase(), 
                    false))

            dbindex++

            'verify Guarantor type'
            checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewGuarType).toUpperCase(), (resultGuar[dbindex]).toUpperCase(), 
                    false))

            dbindex++

            'verify Guarantor relationship'
            checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewRelationship).toUpperCase(), (resultGuar[dbindex]).toUpperCase(), 
                    false))

            dbindex++

            'verify Guarantor mobile phone'
            checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewMobilePhone).toUpperCase(), (resultGuar[dbindex]).toUpperCase(), 
                    false))

            break
        }
    }
}

if ((GlobalVariable.FlagWarning == 0) && (GlobalVariable.FlagFailed == 0)) {
	'write to excel status success'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '3. Guarantor', 0, GlobalVariable.NumofColm -
		1, GlobalVariable.StatusSuccess)
}

def checkVerifyEqualOrMatch(Boolean isMatch) {
    if ((isMatch == false) && (GlobalVariable.FlagFailed == 0)) {
		'write to excel status failed'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '3. Guarantor', 0, GlobalVariable.NumofColm - 
            1, GlobalVariable.StatusFailed)

		'write to excel reason failed verify equal or match'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '3. Guarantor', 1, GlobalVariable.NumofColm - 
            1, GlobalVariable.ReasonFailedVerifyEqualOrMatch)

        GlobalVariable.FlagFailed = 1
    }
}
