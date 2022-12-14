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

'get data file path'
GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'(GlobalVariable.PathAppInquiryPersonal)

GlobalVariable.FlagWarning = 0

'connect DB los'
Sql sqlconnection = CustomKeywords.'dbConnection.connectDB.connectLOS'()

'click tab Asset'
WebUI.click(findTestObject('Object Repository/AppView/Asset/AssetMenu'))

'Verif tidak ada alert yang muncul'
if(WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerPersonal/div_erroralert'), GlobalVariable.TimeOut)==false){
	GlobalVariable.FlagWarning = 1
	'write to excel status warning'
	CustomKeywords.'checkSaveProcess.checkSaveProcess.writeWarningAppView'(GlobalVariable.NumofColm,'6. Asset')
}

'get appno from confins'
appno = WebUI.getText(findTestObject('Object Repository/AppView/MainInformation/Label App No'))

'get arraylist asset supplier info'
ArrayList<String> resultAssetSuppInfo = CustomKeywords.'appView.verifyAppView.checkAssetSupplierInfo'(sqlconnection, 
    appno)

HashMap<String,ArrayList> collateralDetail = new HashMap<String,ArrayList>()
ArrayList <String> collateralinfo = new ArrayList<String>()
ArrayList <String> collateraldoc = new ArrayList<String>()
ArrayList <String> collateralattr = new ArrayList<String>()
ArrayList <String> collateralacc = new ArrayList<String>()
ArrayList <String> collateraluserownerloc = new ArrayList<String>()

index = 0

'verify Supplier Name'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Asset/SupplierName')).toUpperCase(), 
        (resultAssetSuppInfo[index++]).toUpperCase(), false))

'verify Sales Person'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Asset/SalesPerson')).toUpperCase(), 
        (resultAssetSuppInfo[index++]).toUpperCase(), false))

'verify Admin Head'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Asset/AdminHead')).toUpperCase(), 
        (resultAssetSuppInfo[index++]).toUpperCase(), false))

'get arraylist asset info'
ArrayList<String> resultAssetInfo = CustomKeywords.'appView.verifyAppView.checkAssetInformation'(sqlconnection, appno)

index = 0

'verify asset name'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Asset/AssetName')).toUpperCase(), 
        (resultAssetInfo[index++]).toUpperCase(), false))

'verify asset category'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Asset/AssetCategory')).toUpperCase(), 
        (resultAssetInfo[index++]).toUpperCase(), false))

'verify asset price'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Asset/AssetPrice')).replace(
            ',', '').toUpperCase(), (resultAssetInfo[index++]).toUpperCase(), false))

'verify DP Percent / Amount'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Asset/DPPercentAmount')).replace(
            ',', '').toUpperCase(), (resultAssetInfo[index++]).toUpperCase(), false))

'verify Asset Condition'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Asset/AssetCondition')).toUpperCase(), 
        (resultAssetInfo[index++]).toUpperCase(), false))

'verify Asset Usage'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Asset/AssetUsage')).toUpperCase(), 
        (resultAssetInfo[index++]).toUpperCase(), false))

'verify Manufacturing Year'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Asset/ManufacturingYear')).toUpperCase(), 
        (resultAssetInfo[index++]).toUpperCase(), false))

'verify Chasis No'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Asset/ChasisNo')).toUpperCase(), 
        (resultAssetInfo[index++]).toUpperCase(), false))

'verify Engine No'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Asset/EngineNo')).toUpperCase(), 
        (resultAssetInfo[index++]).toUpperCase(), false))

'verify License No'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Asset/LicensePlate')).toUpperCase(), 
        (resultAssetInfo[index++]).toUpperCase(), false))

'Pengecekan pada confins ada atau tidaknya letterno'
if (WebUI.verifyElementPresent(findTestObject('Object Repository/AppView/Asset/LetterNo'), 5, FailureHandling.OPTIONAL)) {
    'verify Letter No'
    checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Asset/LetterNo')).toUpperCase(), 
            (resultAssetInfo[index++]).toUpperCase(), false))
} else {
    index++
}

'verify Color'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Asset/Color')).toUpperCase(), 
        (resultAssetInfo[index++]).toUpperCase(), false))

