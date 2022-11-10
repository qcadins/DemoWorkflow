import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import org.openqa.selenium.By as By
import org.openqa.selenium.WebDriver as WebDriver
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
import groovy.sql.Sql as Sql
import internal.GlobalVariable as GlobalVariable

'Assign directori file excel ke global variabel'
String userDir = System.getProperty('user.dir')

'Assign directori file excel ke global variabel'
String filePath = userDir + GlobalVariable.PathAppInquiryPersonal

'Assign directori file excel ke global variabel'
GlobalVariable.DataFilePath = filePath

String servername = findTestData('Login/Login').getValue(1, 9)

String instancename = findTestData('Login/Login').getValue(2, 9)

String username = findTestData('Login/Login').getValue(3, 9)

String password = findTestData('Login/Login').getValue(4, 9)

String database = findTestData('Login/Login').getValue(5, 9)

String driverclassname = findTestData('Login/Login').getValue(6, 9)

String url = (((servername + ';instanceName=') + instancename) + ';databaseName=') + database

'connect DB'
Sql sqlconnection = CustomKeywords.'dbconnection.connectDB.connect'(url, username, password, driverclassname)

'click tab Asset'
WebUI.click(findTestObject('Object Repository/AppView/Asset/AssetMenu'))

appno = WebUI.getText(findTestObject('Object Repository/AppView/MainInformation/Label App No'))

'get arraylist asset supplier info'
ArrayList<String> resultAssetSuppInfo = CustomKeywords.'dbconnection.VerifyAppView.checkAssetSupplierInfo'(sqlconnection, appno)

index = 0


'verify Supplier Name'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Asset/SupplierName')).toUpperCase(), (resultAssetSuppInfo[index++]).toUpperCase(), 
		false))

'verify Sales Person'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Asset/SalesPerson')).toUpperCase(), (resultAssetSuppInfo[index++]).toUpperCase(), 
		false))

'verify Admin Head'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Asset/AdminHead')).toUpperCase(), (resultAssetSuppInfo[index++]).toUpperCase(), 
		false))


'get arraylist asset info'
ArrayList<String> resultAssetInfo = CustomKeywords.'dbconnection.VerifyAppView.checkAssetInformation'(sqlconnection, appno)

index = 0

'verify asset name'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Asset/AssetName')).toUpperCase(), (resultAssetInfo[index++]).toUpperCase(), 
		false))

'verify asset category'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Asset/AssetCategory')).toUpperCase(), (resultAssetInfo[index++]).toUpperCase(), 
		false))

'verify asset price'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Asset/AssetPrice')).replace(',','').toUpperCase(), (resultAssetInfo[index++]).toUpperCase(), 
		false))

'verify DP Percent / Amount'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Asset/DPPercentAmount')).replace(',','').toUpperCase(), (resultAssetInfo[index++]).toUpperCase(), 
		false))

'verify Asset Condition'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Asset/AssetCondition')).toUpperCase(), (resultAssetInfo[index++]).toUpperCase(),
		false))

'verify Asset Usage'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Asset/AssetUsage')).toUpperCase(), (resultAssetInfo[index++]).toUpperCase(),
		false))

'verify Manufacturing Year'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Asset/ManufacturingYear')).toUpperCase(), (resultAssetInfo[index++]).toUpperCase(),
		false))

'verify Chasis No'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Asset/ChasisNo')).toUpperCase(), (resultAssetInfo[index++]).toUpperCase(),
		false))

'verify Engine No'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Asset/EngineNo')).toUpperCase(), (resultAssetInfo[index++]).toUpperCase(),
		false))

'verify License No'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Asset/LicensePlate')).toUpperCase(), (resultAssetInfo[index++]).toUpperCase(),
		false))

if(WebUI.verifyElementPresent(findTestObject('Object Repository/AppView/Asset/LetterNo'), 5, FailureHandling.OPTIONAL)){
'verify Letter No'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Asset/LetterNo')).toUpperCase(), (resultAssetInfo[index++]).toUpperCase(),
		false))
}else{
index++
}

'verify Color'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Asset/Color')).toUpperCase(), (resultAssetInfo[index++]).toUpperCase(),
		false))

'verify BPKB Issue Date'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Asset/BPKB Issue Date')).toUpperCase(), (resultAssetInfo[index++]).toUpperCase(),
		false))

'verify BPKB City Issuer'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Asset/BPKB Issue City')).toUpperCase(), (resultAssetInfo[index++]).toUpperCase(),
		false))

'verify Note'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Asset/AssetNote')).toUpperCase(), (resultAssetInfo[index++]).toUpperCase(),
		false))

