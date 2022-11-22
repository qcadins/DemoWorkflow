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
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.WebElement as WebElement
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.By as By
import groovy.sql.Sql as Sql

Integer copyAppColm = 2

'get data file path'
GlobalVariable.DataFilePath = CustomKeywords.'dbconnection.connectDB.getExcelPath'(GlobalVariable.PathCompany)

'click menu application data'
WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/a_APPLICATION DATA'))

//Verify sort & paging
pagingTesting()

'input Appno'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/input_Application No_AppNoId'),
	findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofColm,
		13))

'Looping delay untuk handling copy app selama +- 2 menit'
for(int i = 1;i<=8;i++){
	'click button search'
	WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/button_Search'))
	
	'Pengecekan ada/tidak adanya button action pencil yang muncul'
	if(WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/i_FT PRODUCT OFFERING CF4W_font-medium-3 ft-edit-2'),1,FailureHandling.OPTIONAL)){
		break
	}
	else{
		
		'delay 14 detik'
		WebUI.delay(14)
	
	}
}

'click icon pensil untuk select'
WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/i_FT PRODUCT OFFERING CF4W_font-medium-3 ft-edit-2'))

'check if role company data entry / testing'
if (GlobalVariable.RoleCompany == 'Data Entry') {
	if (Integer.parseInt(GlobalVariable.CountofReferantorCompany) > 0) {
		'untuk mendapatkan posisi copy app dari excel'
		for (GlobalVariable.NumofReferantor = 2; GlobalVariable.NumofReferantor <= (Integer.parseInt(GlobalVariable.CountofReferantorCompany) +
		1); (GlobalVariable.NumofReferantor)++) {
			if (findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData').getValue(GlobalVariable.NumofReferantor,
				12) == findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofColm,
				13)) {
				copyAppColm = GlobalVariable.NumofReferantor

				break
			}
		}
	}
	
	'dijalankan tanpa copy app tab referantor'
	if (findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData').getValue(copyAppColm, 10).equalsIgnoreCase(
		'No') || findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData').getValue(copyAppColm, 10).equalsIgnoreCase(
		'Edit')) {
		GlobalVariable.CopyAppColm == copyAppColm

		'call test case referantor'
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabReferantorData'), [:], FailureHandling.CONTINUE_ON_FAILURE //dijalankan copy app referantor
			)
	} else if (findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData').getValue(copyAppColm, 10).equalsIgnoreCase(
		'Yes')) {
		'click button save'
		WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/Button Save'))

		'Write to excel success'
		CustomKeywords.'customizeKeyword.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '5.TabReferantorData',
			0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusSuccess)

		'verify fail'
		if (WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/ApplicationCurrentStep')),
			'REFERANTOR', false, FailureHandling.OPTIONAL)) {
			'Write to excel failed'
			CustomKeywords.'customizeKeyword.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '5.TabReferantorData',
				0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)

			'Write to excel failed reason'
			CustomKeywords.'customizeKeyword.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '5.TabReferantorData',
				1, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailedCopyApp)

			'click button cancel'
			WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/button_Cancel'))
		}
	}
	
	'dijalankan tanpa copy app application data'
	if (findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData').getValue(GlobalVariable.NumofColm,
		10).equalsIgnoreCase('No') || findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData').getValue(
		GlobalVariable.NumofColm, 10).equalsIgnoreCase('Edit')) {
		'call test case application data'
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabApplicationData'), [:], FailureHandling.CONTINUE_ON_FAILURE //dijalankan copy app Application data
			)
	} else if (findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData').getValue(GlobalVariable.NumofColm,
		10).equalsIgnoreCase('Yes')) {
		'click Save'
		WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/button_Save'))

		'Write to excel success'
		CustomKeywords.'customizeKeyword.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '6.TabApplicationData',
			0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusSuccess)

		WebUI.delay(5)

		'Verify fail'
		if (WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/ApplicationCurrentStep')),
			'APPLICATION DATA', false, FailureHandling.OPTIONAL)) {
			'Write to excel failed'
			CustomKeywords.'customizeKeyword.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '6.TabApplicationData',
				0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)

			'Write to excel failed reason'
			CustomKeywords.'customizeKeyword.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '6.TabApplicationData',
				1, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailedCopyApp)

			'click cancel'
			WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/button_Cancel'))
		}
	}
	
	'dijalankan tanpa copy app asset data'
	if (findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData').getValue(GlobalVariable.NumofColm, 10).equalsIgnoreCase(
		'No') || findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData').getValue(GlobalVariable.NumofColm,
		10).equalsIgnoreCase('Edit')) {
		'call test case asset data'
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabAssetData'), [:], FailureHandling.CONTINUE_ON_FAILURE //dijalankan copy app Asset data
			)
	} else if (findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData').getValue(GlobalVariable.NumofColm,
		10).equalsIgnoreCase('Yes')) {
		'input chasis number'
		WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_CHASIS NUMBER'),
			findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData').getValue(GlobalVariable.NumofColm,
				29))

		'input engine number'
		WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_ENGINE NUMBER'),
			findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData').getValue(GlobalVariable.NumofColm,
				30))

		'input license plate number'
		WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_LICENSE PLATE NUMBER'),
			findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData').getValue(GlobalVariable.NumofColm,
				31))

		if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_Serial4'),
			5, FailureHandling.OPTIONAL)) {
			'input serial 4'
			WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_Serial4'), findTestData(
					'NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData').getValue(GlobalVariable.NumofColm, 32),
				FailureHandling.OPTIONAL)
		}
		
		if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_Serial5'),
			5, FailureHandling.OPTIONAL)) {
			'input serial 5'
			WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_Serial5'), findTestData(
					'NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData').getValue(GlobalVariable.NumofColm, 33),
				FailureHandling.OPTIONAL)
		}
		
		'click button save'
		WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/button_Save'))

		'Menunggu Alert security deposit dibawah minimum atau manufacturing year dibawah angka tertentu (jika ada) muncul'
		WebUI.waitForAlert(3)

		'Accept Alert Konfirmasi Security deposit dibawah minimum atau manufacturing year dibawah angka tertentu'
		WebUI.acceptAlert(FailureHandling.OPTIONAL)

		WebUI.delay(5)

		'Write to excel success'
		CustomKeywords.'customizeKeyword.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '7.TabAssetData', 0,
			GlobalVariable.NumofColm - 1, GlobalVariable.StatusSuccess)

		if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/button_Lookup Supplier'),
			5, FailureHandling.OPTIONAL)) {
			'Write to excel failed'
			CustomKeywords.'customizeKeyword.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '7.TabAssetData',
				0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)

			'Write to excel failed reason'
			CustomKeywords.'customizeKeyword.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '7.TabAssetData',
				1, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailedCopyApp)

			'click button cancel'
			WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/button_Cancel'))
		}
	}
	
	'dijalankan tanpa copy app insurance data'
	if (findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData').getValue(GlobalVariable.NumofColm,
		10).equalsIgnoreCase('No') || findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData').getValue(
		GlobalVariable.NumofColm, 10).equalsIgnoreCase('Edit')) {
		'call test case tab insurance data'
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabInsuranceData'), [:], FailureHandling.CONTINUE_ON_FAILURE //dijalankan copy app insurance data
			)
	} else if (findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData').getValue(GlobalVariable.NumofColm,
		10).equalsIgnoreCase('Yes')) {

		'verify field discount ada atau tidak untuk write ulang diskon supaya tidak hilang akibat impact dari calculate insurance'
		if(WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/input_Discount_TotalCustDiscAmt'),2,FailureHandling.OPTIONAL)){
			def val = WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/input_Discount_TotalCustDiscAmt'),'value')
			'Set text discount'
			WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/input_Discount_TotalCustDiscAmt'),val,FailureHandling.OPTIONAL)
		}
	
		'verify button calculate ada atau tidak'
		if(WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/button_Calculate Insurance'), 5, FailureHandling.OPTIONAL)){
			
		'Klik calculate insurance'
		WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/button_Calculate Insurance'))
		
		}
	
		'Klik save'
		WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/button_Save'))

		WebUI.delay(5)

		'Write to excel success'
		CustomKeywords.'customizeKeyword.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '8.TabInsuranceData',
			0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusSuccess)

		'verify fail'
		if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/select_InsuredBy'),
			5, FailureHandling.OPTIONAL)) {
			'Write to excel failed'
			CustomKeywords.'customizeKeyword.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '8.TabInsuranceData',
				0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)

			'Write to excel failed reason'
			CustomKeywords.'customizeKeyword.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '8.TabInsuranceData',
				1, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailedCopyApp)

			'click cancel'
			WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/button_Cancel'))
		}
	}
	
	'dijalankan tanpa copy app financial data'
	if (findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData').getValue(GlobalVariable.NumofColm,
		10).equalsIgnoreCase('No') || findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData').getValue(
		GlobalVariable.NumofColm, 10).equalsIgnoreCase('Edit')) {
		'call test case tab financial data'
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabFinancialData'), [:], FailureHandling.CONTINUE_ON_FAILURE //dijalankan copy app financial data
			)
	} else if (findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData').getValue(GlobalVariable.NumofColm,
		10).equalsIgnoreCase('Yes')) {
		'click button calculate'
		WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/button_Calculate'))

		WebUI.delay(5)

		'click button save'
		WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/button_Save'))

		'Write to excel success'
		CustomKeywords.'customizeKeyword.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '9.TabFinancialData',
			0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusSuccess)

		if (WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/ApplicationCurrentStep')),
			'FINANCIAL DATA', false, FailureHandling.OPTIONAL)) {
			'Write to excel failed'
			CustomKeywords.'customizeKeyword.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '9.TabFinancialData',
				0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)

			'Write to excel failed reason'
			CustomKeywords.'customizeKeyword.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '9.TabFinancialData',
				1, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailedCopyApp)

			'click button cancel'
			WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/button_Cancel'))
		}
	}
	
	'dijalankan tanpa copy term and condition'
	if (findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabTermConditionData').getValue(GlobalVariable.NumofColm,
		10).equalsIgnoreCase('No') || findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabTermConditionData').getValue(
		GlobalVariable.NumofColm, 10).equalsIgnoreCase('Edit')) {
		'call test case tab term condition data'
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabTermConditionData'), [:], FailureHandling.CONTINUE_ON_FAILURE //dijalankan copy app Term and condition
			)
	} else if (findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabTermConditionData').getValue(GlobalVariable.NumofColm,
		10).equalsIgnoreCase('Yes')) {
		'Save'
		WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabTermConditionData/button_Save'))

		'Write to excel success'
		CustomKeywords.'customizeKeyword.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '10.TabTermConditionData',
			0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusSuccess)

		'Verify fail'
		if (WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/ApplicationCurrentStep')),
			'TERM AND CONDITION', false, FailureHandling.OPTIONAL)) {
			'Write to excel failed'
			CustomKeywords.'customizeKeyword.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '10.TabTermConditionData',
				0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)

			'Write to excel failed reason'
			CustomKeywords.'customizeKeyword.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '10.TabTermConditionData',
				1, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailedCopyApp)

			'click cancel'
			WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabTermConditionData/button_Cancel'))
		}
	}
	
	'untuk mendapatkan posisi copy app dari excel'
	for (GlobalVariable.NumofUploadDocument = 2; GlobalVariable.NumofUploadDocument <= (Integer.parseInt(GlobalVariable.CountofUploadDocumentCompany) +
	1); (GlobalVariable.NumofUploadDocument)++) {
		if (findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabUploadDocument').getValue(GlobalVariable.NumofUploadDocument,
			12) == findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofColm,
			13)) {
			copyAppColm = GlobalVariable.NumofUploadDocument

			break
		}
	}
	
	'dijalankan tanpa copy app tab upload document'
	if (findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabUploadDocument').getValue(copyAppColm, 10).equalsIgnoreCase(
		'No') || findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabUploadDocument').getValue(copyAppColm, 10).equalsIgnoreCase(
		'Edit')) {
		'call test case tab upload document'
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabUploadDocument'), [:], FailureHandling.CONTINUE_ON_FAILURE //dijalankan copy app upload document
			)
		//dijalankan copy app upload document
	} else if (findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabUploadDocument').getValue(copyAppColm, 10).equalsIgnoreCase(
		'Yes')) {
		'click button submit'
		WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabUploadDocument/button_Submit'))

		'Write to excel success'
		CustomKeywords.'customizeKeyword.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '11.TabUploadDocument',
			0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusSuccess)

		if (WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabUploadDocument/input_Application No_AppNoId'),
			5, FailureHandling.OPTIONAL)) {
			'Write to excel failed'
			CustomKeywords.'customizeKeyword.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '11.TabUploadDocument',
				0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)

			'Write to excel failed reason'
			CustomKeywords.'customizeKeyword.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '11.TabUploadDocument',
				1, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailedCopyApp)

			'click cancel'
			WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabTermConditionData/button_Cancel'))
		}
		'Verify tab insurance, financial apakah ada nilai yang berubah'
		verifyMatch()
	}
} else {
	'untuk mendapatkan posisi copy app dari excel'
	for (GlobalVariable.NumofReferantor = 2; GlobalVariable.NumofReferantor <= (Integer.parseInt(GlobalVariable.CountofReferantorCompany) +
	1); (GlobalVariable.NumofReferantor)++) {
		if (findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData').getValue(GlobalVariable.NumofReferantor,
			12) == findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofColm,
			13)) {
			copyAppColm = GlobalVariable.NumofReferantor

			break
		}
	}
	
	'dijalankan tanpa copy app tab referantor'
	if (findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData').getValue(copyAppColm, 10).equalsIgnoreCase(
		'No') || findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData').getValue(copyAppColm, 10).equalsIgnoreCase(
		'Edit')) {
		GlobalVariable.CopyAppColm == copyAppColm

		'call test case referantor'
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabReferantorData'), [:], FailureHandling.STOP_ON_FAILURE)
	} else if (findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData').getValue(copyAppColm, 10).equalsIgnoreCase(
		'Yes')) {
		'click button save'
		WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/Button Save'))

		'Write to excel success'
		CustomKeywords.'customizeKeyword.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '5.TabReferantorData',
			0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusSuccess)

		'verify fail'
		if (WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/ApplicationCurrentStep')),
			'REFERANTOR', false, FailureHandling.OPTIONAL)) {
			'Write to excel failed'
			CustomKeywords.'customizeKeyword.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '5.TabReferantorData',
				0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)

			'Write to excel failed reason'
			CustomKeywords.'customizeKeyword.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '5.TabReferantorData',
				1, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailedCopyApp)

			'click button cancel'
			WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/button_Cancel'))
		}
	}
	
	'dijalankan tanpa copy app application data'
	if (findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData').getValue(GlobalVariable.NumofColm,
		10).equalsIgnoreCase('No') || findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData').getValue(
		GlobalVariable.NumofColm, 10).equalsIgnoreCase('Edit')) {
		'call test case application data'
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabApplicationData'), [:], FailureHandling.STOP_ON_FAILURE)
	} else if (findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData').getValue(GlobalVariable.NumofColm,
		10).equalsIgnoreCase('Yes')) {
		'click Save'
		WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/button_Save'))

		'Write to excel success'
		CustomKeywords.'customizeKeyword.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '6.TabApplicationData',
			0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusSuccess)

		WebUI.delay(5)

		'Verify fail'
		if (WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/ApplicationCurrentStep')),
			'APPLICATION DATA', false, FailureHandling.OPTIONAL)) {
			'Write to excel failed'
			CustomKeywords.'customizeKeyword.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '6.TabApplicationData',
				0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)

			'Write to excel failed reason'
			CustomKeywords.'customizeKeyword.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '6.TabApplicationData',
				1, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailedCopyApp)

			'click cancel'
			WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/button_Cancel'))
		}
	}
	
	'dijalankan tanpa copy app asset data'
	if (findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData').getValue(GlobalVariable.NumofColm, 10).equalsIgnoreCase(
		'No') || findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData').getValue(GlobalVariable.NumofColm,
		10).equalsIgnoreCase('Edit')) {
		'call test case asset data'
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabAssetData'), [:], FailureHandling.STOP_ON_FAILURE)
	} else if (findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData').getValue(GlobalVariable.NumofColm,
		10).equalsIgnoreCase('Yes')) {
		'input chasis number'
		WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_CHASIS NUMBER'),
			findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData').getValue(GlobalVariable.NumofColm,
				29))

		'input engine number'
		WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_ENGINE NUMBER'),
			findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData').getValue(GlobalVariable.NumofColm,
				30))

		'input license plate number'
		WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_LICENSE PLATE NUMBER'),
			findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData').getValue(GlobalVariable.NumofColm,
				31))

		if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_Serial4'),
			5, FailureHandling.OPTIONAL)) {
			'input serial 4'
			WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_Serial4'), findTestData(
					'NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData').getValue(GlobalVariable.NumofColm, 32),
				FailureHandling.OPTIONAL)
		}
		
		if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_Serial5'),
			5, FailureHandling.OPTIONAL)) {
			'input serial 5'
			WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_Serial5'), findTestData(
					'NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData').getValue(GlobalVariable.NumofColm, 33),
				FailureHandling.OPTIONAL)
		}
		
		'click button save'
		WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/button_Save'))

		'Menunggu Alert security deposit dibawah minimum atau manufacturing year dibawah angka tertentu (jika ada) muncul'
		WebUI.waitForAlert(3)

		'Accept Alert Konfirmasi Security deposit dibawah minimum atau manufacturing year dibawah angka tertentu'
		WebUI.acceptAlert(FailureHandling.OPTIONAL)

		'delat 5 detik'
		WebUI.delay(5)

		'Write to excel success'
		CustomKeywords.'customizeKeyword.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '7.TabAssetData', 0,
			GlobalVariable.NumofColm - 1, GlobalVariable.StatusSuccess)

		if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/button_Lookup Supplier'),
			5, FailureHandling.OPTIONAL)) {
			'Write to excel failed'
			CustomKeywords.'customizeKeyword.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '7.TabAssetData',
				0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)

			'Write to excel failed reason'
			CustomKeywords.'customizeKeyword.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '7.TabAssetData',
				1, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailedCopyApp)

			'click button cancel'
			WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/button_Cancel'))
		}
	}
	
	'dijalankan tanpa copy app insurance data'
	if (findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData').getValue(GlobalVariable.NumofColm,
		10).equalsIgnoreCase('No') || findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData').getValue(
		GlobalVariable.NumofColm, 10).equalsIgnoreCase('Edit')) {
		'call test case tab insurance data'
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabInsuranceData'), [:], FailureHandling.STOP_ON_FAILURE)
	} else if (findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData').getValue(GlobalVariable.NumofColm,
		10).equalsIgnoreCase('Yes')) {
		'verify button calculate ada atau tidak'
		if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/button_Calculate Insurance'),
			5, FailureHandling.OPTIONAL)) {
			'Klik calculate insurance'
			WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/button_Calculate Insurance'))
		}
		
		'Klik save'
		WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/button_Save'))

		'delay 5 detik'
		WebUI.delay(5)

		'Write to excel success'
		CustomKeywords.'customizeKeyword.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '8.TabInsuranceData',
			0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusSuccess)

		'verify fail'
		if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/select_InsuredBy'),
			5, FailureHandling.OPTIONAL)) {
			'Write to excel failed'
			CustomKeywords.'customizeKeyword.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '8.TabInsuranceData',
				0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)

			'Write to excel failed reason'
			CustomKeywords.'customizeKeyword.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '8.TabInsuranceData',
				1, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailedCopyApp)

			'click cancel'
			WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/button_Cancel'))
		}
	}
	
	'dijalankan tanpa copy app financial data'
	if (findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData').getValue(GlobalVariable.NumofColm,
		10).equalsIgnoreCase('No') || findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData').getValue(
		GlobalVariable.NumofColm, 10).equalsIgnoreCase('Edit')) {
		'call test case tab financial data'
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabFinancialData'), [:], FailureHandling.STOP_ON_FAILURE)
	} else if (findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData').getValue(GlobalVariable.NumofColm,
		10).equalsIgnoreCase('Yes')) {
		'click button calculate'
		WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/button_Calculate'))

		'delay 5 detik'
		WebUI.delay(5)

		'click button save'
		WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/button_Save'))

		'Write to excel success'
		CustomKeywords.'customizeKeyword.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '9.TabFinancialData',
			0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusSuccess)

		if (WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/ApplicationCurrentStep')),
			'FINANCIAL DATA', false, FailureHandling.OPTIONAL)) {
			'Write to excel failed'
			CustomKeywords.'customizeKeyword.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '9.TabFinancialData',
				0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)

			'Write to excel failed reason'
			CustomKeywords.'customizeKeyword.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '9.TabFinancialData',
				1, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailedCopyApp)

			'click button cancel'
			WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/button_Cancel'))
		}
	}
	
	'dijalankan tanpa copy term and condition'
	if (findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabTermConditionData').getValue(GlobalVariable.NumofColm,
		10).equalsIgnoreCase('No') || findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabTermConditionData').getValue(
		GlobalVariable.NumofColm, 10).equalsIgnoreCase('Edit')) {
		'call test case tab term condition data'
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabTermConditionData'), [:], FailureHandling.STOP_ON_FAILURE)
	} else if (findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabTermConditionData').getValue(GlobalVariable.NumofColm,
		10).equalsIgnoreCase('Yes')) {
		'Save'
		WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabTermConditionData/button_Save'))

		'Write to excel success'
		CustomKeywords.'customizeKeyword.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '10.TabTermConditionData',
			0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusSuccess)

		'Verify fail'
		if (WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/ApplicationCurrentStep')),
			'TERM AND CONDITION', false, FailureHandling.OPTIONAL)) {
			'Write to excel failed'
			CustomKeywords.'customizeKeyword.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '10.TabTermConditionData',
				0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)

			'Write to excel failed reason'
			CustomKeywords.'customizeKeyword.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '10.TabTermConditionData',
				1, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailedCopyApp)

			'click cancel'
			WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabTermConditionData/button_Cancel'))
		}
	}
	
	'untuk mendapatkan posisi copy app dari excel'
	for (GlobalVariable.NumofUploadDocument = 2; GlobalVariable.NumofUploadDocument <= (Integer.parseInt(GlobalVariable.CountofUploadDocumentCompany) +
	1); (GlobalVariable.NumofUploadDocument)++) {
		if (findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabUploadDocument').getValue(GlobalVariable.NumofUploadDocument,
			12) == findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofColm,
			13)) {
			copyAppColm = GlobalVariable.NumofUploadDocument

			break
		}
	}
	
	'dijalankan tanpa copy app tab upload document'
	if (findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabUploadDocument').getValue(copyAppColm, 10).equalsIgnoreCase(
		'No') || findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabUploadDocument').getValue(copyAppColm, 10).equalsIgnoreCase(
		'Edit')) {
		'call test case tab upload document'
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabUploadDocument'), [:], FailureHandling.STOP_ON_FAILURE)
	} else if (findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabUploadDocument').getValue(copyAppColm, 10).equalsIgnoreCase(
		'Yes')) {
		'click button submit'
		WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabUploadDocument/button_Submit'))

		'Write to excel success'
		CustomKeywords.'customizeKeyword.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '11.TabUploadDocument',
			0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusSuccess)

		if (WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabUploadDocument/input_Application No_AppNoId'),
			5, FailureHandling.OPTIONAL)) {
			'Write to excel failed'
			CustomKeywords.'customizeKeyword.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '11.TabUploadDocument',
				0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)

			'Write to excel failed reason'
			CustomKeywords.'customizeKeyword.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '11.TabUploadDocument',
				1, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailedCopyApp)

			'click cancel'
			WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabTermConditionData/button_Cancel'))
		}
		'Verify tab insurance, financial apakah ada nilai yang berubah'
		verifyMatch()
	}
}

