import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import org.openqa.selenium.By as By
import org.openqa.selenium.WebElement as WebElement
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

int flagWarning = 0

String userDir = System.getProperty('user.dir')

String filePath = userDir + GlobalVariable.PathCompany

GlobalVariable.DataFilePath = filePath

datafile = findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabManagementShareholder')

ArrayList<String> variableData

copyapp = datafile.getValue(GlobalVariable.NumofMS, 10)

if (GlobalVariable.RoleCompany == 'Testing') {
    'verify application step'
    WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/applicationcurrentstep')), 
        'MANAGEMENT SHAREHOLDER', false, FailureHandling.OPTIONAL)
}

'Loop Multiple ManagementSharholder Data'
for (GlobalVariable.NumofMS = 2; GlobalVariable.NumofMS <= (Integer.parseInt(GlobalVariable.CountAManagementShareholder) + 
1); (GlobalVariable.NumofMS)++) {

GlobalVariable.FlagFailed = 0

    if (datafile.getValue(GlobalVariable.NumofMS, 12) == findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
        GlobalVariable.NumofColm, 13)) {
        if (WebUI.verifyElementNotPresent(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/td_MSName'), 
            5, FailureHandling.OPTIONAL)) {
            'count table guarantor pada confins'
            variableData = DriverFactory.getWebDriver().findElements(By.cssSelector('#mgmnt-shrholder-tab > app-mngmnt-shrhldr-main-data-paging > div > div:nth-child(2) > lib-ucgridview > div > table > thead'))
        } else {
            'count table guarantor pada confins'
            variableData = DriverFactory.getWebDriver().findElements(By.cssSelector('#mgmnt-shrholder-tab > app-mngmnt-shrhldr-main-data-paging > div > div:nth-child(2) > lib-ucgridview > div > table > tbody tr'))
        }
        
        for (i = 1; i <= variableData.size(); i++) {
            'modify object guarantor name'
            modifyNewMSName = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/FromTypeName'), 
                'xpath', 'equals', ('//*[@id="mgmnt-shrholder-tab"]/app-mngmnt-shrhldr-main-data-paging/div/div[2]/lib-ucgridview/div/table/tbody/tr[' + 
                i) + ']/td[2]', true)

			println(WebUI.getText(modifyNewMSName))
			println(datafile.getValue(GlobalVariable.NumofMS, 62))
			println(datafile.getValue(GlobalVariable.NumofMS, 19))
			println(datafile.getValue(GlobalVariable.NumofMS, 17))
			println(datafile.getValue(GlobalVariable.NumofMS, 52))
			println(datafile.getValue(GlobalVariable.NumofMS, 50))
			
            'verify if copy app edit'
            if (copyapp.equalsIgnoreCase('Edit')) {
                if (WebUI.verifyElementNotPresent(modifyNewMSName, 5, FailureHandling.OPTIONAL)) {
                    copyapp == 'No'
                } else if (WebUI.verifyElementPresent(modifyNewMSName, 5, FailureHandling.OPTIONAL)) {
                    if (WebUI.getText(modifyNewMSName).equalsIgnoreCase(datafile.getValue(GlobalVariable.NumofMS, 19)) || WebUI.getText(modifyNewMSName).equalsIgnoreCase(datafile.getValue(GlobalVariable.NumofMS, 17)) || 
                    WebUI.getText(modifyNewMSName).equalsIgnoreCase(datafile.getValue(GlobalVariable.NumofMS, 52)) || WebUI.getText(modifyNewMSName).equalsIgnoreCase(datafile.getValue(GlobalVariable.NumofMS, 50)) || 
                    WebUI.getText(modifyNewMSName).equalsIgnoreCase(datafile.getValue(GlobalVariable.NumofMS, 62))) {
                        break
                    }
                }
            }
            
            if ((i == variableData.size()) || (copyapp == 'No')) {
                if (datafile.getValue(GlobalVariable.NumofMS, 13) == 'Input Data') {
                    'click button add'
                    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/button_Add'))

                    if (datafile.getValue(GlobalVariable.NumofMS, 14).equalsIgnoreCase('Personal')) {
                        'click radio personal'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/span_ Personal'))

                        'input shareholder name'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Shareholder Legal Name_form-control ng-untouched ng-pristine ng-invalid'), 
                            datafile.getValue(GlobalVariable.NumofMS, 19))

                        'input birth place'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Birth Place_form-control ng-untouched ng-pristine ng-invalid'), 
                            datafile.getValue(GlobalVariable.NumofMS, 20))

                        'select idtype'
                        WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/select_Select One AKTA  E-KTP  KARTU TANDA MAHASISWA  KITAS  NPWP  SIM'), 
                            datafile.getValue(GlobalVariable.NumofMS, 21), false)

                        'input Id Expired Date'
                        if (((datafile.getValue(GlobalVariable.NumofMS, 21) != 'E-KTP') && (datafile.getValue(GlobalVariable.NumofMS, 
                            21) != 'NPWP')) && (datafile.getValue(GlobalVariable.NumofMS, 21) != 'AKTA')) {
                            WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Id Expired Date_form-control ng-untouched ng-pristine ng-valid'), 
                                datafile.getValue(GlobalVariable.NumofMS, 22))
                        }
                        
                        'select marital status'
                        WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/select_Select One Married  Single  Widow'), 
                            datafile.getValue(GlobalVariable.NumofMS, 23), false)

                        'input mobile phone'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Mobile Phone_form-control ng-untouched ng-pristine ng-invalid'), 
                            datafile.getValue(GlobalVariable.NumofMS, 24))

                        'select customer model'
                        WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/select_Select One Employee  Non Professional  Professional  Small Medium Enterprise'), 
                            datafile.getValue(GlobalVariable.NumofMS, 25), false)

                        if (datafile.getValue(GlobalVariable.NumofMS, 27).length() > 1) {
                            'click lookup button proffession name'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_Profession Name_btn btn-raised btn-primary'))

                            'input profession code'
                            WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Profession Code_professionCodeId'), 
                                datafile.getValue(GlobalVariable.NumofMS, 26))

                            'click button search'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_Search'))

                            'verify input error'
                            if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/a_Select'), 
                                10, FailureHandling.OPTIONAL)) {
                                'click select'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/a_Select'))
                            } else {
                                'click X'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_X'))

                                flagWarning++

                                continue
                            }
                        }
                        
                        'click button position SLIK'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_Position SLIK_btn btn-raised btn-primary'))

                        'input jabatan code'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Jabatan Code_JabatanCodeId'), 
                            datafile.getValue(GlobalVariable.NumofMS, 28))

                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_Search'))

                        'verify input error'
                        if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/a_Select'), 
                            10, FailureHandling.OPTIONAL)) {
                            'click select'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/a_Select'))
                        } else {
                            'click X'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_X'))

                            flagWarning++

                            continue
                        }
                        
                        'input establishment date'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Establishment Date_form-control ng-untouched ng-pristine ng-invalid'), 
                            datafile.getValue(GlobalVariable.NumofMS, 30))

                        'select gender'
                        WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/select_Select One Female  Male'), 
                            datafile.getValue(GlobalVariable.NumofMS, 31), false)

                        'input birth date'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Birth Date_form-control ng-untouched ng-pristine ng-invalid'), 
                            datafile.getValue(GlobalVariable.NumofMS, 32))

                        'input id no'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Id No_form-control ng-untouched ng-pristine ng-invalid'), 
                            datafile.getValue(GlobalVariable.NumofMS, 33))

                        'input tax id'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Tax Id No_form-control ng-untouched ng-pristine ng-valid'), 
                            datafile.getValue(GlobalVariable.NumofMS, 34))

                        'input mother maiden name'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Mother Maiden Name_form-control ng-untouched ng-pristine ng-invalid'), 
                            datafile.getValue(GlobalVariable.NumofMS, 35))

                        'input email'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Email_form-control ng-untouched ng-pristine ng-valid'), 
                            datafile.getValue(GlobalVariable.NumofMS, 36))

                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_Job Position_btn btn-raised btn-primary'))

                        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Job Position Code_JobCodeId'), 
                            datafile.getValue(GlobalVariable.NumofMS, 37))

                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_Search'))

                        'verify input error'
                        if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/a_Select'), 
                            10, FailureHandling.OPTIONAL)) {
                            'click select'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/a_Select'))
                        } else {
                            'click X'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_X'))

                            flagWarning++

                            continue
                        }
                        
                        'input share'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Share()_form-control ng-untouched ng-pristine ng-valid'), 
                            datafile.getValue(GlobalVariable.NumofMS, 39))

                        if (WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Owner_ng-untouched ng-pristine ng-valid'), 
                            2, FailureHandling.OPTIONAL)) {
                            if (datafile.getValue(GlobalVariable.NumofMS, 40).equalsIgnoreCase('Yes')) {
                                'click check box is Owner'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Owner_ng-untouched ng-pristine ng-valid'))
                            }
                        } else if (WebUI.verifyElementChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Owner_ng-untouched ng-pristine ng-valid'), 
                            2, FailureHandling.OPTIONAL)) {
                            if (datafile.getValue(GlobalVariable.NumofMS, 40).equalsIgnoreCase('No')) {
                                'click check box is Owner'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Owner_ng-untouched ng-pristine ng-valid'))
                            }
                        }
                        
                        if (WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Active_ng-untouched ng-pristine ng-valid'), 
                            2, FailureHandling.OPTIONAL)) {
                            if (datafile.getValue(GlobalVariable.NumofMS, 41).equalsIgnoreCase('Yes')) {
                                'click check box is Active'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Active_ng-untouched ng-pristine ng-valid'))
                            }
                        } else if (WebUI.verifyElementChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Active_ng-untouched ng-pristine ng-valid'), 
                            2, FailureHandling.OPTIONAL)) {
                            if (datafile.getValue(GlobalVariable.NumofMS, 41).equalsIgnoreCase('No')) {
                                'click check box is Active'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Active_ng-untouched ng-pristine ng-valid'))
                            }
                        }
                        
                        if (WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Signer_ng-untouched ng-pristine ng-valid'), 
                            2, FailureHandling.OPTIONAL)) {
                            if (datafile.getValue(GlobalVariable.NumofMS, 42).equalsIgnoreCase('Yes')) {
                                'click check box is Signer'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Signer_ng-untouched ng-pristine ng-valid'))
                            }
                        } else if (WebUI.verifyElementChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Signer_ng-untouched ng-pristine ng-valid'), 
                            2, FailureHandling.OPTIONAL)) {
                            if (datafile.getValue(GlobalVariable.NumofMS, 42).equalsIgnoreCase('No')) {
                                'click check box is Signer'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Signer_ng-untouched ng-pristine ng-valid'))
                            }
                        }
                        
                        'click button lookup department AML'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_DEPARTMENT AML_btn btn-raised btn-primary'))

                        'input department AML Code'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Code_MasterCodeId'), 
                            datafile.getValue(GlobalVariable.NumofMS, 44))

                        'click button search'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_Search'))

                        'verify input error'
                        if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/a_Select'), 
                            10, FailureHandling.OPTIONAL)) {
                            'click select'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/a_Select'))
                        } else {
                            'click X'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_X'))

                            flagWarning++

                            continue
                        }
                        
                        if (datafile.getValue(GlobalVariable.NumofMS, 47).length() > 1) {
                            'click button authority AML lookup'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_Authority AML_btn btn-raised btn-primary'))

                            'input authority AML code'
                            WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Code_MasterCodeId'), 
                                datafile.getValue(GlobalVariable.NumofMS, 46))

                            'click button search'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_Search'))

                            'verify input error'
                            if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/a_Select'), 
                                10, FailureHandling.OPTIONAL)) {
                                'click select'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/a_Select'))
                            } else {
                                'click X'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_X'))

                                flagWarning++

                                continue
                            }
                        }
                    } else if (datafile.getValue(GlobalVariable.NumofMS, 14).equalsIgnoreCase('Company')) {
                        'click radio company'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/span_ Company'))

                        'input shareholder name'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/input_Shareholder Legal Name_form-control ng-untouched ng-pristine ng-invalid'), 
                            datafile.getValue(GlobalVariable.NumofMS, 52))

                        'input tax id'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/input_Tax Id No_form-control ng-untouched ng-pristine ng-invalid'), 
                            datafile.getValue(GlobalVariable.NumofMS, 53))

                        'click button position SLIK'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/button_Position SLIK_btn btn-raised btn-primary'))

                        'input jabatan code'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/input_Jabatan Code_JabatanCodeId'), 
                            datafile.getValue(GlobalVariable.NumofMS, 54))

                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/button_Search'))

                        'verify input error'
                        if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/a_Select'), 
                            10, FailureHandling.OPTIONAL)) {
                            'click select'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/a_Select'))
                        } else {
                            'click X'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/button_X'))

                            flagWarning++
                        }
                        
                        'select company type'
                        WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/select_Select One CV  Koperasi  PT'), 
                            datafile.getValue(GlobalVariable.NumofMS, 56), false)

                        'select customer model'
                        WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/select_Select One Corporate  Non Corporate'), 
                            datafile.getValue(GlobalVariable.NumofMS, 57), false)

                        'input share'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/input_Share()_form-control ng-untouched ng-pristine ng-valid'), 
                            datafile.getValue(GlobalVariable.NumofMS, 58))

                        if (WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Owner_ng-untouched ng-pristine ng-valid'), 
                            2, FailureHandling.OPTIONAL)) {
                            if (datafile.getValue(GlobalVariable.NumofMS, 60).equalsIgnoreCase('Yes')) {
                                'click check box is Owner'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/input_Is Owner_ng-untouched ng-pristine ng-valid'))
                            }
                        } else if (WebUI.verifyElementChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Owner_ng-untouched ng-pristine ng-valid'), 
                            2, FailureHandling.OPTIONAL)) {
                            if (datafile.getValue(GlobalVariable.NumofMS, 60).equalsIgnoreCase('No')) {
                                'click check box is Owner'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/input_Is Owner_ng-untouched ng-pristine ng-valid'))
                            }
                        }
                        
                        if (WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Active_ng-untouched ng-pristine ng-valid'), 
                            2, FailureHandling.OPTIONAL)) {
                            if (datafile.getValue(GlobalVariable.NumofMS, 59).equalsIgnoreCase('Yes')) {
                                'click check box is Active'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/input_Is Active_ng-untouched ng-pristine ng-valid'))
                            }
                        } else if (WebUI.verifyElementChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Active_ng-untouched ng-pristine ng-valid'), 
                            2, FailureHandling.OPTIONAL)) {
                            if (datafile.getValue(GlobalVariable.NumofMS, 59).equalsIgnoreCase('No')) {
                                'click check box is Active'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/input_Is Active_ng-untouched ng-pristine ng-valid'))
                            }
                        }
                    } else if (datafile.getValue(GlobalVariable.NumofMS, 14).equalsIgnoreCase('Public')) {
                        'click radio public'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/span_ Public'))

                        'select Public Type'
                        WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/select_Select One INSTANSI PUBLIK DAERAH  INSTANSI PUBLIK PUSAT  MASYARAKAT  PEMERINTAH REPUBLIK INDONESIA'), 
                            datafile.getValue(GlobalVariable.NumofMS, 62).toUpperCase(), false)

                        if (datafile.getValue(GlobalVariable.NumofMS, 62).equalsIgnoreCase('Instansi Publik Daerah') || 
                        datafile.getValue(GlobalVariable.NumofMS, 62).equalsIgnoreCase('Instansi Publik Pusat')) {
                            'input shareholder name'
                            WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/input_Name_form-control adInsInput ng-untouched ng-pristine ng-invalid'), 
                                datafile.getValue(GlobalVariable.NumofMS, 63))

                            'input identity no'
                            WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/input_Identity No_form-control adInsInput ng-untouched ng-pristine ng-invalid'), 
                                datafile.getValue(GlobalVariable.NumofMS, 64))
                        }
                        
                        'click lookup position SLIK'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/button_Position SLIK_btn btn-raised btn-primary'))

                        'input jabatan code'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/input_Jabatan Code_JabatanCodeId'), 
                            datafile.getValue(GlobalVariable.NumofMS, 65))

                        'click button search'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/button_Search'))

                        'verify input error'
                        if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/a_Select'), 
                            10, FailureHandling.OPTIONAL)) {
                            'click select'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/a_Select'))
                        } else {
                            'click X'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/button_X'))

                            flagWarning++
                        }
                        
                        'input share'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Share()_form-control ng-untouched ng-pristine ng-valid'), 
                            datafile.getValue(GlobalVariable.NumofMS, 67))

                        if (WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/input_Is Active_border-primary ng-untouched ng-pristine ng-valid'), 
                            2, FailureHandling.OPTIONAL)) {
                            if (datafile.getValue(GlobalVariable.NumofMS, 68).equalsIgnoreCase('Yes')) {
                                'click check box is Active'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/input_Is Active_border-primary ng-untouched ng-pristine ng-valid'))
                            }
                        } else if (WebUI.verifyElementChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/input_Is Active_border-primary ng-untouched ng-pristine ng-valid'), 
                            2, FailureHandling.OPTIONAL)) {
                            if (datafile.getValue(GlobalVariable.NumofMS, 68).equalsIgnoreCase('No')) {
                                'click check box is Active'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/input_Is Active_border-primary ng-untouched ng-pristine ng-valid'))
                            }
                        }
                    }
                    
                    if (datafile.getValue(GlobalVariable.NumofMS, 14).equalsIgnoreCase('Personal') || datafile.getValue(
                        GlobalVariable.NumofMS, 14).equalsIgnoreCase('Company')) {
                        if (datafile.getValue(GlobalVariable.NumofMS, 70).length() > 1) {
                            'click button copy'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/button_Copy'))
                        } else {
                            'input address'
                            WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/textarea_Address_form-control ng-untouched ng-pristine ng-invalid'), 
                                datafile.getValue(GlobalVariable.NumofMS, 71))

                            'input RT'
                            WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/input_RT'), 
                                datafile.getValue(GlobalVariable.NumofMS, 72))

                            'input RW'
                            WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/input_RW'), 
                                datafile.getValue(GlobalVariable.NumofMS, 73))

                            'click button lookup zipcode'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/button_Zipcode_btn btn-raised btn-primary'))

                            'input zipcode'
                            WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/input_Zip Code_ZipCode'), 
                                datafile.getValue(GlobalVariable.NumofMS, 74))

                            'input kecamatan'
                            WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/input_Kecamatan_kecamatan'), 
                                datafile.getValue(GlobalVariable.NumofMS, 75))

                            'input kelurahan'
                            WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/input_Kelurahan_Kelurahan'), 
                                datafile.getValue(GlobalVariable.NumofMS, 76))

                            'input kota'
                            WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/input_Kota_kota'), 
                                datafile.getValue(GlobalVariable.NumofMS, 77))

                            'klik button search'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/button_Search'))

                            'verify input error'
                            if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/a_Select'), 
                                10, FailureHandling.OPTIONAL)) {
                                'click select'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/a_Select'))
                            } else {
                                'click X'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/button_X'))

                                flagWarning++
                            }
                            
                            'select ownership'
                            WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/select_Select One Dinas  Family  KPR  Rented  Self - Owned'), 
                                datafile.getValue(GlobalVariable.NumofMS, 78), false)
                        }
                    } else if (datafile.getValue(GlobalVariable.NumofMS, 14).equalsIgnoreCase('Public')) {
                        if (datafile.getValue(GlobalVariable.NumofMS, 70).length() > 1) {
                            'click button copy'
                            WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/button_Copy'))
                        } else {
                            'input address'
                            WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/textarea_Address_form-control ng-untouched ng-pristine ng-invalid'), 
                                datafile.getValue(GlobalVariable.NumofMS, 71))

                            'input RT'
                            WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/input_RT'), 
                                datafile.getValue(GlobalVariable.NumofMS, 72))

                            'input RW'
                            WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/input_RW'), 
                                datafile.getValue(GlobalVariable.NumofMS, 73))

                            'click button lookup zipcode'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/button_Zipcode_btn btn-raised btn-primary'))

                            'input zipcode'
                            WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/input_Zip Code_ZipCode'), 
                                datafile.getValue(GlobalVariable.NumofMS, 74))

                            'input kecamatan'
                            WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/input_Kecamatan_kecamatan'), 
                                datafile.getValue(GlobalVariable.NumofMS, 75))

                            'input kelurahan'
                            WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/input_Kelurahan_Kelurahan'), 
                                datafile.getValue(GlobalVariable.NumofMS, 76))

                            'input kota'
                            WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/input_Kota_kota'), 
                                datafile.getValue(GlobalVariable.NumofMS, 77))

                            'klik button search'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/button_Search'))

                            'verify input error'
                            if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/a_Select'), 
                                10, FailureHandling.OPTIONAL)) {
                                'click select'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/a_Select'))
                            } else {
                                'click X'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/button_X'))

                                flagWarning++
                            }
                        }
                    }
                } else if (datafile.getValue(GlobalVariable.NumofMS, 13) == 'LookUp') {
                    'click button add'
                    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/button_Add'))

                    if (datafile.getValue(GlobalVariable.NumofMS, 14).equalsIgnoreCase('Personal')) {
                        'click radio personal'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/span_ Personal'))

                        'click button lookup customer'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_ShareHolderLegalName'))

                        'input customer no'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Customer No_custNoId'), 
                            datafile.getValue(GlobalVariable.NumofMS, 16))

                        'input customer name'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Customer Name_custNameId'), 
                            datafile.getValue(GlobalVariable.NumofMS, 17))

                        'input ID No'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Id No_IdNoId'), 
                            datafile.getValue(GlobalVariable.NumofMS, 18))

                        'click search'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_Search'))

                        'verify input error'
                        if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/a_Select'), 
                            10, FailureHandling.OPTIONAL)) {
                            'click select'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/a_Select'))
                        } else {
                            'click X'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/Button_X'))

                            'click button cancel'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_Cancel'))

                            'Write to Excel FAILED'
                            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, 
                                '2.TabManagementShareholderData', 0, GlobalVariable.NumofMS - 1, GlobalVariable.StatusFailed)

                            'Write To Excel GlobalVariable.StatusReasonLookup'
                            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, 
                                '2.TabManagementShareholderData', 1, GlobalVariable.NumofMS - 1, GlobalVariable.StatusReasonLookup)

                            'Flagfailed +1 karena lookup gagal'
                            (GlobalVariable.FlagFailed)++

                            continue
                        }
                        
						'check if role testing maka get data MS untuk data verif'
                        if (GlobalVariable.RoleCompany == 'Testing') {
                            'call function MS Personal get data'
                            getDataMS(datafile)

                            'add id type to array'
                            GlobalVariable.confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/select_Select One AKTA  E-KTP  KARTU TANDA MAHASISWA  KITAS  NPWP  SIM'), 
                                    'value'))

                            'add marital status to array'
                            GlobalVariable.confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/select_Select One Married  Single  Widow'), 
                                    'value'))

                            'add customer model to array'
                            GlobalVariable.confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/select_Select One Employee  Non Professional  Professional  Small Medium Enterprise'), 
                                    'value'))

                            'add gender to array'
                            GlobalVariable.confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/select_Select One Female  Male'), 
                                    'value'))

                            'add ownership to array'
                            GlobalVariable.confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/select_Select One Dinas  Family  KPR  Rented  Self - Owned'), 
                                    'value'))

                            'call test case personal data verif'
                            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP1 - CustomerData/TabMSPersonalDataVerif'), 
                                [:], FailureHandling.CONTINUE_ON_FAILURE)
                        }
                        
                        'select customer model'
                        WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/select_Select One Employee  Non Professional  Professional  Small Medium Enterprise'), 
                            datafile.getValue(GlobalVariable.NumofMS, 25), false)

						if (datafile.getValue(GlobalVariable.NumofMS, 27).length() > 1) {
							'click lookup button proffession name'
							WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_Profession Name_btn btn-raised btn-primary'))

							'input profession code'
							WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Profession Code_professionCodeId'),
								datafile.getValue(GlobalVariable.NumofMS, 26))

							'click button search'
							WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_Search'))

							'verify input error'
							if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/a_Select'),
								10, FailureHandling.OPTIONAL)) {
								'click select'
								WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/a_Select'))
							} else {
								'click X'
								WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_X'))

								flagWarning++

								continue
							}
						}
						
                        'click button position SLIK'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_Position SLIK_btn btn-raised btn-primary'))

                        'input jabatan code'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Jabatan Code_JabatanCodeId'), 
                            datafile.getValue(GlobalVariable.NumofMS, 28))

                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_Search'))

                        'verify input error'
                        if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/a_Select'), 
                            10, FailureHandling.OPTIONAL)) {
                            'click select'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/a_Select'))
                        } else {
                            'click X'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_X'))

                            flagWarning++

                            continue
                        }
                        
                        'input establishment date'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Establishment Date_form-control ng-untouched ng-pristine ng-invalid'), 
                            datafile.getValue(GlobalVariable.NumofMS, 30))

                        'click button lookup job data'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_Job Position_btn btn-raised btn-primary'))

                        'input job data code'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Job Position Code_JobCodeId'), 
                            datafile.getValue(GlobalVariable.NumofMS, 37))

                        'click button search'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_Search'))

                        'verify input error'
                        if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/a_Select'), 
                            10, FailureHandling.OPTIONAL)) {
                            'click select'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/a_Select'))
                        } else {
                            'click X'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_X'))

                            flagWarning++

                            continue
                        }
                        
                        'input share'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Share()_form-control ng-untouched ng-pristine ng-valid'), 
                            datafile.getValue(GlobalVariable.NumofMS, 39))

                        if (WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Owner_ng-untouched ng-pristine ng-valid'), 
                            2, FailureHandling.OPTIONAL)) {
                            if (datafile.getValue(GlobalVariable.NumofMS, 40).equalsIgnoreCase('Yes')) {
                                'click check box is Owner'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Owner_ng-untouched ng-pristine ng-valid'))
                            }
                        } else if (WebUI.verifyElementChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Owner_ng-untouched ng-pristine ng-valid'), 
                            2, FailureHandling.OPTIONAL)) {
                            if (datafile.getValue(GlobalVariable.NumofMS, 40).equalsIgnoreCase('No')) {
                                'click check box is Owner'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Owner_ng-untouched ng-pristine ng-valid'))
                            }
                        }
                        
                        if (WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Active_ng-untouched ng-pristine ng-valid'), 
                            2, FailureHandling.OPTIONAL)) {
                            if (datafile.getValue(GlobalVariable.NumofMS, 41).equalsIgnoreCase('Yes')) {
                                'click check box is Active'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Active_ng-untouched ng-pristine ng-valid'))
                            }
                        } else if (WebUI.verifyElementChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Active_ng-untouched ng-pristine ng-valid'), 
                            2, FailureHandling.OPTIONAL)) {
                            if (datafile.getValue(GlobalVariable.NumofMS, 41).equalsIgnoreCase('No')) {
                                'click check box is Active'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Active_ng-untouched ng-pristine ng-valid'))
                            }
                        }
                        
                        if (WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Signer_ng-untouched ng-pristine ng-valid'), 
                            2, FailureHandling.OPTIONAL)) {
                            if (datafile.getValue(GlobalVariable.NumofMS, 42).equalsIgnoreCase('Yes')) {
                                'click check box is Signer'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Signer_ng-untouched ng-pristine ng-valid'))
                            }
                        } else if (WebUI.verifyElementChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Signer_ng-untouched ng-pristine ng-valid'), 
                            2, FailureHandling.OPTIONAL)) {
                            if (datafile.getValue(GlobalVariable.NumofMS, 42).equalsIgnoreCase('No')) {
                                'click check box is Signer'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Signer_ng-untouched ng-pristine ng-valid'))
                            }
                        }
                        
                        if (datafile.getValue(GlobalVariable.NumofMS, 44).length() > 1) {
                            'click button lookup department AML'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_DEPARTMENT AML_btn btn-raised btn-primary'))

                            'input department AML Code'
                            WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Code_MasterCodeId'), 
                                datafile.getValue(GlobalVariable.NumofMS, 44))

                            'click button search'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_Search'))

                            'verify input error'
                            if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/a_Select'), 
                                10, FailureHandling.OPTIONAL)) {
                                'click select'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/a_Select'))
                            } else {
                                'click X'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_X'))

                                flagWarning++

                                continue
                            }
                        }
                    } else if (datafile.getValue(GlobalVariable.NumofMS, 14).equalsIgnoreCase('Company')) {
                        'click radio company'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/span_ Company'))

                        'click button lookup shareholder'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/button_Shareholder Legal Name_btn btn-raised btn-primary'))

                        'input customer no'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/input_Customer No_custNoId'), 
                            datafile.getValue(GlobalVariable.NumofMS, 49))

                        'input customer name'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/input_Customer Name_custNameId'), 
                            datafile.getValue(GlobalVariable.NumofMS, 50))

                        'input NPWP'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/input_NPWP_NpwpId'), 
                            datafile.getValue(GlobalVariable.NumofMS, 51))

                        'click button search'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/button_Search'))

                        'verify input error'
                        if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/a_Select'), 
                            10, FailureHandling.OPTIONAL)) {
                            'click select'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/a_Select'))
                        } else {
                            'click X'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/Button_X'))

                            'click button cancel'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/button_Cancel'))

                            'Write to Excel FAILED'
                            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, 
                                '2.TabManagementShareholderData', 0, GlobalVariable.NumofMS - 1, GlobalVariable.StatusFailed)

                            'Write To Excel GlobalVariable.StatusReasonLookup'
                            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, 
                                '2.TabManagementShareholderData', 1, GlobalVariable.NumofMS - 1, GlobalVariable.StatusReasonLookup)

                            'Flagfailed +1 karena lookup gagal'
                            (GlobalVariable.FlagFailed)++

                            continue
                        }
                        
						'check if role testing maka get data MS untuk data verif'
						if (GlobalVariable.RoleCompany == 'Testing') {
							'call function get data'
							getDataMS(datafile)

							'add company type to array'
							GlobalVariable.confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/select_Select One CV  Koperasi  PT'),
									'value'))

							'add customer model to array'
							GlobalVariable.confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/select_Select One Corporate  Non Corporate'),
									'value'))

							'add ownership to array'
							GlobalVariable.confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/select_Select One Dinas  Family  KPR  Rented  Self - Owned'),
									'value'))

							'call test case company data verif'
							WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP1 - CustomerData/TabMSCompanyDataVerif'),
								[:], FailureHandling.CONTINUE_ON_FAILURE)
						}
						
                        'click button position SLIK'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/button_Position SLIK_btn btn-raised btn-primary'))

                        'input jabatan code'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/input_Jabatan Code_JabatanCodeId'), 
                            datafile.getValue(GlobalVariable.NumofMS, 54))

                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/button_Search'))

                        'verify input error'
                        if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/a_Select'), 
                            10, FailureHandling.OPTIONAL)) {
                            'click select'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/a_Select'))
                        } else {
                            'click X'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/button_X'))

                            flagWarning++

                            continue
                        }
                        
                        
                        
                        'select company type'
                        WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/select_Select One CV  Koperasi  PT'), 
                            datafile.getValue(GlobalVariable.NumofMS, 56), false)

                        'input share'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/input_Share()_form-control ng-untouched ng-pristine ng-valid'), 
                            datafile.getValue(GlobalVariable.NumofMS, 58))

                        if (WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Owner_ng-untouched ng-pristine ng-valid'), 
                            2, FailureHandling.OPTIONAL)) {
                            if (datafile.getValue(GlobalVariable.NumofMS, 60).equalsIgnoreCase('Yes')) {
                                'click check box is Owner'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/input_Is Owner_ng-untouched ng-pristine ng-valid'))
                            }
                        } else if (WebUI.verifyElementChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Owner_ng-untouched ng-pristine ng-valid'), 
                            2, FailureHandling.OPTIONAL)) {
                            if (datafile.getValue(GlobalVariable.NumofMS, 60).equalsIgnoreCase('No')) {
                                'click check box is Owner'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/input_Is Owner_ng-untouched ng-pristine ng-valid'))
                            }
                        }
                        
                        if (WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Active_ng-untouched ng-pristine ng-valid'), 
                            2, FailureHandling.OPTIONAL)) {
                            if (datafile.getValue(GlobalVariable.NumofMS, 59).equalsIgnoreCase('Yes')) {
                                'click check box is Active'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/input_Is Active_ng-untouched ng-pristine ng-valid'))
                            }
                        } else if (WebUI.verifyElementChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Active_ng-untouched ng-pristine ng-valid'), 
                            2, FailureHandling.OPTIONAL)) {
                            if (datafile.getValue(GlobalVariable.NumofMS, 59).equalsIgnoreCase('No')) {
                                'click check box is Active'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/input_Is Active_ng-untouched ng-pristine ng-valid'))
                            }
                        }
                    }
                    
					'call function untuk get data MS yang sudah di input untuk verif Store DB'
                    getData(datafile)
					}
                
                'click button save'
                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_Save'))

                if (Integer.parseInt(datafile.getValue(GlobalVariable.NumofMS, 4)) == 0 && GlobalVariable.FlagFailed==0) {
                    'Check alert'
                    GlobalVariable.FlagFailed=CustomKeywords.'checkSaveProcess.checkSaveProcess.checkAlert'(GlobalVariable.NumofMS, '2.TabManagementShareholderData')
                }
                
				if (datafile.getValue(GlobalVariable.NumofMS, 14).equalsIgnoreCase('Personal') || datafile.getValue(
					GlobalVariable.NumofMS, 14).equalsIgnoreCase('Company')) {
					if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/button_Cancel'),
						5, FailureHandling.OPTIONAL)) {
						'click button cancel'
						WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/button_Cancel'))

						'customer added -1'
						(GlobalVariable.countNumofCustomer)--
					} else {
						if (flagWarning > 0) {
							CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath,
								'2.TabManagementShareholderData', 0, GlobalVariable.NumofMS - 1, GlobalVariable.StatusWarning)
						}
					}
				} else if (datafile.getValue(GlobalVariable.NumofMS, 14).equalsIgnoreCase('Public')) {
					if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/button_Cancel'),
						5, FailureHandling.OPTIONAL)) {
						'click button cancel'
						WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/button_Cancel'))

						'customer added -1'
						(GlobalVariable.countNumofCustomer)--
					} else {
						if (flagWarning > 0) {
							CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath,
								'2.TabManagementShareholderData', 0, GlobalVariable.NumofMS - 1, GlobalVariable.StatusWarning)
						}
					}
				}
                'verify flagfailed lookup == 0 '
                if (GlobalVariable.FlagFailed == 0) {
                    'Check save Process write to excel'
                    CustomKeywords.'checkSaveProcess.checkSaveProcess.checkStatus'(Integer.parseInt(datafile.getValue(GlobalVariable.NumofMS, 
                                4)), findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Tableheadermanagementshareholder'), 
                        GlobalVariable.NumofMS, '2.TabManagementShareholderData')

                    'customer added +1'
                    (GlobalVariable.countNumofCustomer)++

                    if (Integer.parseInt(datafile.getValue(GlobalVariable.NumofMS, 4)) == 0) {
                        'Check error validasi'
                        CustomKeywords.'checkSaveProcess.checkSaveProcess.checkValidasi'(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/errorvalidasi'), 
                            GlobalVariable.NumofMS, '2.TabManagementShareholderData')
                    }
                    
                    if (datafile.getValue(GlobalVariable.NumofMS, 13) == 'Input Data') {
                        if ((GlobalVariable.RoleCompany == 'Testing') && (GlobalVariable.CheckVerifStoreDBCompany == 'Yes')) {
                            if (datafile.getValue(GlobalVariable.NumofMS, 14).equalsIgnoreCase('Company')) {
                                'call test case MS Company data store verif'
                                WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP1 - CustomerData/TabMSCompanyDataStoreDBVerif'), 
                                    [:], FailureHandling.CONTINUE_ON_FAILURE)
                            } else if (datafile.getValue(GlobalVariable.NumofMS, 14).equalsIgnoreCase('Personal')) {
                                'call test case MS Personal data store verif'
                                WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP1 - CustomerData/TabMSPersonalDataStoreDBVerif'), 
                                    [:], FailureHandling.CONTINUE_ON_FAILURE)
                            }
                        }
                    } else {
                        if ((GlobalVariable.RoleCompany == 'Testing') && (GlobalVariable.CheckVerifStoreDBCompany == 'Yes')) {
                            if (datafile.getValue(GlobalVariable.NumofMS, 14).equalsIgnoreCase('Company')) {
                                'call test case MS Company  data store verif'
                                WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP1 - CustomerData/TabMSCompanyDataStoreDBVerif-LookUp'), 
                                    [:], FailureHandling.CONTINUE_ON_FAILURE)
                            } else if (datafile.getValue(GlobalVariable.NumofMS, 14).equalsIgnoreCase('Personal')) {
                                'call test case MS personal data store verif'
                                WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP1 - CustomerData/TabMSPersonalDataStoreDBVerif-LookUp'), 
                                    [:], FailureHandling.CONTINUE_ON_FAILURE)
                            }
                        }
                    }
                }
				
				if (datafile.getValue(GlobalVariable.NumofMS, 14).equalsIgnoreCase('Personal') || datafile.getValue(
					GlobalVariable.NumofMS, 14).equalsIgnoreCase('Company')) {
					if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/button_Cancel'),
						5, FailureHandling.OPTIONAL)) {
						'click button cancel'
						WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/button_Cancel'))

						'customer added -1'
						(GlobalVariable.countNumofCustomer)--
					} else {
						if (flagWarning > 0) {
							CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath,
								'2.TabManagementShareholderData', 0, GlobalVariable.NumofMS - 1, GlobalVariable.StatusWarning)
						}
					}
				} else if (datafile.getValue(GlobalVariable.NumofMS, 14).equalsIgnoreCase('Public')) {
					if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/button_Cancel'),
						5, FailureHandling.OPTIONAL)) {
						'click button cancel'
						WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/button_Cancel'))

						'customer added -1'
						(GlobalVariable.countNumofCustomer)--
					} else {
						if (flagWarning > 0) {
							CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath,
								'2.TabManagementShareholderData', 0, GlobalVariable.NumofMS - 1, GlobalVariable.StatusWarning)
						}
					}
				}
				break
            }
		}
    }
}

