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

String servername = findTestData('Login/Login').getValue(1, 9)

String instancename = findTestData('Login/Login').getValue(2, 9)

String username = findTestData('Login/Login').getValue(3, 9)

String password = findTestData('Login/Login').getValue(4, 9)

String database = findTestData('Login/Login').getValue(5, 9)

String driverclassname = findTestData('Login/Login').getValue(6, 9)

String url = (((servername + ';instanceName=') + instancename) + ';databaseName=') + database

'connect DB'
Sql sqlconnection = CustomKeywords.'dbconnection.connectDB.connect'(url, username, password, driverclassname)

String appno = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail - Personal/appnolabel'))

String resultotherattrinfo = CustomKeywords.'dbconnection.CustomerDataVerif.NAP4OtherAttrInfoStoreData'(sqlconnection, appno, 
    GlobalVariable.custname).replace('HEADER:', '').replace('[', '').replace(']', '')

String resultotherattrlist = CustomKeywords.'dbconnection.CustomerDataVerif.NAP4OtherAttrStoreData'(sqlconnection, appno, 
    GlobalVariable.custname)

resultotherattrinfoarray = resultotherattrinfo.split(', ')

resultotherattrlistarray = resultotherattrlist.replace('[', '').replace(']', '').split(', ')

println(resultotherattrlist)

int arrayindexinfo = 0

int arrayindexlist = 0

'verify debtor group'
WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(GlobalVariable.NumofVerifStore, 13).toUpperCase(), (resultotherattrinfoarray[
    arrayindexinfo++]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify debtor business scale'
WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(GlobalVariable.NumofVerifStore, 15).toUpperCase(), (resultotherattrinfoarray[
    arrayindexinfo++]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify counterpart scale'
WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(GlobalVariable.NumofVerifStore, 17).toUpperCase(), (resultotherattrinfoarray[
    arrayindexinfo++]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify sustainable financial business'
WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(GlobalVariable.NumofVerifStore, 19).toUpperCase(), (resultotherattrinfoarray[
    arrayindexinfo++]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify Go Public'
WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(GlobalVariable.NumofVerifStore, 21).toUpperCase(), (resultotherattrlistarray[
    arrayindexlist++]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify debtor group SLIK code'
WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(GlobalVariable.NumofVerifStore, 22).toUpperCase(), (resultotherattrlistarray[
    arrayindexlist++]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify Violate BMPK'
WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(GlobalVariable.NumofVerifStore, 24).toUpperCase(), (resultotherattrlistarray[
    arrayindexlist++]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify exceed BMPK'
WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(GlobalVariable.NumofVerifStore, 25).toUpperCase(), (resultotherattrlistarray[
    arrayindexlist++]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify Rating Debitor'
WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(GlobalVariable.NumofVerifStore, 26).toUpperCase(), (resultotherattrlistarray[
    arrayindexlist++]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify Rating institute code'
WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(GlobalVariable.NumofVerifStore, 27).toUpperCase(), (resultotherattrlistarray[
    arrayindexlist++]).toUpperCase(), false, FailureHandling.OPTIONAL)

'convert date confins dan excel agar sama'
SimpleDateFormat sdf = new SimpleDateFormat('MM/dd/yyyy')

Date parsedDate = null

String sentDate = GlobalVariable.findDataFile.getValue(GlobalVariable.NumofVerifStore, 29)

parsedDate = sdf.parse(sentDate)

sdf = new SimpleDateFormat('yyyy-MM-dd')

String sDate = sdf.format(parsedDate)

'verify Rating Date'
WebUI.verifyMatch(sDate, resultotherattrlistarray[arrayindexlist++], false, FailureHandling.OPTIONAL)

'verify AFF with MF code'
WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(GlobalVariable.NumofVerifStore, 30).toUpperCase(), (resultotherattrlistarray[
    arrayindexlist++]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify KYC report AML'
WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(GlobalVariable.NumofVerifStore, 32).toUpperCase(), (resultotherattrlistarray[
    arrayindexlist++]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify CSP/USL Source AML Code'
WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(GlobalVariable.NumofVerifStore, 33).toUpperCase(), (resultotherattrlistarray[
    arrayindexlist++]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify Payment code'
WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(GlobalVariable.NumofVerifStore, 35).toUpperCase(), (resultotherattrlistarray[
    arrayindexlist++]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify Business code AML'
WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(GlobalVariable.NumofVerifStore, 37).toUpperCase(), (resultotherattrlistarray[
    arrayindexlist++]).toUpperCase(), false, FailureHandling.OPTIONAL)

sdf = new SimpleDateFormat('MM/dd/yyyy')

parsedDate = null

sentDate = GlobalVariable.findDataFile.getValue(GlobalVariable.NumofVerifStore, 39)

parsedDate = sdf.parse(sentDate)

sdf = new SimpleDateFormat('yyyy-MM-dd')

sDate = sdf.format(parsedDate)

'verify Business period AML'
WebUI.verifyMatch(sDate, resultotherattrlistarray[arrayindexlist++], false, FailureHandling.OPTIONAL)

