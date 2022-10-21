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

String DepartmentAML = findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
    GlobalVariable.NumofGuarantorPersonal, 33)

String AuthorityAML = findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
    GlobalVariable.NumofGuarantorPersonal, 35)

String servername = findTestData('Login/Login').getValue(1, 9)

String instancename = findTestData('Login/Login').getValue(2, 9)

String username = findTestData('Login/Login').getValue(3, 9)

String password = findTestData('Login/Login').getValue(4, 9)

String database = findTestData('Login/Login').getValue(5, 9)

String driverclassname = findTestData('Login/Login').getValue(6, 9)

String url = (((servername + ';instanceName=') + instancename) + ';databaseName=') + database

'connect DB'
Sql sqlconnection = CustomKeywords.'dbconnection.connectDB.connect'(url, username, password, driverclassname)

ArrayList<Boolean> arrayMatch = new ArrayList<>()

String result = CustomKeywords.'dbconnection.CustomerDataVerif.GuarantorDataStoreDBPersonal'(sqlconnection, findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
        GlobalVariable.NumofGuarantorPersonal, 12), findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
        GlobalVariable.NumofGuarantorPersonal, 19)).replace('HEADER:', '').replace('[', '').replace(']', '')

resultarray = result.split(', ')

'ganti value null > "" (String kosong)'
for (i = 0; i <= (resultarray.size() - 1); i++) {
    if ((resultarray[i]).equalsIgnoreCase('null')) {
        (resultarray[i]) = ''
    }
}

int arrayindex = 0

'verify relationship'
arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
        GlobalVariable.NumofGuarantorPersonal, 18).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify guarantor name'
arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
        GlobalVariable.NumofGuarantorPersonal, 19).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify birth place'
arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
        GlobalVariable.NumofGuarantorPersonal, 20).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify id type'
arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
        GlobalVariable.NumofGuarantorPersonal, 21).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify id expired date'
arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
        GlobalVariable.NumofGuarantorPersonal, 22).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify marital status'
arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
        GlobalVariable.NumofGuarantorPersonal, 23).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify mobile phone'
arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
        GlobalVariable.NumofGuarantorPersonal, 24).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify customer model'
arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
        GlobalVariable.NumofGuarantorPersonal, 25).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify gender'
arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
        GlobalVariable.NumofGuarantorPersonal, 26).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify birth date'
arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
        GlobalVariable.NumofGuarantorPersonal, 27).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify id no'
arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
        GlobalVariable.NumofGuarantorPersonal, 28).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify tax id no'
arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
        GlobalVariable.NumofGuarantorPersonal, 29).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify mother maiden name'
arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
        GlobalVariable.NumofGuarantorPersonal, 30).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify email'
arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
        GlobalVariable.NumofGuarantorPersonal, 31).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

//'verify department aml value'
//if (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
//    GlobalVariable.NumofGuarantorPersonal, 33) == '#N/A') {
//    'set department aml value menjadi string kosong'
//    DepartmentAML = ''
//}
//
//'verify department aml'
//WebUI.verifyMatch(DepartmentAML.toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
//
//'verify authority aml value'
//if (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
//    GlobalVariable.NumofGuarantorPersonal, 35) == '#N/A') {
//    'set authority aml value menjadi kosong'
//    AuthorityAML = ''
//}
//
//'verify authority aml'
//WebUI.verifyMatch(AuthorityAML.toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

'verify copy address atau tidak'
if (findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
    GlobalVariable.NumofGuarantorPersonal, 38).equalsIgnoreCase('Yes')) {
    'verify addres copy dari customer'
    arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
            GlobalVariable.NumofGuarantorPersonal, 24).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

    'verify rt copy dari customer'
    arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
            GlobalVariable.NumofGuarantorPersonal, 25).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

    'verify rw copy dari customer'
    arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
            GlobalVariable.NumofGuarantorPersonal, 26).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

    'verify zipcode copy dari customer'
    arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
            GlobalVariable.NumofGuarantorPersonal, 27).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

    'verify kecamatan copy dari customer'
    arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
            GlobalVariable.NumofGuarantorPersonal, 28).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

    'verify kelurahan copy dari customer'
    arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
            GlobalVariable.NumofGuarantorPersonal, 29).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

    'verify kota copy dari customer'
    arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
            GlobalVariable.NumofGuarantorPersonal, 30).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

    'verify ownership copy dari customer'
    arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
            GlobalVariable.NumofGuarantorPersonal, 31).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
} else {
    'verify address'
    arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
            GlobalVariable.NumofGuarantorPersonal, 39).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

    'verify rt'
    arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
            GlobalVariable.NumofGuarantorPersonal, 40).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

    'verify rw'
    arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
            GlobalVariable.NumofGuarantorPersonal, 41).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

    'verify zipcode'
    arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
            GlobalVariable.NumofGuarantorPersonal, 42).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

    'verify kecamatan'
    arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
            GlobalVariable.NumofGuarantorPersonal, 43).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

    'verify kelurahan'
    arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
            GlobalVariable.NumofGuarantorPersonal, 44).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

    'verify kota'
    arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
            GlobalVariable.NumofGuarantorPersonal, 45).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))

    'verify ownership'
    arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabGuarantorPersonal').getValue(
            GlobalVariable.NumofGuarantorPersonal, 46).toUpperCase(), (resultarray[arrayindex++]).toUpperCase(), false, FailureHandling.OPTIONAL))
}

'jika nilai di confins tidak sesuai dengan db'
if(arrayMatch.contains(false)){
	'Write To Excel GlobalVariable.StatusFailed'
	CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '3a.TabGuarantorDataPersonal',
			0, GlobalVariable.NumofGuarantorPersonal - 1, GlobalVariable.StatusFailed)
	
	'Write To Excel GlobalVariable.ReasonFailedStoredDB'
	CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '3a.TabGuarantorDataPersonal',
			1, GlobalVariable.NumofGuarantorPersonal - 1, GlobalVariable.ReasonFailedStoredDB)
}