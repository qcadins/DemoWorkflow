import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import java.text.SimpleDateFormat as SimpleDateFormat
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

String appno = WebUI.getText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail/appnolabel'))

ArrayList<String> resultotherattrinfo = CustomKeywords.'dbConnection.CustomerDataVerif.NAP4OtherAttrInfoStoreData'(sqlconnectionLOS, appno, 
    GlobalVariable.Custname)

ArrayList<String> resultotherattrlist = CustomKeywords.'dbConnection.CustomerDataVerif.NAP4OtherAttrStoreData'(sqlconnectionLOS, appno, 
    GlobalVariable.Custname)

ArrayList<Boolean> arrayMatch = new ArrayList<>()

int arrayindexinfo = 0

int arrayindexlist = 0

'verify debtor group'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 13).toUpperCase(), (resultotherattrinfo[
    arrayindexinfo++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify debtor business scale'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 15).toUpperCase(), (resultotherattrinfo[
    arrayindexinfo++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify counterpart scale'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 17).toUpperCase(), (resultotherattrinfo[
    arrayindexinfo++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify sustainable financial business'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 19).toUpperCase(), (resultotherattrinfo[
    arrayindexinfo++]).toUpperCase(), false, FailureHandling.OPTIONAL))

if(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 21).length() > 0){
	'verify is split treasure'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 21).toUpperCase(), (resultotherattrlist[
	                                                                                                                                              arrayindexlist++]).toUpperCase(), false, FailureHandling.OPTIONAL))
}else{
	arrayindexlist++
}


'verify is debtor group slik'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 22).toUpperCase(), (resultotherattrlist[
    arrayindexlist++]).toUpperCase(), false, FailureHandling.OPTIONAL))

if(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 24).length() > 0){
	'verify is violate BMPK'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 24).toUpperCase(), (resultotherattrlist[
	                                                                                                                                              arrayindexlist++]).toUpperCase(), false, FailureHandling.OPTIONAL))
}else{
	arrayindexlist++
}

if(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 25).length() > 0){
	'verify is exceed BMPK'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 25).toUpperCase(), (resultotherattrlist[
	                                                                                                                                              arrayindexlist++]).toUpperCase(), false, FailureHandling.OPTIONAL))
}else{
	arrayindexlist++
}

'verify AFF with MF slik'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 26).toUpperCase(), (resultotherattrlist[
    arrayindexlist++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify Department AML'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 28).toUpperCase(), (resultotherattrlist[
    arrayindexlist++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify CSP/USL Source AML'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 30).toUpperCase(), (resultotherattrlist[
    arrayindexlist++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify Payment Type'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 32).toUpperCase(), (resultotherattrlist[
    arrayindexlist++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify KYC report AML'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 34).toUpperCase(), (resultotherattrlist[
    arrayindexlist++]).toUpperCase(), false, FailureHandling.OPTIONAL))

String authorityaml = GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 35)

if (!(authorityaml.equalsIgnoreCase('#N/A'))) {
	'verify Authorithy AML'
	arrayMatch.add(WebUI.verifyMatch(authorityaml.toUpperCase(), (resultotherattrlist[arrayindexlist++]).toUpperCase(), false, FailureHandling.OPTIONAL))
}

'convert date confins dan excel agar sama'
SimpleDateFormat sdf = new SimpleDateFormat('MM/dd/yyyy')

Date parsedDate = null

String sentDate = GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 37)

parsedDate = sdf.parse(sentDate)

sdf = new SimpleDateFormat('yyyy-MM-dd')

String sDate = sdf.format(parsedDate)

'verify Attend'
arrayMatch.add(WebUI.verifyMatch(sDate, (resultotherattrlist[arrayindexlist++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify Business source AML'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 38).toUpperCase(), (resultotherattrlist[
    arrayindexlist++]).toUpperCase(), false, FailureHandling.OPTIONAL))

sdf = new SimpleDateFormat('MM/dd/yyyy')

parsedDate = null

sentDate = GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 40)

parsedDate = sdf.parse(sentDate)

sdf = new SimpleDateFormat('yyyy-MM-dd')

sDate = sdf.format(parsedDate)

'verify Business period AML'
arrayMatch.add(WebUI.verifyMatch(sDate, (resultotherattrlist[arrayindexlist++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify Building'
arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofVerifStore, 41).toUpperCase(), (resultotherattrlist[
    arrayindexlist++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'Jika nilai di confins ada yang tidak sesuai dengan db'
if (arrayMatch.contains(false)) {
	
	'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedStoredDB'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('7.OtherAttribute', GlobalVariable.NumofVerifStore, GlobalVariable.StatusFailed, GlobalVariable.ReasonFailedStoredDB)
}