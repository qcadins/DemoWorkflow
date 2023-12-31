import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import org.openqa.selenium.WebElement

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

GlobalVariable.FlagFailed = 0

'call function get data file'
getDataFile()

'call function check ddl'
checkDDL()

if (findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 12).equalsIgnoreCase('Input Data')) {
    'input contact person name asal untuk mereset field jika ke lock'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/EmergencyContact/input_ContactPersonName'), 
        'ABCD')

    'klik label contact person name '
    WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/EmergencyContact/label_Contact Person Name'))

    'input contact person name'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/EmergencyContact/input_ContactPersonName'), 
        findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 15))

    'select id type'
    WebUI.selectOptionByLabel(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/EmergencyContact/select_ID Type'), 
        findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 16), false, FailureHandling.OPTIONAL)

    'input id no'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/EmergencyContact/input_ID No'), findTestData(GlobalVariable.excelPath).getValue(
            GlobalVariable.ColmNAP4, 17))

    if (((findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 16) != 'E-KTP') && (findTestData(GlobalVariable.excelPath).getValue(
        GlobalVariable.ColmNAP4, 16) != 'NPWP')) && (findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 16) != 
    'AKTA')) {
        'input id expired date'
        WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/EmergencyContact/input_ID Expired Date'), 
            findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 18))
    }
    
    'select Customer Relationship'
    WebUI.selectOptionByLabel(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/EmergencyContact/select_CustomerRelationship'), 
        findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 19), false, FailureHandling.OPTIONAL)

    'input Email'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/EmergencyContact/input_Email'), findTestData(GlobalVariable.excelPath).getValue(
            GlobalVariable.ColmNAP4, 20))

    'select gender'
    WebUI.selectOptionByLabel(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/EmergencyContact/select_Gender'), 
        findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 21), false, FailureHandling.OPTIONAL)

    'input Birth Place'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/EmergencyContact/input_BirthPlace'), 
        findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 22))

    'input Birth Date'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/EmergencyContact/input_BirthDate'), findTestData(GlobalVariable.excelPath).getValue(
            GlobalVariable.ColmNAP4, 23))

    'input mobile phone 1'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/EmergencyContact/input_MobilePhone'), 
        findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 24))

    'input mobile phone 2'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/EmergencyContact/input_MobilePhone2'), 
        findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 25))

    if (findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 26).length() > 1) {
        'select copy address from'
        WebUI.selectOptionByLabel(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/EmergencyContact/select_Legal  Residence  Job  Mailing'), 
            findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 26), false)

        'click button copy'
        WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/EmergencyContact/button_Copy'))
		
		'call function get address'
		getDataEmergencyContact()
    } else {
        'input address'
        WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/EmergencyContact/input_TextAddress'), 
            findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 27))

        'input RT'
        WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/EmergencyContact/input_RT'), findTestData(GlobalVariable.excelPath).getValue(
                GlobalVariable.ColmNAP4, 28))

        'input RW'
        WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/EmergencyContact/input_RW'), findTestData(GlobalVariable.excelPath).getValue(
                GlobalVariable.ColmNAP4, 29))

        'click button zipcode'
        WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/EmergencyContact/button_Zipcode'))

        'input zipcode'
        WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/EmergencyContact/input_ZipCode'), 
            findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 30))

        'input Kecamatan'
        WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/EmergencyContact/input_Kecamatan'), 
            findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 31))

        'input Kelurahan'
        WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/EmergencyContact/input_Kelurahan'), 
            findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 32))

        'input Kota'
        WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/EmergencyContact/input_Kota'), findTestData(GlobalVariable.excelPath).getValue(
                GlobalVariable.ColmNAP4, 33))

        'click button search'
        WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/EmergencyContact/button_Search'))

        'click button select'
        WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/EmergencyContact/a_Select'))

        'input phone 1 area'
        WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/EmergencyContact/input_Phone1_Area'), 
            findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 34))

        'input phone 1'
        WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/EmergencyContact/input_Phone1_Number'), 
            findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 35))

        'input phone 1 ext'
        WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/EmergencyContact/input_Phone1_Ext'), 
            findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 36))

        'input phone 2 area'
        WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/EmergencyContact/input_Phone2_Area'), 
            findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 37))

        'input phone 2'
        WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/EmergencyContact/input_Phone2_Number'), 
            findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 38))

        'input phone 2 ext'
        WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/EmergencyContact/input_Phone2_Ext'), 
            findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 39))

        'input phone 3 area'
        WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/EmergencyContact/input_Phone3_Area'), 
            findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 40))

        'input phone 3'
        WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/EmergencyContact/input_Phone3_Number'), 
            findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 41))

        'input phone 3 Ext'
        WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/EmergencyContact/input_Phone3_Ext'), 
            findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 42))
    }
} else if (findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 12).equalsIgnoreCase('LookUp')) {
    'click lookup button contact person'
    WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/EmergencyContact/button_Contact Person Name_btn btn-raised btn-primary'))

    'input customer no'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/EmergencyContact/input_Customer No_custNoId'), 
        findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 13))

    'input customer name'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/EmergencyContact/input_Customer Name_custNameId'), 
        findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 14))

    'click search'
    WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/EmergencyContact/button_Search'))

    'verify input error'
    if (WebUI.verifyElementPresent(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/EmergencyContact/a_Select'), 
        GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
        'click select'
        WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/EmergencyContact/a_Select'))
    } else {
        'click X'
        WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/EmergencyContact/Button_X'))

        'click button back'
        WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerDataCompletion/button_Back'))

        'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.StatusReasonLookup'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('4.EmergencyContact', GlobalVariable.ColmNAP4, 
            GlobalVariable.StatusFailed, GlobalVariable.StatusReasonLookup)

        'Flagfailed +1 karena gagal melakukan lookup'
        (GlobalVariable.FlagFailed)++
    }
    
    if (GlobalVariable.RoleCompany == 'Testing') {
        'call function getdataemergencycontact'
        getDataEmergencyContact()

        'call tc emergencycontactdataverif'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/NAP4VerifyStoreData/Personal/TabEmergencyContactDataVerif'), 
            [:], FailureHandling.CONTINUE_ON_FAILURE)
    }
    
    'select customer relationship'
    WebUI.selectOptionByLabel(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/EmergencyContact/select_CustomerRelationship'), 
        findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 19), false)

    if (findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 26).length() > 1) {
        'select copy address from'
        WebUI.selectOptionByLabel(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/EmergencyContact/select_Legal  Residence  Job  Mailing'), 
            findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 26), false)

        'click button copy'
        WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/EmergencyContact/button_Copy'))
    }
    
    if (GlobalVariable.RoleCompany == 'Testing') {
        'call function getdataemergencycontact'
        getDataEmergencyContact()
    }
}

