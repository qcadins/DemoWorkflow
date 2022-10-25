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
import org.openqa.selenium.WebDriver as WebDriver
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.By as By
import org.openqa.selenium.support.ui.Select as Select

'Assign directori file excel ke global variabel'
String userDir = System.getProperty('user.dir')

'Assign directori file excel ke global variabel'
String filePath = userDir + GlobalVariable.PathCompany

'Assign directori file excel ke global variabel'
GlobalVariable.DataFilePath = filePath

'arraylist referantor name yang gagal'
ArrayList<String> referantorfaileddelete = new ArrayList<String>()

if (GlobalVariable.Role == 'Testing') {
    'verify application step'
    WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/ApplicationCurrentStep')), 
        'REFERANTOR', false, FailureHandling.OPTIONAL)
}

datafilereferantor = findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData')

'Koneksi database'
String servername = findTestData('Login/Login').getValue(1, 8)

String instancename = findTestData('Login/Login').getValue(2, 8)

String username = findTestData('Login/Login').getValue(3, 8)

String password = findTestData('Login/Login').getValue(4, 8)

String database = findTestData('Login/Login').getValue(5, 7)

String driverclassname = findTestData('Login/Login').getValue(6, 8)

String url = (((servername + ';instanceName=') + instancename) + ';databaseName=') + database

Sql sqlConnection = CustomKeywords.'dbconnection.connectDB.connect'(url, username, password, driverclassname)

WebDriver driver = DriverFactory.getWebDriver()

'Ambil text original office dari confins'
String officeName = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/label_OriginalOffice'))

