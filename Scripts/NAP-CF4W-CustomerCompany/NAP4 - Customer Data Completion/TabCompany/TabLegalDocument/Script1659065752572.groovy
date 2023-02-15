import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import java.text.SimpleDateFormat
import java.util.ArrayList

import org.openqa.selenium.By as By
import org.openqa.selenium.WebElement as WebElement
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

GlobalVariable.FlagFailed = 0

GlobalVariable.FlagWarning = 0

'call function get Data File'
getDataFile()

ArrayList<String> legaltypefaileddelete = new ArrayList<>()

ArrayList<String> faileddata = new ArrayList<>()

def LegalDocTypeArray = findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 12).split(';', -1)

def DocumentNoArray = findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 13).split(';', -1)

def DateIssuedArray = findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 14).split(';', -1)

def ExpiredDateArray = findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 15).split(';', -1)

def NotaryNameArray = findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 16).split(';', -1)

def NotaryLocationArray = findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 17).split(';', -1)

def NotesArray = findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 18).split(';')

'copyapp'
copyapp = findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerDataCompletion').getValue(GlobalVariable.NumofColm,
	10)

ArrayList<WebElement> variable

if (copyapp.equalsIgnoreCase('Edit')) {
	
	'count table legal doc jika no data'
	variableData = DriverFactory.getWebDriver().findElements(By.cssSelector('#legal-tab > app-legal-doc-tab > div > div.ng-star-inserted > table > tbody tr'))
	
	if (variableData.size() == 0) {
	'count table legal doc confins'
	variable = DriverFactory.getWebDriver().findElements(By.cssSelector('#legal-tab > app-legal-doc-tab > div > div.ng-star-inserted > lib-ucgridview > div > table > tbody tr'))
	
	for (i = 1; i <= variable.size(); i++) {
		'modify object legal doc type'
		modifyNewLegalDocType = WebUI.modifyObjectProperty(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/CustomerAsset/td_assettype'),
			'xpath', 'equals', ('//*[@id="legal-tab"]/app-legal-doc-tab/div/div[2]/lib-ucgridview/div/table/tbody/tr[' +
			i) + ']/td[1]', true)
		
		'modify object doc no type'
		modifyNewDocNo = WebUI.modifyObjectProperty(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/CustomerAsset/td_assettype'),
			'xpath', 'equals', ('//*[@id="legal-tab"]/app-legal-doc-tab/div/div[2]/lib-ucgridview/div/table/tbody/tr[' +
			i) + ']/td[2]', true)

		'modify object issue date'
		modifyNewIssueDate = WebUI.modifyObjectProperty(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/CustomerAsset/td_assettype'),
			'xpath', 'equals', ('//*[@id="legal-tab"]/app-legal-doc-tab/div/div[2]/lib-ucgridview/div/table/tbody/tr[' +
			i) + ']/td[3]', true)
		
		'modify object Expired Date'
		modifyNewExpiredDate = WebUI.modifyObjectProperty(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/CustomerAsset/td_assettype'),
			'xpath', 'equals', ('//*[@id="legal-tab"]/app-legal-doc-tab/div/div[2]/lib-ucgridview/div/table/tbody/tr[' +
			i) + ']/td[4]', true)
		
		'modify object NotaryName'
		modifyNewNotaryName = WebUI.modifyObjectProperty(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/CustomerAsset/td_assettype'),
			'xpath', 'equals', ('//*[@id="legal-tab"]/app-legal-doc-tab/div/div[2]/lib-ucgridview/div/table/tbody/tr[' +
			i) + ']/td[5]', true)
		
		String issueDate
		
		if(WebUI.getText(modifyNewIssueDate)!="-"){
			issueDate = convertDateFormat(WebUI.getText(modifyNewIssueDate))
		}
		else{
			issueDate = WebUI.getText(modifyNewIssueDate)
		}
		
		String expiredDate
		
		if(WebUI.getText(modifyNewExpiredDate)!="-"){
			expiredDate = convertDateFormat(WebUI.getText(modifyNewExpiredDate))
		}
		else{
			expiredDate = WebUI.getText(modifyNewExpiredDate)
		}
		
		
		for (legal = 1; legal <= LegalDocTypeArray.size(); legal++) {
			
				'verify if asset type sama persis'
				if (WebUI.getText(modifyNewLegalDocType).equalsIgnoreCase(LegalDocTypeArray[(legal - 1)]) && WebUI.getText(
					modifyNewDocNo).equalsIgnoreCase(DocumentNoArray[(legal - 1)]) && issueDate.equalsIgnoreCase(DateIssuedArray[(legal - 1)]) && expiredDate.equalsIgnoreCase(ExpiredDateArray[(legal - 1)]) && WebUI.getText(
					modifyNewNotaryName).equalsIgnoreCase(NotaryNameArray[(legal - 1)])) {
					break
				}
					
				'verify if asset type sama'
				if (WebUI.getText(modifyNewLegalDocType).equalsIgnoreCase(LegalDocTypeArray[(legal - 1)]) && WebUI.getText(
					modifyNewDocNo).equalsIgnoreCase(DocumentNoArray[(legal - 1)])) {
				
					'modify object button edit'
					modifyNewbuttonedit = WebUI.modifyObjectProperty(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/CustomerAsset/buttonedit'),
						'xpath', 'equals', ('//*[@id="legal-tab"]/app-legal-doc-tab/div/div[2]/lib-ucgridview/div/table/tbody/tr[' +
						i) + ']/td[6]/span/span[1]/span/a/i', true)
				
					'click button edit'
					WebUI.click(modifyNewbuttonedit)

					'call function verif legal doc type'
					verifLegalDocType(legal)
					
					'call function input legal doc'
					inputLegalDoc(faileddata)
					
					break
				} else {
					if (legal == LegalDocTypeArray.size()) {
						
						'modify object button delete'
						modifyNewbuttondelete = WebUI.modifyObjectProperty(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/CustomerAsset/buttondelete'),
							'xpath', 'equals', ('//*[@id="legal-tab"]/app-legal-doc-tab/div/div[2]/lib-ucgridview/div/table/tbody/tr[' +
							i) + ']/td[6]/span/span[2]/span/a/i', true)
						
						'get legal doc type sebelum delete'
						legaldoctypebefore = WebUI.getText(modifyNewLegalDocType)

						'get legal doc no sebelum delete'
						legaldocnobefore = WebUI.getText(modifyNewDocNo)

						'click button delete'
						WebUI.click(modifyNewbuttondelete)

						'acceptalert'
						WebUI.acceptAlert(FailureHandling.OPTIONAL)

						if (i == variable.size()) {
							if (WebUI.verifyElementPresent(modifyNewLegalDocType, GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
								'add legal failed kedalam array'
								legaltypefaileddelete.add(legaldoctypebefore + legaldocnobefore)
							}
						} else {
							'get legal doc type setelah delete'
							legaldoctypeafter = WebUI.getText(modifyNewLegalDocType)

							'get legal doc no setelah delete'
							legaldocnoAfter = WebUI.getText(modifyNewDocNo)

							if (WebUI.verifyMatch(legaldoctypeafter, legaldoctypebefore, false, FailureHandling.OPTIONAL) &&
							WebUI.verifyMatch(legaldocnoAfter, legaldocnobefore, false, FailureHandling.OPTIONAL)) {
								'add legal failed kedalam array'
								legaltypefaileddelete.add(legaldoctypebefore + legaldocnobefore)
							}
						}
						
						'count ulang table pada confins'
						variable = DriverFactory.getWebDriver().findElements(By.cssSelector('#legal-tab > app-legal-doc-tab > div > div.ng-star-inserted > lib-ucgridview > div > table > tbody tr'))
						
						i--
					}
				}
		}
		
		'count table legal doc jika no data'
		variableData = DriverFactory.getWebDriver().findElements(By.cssSelector('#legal-tab > app-legal-doc-tab > div > div.ng-star-inserted > table > tbody tr'))
		
		'verify jika table confins no data maka looping akan di skip'
		if(variableData.size() == 1){
			break
		}
	}
	}
	
	if (legaltypefaileddelete.size() > 0) {
		
		'Write To Excel GlobalVariable.StatusWarning and GlobalVariable.ReasonFailedDelete'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('6.LegalDocument', GlobalVariable.ColmNAP4, GlobalVariable.StatusWarning, findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 2) + ';' + GlobalVariable.ReasonFailedDelete + legaltypefaileddelete)
		
		(GlobalVariable.FlagWarning)++
	}
	
	if (variableData.size() == 0) {
	
		'count table legal doc row di confins'
		variable = DriverFactory.getWebDriver().findElements(By.cssSelector('#legal-tab > app-legal-doc-tab > div > div.ng-star-inserted > lib-ucgridview > div > table > tbody tr'))

		for (legal = 1; legal <= LegalDocTypeArray.size(); legal++) {
			for (i = 1; i <= variable.size(); i++) {
				'modify object legal doc type'
				modifyNewLegalDocType = WebUI.modifyObjectProperty(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/CustomerAsset/td_assettype'),
					'xpath', 'equals', ('//*[@id="legal-tab"]/app-legal-doc-tab/div/div[2]/lib-ucgridview/div/table/tbody/tr[' +
					i) + ']/td[1]', true)

				'modify object doc no type'
				modifyNewDocNo = WebUI.modifyObjectProperty(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/CustomerAsset/td_assettype'),
					'xpath', 'equals', ('//*[@id="legal-tab"]/app-legal-doc-tab/div/div[2]/lib-ucgridview/div/table/tbody/tr[' +
					i) + ']/td[2]', true)

				'verify if legal type beda'
				if (!(WebUI.getText(modifyNewLegalDocType).equalsIgnoreCase(LegalDocTypeArray[(legal - 1)]) && WebUI.getText(
					modifyNewDocNo).equalsIgnoreCase(DocumentNoArray[(legal - 1)]))) {
					if (i == variable.size()) {
						'click button add'
						WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/LegalDocument/button_Add'))

						'call function verif legal doc type'
						verifLegalDocType(legal)
						
						'call function input legal doc'
						 inputLegalDoc(faileddata)
						 
						 'count table legal doc table setelah add legal doc baru'
						 variable = DriverFactory.getWebDriver().findElements(By.cssSelector('#legal-tab > app-legal-doc-tab > div > div.ng-star-inserted > lib-ucgridview > div > table > tbody tr'))
						
						break
					}
				} else if (WebUI.getText(modifyNewLegalDocType).equalsIgnoreCase(LegalDocTypeArray[(legal - 1)])) {
					break
				}
			}
		}
	} else {
		for (legal = 1; legal <= LegalDocTypeArray.size(); legal++) {
			'click button add'
			WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/LegalDocument/button_Add'))

			'call function verif legal doc type'
			verifLegalDocType(legal)
			
			'call function input legal doc'
			inputLegalDoc(faileddata)
		}
	}
} else if (copyapp.equalsIgnoreCase('No')) {
	if (LegalDocTypeArray.size() > 0) {
		for (legal = 1; legal <= LegalDocTypeArray.size(); legal++) {
			'click button add'
			WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/LegalDocument/button_Add'))

			'call function verif legal doc type'
			verifLegalDocType(legal)
			
			'call function input legal doc'
			inputLegalDoc(faileddata)
		}
	}
}