'Verify tab insurance, financial apakah ada nilai yang berubah (membandingkan app sumber dengan app hasil copy dalam database)'
public verifyMatch(){
	'Koneksi database'
	String urlLOS = (((findTestData('Login/Login').getValue(1, 8) + ';instanceName=') + findTestData('Login/Login').getValue(2, 8)) + ';databaseName=') + findTestData('Login/Login').getValue(5, 9)
	
	Sql sqlConnectionLOS = CustomKeywords.'dbconnection.connectDB.connect'(urlLOS, findTestData('Login/Login').getValue(3, 8), findTestData('Login/Login').getValue(4, 8), findTestData('Login/Login').getValue(6, 8))
	
	'verify tab insurance & financial, cek apakah ada nilai yang berubah, bernilai true jika tidak ada nilai yang berubah, false jika ada salah satu nilai yang berubah'
	Boolean isMatch = CustomKeywords.'dbconnection.verifyMatchCopyAppYes.verifyMatchCopyAppYesNAP2Company'(sqlConnectionLOS, findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
	GlobalVariable.NumofColm, 9),findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
	GlobalVariable.NumofColm, 13))
		
	if(isMatch==true){
			'Write to excel sheet commission copy app yes'
			CustomKeywords.'customizeKeyword.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '12.TabCommissionData',
				9, GlobalVariable.NumofColm - 1, "Yes")
			'Write to excel sheet reserved fund copy app yes'
			CustomKeywords.'customizeKeyword.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '13.TabReservedFundData',
				9, GlobalVariable.NumofColm - 1, "Yes")
	}
	else if(isMatch==false){
			'Write to excel sheet commission copy app edit'
			CustomKeywords.'customizeKeyword.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '12.TabCommissionData',
				9, GlobalVariable.NumofColm - 1, "Edit")
			'Write to excel sheet reserved fund copy app edit'
			CustomKeywords.'customizeKeyword.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '13.TabReservedFundData',
				9, GlobalVariable.NumofColm - 1, "Edit")
	}
}

