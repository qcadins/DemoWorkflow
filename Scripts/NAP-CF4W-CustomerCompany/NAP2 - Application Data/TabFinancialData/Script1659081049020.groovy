import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import java.math.RoundingMode as RoundingMode
import java.text.NumberFormat as NumberFormat
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
import org.openqa.selenium.By as By
import org.openqa.selenium.Keys as Keys
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import groovy.sql.Sql as Sql

GlobalVariable.SubsidyDPValue = '0'

GlobalVariable.FlagFailed=0

'get data file path'
GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'("\\Excel\\"+ GlobalVariable.LOB +"\\2.2 DataFile_NAP_CF4W_Company.xlsx")

'Klik tab financial'
WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/buttonTabFinancial'))

'get app last step from confins'
String appLastStep = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/label_AppLastStep'))

if(!appLastStep.equalsIgnoreCase("INSURANCE") && !appLastStep.equalsIgnoreCase("FINANCIAL DATA") && GlobalVariable.FirstTimeEntry=="Yes"){
	GlobalVariable.FirstTimeEntry = "No"
}

'call tc subsidy financial'
WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabSubsidyFinancial'),
	[:], FailureHandling.CONTINUE_ON_FAILURE)

'write to excel total subsidy dp'
CustomKeywords.'customizeKeyword.writeExcel.writeToExcelNumber'(GlobalVariable.DataFilePath, '9.TabFinancialData',
	52, GlobalVariable.NumofColm - 1, GlobalVariable.SubsidyDP)

'koneksi db los'
Sql sqlConnectionLOS = CustomKeywords.'dbConnection.connectDB.connectLOS'()

'koneksi db fou'
Sql sqlConnectionFOU = CustomKeywords.'dbConnection.connectDB.connectFOU'()

'declare driver'
WebDriver driver = DriverFactory.getWebDriver()

'Ambil appno dari confins'
String appNo = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/span_appNo'))

if (GlobalVariable.RoleCompany == 'Testing') {
	'verify application step'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/ApplicationCurrentStep')),
		'FINANCIAL DATA', false, FailureHandling.OPTIONAL))
}

'declare excelPathTabFinancial'
excelPathTabFinancial = 'NAP-'+ GlobalVariable.LOB +'-CustomerCompany/NAP2-ApplicationData/TabFinancialData'

