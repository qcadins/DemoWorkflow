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
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import org.openqa.selenium.support.ui.Select as Select
import internal.GlobalVariable as GlobalVariable
import groovy.sql.Sql as Sql

int flagWarning = 0

GlobalVariable.FlagFailed = 0

'connect DB LOS'
Sql sqlConnectionLOS = CustomKeywords.'dbConnection.connectDB.connectLOS'()

'connect DB FOU'
Sql sqlConnectionFOU = CustomKeywords.'dbConnection.connectDB.connectFOU'()

'declare excelPathCustomerPersonal'
excelPathCustomerPersonal = 'NAP-'+ GlobalVariable.LOB  +'-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData'

'Klik tab customer'
WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP1-CustomerData/buttonTabCust'))

//jika role testing
if (GlobalVariable.Role == 'Testing') {
    'verify application step'
    checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/applicationcurrentstep')), 
            'CUSTOMER', false))

    'get cust model ddl value from db'
    ArrayList<String> custmodel = CustomKeywords.'dbConnection.checkCustomer.checkCustomerModelPersonal'(sqlConnectionFOU)

    'get total label from ddl cust model'
    int totalddlcustmodel = WebUI.getNumberOfTotalOption(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/select_CustomerModel'))

    'verify total ddl cust model confins = total ddl db'
    WebUI.verifyEqual(totalddlcustmodel - 1, custmodel.size())

    'verify isi ddl cust model confins = db'
    if (WebUI.verifyOptionsPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/select_CustomerModel'), 
        custmodel) == false) {
        'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDDL'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('1.TabCustomerMainData', GlobalVariable.NumofColm, GlobalVariable.StatusFailed, findTestData(excelPathCustomerPersonal).getValue(GlobalVariable.NumofColm, 2) + ';' + GlobalVariable.ReasonFailedDDL + 'Customer Model')

        (GlobalVariable.FlagFailed)++
    }
    
    'get IDType ddl value from db'
    ArrayList<String> IDType = CustomKeywords.'dbConnection.checkCustomer.checkIDTypeDDL'(sqlConnectionFOU)

    'get total label from ddl IDType'
    int totalddlIDType = WebUI.getNumberOfTotalOption(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/select_Id Type'))

    'verify total ddl IDType confins = total ddl db'
    WebUI.verifyEqual(totalddlIDType - 1, IDType.size())

    'verify isi ddl IDType confins = db'
    if (WebUI.verifyOptionsPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/select_Id Type'), 
        IDType) == false) {
        'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDDL'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('1.TabCustomerMainData', GlobalVariable.NumofColm, 
            GlobalVariable.StatusFailed, findTestData(excelPathCustomerPersonal).getValue(GlobalVariable.NumofColm, 2) + ';' + GlobalVariable.ReasonFailedDDL + 'ID Type')

        (GlobalVariable.FlagFailed)++
    }
    
    'get MaritalStatus ddl value from db'
    ArrayList<String> MaritalStatus = CustomKeywords.'dbConnection.checkCustomer.checkMaritalStatusDLL'(sqlConnectionFOU)

    'get total label from ddl MaritalStatus'
    int totalddlMaritalStatus = WebUI.getNumberOfTotalOption(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/select_MaritalStatus'))

    'verify total ddl MaritalStatus confins = total ddl db'
    WebUI.verifyEqual(totalddlMaritalStatus - 1, MaritalStatus.size())

    'verify isi ddl MaritalStatus confins = db'
    if (WebUI.verifyOptionsPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/select_MaritalStatus'), 
        MaritalStatus) == false) {
        'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDDL'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('1.TabCustomerMainData', GlobalVariable.NumofColm, 
            GlobalVariable.StatusFailed, findTestData(excelPathCustomerPersonal).getValue(GlobalVariable.NumofColm, 2) + ';' +GlobalVariable.ReasonFailedDDL + 'MaritalStatus')

        (GlobalVariable.FlagFailed)++
    }
    
    'get Gender ddl value from db'
    ArrayList<String> Gender = CustomKeywords.'dbConnection.checkCustomer.checkGenderDLLPersonal'(sqlConnectionFOU)

    'get total label from ddl Gender'
    int totalddlGender = WebUI.getNumberOfTotalOption(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/select_Gender'))

    'verify total ddl Gender confins = total ddl db'
    WebUI.verifyEqual(totalddlGender - 1, Gender.size())

    'verify isi ddl Gender confins = db'
    if (WebUI.verifyOptionsPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/select_Gender'), 
        Gender) == false) {
        'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDDL'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('1.TabCustomerMainData', GlobalVariable.NumofColm, 
            GlobalVariable.StatusFailed, findTestData(excelPathCustomerPersonal).getValue(GlobalVariable.NumofColm, 2) + ';' +GlobalVariable.ReasonFailedDDL + 'Gender')

        (GlobalVariable.FlagFailed)++
    }
}