public pagingTesting(){
	if(GlobalVariable.RoleCompany=="Testing" && GlobalVariable.CheckPagingCompany=="Yes"){
		ArrayList<Boolean> resultReset,checkVerifySort,checkVerifyFooter = new ArrayList<>()
		
		'Verif reset'
		resultReset = CustomKeywords.'paging.verifyPaging.resetPaging'()
		
		
		ArrayList<String> listString = new ArrayList<String>()
		
		'click button search'
		WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/button_Search'))
		
		'Inisialisasi driver'
		WebDriver driver = DriverFactory.getWebDriver()
		
		'Inisialisasi variabel'
		ArrayList<WebElement> rowData = driver.findElements(By.cssSelector('body > app-root > app-full-layout > div > div.main-panel > div > div > div > div > nap-detail-paging > lib-ucpaging > lib-ucgridview > div > table > tbody > tr'))
	
		'Klik header office'
		WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/span_Office'))
		'Verif tidak ada alert yang muncul'
		checkVerifySort.add(WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerPersonal/div_erroralert'), 2))
		
		'Klik header appno'
		WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/span_appNo'))
		
		'Verify alert tidak muncul'
		checkVerifySort.add(WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerPersonal/div_erroralert'), 2))
		
		for(int i=1;i<=rowData.size();i++){
			appNoObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/appNo')
					,'xpath','equals',"/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/nap-detail-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr["+i+"]/td[4]/span",true)
			
			listString.add(WebUI.getText(appNoObject))
		}
		'verif sort appno ascending'
		Boolean isSorted = CustomKeywords.'paging.verifyPaging.verifySortAscending'(listString)
		checkVerifySort.add(WebUI.verifyEqual(isSorted,true))
		
		listApp = new ArrayList<String>()
		'Klik header appno'
		WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/span_appNo'))
		for(int i=1;i<=rowData.size();i++){
			appNoObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/appNo')
					,'xpath','equals',"/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/nap-detail-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr["+i+"]/td[4]/span",true)
			
			listApp.add(WebUI.getText(appNoObject))
		}
		'Verif sort appno descending'
		isSorted = CustomKeywords.'paging.verifyPaging.verifySortDescending'(listApp)
		checkVerifySort.add(WebUI.verifyEqual(isSorted,true))
		
		'Klik header custname'
		WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/span_custName'))
		
		'Verify alert tidak muncul'
		checkVerifySort.add(WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerPersonal/div_erroralert'), 2))
		
		listString = new ArrayList<String>()
		for(int i=1;i<=rowData.size();i++){
			appNoObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/custName')
					,'xpath','equals',"/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/nap-detail-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr["+i+"]/td[5]/span",true)
			
			listString.add(WebUI.getText(appNoObject))
			
		}
		'Verif sort custname ascending'
		isSorted = CustomKeywords.'paging.verifyPaging.verifySortAscending'(listString)
		checkVerifySort.add(WebUI.verifyEqual(isSorted,true))
		
		'Klik header custname'
		WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/span_custName'))
		listString = new ArrayList<String>()
		for(int i=1;i<=rowData.size();i++){
			appNoObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/custName')
					,'xpath','equals',"/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/nap-detail-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr["+i+"]/td[5]/span",true)
			
			listString.add(WebUI.getText(appNoObject))
			
		}
		'Verif sort custname descending'
		isSorted = CustomKeywords.'paging.verifyPaging.verifySortDescending'(listString)
		checkVerifySort.add(WebUI.verifyEqual(isSorted,true))
		
		'Klik header poname'
		WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/span_POName'))
		
		'Verify alert tidak muncul'
		checkVerifySort.add(WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerPersonal/div_erroralert'), 2))
		
		listString = new ArrayList<String>()
		for(int i=1;i<=rowData.size();i++){
			appNoObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/POName')
					,'xpath','equals',"/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/nap-detail-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr["+i+"]/td[6]/span",true)
			
			listString.add(WebUI.getText(appNoObject))
		
		}
		'verif sort poname ascending'
		isSorted = CustomKeywords.'paging.verifyPaging.verifySortAscending'(listString)
		checkVerifySort.add(WebUI.verifyEqual(isSorted,true))
		
		'Klik header poname'
		WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/span_POName'))
		listString = new ArrayList<String>()
		for(int i=1;i<=rowData.size();i++){
			appNoObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/POName')
					,'xpath','equals',"/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/nap-detail-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr["+i+"]/td[6]/span",true)
			
			listString.add(WebUI.getText(appNoObject))
			
		}
		'verif sort poname descending'
		isSorted = CustomKeywords.'paging.verifyPaging.verifySortDescending'(listString)
		checkVerifySort.add(WebUI.verifyEqual(isSorted,true))
		
		'Klik 2x appno supaya urutan descending'
		WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/span_appNo'))
		WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/span_appNo'))
		
		'Ambil count data dari confins'
		String[] textCountData = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/countData')).replace(' ', '').replace(':', ';').split(';')
		Integer countDt = Integer.parseInt(textCountData[1])
		
		'Jika count data keseluruhan lebih besar daripada jumlah data page 1'
		if(countDt>rowData.size()){
			'Klik page 2'
			WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/nextPage'))
			
			'verif page 2 active'
			checkVerifyFooter.add(WebUI.verifyElementHasAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/nextPage'),'aria-current',2))
					
			listString = new ArrayList<String>()
			
			listString = CustomKeywords.'paging.verifyPaging.addAppNoForPagingNAP2'(listString)
			
			'Verif appno pada page 2 tidak ada di page 1'
			Boolean isPaging = CustomKeywords.'paging.verifyPaging.verifyPagingFunction'(listApp,listString)
			checkVerifyFooter.add(WebUI.verifyEqual(isPaging,true))
			
			'Klik button prev'
			WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/button_Prev'))
			
			'Verify page 1 active'
			checkVerifyFooter.add(WebUI.verifyElementHasAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/pageOne'),
				'aria-current', 2))
			
			listApp = listString
					
			listString = new ArrayList<String>()
			
			listString = CustomKeywords.'paging.verifyPaging.addAppNoForPagingNAP2'(listString)
					
			'Verif appno yang ada di page 1 tidak ada di page 2'
			isPaging = CustomKeywords.'paging.verifyPaging.verifyPagingFunction'(listApp, listString)
			
			checkVerifyFooter.add(WebUI.verifyEqual(isPaging, true))
			
			'Klik button next'
			WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/button_Next'))
			
			'Verify page 2 active'
			checkVerifyFooter.add(WebUI.verifyElementHasAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/nextPage'),
				'aria-current', 2))
			
			listApp = listString
			
			listString = new ArrayList<String>()
			
			listString = CustomKeywords.'paging.verifyPaging.addAppNoForPagingNAP2'(listString)
					
			'Verif appno yang ada di page 2 tidak ada di page 1'
			isPaging = CustomKeywords.'paging.verifyPaging.verifyPagingFunction'(listApp, listString)
			
			checkVerifyFooter.add(WebUI.verifyEqual(isPaging, true))
		}
		
		'Klik button page 1'
		WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/pageOne'))
		
		checkVerifyFooter.add(WebUI.verifyEqual(CustomKeywords.'paging.verifyPaging.NAP2CountDataInPage'(),true))
		
		if(resultReset.contains(false) && GlobalVariable.StatusFailed!=findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
			GlobalVariable.NumofColm, 1)){
				(new customizeKeyword.writeToExcel()).writeToExcelFunction(GlobalVariable.DataFilePath, '1.TabCustomerMainData',
					0, GlobalVariable.NumofColm-1, GlobalVariable.StatusWarning)
		
				(new customizeKeyword.writeToExcel()).writeToExcelFunction(GlobalVariable.DataFilePath, '1.TabCustomerMainData',
					1, GlobalVariable.NumofColm-1, findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
						GlobalVariable.NumofColm, 2).replace("-","")+GlobalVariable.ReasonFailedReset+"NAP2"+";\n")
		
				GlobalVariable.FlagWarning=1
		}
		
		if(checkVerifySort.contains(false) && GlobalVariable.StatusFailed!=findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
				GlobalVariable.NumofColm, 1)){
					(new customizeKeyword.writeToExcel()).writeToExcelFunction(GlobalVariable.DataFilePath, '1.TabCustomerMainData',
							0, GlobalVariable.NumofColm-1, GlobalVariable.StatusWarning)
			
					(new customizeKeyword.writeToExcel()).writeToExcelFunction(GlobalVariable.DataFilePath, '1.TabCustomerMainData',
							1, GlobalVariable.NumofColm-1, findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
				GlobalVariable.NumofColm, 2).replace("-","")+(GlobalVariable.ReasonFailedSort+"NAP2"+";\n"))
			
				GlobalVariable.FlagWarning=1
		}
			
		if(checkVerifyFooter.contains(false) && GlobalVariable.StatusFailed!=findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
				GlobalVariable.NumofColm, 1)){
					(new customizeKeyword.writeToExcel()).writeToExcelFunction(GlobalVariable.DataFilePath, '1.TabCustomerMainData',
							0, GlobalVariable.NumofColm-1, GlobalVariable.StatusWarning)
			
					(new customizeKeyword.writeToExcel()).writeToExcelFunction(GlobalVariable.DataFilePath, '1.TabCustomerMainData',
							1, GlobalVariable.NumofColm-1, findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
				GlobalVariable.NumofColm, 2).replace("-","")+(GlobalVariable.ReasonFailedFooter+"NAP2"+";\n"))
			
	
				GlobalVariable.FlagWarning=1
		}
			
			
	}
	
}