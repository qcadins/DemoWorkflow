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
String url = servername+';instanceName='+instancename+';databaseName='+database
String urlFOU = servername+';instanceName='+instancename+';databaseName='+databaseFOU
Sql sqlConnectionLOS = CustomKeywords.'dbconnection.connectDB.connect'(url, username,password,driverclassname)
Sql sqlConnectionFOU = CustomKeywords.'dbconnection.connectDB.connect'(urlFOU, username,password,driverclassname)


if (WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/ApplicationCurrentStep')).equalsIgnoreCase('APPLICATION DATA')){

'Ambil text product offering dari confins'
String POName = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/label_ProductOffering'))

'Pengecekan interest type dari db product offering '
String InterestType = CustomKeywords.'dbconnection.checkInterestType.checkInterest'(sqlConnectionLOS,POName)
	
'Ambil text interest type dari confins'
String textInterest = WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/select_InterestType'),'value')
	
'Verif interest type pada confins dengan db'
WebUI.verifyMatch(textInterest, "(?i)"+InterestType, true)

'Ambil nilai username dari confins'
String[] userLogin = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/label_userLogin')).replace(" ","").replace("|",";").split(";")

String usernameLogin = userLogin[0]

String spvName

'Pengecekan job title pada excel cmo atau bukan'
if(findTestData('Login/Login').getValue(5, 2).toLowerCase().contains("Credit Marketing Officer".toLowerCase())){
	
	'Ambil text label officer dari confins'
	String textOfficer = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/label_Officer'))
	
	'Verif username login dengan text label officer'
	WebUI.verifyMatch(textOfficer, usernameLogin,false)
	
	'Ambil nama spv dari db'
	spvName = CustomKeywords.'dbconnection.checkOfficer.checkSPV'(sqlConnectionFOU,usernameLogin)
	
	'Pengecekan jika nama spv dari db = null'
	if(spvName == null){
		
		'Ubah hasil nama spv dari db menjadi -'
		spvName = "-"
	}
	
	'Ambil text spv dari confins'
	String textSPV = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/label_SPV'))
	
	'Verif text spv dari confins sesuai dengan nama spv dari db'
	WebUI.verifyMatch(textSPV, "(?i)"+spvName,true)
}
else{
	'Ambil text original office dari confins'
	String office = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/label_OriginalOffice'))
	
	'Pengecekan jika button lookup ada'
	if(WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/button_MOOfficer'),10, FailureHandling.OPTIONAL)){
		'Click Lookup Officer'
		WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/button_MOOfficer'))
		
		'Click Search'
		WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/button_Search'))
		
		'Cek total data officer pada db'
		Integer countOfficer = CustomKeywords.'dbconnection.checkOfficer.countOfficerLookup'(sqlConnectionFOU,office)
		
		'Ambil nilai total data officer pada lookup confins'
		String[] textTotalDataOfficer = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/label_TotalDataOfficer')).replace(" ","").replace(":",";").split(";")
		
		'Parsing nilai total data officer confins ke integer(angka)'
		Integer totalDataOfficer = Integer.parseInt(textTotalDataOfficer[1])
		
		'Verif total data officer confins sesuai dengan db'
		WebUI.verifyEqual(totalDataOfficer, countOfficer)
		
		'Input MO Code'
		WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/input_MO Code'),
			findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabApplicationData').getValue(
				GlobalVariable.NumofColm, 3))
		
		'Input MO Head Name'
		WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/input_MO Head Name'),
			findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabApplicationData').getValue(
				GlobalVariable.NumofColm, 4))
		
		'Input MO Office Name'
		WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/input_MOOfficeName'),
			findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabApplicationData').getValue(
				GlobalVariable.NumofColm, 5))
		
		'Click Search'
		WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/button_Search'))
			
		'verify input error'
		if(WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/a_Select'),10,FailureHandling.OPTIONAL)){
			
			'Ambil nama spv pada lookup confins'
			spvName = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/span_SPVLookup'))
			
			'Click Select'
			WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/a_Select'),
				FailureHandling.OPTIONAL)
			
			'Ambil nama spv pada confins'
			String textSPV = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/label_LookupSPV'))
			
			'Verif nama spv pada lookup yang diselect sama dengan yang muncul pada tab application data confins'
			WebUI.verifyMatch(textSPV, "(?i)"+spvName,true)
			
		}
		else{
			'click X'
			WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/Button_X'))
		
			'click cancel'
			WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/button_Cancel'))
		
			'write to excel failed'
			CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '6.TabApplicationData', 0, GlobalVariable.NumofColm -
				1, GlobalVariable.StatusFailed)
			
			'Pengecekan jika new consumer finance belum diexpand'
			if(WebUI.verifyElementNotVisible(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA'), FailureHandling.OPTIONAL)){
				
				'Klik new consumer finance'
				WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_New Consumer Finance'))
			}
		}
		
	}
}

