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

'get count colm'
countcolm = GlobalVariable.FindDataFile.getColumnNumbers()

ArrayList<Boolean> arrayMatch = new ArrayList<>()

'connect DB'
Sql sqlconnection = CustomKeywords.'dbconnection.connectDB.connect'(url, username, password, driverclassname)

String appno = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail - Personal/appnolabel'))

String custname = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail - Personal/CustomerNameDetail'))




for (index = GlobalVariable.NumofVerifStore; index < (countcolm + GlobalVariable.NumofVerifStore); index++) {
	if (GlobalVariable.FindDataFile.getValue(index, 9).length() != 0 && GlobalVariable.FindDataFile.getValue(index, 35).length() != 0) {
		
	String resultfinancialdata = CustomKeywords.'dbconnection.CustomerDataVerif.NAP4FinancialDataCompanyStoreData'(sqlconnection, appno, custname, GlobalVariable.FindDataFile.getValue(index, 35))
	
	String resultfinancialattr = CustomKeywords.'dbconnection.CustomerDataVerif.NAP4FinancialAttrCompanyStoreData'(sqlconnection, appno, custname)
	
	int financialdataindex = 0
	int financialattr = 0
	
	
	resultfinancialdataarray = resultfinancialdata.replace('HEADER:', '').replace('[', '').replace(']', '').replace(':', '').split(', ')
	resultfinancialattrarray = resultfinancialattr.replace('HEADER:', '').replace('[', '').replace(']', '').split(', ')
	
	
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
	
	
	
	
    'verify monthly income'
    arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(index, 12).split(',').join(), resultfinancialdataarray[financialdataindex++].split(',').join(), 
        false, FailureHandling.OPTIONAL))

    'verify monthly expense'
    arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(index, 13).split(',').join(), resultfinancialdataarray[financialdataindex++].split(',').join(), 
        false, FailureHandling.OPTIONAL))

    'verify return of investment %'
    arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(index, 14).split(',').join(), resultfinancialdataarray[financialdataindex++].split(',').join(), 
        false, FailureHandling.OPTIONAL))

    'verify return of equity %'
    arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(index, 15).split(',').join(), resultfinancialdataarray[financialdataindex++].split(',').join(), 
        false, FailureHandling.OPTIONAL))

    'verify return of asset %'
    arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(index, 16).split(',').join(), resultfinancialdataarray[financialdataindex++].split(',').join(), 
        false, FailureHandling.OPTIONAL))
	
	'verify profit margin %'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(index, 17).split(',').join(), resultfinancialdataarray[financialdataindex++].split(',').join(),
		false, FailureHandling.OPTIONAL))
	
	'verify current ratio %'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(index, 18).split(',').join(), resultfinancialdataarray[financialdataindex++].split(',').join(),
		false, FailureHandling.OPTIONAL))
	
	'verify debt equity ratio %'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(index, 19).split(',').join(), resultfinancialdataarray[financialdataindex++].split(',').join(),
		false, FailureHandling.OPTIONAL))
	
	'verify inventory turn over %'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(index, 20).split(',').join(), resultfinancialdataarray[financialdataindex++].split(',').join(),
		false, FailureHandling.OPTIONAL))
	
	'verify Account receiveable turn over %'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(index, 21).split(',').join(), resultfinancialdataarray[financialdataindex++].split(',').join(),
		false, FailureHandling.OPTIONAL))
	
	'verify growth %'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(index, 22).split(',').join(), resultfinancialdataarray[financialdataindex++].split(',').join(),
		false, FailureHandling.OPTIONAL))
	
	'verify working capital amount'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(index, 23).split(',').join(), resultfinancialdataarray[financialdataindex++].split(',').join(),
		false, FailureHandling.OPTIONAL))
	
	'verify other monthly installment amount'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(index, 24).split(',').join(), resultfinancialdataarray[financialdataindex++].split(',').join(),
		false, FailureHandling.OPTIONAL))
	
	'verify revenue'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(index, 25).split(',').join(), resultfinancialdataarray[financialdataindex++].split(',').join(),
		false, FailureHandling.OPTIONAL))
	
	'verify operational cost'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(index, 26).split(',').join(), resultfinancialdataarray[financialdataindex++].split(',').join(),
		false, FailureHandling.OPTIONAL))
	
	'verify profit before tax'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(index, 27).split(',').join(), resultfinancialdataarray[financialdataindex++].split(',').join(),
		false, FailureHandling.OPTIONAL))
	
	'verify current asset'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(index, 28).split(',').join(), resultfinancialdataarray[financialdataindex++].split(',').join(),
		false, FailureHandling.OPTIONAL))
	
	'verify nett fix asset'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(index, 29).split(',').join(), resultfinancialdataarray[financialdataindex++].split(',').join(),
		false, FailureHandling.OPTIONAL))
	
	'verify total asset'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(index, 30).split(',').join(), resultfinancialdataarray[financialdataindex++].split(',').join(),
		false, FailureHandling.OPTIONAL))
	
	'verify current liabilities'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(index, 31).split(',').join(), resultfinancialdataarray[financialdataindex++].split(',').join(),
		false, FailureHandling.OPTIONAL))
	
	'verify long term liabilities'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(index, 32).split(',').join(), resultfinancialdataarray[financialdataindex++].split(',').join(),
		false, FailureHandling.OPTIONAL))
	
	'verify shareholder equity'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(index, 33).split(',').join(), resultfinancialdataarray[financialdataindex++].split(',').join(),
		false, FailureHandling.OPTIONAL))
	
	'verify current ratio'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(index, 34).split(',').join(), resultfinancialdataarray[financialdataindex++].split(',').join(),
		false, FailureHandling.OPTIONAL))

    'verify date as of'
    arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(index, 35).toUpperCase(), (resultfinancialdataarray[financialdataindex++]), 
        false, FailureHandling.OPTIONAL))
	
	if(i == GlobalVariable.NumofVerifStore){
		
		int row = 38
	for(finattr = 0; finattr < resultfinancialattrarray.size(); finattr++){
		
	'verify financial attr'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(index, row++).split(',').join(), resultfinancialattrarray[finattr],
		false, FailureHandling.OPTIONAL))
	}
	
	continue
	}
	}else{
	break
	}
}