'verify BPKB Issue Date'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Asset/BPKB Issue Date')).toUpperCase(), 
        (resultAssetInfo[index++]).toUpperCase(), false))

'verify BPKB City Issuer'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Asset/BPKB Issue City')).toUpperCase(), 
        (resultAssetInfo[index++]).toUpperCase(), false))

'verify Note'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Asset/AssetNote')).toUpperCase(), 
        (resultAssetInfo[index++]).toUpperCase(), false))

'get arraylist asset attr list'
ArrayList<String> resultAssetAttrList = CustomKeywords.'appView.verifyAppView.checkAssetAttr'(sqlconnection, appno)

index = 0

'Pengecekan pada confins ada atau tidaknya asset region'
if (WebUI.verifyElementPresent(findTestObject('Object Repository/AppView/Asset/AssetRegion'), GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
    'verify Asset Region'
    checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Asset/AssetRegion')).toUpperCase(), 
            (resultAssetAttrList[index++]).toUpperCase(), false))
} else {
    index++
}

'Pengecekan pada confins ada atau tidaknya warna body'
if (WebUI.verifyElementPresent(findTestObject('Object Repository/AppView/Asset/WarnaBody'), GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
    'verify Warna Body'
    checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Asset/WarnaBody')).toUpperCase(), 
            (resultAssetAttrList[index++]).toUpperCase(), false))
} else {
    index++
}

'Pengecekan pada confins ada atau tidaknya color'
if (WebUI.verifyElementPresent(findTestObject('Object Repository/AppView/Asset/AssetAttrColor'), GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
    'verify Color'
    checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Asset/AssetAttrColor')).toUpperCase(), 
            (resultAssetAttrList[index++]).toUpperCase(), false))
} else {
    index++
}

'Pengecekan pada confins ada atau tidaknya oli exp date'
if (WebUI.verifyElementPresent(findTestObject('Object Repository/AppView/Asset/Oli Expired Date'), GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
    'verify Oli Exp Date'
    checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Asset/Oli Expired Date')).toUpperCase(), 
            (resultAssetAttrList[index++]).toUpperCase(), false))
} else {
    index++
}

'Pengecekan pada confins ada atau tidaknya setoran per hari'
if (WebUI.verifyElementPresent(findTestObject('Object Repository/AppView/Asset/Setoran Per Hari'), GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
    'verify Setoran per hari'
    checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Asset/Setoran Per Hari')).toUpperCase(), 
            (resultAssetAttrList[index++]).toUpperCase(), false))
} else {
    index++
}

'Pengecekan pada confins ada atau tidaknya road worthiness'
if (WebUI.verifyElementPresent(findTestObject('Object Repository/AppView/Asset/OffTheRoad'), GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
    'verify Road Worthiness'
    checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Asset/OffTheRoad')).toUpperCase(), 
            (resultAssetAttrList[index++]).toUpperCase(), false))
} else {
    index++
}

'get arraylist asset Accessories'
ArrayList<String> resultAssetAccessories = CustomKeywords.'appView.verifyAppView.checkAssetAccessories'(sqlconnection, 
    appno)

'count Asset Accessories table'
variableData = DriverFactory.getWebDriver().findElements(By.cssSelector('#assetAcc > lib-ucgridview > div > table > tbody tr'))

index = 0

'Looping accessories pada confins'
for (AccIndex = 1; AccIndex <= variableData.size(); AccIndex++) {
    'modify object SupplierName'
    modifyNewSupplierName = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 'equals', 
        ('//*[@id="assetAcc"]/lib-ucgridview/div/table/tbody/tr[' + AccIndex) + ']/td[2]', true)

    'modify object Accessories Name'
    modifyNewAccesssoriesName = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 
        'equals', ('//*[@id="assetAcc"]/lib-ucgridview/div/table/tbody/tr[' + AccIndex) + ']/td[3]', true)

    'modify object Accessory Price'
    modifyNewAccessoryPrice = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 
        'equals', ('//*[@id="assetAcc"]/lib-ucgridview/div/table/tbody/tr[' + AccIndex) + ']/td[4]', true)

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
    checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewSupplierName).toUpperCase(), (resultAssetAccessories[
            index++]).toUpperCase(), false))

    'verify Accessories Name'
    checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewAccesssoriesName).toUpperCase(), (resultAssetAccessories[
            index++]).toUpperCase(), false))

    'verify Accessory Price'
    checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewAccessoryPrice).replace(',', '').toUpperCase(), (resultAssetAccessories[
            index++]).toUpperCase(), false))

    'verify DP Percent'
    checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewDPPercent).replace(' ', '').replace('%', '').toUpperCase(), 
            (resultAssetAccessories[index++]).toUpperCase(), false))

    'verify DP Amount'
    checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewDPAmount).replace(',', '').toUpperCase(), (resultAssetAccessories[
            index++]).toUpperCase(), false))

    'verify Note'
    checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewNote).toUpperCase(), (resultAssetAccessories[index++]).toUpperCase(), 
            false))
}

