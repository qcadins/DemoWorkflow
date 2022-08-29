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

Sql sqlConnectionLOS = CustomKeywords.'dbconnection.connectDB.connect'(url, username, password, driverclassname)

Sql sqlConnectionFOU = CustomKeywords.'dbconnection.connectDB.connect'(urlFOU, username, password, driverclassname)

if (GlobalVariable.Role == 'Testing') {
    'verify application step'
    WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/ApplicationCurrentStep')), 
        'LIFE INSURANCE', false, FailureHandling.OPTIONAL)
}

'Untuk Hapus cdk-visually-hidden pada tag input, sehingga katalon dapat membaca tag input dengan benar'
def jsCode = 'return document.querySelector("input.mat-checkbox-input.cdk-visually-hidden").classList.remove("cdk-visually-hidden");'

'Custom Keyword untuk Execute Javascript'
CustomKeywords.'JsExecutor.jsExecutionFunction'(jsCode)

'Cek IsCoverLifeInsurance Tercentang atau tidak pada Excel'
if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabLifeInsuranceData').getValue(
    GlobalVariable.NumofColm, 3) == 'YES') {
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
		lifeInscoBranchName = CustomKeywords.'dbconnection.checkLifeInscoBranch.checkDDLLifeInscoBranch'(sqlConnectionFOU, officeName)
	
		'Ambil nilai count life insco branch name dari db'
		countLifeInscoBranch = CustomKeywords.'dbconnection.checkLifeInscoBranch.countDDLLifeInscoBranch'(sqlConnectionFOU,
			officeName)
	
		'Verif dropdownlist life insco branch name yang muncul pada confins sesuai dengan array string life insco branch name dari db'
		WebUI.verifyOptionsPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabLifeInsuranceData/select_LifeInscoBranchName'),
			lifeInscoBranchName)
	
		'Ambil nilai jumlah option/pilihan life insco branch name dari confins'
		Integer totalLifeInscoBranch = WebUI.getNumberOfTotalOption(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabLifeInsuranceData/select_LifeInscoBranchName'))
	
		'Verif jumlah life insco branch name yang muncul pada confins sesuai dengan jumlah life insco branch name pada db'
		WebUI.verifyEqual(totalLifeInscoBranch - 1, countLifeInscoBranch)
	}
    
    'Select DropDownList Life Insco Branch Name'
    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabLifeInsuranceData/select_LifeInscoBranchName'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabLifeInsuranceData').getValue(
            GlobalVariable.NumofColm, 4), false, FailureHandling.OPTIONAL)

    'Select DropDownList Premium Payment Method'
    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabLifeInsuranceData/select_PremiumPaymentMethod'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabLifeInsuranceData').getValue(
            GlobalVariable.NumofColm, 5), false)

    'Cek dan Memastikan Jika Premium Payment Method = Paid in Advance & Capitalized Mix'
    if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabLifeInsuranceData').getValue(
        GlobalVariable.NumofColm, 5) == 'Paid in Advance & Capitalized Mix') {
        'Set Nilai Persentasi Capitalized Premium'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabLifeInsuranceData/input_Capitalized Premium Percentage_paidInAdvPrcnt'), 
            findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabLifeInsuranceData').getValue(
                GlobalVariable.NumofColm, 6))
    }
    
    'Set Notes'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabLifeInsuranceData/textarea_Notes'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabLifeInsuranceData').getValue(
            GlobalVariable.NumofColm, 7))

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
            GlobalVariable.NumofColm, 9)

        'Ambil nilai dari excel untuk menentukan Subject Type Guarantor tercentang atau tidak'
        String isGuarantor = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabLifeInsuranceData').getValue(
            GlobalVariable.NumofColm, 10)

        'Ambil nilai dari excel untuk menentukan Subject Type Spouse tercentang atau tidak'
        String isSpouse = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabLifeInsuranceData').getValue(
            GlobalVariable.NumofColm, 11)

        if (textSubject == 'CUSTOMER') {
            if (isCustomer == 'YES') {
                'Jika belum tercentang'
                if (WebUI.verifyElementNotChecked(modifyObjectCheckbox, 1, FailureHandling.OPTIONAL)) {
                    'Centang yang subject type Customer'
                    WebUI.check(modifyObjectCheckbox)
                }
            } else {
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
                    GlobalVariable.NumofColm, 12).split(';')

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
            } else {
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
            } else {
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
            GlobalVariable.NumofColm, 14))

    NumberFormat decimalFormat = NumberFormat.getPercentInstance()

    CapitalizePremiumPercentageValue = WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabLifeInsuranceData/input_Capitalized Premium Percentage_paidInAdvPrcnt'), 
        'value', FailureHandling.OPTIONAL).replaceAll('\\s', '')

    GlobalVariable.CapitalizePremiumPercentage = decimalFormat.parse(CapitalizePremiumPercentageValue).floatValue()
} else {
    if (WebUI.verifyElementChecked(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabLifeInsuranceData/checkbox_coverlifeinsurance'), 
        1, FailureHandling.OPTIONAL)) {
        WebUI.uncheck(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabLifeInsuranceData/checkbox_coverlifeinsurance'))

        GlobalVariable.CapitalizePremiumPercentage = 0
    }
}

WebUI.delay(5)

'click Save'
WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabLifeInsuranceData/button_Save'))

'Verify input data'
if (WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/ApplicationCurrentStep')), 
    'LIFE INSURANCE', false, FailureHandling.OPTIONAL)) {
    'click cancel'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabLifeInsuranceData/button_Cancel'))

    'write to excel failed'
    CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '9.TabLifeInsuranceData', 
        0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)
} else {
    'write to excel success'
    CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '9.TabLifeInsuranceData', 
        0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusSuccess)
}

