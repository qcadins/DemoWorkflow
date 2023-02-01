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

import groovy.sql.Sql
import internal.GlobalVariable as GlobalVariable

GlobalVariable.FlagWarning = 0

'get data file path'
GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'(GlobalVariable.PathCompany)

'declare variableData'
ArrayList<String> variableData

'declare datafileCustomerCompany'
datafileCustomerCompany = findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData')

'declare datafileMS'
datafileMS = findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabManagementShareholder')

'get copyapp value dari datafileMS'
copyapp = datafileMS.getValue(GlobalVariable.NumofMS, 10)

countMS =  datafileMS.getColumnNumbers()

'check if testing'
if (GlobalVariable.RoleCompany == 'Testing') {
    'verify application step'
    checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/applicationcurrentstep')), 
            'MANAGEMENT SHAREHOLDER', false, FailureHandling.OPTIONAL))
}

'Loop Multiple ManagementSharholder Data'
for (GlobalVariable.NumofMS = GlobalVariable.StartIndex; GlobalVariable.NumofMS <= (countMS - 1); (GlobalVariable.NumofMS)++) {
    GlobalVariable.FlagFailed = 0

    if (datafileMS.getValue(GlobalVariable.NumofMS, 12) == datafileCustomerCompany.getValue(GlobalVariable.NumofColm, 13)) {
        if (WebUI.verifyElementNotPresent(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/td_MSName'), 
            GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
            'count table guarantor pada confins'
            variableData = DriverFactory.getWebDriver().findElements(By.cssSelector('#mgmnt-shrholder-tab > app-mngmnt-shrhldr-main-data-paging > div > div:nth-child(2) > lib-ucgridview > div > table > thead'))
        } else {
            'count table guarantor pada confins'
            variableData = DriverFactory.getWebDriver().findElements(By.cssSelector('#mgmnt-shrholder-tab > app-mngmnt-shrhldr-main-data-paging > div > div:nth-child(2) > lib-ucgridview > div > table > tbody tr'))
        }
        
        if(datafileMS.getValue(GlobalVariable.NumofMS, 14).equalsIgnoreCase('Personal')){
        for (i = 1; i <= variableData.size(); i++) {
            'modify object guarantor name'
            modifyNewMSName = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/FromTypeName'), 
                'xpath', 'equals', ('//*[@id="mgmnt-shrholder-tab"]/app-mngmnt-shrhldr-main-data-paging/div/div[2]/lib-ucgridview/div/table/tbody/tr[' + 
                i) + ']/td[2]', true)

            'verify if copy app edit'
            if (copyapp.equalsIgnoreCase('Edit')) {
                if (WebUI.verifyElementNotPresent(modifyNewMSName, GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                    copyapp == 'No'
                } else if (WebUI.verifyElementPresent(modifyNewMSName, GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                    if (WebUI.getText(modifyNewMSName).equalsIgnoreCase(datafileMS.getValue(GlobalVariable.NumofMS, 19)) || 
                    WebUI.getText(modifyNewMSName).equalsIgnoreCase(datafileMS.getValue(GlobalVariable.NumofMS, 17))) {
                        break
                    }
                }
            }
        
		
            if ((i == variableData.size()) || (copyapp == 'No')) {
                'click button add'
                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/button_Add'))
                if (datafileMS.getValue(GlobalVariable.NumofMS, 13) == 'Input Data') {
                        'click radio personal'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/span_ Personal'))

						'call function check DDL'
						checkDDL()
						
                        'input shareholder name'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Shareholder Legal Name_form-control ng-untouched ng-pristine ng-invalid'), 
                            datafileMS.getValue(GlobalVariable.NumofMS, 19))

                        'input birth place'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Birth Place_form-control ng-untouched ng-pristine ng-invalid'), 
                            datafileMS.getValue(GlobalVariable.NumofMS, 20))

                        'select idtype'
                        WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/select_Select One AKTA  E-KTP  KARTU TANDA MAHASISWA  KITAS  NPWP  SIM'), 
                            datafileMS.getValue(GlobalVariable.NumofMS, 21), false)

                        'input Id Expired Date'
                        if (((datafileMS.getValue(GlobalVariable.NumofMS, 21) != 'E-KTP') && (datafileMS.getValue(GlobalVariable.NumofMS, 
                            21) != 'NPWP')) && (datafileMS.getValue(GlobalVariable.NumofMS, 21) != 'AKTA')) {
                            WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Id Expired Date_form-control ng-untouched ng-pristine ng-valid'), 
                                datafileMS.getValue(GlobalVariable.NumofMS, 22))
                        }
                        
                        'select marital status'
                        WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/select_Select One Married  Single  Widow'), 
                            datafileMS.getValue(GlobalVariable.NumofMS, 23), false)

                        'input mobile phone'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Mobile Phone_form-control ng-untouched ng-pristine ng-invalid'), 
                            datafileMS.getValue(GlobalVariable.NumofMS, 24))

                        'select customer model'
                        WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/select_Select One Employee  Non Professional  Professional  Small Medium Enterprise'), 
                            datafileMS.getValue(GlobalVariable.NumofMS, 25), false)

                        if (datafileMS.getValue(GlobalVariable.NumofMS, 27).length() > 1) {
                            'click lookup button proffession name'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_Profession Name_btn btn-raised btn-primary'))

                            'input profession code'
                            WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Profession Code_professionCodeId'), 
                                datafileMS.getValue(GlobalVariable.NumofMS, 26))

                            'click button search'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_Search'))

                            'verify input error'
                            if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/a_Select'), 
                                GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                                'click select'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/a_Select'))
                            } else {
                                'click X'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_X'))

                                (GlobalVariable.FlagWarning)++

                                continue
                            }
                        }
                        
                        'click button position SLIK'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_Position SLIK_btn btn-raised btn-primary'))

                        'input jabatan code'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Jabatan Code_JabatanCodeId'), 
                            datafileMS.getValue(GlobalVariable.NumofMS, 28))

                        'click button search'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_Search'))

                        'verify input error'
                        if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/a_Select'), 
                            GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                            'click select'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/a_Select'))
                        } else {
                            'click X'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_X'))

                            (GlobalVariable.FlagWarning)++

                            continue
                        }
                        
                        'input establishment date'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Establishment Date_form-control ng-untouched ng-pristine ng-invalid'), 
                            datafileMS.getValue(GlobalVariable.NumofMS, 30))

                        'select gender'
                        WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/select_Select One Female  Male'), 
                            datafileMS.getValue(GlobalVariable.NumofMS, 31), false)

                        'input birth date'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Birth Date_form-control ng-untouched ng-pristine ng-invalid'), 
                            datafileMS.getValue(GlobalVariable.NumofMS, 32))

                        'input id no'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Id No_form-control ng-untouched ng-pristine ng-invalid'), 
                            datafileMS.getValue(GlobalVariable.NumofMS, 33))

                        'input tax id'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Tax Id No_form-control ng-untouched ng-pristine ng-valid'), 
                            datafileMS.getValue(GlobalVariable.NumofMS, 34))

                        'input mother maiden name'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Mother Maiden Name_form-control ng-untouched ng-pristine ng-invalid'), 
                            datafileMS.getValue(GlobalVariable.NumofMS, 35))

                        'input email'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Email_form-control ng-untouched ng-pristine ng-valid'), 
                            datafileMS.getValue(GlobalVariable.NumofMS, 36))

                        'klik button job position'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_Job Position_btn btn-raised btn-primary'))

                        'input job position code'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Job Position Code_JobCodeId'), 
                            datafileMS.getValue(GlobalVariable.NumofMS, 37))

                        'click button search'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_Search'))

                        'verify input error'
                        if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/a_Select'), 
                            GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                            'click select'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/a_Select'))
                        } else {
                            'click X'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_X'))

                            (GlobalVariable.FlagWarning)++

                            continue
                        }
                        
                        'input share'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Share()_form-control ng-untouched ng-pristine ng-valid'), 
                            datafileMS.getValue(GlobalVariable.NumofMS, 39))

                        if (WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Owner_ng-untouched ng-pristine ng-valid'), 
                            GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                            if (datafileMS.getValue(GlobalVariable.NumofMS, 40).equalsIgnoreCase('Yes')) {
                                'click check box is Owner'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Owner_ng-untouched ng-pristine ng-valid'))
                            }
                        } else if (WebUI.verifyElementChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Owner_ng-untouched ng-pristine ng-valid'), 
                            GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                            if (datafileMS.getValue(GlobalVariable.NumofMS, 40).equalsIgnoreCase('No')) {
                                'click check box is Owner'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Owner_ng-untouched ng-pristine ng-valid'))
                            }
                        }
                        
                        if (WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Active_ng-untouched ng-pristine ng-valid'), 
                            GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                            if (datafileMS.getValue(GlobalVariable.NumofMS, 41).equalsIgnoreCase('Yes')) {
                                'click check box is Active'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Active_ng-untouched ng-pristine ng-valid'))
                            }
                        } else if (WebUI.verifyElementChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Active_ng-untouched ng-pristine ng-valid'), 
                            GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                            if (datafileMS.getValue(GlobalVariable.NumofMS, 41).equalsIgnoreCase('No')) {
                                'click check box is Active'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Active_ng-untouched ng-pristine ng-valid'))
                            }
                        }
                        
                        if (WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Signer_ng-untouched ng-pristine ng-valid'), 
                            GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                            if (datafileMS.getValue(GlobalVariable.NumofMS, 42).equalsIgnoreCase('Yes')) {
                                'click check box is Signer'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Signer_ng-untouched ng-pristine ng-valid'))
                            }
                        } else if (WebUI.verifyElementChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Signer_ng-untouched ng-pristine ng-valid'), 
                            GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                            if (datafileMS.getValue(GlobalVariable.NumofMS, 42).equalsIgnoreCase('No')) {
                                'click check box is Signer'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Signer_ng-untouched ng-pristine ng-valid'))
                            }
                        }
                        
                        'click button lookup department AML'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_DEPARTMENT AML_btn btn-raised btn-primary'))

                        'input department AML Code'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Code_MasterCodeId'), 
                            datafileMS.getValue(GlobalVariable.NumofMS, 44))

                        'click button search'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_Search'))

                        'verify input error'
                        if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/a_Select'), 
                            GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                            'click select'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/a_Select'))
                        } else {
                            'click X'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_X'))

                            (GlobalVariable.FlagWarning)++

                            continue
                        }
                        
                        if (datafileMS.getValue(GlobalVariable.NumofMS, 47).length() > 1) {
                            'click button authority AML lookup'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_Authority AML_btn btn-raised btn-primary'))

                            'input authority AML code'
                            WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Code_MasterCodeId'), 
                                datafileMS.getValue(GlobalVariable.NumofMS, 46))

                            'click button search'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_Search'))

                            'verify input error'
                            if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/a_Select'), 
                                GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                                'click select'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/a_Select'))
                            } else {
                                'click X'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_X'))

                                (GlobalVariable.FlagWarning)++

                                continue
                            }
                        }
                    
                        if (datafileMS.getValue(GlobalVariable.NumofMS, 70).length() > 1) {
                            'click button copy'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/button_Copy'))
							
							'call function MS Personal get data'
							getDataMS()
                        } else {
                            'input address'
                            WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/textarea_Address_form-control ng-untouched ng-pristine ng-invalid'), 
                                datafileMS.getValue(GlobalVariable.NumofMS, 71))

                            'input RT'
                            WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/input_RT'), 
                                datafileMS.getValue(GlobalVariable.NumofMS, 72))

                            'input RW'
                            WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/input_RW'), 
                                datafileMS.getValue(GlobalVariable.NumofMS, 73))

                            'click button lookup zipcode'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/button_Zipcode_btn btn-raised btn-primary'))

                            'input zipcode'
                            WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/input_Zip Code_ZipCode'), 
                                datafileMS.getValue(GlobalVariable.NumofMS, 74))

                            'input kecamatan'
                            WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/input_Kecamatan_kecamatan'), 
                                datafileMS.getValue(GlobalVariable.NumofMS, 75))

                            'input kelurahan'
                            WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/input_Kelurahan_Kelurahan'), 
                                datafileMS.getValue(GlobalVariable.NumofMS, 76))

                            'input kota'
                            WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/input_Kota_kota'), 
                                datafileMS.getValue(GlobalVariable.NumofMS, 77))

                            'klik button search'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/button_Search'))

                            'verify input error'
                            if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/a_Select'), 
                                GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                                'click select'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/a_Select'))
                            } else {
                                'click X'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_X'))

                                (GlobalVariable.FlagWarning)++

                                continue
                            }
                            
                            'select ownership'
                            WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/select_Select One Dinas  Family  KPR  Rented  Self - Owned'), 
                                datafileMS.getValue(GlobalVariable.NumofMS, 78), false)
                        }
                                        
                } else if (datafileMS.getValue(GlobalVariable.NumofMS, 13) == 'LookUp') {
                        'click radio personal'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/span_ Personal'))

						'call function check ddl'
						checkDDL()
						
                        'click button lookup customer'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_ShareHolderLegalName'))

                        'input customer no'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Customer No_custNoId'), 
                            datafileMS.getValue(GlobalVariable.NumofMS, 16))

                        'input customer name'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Customer Name_custNameId'), 
                            datafileMS.getValue(GlobalVariable.NumofMS, 17))

                        'input ID No'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Id No_IdNoId'), 
                            datafileMS.getValue(GlobalVariable.NumofMS, 18))

                        'click search'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_Search'))

                        'verify input error'
                        if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/a_Select'), 
                            GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                            'click select'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/a_Select'))
                        } else {
                            'click X'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/Button_X'))

                            'click button cancel'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_Cancel'))

							'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.StatusReasonLookup'
							CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('2.TabManagementShareholderData', GlobalVariable.NumofMS, GlobalVariable.StatusFailed, GlobalVariable.StatusReasonLookup)
							
                            'Flagfailed +1 karena lookup gagal'
                            (GlobalVariable.FlagFailed)++

                            continue
                        }
                        
                        'check if role testing maka get data MS untuk data verif'
                        if (GlobalVariable.RoleCompany == 'Testing') {
                            'call function MS Personal get data'
                            getDataMS()

                            'add id type to array'
                            GlobalVariable.Confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/select_Select One AKTA  E-KTP  KARTU TANDA MAHASISWA  KITAS  NPWP  SIM'), 
                                    'value'))

                            'add marital status to array'
                            GlobalVariable.Confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/select_Select One Married  Single  Widow'), 
                                    'value'))

                            'add customer model to array'
                            GlobalVariable.Confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/select_Select One Employee  Non Professional  Professional  Small Medium Enterprise'), 
                                    'value'))

                            'add gender to array'
                            GlobalVariable.Confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/select_Select One Female  Male'), 
                                    'value'))

                            'add ownership to array'
                            GlobalVariable.Confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/select_Select One Dinas  Family  KPR  Rented  Self - Owned'), 
                                    'value'))

                            'call test case personal data verif'
                            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP1 - CustomerData/ManagementShareholder/TabMSPersonalDataVerif'), 
                                [:], FailureHandling.CONTINUE_ON_FAILURE)
                        }
                        
                        'select customer model'
                        WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/select_Select One Employee  Non Professional  Professional  Small Medium Enterprise'), 
                            datafileMS.getValue(GlobalVariable.NumofMS, 25), false)

                        if (datafileMS.getValue(GlobalVariable.NumofMS, 27).length() > 1) {
                            'click lookup button proffession name'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_Profession Name_btn btn-raised btn-primary'))

                            'input profession code'
                            WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Profession Code_professionCodeId'), 
                                datafileMS.getValue(GlobalVariable.NumofMS, 26))

                            'click button search'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_Search'))

                            'verify input error'
                            if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/a_Select'), 
                                GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                                'click select'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/a_Select'))
                            } else {
                                'click X'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_X'))

                                (GlobalVariable.FlagWarning)++

                                continue
                            }
                        }
                        
                        'click button position SLIK'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_Position SLIK_btn btn-raised btn-primary'))

                        'input jabatan code'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Jabatan Code_JabatanCodeId'), 
                            datafileMS.getValue(GlobalVariable.NumofMS, 28))

                        'click button search'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_Search'))

                        'verify input error'
                        if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/a_Select'), 
                            GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                            'click select'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/a_Select'))
                        } else {
                            'click X'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_X'))

                            (GlobalVariable.FlagWarning)++

                            continue
                        }
                        
                        'input establishment date'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Establishment Date_form-control ng-untouched ng-pristine ng-invalid'), 
                            datafileMS.getValue(GlobalVariable.NumofMS, 30))

                        'click button lookup job data'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_Job Position_btn btn-raised btn-primary'))

                        'input job data code'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Job Position Code_JobCodeId'), 
                            datafileMS.getValue(GlobalVariable.NumofMS, 37))

                        'click button search'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_Search'))

                        'verify input error'
                        if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/a_Select'), 
                            GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                            'click select'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/a_Select'))
                        } else {
                            'click X'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_X'))

                            (GlobalVariable.FlagWarning)++

                            continue
                        }
                        
                        'input share'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Share()_form-control ng-untouched ng-pristine ng-valid'), 
                            datafileMS.getValue(GlobalVariable.NumofMS, 39))

                        if (WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Owner_ng-untouched ng-pristine ng-valid'), 
                            GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                            if (datafileMS.getValue(GlobalVariable.NumofMS, 40).equalsIgnoreCase('Yes')) {
                                'click check box is Owner'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Owner_ng-untouched ng-pristine ng-valid'))
                            }
                        } else if (WebUI.verifyElementChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Owner_ng-untouched ng-pristine ng-valid'), 
                            GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                            if (datafileMS.getValue(GlobalVariable.NumofMS, 40).equalsIgnoreCase('No')) {
                                'click check box is Owner'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Owner_ng-untouched ng-pristine ng-valid'))
                            }
                        }
                        
                        if (WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Active_ng-untouched ng-pristine ng-valid'), 
                            GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                            if (datafileMS.getValue(GlobalVariable.NumofMS, 41).equalsIgnoreCase('Yes')) {
                                'click check box is Active'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Active_ng-untouched ng-pristine ng-valid'))
                            }
                        } else if (WebUI.verifyElementChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Active_ng-untouched ng-pristine ng-valid'), 
                            GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                            if (datafileMS.getValue(GlobalVariable.NumofMS, 41).equalsIgnoreCase('No')) {
                                'click check box is Active'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Active_ng-untouched ng-pristine ng-valid'))
                            }
                        }
                        
                        if (WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Signer_ng-untouched ng-pristine ng-valid'), 
                            GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                            if (datafileMS.getValue(GlobalVariable.NumofMS, 42).equalsIgnoreCase('Yes')) {
                                'click check box is Signer'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Signer_ng-untouched ng-pristine ng-valid'))
                            }
                        } else if (WebUI.verifyElementChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Signer_ng-untouched ng-pristine ng-valid'), 
                            GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                            if (datafileMS.getValue(GlobalVariable.NumofMS, 42).equalsIgnoreCase('No')) {
                                'click check box is Signer'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Signer_ng-untouched ng-pristine ng-valid'))
                            }
                        }
                        
                        if (datafileMS.getValue(GlobalVariable.NumofMS, 44).length() > 1) {
                            'click button lookup department AML'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_DEPARTMENT AML_btn btn-raised btn-primary'))

                            'input department AML Code'
                            WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Code_MasterCodeId'), 
                                datafileMS.getValue(GlobalVariable.NumofMS, 44))

                            'click button search'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_Search'))

                            'verify input error'
                            if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/a_Select'), 
                                GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                                'click select'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/a_Select'))
                            } else {
                                'click X'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_X'))

                                (GlobalVariable.FlagWarning)++

                                continue
                            }
                        }
                    
                    
                    'call function untuk get data MS yang sudah di input untuk verif Store DB'
                    getData()
                
                }
                
                    'get MS Personal name'
                    custname = WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Shareholder Legal Name_form-control ng-untouched ng-pristine ng-invalid'), 
                        'value', FailureHandling.OPTIONAL)

                    'add name to Global variable'
                    GlobalVariable.CustomerName = ((GlobalVariable.CustomerName + ';') + custname)
                 
				               
                'click button save'
                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_Save'))

                if ((Integer.parseInt(datafileMS.getValue(GlobalVariable.NumofMS, 4)) == 0) && (GlobalVariable.FlagFailed == 
                0)) {
                    'Check alert'
                    GlobalVariable.FlagFailed = CustomKeywords.'checkSaveProcess.checkSaveProcess.checkAlert'(GlobalVariable.NumofMS, 
                        '2.TabManagementShareholderData')
                }
                
                    if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/button_Cancel'), 
                        GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                        'click button cancel'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/button_Cancel'))

                        'customer added -1'
                        (GlobalVariable.CountNumofCustomer)--
                    } else {
                        if (GlobalVariable.FlagWarning > 0) {
                            CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '2.TabManagementShareholderData', 
                                0, GlobalVariable.NumofMS - 1, GlobalVariable.StatusWarning)
                        }
                    }
                
                'verify flagfailed lookup == 0 '
                if (GlobalVariable.FlagFailed == 0) {
                    'Check save Process write to excel'
                    CustomKeywords.'checkSaveProcess.checkSaveProcess.checkStatus'(Integer.parseInt(datafileMS.getValue(
                                GlobalVariable.NumofMS, 4)), findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Tableheadermanagementshareholder'), 
                        GlobalVariable.NumofMS, '2.TabManagementShareholderData')

                    'customer added +1'
                    (GlobalVariable.CountNumofCustomer)++

                    if (Integer.parseInt(datafileMS.getValue(GlobalVariable.NumofMS, 4)) == 0) {
                        'Check error validasi'
                        CustomKeywords.'checkSaveProcess.checkSaveProcess.checkValidasi'(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/errorvalidasi'), 
                            GlobalVariable.NumofMS, '2.TabManagementShareholderData')
                    }
                    
					if (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabManagementShareholder').getValue(GlobalVariable.NumofMS, 1) == 'SUCCESS') {
                    if (datafileMS.getValue(GlobalVariable.NumofMS, 13) == 'Input Data') {
                        if ((GlobalVariable.RoleCompany == 'Testing') && (GlobalVariable.CheckVerifStoreDBCompany == 'Yes')) {
                                'call test case MS Personal data store verif'
                                WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP1 - CustomerData/ManagementShareholder/TabMSPersonalDataStoreDBVerif'), 
                                    [:], FailureHandling.CONTINUE_ON_FAILURE)
                        }
                    } else {
                        if ((GlobalVariable.RoleCompany == 'Testing') && (GlobalVariable.CheckVerifStoreDBCompany == 'Yes')) {
                                'call test case MS personal data store verif'
                                WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP1 - CustomerData/ManagementShareholder/TabMSPersonalDataStoreDBVerif-LookUp'), 
                                    [:], FailureHandling.CONTINUE_ON_FAILURE)                            
                        }
                    }
					}
                }
                
                if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/button_Cancel'), 
                		GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                	'click button cancel'
                	WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/button_Cancel'))
                	
                	'customer added -1'
                	(GlobalVariable.CountNumofCustomer)--
                } else {
                	if (GlobalVariable.FlagWarning > 0) {
                		CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '2.TabManagementShareholderData', 
                				0, GlobalVariable.NumofMS - 1, GlobalVariable.StatusWarning)
                	}
                }
					break			
				}
        }
        }
    }else{
	break
    }
}


