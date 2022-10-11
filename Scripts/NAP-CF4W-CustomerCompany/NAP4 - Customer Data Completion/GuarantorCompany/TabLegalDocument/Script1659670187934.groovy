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

String userDir = System.getProperty('user.dir')

String filePath = userDir + GlobalVariable.DataFileGuarantorCompanyCompany

GlobalVariable.DataFilePath = filePath

GlobalVariable.findDataFile = findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/LegalDocument - Company - GuarantorCompany')

def LegalDocTypeArray = findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/LegalDocument - Company - GuarantorCompany').getValue(
    GlobalVariable.NumofGuarantor, 12).split(';')

def DocumentNoArray = findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/LegalDocument - Company - GuarantorCompany').getValue(
    GlobalVariable.NumofGuarantor, 13).split(';')

def DateIssuedArray = findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/LegalDocument - Company - GuarantorCompany').getValue(
    GlobalVariable.NumofGuarantor, 14).split(';')

def ExpiredDateArray = findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/LegalDocument - Company - GuarantorCompany').getValue(
    GlobalVariable.NumofGuarantor, 15).split(';')

def NotaryNameArray = findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/LegalDocument - Company - GuarantorCompany').getValue(
    GlobalVariable.NumofGuarantor, 16).split(';')

def NotaryLocationArray = findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/LegalDocument - Company - GuarantorCompany').getValue(
    GlobalVariable.NumofGuarantor, 17).split(';')

def NotesArray = findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/LegalDocument - Company - GuarantorCompany').getValue(
    GlobalVariable.NumofGuarantor, 18).split(';')

'copyapp'
copyapp = findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerDataCompletion').getValue(GlobalVariable.NumofColm, 
    10)

ArrayList<WebElement> variable

