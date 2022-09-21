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
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import groovy.sql.Sql as Sql

'get value porfession'
String Profession = findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabManagementShareholder').getValue(
    GlobalVariable.NumofFamily, 26)

'get value posittion slik'
String PositionSLIK = findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabManagementShareholder').getValue(
    GlobalVariable.NumofFamily, 28)

'get value job position'
String JobPosition = findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabManagementShareholder').getValue(
    GlobalVariable.NumofFamily, 37)

'get value department aml'
String DepartmentAML = findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabManagementShareholder').getValue(
    GlobalVariable.NumofFamily, 44)

'get value authority aml'
String AuthorityAML = findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabManagementShareholder').getValue(
    GlobalVariable.NumofFamily, 46)

String servername = findTestData('Login/Login').getValue(1, 9)

String instancename = findTestData('Login/Login').getValue(2, 9)

String username = findTestData('Login/Login').getValue(3, 9)

String password = findTestData('Login/Login').getValue(4, 9)

String database = findTestData('Login/Login').getValue(5, 9)

String driverclassname = findTestData('Login/Login').getValue(6, 9)

String url = (((servername + ';instanceName=') + instancename) + ';databaseName=') + database

'connect DB'
Sql sqlconnection = CustomKeywords.'dbconnection.connectDB.connect'(url, username, password, driverclassname)

String result = CustomKeywords.'dbconnection.CustomerDataVerif.MSStoreDataPersonal'(sqlconnection, findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabManagementShareholder').getValue(
        GlobalVariable.NumofFamily, 12)).replace('HEADER:', '').replace('[', '').replace(']', '')

resultarray = result.split(', ')

'ganti value null > "" (String kosong)'
for (i = 0; i <= (resultarray.size() - 1); i++) {
    if ((resultarray[i]).equalsIgnoreCase('null')) {
        (resultarray[i]) = ''
    }
    
    if ((resultarray[i]).equalsIgnoreCase('TRUE')) {
        (resultarray[i]) = 'Yes'
    } else if ((resultarray[i]).equalsIgnoreCase('FALSE')) {
        (resultarray[i]) = 'No'
    }
}

'verify shareholder legal name'
WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabManagementShareholder').getValue(GlobalVariable.NumofFamily, 
        19).toUpperCase(), (resultarray[0]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify birth place'
WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabManagementShareholder').getValue(GlobalVariable.NumofFamily, 
        20).toUpperCase(), (resultarray[1]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify id type'
WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabManagementShareholder').getValue(GlobalVariable.NumofFamily, 
        21).toUpperCase(), (resultarray[2]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify id expired date'
WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabManagementShareholder').getValue(GlobalVariable.NumofFamily, 
        22).toUpperCase(), (resultarray[3]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify marital status'
WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabManagementShareholder').getValue(GlobalVariable.NumofFamily, 
        23).toUpperCase(), (resultarray[4]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify mobile phone'
WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabManagementShareholder').getValue(GlobalVariable.NumofFamily, 
        24).toUpperCase(), (resultarray[5]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify customer model'
WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabManagementShareholder').getValue(GlobalVariable.NumofFamily, 
        25).toUpperCase(), (resultarray[6]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify profession code no value'
if (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabManagementShareholder').getValue(GlobalVariable.NumofFamily, 
    26).equalsIgnoreCase('#N/A')) {
    'set profession code menjadi string kosong'
    Profession = ''
}

