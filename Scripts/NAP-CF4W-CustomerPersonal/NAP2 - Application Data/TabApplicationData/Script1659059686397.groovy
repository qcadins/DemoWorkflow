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
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement

def modifyNewAppNo, modifyNewAgrNo, modifyNewCustName

ArrayList<String> crossfaileddelete = new ArrayList<>()

ArrayList<Integer> posAddCross = new ArrayList<>()

'get data file path'
GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'(GlobalVariable.PathPersonal)

'declare datafileTabApplication'
datafileTabApplication = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabApplicationData')

'Klik tab application'
WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/buttonTabApplication'))

datafileLogin = findTestData('Login/Login')

GlobalVariable.FlagFailed = 0

'koneksi database los'
Sql sqlConnectionLOS = CustomKeywords.'dbConnection.connectDB.connectLOS'()

'koneksi database fou'
Sql sqlConnectionFOU = CustomKeywords.'dbConnection.connectDB.connectFOU'()

'get applaststep from confins'
String appLastStep = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/label_AppLastStep'))

if(!appLastStep.equalsIgnoreCase("REFERANTOR") && GlobalVariable.FirstTimeEntry=="Yes"){
	GlobalVariable.FirstTimeEntry = "No"
}

if (GlobalVariable.Role == 'Testing') {
    'verify application step'
    checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/ApplicationCurrentStep')), 
        'APPLICATION DATA', false, FailureHandling.OPTIONAL))

    'Ambil text product offering dari confins'
    String POName = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/label_ProductOffering'))

    'Pengecekan interest type dari db product offering '
    String InterestType = CustomKeywords.'applicationData.checkInterestType.checkInterest'(sqlConnectionLOS, POName)

    'Ambil text interest type dari confins'
    String textInterest = WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/select_InterestType'), 
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
		String[] userLogin = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/label_userLogin')).replace(
			' ', '').replace('|', ';').split(';')
		
		String usernameLogin = userLogin[0]
		
        'Ambil text label officer dari confins'
        String textOfficer = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/label_Officer'))

        'Cek nama officer pada db dari username login confins'
        String officerName = CustomKeywords.'applicationData.checkOfficer.checkOfficerName'(sqlConnectionFOU, usernameLogin)

        'Verif username login dengan text label officer'
        checkVerifyEqualOrMatch(WebUI.verifyMatch(textOfficer, '(?i)' + officerName, true))
		
//		String officename = datafileLogin.getValue(4,2)
//		
//		String jobtitlerole = datafileLogin.getValue(5,2)

        'Ambil nama spv dari db'
        spvName = CustomKeywords.'applicationData.checkOfficer.checkSPV'(sqlConnectionFOU, usernameLogin)

        'Pengecekan jika nama spv dari db = null'
        if (spvName == null) {
            'Ubah hasil nama spv dari db menjadi -'
            spvName = '-'
        }
        
        'Ambil text spv dari confins'
        String textSPV = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/label_SPV'))

        'Verif text spv dari confins sesuai dengan nama spv dari db'
        checkVerifyEqualOrMatch(WebUI.verifyMatch(textSPV, '(?i)' + spvName, true))
    }
} else {

    'Pengecekan jika button lookup ada'
    if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/button_MOOfficer'), 
        GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
		
		'Ambil text original office dari confins'
		String office = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/label_OriginalOffice'))
	
        'Click Lookup Officer'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/button_MOOfficer'))

        if (GlobalVariable.Role == 'Testing') {
            'Click Search'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/button_Search'))

            'Cek total data officer pada db'
            Integer countOfficer = CustomKeywords.'applicationData.checkOfficer.countOfficerLookup'(sqlConnectionFOU, office)

            'Ambil nilai total data officer pada lookup confins'
            String[] textTotalDataOfficer = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/label_TotalDataOfficer')).replace(
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
        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/input_MO Code'), 
            datafileTabApplication.getValue(
                GlobalVariable.NumofColm, 12))

        'Input MO Head Name'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/input_MO Head Name'), 
            datafileTabApplication.getValue(
                GlobalVariable.NumofColm, 13))

        'Input MO Office Name'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/input_MOOfficeName'), 
            datafileTabApplication.getValue(
                GlobalVariable.NumofColm, 14))

        'Click Search'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/button_Search'))

        'verify input error'
        if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/a_Select'), 
            GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
            if (GlobalVariable.Role == 'Testing') {
                'Ambil nama spv pada lookup confins'
                spvName = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/span_SPVLookup'))

                'Pengecekan jika mo officer yang dipilih dari lookup spvnya kosong'
                if (spvName == '') {
                    spvName = '-'
                }
            }
            
            'Click Select'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/a_Select'), 
                FailureHandling.OPTIONAL)

            if (GlobalVariable.Role == 'Testing') {
                'Ambil nama spv pada confins'
                String textSPV = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/label_LookupSPV'))

                'Verif nama spv pada lookup yang diselect sama dengan yang muncul pada tab application data confins'
                checkVerifyEqualOrMatch(WebUI.verifyMatch(textSPV, '(?i)' + spvName, true))
            }
        } else {
            'click X'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/Button_X'))

            'click cancel'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/button_Cancel'))

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
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/textarea_MONotes'), 
    datafileTabApplication.getValue(
        GlobalVariable.NumofColm, 15))

