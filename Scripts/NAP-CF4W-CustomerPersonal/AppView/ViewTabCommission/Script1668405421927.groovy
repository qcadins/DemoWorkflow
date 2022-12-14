import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import org.openqa.selenium.By as By
import org.openqa.selenium.WebDriver as WebDriver
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import groovy.sql.Sql as Sql
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.WebElement as WebElement

'get data file path'
GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'(GlobalVariable.PathAppInquiryPersonal)

GlobalVariable.FlagWarning = 0

'connect DB LOS'
Sql sqlconnection = CustomKeywords.'dbConnection.connectDB.connectLOS'()

'connect DB FOU'
Sql sqlconnectionFOU = CustomKeywords.'dbConnection.connectDB.connectFOU'()

'Klik tab commission'
WebUI.click(findTestObject('Object Repository/AppView/Commission/Commission Tab'))

'Verif tidak ada alert yang muncul'
if(WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerPersonal/div_erroralert'), GlobalVariable.TimeOut)==false){
	GlobalVariable.FlagWarning = 1
	'write to excel status warning'
	CustomKeywords.'checkSaveProcess.checkSaveProcess.writeWarningAppView'(GlobalVariable.NumofColm,'11. Commission')
}

'get appno from confins'
appno = WebUI.getText(findTestObject('Object Repository/AppView/MainInformation/Label App No'))

'get commission data arraylist from db'
HashMap<String,ArrayList> resultCom = CustomKeywords.'appView.verifyAppView.checkCommissionData'(sqlconnection, appno, sqlconnectionFOU)
ArrayList<String> ComSupp = resultCom.get("ComSupp")
ArrayList<String> ComSuppEmp = resultCom.get("ComSuppEmp")
ArrayList<String> ComRef = resultCom.get("ComRef")
ArrayList<String> listSumm = CustomKeywords.'appView.verifyAppView.checkSummaryCommission'(sqlconnection, appno)
ArrayList<String> AllocCom = new ArrayList<>()

//Check Section Supplier Commission
Suppindex = 1

