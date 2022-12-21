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
import groovy.sql.Sql as Sql
'declare datafileCustomerPersonal'
datafileCustomerPersonal = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData')

GlobalVariable.StartIndex=2

'declare datafileCustomerPersonal'
datafileCustomerPersonal = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData')

'declare datafileReferantor'
datafileReferantor = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabReferantorData')

'declare datafileTabApplication'
datafileTabApplication = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabApplicationData')

'declare datafileTabAsset'
datafileTabAsset = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData')

'declare datafileTabInsurance'
datafileTabInsurance = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData')

'declare datafileTabLifeInsurance'
datafileTabLifeInsurance = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabLifeInsuranceData')

'declare datafileTabFinancial'
datafileTabFinancial = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData')

'declare datafileTabTC'
datafileTabTC = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabTermConditionData')

'declare datafileTabUploadDoc'
datafileTabUploadDoc = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabUploadDocument')

'get data file path'
GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'(GlobalVariable.PathPersonal)

WebUI.click(findTestObject('Object Repository/NAP/ReturnHandling/ExtendReturnHandling'))

WebUI.click(findTestObject('Object Repository/NAP/ReturnHandling/MenuEditApplicationData'))

'input application no'
WebUI.setText(findTestObject('NAP/CommissionReservedFund/TabCommissionData/input_Application No_AppNoId'), datafileCustomerPersonal.getValue(
            GlobalVariable.NumofColm, 13))

'klik search'
WebUI.click(findTestObject('NAP/CommissionReservedFund/TabCommissionData/button_Search'))

'klik icon pensil untuk select'
WebUI.click(findTestObject('NAP/CommissionReservedFund/TabCommissionData/i_Select'))

//call test case satuan nap2
'Looping untuk mencari nilai colm yang menunjukkan colm appno'
for (GlobalVariable.NumofReferantor = 2; GlobalVariable.NumofReferantor <= (datafileReferantor.getColumnNumbers() - 1); (GlobalVariable.NumofReferantor)++) {
	if (datafileReferantor.getValue(
		GlobalVariable.NumofReferantor, 12) == datafileCustomerPersonal.getValue(
		GlobalVariable.NumofColm, 13)) {
			GlobalVariable.StartIndex = GlobalVariable.NumofReferantor
			break
	
	}
}