'click button save and continue'
WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/LegalDocument/button_Save  Continue'))

if ((Integer.parseInt(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 4)) == 0) && (GlobalVariable.FlagFailed ==
0)) {
	'Check alert'
	GlobalVariable.FlagFailed = CustomKeywords.'checkSaveProcess.checkSaveProcess.checkAlert'(GlobalVariable.ColmNAP4,
		'6.LegalDocument')
}

if (GlobalVariable.FlagFailed == 0) {
	'Check save Process write to excel'
	CustomKeywords.'checkSaveProcess.checkSaveProcess.checkStatus'(Integer.parseInt(findTestData(GlobalVariable.excelPath).getValue(
				GlobalVariable.ColmNAP4, 4)), findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/OtherAttribute/button_Debtor Group_btn btn-raised btn-primary'),
		GlobalVariable.ColmNAP4, '6.LegalDocument')

	if (Integer.parseInt(findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 4)) == 0) {
		'Check error validasi'
		CustomKeywords.'checkSaveProcess.checkSaveProcess.checkValidasi'(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion/CustomerCompany/errorvalidasi'),
			GlobalVariable.ColmNAP4, '6.LegalDocument')
	}
}

if (GlobalVariable.FlagWarning > 0) {
	
	'Write To Excel GlobalVariable.StatusWarning and GlobalVariable.ReasonFailedDelete'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('6.LegalDocument', GlobalVariable.ColmNAP4, GlobalVariable.StatusWarning, findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 2) + ';' + GlobalVariable.ReasonFailedInputData + faileddata)
}