'click button save'
WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/EmergencyContact/button_Save  Continue'))

if ((Integer.parseInt(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 4)) == 0) && (GlobalVariable.FlagFailed == 
0)) {
    'Check alert'
    GlobalVariable.FlagFailed = CustomKeywords.'checkSaveProcess.checkSaveProcess.checkAlert'(GlobalVariable.ColmNAP4, '4.EmergencyContact')
}

if (GlobalVariable.FlagFailed == 0) {
    'Check save Process write to excel'
    CustomKeywords.'checkSaveProcess.checkSaveProcess.checkStatus'(Integer.parseInt(findTestData(GlobalVariable.excelPath).getValue(
                GlobalVariable.ColmNAP4, 4)), findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData/button_Add'), 
        GlobalVariable.ColmNAP4, '4.EmergencyContact')

    if (Integer.parseInt(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 4)) == 0) {
        'Check error validasi'
        CustomKeywords.'checkSaveProcess.checkSaveProcess.checkValidasi'(findTestObject('NAP/NAP4-CustomerDataCompletion/errorvalidasi'), 
            GlobalVariable.ColmNAP4, '4.EmergencyContact')
    }
}

if (WebUI.verifyElementPresent(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/EmergencyContact/label_Contact Person Name'), 
    GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
    'click button back'
    WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerDataCompletion/button_Back'))
}

if (((GlobalVariable.RoleCompany == 'Testing') && (GlobalVariable.CheckVerifStoreDBCompany == 'Yes')) && findTestData(GlobalVariable.excelPath).getValue(
    GlobalVariable.ColmNAP4, 12).equalsIgnoreCase('Input Data')) {
    GlobalVariable.NumofVerifStore = GlobalVariable.ColmNAP4

    'call test case verify emergency contact store data'
    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/NAP4VerifyStoreData/Personal/TabEmergencyContactVerifStoreDataDB'), 
        [:], FailureHandling.CONTINUE_ON_FAILURE)
} else if (((GlobalVariable.RoleCompany == 'Testing') && (GlobalVariable.CheckVerifStoreDBCompany == 'Yes')) && findTestData(GlobalVariable.excelPath).getValue(
    GlobalVariable.ColmNAP4, 12).equalsIgnoreCase('LookUp')) {
    'call test case verify emergency contact store data'
    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/NAP4VerifyStoreData/Personal/TabEmergencyContactVerifStoreDataDB - LookUp'), 
        [:], FailureHandling.CONTINUE_ON_FAILURE)
}

