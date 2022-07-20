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

GlobalVariable.SubsidyDPValue = "0"
'Assign directori file excel ke global variabel'
String userDir = System.getProperty('user.dir')

'Assign directori file excel ke global variabel'
String filePath = userDir + GlobalVariable.Path

'Assign directori file excel ke global variabel'
GlobalVariable.DataFilePath = filePath

def SubsidyTypeArray = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData').getValue(
    GlobalVariable.NumofColm, 3).split(';')

def SubsidyfromValueArray = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData').getValue(
    GlobalVariable.NumofColm, 4).split(';')

def AllocationformArray = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData').getValue(
    GlobalVariable.NumofColm, 5).split(';')

def SubsidySourceArray = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData').getValue(
    GlobalVariable.NumofColm, 6).split(';')

def SubsidyValueTypeArray = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData').getValue(
    GlobalVariable.NumofColm, 7).split(';')

def SubsidyValueAmountArray = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData').getValue(
    GlobalVariable.NumofColm, 8).split(';')

def SubsidyValuePercentageArray = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData').getValue(
    GlobalVariable.NumofColm, 9).split(';')

WebDriver driver = DriverFactory.getWebDriver()

ArrayList<WebElement> variable = driver.findElements(By.cssSelector('#FinData_Subsidy > div.table-responsive > table > tbody tr'))