'Dijalankan tanpa copy app tab referantor atau copy app dengan edit'
if(datafileReferantor.getValue(GlobalVariable.StartIndex, 10).equalsIgnoreCase("No") || datafileReferantor.getValue(
	GlobalVariable.StartIndex, 10).equalsIgnoreCase("Edit")){

	'call test case tab referantor'
	WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabReferantorData'),
			[:], FailureHandling.STOP_ON_FAILURE)
}
//dijalankan dengan copy app tab referantor
else if(datafileReferantor.getValue(GlobalVariable.StartIndex, 10).equalsIgnoreCase("Yes")){
	'click button save'
	WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabReferantorData/Button Save'))
	
	'Write to excel success'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '5.TabReferantorData',
				0, GlobalVariable.StartIndex - 1, GlobalVariable.StatusSuccess)
	
	'verify fail'
	if (WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/ApplicationCurrentStep')),
				'REFERANTOR', false, FailureHandling.OPTIONAL)) {
	
		'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.StatusFailedCopyApp'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('5.TabReferantorData', GlobalVariable.StartIndex, GlobalVariable.StatusFailed, GlobalVariable.StatusFailedCopyApp)
	
		'click button cancel'
		WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabReferantorData/button_Cancel'))
	}
}
'Dijalankan tanpa copy app tab application atau copy app dengan edit'
if(datafileTabApplication.getValue(GlobalVariable.NumofColm, 10).equalsIgnoreCase("No") || datafileTabApplication.getValue(
GlobalVariable.NumofColm, 10).equalsIgnoreCase("Edit")){
	'Call test case tab application data'
	WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabApplicationData'),
					[:], FailureHandling.STOP_ON_FAILURE)
}
//dijalankan dengan copy app tab application
else if(datafileTabApplication.getValue(GlobalVariable.NumofColm, 10).equalsIgnoreCase("Yes")){
	'click Save'
	WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/button_Save'))
		
	'Write to excel success'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '6.TabApplicationData',
					0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusSuccess)
		
	WebUI.delay(5)
		
	'Verify fail'
	if (WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/ApplicationCurrentStep')),
		'APPLICATION DATA', false, FailureHandling.OPTIONAL)) {
		
		'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.StatusFailedCopyApp'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('6.TabApplicationData', GlobalVariable.NumofColm, GlobalVariable.StatusFailed, GlobalVariable.StatusFailedCopyApp)
		
		'click cancel'
		WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/button_Cancel'))
	}
}
'Dijalankan tanpa copy app tab asset atau copy app dengan edit'
if(datafileTabAsset.getValue(GlobalVariable.NumofColm, 10).equalsIgnoreCase("No") || datafileTabAsset.getValue(
GlobalVariable.NumofColm, 10).equalsIgnoreCase("Edit")){
	'call test case tab asset data'
	WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabAssetData'),
					[:], FailureHandling.STOP_ON_FAILURE)

}
//dijalankan dengan copy app tab asset
else if(datafileTabAsset.getValue(GlobalVariable.NumofColm, 10).equalsIgnoreCase("Yes")){
			
	'input chasis number'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_CHASIS NUMBER'),
			datafileTabAsset.getValue(GlobalVariable.NumofColm, 29))
							
	'input engine number'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_ENGINE NUMBER'),
			datafileTabAsset.getValue(GlobalVariable.NumofColm, 30))
				
	'input license plate number'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_LICENSE PLATE NUMBER'),
		datafileTabAsset.getValue(GlobalVariable.NumofColm, 31))
				
	if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_Serial4'),
		GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
		'input serial 4'
		WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_Serial4'),
			datafileTabAsset.getValue(GlobalVariable.NumofColm, 32), FailureHandling.OPTIONAL)
	}
				
	if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_Serial5'),
		GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
		'input serial 5'
		WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/input_Serial5'),
			datafileTabAsset.getValue(GlobalVariable.NumofColm, 33), FailureHandling.OPTIONAL)
	}
					
	'click button save'
	WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/button_Save'))
		
	'Menunggu Alert security deposit dibawah minimum atau manufacturing year dibawah angka tertentu (jika ada) muncul'
	WebUI.waitForAlert(3)
		
	'Accept Alert Konfirmasi Security deposit dibawah minimum atau manufacturing year dibawah angka tertentu'
	WebUI.acceptAlert(FailureHandling.OPTIONAL)
	
	'Menunggu Alert konfirmasi integrator muncul'
	WebUI.waitForAlert(3)
	
	'Accept Alert Konfirmasi Integrator'
	WebUI.acceptAlert(FailureHandling.OPTIONAL)
		
	'Write to excel success'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '7.TabAssetData', 0,
					GlobalVariable.NumofColm - 1, GlobalVariable.StatusSuccess)
	
	WebUI.delay(10)
		
	if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/button_Supplier Name_btn btn-raised btn-primary'),
		GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
		
		'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.StatusFailedCopyApp'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('7.TabAssetData', GlobalVariable.NumofColm, GlobalVariable.StatusFailed, GlobalVariable.StatusFailedCopyApp)
		
		'click button cancel'
		WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/button_Cancel'))
	}
}

