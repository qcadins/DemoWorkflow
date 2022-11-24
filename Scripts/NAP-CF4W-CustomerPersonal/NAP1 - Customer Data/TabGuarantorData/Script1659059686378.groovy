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

'get data file path'
GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'(GlobalVariable.PathPersonal)

'declare datafileCustomerPersonal'
datafileCustomerPersonal = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData')

'declare datafileTabGuarantorPersonal'
datafileTabGuarantorPersonal = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabGuarantorDataPersonal')

'declare datafileTabGuarantorCompany'
datafileTabGuarantorCompany = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabGuarantorDataCompany')

copyapp = datafileCustomerPersonal.getValue(GlobalVariable.NumofColm, 10)

ArrayList<String> variableData

if (GlobalVariable.Role == 'Testing') {
    'verify application step'
    WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/applicationcurrentstep')), 
        'GUARANTOR', false, FailureHandling.OPTIONAL)
}

'Loop Multiple Guarantor Data'
for (GlobalVariable.NumofGuarantorPersonal = 2; GlobalVariable.NumofGuarantorPersonal <= (Integer.parseInt(GlobalVariable.CountAGuarantorPersonal) + 
1); (GlobalVariable.NumofGuarantorPersonal)++) {
    GlobalVariable.FlagFailed = 0

    if (datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 12) == datafileCustomerPersonal.getValue(
        GlobalVariable.NumofColm, 13)) {
        if (WebUI.verifyElementPresent(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/label_NO DATA AVAILABLE'), 
            5, FailureHandling.OPTIONAL)) {
            'count table guarantor pada confins'
            variableData = DriverFactory.getWebDriver().findElements(By.cssSelector('#guarantor-tab > app-guarantor-main-data-paging > div > div:nth-child(2) > table > tbody tr'))
        } else {
            'count table guarantor pada confins'
            variableData = DriverFactory.getWebDriver().findElements(By.cssSelector('#guarantor-tab > app-guarantor-main-data-paging > div > div:nth-child(2) > lib-ucgridview > div > table > tbody tr'))
        }
        
        for (int i = 1; i <= variableData.size(); i++) {
            'modify object guarantor name'
            modifyNewGuarantorName = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/FromTypeName'), 
                'xpath', 'equals', ('//*[@id="guarantor-tab"]/app-guarantor-main-data-paging/div/div[2]/lib-ucgridview/div/table/tbody/tr[' + 
                i) + ']/td[2]', true)

            'verify if copy app edit'
            if (copyapp.equalsIgnoreCase('Edit')) {
                if (WebUI.verifyElementPresent(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/label_NO DATA AVAILABLE'), 
                    5, FailureHandling.OPTIONAL)) {
                    copyapp == 'No'
                } else if (WebUI.verifyElementPresent(modifyNewGuarantorName, 5, FailureHandling.OPTIONAL)) {
                    if (WebUI.getText(modifyNewGuarantorName).equalsIgnoreCase(datafileTabGuarantorPersonal.getValue(
                            GlobalVariable.NumofGuarantorPersonal, 19)) || WebUI.getText(modifyNewGuarantorName).equalsIgnoreCase(
                        datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 16))) {
                        break
                    }
                }
            }
            
            if ((i == variableData.size()) || (copyapp == 'No')) {
                if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_3Guarantor Data'), 
                    5, FailureHandling.OPTIONAL)) {
                    'click button Add'
                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_Add'))

                    'click radio personal'
                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/Radio Personal'))

                    if (datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 13) == 
                    'Input Data') {
                        'select guarantor relationship'
                        WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/select_GuarantorRelationship'), 
                            datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                                18), false)

                        'input guarantor name'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Guarantor Legal Name'), 
                            datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                                19))

                        'input birth place'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Birth Place'), 
                            datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                                20))

                        'select id type'
                        WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/select_IDType'), 
                            datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                                21), false)

                        'Verify ID Type'
                        if (((datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                            21) != 'E-KTP') && (datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                            21) != 'NPWP')) && (datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                            21) != 'AKTA')) {
                            'input id expired date'
                            WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Id Expired Date'), 
                                datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                                    22))
                        }
                        
                        'select marital status'
                        WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/select_MaritalStatus'), 
                            datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                                23), false)

                        'input mobile phone'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Mobile Phone'), 
                            datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                                24))

                        'select customer model'
                        WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/select_CustomerModel'), 
                            datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                                25), false)

                        'select gender'
                        WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/select_Select One Female  Male'), 
                            datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                                26), false)

                        'input birth date'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Birth Date_form-control ng-untouched ng-pristine ng-invalid'), 
                            datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                                27))

                        'input id no'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Id No'), 
                            datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                                28))

                        'input tax id'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Tax Id No'), 
                            datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                                29))

                        'input mother maiden'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Mother Maiden Name'), 
                            datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                                30))

                        'input email'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Email'), 
                            datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                                31))

                        'click button lookup department AML'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_DEPARTMENT AML_btn btn-raised btn-primary'))

                        'input department AML Code'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Code_MasterCodeId'), 
                            datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                                33))

                        'click button search'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_Search'))

                        'verify input error'
                        if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/a_Select'), 
                            10, FailureHandling.OPTIONAL)) {
                            'click select'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/a_Select'))
                        } else {
                            'click X'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabNewApplication/Button_X'))

                            'click button cancel'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_Cancel'))

                            'write to excel failed'
                            CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '3a.TabGuarantorDataPersonal', 
                                0, GlobalVariable.NumofGuarantorPersonal - 1, GlobalVariable.StatusFailed)

                            'write to excel reason lookup'
                            CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '3a.TabGuarantorDataPersonal', 
                                1, GlobalVariable.NumofGuarantorPersonal - 1, GlobalVariable.StatusReasonLookup)

                            GlobalVariable.FlagFailed = 1

                            continue
                        }
                        
                        if (datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                            36).length() > 1) {
                            'click button lookup authority AML'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_Authority AML_btn btn-raised btn-primary'))

                            'input authority AML code'
                            WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Code_MasterCodeId'), 
                                datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                                    35))

                            'click button search'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_Search'))

                            'verify input error'
                            verifyInputError(flagWarning)
                        }
                        
                        if (datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                            38) == 'Yes') {
                            'click button copy'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_Copy'))
                        } else if (datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                            38) == 'No') {
                            'input text address'
                            WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/textarea_Address'), 
                                datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                                    39))

                            'input RT'
                            WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_RT'), 
                                datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                                    40))

                            'input RW'
                            WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_RW'), 
                                datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                                    41))

                            'click button zipcode'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_Zipcode_btn btn-raised btn-primary'))

                            'input zipcode'
                            WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Zipcode'), 
                                datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                                    42))

                            'input Kecamatan'
                            WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input Kecamatan'), 
                                datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                                    43))

                            'input Kelurahan'
                            WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input kelurahan'), 
                                datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                                    44))

                            'input kota'
                            WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input kota'), 
                                datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                                    45))

                            'click button search zipcode'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_SearchZipcode'))

                            'verify input error'
                            if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/a_Select'), 
                                10, FailureHandling.OPTIONAL)) {
                                'click select'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/a_Select'))
                            } else {
                                'click X'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabNewApplication/Button_X'))

                                'click button cancel'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_Cancel'))

                                'write to excel failed'
                                CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '3a.TabGuarantorDataPersonal', 
                                    0, GlobalVariable.NumofGuarantorPersonal - 1, GlobalVariable.StatusFailed)

                                'write to excel reason lookup'
                                CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '3a.TabGuarantorDataPersonal', 
                                    1, GlobalVariable.NumofGuarantorPersonal - 1, GlobalVariable.StatusReasonLookup)

                                GlobalVariable.FlagFailed = 1

                                continue
                            }
                            
                            'select ownership'
                            WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/select_Ownership'), 
                                datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                                    46), false)
                        }
                        //Ambil nilai dari confins untuk verif data lookup
                    } else if (datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        13) == 'LookUp') {
                        if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_3Guarantor Data'), 
                            5, FailureHandling.OPTIONAL)) {
                            'click guarantor lookup'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_Guarantor Legal Name_btn btn-raised btn-primary'))

                            'input customer no'
                            WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Customer No_custNoId'), 
                                datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                                    15))

                            'input customer name'
                            WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Customer Name_custNameId'), 
                                datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                                    16))

                            'input id no'
                            WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Id No_IdNoId'), 
                                datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                                    17))

                            'click search'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_Search'))

                            'verify input error'
                            if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/a_Select'), 
                                10, FailureHandling.OPTIONAL)) {
                                'click select'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/a_Select'))
                            } else {
                                'click X'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabNewApplication/Button_X'))

                                'click button cancel'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_Cancel'))

                                'write to excel failed'
                                CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '3a.TabGuarantorDataPersonal', 
                                    0, GlobalVariable.NumofGuarantorPersonal - 1, GlobalVariable.StatusFailed)

                                'write to excel reason lookup'
                                CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '3a.TabGuarantorDataPersonal', 
                                    1, GlobalVariable.NumofGuarantorPersonal - 1, GlobalVariable.StatusReasonLookup)

                                GlobalVariable.FlagFailed = 1

                                continue
                            }
                            
                            if (GlobalVariable.Role == 'Testing') {
                                getDataGuarPersonal()

                                'call test case Personal data verif'
                                WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP1 - Customer Data/TabGuarantorPersonalDataVerif'), 
                                    [:], FailureHandling.CONTINUE_ON_FAILURE)
                            }
                            
                            'select guarantor relationship'
                            WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/select_GuarantorRelationship'), 
                                datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                                    18), false)

                            if (datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                                25).length() > 1) {
                                'select customer model'
                                WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/select_CustomerModel'), 
                                    datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                                        25), false)
                            }
                            
                            if (datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                                34).length() > 1) {
                                'click lookup department AML'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_DEPARTMENT AML_btn btn-raised btn-primary'))

                                'input department AML Code'
                                WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Code_MasterCodeId'), 
                                    datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                                        33))

                                'click button search'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_Search'))

                                'verify input error'
                                if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/a_Select'), 
                                    10, FailureHandling.OPTIONAL)) {
                                    'click select'
                                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/a_Select'))
                                } else {
                                    'click X'
                                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabNewApplication/Button_X'))

                                    'click button cancel'
                                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_Cancel'))

                                    'write to excel failed'
                                    CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, 
                                        '3a.TabGuarantorDataPersonal', 0, GlobalVariable.NumofGuarantorPersonal - 1, GlobalVariable.StatusFailed)

                                    'write to excel reason lookup'
                                    CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, 
                                        '3a.TabGuarantorDataPersonal', 1, GlobalVariable.NumofGuarantorPersonal - 1, GlobalVariable.StatusReasonLookup)

                                    GlobalVariable.FlagFailed = 1

                                    continue
                                }
                            }
                            
                            if (datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                                36).length() > 1) {
                                'click button lookup authority AML'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_Authority AML_btn btn-raised btn-primary'))

                                'input authority AML code'
                                WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Code_MasterCodeId'), 
                                    datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                                        35))

                                'click button search'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_Search'))

                                'verify input error'
                                verifyInputError(flagWarning)
                            }
                        }
                    }
                    
                    //Ambil nilai dari confins untuk verif store db lookup
                    if ((GlobalVariable.Role == 'Testing') && (datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        13) == 'LookUp')) {
                        'call function get data guar personal'
                        getDataGuarPersonal()

                        'add relationship to array'
                        GlobalVariable.Confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/select_CustomerRelationship'), 
                                'value'))
                    }
                    
                    'get customer name'
                    custname = WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Guarantor Legal Name'), 
                        'value', FailureHandling.OPTIONAL)

                    'add name to Global variable'
                    GlobalVariable.CustomerName = ((GlobalVariable.CustomerName + ';') + custname)

                    'click button save'
                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_Save'))

                    if ((Integer.parseInt(datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                            4)) == 0) && (GlobalVariable.FlagFailed == 0)) {
                        'cek alert'
                        GlobalVariable.FlagFailed = CustomKeywords.'checkSaveProcess.checkSaveProcess.checkAlert'(GlobalVariable.NumofGuarantorPersonal, 
                            '3a.TabGuarantorDataPersonal')
                    }
                    
                    if (GlobalVariable.FlagFailed == 0) {
                        'check save process write to excel'
                        CustomKeywords.'checkSaveProcess.checkSaveProcess.checkStatus'(Integer.parseInt(datafileTabGuarantorPersonal.getValue(
                                    GlobalVariable.NumofGuarantorPersonal, 4)), findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/TableGuarantorHeader'), 
                            GlobalVariable.NumofGuarantorPersonal, '3a.TabGuarantorDataPersonal')

                        'customer added +1'
                        (GlobalVariable.CountNumofCustomer)++

                        if (Integer.parseInt(datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                                4)) == 0) {
                            'cek validasi'
                            CustomKeywords.'checkSaveProcess.checkSaveProcess.checkValidasi'(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/div_errorvalidation'), 
                                GlobalVariable.NumofGuarantorPersonal, '3a.TabGuarantorDataPersonal')
                        }
                    }
                    
                    if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/button_Cancel'), 
                        5, FailureHandling.OPTIONAL)) {
                        'click button cancel'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/button_Cancel'))

                        'customer added -1'
                        (GlobalVariable.CountNumofCustomer)--
                    } else {
                        if ((flagWarning > 0) || (GlobalVariable.FlagWarning > 0)) {
                            CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '3a.TabGuarantorDataPersonal', 
                                0, GlobalVariable.NumofGuarantorPersonal - 1, GlobalVariable.StatusWarning)
                        }
                    }
                    
                    if (datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 13) == 
                    'Input Data') {
                        if ((GlobalVariable.Role == 'Testing') && (GlobalVariable.CheckVerifStoreDBPersonal == 'Yes')) {
                            'call test case personal data store verif'
                            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP1 - Customer Data/TabGuarantorDataPersonalStoreDBVerif'), 
                                [:], FailureHandling.CONTINUE_ON_FAILURE)
                        }
                    } else if (datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                        13) == 'LookUp') {
                        if ((GlobalVariable.Role == 'Testing') && (GlobalVariable.CheckVerifStoreDBPersonal == 'Yes')) {
                            'call test case personal data store verif lookup'
                            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP1 - Customer Data/TabGuarantorDataPersonalStoreDBVerif-LookUp'), 
                                [:], FailureHandling.CONTINUE_ON_FAILURE)
                        }
                    }
                    
                    break
                }
            }
        }
    }
}