if (WebUI.verifyNotMatch(WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/tablesubsidynodata')), 
    'NO DATA AVAILABLE', false, FailureHandling.OPTIONAL)) {
    for (i = 1; i <= variable.size(); i++) {
        String NewFromTypeName = ('//*[@id="FinData_Subsidy"]/div[2]/table/tbody/tr[' + i) + ']/td[1]'

        'modify object from type name'
        modifyNewFromTypeName = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/FromTypeName'), 
            'xpath', 'equals', NewFromTypeName, true)

        String NewFromValueName = ('//*[@id="FinData_Subsidy"]/div[2]/table/tbody/tr[' + i) + ']/td[2]'

        'modify object from value name'
        modifyNewFromValueName = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/FromValueName'), 
            'xpath', 'equals', NewFromValueName, true)

        String NewSubsidyAllocation = ('//*[@id="FinData_Subsidy"]/div[2]/table/tbody/tr[' + i) + ']/td[3]'

        'modify object subsidy allocation'
        modifyNewSubsidyAllocation = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/SubsidyAllocation'), 
            'xpath', 'equals', NewSubsidyAllocation, true)

        String NewSubsidySource = ('//*[@id="FinData_Subsidy"]/div[2]/table/tbody/tr[' + i) + ']/td[5]'

        'modify object subsidy source'
        modifyNewSubsidySource = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/SubsidySource'), 
            'xpath', 'equals', NewSubsidySource, true)

        String NewButtonEdit = ('//*[@id="FinData_Subsidy"]/div[2]/table/tbody/tr[' + i) + ']/td[8]/a[1]/i'

        'modify object button edit'
        modifyNewButtonEdit = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/Button_Edit'), 
            'xpath', 'equals', NewButtonEdit, true)

        String NewButtonDelete = ('//*[@id="FinData_Subsidy"]/div[2]/table/tbody/tr[' + i) + ']/td[8]/a[2]/i'

        'modify object button Delete'
        modifyNewButtonDelete = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/Button_Delete'), 
            'xpath', 'equals', NewButtonDelete, true)

        for (subsidyarray = 1; subsidyarray <= SubsidyTypeArray.size(); subsidyarray++) {
            if (((WebUI.getText(modifyNewFromTypeName).equalsIgnoreCase(SubsidyTypeArray[(subsidyarray - 1)]) && WebUI.getText(
                modifyNewFromValueName).equalsIgnoreCase(SubsidyfromValueArray[(subsidyarray - 1)])) && WebUI.getText(modifyNewSubsidyAllocation).equalsIgnoreCase(
                AllocationformArray[(subsidyarray - 1)])) && WebUI.getText(modifyNewSubsidySource).equalsIgnoreCase(SubsidySourceArray[
                (subsidyarray - 1)])) {
                WebUI.click(modifyNewButtonEdit, FailureHandling.OPTIONAL)

                'select multifinance / supplier'
                WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/select_--Select--MultifinanceSupplier'), 
                    SubsidyTypeArray[(subsidyarray - 1)], false)

                if ((SubsidyTypeArray[(subsidyarray - 1)]).equalsIgnoreCase('Supplier')) {
                    'select subsidy from value'
                    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/select_SubsidyFromValueSupplier'), 
                        SubsidyfromValueArray[(subsidyarray - 1)], false)

                    'select subsidy allocation from '
                    WebUI.selectOptionByLabel(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/select_AllocationFromSupplier'), 
                        AllocationformArray[(subsidyarray - 1)], false)

                    'select subsidy source'
                    WebUI.selectOptionByLabel(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/select_SubsidySourceSupplier'), 
                        SubsidySourceArray[(subsidyarray - 1)], false)

                    'select subsidy value type'
                    WebUI.selectOptionByLabel(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/Select_SubsidyValueTypeSupplier'), 
                        SubsidyValueTypeArray[(subsidyarray - 1)], false)

                    if ((SubsidyValueTypeArray[(subsidyarray - 1)]).equalsIgnoreCase('Amount')) {
                        'input subsidy value amount'
                        WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/input_SubsidyValueSupplier'), 
                            SubsidyValueAmountArray[(subsidyarray - 1)])
                    } else if ((SubsidyValueTypeArray[(subsidyarray - 1)]).equalsIgnoreCase('Percentage')) {
                        'input subsidy value percentage'
                        WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/input_SubsidyValueSupplier'), 
                            SubsidyValuePercentageArray[(subsidyarray - 1)])
                    }
                } else if ((SubsidyTypeArray[(subsidyarray - 1)]).equalsIgnoreCase('Multifinance')) {
                    'select subsidy allocation from'
                    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/select_AllocationFromMultifinance'), 
                        AllocationformArray[(subsidyarray - 1)], false)

                    'select subsidy source'
                    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/select_SubsidySourceMultifinance'), 
                        SubsidySourceArray[(subsidyarray - 1)], false)

                    'select subsidy value type'
                    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/select_SubsidyValueTypeMultifinance'), 
                        SubsidyValueTypeArray[(subsidyarray - 1)], false)

                    if ((SubsidyValueTypeArray[(subsidyarray - 1)]).equalsIgnoreCase('Amount')) {
                        'input subsidy value amount'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/input_SubsidyValueAmountMultifinance'), 
                            SubsidyValueAmountArray[(subsidyarray - 1)])
                    } else if ((SubsidyValueTypeArray[(subsidyarray - 1)]).equalsIgnoreCase('Percentage')) {
                        'input subsidy value percentage'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/input_SubsidyValueAmountMultifinance'), 
                            SubsidyValuePercentageArray[(subsidyarray - 1)])
                    }
                }
                
                'click button save subsidy'
                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/button_SaveSubsidy'))

                'Verify gagal add'
                if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/select_--Select--MultifinanceSupplier'), 
                    10, FailureHandling.OPTIONAL)) {
                    'Click cancel'
                    WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/button_CancelSubsidy'))
                }
                
                break
            } else {
                if (subsidyarray == SubsidyTypeArray.size()) {
                    if (WebUI.verifyNotMatch(WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/tablesubsidynodata')), 
                        'NO DATA AVAILABLE', false, FailureHandling.OPTIONAL)) {
                        'click button delete'
                        WebUI.click(modifyNewButtonDelete, FailureHandling.OPTIONAL)

                        WebUI.acceptAlert(FailureHandling.OPTIONAL)

                        variable = driver.findElements(By.cssSelector('#FinData_Subsidy > div.table-responsive > table > tbody tr'))

                        i--
                    } else {
                        break
                    }
                }
            }
        }
    }
}

WebDriver driverr = DriverFactory.getWebDriver()

ArrayList<WebElement> variableData = driverr.findElements(By.cssSelector('#FinData_Subsidy > div.table-responsive > table > tbody tr'))

int countData = variableData.size()

