import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import org.openqa.selenium.By as By
import org.openqa.selenium.support.ui.Select as Select
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
import internal.GlobalVariable as GlobalVariable
import groovy.sql.Sql as Sql

'get data file path'
GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'(GlobalVariable.PathPersonal)

'declare datafileTabApplication'
datafileTabApplication = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabApplicationData')

GlobalVariable.FlagFailed = 0

'koneksi database los'
Sql sqlConnectionLOS = CustomKeywords.'dbConnection.connectDB.connectLOS'()

'koneksi database fou'
Sql sqlConnectionFOU = CustomKeywords.'dbConnection.connectDB.connectFOU'()

'get applaststep from confins'
String appLastStep = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/label_AppLastStep'))

if(!appLastStep.equalsIgnoreCase("REFERANTOR") && GlobalVariable.FirstTimeEntry=="Yes"){
	GlobalVariable.FirstTimeEntry = "No"
}

if (GlobalVariable.Role == 'Testing') {
    'verify application step'
    checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/ApplicationCurrentStep')), 
        'APPLICATION DATA', false, FailureHandling.OPTIONAL))

    'Ambil text product offering dari confins'
    String POName = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/label_ProductOffering'))

    'Pengecekan interest type dari db product offering '
    String InterestType = CustomKeywords.'applicationData.checkInterestType.checkInterest'(sqlConnectionLOS, POName)

    'Ambil text interest type dari confins'
    String textInterest = WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/select_InterestType'), 
        'value')

    'Verif interest type pada confins dengan db'
    checkVerifyEqualOrMatch(WebUI.verifyMatch(textInterest, '(?i)' + InterestType, true))
}

'declare spvname'
String spvName

