import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import java.math.RoundingMode as RoundingMode
import java.text.NumberFormat as NumberFormat
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
import org.openqa.selenium.By as By
import org.openqa.selenium.Keys as Keys
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import groovy.sql.Sql as Sql

GlobalVariable.SubsidyDPValue = '0'

int flagFailed = 0

'Assign directori file excel ke global variabel'
String userDir = System.getProperty('user.dir')

'Assign directori file excel ke global variabel'
String filePath = userDir + GlobalVariable.PathCompany

'Assign directori file excel ke global variabel'
GlobalVariable.DataFilePath = filePath

ArrayList<WebElement> subsidyfaileddelete = new ArrayList<WebElement>()

if (GlobalVariable.RoleCompany == 'Testing') {
    'verify application step'
    WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/ApplicationCurrentStep')), 
        'FINANCIAL DATA', false, FailureHandling.OPTIONAL)
}

def datafilefinancial = findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData')

def SubsidyTypeArray = datafilefinancial.getValue(GlobalVariable.NumofColm, 12).split(';')

def SubsidyfromValueArray = datafilefinancial.getValue(GlobalVariable.NumofColm, 13).split(';')

def AllocationformArray = datafilefinancial.getValue(GlobalVariable.NumofColm, 14).split(';')

def SubsidySourceArray = datafilefinancial.getValue(GlobalVariable.NumofColm, 15).split(';')

def SubsidyValueTypeArray = datafilefinancial.getValue(GlobalVariable.NumofColm, 16).split(';')

def SubsidyValueAmountArray = datafilefinancial.getValue(GlobalVariable.NumofColm, 17).split(';')

def SubsidyValuePercentageArray = datafilefinancial.getValue(GlobalVariable.NumofColm, 18).split(';')

'Mengambil nilai row keberapa dimulai data additional premi rate pada excel'
def TotalPremium = CustomKeywords.'excelGetRow.getRow.getExcelRow'(GlobalVariable.DataFilePath, '8.TabInsuranceData', 'Total Premium') + 
1

if (datafilefinancial.getValue(GlobalVariable.NumofColm, 50).equalsIgnoreCase('Yes')) {
    for (i = 0; i < AllocationformArray.size(); i++) {
        if ((AllocationformArray[i]).equalsIgnoreCase('Discount Insurance')) {
            (SubsidyValueAmountArray[i]) = findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabInsuranceData').getValue(
                GlobalVariable.NumofColm, TotalPremium + 2)
        }
        
        String overrideSubsidyValueAmountArray = SubsidyValueAmountArray.join(';')

        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '9.TabFinancialData', 
            16, GlobalVariable.NumofColm - 1, overrideSubsidyValueAmountArray)
    }
}

WebDriver driver = DriverFactory.getWebDriver()

ArrayList<WebElement> variable = driver.findElements(By.cssSelector('#FinData_Subsidy > div.table-responsive > table > tbody tr'))

int varsize = variable.size()

'Koneksi database'
String servername = findTestData('Login/Login').getValue(1, 8)

String instancename = findTestData('Login/Login').getValue(2, 8)

String username = findTestData('Login/Login').getValue(3, 8)

String password = findTestData('Login/Login').getValue(4, 8)

String database = findTestData('Login/Login').getValue(5, 9)

String databaseFOU = findTestData('Login/Login').getValue(5, 7)

String driverclassname = findTestData('Login/Login').getValue(6, 8)

String url = (((servername + ';instanceName=') + instancename) + ';databaseName=') + database

String urlFOU = (((servername + ';instanceName=') + instancename) + ';databaseName=') + databaseFOU

Sql sqlConnectionLOS = CustomKeywords.'dbconnection.connectDB.connect'(url, username, password, driverclassname)

Sql sqlConnectionFOU = CustomKeywords.'dbconnection.connectDB.connect'(urlFOU, username, password, driverclassname)

'Ambil appno dari confins'
String appNo = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/span_appNo'))