'verify profession code'
WebUI.verifyMatch(Profession.toUpperCase(), (resultarray[7]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify positionSLIK no value'
if (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabManagementShareholder').getValue(GlobalVariable.NumofFamily, 
    28).equalsIgnoreCase('#N/A')) {
    'set positionslik menjadi string kosong'
    PositionSLIK = ''
}

'verify positionslik'
WebUI.verifyMatch(PositionSLIK.toUpperCase(), (resultarray[8]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify employee establishment date'
WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabManagementShareholder').getValue(GlobalVariable.NumofFamily, 
        30).toUpperCase(), (resultarray[9]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify gender'
WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabManagementShareholder').getValue(GlobalVariable.NumofFamily, 
        31).toUpperCase(), (resultarray[10]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify birth date'
WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabManagementShareholder').getValue(GlobalVariable.NumofFamily, 
        32).toUpperCase(), (resultarray[11]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify id no'
WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabManagementShareholder').getValue(GlobalVariable.NumofFamily, 
        33).toUpperCase(), (resultarray[12]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify tax id no'
WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabManagementShareholder').getValue(GlobalVariable.NumofFamily, 
        34).toUpperCase(), (resultarray[13]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify mother maiden name'
WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabManagementShareholder').getValue(GlobalVariable.NumofFamily, 
        35).toUpperCase(), (resultarray[14]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify email'
WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabManagementShareholder').getValue(GlobalVariable.NumofFamily, 
        36).toUpperCase(), (resultarray[15]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify job position code no value'
if (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabManagementShareholder').getValue(GlobalVariable.NumofFamily, 
    37).equalsIgnoreCase('#N/A')) {
    'set job position code menjadi string kosong'
    JobPosition = ''
}

'verify job profession'
WebUI.verifyMatch(JobPosition.toUpperCase(), (resultarray[16]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify share percent'
WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabManagementShareholder').getValue(GlobalVariable.NumofFamily, 
        39).toUpperCase(), (resultarray[17]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify is owner'
WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabManagementShareholder').getValue(GlobalVariable.NumofFamily, 
        40).toUpperCase(), (resultarray[18]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify is active'
WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabManagementShareholder').getValue(GlobalVariable.NumofFamily, 
        41).toUpperCase(), (resultarray[19]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify is signer'
WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabManagementShareholder').getValue(GlobalVariable.NumofFamily, 
        42).toUpperCase(), (resultarray[20]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify department aml no value'
if (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabManagementShareholder').getValue(GlobalVariable.NumofFamily, 
    44).equalsIgnoreCase('#N/A')) {
    'set department aml menjadi string kosong'
    DepartmentAML = ''
}

'verify department aml'
WebUI.verifyMatch(DepartmentAML.toUpperCase(), (resultarray[21]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify authority aml no value'
if (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabManagementShareholder').getValue(GlobalVariable.NumofFamily, 
    46).equalsIgnoreCase('#N/A')) {
    'set authority aml menjadi string kosong'
    AuthorityAML = ''
}

'verify authority aml'
WebUI.verifyMatch(AuthorityAML.toUpperCase(), (resultarray[22]).toUpperCase(), false, FailureHandling.OPTIONAL)

'verify is addres copy atau tidak'
if (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabManagementShareholder').getValue(GlobalVariable.NumofFamily, 
    71).length() > 0) {
    'verify address copy dari customer'
    WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofFamily, 
            24).toUpperCase(), (resultarray[23]).toUpperCase(), false, FailureHandling.OPTIONAL)

    'verify RT copy dari customer'
    WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofFamily, 
            25).toUpperCase(), (resultarray[24]).toUpperCase(), false, FailureHandling.OPTIONAL)

    'verify RW copy dari customer'
    WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofFamily, 
            26).toUpperCase(), (resultarray[25]).toUpperCase(), false, FailureHandling.OPTIONAL)

    'verify Zipcode copy dari customer'
    WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofFamily, 
            27).toUpperCase(), (resultarray[26]).toUpperCase(), false, FailureHandling.OPTIONAL)

    'verify kecamatan copy dari customer'
    WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofFamily, 
            28).toUpperCase(), (resultarray[27]).toUpperCase(), false, FailureHandling.OPTIONAL)

    'verify kelurahan copy dari customer'
    WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofFamily, 
            29).toUpperCase(), (resultarray[28]).toUpperCase(), false, FailureHandling.OPTIONAL)

    'verify kota copy dari customer'
    WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofFamily, 
            30).toUpperCase(), (resultarray[29]).toUpperCase(), false, FailureHandling.OPTIONAL)

    'verify ownership copy dari customer'
    WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(GlobalVariable.NumofFamily, 
            31).toUpperCase(), (resultarray[30]).toUpperCase(), false, FailureHandling.OPTIONAL)
} else {
    'verify address'
    WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabManagementShareholder').getValue(
            GlobalVariable.NumofFamily, 71).toUpperCase(), (resultarray[23]).toUpperCase(), false, FailureHandling.OPTIONAL)

    'verify RT'
    WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabManagementShareholder').getValue(
            GlobalVariable.NumofFamily, 72).toUpperCase(), (resultarray[24]).toUpperCase(), false, FailureHandling.OPTIONAL)

    'verify RW'
    WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabManagementShareholder').getValue(
            GlobalVariable.NumofFamily, 73).toUpperCase(), (resultarray[25]).toUpperCase(), false, FailureHandling.OPTIONAL)

    'verify Zipcode'
    WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabManagementShareholder').getValue(
            GlobalVariable.NumofFamily, 74).toUpperCase(), (resultarray[26]).toUpperCase(), false, FailureHandling.OPTIONAL)

    'verify kecamatan'
    WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabManagementShareholder').getValue(
            GlobalVariable.NumofFamily, 75).toUpperCase(), (resultarray[27]).toUpperCase(), false, FailureHandling.OPTIONAL)

    'verify kelurahan'
    WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabManagementShareholder').getValue(
            GlobalVariable.NumofFamily, 76).toUpperCase(), (resultarray[28]).toUpperCase(), false, FailureHandling.OPTIONAL)

    'verify kota'
    WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabManagementShareholder').getValue(
            GlobalVariable.NumofFamily, 77).toUpperCase(), (resultarray[29]).toUpperCase(), false, FailureHandling.OPTIONAL)

    'verify ownership'
    WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabManagementShareholder').getValue(
            GlobalVariable.NumofFamily, 78).toUpperCase(), (resultarray[30]).toUpperCase(), false, FailureHandling.OPTIONAL)
}

println(resultarray)