def getDataMS() {
    'declare array for confins data'
    def confinsdata = []

    if (datafileMS.getValue(GlobalVariable.NumofMS, 14).equalsIgnoreCase('Personal')) {
	
		if(datafileMS.getValue(GlobalVariable.NumofMS, 13) == 'LookUp'){
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
		}
		
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
		
		if(datafileMS.getValue(GlobalVariable.NumofMS, 13) == 'Input Data'){
			
			Select select = new Select(DriverFactory.getWebDriver().findElement(By.xpath('//*[@id="Address"]/div/div[2]/div[2]/div/div/div/div/select')))
			String optionLabel = select.getFirstSelectedOption().getText()
			
			'add ownership to array'
			confinsdata.add(optionLabel)
		}
    }
    
    GlobalVariable.Confinsdata = confinsdata
}

def getData() {
    'check if role testing dan input dengan lookup maka get data dari confins untuk verif store DB'
    if ((GlobalVariable.RoleCompany == 'Testing') && datafileMS.getValue(GlobalVariable.NumofMS, 13).equalsIgnoreCase('LookUp')) {
       if (datafileMS.getValue(GlobalVariable.NumofMS, 14).equalsIgnoreCase('Personal')) {
            'call function MS get data'
            getDataMS()

            Select selectIdType = new Select(DriverFactory.getWebDriver().findElement(By.xpath('//*[@id="CustMainData"]/div[2]/div[3]/div/div/lib-ucdropdownlist/div/select')))

            'add ID TYPE array'
            GlobalVariable.Confinsdata.add(selectIdType.getFirstSelectedOption().getText())

            Select selectmaritalstatus = new Select(DriverFactory.getWebDriver().findElement(By.xpath('//*[@id="CustMainData"]/div[2]/div[7]/div/div/lib-ucdropdownlist/div/select')))

            'add marital status array'
            GlobalVariable.Confinsdata.add(selectmaritalstatus.getFirstSelectedOption().getText())

            Select selectGender = new Select(DriverFactory.getWebDriver().findElement(By.xpath('//*[@id="CustMainData"]/div[1]/div[2]/div/div/lib-ucdropdownlist/div/select')))

            Select selectcustmodel = new Select(DriverFactory.getWebDriver().findElement(By.xpath('//*[@id="CustMainData"]/div[2]/div[11]/div[1]/div/lib-ucdropdownlist/div/select')))

            'add customer model array'
            GlobalVariable.Confinsdata.add(selectcustmodel.getFirstSelectedOption().getText())

            'add proffesion to array'
            GlobalVariable.Confinsdata.add(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/labelProffesion'), 
                    'value'))

            'add position SLIK to array'
            GlobalVariable.Confinsdata.add(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/label_positionSLIK'), 
                    'value'))

            'add establishment date to array'
            GlobalVariable.Confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Establishment Date_form-control ng-untouched ng-pristine ng-invalid'), 
                    'value'))

            'add Gender array'
            GlobalVariable.Confinsdata.add(selectGender.getFirstSelectedOption().getText())

            'add job position array'
            GlobalVariable.Confinsdata.add(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/labelJobposition'), 
                    'value'))

            'add share to array'
            GlobalVariable.Confinsdata.add(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Share()_form-control ng-untouched ng-pristine ng-valid'), 
                    'value').replace(' %', ''))

            if (WebUI.verifyElementChecked(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Active_ng-untouched ng-pristine ng-valid'), 
                GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                'add is active to array'
                GlobalVariable.Confinsdata.add('Yes')
            } else {
                'add is active to array'
                GlobalVariable.Confinsdata.add('No')
            }
            
            if (WebUI.verifyElementChecked(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Active_ng-untouched ng-pristine ng-valid'), 
                GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                'add is owner to array'
                GlobalVariable.Confinsdata.add('Yes')
            } else {
                'add is owner to array'
                GlobalVariable.Confinsdata.add('No')
            }
            
            if (WebUI.verifyElementChecked(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Signer_ng-untouched ng-pristine ng-valid'), 
                GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                'add is signer to array'
                GlobalVariable.Confinsdata.add('Yes')
            } else {
                'add is signer to array'
                GlobalVariable.Confinsdata.add('No')
            }
            
            'add department aml to array'
            GlobalVariable.Confinsdata.add(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/labelDepartmnetAML'), 
                    'value'))

            'add authority aml to array'
            GlobalVariable.Confinsdata.add(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/labelAuthorityAML'), 
                    'value'))

            Select selectownership = new Select(DriverFactory.getWebDriver().findElement(By.xpath('//*[@id="Address"]/div/div[2]/div[2]/div/div/div/div/select')))

            'add ownership to array'
            GlobalVariable.Confinsdata.add(selectownership.getFirstSelectedOption().getText())
        }
    }
}

