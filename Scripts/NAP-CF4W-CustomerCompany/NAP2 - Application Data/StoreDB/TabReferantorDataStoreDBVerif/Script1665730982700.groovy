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

'connect DB'
Sql sqlconnection = CustomKeywords.'dbConnection.connectDB.connectLOS'()

'declare excelPathCustomerCompany'
excelPathCustomerCompany = 'NAP-'+ GlobalVariable.LOB +'-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData'

'declare excelPathReferantor'
excelPathReferantor = 'NAP-'+ GlobalVariable.LOB +'-CustomerCompany/NAP2-ApplicationData/TabReferantorData'

def modifyObjectCustName

if(GlobalVariable.LOB == 'CF4W'){
	'modify CustName'
	modifyObjectCustName = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/label_CustName'),
			'xpath', 'equals', ('//*[@id="NewApplication"]/div/div[5]/span/div/div[2]/span/label'), true)

}else if(GlobalVariable.LOB == 'FL4W'){
	'modify CustName'
	modifyObjectCustName = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/label_CustName'),
			'xpath', 'equals', ('//*[@id="NewApplication"]/div/div[4]/span/div/div[2]/span/label'), true)
}

'get custname from confins'
custname = WebUI.getText(modifyObjectCustName)

ArrayList<String> vat = new ArrayList<String>()

vat = GlobalVariable.ReferantorVAT

'call keyword NAP2TabReferantorStoreDB dari DB'
ArrayList<String> result = CustomKeywords.'dbConnection.CustomerDataVerif.NAP2TabReferantorStoreDB'(sqlconnection, findTestData(excelPathCustomerCompany).getValue(
        GlobalVariable.NumofColm, 13), custname)

ArrayList<String> arrayMatch = new ArrayList<String>()

int arrayindex = 0

int bankindex = 0

int vatindex = 0

def bankaccount = GlobalVariable.BankAccount.split(' - ')

for (GlobalVariable.NumofReferantor = GlobalVariable.StartIndex; GlobalVariable.NumofReferantor < ((result.size() / 7) + 
GlobalVariable.StartIndex); (GlobalVariable.NumofReferantor)++) {
	
    refcategory = (result[arrayindex++])
	refname = (result[arrayindex++])
	
    for (int excelindex = GlobalVariable.StartIndex; GlobalVariable.NumofReferantor < (findTestData(excelPathReferantor).getColumnNumbers() - 
    1); excelindex++) {
        if (findTestData(excelPathReferantor).getValue(excelindex, 12) == findTestData(excelPathCustomerCompany).getValue(GlobalVariable.NumofColm, 13)) {
			
            if (findTestData(excelPathReferantor).getValue(excelindex, 13).equalsIgnoreCase(refcategory) && findTestData(excelPathReferantor).getValue(excelindex, 14).equalsIgnoreCase(refname)) {
                'verify referantor category'
                arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathReferantor).getValue(excelindex, 13).toUpperCase(), refcategory.toUpperCase(), 
                        false, FailureHandling.OPTIONAL))

                'verify referantor name'
                arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathReferantor).getValue(excelindex, 14).toUpperCase(), (refname).toUpperCase(), 
                        false, FailureHandling.OPTIONAL))

                'verify referantor bank acc'
                arrayMatch.add(WebUI.verifyMatch((bankaccount[bankindex++]).toUpperCase(), (result[arrayindex++]).toUpperCase(), 
                        false, FailureHandling.OPTIONAL))

                'verify referantor bank acc'
                arrayMatch.add(WebUI.verifyMatch((bankaccount[bankindex++]).toUpperCase(), (result[arrayindex++]).toUpperCase(), 
                        false, FailureHandling.OPTIONAL))

                'verify referantor bank acc'
                arrayMatch.add(WebUI.verifyMatch((bankaccount[bankindex++]).toUpperCase(), (result[arrayindex++]).toUpperCase(), 
                        false, FailureHandling.OPTIONAL))

                'verify referantor tax calculation method'
                arrayMatch.add(WebUI.verifyMatch(findTestData(excelPathReferantor).getValue(GlobalVariable.NumofReferantor, 17).toUpperCase(), 
                        (result[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

                arrayMatch.add(WebUI.verifyMatch(vat.get(vatindex++).toString(), (result[arrayindex++]).toString(), false, 
                        FailureHandling.OPTIONAL))

                break
            } else {
                continue
            }
        } else {
		if (findTestData(excelPathReferantor).getValue(excelindex+1, 12) != findTestData(excelPathCustomerCompany).getValue(GlobalVariable.NumofColm, 13)) {
			arrayindex = 0
            break
		}
        }
    }
}

'Jika nilai di confins ada yang tidak sesuai dengan db'
if (arrayMatch.contains(false)) {
    'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedStoredDB'
    CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('5.TabReferantorData', GlobalVariable.StartIndex, 
        GlobalVariable.StatusFailed, findTestData(excelPathReferantor).getValue(GlobalVariable.NumofColm, 2) + ';' + GlobalVariable.ReasonFailedStoredDB)
}

