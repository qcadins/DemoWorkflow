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

GlobalVariable.FlagWarning = 0

'get data file path'
GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'(GlobalVariable.PathCompany)

'declare datafileCustomerCompany'
datafileCustomerCompany = findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData')

'declare datafileGuarantorPersonal'
datafileGuarantorPersonal = findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal')

'get copyapp status dari data file/excel'
copyapp = datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 10)

countGuarantorPersonal = datafileGuarantorPersonal.getColumnNumbers()

'declare variableData'
ArrayList<String> variableData

if (GlobalVariable.RoleCompany == 'Testing') {
    'verify application step'
    WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/applicationcurrentstep')), 
        'GUARANTOR', false, FailureHandling.OPTIONAL)
}

'Loop Multiple Guarantor Data'
for (GlobalVariable.NumofGuarantorPersonal = GlobalVariable.StartIndex; GlobalVariable.NumofGuarantorPersonal <= (countGuarantorPersonal - 1); (GlobalVariable.NumofGuarantorPersonal)++) {
    GlobalVariable.FlagFailed = 0

    if (datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 12) == datafileCustomerCompany.getValue(
        GlobalVariable.NumofColm, 13)) {
        if (WebUI.verifyElementPresent(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/label_NO DATA AVAILABLE'), 
            GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
            'count table guarantor pada confins'
            variableData = DriverFactory.getWebDriver().findElements(By.cssSelector('#guarantor-tab > app-guarantor-main-data-paging > div > div:nth-child(2) > table > tbody tr'))
        } else {
            'count table guarantor pada confins'
            variableData = DriverFactory.getWebDriver().findElements(By.cssSelector('#guarantor-tab > app-guarantor-main-data-paging > div > div:nth-child(2) > lib-ucgridview > div > table > tbody tr'))
        }
        
        for (int i = 1; i <= variableData.size(); i++) {
            'modify object guarantor name'
            modifyNewGuarantorName = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/FromTypeName'), 
                'xpath', 'equals', ('//*[@id="guarantor-tab"]/app-guarantor-main-data-paging/div/div[2]/lib-ucgridview/div/table/tbody/tr[' + 
                i) + ']/td[2]', true)

            'verify if copy app edit'
            if (copyapp.equalsIgnoreCase('Edit')) {
                if (WebUI.verifyElementPresent(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/label_NO DATA AVAILABLE'), 
                    GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                    copyapp == 'No'
                } else if (WebUI.verifyElementPresent(modifyNewGuarantorName, GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                    if (WebUI.getText(modifyNewGuarantorName).equalsIgnoreCase(datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                            19)) || WebUI.getText(modifyNewGuarantorName).equalsIgnoreCase(datafileGuarantorPersonal.getValue(
                            GlobalVariable.NumofGuarantorPersonal, 16))) {
                        break
                    }
                }
            }
            
            if ((i == variableData.size()) || (copyapp == 'No')) {
                if (WebUI.verifyElementPresent(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_3Guarantor Data'), 
                    GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                    'click button Add'
                    WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_Add'))

                    'click radio personal'
                    WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/Radio Personal'))

                    'chcek if indput data / lookup'
                    if (datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 13) == 'Input Data') {
                        'select guarantor relationship'
                        WebUI.selectOptionByLabel(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/select_GuarantorRelationship'), 
                            datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 18), false)

                        'input guarantor name'
                        WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Guarantor Legal Name'), 
                            datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 19))

                        'input birth place'
                        WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Birth Place'), 
                            datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 20))

                        'select id type'
                        WebUI.selectOptionByLabel(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/select_IDType'), 
                            datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 21), false)

                        'Verify ID Type'
                        if (((datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 21) != 'E-KTP') && 
                        (datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 21) != 'NPWP')) && (datafileGuarantorPersonal.getValue(
                            GlobalVariable.NumofGuarantorPersonal, 21) != 'AKTA')) {
                            'input id expired date'
                            WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Id Expired Date'), 
                                datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 22))
                        }
                        
                        'select marital status'
                        WebUI.selectOptionByLabel(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/select_MaritalStatus'), 
                            datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 23), false)

                        'input mobile phone'
                        WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Mobile Phone'), 
                            datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 24))

                        'select customer model'
                        WebUI.selectOptionByLabel(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/select_CustomerModel'), 
                            datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 25), false)

                        'select gender'
                        WebUI.selectOptionByLabel(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/select_Select One Female  Male'), 
                            datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 26), false)

                        'input birth date'
                        WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Birth Date_form-control ng-untouched ng-pristine ng-invalid'), 
                            datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 27))

                        'input id no'
                        WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Id No'), 
                            datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 28))

                        'input tax id'
                        WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Tax Id No'), 
                            datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 29))

                        'input mother maiden'
                        WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Mother Maiden Name'), 
                            datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 30))

                        'input email'
                        WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Email'), 
                            datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 31))

                        'click button lookup department AML'
                        WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_DEPARTMENT AML_btn btn-raised btn-primary'))

                        'input department AML Code'
                        WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Code_MasterCodeId'), 
                            datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 33))

                        'click button search'
                        WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_Search'))

                        'verify input error'
                        if (WebUI.verifyElementPresent(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/a_Select'), 
                            GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                            'click select'
                            WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/a_Select'))
                        } else {
                            'click X'
                            WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/Button_X'))

                            'click button cancel'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/button_Cancel'))

							'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.StatusReasonLookup'
							CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('3a.TabGuarantorDataPersonal', GlobalVariable.NumofGuarantorPersonal, GlobalVariable.StatusFailed, GlobalVariable.StatusReasonLookup)

                            'Flagfailed +1 karena lookup gagal'
                            (GlobalVariable.FlagFailed)++

                            continue
                        }
                        
                        if (datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 36).length() > 1) {
                            'click button lookup authority AML'
                            WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_Authority AML_btn btn-raised btn-primary'))

                            'input authority AML code'
                            WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Code_MasterCodeId'), 
                                datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 35))

                            'click button search'
                            WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_Search'))

                            'verify input error'
                            verifyInputError(flagWarning)
                        }
                        
                        if (datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 38) == 'Yes') {
                            'click button copy'
                            WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_Copy'))
							
							'call function get data guarantor'
							getDataGuarPersonal()
                        } else {
                            'input text address'
                            WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/textarea_Address'), 
                                datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 39))

                            'input RT'
                            WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_RT'), 
                                datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 40))

                            'input RW'
                            WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_RW'), 
                                datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 41))

                            'click button zipcode'
                            WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_Zipcode_btn btn-raised btn-primary'))

                            'input zipcode'
                            WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Zipcode'), 
                                datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 42))

                            'input Kecamatan'
                            WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input Kecamatan'), 
                                datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 43))

                            'input Kelurahan'
                            WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input kelurahan'), 
                                datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 44))

                            'input kota'
                            WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input kota'), 
                                datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 45))

                            'click button search zipcode'
                            WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_SearchZipcode'))

                            'verify input error'
                            if (WebUI.verifyElementPresent(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/a_Select'), 
                                GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                                'click select'
                                WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/a_Select'))
                            } else {
                                'click X'
                                WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/Button_X'))

                                'click button cancel'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/button_Cancel'))

								'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.StatusReasonLookup'
								CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('3a.TabGuarantorDataPersonal', GlobalVariable.NumofGuarantorPersonal, GlobalVariable.StatusFailed, GlobalVariable.StatusReasonLookup)

                                'Flagfailed +1 karena lookup gagal'
                                (GlobalVariable.FlagFailed)++

                                continue
                            }
                            
                            'select ownership'
                            WebUI.selectOptionByLabel(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/select_Ownership'), 
                                datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 46), false)
                        }
                    } else if (datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 13) == 'LookUp') {
                        if (WebUI.verifyElementPresent(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_3Guarantor Data'), 
                            GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                            'click guarantor lookup'
                            WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_Guarantor Legal Name_btn btn-raised btn-primary'))

                            'input customer no'
                            WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Customer No_custNoId'), 
                                datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 15))

                            'input customer name'
                            WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Customer Name_custNameId'), 
                                datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 16))

                            'input id no'
                            WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Id No_IdNoId'), 
                                datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 17))

                            'click search'
                            WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_Search'))

                            'verify input error'
                            if (WebUI.verifyElementPresent(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/a_Select'), 
                                GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                                'click select'
                                WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/a_Select'))
                            } else {
                                'click X'
                                WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/Button_X'))

                                'click button cancel'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/button_Cancel'))

								'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.StatusReasonLookup'
								CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('3a.TabGuarantorDataPersonal', GlobalVariable.NumofGuarantorPersonal, GlobalVariable.StatusFailed, GlobalVariable.StatusReasonLookup)

                                'Flagfailed +1 karena lookup gagal'
                                (GlobalVariable.FlagFailed)++

                                continue
                            }
                            
                            'check if role testing untuk get data customer digunakan untuk data verif'
                            if (GlobalVariable.RoleCompany == 'Testing') {
                                'call function get data guarantor'
                                getDataGuarPersonal()

                                'call test case Personal data verif'
                                WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP1 - CustomerData/Guarantor/TabGuarantorPersonalDataVerif'), 
                                    [:], FailureHandling.CONTINUE_ON_FAILURE)
                            }
                            
                            'select guarantor relationship'
                            WebUI.selectOptionByLabel(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/select_GuarantorRelationship'), 
                                datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 18), false)

                            if (datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 25).length() > 
                            1) {
                                'select customer model'
                                WebUI.selectOptionByLabel(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/select_CustomerModel'), 
                                    datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 25), false)
                            }
                            
                            if (datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 34).length() > 
                            1) {
                                'click lookup department AML'
                                WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_DEPARTMENT AML_btn btn-raised btn-primary'))

                                'input department AML Code'
                                WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Code_MasterCodeId'), 
                                    datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 33))

                                'click button search'
                                WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_Search'))

                                'verify input error'
                                if (WebUI.verifyElementPresent(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/a_Select'), 
                                    GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                                    'click select'
                                    WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/a_Select'))
                                } else {
                                    'click X'
                                    WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/Button_X'))

                                    'click button cancel'
                                    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/button_Cancel'))

									'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.StatusReasonLookup'
									CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('3a.TabGuarantorDataPersonal', GlobalVariable.NumofGuarantorPersonal, GlobalVariable.StatusFailed, GlobalVariable.StatusReasonLookup)

                                    'Flagfailed +1 karena lookup gagal'
                                    (GlobalVariable.FlagFailed)++

                                    continue
                                }
                            }
                            
                            if (datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 36).length() > 
                            1) {
                                'click button lookup authority AML'
                                WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_Authority AML_btn btn-raised btn-primary'))

                                'input authority AML code'
                                WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Code_MasterCodeId'), 
                                    datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 35))

                                'click button search'
                                WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_Search'))

                                'verify input error'
                                verifyInputError(flagWarning)
                            }
                        }
                    }
                    
                    'check if role testing dan diinput dengan lookup untuk get data customer digunakan untuk Store DB Verif'
                    if ((GlobalVariable.RoleCompany == 'Testing') && (datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        13) == 'LookUp')) {
                        'call function get data guar personal'
                        getDataGuarPersonal()

                        'add relationship to array'
                        GlobalVariable.Confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/select_GuarantorRelationship'), 
                                'value'))
                    }
                    
                    'get customer name'
                    custname = WebUI.getAttribute(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Guarantor Legal Name'), 
                        'value', FailureHandling.OPTIONAL)

                    'add name to Global variable'
                    GlobalVariable.CustomerName = ((GlobalVariable.CustomerName + ';') + custname)

                    'click button save'
                    WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_Save'))

                    if ((Integer.parseInt(datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 4)) == 
                    0) && (GlobalVariable.FlagFailed == 0)) {
                        'Check alert'
                        GlobalVariable.FlagFailed = CustomKeywords.'checkSaveProcess.checkSaveProcess.checkAlert'(GlobalVariable.NumofGuarantorPersonal, 
                            '3a.TabGuarantorDataPersonal')
                    }
                    
                    if (GlobalVariable.FlagFailed == 0) {
                        'Check save Process write to excel'
                        CustomKeywords.'checkSaveProcess.checkSaveProcess.checkStatus'(Integer.parseInt(datafileGuarantorPersonal.getValue(
                                    GlobalVariable.NumofGuarantorPersonal, 4)), findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/TableheaderGuarantor'), 
                            GlobalVariable.NumofGuarantorPersonal, '3a.TabGuarantorDataPersonal')

                        'customer added +1'
                        (GlobalVariable.CountNumofCustomer)++

                        if (Integer.parseInt(datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 4)) == 
                        0) {
                            'Check error validasi'
                            CustomKeywords.'checkSaveProcess.checkSaveProcess.checkValidasi'(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/errorvalidasi'), 
                                GlobalVariable.NumofGuarantorPersonal, '3a.TabGuarantorDataPersonal')
                        }
                    }
                    
                    if (WebUI.verifyElementPresent(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/button_Cancel'), 
                        GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                        'click button cancel'
                        WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/button_Cancel'))

                        'customer added -1'
                        (GlobalVariable.CountNumofCustomer)--
                    } else {
                        if ((flagWarning > 0) || (GlobalVariable.FlagWarning > 0)) {
                            CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '3a.TabGuarantorDataPersonal', 
                                0, GlobalVariable.NumofGuarantorPersonal - 1, GlobalVariable.StatusWarning)
                        }
                    }
                    
                    'check if input data / lookup'
                    if (datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 13) == 'Input Data') {
                        if ((GlobalVariable.RoleCompany == 'Testing') && (GlobalVariable.CheckVerifStoreDBCompany == 'Yes')) {
                            'call test case Guarantor personal data verif'
                            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP1 - CustomerData/Guarantor/TabGuarantorDataPersonalStoreDBVerif'), 
                                [:], FailureHandling.CONTINUE_ON_FAILURE)
                        }
                    } else if (datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 13) == 'LookUp') {
                        if ((GlobalVariable.RoleCompany == 'Testing') && (GlobalVariable.CheckVerifStoreDBCompany == 'Yes')) {
                            'call test case Guarantor personal data verif'
                            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP1 - CustomerData/Guarantor/TabGuarantorDataPersonalStoreDBVerif-LookUp'), 
                                [:], FailureHandling.CONTINUE_ON_FAILURE)
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

def getDataGuarPersonal() {
    'declare array for confins data'
    def confinsdata = []

	if(datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal,13) == 'LookUp'){
	    'add customer name to array'
	    confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Guarantor Legal Name'), 
	            'value'))
	
	    'add birth place to array'
	    confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Birth Place'), 
	            'value'))
	
	    'add id expired date to array'
	    confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Id Expired Date'), 
	            'value'))
	
	    'add mobile phone to array'
	    confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Mobile Phone'), 
	            'value'))
	
	    'add birth date to array'
	    confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Birth Date_form-control ng-untouched ng-pristine ng-invalid'), 
	            'value'))
	
	    'add id no to array'
	    confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Id No'), 
	            'value'))
	
	    'add tax id to array'
	    confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Tax Id No'), 
	            'value'))
	
	    'add mother maiden name to array'
	    confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Mother Maiden Name'), 
	            'value'))
	
	    'add email to array'
	    confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Email'), 
	            'value'))
	}
	
    'add address to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/textarea_Address'), 
            'value'))

    'add RT to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_RT'), 
            'value'))

    'add RW to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_RW'), 
            'value'))

    'add zipcode to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/LabelZipcode'), 
            'value'))

    'add kelurahan to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/LabelKelurahan'), 
            'value'))

    'add kecamatan to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/LabelKecamatan'), 
            'value'))

    'add kota to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/LabelKota'), 
            'value'))

	if(datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal,13) == 'LookUp'){
	    'add id type to array'
	    confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/select_IDType'), 
	            'value'))
	
	    'add marital status to array'
	    confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/select_MaritalStatus'), 
	            'value'))
	
	    'add customer model to array'
	    confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/select_CustomerModel'), 
	            'value'))
	
	    'add gender to array'
	    confinsdata.add(WebUI.getAttribute(findTestObject('Object Repository/NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/select_Select One Female  Male'), 
	            'value'))
	
	    'add ownership to array'
	    confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/select_Ownership'), 
	            'value'))
	}else if(datafileGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorCompany, 13) == 'Input Data'){
		Select select = new Select(DriverFactory.getWebDriver().findElement(By.xpath("//div[@id='Address']/div/div[2]/div[2]/div/div/div/div/select")))
		String optionLabel = select.getFirstSelectedOption().getText()
		
		'add ownership to array'
		confinsdata.add(optionLabel)
	}
	
    GlobalVariable.Confinsdata = confinsdata
}

def verifyInputError(int flagWarning) {
    'verify input error'
    if (WebUI.verifyElementPresent(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/a_Select'), 
        GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
        'click select'
        WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/a_Select'))
    } else {
        'click X'
        WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/Button_X'))

        flagWarning++
    }
}
