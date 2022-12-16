import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import groovy.sql.Sql as Sql

'connect DB LOS'
Sql sqlconnectionLOS = CustomKeywords.'dbConnection.connectDB.connectLOS'()

'declare datafileCustomerPersonal'
datafileCustomerPersonal = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData')

'declare datafileTabGuarantorCompany'
datafileTabGuarantorCompany = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabGuarantorDataCompany')

'get guarantorcompanydata from db'
ArrayList<String> result = CustomKeywords.'dbConnection.CustomerDataVerif.GuarantorDataStoreDBCompany'(sqlconnectionLOS, 
    datafileTabGuarantorCompany.getValue(GlobalVariable.StartIndex, 12), datafileTabGuarantorCompany.getValue(
        GlobalVariable.StartIndex, 18))

'declare arrayindex'
int arrayindex = 0

'declare arraymatch'
ArrayList<String> arrayMatch = new ArrayList<String>()

'verify relationship'
arrayMatch.add(WebUI.verifyMatch(datafileTabGuarantorCompany.getValue(GlobalVariable.StartIndex, 17).toUpperCase(), 
        (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify guarantor name'
arrayMatch.add(WebUI.verifyMatch(datafileTabGuarantorCompany.getValue(GlobalVariable.StartIndex, 18).toUpperCase(), 
        (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify tax id no'
arrayMatch.add(WebUI.verifyMatch(datafileTabGuarantorCompany.getValue(GlobalVariable.StartIndex, 19).toUpperCase(), 
        (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify company type'
arrayMatch.add(WebUI.verifyMatch(datafileTabGuarantorCompany.getValue(GlobalVariable.StartIndex, 20).toUpperCase(), 
        (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify customer model'
arrayMatch.add(WebUI.verifyMatch(datafileTabGuarantorCompany.getValue(GlobalVariable.StartIndex, 21).toUpperCase(), 
        (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify copy addres atau tidak'
if (datafileTabGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 23).equalsIgnoreCase('Yes')) {
	
	confinsindex = 0
	
	'verify address copy dari customer'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[confinsindex++].toUpperCase(),
			(result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

	'verify rt copy dari customer'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[confinsindex++].toUpperCase(),
			(result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

	'verify RW copy dari customer'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[confinsindex++].toUpperCase(),
			(result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

	'verify zipcode copy dari customer'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[confinsindex++].toUpperCase(),
			(result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

	'verify kecamatan copy dari customer'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[confinsindex++].toUpperCase(),
			(result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

	'verify kelurahan copy dari customer'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[confinsindex++].toUpperCase(),
			(result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

	'verify kota copy dari customer'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[confinsindex++].toUpperCase(),
			(result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

	'verify ownership'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[confinsindex++].toUpperCase(),
			(result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
} else {
	'verify address'
	arrayMatch.add(WebUI.verifyMatch(datafileTabGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 24).toUpperCase(),
			(result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

	'verify Rt'
	arrayMatch.add(WebUI.verifyMatch(datafileTabGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 25).toUpperCase(),
			(result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

	'verify rw'
	arrayMatch.add(WebUI.verifyMatch(datafileTabGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 26).toUpperCase(),
			(result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

	'verify zipcode'
	arrayMatch.add(WebUI.verifyMatch(datafileTabGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 27).toUpperCase(),
			(result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

	'verify kecamatan'
	arrayMatch.add(WebUI.verifyMatch(datafileTabGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 28).toUpperCase(),
			(result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

	'verify kelurahan'
	arrayMatch.add(WebUI.verifyMatch(datafileTabGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 29).toUpperCase(),
			(result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

	'verify kota'
	arrayMatch.add(WebUI.verifyMatch(datafileTabGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 30).toUpperCase(),
			(result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

	'verify ownership'
	arrayMatch.add(WebUI.verifyMatch(datafileTabGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 31).toUpperCase(),
			(result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
}

'Jika nilai di confins ada yang tidak sesuai dengan db'
if (arrayMatch.contains(false)) {

	'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedStoredDB'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('3b.TabGuarantorDataCompany', GlobalVariable.NumofGuarantorCompany, GlobalVariable.StatusFailed, GlobalVariable.ReasonFailedStoredDB)
}