'click button save'
WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/button_Save'))

'cek alert'
GlobalVariable.FlagFailed = CustomKeywords.'checkSaveProcess.checkSaveProcess.checkAlert'(GlobalVariable.NumofColm, '2a.TabManagementShareholderMain')

if (GlobalVariable.FlagFailed == 0) {
   'Write to Excel SUCCESS'
   CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '2a.TabManagementShareholderMain',
	   0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusSuccess)
}

def getDataMS(def datafile) {
    'declare array for confins data'
    def confinsdata = []

    if (datafile.getValue(GlobalVariable.NumofMS, 14).equalsIgnoreCase('Company')) {
        'add customer name to array'
        confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/input_Shareholder Legal Name_form-control ng-untouched ng-pristine ng-invalid'), 
                'value'))

        'add tax id to array'
        confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/input_Tax Id No_form-control ng-untouched ng-pristine ng-invalid'), 
                'value'))

        'add address to array'
        confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/textarea_Address_form-control ng-untouched ng-pristine ng-invalid'), 
                'value'))

        'add RT to array'
        confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/input_RT'), 
                'value'))

        'add RW to array'
        confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/input_RW'), 
                'value'))

        'add zipcode to array'
        confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/LabelZipcode'), 
                'value'))

        'add kelurahan to array'
        confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/LabelKelurahan'), 
                'value'))

        'add kecamatan to array'
        confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/LabelKecamatan'), 
                'value'))

        'add kota to array'
        confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/LabelKota'), 
                'value'))
    } else if (datafile.getValue(GlobalVariable.NumofMS, 14).equalsIgnoreCase('Personal')) {
        'add customer name to array'
        confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Shareholder Legal Name_form-control ng-untouched ng-pristine ng-invalid'), 
                'value'))

        'add birth place to array'
        confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Birth Place_form-control ng-untouched ng-pristine ng-invalid'), 
                'value'))

        'add id expired date to array'
        confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Id Expired Date_form-control ng-untouched ng-pristine ng-valid'), 
                'value'))

        'add mobile phone to array'
        confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Mobile Phone_form-control ng-untouched ng-pristine ng-invalid'), 
                'value'))

        'add birth date to array'
        confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Birth Date_form-control ng-untouched ng-pristine ng-invalid'), 
                'value'))

        'add id no to array'
        confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Id No_form-control ng-untouched ng-pristine ng-invalid'), 
                'value'))

        'add tax id to array'
        confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Tax Id No_form-control ng-untouched ng-pristine ng-valid'), 
                'value'))

        'add mother maiden name to array'
        confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Mother Maiden Name_form-control ng-untouched ng-pristine ng-invalid'), 
                'value'))

        'add email to array'
        confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Email_form-control ng-untouched ng-pristine ng-valid'), 
                'value'))

        'add address to array'
        confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/textarea_Address_form-control ng-untouched ng-pristine ng-invalid'), 
                'value'))

        'add RT to array'
        confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/input_RT'), 
                'value'))

        'add RW to array'
        confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/input_RW'), 
                'value'))

        'add zipcode to array'
        confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/LabelZipcode'), 
                'value'))

        'add kelurahan to array'
        confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/LabelKelurahan'), 
                'value'))

        'add kecamatan to array'
        confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/LabelKecamatan'), 
                'value'))

        'add kota to array'
        confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/LabelKota'), 
                'value'))
    }
    
    GlobalVariable.confinsdata = confinsdata
}

