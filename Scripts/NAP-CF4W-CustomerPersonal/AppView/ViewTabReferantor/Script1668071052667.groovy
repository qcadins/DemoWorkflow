import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import java.util.ArrayList

import org.openqa.selenium.By as By
import org.openqa.selenium.WebDriver as WebDriver
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
import groovy.sql.Sql as Sql
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.WebElement as WebElement

'get data file path'
GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'(GlobalVariable.PathAppInquiryPersonal)

GlobalVariable.FlagWarning = 0

'connect DB LOS'
Sql sqlconnection = CustomKeywords.'dbConnection.connectDB.connectLOS'()

'connect DB FOU'
Sql sqlConnectionFOU = CustomKeywords.'dbConnection.connectDB.connectFOU'()

'Klik tab referantor'
WebUI.click(findTestObject('Object Repository/AppView/Referantor/Referantor Tab'))

'Verif tidak ada alert yang muncul'
if(WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerPersonal/div_erroralert'), GlobalVariable.TimeOut)==false){
	GlobalVariable.FlagWarning = 1
	'write to excel status warning'
	CustomKeywords.'checkSaveProcess.checkSaveProcess.writeWarningAppView'(GlobalVariable.NumofColm,'4. Referantor')
}

'get appno from confins'
appno = WebUI.getText(findTestObject('Object Repository/AppView/MainInformation/Label App No'))

'get referantor data arraylist from db'
ArrayList<String> resultReferantor = CustomKeywords.'appView.verifyAppView.checkReferantor'(sqlconnection, appno)

'count ref table'
variableData = DriverFactory.getWebDriver().findElements(By.cssSelector('#ViewReferantor > div > table > tbody > tr'))

Refindex = 1

'declare referantordetail'
ArrayList<String> referantorDetail = new ArrayList<String>()

'looping referantor db'
for (dbindex = 0; dbindex < resultReferantor.size(); dbindex++) {
    'modify object ref category'
    modifyNewRefCat = WebUI.modifyObjectProperty(findTestObject('AppView/Referantor/ModifyObj'), 'xpath', 'equals', ('//*[@id=\'ViewReferantor\']/div/table/tbody/tr[' + 
        Refindex) + ']/td[2]', true)

    'modify object ref name'
    modifyNewRefName = WebUI.modifyObjectProperty(findTestObject('AppView/Referantor/ModifyObj'), 'xpath', 'equals', ('//*[@id=\'ViewReferantor\']/div/table/tbody/tr[' + 
        Refindex) + ']/td[3]', true)

    'modify object ref type'
    modifyNewRefType = WebUI.modifyObjectProperty(findTestObject('AppView/Referantor/ModifyObj'), 'xpath', 'equals', ('//*[@id=\'ViewReferantor\']/div/table/tbody/tr[' + 
        Refindex) + ']/td[4]', true)

    'modify object bank acc'
    modifyNewBankAcc = WebUI.modifyObjectProperty(findTestObject('AppView/Referantor/ModifyObj'), 'xpath', 'equals', ('//*[@id=\'ViewReferantor\']/div/table/tbody/tr[' + 
        Refindex) + ']/td[5]', true)

    'modify object tax calc method'
    modifyNewTaxCalc = WebUI.modifyObjectProperty(findTestObject('AppView/Referantor/ModifyObj'), 'xpath', 'equals', ('//*[@id=\'ViewReferantor\']/div/table/tbody/tr[' + 
        Refindex) + ']/td[6]', true)

    'modify object vat'
    modifyNewVat = WebUI.modifyObjectProperty(findTestObject('AppView/Referantor/ModifyObj'), 'xpath', 'equals', ('//*[@id=\'ViewReferantor\']/div/table/tbody/tr[' + 
        Refindex) + ']/td[7]', true)
	
	'modify button view detail'
	modifyViewDetail = WebUI.modifyObjectProperty(findTestObject('AppView/Referantor/ModifyObj'), 'xpath', 'equals', "//*[@id='ViewReferantor']/div/table/tbody/tr["+Refindex+"]/td[8]/a/i", true)

	'get text referantor category'
	String refCat = WebUI.getText(modifyNewRefCat)
	
	'get text referantor name'
	String refName = WebUI.getText(modifyNewRefName)
	
    'verify referantor category'
    checkVerifyEqualOrMatch(WebUI.verifyMatch(refCat.toUpperCase(), (resultReferantor[dbindex]).toUpperCase(), 
            false))

    dbindex++

    'verify referantor name'
    checkVerifyEqualOrMatch(WebUI.verifyMatch(refName.toUpperCase(), (resultReferantor[dbindex]).toUpperCase(), 
            false))

    dbindex++

    'verify referantor type'
    checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewRefType).toUpperCase(), (resultReferantor[dbindex]).toUpperCase(), 
            false))

    dbindex++

    'verify bank account'
    checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewBankAcc).toUpperCase(), (resultReferantor[dbindex]).toUpperCase(), 
            false))

    dbindex++

    'verify tax calculation method'
    checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewTaxCalc).toUpperCase(), (resultReferantor[dbindex]).toUpperCase(), 
            false))

    dbindex++

    'verify vat'
    checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewVat).toUpperCase(), (resultReferantor[dbindex]).toUpperCase(), 
            false))

	'klik view detail'
	WebUI.click(modifyViewDetail)
	
	'Pengecekan referantor category (customer, agency, atau mf employee)'
	if (refCat.equalsIgnoreCase('CUSTOMER')) {
		'pengecekan ke db dan simpan data-data detail referantor yang dibutuhkan dari db'
		 referantorDetail = CustomKeywords.'referantorData.checkReferantorDetail.checkCustomerReferantorWithRefName'(
			sqlConnectionFOU, refName)

	} else if (refCat.equalsIgnoreCase('AGENCY')) {
		'Pengecekan ke db dan simpan data-data detail referantor yang dibutuhkan dari db'
		referantorDetail = CustomKeywords.'referantorData.checkReferantorDetail.checkAgencyReferantorWithRefName'(
			sqlConnectionFOU, refName)

	} else if(refCat.equalsIgnoreCase('MULTIFINANCE EMPLOYEE')){
		'Pengecekan ke db dan simpan data-data detail referantor yang dibutuhkan dari db'
		referantorDetail = CustomKeywords.'referantorData.checkReferantorDetail.checkMFEmployeeReferantorWithRefName'(
			sqlConnectionFOU, refName)
		
	}
	'add data boolean referantordetail ke arraylist arraymatch'
	ArrayList <Boolean> arrayMatch = adddatatoarraylist(referantorDetail)
	
	'Jika nilai di confins ada yang tidak sesuai dengan db'
	if (arrayMatch.contains(false)) {
		'write to excel reason failed verify equal or match'
		checkVerifyEqualOrMatch(false)
	}
	
	'Klik cancel'
	WebUI.click(findTestObject('Object Repository/AppView/Referantor/ButtonCancelView'))
	
    Refindex++
}