for (index = GlobalVariable.NumofVerifStore; index < (countcolm + GlobalVariable.NumofVerifStore); index++) {
	if (GlobalVariable.FindDataFile.getValue(index, 10).length() != 0 && GlobalVariable.FindDataFile.getValue(index, 70).length() != 0) {
	String resultbankacc = CustomKeywords.'dbconnection.CustomerDataVerif.NAP4FinDataBankAccCompanyStoreData'(sqlconnection, appno, custname, GlobalVariable.FindDataFile.getValue(index, 70))
	int bankacc = 0
	resultbankaccarray = resultbankacc.replace('HEADER:', '').replace('[', '').replace(']', '').split(', ')
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
	'verify bank code'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(index, 66).toUpperCase(), (resultbankaccarray[bankacc++]).toUpperCase(),
		false, FailureHandling.OPTIONAL))

	'verify bank branch'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(index, 68).toUpperCase(), (resultbankaccarray[bankacc++]).toUpperCase(),
		false, FailureHandling.OPTIONAL))

	'verify bank name'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(index, 69).toUpperCase(), (resultbankaccarray[bankacc++]).toUpperCase(),
		false, FailureHandling.OPTIONAL))

	'verify bank acc no'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(index, 70).toUpperCase(), (resultbankaccarray[bankacc++]).toUpperCase(),
		false, FailureHandling.OPTIONAL))

	'verify is default'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(index, 71).toUpperCase(), (resultbankaccarray[bankacc++]).toUpperCase(),
		false, FailureHandling.OPTIONAL))

	'verify is active'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(index, 72).toUpperCase(), (resultbankaccarray[bankacc++]).toUpperCase(),
		false, FailureHandling.OPTIONAL))
	
	'verify begining balance'
	arrayMatch.add(WebUI.verifyMatch(GlobalVariable.FindDataFile.getValue(index, 73).split(',').join(), (resultbankaccarray[bankacc++].split(',').join()).toUpperCase(),
		false, FailureHandling.OPTIONAL))
	
	String bankstat = CustomKeywords.'dbconnection.CustomerDataVerif.NAP4FinancialCheckBankStatCompanyStoreData'(sqlconnection, appno, custname, GlobalVariable.FindDataFile.getValue(index, 27))
	
	'verify bank statement yes/no'
	if(bankstat == null){
		continue
	}else if(bankstat.equalsIgnoreCase('true')){
		
		def montharray = GlobalVariable.FindDataFile.getValue(index, 74).split(';',-1)
		
		def yeararray = GlobalVariable.FindDataFile.getValue(index, 75).split(';',-1)
		
		def debittrscarray = GlobalVariable.FindDataFile.getValue(index, 76).split(';',-1)
		
		def debitarray = GlobalVariable.FindDataFile.getValue(index, 77).split(';',-1)
		
		def credittrscarray = GlobalVariable.FindDataFile.getValue(index, 78).split(';',-1)
		
		def creditarray = GlobalVariable.FindDataFile.getValue(index, 79).split(';',-1)
		
		String resultbankstatdata = CustomKeywords.'dbconnection.CustomerDataVerif.NAP4BankStatDataCompanyStoreData'(sqlconnection, appno, custname, GlobalVariable.FindDataFile.getValue(index, 27))
		
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
	}else{
	break
	}
}

'jika nilai di confins tidak sesuai dengan db'
if(arrayMatch.contains(false)){
	'Write To Excel GlobalVariable.StatusFailed'
	CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '4.FinancialData',
			0, GlobalVariable.NumofVerifStore - 1, GlobalVariable.StatusFailed)
	
	'Write To Excel GlobalVariable.ReasonFailedStoredDB'
	CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '4.FinancialData',
			1, GlobalVariable.NumofVerifStore - 1, GlobalVariable.ReasonFailedStoredDB)
}