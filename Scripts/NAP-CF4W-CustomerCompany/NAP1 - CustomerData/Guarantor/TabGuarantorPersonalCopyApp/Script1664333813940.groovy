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
import groovy.sql.Sql as Sql
import internal.GlobalVariable as GlobalVariable

int flagWarning = 0

GlobalVariable.FlagFailed = 0

'get data file path'
GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'("\\Excel\\"+ GlobalVariable.LOB +"\\2.2 DataFile_NAP_"+ GlobalVariable.LOB +"_Company.xlsx")

'declare excelPathCustomerCompany'
excelPathCustomerCompany = 'NAP-'+ GlobalVariable.LOB +'-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData'

'declare excelPathGuarantorPersonal'
excelPathGuarantorPersonal = 'NAP-'+ GlobalVariable.LOB +'-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal'

'Klik tab guarantor'
WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP1-CustomerData/buttonTabGuarantor'))

'declare custnamefaileddelete arraylist'
ArrayList<String> custnamefaileddelete = new ArrayList<String>()

'count table guarantor row confins'
ArrayList<String> variableData = DriverFactory.getWebDriver().findElements(By.cssSelector('#guarantor-tab > app-guarantor-main-data-paging > div > div:nth-child(2) > lib-ucgridview > div > table > tbody tr'))

'chekc if role = testing & edit nap'
if ((GlobalVariable.RoleCompany == 'Testing') && (findTestData(excelPathCustomerCompany).getValue(GlobalVariable.NumofColm, 8).length() > 
1)) {
    'Connect database LOS'
    Sql sqlConnectionLOS = CustomKeywords.'dbConnection.connectDB.connectLOS'()

    ArrayList<String> listGuar = new ArrayList<String>()

    listGuar = CustomKeywords.'dbConnection.getInfoForEditNAP.getGuarantorDataforEditNAP'(sqlConnectionLOS, findTestData(excelPathCustomerCompany).getValue(
            GlobalVariable.NumofColm, 8))

    ArrayList<String> arrayMatch = new ArrayList<String>()

	int indexguar = 0
	
    for (int guardt = 1; guardt <= variableData.size(); guardt++) {
        String result = listGuar.get(guardt - 1)

        'modify object guarantor name'
        modifyNewGuarantorName = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/FromTypeName'), 
            'xpath', 'equals', ('//*[@id="guarantor-tab"]/app-guarantor-main-data-paging/div/div[2]/lib-ucgridview/div/table/tbody/tr[' + 
            guardt) + ']/td[2]', true)

        'modify object guarantor type'
        modifyNewGuarantorType = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/FromTypeName'), 
            'xpath', 'equals', ('//*[@id="guarantor-tab"]/app-guarantor-main-data-paging/div/div[2]/lib-ucgridview/div/table/tbody/tr[' + 
            guardt) + ']/td[3]', true)

        'modify object guarantor relation'
        modifyNewGuarantorRelation = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/FromTypeName'), 
            'xpath', 'equals', ('//*[@id="guarantor-tab"]/app-guarantor-main-data-paging/div/div[2]/lib-ucgridview/div/table/tbody/tr[' + 
            guardt) + ']/td[4]', true)

        'verif guarantor name = result'
        arrayMatch.add(WebUI.verifyMatch(WebUI.getText(modifyNewGuarantorName), '(?i)' + (listGuar[indexguar++]), true))

        'verif guarantor type = result'
        arrayMatch.add(WebUI.verifyMatch(WebUI.getText(modifyNewGuarantorType), '(?i)' + (listGuar[indexguar++]), true))

        'verif guarantor relation = result'
        arrayMatch.add(WebUI.verifyMatch(WebUI.getText(modifyNewGuarantorRelation), '(?i)' + (listGuar[indexguar++]), true))
    }
    
    if (arrayMatch.contains(false)) {

		'Write To Excel GlobalVariable.StatusWarning and GlobalVariable.ReasonFailedLoadData'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('3a.TabGuarantorDataPersonal', GlobalVariable.NumofGuarantorPersonal, GlobalVariable.StatusWarning, GlobalVariable.ReasonFailedLoadData)

		'Write To Excel GlobalVariable.StatusWarning and GlobalVariable.ReasonFailedLoadData'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('3b.TabGuarantorDataCompany', GlobalVariable.NumofGuarantorCompany, GlobalVariable.StatusWarning, GlobalVariable.ReasonFailedLoadData)

        'flagwarning +1'
        (GlobalVariable.FlagWarning)++
    }
}

