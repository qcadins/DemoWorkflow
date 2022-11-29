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

GlobalVariable.FlagFailed = 0

GlobalVariable.FlagWarning = 0

'click lookup profesi'
WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/button_Profession Name_btn btn-raised btn-primary'))

'input kode profesi'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/input_Profession Code_professionCodeId'),
	GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 14))

'click button search'
WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/button_Search'))

'verify input error'
verifyInputError()

'pilih job position'
WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/select_JobPosition'),
	GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 16), false)

'pilih contract/permanent'
WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/select_--Select One-- Contract  Permanent'),
	GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 17), false)

if (WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/input_Internal Employee_ng-untouched ng-pristine ng-valid'),
	2, FailureHandling.OPTIONAL)) {
	'check internal employee'
	if (GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 18) == 'Yes') {
		WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/input_Internal Employee_ng-untouched ng-pristine ng-valid'))
	}
} else if (WebUI.verifyElementChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/input_Internal Employee_ng-untouched ng-pristine ng-valid'),
	2)) {
	'check internal employee'
	if (GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 18) == 'No') {
		WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/input_Internal Employee_ng-untouched ng-pristine ng-valid'))
	}
}

if (WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/input_Is Well Known Company'),
	2, FailureHandling.OPTIONAL)) {
	'check is well known company'
	if (GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 19) == 'Yes') {
		WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/input_Is Well Known Company'))
	}
} else if (WebUI.verifyElementChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/input_Is Well Known Company'),
	2)) {
	'check is well known company'
	if (GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 19) == 'No') {
		WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/input_Is Well Known Company'))
	}
}

'input nama perusahaan'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/input_CompanyName'),
	GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 20))

'click button lookup industry type'
WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/button_Industry Type_btn btn-raised btn-primary'))

'input industry code'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/input_Industry Type Code_industryTypeCodeId'),
	GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 21))

'click button search'
WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/button_Search'))

'verify input error'
verifyInputError()

if (GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 23).length() > 0) {
	'pilih big/medium/small'
	WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/select_--Select One-- Big  Medium  Small'),
		GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 23), false, FailureHandling.OPTIONAL)
}

'input start working date'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/input_StartWorkingDate'),
	GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 24))

'input job title'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/input_Job Title Name'),
	GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 25))

'input number of employee'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/input_Number of Employee_'),
	GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 26))

'input address'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/textarea_Address_'),
	GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 55))

'input RT'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/input_RT'),
	GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 56))

'input RW'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/input_RW'),
	GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 57))

'click search zipcode'
WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/button_Zipcode_btn btn-raised btn-primary'))

'input zipcode'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/input_Zip Code_ZipCode'),
	GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 58))

'input Kecamatan'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/input_Kecamatan_kecamatan'),
	GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 60))

'input kelurahan'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/input_Kelurahan_Kelurahan'),
	GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 59))

'input kota'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/input_Kota_kota'),
	GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 61))

'click search'
WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/button_Search'))

'verify input error'
verifyInputError()

'input note'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/textarea_notes'),
	GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 62), FailureHandling.OPTIONAL)

'input phone1 Area'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/input_Phone 1_Area'),
	GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 63), FailureHandling.OPTIONAL)

'input phone1 number'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/input_Phone 1_Number'),
	GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 64), FailureHandling.OPTIONAL)

'input phone1 Extension'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/input_Phone 1_Ext'),
	GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 65), FailureHandling.OPTIONAL)

'input phone2 area'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/input_Phone 2_Area'),
	GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 66), FailureHandling.OPTIONAL)

'input phone2 number'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/input_Phone 2_Number'),
	GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 67), FailureHandling.OPTIONAL)

'input phone2 extension'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/input_Phone 2_Ext'),
	GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 68), FailureHandling.OPTIONAL)

'input phone3 area'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/input_Phone 3_Area'),
	GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 69), FailureHandling.OPTIONAL)