if (copyapp.equalsIgnoreCase('Edit')) {
    if (WebUI.verifyElementPresent(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument - Company/buttonedit'), 
        5, FailureHandling.OPTIONAL)) {
        variable = DriverFactory.getWebDriver().findElements(By.cssSelector('#legal-tab > app-legal-doc-tab > div > div.ng-star-inserted > lib-ucgridview > div > table > tbody tr'))
    } else {
        variable = DriverFactory.getWebDriver().findElements(By.cssSelector('#legal-tab > app-legal-doc-tab > div > div.ng-star-inserted > table > tbody tr'))
    }
    
    for (i = 1; i <= variable.size(); i++) {
        'modify object legal doc type'
        modifyNewLegalDocType = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerAsset - Personal/td_assettype'), 
            'xpath', 'equals', ('//*[@id="legal-tab"]/app-legal-doc-tab/div/div[2]/lib-ucgridview/div/table/tbody/tr[' + 
            i) + ']/td[1]', true)

        'modify object doc no type'
        modifyNewDocNo = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerAsset - Personal/td_assettype'), 
            'xpath', 'equals', ('//*[@id="legal-tab"]/app-legal-doc-tab/div/div[2]/lib-ucgridview/div/table/tbody/tr[' + 
            i) + ']/td[2]', true)

        'modify object button edit'
        modifyNewbuttonedit = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerAsset - Personal/buttonedit'), 
            'xpath', 'equals', ('//*[@id="legal-tab"]/app-legal-doc-tab/div/div[2]/lib-ucgridview/div/table/tbody/tr[' + 
            i) + ']/td[6]/span/span[1]/span/a/i', true)

        'modify object button delete'
        modifyNewbuttondelete = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerAsset - Personal/buttondelete'), 
            'xpath', 'equals', ('//*[@id="legal-tab"]/app-legal-doc-tab/div/div[2]/lib-ucgridview/div/table/tbody/tr[' + 
            i) + ']/td[6]/span/span[2]/span/a/i', true)

        for (legal = 1; legal <= LegalDocTypeArray.size(); legal++) {
            if (WebUI.verifyElementPresent(modifyNewbuttonedit, 5, FailureHandling.OPTIONAL)) {
                'verify if asset type sama'
                if (WebUI.getText(modifyNewLegalDocType).equalsIgnoreCase(LegalDocTypeArray[(legal - 1)]) && WebUI.getText(
                    modifyNewDocNo).equalsIgnoreCase(DocumentNoArray[(legal - 1)])) {
                    'click button edit'
                    WebUI.click(modifyNewbuttonedit)

					if (legal == 1) {
						if (GlobalVariable.RoleCompany == 'Testing') {
							'Koneksi database'
							String servername = findTestData('Login/Login').getValue(1, 7)
		
							String instancename = findTestData('Login/Login').getValue(2, 7)
		
							String username = findTestData('Login/Login').getValue(3, 7)
		
							String password = findTestData('Login/Login').getValue(4, 7)
		
							String databaseFOU = findTestData('Login/Login').getValue(5, 7)
		
							String driverclassname = findTestData('Login/Login').getValue(6, 7)
		
							String urlFOU = (((servername + ';instanceName=') + instancename) + ';databaseName=') + databaseFOU
		
							Sql sqlConnectionFOU = CustomKeywords.'dbconnection.connectDB.connect'(urlFOU, username, password, driverclassname)
		
							ArrayList<WebElement> LegalDocType
		
							'get data array dari db'
							LegalDocType = CustomKeywords.'dbconnection.checkNAP4db.checkLegaldocument'(sqlConnectionFOU)
		
							'verify array dari db == option list confins'
							WebUI.verifyOptionsPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument - Company/select_NIP  SIUP  TDP'),
								LegalDocType)
		
							'get total label from ddl'
							int totalLegaldoctypeddl = WebUI.getNumberOfTotalOption(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument - Company/select_NIP  SIUP  TDP'))
		
							'verify total ddl confins = total ddl db'
							WebUI.verifyEqual(totalLegaldoctypeddl, LegalDocType.size())
						}
					}
					
                    'select legal doc type'
                    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument - Company/select_NIP  SIUP  TDP'), 
                        LegalDocTypeArray[(legal - 1)], false)

                    'input doc no'
                    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument - Company/input_Document No'), 
                        DocumentNoArray[(legal - 1)])

                    'input date issued'
                    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument - Company/input_Issued Date'), 
                        DateIssuedArray[(legal - 1)])

                    if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/LegalDocument - Company - GuarantorCompany').getValue(
                        GlobalVariable.NumofGuarantor, 15).length() > 0) {
                        'input expired date'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument - Company/input_Expired Date'), 
                            ExpiredDateArray[(legal - 1)])
                    }
                    
                    if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/LegalDocument - Company - GuarantorCompany').getValue(
                        GlobalVariable.NumofGuarantor, 16).length() > 0) {
                        'input notary name'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument - Company/input_Notary Name'), 
                            NotaryNameArray[(legal - 1)])
                    }
                    
                    if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/LegalDocument - Company - GuarantorCompany').getValue(
                        GlobalVariable.NumofGuarantor, 17).length() > 0) {
                        'input notary location'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument - Company/input_Notary Location'), 
                            NotaryLocationArray[(legal - 1)])
                    }
                    
                    if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/LegalDocument - Company - GuarantorCompany').getValue(
                        GlobalVariable.NumofGuarantor, 18).length() > 0) {
                        'input Notes'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument - Company/textarea_Notes'), 
                            NotesArray[(legal - 1)])
                    }
                    
                    'click button save'
                    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument - Company/button_Save'))

                    if (WebUI.verifyElementPresent(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument - Company/button_Cancel'), 
                        5, FailureHandling.OPTIONAL)) {
                        'click button cancel'
                        WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument - Company/button_Cancel'))
                    }
                    
                    break
                } else {
                    if (legal == LegalDocTypeArray.size()) {
                        'click button delete'
                        WebUI.click(modifyNewbuttondelete)

                        'acceptalert'
                        WebUI.acceptAlert(FailureHandling.OPTIONAL)

                        i--
                    }
                }
            } else {
                break
            }
        }
    }
    
    if (WebUI.verifyElementPresent(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument - Company/buttonedit'), 
        5, FailureHandling.OPTIONAL)) {
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

                'modify object button edit'
                modifyNewbuttonedit = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerAsset - Personal/buttonedit'), 
                    'xpath', 'equals', ('//*[@id="legal-tab"]/app-legal-doc-tab/div/div[2]/lib-ucgridview/div/table/tbody/tr[' + 
                    i) + ']/td[6]/span/span[1]/span/a/i', true)

                'modify object button delete'
                modifyNewbuttondelete = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerAsset - Personal/buttondelete'), 
                    'xpath', 'equals', ('//*[@id="legal-tab"]/app-legal-doc-tab/div/div[2]/lib-ucgridview/div/table/tbody/tr[' + 
                    i) + ']/td[6]/span/span[2]/span/a/i', true)

                'verify if legal type beda'
                if (!(WebUI.getText(modifyNewLegalDocType).equalsIgnoreCase(LegalDocTypeArray[(legal - 1)]) && WebUI.getText(
                    modifyNewDocNo).equalsIgnoreCase(DocumentNoArray[(legal - 1)])) || WebUI.verifyElementNotPresent(modifyNewbuttonedit, 
                    5, FailureHandling.OPTIONAL)) {
                    if (i == variable.size()) {
                        'click button add'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument - Company/button_Add'))

						if (legal == 1) {
							if (GlobalVariable.RoleCompany == 'Testing') {
								'Koneksi database'
								String servername = findTestData('Login/Login').getValue(1, 7)
			
								String instancename = findTestData('Login/Login').getValue(2, 7)
			
								String username = findTestData('Login/Login').getValue(3, 7)
			
								String password = findTestData('Login/Login').getValue(4, 7)
			
								String databaseFOU = findTestData('Login/Login').getValue(5, 7)
			
								String driverclassname = findTestData('Login/Login').getValue(6, 7)
			
								String urlFOU = (((servername + ';instanceName=') + instancename) + ';databaseName=') + databaseFOU
			
								Sql sqlConnectionFOU = CustomKeywords.'dbconnection.connectDB.connect'(urlFOU, username, password, driverclassname)
			
								ArrayList<WebElement> LegalDocType
			
								'get data array dari db'
								LegalDocType = CustomKeywords.'dbconnection.checkNAP4db.checkLegaldocument'(sqlConnectionFOU)
			
								'verify array dari db == option list confins'
								WebUI.verifyOptionsPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument - Company/select_NIP  SIUP  TDP'),
									LegalDocType)
			
								'get total label from ddl'
								int totalLegaldoctypeddl = WebUI.getNumberOfTotalOption(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument - Company/select_NIP  SIUP  TDP'))
			
								'verify total ddl confins = total ddl db'
								WebUI.verifyEqual(totalLegaldoctypeddl, LegalDocType.size())
							}
						}
						
                        'select legal doc type'
                        WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument - Company/select_NIP  SIUP  TDP'), 
                            LegalDocTypeArray[(legal - 1)], false)

                        'input doc no'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument - Company/input_Document No'), 
                            DocumentNoArray[(legal - 1)])

                        'input date issued'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument - Company/input_Issued Date'), 
                            DateIssuedArray[(legal - 1)])

                        if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/LegalDocument - Company - GuarantorCompany').getValue(
                            GlobalVariable.NumofGuarantor, 15).length() > 0) {
                            'input expired date'
                            WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument - Company/input_Expired Date'), 
                                ExpiredDateArray[(legal - 1)])
                        }
                        
                        if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/LegalDocument - Company - GuarantorCompany').getValue(
                            GlobalVariable.NumofGuarantor, 16).length() > 0) {
                            'input notary name'
                            WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument - Company/input_Notary Name'), 
                                NotaryNameArray[(legal - 1)])
                        }
                        
                        if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/LegalDocument - Company - GuarantorCompany').getValue(
                            GlobalVariable.NumofGuarantor, 17).length() > 0) {
                            'input notary location'
                            WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument - Company/input_Notary Location'), 
                                NotaryLocationArray[(legal - 1)])
                        }
                        
                        if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/LegalDocument - Company - GuarantorCompany').getValue(
                            GlobalVariable.NumofGuarantor, 18).length() > 0) {
                            'input Notes'
                            WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument - Company/textarea_Notes'), 
                                NotesArray[(legal - 1)])
                        }
                        
                        if (WebUI.verifyElementPresent(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument - Company/button_Cancel'), 
                            5, FailureHandling.OPTIONAL)) {
                            'click button cancel'
                            WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument - Company/button_Cancel'))
                        }
                        
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
            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument - Company/button_Add'))

			if (legal == 1) {
				if (GlobalVariable.RoleCompany == 'Testing') {
					'Koneksi database'
					String servername = findTestData('Login/Login').getValue(1, 7)

					String instancename = findTestData('Login/Login').getValue(2, 7)

					String username = findTestData('Login/Login').getValue(3, 7)

					String password = findTestData('Login/Login').getValue(4, 7)

					String databaseFOU = findTestData('Login/Login').getValue(5, 7)

					String driverclassname = findTestData('Login/Login').getValue(6, 7)

					String urlFOU = (((servername + ';instanceName=') + instancename) + ';databaseName=') + databaseFOU

					Sql sqlConnectionFOU = CustomKeywords.'dbconnection.connectDB.connect'(urlFOU, username, password, driverclassname)

					ArrayList<WebElement> LegalDocType

					'get data array dari db'
					LegalDocType = CustomKeywords.'dbconnection.checkNAP4db.checkLegaldocument'(sqlConnectionFOU)

					'verify array dari db == option list confins'
					WebUI.verifyOptionsPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument - Company/select_NIP  SIUP  TDP'),
						LegalDocType)

					'get total label from ddl'
					int totalLegaldoctypeddl = WebUI.getNumberOfTotalOption(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument - Company/select_NIP  SIUP  TDP'))

					'verify total ddl confins = total ddl db'
					WebUI.verifyEqual(totalLegaldoctypeddl, LegalDocType.size())
				}
			}
			
            'select legal doc type'
            WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument - Company/select_NIP  SIUP  TDP'), 
                LegalDocTypeArray[(legal - 1)], false)

            'input doc no'
            WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument - Company/input_Document No'), 
                DocumentNoArray[(legal - 1)])

            'input date issued'
            WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument - Company/input_Issued Date'), 
                DateIssuedArray[(legal - 1)])

            if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/LegalDocument - Company - GuarantorCompany').getValue(
                GlobalVariable.NumofGuarantor, 15).length() > 0) {
                'input expired date'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument - Company/input_Expired Date'), 
                    ExpiredDateArray[(legal - 1)])
            }
            
            if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/LegalDocument - Company - GuarantorCompany').getValue(
                GlobalVariable.NumofGuarantor, 16).length() > 0) {
                'input notary name'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument - Company/input_Notary Name'), 
                    NotaryNameArray[(legal - 1)])
            }
            
            if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/LegalDocument - Company - GuarantorCompany').getValue(
                GlobalVariable.NumofGuarantor, 17).length() > 0) {
                'input notary location'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument - Company/input_Notary Location'), 
                    NotaryLocationArray[(legal - 1)])
            }
            
            if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/LegalDocument - Company - GuarantorCompany').getValue(
                GlobalVariable.NumofGuarantor, 18).length() > 0) {
                'input Notes'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument - Company/textarea_Notes'), 
                    NotesArray[(legal - 1)])
            }
            
            'click button save'
            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument - Company/button_Save'))

            if (WebUI.verifyElementPresent(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument - Company/button_Cancel'), 
                5, FailureHandling.OPTIONAL)) {
                'click button cancel'
                WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument - Company/button_Cancel'))
            }
        }
    }
} else if (copyapp.equalsIgnoreCase('No')) {
    if (LegalDocTypeArray.size() > 0) {
        for (legal = 1; legal <= LegalDocTypeArray.size(); legal++) {
            'click button add'
            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument - Company/button_Add'))

            if (legal == 1) {
                if (GlobalVariable.RoleCompany == 'Testing') {
                    'Koneksi database'
                    String servername = findTestData('Login/Login').getValue(1, 7)

                    String instancename = findTestData('Login/Login').getValue(2, 7)

                    String username = findTestData('Login/Login').getValue(3, 7)

                    String password = findTestData('Login/Login').getValue(4, 7)

                    String databaseFOU = findTestData('Login/Login').getValue(5, 7)

                    String driverclassname = findTestData('Login/Login').getValue(6, 7)

                    String urlFOU = (((servername + ';instanceName=') + instancename) + ';databaseName=') + databaseFOU

                    Sql sqlConnectionFOU = CustomKeywords.'dbconnection.connectDB.connect'(urlFOU, username, password, driverclassname)

                    ArrayList<WebElement> LegalDocType

                    'get data array dari db'
                    LegalDocType = CustomKeywords.'dbconnection.checkNAP4db.checkLegaldocument'(sqlConnectionFOU)

                    'verify array dari db == option list confins'
                    WebUI.verifyOptionsPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument - Company/select_NIP  SIUP  TDP'), 
                        LegalDocType)

                    'get total label from ddl'
                    int totalLegaldoctypeddl = WebUI.getNumberOfTotalOption(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument - Company/select_NIP  SIUP  TDP'))

                    'verify total ddl confins = total ddl db'
                    WebUI.verifyEqual(totalLegaldoctypeddl, LegalDocType.size())
                }
            }
            
            'select legal doc type'
            WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument - Company/select_NIP  SIUP  TDP'), 
                LegalDocTypeArray[(legal - 1)], false)

            'input doc no'
            WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument - Company/input_Document No'), 
                DocumentNoArray[(legal - 1)])

            'input date issued'
            WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument - Company/input_Issued Date'), 
                DateIssuedArray[(legal - 1)])

            if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/LegalDocument - Company - GuarantorCompany').getValue(
                GlobalVariable.NumofGuarantor, 15).length() > 0) {
                'input expired date'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument - Company/input_Expired Date'), 
                    ExpiredDateArray[(legal - 1)])
            }
            
            if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/LegalDocument - Company - GuarantorCompany').getValue(
                GlobalVariable.NumofGuarantor, 16).length() > 0) {
                'input notary name'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument - Company/input_Notary Name'), 
                    NotaryNameArray[(legal - 1)])
            }
            
            if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/LegalDocument - Company - GuarantorCompany').getValue(
                GlobalVariable.NumofGuarantor, 17).length() > 0) {
                'input notary location'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument - Company/input_Notary Location'), 
                    NotaryLocationArray[(legal - 1)])
            }
            
            if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/LegalDocument - Company - GuarantorCompany').getValue(
                GlobalVariable.NumofGuarantor, 18).length() > 0) {
                'input Notes'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument - Company/textarea_Notes'), 
                    NotesArray[(legal - 1)])
            }
            
            'click button save'
            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument - Company/button_Save'))

            if (WebUI.verifyElementPresent(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument - Company/button_Cancel'), 
                5, FailureHandling.OPTIONAL)) {
                'click button cancel'
                WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument - Company/button_Cancel'))
            }
        }
    }
}