'Pengecekan job title pada excel cmo atau bukan'
if (findTestData('Login/Login').getValue(5, 2).toLowerCase().contains('Credit Marketing Officer'.toLowerCase())) {
    if (GlobalVariable.Role == 'Testing') {
		
		'Ambil nilai username dari confins'
		String[] userLogin = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/label_userLogin')).replace(
			' ', '').replace('|', ';').split(';')
		
		String usernameLogin = userLogin[0]
		
        'Ambil text label officer dari confins'
        String textOfficer = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/label_Officer'))

        'Cek nama officer pada db dari username login confins'
        String officerName = CustomKeywords.'applicationData.checkOfficer.checkOfficerName'(sqlConnectionFOU, usernameLogin)

        'Verif username login dengan text label officer'
        checkVerifyEqualOrMatch(WebUI.verifyMatch(textOfficer, '(?i)' + officerName, true))

        'Ambil nama spv dari db'
        spvName = CustomKeywords.'applicationData.checkOfficer.checkSPV'(sqlConnectionFOU, usernameLogin)

        'Pengecekan jika nama spv dari db = null'
        if (spvName == null) {
            'Ubah hasil nama spv dari db menjadi -'
            spvName = '-'
        }
        
        'Ambil text spv dari confins'
        String textSPV = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/label_SPV'))

        'Verif text spv dari confins sesuai dengan nama spv dari db'
        checkVerifyEqualOrMatch(WebUI.verifyMatch(textSPV, '(?i)' + spvName, true))
    }
} else {

    'Pengecekan jika button lookup ada'
    if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/button_MOOfficer'), 
        10, FailureHandling.OPTIONAL)) {
		
		'Ambil text original office dari confins'
		String office = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/label_OriginalOffice'))
	
        'Click Lookup Officer'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/button_MOOfficer'))

        if (GlobalVariable.Role == 'Testing') {
            'Click Search'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/button_Search'))

            'Cek total data officer pada db'
            Integer countOfficer = CustomKeywords.'applicationData.checkOfficer.countOfficerLookup'(sqlConnectionFOU, office)

            'Ambil nilai total data officer pada lookup confins'
            String[] textTotalDataOfficer = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/label_TotalDataOfficer')).replace(
                ' ', '').replace(':', ';').split(';')

            'Parsing nilai total data officer confins ke integer(angka)'
            Integer totalDataOfficer = Integer.parseInt(textTotalDataOfficer[1])

            'Verif total data officer confins sesuai dengan db'
            if(WebUI.verifyEqual(totalDataOfficer, countOfficer)==false){
			
				'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDataLookup'
				CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('6.TabApplicationData', GlobalVariable.NumofColm, GlobalVariable.StatusFailed, GlobalVariable.ReasonFailedDataLookup)
				
				GlobalVariable.FlagFailed=1
			}
        }
        
        'Input MO Code'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/input_MO Code'), 
            datafileTabApplication.getValue(
                GlobalVariable.NumofColm, 12))

        'Input MO Head Name'
        WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/input_MO Head Name'), 
            datafileTabApplication.getValue(
                GlobalVariable.NumofColm, 13))

        'Input MO Office Name'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/input_MOOfficeName'), 
            datafileTabApplication.getValue(
                GlobalVariable.NumofColm, 14))

        'Click Search'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/button_Search'))

        'verify input error'
        if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/a_Select'), 
            10, FailureHandling.OPTIONAL)) {
            if (GlobalVariable.Role == 'Testing') {
                'Ambil nama spv pada lookup confins'
                spvName = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/span_SPVLookup'))

                'Pengecekan jika mo officer yang dipilih dari lookup spvnya kosong'
                if (spvName == '') {
                    spvName = '-'
                }
            }
            
            'Click Select'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/a_Select'), 
                FailureHandling.OPTIONAL)

            if (GlobalVariable.Role == 'Testing') {
                'Ambil nama spv pada confins'
                String textSPV = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/label_LookupSPV'))

                'Verif nama spv pada lookup yang diselect sama dengan yang muncul pada tab application data confins'
                checkVerifyEqualOrMatch(WebUI.verifyMatch(textSPV, '(?i)' + spvName, true))
            }
        } else {
            'click X'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/Button_X'))

            'click cancel'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/button_Cancel'))

			'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDataLookup'
			CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('6.TabApplicationData', GlobalVariable.NumofColm, GlobalVariable.StatusFailed, GlobalVariable.ReasonFailedDataLookup)

            GlobalVariable.FlagFailed = 1

            'Pengecekan jika new consumer finance belum diexpand'
            if (WebUI.verifyElementNotVisible(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA'), FailureHandling.OPTIONAL)) {
                'Klik new consumer finance'
                WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_New Consumer Finance'))
            }
        }
    }
}

'Input MO Notes'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/textarea_MONotes'), 
    datafileTabApplication.getValue(
        GlobalVariable.NumofColm, 15))

'Select option dropdownlist App Source'
WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/select_ApplicationSource'), 
    datafileTabApplication.getValue(
        GlobalVariable.NumofColm, 17), false, FailureHandling.OPTIONAL)

'Select option dropdownlist First Installment Type'
WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/select_FirstInstallmentType'), 
    datafileTabApplication.getValue(
        GlobalVariable.NumofColm, 18), false)

'Inisialisasi Variabel'
String payFreq = datafileTabApplication.getValue(
    GlobalVariable.NumofColm, 19)

'Select option dropdownlist Payment Frequency'
WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/select_PaymentFrequency'), 
    payFreq, false)

'Inisialisasi Variabel'
String tenorString = datafileTabApplication.getValue(
    GlobalVariable.NumofColm, 20)

'Input tenor'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/input_Tenor'), 
    tenorString)

