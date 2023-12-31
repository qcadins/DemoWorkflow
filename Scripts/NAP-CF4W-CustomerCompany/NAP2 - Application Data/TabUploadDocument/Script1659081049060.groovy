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

'get data file path'
GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'("\\Excel\\"+ GlobalVariable.LOB +"\\2.2 DataFile_NAP_CF4W_Company.xlsx")

'declare excelPathCustomerCompany'
excelPathCustomerCompany = 'NAP-'+ GlobalVariable.LOB +'-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData'

'declare excelPathTabUploadDoc'
excelPathTabUploadDoc = 'NAP-'+ GlobalVariable.LOB +'-CustomerCompany/NAP2-ApplicationData/TabUploadDocument'

'Klik tab upload doc'
WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/buttonTabUploadDoc'))

countUploadDoc = findTestData(excelPathTabUploadDoc).getColumnNumbers()

if (GlobalVariable.RoleCompany == 'Testing') {
    'verify application step'
    checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/ApplicationCurrentStep')), 
            'UPLOAD DOCUMENT', false, FailureHandling.OPTIONAL))
}

for (GlobalVariable.NumofUploadDocument = 2; GlobalVariable.NumofUploadDocument <= (countUploadDoc - 1); (GlobalVariable.NumofUploadDocument)++) {
    if (findTestData(excelPathTabUploadDoc).getValue(GlobalVariable.NumofUploadDocument, 12) == findTestData(excelPathCustomerCompany).getValue(GlobalVariable.NumofColm, 
        13)) {
        'click menu titik 3'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabUploadDocument/span_VIEW APPLICATION  0002APP20211201128_spanMenu'))

        'jika menu titik 3 disabled/enabled'
        if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabUploadDocument/a_New Document'), 
            GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
            'click new document'
            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabUploadDocument/a_New Document'))
        } else {
            break
        }
        
        'upload file'
        CustomKeywords.'customizeKeyword.uploadFile.uploadcustomizeKeyword.Function'(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabUploadDocument/img_Upload _thumbnail thumbnail-change'), 
            findTestData(excelPathTabUploadDoc).getValue(GlobalVariable.NumofUploadDocument, 13))

        'input document name'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabUploadDocument/input_Document Name  _gvMetadatactl02txtRecordValue'), 
            findTestData(excelPathTabUploadDoc).getValue(GlobalVariable.NumofUploadDocument, 14))

        'select jenis document'
        WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabUploadDocument/select_Jenis Document'), 
            findTestData(excelPathTabUploadDoc).getValue(GlobalVariable.NumofUploadDocument, 15), false)

        'input MOU Id'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabUploadDocument/input_Mou Id_gvMetadatactl05txtRecordValue'), 
            findTestData(excelPathTabUploadDoc).getValue(GlobalVariable.NumofUploadDocument, 16))

        'input notes'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabUploadDocument/input_Notes_gvMetadatactl06txtRecordValue'), 
            findTestData(excelPathTabUploadDoc).getValue(GlobalVariable.NumofUploadDocument, 17))

        'click button save'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabUploadDocument/button_save'))

        WebUI.delay(10)

        CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '11.TabUploadDocument', 0, 
            GlobalVariable.NumofUploadDocument - 1, GlobalVariable.StatusSuccess)

        if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabUploadDocument/button_Close'), 
            GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
            'click button close'
            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabUploadDocument/button_Close'))

            'Write To Excel GlobalVariable.StatusWarning and GlobalVariable.StatusReasonMandatoryEmpty'
            CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('11.TabUploadDocument', GlobalVariable.NumofUploadDocument, 
                GlobalVariable.StatusWarning, GlobalVariable.StatusReasonMandatoryEmpty)
        }
    } else {
        break
    }
}

if (WebUI.verifyElementPresent(findTestObject('Object Repository/NAP/ReturnHandling/SubmitUploadDocument'), GlobalVariable.TimeOut, 
    FailureHandling.OPTIONAL)) {
    'click button submit edit application'
    WebUI.click(findTestObject('Object Repository/NAP/ReturnHandling/SubmitUploadDocument'))
} else {
    'click button submit'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabUploadDocument/button_Submit'))
}

'click menu Customer data completion paging'
WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabUploadDocument/a_CUSTOMER DATA COMPLETION'))

WebUI.delay(3)

'input app no'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabUploadDocument/input_Application No_AppNoId'), 
    findTestData(excelPathCustomerCompany).getValue(GlobalVariable.NumofColm, 13))

'click button search'
WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabUploadDocument/button_Search'))

'verify appno'
WebUI.verifyElementText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabUploadDocument/td_0002APP20211201128'), 
    findTestData(excelPathCustomerCompany).getValue(GlobalVariable.NumofColm, 13), FailureHandling.OPTIONAL)

def checkVerifyEqualOrMatch(Boolean isMatch) {
    if ((isMatch == false) && (GlobalVariable.FlagFailed == 0)) {
        'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedVerifyEqualOrMatch'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('11.TabUploadDocument', GlobalVariable.NumofUploadDocument, 
            GlobalVariable.StatusFailed, findTestData(excelPathTabUploadDoc).getValue(GlobalVariable.NumofColm, 2) + ';' + GlobalVariable.ReasonFailedVerifyEqualOrMatch)

        GlobalVariable.FlagFailed = 1
    }
}

