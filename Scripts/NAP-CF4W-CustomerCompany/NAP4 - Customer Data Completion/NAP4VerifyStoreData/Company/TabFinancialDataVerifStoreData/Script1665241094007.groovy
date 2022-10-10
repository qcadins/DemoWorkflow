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

String resultfinancialdata = CustomKeywords.'dbconnection.CustomerDataVerif.NAP4FinancialDataCompanyStoreData'(sqlconnection, appno, custname)

String resultfinancialattr = CustomKeywords.'dbconnection.CustomerDataVerif.NAP4FinancialAttrCompanyStoreData'(sqlconnection, appno, custname)

String resultbankacc = CustomKeywords.'dbconnection.CustomerDataVerif.NAP4FinDataBankAccCompanyStoreData'(sqlconnection, appno, custname)


int financialdataindex = 0
int financialattr = 0
int bankacc = 0

println(resultfinancialdata)
resultfinancialdataarray = resultfinancialdata.replace('HEADER:', '').replace('[', '').replace(']', '').replace(':', '').split(', ')
resultfinancialattrarray = resultfinancialattr.replace('HEADER:', '').replace('[', '').replace(']', '').split(', ')
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

for (i = GlobalVariable.CopyAppColm; i < resultfinancialdataarray.size()/24 + GlobalVariable.CopyAppColm; i++) {
    'verify monthly income'
    WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(i, 12).split(',').join(), resultfinancialdataarray[financialdataindex++].split(',').join(), 
        false, FailureHandling.OPTIONAL)

    'verify monthly expense'
    WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(i, 13).split(',').join(), resultfinancialdataarray[financialdataindex++].split(',').join(), 
        false, FailureHandling.OPTIONAL)

    'verify return of investment %'
    WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(i, 14).split(',').join(), resultfinancialdataarray[financialdataindex++].split(',').join(), 
        false, FailureHandling.OPTIONAL)

    'verify return of equity %'
    WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(i, 15).split(',').join(), resultfinancialdataarray[financialdataindex++].split(',').join(), 
        false, FailureHandling.OPTIONAL)

    'verify return of asset %'
    WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(i, 16).split(',').join(), resultfinancialdataarray[financialdataindex++].split(',').join(), 
        false, FailureHandling.OPTIONAL)
	
	'verify profit margin %'
	WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(i, 17).split(',').join(), resultfinancialdataarray[financialdataindex++].split(',').join(),
		false, FailureHandling.OPTIONAL)
	
	'verify current ratio %'
	WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(i, 18).split(',').join(), resultfinancialdataarray[financialdataindex++].split(',').join(),
		false, FailureHandling.OPTIONAL)
	
	'verify debt equity ratio %'
	WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(i, 19).split(',').join(), resultfinancialdataarray[financialdataindex++].split(',').join(),
		false, FailureHandling.OPTIONAL)
	
	'verify inventory turn over %'
	WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(i, 20).split(',').join(), resultfinancialdataarray[financialdataindex++].split(',').join(),
		false, FailureHandling.OPTIONAL)
	
	'verify Account receiveable turn over %'
	WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(i, 21).split(',').join(), resultfinancialdataarray[financialdataindex++].split(',').join(),
		false, FailureHandling.OPTIONAL)
	
	'verify growth %'
	WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(i, 22).split(',').join(), resultfinancialdataarray[financialdataindex++].split(',').join(),
		false, FailureHandling.OPTIONAL)
	
	'verify working capital amount'
	WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(i, 23).split(',').join(), resultfinancialdataarray[financialdataindex++].split(',').join(),
		false, FailureHandling.OPTIONAL)
	
	'verify other monthly installment amount'
	WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(i, 24).split(',').join(), resultfinancialdataarray[financialdataindex++].split(',').join(),
		false, FailureHandling.OPTIONAL)
	
	'verify revenue'
	WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(i, 25).split(',').join(), resultfinancialdataarray[financialdataindex++].split(',').join(),
		false, FailureHandling.OPTIONAL)
	
	'verify operational cost'
	WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(i, 26).split(',').join(), resultfinancialdataarray[financialdataindex++].split(',').join(),
		false, FailureHandling.OPTIONAL)
	
	'verify profit before tax'
	WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(i, 27).split(',').join(), resultfinancialdataarray[financialdataindex++].split(',').join(),
		false, FailureHandling.OPTIONAL)
	
	'verify current asset'
	WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(i, 28).split(',').join(), resultfinancialdataarray[financialdataindex++].split(',').join(),
		false, FailureHandling.OPTIONAL)
	
	'verify nett fix asset'
	WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(i, 29).split(',').join(), resultfinancialdataarray[financialdataindex++].split(',').join(),
		false, FailureHandling.OPTIONAL)
	
	'verify total asset'
	WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(i, 30).split(',').join(), resultfinancialdataarray[financialdataindex++].split(',').join(),
		false, FailureHandling.OPTIONAL)
	
	'verify current liabilities'
	WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(i, 31).split(',').join(), resultfinancialdataarray[financialdataindex++].split(',').join(),
		false, FailureHandling.OPTIONAL)
	
	'verify long term liabilities'
	WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(i, 32).split(',').join(), resultfinancialdataarray[financialdataindex++].split(',').join(),
		false, FailureHandling.OPTIONAL)
	
	'verify shareholder equity'
	WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(i, 33).split(',').join(), resultfinancialdataarray[financialdataindex++].split(',').join(),
		false, FailureHandling.OPTIONAL)
	
	'verify current ratio'
	WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(i, 34).split(',').join(), resultfinancialdataarray[financialdataindex++].split(',').join(),
		false, FailureHandling.OPTIONAL)

    'verify date as of'
    WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(i, 35).toUpperCase(), (resultfinancialdataarray[financialdataindex++]), 
        false, FailureHandling.OPTIONAL)
	
	if(i == GlobalVariable.CopyAppColm){
		
		int row = 38
	for(finattr = 0; finattr < resultfinancialattrarray.size(); finattr++){
		
	'verify financial attr'
	WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(i, row++).split(',').join(), resultfinancialattrarray[finattr],
		false, FailureHandling.OPTIONAL)
	}
	
	continue
	}
}