//Jika copy app edit
if (datafilereferantor.getValue(GlobalVariable.CopyAppColm, 10).equalsIgnoreCase('Edit')) {
    //pengecekan pada excel data referantor ada lebih dari atau sama dengan 1
    if (Integer.parseInt(GlobalVariable.CountofReferantor) >= 1) {
        'Pengecekan checkbox sebelumnya tidak tercentang'
        if (WebUI.verifyElementNotChecked(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/CheckboxReferantor'), 
            3, FailureHandling.OPTIONAL)) {
            'click referantor checkbox'
            WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/CheckboxReferantor'))
        }
    }
    
    ArrayList<String> variable = driver.findElements(By.cssSelector('#accessoriesData > div.table-responsive > table > tbody > tr'))

    //Edit jika ada data pada confins dan datanya sesuai dengan excel
    'Pengecekan ada data referantor pada confins'
    if (WebUI.verifyNotMatch(WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/TableReferantornodata'),FailureHandling.OPTIONAL), 
        'NO DATA AVAILABLE', false, FailureHandling.OPTIONAL)) {
        'Looping confins referantor'
        for (int i = 1; i <= variable.size(); i++) {
            String newReferantorNameXpath = ('//*[@id=\'accessoriesData\']/div[2]/table/tbody/tr[' + i) + ']/td[3]/lib-uclookupgeneric/div/div/input'

            'modify input referantor name'
            modifyObjectReferantorName = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/input_referantorName'), 
                'xpath', 'equals', newReferantorNameXpath, true)

            String newButtonReferantorXpath = ('//*[@id="accessoriesData"]/div[2]/table/tbody/tr[' + i) + ']/td[3]/lib-uclookupgeneric/div/div/div[1]/span/button'

            'modify button referantor lookup'
            modifyObjectButtonReferantor = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/button_LookupReferantor'), 
                'xpath', 'equals', newButtonReferantorXpath, true)

            String newSelectReferantorCategoryXpath = ('//*[@id="accessoriesData"]/div[2]/table/tbody/tr[' + i) + ']/td[2]/select'

            'modify select referantor category'
            modifyObjectSelectReferantorCategory = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/select_ReferantorCategory'), 
                'xpath', 'equals', newSelectReferantorCategoryXpath, true)

            String newButtonDelete = ('//*[@id="accessoriesData"]/div[2]/table/tbody/tr[' + i) + ']/td[9]/a/i'

            'modify button delete'
            modifyButtonDelete = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/Button Delete'), 
                'xpath', 'equals', newButtonDelete, true)

            String newSelectTaxCalculation = ('//*[@id="accessoriesData"]/div[2]/table/tbody/tr[' + i) + ']/td[6]/select'

            'modify select tax calculation'
            modifySelectTaxCalcualtion = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/select_TaxCalculationMethod'), 
                'xpath', 'equals', newSelectTaxCalculation, true)

            String newSelectBankaccount = ('//*[@id="accessoriesData"]/div[2]/table/tbody/tr[' + i) + ']/td[5]/select'

            'modify select bank account'
            modifySelectBankAccount = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/select_BankAccount'), 
                'xpath', 'equals', newSelectBankaccount, true)

            Select selectedRefCategory = new Select(DriverFactory.getWebDriver().findElement(By.xpath(newSelectReferantorCategoryXpath)))

            'Ambil text referantor category yang dipilih pada confins'
            String textRefCategory = selectedRefCategory.getFirstSelectedOption().getText()

            'Looping excel referantor'
            for (GlobalVariable.NumofReferantor = GlobalVariable.CopyAppColm; GlobalVariable.NumofReferantor <= (Integer.parseInt(
                GlobalVariable.CountofReferantor) + 2); (GlobalVariable.NumofReferantor)++) {
                if (datafilereferantor.getValue(GlobalVariable.NumofReferantor, 12) == findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
                    GlobalVariable.NumofColm, 13)) {
                    'Pengecekan jika referantor category dan referantor name pada confins sesuai dengan excel datafile'
                    if (textRefCategory.equalsIgnoreCase(datafilereferantor.getValue(GlobalVariable.NumofReferantor, 13)) && 
                    WebUI.getAttribute(modifyObjectReferantorName, 'value').equalsIgnoreCase(datafilereferantor.getValue(
                            GlobalVariable.NumofReferantor, 14))) {
                        'select bank account'
                        WebUI.selectOptionByIndex(modifySelectBankAccount, datafilereferantor.getValue(GlobalVariable.NumofReferantor, 
                                16), FailureHandling.OPTIONAL)

                        'call function gettext bankaccount'
                        getTextBankAccount(newSelectBankaccount)

                        'select tax calculation'
                        WebUI.selectOptionByLabel(modifySelectTaxCalcualtion, datafilereferantor.getValue(GlobalVariable.NumofReferantor, 
                                17), false, FailureHandling.OPTIONAL)

                        'cek inputan mandatory apakah sudah terisi semua atau belum'
                        if ((WebUI.verifyOptionSelectedByIndex(modifyObjectSelectReferantorCategory, 0, 3, FailureHandling.OPTIONAL) || 
                        WebUI.verifyOptionSelectedByIndex(modifySelectBankAccount, 0, 3, FailureHandling.OPTIONAL)) || WebUI.verifyOptionSelectedByIndex(
                            modifySelectTaxCalcualtion, 0, 3, FailureHandling.OPTIONAL)) {
                            'write to excel WARNING'
                            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, 
                                '5.TabReferantorData', 0, GlobalVariable.NumofReferantor - 1, GlobalVariable.StatusWarning)

                            'Write To Excel GlobalVariable.StatusReasonLookup'
                            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, 
                                '5.TabReferantorData', 1, GlobalVariable.NumofReferantor - 1, GlobalVariable.StatusReasonMandatoryEmpty)

                            'get referantor name'
                            referantornamebefore = WebUI.getAttribute(modifyObjectReferantorName, 'value', FailureHandling.OPTIONAL)

                            'Click delete'
                            WebUI.click(modifyButtonDelete, FailureHandling.OPTIONAL)

                            if (i == variable.size()) {
                                if (WebUI.verifyElementNotPresent(modifyObjectReferantorName, 5, FailureHandling.OPTIONAL)) {
                                    variable = driver.findElements(By.cssSelector('#accessoriesData > div.table-responsive > table > tbody > tr'))
                                } else {
                                    'add cust name failed kedalam array'
                                    referantorfaileddelete.add(referantornamebefore)

                                    continue
                                }
                            } else {
                                'get cust name sebelum delete'
                                referantornameafter = WebUI.getAttribute(modifyObjectReferantorName, 'value', FailureHandling.OPTIONAL)

                                if (WebUI.verifyNotMatch(referantornameafter, referantornamebefore, false, FailureHandling.OPTIONAL)) {
                                    variable = driver.findElements(By.cssSelector('#accessoriesData > div.table-responsive > table > tbody > tr'))
                                } else {
                                    'add cust name failed kedalam array'
                                    referantorfaileddelete.add(referantornamebefore)

                                    continue
                                }
                            }
                            
                            i--
                        }
                        
                        break
                    } //delete jika ada data pada confins, tetapi pada datafile tidak ada
                } else {
                    if (GlobalVariable.NumofReferantor == (Integer.parseInt(GlobalVariable.CountofReferantor) + 2)) {
                        'Jika pada confins ada datanya'
                        if (WebUI.verifyNotMatch(WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/TableReferantornodata'),FailureHandling.OPTIONAL), 
                            'NO DATA AVAILABLE', false, FailureHandling.OPTIONAL)) {
                            'get referantor name'
                            referantornamebefore = WebUI.getAttribute(modifyObjectReferantorName, 'value', FailureHandling.OPTIONAL)

                            'Click delete'
                            WebUI.click(modifyButtonDelete, FailureHandling.OPTIONAL)

                            if (i == variable.size()) {
                                if (WebUI.verifyElementNotPresent(modifyObjectReferantorName, 5, FailureHandling.OPTIONAL)) {
                                    variable = driver.findElements(By.cssSelector('#accessoriesData > div.table-responsive > table > tbody > tr'))
                                } else {
                                    'add cust name failed kedalam array'
                                    referantorfaileddelete.add(referantornamebefore)

                                    continue
                                }
                            } else {
                                'get cust name sebelum delete'
                                referantornameafter = WebUI.getAttribute(modifyObjectReferantorName, 'value', FailureHandling.OPTIONAL)

                                if (WebUI.verifyNotMatch(referantornameafter, referantornamebefore, false, FailureHandling.OPTIONAL)) {
                                    variable = driver.findElements(By.cssSelector('#accessoriesData > div.table-responsive > table > tbody > tr'))
                                } else {
                                    'add cust name failed kedalam array'
                                    referantorfaileddelete.add(referantornamebefore)

                                    continue
                                }
                            }
                            
                            i--
                        } else {
                            break
                        }
                    }
                }
            }
        }
    }
    
    if (referantorfaileddelete.size() > 0) {
        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '5.TabReferantorData', 
            0, GlobalVariable.CopyAppColm - 1, GlobalVariable.StatusWarning)

        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '5.TabReferantorData', 
            1, GlobalVariable.CopyAppColm - 1, GlobalVariable.ReasonFailedDelete + referantorfaileddelete)

        (GlobalVariable.FlagWarning)++
    }
    
    ArrayList<String> variableData = driver.findElements(By.cssSelector('#accessoriesData > div.table-responsive > table > tbody > tr'))

    int countData = variableData.size()

    int add = 0

    //Add data jika pada confins tidak ada datanya (yang mau diadd), tetapi pada excel ada
    'Looping excel referantor'
    for (GlobalVariable.NumofReferantor = GlobalVariable.CopyAppColm; GlobalVariable.NumofReferantor <= (Integer.parseInt(
        GlobalVariable.CountofReferantor) + 2); (GlobalVariable.NumofReferantor)++) {
        if (datafilereferantor.getValue(GlobalVariable.NumofReferantor, 12) == findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
            GlobalVariable.NumofColm, 13)) {
            'Looping confins referantor'
            for (int j = 1; j <= countData; j++) {
                String newReferantorNameXpath = ('//*[@id=\'accessoriesData\']/div[2]/table/tbody/tr[' + j) + ']/td[3]/lib-uclookupgeneric/div/div/input'

                'modify input referantor name'
                modifyObjectReferantorName = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/input_referantorName'), 
                    'xpath', 'equals', newReferantorNameXpath, true)

                String newButtonReferantorXpath = ('//*[@id="accessoriesData"]/div[2]/table/tbody/tr[' + j) + ']/td[3]/lib-uclookupgeneric/div/div/div[1]/span/button'

                'modify button referantor lookup'
                modifyObjectButtonReferantor = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/button_LookupReferantor'), 
                    'xpath', 'equals', newButtonReferantorXpath, true)

                String newSelectReferantorCategoryXpath = ('//*[@id="accessoriesData"]/div[2]/table/tbody/tr[' + j) + ']/td[2]/select'

                'modify select referantor category'
                modifyObjectSelectReferantorCategory = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/select_ReferantorCategory'), 
                    'xpath', 'equals', newSelectReferantorCategoryXpath, true)

                String newButtonDelete = ('//*[@id="accessoriesData"]/div[2]/table/tbody/tr[' + j) + ']/td[9]/a/i'

                'modify button delete'
                modifyButtonDelete = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/Button Delete'), 
                    'xpath', 'equals', newButtonDelete, true)

                String newSelectTaxCalculation = ('//*[@id="accessoriesData"]/div[2]/table/tbody/tr[' + j) + ']/td[6]/select'

                'modify select tax calculation'
                modifySelectTaxCalcualtion = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/select_TaxCalculationMethod'), 
                    'xpath', 'equals', newSelectTaxCalculation, true)

                String newSelectBankaccount = ('//*[@id="accessoriesData"]/div[2]/table/tbody/tr[' + j) + ']/td[5]/select'

                'modify select bank account'
                modifySelectBankAccount = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/select_BankAccount'), 
                    'xpath', 'equals', newSelectBankaccount, true)

                String refCategory

                String referantorCode

                'Jika pada confins ada data referantor sebelumnya'
                if (WebUI.verifyNotMatch(WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/TableReferantornodata'),FailureHandling.OPTIONAL), 
                    'NO DATA AVAILABLE', false, FailureHandling.OPTIONAL)) {
                    if (add == 1) {
                        'select referantor category'
                        WebUI.selectOptionByLabel(modifyObjectSelectReferantorCategory, datafilereferantor.getValue(GlobalVariable.NumofReferantor, 
                                13), false, FailureHandling.OPTIONAL)

                        'Ambil dan simpan nilai referantor category dari confins'
                        refCategory = WebUI.getAttribute(modifyObjectSelectReferantorCategory, 'value')

                        'click button referantor lookup'
                        WebUI.click(modifyObjectButtonReferantor, FailureHandling.OPTIONAL)

                        if (GlobalVariable.Role == 'Testing') {
                            if (refCategory != '') {
                                'click button search'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/Button_SearchReferantor'))

                                'Cek total data referantor pada db'
                                Integer countReferantor = CustomKeywords.'dbconnection.checkReferantor.countReferantorLookup'(
                                    sqlConnection, refCategory, officeName)

                                'Ambil nilai total data referantor pada lookup confins'
                                String[] textTotalDataReferantor = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/label_TotalDataOfficer')).replace(
                                    ' ', '').replace(':', ';').split(';')

                                'Parsing nilai total data referantor confins ke integer(angka)'
                                Integer totalDataReferantor = Integer.parseInt(textTotalDataReferantor[1])

                                'Verif total data referantor confins sesuai dengan db'
                                if (WebUI.verifyEqual(totalDataReferantor, countReferantor) == false) {
                                    writeReasonFailedLookup()
                                }
                            }
                        }
                        
                        'input referantor name'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/input_Referantor Name_referantorName'), 
                            datafilereferantor.getValue(GlobalVariable.NumofReferantor, 14))

                        'click button search'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/Button_SearchReferantor'))

                        'verify lookup'
                        if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/a_Select'), 
                            5, FailureHandling.OPTIONAL)) {
                            referantorCode = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/span_referantorCode'))

                            'click select'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/a_Select'))
                        } else {
                            'click button x'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/button_X'))

                            'write to excel WARNING'
                            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, 
                                '5.TabReferantorData', 0, GlobalVariable.NumofReferantor - 1, GlobalVariable.StatusWarning)

                            'Write To Excel GlobalVariable.StatusReasonLookup'
                            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, 
                                '5.TabReferantorData', 1, GlobalVariable.NumofReferantor - 1, GlobalVariable.StatusReasonLookup)

                            'Click delete'
                            WebUI.click(modifyButtonDelete, FailureHandling.OPTIONAL)

                            if (WebUI.verifyElementPresent(modifyButtonDelete, 5, FailureHandling.OPTIONAL)) {
                            }
                            
                            continue
                        }
                        
                        if (GlobalVariable.Role == 'Testing') {
                            'Pengecekan terdapat opsi ddl bank account selain select one'
                            if (WebUI.getNumberOfTotalOption(modifySelectBankAccount) > 1) {
                                'Arraylist untuk menampung ddl bank account dari db'
                                ArrayList<String> BankAccount = new ArrayList<String>()

                                'Ambil array teks bank account dari db'
                                BankAccount = CustomKeywords.'dbconnection.checkReferantor.checkBankAccountDDL'(sqlConnection, 
                                    refCategory, officeName, referantorCode)

                                'Verifikasi array teks bank account dari db sesuai dengan ddl yang tampil pada confins'
                                if (WebUI.verifyOptionsPresent(modifySelectBankAccount, BankAccount) == false) {
                                    writeReasonFailedDDL()
                                }
                                
                                'Pengecekan referantor category yang dipilih customer atau agency'
                                if (refCategory.equalsIgnoreCase('Customer') || refCategory.equalsIgnoreCase('Agency')) {
                                    'Ambil teks default bank account dari db'
                                    String defaultBankAccount = CustomKeywords.'dbconnection.checkReferantor.checkBankAccountDefault'(
                                        sqlConnection, refCategory, officeName, referantorCode)

                                    'Verifikasi opsi yang terpilih secara default pada confins sesuai dengan db'
                                    if (WebUI.verifyOptionSelectedByLabel(modifySelectBankAccount, '(?i)' + defaultBankAccount, 
                                        true, 2) == false) {
                                        writeReasonFailedDDL()
                                    }
                                }
                            }
                        }
                        
                        'select bank account'
                        WebUI.selectOptionByIndex(modifySelectBankAccount, datafilereferantor.getValue(GlobalVariable.NumofReferantor, 
                                16), FailureHandling.OPTIONAL)

                        'call function gettext bankaccount'
                        getTextBankAccount(newSelectBankaccount)

                        'select tax calculation method'
                        WebUI.selectOptionByLabel(modifySelectTaxCalcualtion, datafilereferantor.getValue(GlobalVariable.NumofReferantor, 
                                17), false, FailureHandling.OPTIONAL)

                        'cek inputan mandatory apakah sudah terisi semua atau belum'
                        if ((WebUI.verifyOptionSelectedByIndex(modifyObjectSelectReferantorCategory, 0, 3, FailureHandling.OPTIONAL) || 
                        WebUI.verifyOptionSelectedByIndex(modifySelectBankAccount, 0, 3, FailureHandling.OPTIONAL)) || WebUI.verifyOptionSelectedByIndex(
                            modifySelectTaxCalcualtion, 0, 3, FailureHandling.OPTIONAL)) {
                            'write to excel WARNING'
                            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, 
                                '5.TabReferantorData', 0, GlobalVariable.NumofReferantor - 1, GlobalVariable.StatusWarning)

                            'Write To Excel GlobalVariable.StatusReasonLookup'
                            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, 
                                '5.TabReferantorData', 1, GlobalVariable.NumofReferantor - 1, GlobalVariable.StatusReasonMandatoryEmpty)

                            'Click delete'
                            WebUI.click(modifyButtonDelete, FailureHandling.OPTIONAL)

                            if (WebUI.verifyElementPresent(modifyButtonDelete, 5, FailureHandling.OPTIONAL)) {
                                writeReasonFailedDelete()
                            }
                            
                            continue
                        }
                        
                        add = 0
                    }
                    
                    Select selectedRefCategory = new Select(DriverFactory.getWebDriver().findElement(By.xpath(newSelectReferantorCategoryXpath)))

                    'Ambil referantor category yang dipilih pada confins'
                    String textRefCategory = selectedRefCategory.getFirstSelectedOption().getText()

                    if (!(datafilereferantor.getValue(GlobalVariable.NumofReferantor, 13).equalsIgnoreCase(textRefCategory)) || 
                    !(datafilereferantor.getValue(GlobalVariable.NumofReferantor, 14).equalsIgnoreCase(WebUI.getAttribute(
                            modifyObjectReferantorName, 'value')))) {
                        if (countData == j) {
                            'click button add'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/button_Add'))

                            variableData = driver.findElements(By.cssSelector('#accessoriesData > div.table-responsive > table > tbody > tr'))

                            countData = variableData.size()

                            add = 1
                        }
                    } //jika pada confins tidak ada data referantor
                    else {
                        break
                    }
                } else if (WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/TableReferantornodata'),FailureHandling.OPTIONAL), 
                    'NO DATA AVAILABLE', false, FailureHandling.OPTIONAL)) {
                    'click button add'
                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/button_Add'))

                    'select referantor category'
                    WebUI.selectOptionByLabel(modifyObjectSelectReferantorCategory, datafilereferantor.getValue(GlobalVariable.NumofReferantor, 
                            13), false, FailureHandling.OPTIONAL)

                    'Ambil dan simpan nilai referantor category dari confins'
                    refCategory = WebUI.getAttribute(modifyObjectSelectReferantorCategory, 'value')

                    'click button referantor lookup'
                    WebUI.click(modifyObjectButtonReferantor, FailureHandling.OPTIONAL)

                    if (GlobalVariable.Role == 'Testing') {
                        if (refCategory != '') {
                            'click button search'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/Button_SearchReferantor'))

                            'Cek total data referantor pada db'
                            Integer countReferantor = CustomKeywords.'dbconnection.checkReferantor.countReferantorLookup'(
                                sqlConnection, refCategory, officeName)

                            'Ambil nilai total data referantor pada lookup confins'
                            String[] textTotalDataReferantor = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/label_TotalDataOfficer')).replace(
                                ' ', '').replace(':', ';').split(';')

                            'Parsing nilai total data referantor confins ke integer(angka)'
                            Integer totalDataReferantor = Integer.parseInt(textTotalDataReferantor[1])

                            'Verif total data referantor confins sesuai dengan db'
                            if (WebUI.verifyEqual(totalDataReferantor, countReferantor) == false) {
                                writeReasonFailedLookup()
                            }
                        }
                    }
                    
                    'input referantor name'
                    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/input_Referantor Name_referantorName'), 
                        datafilereferantor.getValue(GlobalVariable.NumofReferantor, 14))

                    'click button search'
                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/Button_SearchReferantor'))

                    'verify lookup'
                    if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/a_Select'), 
                        5, FailureHandling.OPTIONAL)) {
                        referantorCode = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/span_referantorCode'))

                        'click select'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/a_Select'))
                    } else {
                        'click button x'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/button_X'))

                        'write to excel WARNING'
                        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '5.TabReferantorData', 
                            0, GlobalVariable.NumofReferantor - 1, GlobalVariable.StatusWarning)

                        'Write To Excel GlobalVariable.StatusReasonLookup'
                        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '5.TabReferantorData', 
                            1, GlobalVariable.NumofReferantor - 1, GlobalVariable.StatusReasonLookup)

                        'get referantor name'
                        referantornamebefore = WebUI.getAttribute(modifyObjectReferantorName, 'value', FailureHandling.OPTIONAL)

                        'Click delete'
                        WebUI.click(modifyButtonDelete, FailureHandling.OPTIONAL)

                        if (WebUI.verifyElementPresent(modifyButtonDelete, 5, FailureHandling.OPTIONAL)) {
                            writeReasonFailedDelete()
                        }
                        
                        continue
                    }
                    
                    if (GlobalVariable.Role == 'Testing') {
                        'Pengecekan terdapat opsi ddl bank account selain select one'
                        if (WebUI.getNumberOfTotalOption(modifySelectBankAccount) > 1) {
                            'Arraylist untuk menampung ddl bank account dari db'
                            ArrayList<String> BankAccount = new ArrayList<String>()

                            'Ambil array teks bank account dari db'
                            BankAccount = CustomKeywords.'dbconnection.checkReferantor.checkBankAccountDDL'(sqlConnection, 
                                refCategory, officeName, referantorCode)

                            'Verifikasi array teks bank account dari db sesuai dengan ddl yang tampil pada confins'
                            if (WebUI.verifyOptionsPresent(modifySelectBankAccount, BankAccount) == false) {
                                writeReasonFailedDDL()
                            }
                            
                            'Pengecekan referantor category yang dipilih customer atau agency'
                            if (refCategory.equalsIgnoreCase('Customer') || refCategory.equalsIgnoreCase('Agency')) {
                                'Ambil teks default bank account dari db'
                                String defaultBankAccount = CustomKeywords.'dbconnection.checkReferantor.checkBankAccountDefault'(
                                    sqlConnection, refCategory, officeName, referantorCode)

                                'Verifikasi opsi yang terpilih secara default pada confins sesuai dengan db'
                                if (WebUI.verifyOptionSelectedByLabel(modifySelectBankAccount, '(?i)' + defaultBankAccount, 
                                    true, 2) == false) {
                                    writeReasonFailedDDL()
                                }
                            }
                        }
                    }
                    
                    'select bank account'
                    WebUI.selectOptionByIndex(modifySelectBankAccount, datafilereferantor.getValue(GlobalVariable.NumofReferantor, 
                            16), FailureHandling.OPTIONAL)

                    'call function gettext bankaccount'
                    getTextBankAccount(newSelectBankaccount)

                    'select tax calculation method'
                    WebUI.selectOptionByLabel(modifySelectTaxCalcualtion, datafilereferantor.getValue(GlobalVariable.NumofReferantor, 
                            17), false, FailureHandling.OPTIONAL)

                    'cek inputan mandatory apakah sudah terisi semua atau belum'
                    if ((WebUI.verifyOptionSelectedByIndex(modifyObjectSelectReferantorCategory, 0, 3, FailureHandling.OPTIONAL) || 
                    WebUI.verifyOptionSelectedByIndex(modifySelectBankAccount, 0, 3, FailureHandling.OPTIONAL)) || WebUI.verifyOptionSelectedByIndex(
                        modifySelectTaxCalcualtion, 0, 3, FailureHandling.OPTIONAL)) {
                        'write to excel WARNING'
                        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '5.TabReferantorData', 
                            0, GlobalVariable.NumofReferantor - 1, GlobalVariable.StatusWarning)

                        'Write To Excel GlobalVariable.StatusReasonLookup'
                        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '5.TabReferantorData', 
                            1, GlobalVariable.NumofReferantor - 1, GlobalVariable.StatusReasonMandatoryEmpty)

                        'Click delete'
                        WebUI.click(modifyButtonDelete, FailureHandling.OPTIONAL)

                        if (WebUI.verifyElementPresent(modifyButtonDelete, 5, FailureHandling.OPTIONAL)) {
                            writeReasonFailedDelete()
                        }
                        
                        'get referantor name'
                        referantornamebefore = WebUI.getAttribute(modifyObjectReferantorName, 'value', FailureHandling.OPTIONAL)

                        variable = driver.findElements(By.cssSelector('#accessoriesData > div.table-responsive > table > tbody > tr'))

                        'Click delete'
                        WebUI.click(modifyButtonDelete, FailureHandling.OPTIONAL)

                        if (j == variable.size()) {
                            if (WebUI.verifyElementNotPresent(modifyObjectReferantorName, 5, FailureHandling.OPTIONAL)) {
                                continue
                            } else {
                                'add cust name failed kedalam array'
                                referantorfaileddelete.add(referantornamebefore)
                            }
                        } else {
                            'get cust name sebelum delete'
                            referantornameafter = WebUI.getAttribute(modifyObjectReferantorName, 'value', FailureHandling.OPTIONAL)

                            if (WebUI.verifyNotMatch(referantornameafter, referantornamebefore, false, FailureHandling.OPTIONAL)) {
                                continue
                            } else {
                                'add cust name failed kedalam array'
                                referantorfaileddelete.add(referantornamebefore)
                            }
                        }
                        
                        j--

                        continue
                    }
                }
                
                Integer iscompleteMandatory = Integer.parseInt(datafilereferantor.getValue(GlobalVariable.NumofReferantor, 
                        4))

                if (iscompleteMandatory == 0) {
                    errorValObject = findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/div_errorvalidation')

                    'cek validasi'
                    CustomKeywords.'checkSaveProcess.checkSaveProcess.checkValidasi'(errorValObject, GlobalVariable.NumofReferantor, 
                        '5.TabReferantorData')
                }
                
                'write to excel SUCCESS'
                CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '5.TabReferantorData', 
                    0, GlobalVariable.NumofReferantor - 1, GlobalVariable.StatusSuccess)
            }
        }
    }
}

