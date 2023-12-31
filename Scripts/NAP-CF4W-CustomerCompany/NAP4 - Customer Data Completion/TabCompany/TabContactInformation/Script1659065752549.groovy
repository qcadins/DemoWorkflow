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

import groovy.sql.Sql
import internal.GlobalVariable as GlobalVariable

'delay supaya section address information ke load'
WebUI.delay(3)

'call function get data file'
getDataFile()

'call function check ddl'
checkDDL()

'input name'
WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/ContactInformation/input_Name_'), 
    findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 12))

'input job title'
WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/ContactInformation/input_Job Title_'), 
    findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 13))

if (findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 14).length() > 2) {
    'select id type'
    WebUI.selectOptionByLabel(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/ContactInformation/select_Id Type'), 
        findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 14), false)

    'input id no'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/ContactInformation/input_Id No_'), 
        findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 15))
}

if (((findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 14) != 'E-KTP') && (findTestData(GlobalVariable.excelPath).getValue(
    GlobalVariable.ColmNAP4, 14) != 'AKTA')) && (findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 14) != 
'NPWP')) {
    'input idexpired date'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/ContactInformation/input_Id Expired Date'), 
        findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 16))
}

'input mobile phone 1'
WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/ContactInformation/input_Mobile Phone 1_'), 
    findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 17))

'input email1'
WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/ContactInformation/input_Email 1_'), 
    findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 18))

'select gender'
WebUI.selectOptionByLabel(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/ContactInformation/select_Select One Female  Male'), 
    findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 19), false)

'select job position'
WebUI.selectOptionByLabel(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/ContactInformation/select_Select Job Position'), 
    findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 20).toUpperCase(), false)

'input birth place'
WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/ContactInformation/input_Birth Place_'), 
    findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 21))

'input birth date'
WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/ContactInformation/input_Birth Date_'), 
    findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 22))

if (findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 23).length() > 2) {
    'select relation'
    WebUI.selectOptionByLabel(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/ContactInformation/select_Relation'), 
        findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 23), false)
}

'input mobile 2'
WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/ContactInformation/input_Mobile Phone 2'), 
    findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 24))

'input email 2'
WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/ContactInformation/input_Email 2_'), 
    findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 25))

'input address'
WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/ContactInformation/textarea_Address_'), 
    findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 27))

'input RT'
WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/ContactInformation/input_RT'), 
    findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 28))

'input RW'
WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/ContactInformation/input_RW'), 
    findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 29))

if (findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 30).length() > 2) {
    'click button zipcode'
    WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/ContactInformation/button_Zipcode_btn btn-raised btn-primary'))

    'input Zipcode'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/ContactInformation/input_Zip Code_ZipCode'), 
        findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 30))

    'input kecamatan'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/ContactInformation/input_Kecamatan_kecamatan'), 
        findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 31))

    'input kelurahan'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/ContactInformation/input_Kelurahan_Kelurahan'), 
        findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 32))

    'input kota'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/ContactInformation/input_Kota_kota'), 
        findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 33))

    'click search'
    WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/ContactInformation/button_Search'))

    'verify input error'
    if (WebUI.verifyElementPresent(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/ContactInformation/a_Select'), 
        GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
        'click select'
        WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/ContactInformation/a_Select'))
    } else {
        'click X'
        WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/ContactInformation/Button_X'))
    }
}

'input phone 1 area'
WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/ContactInformation/input_Phone 1_Area'), 
    findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 34))

'input phone 1 number'
WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/ContactInformation/input_Phone 1_Number'), 
    findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 35))

'input phone 1 extension'
WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/ContactInformation/input_Phone 1_Ext'), 
    findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 36))

'input phone 2 area'
WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/ContactInformation/input_Phone 2_Area'), 
    findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 37))

'input phone 2 number'
WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/ContactInformation/input_Phone 2_Number'), 
    findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 38))

'input phone 2 extension'
WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/ContactInformation/input_Phone 2_Ext'), 
    findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 39))

'input fax'
WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/ContactInformation/input_Fax_Area'), 
    findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 40))

'input fax number'
WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/ContactInformation/input_Fax_Number'), 
    findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 41))

'click button save and continue'
WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/ContactInformation/button_Save  Continue'))

if ((Integer.parseInt(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 4)) == 0) && (GlobalVariable.FlagFailed == 
0)) {
    'Check alert'
    GlobalVariable.FlagFailed = CustomKeywords.'checkSaveProcess.checkSaveProcess.checkAlert'(GlobalVariable.ColmNAP4, 
        '3.ContactInformation')
}

if (GlobalVariable.FlagFailed == 0) {
    'Check save Process write to excel'
    CustomKeywords.'checkSaveProcess.checkSaveProcess.checkStatus'(Integer.parseInt(findTestData(GlobalVariable.excelPath).getValue(
                GlobalVariable.ColmNAP4, 4)), findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/FinancialData/button_Add'), 
        GlobalVariable.ColmNAP4, '3.ContactInformation')

    if (Integer.parseInt(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 4)) == 0) {
        'Check error validasi'
        CustomKeywords.'checkSaveProcess.checkSaveProcess.checkValidasi'(findTestObject('NAP/NAP4-CustomerDataCompletion/errorvalidasi'), 
            GlobalVariable.ColmNAP4, '3.ContactInformation')
    }
}