//jika inputdata
if (findTestData(excelPathCustomerPersonal).getValue(GlobalVariable.NumofColm, 14) == 'Input Data') {
    'input customer legal name'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/input_Customer Legal Name_form-control ng-untouched ng-pristine ng-valid'), 
        findTestData(excelPathCustomerPersonal).getValue(GlobalVariable.NumofColm, 19))

    'input birth place'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/input_Birth Place_form-control ng-untouched ng-pristine ng-valid'), 
        findTestData(excelPathCustomerPersonal).getValue(GlobalVariable.NumofColm, 20))

    'select id type'
    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/select_Id Type'), 
        findTestData(excelPathCustomerPersonal).getValue(GlobalVariable.NumofColm, 21), false)

    'jika id type pada excel bukan e-ktp atau bukan npwp atau bukan akta'
    if (((findTestData(excelPathCustomerPersonal).getValue(GlobalVariable.NumofColm, 21) != 'E-KTP') && (findTestData(excelPathCustomerPersonal).getValue(
        GlobalVariable.NumofColm, 21) != 'NPWP')) && (findTestData(excelPathCustomerPersonal).getValue(GlobalVariable.NumofColm, 21) != 
    'AKTA')) {
        'input expired date'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/input_Id Expired Date_form-control ng-untouched ng-pristine ng-valid ng-star-inserted'), 
            findTestData(excelPathCustomerPersonal).getValue(GlobalVariable.NumofColm, 22))
    }
    
    'select marital status'
    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/select_MaritalStatus'), 
        findTestData(excelPathCustomerPersonal).getValue(GlobalVariable.NumofColm, 23), false)

    'input mobile phone'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/input_Mobile Phone_form-control ng-untouched ng-pristine ng-valid'), 
        findTestData(excelPathCustomerPersonal).getValue(GlobalVariable.NumofColm, 24))

    'select customer model'
    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/select_CustomerModel'), 
        findTestData(excelPathCustomerPersonal).getValue(GlobalVariable.NumofColm, 25), false)

    'select gender'
    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/select_Gender'), 
        findTestData(excelPathCustomerPersonal).getValue(GlobalVariable.NumofColm, 26), false)

    'input birth date'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/input_Birth Date_form-control ng-untouched ng-pristine ng-valid'), 
        findTestData(excelPathCustomerPersonal).getValue(GlobalVariable.NumofColm, 27))

    'input id no'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/input_Id No_form-control ng-untouched ng-pristine ng-valid'), 
        findTestData(excelPathCustomerPersonal).getValue(GlobalVariable.NumofColm, 28))

    'input tax id'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/input_Tax Id No_form-control ng-untouched ng-pristine ng-valid'), 
        findTestData(excelPathCustomerPersonal).getValue(GlobalVariable.NumofColm, 29))

    'input mother maiden name'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/input_Mother Maiden Name_form-control ng-untouched ng-pristine ng-valid'), 
        findTestData(excelPathCustomerPersonal).getValue(GlobalVariable.NumofColm, 30))

    'input email'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/input_Email_form-control ng-untouched ng-pristine ng-valid'), 
        findTestData(excelPathCustomerPersonal).getValue(GlobalVariable.NumofColm, 31))

    'click button department lookup'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/button_DEPARTMENT AML_btn btn-raised btn-primary'))

    'input department AML code'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/input_Code_MasterCodeId'), 
        findTestData(excelPathCustomerPersonal).getValue(GlobalVariable.NumofColm, 33))

    'click button search'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/button_Search'))

    'verify input error'
    CustomKeywords.'customizeKeyword.function.verifyInputLookup'(findTestData(excelPathCustomerPersonal), 
        '1.TabCustomerMainData', GlobalVariable.NumofColm)

    'jika authority aml tidak kosong'
    if (findTestData(excelPathCustomerPersonal).getValue(GlobalVariable.NumofColm, 36).length() > 1) {
        'click authority AML Lookup'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/button_Authority AML_btn btn-raised btn-primary'))

        'input authority AML code'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/input_Code_MasterCodeId'), 
            findTestData(excelPathCustomerPersonal).getValue(GlobalVariable.NumofColm, 35))

        'click button search'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/button_Search'))

        'verify input error'
        if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/a_Select'), 
            GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
            'click select'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/a_Select'))
        } else {
            'click X'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabNewApplication/Button_X'))

            flagWarning++
        }
    }
    
    'input address'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/textarea_Address'), findTestData(excelPathCustomerPersonal).getValue(
            GlobalVariable.NumofColm, 38))

    'input RT'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/input_RT'), findTestData(excelPathCustomerPersonal).getValue(
            GlobalVariable.NumofColm, 39))

    'input RW'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/input_RW'), findTestData(excelPathCustomerPersonal).getValue(
            GlobalVariable.NumofColm, 40))

    'click button zip code'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/button_Zipcode_btn btn-raised btn-primary'))

    'input Zipcode'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/input_Zipcode_form-control ng-untouched ng-pristine ng-valid'), 
        findTestData(excelPathCustomerPersonal).getValue(GlobalVariable.NumofColm, 41))

    'input kecamatan'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/input_Kecamatan'), findTestData(excelPathCustomerPersonal).getValue(
            GlobalVariable.NumofColm, 42))

    'input kelurahan'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/input_Kelurahan'), findTestData(excelPathCustomerPersonal).getValue(
            GlobalVariable.NumofColm, 43))

    'input kota'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/input_Kota'), findTestData(excelPathCustomerPersonal).getValue(
            GlobalVariable.NumofColm, 44))

    'click button search'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/button_SearchZipcode'))

    'verify input error'
    CustomKeywords.'customizeKeyword.function.verifyInputLookup'(findTestData(excelPathCustomerPersonal), 
        '1.TabCustomerMainData', GlobalVariable.NumofColm)

    'select ownership'
    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/select_Ownership'), 
        findTestData(excelPathCustomerPersonal).getValue(GlobalVariable.NumofColm, 45), false //Verif data lookup db dengan yang muncul di confins
        //Ambil nilai terbaru dari confins untuk verif store db lookup
        ) //jika lookup
    //getdata cust
} else if (findTestData(excelPathCustomerPersonal).getValue(GlobalVariable.NumofColm, 14) == 'LookUp') {
    'click lookup button customer'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/button_Customer Legal Name_btn btn-raised btn-primary'))

    'input cust id'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/input_Customer No_custNoId'), 
        findTestData(excelPathCustomerPersonal).getValue(GlobalVariable.NumofColm, 16))

    'input custname'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/input_Customer Name_custNameId'), 
        findTestData(excelPathCustomerPersonal).getValue(GlobalVariable.NumofColm, 17))

    'input Id No'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/input_Id No_IdNoId'), findTestData(excelPathCustomerPersonal).getValue(
            GlobalVariable.NumofColm, 18))

    'click button search'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/button_Search (1)'))

    'verify input error'
    CustomKeywords.'customizeKeyword.function.verifyInputLookup'(findTestData(excelPathCustomerPersonal), 
        '1.TabCustomerMainData', GlobalVariable.NumofColm)

    if (GlobalVariable.Role == 'Testing' && GlobalVariable.FlagFailed == 0) {
        getDataCust()

        'call test case customer data verif'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP1 - Customer Data/Customer/TabCustomerDataVerif'), 
            [:], FailureHandling.CONTINUE_ON_FAILURE)
    }
    
    'jika customer model tidak kosong'
    if (findTestData(excelPathCustomerPersonal).getValue(GlobalVariable.NumofColm, 25).length() > 1) {
        'select customer model'
        WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/select_CustomerModel'), 
            findTestData(excelPathCustomerPersonal).getValue(GlobalVariable.NumofColm, 25), false)
    }
    
    'jika department aml tidak kosong'
    if (findTestData(excelPathCustomerPersonal).getValue(GlobalVariable.NumofColm, 34).length() > 1) {
        'click button department lookup'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/button_DEPARTMENT AML_btn btn-raised btn-primary'))

        'input department AML code'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/input_Code_MasterCodeId'), 
            findTestData(excelPathCustomerPersonal).getValue(GlobalVariable.NumofColm, 33))

        'click button search'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/button_Search'))

        'verify input error'
        CustomKeywords.'customizeKeyword.function.verifyInputLookup'(findTestData(excelPathCustomerPersonal), 
            '1.TabCustomerMainData', GlobalVariable.NumofColm)
    }
    
    'jika authority aml tidak kosong'
    if (findTestData(excelPathCustomerPersonal).getValue(GlobalVariable.NumofColm, 36).length() > 1) {
        'click authority AML Lookup'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/button_Authority AML_btn btn-raised btn-primary'))

        'input authority AML code'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/input_Code_MasterCodeId'), 
            findTestData(excelPathCustomerPersonal).getValue(GlobalVariable.NumofColm, 35))

        'click button search'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/button_Search'))

        'verify input error'
        if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/a_Select'), 
            GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
            'click select'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/a_Select'))
        } else {
            'click X'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabNewApplication/Button_X'))

            flagWarning++
        }
    }
    
    if (GlobalVariable.Role == 'Testing' && GlobalVariable.FlagFailed == 0) {
        'call function getdatacust'
        getDataCust()
    }
}