if (GlobalVariable.Role == 'Testing') {
    'Inisialisasi Variabel'
    double tenor = Double.parseDouble(tenorString)

    'Inisialisasi Variabel'
    int numOfInstallment

    'Verify numofinstallment berdasarkan tenor dan payment Frequency'
    if (payFreq == 'Monthly') {
        numOfInstallment = ((Math.ceil(tenor / 1)) as int)
    } else if (payFreq == 'Bimonthly') {
        numOfInstallment = ((Math.ceil(tenor / 2)) as int)
    } else if (payFreq == 'Quarterly') {
        numOfInstallment = ((Math.ceil(tenor / 3)) as int)
    } else if (payFreq == 'Trimester') {
        numOfInstallment = ((Math.ceil(tenor / 4)) as int)
    } else if (payFreq == 'Semi Annually') {
        numOfInstallment = ((Math.ceil(tenor / 6)) as int)
    } else if (payFreq == 'Annually') {
        numOfInstallment = ((Math.ceil(tenor / 12)) as int)
    } else if (payFreq == 'Weekly') {
        numOfInstallment = (((((4) as int) * tenor) + (Math.round(tenor - 1) / 3)) + 1)
    }
    
    'Click label numofInstallment untuk merefresh numofinstallment'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/label_NumOfInstallment'))

    'Verify numofinstallment sesuai perhitungan'
    WebUI.verifyElementText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/label_NumOfInstallment'), 
        numOfInstallment.toString())
}

if (datafileTabApplication.getValue(
    GlobalVariable.NumofColm, 21).length() > 0) {
    'Select option dropdownlist DPSourcePaymentType'
    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/select_DPSourcePaymentType'), 
        datafileTabApplication.getValue(
            GlobalVariable.NumofColm, 21), false)
}

'get interest type from confins'
String textInterestType = WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/select_InterestType'), 
    'value', FailureHandling.OPTIONAL)

//'Select option dropdownlist Interest Type'
//WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/select_InterestType'), 
//    textInterestType, false)
//CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '6.TabApplicationData',
//	21, GlobalVariable.NumofColm - 1, textInterestType)

'def object scheme'
scheme = findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/select_InstallmentScheme')

'def object wop'
wop = findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/select_WayOfPayment')

'def object notif'
notif = findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/select_CustomerNotification')

'def object inssource'
inssource = findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/select_InstallmentSourcePaymentType')

'Verify/Jika Interest Type = Float Rate'
if (textInterestType.equalsIgnoreCase('Float Rate')) {
    'Select option dropdownlist Floating Period'
    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/select_FloatingPeriod'), 
        datafileTabApplication.getValue(
            GlobalVariable.NumofColm, 23), false)
}

'Select option dropdownlist installment scheme'
WebUI.selectOptionByLabel(scheme, datafileTabApplication.getValue(
        GlobalVariable.NumofColm, 24), false)

'get wop from excel'
textwop = datafileTabApplication.getValue(
    GlobalVariable.NumofColm, 25)

'Select option dropdownlist Way of Payment'
WebUI.selectOptionByLabel(wop, textwop, false)

//'Verify/Jika Way of Payment = Auto Debit'
//if (textwop == 'Auto Debit') {
//    bankacc = findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/select_CustomerBankAccount')
//
//    'Select option dropdownlist Customer Bank Account'
//    WebUI.selectOptionByIndex(bankacc, datafileTabApplication.getValue(
//            GlobalVariable.NumofColm, 26), FailureHandling.OPTIONAL)
//
//    Select select = new Select(DriverFactory.getWebDriver().findElement(By.xpath('//select[@formcontrolname = \'CustBankAcc\']')))
//
//    GlobalVariable.BankAccount = select.getFirstSelectedOption().getText()
//}

'Select option dropdownlist Customer Notification By'
WebUI.selectOptionByLabel(notif, datafileTabApplication.getValue(
        GlobalVariable.NumofColm, 27), false)

if (datafileTabApplication.getValue(
    GlobalVariable.NumofColm, 28).length() > 0) {
    'Select option dropdownlist Installment Source Payment'
    WebUI.selectOptionByLabel(inssource, datafileTabApplication.getValue(
            GlobalVariable.NumofColm, 28), false)
}

