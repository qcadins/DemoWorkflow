import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import org.openqa.selenium.By as By
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import org.openqa.selenium.support.ui.Select as Select
import internal.GlobalVariable as GlobalVariable
import groovy.sql.Sql as Sql

GlobalVariable.FlagWarning = 0

GlobalVariable.FlagFailed = 0

'get data file path'
GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'(GlobalVariable.PathCompany)

'declare datafileCustomerCompany'
datafileCustomerCompany = findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData')

'connect DB FOU'
Sql sqlConnectionFOU = CustomKeywords.'dbConnection.connectDB.connectFOU'()

'connect DB LOS'
Sql sqlConnectionLOS = CustomKeywords.'dbConnection.connectDB.connectLOS'()

'Klik tab customer'
WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP1-CustomerData/buttonTabCust'))

if (datafileCustomerCompany.getValue(GlobalVariable.NumofColm, 14) == 'Input Data') {
    'click radio button Personal'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/Radio_Personal'))

    'click radio button company'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/span_ Company'))

	'call function verify DDL'
	verifyDDL(sqlConnectionFOU)
	
    'input customer name'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/input_Customer Legal Name_form-control ng-untouched ng-pristine ng-invalid'), 
        datafileCustomerCompany.getValue(GlobalVariable.NumofColm, 19))

    'input tax id no'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/input_Tax Id No_form-control ng-untouched ng-pristine ng-invalid'), 
        datafileCustomerCompany.getValue(GlobalVariable.NumofColm, 20))

    'select company type'
    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/select_Select One CV  Koperasi  PT'), 
        datafileCustomerCompany.getValue(GlobalVariable.NumofColm, 21), false)

    'select customer model'
    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/select_Select One Corporate  Non Corporate'), 
        datafileCustomerCompany.getValue(GlobalVariable.NumofColm, 22), false)

    'input address'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/textarea_Address'), datafileCustomerCompany.getValue(
            GlobalVariable.NumofColm, 24))

    'input RT'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/input_RT'), datafileCustomerCompany.getValue(
            GlobalVariable.NumofColm, 25))

    'input RW'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/input_RW'), datafileCustomerCompany.getValue(
            GlobalVariable.NumofColm, 26))

    'click lookup zipcode'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/button_Zipcode_btn btn-raised btn-primary'))

    'input zip code'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/input_Zip Code_ZipCode'), datafileCustomerCompany.getValue(
            GlobalVariable.NumofColm, 27))

    'input kecamatan'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/input_Kecamatan_kecamatan'), 
        datafileCustomerCompany.getValue(GlobalVariable.NumofColm, 28))

    'input kelurahan'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/input_Kelurahan_Kelurahan'), 
        datafileCustomerCompany.getValue(GlobalVariable.NumofColm, 29))

    'input kota'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/input_Kota_kota'), datafileCustomerCompany.getValue(
            GlobalVariable.NumofColm, 30))

    'click button search'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/button_Search'))

    'verify input error'
    CustomKeywords.'customizeKeyword.function.verifyInputLookup'(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/button_Back'), 
        '1.TabCustomerMainData', GlobalVariable.NumofColm)

    'select ownership'
    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/select_Select One Dinas  Family  KPR  Rented  Self - Owned'), 
        datafileCustomerCompany.getValue(GlobalVariable.NumofColm, 31), false)
} else if (datafileCustomerCompany.getValue(GlobalVariable.NumofColm, 14) == 'LookUp') {
    'double check untuk copy app data'
    if (((datafileCustomerCompany.getValue(GlobalVariable.NumofColm, 16).length() > 0) || (datafileCustomerCompany.getValue(
        GlobalVariable.NumofColm, 17).length() > 0)) || (datafileCustomerCompany.getValue(GlobalVariable.NumofColm, 18).length() > 
    0)) {
        'click radio button company'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/span_ Company'))

		'call function verify DDL'
		verifyDDL(sqlConnectionFOU)
		
        'click button lookpup Customer'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/button_Customer Legal Name_btn btn-raised btn-primary'))

        'input customer no'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/input_Customer No_custNoId'), 
            datafileCustomerCompany.getValue(GlobalVariable.NumofColm, 16))

        'input customer name'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/input_Customer Name_custNameId'), 
            datafileCustomerCompany.getValue(GlobalVariable.NumofColm, 17))

        'input tax id no'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/input_NPWP_NpwpId'), datafileCustomerCompany.getValue(
                GlobalVariable.NumofColm, 18))

        'click button search'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/button_Search'))

        'verify input error'
        CustomKeywords.'customizeKeyword.function.verifyInputLookup'(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/button_Back'), 
            '1.TabCustomerMainData', GlobalVariable.NumofColm)
    }
    
    'check if role testing untuk get data customer digunakan untuk data verif'
    if (GlobalVariable.RoleCompany == 'Testing') {
        'call function get data cust'
        getDataCust()

        'add company type to array'
        GlobalVariable.Confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/select_Select One CV  Koperasi  PT'), 
                'value'))

        'add customer model to array'
        GlobalVariable.Confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/select_Select One Corporate  Non Corporate'), 
                'value'))

        'add ownership to array'
        GlobalVariable.Confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/select_Select One Dinas  Family  KPR  Rented  Self - Owned'), 
                'value'))

        'call test case verif customer data'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP1 - CustomerData/Customer/TabCustomerDataVerif'), [:], 
            FailureHandling.CONTINUE_ON_FAILURE)
    }
    
    if (datafileCustomerCompany.getValue(GlobalVariable.NumofColm, 21).length() > 0) {
        'select company type'
        WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/select_Select One CV  Koperasi  PT'), 
            datafileCustomerCompany.getValue(GlobalVariable.NumofColm, 21), false)
    }
}