//Verif default subsidy based on rule, urutan masih statis
if (GlobalVariable.RoleCompany == 'Testing') {
    'Hashmap untuk mengambil arraylist-arraylist nilai result subsidy dari rule subsidy berdasarkan kondisi-kondisi'
    HashMap<String, ArrayList> result = CustomKeywords.'financialData.verifSubsidy.verifySubsidyDefault'(sqlConnectionLOS, 
        sqlConnectionFOU, appNo)

    ArrayList<WebElement> SubsidyFromType

    ArrayList<WebElement> SubsidyFromValue

    ArrayList<WebElement> SubsidyAlloc

    ArrayList<WebElement> SubsidySource

    ArrayList<WebElement> SubsidyValueType

    ArrayList<WebElement> SubsidyValue

    SubsidyFromType = result.get('FT')

    SubsidyFromValue = result.get('FV')

    SubsidyAlloc = result.get('Alloc')

    SubsidySource = result.get('Source')

    SubsidyValueType = result.get('Type')

    SubsidyValue = result.get('Value')

    'Jika pada confins tidak ada data'
    if (WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/tablesubsidynodata')), 
        'NO DATA AVAILABLE', false, FailureHandling.OPTIONAL)) {
        varsize = 0
    }
    
    'Verif jumlah data subsidy yang muncul pada confins sesuai dengan rule'
    WebUI.verifyEqual(varsize, SubsidyFromType.size())

    'Pengecekan jika jumlah data pada confins lebih dari 0 dan jumlah data subsidy pada confins sesuai dengan rule'
    if ((varsize == SubsidyFromType.size()) && (varsize > 0)) {
        'Looping data subsidi pada confins'
        for (i = 1; i <= varsize; i++) {
            'modify object from type name'
            modifyNewFromTypeName = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/FromTypeName'), 
                'xpath', 'equals', ('//*[@id="FinData_Subsidy"]/div[2]/table/tbody/tr[' + i) + ']/td[1]', true)

            'modify object from value name'
            modifyNewFromValueName = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/FromValueName'), 
                'xpath', 'equals', ('//*[@id="FinData_Subsidy"]/div[2]/table/tbody/tr[' + i) + ']/td[2]', true)

            'modify object subsidy allocation'
            modifyNewSubsidyAllocation = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/SubsidyAllocation'), 
                'xpath', 'equals', ('//*[@id="FinData_Subsidy"]/div[2]/table/tbody/tr[' + i) + ']/td[3]', true)

            'modify object subsidy value type'
            modifyNewSubsidyValueType = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/td_SubsidyValueType'), 
                'xpath', 'equals', ('//*[@id=\'FinData_Subsidy\']/div[2]/table/tbody/tr[' + i) + ']/td[4]', true)

            'modify object subsidy source'
            modifyNewSubsidySource = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/SubsidySource'), 
                'xpath', 'equals', ('//*[@id="FinData_Subsidy"]/div[2]/table/tbody/tr[' + i) + ']/td[5]', true)

            'modify object subsidy percentage'
            modifyNewSubsidyPercentage = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/td_SubsidyPercentage'), 
                'xpath', 'equals', ('//*[@id=\'FinData_Subsidy\']/div[2]/table/tbody/tr[' + i) + ']/td[6]', true)

            'modify object subsidy amount'
            modifyNewSubsidyAmount = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/td_SubsidyAmount'), 
                'xpath', 'equals', ('//*[@id=\'FinData_Subsidy\']/div[2]/table/tbody/tr[' + i) + ']/td[7]', true)

            'Verif subsidy from type sesuai rule'
            WebUI.verifyMatch(CustomKeywords.'financialData.verifSubsidy.checkSubsidyFromTypeCode'(sqlConnectionLOS, WebUI.getText(
                        modifyNewFromTypeName)), SubsidyFromType.get(i - 1), false)

            'Verif subsidy from value sesuai rule'
            WebUI.verifyMatch(WebUI.getText(modifyNewFromValueName), SubsidyFromValue.get(i - 1), false)

            'Verif subsidy allocation sesuai rule'
            WebUI.verifyMatch(CustomKeywords.'financialData.verifSubsidy.checkSubsidyAllocCode'(sqlConnectionLOS, WebUI.getText(
                        modifyNewSubsidyAllocation)), SubsidyAlloc.get(i - 1), false)

            'Verif subsidy value type sesuai rule'
            WebUI.verifyMatch(CustomKeywords.'financialData.verifSubsidy.checkSubsidyValueTypeCode'(sqlConnectionLOS, WebUI.getText(
                        modifyNewSubsidyValueType)), SubsidyValueType.get(i - 1), false)

            'Verif subsidy source sesuai rule'
            WebUI.verifyMatch(CustomKeywords.'financialData.verifSubsidy.checkSubsidySourceCode'(sqlConnectionLOS, WebUI.getText(
                        modifyNewSubsidySource)), SubsidySource.get(i - 1), false)

            'Pengecekan value type pada confins bernilai percentage'
            if (WebUI.getText(modifyNewSubsidyValueType).equalsIgnoreCase('Percentage')) {
                'Verif subsidy percentage sesuai rule'
                WebUI.verifyEqual(Double.parseDouble(WebUI.getText(modifyNewSubsidyPercentage).replace(' %', '')), Double.parseDouble(
                        SubsidyValue.get(i - 1))) //Pengecekan value type pada confins bernilai amount
            } else if (WebUI.getText(modifyNewSubsidyValueType).equalsIgnoreCase('Amount')) {
                'Verif subsidy amount sesuai rule'
                WebUI.verifyEqual(Double.parseDouble(WebUI.getText(modifyNewSubsidyAmount).replace(',', '')), Double.parseDouble(
                        SubsidyValue.get(i - 1)))
            }
        }
    }
}

