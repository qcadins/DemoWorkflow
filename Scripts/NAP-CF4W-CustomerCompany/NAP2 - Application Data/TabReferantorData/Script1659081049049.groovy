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
import groovy.sql.Sql as Sql
import org.openqa.selenium.WebElement as WebElement

'Assign directori file excel ke global variabel'
String userDir = System.getProperty('user.dir')

'Assign directori file excel ke global variabel'
String filePath = userDir + GlobalVariable.PathCompany

'Assign directori file excel ke global variabel'
GlobalVariable.DataFilePath = filePath

'click menu application data'
WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/a_APPLICATION DATA'))

'input Appno'
WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/input_Application No_AppNoId'), 
    findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofColm, 
        3))

'click button search'
WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/button_Search'))

'click icon pensil untuk select'
WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/i_FT PRODUCT OFFERING CF4W_font-medium-3 ft-edit-2'))

WebUI.delay(5)

if (WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/ApplicationCurrentStep')).equalsIgnoreCase(
    'REFERANTOR')) {
    'Koneksi database'
    String servername = findTestData('Login/Login').getValue(1, 7)

    String instancename = findTestData('Login/Login').getValue(2, 7)

    String username = findTestData('Login/Login').getValue(3, 7)

    String password = findTestData('Login/Login').getValue(4, 7)

    String database = findTestData('Login/Login').getValue(5, 7)

    String driverclassname = findTestData('Login/Login').getValue(6, 7)

    String url = (((servername + ';instanceName=') + instancename) + ';databaseName=') + database

    Sql sqlConnection = CustomKeywords.'dbconnection.connectDB.connect'(url, username, password, driverclassname)

    if (Integer.parseInt(GlobalVariable.CountofReferantorCompany) >= 1) {
        'click referantor checkbox'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/CheckboxReferantor'))

        'click referantor checkbox'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/CheckboxReferantor'))

        int modifyObjectIndex = 1

        'looping referantor'
        for (GlobalVariable.NumofReferantor = 2; GlobalVariable.NumofReferantor <= (Integer.parseInt(GlobalVariable.CountofReferantorCompany) + 
        1); (GlobalVariable.NumofReferantor)++) {
            if (findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData').getValue(GlobalVariable.NumofReferantor, 
                2) == findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofColm, 
                3)) {
                'click button add'
                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/button_Add'))

                String newButtonReferantorXpath = ('//*[@id="accessoriesData"]/div[2]/table/tbody/tr[' + modifyObjectIndex) + 
                ']/td[3]/lib-uclookupgeneric/div/div/div[1]/span/button'

                'modify button referantor lookup'
                modifyObjectButtonReferantor = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/button_LookupReferantor'), 
                    'xpath', 'equals', newButtonReferantorXpath, true)

                String newSelectReferantorCategoryXpath = ('//*[@id="accessoriesData"]/div[2]/table/tbody/tr[' + modifyObjectIndex) + 
                ']/td[2]/select'

                'modify select referantor category'
                modifyObjectSelectReferantorCategory = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/select_ReferantorCategory'), 
                    'xpath', 'equals', newSelectReferantorCategoryXpath, true)

                String newSelectTaxCalculation = ('//*[@id="accessoriesData"]/div[2]/table/tbody/tr[' + modifyObjectIndex) + 
                ']/td[6]/select'

                'modify select tax calculation'
                modifySelectTaxCalcualtion = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/select_TaxCalculationMethod'), 
                    'xpath', 'equals', newSelectTaxCalculation, true)

                String newSelectBankaccount = ('//*[@id="accessoriesData"]/div[2]/table/tbody/tr[' + modifyObjectIndex) + 
                ']/td[5]/select'

                'modify select bank account'
                modifySelectBankAccount = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/select_BankAccount'), 
                    'xpath', 'equals', newSelectBankaccount, true)

                String newButtonDelete = ('//*[@id="accessoriesData"]/div[2]/table/tbody/tr[' + modifyObjectIndex) + ']/td[8]/a/i'

                'modify button delete'
                modifyButtonDelete = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/Button Delete'), 
                    'xpath', 'equals', newButtonDelete, true)

                'select referantor category'
                WebUI.selectOptionByLabel(modifyObjectSelectReferantorCategory, findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData').getValue(
                        GlobalVariable.NumofReferantor, 3), false, FailureHandling.OPTIONAL)

                'click button referantor lookup'
                WebUI.click(modifyObjectButtonReferantor, FailureHandling.OPTIONAL)

                'input referantor name'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/input_Referantor Name_referantorName'), 
                    findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData').getValue(GlobalVariable.NumofReferantor, 
                        4))

                'click button search'
                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/Button_SearchReferantor'))

                String referantorCode

                'verify lookup'
                if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/a_Select'), 
                    5, FailureHandling.OPTIONAL)) {
                    referantorCode = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/span_referantorCode'))

                    'click select'
                    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/a_Select'))
                } else {
                    'click button x'
                    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/button_X'))

                    'write to excel WARNING'
                    CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '5.TabReferantorData', 
                        0, GlobalVariable.NumofReferantor - 1, GlobalVariable.StatusWarning)

                    'Click delete'
                    WebUI.click(modifyButtonDelete, FailureHandling.OPTIONAL)

                    continue
                }
                
                'select bank account'
                WebUI.selectOptionByIndex(modifySelectBankAccount, findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData').getValue(
                        GlobalVariable.NumofReferantor, 6), FailureHandling.OPTIONAL)

                'select tax calculation method'
                WebUI.selectOptionByLabel(modifySelectTaxCalcualtion, findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData').getValue(
                        GlobalVariable.NumofReferantor, 7), false, FailureHandling.OPTIONAL)

                'cek inputan mandatory apakah sudah terisi semua atau belum'
                if ((WebUI.verifyOptionSelectedByIndex(modifyObjectSelectReferantorCategory, 0, 3, FailureHandling.OPTIONAL) || 
                WebUI.verifyOptionSelectedByIndex(modifySelectBankAccount, 0, 3, FailureHandling.OPTIONAL)) || WebUI.verifyOptionSelectedByIndex(
                    modifySelectTaxCalcualtion, 0, 3, FailureHandling.OPTIONAL)) {
                    'write to excel WARNING'
                    CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '5.TabReferantorData', 
                        0, GlobalVariable.NumofReferantor - 1, GlobalVariable.StatusWarning)

                    'Click delete'
                    WebUI.click(modifyButtonDelete, FailureHandling.OPTIONAL)

                    continue
                }
                
                'Ambil dan simpan nilai referantor category dari confins'
                String refCategory = WebUI.getAttribute(modifyObjectSelectReferantorCategory, 'value')

                String newButtonViewDetail = ('//*[@id="accessoriesData"]/div[2]/table/tbody/tr[' + modifyObjectIndex) + 
                ']/td[7]/a/i'

                'modify button view detail'
                modifyButtonViewDetail = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/i_ViewDetail'), 
                    'xpath', 'equals', newButtonViewDetail, true)

                'click button view detail'
                WebUI.click(modifyButtonViewDetail)

                'Pengecekan referantor category (customer, agency, atau mf employee)'
                if (refCategory.equalsIgnoreCase('CUSTOMER')) {
                    'pengecekan ke db dan simpan data-data detail referantor yang dibutuhkan dari db'
                    ArrayList<WebElement> referantorDetail = CustomKeywords.'dbconnection.checkReferantorDetail.checkCustomerReferantor'(
                        sqlConnection, referantorCode)

                    'Ambil dan simpan npwp no dari confins'
                    String textNPWPNo = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/label_NPWPNo'))

                    'Ambil dan simpan npwp name dari confins'
                    String textNPWPName = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/label_NPWPName'))

                    'Ambil dan simpan address dari confins'
                    String textAddress = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/label_Address'))

                    'ambil dan simpan rt dari confins'
                    String textRT = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/label_RT')).replace(
                        ' ', '')

                    'ambil dan simpan rw dari confins'
                    String textRW = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/label_RW')).replace(
                        '/ ', '')

                    'ambil dan simpan kelurahan dari confins'
                    String textKelurahan = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/label_Kelurahan'))

                    'ambil dan simpan kecamatan dari confins'
                    String textKecamatan = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/label_Kecamatan'))

                    'ambil dan simpan kota dari confins'
                    String textCity = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/label_Kota'))

                    'ambil dan simpan zipcode dari confins'
                    String textZipcode = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/label_Zipcode'))

                    'arraylist boolean untuk menampung hasil dari verififikasi data dari confins sesuai dengan db atau tidak (true atau false)'
                    ArrayList<WebElement> arrayMatch = new ArrayList<WebElement>()

                    arrayMatch.add(WebUI.verifyMatch(textNPWPNo, referantorDetail[0], false, FailureHandling.OPTIONAL))

                    arrayMatch.add(WebUI.verifyMatch(textNPWPName, referantorDetail[1], false, FailureHandling.OPTIONAL))

                    arrayMatch.add(WebUI.verifyMatch(textAddress, referantorDetail[2], false, FailureHandling.OPTIONAL))

                    arrayMatch.add(WebUI.verifyMatch(textRT, referantorDetail[3], false, FailureHandling.OPTIONAL))

                    arrayMatch.add(WebUI.verifyMatch(textRW, referantorDetail[4], false, FailureHandling.OPTIONAL))

                    arrayMatch.add(WebUI.verifyMatch(textKelurahan, referantorDetail[5], false, FailureHandling.OPTIONAL))

                    arrayMatch.add(WebUI.verifyMatch(textKecamatan, referantorDetail[6], false, FailureHandling.OPTIONAL))

                    arrayMatch.add(WebUI.verifyMatch(textCity, referantorDetail[7], false, FailureHandling.OPTIONAL))

                    arrayMatch.add(WebUI.verifyMatch(textZipcode, referantorDetail[8], false, FailureHandling.OPTIONAL))

                    'Jika nilai di confins ada yang tidak sesuai dengan db'
                    if (arrayMatch.contains(false)) {
                        'write to excel WARNING'
                        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '5.TabReferantorData', 
                            0, GlobalVariable.NumofReferantor - 1, GlobalVariable.StatusWarning)

                        modifyObjectIndex++

                        'click cancel'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/button_CancelViewDetail'))

                        continue
                    }
                } else if (refCategory.equalsIgnoreCase('AGENCY')) {
                    'Pengecekan ke db dan simpan data-data detail referantor yang dibutuhkan dari db'
                    ArrayList<WebElement> referantorDetail = CustomKeywords.'dbconnection.checkReferantorDetail.checkAgencyReferantor'(
                        sqlConnection, referantorCode)

                    'Ambil dan simpan npwp no dari confins'
                    String textNPWPNo = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/label_NPWPNo'))

                    'Ambil dan simpan npwp name dari confins'
                    String textNPWPName = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/label_NPWPName'))

                    'Ambil dan simpan address dari confins'
                    String textAddress = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/label_Address'))

                    'ambil dan simpan rt dari confins'
                    String textRT = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/label_RT')).replace(
                        ' ', '')

                    'ambil dan simpan rw dari confins'
                    String textRW = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/label_RW')).replace(
                        '/ ', '')

                    'ambil dan simpan kelurahan dari confins'
                    String textKelurahan = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/label_Kelurahan'))

                    'ambil dan simpan kecamatan dari confins'
                    String textKecamatan = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/label_Kecamatan'))

                    'ambil dan simpan kota dari confins'
                    String textCity = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/label_Kota'))

                    'ambil dan simpan zipcode dari confins'
                    String textZipcode = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/label_Zipcode'))

                    'arraylist boolean untuk menampung hasil dari verififikasi data dari confins sesuai dengan db atau tidak (true atau false)'
                    ArrayList<WebElement> arrayMatch = new ArrayList<WebElement>()

                    arrayMatch.add(WebUI.verifyMatch(textNPWPNo, referantorDetail[0], false, FailureHandling.OPTIONAL))

                    arrayMatch.add(WebUI.verifyMatch(textNPWPName, referantorDetail[1], false, FailureHandling.OPTIONAL))

                    arrayMatch.add(WebUI.verifyMatch(textAddress, referantorDetail[2], false, FailureHandling.OPTIONAL))

                    arrayMatch.add(WebUI.verifyMatch(textRT, referantorDetail[3], false, FailureHandling.OPTIONAL))

                    arrayMatch.add(WebUI.verifyMatch(textRW, referantorDetail[4], false, FailureHandling.OPTIONAL))

                    arrayMatch.add(WebUI.verifyMatch(textKelurahan, referantorDetail[5], false, FailureHandling.OPTIONAL))

                    arrayMatch.add(WebUI.verifyMatch(textKecamatan, referantorDetail[6], false, FailureHandling.OPTIONAL))

                    arrayMatch.add(WebUI.verifyMatch(textCity, referantorDetail[7], false, FailureHandling.OPTIONAL))

                    arrayMatch.add(WebUI.verifyMatch(textZipcode, referantorDetail[8], false, FailureHandling.OPTIONAL))

                    'Jika nilai di confins ada yang tidak sesuai dengan db'
                    if (arrayMatch.contains(false)) {
                        'write to excel WARNING'
                        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '5.TabReferantorData', 
                            0, GlobalVariable.NumofReferantor - 1, GlobalVariable.StatusWarning)

                        modifyObjectIndex++

                        'click cancel'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/button_CancelViewDetail'))

                        continue
                    }
                } else {
                    'Pengecekan ke db dan simpan data-data detail referantor yang dibutuhkan dari db'
                    ArrayList<WebElement> referantorDetail = CustomKeywords.'dbconnection.checkReferantorDetail.checkMFEmployeeReferantor'(
                        sqlConnection, referantorCode)

                    'Ambil dan simpan npwp no dari confins'
                    String textNPWPNo = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/label_NPWPNo'))

                    'Ambil dan simpan npwp name dari confins'
                    String textNPWPName = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/label_NPWPName'))

                    'Ambil dan simpan address dari confins'
                    String textAddress = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/label_Address'))

                    'ambil dan simpan rt dari confins'
                    String textRT = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/label_RT')).replace(
                        ' ', '')

                    'ambil dan simpan rw dari confins'
                    String textRW = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/label_RW')).replace(
                        '/ ', '')

                    'ambil dan simpan kelurahan dari confins'
                    String textKelurahan = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/label_Kelurahan'))

                    'ambil dan simpan kecamatan dari confins'
                    String textKecamatan = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/label_Kecamatan'))

                    'ambil dan simpan kota dari confins'
                    String textCity = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/label_Kota'))

                    'ambil dan simpan zipcode dari confins'
                    String textZipcode = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/label_Zipcode'))

                    'arraylist boolean untuk menampung hasil dari verififikasi data dari confins sesuai dengan db atau tidak (true atau false)'
                    ArrayList<WebElement> arrayMatch = new ArrayList<WebElement>()

                    arrayMatch.add(WebUI.verifyMatch(textNPWPNo, referantorDetail[0], false, FailureHandling.OPTIONAL))

                    arrayMatch.add(WebUI.verifyMatch(textNPWPName, referantorDetail[1], false, FailureHandling.OPTIONAL))

                    arrayMatch.add(WebUI.verifyMatch(textAddress, referantorDetail[2], false, FailureHandling.OPTIONAL))

                    arrayMatch.add(WebUI.verifyMatch(textRT, referantorDetail[3], false, FailureHandling.OPTIONAL))

                    arrayMatch.add(WebUI.verifyMatch(textRW, referantorDetail[4], false, FailureHandling.OPTIONAL))

                    arrayMatch.add(WebUI.verifyMatch(textKelurahan, referantorDetail[5], false, FailureHandling.OPTIONAL))

                    arrayMatch.add(WebUI.verifyMatch(textKecamatan, referantorDetail[6], false, FailureHandling.OPTIONAL))

                    arrayMatch.add(WebUI.verifyMatch(textCity, referantorDetail[7], false, FailureHandling.OPTIONAL))

                    arrayMatch.add(WebUI.verifyMatch(textZipcode, referantorDetail[8], false, FailureHandling.OPTIONAL))

                    'Jika nilai di confins ada yang tidak sesuai dengan db'
                    if (arrayMatch.contains(false)) {
                        'write to excel WARNING'
                        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '5.TabReferantorData', 
                            0, GlobalVariable.NumofReferantor - 1, GlobalVariable.StatusWarning)

                        modifyObjectIndex++

                        'click cancel'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/button_CancelViewDetail'))

                        continue
                    }
                }
                
                'click x'
                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/span_XViewDetail'))

                'write to excel SUCCESS'
                CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '5.TabReferantorData', 
                    0, GlobalVariable.NumofReferantor - 1, GlobalVariable.StatusSuccess)

                modifyObjectIndex++
            }
        }
    }
    
    'click button save'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/Button Save'))

    if (WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/ApplicationCurrentStep')), 
        'REFERANTOR', false, FailureHandling.OPTIONAL)) {
        'click button cancel'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/button_Cancel'))
    }
}