if (WebUI.verifyElementPresent(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/ContactInformation/label_Name'), 
    GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
    'click button back'
    WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerDataCompletion/button_Back'))
}

if ((GlobalVariable.RoleCompany == 'Testing') && (GlobalVariable.CheckVerifStoreDBCompany == 'Yes')) {
    GlobalVariable.NumofVerifStore = GlobalVariable.ColmNAP4

    'call test case verify contact info store data'
    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/NAP4VerifyStoreData/Company/TabContactInfoVerifStoreDataDB'), 
        [:], FailureHandling.CONTINUE_ON_FAILURE)
}

def checkDDL() {
	if (GlobalVariable.Role == 'Testing') {
		'connect DB FOU'
		Sql sqlConnectionFOU = CustomKeywords.'dbConnection.connectDB.connectFOU'()

		'get IDType ddl value from db'
		ArrayList<String> IDType = CustomKeywords.'dbConnection.checkCustomer.checkIDTypeDDL'(sqlConnectionFOU)

		'get total label from ddl IDType'
		int totalddlIDType = WebUI.getNumberOfTotalOption(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/ContactInformation/select_Id Type'))

		'verify total ddl IDType confins = total ddl db'
		WebUI.verifyEqual(totalddlIDType - 1, IDType.size())

		'verify isi ddl IDType confins = db'
		if (WebUI.verifyOptionsPresent(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/ContactInformation/select_Id Type'),
			IDType) == false) {
			
			'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDDL'
			CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('3.ContactInformation', GlobalVariable.NumofColm,
				GlobalVariable.StatusFailed, findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofColm, 2) + ';' + GlobalVariable.ReasonFailedDDL + 'ID Type')

			(GlobalVariable.FlagFailed)++
		}
		
		'get Gender ddl value from db'
		ArrayList<String> Gender = CustomKeywords.'dbConnection.checkCustomer.checkGenderDLLPersonal'(sqlConnectionFOU)

		'get total label from ddl Gender'
		int totalddlGender = WebUI.getNumberOfTotalOption(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/ContactInformation/select_Select One Female  Male'))

		'verify total ddl Gender confins = total ddl db'
		WebUI.verifyEqual(totalddlGender - 1, Gender.size())

		'verify isi ddl Gender confins = db'
		if (WebUI.verifyOptionsPresent(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/ContactInformation/select_Select One Female  Male'),
			Gender) == false) {

			'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDDL'
			CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('3.ContactInformation', GlobalVariable.NumofColm,
				GlobalVariable.StatusFailed, findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofColm, 2) + ';' + GlobalVariable.ReasonFailedDDL + 'Gender')

			(GlobalVariable.FlagFailed)++
		}
		
		'get jobposition ddl value from db'
		ArrayList<String> jobposition = CustomKeywords.'nap4Data.checkNAP4.checkjobPosition'(sqlConnectionFOU)

		'get total label from ddl jobposition'
		int totalddljobposition = WebUI.getNumberOfTotalOption(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/ContactInformation/select_Select Job Position'))

		'verify total ddl jobposition confins = total ddl db'
		WebUI.verifyEqual(totalddljobposition - 1, jobposition.size())

		'verify isi ddl jobposition confins = db'
		if (WebUI.verifyOptionsPresent(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/ContactInformation/select_Select Job Position'),
			jobposition) == false) {
		
			'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDDL'
			CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('3.ContactInformation', GlobalVariable.NumofColm,
				GlobalVariable.StatusFailed, findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofColm, 2) + ';' + GlobalVariable.ReasonFailedDDL + 'jobposition')

			(GlobalVariable.FlagFailed)++
		}
		
		'get relationship ddl value from db'
		ArrayList<String> relationship = CustomKeywords.'nap4Data.checkNAP4.checkcompanyrelationship'(sqlConnectionFOU)

		'get total label from ddl jobposition'
		int totalddlrelationship = WebUI.getNumberOfTotalOption(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/ContactInformation/select_Relation'))

		'verify total ddl relationship confins = total ddl db'
		WebUI.verifyEqual(totalddlrelationship - 1, relationship.size())

		'verify isi ddl relationship confins = db'
		if (WebUI.verifyOptionsPresent(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/ContactInformation/select_Relation'),
			relationship) == false) {
		
			'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDDL'
			CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('3.ContactInformation', GlobalVariable.NumofColm,
				GlobalVariable.StatusFailed, findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.NumofColm, 2) + ';' + GlobalVariable.ReasonFailedDDL + 'relationship')

			(GlobalVariable.FlagFailed)++
		}
	}
}

def getDataFile(){
	if(GlobalVariable.APPSTEP == 'CUSTOMER'){
		'get data file path'
		GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'("\\Excel\\"+ GlobalVariable.LOB +"\\2.2.1 CustomerDataCompletion-Company - Customer.xlsm")
		
		'declare data file Global variable'
		GlobalVariable.excelPath = 'NAP-'+ GlobalVariable.LOB +'-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/ContactInformation'
	}else if(GlobalVariable.APPSTEP == 'SHAREHOLDER COMPANY'){
		'get data file path'
		GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'("\\Excel\\"+ GlobalVariable.LOB +"\\2.2.3 CustomerDataCompletion-Company - ManagementShareholderCompany.xlsm")
		
		'declare data file Global variable'
		GlobalVariable.excelPath = 'NAP-'+ GlobalVariable.LOB +'-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementshareholderCompany/ContactInformation'
	}else if(GlobalVariable.APPSTEP == 'GUARANTOR COMPANY'){
		'get data file path'
		GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'("\\Excel\\"+ GlobalVariable.LOB +"\\2.2.5 CustomerDataCompletion-Company - GuarantorCompany.xlsm")
	
		'declare data file Global variable'
		GlobalVariable.excelPath = 'NAP-'+ GlobalVariable.LOB +'-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/ContactInformation'
	}
}