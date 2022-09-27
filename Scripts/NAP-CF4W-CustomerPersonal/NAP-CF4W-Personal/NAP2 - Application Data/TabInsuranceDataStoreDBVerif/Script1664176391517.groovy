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
import groovy.sql.Sql as Sql
import internal.GlobalVariable as GlobalVariable

String servername = findTestData('Login/Login').getValue(1, 9)

String instancename = findTestData('Login/Login').getValue(2, 9)

String username = findTestData('Login/Login').getValue(3, 9)

String password = findTestData('Login/Login').getValue(4, 9)

String database = findTestData('Login/Login').getValue(5, 9)

String driverclassname = findTestData('Login/Login').getValue(6, 9)

String url = (((servername + ';instanceName=') + instancename) + ';databaseName=') + database

'connect DB'
Sql sqlconnection = CustomKeywords.'dbconnection.connectDB.connect'(url, username, password, driverclassname)

String insuredBy = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
    GlobalVariable.NumofColm, 12)

int arrayindex = 0

ArrayList<String> arraysuminsured = new ArrayList<>()

ArrayList<String> arrayaddpremi = new ArrayList<>()


'Verifikasi nilai insured by'
if (insuredBy == 'Customer') {
    String result = CustomKeywords.'dbconnection.CustomerDataVerif.NAP2InsuranceCStoreDB'(sqlconnection, findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
            GlobalVariable.NumofColm, 13)).replace('HEADER:', '').replace('[', '').replace(']', '')

    resultarray = result.split(', ')

    'ganti value null > "" (String kosong)'
    for (i = 0; i <= (resultarray.size() - 1); i++) {
        if ((resultarray[i]).equalsIgnoreCase('null')) {
            (resultarray[i]) = ''
        } else if ((resultarray[i]) == 'true') {
            (resultarray[i]) = 'Yes'
        } else if ((resultarray[i]) == 'false') {
            (resultarray[i]) = 'No'
        }
    }
	
	'verify insco branch name'
	WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
			GlobalVariable.NumofColm, 14).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

	'verify coverage amount'
	WebUI.verifyEqual(Integer.parseInt(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
			GlobalVariable.NumofColm, 15)), Integer.parseInt((resultarray[arrayindex++])), FailureHandling.OPTIONAL)

	'verify policy no'
	WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
			GlobalVariable.NumofColm, 16).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

	'verify policy name'
	WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
			GlobalVariable.NumofColm, 17).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

	'verify start date'
	WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
			GlobalVariable.NumofColm, 18).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

	'verify end date'
	WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
			GlobalVariable.NumofColm, 19).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

	'verify insurance notes'
	WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
			GlobalVariable.NumofColm, 20).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)
} else if (insuredBy == 'Customer - Multifinance') {
    String result = CustomKeywords.'dbconnection.CustomerDataVerif.NAP2InsuranceCMStoreDB'(sqlconnection, findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
            GlobalVariable.NumofColm, 13)).replace('HEADER:', '').replace('[', '').replace(']', '')

    resultarray = result.split(', ')

    'ganti value null > "" (String kosong)'
    for (i = 0; i <= (resultarray.size() - 1); i++) {
        if ((resultarray[i]).equalsIgnoreCase('null')) {
            (resultarray[i]) = ''
        } else if ((resultarray[i]) == 'true') {
            (resultarray[i]) = 'Yes'
        } else if ((resultarray[i]) == 'false') {
            (resultarray[i]) = 'No'
        }
    }
    
 

    'verify insco branch name'
    WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
            GlobalVariable.NumofColm, 14).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

    'verify coverage amount'
    WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
            GlobalVariable.NumofColm, 15).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

    'verify policy no'
    WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
            GlobalVariable.NumofColm, 16).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

    'verify policy name'
    WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
            GlobalVariable.NumofColm, 17).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

    'verify start date'
    WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
            GlobalVariable.NumofColm, 18).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

    'verify end date'
    WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
            GlobalVariable.NumofColm, 19).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

    'verify insurance notes'
    WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
            GlobalVariable.NumofColm, 20).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

    'verify asset region'
    WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
            GlobalVariable.NumofColm, 22).toUpperCase().replace(',', ''), (resultarray[arrayindex++]).toUpperCase(), false, 
        FailureHandling.OPTIONAL)

    'verify coverage amount'
    WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
            GlobalVariable.NumofColm, 23).toUpperCase().replace(',', ''), (resultarray[arrayindex++]).toUpperCase(), false, 
        FailureHandling.OPTIONAL)

    'verify cover period'
    WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
            GlobalVariable.NumofColm, 24).toUpperCase().replace(',', ''), (resultarray[arrayindex++]).toUpperCase(), false, 
        FailureHandling.OPTIONAL)

    'verify payment type'
    WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
            GlobalVariable.NumofColm, 25).toUpperCase().replace(',', ''), (resultarray[arrayindex++]).toUpperCase(), false, 
        FailureHandling.OPTIONAL)

    'verify insco branch name'
    WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
            GlobalVariable.NumofColm, 26).toUpperCase().replace(',', ''), (resultarray[arrayindex++]).toUpperCase(), false, 
        FailureHandling.OPTIONAL)

    'verify insurance note'
    WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
            GlobalVariable.NumofColm, 27).toUpperCase().replace(',', ''), (resultarray[arrayindex++]).toUpperCase(), false, 
        FailureHandling.OPTIONAL)

    'verify insurance length'
    WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
            GlobalVariable.NumofColm, 28).toUpperCase().replace(',', ''), (resultarray[arrayindex++]).toUpperCase(), false, 
        FailureHandling.OPTIONAL)

    'verify admin fee'
    WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
            GlobalVariable.NumofColm, 31).toUpperCase().replace(',', ''), (resultarray[arrayindex++]).toUpperCase(), false, 
        FailureHandling.OPTIONAL)

    'verify customer stampduty fee'
    WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
            GlobalVariable.NumofColm, 32).toUpperCase().replace(',', ''), (resultarray[arrayindex++]).toUpperCase(), false, 
        FailureHandling.OPTIONAL)

    'verify main coverage'
    WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
            GlobalVariable.NumofColm, 34).toUpperCase().replace(',', ''), (resultarray[arrayindex++]).toUpperCase(), false, 
        FailureHandling.OPTIONAL)

    println(resultarray)
} else if (insuredBy == 'Multifinance') {
    String result = CustomKeywords.'dbconnection.CustomerDataVerif.NAP2InsuranceMStoreDB'(sqlconnection, findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
            GlobalVariable.NumofColm, 13)).replace('HEADER:', '').replace('[', '').replace(']', '')

    resultarray = result.split(', ')

    'ganti value null > "" (String kosong)'
    for (i = 0; i <= (resultarray.size() - 1); i++) {
        if ((resultarray[i]).equalsIgnoreCase('null')) {
            (resultarray[i]) = ''
        } else if ((resultarray[i]) == 'true') {
            (resultarray[i]) = 'Yes'
        } else if ((resultarray[i]) == 'false') {
            (resultarray[i]) = 'No'
        }
    }
	
	'verify asset region'
	WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
			GlobalVariable.NumofColm, 22).toUpperCase().replace(',', ''), (resultarray[arrayindex++]).toUpperCase(), false,
		FailureHandling.OPTIONAL)

	'verify coverage amount'
	WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
			GlobalVariable.NumofColm, 23).toUpperCase().replace(',', ''), (resultarray[arrayindex++]).toUpperCase(), false,
		FailureHandling.OPTIONAL)

	'verify cover period'
	WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
			GlobalVariable.NumofColm, 24).toUpperCase().replace(',', ''), (resultarray[arrayindex++]).toUpperCase(), false,
		FailureHandling.OPTIONAL)

	'verify payment type'
	WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
			GlobalVariable.NumofColm, 25).toUpperCase().replace(',', ''), (resultarray[arrayindex++]).toUpperCase(), false,
		FailureHandling.OPTIONAL)

	'verify insco branch name'
	WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
			GlobalVariable.NumofColm, 26).toUpperCase().replace(',', ''), (resultarray[arrayindex++]).toUpperCase(), false,
		FailureHandling.OPTIONAL)

	'verify insurance note'
	WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
			GlobalVariable.NumofColm, 27).toUpperCase().replace(',', ''), (resultarray[arrayindex++]).toUpperCase(), false,
		FailureHandling.OPTIONAL)

	'verify insurance length'
	WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
			GlobalVariable.NumofColm, 28).toUpperCase().replace(',', ''), (resultarray[arrayindex++]).toUpperCase(), false,
		FailureHandling.OPTIONAL)

	'verify admin fee'
	WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
			GlobalVariable.NumofColm, 31).toUpperCase().replace(',', ''), (resultarray[arrayindex++]).toUpperCase(), false,
		FailureHandling.OPTIONAL)

	'verify customer stampduty fee'
	WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
			GlobalVariable.NumofColm, 32).toUpperCase().replace(',', ''), (resultarray[arrayindex++]).toUpperCase(), false,
		FailureHandling.OPTIONAL)

	'verify main coverage'
	WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
			GlobalVariable.NumofColm, 34).toUpperCase().replace(',', ''), (resultarray[arrayindex++]).toUpperCase(), false,
		FailureHandling.OPTIONAL)
	
	'Verify is capitalized'
	WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
		GlobalVariable.NumofColm, 84).toUpperCase().replace(',', ''), (resultarray[arrayindex++]).toUpperCase(), false,
	FailureHandling.OPTIONAL)
	
	'verify insurance capitalized amount'
	WebUI.verifyMatch(GlobalVariable.InsuranceCapitalizeAmount.replace('.00','').replace(',',''), (resultarray[arrayindex++]).toUpperCase(), false,
	FailureHandling.OPTIONAL)
	
	arrayindex = 0
	
	String resultCVG = CustomKeywords.'dbconnection.CustomerDataVerif.NAP2InsuranceCVGStoreDB'(sqlconnection, findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
		GlobalVariable.NumofColm, 13)).replace('HEADER:', '').replace('[', '').replace(']', '')
		
		resultarrayCVG = result.split(', ')
		
			'ganti value null > "" (String kosong)'
			for (i = 0; i <= (resultarrayCVG.size() - 1); i++) {
				if ((resultarrayCVG[i]).equalsIgnoreCase('null')) {
					(resultarrayCVG[i]) = ''
				} else if ((resultarrayCVG[i]) == 'true') {
					(resultarrayCVG[i]) = 'Yes'
				} else if ((resultarrayCVG[i]) == 'false') {
					(resultarrayCVG[i]) = 'No'
				}
			}
			
	'verify total main premium'
	WebUI.verifyMatch(GlobalVariable.TotalMainPremium.replace('.00','').replace(',',''), (resultarrayCVG[arrayindex++]).toUpperCase(), false,
		FailureHandling.OPTIONAL)
	
	'verify total additional premium'
	WebUI.verifyMatch(GlobalVariable.TotalAdditionalPremium.replace('.00','').replace(',',''), (resultarrayCVG[arrayindex++]).toUpperCase(), false,
		FailureHandling.OPTIONAL)
	
	'verify total fee'
	WebUI.verifyMatch(GlobalVariable.TotalFee.replace('.00','').replace(',',''), (resultarrayCVG[arrayindex++]).toUpperCase(), false,
		FailureHandling.OPTIONAL)
	
	'verify discount'
	WebUI.verifyMatch(GlobalVariable.Discount.replace('.00','').replace(',',''), (resultarrayCVG[arrayindex++]).toUpperCase(), false,
		FailureHandling.OPTIONAL)
	
	'verify total premium after discount'
	WebUI.verifyEqual(Integer.parseInt(GlobalVariable.TotalInsurance.replace('.00','').replace(',','')) - Integer.parseInt(GlobalVariable.InsuranceCapitalizeAmount.replace('.00','').replace(',','')), 
		Integer.parseInt((resultarrayCVG[arrayindex++])), FailureHandling.OPTIONAL)
}

