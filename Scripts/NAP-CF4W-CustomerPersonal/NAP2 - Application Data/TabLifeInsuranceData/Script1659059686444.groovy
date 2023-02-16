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

'get data file path'
GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'(GlobalVariable.PathPersonal)

'declare excelPathTabLifeInsurance'
excelPathTabLifeInsurance = 'NAP-'+ GlobalVariable.LOB +'-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabLifeInsuranceData'

'Klik tab life insurance'
WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/buttonTabLifeInsurance'))

GlobalVariable.FlagFailed=0

'koneksi db los'
Sql sqlConnectionLOS = CustomKeywords.'dbConnection.connectDB.connectLOS'()

'koneksi db fou'
Sql sqlConnectionFOU = CustomKeywords.'dbConnection.connectDB.connectFOU'()

'get applaststep from confins'
String appLastStep = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/label_AppLastStep'))

if(!appLastStep.equalsIgnoreCase("INSURANCE") && !appLastStep.equalsIgnoreCase("LIFE INSURANCE") && GlobalVariable.FirstTimeEntry=="Yes"){
	GlobalVariable.FirstTimeEntry = "No"
}

if (GlobalVariable.Role == 'Testing') {
    'verify application step'
    checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/ApplicationCurrentStep')), 
        'LIFE INSURANCE', false, FailureHandling.OPTIONAL))
}

'Untuk Hapus cdk-visually-hidden pada tag input, sehingga katalon dapat membaca tag input dengan benar'
def jsCode = 'return document.querySelector("input.mat-checkbox-input.cdk-visually-hidden").classList.remove("cdk-visually-hidden");'

'Custom Keyword untuk Execute Javascript'
CustomKeywords.'JsExecutor.jsExecutionFunction'(jsCode)

