import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
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

'get data file path'
GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'(GlobalVariable.DataFileGuarantorCompany)

ArrayList<WebElement> legaltypefaileddelete = new ArrayList<WebElement>()

ArrayList<WebElement> faileddata = new ArrayList<WebElement>()

GlobalVariable.FindDataFile = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorCompany/LegalDocument - Company - Guarantor')

def LegalDocTypeArray = GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofGuarantor, 12).split(';')

def DocumentNoArray = GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofGuarantor, 13).split(';')

def DateIssuedArray = GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofGuarantor, 14).split(';')

def ExpiredDateArray = GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofGuarantor, 15).split(';')

def NotaryNameArray = GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofGuarantor, 16).split(';')

def NotaryLocationArray = GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofGuarantor, 17).split(';')

def NotesArray = GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofGuarantor, 18).split(';')

'copyapp'
copyapp = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/CustomerDataCompletion').getValue(
    GlobalVariable.NumofColm, 10)

ArrayList<WebElement> variable

if (copyapp.equalsIgnoreCase('Edit')) {
	'count table legal doc confins'   
	variable = DriverFactory.getWebDriver().findElements(By.cssSelector('#legal-tab > app-legal-doc-tab > div > div.ng-star-inserted > lib-ucgridview > div > table > tbody tr'))
	
    for (i = 1; i <= variable.size(); i++) {
        'modify object legal doc type'
        modifyNewLegalDocType = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerAsset - Personal/td_assettype'), 
            'xpath', 'equals', ('//*[@id="legal-tab"]/app-legal-doc-tab/div/div[2]/lib-ucgridview/div/table/tbody/tr[' + 
            i) + ']/td[1]', true)

        'modify object doc no type'
        modifyNewDocNo = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerAsset - Personal/td_assettype'), 
            'xpath', 'equals', ('//*[@id="legal-tab"]/app-legal-doc-tab/div/div[2]/lib-ucgridview/div/table/tbody/tr[' + 
            i) + ']/td[2]', true)

        for (legal = 1; legal <= LegalDocTypeArray.size(); legal++) {
                'verify if asset type sama'
                if (WebUI.getText(modifyNewLegalDocType).equalsIgnoreCase(LegalDocTypeArray[(legal - 1)]) && WebUI.getText(
                    modifyNewDocNo).equalsIgnoreCase(DocumentNoArray[(legal - 1)])) {
				
					'modify object button edit'
					modifyNewbuttonedit = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerAsset - Personal/buttonedit'),
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
						modifyNewbuttondelete = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerAsset - Personal/buttondelete'),
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
                            if (WebUI.verifyElementNotPresent(modifyNewLegalDocType, 5, FailureHandling.OPTIONAL)) {
                                'count ulang table pada confins'
                                variable = DriverFactory.getWebDriver().findElements(By.cssSelector('#legal-tab > app-legal-doc-tab > div > div.ng-star-inserted > lib-ucgridview > div > table > tbody tr'))
                            } else {
                                'add legal failed kedalam array'
                                legaltypefaileddelete.add(legaldoctypebefore + legaldocnobefore)
                            }
                        } else {
                            'get legal doc type setelah delete'
                            legaldoctypeafter = WebUI.getText(modifyNewLegalDocType)

                            'get legal doc no setelah delete'
                            legaldocnoAfter = WebUI.getText(modifyNewDocNo)

                            if (WebUI.verifyNotMatch(legaldoctypeafter, legaldoctypebefore, false, FailureHandling.OPTIONAL) && 
                            WebUI.verifyNotMatch(legaldocnoAfter, legaldocnobefore, false, FailureHandling.OPTIONAL)) {
                                'count ulang table pada confins'
                                variable = DriverFactory.getWebDriver().findElements(By.cssSelector('#legal-tab > app-legal-doc-tab > div > div.ng-star-inserted > lib-ucgridview > div > table > tbody tr'))
                            } else {
                                'add legal failed kedalam array'
                                legaltypefaileddelete.add(legaldoctypebefore + legaldocnobefore)

                                continue
                            }
                        }
                        
                        i--
                    }
                }
        }
    }
    
    if (legaltypefaileddelete.size() > 0) {
		'write to excel status warning'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '6.LegalDocument', 
            0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusWarning)

		'write to excel reason failed delete'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '6.LegalDocument', 
            1, GlobalVariable.NumofColm - 1, GlobalVariable.ReasonFailedDelete + legaltypefaileddelete)

        (GlobalVariable.FlagWarning)++
    }
    
    if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument/buttonedit'), 
        5, FailureHandling.OPTIONAL)) {
	
		'count table legal doc row di confins'
        variable = DriverFactory.getWebDriver().findElements(By.cssSelector('#legal-tab > app-legal-doc-tab > div > div.ng-star-inserted > lib-ucgridview > div > table > tbody tr'))

        for (legal = 1; legal <= LegalDocTypeArray.size(); legal++) {
            for (i = 1; i <= variable.size(); i++) {
                'modify object legal doc type'
                modifyNewLegalDocType = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerAsset - Personal/td_assettype'), 
                    'xpath', 'equals', ('//*[@id="legal-tab"]/app-legal-doc-tab/div/div[2]/lib-ucgridview/div/table/tbody/tr[' + 
                    i) + ']/td[1]', true)

                'modify object doc no type'
                modifyNewDocNo = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerAsset - Personal/td_assettype'), 
                    'xpath', 'equals', ('//*[@id="legal-tab"]/app-legal-doc-tab/div/div[2]/lib-ucgridview/div/table/tbody/tr[' + 
                    i) + ']/td[2]', true)

                'verify if legal type beda'
                if (!(WebUI.getText(modifyNewLegalDocType).equalsIgnoreCase(LegalDocTypeArray[(legal - 1)]) && WebUI.getText(
                    modifyNewDocNo).equalsIgnoreCase(DocumentNoArray[(legal - 1)]))) {
                    if (i == variable.size()) {
                        'click button add'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument/button_Add'))

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
        variable = DriverFactory.getWebDriver().findElements(By.cssSelector('#legal-tab > app-legal-doc-tab > div > div.ng-star-inserted > table > tbody tr'))

        for (legal = 1; legal <= LegalDocTypeArray.size(); legal++) {
            'click button add'
            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument/button_Add'))

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
            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument/button_Add'))

			'call function verif legal doc type'
			verifLegalDocType(legal)
            
            'call function input legal doc'
			inputLegalDoc(faileddata)
        }
    }
}

'click button save and continue'
WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument/button_Save  Continue'))

if ((Integer.parseInt(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofColm, 4)) == 0) && (GlobalVariable.FlagFailed == 
0)) {
    'Check alert'
    GlobalVariable.FlagFailed = CustomKeywords.'checkSaveProcess.checkSaveProcess.checkAlert'(GlobalVariable.NumofColm, 
        '6.LegalDocument')
}

