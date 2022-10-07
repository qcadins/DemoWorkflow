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

String appno = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail - Personal/appnolabel'))

String custname = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail - Personal/CustomerNameDetail'))

String resultfinancialdata = CustomKeywords.'dbconnection.CustomerDataVerif.NAP4FinancialData'(sqlconnection, appno, custname)

String resultfinancialattr = CustomKeywords.'dbconnection.CustomerDataVerif.NAP4FinancialAttr'(sqlconnection, appno, custname)

String resultbankacc = CustomKeywords.'dbconnection.CustomerDataVerif.NAP4FinDataBankAcc'(sqlconnection, appno, custname)


int financialdataindex = 0
int financialattr = 0
int bankacc = 0

resultfinancialdataarray = resultfinancialdata.replace('HEADER:', '').replace('[', '').replace(']', '').split(', ')
resultfinancialattrarray = resultfinancialattr.replace('HEADER:', '').replace('[', '').replace(']', '')
resultbankaccarray = resultbankacc.replace('HEADER:', '').replace('[', '').replace(']', '').split(', ')

println(resultfinancialattrarray)
println(resultbankaccarray)

'ganti value null > "" (String kosong)'
for (i = 0; i <= (resultfinancialdataarray.size() - 1); i++) {
	if ((resultfinancialdataarray[i]).equalsIgnoreCase('null')) {
		(resultfinancialdataarray[i]) = ''
	} else if ((resultfinancialdataarray[i]).equalsIgnoreCase('true')) {
		(resultfinancialdataarray[i]) = 'Yes'
	} else if ((resultfinancialdataarray[i]).equalsIgnoreCase('false')) {
		(resultfinancialdataarray[i]) = 'No'
	}
}

'ganti value null > "" (String kosong)'
for (i = 0; i <= (resultbankaccarray.size() - 1); i++) {
	if ((resultbankaccarray[i]).equalsIgnoreCase('null')) {
		(resultbankaccarray[i]) = ''
	} else if ((resultbankaccarray[i]).equalsIgnoreCase('true')) {
		(resultbankaccarray[i]) = 'Yes'
	} else if ((resultbankaccarray[i]).equalsIgnoreCase('false')) {
		(resultbankaccarray[i]) = 'No'
	}
}

for (i = GlobalVariable.CopyAppColm; i <= resultfinancialdataarray.size() + 1; i++) {
    'verify monthly income'
    WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(i, 12).toUpperCase(), (resultfinancialdataarray[financialdataindex++]).toUpperCase(), 
        false, FailureHandling.OPTIONAL)

    'verify other income'
    WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(i, 13).toUpperCase(), (resultfinancialdataarray[financialdataindex++]).toUpperCase(), 
        false, FailureHandling.OPTIONAL)

    'verify source other income'
    WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(i, 14).toUpperCase(), (resultfinancialdataarray[financialdataindex++]).toUpperCase(), 
        false, FailureHandling.OPTIONAL)

    'verify monthly installment'
    WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(i, 15).toUpperCase(), (resultfinancialdataarray[financialdataindex++]).toUpperCase(), 
        false, FailureHandling.OPTIONAL)

    'verify monthly expense'
    WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(i, 16).toUpperCase(), (resultfinancialdataarray[financialdataindex++]).toUpperCase(), 
        false, FailureHandling.OPTIONAL)

    'verify date as of'
    WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(i, 17).toUpperCase(), (resultfinancialdataarray[financialdataindex++]).toUpperCase(), 
        false, FailureHandling.OPTIONAL)
	
	if(i == GlobalVariable.CopyAppColm){
	'verify Jumlah harta'
	WebUI.verifyEqual(Integer.parseInt(GlobalVariable.findDataFile.getValue(i, 21)), Integer.parseInt((resultfinancialattrarray[financialattr++])),
		FailureHandling.OPTIONAL)
	}
}

for (i = GlobalVariable.CopyAppColm; i <= resultbankacc.size() + 1; i++) {
	'verify bank code'
	WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(i, 24).toUpperCase(), (resultbankacc[bankacc++]).toUpperCase(),
		false, FailureHandling.OPTIONAL)

	'verify bank branch'
	WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(i, 25).toUpperCase(), (resultbankacc[bankacc++]).toUpperCase(),
		false, FailureHandling.OPTIONAL)

	'verify bank name'
	WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(i, 26).toUpperCase(), (resultbankacc[bankacc++]).toUpperCase(),
		false, FailureHandling.OPTIONAL)

	'verify bank acc no'
	WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(i, 27).toUpperCase(), (resultbankacc[bankacc++]).toUpperCase(),
		false, FailureHandling.OPTIONAL)

	'verify is default'
	WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(i, 28).toUpperCase(), (resultbankacc[bankacc++]).toUpperCase(),
		false, FailureHandling.OPTIONAL)

	'verify is active'
	WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(i, 29).toUpperCase(), (resultbankacc[bankacc++]).toUpperCase(),
		false, FailureHandling.OPTIONAL)
	
	'verify begining balance'
	WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(i, 30).toUpperCase(), (resultbankacc[bankacc++]).toUpperCase(),
		false, FailureHandling.OPTIONAL)
	
	'verify bank statement yes/no'
	if(WebUI.verifyMatch('Yes', (resultbankacc[bankacc++]).toUpperCase(),false, FailureHandling.OPTIONAL)){
		continue
	}else{
		continue
	}

}

