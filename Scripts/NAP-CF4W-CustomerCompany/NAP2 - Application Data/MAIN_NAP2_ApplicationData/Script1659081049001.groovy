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
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import groovy.sql.Sql as Sql
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.WebElement as WebElement
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.By as By

GlobalVariable.StartIndex = 2

'declare excelPathCustomerCompany'
excelPathCustomerCompany = (('NAP-' + GlobalVariable.LOB) + '-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData')

'declare excelPathReferantor'
excelPathReferantor = (('NAP-' + GlobalVariable.LOB) + '-CustomerCompany/NAP2-ApplicationData/TabReferantorData')

'declare excelPathTabApplication'
excelPathTabApplication = (('NAP-' + GlobalVariable.LOB) + '-CustomerCompany/NAP2-ApplicationData/TabApplicationData')

'declare excelPathTabAsset'
excelPathTabAsset = (('NAP-' + GlobalVariable.LOB) + '-CustomerCompany/NAP2-ApplicationData/TabAssetData')

'declare excelPathTabInsurance'
excelPathTabInsurance = (('NAP-' + GlobalVariable.LOB) + '-CustomerCompany/NAP2-ApplicationData/TabInsuranceData')

'declare excelPathTabFinancial'
excelPathTabFinancial = (('NAP-' + GlobalVariable.LOB) + '-CustomerCompany/NAP2-ApplicationData/TabFinancialData')

'declare excelPathTabTC'
excelPathTabTC = (('NAP-' + GlobalVariable.LOB) + '-CustomerCompany/NAP2-ApplicationData/TabTermConditionData')

'declare excelPathTabUploadDoc'
excelPathTabUploadDoc = (('NAP-' + GlobalVariable.LOB) + '-CustomerCompany/NAP2-ApplicationData/TabUploadDocument')

if (GlobalVariable.LOB == 'CF4W') {
    'Pengecekan jika consumer finance belum diexpand'
    if (WebUI.verifyElementNotVisible(findTestObject('LoginR3BranchManagerSuperuser/a_New Consumer Finance'), FailureHandling.OPTIONAL)) {
        'Klik new consumer finance'
        WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_Consumer Finance'))
    }
    
    'Pengecekan jika new consumer finance belum diexpand'
    if (WebUI.verifyElementNotVisible(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA CF4W'), FailureHandling.OPTIONAL)) {
        'Klik new consumer finance'
        WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_New Consumer Finance'))
    }
    
    'click menu customer application data'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/a_APPLICATION DATA CF4W'))
} else if (GlobalVariable.LOB == 'FL4W') {
    'Pengecekan jika finance leasing belum diexpand'
    if (WebUI.verifyElementNotVisible(findTestObject('LoginR3BranchManagerSuperuser/a_New Finance Leasing'), FailureHandling.OPTIONAL)) {
        'Klik new finance leasing'
        WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_FinanceLeasing4W'))
    }
    
    'Pengecekan jika new consumer finance belum diexpand'
    if (WebUI.verifyElementNotVisible(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA FL4W'), FailureHandling.OPTIONAL)) {
        'Klik new finance leasing'
        WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_New Finance Leasing'))
    }
    
    'click menu customer application data'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/a_APPLICATION DATA FL4W'))
}

//Verify sort & paging
pagingTesting()

'input Appno'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/input_Application No_AppNoId'), 
    findTestData(excelPathCustomerCompany).getValue(GlobalVariable.NumofColm, 13))

'Looping delay untuk handling copy app selama +- 2 menit'
for (int i = 1; i <= 8; i++) {
    'click button search'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/button_Search'))

    'Pengecekan ada/tidak adanya button action pencil yang muncul'
    if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/i_FT PRODUCT OFFERING CF4W_font-medium-3 ft-edit-2'), 
        GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
        break
    } else {
        'delay 14 detik'
        WebUI.delay(14)
    }
}

'click icon pensil untuk select'
WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/i_FT PRODUCT OFFERING CF4W_font-medium-3 ft-edit-2'))

'get data file path'
GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'(('\\Excel\\' + GlobalVariable.LOB) + 
    '\\2.2 DataFile_NAP_CF4W_Company.xlsx')