if (WebUI.verifyElementPresent(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/LegalDocument/th_Expired Date'),
	GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
	'click button back'
	WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerDataCompletion/button_Back'))
}
	
'check if role = testing & verif store db = yes & status = SUCCESS'
if ((GlobalVariable.RoleCompany == 'Testing') && (GlobalVariable.CheckVerifStoreDBCompany == 'Yes') && findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 1) == 'SUCCESS') {
	'declare numofverifstore = ColmNAP4'
	GlobalVariable.NumofVerifStore = GlobalVariable.ColmNAP4

	'Call test case verify legal doc store data'
	WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/NAP4VerifyStoreData/Company/TabLegalDocVerifStoreDataDB'),
		[:], FailureHandling.CONTINUE_ON_FAILURE)
}

def verifLegalDocType(int legal){
	if (GlobalVariable.RoleCompany == 'Testing') {
		
		'connect DB FOU'
		Sql sqlconnectionFOU = CustomKeywords.'dbConnection.connectDB.connectFOU'()

		ArrayList<WebElement> LegalDocType

		'get data array dari db'
		LegalDocType = CustomKeywords.'nap4Data.checkNAP4.checkLegaldocument'(sqlconnectionFOU)

		'verify array dari db == option list confins'
		if (WebUI.verifyOptionsPresent(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/LegalDocument/select_NIP  SIUP  TDP'),
			LegalDocType) == false) {
			
			'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDDL'
			CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('6.LegalDocument', legal, GlobalVariable.StatusFailed, findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 2) + ';' + GlobalVariable.ReasonFailedDDL)

			(GlobalVariable.FlagFailed)++
		}
		
		'get total label from ddl'
		int totalLegaldoctypeddl = WebUI.getNumberOfTotalOption(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/LegalDocument/select_NIP  SIUP  TDP'))

		'verify total ddl confins = total ddl db'
		if (WebUI.verifyEqual(totalLegaldoctypeddl, LegalDocType.size()) == false){
			
		'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDDL'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('6.LegalDocument', legal, GlobalVariable.StatusFailed, findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 2) + ';' + GlobalVariable.ReasonFailedDDL)
		
		(GlobalVariable.FlagFailed)++
		}
	}
}