'get arraylist asset user from db'
ArrayList<String> resultAssetUser = CustomKeywords.'appView.verifyAppView.checkAssetUser'(sqlconnection, appno)

index = 0

'verify asset user name'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Asset/AssetUserName')).toUpperCase(), 
        (resultAssetUser[index++]).toUpperCase(), false))

'verify asset user relationship'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Asset/AssetUserRelationship')).toUpperCase(), 
        (resultAssetUser[index++]).toUpperCase(), false))

'get arraylist asset owner from db'
ArrayList<String> resultAssetOwner = CustomKeywords.'appView.verifyAppView.checkAssetOwner'(sqlconnection, appno)

'count Asset owner div section'
variableData = DriverFactory.getWebDriver().findElements(By.cssSelector('#owner div'))

index = 0

'Looping asset owner data confins'
for (AssetOwnerindex = 1; AssetOwnerindex <= variableData.size(); AssetOwnerindex++) {
    'modify object asset owner left'
    modifyNewAssetOwnerLeft = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 
        'equals', ('//*[@id="owner"]/div[' + AssetOwnerindex) + ']/label[2]', true)

    'modify object asset owner right'
    modifyNewAssetOwnerRight = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 
        'equals', ('//*[@id="owner"]/div[' + AssetOwnerindex) + ']/label[4]', true)

    'verify asset owner left'
    checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewAssetOwnerLeft).toUpperCase(), (resultAssetOwner[index++]).toUpperCase(), 
            false))

    'verify asset owner right'
    checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewAssetOwnerRight).toUpperCase(), (resultAssetOwner[index++]).toUpperCase(), 
            false))
}

'get arraylist asset owner from db'
ArrayList<String> resultAssetLocation = CustomKeywords.'appView.verifyAppView.checkAssetLocation'(sqlconnection, appno)

index = 0

'looping asset location pada confins'
for (Locindex = 1; Locindex < resultAssetLocation.size(); Locindex++) {
    'modify object asset location'
    modifyNewAssetLocation = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 'equals', 
        ('//*[@id="location"]/div[' + Locindex) + ']/label[2]', true)

    'verify asset location'
    checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewAssetLocation).toUpperCase(), (resultAssetLocation[
            index++]).toUpperCase(), false))
}

'get arraylist asset collateral'
ArrayList<String> resultAssetCollateral = CustomKeywords.'appView.verifyAppView.checkAssetCollateral'(sqlconnection, 
    appno)

'count Asset collateral table'
variableData = DriverFactory.getWebDriver().findElements(By.cssSelector('#AdditionalCollateralSection > div > table > tbody tr'))

index = 0