'Dijalankan tanpa copy app tab insurance atau copy app dengan edit'
if(datafileTabInsurance.getValue(GlobalVariable.NumofColm, 10).equalsIgnoreCase("No") || datafileTabInsurance.getValue(
GlobalVariable.NumofColm, 10).equalsIgnoreCase("Edit")){
	'call test case tab insurance'
	WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabInsuranceData'),
					[:], FailureHandling.STOP_ON_FAILURE)

}
//dijalankan dengan copy app tab insurance
else if(datafileTabInsurance.getValue(GlobalVariable.NumofColm, 10).equalsIgnoreCase("Yes")){
			
	'verify field discount ada atau tidak untuk write ulang diskon supaya tidak hilang akibat impact dari calculate insurance'
	if(WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabInsuranceData/input_Discount_TotalCustDiscAmt'), GlobalVariable.TimeOut,FailureHandling.OPTIONAL)){
		def val = WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabInsuranceData/input_Discount_TotalCustDiscAmt'),'value')
		'Set text discount'
		WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabInsuranceData/input_Discount_TotalCustDiscAmt'),val,FailureHandling.OPTIONAL)
	}
	
	'verify button calculate ada atau tidak'
	if(WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabInsuranceData/button_Calculate Insurance'), GlobalVariable.TimeOut,FailureHandling.OPTIONAL)){
		'Klik calculate insurance'
		WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabInsuranceData/button_Calculate Insurance'))
	}
	'Klik save'
	WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabInsuranceData/button_Save'))
		
	WebUI.delay(5)
		
	'Write to excel success'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '8.TabInsuranceData',
		0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusSuccess)
		
	'verify fail'
	if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabInsuranceData/select_InsuredBy'),
		GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
					
		'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.StatusFailedCopyApp'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('8.TabInsuranceData', GlobalVariable.NumofColm, GlobalVariable.StatusFailed, GlobalVariable.StatusFailedCopyApp)
		
		'click cancel'
		WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabInsuranceData/button_Cancel'))
	}
}

'Dijalankan tanpa copy app tab life insurance atau copy app dengan edit'
if(datafileTabLifeInsurance.getValue(GlobalVariable.NumofColm, 10).equalsIgnoreCase("No") || datafileTabLifeInsurance.getValue(
GlobalVariable.NumofColm, 10).equalsIgnoreCase("Edit")){
	'call test case tab life insurance'
	WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabLifeInsuranceData'),
					[:], FailureHandling.STOP_ON_FAILURE)
}
//dijalankan dengan copy app tab life insurance
else if(datafileTabLifeInsurance.getValue(GlobalVariable.NumofColm, 10).equalsIgnoreCase("Yes")){
	'click Save'
	WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabLifeInsuranceData/button_Save'))
	
	'Write to excel success'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '9.TabLifeInsuranceData',
		0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusSuccess)
	
	'Verify fail'
	if (WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/ApplicationCurrentStep')),
		'LIFE INSURANCE', false, FailureHandling.OPTIONAL)) {
	
		'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.StatusFailedCopyApp'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('9.TabLifeInsuranceData', GlobalVariable.NumofColm, GlobalVariable.StatusFailed, GlobalVariable.StatusFailedCopyApp)
	
		'click cancel'
		WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabLifeInsuranceData/button_Cancel'))
	 
	}
}

'Dijalankan tanpa copy app tab financial atau copy app dengan edit'
if(datafileTabFinancial.getValue(GlobalVariable.NumofColm, 10).equalsIgnoreCase("No") || datafileTabFinancial.getValue(
GlobalVariable.NumofColm, 10).equalsIgnoreCase("Edit")){
	'call test case tab financial'
	WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabFinancialData'),
					[:], FailureHandling.STOP_ON_FAILURE)
	}
	//Dijalankan dengan copy app tab financial
	else if(datafileTabFinancial.getValue(GlobalVariable.NumofColm, 10).equalsIgnoreCase("Yes")){
	'click button calculate'
	WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/button_Calculate'))
		
	WebUI.delay(5)
		
	'click button save'
	WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/button_Save'))
		
	'Write to excel success'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '10.TabFinancialData',
		0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusSuccess)
		
	if (WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/ApplicationCurrentStep')),
		'FINANCIAL DATA', false, FailureHandling.OPTIONAL)) {
					
		'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.StatusFailedCopyApp'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('10.TabFinancialData', GlobalVariable.NumofColm, GlobalVariable.StatusFailed, GlobalVariable.StatusFailedCopyApp)
		
		'click button cancel'
		WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/button_Cancel'))
	}
}