'check if role testing dan diinput dengan lookup untuk get data customer digunakan untuk Store DB Verif'
if ((GlobalVariable.RoleCompany == 'Testing') && (datafileCustomerCompany.getValue(GlobalVariable.NumofColm, 14) == 'LookUp')) {
    'call function get data cust'
    getDataCust()

    Select selectcustomertype = new Select(DriverFactory.getWebDriver().findElement(By.xpath('//div[@id="CustMainData"]/div/div[2]/div/div/lib-ucdropdownlist/div/select')))

    Select selectcustomermodel = new Select(DriverFactory.getWebDriver().findElement(By.xpath('//div[@id="CustMainData"]/div[2]/div[2]/div/div/lib-ucdropdownlist/div/select')))

    Select selectownership = new Select(DriverFactory.getWebDriver().findElement(By.xpath('//*[@id="Address"]/div/div[2]/div[2]/div/div/div/div/select')))

    'add customer type to array'
    GlobalVariable.Confinsdata.add(selectcustomertype.getFirstSelectedOption().getText())

    'add customer model to array'
    GlobalVariable.Confinsdata.add(selectcustomermodel.getFirstSelectedOption().getText())

    'add ownership to array'
    GlobalVariable.Confinsdata.add(selectownership.getFirstSelectedOption().getText())
}

'get customer name'
custname = WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/input_Customer Legal Name_form-control ng-untouched ng-pristine ng-invalid'), 
    'value', FailureHandling.OPTIONAL)

'add name to Global variable'
GlobalVariable.CustomerName = custname

'click button save'
WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/button_Save'))

if ((Integer.parseInt(datafileCustomerCompany.getValue(GlobalVariable.NumofColm, 4)) == 0) && (GlobalVariable.FlagFailed == 
0)) {
    'Check alert'
    GlobalVariable.FlagFailed = CustomKeywords.'checkSaveProcess.checkSaveProcess.checkAlert'(GlobalVariable.NumofColm, 
        '1.TabCustomerMainData')
	
	int genset = CustomKeywords.'dbConnection.checkCustomer.checkCustomerNegativeGenSet'(sqlConnectionLOS, custname)
	
	int negcustLOS = CustomKeywords.'dbConnection.checkCustomer.checkCustomerNegativeLOS'(sqlConnectionLOS, custname)
	
	int negcustFOU = CustomKeywords.'dbConnection.checkCustomer.checkCustomerNegativeFOU'(sqlConnectionFOU, custname)
	
	if(genset == 1 && (negcustLOS == 1 || negcustFOU == 1)){
		KeywordUtil.markFailedAndStop('Failed Because Negative Customer')
	}
}

if (GlobalVariable.FlagFailed == 0) {
    if (datafileCustomerCompany.getValue(GlobalVariable.NumofColm, 10).equalsIgnoreCase('No')) {
        'Check save Process write to excel'
        CustomKeywords.'checkSaveProcess.checkSaveProcess.checkStatus'(Integer.parseInt(datafileCustomerCompany.getValue(
                    GlobalVariable.NumofColm, 4)), findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Tableheadermanagementshareholder'), 
            GlobalVariable.NumofColm, '1.TabCustomerMainData')

        'customer added +1'
        (GlobalVariable.CountNumofCustomer)++

        if (Integer.parseInt(datafileCustomerCompany.getValue(GlobalVariable.NumofColm, 4)) == 0) {
            'Check Validasi error'
            CustomKeywords.'checkSaveProcess.checkSaveProcess.checkValidasi'(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/errorvalidasi'), 
                GlobalVariable.NumofColm, '1.TabCustomerMainData')
        }
    } else {
        if (GlobalVariable.FlagWarning == 0) {
            'Write to excel success'
            CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
                0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusSuccess)
        }
        
        'customer added +1'
        (GlobalVariable.CountNumofCustomer)++

        'verify fail'
        if (WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/ApplicationCurrentStep')), 
            'CUSTOMER', false, FailureHandling.OPTIONAL)) {
            'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.StatusFailedCopyApp'
            CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('1.TabCustomerMainData', GlobalVariable.NumofColm, 
                GlobalVariable.StatusFailed, GlobalVariable.StatusFailedCopyApp)
        }
    }
}

