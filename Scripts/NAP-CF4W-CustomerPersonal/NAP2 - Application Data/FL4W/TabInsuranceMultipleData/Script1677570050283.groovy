import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import org.openqa.selenium.By
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

'declare excelPathTabInsurance'
excelPathTabInsurance = 'NAP-'+ GlobalVariable.LOB +'-CustomerPersonal/NAP2-ApplicationData/TabInsuranceData'

'declare excelPathCustomerPersonal'
excelPathCustomerPersonal = (('NAP-' + GlobalVariable.LOB) + '-CustomerPersonal/NAP1-CustomerData/TabCustomerData')

countInsurance = findTestData(excelPathTabInsurance).getColumnNumbers()

WebDriver driver = DriverFactory.getWebDriver()

variable = driver.findElements(By.cssSelector('#collateral > lib-ucgridview > div > table > tbody tr'))

Double totalmainpremi = 00 , totaladdpremi = 00, totalfee = 00

'Looping untuk mencari nilai colm yang menunjukkan colm appno'
for (GlobalVariable.NumofInsurance = 2; GlobalVariable.NumofInsurance <= (findTestData(excelPathTabInsurance).getColumnNumbers() - 1); (GlobalVariable.NumofInsurance)++) {
    if (findTestData(excelPathTabInsurance).getValue(GlobalVariable.NumofInsurance, 8) == findTestData(excelPathCustomerPersonal).getValue(
        GlobalVariable.NumofColm, 13)) {
        GlobalVariable.StartIndex = GlobalVariable.NumofInsurance

        println(GlobalVariable.StartIndex)

        break
    }
}

for (Insurance = 1; Insurance <= variable.size(); Insurance++) {
    if (findTestData(excelPathTabInsurance).getValue(GlobalVariable.NumofInsurance, 8).equalsIgnoreCase(findTestData(excelPathCustomerPersonal).getValue(
            GlobalVariable.NumofColm, 13))) {
		
		'modify button edit Insurance'
		buttonEditInsurance = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabInsuranceData/FL4W/modifyObjectMultiple'),'xpath','equals',"//*[@id='collateral']/lib-ucgridview/div/table/tbody/tr[" + Insurance + "]/td[10]/span/span/span/a/i",true)
		
		'modify main premi'
		modifyMainPremi = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabInsuranceData/FL4W/modifyObjectMultiple'),'xpath','equals',"//*[@id='collateral']/lib-ucgridview/div/table/tbody/tr[" + Insurance + "]/td[7]/span",true)
		
		'modify add premi'
		modifyAddPremi = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabInsuranceData/FL4W/modifyObjectMultiple'),'xpath','equals',"//*[@id='collateral']/lib-ucgridview/div/table/tbody/tr[" + Insurance + "]/td[8]/span",true)
		
		'modify fee'
		modifyFee = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabInsuranceData/FL4W/modifyObjectMultiple'),'xpath','equals',"//*[@id='collateral']/lib-ucgridview/div/table/tbody/tr[" + Insurance + "]/td[9]/span",true)
		
        'click button edit'
        WebUI.click(buttonEditInsurance)

		'call Test case input insurance data'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/FL4W/TabInsuranceData'), [:], FailureHandling.CONTINUE_ON_FAILURE)
		
		totalmainpremi += Double.parseDouble(WebUI.getText(modifyMainPremi).replace(',', ''))
		
		totaladdpremi += Double.parseDouble(WebUI.getText(modifyAddPremi).replace(',', ''))
		
		totalfee += Double.parseDouble(WebUI.getText(modifyFee).replace(',', ''))
		
		GlobalVariable.NumofInsurance++
    }
}
println(totalmainpremi)
println(totaladdpremi)
println(totalfee)

Double TotalPremi = totalmainpremi + totaladdpremi + totalfee

WebUI.verifyEqual(Double.parseDouble(WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabInsuranceData/FL4W/totalPremiumtoCust')).replace(',', '')), TotalPremi)
WebUI.verifyEqual(Double.parseDouble(WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabInsuranceData/FL4W/totalCapitalizedAmount')).replace(',', '')), TotalPremi)

'click button save main'
WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabInsuranceData/FL4W/button_SaveMain'))