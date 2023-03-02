import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import org.openqa.selenium.By

import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import org.openqa.selenium.support.ui.Select
import groovy.sql.Sql
import internal.GlobalVariable as GlobalVariable

'declare excelPathCollateral'
excelPathCollateral = (('NAP-' + GlobalVariable.LOB) + '-CustomerPersonal/NAP2-ApplicationData/TabCollateralData')

'click button lookup existing collateral'
WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/button_Lookup Existing Collateral'))

'set text asset name'
WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/input_FullAssetName Existing Collateral'), findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 62))

'set text amount more than'
WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/input_Amount More Than'), findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 63))

'set text amount less than'
WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/input_Amount Less Than'), findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 64))

'click button search'
WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/button_Search'))

'call function verify lookup'
verifyInputLookup()

'connect DB'
Sql sqlConnectionFOU = CustomKeywords.'dbConnection.connectDB.connectFOU'()

'get custno'
custno = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/label_Custno'))

'get tax date'
taxdate = WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/input_Collateral Tax Date'), 'value')

'get asset data from db'
ArrayList<String> collateraldata = CustomKeywords.'dbConnection.CustomerDataVerif.getDataExistingCollateral'(sqlConnectionFOU, custno, taxdate)

GlobalVariable.Confinsdata = []
index = 0

AssetCode = collateraldata[index++]

Select collateral = new Select(DriverFactory.getWebDriver().findElement(By.xpath('//*[@id="AddCollInfo"]/div/div[1]/div[1]/div/select')))
 
collateraltype = collateral.getFirstSelectedOption().getText()