'Select option dropdownlist App Source'
WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/select_ApplicationSource'), 
    datafileTabApplication.getValue(
        GlobalVariable.NumofColm, 17), false, FailureHandling.OPTIONAL)

'Select option dropdownlist First Installment Type'
WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/select_FirstInstallmentType'), 
    datafileTabApplication.getValue(
        GlobalVariable.NumofColm, 18), false)

'Inisialisasi Variabel'
String payFreq = datafileTabApplication.getValue(
    GlobalVariable.NumofColm, 19)

'Select option dropdownlist Payment Frequency'
WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/select_PaymentFrequency'), 
    payFreq, false)

'Inisialisasi Variabel'
String tenorString = datafileTabApplication.getValue(
    GlobalVariable.NumofColm, 20)

'Input tenor'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/input_Tenor'), 
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
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/label_NumOfInstallment'))

    'Verify numofinstallment sesuai perhitungan'
    WebUI.verifyElementText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/label_NumOfInstallment'), 
        numOfInstallment.toString())
}

if (datafileTabApplication.getValue(
    GlobalVariable.NumofColm, 21).length() > 0) {
    'Select option dropdownlist DPSourcePaymentType'
    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/select_DPSourcePaymentType'), 
        datafileTabApplication.getValue(
            GlobalVariable.NumofColm, 21), false)
}

'get interest type from confins'
String textInterestType = WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/select_InterestType'), 
    'value', FailureHandling.OPTIONAL)

//'Select option dropdownlist Interest Type'
//WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/select_InterestType'), 
//    textInterestType, false)
//CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '6.TabApplicationData',
//	21, GlobalVariable.NumofColm - 1, textInterestType)

'def object scheme'
scheme = findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/select_InstallmentScheme')

'def object wop'
wop = findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/select_WayOfPayment')

'def object notif'
notif = findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/select_CustomerNotification')

'def object inssource'
inssource = findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/select_InstallmentSourcePaymentType')

