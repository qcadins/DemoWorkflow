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
import groovy.sql.Sql as Sql
import org.openqa.selenium.support.ui.Select as Select

int flagWarning = 0

GlobalVariable.FlagFailed = 0

'get data file path'
GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'(GlobalVariable.PathPersonal)

'declare datafileCustomerPersonal'
datafileCustomerPersonal = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData')

'declare datafileTabGuarantorPersonal'
datafileTabGuarantorPersonal = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabGuarantorDataPersonal')

'declare custnamefaileddelete'
ArrayList<String> custnamefaileddelete = new ArrayList<String>()

'declare variabledata'
ArrayList<String> variableData = DriverFactory.getWebDriver().findElements(By.cssSelector('#guarantor-tab > app-guarantor-main-data-paging > div > div:nth-child(2) > lib-ucgridview > div > table > tbody tr'))

if ((GlobalVariable.Role == 'Testing') && (datafileCustomerPersonal.getValue(GlobalVariable.NumofColm, 
    8).length() > 1)) {
    'connect DB LOS'
    Sql sqlConnectionLOS = CustomKeywords.'dbConnection.connectDB.connectLOS'()

	'declare listguar'
    ArrayList<String> listGuar = new ArrayList<String>()

	'get guardata for edit nap from db'
    listGuar = CustomKeywords.'dbConnection.getInfoForEditNAP.getGuarantorDataforEditNAP'(sqlConnectionLOS, datafileCustomerPersonal.getValue(GlobalVariable.NumofColm, 
            8))

	'declare arraymatch'
    ArrayList<String> arrayMatch = new ArrayList<String>()

	'looping tabel guarantor confins'
    for (int guardt = 1; guardt <= variableData.size(); guardt++) {
		'declare result'
        String result = listGuar.get(guardt - 1)

        resultarray = result.split(', ')

        'modify object guarantor name'
        modifyNewGuarantorName = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/FromTypeName'), 
            'xpath', 'equals', ('//*[@id="guarantor-tab"]/app-guarantor-main-data-paging/div/div[2]/lib-ucgridview/div/table/tbody/tr[' + 
            guardt) + ']/td[2]', true)

		'modify object guarantor type'
        modifyNewGuarantorType = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/FromTypeName'), 
            'xpath', 'equals', ('//*[@id="guarantor-tab"]/app-guarantor-main-data-paging/div/div[2]/lib-ucgridview/div/table/tbody/tr[' + 
            guardt) + ']/td[3]', true)

		'modify guarantor relation'
        modifyNewGuarantorRelation = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/FromTypeName'), 
            'xpath', 'equals', ('//*[@id="guarantor-tab"]/app-guarantor-main-data-paging/div/div[2]/lib-ucgridview/div/table/tbody/tr[' + 
            guardt) + ']/td[4]', true)

		'verif guarantor name confins x db'
        arrayMatch.add(WebUI.verifyMatch(WebUI.getText(modifyNewGuarantorName), '(?i)' + (resultarray[0]), true))

		'verif guarantor type confins x db'
        arrayMatch.add(WebUI.verifyMatch(WebUI.getText(modifyNewGuarantorType), '(?i)' + (resultarray[1]), true))

		'verif guarantor relation confins x db'
        arrayMatch.add(WebUI.verifyMatch(WebUI.getText(modifyNewGuarantorRelation), '(?i)' + (resultarray[2]), true))
    }
    
	'Jika ada verif data yang tidak sesuai'
    if (arrayMatch.contains(false)) {

		'Write To Excel GlobalVariable.StatusWarning and GlobalVariable.ReasonFailedLoadData'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('3a.TabGuarantorDataPersonal', GlobalVariable.StartIndex, GlobalVariable.StatusWarning, GlobalVariable.ReasonFailedLoadData)

		'Write To Excel GlobalVariable.StatusWarning and GlobalVariable.ReasonFailedLoadData'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('3b.TabGuarantorDataCompany', GlobalVariable.StartIndex, GlobalVariable.StatusWarning, GlobalVariable.ReasonFailedLoadData)
		
        (GlobalVariable.FlagWarning)++
    }
}

