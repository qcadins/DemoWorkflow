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

if(WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/applicationcurrentstep')).equalsIgnoreCase('MANAGEMENT SHAREHOLDER')){
    'Loop Multiple Guarantor Data'
    for (GlobalVariable.NumofFamily = 2; GlobalVariable.NumofFamily <= (Integer.parseInt(GlobalVariable.CountAManagementShareholder) + 
    1); (GlobalVariable.NumofFamily)++) {
        if (datafile.getValue(GlobalVariable.NumofFamily, 2) == findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
            GlobalVariable.NumofColm, 3)) {
            if (datafile.getValue(GlobalVariable.NumofFamily, 3) == 'Input Data') {
                'click button add'
                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/button_Add'))

                if (datafile.getValue(GlobalVariable.NumofFamily, 4).equalsIgnoreCase('Personal')) {
                    'click radio personal'
                    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/span_ Personal'))

                    'input shareholder name'
                    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Shareholder Legal Name_form-control ng-untouched ng-pristine ng-invalid'), 
                        datafile.getValue(GlobalVariable.NumofFamily, 9))

                    'input birth place'
                    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Birth Place_form-control ng-untouched ng-pristine ng-invalid'), 
                        datafile.getValue(GlobalVariable.NumofFamily, 10))

                    'select idtype'
                    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/select_Select One AKTA  E-KTP  KARTU TANDA MAHASISWA  KITAS  NPWP  SIM'), 
                        datafile.getValue(GlobalVariable.NumofFamily, 11), false)

                    'input Id Expired Date'
                    if (((datafile.getValue(GlobalVariable.NumofFamily, 11) != 'E-KTP') && (datafile.getValue(GlobalVariable.NumofFamily, 
                        11) != 'NPWP')) && (datafile.getValue(GlobalVariable.NumofFamily, 11) != 'AKTA')) {
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Id Expired Date_form-control ng-untouched ng-pristine ng-valid'), 
                            datafile.getValue(GlobalVariable.NumofFamily, 12))
                    }
                    
                    'select marital status'
                    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/select_Select One Married  Single  Widow'), 
                        datafile.getValue(GlobalVariable.NumofFamily, 13), false)

                    'input mobile phone'
                    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Mobile Phone_form-control ng-untouched ng-pristine ng-invalid'), 
                        datafile.getValue(GlobalVariable.NumofFamily, 14))

                    'select customer model'
                    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/select_Select One Employee  Non Professional  Professional  Small Medium Enterprise'), 
                        datafile.getValue(GlobalVariable.NumofFamily, 15), false)

                    if (datafile.getValue(GlobalVariable.NumofFamily, 17).length() > 1) {
                        'click lookup button proffession name'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_Profession Name_btn btn-raised btn-primary'))

                        'input profession code'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Profession Code_professionCodeId'), 
                            datafile.getValue(GlobalVariable.NumofFamily, 16))

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
                        datafile.getValue(GlobalVariable.NumofFamily, 18))

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
                        datafile.getValue(GlobalVariable.NumofFamily, 20))

                    'select gender'
                    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/select_Select One Female  Male'), 
                        datafile.getValue(GlobalVariable.NumofFamily, 21), false)

                    'input birth date'
                    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Birth Date_form-control ng-untouched ng-pristine ng-invalid'), 
                        datafile.getValue(GlobalVariable.NumofFamily, 22))

                    'input id no'
                    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Id No_form-control ng-untouched ng-pristine ng-invalid'), 
                        datafile.getValue(GlobalVariable.NumofFamily, 23))

                    'input tax id'
                    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Tax Id No_form-control ng-untouched ng-pristine ng-valid'), 
                        datafile.getValue(GlobalVariable.NumofFamily, 24))

                    'input mother maiden name'
                    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Mother Maiden Name_form-control ng-untouched ng-pristine ng-invalid'), 
                        datafile.getValue(GlobalVariable.NumofFamily, 25))

                    'input email'
                    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Email_form-control ng-untouched ng-pristine ng-valid'), 
                        datafile.getValue(GlobalVariable.NumofFamily, 26))

                    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_Job Position_btn btn-raised btn-primary'))

                    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Job Position Code_JobCodeId'), 
                        datafile.getValue(GlobalVariable.NumofFamily, 27))

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
                        datafile.getValue(GlobalVariable.NumofFamily, 29))

                    if (WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Owner_ng-untouched ng-pristine ng-valid'), 
                        2, FailureHandling.OPTIONAL)) {
                        if (datafile.getValue(GlobalVariable.NumofFamily, 30).equalsIgnoreCase('Yes')) {
                            'click check box is Owner'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Owner_ng-untouched ng-pristine ng-valid'))
                        }
                    } else if (WebUI.verifyElementChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Owner_ng-untouched ng-pristine ng-valid'), 
                        2, FailureHandling.OPTIONAL)) {
                        if (datafile.getValue(GlobalVariable.NumofFamily, 30).equalsIgnoreCase('No')) {
                            'click check box is Owner'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Owner_ng-untouched ng-pristine ng-valid'))
                        }
                    }
                    
                    if (WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Active_ng-untouched ng-pristine ng-valid'), 
                        2, FailureHandling.OPTIONAL)) {
                        if (datafile.getValue(GlobalVariable.NumofFamily, 31).equalsIgnoreCase('Yes')) {
                            'click check box is Active'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Active_ng-untouched ng-pristine ng-valid'))
                        }
                    } else if (WebUI.verifyElementChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Active_ng-untouched ng-pristine ng-valid'), 
                        2, FailureHandling.OPTIONAL)) {
                        if (datafile.getValue(GlobalVariable.NumofFamily, 31).equalsIgnoreCase('No')) {
                            'click check box is Active'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Active_ng-untouched ng-pristine ng-valid'))
                        }
                    }
                    
                    if (WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Signer_ng-untouched ng-pristine ng-valid'), 
                        2, FailureHandling.OPTIONAL)) {
                        if (datafile.getValue(GlobalVariable.NumofFamily, 32).equalsIgnoreCase('Yes')) {
                            'click check box is Signer'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Signer_ng-untouched ng-pristine ng-valid'))
                        }
                    } else if (WebUI.verifyElementChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Signer_ng-untouched ng-pristine ng-valid'), 
                        2, FailureHandling.OPTIONAL)) {
                        if (datafile.getValue(GlobalVariable.NumofFamily, 32).equalsIgnoreCase('No')) {
                            'click check box is Signer'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Signer_ng-untouched ng-pristine ng-valid'))
                        }
                    }
                    
                    'click button lookup department AML'
                    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_DEPARTMENT AML_btn btn-raised btn-primary'))

                    'input department AML Code'
                    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Code_MasterCodeId'), 
                        datafile.getValue(GlobalVariable.NumofFamily, 34))

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
                    
                    if (datafile.getValue(GlobalVariable.NumofFamily, 37).length() > 1) {
                        'click button authority AML lookup'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_Authority AML_btn btn-raised btn-primary'))

                        'input authority AML code'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Code_MasterCodeId'), 
                            datafile.getValue(GlobalVariable.NumofFamily, 36))

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
                } else if (datafile.getValue(GlobalVariable.NumofFamily, 4).equalsIgnoreCase('Company')) {
                    'click radio company'
                    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/span_ Company'))

                    'input shareholder name'
                    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/input_Shareholder Legal Name_form-control ng-untouched ng-pristine ng-invalid'), 
                        datafile.getValue(GlobalVariable.NumofFamily, 42))

                    'input tax id'
                    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/input_Tax Id No_form-control ng-untouched ng-pristine ng-invalid'), 
                        datafile.getValue(GlobalVariable.NumofFamily, 43))

                    'click button position SLIK'
                    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/button_Position SLIK_btn btn-raised btn-primary'))

                    'input jabatan code'
                    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/input_Jabatan Code_JabatanCodeId'), 
                        datafile.getValue(GlobalVariable.NumofFamily, 44))

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
                        datafile.getValue(GlobalVariable.NumofFamily, 46), false)

                    'select customer model'
                    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/select_Select One Corporate  Non Corporate'), 
                        datafile.getValue(GlobalVariable.NumofFamily, 47), false)

                    'input share'
                    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/input_Share()_form-control ng-untouched ng-pristine ng-valid'), 
                        datafile.getValue(GlobalVariable.NumofFamily, 48))

                    if (WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Owner_ng-untouched ng-pristine ng-valid'), 
                        2, FailureHandling.OPTIONAL)) {
                        if (datafile.getValue(GlobalVariable.NumofFamily, 50).equalsIgnoreCase('Yes')) {
                            'click check box is Owner'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/input_Is Owner_ng-untouched ng-pristine ng-valid'))
                        }
                    } else if (WebUI.verifyElementChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Owner_ng-untouched ng-pristine ng-valid'), 
                        2, FailureHandling.OPTIONAL)) {
                        if (datafile.getValue(GlobalVariable.NumofFamily, 50).equalsIgnoreCase('No')) {
                            'click check box is Owner'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/input_Is Owner_ng-untouched ng-pristine ng-valid'))
                        }
                    }
                    
                    if (WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Active_ng-untouched ng-pristine ng-valid'), 
                        2, FailureHandling.OPTIONAL)) {
                        if (datafile.getValue(GlobalVariable.NumofFamily, 49).equalsIgnoreCase('Yes')) {
                            'click check box is Active'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/input_Is Active_ng-untouched ng-pristine ng-valid'))
                        }
                    } else if (WebUI.verifyElementChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Active_ng-untouched ng-pristine ng-valid'), 
                        2, FailureHandling.OPTIONAL)) {
                        if (datafile.getValue(GlobalVariable.NumofFamily, 49).equalsIgnoreCase('No')) {
                            'click check box is Active'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/input_Is Active_ng-untouched ng-pristine ng-valid'))
                        }
                    }
                } else if (datafile.getValue(GlobalVariable.NumofFamily, 4).equalsIgnoreCase('Public')) {
                    'click radio public'
                    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/span_ Public'))

                    'select Public Type'
                    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/select_Select One INSTANSI PUBLIK DAERAH  INSTANSI PUBLIK PUSAT  MASYARAKAT  PEMERINTAH REPUBLIK INDONESIA'), 
                        datafile.getValue(GlobalVariable.NumofFamily, 52).toUpperCase(), false)

                    if (datafile.getValue(GlobalVariable.NumofFamily, 52).equalsIgnoreCase('Instansi Publik Daerah') || 
                    datafile.getValue(GlobalVariable.NumofFamily, 52).equalsIgnoreCase('Instansi Publik Pusat')) {
                        'input shareholder name'
                    }
                    
                    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/input_Name_form-control adInsInput ng-untouched ng-pristine ng-invalid'), 
                        datafile.getValue(GlobalVariable.NumofFamily, 53))

                    'input identity no'
                    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/input_Identity No_form-control adInsInput ng-untouched ng-pristine ng-invalid'), 
                        datafile.getValue(GlobalVariable.NumofFamily, 54))

                    'click lookup position SLIK'
                    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/button_Position SLIK_btn btn-raised btn-primary'))

                    'input jabatan code'
                    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/input_Jabatan Code_JabatanCodeId'), 
                        datafile.getValue(GlobalVariable.NumofFamily, 55))

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
                        datafile.getValue(GlobalVariable.NumofFamily, 57))

                    if (WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/input_Is Active_border-primary ng-untouched ng-pristine ng-valid'), 
                        2, FailureHandling.OPTIONAL)) {
                        if (datafile.getValue(GlobalVariable.NumofFamily, 58).equalsIgnoreCase('Yes')) {
                            'click check box is Active'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/input_Is Active_border-primary ng-untouched ng-pristine ng-valid'))
                        }
                    } else if (WebUI.verifyElementChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/input_Is Active_border-primary ng-untouched ng-pristine ng-valid'), 
                        2, FailureHandling.OPTIONAL)) {
                        if (datafile.getValue(GlobalVariable.NumofFamily, 58).equalsIgnoreCase('No')) {
                            'click check box is Active'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/input_Is Active_border-primary ng-untouched ng-pristine ng-valid'))
                        }
                    }
                }
                
                if (datafile.getValue(GlobalVariable.NumofFamily, 4).equalsIgnoreCase('Personal') || datafile.getValue(GlobalVariable.NumofFamily, 
                    4).equalsIgnoreCase('Company')) {
                    if (datafile.getValue(GlobalVariable.NumofFamily, 60).length() > 1) {
                        'click button copy'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/button_Copy'))
                    } else {
                        'input address'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/textarea_Address_form-control ng-untouched ng-pristine ng-invalid'), 
                            datafile.getValue(GlobalVariable.NumofFamily, 61))

                        'input RT'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/input_RT'), 
                            datafile.getValue(GlobalVariable.NumofFamily, 62))

                        'input RW'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/input_RW'), 
                            datafile.getValue(GlobalVariable.NumofFamily, 63))

                        'click button lookup zipcode'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/button_Zipcode_btn btn-raised btn-primary'))

                        'input zipcode'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/input_Zip Code_ZipCode'), 
                            datafile.getValue(GlobalVariable.NumofFamily, 64))

                        'input kecamatan'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/input_Kecamatan_kecamatan'), 
                            datafile.getValue(GlobalVariable.NumofFamily, 65))

                        'input kelurahan'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/input_Kelurahan_Kelurahan'), 
                            datafile.getValue(GlobalVariable.NumofFamily, 66))

                        'input kota'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/input_Kota_kota'), 
                            datafile.getValue(GlobalVariable.NumofFamily, 67))

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
                            datafile.getValue(GlobalVariable.NumofFamily, 68), false)
                    }
                } else {
                    'input address'
                    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/textarea_Address_form-control ng-untouched ng-pristine ng-invalid'), 
                        datafile.getValue(GlobalVariable.NumofFamily, 61))

                    'input RT'
                    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/input_RT'), 
                        datafile.getValue(GlobalVariable.NumofFamily, 62))

                    'input RW'
                    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/input_RW'), 
                        datafile.getValue(GlobalVariable.NumofFamily, 63))

                    'click button lookup zipcode'
                    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/button_Zipcode_btn btn-raised btn-primary'))

                    'input zipcode'
                    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/input_Zip Code_ZipCode'), 
                        datafile.getValue(GlobalVariable.NumofFamily, 64))

                    'input kecamatan'
                    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/input_Kecamatan_kecamatan'), 
                        datafile.getValue(GlobalVariable.NumofFamily, 65))

                    'input kelurahan'
                    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/input_Kelurahan_Kelurahan'), 
                        datafile.getValue(GlobalVariable.NumofFamily, 66))

                    'input kota'
                    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/input_Kota_kota'), 
                        datafile.getValue(GlobalVariable.NumofFamily, 67))

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

                if (datafile.getValue(GlobalVariable.NumofFamily, 4).equalsIgnoreCase('Personal') || datafile.getValue(GlobalVariable.NumofFamily, 
                    4).equalsIgnoreCase('Company')) {
                    if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/button_Cancel'), 
                        5, FailureHandling.OPTIONAL)) {
                        'click button cancel'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/button_Cancel'))

                        'write to excel failed'
                        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '2.TabManagementShareholderData', 
                            0, GlobalVariable.NumofFamily - 1, GlobalVariable.StatusFailed)
                    } else {
                        'write to excel success'
                        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '2.TabManagementShareholderData', 
                            0, GlobalVariable.NumofFamily - 1, GlobalVariable.StatusSuccess)

                        if (flagWarning > 0) {
                            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, 
                                '2.TabManagementShareholderData', 0, GlobalVariable.NumofFamily - 1, GlobalVariable.StatusWarning)
                        }
                        
                        'customer added +1'
                        (GlobalVariable.countNumofCustomer)++
                    }
                } else {
                    if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/button_Cancel'), 
                        5, FailureHandling.OPTIONAL)) {
                        'click button cancel'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Public/button_Cancel'))

                        'write to excel failed'
                        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '2.TabManagementShareholderData', 
                            0, GlobalVariable.NumofFamily - 1, GlobalVariable.StatusFailed)
                    } else {
                        'write to excel success'
                        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '2.TabManagementShareholderData', 
                            0, GlobalVariable.NumofFamily - 1, GlobalVariable.StatusSuccess)

                        if (flagWarning > 0) {
                            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, 
                                '2.TabManagementShareholderData', 0, GlobalVariable.NumofFamily - 1, GlobalVariable.StatusWarning)
                        }
                        
                        'customer added +1'
                        (GlobalVariable.countNumofCustomer)++
                    }
                }
            } else if (datafile.getValue(GlobalVariable.NumofFamily, 3) == 'LookUp') {
                'click button add'
                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/button_Add'))

                if (datafile.getValue(GlobalVariable.NumofFamily, 4).equalsIgnoreCase('Personal')) {
                    'click radio personal'
                    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/span_ Personal'))

                    'click button lookup customer'
                    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_ShareHolderLegalName'))

                    'input customer no'
                    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Customer No_custNoId'), 
                        datafile.getValue(GlobalVariable.NumofFamily, 6))

                    'input customer name'
                    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Customer Name_custNameId'), 
                        datafile.getValue(GlobalVariable.NumofFamily, 7))

                    'input ID No'
                    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Id No_IdNoId'), 
                        datafile.getValue(GlobalVariable.NumofFamily, 8))

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

                        continue
                    }
                    
                    'select customer model'
                    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/select_Select One Employee  Non Professional  Professional  Small Medium Enterprise'), 
                        datafile.getValue(GlobalVariable.NumofFamily, 15), false)

                    'click button position SLIK'
                    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_Position SLIK_btn btn-raised btn-primary'))

                    'input jabatan code'
                    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Jabatan Code_JabatanCodeId'), 
                        datafile.getValue(GlobalVariable.NumofFamily, 18))

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
                        datafile.getValue(GlobalVariable.NumofFamily, 20))

                    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_Job Position_btn btn-raised btn-primary'))

                    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Job Position Code_JobCodeId'), 
                        datafile.getValue(GlobalVariable.NumofFamily, 27))

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
                        datafile.getValue(GlobalVariable.NumofFamily, 29))

                    if (WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Owner_ng-untouched ng-pristine ng-valid'), 
                        2, FailureHandling.OPTIONAL)) {
                        if (datafile.getValue(GlobalVariable.NumofFamily, 30).equalsIgnoreCase('Yes')) {
                            'click check box is Owner'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Owner_ng-untouched ng-pristine ng-valid'))
                        }
                    } else if (WebUI.verifyElementChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Owner_ng-untouched ng-pristine ng-valid'), 
                        2, FailureHandling.OPTIONAL)) {
                        if (datafile.getValue(GlobalVariable.NumofFamily, 30).equalsIgnoreCase('No')) {
                            'click check box is Owner'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Owner_ng-untouched ng-pristine ng-valid'))
                        }
                    }
                    
                    if (WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Active_ng-untouched ng-pristine ng-valid'), 
                        2, FailureHandling.OPTIONAL)) {
                        if (datafile.getValue(GlobalVariable.NumofFamily, 31).equalsIgnoreCase('Yes')) {
                            'click check box is Active'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Active_ng-untouched ng-pristine ng-valid'))
                        }
                    } else if (WebUI.verifyElementChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Active_ng-untouched ng-pristine ng-valid'), 
                        2, FailureHandling.OPTIONAL)) {
                        if (datafile.getValue(GlobalVariable.NumofFamily, 31).equalsIgnoreCase('No')) {
                            'click check box is Active'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Active_ng-untouched ng-pristine ng-valid'))
                        }
                    }
                    
                    if (WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Signer_ng-untouched ng-pristine ng-valid'), 
                        2, FailureHandling.OPTIONAL)) {
                        if (datafile.getValue(GlobalVariable.NumofFamily, 32).equalsIgnoreCase('Yes')) {
                            'click check box is Signer'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Signer_ng-untouched ng-pristine ng-valid'))
                        }
                    } else if (WebUI.verifyElementChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Signer_ng-untouched ng-pristine ng-valid'), 
                        2, FailureHandling.OPTIONAL)) {
                        if (datafile.getValue(GlobalVariable.NumofFamily, 32).equalsIgnoreCase('No')) {
                            'click check box is Signer'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Signer_ng-untouched ng-pristine ng-valid'))
                        }
                    }
                } else if (datafile.getValue(GlobalVariable.NumofFamily, 4).equalsIgnoreCase('Company')) {
                    'click radio company'
                    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/span_ Company'))

                    'click button lookup shareholder'
                    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/button_Shareholder Legal Name_btn btn-raised btn-primary'))

                    'input customer no'
                    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/input_Customer No_custNoId'), 
                        datafile.getValue(GlobalVariable.NumofFamily, 39))

                    'input customer name'
                    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/input_Customer Name_custNameId'), 
                        datafile.getValue(GlobalVariable.NumofFamily, 40))

                    'input NPWP'
                    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/input_NPWP_NpwpId'), 
                        datafile.getValue(GlobalVariable.NumofFamily, 41))

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

                        continue
                    }
                    
                    'click button position SLIK'
                    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/button_Position SLIK_btn btn-raised btn-primary'))

                    'input jabatan code'
                    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/input_Jabatan Code_JabatanCodeId'), 
                        datafile.getValue(GlobalVariable.NumofFamily, 44))

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
                        datafile.getValue(GlobalVariable.NumofFamily, 46), false)

                    'input share'
                    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/input_Share()_form-control ng-untouched ng-pristine ng-valid'), 
                        datafile.getValue(GlobalVariable.NumofFamily, 48))

                    if (WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Owner_ng-untouched ng-pristine ng-valid'), 
                        2, FailureHandling.OPTIONAL)) {
                        if (datafile.getValue(GlobalVariable.NumofFamily, 50).equalsIgnoreCase('Yes')) {
                            'click check box is Owner'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/input_Is Owner_ng-untouched ng-pristine ng-valid'))
                        }
                    } else if (WebUI.verifyElementChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Owner_ng-untouched ng-pristine ng-valid'), 
                        2, FailureHandling.OPTIONAL)) {
                        if (datafile.getValue(GlobalVariable.NumofFamily, 50).equalsIgnoreCase('No')) {
                            'click check box is Owner'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/input_Is Owner_ng-untouched ng-pristine ng-valid'))
                        }
                    }
                    
                    if (WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Active_ng-untouched ng-pristine ng-valid'), 
                        2, FailureHandling.OPTIONAL)) {
                        if (datafile.getValue(GlobalVariable.NumofFamily, 49).equalsIgnoreCase('Yes')) {
                            'click check box is Active'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/input_Is Active_ng-untouched ng-pristine ng-valid'))
                        }
                    } else if (WebUI.verifyElementChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/input_Is Active_ng-untouched ng-pristine ng-valid'), 
                        2, FailureHandling.OPTIONAL)) {
                        if (datafile.getValue(GlobalVariable.NumofFamily, 49).equalsIgnoreCase('No')) {
                            'click check box is Active'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Company/input_Is Active_ng-untouched ng-pristine ng-valid'))
                        }
                    }
                    
                }
                'click button save'
                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/Personal/button_Save'))
				
				if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/button_Cancel'),
					5, FailureHandling.OPTIONAL)) {
					'click button cancel'
					WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/button_Cancel'))

					'write to excel failed'
					CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '2.TabManagementShareholderData',
						0, GlobalVariable.NumofFamily - 1, GlobalVariable.StatusFailed)
				} else {
					'write to excel success'
					CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '2.TabManagementShareholderData',
						0, GlobalVariable.NumofFamily - 1, GlobalVariable.StatusSuccess)

					if (flagWarning > 0) {
						CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath,
							'2.TabManagementShareholderData', 0, GlobalVariable.NumofFamily - 1, GlobalVariable.StatusWarning)
					}
					
					'customer added +1'
					(GlobalVariable.countNumofCustomer)++
				}
            }
        }
    }
	
    'click button save'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabManagementShareholderData/button_Save'))
}

