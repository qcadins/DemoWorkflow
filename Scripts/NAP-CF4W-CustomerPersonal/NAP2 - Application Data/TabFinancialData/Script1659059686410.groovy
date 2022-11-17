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

GlobalVariable.FlagFailed=0


'Assign directori file excel ke global variabel'
String userDir = System.getProperty('user.dir')

'Assign directori file excel ke global variabel'
String filePath = userDir + GlobalVariable.PathPersonal

'Assign directori file excel ke global variabel'
GlobalVariable.DataFilePath = filePath

String appLastStep = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/label_AppLastStep'))

if(!appLastStep.equalsIgnoreCase("LIFE INSURANCE") && GlobalVariable.FirstTimeEntry=="Yes"){
	GlobalVariable.FirstTimeEntry = "No"
}

WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabSubsidyFinancial'),
	[:], FailureHandling.CONTINUE_ON_FAILURE)

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

WebDriver driver = DriverFactory.getWebDriver()

'Ambil appno dari confins'
String appNo = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/span_appNo'))

if (GlobalVariable.Role == 'Testing') {
    'verify application step'
    checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/ApplicationCurrentStep')), 
        'FINANCIAL DATA', false, FailureHandling.OPTIONAL))
}

def datafilefinancial = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData')

//Verif fee based on rule
if ((GlobalVariable.Role == 'Testing') && (GlobalVariable.CheckRulePersonal == 'Yes') && GlobalVariable.FirstTimeEntry == "Yes") {
    'Ambil nilai result dari rule credit fee'
    HashMap<String, ArrayList> result = CustomKeywords.'financialData.verifFee.verifyFinancialFee'(sqlConnectionLOS, appNo)

    ArrayList<String> listFee

    ArrayList<String> feeType

    ArrayList<String> fee

    ArrayList<String> feeBhv

    ArrayList<String> feecapType

    ArrayList<String> feecap

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
        if (CustomKeywords.'financialData.verifFee.checkFeeCode'(sqlConnectionLOS, WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/label_Fee'))) == 
        listFee.get(i)) {
            'Verify amount admin fee pada confins sesuai rule'
            if (WebUI.verifyMatch(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/input_Admin Fee'), 
                    'value').replace(',', ''), fee.get(i), false) == false) {
                writeReasonFailedVerifRule()
            }
            
            if (feeBhv.get(i) == 'DEF') {
                'Verify admin fee tidak terlock'
                WebUI.verifyElementNotHasAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/input_Admin Fee'), 
                    'readonly', 1)
            } else if (feeBhv.get(i) == 'LOCK') {
                'verify admin fee terlock'
                WebUI.verifyElementHasAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/input_Admin Fee'), 
                    'readonly', 1)
				CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '10.TabFinancialData',
					20, GlobalVariable.NumofColm - 1, WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/input_Admin Fee'),'value'))
				
            }
            
            if (feecapType.get(i) == 'AMT') {
                'Verify admin fee capitalized amount sesuai rule'
                if (WebUI.verifyMatch(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/input_Admin Fee Capitalize_'), 
                        'value').replace(',', ''), feecap.get(i), false, FailureHandling.OPTIONAL) == false) {
                    writeReasonFailedVerifRule()
                }
            }
        }
        
        'Pengecekan jika list fee pada rule sesuai dengan fee pada confins'
        if (CustomKeywords.'financialData.verifFee.checkFeeCode'(sqlConnectionLOS, WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/label_Fee2'))) == 
        listFee.get(i)) {
            'Verify amount additional admin pada confins sesuai rule'
            if (WebUI.verifyMatch(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/input_Additional Admin'), 
                    'value').replace(',', ''), fee.get(i), false) == false) {
                writeReasonFailedVerifRule()
            }
            
            if (feeBhv.get(i) == 'DEF') {
                'verify additional admin tidak terlock'
                WebUI.verifyElementNotHasAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/input_Additional Admin'), 
                    'readonly', 1)
            } else if (feeBhv.get(i) == 'LOCK') {
                'verify additional admin terlock'
                WebUI.verifyElementHasAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/input_Additional Admin'), 
                    'readonly', 1)
				
				CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '10.TabFinancialData',
					21, GlobalVariable.NumofColm - 1, WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/input_Additional Admin'),'value'))
            }
            
            if (feecapType.get(i) == 'AMT') {
                'Verify additional admin capitalized amount sesuai rule'
                if (WebUI.verifyMatch(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/input_Additional Admin Capitalize'), 
                        'value').replace(',', ''), feecap.get(i), false, FailureHandling.OPTIONAL) == false) {
                    writeReasonFailedVerifRule()
                }
            }
        }
        
        'Pengecekan jika list fee pada rule sesuai dengan fee pada confins'
        if (CustomKeywords.'financialData.verifFee.checkFeeCode'(sqlConnectionLOS, WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/label_Fee3'))) == 
        listFee.get(i)) {
            'Verify notary fee pada confins sesuai rule'
            if (WebUI.verifyMatch(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/input_Notary Fee'), 
                    'value').replace(',', ''), fee.get(i), false) == false) {
                writeReasonFailedVerifRule()
            }
            
            if (feeBhv.get(i) == 'DEF') {
                'Verify notary fee tidak terlock'
                WebUI.verifyElementNotHasAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/input_Notary Fee'), 
                    'readonly', 1)
            } else if (feeBhv.get(i) == 'LOCK') {
                'Verify notary fee terlock'
                WebUI.verifyElementHasAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/input_Notary Fee'), 
                    'readonly', 1)
				
				CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '10.TabFinancialData',
					22, GlobalVariable.NumofColm - 1, WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/input_Notary Fee'),'value'))
            }
            
            if (feecapType.get(i) == 'AMT') {
                'Verify notary fee capitalized amount sesuai rule'
                if (WebUI.verifyMatch(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/input_Notary Fee Capitalize'), 
                        'value').replace(',', ''), feecap.get(i), false, FailureHandling.OPTIONAL) == false) {
                    writeReasonFailedVerifRule()
                }
            }
        }
        
        'Pengecekan jika list fee pada rule sesuai dengan fee pada confins'
        if (CustomKeywords.'financialData.verifFee.checkFeeCode'(sqlConnectionLOS, WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/label_Fee4'))) == 
        listFee.get(i)) {
            'Verify other fee pada confins sesuai rule'
            if (WebUI.verifyMatch(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/input_Other Fee'), 
                    'value').replace(',', ''), fee.get(i), false) == false) {
                writeReasonFailedVerifRule()
            }
            
            if (feeBhv.get(i) == 'DEF') {
                'verify other fee tidak terlock'
                WebUI.verifyElementNotHasAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/input_Other Fee'), 
                    'readonly', 1)
            } else if (feeBhv.get(i) == 'LOCK') {
                'verify other fee terlock'
                WebUI.verifyElementHasAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/input_Other Fee'), 
                    'readonly', 1)
				
				CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '10.TabFinancialData',
					23, GlobalVariable.NumofColm - 1, WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/input_Other Fee'),'value'))
            }
            
            if (feecapType.get(i) == 'AMT') {
                'Verify other fee capitalized amount sesuai rule'
                if (WebUI.verifyMatch(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/input_Other Fee Capitalize'), 
                        'value').replace(',', ''), feecap.get(i), false, FailureHandling.OPTIONAL) == false) {
                    writeReasonFailedVerifRule()
                }
            }
        }
        
        'Pengecekan jika list fee pada rule sesuai dengan fee pada confins'
        if (CustomKeywords.'financialData.verifFee.checkFeeCode'(sqlConnectionLOS, WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/label_Fee5'))) == 
        listFee.get(i)) {
            'verify fiducia fee pada confins sesuai rule'
            if (WebUI.verifyMatch(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/input_Fiducia Fee'), 
                    'value').replace(',', ''), fee.get(i), false) == false) {
                writeReasonFailedVerifRule()
            }
            
            if (feeBhv.get(i) == 'DEF') {
                'Verify fiducia fee tidak terlock'
                WebUI.verifyElementNotHasAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/input_Fiducia Fee'), 
                    'readonly', 1)
            } else if (feeBhv.get(i) == 'LOCK') {
                'Verify fiducia fee terlock'
                WebUI.verifyElementHasAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/input_Fiducia Fee'), 
                    'readonly', 1)
				
				CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '10.TabFinancialData',
					24, GlobalVariable.NumofColm - 1, WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/input_Fiducia Fee'),'value'))
            }
            
            if (feecapType.get(i) == 'AMT') {
                'Verify fiducia fee capitalized amount sesuai rule'
                if (WebUI.verifyMatch(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/input_Fiducia Fee Capitalize'), 
                        'value').replace(',', ''), feecap.get(i), false, FailureHandling.OPTIONAL) == false) {
                    writeReasonFailedVerifRule()
                }
            }
        }
        
        'Jika list fee merupakan provision'
        if (listFee.get(i) == 'PROVISION') {
            'Pengecekan fee type amount/percentage'
            if (feeType.get(i) == 'AMT') {
                'Verify provision fee pada confins sesuai rule'
                if (WebUI.verifyMatch(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/input_Provision Fee Amount'), 
                        'value').replace(',', ''), fee.get(i), false) == false) {
                    writeReasonFailedVerifRule()
                }
                
                if (feeBhv.get(i) == 'DEF') {
                    'Verify provision fee amount tidak terlock'
                    WebUI.verifyElementNotHasAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/input_Provision Fee Amount'), 
                        'readonly', 1) //Verify provision fee amt & pctg terlock
                } else if (feeBhv.get(i) == 'LOCK') {
                    WebUI.verifyElementHasAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/input_Provision Fee Amount'), 
                        'readonly', 1)

                    WebUI.verifyElementHasAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/input_Provision Fee Percentage'), 
                        'readonly', 1)
					
					CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '10.TabFinancialData',
						37, GlobalVariable.NumofColm - 1, WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/input_Provision Fee Percentage'),'value').replace(" %",""))
					
					CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '10.TabFinancialData',
						38, GlobalVariable.NumofColm - 1, WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/input_Provision Fee Amount'),'value'))
                }
                //Verify provision fee amt & pctg terlock
            } else if (feeType.get(i) == 'PRCNT') {
                'Verify provision fee pada confins sesuai rule'
                if (WebUI.verifyEqual(Double.parseDouble(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/input_Provision Fee Percentage'), 
                            'value').replace(' %', '')), Double.parseDouble(fee.get(i))) == false) {
                    writeReasonFailedVerifRule()
                }
                
                if (feeBhv.get(i) == 'DEF') {
                    'verify provision fee percentage tidak terlock'
                    WebUI.verifyElementNotHasAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/input_Provision Fee Percentage'), 
                        'readonly', 1)
                } else if (feeBhv.get(i) == 'LOCK') {
                    WebUI.verifyElementHasAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/input_Provision Fee Amount'), 
                        'readonly', 1)

                    WebUI.verifyElementHasAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/input_Provision Fee Percentage'), 
                        'readonly', 1)
					
					CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '10.TabFinancialData',
						37, GlobalVariable.NumofColm - 1, WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/input_Provision Fee Percentage'),'value').replace(" %",""))
					CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '10.TabFinancialData',
						38, GlobalVariable.NumofColm - 1, WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/input_Provision Fee Amount'),'value'))
				}
            }
            
            if (feecapType.get(i) == 'AMT') {
                'Verify provision fee capitalized amount'
                if (WebUI.verifyMatch(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/input_Provision Fee Capitalize'), 
                        'value').replace(',', ''), feecap.get(i), false, FailureHandling.OPTIONAL) == false) {
                    writeReasonFailedVerifRule()
                }
            }
        }
    }
}

