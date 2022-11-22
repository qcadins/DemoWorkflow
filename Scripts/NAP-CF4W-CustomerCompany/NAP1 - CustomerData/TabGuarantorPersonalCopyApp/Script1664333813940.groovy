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

import groovy.sql.Sql
import internal.GlobalVariable as GlobalVariable

int flagWarning = 0

GlobalVariable.FlagFailed = 0

'get data file path'
GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'(GlobalVariable.PathCompany)

ArrayList<String> custnamefaileddelete = new ArrayList<>()

ArrayList<WebElement> variableData = DriverFactory.getWebDriver().findElements(By.cssSelector('#guarantor-tab > app-guarantor-main-data-paging > div > div:nth-child(2) > lib-ucgridview > div > table > tbody tr'))

if ((GlobalVariable.RoleCompany == 'Testing') && (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
    GlobalVariable.NumofColm, 8).length() > 1)) {
    'Connect database LOS'
    Sql sqlConnectionLOS = CustomKeywords.'dbConnection.connectDB.connectLOS'(urlLOS, username, password, driverclassname)

    ArrayList<String> listGuar = new ArrayList<String>()

    listGuar = CustomKeywords.'dbConnection.getInfoForEditNAP.getGuarantorDataforEditNAP'(sqlConnectionLOS, findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofColm, 
            8))

    ArrayList<Boolean> arrayMatch = new ArrayList<Boolean>()

    for (int guardt = 1; guardt <= variableData.size(); guardt++) {
        String result = listGuar.get(guardt - 1)

        resultarray = result.split(', ')

        'modify object guarantor name'
        modifyNewGuarantorName = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/FromTypeName'), 
            'xpath', 'equals', ('//*[@id="guarantor-tab"]/app-guarantor-main-data-paging/div/div[2]/lib-ucgridview/div/table/tbody/tr[' + 
            guardt) + ']/td[2]', true)

		'modify object guarantor type'
        modifyNewGuarantorType = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/FromTypeName'), 
            'xpath', 'equals', ('//*[@id="guarantor-tab"]/app-guarantor-main-data-paging/div/div[2]/lib-ucgridview/div/table/tbody/tr[' + 
            guardt) + ']/td[3]', true)

		'modify object guarantor relation'
        modifyNewGuarantorRelation = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/FromTypeName'), 
            'xpath', 'equals', ('//*[@id="guarantor-tab"]/app-guarantor-main-data-paging/div/div[2]/lib-ucgridview/div/table/tbody/tr[' + 
            guardt) + ']/td[4]', true)

		'verif guarantor name = result'
        arrayMatch.add(WebUI.verifyMatch(WebUI.getText(modifyNewGuarantorName), '(?i)' + (resultarray[0]), true))

		'verif guarantor type = result'
        arrayMatch.add(WebUI.verifyMatch(WebUI.getText(modifyNewGuarantorType), '(?i)' + (resultarray[1]), true))

		'verif guarantor relation = result'
        arrayMatch.add(WebUI.verifyMatch(WebUI.getText(modifyNewGuarantorRelation), '(?i)' + (resultarray[2]), true))
    }
    
    if (arrayMatch.contains(false)) {
		'write to excel status warning'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '3a.TabGuarantorDataPersonal', 
            0, GlobalVariable.CopyAppColm - 1, GlobalVariable.StatusWarning)

		'write to excel reason failed load data'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '3a.TabGuarantorDataPersonal', 
            1, GlobalVariable.CopyAppColm - 1, GlobalVariable.ReasonFailedLoadData)

		'write to excel status warning'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '3b.TabGuarantorDataCompany', 
            0, GlobalVariable.CopyAppColm - 1, GlobalVariable.StatusWarning)

		'write to excel reason failed load data'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '3b.TabGuarantorDataCompany', 
            1, GlobalVariable.CopyAppColm - 1, GlobalVariable.ReasonFailedLoadData)

		'flagwarning +1'
        (GlobalVariable.FlagWarning)++
    }
}