if (GlobalVariable.FlagFailed == 0) {
    'Check save Process write to excel'
    CustomKeywords.'checkSaveProcess.checkSaveProcess.checkStatus'(Integer.parseInt(GlobalVariable.FindDataFile.getValue(
                GlobalVariable.NumofColm, 4)), findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/OtherAttribute/button_Debtor Group_btn btn-raised btn-primary'), 
        GlobalVariable.NumofColm, '6.LegalDocument')

    if (Integer.parseInt(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofColm, 4)) == 0) {
        'Check error validasi'
        CustomKeywords.'checkSaveProcess.checkSaveProcess.checkValidasi'(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/errorvalidasi'), 
            GlobalVariable.NumofColm, '6.LegalDocument')
    }
}

if (GlobalVariable.FlagWarning > 0) {
	'write to excel status warning'
    CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '6.LegalDocument', 0, GlobalVariable.NumofColm - 
        1, GlobalVariable.StatusWarning)

	'write to excel failed reason'
    CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '6.LegalDocument', 1, GlobalVariable.NumofColm - 
        1, GlobalVariable.ReasonFailedInputData + faileddata)
}

if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument/th_Expired Date'), 
    10, FailureHandling.OPTIONAL)) {
    'click button back'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerDataCompletion/button_Back'))
}

