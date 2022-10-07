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

String appno = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail - Personal/appnolabel'))

String custname = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail - Personal/CustomerNameDetail'))

String result

String jobaddress

String prevjobaddress

String othaddress

if (WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/CustomerModelCode')) == 
'EMPLOYEE') {
    result = CustomKeywords.'dbconnection.CustomerDataVerif.NAP4JobdataEmployeePersonal'(sqlconnection, appno, custname).replace(
        'HEADER:', '').replace('[', '').replace(']', '')

    jobaddress = CustomKeywords.'dbconnection.CustomerDataVerif.NAP4JobdataAddress'(sqlconnection, appno, custname).replace(
        'HEADER:', '').replace('[', '').replace(']', '')

    prevjobaddress = CustomKeywords.'dbconnection.CustomerDataVerif.NAP4PrevJobAddress'(sqlconnection, appno, custname).replace(
        'HEADER:', '').replace('[', '').replace(']', '')

    othaddress = CustomKeywords.'dbconnection.CustomerDataVerif.NAP4OtherBizAddress'(sqlconnection, appno, custname).replace(
        'HEADER:', '').replace('[', '').replace(']', '')
} else if (WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/CustomerModelCode')) == 
'PROFESSIONAL') {
    

    jobaddress = CustomKeywords.'dbconnection.CustomerDataVerif.NAP4JobdataAddress'(sqlconnection, appno, custname).replace(
        'HEADER:', '').replace('[', '').replace(']', '')

    prevjobaddress = CustomKeywords.'dbconnection.CustomerDataVerif.NAP4PrevJobAddress'(sqlconnection, appno, custname).replace(
        'HEADER:', '').replace('[', '').replace(']', '')

    othaddress = CustomKeywords.'dbconnection.CustomerDataVerif.NAP4OtherBizAddress'(sqlconnection, appno, custname).replace(
        'HEADER:', '').replace('[', '').replace(']', '')
} else if (WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/CustomerModelCode')) == 
'NON PROFESSIONAL') {
    result = CustomKeywords.'dbconnection.CustomerDataVerif.NAP4JobdataNonProfPersonal'(sqlconnection, appno, custname).replace(
        'HEADER:', '').replace('[', '').replace(']', '')
} else {
    

    jobaddress = CustomKeywords.'dbconnection.CustomerDataVerif.NAP4JobdataAddress'(sqlconnection, appno, custname).replace(
        'HEADER:', '').replace('[', '').replace(']', '')

    prevjobaddress = CustomKeywords.'dbconnection.CustomerDataVerif.NAP4PrevJobAddress'(sqlconnection, appno, custname).replace(
        'HEADER:', '').replace('[', '').replace(']', '')

    othaddress = CustomKeywords.'dbconnection.CustomerDataVerif.NAP4OtherBizAddress'(sqlconnection, appno, custname).replace(
        'HEADER:', '').replace('[', '').replace(']', '')
}

resultarray = result.split(', ')

'ganti value null > "" (String kosong)'
for (i = 0; i <= (resultarray.size() - 1); i++) {
    if ((resultarray[i]).equalsIgnoreCase('null')) {
        (resultarray[i]) = ''
    } else if ((resultarray[i]).equalsIgnoreCase('true')) {
        (resultarray[i]) = 'Yes'
    } else if ((resultarray[i]).equalsIgnoreCase('false')) {
        (resultarray[i]) = 'No'
    }
}

if(WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/CustomerModelCode')) ==
	'EMPLOYEE' || WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/CustomerModelCode')) ==
	'PROFESSIONAL'){
	
	joaddressarray = jobaddress.split(', ')
	
	prevjobaddressarray = prevjobaddress.split(', ')
	
	othaddressarray = othaddress.split(', ')
	
'ganti value null > "" (String kosong)'
for (i = 0; i <= (joaddressarray.size() - 1); i++) {
    if ((joaddressarray[i]).equalsIgnoreCase('null')) {
        (joaddressarray[i]) = ''
    } else if ((joaddressarray[i]).equalsIgnoreCase('true')) {
        (joaddressarray[i]) = 'Yes'
    } else if ((joaddressarray[i]).equalsIgnoreCase('false')) {
        (joaddressarray[i]) = 'No'
    }
}

'ganti value null > "" (String kosong)'
for (i = 0; i <= (prevjobaddressarray.size() - 1); i++) {
    if ((prevjobaddressarray[i]).equalsIgnoreCase('null')) {
        (prevjobaddressarray[i]) = ''
    } else if ((prevjobaddressarray[i]).equalsIgnoreCase('true')) {
        (prevjobaddressarray[i]) = 'Yes'
    } else if ((prevjobaddressarray[i]).equalsIgnoreCase('false')) {
        (prevjobaddressarray[i]) = 'No'
    }
}

'ganti value null > "" (String kosong)'
for (i = 0; i <= (othaddressarray.size() - 1); i++) {
    if ((othaddressarray[i]).equalsIgnoreCase('null')) {
        (othaddressarray[i]) = ''
    } else if ((othaddressarray[i]).equalsIgnoreCase('true')) {
        (othaddressarray[i]) = 'Yes'
    } else if ((othaddressarray[i]).equalsIgnoreCase('false')) {
        (othaddressarray[i]) = 'No'
    }
}
}
int arrayindex = 0

