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

String resultfinancialdata = CustomKeywords.'dbconnection.CustomerDataVerif.NAP4FinancialDataPersonalStoreData'(sqlconnection, appno, custname)

String resultfinancialattr = CustomKeywords.'dbconnection.CustomerDataVerif.NAP4FinancialAttrStoreData'(sqlconnection, appno, custname)

String resultbankacc = CustomKeywords.'dbconnection.CustomerDataVerif.NAP4FinDataBankAccStoreData'(sqlconnection, appno, custname)

ArrayList<Boolean> arrayMatch = new ArrayList<>()
int financialdataindex = 0
int financialattr = 0
int bankacc = 0

resultfinancialdataarray = resultfinancialdata.replace('HEADER:', '').replace('[', '').replace(']', '').split(', ')
resultfinancialattrarray = resultfinancialattr.replace('HEADER:', '').replace('[', '').replace(']', '')
resultbankaccarray = resultbankacc.replace('HEADER:', '').replace('[', '').replace(']', '').split(', ')


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

for (i = GlobalVariable.NumofVerifStore; i < resultfinancialdataarray.size()/7 + GlobalVariable.NumofVerifStore; i++) {
	'verify monthly income'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(i, 12).split(',').join(), resultfinancialdataarray[financialdataindex++].split(',').join(),
		false, FailureHandling.OPTIONAL))

	'verify other income'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(i, 13).split(',').join(), resultfinancialdataarray[financialdataindex++].split(',').join(),
		false, FailureHandling.OPTIONAL))

	'verify source other income'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(i, 14).split(',').join(), resultfinancialdataarray[financialdataindex++].split(',').join(),
		false, FailureHandling.OPTIONAL))

	'verify monthly installment'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(i, 15).split(',').join(), resultfinancialdataarray[financialdataindex++].split(',').join(),
		false, FailureHandling.OPTIONAL))

	'verify monthly expense'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(i, 16).split(',').join(), resultfinancialdataarray[financialdataindex++].split(',').join(),
		false, FailureHandling.OPTIONAL))

	'verify date as of'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(i, 17).toUpperCase(), (resultfinancialdataarray[financialdataindex++]),
		false, FailureHandling.OPTIONAL))
	
	if(i == GlobalVariable.NumofVerifStore){
	'verify Jumlah harta'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(i, 21).split(',').join(), resultfinancialattrarray,
		false, FailureHandling.OPTIONAL))
	}
}

for (i = GlobalVariable.NumofVerifStore; i < (resultbankaccarray.size()/7) + GlobalVariable.NumofVerifStore ; i++) {
	'verify bank code'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(i, 23).toUpperCase(), (resultbankaccarray[bankacc++]).toUpperCase(),
		false, FailureHandling.OPTIONAL))

	'verify bank branch'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(i, 25).toUpperCase(), (resultbankaccarray[bankacc++]).toUpperCase(),
		false, FailureHandling.OPTIONAL))

	'verify bank name'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(i, 26).toUpperCase(), (resultbankaccarray[bankacc++]).toUpperCase(),
		false, FailureHandling.OPTIONAL))

	'verify bank acc no'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(i, 27).toUpperCase(), (resultbankaccarray[bankacc++]).toUpperCase(),
		false, FailureHandling.OPTIONAL))

	'verify is default'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(i, 28).toUpperCase(), (resultbankaccarray[bankacc++]).toUpperCase(),
		false, FailureHandling.OPTIONAL))

	'verify is active'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(i, 29).toUpperCase(), (resultbankaccarray[bankacc++]).toUpperCase(),
		false, FailureHandling.OPTIONAL))
	
	'verify begining balance'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(i, 30).split(',').join(), (resultbankaccarray[bankacc++].split(',').join()).toUpperCase(),
		false, FailureHandling.OPTIONAL))
	
	String bankstat = CustomKeywords.'dbconnection.CustomerDataVerif.NAP4FinancialCheckBankStatStoreData'(sqlconnection, appno, custname, GlobalVariable.findDataFile.getValue(i, 27))
	
	'verify bank statement yes/no'
	if(bankstat == null){
		continue
	}else if(bankstat.equalsIgnoreCase('true')){
		
		def montharray = GlobalVariable.findDataFile.getValue(i, 31).split(';',-1)
		
		def yeararray = GlobalVariable.findDataFile.getValue(i, 32).split(';',-1)
		
		def debittrscarray = GlobalVariable.findDataFile.getValue(i, 33).split(';',-1)
		
		def debitarray = GlobalVariable.findDataFile.getValue(i, 34).split(';',-1)
		
		def credittrscarray = GlobalVariable.findDataFile.getValue(i, 35).split(';',-1)
		
		def creditarray = GlobalVariable.findDataFile.getValue(i, 36).split(';',-1)
		
		String resultbankstatdata = CustomKeywords.'dbconnection.CustomerDataVerif.NAP4BankStatDataStoreData'(sqlconnection, appno, custname, GlobalVariable.findDataFile.getValue(i, 27))
		
		resultbankstatdataarray = resultbankstatdata.replace('HEADER:', '').replace('[', '').replace(']', '').split(', ')
		
		'ganti value null > "" (String kosong)'
		for (i = 0; i <= (resultbankstatdataarray.size() - 1); i++) {
			if ((resultbankstatdataarray[i]).equalsIgnoreCase('0')) {
				(resultbankstatdataarray[i]) = ''
			}
		}
		
		int bankstatdb = 0
		
		for(bankstatexcel = 0; bankstatexcel < resultbankstatdataarray.size()/6 ; bankstatexcel++){
			'verify month'
			arrayMatch.add(WebUI.verifyMatch(montharray[bankstatexcel].toUpperCase(), (resultbankstatdataarray[bankstatdb++].toUpperCase()),
				false, FailureHandling.OPTIONAL))
			
			'verify year'
			arrayMatch.add(WebUI.verifyMatch(yeararray[bankstatexcel], (resultbankstatdataarray[bankstatdb++].toUpperCase()),
				false, FailureHandling.OPTIONAL))
			
			'verify debit transaction'
			arrayMatch.add(WebUI.verifyMatch(debittrscarray[bankstatexcel], (resultbankstatdataarray[bankstatdb++].toUpperCase()),
				false, FailureHandling.OPTIONAL))
			
			'verify debit'
			arrayMatch.add(WebUI.verifyMatch(debitarray[bankstatexcel].split(',').join(), (resultbankstatdataarray[bankstatdb++].toUpperCase().split(',').join()),
				false, FailureHandling.OPTIONAL))
			
			'verify credit transaction'
			arrayMatch.add(WebUI.verifyMatch(credittrscarray[bankstatexcel], (resultbankstatdataarray[bankstatdb++].toUpperCase()),
				false, FailureHandling.OPTIONAL))
			
			'verify credit'
			arrayMatch.add(WebUI.verifyMatch(creditarray[bankstatexcel].split(',').join(), (resultbankstatdataarray[bankstatdb++].split(',').join()),
				false, FailureHandling.OPTIONAL))
			
			
		}
		
	}

}

'Jika nilai di confins ada yang tidak sesuai dengan db'
if (arrayMatch.contains(false)) {
	'write to excel FAILED'
	CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '5.FinancialData',
		0, GlobalVariable.NumofVerifStore - 1, GlobalVariable.StatusFailed)
	
	'Write To Excel GlobalVariable.ReasonFailedStoredDB'
	CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '5.FinancialData',
		1, GlobalVariable.NumofVerifStore - 1, GlobalVariable.ReasonFailedStoredDB)

}