if ((GlobalVariable.Role == 'Testing') && (GlobalVariable.CheckVerifStoreDBPersonal == 'Yes')) {
    GlobalVariable.NumofVerifStore = GlobalVariable.NumofGuarantor

    'call test case verify legal doc store data'
    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/NAP4VerifyStoreData/Company/TabLegalDocVerifStoreDataDB'), 
        [:], FailureHandling.CONTINUE_ON_FAILURE)
}

def verifLegalDocType(int legal){
	if (legal == 1) {
                if (GlobalVariable.RoleCompany == 'Testing') {

					'connect DB FOU'
					Sql sqlconnectionFOU = CustomKeywords.'dbconnection.connectDB.connectFOU'()

                    ArrayList<String> LegalDocType

                    'get data array dari db'
                    LegalDocType = CustomKeywords.'dbconnection.checkNAP4db.checkLegaldocument'(sqlconnectionFOU)

                    'verify array dari db == option list confins'
                    if (WebUI.verifyOptionsPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument/select_NIP  SIUP  TDP'), 
                        LegalDocType) == false) {
                        'write to excel if failed'
                        CustomKeywords.'customizeKeyword.writeExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '6.LegalDocument', 
                            0, GlobalVariable.NumofGuarantor - 1, GlobalVariable.StatusFailed)

                        'write to excel reasonfailedddl'
                        CustomKeywords.'customizeKeyword.writeExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '6.LegalDocument', 
                            1, GlobalVariable.NumofGuarantor - 1, GlobalVariable.ReasonFailedDDL)

                        GlobalVariable.FlagFailed = 1
                    }
                    
                    'get total label from ddl'
                    int totalLegaldoctypeddl = WebUI.getNumberOfTotalOption(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument/select_NIP  SIUP  TDP'))

                    'verify total ddl confins = total ddl db'
                    if (WebUI.verifyEqual(totalLegaldoctypeddl, LegalDocType.size()) == false) {
                        'write to excel if failed'
                        CustomKeywords.'customizeKeyword.writeExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '6.LegalDocument', 
                            0, GlobalVariable.NumofGuarantor - 1, GlobalVariable.StatusFailed)

                        'write to excel reasonfailedddl'
                        CustomKeywords.'customizeKeyword.writeExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '6.LegalDocument', 
                            1, GlobalVariable.NumofGuarantor - 1, GlobalVariable.ReasonFailedDDL)

                        GlobalVariable.FlagFailed = 1
                    }
                }
            }
}

def inputLegalDoc(){
	'select legal doc type'
	WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument/select_NIP  SIUP  TDP'),
		LegalDocTypeArray[(legal - 1)], false)

	'input doc no'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument/input_Document No'),
		DocumentNoArray[(legal - 1)])

	'input date issued'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument/input_Issued Date'),
		DateIssuedArray[(legal - 1)])

	if (GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofGuarantor, 15).length() > 0) {
		'input expired date'
		WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument/input_Expired Date'),
			ExpiredDateArray[(legal - 1)])
	}
	
	if (GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofGuarantor, 16).length() > 0) {
		'input notary name'
		WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument/input_Notary Name'),
			NotaryNameArray[(legal - 1)])
	}
	
	if (GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofGuarantor, 17).length() > 0) {
		'input notary location'
		WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument/input_Notary Location'),
			NotaryLocationArray[(legal - 1)])
	}
	
	if (GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofGuarantor, 18).length() > 0) {
		'input Notes'
		WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument/textarea_Notes'),
			NotesArray[(legal - 1)])
	}
	
	'click button save'
	WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument/button_Save'))

	if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument/button_Cancel'),
		5, FailureHandling.OPTIONAL)) {
		'click button cancel'
		WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument/button_Cancel'))

		faileddata.add(LegalDocTypeArray[(legal - 1)])

		GlobalVariable.FlagWarning++
	}
}