//'get arraylist asset attr list'
//ArrayList<String> resultAssetAttrList = CustomKeywords.'dbconnection.VerifyAppView.checkAssetAttr'(sqlconnection, appno)
//
//index = 0
//
//'verify Asset Region'
//checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Asset/AssetRegion')).toUpperCase(), (resultAssetAttrList[index++]).toUpperCase(),
//		false))
//
//'verify Warna Body'
//checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Asset/WarnaBody')).toUpperCase(), (resultAssetAttrList[index++]).toUpperCase(),
//		false))
//
//'verify Color'
//checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Asset/AssetAttrColor')).toUpperCase(), (resultAssetAttrList[index++]).toUpperCase(),
//		false))
//
//'verify Oli Exp Date'
//checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Asset/Oli Expired Date')).toUpperCase(), (resultAssetAttrList[index++]).toUpperCase(),
//		false))
//
//'verify Setoran per hari'
//checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Asset/Setoran Per Hari')).toUpperCase(), (resultAssetAttrList[index++]).toUpperCase(),
//		false))
//
//'verify Road Worthiness'
//checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Asset/OffTheRoad')).toUpperCase(), (resultAssetAttrList[index++]).toUpperCase(),
//		false))


'get arraylist asset Accessories'
ArrayList<String> resultAssetAccessories = CustomKeywords.'dbconnection.VerifyAppView.checkAssetAccessories'(sqlconnection, appno)

'count Asset Accessories table'
variableData = DriverFactory.getWebDriver().findElements(By.cssSelector('#assetAcc > lib-ucgridview > div > table > tbody tr'))

index = 0

for (AccIndex = 1; AccIndex <= variableData.size(); AccIndex++) {
	'modify object SupplierName'
	modifyNewSupplierName = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 'equals',
			('//*[@id="assetAcc"]/lib-ucgridview/div/table/tbody/tr[' + AccIndex) + ']/td[2]', true)
	
	'modify object Accessories Name'
	modifyNewAccesssoriesName = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 'equals',
		('//*[@id="assetAcc"]/lib-ucgridview/div/table/tbody/tr[' + AccIndex) + ']/td[3]', true)

	'modify object Accessory Price'
	modifyNewAccessoryPrice = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 'equals',
		('//*[@id="assetAcc"]/lib-ucgridview/div/table/tbody/tr[' + AccIndex) + ']/td[4]', true)

	'modify object DP Percent'
	modifyNewDPPercent = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 'equals',
		('//*[@id="assetAcc"]/lib-ucgridview/div/table/tbody/tr[' + AccIndex) + ']/td[5]', true)

	'modify object DP Amount'
	modifyNewDPAmount = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 'equals',
		('//*[@id="assetAcc"]/lib-ucgridview/div/table/tbody/tr[' + AccIndex) + ']/td[6]', true)

	'modify object Note'
	modifyNewNote = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 'equals',
		('//*[@id="assetAcc"]/lib-ucgridview/div/table/tbody/tr[' + AccIndex) + ']/td[7]', true)
	
	'verify SupplierName'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewSupplierName).toUpperCase(), (resultAssetAccessories[index++]).toUpperCase(),
			false))
	
	'verify Accessories Name'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewAccesssoriesName).toUpperCase(), (resultAssetAccessories[index++]).toUpperCase(),
			false))
	
	'verify Accessory Price'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewAccessoryPrice).replace(',','').toUpperCase(), (resultAssetAccessories[index++]).toUpperCase(),
			false))
	
	'verify DP Percent'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewDPPercent).replace(' ','').replace('%', '').toUpperCase(), (resultAssetAccessories[index++]).toUpperCase(),
			false))
	
	'verify DP Amount'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewDPAmount).replace(',','').toUpperCase(), (resultAssetAccessories[index++]).toUpperCase(),
			false))
	
	'verify Note'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewNote).toUpperCase(), (resultAssetAccessories[index++]).toUpperCase(),
			false))
}


'get arraylist asset user from db'
ArrayList<String> resultAssetUser = CustomKeywords.'dbconnection.VerifyAppView.checkAssetUser'(sqlconnection, appno)

index = 0

'verify asset user name'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Asset/AssetUserName')).toUpperCase(), (resultAssetUser[index++]).toUpperCase(),
		false))

'verify asset user relationship'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Asset/AssetUserRelationship')).toUpperCase(), (resultAssetUser[index++]).toUpperCase(),
		false))


'get arraylist asset owner from db'
ArrayList<String> resultAssetOwner = CustomKeywords.'dbconnection.VerifyAppView.checkAssetOwner'(sqlconnection, appno)

'count Asset owner div section'
variableData = DriverFactory.getWebDriver().findElements(By.cssSelector('#owner div'))

index = 0

for (AssetOwnerindex = 1; AssetOwnerindex <= variableData.size(); AssetOwnerindex++) {
	'modify object asset owner left'
	modifyNewAssetOwnerLeft = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 'equals',
			('//*[@id="owner"]/div[' + AssetOwnerindex) + ']/label[2]', true)
	
	'modify object asset owner right'
	modifyNewAssetOwnerRight = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 'equals',
			('//*[@id="owner"]/div[' + AssetOwnerindex) + ']/label[4]', true)
	
	'verify asset owner left'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewAssetOwnerLeft).toUpperCase(), (resultAssetOwner[index++]).toUpperCase(),
			false))
	
	'verify asset owner right'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewAssetOwnerRight).toUpperCase(), (resultAssetOwner[index++]).toUpperCase(),
			false))
	
}