if (GlobalVariable.RoleCompany == 'Data Entry') {
    'Looping untuk mencari nilai colm yang menunjukkan colm appno'
    for (GlobalVariable.NumofReferantor = 2; GlobalVariable.NumofReferantor <= (findTestData(excelPathReferantor).getColumnNumbers() - 
    1); (GlobalVariable.NumofReferantor)++) {
        if (findTestData(excelPathReferantor).getValue(GlobalVariable.NumofReferantor, 12) == findTestData(excelPathCustomerCompany).getValue(
            GlobalVariable.NumofColm, 13)) {
            GlobalVariable.StartIndex = GlobalVariable.NumofReferantor

            break
        }
    }
    
    'Dijalankan tanpa copy app tab referantor atau copy app dengan edit'
    if (findTestData(excelPathReferantor).getValue(GlobalVariable.StartIndex, 10).equalsIgnoreCase('No') || findTestData(
        excelPathReferantor).getValue(GlobalVariable.StartIndex, 10).equalsIgnoreCase('Edit')) {
        'call test case tab referantor'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabReferantorData'), [:], FailureHandling.CONTINUE_ON_FAILURE //dijalankan dengan copy app tab referantor
            )
    } else if (findTestData(excelPathReferantor).getValue(GlobalVariable.StartIndex, 10).equalsIgnoreCase('Yes')) {
        'click button save'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/Button Save'))

        'Write to excel success'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '5.TabReferantorData', 0, 
            GlobalVariable.StartIndex - 1, GlobalVariable.StatusSuccess)

        'verify fail'
        if (WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/ApplicationCurrentStep')), 
            'REFERANTOR', false, FailureHandling.OPTIONAL)) {
            'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.StatusFailedCopyApp'
            CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('5.TabReferantorData', GlobalVariable.StartIndex, 
                GlobalVariable.StatusFailed, GlobalVariable.StatusFailedCopyApp)

            'click button cancel'
            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/button_Cancel'))
        }
    }
    
    'Dijalankan tanpa copy app tab application atau copy app dengan edit'
    if (findTestData(excelPathTabApplication).getValue(GlobalVariable.NumofColm, 10).equalsIgnoreCase('No') || findTestData(
        excelPathTabApplication).getValue(GlobalVariable.NumofColm, 10).equalsIgnoreCase('Edit')) {
        'Call test case tab application data'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/CF4W/TabApplicationData'), [:], FailureHandling.CONTINUE_ON_FAILURE //dijalankan dengan copy app tab application
            )
    } else if (findTestData(excelPathTabApplication).getValue(GlobalVariable.NumofColm, 10).equalsIgnoreCase('Yes')) {
        'click Save'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/button_Save'))

        'Write to excel success'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '6.TabApplicationData', 0, 
            GlobalVariable.NumofColm - 1, GlobalVariable.StatusSuccess)

        WebUI.delay(5)

        'Verify fail'
        if (WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/ApplicationCurrentStep')), 
            'APPLICATION DATA', false, FailureHandling.OPTIONAL)) {
            'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.StatusFailedCopyApp'
            CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('6.TabApplicationData', GlobalVariable.NumofColm, 
                GlobalVariable.StatusFailed, GlobalVariable.StatusFailedCopyApp)

            'click cancel'
            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/button_Cancel'))
        }
    }
    
    'Dijalankan tanpa copy app tab asset atau copy app dengan edit'
    if (findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofColm, 10).equalsIgnoreCase('No') || findTestData(excelPathTabAsset).getValue(
        GlobalVariable.NumofColm, 10).equalsIgnoreCase('Edit')) {
        'call test case tab asset data'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/CF4W/TabAssetData'), [:], FailureHandling.CONTINUE_ON_FAILURE //dijalankan dengan copy app tab asset
            )
    } else if (findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofColm, 10).equalsIgnoreCase('Yes')) {
        'input chasis number'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_CHASIS NUMBER_CF4W'), 
            findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofColm, 29))

        'input engine number'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_ENGINE NUMBER-CF4W'), 
            findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofColm, 30))

        'input license plate number'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_LICENSE PLATE NUMBER_CF4W'), 
            findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofColm, 31))

        if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_Serial4_CF4W'), 
            GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
            'input serial 4'
            WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_Serial4_CF4W'), findTestData(
                    excelPathTabAsset).getValue(GlobalVariable.NumofColm, 32), FailureHandling.OPTIONAL)
        }
        
        if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_Serial5_CF4W'), 
            GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
            'input serial 5'
            WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_Serial5_CF4W'), findTestData(
                    excelPathTabAsset).getValue(GlobalVariable.NumofColm, 33), FailureHandling.OPTIONAL)
        }
        
        'click button save'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/button_Save-CF4W'))

        'Menunggu Alert security deposit dibawah minimum atau manufacturing year dibawah angka tertentu (jika ada) muncul'
        WebUI.waitForAlert(3)

        'Accept Alert Konfirmasi Security deposit dibawah minimum atau manufacturing year dibawah angka tertentu'
        WebUI.acceptAlert(FailureHandling.OPTIONAL)

        'Menunggu Alert konfirmasi integrator muncul'
        WebUI.waitForAlert(3)

        'Accept Alert Konfirmasi Integrator'
        WebUI.acceptAlert(FailureHandling.OPTIONAL)

        'Write to excel success'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '7.TabAssetData', 0, GlobalVariable.NumofColm - 
            1, GlobalVariable.StatusSuccess)

        if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/button_Supplier Name_CF4W'), 
            GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
            'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.StatusFailedCopyApp'
            CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('7.TabAssetData', GlobalVariable.NumofColm, 
                GlobalVariable.StatusFailed, GlobalVariable.StatusFailedCopyApp)

            'click button cancel'
            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/button_Cancel-CF4W'))
        }
    }
    
    'Dijalankan tanpa copy app tab insurance atau copy app dengan edit'
    if (findTestData(excelPathTabInsurance).getValue(GlobalVariable.NumofColm, 10).equalsIgnoreCase('No') || findTestData(
        excelPathTabInsurance).getValue(GlobalVariable.NumofColm, 10).equalsIgnoreCase('Edit')) {
        'call test case tab insurance'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/CF4W/TabInsuranceData'), [:], FailureHandling.CONTINUE_ON_FAILURE //dijalankan dengan copy app tab insurance
            )
    } else if (findTestData(excelPathTabInsurance).getValue(GlobalVariable.NumofColm, 10).equalsIgnoreCase('Yes')) {
        if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/CF4W/button_Calculate Insurance'), 
            GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
            'Klik calculate insurance'
            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/CF4W/button_Calculate Insurance'))
        }
        
        'Klik save'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/CF4W/button_Save'))

        WebUI.delay(5)

        'Write to excel success'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '8.TabInsuranceData', 0, 
            GlobalVariable.NumofColm - 1, GlobalVariable.StatusSuccess)

        'verify fail'
        if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/CF4W/select_InsuredBy'), 
            GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
            'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.StatusFailedCopyApp'
            CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('8.TabInsuranceData', GlobalVariable.NumofColm, 
                GlobalVariable.StatusFailed, GlobalVariable.StatusFailedCopyApp)

            'click cancel'
            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/CF4W/button_Cancel'))
        }
    }
    
    'Dijalankan tanpa copy app tab financial atau copy app dengan edit'
    if (findTestData(excelPathTabFinancial).getValue(GlobalVariable.NumofColm, 10).equalsIgnoreCase('No') || findTestData(
        excelPathTabFinancial).getValue(GlobalVariable.NumofColm, 10).equalsIgnoreCase('Edit')) {
        'call test case tab financial'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabFinancialData'), [:], FailureHandling.CONTINUE_ON_FAILURE //Dijalankan dengan copy app tab financial
            )
    } else if (findTestData(excelPathTabFinancial).getValue(GlobalVariable.NumofColm, 10).equalsIgnoreCase('Yes')) {
        'click button calculate'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/button_Calculate'))

        WebUI.delay(5)

        'click button save'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/button_Save'))

        'Write to excel success'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '9.TabFinancialData', 0, 
            GlobalVariable.NumofColm - 1, GlobalVariable.StatusSuccess)

        if (WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/ApplicationCurrentStep')), 
            'FINANCIAL DATA', false, FailureHandling.OPTIONAL)) {
            'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.StatusFailedCopyApp'
            CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('9.TabFinancialData', GlobalVariable.NumofColm, 
                GlobalVariable.StatusFailed, GlobalVariable.StatusFailedCopyApp)

            'click button cancel'
            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/button_Cancel-CF4W'))
        }
    }
    
    'Dijalankan tanpa copy app tab term & condition atau copy app dengan edit'
    if (findTestData(excelPathTabTC).getValue(GlobalVariable.NumofColm, 10).equalsIgnoreCase('No') || findTestData(excelPathTabTC).getValue(
        GlobalVariable.NumofColm, 10).equalsIgnoreCase('Edit')) {
        'call test case tab term condition'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabTermConditionData'), [:], FailureHandling.CONTINUE_ON_FAILURE //Dijalankan dengan copy app tab term&condition
            )
    } else if (findTestData(excelPathTabTC).getValue(GlobalVariable.NumofColm, 10).equalsIgnoreCase('Yes')) {
        'Save'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabTermConditionData/button_Save'))

        'Write to excel success'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '10.TabTermConditionData', 
            0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusSuccess)

        'Verify fail'
        if (WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/ApplicationCurrentStep')), 
            'TERM AND CONDITION', false, FailureHandling.OPTIONAL)) {
            'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.StatusFailedCopyApp'
            CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('10.TabTermConditionData', GlobalVariable.NumofColm, 
                GlobalVariable.StatusFailed, GlobalVariable.StatusFailedCopyApp)

            'click cancel'
            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabTermConditionData/button_Cancel'))
        }
    }
    
    'Looping untuk mencari nilai colm yang menunjukkan colm appno'
    for (GlobalVariable.NumofUploadDocument = 2; GlobalVariable.NumofUploadDocument <= (findTestData(excelPathTabUploadDoc).getColumnNumbers() - 
    1); (GlobalVariable.NumofUploadDocument)++) {
        if (findTestData(excelPathTabUploadDoc).getValue(GlobalVariable.NumofUploadDocument, 12) == findTestData(excelPathCustomerCompany).getValue(
            GlobalVariable.NumofColm, 13)) {
            GlobalVariable.StartIndex = GlobalVariable.NumofUploadDocument

            break
        }
    }
    
    'Dijalankan tanpa copy app tab upload document atau copy app dengan edit'
    if (findTestData(excelPathTabUploadDoc).getValue(GlobalVariable.StartIndex, 10).equalsIgnoreCase('No') || findTestData(
        excelPathTabUploadDoc).getValue(GlobalVariable.StartIndex, 10).equalsIgnoreCase('Edit')) {
        'call test case tab upload document'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabUploadDocument'), [:], FailureHandling.CONTINUE_ON_FAILURE //dijalankan dengan copy app tab upload document
            //dijalankan dengan copy app tab referantor
            //dijalankan dengan copy app tab application
            //dijalankan dengan copy app tab asset
            //dijalankan dengan copy app tab insurance
            //Dijalankan dengan copy app tab financial
            //Dijalankan dengan copy app tab term&condition
            //dijalankan dengan copy app tab upload document
            )
    } else if (findTestData(excelPathTabUploadDoc).getValue(GlobalVariable.StartIndex, 10).equalsIgnoreCase('Yes')) {
        'click button submit'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabUploadDocument/button_Submit'))

        'Write to excel success'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '11.TabUploadDocument', 0, 
            GlobalVariable.NumofColm - 1, GlobalVariable.StatusSuccess)

        if (WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabUploadDocument/input_Application No_AppNoId'), 
            GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
            'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.StatusFailedCopyApp'
            CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('11.TabUploadDocument', GlobalVariable.NumofColm, 
                GlobalVariable.StatusFailed, GlobalVariable.StatusFailedCopyApp)

            'click cancel'
            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabTermConditionData/button_Cancel'))
        }
        
        'Verify tab insurance, financial apakah ada nilai yang berubah'
        verifyMatch()
    }
} else {
    if (GlobalVariable.IsDataCancel == 1) {
        KeywordUtil.markFailedAndStop('Failed NAP2')
    }
    
    'Looping untuk mencari nilai colm yang menunjukkan colm appno'
    for (GlobalVariable.NumofReferantor = 2; GlobalVariable.NumofReferantor <= (findTestData(excelPathReferantor).getColumnNumbers() - 
    1); (GlobalVariable.NumofReferantor)++) {
        if (findTestData(excelPathReferantor).getValue(GlobalVariable.NumofReferantor, 12) == findTestData(excelPathCustomerCompany).getValue(
            GlobalVariable.NumofColm, 13)) {
            GlobalVariable.StartIndex = GlobalVariable.NumofReferantor

            break
        }
    }
    
    'Dijalankan tanpa copy app tab referantor atau copy app dengan edit'
    if (findTestData(excelPathReferantor).getValue(GlobalVariable.StartIndex, 10).equalsIgnoreCase('No') || findTestData(
        excelPathReferantor).getValue(GlobalVariable.StartIndex, 10).equalsIgnoreCase('Edit')) {
        'call test case tab referantor'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabReferantorData'), [:], FailureHandling.CONTINUE_ON_FAILURE)
    } else if (findTestData(excelPathReferantor).getValue(GlobalVariable.StartIndex, 10).equalsIgnoreCase('Yes')) {
        'click button save'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/Button Save'))

        'Write to excel success'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '5.TabReferantorData', 0, 
            GlobalVariable.StartIndex - 1, GlobalVariable.StatusSuccess)

        'verify fail'
        if (WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/ApplicationCurrentStep')), 
            'REFERANTOR', false, FailureHandling.OPTIONAL)) {
            'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.StatusFailedCopyApp'
            CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('5.TabReferantorData', GlobalVariable.StartIndex, 
                GlobalVariable.StatusFailed, GlobalVariable.StatusFailedCopyApp)

            'click button cancel'
            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/button_Cancel'))
        }
    }
    
    if (GlobalVariable.IsDataCancel == 1) {
        KeywordUtil.markFailedAndStop('Failed NAP2')
    }
    
    'Dijalankan tanpa copy app tab application atau copy app dengan edit'
    if (findTestData(excelPathTabApplication).getValue(GlobalVariable.NumofColm, 10).equalsIgnoreCase('No') || findTestData(
        excelPathTabApplication).getValue(GlobalVariable.NumofColm, 10).equalsIgnoreCase('Edit')) {
        'Call test case tab application data'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/CF4W/TabApplicationData'), [:], FailureHandling.CONTINUE_ON_FAILURE)
    } else if (findTestData(excelPathTabApplication).getValue(GlobalVariable.NumofColm, 10).equalsIgnoreCase('Yes')) {
        'click Save'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/button_Save'))

        'Write to excel success'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '6.TabApplicationData', 0, 
            GlobalVariable.NumofColm - 1, GlobalVariable.StatusSuccess)

        WebUI.delay(5)

        'Verify fail'
        if (WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/ApplicationCurrentStep')), 
            'APPLICATION DATA', false, FailureHandling.OPTIONAL)) {
            'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.StatusFailedCopyApp'
            CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('6.TabApplicationData', GlobalVariable.NumofColm, 
                GlobalVariable.StatusFailed, GlobalVariable.StatusFailedCopyApp)

            'click cancel'
            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/button_Cancel'))
        }
    }
    
    if (GlobalVariable.IsDataCancel == 1) {
        KeywordUtil.markFailedAndStop('Failed NAP2')
    }
    
    'Dijalankan tanpa copy app tab asset atau copy app dengan edit'
    if (findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofColm, 10).equalsIgnoreCase('No') || findTestData(excelPathTabAsset).getValue(
        GlobalVariable.NumofColm, 10).equalsIgnoreCase('Edit')) {
        'call test case tab asset data'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/CF4W/TabAssetData'), [:], FailureHandling.CONTINUE_ON_FAILURE)
    } else if (findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofColm, 10).equalsIgnoreCase('Yes')) {
        'input chasis number'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_CHASIS NUMBER_CF4W'), 
            findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofColm, 29))

        'input engine number'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_ENGINE NUMBER-CF4W'), 
            findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofColm, 30))

        'input license plate number'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_LICENSE PLATE NUMBER_CF4W'), 
            findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofColm, 31))

        if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_Serial4_CF4W'), 
            GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
            'input serial 4'
            WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_Serial4_CF4W'), findTestData(
                    excelPathTabAsset).getValue(GlobalVariable.NumofColm, 32), FailureHandling.OPTIONAL)
        }
        
        if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_Serial5_CF4W'), 
            GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
            'input serial 5'
            WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_Serial5_CF4W'), findTestData(
                    excelPathTabAsset).getValue(GlobalVariable.NumofColm, 33), FailureHandling.OPTIONAL)
        }
        
        'click button save'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/button_Save-CF4W'))

        'Menunggu Alert security deposit dibawah minimum atau manufacturing year dibawah angka tertentu (jika ada) muncul'
        WebUI.waitForAlert(3)

        'Accept Alert Konfirmasi Security deposit dibawah minimum atau manufacturing year dibawah angka tertentu'
        WebUI.acceptAlert(FailureHandling.OPTIONAL)

        'Menunggu Alert konfirmasi integrator muncul'
        WebUI.waitForAlert(3)

        'Accept Alert Konfirmasi Integrator'
        WebUI.acceptAlert(FailureHandling.OPTIONAL)

        'Write to excel success'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '7.TabAssetData', 0, GlobalVariable.NumofColm - 
            1, GlobalVariable.StatusSuccess)

        WebUI.delay(10)

        if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/button_Supplier Name_CF4W'), 
            GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
            'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.StatusFailedCopyApp'
            CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('7.TabAssetData', GlobalVariable.NumofColm, 
                GlobalVariable.StatusFailed, GlobalVariable.StatusFailedCopyApp)

            'click button cancel'
            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/button_Cancel-CF4W'))
        }
    }
    
    if (GlobalVariable.IsDataCancel == 1) {
        KeywordUtil.markFailedAndStop('Failed NAP2')
    }
    
    'Dijalankan tanpa copy app tab insurance atau copy app dengan edit'
    if (findTestData(excelPathTabInsurance).getValue(GlobalVariable.NumofColm, 10).equalsIgnoreCase('No') || findTestData(
        excelPathTabInsurance).getValue(GlobalVariable.NumofColm, 10).equalsIgnoreCase('Edit')) {
        'call test case tab insurance'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/CF4W/TabInsuranceData'), [:], FailureHandling.CONTINUE_ON_FAILURE)
    } else if (findTestData(excelPathTabInsurance).getValue(GlobalVariable.NumofColm, 10).equalsIgnoreCase('Yes')) {
        'verify field discount ada atau tidak untuk write ulang diskon supaya tidak hilang akibat impact dari calculate insurance'
        if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/CF4W/input_Discount_TotalCustDiscAmt'), 
            GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
            def val = WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/CF4W/input_Discount_TotalCustDiscAmt'), 
                'value')

            'Set text discount'
            WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/CF4W/input_Discount_TotalCustDiscAmt'), 
                val, FailureHandling.OPTIONAL)
        }
        
        'verify button calculate ada atau tidak'
        if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/CF4W/button_Calculate Insurance'), 
            GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
            'Klik calculate insurance'
            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/CF4W/button_Calculate Insurance'))
        }
        
        'Klik save'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/CF4W/button_Save'))

        WebUI.delay(5)

        'Write to excel success'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '8.TabInsuranceData', 0, 
            GlobalVariable.NumofColm - 1, GlobalVariable.StatusSuccess)

        'verify fail'
        if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/CF4W/select_InsuredBy'), 
            GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
            'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.StatusFailedCopyApp'
            CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('8.TabInsuranceData', GlobalVariable.NumofColm, 
                GlobalVariable.StatusFailed, GlobalVariable.StatusFailedCopyApp)

            'click cancel'
            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData/CF4W/button_Cancel'))
        }
    }
    
    if (GlobalVariable.IsDataCancel == 1) {
        KeywordUtil.markFailedAndStop('Failed NAP2')
    }
    
    'Dijalankan tanpa copy app tab financial atau copy app dengan edit'
    if (findTestData(excelPathTabFinancial).getValue(GlobalVariable.NumofColm, 10).equalsIgnoreCase('No') || findTestData(
        excelPathTabFinancial).getValue(GlobalVariable.NumofColm, 10).equalsIgnoreCase('Edit')) {
        'call test case tab financial'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabFinancialData'), [:], FailureHandling.CONTINUE_ON_FAILURE)
    } else if (findTestData(excelPathTabFinancial).getValue(GlobalVariable.NumofColm, 10).equalsIgnoreCase('Yes')) {
        'click button calculate'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/button_Calculate'))

        WebUI.delay(5)

        'click button save'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/button_Save'))

        'Write to excel success'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '9.TabFinancialData', 0, 
            GlobalVariable.NumofColm - 1, GlobalVariable.StatusSuccess)

        if (WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/ApplicationCurrentStep')), 
            'FINANCIAL DATA', false, FailureHandling.OPTIONAL)) {
            'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.StatusFailedCopyApp'
            CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('9.TabFinancialData', GlobalVariable.NumofColm, 
                GlobalVariable.StatusFailed, GlobalVariable.StatusFailedCopyApp)

            'click button cancel'
            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/button_Cancel-CF4W'))
        }
    }
    
    if (GlobalVariable.IsDataCancel == 1) {
        KeywordUtil.markFailedAndStop('Failed NAP2')
    }
    
    'Dijalankan tanpa copy app tab term & condition atau copy app dengan edit'
    if (findTestData(excelPathTabTC).getValue(GlobalVariable.NumofColm, 10).equalsIgnoreCase('No') || findTestData(excelPathTabTC).getValue(
        GlobalVariable.NumofColm, 10).equalsIgnoreCase('Edit')) {
        'call test case tab term condition'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabTermConditionData'), [:], FailureHandling.CONTINUE_ON_FAILURE)
    } else if (findTestData(excelPathTabTC).getValue(GlobalVariable.NumofColm, 10).equalsIgnoreCase('Yes')) {
        'Save'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabTermConditionData/button_Save'))

        'Write to excel success'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '10.TabTermConditionData', 
            0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusSuccess)

        'Verify fail'
        if (WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/ApplicationCurrentStep')), 
            'TERM AND CONDITION', false, FailureHandling.OPTIONAL)) {
            'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.StatusFailedCopyApp'
            CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('10.TabTermConditionData', GlobalVariable.NumofColm, 
                GlobalVariable.StatusFailed, GlobalVariable.StatusFailedCopyApp)

            'click cancel'
            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabTermConditionData/button_Cancel'))
        }
    }
    
    if (GlobalVariable.IsDataCancel == 1) {
        KeywordUtil.markFailedAndStop('Failed NAP2')
    }
    
    'Looping untuk mencari nilai colm yang menunjukkan colm appno'
    for (GlobalVariable.NumofUploadDocument = 2; GlobalVariable.NumofUploadDocument <= (findTestData(excelPathTabUploadDoc).getColumnNumbers() - 
    1); (GlobalVariable.NumofUploadDocument)++) {
        if (findTestData(excelPathTabUploadDoc).getValue(GlobalVariable.NumofUploadDocument, 12) == findTestData(excelPathCustomerCompany).getValue(
            GlobalVariable.NumofColm, 13)) {
            GlobalVariable.StartIndex = GlobalVariable.NumofUploadDocument

            break
        }
    }
    
    'Dijalankan tanpa copy app tab upload document atau copy app dengan edit'
    if (findTestData(excelPathTabUploadDoc).getValue(GlobalVariable.StartIndex, 10).equalsIgnoreCase('No') || findTestData(
        excelPathTabUploadDoc).getValue(GlobalVariable.StartIndex, 10).equalsIgnoreCase('Edit')) {
        'call test case tab upload document'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabUploadDocument'), [:], FailureHandling.CONTINUE_ON_FAILURE)
    } else if (findTestData(excelPathTabUploadDoc).getValue(GlobalVariable.StartIndex, 10).equalsIgnoreCase('Yes')) {
        'click button submit'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabUploadDocument/button_Submit'))

        'Write to excel success'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '11.TabUploadDocument', 0, 
            GlobalVariable.NumofColm - 1, GlobalVariable.StatusSuccess)

        if (WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabUploadDocument/input_Application No_AppNoId'), 
            GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
            'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.StatusFailedCopyApp'
            CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('11.TabUploadDocument', GlobalVariable.NumofColm, 
                GlobalVariable.StatusFailed, GlobalVariable.StatusFailedCopyApp)

            'click cancel'
            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabTermConditionData/button_Cancel'))
        }
        
        'Verify tab insurance, financial apakah ada nilai yang berubah'
        verifyMatch()
    }
}