for (i = GlobalVariable.CopyAppColm; i < (resultbankaccarray.size()/7) + GlobalVariable.CopyAppColm ; i++) {
	'verify bank code'
	WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(i, 66).toUpperCase(), (resultbankaccarray[bankacc++]).toUpperCase(),
		false, FailureHandling.OPTIONAL)

	'verify bank branch'
	WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(i, 68).toUpperCase(), (resultbankaccarray[bankacc++]).toUpperCase(),
		false, FailureHandling.OPTIONAL)

	'verify bank name'
	WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(i, 69).toUpperCase(), (resultbankaccarray[bankacc++]).toUpperCase(),
		false, FailureHandling.OPTIONAL)

	'verify bank acc no'
	WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(i, 70).toUpperCase(), (resultbankaccarray[bankacc++]).toUpperCase(),
		false, FailureHandling.OPTIONAL)

	'verify is default'
	WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(i, 71).toUpperCase(), (resultbankaccarray[bankacc++]).toUpperCase(),
		false, FailureHandling.OPTIONAL)

	'verify is active'
	WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(i, 72).toUpperCase(), (resultbankaccarray[bankacc++]).toUpperCase(),
		false, FailureHandling.OPTIONAL)
	
	'verify begining balance'
	WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(i, 73).split(',').join(), (resultbankaccarray[bankacc++].split(',').join()).toUpperCase(),
		false, FailureHandling.OPTIONAL)
	
	String bankstat = CustomKeywords.'dbconnection.CustomerDataVerif.NAP4FinancialCheckBankStatCompanyStoreData'(sqlconnection, appno, custname, GlobalVariable.findDataFile.getValue(i, 27))
	
	'verify bank statement yes/no'
	if(bankstat == null){
		continue
	}else if(bankstat.equalsIgnoreCase('true')){
		
		def montharray = GlobalVariable.findDataFile.getValue(i, 74).split(';',-1)
		
		def yeararray = GlobalVariable.findDataFile.getValue(i, 75).split(';',-1)
		
		def debittrscarray = GlobalVariable.findDataFile.getValue(i, 76).split(';',-1)
		
		def debitarray = GlobalVariable.findDataFile.getValue(i, 77).split(';',-1)
		
		def credittrscarray = GlobalVariable.findDataFile.getValue(i, 78).split(';',-1)
		
		def creditarray = GlobalVariable.findDataFile.getValue(i, 79).split(';',-1)
		
		String resultbankstatdata = CustomKeywords.'dbconnection.CustomerDataVerif.NAP4BankStatDataCompanyStoreData'(sqlconnection, appno, custname, GlobalVariable.findDataFile.getValue(i, 27))
		
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
			WebUI.verifyMatch(montharray[bankstatexcel].toUpperCase(), (resultbankstatdataarray[bankstatdb++].toUpperCase()),
				false, FailureHandling.OPTIONAL)
			
			'verify year'
			WebUI.verifyMatch(yeararray[bankstatexcel], (resultbankstatdataarray[bankstatdb++].toUpperCase()),
				false, FailureHandling.OPTIONAL)
			
			'verify debit transaction'
			WebUI.verifyMatch(debittrscarray[bankstatexcel], (resultbankstatdataarray[bankstatdb++].toUpperCase()),
				false, FailureHandling.OPTIONAL)
			
			'verify debit'
			WebUI.verifyMatch(debitarray[bankstatexcel].split(',').join(), (resultbankstatdataarray[bankstatdb++].toUpperCase().split(',').join()),
				false, FailureHandling.OPTIONAL)
			
			'verify credit transaction'
			WebUI.verifyMatch(credittrscarray[bankstatexcel], (resultbankstatdataarray[bankstatdb++].toUpperCase()),
				false, FailureHandling.OPTIONAL)
			
			'verify credit'
			WebUI.verifyMatch(creditarray[bankstatexcel].split(',').join(), (resultbankstatdataarray[bankstatdb++].split(',').join()),
				false, FailureHandling.OPTIONAL)
			
			
		}
		
	}

}