'Looping db commission supplier'
for (dbindex = 0; dbindex < ComSupp.size(); dbindex++) {
	
	if(dbindex%10==0){
		'get allocate commission amount supplier from db'
		AllocCom = CustomKeywords.'appView.verifyAppView.checkCommissionAllocation'(sqlconnection,ComSupp[dbindex])
		
		'looping allocate commission amount supplier'
		for(int i=0;i<AllocCom.size();i++){
			'modify object commission data supplier'
			modifyNewAllocCom = WebUI.modifyObjectProperty(findTestObject('AppView/Commission/ModifyAllocateSupplier'), 'xpath', 'equals', "//*[@id='formInformationSupplier']/div/div["+Suppindex+"]/div[3]/div["+(i+1)+"]/div/div[2]/label", true)
			'verify allocate amount supplier'
			checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewAllocCom).replace(",",""), AllocCom[i],
					false))
		}
		
		continue
	}
	'modify object commission data supplier'
	modifyNewComData = WebUI.modifyObjectProperty(findTestObject('AppView/Commission/ModifyComDataSupp'), 'xpath', 'equals', "//*[@id='formInformationSupplier']/div/div["+Suppindex+"]/div[1]/label", true)

	'verify supplierName'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewComData).toUpperCase(), (ComSupp[dbindex]).toUpperCase(),
			false))

	dbindex++
	
	'modify object commission data supplier'
	modifyNewComData = WebUI.modifyObjectProperty(findTestObject('AppView/Commission/ModifyComDataSupp'), 'xpath', 'equals', "//*[@id='formInformationSupplier']/div/div["+Suppindex+"]/div[2]/div[1]/div[2]/label", true)

	'verify commission amt'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewComData).toUpperCase().replace(",",""), (ComSupp[dbindex]).toUpperCase(),
			false))

	dbindex++
	
	'modify object commission data supplier'
	modifyNewComData = WebUI.modifyObjectProperty(findTestObject('AppView/Commission/ModifyComDataSupp'), 'xpath', 'equals', "//*[@id='formInformationSupplier']/div/div["+Suppindex+"]/div[2]/div[2]/div[2]/label", true)
	
	'verify tax base amt'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewComData).toUpperCase().replace(",",""), (ComSupp[dbindex]).toUpperCase(),
			false))

	dbindex++
	
	'modify object commission data supplier'
	modifyNewComData = WebUI.modifyObjectProperty(findTestObject('AppView/Commission/ModifyComDataSupp'), 'xpath', 'equals', "//*[@id='formInformationSupplier']/div/div["+Suppindex+"]/div[2]/div[3]/div[2]/label", true)
	
	'verify disburse amt'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewComData).toUpperCase().replace(",",""), (ComSupp[dbindex]).toUpperCase(),
			false))

	dbindex++
	
	'modify object commission data supplier'
	modifyNewComData = WebUI.modifyObjectProperty(findTestObject('AppView/Commission/ModifyComDataSupp'), 'xpath', 'equals', "//*[@id='formInformationSupplier']/div/div["+Suppindex+"]/div[2]/div[4]/div[2]/label", true)
	
	'verify expense amt'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewComData).toUpperCase().replace(",",""), (ComSupp[dbindex]).toUpperCase(),
			false))

	dbindex++
	
	'modify object commission data supplier'
	modifyNewComData = WebUI.modifyObjectProperty(findTestObject('AppView/Commission/ModifyComDataSupp'), 'xpath', 'equals', "//*[@id='formInformationSupplier']/div/div["+Suppindex+"]/div[2]/div[5]/div[2]/label", true)
	
	'verify tax amt'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewComData).toUpperCase().replace(",",""), (ComSupp[dbindex]).toUpperCase(),
			false))

	dbindex++
	
	'modify object commission data supplier'
	modifyNewComData = WebUI.modifyObjectProperty(findTestObject('AppView/Commission/ModifyComDataSupp'), 'xpath', 'equals', "//*[@id='formInformationSupplier']/div/div["+Suppindex+"]/div[2]/div[6]/div[2]/label", true)
	
	'verify vat amt'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewComData).toUpperCase().replace(",",""), (ComSupp[dbindex]).toUpperCase(),
			false))

	dbindex++
	
	'modify object commission data supplier'
	modifyNewComData = WebUI.modifyObjectProperty(findTestObject('AppView/Commission/ModifyComDataSupp'), 'xpath', 'equals', "//*[@id='formInformationSupplier']/div/div["+Suppindex+"]/div[2]/div[7]/div[2]/label", true)
	
	'verify calculation method'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewComData).toUpperCase(), (ComSupp[dbindex]).toUpperCase(),
			false))

	dbindex++
	
	'modify object commission data supplier'
	modifyNewComData = WebUI.modifyObjectProperty(findTestObject('AppView/Commission/ModifyComDataSupp'), 'xpath', 'equals', "//*[@id='formInformationSupplier']/div/div["+Suppindex+"]/div[2]/div[8]/div[2]/label", true)
	
	'verify bank account'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewComData).toUpperCase(), (ComSupp[dbindex]).toUpperCase(),
			false))

	Suppindex++
}

//Check Section Supplier Employee Commission
SuppEmpindex = 1

