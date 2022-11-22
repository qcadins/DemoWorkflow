import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import java.text.NumberFormat as NumberFormat
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
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.WebElement as WebElement
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.By as By
import org.openqa.selenium.JavascriptExecutor as JavascriptExecutor
import groovy.sql.Sql as Sql

'Assign directori file excel ke global variabel'
String userDir = System.getProperty('user.dir')

'Assign directori file excel ke global variabel'
String filePath = userDir + GlobalVariable.PathPersonal

'Assign directori file excel ke global variabel'
GlobalVariable.DataFilePath = filePath

 GlobalVariable.FlagFailed=0
'Koneksi database'
String servername = findTestData('Login/Login').getValue(1, 8)

String instancename = findTestData('Login/Login').getValue(2, 8)

String username = findTestData('Login/Login').getValue(3, 8)

String password = findTestData('Login/Login').getValue(4, 8)

String database = findTestData('Login/Login').getValue(5, 9)

String databaseFOU = findTestData('Login/Login').getValue(5, 7)

String driverclassname = findTestData('Login/Login').getValue(6, 8)

String url = (((servername + ';instanceName=') + instancename) + ';databaseName=') + database

String urlFOU = (((servername + ';instanceName=') + instancename) + ';databaseName=') + databaseFOU

Sql sqlConnectionLOS = CustomKeywords.'dbConnection.connectDB.connect'(url, username, password, driverclassname)

Sql sqlConnectionFOU = CustomKeywords.'dbConnection.connectDB.connect'(urlFOU, username, password, driverclassname)

String appLastStep = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/label_AppLastStep'))

if(!appLastStep.equalsIgnoreCase("INSURANCE") && GlobalVariable.FirstTimeEntry=="Yes"){
	GlobalVariable.FirstTimeEntry = "No"
}

if (GlobalVariable.Role == 'Testing') {
    'verify application step'
    checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/ApplicationCurrentStep')), 
        'LIFE INSURANCE', false, FailureHandling.OPTIONAL))
}

'Untuk Hapus cdk-visually-hidden pada tag input, sehingga katalon dapat membaca tag input dengan benar'
def jsCode = 'return document.querySelector("input.mat-checkbox-input.cdk-visually-hidden").classList.remove("cdk-visually-hidden");'

'Custom Keyword untuk Execute Javascript'
CustomKeywords.'JsExecutor.jsExecutionFunction'(jsCode)