for (int i = 1; i <= variableData.size(); i++) {
    'modify object guarantor name'
    modifyNewGuarantorName = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/FromTypeName'), 
        'xpath', 'equals', ('//*[@id="guarantor-tab"]/app-guarantor-main-data-paging/div/div[2]/lib-ucgridview/div/table/tbody/tr[' + 
        i) + ']/td[2]', true)

    'modify object guarantor name'
    modifyNewGuarantorTypeName = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/FromTypeName'), 
        'xpath', 'equals', ('//*[@id="guarantor-tab"]/app-guarantor-main-data-paging/div/div[2]/lib-ucgridview/div/table/tbody/tr[' + 
        i) + ']/td[3]', true)
	
    'Loop Multiple guarantor data'
    for (GlobalVariable.NumofGuarantorPersonal = 2; GlobalVariable.NumofGuarantorPersonal <= (Integer.parseInt(GlobalVariable.CountAGuarantorPersonalCompany) + 
    1); (GlobalVariable.NumofGuarantorPersonal)++) {

        if (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(GlobalVariable.NumofGuarantorPersonal, 
            12) == findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofColm, 
            13)) {
		
            if (WebUI.verifyElementPresent(modifyNewGuarantorName, 5, FailureHandling.OPTIONAL)) {
				
                if (WebUI.getText(modifyNewGuarantorTypeName).equalsIgnoreCase('Personal')) {
					
                    if (WebUI.getText(modifyNewGuarantorName).equalsIgnoreCase(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
                            GlobalVariable.NumofGuarantorPersonal, 19)) || WebUI.getText(modifyNewGuarantorName).equalsIgnoreCase(
                        findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
                            GlobalVariable.NumofGuarantorPersonal, 16))) {
						
						'modify object button edit'
						modifyNewButtonEdit = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/FromTypeName'),
							'xpath', 'equals', ('//*[@id="guarantor-tab"]/app-guarantor-main-data-paging/div/div[2]/lib-ucgridview/div/table/tbody/tr[' +
							i) + ']/td[5]/span/span[1]/span/a/i', true)
					
						if (WebUI.verifyElementPresent(modifyNewButtonEdit, 5, FailureHandling.OPTIONAL)) {
                            'click button edit'
                            WebUI.click(modifyNewButtonEdit, FailureHandling.OPTIONAL)

                            if (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
                                GlobalVariable.NumofGuarantorPersonal, 13) == 'Input Data') {
							
								'click radio company'
								WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/Radio Company'))
								
                                'click radio personal'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/Radio Personal'))

                                'select guarantor relationship'
                                WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/select_GuarantorRelationship'), 
                                    findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
                                        GlobalVariable.NumofGuarantorPersonal, 18), false)

                                'input guarantor name'
                                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Guarantor Legal Name'), 
                                    findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
                                        GlobalVariable.NumofGuarantorPersonal, 19))

                                'input birth place'
                                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Birth Place'), 
                                    findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
                                        GlobalVariable.NumofGuarantorPersonal, 20))

                                'select id type'
                                WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/select_IDType'), 
                                    findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
                                        GlobalVariable.NumofGuarantorPersonal, 21), false)

                                'Verify ID Type'
                                if (((findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
                                    GlobalVariable.NumofGuarantorPersonal, 21) != 'E-KTP') && (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
                                    GlobalVariable.NumofGuarantorPersonal, 21) != 'NPWP')) && (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
                                    GlobalVariable.NumofGuarantorPersonal, 21) != 'AKTA')) {
                                    'input id expired date'
                                    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Id Expired Date'), 
                                        findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
                                            GlobalVariable.NumofGuarantorPersonal, 22))
                                }
                                
                                'select marital status'
                                WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/select_MaritalStatus'), 
                                    findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
                                        GlobalVariable.NumofGuarantorPersonal, 23), false)

                                'input mobile phone'
                                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Mobile Phone'), 
                                    findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
                                        GlobalVariable.NumofGuarantorPersonal, 24))

                                'select customer model'
                                WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/select_CustomerModel'), 
                                    findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
                                        GlobalVariable.NumofGuarantorPersonal, 25), false)

                                'select gender'
                                WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/select_Select One Female  Male'), 
                                    findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
                                        GlobalVariable.NumofGuarantorPersonal, 26), false)

                                'input birth date'
                                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Birth Date_form-control ng-untouched ng-pristine ng-invalid'), 
                                    findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
                                        GlobalVariable.NumofGuarantorPersonal, 27))

                                'input id no'
                                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Id No'), 
                                    findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
                                        GlobalVariable.NumofGuarantorPersonal, 28))

                                'input tax id'
                                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Tax Id No'), 
                                    findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
                                        GlobalVariable.NumofGuarantorPersonal, 29))

                                'input mother maiden'
                                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Mother Maiden Name'), 
                                    findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
                                        GlobalVariable.NumofGuarantorPersonal, 30))

                                'input email'
                                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Email'), 
                                    findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
                                        GlobalVariable.NumofGuarantorPersonal, 31))

                                'click button lookup department AML'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_DEPARTMENT AML_btn btn-raised btn-primary'))

                                'input department AML Code'
                                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Code_MasterCodeId'), 
                                    findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
                                        GlobalVariable.NumofGuarantorPersonal, 33))

                                'click button search'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_Search'))

                                'verify input error'
                                if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/a_Select'), 
                                    10, FailureHandling.OPTIONAL)) {
                                    'click select'
                                    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/a_Select'))
                                } else {
                                    'click X'
                                    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/Button_X'))

                                    'click button cancel'
                                    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/button_Cancel'))

                                    'write to excel failed'
                                    CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, 
                                        '3a.TabGuarantorDataPersonal', 0, GlobalVariable.NumofGuarantorPersonal - 1, GlobalVariable.StatusFailed)

                                    'Write To Excel GlobalVariable.StatusReasonLookup'
                                    CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, 
                                        '3a.TabGuarantorDataPersonal', 1, GlobalVariable.NumofGuarantorPersonal - 1, GlobalVariable.StatusReasonLookup)

                                    'Flagfailed +1 karena lookup gagal'
                                    (GlobalVariable.FlagFailed)++

                                    continue
                                }
                                
                                if (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
                                    GlobalVariable.NumofGuarantorPersonal, 36).length() > 1) {
                                    'click button lookup authority AML'
                                    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_Authority AML_btn btn-raised btn-primary'))

                                    'input authority AML code'
                                    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Code_MasterCodeId'), 
                                        findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
                                            GlobalVariable.NumofGuarantorPersonal, 35))

                                    'click button search'
                                    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_Search'))

                                    'verify input error'
                                    if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/a_Select'), 
                                        10, FailureHandling.OPTIONAL)) {
                                        'click select'
                                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/a_Select'))
                                    } else {
                                        'click X'
                                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/Button_X'))

                                        flagWarning++
                                    }
                                }
                                
                                if (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
                                    GlobalVariable.NumofGuarantorPersonal, 38) == 'Yes') {
                                    'click button copy'
                                    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_Copy'))
                                } else {
                                    'input text address'
                                    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/textarea_Address'), 
                                        findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
                                            GlobalVariable.NumofGuarantorPersonal, 39))

                                    'input RT'
                                    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_RT'), 
                                        findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
                                            GlobalVariable.NumofGuarantorPersonal, 40))

                                    'input RW'
                                    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_RW'), 
                                        findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
                                            GlobalVariable.NumofGuarantorPersonal, 41))

                                    'click button zipcode'
                                    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_Zipcode_btn btn-raised btn-primary'))

                                    'input zipcode'
                                    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Zipcode'), 
                                        findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
                                            GlobalVariable.NumofGuarantorPersonal, 42))

                                    'input Kecamatan'
                                    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input Kecamatan'), 
                                        findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
                                            GlobalVariable.NumofGuarantorPersonal, 43))

                                    'input Kelurahan'
                                    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input kelurahan'), 
                                        findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
                                            GlobalVariable.NumofGuarantorPersonal, 44))

                                    'input kota'
                                    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input kota'), 
                                        findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
                                            GlobalVariable.NumofGuarantorPersonal, 45))

                                    'click button search zipcode'
                                    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_SearchZipcode'))

                                    'verify input error'
                                    if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/a_Select'), 
                                        10, FailureHandling.OPTIONAL)) {
                                        'click select'
                                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/a_Select'))
                                    } else {
                                        'click X'
                                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/Button_X'))

                                        'click button cancel'
                                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/button_Cancel'))

                                        'write to excel failed'
                                        CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, 
                                            '3a.TabGuarantorDataPersonal', 0, GlobalVariable.NumofGuarantorPersonal - 1, 
                                            GlobalVariable.StatusFailed)

                                        'Write To Excel GlobalVariable.StatusReasonLookup'
                                        CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, 
                                            '3a.TabGuarantorDataPersonal', 1, GlobalVariable.NumofGuarantorPersonal - 1, 
                                            GlobalVariable.StatusReasonLookup)

                                        'Flagfailed +1 karena lookup gagal'
                                        (GlobalVariable.FlagFailed)++

                                        continue
                                    }
                                    
                                    'select ownership'
                                    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/select_Ownership'), 
                                        findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
                                            GlobalVariable.NumofGuarantorPersonal, 46), false)
                                }
                            } else if (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
                                GlobalVariable.NumofGuarantorPersonal, 13) == 'LookUp') {
                                if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_3Guarantor Data'), 
                                    5, FailureHandling.OPTIONAL)) {
								
									'click radio company'
									WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/Radio Company'))
								
                                    'click radio personal'
                                    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/Radio Personal'))

                                    'click guarantor lookup'
                                    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_Guarantor Legal Name_btn btn-raised btn-primary'))

                                    'input customer no'
                                    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Customer No_custNoId'), 
                                        findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
                                            GlobalVariable.NumofGuarantorPersonal, 15))

                                    'input customer name'
                                    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Customer Name_custNameId'), 
                                        findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
                                            GlobalVariable.NumofGuarantorPersonal, 16))

                                    'input id no'
                                    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Id No_IdNoId'), 
                                        findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
                                            GlobalVariable.NumofGuarantorPersonal, 17))

                                    'click search'
                                    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_Search'))

                                    'verify input error'
                                    if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/a_Select'), 
                                        10, FailureHandling.OPTIONAL)) {
                                        'click select'
                                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/a_Select'))
                                    } else {
                                        'click X'
                                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/Button_X'))

                                        'click button cancel'
                                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/button_Cancel'))

                                        'write to excel failed'
                                        CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, 
                                            '3a.TabGuarantorDataPersonal', 0, GlobalVariable.NumofGuarantorPersonal - 1, 
                                            GlobalVariable.StatusFailed)

                                        'Write To Excel GlobalVariable.StatusReasonLookup'
                                        CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, 
                                            '3a.TabGuarantorDataPersonal', 1, GlobalVariable.NumofGuarantorPersonal - 1, 
                                            GlobalVariable.StatusReasonLookup)

                                        'Flagfailed +1 karena lookup gagal'
                                        (GlobalVariable.FlagFailed)++

                                        continue
                                    }
                                    
                                    'check if role testing untuk get data customer digunakan untuk data verif'
                                    if (GlobalVariable.RoleCompany == 'Testing') {
                                        'call function get data guarantor'
                                        getDataGuarPersonal()

                                        'call test case Personal data verif'
                                        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP1 - CustomerData/TabGuarantorPersonalDataVerif'), 
                                            [:], FailureHandling.CONTINUE_ON_FAILURE)
                                    }
                                    
                                    'select guarantor relationship'
                                    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/select_GuarantorRelationship'), 
                                        findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
                                            GlobalVariable.NumofGuarantorPersonal, 18), false)

                                    if (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
                                        GlobalVariable.NumofGuarantorPersonal, 25).length() > 1) {
                                        'select customer model'
                                        WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/select_CustomerModel'), 
                                            findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
                                                GlobalVariable.NumofGuarantorPersonal, 25), false)
                                    }
                                    
                                    if (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
                                        GlobalVariable.NumofGuarantorPersonal, 34).length() > 1) {
                                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_DEPARTMENT AML_btn btn-raised btn-primary'))

                                        'input department AML Code'
                                        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Code_MasterCodeId'), 
                                            findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
                                                GlobalVariable.NumofGuarantorPersonal, 33))

                                        'click button search'
                                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_Search'))

                                        'verify input error'
                                        if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/a_Select'), 
                                            10, FailureHandling.OPTIONAL)) {
                                            'click select'
                                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/a_Select'))
                                        } else {
                                            'click X'
                                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/Button_X'))

                                            'click button cancel'
                                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/button_Cancel'))

                                            'write to excel failed'
                                            CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, 
                                                '3a.TabGuarantorDataPersonal', 0, GlobalVariable.NumofGuarantorPersonal - 
                                                1, GlobalVariable.StatusFailed)

                                            'Write To Excel GlobalVariable.StatusReasonLookup'
                                            CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, 
                                                '3a.TabGuarantorDataPersonal', 1, GlobalVariable.NumofGuarantorPersonal - 
                                                1, GlobalVariable.StatusReasonLookup)

                                            'Flagfailed +1 karena lookup gagal'
                                            (GlobalVariable.FlagFailed)++

                                            continue
                                        }
                                    }
                                    
                                    if (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
                                        GlobalVariable.NumofGuarantorPersonal, 36).length() > 1) {
                                        'click button lookup authority AML'
                                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_Authority AML_btn btn-raised btn-primary'))

                                        'input authority AML code'
                                        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Code_MasterCodeId'), 
                                            findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
                                                GlobalVariable.NumofGuarantorPersonal, 35))

                                        'click button search'
                                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_Search'))

                                        'verify input error'
                                        if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/a_Select'), 
                                            10, FailureHandling.OPTIONAL)) {
                                            'click select'
                                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/a_Select'))
                                        } else {
                                            'click X'
                                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/Button_X'))

                                            flagWarning++
                                        }
                                    }
                                }
                            }
                            
                            'check if role testing dan diinput dengan lookup untuk get data customer digunakan untuk Store DB Verif'
                            if ((GlobalVariable.RoleCompany == 'Testing') && (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
                                GlobalVariable.NumofGuarantorPersonal, 13) == 'LookUp')) {
                                'call function get data guar personal'
                                getDataGuarPersonal()

                                'add relationship to array'
                                GlobalVariable.Confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/select_GuarantorRelationship'), 
                                        'value'))
                            }
                            
                            'click button save'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_Save'))

                            if ((Integer.parseInt(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
                                    GlobalVariable.NumofGuarantorPersonal, 4)) == 0) && (GlobalVariable.FlagFailed == 0)) {
                                'Check alert'
                                GlobalVariable.FlagFailed = CustomKeywords.'checkSaveProcess.checkSaveProcess.checkAlert'(
                                    GlobalVariable.NumofGuarantorPersonal, '3a.TabGuarantorDataPersonal')
                            }
                            
                            if (GlobalVariable.FlagFailed == 0) {
                                'Check save Process write to excel'
                                CustomKeywords.'checkSaveProcess.checkSaveProcess.checkStatus'(Integer.parseInt(findTestData(
                                            'NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
                                            GlobalVariable.NumofGuarantorPersonal, 4)), findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/TableheaderGuarantor'), 
                                    GlobalVariable.NumofGuarantorPersonal, '3a.TabGuarantorDataPersonal')

                                'customer added +1'
                                (GlobalVariable.CountNumofCustomer)++

                                if (Integer.parseInt(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
                                        GlobalVariable.NumofGuarantorPersonal, 4)) == 0) {
                                    'Check error validasi'
                                    CustomKeywords.'checkSaveProcess.checkSaveProcess.checkValidasi'(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/errorvalidasi'), 
                                        GlobalVariable.NumofGuarantorPersonal, '3a.TabGuarantorDataPersonal')
                                }
                            }
                            
                            if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/button_Cancel'), 
                                5, FailureHandling.OPTIONAL)) {
                                'click button cancel'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/button_Cancel'))

                                'customer added -1'
                                (GlobalVariable.CountNumofCustomer)--
                            } else {
                                if ((flagWarning > 0) || (GlobalVariable.FlagWarning > 0)) {
                                    CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, 
                                        '3a.TabGuarantorDataPersonal', 0, GlobalVariable.NumofGuarantorPersonal - 1, GlobalVariable.StatusWarning)
                                }
                            }
                            
                            if (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
                                GlobalVariable.NumofGuarantorPersonal, 13) == 'Input Data') {
                                if ((GlobalVariable.RoleCompany == 'Testing') && (GlobalVariable.CheckVerifStoreDBCompany == 
                                'Yes')) {
                                    'call test case Guarantor personal data verif'
                                    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP1 - CustomerData/TabGuarantorDataPersonalStoreDBVerif'), 
                                        [:], FailureHandling.CONTINUE_ON_FAILURE)
                                }
                            } else if (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
                                GlobalVariable.NumofGuarantorPersonal, 13) == 'LookUp') {
                                if ((GlobalVariable.RoleCompany == 'Testing') && (GlobalVariable.CheckVerifStoreDBCompany == 
                                'Yes')) {
                                    'call test case Guarantor personal data verif'
                                    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP1 - CustomerData/TabGuarantorDataPersonalStoreDBVerif-LookUp'), 
                                        [:], FailureHandling.CONTINUE_ON_FAILURE)
                                }
                            }
                            
                            break
                        }
                    } else {
                        if (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
                            GlobalVariable.NumofGuarantorPersonal + 1, 12) != findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
                            GlobalVariable.NumofColm, 13)) {
						
							'modify object button delete'
							modifyNewButtonDelete = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/FromTypeName'),
								'xpath', 'equals', ('//*[@id="guarantor-tab"]/app-guarantor-main-data-paging/div/div[2]/lib-ucgridview/div/table/tbody/tr[' +
								i) + ']/td[5]/span/span[2]/span/a/i', true)
						
                            if (WebUI.verifyElementPresent(modifyNewButtonDelete, 5, FailureHandling.OPTIONAL)) {
                                'get cust name sebelum delete'
                                CustNameBefore = WebUI.getText(modifyNewGuarantorName)

                                'click button Delete'
                                WebUI.click(modifyNewButtonDelete, FailureHandling.OPTIONAL)

                                'accept alert'
                                WebUI.acceptAlert()

                                if (i == variableData.size()) {
                                    if (WebUI.verifyElementNotPresent(modifyNewGuarantorName, 5, FailureHandling.OPTIONAL)) {
                                        'count ulang table pada confins'
                                        variableData = DriverFactory.getWebDriver().findElements(By.cssSelector('#guarantor-tab > app-guarantor-main-data-paging > div > div:nth-child(2) > lib-ucgridview > div > table > tbody tr'))
                                    } else {
                                        'add cust name failed kedalam array'
                                        custnamefaileddelete.add(CustNameBefore)
                                    }
                                } else {
                                    'get cust name sebelum delete'
                                    CustNameAfter = WebUI.getText(modifyNewGuarantorName)

                                    if (WebUI.verifyNotMatch(CustNameAfter, CustNameBefore, false, FailureHandling.OPTIONAL)) {
                                        'count ulang table pada confins'
                                        variableData = DriverFactory.getWebDriver().findElements(By.cssSelector('#guarantor-tab > app-guarantor-main-data-paging > div > div:nth-child(2) > lib-ucgridview > div > table > tbody tr'))
                                    } else {
                                        'add cust name failed kedalam array'
                                        custnamefaileddelete.add(CustNameBefore)

                                        continue
                                    }
                                }
                                
                                i--
                            }
                        }
                    }
                } else {
                    break
                }
            }
        }
    }
}