for (s = 1; s <= SubsidyTypeArray.size(); s++) {
    for (SubsidyCheck = 1; SubsidyCheck <= countData; SubsidyCheck++) {
        if (WebUI.verifyNotMatch(WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/tablesubsidynodata')), 
            'NO DATA AVAILABLE', false, FailureHandling.OPTIONAL)) {
            String NewFromTypeName = ('//*[@id="FinData_Subsidy"]/div[2]/table/tbody/tr[' + SubsidyCheck) + ']/td[1]'

            'modify object from type name'
            modifyNewFromTypeName = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/FromTypeName'), 
                'xpath', 'equals', NewFromTypeName, true)

            String NewFromValueName = ('//*[@id="FinData_Subsidy"]/div[2]/table/tbody/tr[' + SubsidyCheck) + ']/td[2]'

            'modify object from value name'
            modifyNewFromValueName = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/FromValueName'), 
                'xpath', 'equals', NewFromValueName, true)

            String NewSubsidyAllocation = ('//*[@id="FinData_Subsidy"]/div[2]/table/tbody/tr[' + SubsidyCheck) + ']/td[3]'

            'modify object subsidy allocation'
            modifyNewSubsidyAllocation = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/SubsidyAllocation'), 
                'xpath', 'equals', NewSubsidyAllocation, true)

            String NewSubsidySource = ('//*[@id="FinData_Subsidy"]/div[2]/table/tbody/tr[' + SubsidyCheck) + ']/td[5]'

            'modify object subsidy source'
            modifyNewSubsidySource = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/SubsidySource'), 
                'xpath', 'equals', NewSubsidySource, true)

            if (((!((SubsidyTypeArray[(s - 1)]).equalsIgnoreCase(WebUI.getText(modifyNewFromTypeName))) || !((SubsidyfromValueArray[
            (s - 1)]).equalsIgnoreCase(WebUI.getText(modifyNewFromValueName)))) || !((AllocationformArray[(s - 1)]).equalsIgnoreCase(
                WebUI.getText(modifyNewSubsidyAllocation)))) || !((SubsidySourceArray[(s - 1)]).equalsIgnoreCase(WebUI.getText(
                    modifyNewSubsidySource)))) {
                if (countData == SubsidyCheck) {
                    'click button add subsidy'
                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/button_Add Subsidy'))

                    'select multifinance / supplier'
                    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/select_--Select--MultifinanceSupplier'), 
                        SubsidyTypeArray[(s - 1)], false)

                    if ((SubsidyTypeArray[(s - 1)]).equalsIgnoreCase('Supplier')) {
                        'select subsidy from value'
                        WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/select_SubsidyFromValueSupplier'), 
                            SubsidyfromValueArray[(s - 1)], false)

                        'select subsidy allocation from '
                        WebUI.selectOptionByLabel(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/select_AllocationFromSupplier'), 
                            AllocationformArray[(s - 1)], false)

                        'select subsidy source'
                        WebUI.selectOptionByLabel(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/select_SubsidySourceSupplier'), 
                            SubsidySourceArray[(s - 1)], false)

                        'select subsidy value type'
                        WebUI.selectOptionByLabel(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/Select_SubsidyValueTypeSupplier'), 
                            SubsidyValueTypeArray[(s - 1)], false)

                        if ((SubsidyValueTypeArray[(s - 1)]).equalsIgnoreCase('Amount')) {
                            'input subsidy value amount'
                            WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/input_SubsidyValueSupplier'), 
                                SubsidyValueAmountArray[(s - 1)])
                        } else if ((SubsidyValueTypeArray[(s - 1)]).equalsIgnoreCase('Percentage')) {
                            'input subsidy value percentage'
                            WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/input_SubsidyValueSupplier'), 
                                SubsidyValuePercentageArray[(s - 1)])
                        }
                    } else if ((SubsidyTypeArray[(s - 1)]).equalsIgnoreCase('Multifinance')) {
                        'select subsidy allocation from'
                        WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/select_AllocationFromMultifinance'), 
                            AllocationformArray[(s - 1)], false)

                        'select subsidy source'
                        WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/select_SubsidySourceMultifinance'), 
                            SubsidySourceArray[(s - 1)], false)

                        'select subsidy value type'
                        WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/select_SubsidyValueTypeMultifinance'), 
                            SubsidyValueTypeArray[(s - 1)], false)

                        if ((SubsidyValueTypeArray[(s - 1)]).equalsIgnoreCase('Amount')) {
                            'input subsidy value amount'
                            WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/input_SubsidyValueAmountMultifinance'), 
                                SubsidyValueAmountArray[(s - 1)])
                        } else if ((SubsidyValueTypeArray[(s - 1)]).equalsIgnoreCase('Percentage')) {
                            'input subsidy value percentage'
                            WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/input_SubsidyValueAmountMultifinance'), 
                                SubsidyValuePercentageArray[(s - 1)])
                        }
                    }
                    
                    'click button save subsidy'
                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/button_SaveSubsidy'))

                    'Verify gagal add'
                    if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/select_--Select--MultifinanceSupplier'), 
                        10, FailureHandling.OPTIONAL)) {
                        'Click cancel'
                        WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/button_CancelSubsidy'))
                    }
                }
            } else {
                break
            }
        } else if (WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/tablesubsidynodata')), 
            'NO DATA AVAILABLE', false, FailureHandling.OPTIONAL)) {
            if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData').getValue(
                GlobalVariable.NumofColm, 3) != '') {
                if (SubsidyTypeArray.size() > 0) {
                    'click button add subsidy'
                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/button_Add Subsidy'))

                    'select multifinance / supplier'
                    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/select_--Select--MultifinanceSupplier'), 
                        SubsidyTypeArray[(s - 1)], false)

                    if ((SubsidyTypeArray[(s - 1)]).equalsIgnoreCase('Supplier')) {
                        'select subsidy from value'
                        WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/select_SubsidyFromValueSupplier'), 
                            SubsidyfromValueArray[(s - 1)], false)

                        'select subsidy allocation from '
                        WebUI.selectOptionByLabel(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/select_AllocationFromSupplier'), 
                            AllocationformArray[(s - 1)], false)

                        'select subsidy source'
                        WebUI.selectOptionByLabel(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/select_SubsidySourceSupplier'), 
                            SubsidySourceArray[(s - 1)], false)

                        'select subsidy value type'
                        WebUI.selectOptionByLabel(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/Select_SubsidyValueTypeSupplier'), 
                            SubsidyValueTypeArray[(s - 1)], false)

                        if ((SubsidyValueTypeArray[(s - 1)]).equalsIgnoreCase('Amount')) {
                            'input subsidy value amount'
                            WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/input_SubsidyValueSupplier'), 
                                SubsidyValueAmountArray[(s - 1)])
                        } else if ((SubsidyValueTypeArray[(s - 1)]).equalsIgnoreCase('Percentage')) {
                            'input subsidy value percentage'
                            WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/input_SubsidyValueSupplier'), 
                                SubsidyValuePercentageArray[(s - 1)])
                        }
                    } else if ((SubsidyTypeArray[(s - 1)]).equalsIgnoreCase('Multifinance')) {
                        'select subsidy allocation from'
                        WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/select_AllocationFromMultifinance'), 
                            AllocationformArray[(s - 1)], false)

                        'select subsidy source'
                        WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/select_SubsidySourceMultifinance'), 
                            SubsidySourceArray[(s - 1)], false)

                        'select subsidy value type'
                        WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/select_SubsidyValueTypeMultifinance'), 
                            SubsidyValueTypeArray[(s - 1)], false)

                        if ((SubsidyValueTypeArray[(s - 1)]).equalsIgnoreCase('Amount')) {
                            'input subsidy value amount'
                            WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/input_SubsidyValueAmountMultifinance'), 
                                SubsidyValueAmountArray[(s - 1)])
                        } else if ((SubsidyValueTypeArray[(s - 1)]).equalsIgnoreCase('Percentage')) {
                            'input subsidy value percentage'
                            WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/input_SubsidyValueAmountMultifinance'), 
                                SubsidyValuePercentageArray[(s - 1)])
                        }
                    }
                    
                    'click button save subsidy'
                    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/button_SaveSubsidy'))

                    'Verify gagal add'
                    if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/select_--Select--MultifinanceSupplier'), 
                        10, FailureHandling.OPTIONAL)) {
                        'Click cancel'
                        WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/button_CancelSubsidy'))
                    }
                }
            }
        }
    }
}