if (WebUI.verifyNotMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/tablesubsidynodata')), 
    'NO DATA AVAILABLE', false, FailureHandling.OPTIONAL)) {
    for (i = 1; i <= variable.size(); i++) {
        'modify object from type name'
        modifyNewFromTypeName = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/FromTypeName'), 
            'xpath', 'equals', ('//*[@id="FinData_Subsidy"]/div[2]/table/tbody/tr[' + i) + ']/td[1]', true)

        'modify object from value name'
        modifyNewFromValueName = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/FromValueName'), 
            'xpath', 'equals', ('//*[@id="FinData_Subsidy"]/div[2]/table/tbody/tr[' + i) + ']/td[2]', true)

        'modify object subsidy allocation'
        modifyNewSubsidyAllocation = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/SubsidyAllocation'), 
            'xpath', 'equals', ('//*[@id="FinData_Subsidy"]/div[2]/table/tbody/tr[' + i) + ']/td[3]', true)

        'modify object subsidy source'
        modifyNewSubsidySource = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/SubsidySource'), 
            'xpath', 'equals', ('//*[@id="FinData_Subsidy"]/div[2]/table/tbody/tr[' + i) + ']/td[5]', true)

        'modify object button edit'
        modifyNewButtonEdit = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/Button_Edit'), 
            'xpath', 'equals', ('//*[@id="FinData_Subsidy"]/div[2]/table/tbody/tr[' + i) + ']/td[8]/a[1]/i', true)

        'modify object button Delete'
        modifyNewButtonDelete = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/Button_Delete'), 
            'xpath', 'equals', ('//*[@id="FinData_Subsidy"]/div[2]/table/tbody/tr[' + i) + ']/td[8]/a[2]/i', true)

        for (subsidyarray = 1; subsidyarray <= SubsidyTypeArray.size(); subsidyarray++) {
            if (((WebUI.getText(modifyNewFromTypeName).equalsIgnoreCase(SubsidyTypeArray[(subsidyarray - 1)]) && WebUI.getText(
                modifyNewFromValueName).equalsIgnoreCase(SubsidyfromValueArray[(subsidyarray - 1)])) && WebUI.getText(modifyNewSubsidyAllocation).equalsIgnoreCase(
                AllocationformArray[(subsidyarray - 1)])) && WebUI.getText(modifyNewSubsidySource).equalsIgnoreCase(SubsidySourceArray[
                (subsidyarray - 1)])) {
                WebUI.click(modifyNewButtonEdit, FailureHandling.OPTIONAL)

                'select multifinance / supplier'
                WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/select_--Select--MultifinanceSupplier'), 
                    SubsidyTypeArray[(subsidyarray - 1)], false)

                if ((SubsidyTypeArray[(subsidyarray - 1)]).equalsIgnoreCase('Supplier') || (SubsidyTypeArray[(subsidyarray - 
                1)]).equalsIgnoreCase('Multifinance')) {
                    int i = 2

                    if ((SubsidyTypeArray[(subsidyarray - 1)]).equalsIgnoreCase('Supplier')) {
                        subsidyfromvalue = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/select_AllocationFromSupplier'), 
                            'xpath', 'equals', ('/html/body/ngb-modal-window/div/div/app-subsidy-add-edit/form/div[2]/div/div[' + 
                            i++) + ']/div/select', true)

                        'select subsidy from value'
                        WebUI.selectOptionByLabel(subsidyfromvalue, SubsidyfromValueArray[(subsidyarray - 1)], false)
                    }
                    
                    subsidyallocationfrom = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/select_AllocationFromSupplier'), 
                        'xpath', 'equals', ('/html/body/ngb-modal-window/div/div/app-subsidy-add-edit/form/div[2]/div/div[' + 
                        i++) + ']/div/select', true)

                    subsidyvaluesource = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/select_SubsidySourceSupplier'), 
                        'xpath', 'equals', ('/html/body/ngb-modal-window/div/div/app-subsidy-add-edit/form/div[2]/div/div[' + 
                        i++) + ']/div/select', true)

                    subsidyvaluetype = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/Select_SubsidyValueTypeSupplier'), 
                        'xpath', 'equals', ('/html/body/ngb-modal-window/div/div/app-subsidy-add-edit/form/div[2]/div/div[' + 
                        i++) + ']/div/select', true)

                    subsidyvalue = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_SubsidyValueSupplier'), 
                        'xpath', 'equals', ('/html/body/ngb-modal-window/div/div/app-subsidy-add-edit/form/div[2]/div/div[' + 
                        i++) + ']/div/input', true)

                    'select subsidy allocation from '
                    WebUI.selectOptionByLabel(subsidyallocationfrom, AllocationformArray[(subsidyarray - 1)], false)

                    'select subsidy source'
                    WebUI.selectOptionByLabel(subsidyvaluesource, SubsidySourceArray[(subsidyarray - 1)], false)

                    'select subsidy value type'
                    WebUI.selectOptionByLabel(subsidyvaluetype, SubsidyValueTypeArray[(subsidyarray - 1)], false)

                    if ((SubsidyValueTypeArray[(subsidyarray - 1)]).equalsIgnoreCase('Amount')) {
                        'input subsidy value amount'
                        WebUI.setText(subsidyvalue, SubsidyValueAmountArray[(subsidyarray - 1)])
                    } else if ((SubsidyValueTypeArray[(subsidyarray - 1)]).equalsIgnoreCase('Percentage')) {
                        'input subsidy value percentage'
                        WebUI.setText(subsidyvalue, SubsidyValuePercentageArray[(subsidyarray - 1)])
                    }
                }
                
                'click button save subsidy'
                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/button_SaveSubsidy'))

                'Verify gagal add'
                if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/select_--Select--MultifinanceSupplier'), 
                    10, FailureHandling.OPTIONAL)) {
                    'Click cancel'
                    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/button_CancelSubsidy'))
                }
                
                break
            } else {
                if (subsidyarray == SubsidyTypeArray.size()) {
                    if (WebUI.verifyNotMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/tablesubsidynodata')), 
                        'NO DATA AVAILABLE', false, FailureHandling.OPTIONAL)) {
                        'get subsidy allocation'
                        subsidyallocationnamebefore = WebUI.getText(modifyNewSubsidyAllocation)

                        'click button delete'
                        WebUI.click(modifyNewButtonDelete, FailureHandling.OPTIONAL)

                        WebUI.acceptAlert(FailureHandling.OPTIONAL)

                        if (i == variable.size()) {
                            if (WebUI.verifyElementNotPresent(modifyNewSubsidyAllocation, 5, FailureHandling.OPTIONAL)) {
                                'count ulang table pada confins'
                                variable = driver.findElements(By.cssSelector('#FinData_Subsidy > div.table-responsive > table > tbody tr'))
                            } else {
                                'add cust name failed kedalam array'
                                subsidyfaileddelete.add(subsidyallocationnamebefore)
                            }
                        } else {
                            'get cust name sebelum delete'
                            subsidyallocationnameAfter = WebUI.getText(modifyNewSubsidyAllocation)

                            if (WebUI.verifyNotMatch(subsidyallocationnameAfter, subsidyallocationnamebefore, false, FailureHandling.OPTIONAL)) {
                                'count ulang table pada confins'
                                variable = driver.findElements(By.cssSelector('#FinData_Subsidy > div.table-responsive > table > tbody tr'))
                            } else {
                                'add cust name failed kedalam array'
                                subsidyfaileddelete.add(subsidyallocationnamebefore)

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

if (subsidyfaileddelete.size() > 0) {
    CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '9.TabFinancialData', 0, 
        GlobalVariable.CopyAppColm - 1, GlobalVariable.StatusWarning)

    CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '9.TabFinancialData', 1, 
        GlobalVariable.CopyAppColm - 1, GlobalVariable.ReasonFailedDelete + subsidyfaileddelete)

    (GlobalVariable.FlagWarning)++
}

ArrayList<WebElement> variableData = DriverFactory.getWebDriver().findElements(By.cssSelector('#FinData_Subsidy > div.table-responsive > table > tbody tr'))

int countData = variableData.size()

for (s = 1; s <= SubsidyTypeArray.size(); s++) {
    for (SubsidyCheck = 1; SubsidyCheck <= countData; SubsidyCheck++) {
        if (WebUI.verifyNotMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/tablesubsidynodata')), 
            'NO DATA AVAILABLE', false, FailureHandling.OPTIONAL)) {
            'modify object from type name'
            modifyNewFromTypeName = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/FromTypeName'), 
                'xpath', 'equals', ('//*[@id="FinData_Subsidy"]/div[2]/table/tbody/tr[' + SubsidyCheck) + ']/td[1]', true)

            'modify object from value name'
            modifyNewFromValueName = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/FromValueName'), 
                'xpath', 'equals', ('//*[@id="FinData_Subsidy"]/div[2]/table/tbody/tr[' + SubsidyCheck) + ']/td[2]', true)

            'modify object subsidy allocation'
            modifyNewSubsidyAllocation = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/SubsidyAllocation'), 
                'xpath', 'equals', ('//*[@id="FinData_Subsidy"]/div[2]/table/tbody/tr[' + SubsidyCheck) + ']/td[3]', true)

            'modify object subsidy source'
            modifyNewSubsidySource = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/SubsidySource'), 
                'xpath', 'equals', ('//*[@id="FinData_Subsidy"]/div[2]/table/tbody/tr[' + SubsidyCheck) + ']/td[5]', true)

            if (((!((SubsidyTypeArray[(s - 1)]).equalsIgnoreCase(WebUI.getText(modifyNewFromTypeName))) || !((SubsidyfromValueArray[
            (s - 1)]).equalsIgnoreCase(WebUI.getText(modifyNewFromValueName)))) || !((AllocationformArray[(s - 1)]).equalsIgnoreCase(
                WebUI.getText(modifyNewSubsidyAllocation)))) || !((SubsidySourceArray[(s - 1)]).equalsIgnoreCase(WebUI.getText(
                    modifyNewSubsidySource)))) {
                if (countData == SubsidyCheck) {
                    'click button add subsidy'
                    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/button_Add Subsidy'))

                    'select multifinance / supplier'
                    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/select_--Select--MultifinanceSupplier'), 
                        '(?i)' + (SubsidyTypeArray[(s - 1)]), true)

                    if ((SubsidyTypeArray[(s - 1)]).equalsIgnoreCase('Supplier')) {
                        'select subsidy from value'
                        WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/select_SubsidyFromValueSupplier'), 
                            '(?i)' + (SubsidyfromValueArray[(s - 1)]), true)

                        'select subsidy allocation from '
                        WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/select_AllocationFromSupplier'), 
                            '(?i)' + (AllocationformArray[(s - 1)]), true)

                        'select subsidy source'
                        WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/select_SubsidySourceSupplier'), 
                            '(?i)' + (SubsidySourceArray[(s - 1)]), true)

                        'select subsidy value type'
                        WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/Select_SubsidyValueTypeSupplier'), 
                            '(?i)' + (SubsidyValueTypeArray[(s - 1)]), true)

                        if ((SubsidyValueTypeArray[(s - 1)]).equalsIgnoreCase('Amount')) {
                            'input subsidy value amount'
                            WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_SubsidyValueSupplier'), 
                                SubsidyValueAmountArray[(s - 1)])
                        } else if ((SubsidyValueTypeArray[(s - 1)]).equalsIgnoreCase('Percentage')) {
                            'input subsidy value percentage'
                            WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_SubsidyValueSupplier'), 
                                SubsidyValuePercentageArray[(s - 1)])
                        }
                    } else if ((SubsidyTypeArray[(s - 1)]).equalsIgnoreCase('Multifinance')) {
                        'select subsidy allocation from'
                        WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/select_AllocationFromMultifinance'), 
                            '(?i)' + (AllocationformArray[(s - 1)]), true)

                        'select subsidy source'
                        WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/select_SubsidySourceMultifinance'), 
                            '(?i)' + (SubsidySourceArray[(s - 1)]), true)

                        'select subsidy value type'
                        WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/select_SubsidyValueTypeMultifinance'), 
                            '(?i)' + (SubsidyValueTypeArray[(s - 1)]), true)

                        if ((SubsidyValueTypeArray[(s - 1)]).equalsIgnoreCase('Amount')) {
                            'input subsidy value amount'
                            WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_SubsidyValueAmountMultifinance'), 
                                SubsidyValueAmountArray[(s - 1)])
                        } else if ((SubsidyValueTypeArray[(s - 1)]).equalsIgnoreCase('Percentage')) {
                            'input subsidy value percentage'
                            WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_SubsidyValueAmountMultifinance'), 
                                SubsidyValuePercentageArray[(s - 1)])
                        }
                    }
                    
                    'click button save subsidy'
                    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/button_SaveSubsidy'))

                    'Verify gagal add'
                    if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/select_--Select--MultifinanceSupplier'), 
                        10, FailureHandling.OPTIONAL)) {
                        'Click cancel'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/button_CancelSubsidy'))
                    }
                }
            } else {
                break
            }
        } else if (WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/tablesubsidynodata')), 
            'NO DATA AVAILABLE', false, FailureHandling.OPTIONAL)) {
            if (datafilefinancial.getValue(GlobalVariable.NumofColm, 12) != '') {
                if (SubsidyTypeArray.size() > 0) {
                    'click button add subsidy'
                    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/button_Add Subsidy'))

                    'select multifinance / supplier'
                    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/select_--Select--MultifinanceSupplier'), 
                        '(?i)' + (SubsidyTypeArray[(s - 1)]), true)

                    if ((SubsidyTypeArray[(s - 1)]).equalsIgnoreCase('Supplier')) {
                        'select subsidy from value'
                        WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/select_SubsidyFromValueSupplier'), 
                            '(?i)' + (SubsidyfromValueArray[(s - 1)]), true)

                        'select subsidy allocation from '
                        WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/select_AllocationFromSupplier'), 
                            '(?i)' + (AllocationformArray[(s - 1)]), true)

                        'select subsidy source'
                        WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/select_SubsidySourceSupplier'), 
                            '(?i)' + (SubsidySourceArray[(s - 1)]), true)

                        'select subsidy value type'
                        WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/Select_SubsidyValueTypeSupplier'), 
                            '(?i)' + (SubsidyValueTypeArray[(s - 1)]), true)

                        if ((SubsidyValueTypeArray[(s - 1)]).equalsIgnoreCase('Amount')) {
                            'input subsidy value amount'
                            WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_SubsidyValueSupplier'), 
                                '(?i)' + (SubsidyValueAmountArray[(s - 1)]))
                        } else if ((SubsidyValueTypeArray[(s - 1)]).equalsIgnoreCase('Percentage')) {
                            'input subsidy value percentage'
                            WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_SubsidyValueSupplier'), 
                                '(?i)' + (SubsidyValuePercentageArray[(s - 1)]))
                        }
                    } else if ((SubsidyTypeArray[(s - 1)]).equalsIgnoreCase('Multifinance')) {
                        'select subsidy allocation from'
                        WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/select_AllocationFromMultifinance'), 
                            '(?i)' + (AllocationformArray[(s - 1)]), true)

                        'select subsidy source'
                        WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/select_SubsidySourceMultifinance'), 
                            '(?i)' + (SubsidySourceArray[(s - 1)]), true)

                        'select subsidy value type'
                        WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/select_SubsidyValueTypeMultifinance'), 
                            '(?i)' + (SubsidyValueTypeArray[(s - 1)]), true)

                        if ((SubsidyValueTypeArray[(s - 1)]).equalsIgnoreCase('Amount')) {
                            'input subsidy value amount'
                            WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_SubsidyValueAmountMultifinance'), 
                                SubsidyValueAmountArray[(s - 1)])
                        } else if ((SubsidyValueTypeArray[(s - 1)]).equalsIgnoreCase('Percentage')) {
                            'input subsidy value percentage'
                            WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_SubsidyValueAmountMultifinance'), 
                                SubsidyValuePercentageArray[(s - 1)])
                        }
                    }
                    
                    'click button save subsidy'
                    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/button_SaveSubsidy'))

                    'Verify gagal add'
                    if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/select_--Select--MultifinanceSupplier'), 
                        10, FailureHandling.OPTIONAL)) {
                        'Click cancel'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/button_CancelSubsidy'))
                    }
                }
            }
        }
    }
}

