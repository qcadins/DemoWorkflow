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

//for test purposes
//GlobalVariable.NumofReferantor = 2

custname = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/label_CustName'))

ArrayList<String> result = CustomKeywords.'dbconnection.CustomerDataVerif.NAP2TabReferantorStoreDB'(sqlconnection, findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabReferantorData').getValue(
		GlobalVariable.CopyAppColm, 12), custname)

ArrayList<Boolean> arrayMatch = new ArrayList<>()
	
println(result)
int arrayindex = 0
int bankindex = 0


def bankaccount = GlobalVariable.BankAccount.split(' - ')


println(bankaccount)
println(result)

for (GlobalVariable.NumofReferantor = 2; GlobalVariable.NumofReferantor < result.size()/6 + 2; (GlobalVariable.NumofReferantor)++) {
    arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabReferantorData').getValue(
            GlobalVariable.NumofReferantor, 13).toUpperCase(), (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

    arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabReferantorData').getValue(
            GlobalVariable.NumofReferantor, 14).toUpperCase(), (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

    arrayMatch.add(WebUI.verifyMatch((bankaccount[bankindex++]).toUpperCase(), (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	
	arrayMatch.add(WebUI.verifyMatch((bankaccount[bankindex++]).toUpperCase(), (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	
	arrayMatch.add(WebUI.verifyMatch((bankaccount[bankindex++]).toUpperCase(), (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

    arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabReferantorData').getValue(
            GlobalVariable.NumofReferantor, 17).toUpperCase(), (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
}

'Jika nilai di confins ada yang tidak sesuai dengan db'
if (arrayMatch.contains(false)) {
	'write to excel FAILED'
	CustomKeywords.'customizeKeyword.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '5.TabReferantorData',
		0, GlobalVariable.CopyAppColm - 1, GlobalVariable.StatusFailed)
	
	'Write To Excel GlobalVariable.ReasonFailedStoredDB'
	CustomKeywords.'customizeKeyword.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '5.TabReferantorData',
		1, GlobalVariable.CopyAppColm - 1, GlobalVariable.ReasonFailedStoredDB)

}