'Input MO Notes'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/textarea_MONotes'), 
    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabApplicationData').getValue(
        GlobalVariable.NumofColm, 6))

'Select option dropdownlist App Source'
WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/select_ApplicationSource'), 
    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabApplicationData').getValue(
        GlobalVariable.NumofColm, 8), false, FailureHandling.OPTIONAL)

'Select option dropdownlist First Installment Type'
WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/select_FirstInstallmentType'), 
    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabApplicationData').getValue(
        GlobalVariable.NumofColm, 9), false)

'Inisialisasi Variabel'
String payFreq = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabApplicationData').getValue(
    GlobalVariable.NumofColm, 10)

'Select option dropdownlist Payment Frequency'
WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/select_PaymentFrequency'), 
    payFreq, false)

'Inisialisasi Variabel'
String tenorString = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabApplicationData').getValue(
    GlobalVariable.NumofColm, 11)

'Input tenor'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/input_Tenor'), 
    tenorString)

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
} else {
    numOfInstallment = (((((4) as int) * tenor)) + (Math.round(tenor - 1) / 3) + 1)
}

'Click label numofInstallment untuk merefresh numofinstallment'
WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/label_NumOfInstallment'))

'Verify numofinstallment sesuai perhitungan'
WebUI.verifyElementText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/label_NumOfInstallment'), 
    numOfInstallment.toString())

if(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabApplicationData').getValue(
        GlobalVariable.NumofColm, 12).length() > 0){
'Select option dropdownlist DPSourcePaymentType'
WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/select_DPSourcePaymentType'), 
    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabApplicationData').getValue(
        GlobalVariable.NumofColm, 12), false)
}

String textInterestType = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabApplicationData').getValue(
    GlobalVariable.NumofColm, 13)

'Select option dropdownlist Interest Type'
WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/select_InterestType'), 
    textInterestType, false)

scheme = findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/select_InstallmentScheme')

wop = findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/select_WayOfPayment')

notif = findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/select_CustomerNotification')

inssource = findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/select_InstallmentSourcePaymentType')

'Verify/Jika Interest Type = Float Rate'
if (textInterestType == 'Float Rate') {
    'Select option dropdownlist Floating Period'
    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/select_FloatingPeriod'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabApplicationData').getValue(
            GlobalVariable.NumofColm, 14), false)
}

'Select option dropdownlist installment scheme'
WebUI.selectOptionByLabel(scheme, findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabApplicationData').getValue(
        GlobalVariable.NumofColm, 15), false)

textwop = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabApplicationData').getValue(
    GlobalVariable.NumofColm, 16)

'Select option dropdownlist Way of Payment'
WebUI.selectOptionByLabel(wop, textwop, false)

'Verify/Jika Way of Payment = Auto Debit'
if (textwop == 'Auto Debit') {
    bankacc = findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/select_CustomerBankAccount')
	
    'Select option dropdownlist Customer Bank Account'
    WebUI.selectOptionByIndex(bankacc, findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabApplicationData').getValue(
            GlobalVariable.NumofColm, 17), FailureHandling.OPTIONAL)
}

'Select option dropdownlist Customer Notification By'
WebUI.selectOptionByLabel(notif, findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabApplicationData').getValue(
        GlobalVariable.NumofColm, 18), false)