for (int SubsidyCheck = 1; SubsidyCheck <= variableData.size(); SubsidyCheck++) {
    if (WebUI.verifyNotMatch(WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/tablesubsidynodata')), 
        'NO DATA AVAILABLE', false, FailureHandling.OPTIONAL)) {
        String NewSubsidyAllocation = ('//*[@id="FinData_Subsidy"]/div[2]/table/tbody/tr[' + SubsidyCheck) + ']/td[3]'

        'modify object subsidy allocation'
        modifyNewSubsidyAllocation = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/SubsidyAllocation'), 
            'xpath', 'equals', NewSubsidyAllocation, true)

        String NewSubsidyAmount = ('//*[@id="FinData_Subsidy"]/div[2]/table/tbody/tr[' + SubsidyCheck) + ']/td[7]'

        'modify object SubsidyAmount'
        modifyNewSubsidyAmount = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/SubsidyAmount'), 
            'xpath', 'equals', NewSubsidyAmount, true)

        if (WebUI.getText(modifyNewSubsidyAllocation, FailureHandling.OPTIONAL).equalsIgnoreCase('SUBSIDY DP')) {
            GlobalVariable.SubsidyDPValue = WebUI.getText(modifyNewSubsidyAmount, FailureHandling.CONTINUE_ON_FAILURE).replace(',', '').replace(
                '.00', '')

            println(GlobalVariable.SubsidyDPValue)
        }
    }
}