for (int SubsidyCheck = 1; SubsidyCheck <= variableData.size(); SubsidyCheck++) {
    if (WebUI.verifyNotMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/tablesubsidynodata')), 
        'NO DATA AVAILABLE', false, FailureHandling.OPTIONAL)) {
        String NewSubsidyAllocation = ('//*[@id="FinData_Subsidy"]/div[2]/table/tbody/tr[' + SubsidyCheck) + ']/td[3]'

        'modify object subsidy allocation'
        modifyNewSubsidyAllocation = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/SubsidyAllocation'), 
            'xpath', 'equals', NewSubsidyAllocation, true)

        String NewSubsidyAmount = ('//*[@id="FinData_Subsidy"]/div[2]/table/tbody/tr[' + SubsidyCheck) + ']/td[7]'

        'modify object SubsidyAmount'
        modifyNewSubsidyAmount = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/SubsidyAmount'), 
            'xpath', 'equals', NewSubsidyAmount, true)

        if (WebUI.getText(modifyNewSubsidyAllocation, FailureHandling.OPTIONAL).equalsIgnoreCase('SUBSIDY DP')) {
            GlobalVariable.SubsidyDPValue = WebUI.getText(modifyNewSubsidyAmount, FailureHandling.CONTINUE_ON_FAILURE).replace(
                ',', '').replace('.00', '')
        }
    }
}

