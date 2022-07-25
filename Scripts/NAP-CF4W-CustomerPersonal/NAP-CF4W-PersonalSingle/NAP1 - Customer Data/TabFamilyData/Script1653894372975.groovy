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
int flagWarning =0
if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/button_2Family Data'),5, 
    FailureHandling.OPTIONAL)) {
    'Loop Multiple family data'
    for (GlobalVariable.NumofFamily = 2; GlobalVariable.NumofFamily <= (Integer.parseInt(GlobalVariable.CountAFamily) + 
    1); (GlobalVariable.NumofFamily)++) {
        if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData').getValue(
            GlobalVariable.NumofFamily, 2) == findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
            GlobalVariable.NumofColm, 3)) {
            if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData').getValue(
                GlobalVariable.NumofFamily, 3) == 'Input Data') {
                if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/button_2Family Data'),5, 
                    FailureHandling.OPTIONAL)) {
                    'click button add'
                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/button_Add'))

                    'select customer relation'
                    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/select_CustomerRelation'), 
                        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData').getValue(
                            GlobalVariable.NumofFamily, 8), false)

                    'input family name'
                    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/input_Family Legal Name_form-control ng-untouched ng-pristine ng-invalid'), 
                        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData').getValue(
                            GlobalVariable.NumofFamily, 9))

                    'input birth place'
                    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/input_Birth Place_form-control ng-untouched ng-pristine ng-invalid'), 
                        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData').getValue(
                            GlobalVariable.NumofFamily, 10))

                    'select idtype'
                    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/select_IDType'), 
                        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData').getValue(
                            GlobalVariable.NumofFamily, 11), false)

                    'input Id Expired Date'
                    if (((findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData').getValue(
                        GlobalVariable.NumofFamily, 11) != 'E-KTP') && (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData').getValue(
                        GlobalVariable.NumofFamily, 11) != 'NPWP')) && (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData').getValue(
                        GlobalVariable.NumofFamily, 11) != 'AKTA')) {
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/input_Id Expired Date'), 
                            findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData').getValue(
                                GlobalVariable.NumofFamily, 12))
                    }
                    
                    'select marital status'
                    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/select_Marital Status'), 
                        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData').getValue(
                            GlobalVariable.NumofFamily, 13), false)

                    'input mobile phone'
                    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/input_Mobile Phone_form-control ng-untouched ng-pristine ng-invalid'), 
                        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData').getValue(
                            GlobalVariable.NumofFamily, 14))

                    'select customer model'
                    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/select_CustomerModel'), 
                        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData').getValue(
                            GlobalVariable.NumofFamily, 15), false)

                    if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData').getValue(
                        GlobalVariable.NumofFamily, 17).length() > 1) {
                        'click lookup button proffession name'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/button_Profession Name_btn btn-raised btn-primary'))

                        'input profession code'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/input_Profession Code_professionCodeId'), 
                            findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData').getValue(
                                GlobalVariable.NumofFamily, 16))

                        'click button search'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/button_Search'))

                        'verify input error'
						if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/a_Select'), 10,
							FailureHandling.OPTIONAL)) {
							'click select'
							WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/a_Select'))
						}
						else{
							'click X'
							WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabNewApplication/Button_X'))
							flagWarning++
							continue
						}
                    }
                    
                    'input employee establishment date'
                    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/input_Employee Establishment Date'), 
                        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData').getValue(
                            GlobalVariable.NumofFamily, 18))

                    'select gender'
                    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/select_Gender'), 
                        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData').getValue(
                            GlobalVariable.NumofFamily, 19), false)

                    'input birth date'
                    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/input_Birth Date_form-control ng-untouched ng-pristine ng-invalid'), 
                        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData').getValue(
                            GlobalVariable.NumofFamily, 20))

                    'input id no'
                    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/input_Id No_form-control ng-untouched ng-pristine ng-invalid'), 
                        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData').getValue(
                            GlobalVariable.NumofFamily, 21))

                    'input tax id'
                    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/input_Tax Id No_form-control ng-untouched ng-pristine ng-valid'), 
                        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData').getValue(
                            GlobalVariable.NumofFamily, 22))

                    'input mother maiden name'
                    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/input_Mother Maiden Name_form-control ng-untouched ng-pristine ng-invalid'), 
                        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData').getValue(
                            GlobalVariable.NumofFamily, 23))

                    'input email'
                    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/input_Email_form-control ng-untouched ng-pristine ng-invalid'), 
                        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData').getValue(
                            GlobalVariable.NumofFamily, 24))

                    'select nationality'
                    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/select_nationality'), 
                        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData').getValue(
                            GlobalVariable.NumofFamily, 25), false)

                    if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData').getValue(
                        GlobalVariable.NumofFamily, 25) == 'Foreigner') {
                        'click button lookup country'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/button_INDONESIA_btn btn-raised btn-primary'))

                        'input country code'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/input_Country Code_countryCodeId'), 
                            findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData').getValue(
                                GlobalVariable.NumofFamily, 26))

                        'click button search'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/button_Search'))

                        'verify input error'
						if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/a_Select'), 10,
							FailureHandling.OPTIONAL)) {
							'click select'
							WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/a_Select'))
						}
						else{
							'click X'
							WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabNewApplication/Button_X'))
							
							'click button cancel'
							WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/button_Cancel'))
							
							'Write to Excel FAILED'
							CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '2.TabFamilyData', 0,
								GlobalVariable.NumofFamily - 1, GlobalVariable.StatusFailed)
							
							continue
						}
                    }
                    
                    if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData').getValue(
                        GlobalVariable.NumofFamily, 29).length() > 1) {
                        'click button lookup job position'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/button_Job Position_btn btn-raised btn-primary'))

                        'input job position code'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/input_Job Position Code_JobCodeId'), 
                            findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData').getValue(
                                GlobalVariable.NumofFamily, 28))

                        'click button search'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/button_Search'))

                        'verify input error'
						if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/a_Select'), 10,
							FailureHandling.OPTIONAL)) {
							'click select'
							WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/a_Select'))
						}
						else{
							'click X'
							WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabNewApplication/Button_X'))
							flagWarning++
							continue
							
						}
                    }
                    
                    if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData').getValue(
                        GlobalVariable.NumofFamily, 32).length() > 1) {
                        'click button lookup department AML'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/button_DEPARTMENT AML_btn btn-raised btn-primary'))

                        'input department AML Code'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/input_Code_MasterCodeId'), 
                            findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData').getValue(
                                GlobalVariable.NumofFamily, 31))

                        'click button search'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/button_Search'))

                        'verify input error'
						if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/a_Select'), 10,
							FailureHandling.OPTIONAL)) {
							'click select'
							WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/a_Select'))
						}
						else{
							'click X'
							WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabNewApplication/Button_X'))
							
							'click button cancel'
							WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/button_Cancel'))
							
							'Write to Excel FAILED'
							CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '2.TabFamilyData', 0,
								GlobalVariable.NumofFamily - 1, GlobalVariable.StatusFailed)
							
							continue
						}
                    }
                    
                    if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData').getValue(
                        GlobalVariable.NumofFamily, 34).length() > 1) {
                        'click button lookup authority AML'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/button_Authority AML_btn btn-raised btn-primary'))

                        'input authority AML Code'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/input_Code_MasterCodeId'), 
                            findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData').getValue(
                                GlobalVariable.NumofFamily, 33))

                        'click button search'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/button_Search'))

                        'verify input error'
						if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/a_Select'), 10,
							FailureHandling.OPTIONAL)) {
							'click select'
							WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/a_Select'))
						}
						else{
							'click X'
							WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabNewApplication/Button_X'))
							flagWarning++
							continue
							
						}
                    }
                    
                    if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData').getValue(
                        GlobalVariable.NumofFamily, 36) == 'Yes') {
                        'click button copy'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/button_Copy'))
                    } else if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData').getValue(
                        GlobalVariable.NumofFamily, 36) == 'No') {
                        'input text address'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/textarea_Address'), 
                            findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData').getValue(
                                GlobalVariable.NumofFamily, 37))

                        'input RT'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/input_RT'), 
                            findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData').getValue(
                                GlobalVariable.NumofFamily, 38))

                        'input RW'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/input_RW'), 
                            findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData').getValue(
                                GlobalVariable.NumofFamily, 39))

                        'click button zipcode'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/button_Zipcode_btn btn-raised btn-primary'))

                        'input zipcode'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/input_Zipcode_form-control ng-untouched ng-pristine ng-invalid'), 
                            findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData').getValue(
                                GlobalVariable.NumofFamily, 40))

                        'input kecamatan'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/input_Kecamatan'), 
                            findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData').getValue(
                                GlobalVariable.NumofFamily, 41))

                        'input kelurahan'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/input_Kelurahan'), 
                            findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData').getValue(
                                GlobalVariable.NumofFamily, 42))

                        'input kota'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/input_Kota'), 
                            findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData').getValue(
                                GlobalVariable.NumofFamily, 43))

                        'click button search'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/button_SearchZipcode'))

                        'verify input error'
						if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/a_Select'), 10,
							FailureHandling.OPTIONAL)) {
							'click select'
							WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/a_Select'))
						}
						else{
							'click X'
							WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabNewApplication/Button_X'))
							
							'click button cancel'
							WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/button_Cancel'))
							
							'Write to Excel FAILED'
							CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '2.TabFamilyData', 0,
								GlobalVariable.NumofFamily - 1, GlobalVariable.StatusFailed)
							
							continue
						}

                        'select ownership'
                        WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/select_Ownership'), 
                            findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData').getValue(
                                GlobalVariable.NumofFamily, 44), false)
                    }
                    
                    'click button save'
                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/button_Save'))

                    'verify input error'
                    if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/button_Cancel'),5, 
                        FailureHandling.OPTIONAL)) {
                        'click button cancel'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/button_Cancel'))

                        'Write to Excel FAILED'
                        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '2.TabFamilyData', 
                            0, GlobalVariable.NumofFamily - 1, GlobalVariable.StatusFailed)
                    } else {
                        'Write to Excel SUCCESS'
                        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '2.TabFamilyData', 
                            0, GlobalVariable.NumofFamily - 1, GlobalVariable.StatusSuccess)
						if(flagWarning>0){
							'Write to Excel WARNING'
							CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '2.TabFamilyData',
								0, GlobalVariable.NumofFamily - 1, GlobalVariable.StatusWarning)
						}
						'customer added +1'
						GlobalVariable.countNumofCustomer++
                    }
                }
            } else if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData').getValue(
                GlobalVariable.NumofFamily, 3) == 'LookUp') {
                if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/button_2Family Data'),5, 
                    FailureHandling.OPTIONAL)) {
                    'click button add'
                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/button_Add'))

                    'select customer relation'
                    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/select_CustomerRelation'), 
                        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData').getValue(
                            GlobalVariable.NumofFamily, 8), false)

                    'click customer lookup'
                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/button_Family Legal Name_btn btn-raised btn-primary'))

                    'input customer no'
                    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/input_Customer No_custNoId'), 
                        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData').getValue(
                            GlobalVariable.NumofFamily, 5))

                    'input customer name'
                    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/input_Customer Name_custNameId'), 
                        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData').getValue(
                            GlobalVariable.NumofFamily, 6))

                    'input customer id no'
                    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/input_Id No_IdNoId'), 
                        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData').getValue(
                            GlobalVariable.NumofFamily, 7))

                    'click search'
                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/button_Search'))

                    'verify input error'
					if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/a_Select'), 10,
						FailureHandling.OPTIONAL)) {
						'click select'
						WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/a_Select'))
					}
					else{
						'click X'
						WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabNewApplication/Button_X'))
						
						'click button cancel'
						WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/button_Cancel'))
						
						'Write to Excel FAILED'
						CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '2.TabFamilyData', 0,
							GlobalVariable.NumofFamily - 1, GlobalVariable.StatusFailed)
						
						continue
					}
					
					'select customer model'
					WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/select_CustomerModel'),
						findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData').getValue(
							GlobalVariable.NumofFamily, 15), false)

					if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData').getValue(
						GlobalVariable.NumofFamily, 17).length() > 1) {
						'click lookup button proffession name'
						WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/button_Profession Name_btn btn-raised btn-primary'))

						'input profession code'
						WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/input_Profession Code_professionCodeId'),
							findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData').getValue(
								GlobalVariable.NumofFamily, 16))

						'click button search'
						WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/button_Search'))

						'verify input error'
						if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/a_Select'), 10,
							FailureHandling.OPTIONAL)) {
							'click select'
							WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/a_Select'))
						}
						else{
							'click X'
							WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabNewApplication/Button_X'))
							flagWarning++
							continue
						}
					}
					
					'input employee establishment date'
					WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/input_Employee Establishment Date'),
						findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData').getValue(
							GlobalVariable.NumofFamily, 18))
					
					'select nationality'
					WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/select_nationality'),
						findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData').getValue(
							GlobalVariable.NumofFamily, 25), false)

					if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData').getValue(
						GlobalVariable.NumofFamily, 25) == 'Foreigner') {
						'click button lookup country'
						WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/button_INDONESIA_btn btn-raised btn-primary'))

						'input country code'
						WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/input_Country Code_countryCodeId'),
							findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData').getValue(
								GlobalVariable.NumofFamily, 26))

						'click button search'
						WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/button_Search'))

						'verify input error'
						if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/a_Select'), 10,
							FailureHandling.OPTIONAL)) {
							'click select'
							WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/a_Select'))
						}
						else{
							'click X'
							WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabNewApplication/Button_X'))
							
							'click button cancel'
							WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/button_Cancel'))
							
							'Write to Excel FAILED'
							CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '2.TabFamilyData', 0,
								GlobalVariable.NumofFamily - 1, GlobalVariable.StatusFailed)
							
							continue
							
						}
					}
					
					if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData').getValue(
						GlobalVariable.NumofFamily, 29).length() > 1) {
						'click button lookup job position'
						WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/button_Job Position_btn btn-raised btn-primary'))

						'input job position code'
						WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/input_Job Position Code_JobCodeId'),
							findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData').getValue(
								GlobalVariable.NumofFamily, 28))

						'click button search'
						WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/button_Search'))

						'verify input error'
						if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/a_Select'), 10,
							FailureHandling.OPTIONAL)) {
							'click select'
							WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/a_Select'))
						}
						else{
							'click X'
							WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabNewApplication/Button_X'))
							flagWarning++
							continue
							
						}
					}
					
					if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData').getValue(
						GlobalVariable.NumofFamily, 32).length() > 1) {
						'click button lookup department AML'
						WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/button_DEPARTMENT AML_btn btn-raised btn-primary'))

						'input department AML Code'
						WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/input_Code_MasterCodeId'),
							findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData').getValue(
								GlobalVariable.NumofFamily, 31))

						'click button search'
						WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/button_Search'))

						'verify input error'
						if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/a_Select'), 10,
							FailureHandling.OPTIONAL)) {
							'click select'
							WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/a_Select'))
						}
						else{
							'click X'
							WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabNewApplication/Button_X'))
							
							'click button cancel'
							WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/button_Cancel'))
							
							'Write to Excel FAILED'
							CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '2.TabFamilyData', 0,
								GlobalVariable.NumofFamily - 1, GlobalVariable.StatusFailed)
							
							continue
							
						}
					}
					
					if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData').getValue(
						GlobalVariable.NumofFamily, 34).length() > 1) {
						'click button lookup authority AML'
						WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/button_Authority AML_btn btn-raised btn-primary'))

						'input authority AML Code'
						WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/input_Code_MasterCodeId'),
							findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData').getValue(
								GlobalVariable.NumofFamily, 33))

						'click button search'
						WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/button_Search'))

						'verify input error'
						if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/a_Select'), 10,
							FailureHandling.OPTIONAL)) {
							'click select'
							WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/a_Select'))
						}
						else{
							'click X'
							WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabNewApplication/Button_X'))
							flagWarning++
							continue
						}
					}

                    'click button save'
                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/button_Save'))
					
			

                    'verify input error'
                    if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/button_Cancel'),5, 
                        FailureHandling.OPTIONAL)) {
                        'click button cancel'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/button_Cancel'))

                        'Write to Excel FAILED'
                        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '2.TabFamilyData', 
                            0, GlobalVariable.NumofFamily - 1, GlobalVariable.StatusFailed)
                    } else {
                        'Write to Excel SUCCESS'
                        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '2.TabFamilyData', 
                            0, GlobalVariable.NumofFamily - 1, GlobalVariable.StatusSuccess)
						
						
						if(flagWarning>0){
							'Write to Excel WARNING'
							CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '2.TabFamilyData',
								0, GlobalVariable.NumofFamily - 1, GlobalVariable.StatusWarning)
							
							'customer added +1'
							GlobalVariable.countNumofCustomer++
						}
					}
                }
            }
        }
    }
	
	'click button save and continue'
	WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/button_Save and continue'))
	
    if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/th_Family Name'),5, 
        FailureHandling.OPTIONAL)) {
		'click back'
		WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData/button_Back'))
	
		'Write to Excel FAILED'
		CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '2a.TabFamilyDataMain',
			0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)
    }
	else{
		'Write to Excel SUCCESS'
		CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '2a.TabFamilyDataMain',
			0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusSuccess)
	}
}

println(GlobalVariable.countNumofCustomer)