if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData').getValue(
    GlobalVariable.NumofColm, 11) == 'No') {
    'input admin fee'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/input_Admin Fee'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData').getValue(
            GlobalVariable.NumofColm, 12))

    if (WebUI.verifyElementNotChecked(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/input_Admin Fee Capitalize_checkbox'), 
        2, FailureHandling.OPTIONAL)) {
        if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData').getValue(
            GlobalVariable.NumofColm, 17) == 'Yes') {
            'click admin fee capitalized checkbox'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/input_Admin Fee Capitalize_checkbox'))
        }
    } else {
        if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData').getValue(
            GlobalVariable.NumofColm, 17) == 'No') {
            'click admin fee capitalized checkbox'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/input_Admin Fee Capitalize_checkbox'))
        }
    }
    
    if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData').getValue(
        GlobalVariable.NumofColm, 17) == 'Yes') {
        'input admin fee capitalized '
        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/input_Admin Fee Capitalize_'), 
            findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData').getValue(
                GlobalVariable.NumofColm, 18))
    }
    
    'input additional admin'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/input_Additional Admin'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData').getValue(
            GlobalVariable.NumofColm, 13))

    if (WebUI.verifyElementNotChecked(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/input_Additional Admin Capitalize_checkbox'), 
        2, FailureHandling.OPTIONAL)) {
        if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData').getValue(
            GlobalVariable.NumofColm, 19) == 'Yes') {
            'click additional admin capitalized checkbox'
            WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/input_Additional Admin Capitalize_checkbox'))
        }
    } else {
        if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData').getValue(
            GlobalVariable.NumofColm, 19) == 'No') {
            'click additional admin capitalized checkbox'
            WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/input_Additional Admin Capitalize_checkbox'))
        }
    }
    
    if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData').getValue(
        GlobalVariable.NumofColm, 19) == 'Yes') {
        'input additional admin capitalized'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/input_Additional Admin Capitalize'), 
            findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData').getValue(
                GlobalVariable.NumofColm, 20))
    }
    
    'input notary fee'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/input_Notary Fee'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData').getValue(
            GlobalVariable.NumofColm, 14))

    if (WebUI.verifyElementNotChecked(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/input_Notary Fee Capitalize_checkbox'), 
        2, FailureHandling.OPTIONAL)) {
        if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData').getValue(
            GlobalVariable.NumofColm, 21) == 'Yes') {
            'click notary fee capitalized checkbox'
            WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/input_Notary Fee Capitalize_checkbox'))
        }
    } else {
        if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData').getValue(
            GlobalVariable.NumofColm, 21) == 'No') {
            'click notary fee capitalized checkbox'
            WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/input_Notary Fee Capitalize_checkbox'))
        }
    }
    
    if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData').getValue(
        GlobalVariable.NumofColm, 21) == 'Yes') {
        'input lick notary fee capitalized '
        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/input_Notary Fee Capitalize'), 
            findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData').getValue(
                GlobalVariable.NumofColm, 22))
    }
    
    'input other fee'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/input_Other Fee'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData').getValue(
            GlobalVariable.NumofColm, 15))

    if (WebUI.verifyElementNotChecked(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/input_Other Fee Capitalize_checkbox'), 
        2, FailureHandling.OPTIONAL)) {
        if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData').getValue(
            GlobalVariable.NumofColm, 23) == 'Yes') {
            'click other fee capitalized checkbox'
            WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/input_Other Fee Capitalize_checkbox'))
        }
    } else {
        if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData').getValue(
            GlobalVariable.NumofColm, 23) == 'No') {
            'click other fee capitalized checkbox'
            WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/input_Other Fee Capitalize_checkbox'))
        }
    }
    
    if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData').getValue(
        GlobalVariable.NumofColm, 23) == 'Yes') {
        'input other fee capitalized'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/input_Other Fee Capitalize'), 
            findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData').getValue(
                GlobalVariable.NumofColm, 24))
    }
    
    'input fiducia fee'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/input_Fiducia Fee'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData').getValue(
            GlobalVariable.NumofColm, 16))

    if (WebUI.verifyElementNotChecked(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/input_Fiducia Fee Capitalize_checkbox'), 
        2, FailureHandling.OPTIONAL)) {
        if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData').getValue(
            GlobalVariable.NumofColm, 25) == 'Yes') {
            'click fiducia fee capitalized checkbox'
            WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/input_Fiducia Fee Capitalize_checkbox'))
        }
    } else {
        if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData').getValue(
            GlobalVariable.NumofColm, 25) == 'No') {
            'click fiducia fee capitalized checkbox'
            WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/input_Fiducia Fee Capitalize_checkbox'))
        }
    }
    
    if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData').getValue(
        GlobalVariable.NumofColm, 25) == 'Yes') {
        'input fiducia fee capitalized'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/input_Fiducia Fee Capitalize'), 
            findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData').getValue(
                GlobalVariable.NumofColm, 26))
    }
    
    if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData').getValue(
        GlobalVariable.NumofColm, 27) == 'Amount') {
        'click amount radio button'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/div_AmountRadio'))

        'click amount radio button'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/div_AmountRadio'))
    } else if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData').getValue(
        GlobalVariable.NumofColm, 27) == 'Percentage') {
        'click percentage radio button'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/div_PercentageRadio'))

        'click percentage radio button'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/div_PercentageRadio'))
    }
    
    'select Provision fee calculation base'
    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/select_OTR-DPOTR-DP  Ins Cptlz  Fee Cptlz(Excl Provision)'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData').getValue(
            GlobalVariable.NumofColm, 28), false)

    if (WebUI.verifyElementNotChecked(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/input_Provision Fee Capitalize_checkbox'), 
        2, FailureHandling.OPTIONAL)) {
        if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData').getValue(
            GlobalVariable.NumofColm, 31) == 'Yes') {
            'click provision fee capitalized checkbox'
            WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/input_Provision Fee Capitalize_checkbox'))
        }
    } else {
        if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData').getValue(
            GlobalVariable.NumofColm, 31) == 'No') {
            'click provision fee capitalized checkbox'
            WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/input_Provision Fee Capitalize_checkbox'))
        }
    }
    
    if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData').getValue(
        GlobalVariable.NumofColm, 31) == 'Yes') {
        'input provision fee capitalized '
        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/input_Provision Fee Capitalize'), 
            findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData').getValue(
                GlobalVariable.NumofColm, 32))
    }
    
    if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData').getValue(
        GlobalVariable.NumofColm, 27) == 'Percentage') {
        WebUI.sendKeys(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/input_Provision Fee Percentage'), 
            Keys.chord(Keys.CONTROL, 'a'), FailureHandling.OPTIONAL)

        WebUI.sendKeys(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/input_Provision Fee Percentage'), 
            Keys.chord(Keys.BACK_SPACE), FailureHandling.OPTIONAL)

        WebUI.sendKeys(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/input_Provision Fee Percentage'), 
            Keys.chord(Keys.HOME), FailureHandling.OPTIONAL)

        'input provision fee percentage'
        WebUI.sendKeys(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/input_Provision Fee Percentage'), 
            Keys.chord(Keys.RIGHT, findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData').getValue(
                    GlobalVariable.NumofColm, 29)), FailureHandling.OPTIONAL)
    } else if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData').getValue(
        GlobalVariable.NumofColm, 27) == 'Amount') {
        WebUI.sendKeys(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/input_Provision Fee Amount'), 
            Keys.chord(Keys.CONTROL, 'a'), FailureHandling.OPTIONAL)

        WebUI.sendKeys(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/input_Provision Fee Amount'), 
            Keys.chord(Keys.DELETE), FailureHandling.OPTIONAL)

        WebUI.sendKeys(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/input_Provision Fee Amount'), 
            Keys.chord(Keys.HOME), FailureHandling.OPTIONAL)

        WebUI.sendKeys(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/input_Provision Fee Amount'), 
            Keys.chord(Keys.RIGHT, findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData').getValue(
                    GlobalVariable.NumofColm, 30)))
    }
}