'Cek IsCoverLifeInsurance Tercentang atau tidak pada Excel'
if (findTestData(excelPathTabLifeInsurance).getValue(GlobalVariable.NumofColm, 12) == 'YES') {
    'Memastikan Kondisi Awal Checkbox tidak tercentang'
    if (WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabLifeInsuranceData/checkbox_coverlifeinsurance'), 
        GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
        'Mencentang IsCoverLifeInsurance'
        WebUI.check(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabLifeInsuranceData/checkbox_coverlifeinsurance'))
    }
    
	if(GlobalVariable.Role=="Testing"){
		'Ambil text original office dari confins'
		String officeName = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/label_OriginalOffice'))
	
		'declare lifeinscobranchname'
		ArrayList<WebElement> lifeInscoBranchName = new ArrayList<WebElement>()
	
		'declare countlifeinscobranch'
		Integer countLifeInscoBranch = 0
	
		'Ambil array string (text) life insco branch name dari db'
		lifeInscoBranchName = CustomKeywords.'lifeInsuranceData.checkLifeInscoBranch.checkDDLLifeInscoBranch'(sqlConnectionFOU, officeName)
	
		'Ambil nilai count life insco branch name dari db'
		countLifeInscoBranch = CustomKeywords.'lifeInsuranceData.checkLifeInscoBranch.countDDLLifeInscoBranch'(sqlConnectionFOU,
			officeName)
	
		'Verif dropdownlist life insco branch name yang muncul pada confins sesuai dengan array string life insco branch name dari db'
		if(WebUI.verifyOptionsPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabLifeInsuranceData/select_LifeInscoBranchName'),
			lifeInscoBranchName)==false){
					
			'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedVerifyRule'
			CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('9.TabLifeInsuranceData', GlobalVariable.NumofColm, GlobalVariable.StatusFailed, findTestData(excelPathTabLifeInsurance).getValue(GlobalVariable.NumofColm, 2) + ';'+GlobalVariable.ReasonFailedVerifyRule)
		
			GlobalVariable.FlagFailed=1
		}
	
		'Ambil nilai jumlah option/pilihan life insco branch name dari confins'
		Integer totalLifeInscoBranch = WebUI.getNumberOfTotalOption(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabLifeInsuranceData/select_LifeInscoBranchName'))
	
		'Verif jumlah life insco branch name yang muncul pada confins sesuai dengan jumlah life insco branch name pada db'
		if(WebUI.verifyEqual(totalLifeInscoBranch - 1, countLifeInscoBranch)==false){
		
			'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedVerifyRule'
			CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('9.TabLifeInsuranceData', GlobalVariable.NumofColm, GlobalVariable.StatusFailed, findTestData(excelPathTabLifeInsurance).getValue(GlobalVariable.NumofColm, 2) + ';'+GlobalVariable.ReasonFailedVerifyRule)
		
			GlobalVariable.FlagFailed=1
		}
		
		'get paymentmethod ddl value from db'
		ArrayList<String> paymentmethod = CustomKeywords.'lifeInsuranceData.checkLifeInscoBranch.checkDDLPremiumPaymentMethod'(sqlConnectionFOU)
		
		'get total label from ddl paymentmethod'
		int totalddlpaymentmethod = WebUI.getNumberOfTotalOption(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabLifeInsuranceData/select_PremiumPaymentMethod'))
	
		'verify total ddl paymentmethod confins = total ddl db'
		WebUI.verifyEqual(totalddlpaymentmethod - 1, paymentmethod.size())
		
		'verify isi ddl paymentmethod confins = db'
		if (WebUI.verifyOptionsPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabLifeInsuranceData/select_PremiumPaymentMethod'),
			paymentmethod) == false) {
	
			'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDDL'
			CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('9.TabLifeInsuranceData', GlobalVariable.NumofColm, GlobalVariable.StatusFailed, findTestData(excelPathTabLifeInsurance).getValue(GlobalVariable.NumofColm, 2) + ';'+GlobalVariable.ReasonFailedDDL + 'cover period')
	
			(GlobalVariable.FlagFailed)++
		}
	}
    
    'Select DropDownList Life Insco Branch Name'
    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabLifeInsuranceData/select_LifeInscoBranchName'), 
        findTestData(excelPathTabLifeInsurance).getValue(
            GlobalVariable.NumofColm, 13), false, FailureHandling.OPTIONAL)

    'Select DropDownList Premium Payment Method'
    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabLifeInsuranceData/select_PremiumPaymentMethod'), 
        findTestData(excelPathTabLifeInsurance).getValue(
            GlobalVariable.NumofColm, 14), false)

    'Cek dan Memastikan Jika Premium Payment Method = Paid in Advance & Capitalized Mix'
    if (findTestData(excelPathTabLifeInsurance).getValue(
        GlobalVariable.NumofColm, 14) == 'Paid in Advance & Capitalized Mix') {
        'Set Nilai Persentasi Capitalized Premium'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabLifeInsuranceData/input_Capitalized Premium Percentage_paidInAdvPrcnt'), 
            findTestData(excelPathTabLifeInsurance).getValue(
                GlobalVariable.NumofColm, 15))
    }
    
    'Set Notes'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabLifeInsuranceData/textarea_Notes'), 
        findTestData(excelPathTabLifeInsurance).getValue(
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
        
		'modify subject'
        modifyObjectSubject = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabLifeInsuranceData/td_SubjectType'), 
            'xpath', 'equals', (('//*[@id="gridLifeInsD"]/table/tbody/tr[' + i) + ']/td[3]'), true)

		'modify checkbox'
        modifyObjectCheckbox = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabLifeInsuranceData/input_CheckSubject'), 
            'xpath', 'equals', (('//*[@id="gridLifeInsD"]/table/tbody/tr[' + i) + ']/td[4]/input'), true)

        'Variabel text digunakan untuk menyimpan isi dari kolom Subject Type'
        String textSubject = WebUI.getText(modifyObjectSubject)

        'Ambil nilai dari excel untuk menentukan Subject Type Customer tercentang atau tidak'
        String isCustomer = findTestData(excelPathTabLifeInsurance).getValue(
            GlobalVariable.NumofColm, 18)

        'Ambil nilai dari excel untuk menentukan Subject Type Guarantor tercentang atau tidak'
        String isGuarantor = findTestData(excelPathTabLifeInsurance).getValue(
            GlobalVariable.NumofColm, 19)

        'Ambil nilai dari excel untuk menentukan Subject Type Spouse tercentang atau tidak'
        String isSpouse = findTestData(excelPathTabLifeInsurance).getValue(
            GlobalVariable.NumofColm, 20)

        if (textSubject == 'CUSTOMER') {
            if (isCustomer == 'YES') {
                'Jika belum tercentang'
                if (WebUI.verifyElementNotChecked(modifyObjectCheckbox, GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                    'Centang yang subject type Customer'
                    WebUI.check(modifyObjectCheckbox)
                }
            } else if(isCustomer=='NO'){
                'Jika sudah tercentang'
                if (WebUI.verifyElementChecked(modifyObjectCheckbox, GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                    'Uncentang yang subject type customer'
                    WebUI.uncheck(modifyObjectCheckbox)
                }
            }
        } else if (textSubject == 'GUARANTOR') {
            if (isGuarantor == 'YES') {
                'Jika belum tercentang'
                if (WebUI.verifyElementNotChecked(modifyObjectCheckbox, GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                    'Centang yang subject type Guarantor'
                    WebUI.check(modifyObjectCheckbox)
                }
            } else if (isGuarantor == 'YES PARTIAL') {
                'Jika sudah tercentang'
                if (WebUI.verifyElementChecked(modifyObjectCheckbox, GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                    'Uncentang yang subject type guarantor'
                    WebUI.uncheck(modifyObjectCheckbox)
                }
                
                def checkGuarantor = findTestData(excelPathTabLifeInsurance).getValue(
                    GlobalVariable.NumofColm, 21).split(';')

                modifyObjectSubjectName = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabLifeInsuranceData/td_SubjectName'), 
                    'xpath', 'equals', (('//*[@id="gridLifeInsD"]/table/tbody/tr[' + i) + ']/td[1]'), true)

                String textSubjectName = WebUI.getText(modifyObjectSubjectName)

                for (j = 1; j <= checkGuarantor.size(); j++) {
                    if (textSubjectName.equalsIgnoreCase(checkGuarantor[(j - 1)])) {
                        'Jika belum tercentang'
                        if (WebUI.verifyElementNotChecked(modifyObjectCheckbox, GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                            'Centang'
                            WebUI.check(modifyObjectCheckbox)
                        }
                    }
                }
            } else if(isGuarantor=='NO') {
                'Jika sudah tercentang'
                if (WebUI.verifyElementChecked(modifyObjectCheckbox, GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                    'Uncentang yang subject type guarantor'
                    WebUI.uncheck(modifyObjectCheckbox)
                }
            }
        } else if (textSubject == 'SPOUSE') {
            if (isSpouse == 'YES') {
                'Jika belum tercentang'
                if (WebUI.verifyElementNotChecked(modifyObjectCheckbox, GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                    'Centang yang subject type Spouse'
                    WebUI.check(modifyObjectCheckbox)
                }
            } else if(isSpouse=='NO'){
                'Jika sudah tercentang'
                if (WebUI.verifyElementChecked(modifyObjectCheckbox, GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                    'Uncentang yang subject type spouse'
                    WebUI.uncheck(modifyObjectCheckbox)
                }
            }
        }
    }
    
    'Set Admin Fee Amount'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabLifeInsuranceData/input_Admin Fee Amount_CustAdminFeeAmt'), 
        findTestData(excelPathTabLifeInsurance).getValue(
            GlobalVariable.NumofColm, 23))

    NumberFormat decimalFormat = NumberFormat.getPercentInstance()

	'get capitalizepremiumpercentage'
    CapitalizePremiumPercentageValue = WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabLifeInsuranceData/input_Capitalized Premium Percentage_paidInAdvPrcnt'), 
        'value', FailureHandling.OPTIONAL).replaceAll('\\s', '')

	'parsing capitalizepremiumpercentage ke desimal'
    GlobalVariable.CapitalizePremiumPercentage = decimalFormat.parse(CapitalizePremiumPercentageValue).floatValue()
} else if(findTestData(excelPathTabLifeInsurance).getValue(
    GlobalVariable.NumofColm, 12) == 'NO') {
    if (WebUI.verifyElementChecked(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabLifeInsuranceData/checkbox_coverlifeinsurance'), 
        GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
		'uncentang coverlifeinsurance'
        WebUI.uncheck(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabLifeInsuranceData/checkbox_coverlifeinsurance'))

        GlobalVariable.CapitalizePremiumPercentage = 0
    }
}

WebUI.delay(5)

'click Save'
WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabLifeInsuranceData/button_Save'))

Integer iscompleteMandatory = Integer.parseInt(findTestData(excelPathTabLifeInsurance).getValue(GlobalVariable.NumofColm, 4))

if(iscompleteMandatory==0 && GlobalVariable.FlagFailed==0){
	'cek alert'
	GlobalVariable.FlagFailed = CustomKeywords.'checkSaveProcess.checkSaveProcess.checkAlert'(GlobalVariable.NumofColm, '9.TabLifeInsuranceData')
}

if(GlobalVariable.FlagFailed==0){
	'check save process write to excel'
	CustomKeywords.'checkSaveProcess.checkSaveProcess.checkStatus'(iscompleteMandatory,
		findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/tablesubsidynodata'), GlobalVariable.NumofColm, '9.TabLifeInsuranceData')
	if(iscompleteMandatory==0){
		errorValObject = findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/div_errorvalidation')
		'cek validasi'
		CustomKeywords.'checkSaveProcess.checkSaveProcess.checkValidasi'(errorValObject, GlobalVariable.NumofColm, '9.TabLifeInsuranceData')
	}
}

'Verify input data'
if (WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/ApplicationCurrentStep')), 
    'LIFE INSURANCE', false, FailureHandling.OPTIONAL)) {
    'click cancel'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabLifeInsuranceData/button_Cancel'))

	GlobalVariable.IsDataCancel = 1
} 
else{
	if(GlobalVariable.Role == 'Testing' && GlobalVariable.CheckVerifStoreDBPersonal=="Yes" && findTestData(excelPathTabLifeInsurance).getValue(GlobalVariable.NumofColm, 12) == 'YES'){
		'call test case store db life insurance data'
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabLifeInsuranceDataStoreDBVerif'),
				[:], FailureHandling.CONTINUE_ON_FAILURE)
	}
}

public checkVerifyEqualOrMatch(Boolean isMatch){
		if(isMatch==false && GlobalVariable.FlagFailed==0){
	
			'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedVerifyEqualOrMatch'
			CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('9.TabLifeInsuranceData', GlobalVariable.NumofColm, GlobalVariable.StatusFailed, findTestData(excelPathTabLifeInsurance).getValue(GlobalVariable.NumofColm, 2) + ';'+ GlobalVariable.ReasonFailedVerifyEqualOrMatch)
			
			GlobalVariable.FlagFailed=1
		}
}

