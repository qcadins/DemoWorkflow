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

'Klik tab financial'
WebUI.click(findTestObject('Object Repository/AppView/Financial/Financial Tab'))

'Verif tidak ada alert yang muncul'
if(WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerPersonal/div_erroralert'), 2)==false){
	GlobalVariable.FlagWarning = 1
	CustomKeywords.'checkSaveProcess.checkSaveProcess.writeWarningAppView'(GlobalVariable.NumofColm,'8. Financial')
}

'get text appno'
appno = WebUI.getText(findTestObject('Object Repository/AppView/MainInformation/Label App No'))

'get financial data arraylist from db'
HashMap<String,ArrayList> resultFin = CustomKeywords.'dbconnection.VerifyAppView.checkFinancial'(sqlconnectionLOS, appno)
ArrayList<String> listSubsidy = resultFin.get("Subsidy")
ArrayList<String> listFee = resultFin.get("Fee")
ArrayList<String> listFinancialData = resultFin.get("FinData")
ArrayList<String> installmentTable = resultFin.get("InstTable")

Subindex = 1

'Looping untuk verif subsidy'
for (dbindex = 0; dbindex < listSubsidy.size(); dbindex++) {
	'modify object subsidy from type'
	modifyNewFromType = WebUI.modifyObjectProperty(findTestObject('AppView/Financial/ModifyObjSubsidy'), 'xpath', 'equals', "//*[@id='FinData_Subsidy']/table/tbody/tr["+Subindex+"]/td[1]", true)

	'modify object subsidy from value'
	modifyNewFromValue = WebUI.modifyObjectProperty(findTestObject('AppView/Financial/ModifyObjSubsidy'), 'xpath', 'equals', "//*[@id='FinData_Subsidy']/table/tbody/tr["+Subindex+"]/td[2]", true)

	'modify object subsidy allocation'
	modifyNewAlloc = WebUI.modifyObjectProperty(findTestObject('AppView/Financial/ModifyObjSubsidy'), 'xpath', 'equals', "//*[@id='FinData_Subsidy']/table/tbody/tr["+Subindex+"]/td[3]", true)

	'modify object subsidy source'
	modifyNewSource = WebUI.modifyObjectProperty(findTestObject('AppView/Financial/ModifyObjSubsidy'), 'xpath', 'equals', "//*[@id='FinData_Subsidy']/table/tbody/tr["+Subindex+"]/td[4]", true)

	'modify object subsidy value type'
	modifyNewValueType = WebUI.modifyObjectProperty(findTestObject('AppView/Financial/ModifyObjSubsidy'), 'xpath', 'equals', "//*[@id='FinData_Subsidy']/table/tbody/tr["+Subindex+"]/td[5]", true)

	'modify object subsidy value'
	modifyNewValue = WebUI.modifyObjectProperty(findTestObject('AppView/Financial/ModifyObjSubsidy'), 'xpath', 'equals', "//*[@id='FinData_Subsidy']/table/tbody/tr["+Subindex+"]/td[6]", true)

	'verify subsidy from type'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewFromType).toUpperCase(), (listSubsidy[dbindex]).toUpperCase(),
			false))

	dbindex++

	'verify subsidy from value'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewFromValue).toUpperCase(), (listSubsidy[dbindex]).toUpperCase(),
			false))

	dbindex++
	
	'verify subsidy allocation'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewAlloc).toUpperCase(), (listSubsidy[dbindex]).toUpperCase(),
			false))

	dbindex++
	
	'verify subsidy source'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewSource).toUpperCase(), (listSubsidy[dbindex]).toUpperCase(),
			false))

	dbindex++
	
	'verify subsidy value type'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewValueType).toUpperCase(), (listSubsidy[dbindex]).toUpperCase(),
			false))

	dbindex++

	'verify subsidy value'
	checkVerifyEqualOrMatch(WebUI.verifyEqual(Double.parseDouble(WebUI.getText(modifyNewValue).replace(",","")), Double.parseDouble(listSubsidy[dbindex])))

	Subindex++
}

Feeindex = 1