if (datafilefinancial.getValue(GlobalVariable.NumofColm, 20) == 'No') {
    'input admin fee'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/input_Admin Fee'), 
        datafilefinancial.getValue(GlobalVariable.NumofColm, 21),FailureHandling.OPTIONAL)

    if (WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/input_Admin Fee Capitalize_checkbox'), 
        2, FailureHandling.OPTIONAL)) {
        if (datafilefinancial.getValue(GlobalVariable.NumofColm, 26) == 'Yes') {
            'click admin fee capitalized checkbox'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/input_Admin Fee Capitalize_checkbox'))
        }
    } else {
        if (datafilefinancial.getValue(GlobalVariable.NumofColm, 26) == 'No') {
            'click admin fee capitalized checkbox'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/input_Admin Fee Capitalize_checkbox'))
        }
    }
    
    if (datafilefinancial.getValue(GlobalVariable.NumofColm, 26) == 'Yes') {
        'input admin fee capitalized '
        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/input_Admin Fee Capitalize_'), 
            datafilefinancial.getValue(GlobalVariable.NumofColm, 27))
    }
	
    'input additional admin'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/input_Additional Admin'), 
        datafilefinancial.getValue(GlobalVariable.NumofColm, 22))

    if (WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/input_Additional Admin Capitalize_checkbox'), 
        2, FailureHandling.OPTIONAL)) {
        if (datafilefinancial.getValue(GlobalVariable.NumofColm, 28) == 'Yes') {
            'click additional admin capitalized checkbox'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/input_Additional Admin Capitalize_checkbox'))
        }
    } else {
        if (datafilefinancial.getValue(GlobalVariable.NumofColm, 28) == 'No') {
            'click additional admin capitalized checkbox'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/input_Additional Admin Capitalize_checkbox'))
        }
    }
    
    if (datafilefinancial.getValue(GlobalVariable.NumofColm, 28) == 'Yes') {
        'input additional admin capitalized'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/input_Additional Admin Capitalize'), 
            datafilefinancial.getValue(GlobalVariable.NumofColm, 29))
    }
    
    'input notary fee'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/input_Notary Fee'), 
        datafilefinancial.getValue(GlobalVariable.NumofColm, 23))

    if (WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/input_Notary Fee Capitalize_checkbox'), 
        2, FailureHandling.OPTIONAL)) {
        if (datafilefinancial.getValue(GlobalVariable.NumofColm, 30) == 'Yes') {
            'click notary fee capitalized checkbox'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/input_Notary Fee Capitalize_checkbox'))
        }
    } else {
        if (datafilefinancial.getValue(GlobalVariable.NumofColm, 30) == 'No') {
            'click notary fee capitalized checkbox'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/input_Notary Fee Capitalize_checkbox'))
        }
    }
    
    if (datafilefinancial.getValue(GlobalVariable.NumofColm, 30) == 'Yes') {
        'input lick notary fee capitalized '
        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/input_Notary Fee Capitalize'), 
            datafilefinancial.getValue(GlobalVariable.NumofColm, 31))
    }
    
    'input other fee'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/input_Other Fee'), 
        datafilefinancial.getValue(GlobalVariable.NumofColm, 24))

    if (WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/input_Other Fee Capitalize_checkbox'), 
        2, FailureHandling.OPTIONAL)) {
        if (datafilefinancial.getValue(GlobalVariable.NumofColm, 32) == 'Yes') {
            'click other fee capitalized checkbox'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/input_Other Fee Capitalize_checkbox'))
        }
    } else {
        if (datafilefinancial.getValue(GlobalVariable.NumofColm, 32) == 'No') {
            'click other fee capitalized checkbox'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/input_Other Fee Capitalize_checkbox'))
        }
    }
    
    if (datafilefinancial.getValue(GlobalVariable.NumofColm, 32) == 'Yes') {
        'input other fee capitalized'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/input_Other Fee Capitalize'), 
            datafilefinancial.getValue(GlobalVariable.NumofColm, 33))
    }
    
    'input fiducia fee'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/input_Fiducia Fee'), 
        datafilefinancial.getValue(GlobalVariable.NumofColm, 25))

    if (WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/input_Fiducia Fee Capitalize_checkbox'), 
        2, FailureHandling.OPTIONAL)) {
        if (datafilefinancial.getValue(GlobalVariable.NumofColm, 34) == 'Yes') {
            'click fiducia fee capitalized checkbox'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/input_Fiducia Fee Capitalize_checkbox'))
        }
    } else {
        if (datafilefinancial.getValue(GlobalVariable.NumofColm, 34) == 'No') {
            'click fiducia fee capitalized checkbox'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/input_Fiducia Fee Capitalize_checkbox'))
        }
    }
    
    if (datafilefinancial.getValue(GlobalVariable.NumofColm, 34) == 'Yes') {
        'input fiducia fee capitalized'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/input_Fiducia Fee Capitalize'), 
            datafilefinancial.getValue(GlobalVariable.NumofColm, 35))
    }
    
    if (datafilefinancial.getValue(GlobalVariable.NumofColm, 36) == 'Amount') {
        'click amount radio button'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/div_AmountRadio'))

        'click amount radio button'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/div_AmountRadio'))
    } else if (datafilefinancial.getValue(GlobalVariable.NumofColm, 36) == 'Percentage') {
        'click percentage radio button'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/div_PercentageRadio'))

        'click percentage radio button'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/div_PercentageRadio'))
    }
    
    'select Provision fee calculation base'
    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/select_OTR-DPOTR-DP  Ins Cptlz  Fee Cptlz(Excl Provision)'), 
        datafilefinancial.getValue(GlobalVariable.NumofColm, 37), false)

    if (WebUI.verifyElementNotChecked(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/input_Provision Fee Capitalize_checkbox'), 
        2, FailureHandling.OPTIONAL)) {
        if (datafilefinancial.getValue(GlobalVariable.NumofColm, 40) == 'Yes') {
            'click provision fee capitalized checkbox'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/input_Provision Fee Capitalize_checkbox'))
        }
    } else {
        if (datafilefinancial.getValue(GlobalVariable.NumofColm, 40) == 'No') {
            'click provision fee capitalized checkbox'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/input_Provision Fee Capitalize_checkbox'))
        }
    }
    
    if (datafilefinancial.getValue(GlobalVariable.NumofColm, 40) == 'Yes') {
        'input provision fee capitalized '
        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/input_Provision Fee Capitalize'), 
            datafilefinancial.getValue(GlobalVariable.NumofColm, 41))
    }
    
    if (datafilefinancial.getValue(GlobalVariable.NumofColm, 36) == 'Percentage') {
        sendKeys(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/input_Provision Fee Percentage'))

        'input provision fee percentage'
        WebUI.sendKeys(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/input_Provision Fee Percentage'), 
            Keys.chord(Keys.RIGHT, datafilefinancial.getValue(GlobalVariable.NumofColm, 38)), FailureHandling.OPTIONAL)
    } else if (datafilefinancial.getValue(GlobalVariable.NumofColm, 36) == 'Amount') {
        sendKeys(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/input_Provision Fee Amount'))

        WebUI.sendKeys(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/input_Provision Fee Amount'), 
            Keys.chord(Keys.RIGHT, datafilefinancial.getValue(GlobalVariable.NumofColm, 39)))
    }

}

if(GlobalVariable.Role=="Testing" && GlobalVariable.CheckRulePersonal == 'Yes' && GlobalVariable.FirstTimeEntry == "Yes"){
	
	ArrayList<String> result = new ArrayList<String>()
	
	'Hashmap untuk ambil nilai additional premi rate, sum insured amount, dan main coverage typenya dari rule excel berdasarkan condition'
	result = CustomKeywords.'financialData.verifRate.verifyFinancialRate'(sqlConnectionLOS, appNo)
	
	'Verify default effective rate'
	if(WebUI.verifyEqual(Double.parseDouble(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/input_Effective Rate'),'value').replace(" %","")),Double.parseDouble(result.get(1)))==false){
		writeReasonFailedVerifRule()
	}
	
	'Verify default supplier rate'
	if(WebUI.verifyEqual(Double.parseDouble(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/input_Supplier Rate'),'value').replace(" %","")),Double.parseDouble(result.get(0)))==false){
		writeReasonFailedVerifRule()
	}
	
	'Verify def/lock effective rate'
	if(result.get(2)=="LOCK"){
		'Verify effective rate terlock'
		if(WebUI.verifyElementHasAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/input_Effective Rate'),'disabled',1)==false){
			writeReasonFailedVerifRule()
		}
	}
	else if(result.get(2)=="DEF"){
		'Verify effetcive rate tidak terlock/dapat diinput'
		if(WebUI.verifyElementNotHasAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/input_Effective Rate'),'disabled',1)==false){
			writeReasonFailedVerifRule()
		}
	}
}




if (datafilefinancial.getValue(GlobalVariable.NumofColm, 43).length() > 1) {
    'select Rate type'
    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/select_RateType'), 
        datafilefinancial.getValue(GlobalVariable.NumofColm, 43), false, FailureHandling.OPTIONAL)

    if (datafilefinancial.getValue(GlobalVariable.NumofColm, 43) == 'Effective Rate') {
        'input effective rate'
        sendKeys(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/input_Effective Rate'))

        WebUI.sendKeys(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/input_Effective Rate'), 
            Keys.chord(Keys.RIGHT, datafilefinancial.getValue(GlobalVariable.NumofColm, 44)), FailureHandling.OPTIONAL)
    } else if (datafilefinancial.getValue(GlobalVariable.NumofColm, 43) == 'Flat Rate') {
        'input flat rate'
        sendKeys(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/input_Flat Rate'))

        WebUI.sendKeys(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/input_Flat Rate'), 
            Keys.chord(Keys.RIGHT, datafilefinancial.getValue(GlobalVariable.NumofColm, 45)), FailureHandling.OPTIONAL)
    }
    
    GlobalVariable.Effectiverate = WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/input_Effective Rate'), 
        'value', FailureHandling.OPTIONAL)

    GlobalVariable.Flatrate = WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/input_Flat Rate'), 
        'value', FailureHandling.OPTIONAL)
}

'Pengecekan supplier rate tidak terlock'
if(WebUI.verifyElementNotHasAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/input_Supplier Rate'),'disabled',1,FailureHandling.OPTIONAL)){
	'Input supplier rate'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/input_Supplier Rate'),datafilefinancial.getValue(GlobalVariable.NumofColm, 50))
}

if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabApplicationData').getValue(
    GlobalVariable.NumofColm, 18) == 'Arrear') {
    if (datafilefinancial.getValue(GlobalVariable.NumofColm, 46) == 'Yes') {
        'input grace period'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/input_Grace Period'), 
            datafilefinancial.getValue(GlobalVariable.NumofColm, 47))

        'select method'
        WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/select_--Select--Interest OnlyRoll Over'), 
            datafilefinancial.getValue(GlobalVariable.NumofColm, 48), false)
    }
	else if (datafilefinancial.getValue(GlobalVariable.NumofColm, 46) == 'No') {
		'input grace period'
		WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/input_Grace Period'),
			"0")

		'select method'
		WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/select_--Select--Interest OnlyRoll Over'),
			"-Select One-", false)
	}
}