'get customer name'
custname = WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/input_Customer Legal Name_form-control ng-untouched ng-pristine ng-valid'), 
    'value', FailureHandling.OPTIONAL)

'add name to Global variable'
GlobalVariable.CustomerName = custname

'click button save'
WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/button_Save'))

Integer iscompleteMandatory = Integer.parseInt(findTestData(excelPathCustomerPersonal).getValue(GlobalVariable.NumofColm, 4))

if ((iscompleteMandatory == 0) && (GlobalVariable.FlagFailed == 0)) {
    'cek error alert muncul/tidak'
    GlobalVariable.FlagFailed = CustomKeywords.'checkSaveProcess.checkSaveProcess.checkAlert'(GlobalVariable.NumofColm, 
        '1.TabCustomerMainData')

    int genset = CustomKeywords.'dbConnection.checkCustomer.checkCustomerNegativeGenSet'(sqlConnectionLOS)

    int negcustLOS = CustomKeywords.'dbConnection.checkCustomer.checkCustomerNegativeLOS'(sqlConnectionLOS, custname)

    int negcustFOU = CustomKeywords.'dbConnection.checkCustomer.checkCustomerNegativeFOU'(sqlConnectionFOU, custname)

    if ((genset == 1) && ((negcustLOS == 1) || (negcustFOU == 1))) {
        KeywordUtil.markFailedAndStop('Failed Because Negative Customer')
    }
}

