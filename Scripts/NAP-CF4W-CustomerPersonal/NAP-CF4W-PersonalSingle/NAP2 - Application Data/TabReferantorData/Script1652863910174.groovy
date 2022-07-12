import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable

'click menu application data'
WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/a_APPLICATION DATA'))

'input Appno'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabReferantorData/input_Application No_AppNoId'), 
    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
        GlobalVariable.NumofColm, 3))

'click button search'
WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabReferantorData/button_Search'))

'click icon pensil untuk select'
WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabReferantorData/i_FT PRODUCT OFFERING CF4W_font-medium-3 ft-edit-2'))

'Assign directori file excel ke global variabel'
String userDir = System.getProperty('user.dir')

'Assign directori file excel ke global variabel'
String filePath = userDir + GlobalVariable.Path

'Assign directori file excel ke global variabel'
GlobalVariable.DataFilePath = filePath

if (Integer.parseInt(GlobalVariable.CountofReferantor) >= 1) {
    'click referantor checkbox'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabReferantorData/CheckboxReferantor'))

    int modifyObjectIndex = 1

    'looping referantor'
    for (GlobalVariable.NumofReferantor = 2; GlobalVariable.NumofReferantor <= (Integer.parseInt(GlobalVariable.CountofReferantor) + 
    1); (GlobalVariable.NumofReferantor)++) {
        if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabReferantorData').getValue(
            GlobalVariable.NumofReferantor, 2) == findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
            GlobalVariable.NumofColm, 3)) {
            'click button add'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabReferantorData/button_Add'))

            String newButtonReferantorXpath = ('//*[@id="accessoriesData"]/div[2]/table/tbody/tr[' + modifyObjectIndex) + 
            ']/td[3]/lib-uclookupgeneric/div/div/div[1]/span/button'

            'modify button referantor lookup'
            modifyObjectButtonReferantor = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabReferantorData/button_LookupReferantor'), 
                'xpath', 'equals', newButtonReferantorXpath, true)

            String newSelectReferantorCategoryXpath = ('//*[@id="accessoriesData"]/div[2]/table/tbody/tr[' + modifyObjectIndex) + 
            ']/td[2]/select'

            'modify select referantor category'
            modifyObjectSelectReferantorCategory = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabReferantorData/select_ReferantorCategory'), 
                'xpath', 'equals', newSelectReferantorCategoryXpath, true)

            String newSelectTaxCalculation = ('//*[@id="accessoriesData"]/div[2]/table/tbody/tr[' + modifyObjectIndex) + 
            ']/td[6]/select'

            'modify select tax calculation'
            modifySelectTaxCalcualtion = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabReferantorData/select_TaxCalculationMethod'), 
                'xpath', 'equals', newSelectTaxCalculation, true)

            String newSelectBankaccount = ('//*[@id="accessoriesData"]/div[2]/table/tbody/tr[' + modifyObjectIndex) + ']/td[5]/select'

            'modify select bank account'
            modifySelectBankAccount = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabReferantorData/select_BankAccount'), 
                'xpath', 'equals', newSelectBankaccount, true)

            String newButtonDelete = ('//*[@id="accessoriesData"]/div[2]/table/tbody/tr[' + modifyObjectIndex) + ']/td[8]/a/i'

            'modify button delete'
            modifyButtonDelete = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabReferantorData/Button Delete'), 
                'xpath', 'equals', newButtonDelete, true)

            'select referantor category'
            WebUI.selectOptionByLabel(modifyObjectSelectReferantorCategory, findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabReferantorData').getValue(
                    GlobalVariable.NumofReferantor, 3), false, FailureHandling.OPTIONAL)

            'click button referantor lookup'
            WebUI.click(modifyObjectButtonReferantor, FailureHandling.OPTIONAL)

            'input referantor name'
            WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabReferantorData/input_Referantor Name_referantorName'), 
                findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabReferantorData').getValue(
                    GlobalVariable.NumofReferantor, 4))

            'click button search'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabReferantorData/Button_SearchReferantor'))

            'verify lookup'
            if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabReferantorData/a_Select'), 
                5, FailureHandling.OPTIONAL)) {
                'click select'
                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabReferantorData/a_Select'))
            } else {
                'click button x'
                WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabReferantorData/button_X'))

                'write to excel FAILED'
                CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '5.TabReferantorData', 
                    0, GlobalVariable.NumofReferantor - 1, GlobalVariable.StatusFailed)

                'Click delete'
                WebUI.click(modifyButtonDelete, FailureHandling.OPTIONAL)

                continue
            }
            
            'select bank account'
            WebUI.selectOptionByIndex(modifySelectBankAccount, findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabReferantorData').getValue(
                    GlobalVariable.NumofReferantor, 6), FailureHandling.OPTIONAL)

            'select tax calculation method'
            WebUI.selectOptionByLabel(modifySelectTaxCalcualtion, findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabReferantorData').getValue(
                    GlobalVariable.NumofReferantor, 7), false, FailureHandling.OPTIONAL)

            'cek inputan mandatory apakah sudah terisi semua atau belum'
            if ((WebUI.verifyOptionSelectedByIndex(modifyObjectSelectReferantorCategory, 0, 3, FailureHandling.OPTIONAL) || 
            WebUI.verifyOptionSelectedByIndex(modifySelectBankAccount, 0, 3, FailureHandling.OPTIONAL)) || WebUI.verifyOptionSelectedByIndex(
                modifySelectTaxCalcualtion, 0, 3, FailureHandling.OPTIONAL)) {
                'write to excel FAILED'
                CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '5.TabReferantorData', 
                    0, GlobalVariable.NumofReferantor - 1, GlobalVariable.StatusFailed)

                'Click delete'
                WebUI.click(modifyButtonDelete, FailureHandling.OPTIONAL)

                continue
            }
            
            'write to excel SUCCESS'
            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '5.TabReferantorData', 
                0, GlobalVariable.NumofReferantor - 1, GlobalVariable.StatusSuccess)

            modifyObjectIndex++
        }
    }
    
}

'click button save'
WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabReferantorData/Button Save'))

if (WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/ApplicationCurrentStep')), 
    'REFERANTOR', false, FailureHandling.OPTIONAL)) {
    'click button cancel'
    WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabReferantorData/button_Cancel'))
}

