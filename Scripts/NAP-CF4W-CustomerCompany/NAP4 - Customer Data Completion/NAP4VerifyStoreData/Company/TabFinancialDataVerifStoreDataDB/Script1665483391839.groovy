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

if (GlobalVariable.APPSTEP == 'CUSTOMER') {
		
	'get data excelPathCustDetail'
	excelPathCustDetail = 'NAP-'+ GlobalVariable.LOB +'-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/CustomerDetail'

} else if (GlobalVariable.APPSTEP == 'SHAREHOLDER COMPANY') {

	'get data excelPathCustDetail'
	excelPathCustDetail = 'NAP-'+ GlobalVariable.LOB +'-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderCompany/CustomerDetail'

} else if (GlobalVariable.APPSTEP == 'GUARANTOR COMPANY') {
	
	'get data excelPathCustDetail'
	excelPathCustDetail = 'NAP-'+ GlobalVariable.LOB +'-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/CustomerDetail'
	
}

'get count colm'
countcolm = findTestData(GlobalVariable.excelPath).getColumnNumbers()

ArrayList<Boolean> arrayMatch = new ArrayList<>()

'get appno from confins'
String appno = WebUI.getText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail/appnolabel'))

'get custname from confins'
String custname = WebUI.getText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail/CustomerNameDetail'))

for (index = GlobalVariable.NumofVerifStore; index <= (countcolm); index++) {
	if (findTestData(GlobalVariable.excelPath).getValue(index, 9).equalsIgnoreCase(findTestData(excelPathCustDetail).getValue(GlobalVariable.NumofVerifStore,
		12)) && findTestData(GlobalVariable.excelPath).getValue(index, 10).equalsIgnoreCase(findTestData(excelPathCustDetail).getValue(GlobalVariable.NumofVerifStore,
		13))) {
	if (findTestData(GlobalVariable.excelPath).getValue(index, 9).length() != 0 && findTestData(GlobalVariable.excelPath).getValue(index, 35).length() != 0) {
		
	'get financial data from db'
	ArrayList<String> resultfinancialdata = CustomKeywords.'dbConnection.CustomerDataVerif.NAP4FinancialDataCompanyStoreData'(sqlconnectionLOS, appno, custname, findTestData(GlobalVariable.excelPath).getValue(index, 35))
	
	'get financialattr from db'
	ArrayList<String> resultfinancialattr = CustomKeywords.'dbConnection.CustomerDataVerif.NAP4FinancialAttrCompanyStoreData'(sqlconnectionLOS, appno, custname)
	
	int financialdataindex = 0, financialattr = 0
	
	'verify monthly income'
	arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(index, 12).split(',').join(), resultfinancialdata[financialdataindex++].split(',').join(),
		false, FailureHandling.OPTIONAL))

	'verify monthly expense'
	arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(index, 13).split(',').join(), resultfinancialdata[financialdataindex++].split(',').join(),
		false, FailureHandling.OPTIONAL))

	'verify return of investment %'
	arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(index, 14).split(',').join(), resultfinancialdata[financialdataindex++].split(',').join(),
		false, FailureHandling.OPTIONAL))

	'verify return of equity %'
	arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(index, 15).split(',').join(), resultfinancialdata[financialdataindex++].split(',').join(),
		false, FailureHandling.OPTIONAL))

	'verify return of asset %'
	arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(index, 16).split(',').join(), resultfinancialdata[financialdataindex++].split(',').join(),
		false, FailureHandling.OPTIONAL))
	
	'verify profit margin %'
	arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(index, 17).split(',').join(), resultfinancialdata[financialdataindex++].split(',').join(),
		false, FailureHandling.OPTIONAL))
	
	'verify current ratio %'
	arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(index, 18).split(',').join(), resultfinancialdata[financialdataindex++].split(',').join(),
		false, FailureHandling.OPTIONAL))
	
	'verify debt equity ratio %'
	arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(index, 19).split(',').join(), resultfinancialdata[financialdataindex++].split(',').join(),
		false, FailureHandling.OPTIONAL))
	
	'verify inventory turn over %'
	arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(index, 20).split(',').join(), resultfinancialdata[financialdataindex++].split(',').join(),
		false, FailureHandling.OPTIONAL))
	
	'verify Account receiveable turn over %'
	arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(index, 21).split(',').join(), resultfinancialdata[financialdataindex++].split(',').join(),
		false, FailureHandling.OPTIONAL))
	
	'verify growth %'
	arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(index, 22).split(',').join(), resultfinancialdata[financialdataindex++].split(',').join(),
		false, FailureHandling.OPTIONAL))
	
	'verify working capital amount'
	arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(index, 23).split(',').join(), resultfinancialdata[financialdataindex++].split(',').join(),
		false, FailureHandling.OPTIONAL))
	
	'verify other monthly installment amount'
	arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(index, 24).split(',').join(), resultfinancialdata[financialdataindex++].split(',').join(),
		false, FailureHandling.OPTIONAL))
	
	'verify revenue'
	arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(index, 25).split(',').join(), resultfinancialdata[financialdataindex++].split(',').join(),
		false, FailureHandling.OPTIONAL))
	
	'verify operational cost'
	arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(index, 26).split(',').join(), resultfinancialdata[financialdataindex++].split(',').join(),
		false, FailureHandling.OPTIONAL))
	
	'verify profit before tax'
	arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(index, 27).split(',').join(), resultfinancialdata[financialdataindex++].split(',').join(),
		false, FailureHandling.OPTIONAL))
	
	'verify current asset'
	arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(index, 28).split(',').join(), resultfinancialdata[financialdataindex++].split(',').join(),
		false, FailureHandling.OPTIONAL))
	
	'verify nett fix asset'
	arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(index, 29).split(',').join(), resultfinancialdata[financialdataindex++].split(',').join(),
		false, FailureHandling.OPTIONAL))
	
	'verify total asset'
	arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(index, 30).split(',').join(), resultfinancialdata[financialdataindex++].split(',').join(),
		false, FailureHandling.OPTIONAL))
	
	'verify current liabilities'
	arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(index, 31).split(',').join(), resultfinancialdata[financialdataindex++].split(',').join(),
		false, FailureHandling.OPTIONAL))
	
	'verify long term liabilities'
	arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(index, 32).split(',').join(), resultfinancialdata[financialdataindex++].split(',').join(),
		false, FailureHandling.OPTIONAL))
	
	'verify shareholder equity'
	arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(index, 33).split(',').join(), resultfinancialdata[financialdataindex++].split(',').join(),
		false, FailureHandling.OPTIONAL))
	
	'verify current ratio'
	arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(index, 34).split(',').join(), resultfinancialdata[financialdataindex++].split(',').join(),
		false, FailureHandling.OPTIONAL))

	'verify date as of'
	arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(index, 35).toUpperCase(), (resultfinancialdata[financialdataindex++]),
		false, FailureHandling.OPTIONAL))
	
	if(index == GlobalVariable.NumofVerifStore){
		
		int row = 38
	for(finattr = 0; finattr < resultfinancialattr.size(); finattr++){
		
	'verify financial attr'
	arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(index, row++).split(',').join(), resultfinancialattr[finattr],
		false, FailureHandling.OPTIONAL))
	}
	
	continue
	}
	}
	}else{
	break
	}
}