if (GlobalVariable.FlagFailed == 0) {
    if (findTestData(excelPathCustomerPersonal).getValue(GlobalVariable.NumofColm, 10).equalsIgnoreCase('No')) {
        'check save process write to excel'
        CustomKeywords.'checkSaveProcess.checkSaveProcess.checkStatus'(iscompleteMandatory, findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabFamilyData/TableFamilyHeader'), 
            GlobalVariable.NumofColm, '1.TabCustomerMainData')

        'customer added +1'
        (GlobalVariable.CountNumofCustomer)++

        if (iscompleteMandatory == 0) {
            errorValObject = findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/div_errorvalidation')

            'cek validasi'
            CustomKeywords.'checkSaveProcess.checkSaveProcess.checkValidasi'(errorValObject, GlobalVariable.NumofColm, '1.TabCustomerMainData')
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
        if (WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/ApplicationCurrentStep')), 
            'CUSTOMER', false, FailureHandling.OPTIONAL)) {
            'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.StatusFailedCopyApp'
            CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('1.TabCustomerMainData', GlobalVariable.NumofColm, 
                GlobalVariable.StatusFailed, findTestData(excelPathCustomerPersonal).getValue(GlobalVariable.NumofColm, 2) + ';' +GlobalVariable.StatusFailedCopyApp)
        }
    }
}

