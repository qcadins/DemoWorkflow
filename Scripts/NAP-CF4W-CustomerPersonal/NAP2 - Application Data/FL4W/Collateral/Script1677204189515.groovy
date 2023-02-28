import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import org.openqa.selenium.By as By
import org.openqa.selenium.support.ui.Select as Select
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

int flagWarning = 0

GlobalVariable.FlagFailed = 0

'get data file path'
GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'(((('\\Excel\\' + GlobalVariable.LOB) + 
    '\\2.1 DataFile_NAP_') + GlobalVariable.LOB) + '.xlsx')

'koneksi los'
Sql sqlConnectionLOS = CustomKeywords.'dbConnection.connectDB.connectLOS'()

'koneksi fou'
Sql sqlConnectionFOU = CustomKeywords.'dbConnection.connectDB.connectFOU'()

'declare excelPathCollateral'
excelPathCollateral = (('NAP-' + GlobalVariable.LOB) + '-CustomerPersonal/NAP2-ApplicationData/TabCollateralData')

'declare excelPathCustomerPersonal'
excelPathCustomerPersonal = (('NAP-' + GlobalVariable.LOB) + '-CustomerPersonal/NAP1-CustomerData/TabCustomerData')

'select collateral status'
WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/Select_Collateral Status'), 
    findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 12), false)

'check if existing collateral'
if (findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 12).equalsIgnoreCase('Existing Collateral')) {
	
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
	
	'select collateral status'
	WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/Select_Collateral Status'),
		'New Collateral', false)
	
	'select collateral type dengan index untuk refresh karena kadang lookup collateral tidak muncul button nya'
	WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/Select_Collateral Type'),
		'BMW', false)
	
	'call function check ddl collateral info'
	checkDDL(sqlConnectionFOU)
	
	'select collateral type'
	WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/Select_Collateral Type'),
		'(?i)' + findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 14), true)


	'click button lookup collateral'
	WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/button_Lookup Collateral Name'))

	'input collateral code'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/input_Collateral Code'),
		findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 15))

	'input collateral name'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/input_Collateral Name'),
		findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 16))

	'click button search'
	WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/button_Search'))

	'call function verifyInputLookup'
	verifyInputLookup()
	
	'select collateral status'
	WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/Select_Collateral Status'),
		findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 12), false)
	
	'call test case collateral existing'
    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/FL4W/CollateralExisting'), [:], FailureHandling.CONTINUE_ON_FAILURE)
	
//	'call function input coll attr'
//	inputCollAttr(sqlConnectionFOU)
//	
//	'call function input coll doc'
//	inputCollAttr(sqlConnectionFOU)
}else if (findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 12).equalsIgnoreCase('New Collateral')) {

	'select collateral type dengan index untuk refresh karena kadang lookup collateral tidak muncul button nya'
	WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/Select_Collateral Type'), 
	    'BMW', false)
	
	'call function check ddl collateral info'
	checkDDL(sqlConnectionFOU)
	
	'select collateral type'
	WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/Select_Collateral Type'), 
	    '(?i)' + findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 14), true)


    'click button lookup collateral'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/button_Lookup Collateral Name'))

    'input collateral code'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/input_Collateral Code'), 
        findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 15))

    'input collateral name'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/input_Collateral Name'), 
        findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 16))

    'click button search'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/button_Search'))

    'call function verifyInputLookup'
    verifyInputLookup()

'input manufacturing year'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/input_Manufacturing Year'), 
    findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 17))

if (WebUI.verifyElementNotHasAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/Select_Collateral Condition'), 
    'disabled', GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
    'select collateral condition'
    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/Select_Collateral Condition'), 
        findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 18), false)
}

if (WebUI.verifyElementNotHasAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/Select_Collateral Usage'), 
    'disabled', GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
    'select collateral usage'
    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/Select_Collateral Usage'), 
        findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 19), false)
}

'input collateral tax date'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/input_Collateral Tax Date'), 
    findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 20))

'input collateral price'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/input_Collateral Price'), 
    findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 21))

'input collateral porsion percentage'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/input_Collateral Portion Prctg'), 
    findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 22))

'input collateral note'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/input_Collateral Notes'), 
    findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 25))

'input collateral serial 1'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/input_Serial No 1'), 
    findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 26))