'looping data commission suppplier employee from db'
for (dbindex = 0; dbindex < ComSuppEmp.size(); dbindex++) {
	
	if(dbindex%12==0){
		'get allocate commision amount supplier employee from db'
		AllocCom = CustomKeywords.'appView.verifyAppView.checkCommissionAllocation'(sqlconnection,ComSuppEmp[dbindex])
		'looping allocate commission amount supplier employee'
		for(int i=0;i<AllocCom.size();i++){
			'modify object commission data supplier employee'
			modifyNewAllocCom = WebUI.modifyObjectProperty(findTestObject('AppView/Commission/ModifyAllocateSupplierEmp'), 'xpath', 'equals', "//*[@id='formInformationSupplierEmployee']/div/div["+SuppEmpindex+"]/div[3]/div["+(i+1)+"]/div/div[2]/label", true)
			'verify allocate amount supplier employee'
			checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewAllocCom).replace(",",""), AllocCom[i],
					false))
		}
		
		continue
	}
	'modify object commission data supplier employee'
	modifyNewComData = WebUI.modifyObjectProperty(findTestObject('AppView/Commission/ModifyComDataSuppEmp'), 'xpath', 'equals', "//*[@id='formInformationSupplierEmployee']/div/div["+SuppEmpindex+"]/div[1]/label", true)

	'verify supplierEmpName'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewComData).toUpperCase(), (ComSuppEmp[dbindex]).toUpperCase(),
			false))

	dbindex++
	
	'modify object commission data supplier employee'
	modifyNewComData = WebUI.modifyObjectProperty(findTestObject('AppView/Commission/ModifyComDataSuppEmp'), 'xpath', 'equals', "//*[@id='formInformationSupplierEmployee']/div/div["+SuppEmpindex+"]/div[2]/div[1]/div[2]/label", true)

	'verify supplierName'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewComData).toUpperCase().replace(",",""), (ComSuppEmp[dbindex]).toUpperCase(),
			false))

	dbindex++
	
	'modify object commission data supplier employee'
	modifyNewComData = WebUI.modifyObjectProperty(findTestObject('AppView/Commission/ModifyComDataSuppEmp'), 'xpath', 'equals', "//*[@id='formInformationSupplierEmployee']/div/div["+SuppEmpindex+"]/div[2]/div[2]/div[2]/label", true)

	'verify JobPosition'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewComData).toUpperCase().replace(",",""), (ComSuppEmp[dbindex]).toUpperCase(),
			false))

	dbindex++
	
	'modify object commission data supplier employee'
	modifyNewComData = WebUI.modifyObjectProperty(findTestObject('AppView/Commission/ModifyComDataSuppEmp'), 'xpath', 'equals', "//*[@id='formInformationSupplierEmployee']/div/div["+SuppEmpindex+"]/div[2]/div[3]/div[2]/label", true)

	'verify commission amt'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewComData).toUpperCase().replace(",",""), (ComSuppEmp[dbindex]).toUpperCase(),
			false))

	dbindex++
	
	'modify object commission data supplier employee'
	modifyNewComData = WebUI.modifyObjectProperty(findTestObject('AppView/Commission/ModifyComDataSuppEmp'), 'xpath', 'equals', "//*[@id='formInformationSupplierEmployee']/div/div["+SuppEmpindex+"]/div[2]/div[4]/div[2]/label", true)
	
	'verify tax base amt'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewComData).toUpperCase().replace(",",""), (ComSuppEmp[dbindex]).toUpperCase(),
			false))

	dbindex++
	
	'modify object commission data supplier employee'
	modifyNewComData = WebUI.modifyObjectProperty(findTestObject('AppView/Commission/ModifyComDataSuppEmp'), 'xpath', 'equals', "//*[@id='formInformationSupplierEmployee']/div/div["+SuppEmpindex+"]/div[2]/div[5]/div[2]/label", true)
	
	'verify disburse amt'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewComData).toUpperCase().replace(",",""), (ComSuppEmp[dbindex]).toUpperCase(),
			false))

	dbindex++
	
	'modify object commission data supplier employee'
	modifyNewComData = WebUI.modifyObjectProperty(findTestObject('AppView/Commission/ModifyComDataSuppEmp'), 'xpath', 'equals', "//*[@id='formInformationSupplierEmployee']/div/div["+SuppEmpindex+"]/div[2]/div[6]/div[2]/label", true)
	
	'verify expense amt'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewComData).toUpperCase().replace(",",""), (ComSuppEmp[dbindex]).toUpperCase(),
			false))

	dbindex++
	
	'modify object commission data supplier employee'
	modifyNewComData = WebUI.modifyObjectProperty(findTestObject('AppView/Commission/ModifyComDataSuppEmp'), 'xpath', 'equals', "//*[@id='formInformationSupplierEmployee']/div/div["+SuppEmpindex+"]/div[2]/div[7]/div[2]/label", true)
	
	'verify tax amt'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewComData).toUpperCase().replace(",",""), (ComSuppEmp[dbindex]).toUpperCase(),
			false))

	dbindex++
	
	'modify object commission data supplier employee'
	modifyNewComData = WebUI.modifyObjectProperty(findTestObject('AppView/Commission/ModifyComDataSuppEmp'), 'xpath', 'equals', "//*[@id='formInformationSupplierEmployee']/div/div["+SuppEmpindex+"]/div[2]/div[8]/div[2]/label", true)
	
	'verify vat amt'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewComData).toUpperCase().replace(",",""), (ComSuppEmp[dbindex]).toUpperCase(),
			false))

	dbindex++
	
	'modify object commission data supplier employee'
	modifyNewComData = WebUI.modifyObjectProperty(findTestObject('AppView/Commission/ModifyComDataSuppEmp'), 'xpath', 'equals', "//*[@id='formInformationSupplierEmployee']/div/div["+SuppEmpindex+"]/div[2]/div[9]/div[2]/label", true)
	
	'verify calculation method'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewComData).toUpperCase(), (ComSuppEmp[dbindex]).toUpperCase(),
			false))

	dbindex++
	
	'modify object commission data supplier employee'
	modifyNewComData = WebUI.modifyObjectProperty(findTestObject('AppView/Commission/ModifyComDataSuppEmp'), 'xpath', 'equals', "//*[@id='formInformationSupplierEmployee']/div/div["+SuppEmpindex+"]/div[2]/div[10]/div[2]/label", true)
	
	'verify bank account'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewComData).toUpperCase(), (ComSuppEmp[dbindex]).toUpperCase(),
			false))

	SuppEmpindex++
}