//Verif fee based on rule
if (GlobalVariable.RoleCompany == 'Testing') {
    'Ambil nilai result dari rule credit fee'
    HashMap<String, ArrayList> result = CustomKeywords.'financialData.verifFee.verifyFinancialFee'(sqlConnectionLOS, appNo)

    ArrayList<WebElement> listFee

    ArrayList<WebElement> feeType

    ArrayList<WebElement> fee

    ArrayList<WebElement> feeBhv

    ArrayList<WebElement> feecapType

    ArrayList<WebElement> feecap

    listFee = result.get('listFee')

    feeType = result.get('feeType')

    fee = result.get('fee')

    feeBhv = result.get('feeBhv')

    feecapType = result.get('feecapType')

    feecap = result.get('feecap')

    Integer counter = 0

    //Looping listfee dari result rule
    for (int i = counter; i < listFee.size(); i++) {
        'Pengecekan jika list fee pada rule sesuai dengan fee pada confins'
        if (CustomKeywords.'financialData.verifFee.checkFeeCode'(sqlConnectionLOS, WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/label_Fee'))) == 
        listFee.get(i)) {
            'Verify amount admin fee pada confins sesuai rule'
            WebUI.verifyMatch(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Admin Fee'), 
                    'value').replace(',', ''), fee.get(i), false)

            if (feeBhv.get(i) == 'DEF') {
                'Verify admin fee tidak terlock'
                WebUI.verifyElementNotHasAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Admin Fee'), 
                    'readonly', 1)
            } else if (feeBhv.get(i) == 'LOCK') {
                'verify admin fee terlock'
                WebUI.verifyElementHasAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Admin Fee'), 
                    'readonly', 1)
            }
            
            if (feecapType.get(i) == 'AMT') {
                'Verify admin fee capitalized amount sesuai rule'
                WebUI.verifyMatch(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Admin Fee Capitalize_'), 
                        'value').replace(',', ''), feecap.get(i), false, FailureHandling.OPTIONAL)
            }
        }
        
        'Pengecekan jika list fee pada rule sesuai dengan fee pada confins'
        if (CustomKeywords.'financialData.verifFee.checkFeeCode'(sqlConnectionLOS, WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/label_Fee2'))) == 
        listFee.get(i)) {
            'Verify amount additional admin pada confins sesuai rule'
            WebUI.verifyMatch(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Additional Admin'), 
                    'value').replace(',', ''), fee.get(i), false)

            if (feeBhv.get(i) == 'DEF') {
                'verify additional admin tidak terlock'
                WebUI.verifyElementNotHasAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Additional Admin'), 
                    'readonly', 1)
            } else if (feeBhv.get(i) == 'LOCK') {
                'verify additional admin terlock'
                WebUI.verifyElementHasAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Additional Admin'), 
                    'readonly', 1)
            }
            
            if (feecapType.get(i) == 'AMT') {
                'Verify additional admin capitalized amount sesuai rule'
                WebUI.verifyMatch(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Additional Admin Capitalize'), 
                        'value').replace(',', ''), feecap.get(i), false, FailureHandling.OPTIONAL)
            }
        }
        
        'Pengecekan jika list fee pada rule sesuai dengan fee pada confins'
        if (CustomKeywords.'financialData.verifFee.checkFeeCode'(sqlConnectionLOS, WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/label_Fee3'))) == 
        listFee.get(i)) {
            'Verify notary fee pada confins sesuai rule'
            WebUI.verifyMatch(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Notary Fee'), 
                    'value').replace(',', ''), fee.get(i), false)

            if (feeBhv.get(i) == 'DEF') {
                'Verify notary fee tidak terlock'
                WebUI.verifyElementNotHasAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Notary Fee'), 
                    'readonly', 1)
            } else if (feeBhv.get(i) == 'LOCK') {
                'Verify notary fee terlock'
                WebUI.verifyElementHasAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Notary Fee'), 
                    'readonly', 1)
            }
            
            if (feecapType.get(i) == 'AMT') {
                'Verify notary fee capitalized amount sesuai rule'
                WebUI.verifyMatch(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Notary Fee Capitalize'), 
                        'value').replace(',', ''), feecap.get(i), false, FailureHandling.OPTIONAL)
            }
        }
        
        'Pengecekan jika list fee pada rule sesuai dengan fee pada confins'
        if (CustomKeywords.'financialData.verifFee.checkFeeCode'(sqlConnectionLOS, WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/label_Fee4'))) == 
        listFee.get(i)) {
            'Verify other fee pada confins sesuai rule'
            WebUI.verifyMatch(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Other Fee'), 
                    'value').replace(',', ''), fee.get(i), false)

            if (feeBhv.get(i) == 'DEF') {
                'verify other fee tidak terlock'
                WebUI.verifyElementNotHasAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Other Fee'), 
                    'readonly', 1)
            } else if (feeBhv.get(i) == 'LOCK') {
                'verify other fee terlock'
                WebUI.verifyElementHasAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Other Fee'), 
                    'readonly', 1)
            }
            
            if (feecapType.get(i) == 'AMT') {
                'Verify other fee capitalized amount sesuai rule'
                WebUI.verifyMatch(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Other Fee Capitalize'), 
                        'value').replace(',', ''), feecap.get(i), false, FailureHandling.OPTIONAL)
            }
        }
        
        'Pengecekan jika list fee pada rule sesuai dengan fee pada confins'
        if (CustomKeywords.'financialData.verifFee.checkFeeCode'(sqlConnectionLOS, WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/label_Fee5'))) == 
        listFee.get(i)) {
            'verify fiducia fee pada confins sesuai rule'
            WebUI.verifyMatch(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Fiducia Fee'), 
                    'value').replace(',', ''), fee.get(i), false)

            if (feeBhv.get(i) == 'DEF') {
                'Verify fiducia fee tidak terlock'
                WebUI.verifyElementNotHasAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Fiducia Fee'), 
                    'readonly', 1)
            } else if (feeBhv.get(i) == 'LOCK') {
                'Verify fiducia fee terlock'
                WebUI.verifyElementHasAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Fiducia Fee'), 
                    'readonly', 1)
            }
            
            if (feecapType.get(i) == 'AMT') {
                'Verify fiducia fee capitalized amount sesuai rule'
                WebUI.verifyMatch(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Fiducia Fee Capitalize'), 
                        'value').replace(',', ''), feecap.get(i), false, FailureHandling.OPTIONAL)
            }
        }
        
        'Jika list fee merupakan provision'
        if (listFee.get(i) == 'PROVISION') {
            'Pengecekan fee type amount/percentage'
            if (feeType.get(i) == 'AMT') {
                'Verify provision fee pada confins sesuai rule'
                WebUI.verifyMatch(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Provision Fee Amount'), 
                        'value').replace(',', ''), fee.get(i), false)

                if (feeBhv.get(i) == 'DEF') {
                    'Verify provision fee amount tidak terlock'
                    WebUI.verifyElementNotHasAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Provision Fee Amount'), 
                        'readonly', 1) //Verify provision fee amt & pctg terlock
                } else if (feeBhv.get(i) == 'LOCK') {
                    WebUI.verifyElementHasAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Provision Fee Amount'), 
                        'readonly', 1)

                    WebUI.verifyElementHasAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Provision Fee Percentage'), 
                        'readonly', 1)
                }
                //Verify provision fee amt & pctg terlock
            } else if (feeType.get(i) == 'PRCNT') {
                'Verify provision fee pada confins sesuai rule'
                WebUI.verifyEqual(Double.parseDouble(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Provision Fee Percentage'), 
                            'value').replace(' %', '')), Double.parseDouble(fee.get(i)))

                if (feeBhv.get(i) == 'DEF') {
                    'verify provision fee percentage tidak terlock'
                    WebUI.verifyElementNotHasAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Provision Fee Percentage'), 
                        'readonly', 1)
                } else if (feeBhv.get(i) == 'LOCK') {
                    WebUI.verifyElementHasAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Provision Fee Amount'), 
                        'readonly', 1)

                    WebUI.verifyElementHasAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Provision Fee Percentage'), 
                        'readonly', 1)
                }
            }
            
            if (feecapType.get(i) == 'AMT') {
                'Verify provision fee capitalized amount'
                WebUI.verifyMatch(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Provision Fee Capitalize'), 
                        'value').replace(',', ''), feecap.get(i), false, FailureHandling.OPTIONAL)
            }
        }
    }
}