if(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabApplicationData').getValue(
        GlobalVariable.NumofColm, 19).length() >0){
'Select option dropdownlist Installment Source Payment'
WebUI.selectOptionByLabel(inssource, findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabApplicationData').getValue(
        GlobalVariable.NumofColm, 19), false)
}

'Jika/Verify Copy Address From ada isi/tidak kosong pada excel'
if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabApplicationData').getValue(
    GlobalVariable.NumofColm, 21).length() > 1) {
    'Select option dropdownlist Copy Address From '
    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/select_CopyAddressFrom'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabApplicationData').getValue(
            GlobalVariable.NumofColm, 21), false)

    'Click copy'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/button_Copy'))
} else {
    'Input Address'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/textarea_Address'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabApplicationData').getValue(
            GlobalVariable.NumofColm, 22))

    'Input RT'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/input_RT'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabApplicationData').getValue(
            GlobalVariable.NumofColm, 23))

    'Input RW'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/input_RW'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabApplicationData').getValue(
            GlobalVariable.NumofColm, 24))

    'Click Lookup Zipcode'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/button_Zipcode'))

    'Input Zipcode'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/input_ZipCodeLookup'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabApplicationData').getValue(
            GlobalVariable.NumofColm, 25))

    'Input Kecamatan'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/input_Kecamatan_kecamatan'), 
    		findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabApplicationData').getValue(
    				GlobalVariable.NumofColm, 26))
	
    'Input Kelurahan'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/input_Kelurahan_Kelurahan'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabApplicationData').getValue(
            GlobalVariable.NumofColm, 27))


    'Input Kota'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/input_Kota_kota'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabApplicationData').getValue(
            GlobalVariable.NumofColm, 28))

    'Click search'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/button_Search'))

    'verify input error'
	if(WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/a_Select'),10,FailureHandling.OPTIONAL)){
		'Click Select'
		WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/a_Select'),
			FailureHandling.OPTIONAL)
	}
	else{
		'click X'
		WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/Button_X'))
	
		'click cancel'
		WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/button_Cancel'))
	
		'write to excel failed'
		CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '6.TabApplicationData', 0, GlobalVariable.NumofColm -
			1, GlobalVariable.StatusFailed)
		'Pengecekan jika new consumer finance belum diexpand'
		if(WebUI.verifyElementNotVisible(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA'), FailureHandling.OPTIONAL)){
			
			'Klik new consumer finance'
			WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_New Consumer Finance'))
		}
	}
}

'Input Phone 1 Area'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/input_Phone 1 area'), 
    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabApplicationData').getValue(
        GlobalVariable.NumofColm, 29))

'Input Phone 1 Number'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/input_Phone 1 number'), 
    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabApplicationData').getValue(
        GlobalVariable.NumofColm, 30))

'Input Phone 1 Extension'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/input_Phone 1 extension'), 
    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabApplicationData').getValue(
        GlobalVariable.NumofColm, 31))

'Input Phone 2 Area'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/input_Phone 2 area'), 
    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabApplicationData').getValue(
        GlobalVariable.NumofColm, 32))

'Input Phone 2 Number'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/input_Phone 2 number'), 
    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabApplicationData').getValue(
        GlobalVariable.NumofColm, 33))

'Input Phone 2 Extension'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/input_Phone 2 extension'), 
    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabApplicationData').getValue(
        GlobalVariable.NumofColm, 34))

'Input Phone 3 Area'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/input_Phone 3 area'), 
    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabApplicationData').getValue(
        GlobalVariable.NumofColm, 35))

'Input Phone 3 Number'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/input_Phone 3 number'), 
    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabApplicationData').getValue(
        GlobalVariable.NumofColm, 36))

'Input Phone 3 Extension'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/input_Phone 3 extension'), 
    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabApplicationData').getValue(
        GlobalVariable.NumofColm, 37))

'Input Fax Area'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/input_Fax area'), 
    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabApplicationData').getValue(
        GlobalVariable.NumofColm, 38))