if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData').getValue(
    GlobalVariable.NumofColm, 34).length() > 1) {
    'select Rate type'
    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/select_RateType'), 
        findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData').getValue(
            GlobalVariable.NumofColm, 34), false, FailureHandling.OPTIONAL)

    if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData').getValue(
        GlobalVariable.NumofColm, 34) == 'Effective Rate') {
        'input effective rate'
        WebUI.sendKeys(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/input_Effective Rate'), 
            Keys.chord(Keys.CONTROL, 'a'), FailureHandling.OPTIONAL)

        WebUI.sendKeys(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/input_Effective Rate'), 
            Keys.chord(Keys.BACK_SPACE), FailureHandling.OPTIONAL)

        WebUI.sendKeys(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/input_Effective Rate'), 
            Keys.chord(Keys.HOME), FailureHandling.OPTIONAL)

        WebUI.sendKeys(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/input_Effective Rate'), 
            Keys.chord(Keys.RIGHT, findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData').getValue(
                    GlobalVariable.NumofColm, 35)), FailureHandling.OPTIONAL)
    } else if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData').getValue(
        GlobalVariable.NumofColm, 34) == 'Flat Rate') {
        'input flat rate'
        WebUI.sendKeys(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/input_Flat Rate'), 
            Keys.chord(Keys.CONTROL, 'a'), FailureHandling.OPTIONAL)

        WebUI.sendKeys(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/input_Flat Rate'), 
            Keys.chord(Keys.BACK_SPACE), FailureHandling.OPTIONAL)

        WebUI.sendKeys(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/input_Flat Rate'), 
            Keys.chord(Keys.HOME), FailureHandling.OPTIONAL)

        WebUI.sendKeys(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/input_Flat Rate'), 
            Keys.chord(Keys.RIGHT, findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData').getValue(
                    GlobalVariable.NumofColm, 36)), FailureHandling.OPTIONAL)
    }
}