'looping guarantor data confins'
for (int i = 1; i <= variableData.size(); i++) {
    'modify object guarantor name'
    modifyNewGuarantorName = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/FromTypeName'), 
        'xpath', 'equals', ('//*[@id="guarantor-tab"]/app-guarantor-main-data-paging/div/div[2]/lib-ucgridview/div/table/tbody/tr[' + 
        i) + ']/td[2]', true)

    'modify object guarantor name'
    modifyNewGuarantorTypeName = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/FromTypeName'), 
        'xpath', 'equals', ('//*[@id="guarantor-tab"]/app-guarantor-main-data-paging/div/div[2]/lib-ucgridview/div/table/tbody/tr[' + 
        i) + ']/td[3]', true)

    'Loop Multiple guarantor data'
    for (GlobalVariable.NumofGuarantorPersonal = GlobalVariable.StartIndex; GlobalVariable.NumofGuarantorPersonal <= (findTestData(excelPathGuarantorPersonal).getColumnNumbers() - 1); (GlobalVariable.NumofGuarantorPersonal)++) {
        if (findTestData(excelPathGuarantorPersonal).getValue(GlobalVariable.NumofGuarantorPersonal, 12) == findTestData(excelPathCustomerCompany).getValue(
            GlobalVariable.NumofColm, 13)) {
            if (WebUI.verifyElementPresent(modifyNewGuarantorName, GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                if (WebUI.getText(modifyNewGuarantorTypeName).equalsIgnoreCase('Personal')) {
                    if (WebUI.getText(modifyNewGuarantorName).equalsIgnoreCase(findTestData(excelPathGuarantorPersonal).getValue(GlobalVariable.NumofGuarantorPersonal, 
                            19)) || WebUI.getText(modifyNewGuarantorName).equalsIgnoreCase(findTestData(excelPathGuarantorPersonal).getValue(
                            GlobalVariable.NumofGuarantorPersonal, 16))) {
                        'modify object button edit'
                        modifyNewButtonEdit = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/FromTypeName'), 
                            'xpath', 'equals', ('//*[@id="guarantor-tab"]/app-guarantor-main-data-paging/div/div[2]/lib-ucgridview/div/table/tbody/tr[' + 
                            i) + ']/td[5]/span/span[1]/span/a/i', true)

                        'click button edit'
                        WebUI.click(modifyNewButtonEdit, FailureHandling.OPTIONAL)

                        if (findTestData(excelPathGuarantorPersonal).getValue(GlobalVariable.NumofGuarantorPersonal, 13) == 'Input Data') {
                            'click radio company'
                            WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/Radio Company'))

                            'click radio personal'
                            WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/Radio Personal'))

                            'select guarantor relationship'
                            WebUI.selectOptionByLabel(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/select_GuarantorRelationship'), 
                                findTestData(excelPathGuarantorPersonal).getValue(GlobalVariable.NumofGuarantorPersonal, 18), false)

                            'input guarantor name'
                            WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Guarantor Legal Name'), 
                                findTestData(excelPathGuarantorPersonal).getValue(GlobalVariable.NumofGuarantorPersonal, 19))

                            'input birth place'
                            WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Birth Place'), 
                                findTestData(excelPathGuarantorPersonal).getValue(GlobalVariable.NumofGuarantorPersonal, 20))

                            'select id type'
                            WebUI.selectOptionByLabel(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/select_IDType'), 
                                findTestData(excelPathGuarantorPersonal).getValue(GlobalVariable.NumofGuarantorPersonal, 21), false)

                            'Verify ID Type'
                            if (((findTestData(excelPathGuarantorPersonal).getValue(GlobalVariable.NumofGuarantorPersonal, 21) != 'E-KTP') && 
                            (findTestData(excelPathGuarantorPersonal).getValue(GlobalVariable.NumofGuarantorPersonal, 21) != 'NPWP')) && 
                            (findTestData(excelPathGuarantorPersonal).getValue(GlobalVariable.NumofGuarantorPersonal, 21) != 'AKTA')) {
                                'input id expired date'
                                WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Id Expired Date'), 
                                    findTestData(excelPathGuarantorPersonal).getValue(GlobalVariable.NumofGuarantorPersonal, 22))
                            }
                            
                            'select marital status'
                            WebUI.selectOptionByLabel(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/select_MaritalStatus'), 
                                findTestData(excelPathGuarantorPersonal).getValue(GlobalVariable.NumofGuarantorPersonal, 23), false)

                            'input mobile phone'
                            WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Mobile Phone'), 
                                findTestData(excelPathGuarantorPersonal).getValue(GlobalVariable.NumofGuarantorPersonal, 24))

                            'select customer model'
                            WebUI.selectOptionByLabel(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/select_CustomerModel'), 
                                findTestData(excelPathGuarantorPersonal).getValue(GlobalVariable.NumofGuarantorPersonal, 25), false)

                            'select gender'
                            WebUI.selectOptionByLabel(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/select_Select One Female  Male'), 
                                findTestData(excelPathGuarantorPersonal).getValue(GlobalVariable.NumofGuarantorPersonal, 26), false)

                            'input birth date'
                            WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Birth Date_form-control ng-untouched ng-pristine ng-invalid'), 
                                findTestData(excelPathGuarantorPersonal).getValue(GlobalVariable.NumofGuarantorPersonal, 27))

                            'input id no'
                            WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Id No'), 
                                findTestData(excelPathGuarantorPersonal).getValue(GlobalVariable.NumofGuarantorPersonal, 28))

                            'input tax id'
                            WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Tax Id No'), 
                                findTestData(excelPathGuarantorPersonal).getValue(GlobalVariable.NumofGuarantorPersonal, 29))

                            'input mother maiden'
                            WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Mother Maiden Name'), 
                                findTestData(excelPathGuarantorPersonal).getValue(GlobalVariable.NumofGuarantorPersonal, 30))

                            'input email'
                            WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Email'), 
                                findTestData(excelPathGuarantorPersonal).getValue(GlobalVariable.NumofGuarantorPersonal, 31))

                            'click button lookup department AML'
                            WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_DEPARTMENT AML_btn btn-raised btn-primary'))

                            'input department AML Code'
                            WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Code_MasterCodeId'), 
                                findTestData(excelPathGuarantorPersonal).getValue(GlobalVariable.NumofGuarantorPersonal, 33))

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

								'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.StatusReasonLookup'
								CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('3a.TabGuarantorDataPersonal', GlobalVariable.NumofGuarantorPersonal, GlobalVariable.StatusFailed, GlobalVariable.StatusReasonLookup)

                                'Flagfailed +1 karena lookup gagal'
                                (GlobalVariable.FlagFailed)++

                                continue
                            }
                            
                            if (findTestData(excelPathGuarantorPersonal).getValue(GlobalVariable.NumofGuarantorPersonal, 36).length() > 
                            1) {
                                'click button lookup authority AML'
                                WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_Authority AML_btn btn-raised btn-primary'))

                                'input authority AML code'
                                WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Code_MasterCodeId'), 
                                    findTestData(excelPathGuarantorPersonal).getValue(GlobalVariable.NumofGuarantorPersonal, 35))

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

                                    flagWarning++
                                }
                            }
                            
                            if (findTestData(excelPathGuarantorPersonal).getValue(GlobalVariable.NumofGuarantorPersonal, 38) == 'Yes') {
                                'click button copy'
                                WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_Copy'))
                            } else {
                                'input text address'
                                WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/textarea_Address'), 
                                    findTestData(excelPathGuarantorPersonal).getValue(GlobalVariable.NumofGuarantorPersonal, 39))

                                'input RT'
                                WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_RT'), 
                                    findTestData(excelPathGuarantorPersonal).getValue(GlobalVariable.NumofGuarantorPersonal, 40))

                                'input RW'
                                WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_RW'), 
                                    findTestData(excelPathGuarantorPersonal).getValue(GlobalVariable.NumofGuarantorPersonal, 41))

                                'click button zipcode'
                                WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_Zipcode_btn btn-raised btn-primary'))

                                'input zipcode'
                                WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Zipcode'), 
                                    findTestData(excelPathGuarantorPersonal).getValue(GlobalVariable.NumofGuarantorPersonal, 42))

                                'input Kecamatan'
                                WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input Kecamatan'), 
                                    findTestData(excelPathGuarantorPersonal).getValue(GlobalVariable.NumofGuarantorPersonal, 43))

                                'input Kelurahan'
                                WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input kelurahan'), 
                                    findTestData(excelPathGuarantorPersonal).getValue(GlobalVariable.NumofGuarantorPersonal, 44))

                                'input kota'
                                WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input kota'), 
                                    findTestData(excelPathGuarantorPersonal).getValue(GlobalVariable.NumofGuarantorPersonal, 45))

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

									'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.StatusReasonLookup'
									CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('3a.TabGuarantorDataPersonal', GlobalVariable.NumofGuarantorPersonal, GlobalVariable.StatusFailed, GlobalVariable.StatusReasonLookup)
									
                                    'Flagfailed +1 karena lookup gagal'
                                    (GlobalVariable.FlagFailed)++

                                    continue
                                }
                                
                                'select ownership'
                                WebUI.selectOptionByLabel(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/select_Ownership'), 
                                    findTestData(excelPathGuarantorPersonal).getValue(GlobalVariable.NumofGuarantorPersonal, 46), false)
                            }
                        } else if (findTestData(excelPathGuarantorPersonal).getValue(GlobalVariable.NumofGuarantorPersonal, 13) == 'LookUp') {
                            if (WebUI.verifyElementPresent(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_3Guarantor Data'), 
                                GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                                'click radio company'
                                WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/Radio Company'))

                                'click radio personal'
                                WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/Radio Personal'))

                                'click guarantor lookup'
                                WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_Guarantor Legal Name_btn btn-raised btn-primary'))

                                'input customer no'
                                WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Customer No_custNoId'), 
                                    findTestData(excelPathGuarantorPersonal).getValue(GlobalVariable.NumofGuarantorPersonal, 15))

                                'input customer name'
                                WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Customer Name_custNameId'), 
                                    findTestData(excelPathGuarantorPersonal).getValue(GlobalVariable.NumofGuarantorPersonal, 16))

                                'input id no'
                                WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Id No_IdNoId'), 
                                    findTestData(excelPathGuarantorPersonal).getValue(GlobalVariable.NumofGuarantorPersonal, 17))

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
                                    findTestData(excelPathGuarantorPersonal).getValue(GlobalVariable.NumofGuarantorPersonal, 18), false)

                                if (findTestData(excelPathGuarantorPersonal).getValue(GlobalVariable.NumofGuarantorPersonal, 25).length() > 
                                1) {
                                    'select customer model'
                                    WebUI.selectOptionByLabel(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/select_CustomerModel'), 
                                        findTestData(excelPathGuarantorPersonal).getValue(GlobalVariable.NumofGuarantorPersonal, 25), 
                                        false)
                                }
                                
                                if (findTestData(excelPathGuarantorPersonal).getValue(GlobalVariable.NumofGuarantorPersonal, 34).length() > 
                                1) {
                                    WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_DEPARTMENT AML_btn btn-raised btn-primary'))

                                    'input department AML Code'
                                    WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Code_MasterCodeId'), 
                                        findTestData(excelPathGuarantorPersonal).getValue(GlobalVariable.NumofGuarantorPersonal, 33))

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

										'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.StatusReasonLookup'
										CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('3a.TabGuarantorDataPersonal', GlobalVariable.NumofGuarantorPersonal, GlobalVariable.StatusFailed, GlobalVariable.StatusReasonLookup)

                                        'Flagfailed +1 karena lookup gagal'
                                        (GlobalVariable.FlagFailed)++

                                        continue
                                    }
                                }
                                
                                if (findTestData(excelPathGuarantorPersonal).getValue(GlobalVariable.NumofGuarantorPersonal, 36).length() > 
                                1) {
                                    'click button lookup authority AML'
                                    WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_Authority AML_btn btn-raised btn-primary'))

                                    'input authority AML code'
                                    WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Code_MasterCodeId'), 
                                        findTestData(excelPathGuarantorPersonal).getValue(GlobalVariable.NumofGuarantorPersonal, 35))

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

                                        flagWarning++
                                    }
                                }
                            }
                        }
                        
                        'check if role testing dan diinput dengan lookup untuk get data customer digunakan untuk Store DB Verif'
                        if ((GlobalVariable.RoleCompany == 'Testing') && (findTestData(excelPathGuarantorPersonal).getValue(GlobalVariable.NumofGuarantorPersonal, 
                            13) == 'LookUp')) {
                            'call function get data guar personal'
                            getDataGuarPersonal()

                            'add relationship to array'
                            GlobalVariable.Confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/select_GuarantorRelationship'), 
                                    'value'))
                        }
                        
                        'click button save'
                        WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_Save'))

                        if ((Integer.parseInt(findTestData(excelPathGuarantorPersonal).getValue(GlobalVariable.NumofGuarantorPersonal, 
                                4)) == 0) && (GlobalVariable.FlagFailed == 0)) {
                            'Check alert'
                            GlobalVariable.FlagFailed = CustomKeywords.'checkSaveProcess.checkSaveProcess.checkAlert'(GlobalVariable.NumofGuarantorPersonal, 
                                '3a.TabGuarantorDataPersonal')
                        }
                        
                        if (GlobalVariable.FlagFailed == 0) {
                            'Check save Process write to excel'
                            CustomKeywords.'checkSaveProcess.checkSaveProcess.checkStatus'(Integer.parseInt(findTestData(excelPathGuarantorPersonal).getValue(
                                        GlobalVariable.NumofGuarantorPersonal, 4)), findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/TableheaderGuarantor'), 
                                GlobalVariable.NumofGuarantorPersonal, '3a.TabGuarantorDataPersonal')

                            'customer added +1'
                            (GlobalVariable.CountNumofCustomer)++

                            if (Integer.parseInt(findTestData(excelPathGuarantorPersonal).getValue(GlobalVariable.NumofGuarantorPersonal, 
                                    4)) == 0) {
                                'Check error validasi'
                                CustomKeywords.'checkSaveProcess.checkSaveProcess.checkValidasi'(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/errorvalidasi'), 
                                    GlobalVariable.NumofGuarantorPersonal, '3a.TabGuarantorDataPersonal')
                            }
                        }
                        
                        if (WebUI.verifyElementPresent(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/button_Cancel'), 
                            GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                            'click button cancel'
                            WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/button_Cancel'))

							GlobalVariable.IsDataCancel = 1
							
                            'customer added -1'
                            (GlobalVariable.CountNumofCustomer)--
                        } else {
                            if ((flagWarning > 0) || (GlobalVariable.FlagWarning > 0)) {
                                CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '3a.TabGuarantorDataPersonal', 
                                    0, GlobalVariable.NumofGuarantorPersonal - 1, GlobalVariable.StatusWarning)
                            }
							
							
							if (findTestData(excelPathGuarantorPersonal).getValue(GlobalVariable.NumofGuarantorPersonal, 13) == 'Input Data') {
								if ((GlobalVariable.RoleCompany == 'Testing') && (GlobalVariable.CheckVerifStoreDBCompany ==
								'Yes')) {
									'call test case Guarantor personal data verif'
									WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP1 - CustomerData/Guarantor/TabGuarantorDataPersonalStoreDBVerif'),
										[:], FailureHandling.CONTINUE_ON_FAILURE)
								}
							} else if (findTestData(excelPathGuarantorPersonal).getValue(GlobalVariable.NumofGuarantorPersonal, 13) == 'LookUp') {
								if ((GlobalVariable.RoleCompany == 'Testing') && (GlobalVariable.CheckVerifStoreDBCompany ==
								'Yes')) {
									'call test case Guarantor personal data verif'
									WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP1 - CustomerData/Guarantor/TabGuarantorDataPersonalStoreDBVerif-LookUp'),
										[:], FailureHandling.CONTINUE_ON_FAILURE)
								}
							}
							
                        }
						
                        break
                    } else {
                        if (findTestData(excelPathGuarantorPersonal).getValue(GlobalVariable.NumofGuarantorPersonal + 1, 12) != findTestData(excelPathCustomerCompany).getValue(
                            GlobalVariable.NumofColm, 13)) {
                            'modify object button delete'
                            modifyNewButtonDelete = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/FromTypeName'), 
                                'xpath', 'equals', ('//*[@id="guarantor-tab"]/app-guarantor-main-data-paging/div/div[2]/lib-ucgridview/div/table/tbody/tr[' + 
                                i) + ']/td[5]/span/span[2]/span/a/i', true)

                            'get cust name sebelum delete'
                            CustNameBefore = WebUI.getText(modifyNewGuarantorName)

                            'click button Delete'
                            WebUI.click(modifyNewButtonDelete, FailureHandling.OPTIONAL)

                            'accept alert'
                            WebUI.acceptAlert()

                            if (i == variableData.size()) {
                                if (WebUI.verifyElementPresent(modifyNewGuarantorName, GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                                	'add cust name failed kedalam array'
                                	custnamefaileddelete.add(CustNameBefore)                                    
                                }
                            } else {
                                'get cust name sebelum delete'
                                CustNameAfter = WebUI.getText(modifyNewGuarantorName)

                                if (WebUI.verifyMatch(CustNameAfter, CustNameBefore, false, FailureHandling.OPTIONAL)) {
									'add cust name failed kedalam array'
									custnamefaileddelete.add(CustNameBefore)
                                }
                            }
                            
                            'count ulang table pada confins setelah delete guarantor'
                            variableData = DriverFactory.getWebDriver().findElements(By.cssSelector('#guarantor-tab > app-guarantor-main-data-paging > div > div:nth-child(2) > lib-ucgridview > div > table > tbody tr'))
							
                            i--
                        }
                    }
                } 
            }
        }else {
               break
        }
    }
}

if (custnamefaileddelete.size() > 0) {
	
	'Write To Excel GlobalVariable.StatusWarning and GlobalVariable.ReasonFailedDelete'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('3a.TabGuarantorDataPersonal', GlobalVariable.StartIndex, GlobalVariable.StatusWarning, GlobalVariable.ReasonFailedDelete + custnamefaileddelete)

    (GlobalVariable.FlagWarning)++
}

WebUI.delay(5)

'call test case tab guarantor personal'
WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP1 - CustomerData/Guarantor/TabGuarantorPersonal'), [:], FailureHandling.CONTINUE_ON_FAILURE)

def getDataGuarPersonal() {
    'declare array for confins data'
    def confinsdata = []

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

    GlobalVariable.Confinsdata = confinsdata
}