'input phone3 number'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/input_Phone 3_Number'),
	GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 70), FailureHandling.OPTIONAL)

'input phone3 extension'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/input_Phone 3_Ext'),
	GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 71), FailureHandling.OPTIONAL)

'input fax'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/input_Fax_Area'),
	GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 72), FailureHandling.OPTIONAL)

'input fax'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/input_Fax_Number'),
	GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 73), FailureHandling.OPTIONAL)

if (GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 74).length() > 0) {
	'select ownership'
	WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/select_Select One Dinas  Family  KPR  Rented  Self - Owned'),
		GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 74), false, FailureHandling.OPTIONAL)
}

if (GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 76).length() > 0) {
	'input Previous Company Name'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/input_Previous Company Name_'),
		GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 76))
}

if (GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 77).length() > 0) {
	'input previous employment date'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/input_Previous Employment Date_'),
		GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 77))
}

if (GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 79).length() > 0) {
	'input address'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/textarea_Address PreviousJobAddress'),
		GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 79))

	'input RT'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/input_RT  PreviousJobAddress'),
		GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 80))

	'input RW'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/input_RW PreviousJobAddress'),
		GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 81))

	if (GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 82).length() > 0) {
		'click search zipcode'
		WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/button_Zipcode_btn PreviousJobAddress'))

		'input zipcode'
		WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/input_Zip Code_PreviousJobAddress'),
			GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 82))

		'input Kecamatan'
		WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/input_Kecamatan_PreviousJobAddress'),
			GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 84))

		'input kelurahan'
		WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/input_Kelurahan_PreviousJobAddress'),
			GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 83))

		'input kota'
		WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/input_Kota_PreviousJobAddress'),
			GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 85))

		'click search'
		WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/button_Search'))

		'verify input error'
		if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/a_Select'),
			10, FailureHandling.OPTIONAL)) {
			'click select'
			WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/a_Select'))
		} else {
			'click X'
			WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/TabApplicationData/Button_X'))

			GlobalVariable.FlagWarning++
		}
	}
	
	'input note'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/textarea_Notes_PreviousJobAddress'),
		GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 86))

	'input phone1 Area'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/input_Phone 1_Area_PreviousJobAddress'),
		GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 87))

	'input phone1 number'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/input_Phone 1_Number_PreviousJobAddress'),
		GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 88))

	'input phone1 Extension'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/input_Phone 1_ext_PreviousJobAddress'),
		GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 89))

	'input phone2 area'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/input_Phone 2_Area_PreviousJobAddress'),
		GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 90))

	'input phone2 number'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/input_Phone 2_Number_PreviousJobAddress'),
		GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 91))

	'input phone2 extension'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/input_Phone 2_Ext_PreviousJobAddress'),
		GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 92))

	'input phone3 area'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/input_Phone 3_Area_PreviousJobAddress'),
		GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 93))

	'input phone3 number'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/input_Phone 3_Number_PreviousJobAddress'),
		GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 94))

	'input phone3 extension'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/input_Phone 3_Ext_PreviousJobAddress'),
		GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 95))

	'input fax'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/input_Fax_Area_PreviousJobAddress'),
		GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 96))

	'input fax'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/input_Fax_Number_PreviousJobAddress'),
		GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 97))

	if (GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 98).length() > 0) {
		'select ownership'
		WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/select_OwnerShip PreviousJobAddress'),
			GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 98), false, FailureHandling.OPTIONAL)
	}
}

if (GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 100).length() > 0) {
	'input other business name'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/input_Other Business Name_'),
		GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 100))
}

if (GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 101).length() > 0) {
	'input other business type'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/input_Other Business Type_'),
		GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 101))
}

if (GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 102).length() > 0) {
	'input other business industry type'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/input_Other Business Industry Type_'),
		GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 102))
}

if (GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 103).length() > 0) {
	'input other job position'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/input_Other Job Position_'),
		GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 103))
}