'Jika/Verify Copy Address From ada isi/tidak kosong pada excel'
if (datafileTabApplication.getValue(
    GlobalVariable.NumofColm, 30).length() > 1) {
    'Select option dropdownlist Copy Address From '
    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/select_CopyAddressFrom'), 
        datafileTabApplication.getValue(
            GlobalVariable.NumofColm, 30), false)

    'Click copy'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/button_Copy'))
} else {
    'Input Address'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/textarea_Address'), 
        datafileTabApplication.getValue(
            GlobalVariable.NumofColm, 31))

    'Input RT'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/input_RT'), 
        datafileTabApplication.getValue(
            GlobalVariable.NumofColm, 32))

    'Input RW'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/input_RW'), 
        datafileTabApplication.getValue(
            GlobalVariable.NumofColm, 33))

    'Click Lookup Zipcode'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/button_Zipcode'))

    'Input Zipcode'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/input_ZipCodeLookup'), 
        datafileTabApplication.getValue(
            GlobalVariable.NumofColm, 34))

    'Input Kecamatan'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/input_Kecamatan_kecamatan'), 
        datafileTabApplication.getValue(
            GlobalVariable.NumofColm, 35))

    'Input Kelurahan'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/input_Kelurahan_Kelurahan'), 
        datafileTabApplication.getValue(
            GlobalVariable.NumofColm, 36))

    'Input Kota'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/input_Kota_kota'), 
        datafileTabApplication.getValue(
            GlobalVariable.NumofColm, 37))

    'Click search'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/button_Search'))

	'verify input error'
	CustomKeywords.'Function.verifyInputErrorLookUp'(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/button_Cancel'), '6.TabApplicationData', GlobalVariable.NumofColm)
}

'Input Phone 1 Area'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/input_Phone 1 area'), 
    datafileTabApplication.getValue(
        GlobalVariable.NumofColm, 38))

'Input Phone 1 Number'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/input_Phone 1 number'), 
    datafileTabApplication.getValue(
        GlobalVariable.NumofColm, 39))

'Input Phone 1 Extension'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/input_Phone 1 extension'), 
    datafileTabApplication.getValue(
        GlobalVariable.NumofColm, 40))

'Input Phone 2 Area'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/input_Phone 2 area'), 
    datafileTabApplication.getValue(
        GlobalVariable.NumofColm, 41))

'Input Phone 2 Number'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/input_Phone 2 number'), 
    datafileTabApplication.getValue(
        GlobalVariable.NumofColm, 42))

'Input Phone 2 Extension'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/input_Phone 2 extension'), 
    datafileTabApplication.getValue(
        GlobalVariable.NumofColm, 43))

'Input Phone 3 Area'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/input_Phone 3 area'), 
    datafileTabApplication.getValue(
        GlobalVariable.NumofColm, 44))

'Input Phone 3 Number'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/input_Phone 3 number'), 
    datafileTabApplication.getValue(
        GlobalVariable.NumofColm, 45))

'Input Phone 3 Extension'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/input_Phone 3 extension'), 
    datafileTabApplication.getValue(
        GlobalVariable.NumofColm, 46))

'Input Fax Area'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/input_Fax area'), 
    datafileTabApplication.getValue(
        GlobalVariable.NumofColm, 47))

'Input Fax Number'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/input_Fax number'), 
    datafileTabApplication.getValue(
        GlobalVariable.NumofColm, 48))

'get coc from excel'
String coc = datafileTabApplication.getValue(
    GlobalVariable.NumofColm, 50)

'declare object slikbtn'
slikbtn = findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/button_Economic Sector (Slik)')

'Select option dropdownlist characteristic of credit'
WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/select_CharacteristicOfCredit'), 
    coc, false)

'Verify/Jika coc selain lainnya'
if (coc != 'Lainnya') {
    'Input prev agreement no'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/input_PrevAgreementNo'), 
        datafileTabApplication.getValue(
            GlobalVariable.NumofColm, 51))

    'Select option dropdownlist way of restructure '
    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/select_WayOfRestructure'), 
        datafileTabApplication.getValue(
            GlobalVariable.NumofColm, 52), false)
}