'click button calculate'
WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/button_Calculate'))

'click button calculate'
WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/button_Calculate'))

if (WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/td_InterestAmount'), 
    5, FailureHandling.OPTIONAL)) {
    'click button cancel'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabAssetData/button_Cancel'))

    CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '10.TabFinancialData', 
        0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)

    CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '10.TabFinancialData', 
        1, GlobalVariable.NumofColm - 1, GlobalVariable.StatusReasonCalculateGagal)

    GlobalVariable.FlagFailed = 1
}

'Pengecekan jika tdp at mf tidak kosong'
if (datafilefinancial.getValue(GlobalVariable.NumofColm, 49).length() > 0) {
    'input tdp at mf'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/input_TDP Paid at MF'), 
        datafilefinancial.getValue(GlobalVariable.NumofColm, 49))

    'click button calculate'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/button_Calculate'))
}

if (GlobalVariable.Role == 'Testing') {
    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabFinancialDataVerif'), 
        [:], FailureHandling.CONTINUE_ON_FAILURE)
}

WebUI.delay(5)

'click button save'
WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/button_Save'))

Integer iscompleteMandatory = Integer.parseInt(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData').getValue(
        GlobalVariable.NumofColm, 4))

if (iscompleteMandatory == 0 && GlobalVariable.FlagFailed==0) {
    'cek alert'
    GlobalVariable.FlagFailed = CustomKeywords.'checkSaveProcess.checkSaveProcess.checkAlert'(GlobalVariable.NumofColm, '10.TabFinancialData')
}