'Looping untuk verif fee'
for (dbindex = 0; dbindex < listFee.size(); dbindex++) {
	'modify object fee'
	modifyNewFee = WebUI.modifyObjectProperty(findTestObject('AppView/Financial/ModifyFee'), 'xpath', 'equals', "//*[@id='fee']/div/div["+Feeindex+"]/div/div[2]", true)
	
	'modify object fee capitalize'
	modifyNewFeeCap = WebUI.modifyObjectProperty(findTestObject('AppView/Financial/ModifyFee'), 'xpath', 'equals', "//*[@id='fee']/div/div["+Feeindex+"]/div/div[4]", true)

	
	'verify all fee'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewFee).toUpperCase().replace(",",""), (listFee[dbindex]).toUpperCase(),
			false))

	dbindex++

	'verify all fee capitalize'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewFeeCap).toUpperCase().replace(",",""), (listFee[dbindex]).toUpperCase(),
			false))

	Feeindex++
}

int Finindex = 0

'verify total asset price'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Financial/TotalAssetPrice')).toUpperCase().replace(",",""),
		(listFinancialData[Finindex++]).toUpperCase(), false))

'verify total fee'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Financial/TotalFee')).toUpperCase().replace(",",""),
		(listFinancialData[Finindex++]).toUpperCase(), false))

'verify total fee capitalize'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Financial/TotalFeeCap')).toUpperCase().replace(",",""),
		(listFinancialData[Finindex++]).toUpperCase(), false))

'verify total insurance'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Financial/TotalInsurance')).toUpperCase().replace(",",""),
		(listFinancialData[Finindex++]).toUpperCase(), false))

'verify total insurance capitalize'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Financial/TotalInsuranceCap')).toUpperCase().replace(",",""),
		(listFinancialData[Finindex++]).toUpperCase(), false))

'verify total life insurance'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Financial/TotalLifeInsurance')).toUpperCase().replace(",",""),
		(listFinancialData[Finindex++]).toUpperCase(), false))

'verify total life insurance capitalize'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Financial/TotalLifeInsuranceCap')).toUpperCase().replace(",",""),
		(listFinancialData[Finindex++]).toUpperCase(), false))

'verify dp asset'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Financial/DPAsset')).toUpperCase().replace(",",""),
		(listFinancialData[Finindex++]).toUpperCase(), false))

'verify dp asset-subsidy dp'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Financial/DPAssetNett')).toUpperCase().replace(",",""),
		(listFinancialData[Finindex++]).toUpperCase(), false))

'verify tdp'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Financial/TDP')).toUpperCase().replace(",",""),
		(listFinancialData[Finindex++]).toUpperCase(), false))

'verify tdp paid at mf'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Financial/TDPPaidAtMF')).toUpperCase().replace(",",""),
		(listFinancialData[Finindex++]).toUpperCase(), false))

'verify ntf'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Financial/NTF')).toUpperCase().replace(",",""),
		(listFinancialData[Finindex++]).toUpperCase(), false))

'verify effective rate'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Financial/EffectiveRateCust')).toUpperCase().replace(",","").replace(" %",""),
		(listFinancialData[Finindex++]).toUpperCase(), false))

'verify standard rate'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Financial/StdRate')).toUpperCase().replace(",","").replace(" %",""),
		(listFinancialData[Finindex++]).toUpperCase(), false))

'verify flat rate'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Financial/FlatRateCust')).toUpperCase().replace(",","").replace(" %",""),
		(listFinancialData[Finindex++]).toUpperCase(), false))

'verify installment amount'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Financial/InstAmt')).toUpperCase().replace(",",""),
		(listFinancialData[Finindex++]).toUpperCase(), false))

'verify grace period'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Financial/GracePeriod')).toUpperCase().replace(",",""),
		(listFinancialData[Finindex++]).toUpperCase(), false))

'verify rounding'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Financial/Rounding')).toUpperCase().replace(",",""),
		(listFinancialData[Finindex++]).toUpperCase(), false))

'verify total interest'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Financial/TotalInterest')).toUpperCase().replace(",",""),
		(listFinancialData[Finindex++]).toUpperCase(), false))

'verify diff rate amount'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Financial/DiffRateAmt')).toUpperCase().replace(",",""),
		(listFinancialData[Finindex++]).toUpperCase(), false))

'verify total ar'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Financial/TotalAR')).toUpperCase().replace(",",""),
		(listFinancialData[Finindex++]).toUpperCase(), false))

'verify gross yield'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Financial/GrossYield')).toUpperCase().replace(",","").replace(" %",""),
		(listFinancialData[Finindex++]).toUpperCase(), false))

'verify ltv'
checkVerifyEqualOrMatch(WebUI.verifyEqual(Double.parseDouble(WebUI.getText(findTestObject('Object Repository/AppView/Financial/LTV')).replace(",","").replace(" %","")),
		Double.parseDouble(listFinancialData[Finindex++])*100))

