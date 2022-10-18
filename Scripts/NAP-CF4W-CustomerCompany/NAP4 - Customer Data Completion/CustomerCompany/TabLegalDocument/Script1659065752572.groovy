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

int flagWarning = 0

String userDir = System.getProperty('user.dir')

String filePath = userDir + GlobalVariable.DataFileCustomerCompany

GlobalVariable.DataFilePath = filePath

ArrayList<WebElement> legaltypefaileddelete = new ArrayList<WebElement>()

ArrayList<WebElement> faileddata = new ArrayList<WebElement>()

GlobalVariable.findDataFile = findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument - Company - Customer')

def LegalDocTypeArray = findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument - Company - Customer').getValue(
    GlobalVariable.NumofColm, 12).split(';', -1)

def DocumentNoArray = findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument - Company - Customer').getValue(
    GlobalVariable.NumofColm, 13).split(';', -1)

def DateIssuedArray = findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument - Company - Customer').getValue(
    GlobalVariable.NumofColm, 14).split(';', -1)

def ExpiredDateArray = findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument - Company - Customer').getValue(
    GlobalVariable.NumofColm, 15).split(';', -1)

def NotaryNameArray = findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument - Company - Customer').getValue(
    GlobalVariable.NumofColm, 16).split(';', -1)

def NotaryLocationArray = findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument - Company - Customer').getValue(
    GlobalVariable.NumofColm, 17).split(';', -1)

def NotesArray = findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument - Company - Customer').getValue(
    GlobalVariable.NumofColm, 18).split(';', -1)

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

                            Sql sqlConnectionFOU = CustomKeywords.'dbconnection.connectDB.connect'(urlFOU, username, password, 
                                driverclassname)

                            ArrayList<WebElement> LegalDocType

                            'get data array dari db'
                            LegalDocType = CustomKeywords.'dbconnection.checkNAP4db.checkLegaldocument'(sqlConnectionFOU)

                            'verify array dari db == option list confins'
                            if (WebUI.verifyOptionsPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument - Company/select_NIP  SIUP  TDP'), 
                                LegalDocType) == false) {
                                'Write To Excel GlobalVariable.StatusFailed'
                                CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, 
                                    '6.LegalDocument', 0, legal - 1, GlobalVariable.StatusFailed)

                                'Write To Excel GlobalVariable.ReasonFailedDDL'
                                CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, 
                                    '6.LegalDocument', 1, legal - 1, GlobalVariable.ReasonFailedDDL)

                                (GlobalVariable.FlagFailed)++
                            }
                            
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

                    if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument - Company - Customer').getValue(
                        GlobalVariable.NumofColm, 15).length() > 0) {
                        'input expired date'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument - Company/input_Expired Date'), 
                            ExpiredDateArray[(legal - 1)])
                    }
                    
                    if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument - Company - Customer').getValue(
                        GlobalVariable.NumofColm, 16).length() > 0) {
                        'input notary name'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument - Company/input_Notary Name'), 
                            NotaryNameArray[(legal - 1)])
                    }
                    
                    if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument - Company - Customer').getValue(
                        GlobalVariable.NumofColm, 17).length() > 0) {
                        'input notary location'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument - Company/input_Notary Location'), 
                            NotaryLocationArray[(legal - 1)])
                    }
                    
                    if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument - Company - Customer').getValue(
                        GlobalVariable.NumofColm, 18).length() > 0) {
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

                        faileddata.add(LegalDocTypeArray[(legal - 1)])

                        flagWarning++
                    }
                    
                    break
                } else {
                    if (legal == LegalDocTypeArray.size()) {
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
            } else {
                break
            }
        }
    }
    
    if (legaltypefaileddelete.size() > 0) {
        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '6.LegalDocument', 
            0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusWarning)

        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '6.LegalDocument', 
            1, GlobalVariable.NumofColm - 1, GlobalVariable.ReasonFailedDelete + legaltypefaileddelete)

        (GlobalVariable.FlagWarning)++
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

                                String urlFOU = (((servername + ';instanceName=') + instancename) + ';databaseName=') + 
                                databaseFOU

                                Sql sqlConnectionFOU = CustomKeywords.'dbconnection.connectDB.connect'(urlFOU, username, 
                                    password, driverclassname)

                                ArrayList<WebElement> LegalDocType

                                'get data array dari db'
                                LegalDocType = CustomKeywords.'dbconnection.checkNAP4db.checkLegaldocument'(sqlConnectionFOU)

                                'verify array dari db == option list confins'
                                if (WebUI.verifyOptionsPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument - Company/select_NIP  SIUP  TDP'), 
                                    LegalDocType) == false) {
                                    'Write To Excel GlobalVariable.StatusFailed'
                                    CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, 
                                        '6.LegalDocument', 0, legal - 1, GlobalVariable.StatusFailed)

                                    'Write To Excel GlobalVariable.ReasonFailedDDL'
                                    CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, 
                                        '6.LegalDocument', 1, legal - 1, GlobalVariable.ReasonFailedDDL)

                                    (GlobalVariable.FlagFailed)++
                                }
                                
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

                        if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument - Company - Customer').getValue(
                            GlobalVariable.NumofColm, 15).length() > 0) {
                            'input expired date'
                            WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument - Company/input_Expired Date'), 
                                ExpiredDateArray[(legal - 1)])
                        }
                        
                        if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument - Company - Customer').getValue(
                            GlobalVariable.NumofColm, 16).length() > 0) {
                            'input notary name'
                            WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument - Company/input_Notary Name'), 
                                NotaryNameArray[(legal - 1)])
                        }
                        
                        if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument - Company - Customer').getValue(
                            GlobalVariable.NumofColm, 17).length() > 0) {
                            'input notary location'
                            WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument - Company/input_Notary Location'), 
                                NotaryLocationArray[(legal - 1)])
                        }
                        
                        if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument - Company - Customer').getValue(
                            GlobalVariable.NumofColm, 18).length() > 0) {
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

                            faileddata.add(LegalDocTypeArray[(legal - 1)])

                            flagWarning++
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
                    if (WebUI.verifyOptionsPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument - Company/select_NIP  SIUP  TDP'), 
                        LegalDocType) == false) {
                        'Write To Excel GlobalVariable.StatusFailed'
                        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '6.LegalDocument', 
                            0, legal - 1, GlobalVariable.StatusFailed)

                        'Write To Excel GlobalVariable.ReasonFailedDDL'
                        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '6.LegalDocument', 
                            1, legal - 1, GlobalVariable.ReasonFailedDDL)

                        (GlobalVariable.FlagFailed)++
                    }
                    
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

            if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument - Company - Customer').getValue(
                GlobalVariable.NumofColm, 15).length() > 0) {
                'input expired date'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument - Company/input_Expired Date'), 
                    ExpiredDateArray[(legal - 1)])
            }
            
            if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument - Company - Customer').getValue(
                GlobalVariable.NumofColm, 16).length() > 0) {
                'input notary name'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument - Company/input_Notary Name'), 
                    NotaryNameArray[(legal - 1)])
            }
            
            if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument - Company - Customer').getValue(
                GlobalVariable.NumofColm, 17).length() > 0) {
                'input notary location'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument - Company/input_Notary Location'), 
                    NotaryLocationArray[(legal - 1)])
            }
            
            if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument - Company - Customer').getValue(
                GlobalVariable.NumofColm, 18).length() > 0) {
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

                faileddata.add(LegalDocTypeArray[(legal - 1)])

                flagWarning++
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
                    if (WebUI.verifyOptionsPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument - Company/select_NIP  SIUP  TDP'), 
                        LegalDocType) == false) {
                        'Write To Excel GlobalVariable.StatusFailed'
                        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '6.LegalDocument', 
                            0, legal - 1, GlobalVariable.StatusFailed)

                        'Write To Excel GlobalVariable.ReasonFailedDDL'
                        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '6.LegalDocument', 
                            1, legal - 1, GlobalVariable.ReasonFailedDDL)

                        (GlobalVariable.FlagFailed)++
                    }
                    
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

            if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument - Company - Customer').getValue(
                GlobalVariable.NumofColm, 15).length() > 0) {
                'input expired date'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument - Company/input_Expired Date'), 
                    ExpiredDateArray[(legal - 1)])
            }
            
            if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument - Company - Customer').getValue(
                GlobalVariable.NumofColm, 16).length() > 0) {
                'input notary name'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument - Company/input_Notary Name'), 
                    NotaryNameArray[(legal - 1)])
            }
            
            if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument - Company - Customer').getValue(
                GlobalVariable.NumofColm, 17).length() > 0) {
                'input notary location'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument - Company/input_Notary Location'), 
                    NotaryLocationArray[(legal - 1)])
            }
            
            if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument - Company - Customer').getValue(
                GlobalVariable.NumofColm, 18).length() > 0) {
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

                faileddata.add(LegalDocTypeArray[(legal - 1)])

                flagWarning++
            }
        }
    }
}