if ((GlobalVariable.FlagWarning == 0) && (GlobalVariable.FlagFailed == 0)) {
	'write to excel status success'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '4. Referantor', 0, GlobalVariable.NumofColm -
		1, GlobalVariable.StatusSuccess)
}

def checkVerifyEqualOrMatch(Boolean isMatch) {
    if ((isMatch == false) && (GlobalVariable.FlagFailed == 0)) {
		'write to excel status failed'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '4. Referantor', 0, GlobalVariable.NumofColm - 
            1, GlobalVariable.StatusFailed)

		'write to excel reason failed verify equal or match'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '4. Referantor', 1, GlobalVariable.NumofColm - 
            1, GlobalVariable.ReasonFailedVerifyEqualOrMatch)

        GlobalVariable.FlagFailed = 1
    } 
}

def adddatatoarraylist(ArrayList<String> referantorDetail) {
	'Ambil dan simpan npwp no dari confins'
	String textNPWPNo = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabReferantorData/label_NPWPNo'))

	'Ambil dan simpan npwp name dari confins'
	String textNPWPName = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabReferantorData/label_NPWPName'))

	'Ambil dan simpan address dari confins'
	String textAddress = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabReferantorData/label_Address'))

	'ambil dan simpan rt dari confins'
	String textRT = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabReferantorData/label_RT')).replace(
		' ', '')

	'ambil dan simpan rw dari confins'
	String textRW = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabReferantorData/label_RW')).replace(
		'/ ', '')

	'ambil dan simpan kelurahan dari confins'
	String textKelurahan = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabReferantorData/label_Kelurahan'))

	'ambil dan simpan kecamatan dari confins'
	String textKecamatan = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabReferantorData/label_Kecamatan'))

	'ambil dan simpan kota dari confins'
	String textCity = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabReferantorData/label_Kota'))

	'ambil dan simpan zipcode dari confins'
	String textZipcode = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabReferantorData/label_Zipcode'))

	'arraylist boolean untuk menampung hasil dari verififikasi data dari confins sesuai dengan db atau tidak (true atau false)'
	ArrayList<String> arrayMatch = new ArrayList<String>()

	'verify npwpno db x confins'
	arrayMatch.add(WebUI.verifyMatch(textNPWPNo, '(?i)' + (referantorDetail[0]), true, FailureHandling.OPTIONAL))

	'verify npwpname db x confins'
	arrayMatch.add(WebUI.verifyMatch(textNPWPName, '(?i)' + (referantorDetail[1]), true, FailureHandling.OPTIONAL))

	'verify address db x confins'
	arrayMatch.add(WebUI.verifyMatch(textAddress, '(?i)' + (referantorDetail[2]), true, FailureHandling.OPTIONAL))

	'verify rt db x confins'
	arrayMatch.add(WebUI.verifyMatch(textRT, '(?i)' + (referantorDetail[3]), true, FailureHandling.OPTIONAL))

	'verify rw db x confins'
	arrayMatch.add(WebUI.verifyMatch(textRW, '(?i)' + (referantorDetail[4]), true, FailureHandling.OPTIONAL))

	'verify kelurahan db x confins'
	arrayMatch.add(WebUI.verifyMatch(textKelurahan, '(?i)' + (referantorDetail[5]), true, FailureHandling.OPTIONAL))

	'verify kecamatan db x confins'
	arrayMatch.add(WebUI.verifyMatch(textKecamatan, '(?i)' + (referantorDetail[6]), true, FailureHandling.OPTIONAL))

	'verify city db x confins'
	arrayMatch.add(WebUI.verifyMatch(textCity, '(?i)' + (referantorDetail[7]), true, FailureHandling.OPTIONAL))

	'verify zipcode db x confins'
	arrayMatch.add(WebUI.verifyMatch(textZipcode, "(?i)"+referantorDetail[8], true, FailureHandling.OPTIONAL))
	
	return arrayMatch
}