'click button save and continue'
WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument - Company/button_Save  Continue'))

if (Integer.parseInt(findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/LegalDocument - Company - GuarantorCompany').getValue(
        GlobalVariable.NumofGuarantor, 4)) == 0) {
    'Check alert'
    CustomKeywords.'checkSaveProcess.checkSaveProcess.checkAlert'(GlobalVariable.NumofGuarantor, '6.LegalDocument')
}

if (GlobalVariable.FlagFailed == 0) {
    'Check save Process write to excel'
    CustomKeywords.'checkSaveProcess.checkSaveProcess.checkStatus'(Integer.parseInt(findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/LegalDocument - Company - GuarantorCompany').getValue(
                GlobalVariable.NumofGuarantor, 4)), findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/OtherAttribute - Company/button_Debtor Group_btn btn-raised btn-primary'), 
        GlobalVariable.NumofGuarantor, '6.LegalDocument')

    if (Integer.parseInt(findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/LegalDocument - Company - GuarantorCompany').getValue(
            GlobalVariable.NumofGuarantor, 4)) == 0) {
        'Check error validasi'
        CustomKeywords.'checkSaveProcess.checkSaveProcess.checkValidasi'(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/errorvalidasi'), 
            GlobalVariable.NumofGuarantor, '6.LegalDocument')
    }
}

if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument - Company/th_Expired Date'), 
    10, FailureHandling.OPTIONAL)) {
    'click button back'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerDataCompletion/button_Back'))
}

if(GlobalVariable.RoleCompany == 'Testing' && GlobalVariable.CheckVerifStoreDBCompany=="Yes"){
	GlobalVariable.NumofVerifStore = GlobalVariable.NumofGuarantor
		
	'Call test case verify legal doc store data'
	WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/NAP4VerifyStoreData/Company/TabLegalDocVerifStoreDataDB'),
		[:], FailureHandling.CONTINUE_ON_FAILURE)
}