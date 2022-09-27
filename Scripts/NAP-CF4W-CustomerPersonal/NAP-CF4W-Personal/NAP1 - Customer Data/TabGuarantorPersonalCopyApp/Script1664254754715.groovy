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
import internal.GlobalVariable as GlobalVariable

int flagWarning = 0

int flagFailed = 0

String userDir = System.getProperty('user.dir')

String filePath = userDir + GlobalVariable.PathPersonal

GlobalVariable.DataFilePath = filePath

ArrayList<WebElement> variableData = DriverFactory.getWebDriver().findElements(By.cssSelector('#guarantor-tab > app-guarantor-main-data-paging > div > div:nth-child(2) > lib-ucgridview > div > table > tbody tr'))

for (i = 1; i <= variableData.size(); i++) {
    'modify object guarantor name'
    modifyNewGuarantorName = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/FromTypeName'), 
        'xpath', 'equals', ('//*[@id="guarantor-tab"]/app-guarantor-main-data-paging/div/div[2]/lib-ucgridview/div/table/tbody/tr[' + 
        i) + ']/td[2]', true)

    'modify object guarantor name'
    modifyNewGuarantorTypeName = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/FromTypeName'), 
        'xpath', 'equals', ('//*[@id="guarantor-tab"]/app-guarantor-main-data-paging/div/div[2]/lib-ucgridview/div/table/tbody/tr[' + 
        i) + ']/td[3]', true)

    'modify object button edit'
    modifyNewButtonEdit = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/FromTypeName'), 
        'xpath', 'equals', ('//*[@id="guarantor-tab"]/app-guarantor-main-data-paging/div/div[2]/lib-ucgridview/div/table/tbody/tr[' + 
        i) + ']/td[5]/span/span[1]/span/a/i', true)

    'modify object button delete'
    modifyNewButtonDelete = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/FromTypeName'), 
        'xpath', 'equals', ('//*[@id="guarantor-tab"]/app-guarantor-main-data-paging/div/div[2]/lib-ucgridview/div/table/tbody/tr[' + 
        i) + ']/td[5]/span/span[2]/span/a/i', true)

    'Loop Multiple guarantor data'
    for (GlobalVariable.NumofGuarantorPersonal = 2; GlobalVariable.NumofGuarantorPersonal <= (Integer.parseInt(GlobalVariable.CountAGuarantorPersonal) + 
    1); (GlobalVariable.NumofGuarantorPersonal)++) {
        if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabGuarantorDataPersonal').getValue(
            GlobalVariable.NumofGuarantorPersonal, 12) == findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
            GlobalVariable.NumofColm, 13)) {
            if (WebUI.verifyElementPresent(modifyNewGuarantorName, 5, FailureHandling.OPTIONAL)) {
                if (WebUI.getText(modifyNewGuarantorTypeName).equalsIgnoreCase('Personal')) {
                    if (WebUI.getText(modifyNewGuarantorName).equalsIgnoreCase(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabGuarantorDataPersonal').getValue(
                            GlobalVariable.NumofGuarantorPersonal, 19))) {
                        if (WebUI.verifyElementPresent(modifyNewButtonEdit, 5, FailureHandling.OPTIONAL)) {
                            'click button edit'
                            WebUI.click(modifyNewButtonEdit, FailureHandling.OPTIONAL)

                            if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabGuarantorDataPersonal').getValue(
                                GlobalVariable.NumofGuarantorPersonal, 13) == 'Input Data') {
                                'click radio personal'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/Radio Personal'))

                                'select guarantor relationship'
                                WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/select_GuarantorRelationship'), 
                                    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabGuarantorDataPersonal').getValue(
                                        GlobalVariable.NumofGuarantorPersonal, 18), false)

                                'input guarantor name'
                                WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Guarantor Legal Name'), 
                                    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabGuarantorDataPersonal').getValue(
                                        GlobalVariable.NumofGuarantorPersonal, 19))

                                'input birth place'
                                WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Birth Place'), 
                                    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabGuarantorDataPersonal').getValue(
                                        GlobalVariable.NumofGuarantorPersonal, 20))

                                'select id type'
                                WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/select_IDType'), 
                                    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabGuarantorDataPersonal').getValue(
                                        GlobalVariable.NumofGuarantorPersonal, 21), false)

                                'Verify ID Type'
                                if (((findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabGuarantorDataPersonal').getValue(
                                    GlobalVariable.NumofGuarantorPersonal, 21) != 'E-KTP') && (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabGuarantorDataPersonal').getValue(
                                    GlobalVariable.NumofGuarantorPersonal, 21) != 'NPWP')) && (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabGuarantorDataPersonal').getValue(
                                    GlobalVariable.NumofGuarantorPersonal, 21) != 'AKTA')) {
                                    'input id expired date'
                                    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Id Expired Date'), 
                                        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabGuarantorDataPersonal').getValue(
                                            GlobalVariable.NumofGuarantorPersonal, 22))
                                }
                                
                                'select marital status'
                                WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/select_MaritalStatus'), 
                                    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabGuarantorDataPersonal').getValue(
                                        GlobalVariable.NumofGuarantorPersonal, 23), false)

                                'input mobile phone'
                                WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Mobile Phone'), 
                                    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabGuarantorDataPersonal').getValue(
                                        GlobalVariable.NumofGuarantorPersonal, 24))

                                'select customer model'
                                WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/select_CustomerModel'), 
                                    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabGuarantorDataPersonal').getValue(
                                        GlobalVariable.NumofGuarantorPersonal, 25), false)

                                'select gender'
                                WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/select_Select One Female  Male'), 
                                    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabGuarantorDataPersonal').getValue(
                                        GlobalVariable.NumofGuarantorPersonal, 26), false)

                                'input birth date'
                                WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Birth Date_form-control ng-untouched ng-pristine ng-invalid'), 
                                    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabGuarantorDataPersonal').getValue(
                                        GlobalVariable.NumofGuarantorPersonal, 27))

                                'input id no'
                                WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Id No'), 
                                    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabGuarantorDataPersonal').getValue(
                                        GlobalVariable.NumofGuarantorPersonal, 28))

                                'input tax id'
                                WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Tax Id No'), 
                                    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabGuarantorDataPersonal').getValue(
                                        GlobalVariable.NumofGuarantorPersonal, 29))

                                'input mother maiden'
                                WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Mother Maiden Name'), 
                                    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabGuarantorDataPersonal').getValue(
                                        GlobalVariable.NumofGuarantorPersonal, 30))

                                'input email'
                                WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Email'), 
                                    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabGuarantorDataPersonal').getValue(
                                        GlobalVariable.NumofGuarantorPersonal, 31))

                                'click button lookup department AML'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_DEPARTMENT AML_btn btn-raised btn-primary'))

                                'input department AML Code'
                                WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Code_MasterCodeId'), 
                                    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabGuarantorDataPersonal').getValue(
                                        GlobalVariable.NumofGuarantorPersonal, 33))

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
                                    CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, 
                                        '3a.TabGuarantorDataPersonal', 0, GlobalVariable.NumofGuarantorPersonal - 1, GlobalVariable.StatusFailed)

                                    'write to excel reason lookup'
                                    CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, 
                                        '3a.TabGuarantorDataPersonal', 1, GlobalVariable.NumofGuarantorPersonal - 1, GlobalVariable.StatusReasonLookup)

                                    flagFailed = 1

                                    continue
                                }
                                
                                if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabGuarantorDataPersonal').getValue(
                                    GlobalVariable.NumofGuarantorPersonal, 36).length() > 1) {
                                    'click button lookup authority AML'
                                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_Authority AML_btn btn-raised btn-primary'))

                                    'input authority AML code'
                                    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Code_MasterCodeId'), 
                                        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabGuarantorDataPersonal').getValue(
                                            GlobalVariable.NumofGuarantorPersonal, 35))

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

                                        flagWarning++
                                    }
                                }
                                
                                if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabGuarantorDataPersonal').getValue(
                                    GlobalVariable.NumofGuarantorPersonal, 38) == 'Yes') {
                                    'click button copy'
                                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_Copy'))
                                } else if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabGuarantorDataPersonal').getValue(
                                    GlobalVariable.NumofGuarantorPersonal, 38) == 'No') {
                                    'input text address'
                                    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/textarea_Address'), 
                                        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabGuarantorDataPersonal').getValue(
                                            GlobalVariable.NumofGuarantorPersonal, 39))

                                    'input RT'
                                    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_RT'), 
                                        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabGuarantorDataPersonal').getValue(
                                            GlobalVariable.NumofGuarantorPersonal, 40))

                                    'input RW'
                                    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_RW'), 
                                        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabGuarantorDataPersonal').getValue(
                                            GlobalVariable.NumofGuarantorPersonal, 41))

                                    'click button zipcode'
                                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_Zipcode_btn btn-raised btn-primary'))

                                    'input zipcode'
                                    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Zipcode'), 
                                        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabGuarantorDataPersonal').getValue(
                                            GlobalVariable.NumofGuarantorPersonal, 42))

                                    'input Kecamatan'
                                    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input Kecamatan'), 
                                        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabGuarantorDataPersonal').getValue(
                                            GlobalVariable.NumofGuarantorPersonal, 43))

                                    'input Kelurahan'
                                    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input kelurahan'), 
                                        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabGuarantorDataPersonal').getValue(
                                            GlobalVariable.NumofGuarantorPersonal, 44))

                                    'input kota'
                                    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input kota'), 
                                        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabGuarantorDataPersonal').getValue(
                                            GlobalVariable.NumofGuarantorPersonal, 45))

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
                                        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, 
                                            '3a.TabGuarantorDataPersonal', 0, GlobalVariable.NumofGuarantorPersonal - 1, 
                                            GlobalVariable.StatusFailed)

                                        'write to excel reason lookup'
                                        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, 
                                            '3a.TabGuarantorDataPersonal', 1, GlobalVariable.NumofGuarantorPersonal - 1, 
                                            GlobalVariable.StatusReasonLookup)

                                        flagFailed = 1

                                        continue
                                    }
                                    
                                    'select ownership'
                                    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/select_Ownership'), 
                                        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabGuarantorDataPersonal').getValue(
                                            GlobalVariable.NumofGuarantorPersonal, 46), false)
                                }
                                
                                'get customer name'
                                custname = WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Guarantor Legal Name'), 
                                    'value', FailureHandling.OPTIONAL)

                                'add name to Global variable'
                                GlobalVariable.CustomerName = ((GlobalVariable.CustomerName + ';') + custname)

                                'click button save'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_Save'))

                                Integer iscompleteMandatory = Integer.parseInt(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabGuarantorDataPersonal').getValue(
                                        GlobalVariable.NumofGuarantorPersonal, 4))

                                if (iscompleteMandatory == 0) {
                                    'cek alert'
                                    flagFailed = CustomKeywords.'checkSaveProcess.checkSaveProcess.checkAlert'(GlobalVariable.NumofGuarantorPersonal, 
                                        '3a.TabGuarantorDataPersonal')
                                }
                                
                                if (flagFailed == 0) {
                                    'check save process write to excel'
                                    CustomKeywords.'checkSaveProcess.checkSaveProcess.checkStatus'(iscompleteMandatory, 
                                        findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/TableGuarantorHeader'), 
                                        GlobalVariable.NumofGuarantorPersonal, '3a.TabGuarantorDataPersonal')

                                    if (iscompleteMandatory == 0) {
                                        errorValObject = findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/div_errorvalidation')

                                        'cek validasi'
                                        CustomKeywords.'checkSaveProcess.checkSaveProcess.checkValidasi'(errorValObject, 
                                            GlobalVariable.NumofGuarantorPersonal, '3a.TabGuarantorDataPersonal')
                                    }
                                }
                                
                                if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/button_Cancel'), 
                                    5, FailureHandling.OPTIONAL)) {
                                    'click button cancel'
                                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/button_Cancel'))
                                } else {
                                    if (flagWarning > 0) {
                                        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, 
                                            '3a.TabGuarantorDataPersonal', 0, GlobalVariable.NumofGuarantorPersonal - 1, 
                                            GlobalVariable.StatusWarning)
                                    }
                                    
                                    'customer added +1'
                                    (GlobalVariable.countNumofCustomer)++
                                }
                            } else if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabGuarantorDataPersonal').getValue(
                                GlobalVariable.NumofGuarantorPersonal, 13) == 'LookUp') {
                                if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_3Guarantor Data'), 
                                    5, FailureHandling.OPTIONAL)) {
                                    'click radio personal'
                                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/Radio Personal'))

                                    'select guarantor relationship'
                                    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/select_GuarantorRelationship'), 
                                        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabGuarantorDataPersonal').getValue(
                                            GlobalVariable.NumofGuarantorPersonal, 18), false)

                                    'click guarantor lookup'
                                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_Guarantor Legal Name_btn btn-raised btn-primary'))

                                    'input customer no'
                                    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Customer No_custNoId'), 
                                        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabGuarantorDataPersonal').getValue(
                                            GlobalVariable.NumofGuarantorPersonal, 15))

                                    'input customer name'
                                    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Customer Name_custNameId'), 
                                        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabGuarantorDataPersonal').getValue(
                                            GlobalVariable.NumofGuarantorPersonal, 16))

                                    'input id no'
                                    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Id No_IdNoId'), 
                                        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabGuarantorDataPersonal').getValue(
                                            GlobalVariable.NumofGuarantorPersonal, 17))

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
                                        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, 
                                            '3a.TabGuarantorDataPersonal', 0, GlobalVariable.NumofGuarantorPersonal - 1, 
                                            GlobalVariable.StatusFailed)

                                        'write to excel reason lookup'
                                        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, 
                                            '3a.TabGuarantorDataPersonal', 1, GlobalVariable.NumofGuarantorPersonal - 1, 
                                            GlobalVariable.StatusReasonLookup)

                                        flagFailed = 1

                                        continue
                                    }
                                    
                                    if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabGuarantorDataPersonal').getValue(
                                        GlobalVariable.NumofGuarantorPersonal, 25).length() > 1) {
                                        'select customer model'
                                        WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/select_CustomerModel'), 
                                            findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabGuarantorDataPersonal').getValue(
                                                GlobalVariable.NumofGuarantorPersonal, 25), false)
                                    }
                                    
                                    if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabGuarantorDataPersonal').getValue(
                                        GlobalVariable.NumofGuarantorPersonal, 34).length() > 1) {
                                        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_DEPARTMENT AML_btn btn-raised btn-primary'))

                                        'input department AML Code'
                                        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Code_MasterCodeId'), 
                                            findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabGuarantorDataPersonal').getValue(
                                                GlobalVariable.NumofGuarantorPersonal, 33))

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
                                            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, 
                                                '3a.TabGuarantorDataPersonal', 0, GlobalVariable.NumofGuarantorPersonal - 
                                                1, GlobalVariable.StatusFailed)

                                            'write to excel reason lookup'
                                            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, 
                                                '3a.TabGuarantorDataPersonal', 1, GlobalVariable.NumofGuarantorPersonal - 
                                                1, GlobalVariable.StatusReasonLookup)

                                            flagFailed = 1

                                            continue
                                        }
                                    }
                                    
                                    if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabGuarantorDataPersonal').getValue(
                                        GlobalVariable.NumofGuarantorPersonal, 36).length() > 1) {
                                        'click button lookup authority AML'
                                        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_Authority AML_btn btn-raised btn-primary'))

                                        'input authority AML code'
                                        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Code_MasterCodeId'), 
                                            findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabGuarantorDataPersonal').getValue(
                                                GlobalVariable.NumofGuarantorPersonal, 35))

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

                                            flagWarning++
                                        }
                                    }
                                    
                                    'get customer name'
                                    custname = WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Guarantor Legal Name'), 
                                        'value', FailureHandling.OPTIONAL)

                                    'add name to Global variable'
                                    GlobalVariable.CustomerName = ((GlobalVariable.CustomerName + ';') + custname)

                                    if (GlobalVariable.Role == 'Testing') {
                                        'call test case Personal data verif'
                                        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1 - Customer Data/TabGuarantorPersonalDataVerif'), 
                                            [:], FailureHandling.CONTINUE_ON_FAILURE)
                                    }
                                    
                                    'click button save'
                                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_Save'))

                                    Integer iscompleteMandatory = Integer.parseInt(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabGuarantorDataPersonal').getValue(
                                            GlobalVariable.NumofGuarantorPersonal, 4))

                                    if (iscompleteMandatory == 0) {
                                        'cek alert'
                                        flagFailed = CustomKeywords.'checkSaveProcess.checkSaveProcess.checkAlert'(GlobalVariable.NumofGuarantorPersonal, 
                                            '3a.TabGuarantorDataPersonal')
                                    }
                                    
                                    if (flagFailed == 0) {
                                        'check save process write to excel'
                                        CustomKeywords.'checkSaveProcess.checkSaveProcess.checkStatus'(iscompleteMandatory, 
                                            findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/TableGuarantorHeader'), 
                                            GlobalVariable.NumofGuarantorPersonal, '3a.TabGuarantorDataPersonal')

                                        if (iscompleteMandatory == 0) {
                                            errorValObject = findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/div_errorvalidation')

                                            'cek validasi'
                                            CustomKeywords.'checkSaveProcess.checkSaveProcess.checkValidasi'(errorValObject, 
                                                GlobalVariable.NumofGuarantorPersonal, '3a.TabGuarantorDataPersonal')
                                        }
                                    }
                                    
                                    if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/button_Cancel'), 
                                        5, FailureHandling.OPTIONAL)) {
                                        'click button cancel'
                                        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/button_Cancel'))
                                    } else {
                                        if (flagWarning > 0) {
                                            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, 
                                                '3a.TabGuarantorDataPersonal', 0, GlobalVariable.NumofGuarantorPersonal - 
                                                1, GlobalVariable.StatusWarning)
                                        }
                                        
                                        'customer added +1'
                                        (GlobalVariable.countNumofCustomer)++
                                    }
                                }
                            }
                            
                            if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabGuarantorDataPersonal').getValue(
                                GlobalVariable.NumofGuarantorPersonal, 13) == 'Input Data') {
                                if (GlobalVariable.Role == 'Testing') {
                                    'call test case company data store verif'
                                    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1 - Customer Data/TabGuarantorDataPersonalStoreDBVerif'), 
                                        [:], FailureHandling.CONTINUE_ON_FAILURE)
                                }
                            }
                        }
                    } else {
                        if (GlobalVariable.NumofGuarantor == (Integer.parseInt(GlobalVariable.CountAGuarantorPersonal) + 
                        1)) {
                            if (WebUI.verifyElementPresent(modifyNewButtonDelete, 5, FailureHandling.OPTIONAL)) {
                                'click button Delete'
                                WebUI.click(modifyNewButtonDelete, FailureHandling.OPTIONAL)

                                'accept alert'
                                WebUI.acceptAlert()

                                i--
                            }
                        }
                    }
                }
            } else {
                break
            }
        }
    }
}

WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1 - Customer Data/TCAddGuarantorPersonal'), 
    [:], FailureHandling.CONTINUE_ON_FAILURE)
