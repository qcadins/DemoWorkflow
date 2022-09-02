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

int flagWarning = 0

String userDir = System.getProperty('user.dir')

String filePath = userDir + GlobalVariable.PathCompany

GlobalVariable.DataFilePath = filePath

datafile = findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabManagementShareholder')

if (GlobalVariable.RoleCompany == 'Testing') {
    'verify application step'
    WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/applicationcurrentstep')), 
        'MANAGEMENT SHAREHOLDER', false, FailureHandling.OPTIONAL)
}

'Loop Multiple Guarantor Data'
for (GlobalVariable.NumofFamily = 2; GlobalVariable.NumofFamily <= (Integer.parseInt(GlobalVariable.CountAManagementShareholder) + 
1); (GlobalVariable.NumofFamily)++) {
    if (datafile.getValue(GlobalVariable.NumofFamily, 12) == findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
        GlobalVariable.NumofColm, 13)) {
        if (datafile.getValue(GlobalVariable.NumofFamily, 13) == 'Input Data') {
            'click button add'
            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/button_Add'))

            if (datafile.getValue(GlobalVariable.NumofFamily, 14).equalsIgnoreCase('Personal')) {
                'click radio personal'
                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/span_ Personal'))

                'input shareholder name'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Shareholder Legal Name_form-control ng-untouched ng-pristine ng-invalid'), 
                    datafile.getValue(GlobalVariable.NumofFamily, 19))

                'input birth place'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Birth Place_form-control ng-untouched ng-pristine ng-invalid'), 
                    datafile.getValue(GlobalVariable.NumofFamily, 20))

                'select idtype'
                WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/select_Select One AKTA  E-KTP  KARTU TANDA MAHASISWA  KITAS  NPWP  SIM'), 
                    datafile.getValue(GlobalVariable.NumofFamily, 21), false)

                'input Id Expired Date'
                if (((datafile.getValue(GlobalVariable.NumofFamily, 21) != 'E-KTP') && (datafile.getValue(GlobalVariable.NumofFamily, 
                    21) != 'NPWP')) && (datafile.getValue(GlobalVariable.NumofFamily, 21) != 'AKTA')) {
                    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Id Expired Date_form-control ng-untouched ng-pristine ng-valid'), 
                        datafile.getValue(GlobalVariable.NumofFamily, 22))
                }
                
                'select marital status'
                WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/select_Select One Married  Single  Widow'), 
                    datafile.getValue(GlobalVariable.NumofFamily, 23), false)

                'input mobile phone'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Mobile Phone_form-control ng-untouched ng-pristine ng-invalid'), 
                    datafile.getValue(GlobalVariable.NumofFamily, 24))

                'select customer model'
                WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/select_Select One Employee  Non Professional  Professional  Small Medium Enterprise'), 
                    datafile.getValue(GlobalVariable.NumofFamily, 25), false)

                if (datafile.getValue(GlobalVariable.NumofFamily, 27).length() > 1) {
                    'click lookup button proffession name'
                    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_Profession Name_btn btn-raised btn-primary'))

                    'input profession code'
                    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Profession Code_professionCodeId'), 
                        datafile.getValue(GlobalVariable.NumofFamily, 26))

                    'click button search'
                    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_Search'))

                    'verify input error'
                    if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/a_Select'), 
                        10, FailureHandling.OPTIONAL)) {
                        'click select'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/a_Select'))
                    } else {
                        'click X'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_X'))

                        flagWarning++

                        continue
                    }
                }
                
                'click button position SLIK'
                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_Position SLIK_btn btn-raised btn-primary'))

                'input jabatan code'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Jabatan Code_JabatanCodeId'), 
                    datafile.getValue(GlobalVariable.NumofFamily, 28))

                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_Search'))

                'verify input error'
                if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/a_Select'), 
                    10, FailureHandling.OPTIONAL)) {
                    'click select'
                    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/a_Select'))
                } else {
                    'click X'
                    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_X'))

                    flagWarning++

                    continue
                }
                
                'input establishment date'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Establishment Date_form-control ng-untouched ng-pristine ng-invalid'), 
                    datafile.getValue(GlobalVariable.NumofFamily, 30))

                'select gender'
                WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/select_Select One Female  Male'), 
                    datafile.getValue(GlobalVariable.NumofFamily, 31), false)

                'input birth date'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Birth Date_form-control ng-untouched ng-pristine ng-invalid'), 
                    datafile.getValue(GlobalVariable.NumofFamily, 32))

                'input id no'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Id No_form-control ng-untouched ng-pristine ng-invalid'), 
                    datafile.getValue(GlobalVariable.NumofFamily, 33))

                'input tax id'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Tax Id No_form-control ng-untouched ng-pristine ng-valid'), 
                    datafile.getValue(GlobalVariable.NumofFamily, 34))

                'input mother maiden name'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Mother Maiden Name_form-control ng-untouched ng-pristine ng-invalid'), 
                    datafile.getValue(GlobalVariable.NumofFamily, 35))

                'input email'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Email_form-control ng-untouched ng-pristine ng-valid'), 
                    datafile.getValue(GlobalVariable.NumofFamily, 36))

                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_Job Position_btn btn-raised btn-primary'))

                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Job Position Code_JobCodeId'), 
                    datafile.getValue(GlobalVariable.NumofFamily, 37))

                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_Search'))

                'verify input error'
                if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/a_Select'), 
                    10, FailureHandling.OPTIONAL)) {
                    'click select'
                    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/a_Select'))
                } else {
                    'click X'
                    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_X'))

                    flagWarning++

                    continue
                }
                
                'input share'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Share()_form-control ng-untouched ng-pristine ng-valid'), 
                    datafile.getValue(GlobalVariable.NumofFamily, 39))

                if (WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Owner_ng-untouched ng-pristine ng-valid'), 
                    2, FailureHandling.OPTIONAL)) {
                    if (datafile.getValue(GlobalVariable.NumofFamily, 40).equalsIgnoreCase('Yes')) {
                        'click check box is Owner'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Owner_ng-untouched ng-pristine ng-valid'))
                    }
                } else if (WebUI.verifyElementChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Owner_ng-untouched ng-pristine ng-valid'), 
                    2, FailureHandling.OPTIONAL)) {
                    if (datafile.getValue(GlobalVariable.NumofFamily, 40).equalsIgnoreCase('No')) {
                        'click check box is Owner'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Owner_ng-untouched ng-pristine ng-valid'))
                    }
                }
                
                if (WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Active_ng-untouched ng-pristine ng-valid'), 
                    2, FailureHandling.OPTIONAL)) {
                    if (datafile.getValue(GlobalVariable.NumofFamily, 41).equalsIgnoreCase('Yes')) {
                        'click check box is Active'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Active_ng-untouched ng-pristine ng-valid'))
                    }
                } else if (WebUI.verifyElementChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Active_ng-untouched ng-pristine ng-valid'), 
                    2, FailureHandling.OPTIONAL)) {
                    if (datafile.getValue(GlobalVariable.NumofFamily, 41).equalsIgnoreCase('No')) {
                        'click check box is Active'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Active_ng-untouched ng-pristine ng-valid'))
                    }
                }
                
                if (WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Signer_ng-untouched ng-pristine ng-valid'), 
                    2, FailureHandling.OPTIONAL)) {
                    if (datafile.getValue(GlobalVariable.NumofFamily, 42).equalsIgnoreCase('Yes')) {
                        'click check box is Signer'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Signer_ng-untouched ng-pristine ng-valid'))
                    }
                } else if (WebUI.verifyElementChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Signer_ng-untouched ng-pristine ng-valid'), 
                    2, FailureHandling.OPTIONAL)) {
                    if (datafile.getValue(GlobalVariable.NumofFamily, 42).equalsIgnoreCase('No')) {
                        'click check box is Signer'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Signer_ng-untouched ng-pristine ng-valid'))
                    }
                }
                
                'click button lookup department AML'
                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_DEPARTMENT AML_btn btn-raised btn-primary'))

                'input department AML Code'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Code_MasterCodeId'), 
                    datafile.getValue(GlobalVariable.NumofFamily, 44))

                'click button search'
                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_Search'))

                'verify input error'
                if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/a_Select'), 
                    10, FailureHandling.OPTIONAL)) {
                    'click select'
                    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/a_Select'))
                } else {
                    'click X'
                    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_X'))

                    flagWarning++

                    continue
                }
                
                if (datafile.getValue(GlobalVariable.NumofFamily, 47).length() > 1) {
                    'click button authority AML lookup'
                    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_Authority AML_btn btn-raised btn-primary'))

                    'input authority AML code'
                    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Code_MasterCodeId'), 
                        datafile.getValue(GlobalVariable.NumofFamily, 46))

                    'click button search'
                    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_Search'))

                    'verify input error'
                    if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/a_Select'), 
                        10, FailureHandling.OPTIONAL)) {
                        'click select'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/a_Select'))
                    } else {
                        'click X'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_X'))

                        flagWarning++

                        continue
                    }
                }
            } else if (datafile.getValue(GlobalVariable.NumofFamily, 14).equalsIgnoreCase('Company')) {
                'click radio company'
                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/span_ Company'))

                'input shareholder name'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/input_Shareholder Legal Name_form-control ng-untouched ng-pristine ng-invalid'), 
                    datafile.getValue(GlobalVariable.NumofFamily, 52))

                'input tax id'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/input_Tax Id No_form-control ng-untouched ng-pristine ng-invalid'), 
                    datafile.getValue(GlobalVariable.NumofFamily, 53))

                'click button position SLIK'
                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/button_Position SLIK_btn btn-raised btn-primary'))

                'input jabatan code'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/input_Jabatan Code_JabatanCodeId'), 
                    datafile.getValue(GlobalVariable.NumofFamily, 54))

                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/button_Search'))

                'verify input error'
                if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/a_Select'), 
                    10, FailureHandling.OPTIONAL)) {
                    'click select'
                    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/a_Select'))
                } else {
                    'click X'
                    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/button_X'))

                    flagWarning++
                }
                
                'select company type'
                WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/select_Select One CV  Koperasi  PT'), 
                    datafile.getValue(GlobalVariable.NumofFamily, 56), false)

                'select customer model'
                WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/select_Select One Corporate  Non Corporate'), 
                    datafile.getValue(GlobalVariable.NumofFamily, 57), false)

                'input share'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/input_Share()_form-control ng-untouched ng-pristine ng-valid'), 
                    datafile.getValue(GlobalVariable.NumofFamily, 58))

                if (WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Owner_ng-untouched ng-pristine ng-valid'), 
                    2, FailureHandling.OPTIONAL)) {
                    if (datafile.getValue(GlobalVariable.NumofFamily, 60).equalsIgnoreCase('Yes')) {
                        'click check box is Owner'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/input_Is Owner_ng-untouched ng-pristine ng-valid'))
                    }
                } else if (WebUI.verifyElementChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Owner_ng-untouched ng-pristine ng-valid'), 
                    2, FailureHandling.OPTIONAL)) {
                    if (datafile.getValue(GlobalVariable.NumofFamily, 60).equalsIgnoreCase('No')) {
                        'click check box is Owner'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/input_Is Owner_ng-untouched ng-pristine ng-valid'))
                    }
                }
                
                if (WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Active_ng-untouched ng-pristine ng-valid'), 
                    2, FailureHandling.OPTIONAL)) {
                    if (datafile.getValue(GlobalVariable.NumofFamily, 59).equalsIgnoreCase('Yes')) {
                        'click check box is Active'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/input_Is Active_ng-untouched ng-pristine ng-valid'))
                    }
                } else if (WebUI.verifyElementChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Active_ng-untouched ng-pristine ng-valid'), 
                    2, FailureHandling.OPTIONAL)) {
                    if (datafile.getValue(GlobalVariable.NumofFamily, 59).equalsIgnoreCase('No')) {
                        'click check box is Active'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/input_Is Active_ng-untouched ng-pristine ng-valid'))
                    }
                }
            } else {
                'click radio public'
                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/span_ Public'))

                'select Public Type'
                WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/select_Select One INSTANSI PUBLIK DAERAH  INSTANSI PUBLIK PUSAT  MASYARAKAT  PEMERINTAH REPUBLIK INDONESIA'), 
                    datafile.getValue(GlobalVariable.NumofFamily, 62).toUpperCase(), false)

                if (datafile.getValue(GlobalVariable.NumofFamily, 62).equalsIgnoreCase('Instansi Publik Daerah') || datafile.getValue(
                    GlobalVariable.NumofFamily, 62).equalsIgnoreCase('Instansi Publik Pusat')) {
                    'input shareholder name'
                    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/input_Name_form-control adInsInput ng-untouched ng-pristine ng-invalid'), 
                        datafile.getValue(GlobalVariable.NumofFamily, 63))

                    'input identity no'
                    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/input_Identity No_form-control adInsInput ng-untouched ng-pristine ng-invalid'), 
                        datafile.getValue(GlobalVariable.NumofFamily, 64))
                }
                
                'click lookup position SLIK'
                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/button_Position SLIK_btn btn-raised btn-primary'))

                'input jabatan code'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/input_Jabatan Code_JabatanCodeId'), 
                    datafile.getValue(GlobalVariable.NumofFamily, 65))

                'click button search'
                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/button_Search'))

                'verify input error'
                if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/a_Select'), 
                    10, FailureHandling.OPTIONAL)) {
                    'click select'
                    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/a_Select'))
                } else {
                    'click X'
                    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/button_X'))

                    flagWarning++
                }
                
                'input share'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Share()_form-control ng-untouched ng-pristine ng-valid'), 
                    datafile.getValue(GlobalVariable.NumofFamily, 67))

                if (WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/input_Is Active_border-primary ng-untouched ng-pristine ng-valid'), 
                    2, FailureHandling.OPTIONAL)) {
                    if (datafile.getValue(GlobalVariable.NumofFamily, 68).equalsIgnoreCase('Yes')) {
                        'click check box is Active'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/input_Is Active_border-primary ng-untouched ng-pristine ng-valid'))
                    }
                } else if (WebUI.verifyElementChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/input_Is Active_border-primary ng-untouched ng-pristine ng-valid'), 
                    2, FailureHandling.OPTIONAL)) {
                    if (datafile.getValue(GlobalVariable.NumofFamily, 68).equalsIgnoreCase('No')) {
                        'click check box is Active'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/input_Is Active_border-primary ng-untouched ng-pristine ng-valid'))
                    }
                }
            }
            
            if (datafile.getValue(GlobalVariable.NumofFamily, 14).equalsIgnoreCase('Personal') || datafile.getValue(GlobalVariable.NumofFamily, 
                14).equalsIgnoreCase('Company')) {
                if (datafile.getValue(GlobalVariable.NumofFamily, 70).length() > 1) {
                    'click button copy'
                    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/button_Copy'))
                } else {
                    'input address'
                    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/textarea_Address_form-control ng-untouched ng-pristine ng-invalid'), 
                        datafile.getValue(GlobalVariable.NumofFamily, 71))

                    'input RT'
                    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/input_RT'), 
                        datafile.getValue(GlobalVariable.NumofFamily, 72))

                    'input RW'
                    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/input_RW'), 
                        datafile.getValue(GlobalVariable.NumofFamily, 73))

                    'click button lookup zipcode'
                    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/button_Zipcode_btn btn-raised btn-primary'))

                    'input zipcode'
                    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/input_Zip Code_ZipCode'), 
                        datafile.getValue(GlobalVariable.NumofFamily, 74))

                    'input kecamatan'
                    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/input_Kecamatan_kecamatan'), 
                        datafile.getValue(GlobalVariable.NumofFamily, 75))

                    'input kelurahan'
                    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/input_Kelurahan_Kelurahan'), 
                        datafile.getValue(GlobalVariable.NumofFamily, 76))

                    'input kota'
                    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/input_Kota_kota'), 
                        datafile.getValue(GlobalVariable.NumofFamily, 77))

                    'klik button search'
                    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/button_Search'))

                    'verify input error'
                    if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/a_Select'), 
                        10, FailureHandling.OPTIONAL)) {
                        'click select'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/a_Select'))
                    } else {
                        'click X'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/button_X'))

                        flagWarning++
                    }
                    
                    'select ownership'
                    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/select_Select One Dinas  Family  KPR  Rented  Self - Owned'), 
                        datafile.getValue(GlobalVariable.NumofFamily, 78), false)
                }
            } else {
                'input address'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/textarea_Address_form-control ng-untouched ng-pristine ng-invalid'), 
                    datafile.getValue(GlobalVariable.NumofFamily, 71))

                'input RT'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/input_RT'), 
                    datafile.getValue(GlobalVariable.NumofFamily, 72))

                'input RW'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/input_RW'), 
                    datafile.getValue(GlobalVariable.NumofFamily, 73))

                'click button lookup zipcode'
                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/button_Zipcode_btn btn-raised btn-primary'))

                'input zipcode'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/input_Zip Code_ZipCode'), 
                    datafile.getValue(GlobalVariable.NumofFamily, 74))

                'input kecamatan'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/input_Kecamatan_kecamatan'), 
                    datafile.getValue(GlobalVariable.NumofFamily, 75))

                'input kelurahan'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/input_Kelurahan_Kelurahan'), 
                    datafile.getValue(GlobalVariable.NumofFamily, 76))

                'input kota'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/input_Kota_kota'), 
                    datafile.getValue(GlobalVariable.NumofFamily, 77))

                'klik button search'
                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/button_Search'))

                'verify input error'
                if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/a_Select'), 
                    10, FailureHandling.OPTIONAL)) {
                    'click select'
                    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/a_Select'))
                } else {
                    'click X'
                    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/button_X'))

                    flagWarning++
                }
            }
            
            'click button save'
            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_Save'))
			
			'Check save Process write to excel'
			CustomKeywords.'checkSaveProcess.checkSaveProcess.checkStatus'(Integer.parseInt(datafile.getValue(GlobalVariable.NumofFamily, 4)), findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Tableheadermanagementshareholder'), GlobalVariable.NumofFamily, '2.TabManagementShareholderData')

            if (datafile.getValue(GlobalVariable.NumofFamily, 14).equalsIgnoreCase('Personal') || datafile.getValue(GlobalVariable.NumofFamily, 
                14).equalsIgnoreCase('Company')) {
                if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/button_Cancel'), 
                    5, FailureHandling.OPTIONAL)) {
                    'click button cancel'
                    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/button_Cancel'))

                    
                } else {
               

                    if (flagWarning > 0) {
                        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '2.TabManagementShareholderData', 
                            0, GlobalVariable.NumofFamily - 1, GlobalVariable.StatusWarning)
                    }
                    
                    'customer added +1'
                    (GlobalVariable.countNumofCustomer)++
                }
            } else {
                if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/button_Cancel'), 
                    5, FailureHandling.OPTIONAL)) {
                    'click button cancel'
                    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/button_Cancel'))

                    
                } else {
                    

                    if (flagWarning > 0) {
                        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '2.TabManagementShareholderData', 
                            0, GlobalVariable.NumofFamily - 1, GlobalVariable.StatusWarning)
                    }
                    
                    'customer added +1'
                    (GlobalVariable.countNumofCustomer)++
                }
            }
        } else {
            'click button add'
            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/button_Add'))

            if (datafile.getValue(GlobalVariable.NumofFamily, 14).equalsIgnoreCase('Personal')) {
                'click radio personal'
                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/span_ Personal'))

                'click button lookup customer'
                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_ShareHolderLegalName'))

                'input customer no'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Customer No_custNoId'), 
                    datafile.getValue(GlobalVariable.NumofFamily, 16))

                'input customer name'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Customer Name_custNameId'), 
                    datafile.getValue(GlobalVariable.NumofFamily, 17))

                'input ID No'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Id No_IdNoId'), 
                    datafile.getValue(GlobalVariable.NumofFamily, 18))

                'click search'
                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_Search'))

                'verify input error'
                if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/a_Select'), 
                    10, FailureHandling.OPTIONAL)) {
                    'click select'
                    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/a_Select'))
                } else {
                    'click X'
                    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/Button_X'))

                    'click button cancel'
                    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_Cancel'))

                    'Write to Excel FAILED'
                    CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '2.TabManagementShareholderData', 
                        0, GlobalVariable.NumofFamily - 1, GlobalVariable.StatusFailed)
					
					'Write To Excel GlobalVariable.StatusReasonLookup'
					CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '2.TabManagementShareholderData',
						1, GlobalVariable.NumofFamily - 1, GlobalVariable.StatusReasonLookup)

                    continue
                }
                
                'select customer model'
                WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/select_Select One Employee  Non Professional  Professional  Small Medium Enterprise'), 
                    datafile.getValue(GlobalVariable.NumofFamily, 25), false)

                'click button position SLIK'
                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_Position SLIK_btn btn-raised btn-primary'))

                'input jabatan code'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Jabatan Code_JabatanCodeId'), 
                    datafile.getValue(GlobalVariable.NumofFamily, 28))

                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_Search'))

                'verify input error'
                if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/a_Select'), 
                    10, FailureHandling.OPTIONAL)) {
                    'click select'
                    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/a_Select'))
                } else {
                    'click X'
                    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_X'))

                    flagWarning++

                    continue
                }
                
                'input establishment date'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Establishment Date_form-control ng-untouched ng-pristine ng-invalid'), 
                    datafile.getValue(GlobalVariable.NumofFamily, 30))

                'click button lookup job data'
                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_Job Position_btn btn-raised btn-primary'))

                'input job data code'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Job Position Code_JobCodeId'), 
                    datafile.getValue(GlobalVariable.NumofFamily, 37))

                'click button search'
                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_Search'))

                'verify input error'
                if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/a_Select'), 
                    10, FailureHandling.OPTIONAL)) {
                    'click select'
                    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/a_Select'))
                } else {
                    'click X'
                    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_X'))

                    flagWarning++

                    continue
                }
                
                'input share'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Share()_form-control ng-untouched ng-pristine ng-valid'), 
                    datafile.getValue(GlobalVariable.NumofFamily, 39))

                if (WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Owner_ng-untouched ng-pristine ng-valid'), 
                    2, FailureHandling.OPTIONAL)) {
                    if (datafile.getValue(GlobalVariable.NumofFamily, 40).equalsIgnoreCase('Yes')) {
                        'click check box is Owner'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Owner_ng-untouched ng-pristine ng-valid'))
                    }
                } else if (WebUI.verifyElementChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Owner_ng-untouched ng-pristine ng-valid'), 
                    2, FailureHandling.OPTIONAL)) {
                    if (datafile.getValue(GlobalVariable.NumofFamily, 40).equalsIgnoreCase('No')) {
                        'click check box is Owner'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Owner_ng-untouched ng-pristine ng-valid'))
                    }
                }
                
                if (WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Active_ng-untouched ng-pristine ng-valid'), 
                    2, FailureHandling.OPTIONAL)) {
                    if (datafile.getValue(GlobalVariable.NumofFamily, 41).equalsIgnoreCase('Yes')) {
                        'click check box is Active'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Active_ng-untouched ng-pristine ng-valid'))
                    }
                } else if (WebUI.verifyElementChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Active_ng-untouched ng-pristine ng-valid'), 
                    2, FailureHandling.OPTIONAL)) {
                    if (datafile.getValue(GlobalVariable.NumofFamily, 41).equalsIgnoreCase('No')) {
                        'click check box is Active'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Active_ng-untouched ng-pristine ng-valid'))
                    }
                }
                
                if (WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Signer_ng-untouched ng-pristine ng-valid'), 
                    2, FailureHandling.OPTIONAL)) {
                    if (datafile.getValue(GlobalVariable.NumofFamily, 42).equalsIgnoreCase('Yes')) {
                        'click check box is Signer'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Signer_ng-untouched ng-pristine ng-valid'))
                    }
                } else if (WebUI.verifyElementChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Signer_ng-untouched ng-pristine ng-valid'), 
                    2, FailureHandling.OPTIONAL)) {
                    if (datafile.getValue(GlobalVariable.NumofFamily, 42).equalsIgnoreCase('No')) {
                        'click check box is Signer'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Signer_ng-untouched ng-pristine ng-valid'))
                    }
                }
                
                if (datafile.getValue(GlobalVariable.NumofFamily, 44).length() > 1) {
                    'click button lookup department AML'
                    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_DEPARTMENT AML_btn btn-raised btn-primary'))

                    'input department AML Code'
                    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Code_MasterCodeId'), 
                        datafile.getValue(GlobalVariable.NumofFamily, 44))

                    'click button search'
                    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_Search'))

                    'verify input error'
                    if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/a_Select'), 
                        10, FailureHandling.OPTIONAL)) {
                        'click select'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/a_Select'))
                    } else {
                        'click X'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_X'))

                        flagWarning++

                        continue
                    }
                }
                
                if (GlobalVariable.RoleCompany == 'Testing') {
                    'call test case personal data verif'
                    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP1 - CustomerData/TabMSPersonalDataVerif'), 
                        [:], FailureHandling.CONTINUE_ON_FAILURE)
                }
            } else if (datafile.getValue(GlobalVariable.NumofFamily, 14).equalsIgnoreCase('Company')) {
                'click radio company'
                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/span_ Company'))

                'click button lookup shareholder'
                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/button_Shareholder Legal Name_btn btn-raised btn-primary'))

                'input customer no'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/input_Customer No_custNoId'), 
                    datafile.getValue(GlobalVariable.NumofFamily, 49))

                'input customer name'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/input_Customer Name_custNameId'), 
                    datafile.getValue(GlobalVariable.NumofFamily, 50))

                'input NPWP'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/input_NPWP_NpwpId'), 
                    datafile.getValue(GlobalVariable.NumofFamily, 51))

                'click button search'
                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/button_Search'))

                'verify input error'
                if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/a_Select'), 
                    10, FailureHandling.OPTIONAL)) {
                    'click select'
                    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/a_Select'))
                } else {
                    'click X'
                    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/Button_X'))

                    'click button cancel'
                    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/button_Cancel'))

                    'Write to Excel FAILED'
                    CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '2.TabManagementShareholderData', 
                        0, GlobalVariable.NumofFamily - 1, GlobalVariable.StatusFailed)
					
					'Write To Excel GlobalVariable.StatusReasonLookup'
					CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '2.TabManagementShareholderData',
						1, GlobalVariable.NumofFamily - 1, GlobalVariable.StatusReasonLookup)


                    continue
                }
                
                'click button position SLIK'
                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/button_Position SLIK_btn btn-raised btn-primary'))

                'input jabatan code'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/input_Jabatan Code_JabatanCodeId'), 
                    datafile.getValue(GlobalVariable.NumofFamily, 54))

                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/button_Search'))

                'verify input error'
                if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/a_Select'), 
                    10, FailureHandling.OPTIONAL)) {
                    'click select'
                    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/a_Select'))
                } else {
                    'click X'
                    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/button_X'))

                    flagWarning++

                    continue
                }
                
                'select company type'
                WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/select_Select One CV  Koperasi  PT'), 
                    datafile.getValue(GlobalVariable.NumofFamily, 56), false)

                'input share'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/input_Share()_form-control ng-untouched ng-pristine ng-valid'), 
                    datafile.getValue(GlobalVariable.NumofFamily, 58))

                if (WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Owner_ng-untouched ng-pristine ng-valid'), 
                    2, FailureHandling.OPTIONAL)) {
                    if (datafile.getValue(GlobalVariable.NumofFamily, 60).equalsIgnoreCase('Yes')) {
                        'click check box is Owner'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/input_Is Owner_ng-untouched ng-pristine ng-valid'))
                    }
                } else if (WebUI.verifyElementChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Owner_ng-untouched ng-pristine ng-valid'), 
                    2, FailureHandling.OPTIONAL)) {
                    if (datafile.getValue(GlobalVariable.NumofFamily, 60).equalsIgnoreCase('No')) {
                        'click check box is Owner'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/input_Is Owner_ng-untouched ng-pristine ng-valid'))
                    }
                }
                
                if (WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Active_ng-untouched ng-pristine ng-valid'), 
                    2, FailureHandling.OPTIONAL)) {
                    if (datafile.getValue(GlobalVariable.NumofFamily, 59).equalsIgnoreCase('Yes')) {
                        'click check box is Active'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/input_Is Active_ng-untouched ng-pristine ng-valid'))
                    }
                } else if (WebUI.verifyElementChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Active_ng-untouched ng-pristine ng-valid'), 
                    2, FailureHandling.OPTIONAL)) {
                    if (datafile.getValue(GlobalVariable.NumofFamily, 59).equalsIgnoreCase('No')) {
                        'click check box is Active'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/input_Is Active_ng-untouched ng-pristine ng-valid'))
                    }
                }
                
                if (GlobalVariable.RoleCompany == 'Testing') {
                    'call test case company data verif'
                    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP1 - CustomerData/TabMSCompanyDataVerif'), 
                        [:], FailureHandling.CONTINUE_ON_FAILURE)
                }
            }
            
            'click button save'
            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_Save'))
			
			'Check save Process write to excel'
			CustomKeywords.'checkSaveProcess.checkSaveProcess.checkStatus'(Integer.parseInt(datafile.getValue(GlobalVariable.NumofFamily, 4)), findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Tableheadermanagementshareholder'), GlobalVariable.NumofFamily, '2.TabManagementShareholderData')

            if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/button_Cancel'), 
                5, FailureHandling.OPTIONAL)) {
                'click button cancel'
                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/button_Cancel'))

       
            } else {
               

                if (flagWarning > 0) {
                    CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '2.TabManagementShareholderData', 
                        0, GlobalVariable.NumofFamily - 1, GlobalVariable.StatusWarning)
                }
                
                'customer added +1'
                (GlobalVariable.countNumofCustomer)++
            }
        }
    }
}

'click button save'
WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/button_Save'))