if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/input_Serial No 2'), 
    GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
    'input collateral serial 2'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/input_Serial No 2'), 
        findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 27))
}

if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/input_Serial No 3'), 
    GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
    'input collateral serial 3'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/input_Serial No 3'), 
        findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 28))
}

if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/input_Serial No 4'), 
    GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
    'input collateral serial 4'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/input_Serial No 4'), 
        findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 29))
}

if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/input_Serial No 5'), 
    GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
    'input collateral serial 5'
    WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/input_Serial No 5'), 
        findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 30))
}

'call function input coll attr'	
inputCollAttr(sqlConnectionFOU)

'call function input coll doc'
inputCollAttr(sqlConnectionFOU)

if (CustomKeywords.'assetData.checkAssetData.checkSelfOwner'(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/checkBox_Self Owner Status'), 
    GlobalVariable.NumofCollateral, 34, excelPathCollateral) == true) {
    'click self owner checkbox'
    WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/checkBox_Self Owner'))

    'call function get data asset owner address'
    getDataOwner()

    if (findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 45) == 'Yes') {
        collateralLocCopy()
    } else if (findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 45) == 'No') {
        assetLocInput()
    }
} else if (findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 34) == 'No') {
    if (WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/checkBox_Self Owner Status'), 
        'aria-checked') == 'true') {
        'click self owner checkbox'
        WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/checkBox_Self Owner'))
    }
    
    if (findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 35) == 'Personal') {
        'click radio button Personal'
        WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/Radio_Personal'))

        if (GlobalVariable.Role == 'Testing') {
            ArrayList<String> ownerPersonalRelation = new ArrayList<String>()

            'Ambil array string (text) owner relationship dari db'
            ownerPersonalRelation = CustomKeywords.'assetData.checkAssetData.checkPersonalRelationshipDDL'(sqlConnectionFOU)

            Integer countOwnerPersonalRelation = ownerPersonalRelation.size()

            'Verif dropdownlist owner relationship yang muncul pada confins sesuai dengan array string owner relationship dari db'
            if (WebUI.verifyOptionsPresent(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/select_Owner Relationship'), 
                ownerPersonalRelation) == false) {
                'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDDL'
                CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('7b.CollateralData', GlobalVariable.NumofCollateral, 
                    GlobalVariable.StatusFailed, ((findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 
                        2) + ';') + GlobalVariable.ReasonFailedDDL) + 'value Owner relationship Personal')

                GlobalVariable.FlagFailed = 1
            }
            
            'Ambil nilai jumlah option/pilihan owner relationship dari confins'
            Integer totalOwnerPersonalRelation = WebUI.getNumberOfTotalOption(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/select_Owner Relationship'))

            'Verif jumlah owner relationship yang muncul pada confins sesuai dengan jumlah owner relationship pada db'
            if (WebUI.verifyEqual(totalOwnerPersonalRelation, countOwnerPersonalRelation) == false) {
                'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDDL'
                CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('7b.CollateralData', GlobalVariable.NumofCollateral, 
                    GlobalVariable.StatusFailed, ((findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 
                        2) + ';') + GlobalVariable.ReasonFailedDDL) + 'Jumlah Owner Relationship Personal')

                GlobalVariable.FlagFailed = 1
            }
        }
    } else if (findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 35) == 'Company') {
        'click radio button company'
        WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/Radio_Company'))

        if (GlobalVariable.Role == 'Testing') {
            ArrayList<String> ownerCompanyRelation = new ArrayList<String>()

            'Ambil array string (text) owner relationship dari db'
            ownerCompanyRelation = CustomKeywords.'assetData.checkAssetData.checkCompanyRelationshipDDL'(sqlConnectionFOU)

            Integer countOwnerCompanyRelation = ownerCompanyRelation.size()

            'Verif dropdownlist owner relationship yang muncul pada confins sesuai dengan array string owner relationship dari db'
            if (WebUI.verifyOptionsPresent(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/select_Owner Relationship'), 
                ownerCompanyRelation) == false) {
                'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDDL'
                CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('7b.CollateralData', GlobalVariable.NumofCollateral, 
                    GlobalVariable.StatusFailed, ((findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 
                        2) + ';') + GlobalVariable.ReasonFailedDDL) + 'Value Owner Relationship Company')

                GlobalVariable.FlagFailed = 1
            }
            
            'Ambil nilai jumlah option/pilihan owner relationship dari confins'
            Integer totalOwnerCompanyRelation = WebUI.getNumberOfTotalOption(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/select_Owner Relationship'))

            'Verif jumlah owner relationship yang muncul pada confins sesuai dengan jumlah owner relationship pada db'
            if (WebUI.verifyEqual(totalOwnerCompanyRelation, countOwnerCompanyRelation) == false) {
                'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDDL'
                CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('7b.CollateralData', GlobalVariable.NumofCollateral, 
                    GlobalVariable.StatusFailed, ((findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 
                        2) + ';') + GlobalVariable.ReasonFailedDDL) + 'Jumlah Owner Relationship Company')

                GlobalVariable.FlagFailed = 1
            }
            
            'get ownerprofession ddl value from db'
            ArrayList<String> ownerprofession = CustomKeywords.'dbConnection.checkCustomer.checkCompanyTypeDDL'(sqlConnectionFOU)

            'get total label from ddl ownerprofession'
            int totalddlownerprofession = WebUI.getNumberOfTotalOption(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/select_Owner Profession'))

            'verify total ddl ownerprofession confins = total ddl db'
            WebUI.verifyEqual(totalddlownerprofession - 1, ownerprofession.size())

            'verify isi ddl ownerprofession confins = db'
            if (WebUI.verifyOptionsPresent(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/select_Owner Profession'), 
                ownerprofession) == false) {
                'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDDL'
                CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('7b.CollateralData', GlobalVariable.NumofCollateral, 
                    GlobalVariable.StatusFailed, ((findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 
                        2) + ';') + GlobalVariable.ReasonFailedDDL) + 'ownerprofession')

                (GlobalVariable.FlagFailed)++
            }
        }
    }
    
    'input owner name'
    WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/input_Owner Name'), 
        findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 36))

    'select owner relationship'
    WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/select_Owner Relationship'), 
        findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 37), false)

    if (findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 35) == 'Personal') {
        if (findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 39).length() > 1) {
            'click button owner profession'
            WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/button_Lookup Owner Profession'))

            'input profession code'
            WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/input_Profession Code'), 
                findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 38))

            'click button search'
            WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/button_Search'))

            'call function verify input lookup'
            verifyInputLookup()
        }
    } else if (findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 35) == 'Company') {
        'select company owner profession'
        WebUI.selectOptionByLabel(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/select_Owner Profession'), 
            findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 40), false)
    }
    
    if (GlobalVariable.Role == 'Testing') {
        'get IDType ddl value from db'
        ArrayList<String> IDType = CustomKeywords.'dbConnection.checkCustomer.checkIDTypeDDL'(sqlConnectionFOU)

        'get total label from ddl IDType'
        int totalddlIDType = WebUI.getNumberOfTotalOption(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/select_Owner Type Id'))

        'verify total ddl IDType confins = total ddl db'
        WebUI.verifyEqual(totalddlIDType, IDType.size())

        'verify isi ddl IDType confins = db'
        if (WebUI.verifyOptionsPresent(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/select_Owner Type Id'), 
            IDType) == false) {
            'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDDL'
            CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('7b.CollateralData', GlobalVariable.NumofCollateral, 
                GlobalVariable.StatusFailed, ((findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 
                    2) + ';') + GlobalVariable.ReasonFailedDDL) + 'IDType')

            (GlobalVariable.FlagFailed)++
        }
        
        'get copyaddr ddl value from db'
        ArrayList<String> copyaddr = CustomKeywords.'dbConnection.checkCustomer.checkCopyAddr'(sqlConnectionLOS, findTestData(
                excelPathCustomerPersonal).getValue(GlobalVariable.NumofColm, 13))

        'get total label from ddl copyaddr asset owner'
        int totalddlcopyaddrassetowner = WebUI.getNumberOfTotalOption(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/select_Address Type Owner'))

        'get total label from ddl copyaddr asset location'
        int totalddlcopyaddrassetlocation = WebUI.getNumberOfTotalOption(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/select_Address Type Location'))

        'verify total ddl totalddlcopyaddrassetowner confins = total ddl db'
        WebUI.verifyEqual(totalddlcopyaddrassetowner - 1, copyaddr.size())

        'verify total ddl ownerprofession confins = total ddl db'
        WebUI.verifyEqual(totalddlcopyaddrassetlocation - 1, copyaddr.size())

        'verify isi ddl copyaddr asset owner confins = db'
        if (WebUI.verifyOptionsPresent(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/select_Address Type Owner'), 
            copyaddr) == false) {
            'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDDL'
            CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('7b.CollateralData', GlobalVariable.NumofCollateral, 
                GlobalVariable.StatusFailed, ((findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 
                    2) + ';') + GlobalVariable.ReasonFailedDDL) + 'copyaddr asset owner')

            (GlobalVariable.FlagFailed)++
        }
        
        'verify isi ddl copyaddr asset location confins = db'
        if (WebUI.verifyOptionsPresent(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/select_Address Type Location'), 
            copyaddr) == false) {
            'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDDL'
            CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('7b.CollateralData', GlobalVariable.NumofCollateral, 
                GlobalVariable.StatusFailed, ((findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 
                    2) + ';') + GlobalVariable.ReasonFailedDDL) + 'copyaddr asset location')

            (GlobalVariable.FlagFailed)++
        }
    }
    
    'select id type'
    WebUI.selectOptionByLabel(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/select_Owner Type Id'), 
        findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 41), false)

    'input id no'
    WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/input_Owner Id No'), 
        findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 42))

    'input owner mobile no'
    WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/input_Owner Mobile Phone'), 
        findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 43))

    if (findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 45) == 'Yes') {
        'select copy address'
        WebUI.selectOptionByLabel(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/select_Address Type Owner'), 
            findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 46), false)

        'click button copy'
        WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/button_Copy Address Owner'))

        'call function get data asset owner address'
        getDataOwner()

        'call function asset location copy address'
        collateralLocCopy()
    } else if (findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 45) == 'No') {
        'input address'
        WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/input_Address Owner'), 
            findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 47))

        'input RT'
        WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/input_RT Owner'), 
            findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 48))

        'input RW'
        WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/input_RW Owner'), 
            findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 49))

        'click button Zipcode'
        WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/button_Lookup Zipcode Owner'))

        'input Zipcode'
        WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/input_Zipcode'), 
            findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 50))

        'input kelurahan'
        WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/input_Kelurahan'), 
            findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 51))

        'input Kecamatan'
        WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/input_Kecamatan'), 
            findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 52))

        'input Kota'
        WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/input_Kota'), 
            findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 53))

        'click search button'
        WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/button_Search'))

        'call function verify input lookup'
        verifyInputLookup()

        assetLocInput()
    }
}
}