'Verify/Jika Interest Type = Float Rate'
if (textInterestType.equalsIgnoreCase('Float Rate')) {
    'Select option dropdownlist Floating Period'
    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/select_FloatingPeriod'), 
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
//    bankacc = findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/select_CustomerBankAccount')
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

//Cross App/Agr	
def appCross = datafileTabApplication.getValue(GlobalVariable.NumofColm, 63)
def agrCross = datafileTabApplication.getValue(GlobalVariable.NumofColm, 64)
def custCross = datafileTabApplication.getValue(GlobalVariable.NumofColm, 65)

'declare driver'
WebDriver driver = DriverFactory.getWebDriver()

'declare variable'
ArrayList<WebElement> variable = driver.findElements(By.cssSelector('#crossInfomation > div:nth-child(2) > table > tbody > tr'))

if(appCross.length()>0 && agrCross.length()>0 && custCross.length()>0){
	appCrossArr = appCross.split(";",-1)
	agrCrossArr = agrCross.split(";",-1)
	custCrossArr = custCross.split(";",-1)
	
	if (WebUI.verifyNotMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/tablenodata'),FailureHandling.OPTIONAL),
		'NO DATA AVAILABLE', false, FailureHandling.OPTIONAL)) {
		for (int i = 1; i <= variable.size(); i++) {
			'modify object application no'
			modifyNewAppNo = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/ModifyCross'),
				'xpath', 'equals', "//*[@id='crossInfomation']/div[2]/table/tbody/tr["+i+"]/td[1]", true)
	 
			'modify object agreement no'
			modifyNewAgrNo = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/ModifyCrossAgr'),
				'xpath', 'equals', "//*[@id='crossInfomation']/div[2]/table/tbody/tr["+i+"]/td[2]", true)
	 
			'modify object cust name'
			modifyNewCustName = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/ModifyCrossCust'),
				'xpath', 'equals', "//*[@id='crossInfomation']/div[2]/table/tbody/tr["+i+"]/td[3]", true)
	
			for (int crossarray = 1; crossarray <= appCrossArr.size(); crossarray++) {
				if (((WebUI.getText(modifyNewAppNo).equalsIgnoreCase(appCrossArr[(crossarray - 1)]) && WebUI.getText(
					modifyNewAgrNo).equalsIgnoreCase(agrCrossArr[(crossarray - 1)])) && WebUI.getText(modifyNewCustName).equalsIgnoreCase(
					custCrossArr[(crossarray - 1)])) ) {
				
					break
				} else {
				
					'modify object button Delete'
					modifyNewButtonDelete = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/buttonDelete'),
							'xpath', 'equals', "//*[@id='crossInfomation']/div[2]/table/tbody/tr["+i+"]/td[4]/a/i", true)
					
					if (crossarray == appCrossArr.size()) {
						if (WebUI.verifyNotMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/tablenodata'),FailureHandling.OPTIONAL),
								'NO DATA AVAILABLE', false, FailureHandling.OPTIONAL)) {
							
							'get agreement no before'
							agrnobefore = WebUI.getText(modifyNewAgrNo)
									
							'click button delete'
							WebUI.click(modifyNewButtonDelete, FailureHandling.OPTIONAL)
							
							'accept alert'
							WebUI.acceptAlert(FailureHandling.OPTIONAL)
									
							if(i == variable.size()){
									if (WebUI.verifyElementPresent(modifyNewAgrNo, 5, FailureHandling.OPTIONAL)) {
										'add cust name failed kedalam array'
										crossfaileddelete.add(agrnobefore)
									}
							}else{
									'get cust name sebelum delete'
									agrNoAfter = WebUI.getText(modifyNewAgrNo)
												
									if (WebUI.verifyMatch(agrNoAfter, agrnobefore, false, FailureHandling.OPTIONAL)) {
										'add cust name failed kedalam array'
										crossfaileddelete.add(agrnobefore)
									}
							}
							
							i--
						} else {
							break
						}
					}
				}
			}
			
			'check if table cross application/agreement sudah kosong'
			variable = driver.findElements(By.cssSelector('#crossInfomation > div:nth-child(2) > table > tbody > tr'))
			
			if(variable.size() == 1){
				break
			}
		}
	}
	
	'Jika ada delete cross app/ agr yang gagal'
	if(crossfaileddelete.size() > 0){
		
		'Write To Excel GlobalVariable.StatusWarning and GlobalVariable.ReasonFailedDelete'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('6.TabApplicationData', GlobalVariable.StartIndex, GlobalVariable.StatusWarning, GlobalVariable.ReasonFailedDelete + crossfaileddelete)
		
		GlobalVariable.FlagWarning++
	}
	
	'declare variabledata'
	ArrayList<WebElement> variableData = DriverFactory.getWebDriver().findElements(By.cssSelector('#crossInfomation > div:nth-child(2) > table > tbody > tr'))
	
	'declare countdata'
	int countData = variableData.size()
	
	'looping cross app excel'
	for (int s = 1; s <= appCrossArr.size(); s++) {
		'looping cross app confins'
		for (int CrossAppCheck = 1; CrossAppCheck <= countData; CrossAppCheck++) {
			if (WebUI.verifyNotMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/tablenodata'),FailureHandling.OPTIONAL),
				'NO DATA AVAILABLE', false, FailureHandling.OPTIONAL)) {
	
				'modify object application no'
				modifyNewAppNo = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/ModifyCross'),
					'xpath', 'equals', "//*[@id='crossInfomation']/div[2]/table/tbody/tr["+CrossAppCheck+"]/td[1]", true)
	 
				'modify object agreement no'
				modifyNewAgrNo = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/ModifyCrossAgr'),
					'xpath', 'equals', "//*[@id='crossInfomation']/div[2]/table/tbody/tr["+CrossAppCheck+"]/td[2]", true)
				
				'modify object cust name'
				modifyNewCustName = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/ModifyCrossCust'),
					'xpath', 'equals', "//*[@id='crossInfomation']/div[2]/table/tbody/tr["+CrossAppCheck+"]/td[3]", true)
	
				if (((!((appCrossArr[(s - 1)]).equalsIgnoreCase(WebUI.getText(modifyNewAppNo))) || !((agrCrossArr[
				(s - 1)]).equalsIgnoreCase(WebUI.getText(modifyNewAgrNo)))) || !((custCrossArr[(s - 1)]).equalsIgnoreCase(
					WebUI.getText(modifyNewCustName)))) ) {
					if (countData == CrossAppCheck) {
						'add posisi data crossapp pada excel ke arraylist'
						posAddCross.add(s-1)
					}
				} else {
					break
				}
			} else if (WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/tablenodata'),FailureHandling.OPTIONAL),
				'NO DATA AVAILABLE', false, FailureHandling.OPTIONAL)) {
				if (datafileTabApplication.getValue(GlobalVariable.NumofColm, 63) != '') {
					if (appCrossArr.size() > 0) {
						'add posisi data crossapp pada excel ke arraylist'
						posAddCross.add(s-1)
					}
				}
			}
		}
	}
	
	
}