'looping tabel guarantor confins'
for (int i = 1; i <= variableData.size(); i++) {
    'modify object guarantor name'
    modifyNewGuarantorName = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/FromTypeName'), 
        'xpath', 'equals', ('//*[@id="guarantor-tab"]/app-guarantor-main-data-paging/div/div[2]/lib-ucgridview/div/table/tbody/tr[' + 
        i) + ']/td[2]', true)

    'modify object guarantor name'
    modifyNewGuarantorTypeName = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/FromTypeName'), 
        'xpath', 'equals', ('//*[@id="guarantor-tab"]/app-guarantor-main-data-paging/div/div[2]/lib-ucgridview/div/table/tbody/tr[' + 
        i) + ']/td[3]', true)

    'Loop Multiple guarantor data personal excel'
    for (GlobalVariable.NumofGuarantorPersonal = 2; GlobalVariable.NumofGuarantorPersonal <= (Integer.parseInt(GlobalVariable.CountAGuarantorPersonal) + 
    1); (GlobalVariable.NumofGuarantorPersonal)++) {
        if (datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 12) == datafileCustomerPersonal.getValue(
            GlobalVariable.NumofColm, 13)) {
			'Jika ada guarantor name pada confins'
            if (WebUI.verifyElementPresent(modifyNewGuarantorName, GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
				'Jika guarantor type bernilai personal'
                if (WebUI.getText(modifyNewGuarantorTypeName).equalsIgnoreCase('Personal')) {
					'Jika guarantor name pada confins = guarantor name pada excel / excel lookup'
                    if (WebUI.getText(modifyNewGuarantorName).equalsIgnoreCase(datafileTabGuarantorPersonal.getValue(
                            GlobalVariable.NumofGuarantorPersonal, 19)) || WebUI.getText(modifyNewGuarantorName).equalsIgnoreCase(
                        datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 16))) {
					
                        'modify object button edit'
                        modifyNewButtonEdit = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/FromTypeName'), 
                            'xpath', 'equals', ('//*[@id="guarantor-tab"]/app-guarantor-main-data-paging/div/div[2]/lib-ucgridview/div/table/tbody/tr[' + 
                            i) + ']/td[5]/span/span[1]/span/a/i', true)

                        'click button edit'
                        WebUI.click(modifyNewButtonEdit, FailureHandling.OPTIONAL)

                        if (datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                            13) == 'Input Data') {
                            'click radio company'
                            WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataCompany/Radio Company'))

                            'click radio personal'
                            WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/Radio Personal'))

                            'select guarantor relationship'
                            WebUI.selectOptionByLabel(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/select_GuarantorRelationship'), 
                                datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                                    18), false)

                            'input guarantor name'
                            WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Guarantor Legal Name'), 
                                datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                                    19))

                            'input birth place'
                            WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Birth Place'), 
                                datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                                    20))

                            'select id type'
                            WebUI.selectOptionByLabel(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/select_IDType'), 
                                datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                                    21), false)

                            'Verify ID Type'
                            if (((datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                                21) != 'E-KTP') && (datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                                21) != 'NPWP')) && (datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                                21) != 'AKTA')) {
                                'input id expired date'
                                WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Id Expired Date'), 
                                    datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                                        22))
                            }
                            
                            'select marital status'
                            WebUI.selectOptionByLabel(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/select_MaritalStatus'), 
                                datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                                    23), false)

                            'input mobile phone'
                            WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Mobile Phone'), 
                                datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                                    24))

                            'select customer model'
                            WebUI.selectOptionByLabel(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/select_CustomerModel'), 
                                datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                                    25), false)

                            'select gender'
                            WebUI.selectOptionByLabel(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/select_Select One Female  Male'), 
                                datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                                    26), false)

                            'input birth date'
                            WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Birth Date_form-control ng-untouched ng-pristine ng-invalid'), 
                                datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                                    27))

                            'input id no'
                            WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Id No'), 
                                datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                                    28))

                            'input tax id'
                            WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Tax Id No'), 
                                datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                                    29))

                            'input mother maiden'
                            WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Mother Maiden Name'), 
                                datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                                    30))

                            'input email'
                            WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Email'), 
                                datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                                    31))

                            'click button lookup department AML'
                            WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_DEPARTMENT AML_btn btn-raised btn-primary'))

                            'input department AML Code'
                            WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Code_MasterCodeId'), 
                                datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                                    33))

                            'click button search'
                            WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_Search'))

                            'verify input error'
                            if (WebUI.verifyElementPresent(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/a_Select'), 
                                GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                                'click select'
                                WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/a_Select'))
                            } else {
                                'click X'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabNewApplication/Button_X'))

                                'click button cancel'
                                WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_Cancel'))

								'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.StatusReasonLookup'
								CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('3a.TabGuarantorDataPersonal', GlobalVariable.NumofGuarantorPersonal, GlobalVariable.StatusFailed, GlobalVariable.StatusReasonLookup)

                                GlobalVariable.FlagFailed = 1

                                continue
                            }
                            
							'Jika authority aml tidak kosong'
                            if (datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                                36).length() > 1) {
                                'click button lookup authority AML'
                                WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_Authority AML_btn btn-raised btn-primary'))

                                'input authority AML code'
                                WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Code_MasterCodeId'), 
                                    datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                                        35))

                                'click button search'
                                WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_Search'))

                                'verify input error'
                                if (WebUI.verifyElementPresent(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/a_Select'), 
                                    GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                                    'click select'
                                    WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/a_Select'))
                                } else {
                                    'click X'
                                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabNewApplication/Button_X'))

                                    flagWarning++
                                }
                            }
                            'Jika copy address yes'
                            if (datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                                38) == 'Yes') {
                                'click button copy'
                                WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_Copy'))
								
								getDataGuarPersonal()
                            } //jika copy address no
							else if (datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                                38) == 'No') {
                                'input text address'
                                WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/textarea_Address'), 
                                    datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                                        39))

                                'input RT'
                                WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_RT'), 
                                    datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                                        40))

                                'input RW'
                                WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_RW'), 
                                    datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                                        41))

                                'click button zipcode'
                                WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_Zipcode_btn btn-raised btn-primary'))

                                'input zipcode'
                                WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Zipcode'), 
                                    datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                                        42))

                                'input Kecamatan'
                                WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input Kecamatan'), 
                                    datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                                        43))

                                'input Kelurahan'
                                WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input kelurahan'), 
                                    datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                                        44))

                                'input kota'
                                WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input kota'), 
                                    datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                                        45))

                                'click button search zipcode'
                                WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_SearchZipcode'))

                                'verify input error'
                                if (WebUI.verifyElementPresent(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/a_Select'), 
                                    GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                                    'click select'
                                    WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/a_Select'))
                                } else {
                                    'click X'
                                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabNewApplication/Button_X'))

                                    'click button cancel'
                                    WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_Cancel'))

									'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.StatusReasonLookup'
									CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('3a.TabGuarantorDataPersonal', GlobalVariable.NumofGuarantorPersonal, GlobalVariable.StatusFailed, GlobalVariable.StatusReasonLookup)

                                    GlobalVariable.FlagFailed = 1

                                    continue
                                }
                                
                                'select ownership'
                                WebUI.selectOptionByLabel(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/select_Ownership'), 
                                    datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                                        46), false)
                            }
                        } else if (datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                            13) == 'LookUp') {
                            if (WebUI.verifyElementPresent(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_3Guarantor Data'), 
                                GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                                'click radio personal'
                                WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/Radio Personal'))

                                'click guarantor lookup'
                                WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_Guarantor Legal Name_btn btn-raised btn-primary'))

                                'input customer no'
                                WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Customer No_custNoId'), 
                                    datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                                        15))

                                'input customer name'
                                WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Customer Name_custNameId'), 
                                    datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                                        16))

                                'input id no'
                                WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Id No_IdNoId'), 
                                    datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                                        17))

                                'click search'
                                WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_Search'))

                                'verify input error'
                                if (WebUI.verifyElementPresent(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/a_Select'), 
                                    GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                                    'click select'
                                    WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/a_Select'))
                                } else {
                                    'click X'
                                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabNewApplication/Button_X'))

                                    'click button cancel'
                                    WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_Cancel'))

									'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.StatusReasonLookup'
									CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('3a.TabGuarantorDataPersonal', GlobalVariable.NumofGuarantorPersonal, GlobalVariable.StatusFailed, GlobalVariable.StatusReasonLookup)
									
                                    GlobalVariable.FlagFailed = 1

                                    continue
                                }
                                
                                'check if role testing untuk get data customer digunakan untuk data verif'
                                if (GlobalVariable.Role == 'Testing') {
                                    'call function get data guarantor'
                                    getDataGuarPersonal()

                                    'call test case Personal data verif'
                                    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP1 - Customer Data/TabGuarantorPersonalDataVerif'), 
                                        [:], FailureHandling.CONTINUE_ON_FAILURE)
                                }
                                
                                'select guarantor relationship'
                                WebUI.selectOptionByLabel(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/select_GuarantorRelationship'), 
                                    datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                                        18), false)

								'Jika customer model tidak kosong'
                                if (datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                                    25).length() > 1) {
                                    'select customer model'
                                    WebUI.selectOptionByLabel(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/select_CustomerModel'), 
                                        datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                                            25), false)
                                }
                                
								'jika department aml tidak kosong'
                                if (datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                                    34).length() > 1) {
									'klik button lookup department aml'
                                    WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_DEPARTMENT AML_btn btn-raised btn-primary'))

                                    'input department AML Code'
                                    WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Code_MasterCodeId'), 
                                        datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                                            33))

                                    'click button search'
                                    WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_Search'))

                                    'verify input error'
                                    if (WebUI.verifyElementPresent(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/a_Select'), 
                                        GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                                        'click select'
                                        WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/a_Select'))
                                    } else {
                                        'click X'
                                        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabNewApplication/Button_X'))

                                        'click button cancel'
                                        WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_Cancel'))

										'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.StatusReasonLookup'
										CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('3a.TabGuarantorDataPersonal', GlobalVariable.NumofGuarantorPersonal, GlobalVariable.StatusFailed, GlobalVariable.StatusReasonLookup)

                                        GlobalVariable.FlagFailed = 1

                                        continue
                                    }
                                }
                                
								'jika authority aml tidak kosong'
                                if (datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                                    36).length() > 1) {
                                    'click button lookup authority AML'
                                    WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_Authority AML_btn btn-raised btn-primary'))

                                    'input authority AML code'
                                    WebUI.setText(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/input_Code_MasterCodeId'), 
                                        datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                                            35))

                                    'click button search'
                                    WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_Search'))

                                    'verify input error'
                                    if (WebUI.verifyElementPresent(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/a_Select'), 
                                        GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                                        'click select'
                                        WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/a_Select'))
                                    } else {
                                        'click X'
                                        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabNewApplication/Button_X'))

                                        flagWarning++
                                    }
                                }
                            }
                        }
                        
                        'check if role testing dan diinput dengan lookup untuk get data customer digunakan untuk Store DB Verif'
                        if ((GlobalVariable.Role == 'Testing') && (datafileTabGuarantorPersonal.getValue(
                            GlobalVariable.NumofGuarantorPersonal, 13) == 'LookUp')) {
                            'call function get data guar personal'
                            getDataGuarPersonal()

                            'add relationship to array'
                            GlobalVariable.Confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/select_GuarantorRelationship'), 
                                    'value'))
                        }
                        
                        'click button save'
                        WebUI.click(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_Save'))

                        if ((Integer.parseInt(datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                                4)) == 0) && (GlobalVariable.FlagFailed == 0)) {
                            'Check alert'
                            GlobalVariable.FlagFailed = CustomKeywords.'checkSaveProcess.checkSaveProcess.checkAlert'(GlobalVariable.NumofGuarantorPersonal, 
                                '3a.TabGuarantorDataPersonal')
                        }
                        
                        if (GlobalVariable.FlagFailed == 0) {
                            'Check save Process write to excel'
                            CustomKeywords.'checkSaveProcess.checkSaveProcess.checkStatus'(Integer.parseInt(datafileTabGuarantorPersonal.getValue(
                                        GlobalVariable.NumofGuarantorPersonal, 4)), findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/TableheaderGuarantor'), 
                                GlobalVariable.NumofGuarantorPersonal, '3a.TabGuarantorDataPersonal')

                            'customer added +1'
                            (GlobalVariable.CountNumofCustomer)++

                            if (Integer.parseInt(datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                                    4)) == 0) {
                                'Check error validasi'
                                CustomKeywords.'checkSaveProcess.checkSaveProcess.checkValidasi'(findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/errorvalidasi'), 
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
								'write to excel status warning'
                                CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '3a.TabGuarantorDataPersonal', 
                                    0, GlobalVariable.NumofGuarantorPersonal - 1, GlobalVariable.StatusWarning)
                            }
                        }
                        
                        if (datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                            13) == 'Input Data') {
                            if ((GlobalVariable.Role == 'Testing') && (GlobalVariable.CheckVerifStoreDBPersonal == 'Yes')) {
                                'call test case company data store verif'
                                WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP1 - Customer Data/TabGuarantorDataPersonalStoreDBVerif'), 
                                    [:], FailureHandling.CONTINUE_ON_FAILURE)
                            }
                        } else if (datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal, 
                            13) == 'LookUp') {
                            if ((GlobalVariable.Role == 'Testing') && (GlobalVariable.CheckVerifStoreDBPersonal == 'Yes')) {
                                'call test case Guarantor personal data verif'
                                WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP1 - Customer Data/TabGuarantorDataPersonalStoreDBVerif-LookUp'), 
                                    [:], FailureHandling.CONTINUE_ON_FAILURE)
                            }
                        }
                        
                        break
                    } else {
						'Jika appno pada excel guarantor personal tidak sama dengan appno pada excel customermaindata'
                        if (datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal + 
                            1, 12) != datafileCustomerPersonal.getValue(GlobalVariable.NumofColm, 13)) {
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

							'Jika guarantor merupakan index terakhir'
                            if (i == variableData.size()) {
								'jika tidak muncul guarantor name'
                                if (WebUI.verifyElementPresent(modifyNewGuarantorName, GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
									'add cust name failed kedalam array'
									custnamefaileddelete.add(CustNameBefore)
                                }
                            } else {
                                'get cust name sebelum delete'
                                CustNameAfter = WebUI.getText(modifyNewGuarantorName)

								'Jika guarantor name after delete tidak sama dengan guarantor name before delete'
                                if (WebUI.verifyMatch(CustNameAfter, CustNameBefore, false, FailureHandling.OPTIONAL)) {
                                	'add cust name failed kedalam array'
                                	custnamefaileddelete.add(CustNameBefore)
                                }
                            }
                            
							'count ulang table guarantor setelah delete'
                            variableData = DriverFactory.getWebDriver().findElements(By.cssSelector('#guarantor-tab > app-guarantor-main-data-paging > div > div:nth-child(2) > lib-ucgridview > div > table > tbody tr'))
							
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

'Jika ada delete guarantor gagal'
if (custnamefaileddelete.size() > 0) {

	'Write To Excel GlobalVariable.StatusWarning and GlobalVariable.ReasonFailedDelete'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('3a.TabGuarantorDataPersonal', GlobalVariable.StartIndex, GlobalVariable.StatusWarning, GlobalVariable.ReasonFailedDelete + custnamefaileddelete)
	
    (GlobalVariable.FlagWarning)++
}

'call test case guarantorcompanycopyapp'
WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP1 - Customer Data/TabGuarantorCompanyCopyApp'), [:], FailureHandling.CONTINUE_ON_FAILURE)

WebUI.delay(5)

'call test case guarantordata'
WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP1 - Customer Data/TabGuarantorData'), [:], FailureHandling.CONTINUE_ON_FAILURE)

def getDataGuarPersonal() {
    'declare array for confins data'
    def confinsdata = []

	if(datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal,13) == 'LookUp'){
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

	if(datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal,13) == 'LookUp'){
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
	    confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/select_Select One Female  Male'), 
	            'value'))
	
	    'add ownership to array'
	    confinsdata.add(WebUI.getAttribute(findTestObject('NAP/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/select_Ownership'), 
	            'value'))
	
	}
	else if(datafileTabGuarantorPersonal.getValue(GlobalVariable.NumofGuarantorPersonal,13) == 'Input Data'){
		Select select = new Select(DriverFactory.getWebDriver().findElement(By.xpath("//div[@id='Address']/div/div[2]/div[2]/div/div/div/div/select")))
		String optionLabel = select.getFirstSelectedOption().getText()
		
		'add ownership to array'
		confinsdata.add(optionLabel)
		
	}

    GlobalVariable.Confinsdata = confinsdata
}