//Verif fee based on rule
if ((GlobalVariable.RoleCompany == 'Testing') && GlobalVariable.FirstTimeEntry=="Yes") {
	
	if((GlobalVariable.CheckRuleCompany == 'Yes')){
	'Ambil nilai result dari rule credit fee'
	HashMap<String, ArrayList> result = CustomKeywords.'financialData.verifyFee.verifyFinancialFee'(sqlConnectionLOS, appNo)

	'declare listfee, feetype, fee, feebhv, feecaptype, feecap'
	ArrayList<String> listFee, feeType, fee, feeBhv, feecapType, feecap, stdFee

	listFee = result.get('listFee')

	feeType = result.get('feeType')

	fee = result.get('fee')

	feeBhv = result.get('feeBhv')

	feecapType = result.get('feecapType')

	feecap = result.get('feecap')
	
	stdFee = result.get('stdFee')
	
	Integer stdPcnt = Integer.parseInt(stdFee[5])
	
	if(feeType[5] == "PRCNT"){
		stdFee.remove(5)
		stdFee.add((stdPcnt*Integer.parseInt(findTestData(excelPathTabFinancial).getValue(GlobalVariable.NumofColm, 73))/100).toString())
	}
	
	GlobalVariable.StandardFee = stdFee

	'declare counter'
	Integer counter = 0

	//Looping listfee dari result rule
	for (int i = counter; i < listFee.size(); i++) {
		'Pengecekan jika list fee pada rule sesuai dengan fee pada confins'
		if (CustomKeywords.'financialData.verifyFee.checkFeeCode'(sqlConnectionLOS, WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/label_Fee'))) ==
		listFee.get(i)) {
			'Verify amount admin fee pada confins sesuai rule'
			if (WebUI.verifyMatch(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Admin Fee'),
					'value').replace(',', ''), fee.get(i), false) == false) {
				'write to excel reason failed verify rule'
				writeReasonFailedVerifRule()
			}
			
			if (feeBhv.get(i) == 'DEF') {
				'Verify admin fee tidak terlock'
				WebUI.verifyElementNotHasAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Admin Fee'),
					'readonly', GlobalVariable.TimeOut)
			} else if (feeBhv.get(i) == 'LOCK') {
				'verify admin fee terlock'
				WebUI.verifyElementHasAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Admin Fee'),
					'readonly', GlobalVariable.TimeOut)
				
				'write to excel admin fee'
				CustomKeywords.'customizeKeyword.writeExcel.writeToExcelNumber'(GlobalVariable.DataFilePath, '9.TabFinancialData',
					20, GlobalVariable.NumofColm - 1, Integer.parseInt(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Admin Fee'),'value').replace(",","")))
				
			}
			
			if (feecapType.get(i) == 'AMT') {
				'Verify admin fee capitalized amount sesuai rule'
				if (WebUI.verifyMatch(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Admin Fee Capitalize_'),
						'value').replace(',', ''), feecap.get(i), false, FailureHandling.OPTIONAL) == false) {
					'write to excel reason failed verify rule'
					writeReasonFailedVerifRule()
				}
			}
		}
		
		'Pengecekan jika list fee pada rule sesuai dengan fee pada confins'
		if (CustomKeywords.'financialData.verifyFee.checkFeeCode'(sqlConnectionLOS, WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/label_Fee2'))) ==
		listFee.get(i)) {
			'Verify amount additional admin pada confins sesuai rule'
			if (WebUI.verifyMatch(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Additional Admin'),
					'value').replace(',', ''), fee.get(i), false) == false) {
				'write to excel reason failed verify rule'
				writeReasonFailedVerifRule()
			}
			
			if (feeBhv.get(i) == 'DEF') {
				'verify additional admin tidak terlock'
				WebUI.verifyElementNotHasAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Additional Admin'),
					'readonly', GlobalVariable.TimeOut)
			} else if (feeBhv.get(i) == 'LOCK') {
				'verify additional admin terlock'
				WebUI.verifyElementHasAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Additional Admin'),
					'readonly', GlobalVariable.TimeOut)
				
				'write to excel additional admin fee'
				CustomKeywords.'customizeKeyword.writeExcel.writeToExcelNumber'(GlobalVariable.DataFilePath, '9.TabFinancialData',
					21, GlobalVariable.NumofColm - 1, Integer.parseInt(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Additional Admin'),'value').replace(",","")))
			}
			
			if (feecapType.get(i) == 'AMT') {
				'Verify additional admin capitalized amount sesuai rule'
				if (WebUI.verifyMatch(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Additional Admin Capitalize'),
						'value').replace(',', ''), feecap.get(i), false, FailureHandling.OPTIONAL) == false) {
					'write to excel reason failed verify rule'
					writeReasonFailedVerifRule()
				}
			}
		}
		
		'Pengecekan jika list fee pada rule sesuai dengan fee pada confins'
		if (CustomKeywords.'financialData.verifyFee.checkFeeCode'(sqlConnectionLOS, WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/label_Fee3'))) ==
		listFee.get(i)) {
			'Verify notary fee pada confins sesuai rule'
			if (WebUI.verifyMatch(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Notary Fee'),
					'value').replace(',', ''), fee.get(i), false) == false) {
				'write to excel reason failed verify rule'
				writeReasonFailedVerifRule()
			}
			
			if (feeBhv.get(i) == 'DEF') {
				'Verify notary fee tidak terlock'
				WebUI.verifyElementNotHasAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Notary Fee'),
					'readonly', GlobalVariable.TimeOut)
			} else if (feeBhv.get(i) == 'LOCK') {
				'Verify notary fee terlock'
				WebUI.verifyElementHasAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Notary Fee'),
					'readonly', GlobalVariable.TimeOut)
				
				'write to excel notary fee'
				CustomKeywords.'customizeKeyword.writeExcel.writeToExcelNumber'(GlobalVariable.DataFilePath, '9.TabFinancialData',
					22, GlobalVariable.NumofColm - 1, Integer.parseInt(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Notary Fee'),'value').replace(",","")))
			}
			
			if (feecapType.get(i) == 'AMT') {
				'Verify notary fee capitalized amount sesuai rule'
				if (WebUI.verifyMatch(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Notary Fee Capitalize'),
						'value').replace(',', ''), feecap.get(i), false, FailureHandling.OPTIONAL) == false) {
					'write to excel reason failed verify rule'
					writeReasonFailedVerifRule()
				}
			}
		}
		
		'Pengecekan jika list fee pada rule sesuai dengan fee pada confins'
		if (CustomKeywords.'financialData.verifyFee.checkFeeCode'(sqlConnectionLOS, WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/label_Fee4'))) ==
		listFee.get(i)) {
			'Verify other fee pada confins sesuai rule'
			if (WebUI.verifyMatch(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Other Fee'),
					'value').replace(',', ''), fee.get(i), false) == false) {
				'write to excel reason failed verify rule'
				writeReasonFailedVerifRule()
			}
			
			if (feeBhv.get(i) == 'DEF') {
				'verify other fee tidak terlock'
				WebUI.verifyElementNotHasAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Other Fee'),
					'readonly', GlobalVariable.TimeOut)
			} else if (feeBhv.get(i) == 'LOCK') {
				'verify other fee terlock'
				WebUI.verifyElementHasAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Other Fee'),
					'readonly', GlobalVariable.TimeOut)
				
				'write to excel other fee'
				CustomKeywords.'customizeKeyword.writeExcel.writeToExcelNumber'(GlobalVariable.DataFilePath, '9.TabFinancialData',
					23, GlobalVariable.NumofColm - 1, Integer.parseInt(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Other Fee'),'value').replace(",","")))
			}
			
			if (feecapType.get(i) == 'AMT') {
				'Verify other fee capitalized amount sesuai rule'
				if (WebUI.verifyMatch(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Other Fee Capitalize'),
						'value').replace(',', ''), feecap.get(i), false, FailureHandling.OPTIONAL) == false) {
					'write to excel reason failed verify rule'
					writeReasonFailedVerifRule()
				}
			}
		}
		
		'Pengecekan jika list fee pada rule sesuai dengan fee pada confins'
		if (CustomKeywords.'financialData.verifyFee.checkFeeCode'(sqlConnectionLOS, WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/label_Fee5'))) ==
		listFee.get(i)) {
			'verify fiducia fee pada confins sesuai rule'
			if (WebUI.verifyMatch(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Fiducia Fee'),
					'value').replace(',', ''), fee.get(i), false) == false) {
				'write to excel reason failed verify rule'
				writeReasonFailedVerifRule()
			}
			
			if (feeBhv.get(i) == 'DEF') {
				'Verify fiducia fee tidak terlock'
				WebUI.verifyElementNotHasAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Fiducia Fee'),
					'readonly', GlobalVariable.TimeOut)
			} else if (feeBhv.get(i) == 'LOCK') {
				'Verify fiducia fee terlock'
				WebUI.verifyElementHasAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Fiducia Fee'),
					'readonly', GlobalVariable.TimeOut)
				
				'write to excel fiducia fee'
				CustomKeywords.'customizeKeyword.writeExcel.writeToExcelNumber'(GlobalVariable.DataFilePath, '9.TabFinancialData',
					24, GlobalVariable.NumofColm - 1, Integer.parseInt(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Fiducia Fee'),'value').replace(",","")))
			}
			
			if (feecapType.get(i) == 'AMT') {
				'Verify fiducia fee capitalized amount sesuai rule'
				if (WebUI.verifyMatch(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Fiducia Fee Capitalize'),
						'value').replace(',', ''), feecap.get(i), false, FailureHandling.OPTIONAL) == false) {
					'write to excel reason failed verify rule'
					writeReasonFailedVerifRule()
				}
			}
		}
		
		'Jika list fee merupakan provision'
		if (listFee.get(i) == 'PROVISION') {
			'Pengecekan fee type amount/percentage'
			if (feeType.get(i) == 'AMT') {
				'Verify provision fee pada confins sesuai rule'
				if (WebUI.verifyMatch(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Provision Fee Amount'),
						'value').replace(',', ''), fee.get(i), false) == false) {
					'write to excel reason failed verify rule'
					writeReasonFailedVerifRule()
				}
				
				if (feeBhv.get(i) == 'DEF') {
					'Verify provision fee amount tidak terlock'
					WebUI.verifyElementNotHasAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Provision Fee Amount'),
						'readonly', GlobalVariable.TimeOut) //Verify provision fee amt & pctg terlock
				} else if (feeBhv.get(i) == 'LOCK') {
					'Verify provision fee amount terlock'
					WebUI.verifyElementHasAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Provision Fee Amount'),
						'readonly', GlobalVariable.TimeOut)

					'Verify provision fee percentage terlock'
					WebUI.verifyElementHasAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Provision Fee Percentage'),
						'readonly', GlobalVariable.TimeOut)
					
					'write to excel provision fee percentage'
					CustomKeywords.'customizeKeyword.writeExcel.writeToExcelNumber'(GlobalVariable.DataFilePath, '9.TabFinancialData',
						37, GlobalVariable.NumofColm - 1, Integer.parseInt(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Provision Fee Percentage'),'value').replace(" %","").replace(",","")))
					
					'write to excel provision fee amount'
					CustomKeywords.'customizeKeyword.writeExcel.writeToExcelNumber'(GlobalVariable.DataFilePath, '9.TabFinancialData',
						38, GlobalVariable.NumofColm - 1, Integer.parseInt(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Provision Fee Amount'),'value').replace(",","")))
				}
				//Verify provision fee amt & pctg terlock
			} else if (feeType.get(i) == 'PRCNT') {
				'Verify provision fee pada confins sesuai rule'
				if (WebUI.verifyEqual(Double.parseDouble(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Provision Fee Percentage'),
							'value').replace(' %', '')), Double.parseDouble(fee.get(i))) == false) {
					'write to excel reason failed verify rule'
					writeReasonFailedVerifRule()
				}
				
				if (feeBhv.get(i) == 'DEF') {
					'verify provision fee percentage tidak terlock'
					WebUI.verifyElementNotHasAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Provision Fee Percentage'),
						'readonly', GlobalVariable.TimeOut)
				} else if (feeBhv.get(i) == 'LOCK') {
					'Verify provision fee amount terlock'
					WebUI.verifyElementHasAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Provision Fee Amount'),
						'readonly', GlobalVariable.TimeOut)

					'Verify provision fee percentage terlock'
					WebUI.verifyElementHasAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Provision Fee Percentage'),
						'readonly', GlobalVariable.TimeOut)
					
					'write to excel provision fee percentage'
					CustomKeywords.'customizeKeyword.writeExcel.writeToExcelNumber'(GlobalVariable.DataFilePath, '9.TabFinancialData',
						37, GlobalVariable.NumofColm - 1, Integer.parseInt(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Provision Fee Percentage'),'value').replace(" %","").replace(",","")))
					
					'write to excel provision fee amount'
					CustomKeywords.'customizeKeyword.writeExcel.writeToExcelNumber'(GlobalVariable.DataFilePath, '9.TabFinancialData',
						38, GlobalVariable.NumofColm - 1, Integer.parseInt(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Provision Fee Amount'),'value').replace(",","")))
				}
			}
			
			if (feecapType.get(i) == 'AMT') {
				'Verify provision fee capitalized amount'
				if (WebUI.verifyMatch(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Provision Fee Capitalize'),
						'value').replace(',', ''), feecap.get(i), false, FailureHandling.OPTIONAL) == false) {
					'write to excel reason failed verify rule'
					writeReasonFailedVerifRule()
				}
			}
		}
	}
}
	
	if(GlobalVariable.FirstTimeEntry=="Yes"){
		'get default rounding value dari db'
		Integer defaultRounding = CustomKeywords.'financialData.verifyFee.checkDefaultRounding'(sqlConnectionLOS, findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofColm, 12))
		
		'verify default rounding value'
		checkVerifyEqualOrMatch(WebUI.verifyEqual(defaultRounding, Integer.parseInt(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Rounding'), 'value').replace(",",""))))
	}
}

if (findTestData(excelPathTabFinancial).getValue(GlobalVariable.NumofColm, 20) == 'No') {
	'input admin fee'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Admin Fee'),
		findTestData(excelPathTabFinancial).getValue(GlobalVariable.NumofColm, 21),FailureHandling.OPTIONAL)

	if (WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Admin Fee Capitalize_checkbox'),
		GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
		if (findTestData(excelPathTabFinancial).getValue(GlobalVariable.NumofColm, 26) == 'Yes') {
			'click admin fee capitalized checkbox'
			WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Admin Fee Capitalize_checkbox'))
		}
	} else {
		if (findTestData(excelPathTabFinancial).getValue(GlobalVariable.NumofColm, 26) == 'No') {
			'click admin fee capitalized checkbox'
			WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Admin Fee Capitalize_checkbox'))
		}
	}
	
	if (findTestData(excelPathTabFinancial).getValue(GlobalVariable.NumofColm, 26) == 'Yes') {
		'input admin fee capitalized '
		WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Admin Fee Capitalize_'),
			findTestData(excelPathTabFinancial).getValue(GlobalVariable.NumofColm, 27))
	}
	
	'input additional admin'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Additional Admin'),
		findTestData(excelPathTabFinancial).getValue(GlobalVariable.NumofColm, 22))

	if (WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Additional Admin Capitalize_checkbox'),
		GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
		if (findTestData(excelPathTabFinancial).getValue(GlobalVariable.NumofColm, 28) == 'Yes') {
			'click additional admin capitalized checkbox'
			WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Additional Admin Capitalize_checkbox'))
		}
	} else {
		if (findTestData(excelPathTabFinancial).getValue(GlobalVariable.NumofColm, 28) == 'No') {
			'click additional admin capitalized checkbox'
			WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Additional Admin Capitalize_checkbox'))
		}
	}
	
	if (findTestData(excelPathTabFinancial).getValue(GlobalVariable.NumofColm, 28) == 'Yes') {
		'input additional admin capitalized'
		WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Additional Admin Capitalize'),
			findTestData(excelPathTabFinancial).getValue(GlobalVariable.NumofColm, 29))
	}
	
	'input notary fee'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Notary Fee'),
		findTestData(excelPathTabFinancial).getValue(GlobalVariable.NumofColm, 23))

	if (WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Notary Fee Capitalize_checkbox'),
		GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
		if (findTestData(excelPathTabFinancial).getValue(GlobalVariable.NumofColm, 30) == 'Yes') {
			'click notary fee capitalized checkbox'
			WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Notary Fee Capitalize_checkbox'))
		}
	} else {
		if (findTestData(excelPathTabFinancial).getValue(GlobalVariable.NumofColm, 30) == 'No') {
			'click notary fee capitalized checkbox'
			WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Notary Fee Capitalize_checkbox'))
		}
	}
	
	if (findTestData(excelPathTabFinancial).getValue(GlobalVariable.NumofColm, 30) == 'Yes') {
		'input lick notary fee capitalized '
		WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Notary Fee Capitalize'),
			findTestData(excelPathTabFinancial).getValue(GlobalVariable.NumofColm, 31))
	}
	
	'input other fee'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Other Fee'),
		findTestData(excelPathTabFinancial).getValue(GlobalVariable.NumofColm, 24))

	if (WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Other Fee Capitalize_checkbox'),
		GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
		if (findTestData(excelPathTabFinancial).getValue(GlobalVariable.NumofColm, 32) == 'Yes') {
			'click other fee capitalized checkbox'
			WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Other Fee Capitalize_checkbox'))
		}
	} else {
		if (findTestData(excelPathTabFinancial).getValue(GlobalVariable.NumofColm, 32) == 'No') {
			'click other fee capitalized checkbox'
			WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Other Fee Capitalize_checkbox'))
		}
	}
	
	if (findTestData(excelPathTabFinancial).getValue(GlobalVariable.NumofColm, 32) == 'Yes') {
		'input other fee capitalized'
		WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Other Fee Capitalize'),
			findTestData(excelPathTabFinancial).getValue(GlobalVariable.NumofColm, 33))
	}
	
	'input fiducia fee'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Fiducia Fee'),
		findTestData(excelPathTabFinancial).getValue(GlobalVariable.NumofColm, 25))

	if (WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Fiducia Fee Capitalize_checkbox'),
		GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
		if (findTestData(excelPathTabFinancial).getValue(GlobalVariable.NumofColm, 34) == 'Yes') {
			'click fiducia fee capitalized checkbox'
			WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Fiducia Fee Capitalize_checkbox'))
		}
	} else {
		if (findTestData(excelPathTabFinancial).getValue(GlobalVariable.NumofColm, 34) == 'No') {
			'click fiducia fee capitalized checkbox'
			WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Fiducia Fee Capitalize_checkbox'))
		}
	}
	
	if (findTestData(excelPathTabFinancial).getValue(GlobalVariable.NumofColm, 34) == 'Yes') {
		'input fiducia fee capitalized'
		WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Fiducia Fee Capitalize'),
			findTestData(excelPathTabFinancial).getValue(GlobalVariable.NumofColm, 35))
	}
	
	if (findTestData(excelPathTabFinancial).getValue(GlobalVariable.NumofColm, 36) == 'Amount') {
		'click amount radio button'
		WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/div_AmountRadio'))

		'click amount radio button'
		WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/div_AmountRadio'))
	} else if (findTestData(excelPathTabFinancial).getValue(GlobalVariable.NumofColm, 36) == 'Percentage') {
		'click percentage radio button'
		WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/div_PercentageRadio'))

		'click percentage radio button'
		WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/div_PercentageRadio'))
	}
	
	if(GlobalVariable.RoleCompany == 'Testing'){
		'get paymentmethod ddl value from db'
		ArrayList<String> provisionfee = CustomKeywords.'financialData.verifyFee.checkProvisionFeeDDL'(sqlConnectionFOU)
		
		'get total label from ddl provisionfee'
		int totalddlprovisionfee = WebUI.getNumberOfTotalOption(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/select_OTR-DPOTR-DP  Ins Cptlz  Fee Cptlz(Excl Provision)'))
	
		'verify total ddl provisionfee confins = total ddl db'
		WebUI.verifyEqual(totalddlprovisionfee, provisionfee.size())
		
		'verify isi ddl provisionfee confins = db'
		if (WebUI.verifyOptionsPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/select_OTR-DPOTR-DP  Ins Cptlz  Fee Cptlz(Excl Provision)'),
			provisionfee) == false) {
	
			'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDDL'
			CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('9.TabFinancialData', GlobalVariable.NumofColm, findTestData(excelPathTabFinancial).getValue(GlobalVariable.NumofColm, 2) + ';' + GlobalVariable.StatusFailed, GlobalVariable.ReasonFailedDDL + 'provisionfee')
	
			(GlobalVariable.FlagFailed)++
		}
			
		'get ratetype ddl value from db'
		ArrayList<String> ratetype = CustomKeywords.'financialData.verifyFee.checkRateTypeDDL'(sqlConnectionFOU)
				
				'get total label from ddl ratetype'
				int totalddlratetype = WebUI.getNumberOfTotalOption(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/select_RateType'))
				
				'verify total ddl ratetype confins = total ddl db'
				WebUI.verifyEqual(totalddlratetype - 1, ratetype.size())
				
				'verify isi ddl ratetype confins = db'
				if (WebUI.verifyOptionsPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/select_RateType'),
						ratetype) == false) {
					
					'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDDL'
					CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('9.TabFinancialData', GlobalVariable.NumofColm, GlobalVariable.StatusFailed, findTestData(excelPathTabFinancial).getValue(GlobalVariable.NumofColm, 2) + ';' + GlobalVariable.ReasonFailedDDL + 'ratetype')
					
					(GlobalVariable.FlagFailed)++
				}
	}
	
	'select Provision fee calculation base'
	WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/select_OTR-DPOTR-DP  Ins Cptlz  Fee Cptlz(Excl Provision)'),
		findTestData(excelPathTabFinancial).getValue(GlobalVariable.NumofColm, 37), false)

	if (WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Provision Fee Capitalize_checkbox'),
		GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
		if (findTestData(excelPathTabFinancial).getValue(GlobalVariable.NumofColm, 40) == 'Yes') {
			'click provision fee capitalized checkbox'
			WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Provision Fee Capitalize_checkbox'))
		}
	} else {
		if (findTestData(excelPathTabFinancial).getValue(GlobalVariable.NumofColm, 40) == 'No') {
			'click provision fee capitalized checkbox'
			WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Provision Fee Capitalize_checkbox'))
		}
	}
	
	if (findTestData(excelPathTabFinancial).getValue(GlobalVariable.NumofColm, 40) == 'Yes') {
		'input provision fee capitalized '
		WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Provision Fee Capitalize'),
			findTestData(excelPathTabFinancial).getValue(GlobalVariable.NumofColm, 41))
	}
	
	if (findTestData(excelPathTabFinancial).getValue(GlobalVariable.NumofColm, 36) == 'Percentage') {
		sendKeys(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Provision Fee Percentage'))

		'input provision fee percentage'
		WebUI.sendKeys(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Provision Fee Percentage'),
			Keys.chord(Keys.RIGHT, findTestData(excelPathTabFinancial).getValue(GlobalVariable.NumofColm, 38)), FailureHandling.OPTIONAL)
		
		WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Provision Fee Amount'))
		
		WebUI.delay(2)
		
		'write to excel provision fee amount'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcelNumber'(GlobalVariable.DataFilePath, '9.TabFinancialData',
			38, GlobalVariable.NumofColm - 1, Integer.parseInt(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Provision Fee Amount'),'value').replace(',','')))
		
	} else if (findTestData(excelPathTabFinancial).getValue(GlobalVariable.NumofColm, 36) == 'Amount') {
		
		sendKeys(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Provision Fee Amount'))

		'input provision fee amount'
		WebUI.sendKeys(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Provision Fee Amount'),
			Keys.chord(Keys.RIGHT, findTestData(excelPathTabFinancial).getValue(GlobalVariable.NumofColm, 39)))
	}
}

if(GlobalVariable.RoleCompany=="Testing" && GlobalVariable.CheckRuleCompany == 'Yes' && GlobalVariable.FirstTimeEntry == "Yes"){
	
	'declare arraylist result'
	ArrayList<String> result = new ArrayList<String>()
	
	Double dpnettpercent = Double.parseDouble(findTestData(excelPathTabFinancial).getValue(GlobalVariable.NumofColm, 72))
	
	'Hashmap untuk ambil nilai additional premi rate, sum insured amount, dan main coverage typenya dari rule excel berdasarkan condition'
	result = CustomKeywords.'financialData.verifyRate.verifyFinancialRate'(sqlConnectionLOS, appNo, dpnettpercent)
	
//	'Verify default effective rate'
//	if(WebUI.verifyEqual(Double.parseDouble(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/input_Effective Rate'),'value').replace(" %","")),Double.parseDouble(result.get(1)))==false){
//		'write to excel reason failed verify rule'
//		writeReasonFailedVerifRule()
//	}
//	
//	'Verify default supplier rate'
//	if(WebUI.verifyEqual(Double.parseDouble(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/input_Supplier Rate'),'value').replace(" %","")),Double.parseDouble(result.get(0)))==false){
//		'write to excel reason failed verify rule'
//		writeReasonFailedVerifRule()
//	}
	
	'Verify def/lock effective rate'
	if(result.get(2)=="LOCK"){
		'write to excel effective rate'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '9.TabFinancialData',
			43, GlobalVariable.NumofColm - 1, WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Effective Rate'),'value').replace(" %",""))
		'Verify effective rate terlock'
		if(WebUI.verifyElementHasAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Effective Rate'),'disabled', GlobalVariable.TimeOut)==false){
			'write to excel reason failed verify rule'
			writeReasonFailedVerifRule()
		}
	}
	else if(result.get(2)=="DEF"){
		'Verify effetcive rate tidak terlock/dapat diinput'
		if(WebUI.verifyElementNotHasAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Effective Rate'),'disabled', GlobalVariable.TimeOut)==false){
			'write to excel reason failed verify rule'
			writeReasonFailedVerifRule()
		}
	}
}

if (findTestData(excelPathTabFinancial).getValue(GlobalVariable.NumofColm, 43).length() > 1) {
	'select Rate type'
	WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/select_RateType'),
		findTestData(excelPathTabFinancial).getValue(GlobalVariable.NumofColm, 43), false, FailureHandling.OPTIONAL)

	if (findTestData(excelPathTabFinancial).getValue(GlobalVariable.NumofColm, 43) == 'Effective Rate') {
		'input effective rate'
		sendKeys(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Effective Rate'))

		WebUI.sendKeys(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Effective Rate'),
			Keys.chord(Keys.RIGHT, findTestData(excelPathTabFinancial).getValue(GlobalVariable.NumofColm, 44)), FailureHandling.OPTIONAL)
	} else if (findTestData(excelPathTabFinancial).getValue(GlobalVariable.NumofColm, 43) == 'Flat Rate') {
		'input flat rate'
		sendKeys(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Flat Rate'))

		WebUI.sendKeys(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Flat Rate'),
			Keys.chord(Keys.RIGHT, findTestData(excelPathTabFinancial).getValue(GlobalVariable.NumofColm, 45)), FailureHandling.OPTIONAL)
	}
	
	'get effective rate from confins'
	GlobalVariable.Effectiverate = WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Effective Rate'),
		'value', FailureHandling.OPTIONAL)

	'get flat rate from confins'
	GlobalVariable.Flatrate = WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Flat Rate'),
		'value', FailureHandling.OPTIONAL)
}

'Pengecekan supplier rate tidak terlock'
if(WebUI.verifyElementNotHasAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Supplier Rate'),'disabled', GlobalVariable.TimeOut,FailureHandling.OPTIONAL)){
	'Input supplier rate'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Supplier Rate'),findTestData(excelPathTabFinancial).getValue(GlobalVariable.NumofColm, 50))
}

if (findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData').getValue(
	GlobalVariable.NumofColm, 18) == 'Arrear') {
	if (findTestData(excelPathTabFinancial).getValue(GlobalVariable.NumofColm, 46) == 'Yes') {
		'input grace period'
		WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Grace Period'),
			findTestData(excelPathTabFinancial).getValue(GlobalVariable.NumofColm, 47))

		'select method'
		WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/select_--Select--Interest OnlyRoll Over'),
			findTestData(excelPathTabFinancial).getValue(GlobalVariable.NumofColm, 48), false)
	}
	else if (findTestData(excelPathTabFinancial).getValue(GlobalVariable.NumofColm, 46) == 'No') {
		'input grace period'
		WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Grace Period'),
			"0")

		'select method'
		WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/select_--Select--Interest OnlyRoll Over'),
			"-Select One-", false)
	}
}

