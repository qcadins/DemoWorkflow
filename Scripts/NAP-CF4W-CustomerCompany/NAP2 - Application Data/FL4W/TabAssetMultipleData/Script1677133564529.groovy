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

'declare excelPathTabAsset'
excelPathTabAsset = (('NAP-' + GlobalVariable.LOB) + '-CustomerCompany/NAP2-ApplicationData/TabAssetData')

'declare excelPathCustomerCompany'
excelPathCustomerCompany = (('NAP-' + GlobalVariable.LOB) + '-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData')

countAccessories = findTestData(excelPathTabAsset).getColumnNumbers()

WebDriver driver = DriverFactory.getWebDriver()

'Looping untuk mencari nilai colm yang menunjukkan colm appno'
for (GlobalVariable.NumofAsset = 2; GlobalVariable.NumofAsset <= (findTestData(excelPathTabAsset).getColumnNumbers() - 1); (GlobalVariable.NumofAsset)++) {
    if (findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 8) == findTestData(excelPathCustomerCompany).getValue(
        GlobalVariable.NumofColm, 13)) {
        GlobalVariable.StartIndex = GlobalVariable.NumofAsset

        println(GlobalVariable.StartIndex)

        break
    }
}

for (GlobalVariable.NumofAsset = GlobalVariable.StartIndex; GlobalVariable.NumofAsset <= (countAccessories - 1); (GlobalVariable.NumofAsset)++) {
    if (findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofAsset, 8).equalsIgnoreCase(findTestData(excelPathCustomerCompany).getValue(
            GlobalVariable.NumofColm, 13))) {
        'click button add'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/AssetMultiple/buttonAddAsset'))

		'call Test case input Asset data'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/FL4W/TabAssetData'), [:], FailureHandling.CONTINUE_ON_FAILURE)
    }
}