def checkVerifyEqualOrMatch(Boolean isMatch) {
    if ((isMatch == false) && (GlobalVariable.FlagFailed == 0)) {

		'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedVerifyEqualOrMatch'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('2.TabManagementShareholderData', GlobalVariable.NumofMS, GlobalVariable.StatusFailed, GlobalVariable.ReasonFailedVerifyEqualOrMatch)

        GlobalVariable.FlagFailed = 1
    }
}

def checkDDL(){
	if(GlobalVariable.RoleCompany == 'Testing'){
		
		'connect DB FOU'
		Sql sqlConnectionFOU = CustomKeywords.'dbConnection.connectDB.connectFOU'()
				
				'get IDType ddl value from db'
				ArrayList<String> IDType = CustomKeywords.'dbConnection.checkCustomer.checkIDTypeDDL'(sqlConnectionFOU)
				
				'get total label from ddl IDType'
				int totalddlIDType = WebUI.getNumberOfTotalOption(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/select_Select One AKTA  E-KTP  KARTU TANDA MAHASISWA  KITAS  NPWP  SIM'))
				
				'verify total ddl IDType confins = total ddl db'
				WebUI.verifyEqual(totalddlIDType - 1, IDType.size())
				
				'verify isi ddl IDType confins = db'
				if (WebUI.verifyOptionsPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/select_Select One AKTA  E-KTP  KARTU TANDA MAHASISWA  KITAS  NPWP  SIM'),
						IDType) == false) {
					
					'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDDL'
					CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('1.TabCustomerMainData', GlobalVariable.NumofColm, GlobalVariable.StatusFailed, GlobalVariable.ReasonFailedDDL + 'ID Type')
					
					(GlobalVariable.FlagFailed)++
				}
		
		'get MaritalStatus ddl value from db'
		ArrayList<String> MaritalStatus = CustomKeywords.'dbConnection.checkCustomer.checkMaritalStatusDLL'(sqlConnectionFOU)
				
				'get total label from ddl MaritalStatus'
				int totalddlMaritalStatus = WebUI.getNumberOfTotalOption(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/select_Select One Married  Single  Widow'))
				
				'verify total ddl MaritalStatus confins = total ddl db'
				WebUI.verifyEqual(totalddlMaritalStatus - 1, MaritalStatus.size())
				
				'verify isi ddl MaritalStatus confins = db'
				if (WebUI.verifyOptionsPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/select_Select One Married  Single  Widow'),
						MaritalStatus) == false) {
					
					'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDDL'
					CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('1.TabCustomerMainData', GlobalVariable.NumofColm, GlobalVariable.StatusFailed, GlobalVariable.ReasonFailedDDL + 'MaritalStatus')
					
					(GlobalVariable.FlagFailed)++
				}
		
		'get CustModel ddl value from db'
		ArrayList<String> CustModel = CustomKeywords.'dbConnection.checkCustomer.checkCustomerModelPersonal'(sqlConnectionFOU)
				
				'get total label from ddl CustModel'
				int totalddlCustModel = WebUI.getNumberOfTotalOption(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/select_Select One Employee  Non Professional  Professional  Small Medium Enterprise'))
				
				'verify total ddl CustModel confins = total ddl db'
				WebUI.verifyEqual(totalddlCustModel - 1, CustModel.size())
				
				'verify isi ddl CustModel confins = db'
				if (WebUI.verifyOptionsPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/select_Select One Employee  Non Professional  Professional  Small Medium Enterprise'),
						CustModel) == false) {
					
					'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDDL'
					CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('1.TabCustomerMainData', GlobalVariable.NumofColm, GlobalVariable.StatusFailed, GlobalVariable.ReasonFailedDDL + 'CustModel')
					
					(GlobalVariable.FlagFailed)++
				}
		
		'get Gender ddl value from db'
		ArrayList<String> Gender = CustomKeywords.'dbConnection.checkCustomer.checkGenderDLLPersonal'(sqlConnectionFOU)
				
				'get total label from ddl Gender'
				int totalddlGender = WebUI.getNumberOfTotalOption(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/select_Select One Female  Male'))
				
				'verify total ddl Gender confins = total ddl db'
				WebUI.verifyEqual(totalddlGender - 1, Gender.size())
				
				'verify isi ddl Gender confins = db'
				if (WebUI.verifyOptionsPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/select_Select One Female  Male'),
						Gender) == false) {
					
					'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDDL'
					CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('1.TabCustomerMainData', GlobalVariable.NumofColm, GlobalVariable.StatusFailed, GlobalVariable.ReasonFailedDDL + 'Gender')
					
					(GlobalVariable.FlagFailed)++
				}
	}
	
}