'get arraylist asset owner from db'
ArrayList<String> resultAssetLocation = CustomKeywords.'dbconnection.VerifyAppView.checkAssetLocation'(sqlconnection, appno)

index = 0

for(Locindex = 1; Locindex < resultAssetLocation.size(); Locindex++){
	'modify object asset location'
	modifyNewAssetLocation = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 'equals',
			('//*[@id="location"]/div[' + Locindex) + ']/label[2]', true)
	
	'verify asset owner right'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewAssetLocation).toUpperCase(), (resultAssetLocation[index++]).toUpperCase(),
			false))
}


'get arraylist asset collateral'
ArrayList<String> resultAssetCollateral = CustomKeywords.'dbconnection.VerifyAppView.checkAssetCollateral'(sqlconnection, appno)

'count Asset collateral table'
variableData = DriverFactory.getWebDriver().findElements(By.cssSelector('#AdditionalCollateralSection > div > table > tbody tr'))

index = 0

for (collateralindex = 1; collateralindex <= variableData.size(); collateralindex++) {
	'modify object collateral no'
	modifyNewCollateralNo = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 'equals',
			('//*[@id="AdditionalCollateralSection"]/div/table/tbody/tr[' + collateralindex) + ']/td[2]', true)
	
	'modify object collateral name'
	modifyNewCollateralName = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 'equals',
		('//*[@id="AdditionalCollateralSection"]/div/table/tbody/tr[' + collateralindex) + ']/td[3]', true)

	'modify object collateral type'
	modifyNewCollateralType = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 'equals',
		('//*[@id="AdditionalCollateralSection"]/div/table/tbody/tr[' + collateralindex) + ']/td[4]', true)

	'modify object collateral price'
	modifyNewCollateralPrice = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 'equals',
		('//*[@id="AdditionalCollateralSection"]/div/table/tbody/tr[' + collateralindex) + ']/td[5]', true)

	'modify object chasis no'
	modifyNewChasisNo = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 'equals',
		('//*[@id="AdditionalCollateralSection"]/div/table/tbody/tr[' + collateralindex) + ']/td[6]', true)

	'modify object engine no'
	modifyNewEngineNo = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 'equals',
		('//*[@id="AdditionalCollateralSection"]/div/table/tbody/tr[' + collateralindex) + ']/td[7]', true)
	
	'modify object license no'
	modifyNewLicenseNo = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 'equals',
		('//*[@id="AdditionalCollateralSection"]/div/table/tbody/tr[' + collateralindex) + ']/td[8]', true)
	
	'modify object notes'
	modifyNewNote = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 'equals',
		('//*[@id="AdditionalCollateralSection"]/div/table/tbody/tr[' + collateralindex) + ']/td[9]', true)
	
	'verify Collateral no'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewCollateralNo).toUpperCase(), (resultAssetCollateral[index++]).toUpperCase(),
			false))
	
	'verify Collateral Name'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewCollateralName).toUpperCase(), (resultAssetCollateral[index++]).toUpperCase(),
			false))
	
	'verify Collateral type'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewCollateralType).replace(' ','').toUpperCase(), (resultAssetCollateral[index++]).replace(' ','').toUpperCase(),
			false))
	
	'verify Collateral price'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewCollateralPrice).replace(',','').toUpperCase(), (resultAssetCollateral[index++]).toUpperCase(),
			false))
	
	'verify Chasisno'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewChasisNo).replace(',','').toUpperCase(), (resultAssetCollateral[index++]).toUpperCase(),
			false))
	
	'verify Engine no'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewEngineNo).replace(',','').toUpperCase(), (resultAssetCollateral[index++]).toUpperCase(),
			false))
	
	'verify License no'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewLicenseNo).replace(',','').toUpperCase(), (resultAssetCollateral[index++]).toUpperCase(),
			false))
	
	'verify Note'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewNote).toUpperCase(), (resultAssetCollateral[index++]).toUpperCase(),
			false))
}

def checkVerifyEqualOrMatch(Boolean isMatch) {
    if ((isMatch == false) && (GlobalVariable.FlagFailed == 0)) {
        new writetoexcel.writeToExcel().writeToExcelFunction(GlobalVariable.DataFilePath, '3. Guarantor', 0, GlobalVariable.NumofColm - 
            1, GlobalVariable.StatusFailed)

        new writetoexcel.writeToExcel().writeToExcelFunction(GlobalVariable.DataFilePath, '3. Guarantor', 1, GlobalVariable.NumofColm - 
            1, GlobalVariable.ReasonFailedVerifyEqualOrMatch)

        GlobalVariable.FlagFailed = 1
    }
}

