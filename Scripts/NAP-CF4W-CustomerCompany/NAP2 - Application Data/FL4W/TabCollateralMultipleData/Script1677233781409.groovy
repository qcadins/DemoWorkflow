import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
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
import internal.GlobalVariable as GlobalVariable

'declare excelPathCollateral'
excelPathCollateral = (('NAP-' + GlobalVariable.LOB) + '-CustomerCompany/NAP2-ApplicationData/TabCollateralData')

'declare excelPathCustomerCompany'
excelPathCustomerCompany = 'NAP-'+ GlobalVariable.LOB +'-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData'

countCollateral = findTestData(excelPathCollateral).getColumnNumbers()

WebDriver driver = DriverFactory.getWebDriver()

'Looping untuk mencari nilai colm yang menunjukkan colm appno'
for (GlobalVariable.NumofCollateral = 2; GlobalVariable.NumofCollateral <= (findTestData(excelPathCollateral).getColumnNumbers() - 
1); (GlobalVariable.NumofCollateral)++) {
    if (findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 8) == findTestData(excelPathCustomerCompany).getValue(
        GlobalVariable.NumofColm, 13)) {
        GlobalVariable.StartIndex = GlobalVariable.NumofCollateral

        println(GlobalVariable.StartIndex)

        break
    }
}

for (GlobalVariable.NumofCollateral = GlobalVariable.StartIndex; GlobalVariable.NumofCollateral <= (countCollateral - 1); (GlobalVariable.NumofCollateral)++) {
    if (findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 8).equalsIgnoreCase(findTestData(excelPathCustomerCompany).getValue(
            GlobalVariable.NumofColm, 13))) {
        'click button add'
        WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/Collateral/button_AddCollateral'))
		
        'call Test case input Asset data'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/FL4W/Collateral'), [:], FailureHandling.CONTINUE_ON_FAILURE)
    }
}

'click button save'
WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/Collateral/button_SaveMain'))