'input rounding'
WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Rounding'),
	findTestData(excelPathTabFinancial).getValue(GlobalVariable.NumofColm, 52))

'click button calculate'
WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/button_Calculate'))

'click button calculate'
WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/button_Calculate'))

'write to excel effective rate'
CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '9.TabFinancialData',
	43, GlobalVariable.NumofColm - 1, WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Effective Rate'),'value').replace(" %",""))

if (WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/td_InterestAmount'),
	GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
	'click button cancel'
	WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/button_Cancel'))
	
	GlobalVariable.IsDataCancel = 1
	
	'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.StatusReasonCalculateGagal'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('9.TabFinancialData', GlobalVariable.NumofColm, GlobalVariable.StatusFailed, GlobalVariable.StatusReasonCalculateGagal)

	GlobalVariable.FlagFailed = 1
}

'Pengecekan jika tdp at mf tidak kosong'
if (findTestData(excelPathTabFinancial).getValue(GlobalVariable.NumofColm, 49).length() > 0) {
	'input tdp at mf'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_TDP Paid at MF'),
		findTestData(excelPathTabFinancial).getValue(GlobalVariable.NumofColm, 49))

	'click button calculate'
	WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/button_Calculate'))
}

if (GlobalVariable.RoleCompany == 'Testing') {
	'call test case financial data verif'
	WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabFinancialDataVerif'),
		[:], FailureHandling.CONTINUE_ON_FAILURE)
}