//GuarantorCompany
for (GlobalVariable.NumofGuarantorCompany = 2; GlobalVariable.NumofGuarantorCompany <= (Integer.parseInt(GlobalVariable.CountAGuarantorCompany) + 
1); (GlobalVariable.NumofGuarantorCompany)++) {
    GlobalVariable.FlagFailed = 0

    if (datafileTabGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 12) == datafileCustomerPersonal.getValue(
        GlobalVariable.NumofColm, 13)) {
        if (WebUI.verifyElementPresent(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/label_NO DATA AVAILABLE'), 
            5, FailureHandling.OPTIONAL)) {
            'count table guarantor pada confins'
            variableData = DriverFactory.getWebDriver().findElements(By.cssSelector('#guarantor-tab > app-guarantor-main-data-paging > div > div:nth-child(2) > table > tbody tr'))
        } else {
            'count table guarantor pada confins'
            variableData = DriverFactory.getWebDriver().findElements(By.cssSelector('#guarantor-tab > app-guarantor-main-data-paging > div > div:nth-child(2) > lib-ucgridview > div > table > tbody tr'))
        }
        
        for (int i = 1; i <= variableData.size(); i++) {
            'modify object guarantor name'
            modifyNewGuarantorName = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/FromTypeName'), 
                'xpath', 'equals', ('//*[@id="guarantor-tab"]/app-guarantor-main-data-paging/div/div[2]/lib-ucgridview/div/table/tbody/tr[' + 
                i) + ']/td[2]', true)

            if (copyapp.equalsIgnoreCase('Edit')) {
                if (WebUI.verifyElementPresent(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/label_NO DATA AVAILABLE'), 
                    5, FailureHandling.OPTIONAL)) {
                    copyapp = 'No'
                } else if (WebUI.verifyElementPresent(modifyNewGuarantorName, 5, FailureHandling.OPTIONAL)) {
                    if (WebUI.getText(modifyNewGuarantorName).equalsIgnoreCase(datafileTabGuarantorCompany.getValue(
                            GlobalVariable.NumofGuarantorCompany, 18)) || WebUI.getText(modifyNewGuarantorName).equalsIgnoreCase(
                        datafileTabGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 16))) {
                        break
                    }
                }
            }
            
            if ((i == variableData.size()) || (copyapp == 'No')) {
                if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_3Guarantor Data'), 
                    5, FailureHandling.OPTIONAL)) {
                    'click button Add'
                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_Add'))

                    'click radio company'
                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/Radio Company'))

                    if (datafileTabGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 13) == 
                    'Input Data') {
                        'select customer relationship'
                        WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/select_CustomerRelationship'), 
                            datafileTabGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 17), 
                            false)

                        'input guarantor name'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/input_Guarantor Legal Name'), 
                            datafileTabGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 18))

                        'input tax id'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/input_Tax Id No_form-control ng-untouched ng-pristine ng-invalid'), 
                            datafileTabGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 19))

                        'select customer type'
                        WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/select_CustomerType'), 
                            datafileTabGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 20), 
                            false)

                        'select customer model'
                        WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/select_CustomerModel'), 
                            datafileTabGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 21), 
                            false)

                        if (datafileTabGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 23) == 
                        'Yes') {
                            'click button copy'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_Copy'))
                        } else if (datafileTabGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 
                            23) == 'No') {
                            'input text address'
                            WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/textarea_Address'), 
                                datafileTabGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 
                                    24))

                            'input RT'
                            WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_RT'), 
                                datafileTabGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 
                                    25))

                            'input RW'
                            WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_RW'), 
                                datafileTabGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 
                                    26))

                            'click button zipcode'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/button_Zipcode_btn btn-raised btn-primary'))

                            'input zipcode'
                            WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Zipcode'), 
                                datafileTabGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 
                                    27))

                            'input Kecamatan'
                            WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input Kecamatan'), 
                                datafileTabGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 
                                    28))

                            'input Kelurahan'
                            WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input kelurahan'), 
                                datafileTabGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 
                                    29))

                            'input kota'
                            WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input kota'), 
                                datafileTabGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 
                                    30))

                            'click search'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_Search'))

                            'verify input error'
                            if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/a_Select'), 
                                10, FailureHandling.OPTIONAL)) {
                                'click select'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/a_Select'))
                            } else {
                                'click X'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabNewApplication/Button_X'))

                                'click button cancel'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_Cancel'))

                                'write to excel failed'
                                CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '3b.TabGuarantorDataCompany', 
                                    0, GlobalVariable.NumofGuarantorCompany - 1, GlobalVariable.StatusFailed)

                                'write to excel reason lookup'
                                CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '3b.TabGuarantorDataCompany', 
                                    1, GlobalVariable.NumofGuarantorCompany - 1, GlobalVariable.StatusReasonLookup)

                                GlobalVariable.FlagFailed = 1

                                continue
                            }
                            
                            'select ownership'
                            WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/select_Ownership'), 
                                datafileTabGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 
                                    31), false)
                        }
                        //Ambil nilai dari confins untuk verif data lookup db
                    } else if (datafileTabGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 
                        13) == 'LookUp') {
                        if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_3Guarantor Data'), 
                            5, FailureHandling.OPTIONAL)) {
                            'click guarantor lookup'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/button_Guarantor Legal Name_btn btn-raised btn-primary'))

                            'input customer no'
                            WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/input_Customer No_custNoId'), 
                                datafileTabGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 
                                    15))

                            'input customer name'
                            WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/input_Customer Name_custNameId'), 
                                datafileTabGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 
                                    16))

                            'click search'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/button_Search'))

                            'verify input error'
                            if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/a_Select'), 
                                10, FailureHandling.OPTIONAL)) {
                                'click select'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/a_Select'))
                            } else {
                                'click X'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabNewApplication/Button_X'))

                                'click button cancel'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_Cancel'))

                                'write to excel failed'
                                CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '3b.TabGuarantorDataCompany', 
                                    0, GlobalVariable.NumofGuarantorCompany - 1, GlobalVariable.StatusFailed)

                                'write to excel reason lookup'
                                CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '3b.TabGuarantorDataCompany', 
                                    1, GlobalVariable.NumofGuarantorCompany - 1, GlobalVariable.StatusReasonLookup)

                                GlobalVariable.FlagFailed = 1

                                continue
                            }
                            
                            if (GlobalVariable.Role == 'Testing') {
                                'call function get data guarantor company'
                                getDataGuarCompany()

                                'add company type to array'
                                GlobalVariable.Confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/select_CustomerType'), 
                                        'value'))

                                'add customer model to array'
                                GlobalVariable.Confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/select_CustomerModel'), 
                                        'value'))

                                'add ownership to array'
                                GlobalVariable.Confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/select_Ownership'), 
                                        'value'))

                                'call test case company data verif'
                                WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP1 - Customer Data/TabGuarantorCompanyDataVerif'), 
                                    [:], FailureHandling.CONTINUE_ON_FAILURE)
                            }
                            
                            'select customer relationship'
                            WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/select_CustomerRelationship'), 
                                datafileTabGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 
                                    17), false)

                            if (datafileTabGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 
                                20).length() > 1) {
                                'select customer type'
                                WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/select_CustomerType'), 
                                    datafileTabGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 
                                        20), false)
                            }
                        }
                    }
                    
                    //Ambil nilai dari confins untuk verif store db lookup
                    if ((GlobalVariable.Role == 'Testing') && (datafileTabGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 
                        13) == 'LookUp')) {
                        'call function get data guarantor company'
                        getDataGuarCompany()

                        'add relationship to array'
                        GlobalVariable.Confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/select_CustomerRelationship'), 
                                'value'))
                    }
                    
                    'get customer name'
                    custname = WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/input_Guarantor Legal Name'), 
                        'value', FailureHandling.OPTIONAL)

                    'add name to Global variable'
                    GlobalVariable.CustomerName = ((GlobalVariable.CustomerName + ';') + custname)

                    'Klik save'
                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_Save'))

                    Integer iscompleteMandatory = Integer.parseInt(datafileTabGuarantorCompany.getValue(
                            GlobalVariable.NumofGuarantorCompany, 4))

                    if ((iscompleteMandatory == 0) && (GlobalVariable.FlagFailed == 0)) {
                        'cek alert'
                        GlobalVariable.FlagFailed = CustomKeywords.'checkSaveProcess.checkSaveProcess.checkAlert'(GlobalVariable.NumofGuarantorCompany, 
                            '3b.TabGuarantorDataCompany')
                    }
                    
                    if (GlobalVariable.FlagFailed == 0) {
                        'check save process write to excel'
                        CustomKeywords.'checkSaveProcess.checkSaveProcess.checkStatus'(iscompleteMandatory, findTestObject(
                                'Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/TableGuarantorHeader'), 
                            GlobalVariable.NumofGuarantorCompany, '3b.TabGuarantorDataCompany')

                        if (iscompleteMandatory == 0) {
                            errorValObject = findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/div_errorvalidation')

                            'cek validasi'
                            CustomKeywords.'checkSaveProcess.checkSaveProcess.checkValidasi'(errorValObject, GlobalVariable.NumofGuarantorCompany, 
                                '3b.TabGuarantorDataCompany')
                        }
                    }
                    
                    if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/button_Cancel'), 
                        5, FailureHandling.OPTIONAL)) {
                        'click button cancel'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/button_Cancel'))
                    } else {
                        if ((flagWarning > 0) || (GlobalVariable.FlagWarning > 0)) {
                            CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '3b.TabGuarantorDataCompany', 
                                0, GlobalVariable.NumofGuarantorCompany - 1, GlobalVariable.StatusWarning)
                        }
                        
                        'customer added +1'
                        (GlobalVariable.CountNumofCustomer)++
                    }
                    
                    if (datafileTabGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 13) == 
                    'Input Data') {
                        if ((GlobalVariable.Role == 'Testing') && (GlobalVariable.CheckVerifStoreDBPersonal == 'Yes')) {
                            'call test case company data store verif'
                            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP1 - Customer Data/TabGuarantorDataCompanyStoreDBVerif'), 
                                [:], FailureHandling.CONTINUE_ON_FAILURE)
                        }
                    } else if (datafileTabGuarantorCompany.getValue(GlobalVariable.NumofGuarantorCompany, 
                        13) == 'LookUp') {
                        if ((GlobalVariable.Role == 'Testing') && (GlobalVariable.CheckVerifStoreDBPersonal == 'Yes')) {
                            'call test case company data store verif lookup'
                            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP1 - Customer Data/TabGuarantorDataCompanyStoreDBVerif-LookUp'), 
                                [:], FailureHandling.CONTINUE_ON_FAILURE)
                        }
                    }
                }
            }
        }
    }
}