'Verify tab insurance, financial apakah ada nilai yang berubah (membandingkan app sumber dengan app hasil copy dalam database'

def verifyMatch() {
    'Koneksi database'
    Sql sqlConnectionLOS = CustomKeywords.'dbConnection.connectDB.connectLOS'()

    'verify tab insurance, financial, cek apakah ada nilai yang berubah, bernilai true jika tidak ada nilai yang berubah, false jika ada salah satu nilai yang berubah'
    Boolean isMatch = CustomKeywords.'dbConnection.getInfoForEditNAP.verifyMatchCopyAppYesNAP2Company'(sqlConnectionLOS, 
        findTestData(excelPathCustomerCompany).getValue(GlobalVariable.NumofColm, 9), findTestData(excelPathCustomerCompany).getValue(
            GlobalVariable.NumofColm, 13))

    if (isMatch == true) {
        'Write to excel sheet commission copy app yes'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '12.TabCommissionData', 9, 
            GlobalVariable.NumofColm - 1, 'Yes')

        'Write to excel sheet reserved fund copy app yes'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '13.TabReservedFundData', 
            9, GlobalVariable.NumofColm - 1, 'Yes')
    } else if (isMatch == false) {
        'Write to excel sheet commission copy app edit'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '12.TabCommissionData', 9, 
            GlobalVariable.NumofColm - 1, 'Edit')

        'Write to excel sheet reserved fund copy app edit'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '13.TabReservedFundData', 
            9, GlobalVariable.NumofColm - 1, 'Edit')
    }
}

