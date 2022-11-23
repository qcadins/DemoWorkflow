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
GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'(GlobalVariable.PathPersonal)


if (GlobalVariable.Role == 'Testing') {
    'verify application step'
    checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/ApplicationCurrentStep')), 
        'UPLOAD DOCUMENT', false, FailureHandling.OPTIONAL))
}

if (Integer.parseInt(GlobalVariable.CountofUploadDocument) >= 1) {
    for (GlobalVariable.NumofUploadDocument = 2; GlobalVariable.NumofUploadDocument <= (Integer.parseInt(GlobalVariable.CountofUploadDocument) + 
    1); (GlobalVariable.NumofUploadDocument)++) {
        if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabUploadDocument').getValue(
            GlobalVariable.NumofUploadDocument, 12) == findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
            GlobalVariable.NumofColm, 13)) {
            'click menu titik 3'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabUploadDocument/span_VIEW APPLICATION  0002APP20211201128_spanMenu'))

            'jika menu titik 3 disabled/enabled'
            if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabUploadDocument/a_New Document'), 
                5, FailureHandling.OPTIONAL)) {
                'click new document'
                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabUploadDocument/a_New Document'))
            } else {
                break
            }
            
            'upload file'
            CustomKeywords.'customizeKeyword.uploadFile.uploadFunction'(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabUploadDocument/img_Upload _thumbnail thumbnail-change'), 
                findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabUploadDocument').getValue(
                    GlobalVariable.NumofUploadDocument, 13))

            'input document name'
            WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabUploadDocument/input_Document Name  _gvMetadatactl02txtRecordValue'), 
                findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabUploadDocument').getValue(
                    GlobalVariable.NumofUploadDocument, 14))

            'select jenis document'
            WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabUploadDocument/select_Jenis Document'), 
                findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabUploadDocument').getValue(
                    GlobalVariable.NumofUploadDocument, 15), false)

            'input MOU Id'
            WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabUploadDocument/input_Mou Id_gvMetadatactl05txtRecordValue'), 
                findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabUploadDocument').getValue(
                    GlobalVariable.NumofUploadDocument, 16))

            'input notes'
            WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabUploadDocument/input_Notes_gvMetadatactl06txtRecordValue'), 
                findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabUploadDocument').getValue(
                    GlobalVariable.NumofUploadDocument, 17))

            'click button save'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabUploadDocument/button_save'))

			WebUI.delay(10)
			
            CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '12.TabUploadDocument', 
                0, GlobalVariable.NumofUploadDocument - 1, GlobalVariable.StatusSuccess)

            if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabUploadDocument/button_Close'), 
                5, FailureHandling.OPTIONAL)) {
                'click button close'
                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabUploadDocument/button_Close'))

                CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '12.TabUploadDocument', 
                    0, GlobalVariable.NumofUploadDocument - 1, GlobalVariable.StatusWarning)
				
				'Write To Excel GlobalVariable.StatusReason'
				CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '12.TabUploadDocument',
					1, GlobalVariable.NumofUploadDocument - 1, GlobalVariable.StatusReasonMandatoryEmpty)
            }
        }
			
    }
}

'click button submit'
WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabUploadDocument/button_Submit'))

'click menu Customer data completion paging'
WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabUploadDocument/a_CUSTOMER DATA COMPLETION'))

'input app no'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabUploadDocument/input_Application No_AppNoId'), 
    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
        GlobalVariable.NumofColm, 13))

'click button search'
WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabUploadDocument/button_Search'))

'verify appno'
WebUI.verifyElementText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabUploadDocument/td_0002APP20211201128'), 
    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
        GlobalVariable.NumofColm, 13), FailureHandling.OPTIONAL)

public checkVerifyEqualOrMatch(Boolean isMatch){
	if(isMatch==false && GlobalVariable.FlagFailed==0){
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '12.TabUploadDocument',
				0, GlobalVariable.NumofUploadDocument-1, GlobalVariable.StatusFailed)

		CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '12.TabUploadDocument',
				1, GlobalVariable.NumofUploadDocument-1, GlobalVariable.ReasonFailedVerifyEqualOrMatch)

		GlobalVariable.FlagFailed=1
	}
}