if(findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 12).equalsIgnoreCase('New Collateral')){
	'click button save'
	WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/button_Save'))
}else if(findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 12).equalsIgnoreCase('Existing Collateral')){
	'click button save'
	WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/button_SaveExistinCol'))
}


Integer iscompleteMandatory = Integer.parseInt(findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 
        4))

if ((iscompleteMandatory == 0) && (GlobalVariable.FlagFailed == 0)) {
    'cek alert'
    GlobalVariable.FlagFailed = CustomKeywords.'checkSaveProcess.checkSaveProcess.checkAlert'(GlobalVariable.NumofCollateral, 
        '7b.CollateralData')
}

if (GlobalVariable.FlagFailed == 0) {
    'check save process write to excel'
    CustomKeywords.'checkSaveProcess.checkSaveProcess.checkStatus'(iscompleteMandatory, findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/button_AddCollateral'), 
        GlobalVariable.NumofCollateral, '7b.CollateralData')

    if (iscompleteMandatory == 0) {
        errorValObject = findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP1-CustomerData/TabCustomerData/div_errorvalidation')

        'cek validasi'
        CustomKeywords.'checkSaveProcess.checkSaveProcess.checkValidasi'(errorValObject, GlobalVariable.NumofCollateral, 
            '7b.CollateralData')
    }
}

