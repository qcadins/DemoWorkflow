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
Sql sqlconnection = CustomKeywords.'dbConnection.connectDB.connect'(url, username, password, driverclassname)

ArrayList<Boolean> resultsupplierinformation = CustomKeywords.'dbConnection.CustomerDataVerif.NAP2TabAssetSupplierInfoStoreDB'(
    sqlconnection, findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
        GlobalVariable.NumofColm, 13))

ArrayList<Boolean> resultassetinformation = CustomKeywords.'dbConnection.CustomerDataVerif.NAP2TabAssetStoreDB'(sqlconnection, 
    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
        GlobalVariable.NumofColm, 13))

ArrayList<Boolean> arrayMatch = new ArrayList<Boolean>()

'ganti value null > "" (String kosong)'
for (i = 0; i <= (resultassetinformation.size() - 1); i++) {
//    if ((resultassetinformation[i]) == null) {
//        (resultassetinformation[i]) = ''
//    } 
//	else 
	if ((resultassetinformation[i]).equalsIgnoreCase(',')) {
        (resultassetinformation[i]) = ', '
    }
}

println(resultassetinformation)

int arraysuppinfoindex = 0

int arrayassetinfoindex = 0

'verify supplier code'
arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
            GlobalVariable.NumofColm, 12).toUpperCase(), (resultsupplierinformation[arraysuppinfoindex++]).toUpperCase(), 
        false, FailureHandling.OPTIONAL))

'verify supplier name'
arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
            GlobalVariable.NumofColm, 13).toUpperCase(), (resultsupplierinformation[arraysuppinfoindex++]).toUpperCase(), 
        false, FailureHandling.OPTIONAL))

'verify sales person'
arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
            GlobalVariable.NumofColm, 14).toUpperCase(), (resultsupplierinformation[arraysuppinfoindex++]).toUpperCase(), 
        false, FailureHandling.OPTIONAL))

'verify admin head'
arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
            GlobalVariable.NumofColm, 15).toUpperCase(), (resultsupplierinformation[arraysuppinfoindex++]).toUpperCase(), 
        false, FailureHandling.OPTIONAL))

'verify asset code'
arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
            GlobalVariable.NumofColm, 17).toUpperCase(), (resultassetinformation[arrayassetinfoindex++]).toUpperCase(), 
        false, FailureHandling.OPTIONAL))

'verify asset condition'
arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
            GlobalVariable.NumofColm, 18).toUpperCase(), (resultassetinformation[arrayassetinfoindex++]).toUpperCase(), 
        false, FailureHandling.OPTIONAL))

'verify asset price'
arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
            GlobalVariable.NumofColm, 19).replace(',', ''), resultassetinformation[arrayassetinfoindex++], false, FailureHandling.OPTIONAL))

'verify asset usage'
arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
            GlobalVariable.NumofColm, 20).toUpperCase(), (resultassetinformation[arrayassetinfoindex++]).toUpperCase(), 
        false, FailureHandling.OPTIONAL))

'verify color'
arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
            GlobalVariable.NumofColm, 21).toUpperCase(), (resultassetinformation[arrayassetinfoindex++]).toUpperCase(), 
        false, FailureHandling.OPTIONAL))

'verify BPKB ISSUER'
arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
            GlobalVariable.NumofColm, 22).toUpperCase(), (resultassetinformation[arrayassetinfoindex++]).replace(',', ', ').toUpperCase(), 
        false, FailureHandling.OPTIONAL))

'verify BPKB ISSUE DATE'
arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
            GlobalVariable.NumofColm, 23).toUpperCase(), (resultassetinformation[arrayassetinfoindex++]).toUpperCase(), 
        false, FailureHandling.OPTIONAL))

'verify note'
arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
            GlobalVariable.NumofColm, 24).toUpperCase(), (resultassetinformation[arrayassetinfoindex++]).toUpperCase(), 
        false, FailureHandling.OPTIONAL))

'verify manufacturing year'
arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
            GlobalVariable.NumofColm, 25).toUpperCase(), (resultassetinformation[arrayassetinfoindex++]).toUpperCase(), 
        false, FailureHandling.OPTIONAL))