for (index = GlobalVariable.NumofVerifStore; index <= (countcolm); index++) {
	if (findTestData(GlobalVariable.excelPath).getValue(index, 9).equalsIgnoreCase(findTestData(excelPathCustDetail).getValue(GlobalVariable.NumofVerifStore,
		12)) && findTestData(GlobalVariable.excelPath).getValue(index, 10).equalsIgnoreCase(findTestData(excelPathCustDetail).getValue(GlobalVariable.NumofVerifStore,
		13))) {
	if (findTestData(GlobalVariable.excelPath).getValue(index, 10).length() != 0 && findTestData(GlobalVariable.excelPath).getValue(index, 70).length() != 0) {
	'get bankacc from db'
	ArrayList<String> resultbankacc = CustomKeywords.'dbConnection.CustomerDataVerif.NAP4FinDataBankAccCompanyStoreData'(sqlconnectionLOS, appno, custname, findTestData(GlobalVariable.excelPath).getValue(index, 70))
	
	int bankacc = 0
	
	'verify bank code'
	arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(index, 66).toUpperCase(), (resultbankacc[bankacc++]).toUpperCase(),
		false, FailureHandling.OPTIONAL))

	'verify bank branch'
	arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(index, 68).toUpperCase(), (resultbankacc[bankacc++]).toUpperCase(),
		false, FailureHandling.OPTIONAL))

	'verify bank name'
	arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(index, 69).toUpperCase(), (resultbankacc[bankacc++]).toUpperCase(),
		false, FailureHandling.OPTIONAL))

	'verify bank acc no'
	arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(index, 70).toUpperCase(), (resultbankacc[bankacc++]).toUpperCase(),
		false, FailureHandling.OPTIONAL))

	'verify is default'
	arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(index, 71).toUpperCase(), (resultbankacc[bankacc++]).toUpperCase(),
		false, FailureHandling.OPTIONAL))

	'verify is active'
	arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(index, 72).toUpperCase(), (resultbankacc[bankacc++]).toUpperCase(),
		false, FailureHandling.OPTIONAL))
	
	'verify begining balance'
	arrayMatch.add(WebUI.verifyMatch(findTestData(GlobalVariable.excelPath).getValue(index, 73).split(',').join(), (resultbankacc[bankacc++].split(',').join()).toUpperCase(),
		false, FailureHandling.OPTIONAL))
	
	'get bankstat from db'
	String bankstat = CustomKeywords.'dbConnection.CustomerDataVerif.NAP4FinancialCheckBankStatCompanyStoreData'(sqlconnectionLOS, appno, custname, findTestData(GlobalVariable.excelPath).getValue(index, 27))
	
	'verify bank statement yes/no'
	if(bankstat == null){
		continue
	}else if(bankstat.equalsIgnoreCase('true')){
		
		def montharray = findTestData(GlobalVariable.excelPath).getValue(index, 74).split(';',-1)
		
		def yeararray = findTestData(GlobalVariable.excelPath).getValue(index, 75).split(';',-1)
		
		def debittrscarray = findTestData(GlobalVariable.excelPath).getValue(index, 76).split(';',-1)
		
		def debitarray = findTestData(GlobalVariable.excelPath).getValue(index, 77).split(';',-1)
		
		def credittrscarray = findTestData(GlobalVariable.excelPath).getValue(index, 78).split(';',-1)
		
		def creditarray = findTestData(GlobalVariable.excelPath).getValue(index, 79).split(';',-1)
		
		'get bankstatdata from db'
		ArrayList<String> resultbankstatdata = CustomKeywords.'dbConnection.CustomerDataVerif.NAP4BankStatDataCompanyStoreData'(sqlconnectionLOS, appno, custname, findTestData(GlobalVariable.excelPath).getValue(index, 27))
		
		'ganti value null > "" (String kosong)'
		for (i = 0; i <= (resultbankstatdata.size() - 1); i++) {
			if ((resultbankstatdata[i]).equalsIgnoreCase('0')) {
				(resultbankstatdata[i]) = ''
			}
		}
		
		int bankstatdb = 0
		
		for(bankstatexcel = 0; bankstatexcel < resultbankstatdata.size()/6 ; bankstatexcel++){
			'verify month'
			arrayMatch.add(WebUI.verifyMatch(montharray[bankstatexcel].toUpperCase(), (resultbankstatdata[bankstatdb++].toUpperCase()),
				false, FailureHandling.OPTIONAL))
			
			'verify year'
			arrayMatch.add(WebUI.verifyMatch(yeararray[bankstatexcel], (resultbankstatdata[bankstatdb++].toUpperCase()),
				false, FailureHandling.OPTIONAL))
			
			'verify debit transaction'
			arrayMatch.add(WebUI.verifyMatch(debittrscarray[bankstatexcel], (resultbankstatdata[bankstatdb++].toUpperCase()),
				false, FailureHandling.OPTIONAL))
			
			'verify debit'
			arrayMatch.add(WebUI.verifyMatch(debitarray[bankstatexcel].split(',').join(), (resultbankstatdata[bankstatdb++].toUpperCase().split(',').join()),
				false, FailureHandling.OPTIONAL))
			
			'verify credit transaction'
			arrayMatch.add(WebUI.verifyMatch(credittrscarray[bankstatexcel], (resultbankstatdata[bankstatdb++].toUpperCase()),
				false, FailureHandling.OPTIONAL))
			
			'verify credit'
			arrayMatch.add(WebUI.verifyMatch(creditarray[bankstatexcel].split(',').join(), (resultbankstatdata[bankstatdb++].split(',').join()),
				false, FailureHandling.OPTIONAL))
			
			
		}
		
	}
	}
	}else{
	break
	}
}

'jika nilai di confins tidak sesuai dengan db'
if(arrayMatch.contains(false)){
	
	'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedStoredDB'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('4.FinancialData', GlobalVariable.NumofVerifStore, GlobalVariable.StatusFailed, GlobalVariable.ReasonFailedStoredDB)
}