'looping asset collateral pada confins'
for (collateralindex = 1; collateralindex <= variableData.size(); collateralindex++) {
    'modify object collateral no'
    modifyNewCollateralNo = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 'equals', 
        ('//*[@id="AdditionalCollateralSection"]/div/table/tbody/tr[' + collateralindex) + ']/td[2]', true)

    'modify object collateral name'
    modifyNewCollateralName = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 
        'equals', ('//*[@id="AdditionalCollateralSection"]/div/table/tbody/tr[' + collateralindex) + ']/td[3]', true)

    'modify object collateral type'
    modifyNewCollateralType = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 
        'equals', ('//*[@id="AdditionalCollateralSection"]/div/table/tbody/tr[' + collateralindex) + ']/td[4]', true)

    'modify object collateral price'
    modifyNewCollateralPrice = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 
        'equals', ('//*[@id="AdditionalCollateralSection"]/div/table/tbody/tr[' + collateralindex) + ']/td[5]', true)

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
	
	'modify button view detail'
	modifyButtonViewDetail = WebUI.modifyObjectProperty(findTestObject('Object Repository/AppView/Asset/ViewDetail'),'xpath','equals',"//*[@id='AdditionalCollateralSection']/div/table/tbody/tr["+collateralindex+"]/td[10]/a/i",true) 

    'verify Collateral no'
    checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewCollateralNo).toUpperCase(), (resultAssetCollateral[
            index++]).toUpperCase(), false))

    'verify Collateral Name'
    checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewCollateralName).toUpperCase(), (resultAssetCollateral[
            index++]).toUpperCase(), false))

    'verify Collateral type'
    checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewCollateralType).replace(' ', '').toUpperCase(), (resultAssetCollateral[
            index++]).replace(' ', '').toUpperCase(), false))

    'verify Collateral price'
    checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewCollateralPrice).replace(',', '').toUpperCase(), (resultAssetCollateral[
            index++]).toUpperCase(), false))

    'verify Chasisno'
    checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewChasisNo).replace(',', '').toUpperCase(), (resultAssetCollateral[
            index++]).toUpperCase(), false))

    'verify Engine no'
    checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewEngineNo).replace(',', '').toUpperCase(), (resultAssetCollateral[
            index++]).toUpperCase(), false))

    'verify License no'
    checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewLicenseNo).replace(',', '').toUpperCase(), (resultAssetCollateral[
            index++]).toUpperCase(), false))

    'verify Note'
    checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewNote).toUpperCase(), (resultAssetCollateral[index++]).toUpperCase(), 
            false))
	
	'Klik button view detail'
	WebUI.click(modifyButtonViewDetail)
	
	'Pengecekan data collateral detail pada db'
	collateralDetail = CustomKeywords.'appView.verifyAppView.checkAssetCollateralDetail'(sqlconnection, appno)
	collateralinfo = collateralDetail.get("ColInfo")
	collateraldoc = collateralDetail.get("ColDoc")
	collateralattr = collateralDetail.get("ColAttr")
	collateralacc = collateralDetail.get("ColAcc")
	collateraluserownerloc = collateralDetail.get("ColUserOwnerLoc")
	
	index = 0
	
	'verify app colatteral no confins dengan db'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Asset/ModifyCollateralInfo')).toUpperCase(),collateralinfo[index++].toUpperCase(),false))
	
	'looping verify section collateral information confins dengan db'
	for(int collateralindex = 2;collateralindex<=(collateralinfo.size()+1)/2;collateralindex++){
		'modify object collateralinfoleft'
		modifyCollateralInfoLeft = WebUI.modifyObjectProperty(findTestObject('Object Repository/AppView/Asset/ModifyCollateralInfo'),'xpath','equals',"//*[@id='asset']/div["+collateralindex+"]/label[2]",true)
		
		'modify object collateralinforight'
		modifyCollateralInfoRight = WebUI.modifyObjectProperty(findTestObject('Object Repository/AppView/Asset/ModifyCollateralInfo'),'xpath','equals',"//*[@id='asset']/div["+collateralindex+"]/label[4]",true)
		
		'verify collateral information bagian kiri'
		checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyCollateralInfoLeft).replace(",","").toUpperCase(),collateralinfo[index++].replace(" -  "," - ").toUpperCase(),false))
		
		'verify collateral information bagian kanan'
		checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyCollateralInfoRight).toUpperCase(),collateralinfo[index++].toUpperCase(),false))
	}
	
	index = 0
	'looping verify section collateral document confins dengan db'
	for(int collateralindex = 1;collateralindex<=collateraldoc.size()/5;collateralindex++){
		'modify object document name'
		modifyDocumentName = WebUI.modifyObjectProperty(findTestObject('Object Repository/AppView/Asset/ModifyCollateralDocument'),'xpath','equals',"//*[@id='AddCollDocument']/table/tbody/tr["+collateralindex+"]/td[1]",true)
		
		'modify object received'
		modifyReceived = WebUI.modifyObjectProperty(findTestObject('Object Repository/AppView/Asset/ModifyCollateralDocument'),'xpath','equals',"//*[@id='AddCollDocument']/table/tbody/tr["+collateralindex+"]/td[2]",true)
		
		'modify object document no'
		modifyDocumentNo = WebUI.modifyObjectProperty(findTestObject('Object Repository/AppView/Asset/ModifyCollateralDocument'),'xpath','equals',"//*[@id='AddCollDocument']/table/tbody/tr["+collateralindex+"]/td[3]",true)
		 
		'modify object expired date'
		modifyExpiredDate = WebUI.modifyObjectProperty(findTestObject('Object Repository/AppView/Asset/ModifyCollateralDocument'),'xpath','equals',"//*[@id='AddCollDocument']/table/tbody/tr["+collateralindex+"]/td[4]",true)
		 
		'modify object document notes'
		modifyDocumentNotes = WebUI.modifyObjectProperty(findTestObject('Object Repository/AppView/Asset/ModifyCollateralDocument'),'xpath','equals',"//*[@id='AddCollDocument']/table/tbody/tr["+collateralindex+"]/td[5]",true)
				
		'verify document name'
		checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyDocumentName).toUpperCase(),collateraldoc[index++].toUpperCase(),false))
		
		'verify received'
		checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyReceived).toUpperCase(),collateraldoc[index++].toUpperCase(),false))
		
		'verify document no'
		checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyDocumentNo).toUpperCase(),collateraldoc[index++].toUpperCase(),false))
		
		'verify expired date'
		checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyExpiredDate).toUpperCase(),collateraldoc[index++].toUpperCase(),false))
		
		'verify document notes'
		checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyDocumentNotes).toUpperCase(),collateraldoc[index++].toUpperCase(),false))
		
	}
	
	index = 0
	'looping section collateral attribute confins dengan db'
	for(int collateralindex = 1;collateralindex<=collateralattr.size()/2;collateralindex++){
		'modify object attrname'
		modifyAttrName = WebUI.modifyObjectProperty(findTestObject('Object Repository/AppView/Asset/ModifyCollateralAttribute'),'xpath','equals',"//*[@id='collAttr']/div/div/div["+collateralindex+"]/div/label[1]",true)
		
		'modify object attrvalue'
		modifyAttrValue = WebUI.modifyObjectProperty(findTestObject('Object Repository/AppView/Asset/ModifyCollateralAttribute'),'xpath','equals',"//*[@id='collAttr']/div/div/div["+collateralindex+"]/div/label[2]",true)
	
		'verify attribute name'
		checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyAttrName).toUpperCase(),collateralattr[index++].toUpperCase(),false))
		
		'verify attribute value'
		checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyAttrValue).replace(",","").toUpperCase(),collateralattr[index++].toUpperCase(),false))
		
	}
	
	index = 0
	'looping section collateral accessories confins dengan db'
	for(int collateralindex = 1;collateralindex<=collateralacc.size()/5;collateralindex++){
		'modify object accessories name'
		modifyAccName = WebUI.modifyObjectProperty(findTestObject('Object Repository/AppView/Asset/ModifyCollateralAccessories'),'xpath','equals',"//*[@id='collateralAccessories']/lib-ucgridview/div/table/tbody/tr["+collateralindex+"]/td[2]",true)
		
		'modify object accessories price'
		modifyAccPrice = WebUI.modifyObjectProperty(findTestObject('Object Repository/AppView/Asset/ModifyCollateralAccessories'),'xpath','equals',"//*[@id='collateralAccessories']/lib-ucgridview/div/table/tbody/tr["+collateralindex+"]/td[3]",true)
		
		'modify object security deposit percentage'
		modifySecDepositPctg = WebUI.modifyObjectProperty(findTestObject('Object Repository/AppView/Asset/ModifyCollateralAccessories'),'xpath','equals',"//*[@id='collateralAccessories']/lib-ucgridview/div/table/tbody/tr["+collateralindex+"]/td[4]",true)
		
		'modify object security deposit amount'
		modifySecDepositAmt = WebUI.modifyObjectProperty(findTestObject('Object Repository/AppView/Asset/ModifyCollateralAccessories'),'xpath','equals',"//*[@id='collateralAccessories']/lib-ucgridview/div/table/tbody/tr["+collateralindex+"]/td[5]",true)
		
		'modify object accessories notes'
		modifyNotes = WebUI.modifyObjectProperty(findTestObject('Object Repository/AppView/Asset/ModifyCollateralAccessories'),'xpath','equals',"//*[@id='collateralAccessories']/lib-ucgridview/div/table/tbody/tr["+collateralindex+"]/td[6]",true)
		
		'verify accessories name'
		checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyAccName).toUpperCase(),collateralacc[index++].toUpperCase(),false))
		
		'verify accessories price'
		checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyAccPrice).replace(",","").toUpperCase(),collateralacc[index++].toUpperCase(),false))
		
		'verify security deposit percent'
		checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifySecDepositPctg).replace("  %","").toUpperCase(),collateralacc[index++].toUpperCase(),false))
		
		'verify security deposit amount'
		checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifySecDepositAmt).replace(",","").toUpperCase(),collateralacc[index++].toUpperCase(),false))
		
		'modify accessories notes'
		checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNotes).toUpperCase(),collateralacc[index++].toUpperCase(),false))
		
	}
	
	index = 0
	
	'verify collateral user name confins dengan db'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Asset/CollateralUserName')),collateraluserownerloc[index++].toUpperCase(),false))
	
	'verify collateral user relationship confins dengan db'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Asset/CollateralUserRelationship')),collateraluserownerloc[index++].toUpperCase(),false))
	
	'get banyaknya field pada section collateral owner confins'
	variableDataColl = DriverFactory.getWebDriver().findElements(By.cssSelector('#CollOwnerId > div label.label-control.ng-star-inserted'))
	
	'looping verify section collateral owner confins dengan db'
	for(int collateralindex = 1;collateralindex<=variableDataColl.size();collateralindex++){
				 
		'modify object collateral owner'
		modifyCollOwner = WebUI.modifyObjectProperty(findTestObject('Object Repository/AppView/Asset/ModifyCollateralOwner'),'xpath','equals',"//*[@id='CollOwnerId']/div/div["+collateralindex+"]/span/div/div[2]",true)
				 		
		'verify collateral owner' 
		checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyCollOwner).toUpperCase(),collateraluserownerloc[index++].toUpperCase(),false))
				
	}
	
	'get banyaknya field pada section collateral location confins'
	variableDataColl = DriverFactory.getWebDriver().findElements(By.cssSelector('#LocationId > div label.label-control.ng-star-inserted'))
	
	'looping verify section collateral location confins dengan db'
	for(int collateralindex = 1;collateralindex<=variableDataColl.size();collateralindex++){
		
		'modify object collateral location'
		modifyCollLocation = WebUI.modifyObjectProperty(findTestObject('Object Repository/AppView/Asset/ModifyCollateralLocation'),'xpath','equals',"//*[@id='LocationId']/div/div["+collateralindex+"]/span/div/div[2]",true)
				 
		'verify collateral location'
		checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyCollLocation).toUpperCase(),collateraluserownerloc[index++].toUpperCase(),false))
	   
	}

	'Klik button back'
	WebUI.click(findTestObject('Object Repository/AppView/Asset/Back'))
}

if ((GlobalVariable.FlagWarning == 0) && (GlobalVariable.FlagFailed == 0)) {
	'write to excel status success'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '6. Asset', 0, GlobalVariable.NumofColm -
		1, GlobalVariable.StatusSuccess)
}

def checkVerifyEqualOrMatch(Boolean isMatch) {
    if ((isMatch == false) && (GlobalVariable.FlagFailed == 0)) {
		'write to excel status failed'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '6. Asset', 0, GlobalVariable.NumofColm - 
            1, GlobalVariable.StatusFailed)

		'write to excel failed reason verify equal or match'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '6. Asset', 1, GlobalVariable.NumofColm - 
            1, GlobalVariable.ReasonFailedVerifyEqualOrMatch)

        GlobalVariable.FlagFailed = 1
    }
}