if (WebUI.verifyElementPresent(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/button_Cancel'), 
    GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
    'click button cancel'
    WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/button_Cancel'))

    GlobalVariable.IsDataCancel = 1
} else {
    if (flagWarning > 0) {
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '7b.CollateralData', 0, GlobalVariable.NumofCollateral - 
            1, GlobalVariable.StatusWarning)
    }
    
    if ((GlobalVariable.Role == 'Testing') && (GlobalVariable.CheckVerifStoreDBPersonal == 'Yes')) {
		if(findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 12).equalsIgnoreCase('New Collateral')){
			
        'call test case store db asset data'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/FL4W/TabCollateralDataStoreDBVerif'), 
            [:], FailureHandling.CONTINUE_ON_FAILURE)
		}else if(findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 12).equalsIgnoreCase('Existing Collateral')){
		'call test case store db asset data'
		WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/FL4W/TabCollateralDataStoreDBVerif - Existing'),
			[:], FailureHandling.CONTINUE_ON_FAILURE)
		}
    }
}

def checkVerifyEqualOrMatch(Boolean isMatch) {
    if ((isMatch == false) && (GlobalVariable.FlagFailed == 0)) {
        'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedVerifyEqualOrMatch'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('7b.CollateralData', GlobalVariable.NumofCollateral, 
            GlobalVariable.StatusFailed, (findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 2) + 
            ';') + GlobalVariable.ReasonFailedVerifyEqualOrMatch)

        GlobalVariable.FlagFailed = 1
    }
}