if (GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 104).length() > 0) {
	'input establishment date'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/input_Establishment Date_'),
		GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 104))
}

if (GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 106).length() > 0) {
	'input address'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/textarea_Address_OtherBusinessAddress'),
		GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 106))

	'input RT'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/input_RT  OtherBusinessAddress'),
		GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 107))

	'input RW'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/input_RW OtherBusinessAddress'),
		GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 108))

	if (GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 109).length() > 0) {
		'click search zipcode'
		WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/button_Zipcode_btn OtherBusinessAddress'))

		'input zipcode'
		WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/input_Zip Code_OtherBusinessAddress'),
			GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 109))

		'input Kecamatan'
		WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/input_Kecamatan_OtherBusinessAddress'),
			GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 111))

		'input kelurahan'
		WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/input_Kelurahan_OtherBusinessAddress'),
			GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 110))

		'input kota'
		WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/input_Kota_OtherBusinessAddress'),
			GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 112))

		'click search'
		WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/button_Search'))

		'verify input error'
		if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/a_Select'),
			10, FailureHandling.OPTIONAL)) {
			'click select'
			WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/a_Select'))
		} else {
			'click X'
			WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/TabApplicationData/Button_X'))

			GlobalVariable.FlagWarning++
		}
	}
	
	'input note'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/textarea_Notes_OtherBusinessAddress'),
		GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 113))

	'input phone1 Area'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/input_Phone 1_Area_OtherBusinessAddress'),
		GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 114))

	'input phone1 number'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/input_Phone 1_Number_OtherBusinessAddress'),
		GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 115))

	'input phone1 Extension'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/input_Phone 1_ext_OtherBusinessAddress'),
		GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 116))

	'input phone2 area'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/input_Phone 2_Area_OtherBusinessAddress'),
		GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 117))

	'input phone2 number'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/input_Phone 2_Number_OtherBusinessAddress'),
		GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 118))

	'input phone2 extension'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/input_Phone 2_Ext_OtherBusinessAddress'),
		GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 119))

	'input phone3 area'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/input_Phone 3_Area_OtherBusinessAddress'),
		GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 120))

	'input phone3 number'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/input_Phone 3_Number_OtherBusinessAddress'),
		GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 121))

	'input phone3 extension'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/input_Phone 3_ext_OtherBusinessAddress'),
		GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 122))

	'input fax'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/input_Fax_Area_OtherBusinessAddress'),
		GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 123))

	'input fax'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/input_Fax_Number_OtherBusinessAddress'),
		GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 124))

	if (GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 125).length() > 0) {
		'select ownership'
		WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/select_Ownership OtherBusinessAddress'),
			GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofMS, 125), false, FailureHandling.OPTIONAL)
	}
}

'click save and continue button'
WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/button_Save  Continue'))

if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/label_Profession Name'),
	5, FailureHandling.OPTIONAL)) {
	'click button back'
	WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/button_Back'))
} else {
	if (GlobalVariable.FlagWarning > 0) {
		'write to excel status warning'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '3.JobData', 0, GlobalVariable.NumofMS -
			1, GlobalVariable.StatusWarning)
	}
}

def verifyInputError(){
	if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/a_Select'),
		10, FailureHandling.OPTIONAL)) {
		'click select'
		WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/a_Select'))
	} else {
		'click X'
		WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/TabApplicationData/Button_X'))
	
		'click button back'
		WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/JobDataEmployee - Personal/button_Back'))
	
		'write to excel if failed'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '3.JobData', 0, GlobalVariable.NumofMS -
			1, GlobalVariable.StatusFailed)
	
		'write to excel reason lookup'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '3.JobData', 1, GlobalVariable.NumofMS -
			1, GlobalVariable.StatusReasonLookup)
	
		'flagfailed +1 karena gagal melakukan lookup'
		(GlobalVariable.FlagFailed)++
	}
}