addCrossAppAgr(posAddCross,sqlConnectionLOS)
	
'Jika/Verify Copy Address From ada isi/tidak kosong pada excel'
if (datafileTabApplication.getValue(
    GlobalVariable.NumofColm, 30).length() > 1) {
    'Select option dropdownlist Copy Address From '
    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/select_CopyAddressFrom'), 
        datafileTabApplication.getValue(
            GlobalVariable.NumofColm, 30), false)

    'Click copy'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/button_Copy'))
} else {
    'Input Address'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/textarea_Address'), 
        datafileTabApplication.getValue(
            GlobalVariable.NumofColm, 31))

    'Input RT'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/input_RT'), 
        datafileTabApplication.getValue(
            GlobalVariable.NumofColm, 32))

    'Input RW'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/input_RW'), 
        datafileTabApplication.getValue(
            GlobalVariable.NumofColm, 33))

    'Click Lookup Zipcode'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/button_Zipcode'))

    'Input Zipcode'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/input_ZipCodeLookup'), 
        datafileTabApplication.getValue(
            GlobalVariable.NumofColm, 34))

    'Input Kecamatan'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/input_Kecamatan_kecamatan'), 
        datafileTabApplication.getValue(
            GlobalVariable.NumofColm, 35))

    'Input Kelurahan'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/input_Kelurahan_Kelurahan'), 
        datafileTabApplication.getValue(
            GlobalVariable.NumofColm, 36))

    'Input Kota'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/input_Kota_kota'), 
        datafileTabApplication.getValue(
            GlobalVariable.NumofColm, 37))

    'Click search'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/button_Search'))

	'verify input error'
	CustomKeywords.'customizeKeyword.function.verifyInputLookup'(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/button_Cancel'), '6.TabApplicationData', GlobalVariable.NumofColm)
}

'Input Phone 1 Area'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/input_Phone 1 area'), 
    datafileTabApplication.getValue(
        GlobalVariable.NumofColm, 38))

'Input Phone 1 Number'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/input_Phone 1 number'), 
    datafileTabApplication.getValue(
        GlobalVariable.NumofColm, 39))

'Input Phone 1 Extension'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/input_Phone 1 extension'), 
    datafileTabApplication.getValue(
        GlobalVariable.NumofColm, 40))