'click button save and continue'
WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument - Company/button_Save  Continue'))

if (Integer.parseInt(findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument - Company - Customer').getValue(
        GlobalVariable.NumofColm, 4)) == 0) {
    'Check alert'
    CustomKeywords.'checkSaveProcess.checkSaveProcess.checkAlert'(GlobalVariable.NumofColm, '6.LegalDocument')
}

if (GlobalVariable.FlagFailed == 0) {
    'Check save Process write to excel'
    CustomKeywords.'checkSaveProcess.checkSaveProcess.checkStatus'(Integer.parseInt(findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument - Company - Customer').getValue(
                GlobalVariable.NumofColm, 4)), findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/OtherAttribute - Company/button_Debtor Group_btn btn-raised btn-primary'), 
        GlobalVariable.NumofColm, '6.LegalDocument')

    if (Integer.parseInt(findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument - Company - Customer').getValue(
            GlobalVariable.NumofColm, 4)) == 0) {
        'Check error validasi'
        CustomKeywords.'checkSaveProcess.checkSaveProcess.checkValidasi'(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/errorvalidasi'), 
            GlobalVariable.NumofColm, '6.LegalDocument')
    }
}

if (flagWarning > 0) {
    CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '6.LegalDocument', 0, GlobalVariable.NumofColm - 
        1, GlobalVariable.StatusWarning)

    CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '6.LegalDocument', 1, GlobalVariable.NumofColm - 
        1, GlobalVariable.ReasonFailedInputData + faileddata)
}

if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/LegalDocument - Company/th_Expired Date'), 
    10, FailureHandling.OPTIONAL)) {
    'click button back'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerDataCompletion/button_Back'))
}

if ((GlobalVariable.RoleCompany == 'Testing') && (GlobalVariable.CheckVerifStoreDBCompany == 'Yes')) {
    GlobalVariable.NumofVerifStore = GlobalVariable.NumofColm

    'Call test case verify legal doc store data'
    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/NAP4VerifyStoreData/Company/TabLegalDocVerifStoreDataDB'), 
        [:], FailureHandling.CONTINUE_ON_FAILURE)
}