def pagingTesting() {
    if ((GlobalVariable.RoleCompany == 'Testing') && (GlobalVariable.CheckPagingCompany == 'Yes')) {
        ArrayList<WebElement> resultReset = new ArrayList<WebElement>()

        ArrayList<WebElement> checkVerifySort = new ArrayList<WebElement>()

        ArrayList<WebElement> checkVerifyFooter = new ArrayList<WebElement>()

        'Verif reset'
        resultReset = CustomKeywords.'paging.verifyPaging.resetPaging'()

        ArrayList<WebElement> listString = new ArrayList<WebElement>()

        'click button search'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/button_Search'))

        'Inisialisasi driver'
        WebDriver driver = DriverFactory.getWebDriver()

        'Inisialisasi variabel'
        ArrayList<WebElement> rowData = driver.findElements(By.cssSelector('body > app-root > app-full-layout > div > div.main-panel > div > div > div > div > nap-detail-paging > lib-ucpaging > lib-ucgridview > div > table > tbody > tr'))

        'Klik header office'
        WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/span_Office'))

        'Verif tidak ada alert yang muncul'
        checkVerifySort.add(WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerCompany/div_erroralert'), GlobalVariable.TimeOut))

        'Klik header appno'
        WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/span_appNo'))

        'Verify alert tidak muncul'
        checkVerifySort.add(WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerCompany/div_erroralert'), GlobalVariable.TimeOut))

        for (int i = 1; i <= rowData.size(); i++) {
            appNoObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/appNo'), 
                'xpath', 'equals', ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/nap-detail-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
                i) + ']/td[4]/span', true)

            listString.add(WebUI.getText(appNoObject))
        }
        
        'verif sort appno ascending'
        Boolean isSorted = CustomKeywords.'paging.verifyPaging.verifySortAscending'(listString)

        checkVerifySort.add(WebUI.verifyEqual(isSorted, true))

        listApp = new ArrayList<WebElement>()

        'Klik header appno'
        WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/span_appNo'))

        for (int i = 1; i <= rowData.size(); i++) {
            appNoObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/appNo'), 
                'xpath', 'equals', ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/nap-detail-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
                i) + ']/td[4]/span', true)

            listApp.add(WebUI.getText(appNoObject))
        }
        
        'Verif sort appno descending'
        isSorted = CustomKeywords.'paging.verifyPaging.verifySortDescending'(listApp)

        checkVerifySort.add(WebUI.verifyEqual(isSorted, true))

        'Klik header custname'
        WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/span_custName'))

        'Verify alert tidak muncul'
        checkVerifySort.add(WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerCompany/div_erroralert'), 2))

        listString = new ArrayList<WebElement>()

        for (int i = 1; i <= rowData.size(); i++) {
            appNoObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/custName'), 
                'xpath', 'equals', ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/nap-detail-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
                i) + ']/td[5]/span', true)

            listString.add(WebUI.getText(appNoObject))
        }
        
        'Verif sort custname ascending'
        isSorted = CustomKeywords.'paging.verifyPaging.verifySortAscending'(listString)

        checkVerifySort.add(WebUI.verifyEqual(isSorted, true))

        'Klik header custname'
        WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/span_custName'))

        listString = new ArrayList<WebElement>()

        for (int i = 1; i <= rowData.size(); i++) {
            appNoObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/custName'), 
                'xpath', 'equals', ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/nap-detail-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
                i) + ']/td[5]/span', true)

            listString.add(WebUI.getText(appNoObject))
        }
        
        'Verif sort custname descending'
        isSorted = CustomKeywords.'paging.verifyPaging.verifySortDescending'(listString)

        checkVerifySort.add(WebUI.verifyEqual(isSorted, true))

        'Klik header poname'
        WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/span_POName'))

        'Verify alert tidak muncul'
        checkVerifySort.add(WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerCompany/div_erroralert'), 2))

        listString = new ArrayList<WebElement>()

        for (int i = 1; i <= rowData.size(); i++) {
            appNoObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/POName'), 
                'xpath', 'equals', ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/nap-detail-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
                i) + ']/td[6]/span', true)

            listString.add(WebUI.getText(appNoObject))
        }
        
        'verif sort poname ascending'
        isSorted = CustomKeywords.'paging.verifyPaging.verifySortAscending'(listString)

        checkVerifySort.add(WebUI.verifyEqual(isSorted, true))

        'Klik header poname'
        WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/span_POName'))

        listString = new ArrayList<WebElement>()

        for (int i = 1; i <= rowData.size(); i++) {
            appNoObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/POName'), 
                'xpath', 'equals', ('/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/nap-detail-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr[' + 
                i) + ']/td[6]/span', true)

            listString.add(WebUI.getText(appNoObject))
        }
        
        'verif sort poname descending'
        isSorted = CustomKeywords.'paging.verifyPaging.verifySortDescending'(listString)

        checkVerifySort.add(WebUI.verifyEqual(isSorted, true))

        'Klik 2x appno supaya urutan descending'
        WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/span_appNo'))

        WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/span_appNo'))

        'Ambil count data dari confins'
        String[] textCountData = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/countData')).replace(
            ' ', '').replace(':', ';').split(';')

        Integer countDt = Integer.parseInt(textCountData[1])

        'Jika count data keseluruhan lebih besar daripada jumlah data page 1'
        if (countDt > rowData.size()) {
            'Klik page 2'
            WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/nextPage'))

            'verif page 2 active'
            checkVerifyFooter.add(WebUI.verifyElementHasAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/nextPage'), 
                    'aria-current', GlobalVariable.TimeOut))

            listString = new ArrayList<WebElement>()

            listString = CustomKeywords.'paging.verifyPaging.addAppNoForPagingNAP2'(listString)

            'Verif appno pada page 2 tidak ada di page 1'
            Boolean isPaging = CustomKeywords.'paging.verifyPaging.verifyPagingFunction'(listApp, listString)

            checkVerifyFooter.add(WebUI.verifyEqual(isPaging, true))

            'Klik button prev'
            WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/button_Prev'))

            'Verify page 1 active'
            checkVerifyFooter.add(WebUI.verifyElementHasAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/pageOne'), 
                    'aria-current', GlobalVariable.TimeOut))

            listApp = listString

            listString = new ArrayList<WebElement>()

            listString = CustomKeywords.'paging.verifyPaging.addAppNoForPagingNAP2'(listString)

            'Verif appno yang ada di page 1 tidak ada di page 2'
            isPaging = CustomKeywords.'paging.verifyPaging.verifyPagingFunction'(listApp, listString)

            checkVerifyFooter.add(WebUI.verifyEqual(isPaging, true))

            'Klik button next'
            WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/button_Next'))

            'Verify page 2 active'
            checkVerifyFooter.add(WebUI.verifyElementHasAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/nextPage'), 
                    'aria-current', GlobalVariable.TimeOut))

            listApp = listString

            listString = new ArrayList<WebElement>()

            listString = CustomKeywords.'paging.verifyPaging.addAppNoForPagingNAP2'(listString)

            'Verif appno yang ada di page 2 tidak ada di page 1'
            isPaging = CustomKeywords.'paging.verifyPaging.verifyPagingFunction'(listApp, listString)

            checkVerifyFooter.add(WebUI.verifyEqual(isPaging, true))
        }
        
        'Klik button page 1'
        WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/pageOne'))

        checkVerifyFooter.add(WebUI.verifyEqual(CustomKeywords.'paging.verifyPaging.NAP2CountDataInPage'(), true))

        if (resultReset.contains(false) && (GlobalVariable.StatusFailed != findTestData(excelPathCustomerCompany).getValue(
            GlobalVariable.NumofColm, 1))) {
            'Write To Excel GlobalVariable.StatusWarning and reason'
            CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('1.TabCustomerMainData', GlobalVariable.NumofColm, 
                GlobalVariable.StatusWarning, ((((findTestData(excelPathCustomerCompany).getValue(GlobalVariable.NumofColm, 
                    2).replace('-', '') + findTestData(excelPathCustomerCompany).getValue(GlobalVariable.NumofColm, 2)) + 
                ';') + GlobalVariable.ReasonFailedReset) + 'NAP2') + ';\n')

            GlobalVariable.FlagWarning = 1
        }
        
        if (checkVerifySort.contains(false) && (GlobalVariable.StatusFailed != findTestData(excelPathCustomerCompany).getValue(
            GlobalVariable.NumofColm, 1))) {
            'Write To Excel GlobalVariable.StatusWarning and reason'
            CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('1.TabCustomerMainData', GlobalVariable.NumofColm, 
                GlobalVariable.StatusWarning, ((((findTestData(excelPathCustomerCompany).getValue(GlobalVariable.NumofColm, 
                    2).replace('-', '') + findTestData(excelPathCustomerCompany).getValue(GlobalVariable.NumofColm, 2)) + 
                ';') + GlobalVariable.ReasonFailedSort) + 'NAP2') + ';\n')

            GlobalVariable.FlagWarning = 1
        }
        
        if (checkVerifyFooter.contains(false) && (GlobalVariable.StatusFailed != findTestData(excelPathCustomerCompany).getValue(
            GlobalVariable.NumofColm, 1))) {
            'Write To Excel GlobalVariable.StatusWarning and reason'
            CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('1.TabCustomerMainData', GlobalVariable.NumofColm, 
                GlobalVariable.StatusWarning, ((((findTestData(excelPathCustomerCompany).getValue(GlobalVariable.NumofColm, 
                    2).replace('-', '') + findTestData(excelPathCustomerCompany).getValue(GlobalVariable.NumofColm, 2)) + 
                ';') + GlobalVariable.ReasonFailedFooter) + 'NAP2') + ';\n')

            GlobalVariable.FlagWarning = 1
        }
    }
}