//Check Section Referantor Commission
Refindex = 1

'looping data commission referantor from db'
for (dbindex = 0; dbindex < ComRef.size(); dbindex++) {
	
	if(dbindex%10==0){
		'get allocate commission referantor amount from db'
		AllocCom = CustomKeywords.'appView.verifyAppView.checkCommissionAllocation'(sqlconnection,ComRef[dbindex])
		'looping allocate commission referantor amount'
		for(int i=0;i<AllocCom.size();i++){
			'modify object commission data referantor'
			modifyNewAllocCom = WebUI.modifyObjectProperty(findTestObject('AppView/Commission/ModifyAllocateRef'), 'xpath', 'equals', "//*[@id='formInformationReferantor']/div/div["+Refindex+"]/div[3]/div["+(i+1)+"]/div/div[2]/label", true)
			'verify allocate amount'
			checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewAllocCom).replace(",",""), AllocCom[i],
					false))
		}
		
		continue
	}
	'modify object commission data referantor'
	modifyNewComData = WebUI.modifyObjectProperty(findTestObject('AppView/Commission/ModifyComDataRef'), 'xpath', 'equals', "//*[@id='formInformationReferantor']/div/div["+Refindex+"]/div[1]/label", true)

	'verify supplierName'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewComData).toUpperCase(), (ComRef[dbindex]).toUpperCase(),
			false))

	dbindex++
	
	'modify object commission data referantor'
	modifyNewComData = WebUI.modifyObjectProperty(findTestObject('AppView/Commission/ModifyComDataRef'), 'xpath', 'equals', "//*[@id='formInformationReferantor']/div/div["+Refindex+"]/div[2]/div[1]/div[2]/label", true)

	'verify commission amt'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewComData).toUpperCase().replace(",",""), (ComRef[dbindex]).toUpperCase(),
			false))

	dbindex++
	
	'modify object commission data referantor'
	modifyNewComData = WebUI.modifyObjectProperty(findTestObject('AppView/Commission/ModifyComDataRef'), 'xpath', 'equals', "//*[@id='formInformationReferantor']/div/div["+Refindex+"]/div[2]/div[2]/div[2]/label", true)
	
	'verify tax base amt'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewComData).toUpperCase().replace(",",""), (ComRef[dbindex]).toUpperCase(),
			false))

	dbindex++
	
	'modify object commission data referantor'
	modifyNewComData = WebUI.modifyObjectProperty(findTestObject('AppView/Commission/ModifyComDataRef'), 'xpath', 'equals', "//*[@id='formInformationReferantor']/div/div["+Refindex+"]/div[2]/div[3]/div[2]/label", true)
	
	'verify disburse amt'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewComData).toUpperCase().replace(",",""), (ComRef[dbindex]).toUpperCase(),
			false))

	dbindex++
	
	'modify object commission data referantor'
	modifyNewComData = WebUI.modifyObjectProperty(findTestObject('AppView/Commission/ModifyComDataRef'), 'xpath', 'equals', "//*[@id='formInformationReferantor']/div/div["+Refindex+"]/div[2]/div[4]/div[2]/label", true)
	
	'verify expense amt'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewComData).toUpperCase().replace(",",""), (ComRef[dbindex]).toUpperCase(),
			false))

	dbindex++
	
	'modify object commission data referantor'
	modifyNewComData = WebUI.modifyObjectProperty(findTestObject('AppView/Commission/ModifyComDataRef'), 'xpath', 'equals', "//*[@id='formInformationReferantor']/div/div["+Refindex+"]/div[2]/div[5]/div[2]/label", true)
	
	'verify tax amt'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewComData).toUpperCase().replace(",",""), (ComRef[dbindex]).toUpperCase(),
			false))

	dbindex++
	
	'modify object commission data referantor'
	modifyNewComData = WebUI.modifyObjectProperty(findTestObject('AppView/Commission/ModifyComDataRef'), 'xpath', 'equals', "//*[@id='formInformationReferantor']/div/div["+Refindex+"]/div[2]/div[6]/div[2]/label", true)
	
	'verify vat amt'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewComData).toUpperCase().replace(",",""), (ComRef[dbindex]).toUpperCase(),
			false))

	dbindex++
	
	'modify object commission data referantor'
	modifyNewComData = WebUI.modifyObjectProperty(findTestObject('AppView/Commission/ModifyComDataRef'), 'xpath', 'equals', "//*[@id='formInformationReferantor']/div/div["+Refindex+"]/div[2]/div[7]/div[2]/label", true)
	
	'verify calculation method'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewComData).toUpperCase(), (ComRef[dbindex]).toUpperCase(),
			false))

	dbindex++
	
	'modify object commission data referantor'
	modifyNewComData = WebUI.modifyObjectProperty(findTestObject('AppView/Commission/ModifyComDataRef'), 'xpath', 'equals', "//*[@id='formInformationReferantor']/div/div["+Refindex+"]/div[2]/div[8]/div[2]/label", true)
	
	'verify bank account'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewComData).toUpperCase(), (ComRef[dbindex]).toUpperCase(),
			false))

	Refindex++
}