'verify num of inst'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Financial/NumofInst')).toUpperCase().replace(",",""),
		(listFinancialData[Finindex++]).toUpperCase(), false))

'verify first installment type'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Financial/FirstInstType')).toUpperCase().replace(",",""),
		(listFinancialData[Finindex++]).toUpperCase(), false))

Instindex = 1

'Looping untuk verif tabel angsuran'
for (dbindex = 0; dbindex < installmentTable.size(); dbindex++) {
	'modify object Seq no'
	modifyNewNo = WebUI.modifyObjectProperty(findTestObject('AppView/Financial/ModifyTable'), 'xpath', 'equals', "//*[@id='mat-tab-content-0-7']/div/view-financial/div/div[8]/table/tbody/tr["+Instindex+"]/td[1]", true)
	
	'modify object installment amount'
	modifyNewInstAmt = WebUI.modifyObjectProperty(findTestObject('AppView/Financial/ModifyTable'), 'xpath', 'equals', "//*[@id='mat-tab-content-0-7']/div/view-financial/div/div[8]/table/tbody/tr["+Instindex+"]/td[2]", true)

	'modify object principal amount'
	modifyNewPrinAmt = WebUI.modifyObjectProperty(findTestObject('AppView/Financial/ModifyTable'), 'xpath', 'equals', "//*[@id='mat-tab-content-0-7']/div/view-financial/div/div[8]/table/tbody/tr["+Instindex+"]/td[3]", true)

	'modify object interest amount'
	modifyNewIntAmt = WebUI.modifyObjectProperty(findTestObject('AppView/Financial/ModifyTable'), 'xpath', 'equals', "//*[@id='mat-tab-content-0-7']/div/view-financial/div/div[8]/table/tbody/tr["+Instindex+"]/td[4]", true)

	'modify object os principal amount'
	modifyNewOSPrinAmt = WebUI.modifyObjectProperty(findTestObject('AppView/Financial/ModifyTable'), 'xpath', 'equals', "//*[@id='mat-tab-content-0-7']/div/view-financial/div/div[8]/table/tbody/tr["+Instindex+"]/td[5]", true)

	'modify object os interest amount'
	modifyNewOSIntAmt = WebUI.modifyObjectProperty(findTestObject('AppView/Financial/ModifyTable'), 'xpath', 'equals', "//*[@id='mat-tab-content-0-7']/div/view-financial/div/div[8]/table/tbody/tr["+Instindex+"]/td[6]", true)

	
	'verify seqno'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewNo).toUpperCase().replace(",",""), (installmentTable[dbindex]).toUpperCase(),
			false))

	dbindex++
	
	'verify installment amount'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewInstAmt).toUpperCase().replace(",",""), (installmentTable[dbindex]).toUpperCase(),
		false))

	dbindex++

	'verify principal amount'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewPrinAmt).toUpperCase().replace(",",""), (installmentTable[dbindex]).toUpperCase(),
	false))

	dbindex++

	'verify interest amount'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewIntAmt).toUpperCase().replace(",",""), (installmentTable[dbindex]).toUpperCase(),
	false))

	dbindex++

	'verify os principal amount'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewOSPrinAmt).toUpperCase().replace(",",""), (installmentTable[dbindex]).toUpperCase(),
	false))

	dbindex++

	'verify os interest amount'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewOSIntAmt).toUpperCase().replace(",",""), (installmentTable[dbindex]).toUpperCase(),
			false))

	Instindex++
}

if ((GlobalVariable.FlagWarning == 0) && (GlobalVariable.FlagFailed == 0)) {
	new customizeKeyword.writeToExcel().writeToExcelFunction(GlobalVariable.DataFilePath, '8. Financial', 0, GlobalVariable.NumofColm -
		1, GlobalVariable.StatusSuccess)
}

def checkVerifyEqualOrMatch(Boolean isMatch) {
	if ((isMatch == false) && (GlobalVariable.FlagFailed == 0)) {
		new customizeKeyword.writeToExcel().writeToExcelFunction(GlobalVariable.DataFilePath, '8. Financial', 0, GlobalVariable.NumofColm -
			1, GlobalVariable.StatusFailed)

		new customizeKeyword.writeToExcel().writeToExcelFunction(GlobalVariable.DataFilePath, '8. Financial', 1, GlobalVariable.NumofColm -
			1, GlobalVariable.ReasonFailedVerifyEqualOrMatch)

		GlobalVariable.FlagFailed = 1
	}
}