//Jika copy app no
if (datafilereferantor.getValue(GlobalVariable.CopyAppColm, 10).equalsIgnoreCase('No')) {
    if (Integer.parseInt(GlobalVariable.CountofReferantor) >= 1) {
        if (WebUI.verifyElementNotChecked(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/CheckboxReferantor'), 
            3, FailureHandling.OPTIONAL)) {
            'click referantor checkbox'
            WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/CheckboxReferantor'))
        }
        
        int modifyObjectIndex = 1

        'looping referantor'
        for (GlobalVariable.NumofReferantor = 2; GlobalVariable.NumofReferantor <= (Integer.parseInt(GlobalVariable.CountofReferantor) + 
        1); (GlobalVariable.NumofReferantor)++) {
            if (datafilereferantor.getValue(GlobalVariable.NumofReferantor, 12) == findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
                GlobalVariable.NumofColm, 13)) {
                'click button add'
                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/button_Add'))

                String newButtonReferantorXpath = ('//*[@id="accessoriesData"]/div[2]/table/tbody/tr[' + modifyObjectIndex) + 
                ']/td[3]/lib-uclookupgeneric/div/div/div[1]/span/button'

                'modify button referantor lookup'
                modifyObjectButtonReferantor = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/button_LookupReferantor'), 
                    'xpath', 'equals', newButtonReferantorXpath, true)

                String newSelectReferantorCategoryXpath = ('//*[@id="accessoriesData"]/div[2]/table/tbody/tr[' + modifyObjectIndex) + 
                ']/td[2]/select'

                'modify select referantor category'
                modifyObjectSelectReferantorCategory = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/select_ReferantorCategory'), 
                    'xpath', 'equals', newSelectReferantorCategoryXpath, true)

                String newSelectTaxCalculation = ('//*[@id="accessoriesData"]/div[2]/table/tbody/tr[' + modifyObjectIndex) + 
                ']/td[6]/select'

                'modify select tax calculation'
                modifySelectTaxCalcualtion = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/select_TaxCalculationMethod'), 
                    'xpath', 'equals', newSelectTaxCalculation, true)

                String newSelectBankaccount = ('//*[@id="accessoriesData"]/div[2]/table/tbody/tr[' + modifyObjectIndex) + 
                ']/td[5]/select'

                'modify select bank account'
                modifySelectBankAccount = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/select_BankAccount'), 
                    'xpath', 'equals', newSelectBankaccount, true)

                String newButtonDelete = ('//*[@id="accessoriesData"]/div[2]/table/tbody/tr[' + modifyObjectIndex) + ']/td[9]/a/i'

                'modify button delete'
                modifyButtonDelete = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/Button Delete'), 
                    'xpath', 'equals', newButtonDelete, true)

                'select referantor category'
                WebUI.selectOptionByLabel(modifyObjectSelectReferantorCategory, datafilereferantor.getValue(GlobalVariable.NumofReferantor, 
                        13), false, FailureHandling.OPTIONAL)

                'Ambil dan simpan nilai referantor category dari confins'
                String refCategory = WebUI.getAttribute(modifyObjectSelectReferantorCategory, 'value')

                'click button referantor lookup'
                WebUI.click(modifyObjectButtonReferantor, FailureHandling.OPTIONAL)

                if (GlobalVariable.Role == 'Testing') {
                    if (refCategory != '') {
                        'click button search'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/Button_SearchReferantor'))

                        'Cek total data referantor pada db'
                        Integer countReferantor = CustomKeywords.'dbconnection.checkReferantor.countReferantorLookup'(sqlConnection, 
                            refCategory, officeName)

                        'Ambil nilai total data referantor pada lookup confins'
                        String[] textTotalDataReferantor = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/label_TotalDataOfficer')).replace(
                            ' ', '').replace(':', ';').split(';')

                        'Parsing nilai total data referantor confins ke integer(angka)'
                        Integer totalDataReferantor = Integer.parseInt(textTotalDataReferantor[1])

                        'Verif total data referantor confins sesuai dengan db'
                        if (WebUI.verifyEqual(totalDataReferantor, countReferantor) == false) {
                            writeReasonFailedLookup()
                        }
                    }
                }
                
                'input referantor name'
                WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/input_Referantor Name_referantorName'), 
                    datafilereferantor.getValue(GlobalVariable.NumofReferantor, 14))

                'click button search'
                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/Button_SearchReferantor'))

                String referantorCode

                'verify lookup'
                if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/a_Select'), 
                    5, FailureHandling.OPTIONAL)) {
                    referantorCode = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/span_referantorCode'))

                    'click select'
                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/a_Select'))
                } else {
                    'click button x'
                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/button_X'))

                    'write to excel WARNING'
                    CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '5.TabReferantorData', 
                        0, GlobalVariable.NumofReferantor - 1, GlobalVariable.StatusWarning)

                    'Write To Excel GlobalVariable.StatusReasonLookup'
                    CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '5.TabReferantorData', 
                        1, GlobalVariable.NumofReferantor - 1, GlobalVariable.StatusReasonLookup)

                    'Click delete'
                    WebUI.click(modifyButtonDelete, FailureHandling.OPTIONAL)

                    if (WebUI.verifyElementPresent(modifyButtonDelete, 5, FailureHandling.OPTIONAL)) {
                        writeReasonFailedDelete()
                    }
                    
                    continue
                }
                
                if (GlobalVariable.Role == 'Testing') {
                    'Pengecekan terdapat opsi ddl bank account selain select one'
                    if (WebUI.getNumberOfTotalOption(modifySelectBankAccount) > 1) {
                        'Arraylist untuk menampung ddl bank account dari db'
                        ArrayList<String> BankAccount = new ArrayList<String>()

                        'Ambil array teks bank account dari db'
                        BankAccount = CustomKeywords.'dbconnection.checkReferantor.checkBankAccountDDL'(sqlConnection, refCategory, 
                            officeName, referantorCode)

                        'Verifikasi array teks bank account dari db sesuai dengan ddl yang tampil pada confins'
                        if (WebUI.verifyOptionsPresent(modifySelectBankAccount, BankAccount) == false) {
                            writeReasonFailedDDL()
                        }
                        
                        'Pengecekan referantor category yang dipilih customer atau agency'
                        if (refCategory.equalsIgnoreCase('Customer') || refCategory.equalsIgnoreCase('Agency')) {
                            'Ambil teks default bank account dari db'
                            String defaultBankAccount = CustomKeywords.'dbconnection.checkReferantor.checkBankAccountDefault'(
                                sqlConnection, refCategory, officeName, referantorCode)

                            'Verifikasi opsi yang terpilih secara default pada confins sesuai dengan db'
                            if (WebUI.verifyOptionSelectedByLabel(modifySelectBankAccount, '(?i)' + defaultBankAccount, 
                                true, 2) == false) {
                                writeReasonFailedDDL()
                            }
                        }
                    }
                }
                
                'select bank account'
                WebUI.selectOptionByIndex(modifySelectBankAccount, datafilereferantor.getValue(GlobalVariable.NumofReferantor, 
                        16), FailureHandling.OPTIONAL)

                'call function gettext bankaccount'
                getTextBankAccount(newSelectBankaccount)

                'select tax calculation method'
                WebUI.selectOptionByLabel(modifySelectTaxCalcualtion, datafilereferantor.getValue(GlobalVariable.NumofReferantor, 
                        17), false, FailureHandling.OPTIONAL)

                'cek inputan mandatory apakah sudah terisi semua atau belum'
                if ((WebUI.verifyOptionSelectedByIndex(modifyObjectSelectReferantorCategory, 0, 3, FailureHandling.OPTIONAL) || 
                WebUI.verifyOptionSelectedByIndex(modifySelectBankAccount, 0, 3, FailureHandling.OPTIONAL)) || WebUI.verifyOptionSelectedByIndex(
                    modifySelectTaxCalcualtion, 0, 3, FailureHandling.OPTIONAL)) {
                    'write to excel WARNING'
                    CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '5.TabReferantorData', 
                        0, GlobalVariable.NumofReferantor - 1, GlobalVariable.StatusWarning)

                    'Write To Excel GlobalVariable.StatusReasonLookup'
                    CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '5.TabReferantorData', 
                        1, GlobalVariable.NumofReferantor - 1, GlobalVariable.StatusReasonMandatoryEmpty)

                    'Click delete'
                    WebUI.click(modifyButtonDelete, FailureHandling.OPTIONAL)

                    if (WebUI.verifyElementPresent(modifyButtonDelete, 5, FailureHandling.OPTIONAL)) {
                        writeReasonFailedDelete()
                    }
                    
                    continue
                }
                
                //Testing
                if (GlobalVariable.Role == 'Testing') {
                    String newButtonViewDetail = ('//*[@id="accessoriesData"]/div[2]/table/tbody/tr[' + modifyObjectIndex) + 
                    ']/td[8]/a/i'

                    'modify button view detail'
                    modifyButtonViewDetail = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/i_ViewDetail'), 
                        'xpath', 'equals', newButtonViewDetail, true)

                    'click button view detail'
                    WebUI.click(modifyButtonViewDetail)

                    'Pengecekan referantor category (customer, agency, atau mf employee)'
                    if (refCategory.equalsIgnoreCase('CUSTOMER')) {
                        'pengecekan ke db dan simpan data-data detail referantor yang dibutuhkan dari db'
                        ArrayList<String> referantorDetail = CustomKeywords.'dbconnection.checkReferantorDetail.checkCustomerReferantor'(
                            sqlConnection, referantorCode)

                        ArrayList<String> arrayMatch = adddatatoarraylist(referantorDetail)

                        'Jika nilai di confins ada yang tidak sesuai dengan db'
                        if (arrayMatch.contains(false)) {
                            writeToExcelTidakSesuaiDB()

                            modifyObjectIndex++

                            continue
                        }
                    } else if (refCategory.equalsIgnoreCase('AGENCY')) {
                        'Pengecekan ke db dan simpan data-data detail referantor yang dibutuhkan dari db'
                        ArrayList<String> referantorDetail = CustomKeywords.'dbconnection.checkReferantorDetail.checkAgencyReferantor'(
                            sqlConnection, referantorCode)

                        ArrayList<String> arrayMatch = adddatatoarraylist(referantorDetail)

                        'Jika nilai di confins ada yang tidak sesuai dengan db'
                        if (arrayMatch.contains(false)) {
                            writeToExcelTidakSesuaiDB()

                            modifyObjectIndex++

                            continue
                        }
                    } else if (refCategory.equalsIgnoreCase('MULTIFINANCE EMPLOYEE')) {
                        'Pengecekan ke db dan simpan data-data detail referantor yang dibutuhkan dari db'
                        ArrayList<String> referantorDetail = CustomKeywords.'dbconnection.checkReferantorDetail.checkMFEmployeeReferantor'(
                            sqlConnection, referantorCode)

                        ArrayList<String> arrayMatch = adddatatoarraylist(referantorDetail)

                        'Jika nilai di confins ada yang tidak sesuai dengan db'
                        if (arrayMatch.contains(false)) {
                            writeToExcelTidakSesuaiDB()

                            modifyObjectIndex++

                            continue
                        }
                    }
                    
                    'click x'
                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/span_XViewDetail'))
                }
                
                'write to excel SUCCESS'
                CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '5.TabReferantorData', 
                    0, GlobalVariable.NumofReferantor - 1, GlobalVariable.StatusSuccess)

                modifyObjectIndex++
            }
        }
    }
}