def getDataEmergencyContact() {
    ArrayList<String> confinsdata = new ArrayList<String>()

	if(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 12).equalsIgnoreCase('LookUp')){
    'get name'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/EmergencyContact/input_ContactPersonName'), 
            'value'))

    'get id type'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/EmergencyContact/select_ID Type'), 
            'value'))

    'get id no'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/EmergencyContact/input_ID No'), 
            'value'))

    'get id expired date'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/EmergencyContact/input_ID Expired Date'), 
            'value'))

    'get mobile phone 1'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/EmergencyContact/input_MobilePhone'), 
            'value'))

    'get Email'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/EmergencyContact/input_Email'), 
            'value'))

    'get gender'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/EmergencyContact/select_Gender'), 
            'value'))

    'get Birth Place'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/EmergencyContact/input_BirthPlace'), 
            'value'))

    'get Birth Date'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/EmergencyContact/input_BirthDate'), 
            'value'))

    'get mobile phone 2'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/EmergencyContact/input_MobilePhone2'), 
            'value'))
	}
	
	'get address'
	confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/EmergencyContact/input_TextAddress'),
			'value'))

	'get RT'
	confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/EmergencyContact/input_RT'),
			'value'))

	'get RW'
	confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/EmergencyContact/input_RW'),
			'value'))

	'get zipcode'
	confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/EmergencyContact/label_Zipcode'),
			'value'))

	'get Kelurahan'
	confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/EmergencyContact/label_Kelurahan'),
			'value'))

	'get Kecamatan'
	confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/EmergencyContact/label_Kecamatan'),
			'value'))

	'get Kota'
	confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/EmergencyContact/label_Kota'),
			'value'))

	'input phone 1 area'
	confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/EmergencyContact/input_Phone1_Area'),
			'value'))

	'input phone 1'
	confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/EmergencyContact/input_Phone1_Number'),
			'value'))

	'input phone 1 ext'
	confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/EmergencyContact/input_Phone1_Ext'),
			'value'))

	'input phone 2 area'
	confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/EmergencyContact/input_Phone2_Area'),
			'value'))

	'input phone 2'
	confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/EmergencyContact/input_Phone2_Number'),
			'value'))

	'input phone 2 ext'
	confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/EmergencyContact/input_Phone2_Ext'),
			'value'))

	'input phone 3 area'
	confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/EmergencyContact/input_Phone3_Area'),
			'value'))

	'input phone 3'
	confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/EmergencyContact/input_Phone3_Number'),
			'value'))

	'input phone 3 Ext'
	confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/EmergencyContact/input_Phone3_Ext'),
			'value'))
	
	if(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 12).equalsIgnoreCase('LookUp')){
	'select ownership'
	confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/EmergencyContact/select_CustomerRelationship'),
		'value'))
	}
	
    GlobalVariable.Confinsdata = confinsdata
}

def checkDDL(){
	if(GlobalVariable.RoleCompany == 'Testing'){
		'connect DB FOU'
		Sql sqlConnectionFOU = CustomKeywords.'dbConnection.connectDB.connectFOU'()
		
		'get IDType ddl value from db'
		ArrayList<String> IDType = CustomKeywords.'dbConnection.checkCustomer.checkIDTypeDDL'(sqlConnectionFOU)
	
		'get total label from ddl IDType'
		int totalddlIDType = WebUI.getNumberOfTotalOption(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/EmergencyContact/select_ID Type'))
	
		'verify total ddl IDType confins = total ddl db'
		WebUI.verifyEqual(totalddlIDType - 1, IDType.size())
	
		'verify isi ddl IDType confins = db'
		if (WebUI.verifyOptionsPresent(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/EmergencyContact/select_ID Type'),
			IDType) == false) {
			
			'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDDL'
			CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('4.EmergencyContact', GlobalVariable.NumofColm,
				GlobalVariable.StatusFailed, findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 2) + ';' + GlobalVariable.ReasonFailedDDL + 'ID Type')
	
			(GlobalVariable.FlagFailed)++
		}
			
			'get Gender ddl value from db'
			ArrayList<String> Gender = CustomKeywords.'dbConnection.checkCustomer.checkGenderDLLPersonal'(sqlConnectionFOU)
		
			'get total label from ddl Gender'
			int totalddlGender = WebUI.getNumberOfTotalOption(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/EmergencyContact/select_Gender'))
		
			'verify total ddl Gender confins = total ddl db'
			WebUI.verifyEqual(totalddlGender - 1, Gender.size())
		
			'verify isi ddl Gender confins = db'
			if (WebUI.verifyOptionsPresent(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/EmergencyContact/select_Gender'),
				Gender) == false) {
				
				'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDDL'
				CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('4.EmergencyContact', GlobalVariable.NumofColm,
					GlobalVariable.StatusFailed, findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 2) + ';' + GlobalVariable.ReasonFailedDDL + 'Gender')
		
				(GlobalVariable.FlagFailed)++
			}
	}
}

def getDataFile(){
	if (GlobalVariable.APPSTEP == 'SHAREHOLDER PERSONAL') {
		'get data file path'
		GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'("\\Excel\\"+ GlobalVariable.LOB +"\\2.2.2 CustomerDataCompletion-Company - ManagementShareholderPersonal.xlsm")
	
		'declare data file Global variable'
		GlobalVariable.excelPath = 'NAP-'+ GlobalVariable.LOB +'-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderPersonal/EmergencyContact'
	} else if (GlobalVariable.APPSTEP == 'GUARANTOR PERSONAL') {
		'get data file path'
		GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'("\\Excel\\"+ GlobalVariable.LOB +"\\2.2.4 CustomerDataCompletion-Company - GuarantorPersonal.xlsm")
	
		GlobalVariable.excelPath = 'NAP-'+ GlobalVariable.LOB +'-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorPersonal/EmergencyContact'
	}
}