if (datafilefinancial.getValue(GlobalVariable.NumofColm, 20) == 'No') {
    'input admin fee'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Admin Fee'), datafilefinancial.getValue(
            GlobalVariable.NumofColm, 21))

    if (WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Admin Fee Capitalize_checkbox'), 
        2, FailureHandling.OPTIONAL)) {
        if (datafilefinancial.getValue(GlobalVariable.NumofColm, 26) == 'Yes') {
            'click admin fee capitalized checkbox'
            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Admin Fee Capitalize_checkbox'))
        }
    } else {
        if (datafilefinancial.getValue(GlobalVariable.NumofColm, 26) == 'No') {
            'click admin fee capitalized checkbox'
            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Admin Fee Capitalize_checkbox'))
        }
    }
    
    if (datafilefinancial.getValue(GlobalVariable.NumofColm, 26) == 'Yes') {
        'input admin fee capitalized '
        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Admin Fee Capitalize_'), 
            datafilefinancial.getValue(GlobalVariable.NumofColm, 27))
    }
    
    'input additional admin'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Additional Admin'), 
        datafilefinancial.getValue(GlobalVariable.NumofColm, 22))

    if (WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Additional Admin Capitalize_checkbox'), 
        2, FailureHandling.OPTIONAL)) {
        if (datafilefinancial.getValue(GlobalVariable.NumofColm, 28) == 'Yes') {
            'click additional admin capitalized checkbox'
            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Additional Admin Capitalize_checkbox'))
        }
    } else {
        if (datafilefinancial.getValue(GlobalVariable.NumofColm, 28) == 'No') {
            'click additional admin capitalized checkbox'
            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Additional Admin Capitalize_checkbox'))
        }
    }
    
    if (datafilefinancial.getValue(GlobalVariable.NumofColm, 28) == 'Yes') {
        'input additional admin capitalized'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Additional Admin Capitalize'), 
            datafilefinancial.getValue(GlobalVariable.NumofColm, 29))
    }
    
    'input notary fee'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Notary Fee'), datafilefinancial.getValue(
            GlobalVariable.NumofColm, 23))

    if (WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Notary Fee Capitalize_checkbox'), 
        2, FailureHandling.OPTIONAL)) {
        if (datafilefinancial.getValue(GlobalVariable.NumofColm, 30) == 'Yes') {
            'click notary fee capitalized checkbox'
            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Notary Fee Capitalize_checkbox'))
        }
    } else {
        if (datafilefinancial.getValue(GlobalVariable.NumofColm, 30) == 'No') {
            'click notary fee capitalized checkbox'
            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Notary Fee Capitalize_checkbox'))
        }
    }
    
    if (datafilefinancial.getValue(GlobalVariable.NumofColm, 30) == 'Yes') {
        'input lick notary fee capitalized '
        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Notary Fee Capitalize'), 
            datafilefinancial.getValue(GlobalVariable.NumofColm, 31))
    }
    
    'input other fee'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Other Fee'), datafilefinancial.getValue(
            GlobalVariable.NumofColm, 24))

    if (WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Other Fee Capitalize_checkbox'), 
        2, FailureHandling.OPTIONAL)) {
        if (datafilefinancial.getValue(GlobalVariable.NumofColm, 32) == 'Yes') {
            'click other fee capitalized checkbox'
            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Other Fee Capitalize_checkbox'))
        }
    } else {
        if (datafilefinancial.getValue(GlobalVariable.NumofColm, 32) == 'No') {
            'click other fee capitalized checkbox'
            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Other Fee Capitalize_checkbox'))
        }
    }
    
    if (datafilefinancial.getValue(GlobalVariable.NumofColm, 32) == 'Yes') {
        'input other fee capitalized'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Other Fee Capitalize'), 
            datafilefinancial.getValue(GlobalVariable.NumofColm, 33))
    }
    
    'input fiducia fee'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Fiducia Fee'), datafilefinancial.getValue(
            GlobalVariable.NumofColm, 25))

    if (WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Fiducia Fee Capitalize_checkbox'), 
        2, FailureHandling.OPTIONAL)) {
        if (datafilefinancial.getValue(GlobalVariable.NumofColm, 34) == 'Yes') {
            'click fiducia fee capitalized checkbox'
            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Fiducia Fee Capitalize_checkbox'))
        }
    } else {
        if (datafilefinancial.getValue(GlobalVariable.NumofColm, 34) == 'No') {
            'click fiducia fee capitalized checkbox'
            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Fiducia Fee Capitalize_checkbox'))
        }
    }
    
    if (datafilefinancial.getValue(GlobalVariable.NumofColm, 34) == 'Yes') {
        'input fiducia fee capitalized'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Fiducia Fee Capitalize'), 
            datafilefinancial.getValue(GlobalVariable.NumofColm, 35))
    }
    
    if (datafilefinancial.getValue(GlobalVariable.NumofColm, 36) == 'Amount') {
        'click amount radio button'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/div_AmountRadio'))

        'click amount radio button'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/div_AmountRadio'))
    } else if (datafilefinancial.getValue(GlobalVariable.NumofColm, 36) == 'Percentage') {
        'click percentage radio button'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/div_PercentageRadio'))

        'click percentage radio button'
        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/div_PercentageRadio'))
    }
    
    'select Provision fee calculation base'
    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/select_OTR-DPOTR-DP  Ins Cptlz  Fee Cptlz(Excl Provision)'), 
        datafilefinancial.getValue(GlobalVariable.NumofColm, 37), false)

    if (WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Provision Fee Capitalize_checkbox'), 
        2, FailureHandling.OPTIONAL)) {
        if (datafilefinancial.getValue(GlobalVariable.NumofColm, 40) == 'Yes') {
            'click provision fee capitalized checkbox'
            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Provision Fee Capitalize_checkbox'))
        }
    } else {
        if (datafilefinancial.getValue(GlobalVariable.NumofColm, 40) == 'No') {
            'click provision fee capitalized checkbox'
            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Provision Fee Capitalize_checkbox'))
        }
    }
    
    if (datafilefinancial.getValue(GlobalVariable.NumofColm, 40) == 'Yes') {
        'input provision fee capitalized '
        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Provision Fee Capitalize'), 
            datafilefinancial.getValue(GlobalVariable.NumofColm, 41))
    }
    
    if (datafilefinancial.getValue(GlobalVariable.NumofColm, 36) == 'Percentage') {
        WebUI.sendKeys(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Provision Fee Percentage'), 
            Keys.chord(Keys.CONTROL, 'a'), FailureHandling.OPTIONAL)

        WebUI.sendKeys(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Provision Fee Percentage'), 
            Keys.chord(Keys.BACK_SPACE), FailureHandling.OPTIONAL)

        WebUI.sendKeys(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Provision Fee Percentage'), 
            Keys.chord(Keys.HOME), FailureHandling.OPTIONAL)

        'input provision fee percentage'
        WebUI.sendKeys(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Provision Fee Percentage'), 
            Keys.chord(Keys.RIGHT, datafilefinancial.getValue(GlobalVariable.NumofColm, 38)), FailureHandling.OPTIONAL)
    } else if (datafilefinancial.getValue(GlobalVariable.NumofColm, 36) == 'Amount') {
        WebUI.sendKeys(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Provision Fee Amount'), 
            Keys.chord(Keys.CONTROL, 'a'), FailureHandling.OPTIONAL)

        WebUI.sendKeys(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Provision Fee Amount'), 
            Keys.chord(Keys.DELETE), FailureHandling.OPTIONAL)

        WebUI.sendKeys(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Provision Fee Amount'), 
            Keys.chord(Keys.HOME), FailureHandling.OPTIONAL)

        WebUI.sendKeys(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Provision Fee Amount'), 
            Keys.chord(Keys.RIGHT, datafilefinancial.getValue(GlobalVariable.NumofColm, 39)))
    }
}

