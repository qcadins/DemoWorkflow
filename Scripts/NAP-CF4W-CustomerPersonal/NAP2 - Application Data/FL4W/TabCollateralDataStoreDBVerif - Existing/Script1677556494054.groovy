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

'connect DB'
Sql sqlconnection = CustomKeywords.'dbConnection.connectDB.connectLOS'()

'connect DB'
Sql sqlConnectionFOU = CustomKeywords.'dbConnection.connectDB.connectFOU'()

'declare excelPathCustomerPersonal'
excelPathCustomerPersonal = 'NAP-'+ GlobalVariable.LOB +'-CustomerPersonal/NAP1-CustomerData/TabCustomerData'

'declare excelPathCollateral'
excelPathCollateral = (('NAP-' + GlobalVariable.LOB) + '-CustomerPersonal/NAP2-ApplicationData/TabCollateralData')

'get asset data from db'
ArrayList<String> resultCollateralinformation = CustomKeywords.'dbConnection.CustomerDataVerif.ExistingCollateralstoreDB'(sqlconnection, 
findTestData(excelPathCustomerPersonal).getValue(GlobalVariable.NumofColm, 13))

//'get asset document from db'
//ArrayList<String> resultdocumentinformation = CustomKeywords.'dbConnection.CustomerDataVerif.NAP2AssetDocumentStoreDB'(sqlconnection, 
//    findTestData(excelPathCustomerPersonal).getValue(GlobalVariable.NumofColm, 13))
//
//'keyword untuk mencari nama-nama asset document yang muncul pada tab collateral'
//ArrayList<String> docName = CustomKeywords.'assetData.checkAssetDoc.checkCollateralDocument'(sqlConnectionFOU, findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 14))

'declare arraymatch'
ArrayList<String> arrayMatch = new ArrayList<String>()

'declare arrayCollateralinfoindex,arraydocinfoindex'
int arrayCollateralinfoindex = 0

int arraydocinfoindex = 0


println(GlobalVariable.Confinsdata.size())
println(resultCollateralinformation.size())

for(index = 0 ; index < resultCollateralinformation.size(); index++){
		'verify confins data == store db'
		arrayMatch.add(WebUI.verifyMatch(GlobalVariable.Confinsdata[index].replace(',','').replace('.00','').toUpperCase(), resultCollateralinformation[arrayCollateralinfoindex++].replace(',','').replace('.00','').toUpperCase(), false))
}

'Jika nilai di confins ada yang tidak sesuai dengan db'
if (arrayMatch.contains(false)) {
    'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedStoredDB'
    CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('7b.CollateralData', GlobalVariable.NumofCollateral, GlobalVariable.StatusFailed, 
        findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 2) + ';'+GlobalVariable.ReasonFailedStoredDB)
}