'click button save'
WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/Button Save'))

Integer iscompleteMandatory = Integer.parseInt(datafilereferantor.getValue(GlobalVariable.CopyAppColm, 4))

if (iscompleteMandatory == 0 && GlobalVariable.FlagFailed==0) {
    'cek alert'
    GlobalVariable.FlagFailed = CustomKeywords.'checkSaveProcess.checkSaveProcess.checkAlert'(GlobalVariable.CopyAppColm, 
        '5.TabReferantorData')
}

if (WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/ApplicationCurrentStep')), 
    'REFERANTOR', false, FailureHandling.OPTIONAL)) {
    'click button cancel'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/button_Cancel'))
}

if (((GlobalVariable.Role == 'Testing') && (GlobalVariable.CheckVerifStoreDBPersonal == 'Yes')) && (GlobalVariable.FlagFailed == 
0)) {
    'call test case store db referantor data'
    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabReferantorDataStoreDBVerif'), [:], 
    FailureHandling.CONTINUE_ON_FAILURE)
}



def adddatatoarraylist(ArrayList<String> referantorDetail) {
    'Ambil dan simpan npwp no dari confins'
    String textNPWPNo = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/label_NPWPNo'))

    'Ambil dan simpan npwp name dari confins'
    String textNPWPName = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/label_NPWPName'))

    'Ambil dan simpan address dari confins'
    String textAddress = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/label_Address'))

    'ambil dan simpan rt dari confins'
    String textRT = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/label_RT')).replace(
        ' ', '')

    'ambil dan simpan rw dari confins'
    String textRW = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/label_RW')).replace(
        '/ ', '')

    'ambil dan simpan kelurahan dari confins'
    String textKelurahan = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/label_Kelurahan'))

    'ambil dan simpan kecamatan dari confins'
    String textKecamatan = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/label_Kecamatan'))

    'ambil dan simpan kota dari confins'
    String textCity = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/label_Kota'))

    'ambil dan simpan zipcode dari confins'
    String textZipcode = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/label_Zipcode'))

    'arraylist boolean untuk menampung hasil dari verififikasi data dari confins sesuai dengan db atau tidak (true atau false)'
    ArrayList<String> arrayMatch = new ArrayList<String>()

    arrayMatch.add(WebUI.verifyMatch(textNPWPNo, '(?i)' + (referantorDetail[0]), true, FailureHandling.OPTIONAL))

    arrayMatch.add(WebUI.verifyMatch(textNPWPName, '(?i)' + (referantorDetail[1]), true, FailureHandling.OPTIONAL))

    arrayMatch.add(WebUI.verifyMatch(textAddress, '(?i)' + (referantorDetail[2]), true, FailureHandling.OPTIONAL))

    arrayMatch.add(WebUI.verifyMatch(textRT, '(?i)' + (referantorDetail[3]), true, FailureHandling.OPTIONAL))

    arrayMatch.add(WebUI.verifyMatch(textRW, '(?i)' + (referantorDetail[4]), true, FailureHandling.OPTIONAL))

    arrayMatch.add(WebUI.verifyMatch(textKelurahan, '(?i)' + (referantorDetail[5]), true, FailureHandling.OPTIONAL))

    arrayMatch.add(WebUI.verifyMatch(textKecamatan, '(?i)' + (referantorDetail[6]), true, FailureHandling.OPTIONAL))

    arrayMatch.add(WebUI.verifyMatch(textCity, '(?i)' + (referantorDetail[7]), true, FailureHandling.OPTIONAL))

    arrayMatch.add(WebUI.verifyMatch(textZipcode, '(?i)' + (referantorDetail[8]), true, FailureHandling.OPTIONAL))

    return arrayMatch
}