if (GlobalVariable.FlagFailed == 0) {
    'check save process write to excel'
    CustomKeywords.'checkSaveProcess.checkSaveProcess.checkStatus'(iscompleteMandatory, findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabTermConditionData/td_Checkbox'), 
        GlobalVariable.NumofColm, '10.TabFinancialData')

    if (iscompleteMandatory == 0) {
        errorValObject = findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData/div_errorvalidation')

        'cek validasi'
        CustomKeywords.'checkSaveProcess.checkSaveProcess.checkValidasi'(errorValObject, GlobalVariable.NumofColm, '10.TabFinancialData')
    }
}

if (GlobalVariable.Role == 'Testing' && GlobalVariable.CheckVerifStoreDBPersonal=="Yes") {
	'call test case subsidy store db verif'
	WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabSubsidyStoreDBVerif'),
			[:], FailureHandling.CONTINUE_ON_FAILURE)


	'call test case financial datastore db verif'
	WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabFinancialDataStoreDBVerif'),
			[:], FailureHandling.CONTINUE_ON_FAILURE)

}
if (WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/ApplicationCurrentStep')), 
    'FINANCIAL DATA', false, FailureHandling.OPTIONAL)) {
    'click button cancel'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabAssetData/button_Cancel'))
}

def writeReasonFailedVerifRule() {
    'write to excel failed'
    CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '10.TabFinancialData', 
        0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)

    'Write To Excel GlobalVariable.StatusReason'
    CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '10.TabFinancialData', 
        1, GlobalVariable.NumofColm - 1, GlobalVariable.ReasonFailedVerifyRule)

    GlobalVariable.FlagFailed = 1
}

def sendKeys(TestObject object) {
    WebUI.sendKeys(object, Keys.chord(Keys.CONTROL, 'a'), FailureHandling.OPTIONAL)

    WebUI.sendKeys(object, Keys.chord(Keys.BACK_SPACE), FailureHandling.OPTIONAL)

    WebUI.sendKeys(object, Keys.chord(Keys.HOME), FailureHandling.OPTIONAL)
}

public checkVerifyEqualOrMatch(Boolean isMatch){
	if(isMatch==false && GlobalVariable.FlagFailed==0){
		(new writetoexcel.writeToExcel()).writeToExcelFunction(GlobalVariable.DataFilePath, '10.TabFinancialData',
				0, GlobalVariable.NumofColm-1, GlobalVariable.StatusFailed)

		(new writetoexcel.writeToExcel()).writeToExcelFunction(GlobalVariable.DataFilePath, '10.TabFinancialData',
				1, GlobalVariable.NumofColm-1, GlobalVariable.ReasonFailedVerifyEqualOrMatch)

		GlobalVariable.FlagFailed=1
	}
}