if (custnamefaileddelete.size() > 0) {
    CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '3a.TabGuarantorDataPersonal', 
        0, GlobalVariable.CopyAppColm - 1, GlobalVariable.StatusWarning)

    CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '3a.TabGuarantorDataPersonal', 
        1, GlobalVariable.CopyAppColm - 1, GlobalVariable.ReasonFailedDelete + custnamefaileddelete)

    (GlobalVariable.FlagWarning)++
}

WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP1 - CustomerData/TabGuarantorCompanyCopyApp'), [:], FailureHandling.CONTINUE_ON_FAILURE)

WebUI.delay(5)

WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP1 - CustomerData/TabGuarantorData'), [:], FailureHandling.CONTINUE_ON_FAILURE)

def getDataGuarPersonal() {
    'declare array for confins data'
    def confinsdata = []

    'add customer name to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Guarantor Legal Name'), 
            'value'))

    'add birth place to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Birth Place'), 
            'value'))

    'add id expired date to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Id Expired Date'), 
            'value'))

    'add mobile phone to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Mobile Phone'), 
            'value'))

    'add birth date to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Birth Date_form-control ng-untouched ng-pristine ng-invalid'), 
            'value'))

    'add id no to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Id No'), 
            'value'))

    'add tax id to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Tax Id No'), 
            'value'))

    'add mother maiden name to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Mother Maiden Name'), 
            'value'))

    'add email to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Email'), 
            'value'))

    'add address to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/textarea_Address'), 
            'value'))

    'add RT to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_RT'), 
            'value'))

    'add RW to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_RW'), 
            'value'))

    'add zipcode to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/LabelZipcode'), 
            'value'))

    'add kelurahan to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/LabelKelurahan'), 
            'value'))

    'add kecamatan to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/LabelKecamatan'), 
            'value'))

    'add kota to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/LabelKota'), 
            'value'))

    'add id type to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/select_IDType'), 
            'value'))

    'add marital status to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/select_MaritalStatus'), 
            'value'))

    'add customer model to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/select_CustomerModel'), 
            'value'))

    'add gender to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/select_Select One Female  Male'), 
            'value'))

    'add ownership to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/select_Ownership'), 
            'value'))

    GlobalVariable.Confinsdata = confinsdata
}