if (datafilefinancial.getValue(GlobalVariable.NumofColm, 43).length() > 1) {
    'select Rate type'
    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/select_RateType'), 
        datafilefinancial.getValue(GlobalVariable.NumofColm, 43), false, FailureHandling.OPTIONAL)

    if (datafilefinancial.getValue(GlobalVariable.NumofColm, 43) == 'Effective Rate') {
        'input effective rate'
        WebUI.sendKeys(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Effective Rate'), 
            Keys.chord(Keys.CONTROL, 'a'), FailureHandling.OPTIONAL)

        WebUI.sendKeys(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Effective Rate'), 
            Keys.chord(Keys.BACK_SPACE), FailureHandling.OPTIONAL)

        WebUI.sendKeys(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Effective Rate'), 
            Keys.chord(Keys.HOME), FailureHandling.OPTIONAL)

        WebUI.sendKeys(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Effective Rate'), 
            Keys.chord(Keys.RIGHT, datafilefinancial.getValue(GlobalVariable.NumofColm, 44)), FailureHandling.OPTIONAL)
    } else if (datafilefinancial.getValue(GlobalVariable.NumofColm, 43) == 'Flat Rate') {
        'input flat rate'
        WebUI.sendKeys(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Flat Rate'), 
            Keys.chord(Keys.CONTROL, 'a'), FailureHandling.OPTIONAL)

        WebUI.sendKeys(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Flat Rate'), 
            Keys.chord(Keys.BACK_SPACE), FailureHandling.OPTIONAL)

        WebUI.sendKeys(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Flat Rate'), 
            Keys.chord(Keys.HOME), FailureHandling.OPTIONAL)

        WebUI.sendKeys(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Flat Rate'), 
            Keys.chord(Keys.RIGHT, datafilefinancial.getValue(GlobalVariable.NumofColm, 45)), FailureHandling.OPTIONAL)
    }
}

