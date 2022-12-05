import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
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
import groovy.sql.Sql as Sql
import internal.GlobalVariable as GlobalVariable

'connect DB LOS'
Sql sqlconnectionLOS = CustomKeywords.'dbConnection.connectDB.connectLOS'()

String appno = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail - Personal/appnolabel'))

String custname = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail - Personal/CustomerNameDetail'))

ArrayList<String> result = new ArrayList<>()

ArrayList<String> jobaddress = new ArrayList<>()

ArrayList<String> prevjobaddress = new ArrayList<>()

ArrayList<String> othaddress = new ArrayList<>()

ArrayList<Boolean> arrayMatch = new ArrayList<>()
if (WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/CustomerModelCode')) ==
'EMPLOYEE') {
	result = CustomKeywords.'dbConnection.CustomerDataVerif.NAP4JobdataEmployeePersonalStoreData'(sqlconnectionLOS, appno, custname)

	jobaddress = CustomKeywords.'dbConnection.CustomerDataVerif.NAP4JobdataAddressStoreData'(sqlconnectionLOS, appno, custname)

	prevjobaddress = CustomKeywords.'dbConnection.CustomerDataVerif.NAP4PrevJobAddressStoreData'(sqlconnectionLOS, appno, custname)

	othaddress = CustomKeywords.'dbConnection.CustomerDataVerif.NAP4OtherBizAddressStoredata'(sqlconnectionLOS, appno, custname)
	
} else if (WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/CustomerModelCode')) ==
'PROFESSIONAL') {
	
	result = CustomKeywords.'dbConnection.CustomerDataVerif.NAP4JobdataProfPersonalStoreData'(sqlconnectionLOS, appno, custname)

	jobaddress = CustomKeywords.'dbConnection.CustomerDataVerif.NAP4JobdataAddressStoreData'(sqlconnectionLOS, appno, custname)

	prevjobaddress = CustomKeywords.'dbConnection.CustomerDataVerif.NAP4PrevJobAddressStoreData'(sqlconnectionLOS, appno, custname)

	othaddress = CustomKeywords.'dbConnection.CustomerDataVerif.NAP4OtherBizAddressStoreData'(sqlconnectionLOS, appno, custname)
	
} else if (WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/CustomerModelCode')) ==
'NON PROFESSIONAL') {
	result = CustomKeywords.'dbConnection.CustomerDataVerif.NAP4JobdataNonProfPersonalStoreData'(sqlconnectionLOS, appno, custname)
} else {
	
	result = CustomKeywords.'dbConnection.CustomerDataVerif.NAP4JobdataSMEPersonalStoreData'(sqlconnectionLOS, appno, custname)

	jobaddress = CustomKeywords.'dbConnection.CustomerDataVerif.NAP4JobdataAddressStoreData'(sqlconnectionLOS, appno, custname)

	prevjobaddress = CustomKeywords.'dbConnection.CustomerDataVerif.NAP4PrevJobAddressStoreData'(sqlconnectionLOS, appno, custname)

	othaddress = CustomKeywords.'dbConnection.CustomerDataVerif.NAP4OtherBizAddressStoreData'(sqlconnectionLOS, appno, custname)
}

int arrayindex = 0, JobAddressindex = 0, PrevJobindex = 0, OtherAddressindex = 0

if (WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/CustomerModelCode')) ==
'EMPLOYEE') {
	'verify proffession code'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 14).toUpperCase(), (result[
		arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

	'verify job position'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 16).toUpperCase(), (result[
		arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

	'verify job status'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 17).toUpperCase(), (result[
		arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

	'verify is internal employee'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 18).toUpperCase(), (result[
		arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

	'verify is well known company'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 19).toUpperCase(), (result[
		arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

	'verify company name'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 20).toUpperCase(), (result[
		arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

	'verify industry type'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 21).toUpperCase(), (result[
		arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

	'verify company scale'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 23).toUpperCase(), (result[
		arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

	'verify start working'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 24).toUpperCase(), (result[
		arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

	'verify job title'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 25).toUpperCase(), (result[
		arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

	'verify Num of Employee'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 26).toUpperCase(), (result[
		arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
} else if (WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/CustomerModelCode')) ==
'PROFESSIONAL') {

	'verify proffession code'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 28).toUpperCase(), (result[
		arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

	'verify is well known company'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 30).toUpperCase(), (result[
		arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	
	'verify company name'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 31).toUpperCase(), (result[
		arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	
	'verify industry type'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 32).toUpperCase(), (result[
		arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	
	'verify Professional No'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 34).toUpperCase(), (result[
		arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	
	'verify start working'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 35).toUpperCase(), (result[
		arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	
	'verify job title'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 36).toUpperCase(), (result[
		arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	
} else if (WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/CustomerModelCode')) ==
'NON PROFESSIONAL') {
	'verify profession code'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 38).toUpperCase(), (result[
		arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

	'verify job title'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 40).toUpperCase(), (result[
		arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
} else {
	'verify proffession code'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 42).toUpperCase(), (result[
		arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	
	'verify job position'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 44).toUpperCase(), (result[
		arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	
	'verify is well known company'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 45).toUpperCase(), (result[
		arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	
	'verify company name'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 46).toUpperCase(), (result[
		arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	
	'verify industry type'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 47).toUpperCase(), (result[
		arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	
	'verify company scale'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 49).toUpperCase(), (result[
		arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	
	'verify start working'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 50).toUpperCase(), (result[
		arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	
	'verify job title'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 51).toUpperCase(), (result[
		arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	
	'verify Num of Employee'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 52).toUpperCase(), (result[
		arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	
	'verify Investment Type'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 53).toUpperCase(), (result[
		arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
}

if(WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/CustomerModelCode')) ==
'EMPLOYEE' || WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/CustomerModelCode')) ==
'PROFESSIONAL' || WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/CustomerModelCode')) ==
'SMALL MEDIUM ENTERPRISE'){
int row = 55
for(i = 0; i < jobaddress.size(); i++){
'verify Job address'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, row++).toUpperCase(), (jobaddress[
	i]).toUpperCase(), false, FailureHandling.OPTIONAL))
}

if(prevjobaddress.size() > 0){
'verify Previous company name'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 76).toUpperCase(), (prevjobaddress[
	PrevJobindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify Previous employment date'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 77).toUpperCase(), (prevjobaddress[
	PrevJobindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
}

row = 79
for(i = 2; i < prevjobaddress.size(); i++){
'verify Job address'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, row++).toUpperCase(), (prevjobaddress[
	i]).toUpperCase(), false, FailureHandling.OPTIONAL))
}

if(othaddress.size() > 0){
'verify Other business'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 100).toUpperCase(), (othaddress[
	OtherAddressindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify Other business type'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 101).toUpperCase(), (othaddress[
	OtherAddressindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify Other business industry type'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 102).toUpperCase(), (othaddress[
	OtherAddressindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify Other job position'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 103).toUpperCase(), (othaddress[
	OtherAddressindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify establishment date'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 104).toUpperCase(), (othaddress[
	OtherAddressindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
}

row = 106
for(i = 5 ; i < othaddress.size(); i++){
'verify Other Job address'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, row++).toUpperCase(), (othaddress[
	i]).toUpperCase(), false, FailureHandling.OPTIONAL))

}

}

'Jika nilai di confins ada yang tidak sesuai dengan db'
if (arrayMatch.contains(false)) {

	'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedStoredDB'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('3.JobData', GlobalVariable.NumofVerifStore, GlobalVariable.StatusFailed, GlobalVariable.ReasonFailedStoredDB)
}