def writeReasonFailedDelete() {
    'Write To Excel GlobalVariable.ReasonFailedDelete'
    CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '5.TabReferantorData', 
        1, GlobalVariable.NumofReferantor - 1, GlobalVariable.ReasonFailedDelete)
}

def writeReasonFailedLookup() {
    'Write To Excel GlobalVariable.StatusFailed'
    CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '5.TabReferantorData', 
        0, GlobalVariable.NumofReferantor - 1, GlobalVariable.StatusFailed)

    'Write To Excel GlobalVariable.ReasonFailedDataLookup'
    CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '5.TabReferantorData', 
        1, GlobalVariable.NumofReferantor, GlobalVariable.ReasonFailedDataLookup)

    GlobalVariable.FlagFailed = 1
}

def writeReasonFailedDDL() {
    'Write To Excel GlobalVariable.StatusFailed'
    CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '5.TabReferantorData', 
        0, GlobalVariable.NumofReferantor - 1, GlobalVariable.StatusFailed)

    'Write To Excel GlobalVariable.ReasonFailedDataLookup'
    CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '5.TabReferantorData', 
        1, GlobalVariable.NumofReferantor, GlobalVariable.ReasonFailedDDL)

    GlobalVariable.FlagFailed = 1
}

def writeToExcelTidakSesuaiDB() {
    'write to excel WARNING'
    CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '5.TabReferantorData', 
        0, GlobalVariable.NumofReferantor - 1, GlobalVariable.StatusWarning)

    'Write To Excel GlobalVariable.StatusReason'
    CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '5.TabReferantorData', 
        1, GlobalVariable.NumofReferantor - 1, GlobalVariable.StatusReasonTidakSesuaiDB)

    'click cancel'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/button_CancelViewDetail'))
}

def getTextBankAccount(String newSelectBankaccount) {
    if (GlobalVariable.BankAccount == '') {
        Select selectBankAcc = new Select(DriverFactory.getWebDriver().findElement(By.xpath(newSelectBankaccount)))

        'Ambil text bank account yang dipilih pada confins'
        GlobalVariable.BankAccount = selectBankAcc.getFirstSelectedOption().getText()
    } else {
        Select selectBankAcc = new Select(DriverFactory.getWebDriver().findElement(By.xpath(newSelectBankaccount)))

        'Ambil text bank account yang dipilih pada confins'
        GlobalVariable.BankAccount = ((GlobalVariable.BankAccount + ' - ') + selectBankAcc.getFirstSelectedOption().getText())
    }
}