//Check Section Summary, looping section summary
for (dbindex = 0; dbindex < listSumm.size(); dbindex++) {
	
	'modify object summary data'
	modifyNewComData = WebUI.modifyObjectProperty(findTestObject('AppView/Commission/ModifySummaryAmt'), 'xpath', 'equals', "//*[@id='formSummary']/div/div/div["+(dbindex+1)+"]/div[2]/label", true)

	'verify summary amount'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewComData).toUpperCase().replace(",",""), (listSumm[dbindex]).toUpperCase(),
			false))

	
}

if ((GlobalVariable.FlagWarning == 0) && (GlobalVariable.FlagFailed == 0)) {
	'write to excel status success'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '11. Commission', 0, GlobalVariable.NumofColm -
		1, GlobalVariable.StatusSuccess)
}

def checkVerifyEqualOrMatch(Boolean isMatch) {
	if ((isMatch == false) && (GlobalVariable.FlagFailed == 0)) {
		'write to excel status failed'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '11. Commission', 0, GlobalVariable.NumofColm -
			1, GlobalVariable.StatusFailed)

		'write to excel reason failed verify equal or match'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '11. Commission', 1, GlobalVariable.NumofColm -
			1, GlobalVariable.ReasonFailedVerifyEqualOrMatch)

		GlobalVariable.FlagFailed = 1
	}
}