'Input Phone 2 Area'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/input_Phone 2 area'), 
    datafileTabApplication.getValue(
        GlobalVariable.NumofColm, 41))

'Input Phone 2 Number'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/input_Phone 2 number'), 
    datafileTabApplication.getValue(
        GlobalVariable.NumofColm, 42))

'Input Phone 2 Extension'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/input_Phone 2 extension'), 
    datafileTabApplication.getValue(
        GlobalVariable.NumofColm, 43))

'Input Phone 3 Area'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/input_Phone 3 area'), 
    datafileTabApplication.getValue(
        GlobalVariable.NumofColm, 44))

'Input Phone 3 Number'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/input_Phone 3 number'), 
    datafileTabApplication.getValue(
        GlobalVariable.NumofColm, 45))

'Input Phone 3 Extension'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/input_Phone 3 extension'), 
    datafileTabApplication.getValue(
        GlobalVariable.NumofColm, 46))

'Input Fax Area'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/input_Fax area'), 
    datafileTabApplication.getValue(
        GlobalVariable.NumofColm, 47))

'Input Fax Number'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/input_Fax number'), 
    datafileTabApplication.getValue(
        GlobalVariable.NumofColm, 48))

'get coc from excel'
String coc = datafileTabApplication.getValue(
    GlobalVariable.NumofColm, 50)

'declare object slikbtn'
slikbtn = findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/button_Economic Sector (Slik)')

'Select option dropdownlist characteristic of credit'
WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/select_CharacteristicOfCredit'), 
    coc, false)

'Verify/Jika coc selain lainnya'
if (coc != 'Lainnya') {
    'Input prev agreement no'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/input_PrevAgreementNo'), 
        datafileTabApplication.getValue(
            GlobalVariable.NumofColm, 51))

    'Select option dropdownlist way of restructure '
    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/select_WayOfRestructure'), 
        datafileTabApplication.getValue(
            GlobalVariable.NumofColm, 52), false)
}

'Click lookup slik'
WebUI.click(slikbtn)

'Input Economic Sector Code'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/input_Economic Sector Code'), 
    datafileTabApplication.getValue(
        GlobalVariable.NumofColm, 53))

'Click Search'
WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/button_Search'))

'verify input error'
		CustomKeywords.'customizeKeyword.function.verifyInputLookup'(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/button_Cancel'), '6.TabApplicationData', GlobalVariable.NumofColm)

'Select option dropdownlist Blacklist APPI'
WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/select_BlackListAppi'), 
    datafileTabApplication.getValue(
        GlobalVariable.NumofColm, 56), false)

'Input APPI Score'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/input_APPI SCORE'), 
    datafileTabApplication.getValue(
        GlobalVariable.NumofColm, 57))

'Input Date APP DATA'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/input_DATE APP DATA'), 
    datafileTabApplication.getValue(
        GlobalVariable.NumofColm, 58))

'Click Lookup REF MASTER APP DATA'
WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/button_REF MASTER APP DATA'))

'Input Code REF MASTER APP DATA'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/input_Code_RefMasterAppData'), 
    datafileTabApplication.getValue(
        GlobalVariable.NumofColm, 59))

'Click Search'
WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/button_Search'))

'verify input error'
		CustomKeywords.'customizeKeyword.function.verifyInputLookup'(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/button_Cancel'), '6.TabApplicationData', GlobalVariable.NumofColm)

'Input Jumlah Asset'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/input_JUMLAH ASSET'), 
    datafileTabApplication.getValue(
        GlobalVariable.NumofColm, 61))

WebUI.delay(3)

'click Save'
WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/button_Save'))

Integer iscompleteMandatory = Integer.parseInt(datafileTabApplication.getValue(
        GlobalVariable.NumofColm, 4))

if (iscompleteMandatory == 0 && GlobalVariable.FlagFailed==0) {
    'cek alert'
    GlobalVariable.FlagFailed = CustomKeywords.'checkSaveProcess.checkSaveProcess.checkAlert'(GlobalVariable.NumofColm, '6.TabApplicationData')
}

WebUI.delay(5)