def assetLocInput() {
    'input address'
    WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/input_Address Location'), 
        findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 47))

    'input RT'
    WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/input_RT Location'), 
        findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 48))

    'input RW'
    WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/input_RW Location'), 
        findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 49))

    'click button zipcode'
    WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/button_Zipcode Location'))

    'input Zipcode'
    WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/input_Zipcode'), 
        findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 50))

    'input kelurahan'
    WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/input_Kelurahan'), 
        findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 51))

    'input Kecamatan'
    WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/input_Kecamatan'), 
        findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 52))

    'input Kota'
    WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/input_Kota'), 
        findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 53))

    'click search button'
    WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/button_Search'))

    'call function verify input lookup'
    verifyInputLookup()
}

def collateralLocCopy() {
    'select copy address'
    WebUI.selectOptionByLabel(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/select_Address Type Location'), 
        findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 46), false)

    'click button copy'
    WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/button_Copy Location'))
}

def getDataOwner() {
    'declare array for confins data'
    def confinsdata = []

    if (findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 34) == 'Yes') {
        'add owner type to array'
        confinsdata.add('PERSONAL')

        'add owner name to array'
        confinsdata.add(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/input_Owner Name'), 
                'value'))

        Select ownerRelation = new Select(DriverFactory.getWebDriver().findElement(By.xpath('//*[@id="AddCollOwner"]/div[3]/div[1]/select')))

        Select ownerIdType = new Select(DriverFactory.getWebDriver().findElement(By.xpath('//*[@id="AddCollOwner"]/div[2]/div[2]/select')))

        String ownerRelationLabel = ownerRelation.getFirstSelectedOption().getText()

        String ownerIdTypeLabel = ownerIdType.getFirstSelectedOption().getText()

        'add owner relation to array'
        confinsdata.add(ownerRelationLabel)

        'add owner profession to array'
        confinsdata.add(WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/label_OwnerProfession')))

        'add owner id type to array'
        confinsdata.add(ownerIdTypeLabel)

        'add owner id no to array'
        confinsdata.add(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/input_Owner Id No'), 
                'value'))

        'add owner owner mobile no to array'
        confinsdata.add(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/input_Owner Mobile Phone'), 
                'value'))
    }
    
    'add address to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/input_Address Owner'), 
            'value'))

    'add RT to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/input_RT Owner'), 
            'value'))

    'add RW to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/input_RW Owner'), 
            'value'))

    if (findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 34) == 'No') {
        'modify Asset Owner Zipcode'
        modifyObejctCollateralOwnerZipcode = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/label_Zipcode Owner'), 
            'xpath', 'equals', '//*[@id="collOwnerAddress"]/div/div[2]/div/div[1]/div/lib-uclookupgeneric/div/div/input', 
            true)

        'add zipcode to array'
        confinsdata.add(WebUI.getAttribute(modifyObejctCollateralOwnerZipcode, 'value'))
    } else if (findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 34) == 'Yes') {
        'add zipcode to array'
        confinsdata.add(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/label_Zipcode Owner'), 
                'value'))
    }
    
    'add kelurahan to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/label_Kelurahan Owner'), 
            'value'))

    'add kecamatan to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/label_Kecamatan Owner'), 
            'value'))

    'add kota to array'
    confinsdata.add(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/label_Kota Owner'), 
            'value'))

    GlobalVariable.Confinsdata = confinsdata
}