if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_Add'), 
    5, FailureHandling.OPTIONAL)) {
    'click button save and continue'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_Save and continue'))
}

WebUI.delay(10)

def getDataGuarPersonal() {
    'declare array for confins data'
    def confinsdata = []

    'add customer name to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Guarantor Legal Name'), 
            'value'))

    'add birth place to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Birth Place'), 
            'value'))

    'add id expired date to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Id Expired Date'), 
            'value'))

    'add mobile phone to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Mobile Phone'), 
            'value'))

    'add birth date to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Birth Date_form-control ng-untouched ng-pristine ng-invalid'), 
            'value'))

    'add id no to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Id No'), 
            'value'))

    'add tax id to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Tax Id No'), 
            'value'))

    'add mother maiden name to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Mother Maiden Name'), 
            'value'))

    'add email to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Email'), 
            'value'))

    'add address to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/textarea_Address'), 
            'value'))

    'add RT to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_RT'), 
            'value'))

    'add RW to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_RW'), 
            'value'))

    'add zipcode to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/LabelZipcode'), 
            'value'))

    'add kelurahan to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/LabelKelurahan'), 
            'value'))

    'add kecamatan to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/LabelKecamatan'), 
            'value'))

    'add kota to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/LabelKota'), 
            'value'))

    'add id type to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/select_Select IdType'), 
            'value'))

    'add marital status to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/select_MaritalStatus'), 
            'value'))

    'add customer model to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/select_CustomerModel'), 
            'value'))

    'add gender to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/select_Gender'), 
            'value'))

    'add ownership to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/select_Ownership'), 
            'value'))

    GlobalVariable.Confinsdata = confinsdata
}

def getDataGuarCompany() {
    'declare array for confins data'
    def confinsdata = []

    'add customer name to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/input_Guarantor Legal Name'), 
            'value'))

    'add tax id to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/input_Tax Id No_form-control ng-untouched ng-pristine ng-invalid'), 
            'value'))

    'add address to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/textarea_Address'), 
            'value'))

    'add RT to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/input_RT'), 
            'value'))

    'add RW to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/input_RW'), 
            'value'))

    'add zipcode to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/LabelZipcode'), 
            'value'))

    'add kelurahan to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/LabelKelurahan'), 
            'value'))

    'add kecamatan to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/LabelKecamatan'), 
            'value'))

    'add kota to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/LabelKota'), 
            'value'))

    'add company type to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/select_CustomerType'), 
            'value'))

    'add customer model to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/select_CustomerModel'), 
            'value'))

    'add ownership to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/select_Ownership'), 
            'value'))

    GlobalVariable.Confinsdata = confinsdata
}

def verifyInputError(int flagWarning) {
    if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/a_Select'), 
        10, FailureHandling.OPTIONAL)) {
        'click select'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/a_Select'))
    } else {
        'click X'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabNewApplication/Button_X'))

        flagWarning++
    }
}