if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
    GlobalVariable.NumofColm, 26).equalsIgnoreCase('Percentage')) {
    'verify DP percent'
    arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
                GlobalVariable.NumofColm, 27).toUpperCase(), (resultassetinformation[arrayassetinfoindex++]).toUpperCase(), 
            false, FailureHandling.OPTIONAL))

    'skip amount'
    arrayassetinfoindex++
} else if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
    GlobalVariable.NumofColm, 26).equalsIgnoreCase('Amount')) {
    'skip percentage'
    arrayassetinfoindex++

    'verify DP Amount'
    arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
                GlobalVariable.NumofColm, 28).replace(',', ''), (resultassetinformation[arrayassetinfoindex++]).toUpperCase(), 
            false, FailureHandling.OPTIONAL))
}

'verify chasis number'
arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
            GlobalVariable.NumofColm, 29).toUpperCase(), (resultassetinformation[arrayassetinfoindex++]).toUpperCase(), 
        false, FailureHandling.OPTIONAL))

'verify engine number'
arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
            GlobalVariable.NumofColm, 30).toUpperCase(), (resultassetinformation[arrayassetinfoindex++]).toUpperCase(), 
        false, FailureHandling.OPTIONAL))

'verify license plate'
arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
            GlobalVariable.NumofColm, 31).toUpperCase(), (resultassetinformation[arrayassetinfoindex++]).toUpperCase(), 
        false, FailureHandling.OPTIONAL))

'verify serial no 4'
arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
            GlobalVariable.NumofColm, 32).toUpperCase(), (resultassetinformation[arrayassetinfoindex++]).toUpperCase(), 
        false, FailureHandling.OPTIONAL))

'verify serial no 5'
arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
            GlobalVariable.NumofColm, 33).toUpperCase(), (resultassetinformation[arrayassetinfoindex++]).toUpperCase(), 
        false, FailureHandling.OPTIONAL))

'verify asset region'
arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
            GlobalVariable.NumofColm, 35).toUpperCase(), (resultassetinformation[arrayassetinfoindex++]).toUpperCase(), 
        false, FailureHandling.OPTIONAL))

if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
    GlobalVariable.NumofColm, 37).equalsIgnoreCase('No')) {
    'verify user name'
    arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
                GlobalVariable.NumofColm, 38).toUpperCase(), (resultassetinformation[arrayassetinfoindex++]).toUpperCase(), 
            false, FailureHandling.OPTIONAL))

    'verify user relation'
    arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
                GlobalVariable.NumofColm, 39).toUpperCase(), (resultassetinformation[arrayassetinfoindex++]).toUpperCase(), 
            false, FailureHandling.OPTIONAL))
}

if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
    GlobalVariable.NumofColm, 41).equalsIgnoreCase('No')) {
    'verify owner type'
    arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
                GlobalVariable.NumofColm, 42).toUpperCase(), (resultassetinformation[arrayassetinfoindex++]).toUpperCase(), 
            false, FailureHandling.OPTIONAL))

    'verify owner name'
    arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
                GlobalVariable.NumofColm, 43).toUpperCase(), (resultassetinformation[arrayassetinfoindex++]).toUpperCase(), 
            false, FailureHandling.OPTIONAL))

    'verify owner relation'
    arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
                GlobalVariable.NumofColm, 44).toUpperCase(), (resultassetinformation[arrayassetinfoindex++]).toUpperCase(), 
            false, FailureHandling.OPTIONAL))

    if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
        GlobalVariable.NumofColm, 42).equalsIgnoreCase('Personal')) {
        'verify owner profession'
        arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
                    GlobalVariable.NumofColm, 46).toUpperCase(), (resultassetinformation[arrayassetinfoindex++]).toUpperCase(), 
                false, FailureHandling.OPTIONAL))
    } else {
        'verify owner profession'
        arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
                    GlobalVariable.NumofColm, 47).toUpperCase(), (resultassetinformation[arrayassetinfoindex++]).toUpperCase(), 
                false, FailureHandling.OPTIONAL))
    }
    
    'verify owner id type'
    arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
                GlobalVariable.NumofColm, 48).toUpperCase(), (resultassetinformation[arrayassetinfoindex++]).toUpperCase(), 
            false, FailureHandling.OPTIONAL))

    'verify owner id no'
    arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
                GlobalVariable.NumofColm, 49).toUpperCase(), (resultassetinformation[arrayassetinfoindex++]).toUpperCase(), 
            false, FailureHandling.OPTIONAL))

    'verify owner mobile no'
    arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
                GlobalVariable.NumofColm, 50).toUpperCase(), (resultassetinformation[arrayassetinfoindex++]).toUpperCase(), 
            false, FailureHandling.OPTIONAL))
}