def verifyInputLookup() {
    if (WebUI.verifyElementPresent(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/a_Select'), 
        GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
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

def checkDDL(Sql sqlConnectionFOU) {
    if (GlobalVariable.Role == 'Testing') {
        'Ambil array string (text) asset usage dari db'
        ArrayList<String> assetUsage = CustomKeywords.'assetData.checkAssetData.checkAssetUsageDDL'(sqlConnectionFOU)

        'Ambil array string (text) asset condition dari db'
        ArrayList<String> assetCondition = CustomKeywords.'assetData.checkAssetData.checkAssetConditionDDL'(sqlConnectionFOU)

        'Ambil nilai jumlah option/pilihan asset usage dari confins'
        Integer totalAssetUsage = WebUI.getNumberOfTotalOption(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/Select_Collateral Usage'))

        'Ambil nilai jumlah option/pilihan asset Condition dari confins'
        Integer totalAssetCondition = WebUI.getNumberOfTotalOption(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/Select_Collateral Condition'))

        'Verif jumlah asset usage yang muncul pada confins sesuai dengan jumlah asset usage pada db'
        if (WebUI.verifyEqual(totalAssetUsage, assetUsage.size()) == false) {
            'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDDL'
            CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('7.TabAssetData', GlobalVariable.NumofCollateral, 
                GlobalVariable.StatusFailed, ((findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 
                    2) + ';') + GlobalVariable.ReasonFailedDDL) + 'Jumlah Asset Usage')

            GlobalVariable.FlagFailed = 1
        }
        
        'Verif jumlah asset condition yang muncul pada confins sesuai dengan jumlah asset usage pada db'
        if (WebUI.verifyEqual(totalAssetCondition, assetCondition.size()) == false) {
            'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDDL'
            CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('7.TabAssetData', GlobalVariable.NumofCollateral, 
                GlobalVariable.StatusFailed, ((findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 
                    2) + ';') + GlobalVariable.ReasonFailedDDL) + 'Jumlah Asset Condition')

            GlobalVariable.FlagFailed = 1
        }
        
        'Verif dropdownlist asset usage yang muncul pada confins sesuai dengan array string asset usage dari db'
        if (WebUI.verifyOptionsPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/Select_Collateral Usage'), 
            assetUsage) == false) {
            'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDDL'
            CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('7b.CollateralData', GlobalVariable.NumofCollateral, 
                GlobalVariable.StatusFailed, ((findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 
                    2) + ';') + GlobalVariable.ReasonFailedDDL) + 'Value Asset Usage')

            GlobalVariable.FlagFailed = 1
        }
        
        'Verif dropdownlist asset condition yang muncul pada confins sesuai dengan array string asset condition dari db'
        if (WebUI.verifyOptionsPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabAssetData/Collateral/Select_Collateral Condition'), 
            assetCondition) == false) {
            'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDDL'
            CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('7b.CollateralData', GlobalVariable.NumofCollateral, 
                GlobalVariable.StatusFailed, ((findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 
                    2) + ';') + GlobalVariable.ReasonFailedDDL) + 'value Asset condition')

            GlobalVariable.FlagFailed = 1
        }
    }
}

def inputCollAttr(Sql sqlConnectionFOU){
	'count collateral attribute'
	int countCollateralAtrtibute = CustomKeywords.'customizeKeyword.getRowAssetAttribute.countRowCollateralAttribute'(sqlConnectionFOU,
		findTestData(excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 15))
	
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
}

def inputColDoc(Sql sqlConnectionFOU){
	'keyword untuk mencari nama-nama asset document yang muncul pada tab asset'
	ArrayList<String> docName = CustomKeywords.'assetData.checkAssetDoc.checkCollateralDocument'(sqlConnectionFOU, findTestData(
			excelPathCollateral).getValue(GlobalVariable.NumofCollateral, 14))
	
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
}