if (findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData').getValue(GlobalVariable.NumofColm, 
    18) == 'Arrear') {
    if (datafilefinancial.getValue(GlobalVariable.NumofColm, 46) == 'Yes') {
        'input grace period'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Grace Period'), 
            datafilefinancial.getValue(GlobalVariable.NumofColm, 47))

        'select method'
        WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/select_--Select--Interest OnlyRoll Over'), 
            datafilefinancial.getValue(GlobalVariable.NumofColm, 48), false)
    }
}

'click button calculate'
WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/button_Calculate'))

'click button calculate'
WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/button_Calculate'))

if (WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/td_InterestAmount'), 
    5, FailureHandling.OPTIONAL)) {
    'click button cancel'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/button_Cancel'))

    CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '9.TabFinancialData', 0, 
        GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)

    CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '9.TabFinancialData', 1, 
        GlobalVariable.NumofColm - 1, GlobalVariable.StatusReasonCalculateGagal)

    flagFailed = 1
}

'Pengecekan jika tdp at mf tidak kosong'
if (datafilefinancial.getValue(GlobalVariable.NumofColm, 49).length() > 0) {
    'input tdp at mf'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_TDP Paid at MF'), 
        datafilefinancial.getValue(GlobalVariable.NumofColm, 49))

    'click button calculate'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/button_Calculate'))
}

if (GlobalVariable.RoleCompany == 'Testing') {
    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabFinancialDataVerif'), [:], FailureHandling.CONTINUE_ON_FAILURE)
}

WebUI.delay(5)

'click button save'
WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/button_Save'))

Integer iscompleteMandatory = Integer.parseInt(findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData').getValue(
        GlobalVariable.NumofColm, 4))

if (iscompleteMandatory == 0) {
    'cek alert'
    flagFailed = CustomKeywords.'checkSaveProcess.checkSaveProcess.checkAlert'(GlobalVariable.NumofColm, '9.TabFinancialData')
}

if (flagFailed == 0) {
    'check save process write to excel'
    CustomKeywords.'checkSaveProcess.checkSaveProcess.checkStatus'(iscompleteMandatory, findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabTermConditionData/td_Checkbox'), 
        GlobalVariable.NumofColm, '9.TabFinancialData')

    if (iscompleteMandatory == 0) {
        errorValObject = findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData/div_errorvalidation')

        'cek validasi'
        CustomKeywords.'checkSaveProcess.checkSaveProcess.checkValidasi'(errorValObject, GlobalVariable.NumofColm, '9.TabFinancialData')
    }
}

//if (GlobalVariable.RoleCompany == 'Testing' && GlobalVariable.CheckVerifStoreDBPersonalCompany=="Yes") {
//	'call test case subsidy store db verif'
//	WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabSubsidyStoreDBVerif'),
//			[:], FailureHandling.CONTINUE_ON_FAILURE)
//	
//
//	'call test case financial datastore db verif'
//	WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabFinancialDataStoreDBVerif'),
//			[:], FailureHandling.CONTINUE_ON_FAILURE)
//	
//}
if (WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/ApplicationCurrentStep')), 
    'FINANCIAL DATA', false, FailureHandling.OPTIONAL)) {
    'click button cancel'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/button_Cancel'))
}