int JobAddressindex = 0

int PrevJobindex = 0

int OtherAddressindex = 0

if (WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/CustomerModelCode')) == 
'EMPLOYEE') {
    'verify proffession code'
    WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(GlobalVariable.NumofVerifStore, 14).toUpperCase(), (resultarray[
        arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

    'verify job position'
    WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(GlobalVariable.NumofVerifStore, 16).toUpperCase(), (resultarray[
        arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

    'verify job status'
    WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(GlobalVariable.NumofVerifStore, 17).toUpperCase(), (resultarray[
        arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

    'verify is internal employee'
    WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(GlobalVariable.NumofVerifStore, 18).toUpperCase(), (resultarray[
        arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

    'verify is well known company'
    WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(GlobalVariable.NumofVerifStore, 19).toUpperCase(), (resultarray[
        arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

    'verify company name'
    WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(GlobalVariable.NumofVerifStore, 20).toUpperCase(), (resultarray[
        arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

    'verify industry type'
    WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(GlobalVariable.NumofVerifStore, 21).toUpperCase(), (resultarray[
        arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

    'verify company scale'
    WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(GlobalVariable.NumofVerifStore, 23).toUpperCase(), (resultarray[
        arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

    'verify start working'
    WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(GlobalVariable.NumofVerifStore, 24).toUpperCase(), (resultarray[
        arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

    'verify job title'
    WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(GlobalVariable.NumofVerifStore, 25).toUpperCase(), (resultarray[
        arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

    'verify job title'
    WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(GlobalVariable.NumofVerifStore, 26).toUpperCase(), (resultarray[
        arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)
} else if (WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/CustomerModelCode')) == 
'PROFESSIONAL') {
} else if (WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/CustomerModelCode')) == 
'NON PROFESSIONAL') {
    'verify profession code'
    WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(GlobalVariable.NumofVerifStore, 38).toUpperCase(), (resultarray[
        arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

    'verify job title'
    WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(GlobalVariable.NumofVerifStore, 40).toUpperCase(), (resultarray[
        arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)
} else {
}

if(WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/CustomerModelCode')) == 
'EMPLOYEE' || WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP4-CustomerDataCompletion/CustomerPersonal/JobDataEmployee - Personal/CustomerModelCode')) == 
'PROFESSIONAL'){
int row = 55
for(i = 0; i < joaddressarray.size(); i++){
'verify Job address'
WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(GlobalVariable.NumofVerifStore, row++).toUpperCase(), (joaddressarray[
    i]).toUpperCase(), false, FailureHandling.OPTIONAL)
}

'verify Previous company name'
WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(GlobalVariable.NumofVerifStore, 76).toUpperCase(), (prevjobaddressarray[
    PrevJobindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify Previous employment date'
WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(GlobalVariable.NumofVerifStore, 77).toUpperCase(), (prevjobaddressarray[
    PrevJobindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

row = 79
for(i = 2; i < prevjobaddressarray.size(); i++){
'verify Job address'
WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(GlobalVariable.NumofVerifStore, row++).toUpperCase(), (prevjobaddressarray[
    i]).toUpperCase(), false, FailureHandling.OPTIONAL)
}

'verify Other business'
WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(GlobalVariable.NumofVerifStore, 100).toUpperCase(), (othaddressarray[
    OtherAddressindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify Other business type'
WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(GlobalVariable.NumofVerifStore, 101).toUpperCase(), (othaddressarray[
    OtherAddressindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify Other business industry type'
WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(GlobalVariable.NumofVerifStore, 102).toUpperCase(), (othaddressarray[
    OtherAddressindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify Other job position'
WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(GlobalVariable.NumofVerifStore, 103).toUpperCase(), (othaddressarray[
    OtherAddressindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify establishment date'
WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(GlobalVariable.NumofVerifStore, 104).toUpperCase(), (othaddressarray[
    OtherAddressindex++]).toUpperCase(), false, FailureHandling.OPTIONAL)

row = 106
for(i = 5 ; i < othaddressarray.size(); i++){
'verify Other Job address'
WebUI.verifyMatch(GlobalVariable.findDataFile.getValue(GlobalVariable.NumofVerifStore, row++).toUpperCase(), (othaddressarray[
    i]).toUpperCase(), false, FailureHandling.OPTIONAL)

}

}