'Dijalankan tanpa copy app tab term & condition atau copy app dengan edit'
if(datafileTabTC.getValue(GlobalVariable.NumofColm, 10).equalsIgnoreCase("No") || datafileTabTC.getValue(
GlobalVariable.NumofColm, 10).equalsIgnoreCase("Edit")){
	'call test case tab term condition'
	WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabTermConditionData'),
					[:], FailureHandling.STOP_ON_FAILURE)
}
//Dijalankan dengan copy app tab term&condition
else if(datafileTabTC.getValue(GlobalVariable.NumofColm, 10).equalsIgnoreCase("Yes")){
	'Save'
	WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabTermConditionData/button_Save'))
		
	'Write to excel success'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '11.TabTermConditionData',
					0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusSuccess)
		
	'Verify fail'
	if (WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/ApplicationCurrentStep')),
		'TERM AND CONDITION', false, FailureHandling.OPTIONAL)) {
					
		'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.StatusFailedCopyApp'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('11.TabTermConditionData', GlobalVariable.NumofColm, GlobalVariable.StatusFailed, GlobalVariable.StatusFailedCopyApp)
		
		'click cancel'
		WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabTermConditionData/button_Cancel'))
	}
}

'Looping untuk mencari nilai colm yang menunjukkan colm appno'
for (GlobalVariable.NumofUploadDocument = 2; GlobalVariable.NumofUploadDocument <= (datafileTabUploadDoc.getColumnNumbers() - 1); (GlobalVariable.NumofUploadDocument)++) {
	if (datafileTabUploadDoc.getValue(
		GlobalVariable.NumofUploadDocument, 12) == datafileCustomerPersonal.getValue(
		GlobalVariable.NumofColm, 13)) {
		GlobalVariable.StartIndex = GlobalVariable.NumofUploadDocument
		break
		}
}

'Dijalankan tanpa copy app tab upload document atau copy app dengan edit'
if(datafileTabUploadDoc.getValue(GlobalVariable.StartIndex, 10).equalsIgnoreCase("No") || datafileTabUploadDoc.getValue(GlobalVariable.StartIndex, 10).equalsIgnoreCase("Edit")){
	'call test case tab upload document'
	WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabUploadDocument'),
					[:], FailureHandling.STOP_ON_FAILURE)
}
//dijalankan dengan copy app tab upload document
else if(datafileTabUploadDoc.getValue(GlobalVariable.StartIndex, 10).equalsIgnoreCase("Yes")){

	'click button submit edit application'
	WebUI.click(findTestObject('Object Repository/NAP/ReturnHandling/SubmitUploadDocument'))

		
	'Write to excel success'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '12.TabUploadDocument',
		0, GlobalVariable.StartIndex - 1, GlobalVariable.StatusSuccess)
		
	if (WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabUploadDocument/input_Application No_AppNoId'),
		GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
					
		'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.StatusFailedCopyApp'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('12.TabUploadDocument', GlobalVariable.StartIndex, GlobalVariable.StatusFailed, GlobalVariable.StatusFailedCopyApp)
		
		'click cancel'
		WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabTermConditionData/button_Cancel'))
	}
	'Verify tab insurance, life insurance, financial apakah ada nilai yang berubah'
	verifyMatch()

}

'Verify tab insurance, life insurance, financial apakah ada nilai yang berubah (membandingkan app sumber dengan app hasil copy dalam database)'
public verifyMatch(){
	'Koneksi database'
	Sql sqlConnectionLOS = CustomKeywords.'dbConnection.connectDB.connectLOS'()
	
	'verify tab insurance, life insurance & financial, cek apakah ada nilai yang berubah, bernilai true jika tidak ada nilai yang berubah, false jika ada salah satu nilai yang berubah'
	Boolean isMatch = CustomKeywords.'dbConnection.getInfoForEditNAP.verifyMatchCopyAppYesNAP2Personal'(sqlConnectionLOS, datafileCustomerPersonal.getValue(
	GlobalVariable.NumofColm, 9),datafileCustomerPersonal.getValue(GlobalVariable.NumofColm, 13))
		
	if(isMatch==true){
			'Write to excel sheet commission copy app yes'
			CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '13.TabCommissionData',
				9, GlobalVariable.NumofColm - 1, "Yes")
			'Write to excel sheet reserved fund copy app yes'
			CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '14.TabReservedFundData',
				9, GlobalVariable.NumofColm - 1, "Yes")
	}
	else if(isMatch==false){
			'Write to excel sheet commission copy app edit'
			CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '13.TabCommissionData',
				9, GlobalVariable.NumofColm - 1, "Edit")
			'Write to excel sheet reserved fund copy app edit'
			CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '14.TabReservedFundData',
				9, GlobalVariable.NumofColm - 1, "Edit")
	}
}