'verif collateral condition'
WebUI.verifyOptionSelectedByLabel(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/Select_Collateral Condition'), collateraldata[index++].toUpperCase(), false, GlobalVariable.TimeOut)

Select collateralcondition = new Select(DriverFactory.getWebDriver().findElement(By.xpath('//*[@id="AddCollInfo"]/div/div[1]/div[3]/div/select')))
 
GlobalVariable.Confinsdata.add(collateralcondition.getFirstSelectedOption().getText())

'verif collateral usage'
WebUI.verifyOptionSelectedByLabel(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/Select_Collateral Usage'), collateraldata[index++].toUpperCase(), false, GlobalVariable.TimeOut)

Select collateralusage = new Select(DriverFactory.getWebDriver().findElement(By.xpath('//*[@id="AddCollInfo"]/div/div[1]/div[4]/div/select')))
 
GlobalVariable.Confinsdata.add(collateralusage.getFirstSelectedOption().getText())

'verif collateral tax date'
WebUI.verifyMatch(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/input_Collateral Tax Date'), 'value'), collateraldata[index++].toUpperCase(), false)

GlobalVariable.Confinsdata.add(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/input_Collateral Tax Date'), 'value'))

'verify equal collateral price'
WebUI.verifyEqual(Double.parseDouble(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/input_Collateral Price'), 'value').replace(',','')), Double.parseDouble(collateraldata[index++].toUpperCase()))

GlobalVariable.Confinsdata.add(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/input_Collateral Price'), 'value'))

'verify notes'
WebUI.verifyMatch(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/input_Collateral Notes'), 'value'), collateraldata[index++].toUpperCase(), false)

GlobalVariable.Confinsdata.add(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/input_Collateral Notes'), 'value'))

if(WebUI.verifyElementPresent(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/input_Serial No 1 - ExistingCol'), GlobalVariable.TimeOut, FailureHandling.OPTIONAL)){
	'verify serial 1'
	WebUI.verifyMatch(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/input_Serial No 1 - ExistingCol'), 'value'), collateraldata[index++].toUpperCase(), false)
	
	GlobalVariable.Confinsdata.add(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/input_Serial No 1 - ExistingCol'), 'value'))
	
}else{
index++
GlobalVariable.Confinsdata.add('')
}

if(WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/input_Serial No 2 - ExistingCol'), GlobalVariable.TimeOut, FailureHandling.OPTIONAL)){
	'verify serial 2'
	WebUI.verifyMatch(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/input_Serial No 2 - ExistingCol'), 'value'), collateraldata[index++].toUpperCase(), false)
	
	GlobalVariable.Confinsdata.add(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/input_Serial No 2 - ExistingCol'), 'value'))
	
}else{
index++
GlobalVariable.Confinsdata.add('')
}

if(WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/input_Serial No 3 - ExistingCol'), GlobalVariable.TimeOut, FailureHandling.OPTIONAL)){
	'verify serial 3'
	WebUI.verifyMatch(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/input_Serial No 3 - ExistingCol'), 'value'), collateraldata[index++].toUpperCase(), false)
	
	GlobalVariable.Confinsdata.add(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/input_Serial No 3 - ExistingCol'), 'value'))
	
}else{
index++
GlobalVariable.Confinsdata.add('')
}

if(WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/input_Serial No 4 - ExistingCol'), GlobalVariable.TimeOut, FailureHandling.OPTIONAL)){
	'verify serial 4'
	WebUI.verifyMatch(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/input_Serial No 4 - ExistingCol'), 'value'), collateraldata[index++].toUpperCase(), false)
	
	GlobalVariable.Confinsdata.add(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/input_Serial No 4 - ExistingCol'), 'value'))
	
}else{
index++

GlobalVariable.Confinsdata.add('')
}

if(WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/input_Serial No 5 - ExistingCol'), GlobalVariable.TimeOut, FailureHandling.OPTIONAL)){
	'verify serial 5'
	WebUI.verifyMatch(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/input_Serial No 5 - ExistingCol'), 'value'), collateraldata[index++].toUpperCase(), false)
	
	GlobalVariable.Confinsdata.add(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/input_Serial No 5 - ExistingCol'), 'value'))
	
}else{
index++
GlobalVariable.Confinsdata.add('')
}

'verify owner name'
WebUI.verifyMatch(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/input_Owner Name'), 'value'), collateraldata[index++].toUpperCase(), false)

GlobalVariable.Confinsdata.add(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/input_Owner Name'), 'value'))

'verify owner relationship'
WebUI.verifyOptionSelectedByLabel(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/select_Owner Relationship'), collateraldata[index++].toUpperCase(), false, GlobalVariable.TimeOut)

Select ownerrelation = new Select(DriverFactory.getWebDriver().findElement(By.xpath('//*[@id="AddCollOwner"]/div[3]/div[1]/select')))
 
GlobalVariable.Confinsdata.add(ownerrelation.getFirstSelectedOption().getText())

'verify owner profession'
WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/label_ProfessionOwner')), collateraldata[index++].toUpperCase(), false)

GlobalVariable.Confinsdata.add(WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/label_ProfessionOwner')))

'verify owner id type'
WebUI.verifyOptionSelectedByLabel(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/select_Owner Type Id'), collateraldata[index++].toUpperCase(), false, GlobalVariable.TimeOut)

Select owneridtype = new Select(DriverFactory.getWebDriver().findElement(By.xpath('//*[@id="AddCollOwner"]/div[2]/div[2]/select')))
 
GlobalVariable.Confinsdata.add(owneridtype.getFirstSelectedOption().getText())

'verify owner id no'
WebUI.verifyMatch(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/input_Owner Id No'), 'value'), collateraldata[index++].toUpperCase(), false)

GlobalVariable.Confinsdata.add(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/input_Owner Id No'), 'value'))

'verify owner phone no'
WebUI.verifyMatch(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/input_Owner Mobile Phone'), 'value'), collateraldata[index++].toUpperCase(), false)

GlobalVariable.Confinsdata.add(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/input_Owner Mobile Phone'), 'value'))

'verify owner address'
WebUI.verifyMatch(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/input_Address Owner'), 'value'), collateraldata[index++].toUpperCase(), false)

GlobalVariable.Confinsdata.add(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/input_Address Owner'), 'value'))

'verify owner RT'
WebUI.verifyMatch(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/input_RT Owner'), 'value'), collateraldata[index++].toUpperCase(), false)

GlobalVariable.Confinsdata.add(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/input_RT Owner'), 'value'))

'verify owner RW'
WebUI.verifyMatch(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/input_RW Owner'), 'value'), collateraldata[index++].toUpperCase(), false)

GlobalVariable.Confinsdata.add(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/input_RW Owner'), 'value'))

'verify owner Zipcode'
WebUI.verifyMatch(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/label_Zipcode Owner'), 'value'), collateraldata[index++].toUpperCase(), false)

GlobalVariable.Confinsdata.add(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/label_Zipcode Owner'), 'value'))

'verify owner kelurahan'
WebUI.verifyMatch(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/label_Kelurahan Owner'), 'value'), collateraldata[index++].toUpperCase(), false)

GlobalVariable.Confinsdata.add(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/label_Kelurahan Owner'), 'value'))

'verify owner Kecamatan'
WebUI.verifyMatch(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/label_Kecamatan Owner'), 'value'), collateraldata[index++].toUpperCase(), false)

GlobalVariable.Confinsdata.add(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/label_Kecamatan Owner'), 'value'))

'verify owner Kota'
WebUI.verifyMatch(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/label_Kota Owner'), 'value'), collateraldata[index++].toUpperCase(), false)

GlobalVariable.Confinsdata.add(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/label_Kota Owner'), 'value'))

'verify location address'
WebUI.verifyMatch(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/input_Address Location'), 'value'), collateraldata[index++].toUpperCase(), false)

GlobalVariable.Confinsdata.add(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/input_Address Location'), 'value'))

'verify location RT'
WebUI.verifyMatch(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/input_RT Location'), 'value'), collateraldata[index++].toUpperCase(), false)

GlobalVariable.Confinsdata.add(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/input_RT Location'), 'value'))

'verify location RW'
WebUI.verifyMatch(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/input_RW Location'), 'value'), collateraldata[index++].toUpperCase(), false)

GlobalVariable.Confinsdata.add(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/input_RW Location'), 'value'))

'verify owner Zipcode'
WebUI.verifyMatch(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/label_Zipcode Location'), 'value'), collateraldata[index++].toUpperCase(), false)

GlobalVariable.Confinsdata.add(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/label_Zipcode Location'), 'value'))

'verify location kelurahan'
WebUI.verifyMatch(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/label_Kelurahan Location'), 'value'), collateraldata[index++].toUpperCase(), false)

GlobalVariable.Confinsdata.add(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/label_Kelurahan Location'), 'value'))

'verify location Kecamatan'
WebUI.verifyMatch(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/label_Kecamatan Location'), 'value'), collateraldata[index++].toUpperCase(), false)

GlobalVariable.Confinsdata.add(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/label_Kecamatan Location'), 'value'))

'verify location Kota'
WebUI.verifyMatch(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/label_Kota Location'), 'value'), collateraldata[index++].toUpperCase(), false)

GlobalVariable.Confinsdata.add(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/label_Kota Location'), 'value'))

'input collateral porsion percentage'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/input_Collateral Portion Prctg'),
	findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 22))

'count collateral attribute'
int countCollateralAtrtibute = CustomKeywords.'customizeKeyword.getRowAssetAttribute.countRowCollateralAttribute'(sqlConnectionFOU, AssetCode)

for (int i = 1; i <= countCollateralAtrtibute; i++) {
	'modify Asset Attribute Input Text'
	modifyObjectCollateralAttributeInputText = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/object_Collateral Attr'),
		'xpath', 'equals', ('//*[@id="RefAttrContentAppCollateralAttrObjs"]/div[2]/div/div[' + i) + ']/div/div/input[@type="text"]',
		true)

	//
	'modify Asset Attribute Select Dropdownlist'
	modifyObjectCollateralAttributeList = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/object_Collateral Attr'),
		'xpath', 'equals', ('//*[@id="RefAttrContentAppCollateralAttrObjs"]/div[2]/div/div[' + i) + ']/div/div/select',
		true)

	'modify Asset Attribute Input Date'
	modifyObjectCollateralAttributeInputDate = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/object_Collateral Attr'),
		'xpath', 'equals', ('//*[@id="RefAttrContentAppCollateralAttrObjs"]/div[2]/div/div[' + i) + ']/div/div/input[@type="date"]',
		true)

	'modify Asset Attribute Input Number '
	modifyObjectCollateralAttributeInputNumber = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/object_Collateral Attr'),
		'xpath', 'equals', ('//*[@id="RefAttrContentAppCollateralAttrObjs"]/div[2]/div/div[' + i) + ']/div/div/input[@formcontrolname="AttrValue"]',
		true)

	if (WebUI.verifyElementPresent(modifyObjectCollateralAttributeInputText, GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
		'input "Attribute"'
		WebUI.setText(modifyObjectCollateralAttributeInputText, 'Attr', FailureHandling.OPTIONAL)
	} else if (WebUI.verifyElementPresent(modifyObjectCollateralAttributeList, GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
		'select option index 1'
		WebUI.selectOptionByIndex(modifyObjectCollateralAttributeList, 1, FailureHandling.OPTIONAL)
	} else if (WebUI.verifyElementPresent(modifyObjectCollateralAttributeInputDate, GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
		'input date 01/01/2000'
		WebUI.setText(modifyObjectCollateralAttributeInputDate, '01/01/2000', FailureHandling.OPTIONAL)
	} else if (WebUI.verifyElementPresent(modifyObjectCollateralAttributeInputNumber, GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
		'input number 0'
		WebUI.setText(modifyObjectCollateralAttributeInputNumber, '0', FailureHandling.OPTIONAL)
	}
}



'keyword untuk mencari nama-nama asset document yang muncul pada tab asset'
ArrayList<String> docName = CustomKeywords.'assetData.checkAssetDoc.checkCollateralDocument'(sqlConnectionFOU, collateraltype)

'looping asset document'
for (int i = 0; i < docName.size(); i++) {
'modify document name'
modifyDocumentName = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/object_Collateral Doc'),
	'xpath', 'equals', ('//*[@id=\'AddCollDocument\']/div/table/tbody/tr[' + (i + 1)) + ']/td[1]', true)

'get text document name'
textDocumentName = WebUI.getText(modifyDocumentName)

'Verif document name yang muncul pada confins sesuai dengan db'
if (WebUI.verifyMatch(textDocumentName, docName[i], false) == false) {
	checkVerifyEqualOrMatch(false)

	break
}

'modify object received'
modifyReceived = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/object_Collateral Doc'),
	'xpath', 'equals', ('//*[@id=\'AddCollDocument\']/div/table/tbody/tr[' + (i + 1)) + ']/td[2]/input', true)

'modify object document no'
modifyDocumentNo = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/object_Collateral Doc'),
	'xpath', 'equals', ('//*[@id=\'AddCollDocument\']/div/table/tbody/tr[' + (i + 1)) + ']/td[3]/input', true)

'modify object expired date'
modifyExpiredDate = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/object_Collateral Doc'),
	'xpath', 'equals', ('//*[@id=\'AddCollDocument\']/div/table/tbody/tr[' + (i + 1)) + ']/td[4]/input', true)

'modify document notes'
modifyDocumentNotes = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/object_Collateral Doc'),
	'xpath', 'equals', ('//*[@id=\'AddCollDocument\']/div/table/tbody/tr[' + (i + 1)) + ']/td[5]/input', true)

'Pengecekan nilai received pada excel "yes" atau "no" dan pengecekan kondisi checkbox tercentang atau tidak'
if (findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 57).equalsIgnoreCase('Yes') && WebUI.verifyElementNotChecked(
	modifyReceived, GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
	'centang received'
	WebUI.check(modifyReceived)
} else if (findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 57).equalsIgnoreCase('No') &&
WebUI.verifyElementChecked(modifyReceived, GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
	'uncentang received'
	WebUI.uncheck(modifyReceived)
}

'input documentNo'
WebUI.setText(modifyDocumentNo, findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 58))

'input expired date'
WebUI.setText(modifyExpiredDate, findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 59))

'input document notes'
WebUI.setText(modifyDocumentNotes, findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 60))
}

def verifyInputLookup() {
	if (WebUI.verifyElementPresent(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/a_Select'), GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
		'click button select'
		WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/a_Select'))
	} else {
		'click x'
		WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/button_X'))
		
		'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDataLookup'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('7b.CollateralData', GlobalVariable.NumofCollateral,
			GlobalVariable.StatusFailed, (findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 2) +
			';') + GlobalVariable.ReasonFailedDataLookup)
	}
}