'Click lookup slik'
WebUI.click(slikbtn)

'Input Economic Sector Code'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/input_Economic Sector Code'), 
    datafileTabApplication.getValue(
        GlobalVariable.NumofColm, 53))

'Click Search'
WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/button_Search'))

'verify input error'
		CustomKeywords.'Function.verifyInputErrorLookUp'(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/button_Cancel'), '6.TabApplicationData', GlobalVariable.NumofColm)

'Select option dropdownlist Blacklist APPI'
WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/select_BlackListAppi'), 
    datafileTabApplication.getValue(
        GlobalVariable.NumofColm, 56), false)

'Input APPI Score'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/input_APPI SCORE'), 
    datafileTabApplication.getValue(
        GlobalVariable.NumofColm, 57))

'Input Date APP DATA'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/input_DATE APP DATA'), 
    datafileTabApplication.getValue(
        GlobalVariable.NumofColm, 58))

'Click Lookup REF MASTER APP DATA'
WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/button_REF MASTER APP DATA'))

'Input Code REF MASTER APP DATA'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/input_Code_RefMasterAppData'), 
    datafileTabApplication.getValue(
        GlobalVariable.NumofColm, 59))

'Click Search'
WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/button_Search'))

'verify input error'
		CustomKeywords.'Function.verifyInputErrorLookUp'(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/button_Cancel'), '6.TabApplicationData', GlobalVariable.NumofColm)

'Input Jumlah Asset'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/input_JUMLAH ASSET'), 
    datafileTabApplication.getValue(
        GlobalVariable.NumofColm, 61))

WebUI.delay(3)

'click Save'
WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/button_Save'))

Integer iscompleteMandatory = Integer.parseInt(datafileTabApplication.getValue(
        GlobalVariable.NumofColm, 4))

if (iscompleteMandatory == 0 && GlobalVariable.FlagFailed==0) {
    'cek alert'
    GlobalVariable.FlagFailed = CustomKeywords.'checkSaveProcess.checkSaveProcess.checkAlert'(GlobalVariable.NumofColm, '6.TabApplicationData')
}

WebUI.delay(5)

if (GlobalVariable.FlagFailed == 0) {
    'check save process write to excel'
    CustomKeywords.'checkSaveProcess.checkSaveProcess.checkStatus'(iscompleteMandatory, findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabAssetData/button_Asset Name_btn btn-raised btn-primary'), 
        GlobalVariable.NumofColm, '6.TabApplicationData')

    if (iscompleteMandatory == 0) {
        errorValObject = findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/div_errorvalidation')

        'cek validasi'
        CustomKeywords.'checkSaveProcess.checkSaveProcess.checkValidasi'(errorValObject, GlobalVariable.NumofColm, '6.TabApplicationData')
    }
}

'Verify input data'
if (WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/ApplicationCurrentStep')), 
    'APPLICATION DATA', false, FailureHandling.OPTIONAL)) {
    'click cancel'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/button_Cancel'))
}

if(GlobalVariable.Role == 'Testing' && GlobalVariable.CheckVerifStoreDBPersonal=="Yes"){
		'call test case store db application data'
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabApplicationDataStoreDBVerif'),
				[:], FailureHandling.CONTINUE_ON_FAILURE)
}

public checkVerifyEqualOrMatch(Boolean isMatch){
	if(isMatch==false && GlobalVariable.FlagFailed==0){
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '6.TabApplicationData',
				0, GlobalVariable.NumofColm-1, GlobalVariable.StatusFailed)

		CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '6.TabApplicationData',
				1, GlobalVariable.NumofColm-1, GlobalVariable.ReasonFailedVerifyEqualOrMatch)

		GlobalVariable.FlagFailed=1
	}
}