WebUI.delay(5)

'click button save'
WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/button_Save'))

Integer iscompleteMandatory = Integer.parseInt(findTestData(excelPathTabFinancial).getValue(
		GlobalVariable.NumofColm, 4))

if (iscompleteMandatory == 0 && GlobalVariable.FlagFailed==0) {
	'cek alert'
	GlobalVariable.FlagFailed = CustomKeywords.'checkSaveProcess.checkSaveProcess.checkAlert'(GlobalVariable.NumofColm, '9.TabFinancialData')
}

if (GlobalVariable.FlagFailed == 0) {
	'check save process write to excel'
	CustomKeywords.'checkSaveProcess.checkSaveProcess.checkStatus'(iscompleteMandatory, findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabTermConditionData/td_Checkbox'),
		GlobalVariable.NumofColm, '9.TabFinancialData')

	if (iscompleteMandatory == 0) {
		errorValObject = findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/div_errorvalidation')

		'cek validasi'
		CustomKeywords.'checkSaveProcess.checkSaveProcess.checkValidasi'(errorValObject, GlobalVariable.NumofColm, '9.TabFinancialData')
	}
}

WebUI.delay(5)


if (WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/ApplicationCurrentStep')),
	'FINANCIAL DATA', false, FailureHandling.OPTIONAL)) {
	'click button cancel'
	WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/button_Cancel-CF4W'))
	
	GlobalVariable.IsDataCancel = 1
}else{
	'check if role = testing & check store db = yes & status = success'
	if (GlobalVariable.RoleCompany == 'Testing' && GlobalVariable.CheckVerifStoreDBCompany=="Yes") {
		'call test case subsidy store db verif'
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/StoreDB/TabSubsidyStoreDBVerif'),
				[:], FailureHandling.CONTINUE_ON_FAILURE)
	
		'call test case financial datastore db verif'
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/StoreDB/TabFinancialDataStoreDBVerif'),
				[:], FailureHandling.CONTINUE_ON_FAILURE)
	
	}
}

def writeReasonFailedVerifRule() {
	
	'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedVerifyRule'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('9.TabFinancialData', GlobalVariable.NumofColm, GlobalVariable.StatusFailed, findTestData(excelPathTabFinancial).getValue(GlobalVariable.NumofColm, 2) + ';' + GlobalVariable.ReasonFailedVerifyRule)

	GlobalVariable.FlagFailed = 1
}

def sendKeys(TestObject object) {
	WebUI.sendKeys(object, Keys.chord(Keys.CONTROL, 'a'), FailureHandling.OPTIONAL)

	WebUI.sendKeys(object, Keys.chord(Keys.BACK_SPACE), FailureHandling.OPTIONAL)

	WebUI.sendKeys(object, Keys.chord(Keys.HOME), FailureHandling.OPTIONAL)
}

public checkVerifyEqualOrMatch(Boolean isMatch){
	if(isMatch==false && GlobalVariable.FlagFailed==0){

		'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedVerifyEqualOrMatch'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('9.TabFinancialData', GlobalVariable.NumofColm, GlobalVariable.StatusFailed, GlobalVariable.ReasonFailedVerifyEqualOrMatch)
		
		GlobalVariable.FlagFailed=1
	}
}