'Cek IsCoverLifeInsurance Tercentang atau tidak pada Excel'
if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabLifeInsuranceData').getValue(
    GlobalVariable.NumofColm, 12) == 'YES') {
    'Memastikan Kondisi Awal Checkbox tidak tercentang'
    if (WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabLifeInsuranceData/checkbox_coverlifeinsurance'), 
        1, FailureHandling.OPTIONAL)) {
        'Mencentang IsCoverLifeInsurance'
        WebUI.check(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabLifeInsuranceData/checkbox_coverlifeinsurance'))
    }
    
	if(GlobalVariable.Role=="Testing"){
		'Ambil text original office dari confins'
		String officeName = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/label_OriginalOffice'))
	
		ArrayList<WebElement> lifeInscoBranchName = new ArrayList<WebElement>()
	
		Integer countLifeInscoBranch = 0
	
		'Ambil array string (text) life insco branch name dari db'
		lifeInscoBranchName = CustomKeywords.'lifeInsuranceData.checkLifeInscoBranch.checkDDLLifeInscoBranch'(sqlConnectionFOU, officeName)
	
		'Ambil nilai count life insco branch name dari db'
		countLifeInscoBranch = CustomKeywords.'lifeInsuranceData.checkLifeInscoBranch.countDDLLifeInscoBranch'(sqlConnectionFOU,
			officeName)
	
		'Verif dropdownlist life insco branch name yang muncul pada confins sesuai dengan array string life insco branch name dari db'
		if(WebUI.verifyOptionsPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabLifeInsuranceData/select_LifeInscoBranchName'),
			lifeInscoBranchName)==false){
			'write to excel failed'
			CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '9.TabLifeInsuranceData', 0,
			GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)
		
			'Write To Excel GlobalVariable.StatusReason'
			CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '9.TabLifeInsuranceData',
			1, GlobalVariable.NumofColm - 1, GlobalVariable.ReasonFailedDDL)
		
			GlobalVariable.FlagFailed=1
		}
	
		'Ambil nilai jumlah option/pilihan life insco branch name dari confins'
		Integer totalLifeInscoBranch = WebUI.getNumberOfTotalOption(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabLifeInsuranceData/select_LifeInscoBranchName'))
	
		'Verif jumlah life insco branch name yang muncul pada confins sesuai dengan jumlah life insco branch name pada db'
		if(WebUI.verifyEqual(totalLifeInscoBranch - 1, countLifeInscoBranch)==false){
			'write to excel failed'
			CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '9.TabLifeInsuranceData', 0,
			GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)
		
			'Write To Excel GlobalVariable.StatusReason'
			CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '9.TabLifeInsuranceData',
			1, GlobalVariable.NumofColm - 1, GlobalVariable.ReasonFailedDDL)
		
			GlobalVariable.FlagFailed=1
		}
	}
    
    'Select DropDownList Life Insco Branch Name'
    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabLifeInsuranceData/select_LifeInscoBranchName'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabLifeInsuranceData').getValue(
            GlobalVariable.NumofColm, 13), false, FailureHandling.OPTIONAL)

    'Select DropDownList Premium Payment Method'
    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabLifeInsuranceData/select_PremiumPaymentMethod'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabLifeInsuranceData').getValue(
            GlobalVariable.NumofColm, 14), false)

    'Cek dan Memastikan Jika Premium Payment Method = Paid in Advance & Capitalized Mix'
    if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabLifeInsuranceData').getValue(
        GlobalVariable.NumofColm, 14) == 'Paid in Advance & Capitalized Mix') {
        'Set Nilai Persentasi Capitalized Premium'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabLifeInsuranceData/input_Capitalized Premium Percentage_paidInAdvPrcnt'), 
            findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabLifeInsuranceData').getValue(
                GlobalVariable.NumofColm, 15))
    }
    
    'Set Notes'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabLifeInsuranceData/textarea_Notes'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabLifeInsuranceData').getValue(
            GlobalVariable.NumofColm, 16))

    'Inisialisasi Driver'
    WebDriver driver = DriverFactory.getWebDriver()

    'Inisialisasi Variabel'
    ArrayList<WebElement> variable = driver.findElements(By.cssSelector('#gridLifeInsD > table > tbody tr'))

    //dimana css_selector_name adalah elemen dari parent atas object yang ingin dilacak, dan div tergantung daripada bentuk element html tersebut
    'Menghitung count (size dari variabel) yang akan digunakan sebagai total banyaknya data pada life insurance subject'
    int count = variable.size()

    'Looping data life insurance subject'
    for (int i = 1; i <= count; i++) {
        String newxpathSubject

        String newxpathcheckbox

        newxpathSubject = (('//*[@id="gridLifeInsD"]/table/tbody/tr[' + i) + ']/td[3]')

        newxpathcheckbox = (('//*[@id="gridLifeInsD"]/table/tbody/tr[' + i) + ']/td[4]/input')

        modifyObjectSubject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabLifeInsuranceData/td_SubjectType'), 
            'xpath', 'equals', newxpathSubject, true)

        modifyObjectCheckbox = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabLifeInsuranceData/input_CheckSubject'), 
            'xpath', 'equals', newxpathcheckbox, true)

        'Variabel text digunakan untuk menyimpan isi dari kolom Subject Type'
        String textSubject = WebUI.getText(modifyObjectSubject)

        'Ambil nilai dari excel untuk menentukan Subject Type Customer tercentang atau tidak'
        String isCustomer = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabLifeInsuranceData').getValue(
            GlobalVariable.NumofColm, 18)

        'Ambil nilai dari excel untuk menentukan Subject Type Guarantor tercentang atau tidak'
        String isGuarantor = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabLifeInsuranceData').getValue(
            GlobalVariable.NumofColm, 19)

        'Ambil nilai dari excel untuk menentukan Subject Type Spouse tercentang atau tidak'
        String isSpouse = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabLifeInsuranceData').getValue(
            GlobalVariable.NumofColm, 20)

        if (textSubject == 'CUSTOMER') {
            if (isCustomer == 'YES') {
                'Jika belum tercentang'
                if (WebUI.verifyElementNotChecked(modifyObjectCheckbox, 1, FailureHandling.OPTIONAL)) {
                    'Centang yang subject type Customer'
                    WebUI.check(modifyObjectCheckbox)
                }
            } else if(isCustomer=='NO'){
                'Jika sudah tercentang'
                if (WebUI.verifyElementChecked(modifyObjectCheckbox, 1, FailureHandling.OPTIONAL)) {
                    'Uncentang yang subject type customer'
                    WebUI.uncheck(modifyObjectCheckbox)
                }
            }
        } else if (textSubject == 'GUARANTOR') {
            if (isGuarantor == 'YES') {
                'Jika belum tercentang'
                if (WebUI.verifyElementNotChecked(modifyObjectCheckbox, 1, FailureHandling.OPTIONAL)) {
                    'Centang yang subject type Guarantor'
                    WebUI.check(modifyObjectCheckbox)
                }
            } else if (isGuarantor == 'YES PARTIAL') {
                'Jika sudah tercentang'
                if (WebUI.verifyElementChecked(modifyObjectCheckbox, 1, FailureHandling.OPTIONAL)) {
                    'Uncentang yang subject type guarantor'
                    WebUI.uncheck(modifyObjectCheckbox)
                }
                
                def checkGuarantor = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabLifeInsuranceData').getValue(
                    GlobalVariable.NumofColm, 21).split(';')

                newxpathSubjectName = (('//*[@id="gridLifeInsD"]/table/tbody/tr[' + i) + ']/td[1]')

                modifyObjectSubjectName = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabLifeInsuranceData/td_SubjectName'), 
                    'xpath', 'equals', newxpathSubjectName, true)

                String textSubjectName = WebUI.getText(modifyObjectSubjectName)

                for (j = 1; j <= checkGuarantor.size(); j++) {
                    if (textSubjectName.equalsIgnoreCase(checkGuarantor[(j - 1)])) {
                        'Jika belum tercentang'
                        if (WebUI.verifyElementNotChecked(modifyObjectCheckbox, 1, FailureHandling.OPTIONAL)) {
                            'Centang'
                            WebUI.check(modifyObjectCheckbox)
                        }
                    }
                }
            } else if(isGuarantor=='NO') {
                'Jika sudah tercentang'
                if (WebUI.verifyElementChecked(modifyObjectCheckbox, 1, FailureHandling.OPTIONAL)) {
                    'Uncentang yang subject type guarantor'
                    WebUI.uncheck(modifyObjectCheckbox)
                }
            }
        } else if (textSubject == 'SPOUSE') {
            if (isSpouse == 'YES') {
                'Jika belum tercentang'
                if (WebUI.verifyElementNotChecked(modifyObjectCheckbox, 1, FailureHandling.OPTIONAL)) {
                    'Centang yang subject type Spouse'
                    WebUI.check(modifyObjectCheckbox)
                }
            } else if(isSpouse=='NO'){
                'Jika sudah tercentang'
                if (WebUI.verifyElementChecked(modifyObjectCheckbox, 1, FailureHandling.OPTIONAL)) {
                    'Uncentang yang subject type spouse'
                    WebUI.uncheck(modifyObjectCheckbox)
                }
            }
        }
    }
    
    'Set Admin Fee Amount'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabLifeInsuranceData/input_Admin Fee Amount_CustAdminFeeAmt'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabLifeInsuranceData').getValue(
            GlobalVariable.NumofColm, 23))

    NumberFormat decimalFormat = NumberFormat.getPercentInstance()

    CapitalizePremiumPercentageValue = WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabLifeInsuranceData/input_Capitalized Premium Percentage_paidInAdvPrcnt'), 
        'value', FailureHandling.OPTIONAL).replaceAll('\\s', '')

    GlobalVariable.CapitalizePremiumPercentage = decimalFormat.parse(CapitalizePremiumPercentageValue).floatValue()
} else if(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabLifeInsuranceData').getValue(
    GlobalVariable.NumofColm, 12) == 'NO') {
    if (WebUI.verifyElementChecked(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabLifeInsuranceData/checkbox_coverlifeinsurance'), 
        1, FailureHandling.OPTIONAL)) {
        WebUI.uncheck(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabLifeInsuranceData/checkbox_coverlifeinsurance'))

        GlobalVariable.CapitalizePremiumPercentage = 0
    }
}

WebUI.delay(5)

'click Save'
WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabLifeInsuranceData/button_Save'))

Integer iscompleteMandatory = Integer.parseInt(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabLifeInsuranceData').getValue(GlobalVariable.NumofColm, 4))

if(iscompleteMandatory==0 && GlobalVariable.FlagFailed==0){
	'cek alert'
	GlobalVariable.FlagFailed = CustomKeywords.'checkSaveProcess.checkSaveProcess.checkAlert'(GlobalVariable.NumofColm, '9.TabLifeInsuranceData')
}

if(GlobalVariable.FlagFailed==0){
	'check save process write to excel'
	CustomKeywords.'checkSaveProcess.checkSaveProcess.checkStatus'(iscompleteMandatory,
		findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/tablesubsidynodata'), GlobalVariable.NumofColm, '9.TabLifeInsuranceData')
	if(iscompleteMandatory==0){
		errorValObject = findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/div_errorvalidation')
		'cek validasi'
		CustomKeywords.'checkSaveProcess.checkSaveProcess.checkValidasi'(errorValObject, GlobalVariable.NumofColm, '9.TabLifeInsuranceData')
	}
}

if(GlobalVariable.Role == 'Testing' && GlobalVariable.CheckVerifStoreDBPersonal=="Yes"){
	'call test case store db life insurance data'
	WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabLifeInsuranceDataStoreDBVerif'),
			[:], FailureHandling.CONTINUE_ON_FAILURE)
}

'Verify input data'
if (WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/ApplicationCurrentStep')), 
    'LIFE INSURANCE', false, FailureHandling.OPTIONAL)) {
    'click cancel'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabLifeInsuranceData/button_Cancel'))

   
} 

public checkVerifyEqualOrMatch(Boolean isMatch){
		if(isMatch==false && GlobalVariable.FlagFailed==0){
			CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '9.TabLifeInsuranceData',
					0, GlobalVariable.NumofColm-1, GlobalVariable.StatusFailed)
	
			CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '9.TabLifeInsuranceData',
					1, GlobalVariable.NumofColm-1, GlobalVariable.ReasonFailedVerifyEqualOrMatch)
	
			GlobalVariable.FlagFailed=1
		}
}