if (GlobalVariable.FlagFailed == 0) {
    'check save process write to excel'
    CustomKeywords.'checkSaveProcess.checkSaveProcess.checkStatus'(iscompleteMandatory, findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/button_Asset Name_btn btn-raised btn-primary'), 
        GlobalVariable.NumofColm, '6.TabApplicationData')

    if (iscompleteMandatory == 0) {
        errorValObject = findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/div_errorvalidation')

        'cek validasi'
        CustomKeywords.'checkSaveProcess.checkSaveProcess.checkValidasi'(errorValObject, GlobalVariable.NumofColm, '6.TabApplicationData')
    }
}

'Verify input data'
if (WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/ApplicationCurrentStep')), 
    'APPLICATION DATA', false, FailureHandling.OPTIONAL)) {
    'click cancel'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/button_Cancel'))
}

if(GlobalVariable.Role == 'Testing' && GlobalVariable.CheckVerifStoreDBPersonal=="Yes"){
		'call test case store db application data'
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabApplicationDataStoreDBVerif'),
				[:], FailureHandling.CONTINUE_ON_FAILURE)
}

public checkVerifyEqualOrMatch(Boolean isMatch){
	if(isMatch==false && GlobalVariable.FlagFailed==0){

		'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedVerifyEqualOrMatch'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('6.TabApplicationData', GlobalVariable.NumofColm, GlobalVariable.StatusFailed, GlobalVariable.ReasonFailedVerifyEqualOrMatch)
		
		GlobalVariable.FlagFailed=1
	}
}

public addCrossAppAgr(ArrayList<Integer> s, Sql sqlConnectionLOS){
	Boolean verifCountDataLookup = false
	Integer countDataCross

	if (WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/tablenodata'),FailureHandling.OPTIONAL),
		'NO DATA AVAILABLE', false, FailureHandling.OPTIONAL)) {
		verifCountDataLookup = true
	}
	
	'klik button add'
	WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/button_AddCrossAppAgr'))
	
	if(verifCountDataLookup == true && GlobalVariable.Role=="Testing"){
		WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/button_Search'))
		
		countDataCross = CustomKeywords.'applicationData.checkAppCrossData.checkCountAppAgr'(sqlConnectionLOS)
		
		'Ambil nilai total data cross agreement pada lookup confins'
		String[] textTotalDataCross = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/label_TotalDataCross')).replace(
			' ', '').replace(':', ';').split(';')

		'Parsing nilai total data cross confins ke integer(angka)'
		Integer totalDataCross = Integer.parseInt(textTotalDataCross[1])

		'Verif total data cross application confins sesuai dengan db'
		if(WebUI.verifyEqual(totalDataCross, countDataCross)==false){
		
			'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDataLookup'
			CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('6.TabApplicationData', GlobalVariable.NumofColm, GlobalVariable.StatusFailed, GlobalVariable.ReasonFailedDataLookup)
			
			GlobalVariable.FlagFailed=1
		}
		
	}
	for(int i=0;i<s.size();i++){
		'input application no'
		WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/input_ApplicationNo'),appCrossArr[s[i]])
		
		'input ageement no'
		WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/input_AgreementNo'),agrCrossArr[s[i]])
		
		'input customer name'
		WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/input_CustName'),custCrossArr[s[i]])
		
		'klik button search'
		WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/button_Search'))
		
		'verify input lookup'
		if (WebUI.verifyElementPresent(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/input_checkboxcross'),
			GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
			'centang checkbox'
			WebUI.check(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/input_checkboxcross'))
			
		} else {

			'Write To Excel GlobalVariable.StatusWarning and GlobalVariable.StatusReasonLookup'
			CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('6.TabApplicationData', GlobalVariable.NumofColm, GlobalVariable.StatusWarning, GlobalVariable.StatusReasonLookup)
			
			continue
		}
	
		'klik button add to temp'
		WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/button_Add To Temp'))
	}
	
	if (WebUI.verifyElementPresent(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/button_SaveLookup'),
		GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
		'klik button save pada lookup'
		WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/button_SaveLookup'))
	
	}
	else{
		'Klik button X'
		WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/Button_X'))
	}
	
}