def getData(def datafile){
	'check if role testing dan input dengan lookup maka get data dari confins untuk verif store DB'
	if (GlobalVariable.RoleCompany == 'Testing' && datafile.getValue(GlobalVariable.NumofMS, 13).equalsIgnoreCase('LookUp')) {
		if (datafile.getValue(GlobalVariable.NumofMS, 14).equalsIgnoreCase('Company')) {
			'call function MS get Data'
			getDataMS(datafile)

			'add Position SLIK to array'
			GlobalVariable.confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/label_positionSLIK'),
					'value'))

			Select selectcustomertype = new Select(DriverFactory.getWebDriver().findElement(By.xpath('//div[@id="CustMainData"]/div/div[2]/div/div/lib-ucdropdownlist/div/select')))

			'add company type to array'
			GlobalVariable.confinsdata.add(selectcustomertype.getFirstSelectedOption().getText())

			Select selectcustomermodel = new Select(DriverFactory.getWebDriver().findElement(By.xpath('//div[@id="CustMainData"]/div[2]/div[2]/div/div/lib-ucdropdownlist/div/select')))

			'add customer model to array'
			GlobalVariable.confinsdata.add(selectcustomermodel.getFirstSelectedOption().getText())

			'add share to array'
			GlobalVariable.confinsdata.add(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/input_Share()_form-control ng-untouched ng-pristine ng-valid'),
					'value').replace('%', ''))

			if (WebUI.verifyElementChecked(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/input_Is Active_ng-untouched ng-pristine ng-valid'),
				2, FailureHandling.OPTIONAL)) {
				'add is active to array'
				GlobalVariable.confinsdata.add('Yes')
			} else {
				'add is active to array'
				GlobalVariable.confinsdata.add('No')
			}
			
			if (WebUI.verifyElementChecked(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/input_Is Owner_ng-untouched ng-pristine ng-valid'),
				2, FailureHandling.OPTIONAL)) {
				'add is owner to array'
				GlobalVariable.confinsdata.add('Yes')
			} else {
				'add is owner to array'
				GlobalVariable.confinsdata.add('No')
			}
			
			Select selectownership = new Select(DriverFactory.getWebDriver().findElement(By.xpath('//*[@id="Address"]/div/div[2]/div[2]/div/div/div/div/select')))

			'add ownership to array'
			GlobalVariable.confinsdata.add(selectownership.getFirstSelectedOption().getText())
		}else if (datafile.getValue(GlobalVariable.NumofMS, 14).equalsIgnoreCase('Personal')) {
		'call function MS get data'
		getDataMS(datafile)

		Select selectIdType = new Select(DriverFactory.getWebDriver().findElement(By.xpath('//*[@id="CustMainData"]/div[2]/div[3]/div/div/lib-ucdropdownlist/div/select')))

		'add ID TYPE array'
		GlobalVariable.confinsdata.add(selectIdType.getFirstSelectedOption().getText())

		Select selectmaritalstatus = new Select(DriverFactory.getWebDriver().findElement(By.xpath('//*[@id="CustMainData"]/div[2]/div[7]/div/div/lib-ucdropdownlist/div/select')))

		'add marital status array'
		GlobalVariable.confinsdata.add(selectmaritalstatus.getFirstSelectedOption().getText())

		Select selectGender = new Select(DriverFactory.getWebDriver().findElement(By.xpath('//*[@id="CustMainData"]/div[1]/div[2]/div/div/lib-ucdropdownlist/div/select')))

		Select selectcustmodel = new Select(DriverFactory.getWebDriver().findElement(By.xpath('//*[@id="CustMainData"]/div[2]/div[11]/div[1]/div/lib-ucdropdownlist/div/select')))

		'add customer model array'
		GlobalVariable.confinsdata.add(selectcustmodel.getFirstSelectedOption().getText())

		'add proffesion to array'
		GlobalVariable.confinsdata.add(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/labelProffesion'),
				'value'))

		'add position SLIK to array'
		GlobalVariable.confinsdata.add(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/label_positionSLIK'),
				'value'))

		'add establishment date to array'
		GlobalVariable.confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Establishment Date_form-control ng-untouched ng-pristine ng-invalid'),
				'value'))

		'add Gender array'
		GlobalVariable.confinsdata.add(selectGender.getFirstSelectedOption().getText())

		'add job position array'
		GlobalVariable.confinsdata.add(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/labelJobposition'),
				'value'))

		'add share to array'
		GlobalVariable.confinsdata.add(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Share()_form-control ng-untouched ng-pristine ng-valid'),
				'value').replace(' %', ''))

		if (WebUI.verifyElementChecked(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Active_ng-untouched ng-pristine ng-valid'),
			2, FailureHandling.OPTIONAL)) {
			'add is active to array'
			GlobalVariable.confinsdata.add('Yes')
		} else {
			'add is active to array'
			GlobalVariable.confinsdata.add('No')
		}
		
		if (WebUI.verifyElementChecked(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Active_ng-untouched ng-pristine ng-valid'),
			2, FailureHandling.OPTIONAL)) {
			'add is owner to array'
			GlobalVariable.confinsdata.add('Yes')
		} else {
			'add is owner to array'
			GlobalVariable.confinsdata.add('No')
		}
		
		if (WebUI.verifyElementChecked(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Signer_ng-untouched ng-pristine ng-valid'),
			2, FailureHandling.OPTIONAL)) {
			'add is signer to array'
			GlobalVariable.confinsdata.add('Yes')
		} else {
			'add is signer to array'
			GlobalVariable.confinsdata.add('No')
		}
		
		'add department aml to array'
		GlobalVariable.confinsdata.add(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/labelDepartmnetAML'),
				'value'))

		'add authority aml to array'
		GlobalVariable.confinsdata.add(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/labelAuthorityAML'),
				'value'))

		Select selectownership = new Select(DriverFactory.getWebDriver().findElement(By.xpath('//*[@id="Address"]/div/div[2]/div[2]/div/div/div/div/select')))

		'add ownership to array'
		GlobalVariable.confinsdata.add(selectownership.getFirstSelectedOption().getText())
	}
}
}