def inputLegalDoc(ArrayList<String> faileddata){
	def LegalDocTypeArray = findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 12).split(';', -1)
	
	def DocumentNoArray = findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 13).split(';', -1)
	
	def DateIssuedArray = findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 14).split(';', -1)
	
	def ExpiredDateArray = findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 15).split(';', -1)
	
	def NotaryNameArray = findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 16).split(';', -1)
	
	def NotaryLocationArray = findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 17).split(';', -1)
	
	def NotesArray = findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 18).split(';', -1)
	
	'select legal doc type'
	WebUI.selectOptionByLabel(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/LegalDocument/select_NIP  SIUP  TDP'),
		LegalDocTypeArray[(legal - 1)], false)

	'input doc no'
	WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/LegalDocument/input_Document No'),
		DocumentNoArray[(legal - 1)])

	'input date issued'
	WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/LegalDocument/input_Issued Date'),
		DateIssuedArray[(legal - 1)])

	if (findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 15).length() > 0) {
		'input expired date'
		WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/LegalDocument/input_Expired Date'),
			ExpiredDateArray[(legal - 1)])
	}
	
	if (findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 16).length() > 0) {
		'input notary name'
		WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/LegalDocument/input_Notary Name'),
			NotaryNameArray[(legal - 1)])
	}
	
	if (findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 17).length() > 0) {
		'input notary location'
		WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/LegalDocument/input_Notary Location'),
			NotaryLocationArray[(legal - 1)])
	}
	
	if (findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.ColmNAP4, 18).length() > 0) {
		'input Notes'
		WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/LegalDocument/textarea_Notes'),
			NotesArray[(legal - 1)])
	}
	
	'click button save'
	WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/LegalDocument/button_Save'))

	if (WebUI.verifyElementPresent(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/LegalDocument/button_Cancel'),
		GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
		'click button cancel'
		WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/LegalDocument/button_Cancel'))

		'add failed delete data to array'
		faileddata.add(LegalDocTypeArray[(legal - 1)])

		'GlobalVariable.FlagWarning +1'
		GlobalVariable.FlagWarning++
	}
}

public convertDateFormat(String sentDate){
	'convert date confins dan excel agar sama'
	SimpleDateFormat sdf = new SimpleDateFormat('dd-MMM-yyyy')
	
	Date parsedDate = null
	
	parsedDate = sdf.parse(sentDate)
	
	sdf = new SimpleDateFormat('MM/dd/yyyy')
	
	String sDate = sdf.format(parsedDate)
	
	return sDate
}

def getDataFile(){
	if(GlobalVariable.APPSTEP == 'CUSTOMER'){
		'get data file path'
		GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'(GlobalVariable.DataFileCustomerCompany)
		
		'declare data file Global variable'
		GlobalVariable.excelPath = 'NAP-'+ GlobalVariable.LOB +'-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument'
	}else if(GlobalVariable.APPSTEP == 'SHAREHOLDER COMPANY'){
		'get data file path'
		GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'(GlobalVariable.DataFileManagementShareholderCompany)
				
		'declare data file Global variable'
		GlobalVariable.excelPath ='NAP-'+ GlobalVariable.LOB +'-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementshareholderCompany/LegalDocument'
	}else if(GlobalVariable.APPSTEP == 'GUARANTOR COMPANY'){
		'get data file path'
		GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'(GlobalVariable.DataFileGuarantorCompanyCompany)
		
		'declare data file Global variable'
		GlobalVariable.excelPath = 'NAP-'+ GlobalVariable.LOB +'-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/LegalDocument'
	}
}