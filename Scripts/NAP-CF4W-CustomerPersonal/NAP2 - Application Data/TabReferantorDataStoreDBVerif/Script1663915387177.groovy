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

'connect DB los'
Sql sqlconnection = CustomKeywords.'dbConnection.connectDB.connectLOS'()

'declare datafileReferantor'
datafileReferantor = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabReferantorData')

'get text custname'
custname = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/label_CustName'))

'get referantor data from db'
ArrayList<String> result = CustomKeywords.'dbConnection.CustomerDataVerif.NAP2TabReferantorStoreDB'(sqlconnection, datafileReferantor.getValue(
		GlobalVariable.StartIndex, 12), custname)

'declare arraymatch'
ArrayList<Boolean> arrayMatch = new ArrayList<>()

ArrayList<Boolean> vat = new ArrayList<>()
vat = GlobalVariable.ReferantorVAT
	
'declare arrayindex, bankindex'
int arrayindex = 0, bankindex = 0, vatindex = 0

'declare bankaccount'
def bankaccount = GlobalVariable.BankAccount.split(' - ')

'looping referantor data verif'
for (GlobalVariable.NumofReferantor = GlobalVariable.StartIndex; GlobalVariable.NumofReferantor < result.size()/7 + GlobalVariable.StartIndex; (GlobalVariable.NumofReferantor)++) {
    arrayMatch.add(WebUI.verifyMatch(datafileReferantor.getValue(
            GlobalVariable.NumofReferantor, 13).toUpperCase(), (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

    arrayMatch.add(WebUI.verifyMatch(datafileReferantor.getValue(
            GlobalVariable.NumofReferantor, 14).toUpperCase(), (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

    arrayMatch.add(WebUI.verifyMatch((bankaccount[bankindex++]).toUpperCase(), (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	
	arrayMatch.add(WebUI.verifyMatch((bankaccount[bankindex++]).toUpperCase(), (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	
	arrayMatch.add(WebUI.verifyMatch((bankaccount[bankindex++]).toUpperCase(), (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

    arrayMatch.add(WebUI.verifyMatch(datafileReferantor.getValue(
            GlobalVariable.NumofReferantor, 17).toUpperCase(), (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
	
	arrayMatch.add(WebUI.verifyMatch(vat.get(vatindex++).toString(), (result[arrayindex++]).toString(), false, FailureHandling.OPTIONAL))
}

'Jika nilai di confins ada yang tidak sesuai dengan db'
if (arrayMatch.contains(false)) {
		
	'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedStoredDB'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('5.TabReferantorData', GlobalVariable.StartIndex, GlobalVariable.StatusFailed, GlobalVariable.ReasonFailedStoredDB)

}