'verify fail'
if (WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/ApplicationCurrentStep')), 
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
	GlobalVariable.IsDataCancel = 1
} else {
    if (flagWarning > 0) {
        'write to excel status warning'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
            0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusWarning)
    }
	if (findTestData(excelPathCustomerPersonal).getValue(GlobalVariable.NumofColm, 14) == 'Input Data') {
		if ((GlobalVariable.Role == 'Testing') && (GlobalVariable.CheckVerifStoreDBPersonal == 'Yes')) {
			'call test case customer data store verif'
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP1 - Customer Data/Customer/TabCustomerDataStoreDBVerif'),
				[:], FailureHandling.CONTINUE_ON_FAILURE)
		}
	} else if (findTestData(excelPathCustomerPersonal).getValue(GlobalVariable.NumofColm, 14) == 'LookUp') {
		if ((GlobalVariable.Role == 'Testing') && (GlobalVariable.CheckVerifStoreDBPersonal == 'Yes')) {
			'call test case customer data store verif lookup'
			WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP1 - Customer Data/Customer/TabCustomerDataStoreDBVerif-LookUp'),
				[:], FailureHandling.CONTINUE_ON_FAILURE)
		}
	}
}



GlobalVariable.FlagFailed = 0

def getDataCust() {
    'declare confinsdata'
    def confinsdata = []

    'add customer name to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/input_Customer Legal Name_form-control ng-untouched ng-pristine ng-valid'), 
            'value'))

    'add birth place to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/input_Birth Place_form-control ng-untouched ng-pristine ng-valid'), 
            'value'))

    'add id expired date to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/input_Id Expired Date_form-control ng-untouched ng-pristine ng-valid ng-star-inserted'), 
            'value'))

    'add mobile phone to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/input_Mobile Phone_form-control ng-untouched ng-pristine ng-valid'), 
            'value'))

    'add birth date to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/input_Birth Date_form-control ng-untouched ng-pristine ng-valid'), 
            'value'))

    'add id no to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/input_Id No_form-control ng-untouched ng-pristine ng-valid'), 
            'value'))

    'add tax id to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/input_Tax Id No_form-control ng-untouched ng-pristine ng-valid'), 
            'value'))

    'add mother maiden name to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/input_Mother Maiden Name_form-control ng-untouched ng-pristine ng-valid'), 
            'value'))

    'add email to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/input_Email_form-control ng-untouched ng-pristine ng-valid'), 
            'value'))

    'add address to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/textarea_Address'), 
            'value'))

    'add RT to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/input_RT'), 
            'value'))

    'add RW to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/input_RW'), 
            'value'))

    'add zipcode to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/input_zipcodeLookUp'), 
            'value'))

    'add kelurahan to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/LabelKelurahan'), 
            'value'))

    'add kecamatan to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/LabelKecamatan'), 
            'value'))

    'add kota to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/LabelKota'), 
            'value'))

    'add id type to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/select_Select IdType'), 
            'value'))

    'add marital status to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/select_MaritalStatus'), 
            'value'))

    'add customer model to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/select_CustomerModel'), 
            'value'))

    'add gender to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/select_Gender'), 
            'value'))

    'add ownership to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/select_Ownership'), 
            'value'))

    GlobalVariable.Confinsdata = confinsdata
}

def getAddressData() {
    'get address'
    GlobalVariable.LookupAddressData.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/textarea_Address'), 
            'value'))

    'get RT'
    GlobalVariable.LookupAddressData.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/input_RT'), 
            'value'))

    'get RW'
    GlobalVariable.LookupAddressData.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/input_RW'), 
            'value'))

    'get zipcode'
    GlobalVariable.LookupAddressData.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/input_zipcodeLookUp'), 
            'value'))

    'get kelurahan'
    GlobalVariable.LookupAddressData.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/LabelKelurahan'), 
            'value'))

    'get kecamatan'
    GlobalVariable.LookupAddressData.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/LabelKecamatan'), 
            'value'))

    'get kota'
    GlobalVariable.LookupAddressData.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/LabelKota'), 
            'value'))

    Select selectOwnership = new Select(DriverFactory.getWebDriver().findElement(By.xpath('//div[@id="Address"]/div/div[2]/div[2]/div/div/div/div/select')))

    'get ownership'
    GlobalVariable.LookupAddressData.add(selectOwnership.getFirstSelectedOption().getText())
}

def checkVerifyEqualOrMatch(Boolean isMatch) {
    if ((isMatch == false) && (GlobalVariable.FlagFailed == 0)) {
        'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedVerifyEqualOrMatch'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('1.TabCustomerMainData', GlobalVariable.NumofColm, 
            GlobalVariable.StatusFailed, findTestData(excelPathCustomerPersonal).getValue(GlobalVariable.NumofColm, 2) + ';' +GlobalVariable.ReasonFailedVerifyEqualOrMatch)

        GlobalVariable.FlagFailed = 1
    }
}

