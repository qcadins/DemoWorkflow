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
import internal.GlobalVariable as GlobalVariable

'declare datafileCustomerPersonal'
datafileCustomerPersonal = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData')

'declare datafileCommission'
datafileCommission = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/CommissionReservedFund/TabCommissionData')

'declare datafileReservedFund'
datafileReservedFund = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/CommissionReservedFund/TabReservedFundData')

inputAppNo()

'Mengambil nilai row keberapa dimulai data return pada excel'
def returnRowCom = CustomKeywords.'customizeKeyword.getRow.getExcelRow'(GlobalVariable.DataFilePath, '13.TabCommissionData', 
    'Return Commission & Reserved Fund') + 1

'Mengambil nilai row keberapa dimulai data return pada excel'
def returnRowRsv = CustomKeywords.'customizeKeyword.getRow.getExcelRow'(GlobalVariable.DataFilePath, '14.TabReservedFundData', 
    'Return Commission & Reserved Fund') + 1

if (datafileCommission.getValue(GlobalVariable.NumofColm, returnRowCom + 1).equalsIgnoreCase('Yes') || datafileReservedFund.getValue(
    GlobalVariable.NumofColm, returnRowRsv + 1).equalsIgnoreCase('Yes')) {
	'verify match status request pada paging return handling'
	WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/NAP/ReturnHandling/TaskStatusPaging')), 'REQUEST', false)

	'klik icon pensil untuk select'
	WebUI.click(findTestObject('NAP/CommissionReservedFund/TabCommissionData/i_Select'))

	'verify match task status request'
    WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/NAP/ReturnHandling/TaskStatus')), 'REQUEST', false)

	'call test case edit application data '
    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/ReturnHandling/EditApplicationData'), [:], FailureHandling.STOP_ON_FAILURE)

    inputAppNo()
	
	'Looping delay +-20detik'
	for(int i = 1;i<=4;i++){
		'klik search'
		WebUI.click(findTestObject('NAP/CommissionReservedFund/TabCommissionData/button_Search'))
		
		'Pengecekan kolom status bernilai done'
		if(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/NAP/ReturnHandling/TaskStatusPaging')), 'DONE', false,FailureHandling.OPTIONAL)){
			break
		}
		else{
			'delay 4 detik'
			WebUI.delay(4)
		}
	}

	'klik icon pensil untuk select'
	WebUI.click(findTestObject('NAP/CommissionReservedFund/TabCommissionData/i_Select'))
	
    WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/NAP/ReturnHandling/TaskStatus')), 'DONE', false)
}

'klik save'
WebUI.click(findTestObject('Object Repository/NAP/ReturnHandling/button_Save'))

def inputAppNo() {
	if(WebUI.verifyElementNotVisible(findTestObject('Object Repository/NAP/ReturnHandling/MenuReturnHandling'), FailureHandling.OPTIONAL)){
		'Klik extend menu return handling'
		WebUI.click(findTestObject('Object Repository/NAP/ReturnHandling/ExtendReturnHandling'))
	}
   

    'klik return handling'
    WebUI.click(findTestObject('Object Repository/NAP/ReturnHandling/MenuReturnHandling'))

    'input application no'
    WebUI.setText(findTestObject('NAP/CommissionReservedFund/TabCommissionData/input_Application No_AppNoId'), datafileCustomerPersonal.getValue(
            GlobalVariable.NumofColm, 13))

    'klik search'
    WebUI.click(findTestObject('NAP/CommissionReservedFund/TabCommissionData/button_Search'))

}