'verify fail'
if (WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/ApplicationCurrentStep')), 
    'CUSTOMER', false, FailureHandling.OPTIONAL)) {
    'click menu Customer main'
    WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA'))

    'customer added -1'
    (GlobalVariable.CountNumofCustomer)--

    'Pengecekan jika new consumer finance belum diexpand'
    if (WebUI.verifyElementNotVisible(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA'), FailureHandling.OPTIONAL)) {
        'Klik new consumer finance'
        WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_New Consumer Finance'))
    }
} else {
    if (GlobalVariable.FlagWarning > 0) {
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
            0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusWarning)
    }
}

if (datafileCustomerCompany.getValue(GlobalVariable.NumofColm, 14) == 'Input Data') {
    if ((GlobalVariable.RoleCompany == 'Testing') && (GlobalVariable.CheckVerifStoreDBCompany == 'Yes')) {
        'call test case verif customer store data'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP1 - CustomerData/Customer/TabCustomerDataStoreDBVerif'), 
            [:], FailureHandling.CONTINUE_ON_FAILURE)
    }
} else if (datafileCustomerCompany.getValue(GlobalVariable.NumofColm, 14) == 'LookUp') {
    if ((GlobalVariable.RoleCompany == 'Testing') && (GlobalVariable.CheckVerifStoreDBCompany == 'Yes')) {
        'call test case verif customer store data'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP1 - CustomerData/Customer/TabCustomerDataStoreDBVerif-LookUp'), 
            [:], FailureHandling.CONTINUE_ON_FAILURE)
    }
}

def getDataCust() {
    def confinsdata = []

    'add customer name to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/input_Customer Legal Name_form-control ng-untouched ng-pristine ng-invalid'), 
            'value'))

    'add tax id to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/input_Tax Id No_form-control ng-untouched ng-pristine ng-invalid'), 
            'value'))

    'add address to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/textarea_Address'), 
            'value'))

    'add RT to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/input_RT'), 
            'value'))

    'add RW to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/input_RW'), 
            'value'))

    'add zipcode to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/LabelZipcode'), 
            'value'))

    'add kelurahan to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/LabelKelurahan'), 
            'value'))

    'add kecamatan to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/LabelKecamatan'), 
            'value'))

    'add kota to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/LabelKota'), 
            'value'))

    GlobalVariable.Confinsdata = confinsdata
}

def checkVerifyEqualOrMatch(Boolean isMatch) {
    if ((isMatch == false) && (GlobalVariable.FlagFailed == 0)) {
        'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedVerifyEqualOrMatch'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('1.TabCustomerMainData', GlobalVariable.NumofColm, 
            GlobalVariable.StatusFailed, GlobalVariable.ReasonFailedVerifyEqualOrMatch)

        GlobalVariable.FlagFailed = 1
    }
}

def verifyDDL(Sql sqlConnectionFOU){
	if (GlobalVariable.RoleCompany == 'Testing') {
		'verify application step'
		checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/applicationcurrentstep')),
				'CUSTOMER', false, FailureHandling.OPTIONAL))
		
		'get cust model ddl value from db'
		ArrayList<String> custmodel = CustomKeywords.'dbConnection.checkCustomer.checkCustomerModelCompany'(sqlConnectionFOU)
		
		'get total label from ddl cust model'
		int totalddlcustmodel = WebUI.getNumberOfTotalOption(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/select_Select One Corporate  Non Corporate'))
	
		'verify total ddl cust model confins = total ddl db'
		WebUI.verifyEqual(totalddlcustmodel - 1, custmodel.size())
		
		'verify isi ddl cust model confins = db'
		if (WebUI.verifyOptionsPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/select_Select One Corporate  Non Corporate'),
			custmodel) == false) {
	
			'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDDL'
			CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('1.TabCustomerMainData', GlobalVariable.NumofColm, GlobalVariable.StatusFailed, GlobalVariable.ReasonFailedDDL + 'Customer Model')
	
			(GlobalVariable.FlagFailed)++
		}
	}
}