if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabApplicationData').getValue(
    GlobalVariable.NumofColm, 9) == 'Arrear') {
    if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData').getValue(
        GlobalVariable.NumofColm, 37) == 'Yes') {
        'input grace period'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/input_Grace Period'), 
            findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData').getValue(
                GlobalVariable.NumofColm, 38))

        'select method'
        WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/select_--Select--Interest OnlyRoll Over'), 
            findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData').getValue(
                GlobalVariable.NumofColm, 39), false)
    }
}

'click button calculate'
WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/button_Calculate'))

WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP-CF4W-PersonalSingle/NAP2 - Application Data/TabFinancialDataVerif'),
	[:], FailureHandling.CONTINUE_ON_FAILURE)


if (WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/label_FIRST INSTALLMENT TYPE')).equalsIgnoreCase(
    'ADVANCE')) {
    'verify interest amount "0.00" if insttalment type Advance'
    WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/td_InterestAmount')), 
        '0.00', false)
} else if (WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/label_FIRST INSTALLMENT TYPE')).equalsIgnoreCase(
    'ARREAR')) {
    'verify interest amount NOT "0.00" if installment type arrear'
    WebUI.verifyNotMatch(WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/td_InterestAmount')), 
        '0.00', false)
}

WebUI.delay(5)

'click button save'
WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData/button_Save'))

CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '10.TabFinancialData', 0, GlobalVariable.NumofColm - 
    1, GlobalVariable.StatusSuccess)

if (WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/ApplicationCurrentStep')), 
    'FINANCIAL DATA', false, FailureHandling.OPTIONAL)) {
    'click button cancel'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData/button_Cancel'))

    CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '10.TabFinancialData', 
        0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)
}