'Input Fax Number'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/input_Fax number'), 
    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabApplicationData').getValue(
        GlobalVariable.NumofColm, 39))

String coc = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabApplicationData').getValue(
    GlobalVariable.NumofColm, 41)

slikbtn = findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/button_Economic Sector (Slik)')

'Select option dropdownlist characteristic of credit'
WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/select_CharacteristicOfCredit'), 
    coc, false)

'Verify/Jika coc selain lainnya'
if (coc != 'Lainnya') {
    'Input prev agreement no'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/input_PrevAgreementNo'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabApplicationData').getValue(
            GlobalVariable.NumofColm, 42))

    'Select option dropdownlist way of restructure '
    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/select_WayOfRestructure'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabApplicationData').getValue(
            GlobalVariable.NumofColm, 43), false)
}

'Click lookup slik'
WebUI.click(slikbtn)

'Input Economic Sector Code'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/input_Economic Sector Code'), 
    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabApplicationData').getValue(
        GlobalVariable.NumofColm, 44))

'Click Search'
WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/button_Search'))

'verify input error'
if(WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/a_Select'),10,FailureHandling.OPTIONAL)){
	'Click Select'
	WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/a_Select'),
		FailureHandling.OPTIONAL)
}
else{
	'click X'
	WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/Button_X'))

	'click cancel'
	WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/button_Cancel'))

	'write to excel failed'
	CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '6.TabApplicationData', 0, GlobalVariable.NumofColm -
		1, GlobalVariable.StatusFailed)
	'Pengecekan jika new consumer finance belum diexpand'
	if(WebUI.verifyElementNotVisible(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA'), FailureHandling.OPTIONAL)){
		
		'Klik new consumer finance'
		WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_New Consumer Finance'))
	}
}

'Select option dropdownlist Blacklist APPI'
WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/select_BlackListAppi'), 
    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabApplicationData').getValue(
        GlobalVariable.NumofColm, 47), false)

'Input APPI Score'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/input_APPI SCORE'), 
    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabApplicationData').getValue(
        GlobalVariable.NumofColm, 48))

'Input Date APP DATA'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/input_DATE APP DATA'), 
    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabApplicationData').getValue(
        GlobalVariable.NumofColm, 49))

'Click Lookup REF MASTER APP DATA'
WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/button_REF MASTER APP DATA'))

'Input Code REF MASTER APP DATA'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/input_Code_RefMasterAppData'), 
    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabApplicationData').getValue(
        GlobalVariable.NumofColm, 50))

'Click Search'
WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/button_Search'))

'verify input error'
if(WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/a_Select'),10,FailureHandling.OPTIONAL)){
	'Click Select'
	WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/a_Select'),
		FailureHandling.OPTIONAL)
}
else{
	'click X'
	WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/Button_X'))

	'click cancel'
	WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/button_Cancel'))

	'write to excel failed'
	CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '6.TabApplicationData', 0, GlobalVariable.NumofColm -
		1, GlobalVariable.StatusFailed)
	'Pengecekan jika new consumer finance belum diexpand'
	if(WebUI.verifyElementNotVisible(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA'), FailureHandling.OPTIONAL)){
		
		'Klik new consumer finance'
		WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_New Consumer Finance'))
	}
}

'Input Jumlah Asset'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/input_JUMLAH ASSET'), 
    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabApplicationData').getValue(
        GlobalVariable.NumofColm, 52))

WebUI.delay(3)

'click Save'
WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/button_Save'))

WebUI.delay(5)

'Verify input data'
if (WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/ApplicationCurrentStep')), 'APPLICATION DATA', false, FailureHandling.OPTIONAL)) {
    'click cancel'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/button_Cancel'))

    'write to excel failed'
    CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '6.TabApplicationData', 0, GlobalVariable.NumofColm - 
        1, GlobalVariable.StatusFailed)
} else {
    'write to excel success'
    CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '6.TabApplicationData', 0, 
        GlobalVariable.NumofColm - 1, GlobalVariable.StatusSuccess)
}

}