'verify copy address'
if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
    GlobalVariable.NumofColm, 52).equalsIgnoreCase('Yes')) {
    'verify address'
    arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
                GlobalVariable.NumofColm, 38).toUpperCase(), (resultassetinformation[arrayassetinfoindex++]).toUpperCase(), 
            false, FailureHandling.OPTIONAL))

    'verify Rt'
    arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
                GlobalVariable.NumofColm, 39).toUpperCase(), (resultassetinformation[arrayassetinfoindex++]).toUpperCase(), 
            false, FailureHandling.OPTIONAL))

    'verify RW'
    arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
                GlobalVariable.NumofColm, 40).toUpperCase(), (resultassetinformation[arrayassetinfoindex++]).toUpperCase(), 
            false, FailureHandling.OPTIONAL))

    'verify Zipcode'
    arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
                GlobalVariable.NumofColm, 41).toUpperCase(), (resultassetinformation[arrayassetinfoindex++]).toUpperCase(), 
            false, FailureHandling.OPTIONAL))

    'verify kecamatan'
    arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
                GlobalVariable.NumofColm, 42).toUpperCase(), (resultassetinformation[arrayassetinfoindex++]).toUpperCase(), 
            false, FailureHandling.OPTIONAL))

    'verify kelurahan'
    arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
                GlobalVariable.NumofColm, 43).toUpperCase(), (resultassetinformation[arrayassetinfoindex++]).toUpperCase(), 
            false, FailureHandling.OPTIONAL))

    'verify kota'
    arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
                GlobalVariable.NumofColm, 44).toUpperCase(), (resultassetinformation[arrayassetinfoindex++]).toUpperCase(), 
            false, FailureHandling.OPTIONAL))
} else {
    'verify address'
    arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
                GlobalVariable.NumofColm, 54).toUpperCase(), (resultassetinformation[arrayassetinfoindex++]).toUpperCase(), 
            false, FailureHandling.OPTIONAL))

    'verify rt'
    arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
                GlobalVariable.NumofColm, 55).toUpperCase(), (resultassetinformation[arrayassetinfoindex++]).toUpperCase(), 
            false, FailureHandling.OPTIONAL))

    'verify rw'
    arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
                GlobalVariable.NumofColm, 56).toUpperCase(), (resultassetinformation[arrayassetinfoindex++]).toUpperCase(), 
            false, FailureHandling.OPTIONAL))

    'verify zipcode'
    arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
                GlobalVariable.NumofColm, 57).toUpperCase(), (resultassetinformation[arrayassetinfoindex++]).toUpperCase(), 
            false, FailureHandling.OPTIONAL))

    'verify kecamatan'
    arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
                GlobalVariable.NumofColm, 58).toUpperCase(), (resultassetinformation[arrayassetinfoindex++]).toUpperCase(), 
            false, FailureHandling.OPTIONAL))

    'verify kelurahan'
    arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
                GlobalVariable.NumofColm, 59).toUpperCase(), (resultassetinformation[arrayassetinfoindex++]).toUpperCase(), 
            false, FailureHandling.OPTIONAL))

    'verify kota'
    arrayMatch.add(WebUI.verifyMatch(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabAssetData').getValue(
                GlobalVariable.NumofColm, 60).toUpperCase(), (resultassetinformation[arrayassetinfoindex++]).toUpperCase(), 
            false, FailureHandling.OPTIONAL))
}

'Jika nilai di confins ada yang tidak sesuai dengan db'
if (arrayMatch.contains(false)) {
    'write to excel FAILED'
    CustomKeywords.'customizeKeyword.writeToExcel.writeToExcel'(GlobalVariable.DataFilePath, '7.TabAssetData', 0, GlobalVariable.NumofColm - 
        1, GlobalVariable.StatusFailed)

    'Write To Excel GlobalVariable.ReasonFailedStoredDB'
    CustomKeywords.'customizeKeyword.writeToExcel.writeToExcel'(GlobalVariable.DataFilePath, '7.TabAssetData', 1, GlobalVariable.NumofColm - 
        1, GlobalVariable.ReasonFailedStoredDB)
}

