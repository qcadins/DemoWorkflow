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

String servername = findTestData('Login/Login').getValue(1, 9)

String instancename = findTestData('Login/Login').getValue(2, 9)

String username = findTestData('Login/Login').getValue(3, 9)

String password = findTestData('Login/Login').getValue(4, 9)

String database = findTestData('Login/Login').getValue(5, 9)

String driverclassname = findTestData('Login/Login').getValue(6, 9)

String url = (((servername + ';instanceName=') + instancename) + ';databaseName=') + database

'connect DB'
Sql sqlconnection = CustomKeywords.'dbconnection.connectDB.connect'(url, username, password, driverclassname)

String result = CustomKeywords.'dbconnection.CustomerDataVerif.NAP2TabReferantorStoreDB'(sqlconnection, findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabReferantorData').getValue(
        GlobalVariable.NumofColm, 12))

int arrayindex = 0

resultarray = result.replace('HEADER:', '').replace('[', '').replace(']', '').split(', ')

for (GlobalVariable.NumofColm = 2; GlobalVariable.NumofColm <= (Integer.parseInt(GlobalVariable.CountofReferantor) + 1); (GlobalVariable.NumofColm)++) {
    WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabReferantorData').getValue(
            GlobalVariable.NumofColm, 13).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

    WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabReferantorData').getValue(
            GlobalVariable.NumofColm, 14).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

    WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabReferantorData').getValue(
            GlobalVariable.NumofColm, 16).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

    WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabReferantorData').getValue(
            GlobalVariable.NumofColm, 17).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)
}

println(resultarray)

