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

String userDir = System.getProperty('user.dir')

String filePath = userDir + GlobalVariable.DataFileGuarantorCompany

GlobalVariable.DataFilePath = filePath

def LegalDocTypeArray = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorCompany/LegalDocument - Company - Guarantor').getValue(
    GlobalVariable.NumofGuarantor, 12).split(';')

def DocumentNoArray = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorCompany/LegalDocument - Company - Guarantor').getValue(
    GlobalVariable.NumofGuarantor, 13).split(';')

def DateIssuedArray = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorCompany/LegalDocument - Company - Guarantor').getValue(
    GlobalVariable.NumofGuarantor, 14).split(';')

def ExpiredDateArray = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorCompany/LegalDocument - Company - Guarantor').getValue(
    GlobalVariable.NumofGuarantor, 15).split(';')

def NotaryNameArray = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorCompany/LegalDocument - Company - Guarantor').getValue(
    GlobalVariable.NumofGuarantor, 16).split(';')

def NotaryLocationArray = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorCompany/LegalDocument - Company - Guarantor').getValue(
    GlobalVariable.NumofGuarantor, 17).split(';')

def NotesArray = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorCompany/LegalDocument - Company - Guarantor').getValue(
    GlobalVariable.NumofGuarantor, 18).split(';')

if (LegalDocTypeArray.size() > 0) {
    for (i = 1; i <= LegalDocTypeArray.size(); i++) {
        'click button add'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/LegalDocument - Company/button_Add'))

		if (i == 1) {
			if (GlobalVariable.Role == 'Testing') {
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
				WebUI.verifyOptionsPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/LegalDocument - Company/select_NIP  SIUP  TDP'),
					LegalDocType)

				'get total label from ddl'
				int totalLegaldoctypeddl = WebUI.getNumberOfTotalOption(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/LegalDocument - Company/select_NIP  SIUP  TDP'))

				'verify total ddl confins = total ddl db'
				WebUI.verifyEqual(totalLegaldoctypeddl, LegalDocType.size())
			}
		}
		
        'select legal doc type'
        WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/LegalDocument - Company/select_NIP  SIUP  TDP'), 
            LegalDocTypeArray[(i - 1)], false)

        'input doc no'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/LegalDocument - Company/input_Document No'), 
            DocumentNoArray[(i - 1)])

        'input date issued'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/LegalDocument - Company/input_Issued Date'), 
            DateIssuedArray[(i - 1)])

        if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorCompany/LegalDocument - Company - Guarantor').getValue(
            GlobalVariable.NumofGuarantor, 15).length() > 0) {
            'input expired date'
            WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/LegalDocument - Company/input_Expired Date'), 
                ExpiredDateArray[(i - 1)])
        }
        
        if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorCompany/LegalDocument - Company - Guarantor').getValue(
            GlobalVariable.NumofGuarantor, 16).length() > 0) {
            'input notary name'
            WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/LegalDocument - Company/input_Notary Name'), 
                NotaryNameArray[(i - 1)])
        }
        
        if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorCompany/LegalDocument - Company - Guarantor').getValue(
            GlobalVariable.NumofGuarantor, 17).length() > 0) {
            'input notary location'
            WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/LegalDocument - Company/input_Notary Location'), 
                NotaryLocationArray[(i - 1)])
        }
        
        if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorCompany/LegalDocument - Company - Guarantor').getValue(
            GlobalVariable.NumofGuarantor, 18).length() > 0) {
            'input Notes'
            WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/LegalDocument - Company/textarea_Notes'), 
                NotesArray[(i - 1)])
        }
        
        'click button save'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/LegalDocument - Company/button_Save'))

        if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/LegalDocument - Company/select_NIP  SIUP  TDP'), 
            2, FailureHandling.OPTIONAL)) {
            WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/LegalDocument - Company/button_Cancel'))
        }
    }
}

'click button save and continue'
WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/LegalDocument - Company/button_Save  Continue'))

Integer iscompleteMandatory = Integer.parseInt(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP4-CustomerDataCompletion/GuarantorCompany/LegalDocument - Company - Guarantor').getValue(
            GlobalVariable.NumofGuarantor, 4))
'Check save Process write to excel'
CustomKeywords.'checkSaveProcess.checkSaveProcess.checkStatus'(iscompleteMandatory, findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/OtherAttribute - Company/button_Debtor Group_btn btn-raised btn-primary'), 
    GlobalVariable.NumofGuarantor, '6.LegalDocument')
if(iscompleteMandatory==0){
	errorValObject = findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/div_errorvalidation')
	CustomKeywords.'checkSaveProcess.checkSaveProcess.checkValidasi'(errorValObject, GlobalVariable.NumofGuarantor, '6.LegalDocument')
}


if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/GuarantorCompany/LegalDocument - Company/th_Expired Date'), 
    10, FailureHandling.OPTIONAL)) {
    'click button back'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerDataCompletion/button_Back'))
}

