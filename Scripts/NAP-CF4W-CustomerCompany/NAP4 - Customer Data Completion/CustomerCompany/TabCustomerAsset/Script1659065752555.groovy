import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import java.awt.TexturePaintContext.Byte as Byte
import org.openqa.selenium.By as By
import org.openqa.selenium.WebElement as WebElement
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
import internal.GlobalVariable as GlobalVariable

GlobalVariable.FlagWarning = 0

GlobalVariable.FlagFailed = 0

'get data file path'
GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'(GlobalVariable.DataFileCustomerCompany)

ArrayList<Boolean> assettypefaileddelete = new ArrayList<Boolean>()

ArrayList<Boolean> faileddata = new ArrayList<Boolean>()

'declare data file Global variable'
GlobalVariable.FindDataFile = findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/CustomerAsset - Company - Customer')

def assettypearray = GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofColm, 13).split(';', -1)

def assetdescriptionarray = GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofColm, 14).split(';', -1)

def assetvaluearray = GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofColm, 15).split(';', -1)

def assetquantityarray = GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofColm, 16).split(';', -1)

'copyapp'
copyapp = findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerDataCompletion').getValue(GlobalVariable.NumofColm, 
    10)

if (copyapp.equalsIgnoreCase('Edit')) {
    if (WebUI.verifyElementPresent(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerAsset - Personal/buttonedit'), 
        5, FailureHandling.OPTIONAL)) {
        'count table customer asset row di confins'
        ArrayList<Boolean> variable = DriverFactory.getWebDriver().findElements(By.cssSelector('#CustomerAssetSection > div:nth-child(2) > table > tbody tr'))

        for (i = 1; i <= variable.size(); i++) {
            'modify object customer asset type'
            modifyNewcustomeassetType = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerAsset - Personal/td_assettype'), 
                'xpath', 'equals', ('//*[@id="CustomerAssetSection"]/div[2]/table/tbody/tr[' + i) + ']/td[1]', true)

            'modify object customer asset desc'
            modifyNewcustomeassetDesc = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerAsset - Personal/td_assettype'), 
                'xpath', 'equals', ('//*[@id="CustomerAssetSection"]/div[2]/table/tbody/tr[' + i) + ']/td[2]', true)

            for (asset = 1; asset <= assettypearray.size(); asset++) {
                'modify object button edit'
                modifyNewbuttonedit = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerAsset - Personal/buttonedit'), 
                    'xpath', 'equals', ('//*[@id="CustomerAssetSection"]/div[2]/table/tbody/tr[' + i) + ']/td[6]/a/i', true)

                'verify if asset type sama'
                if (WebUI.getText(modifyNewcustomeassetType).equalsIgnoreCase(assettypearray[(asset - 1)])) {
                    'click button edit'
                    WebUI.click(modifyNewbuttonedit)

					'call function input asset data'
					inputAssetData()
                    
                    break
                } else {
                    'modify object button delete'
                    modifyNewbuttondelete = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerAsset - Personal/buttondelete'), 
                        'xpath', 'equals', ('//*[@id="CustomerAssetSection"]/div[2]/table/tbody/tr[' + i) + ']/td[7]/a/i', 
                        true)

                    if (asset == assettypearray.size()) {
                        'get asset type sebelum delete'
                        assettypebefore = WebUI.getText(modifyNewcustomeassetType)

                        'get asset desc sebelum delete'
                        assetdescbefore = WebUI.getText(modifyNewcustomeassetDesc)

                        'click button delete'
                        WebUI.click(modifyNewbuttondelete)

                        'acceptalert'
                        WebUI.acceptAlert(FailureHandling.OPTIONAL)

                        if (i == variable.size()) {
                            if (WebUI.verifyElementNotPresent(modifyNewcustomeassetType, 5, FailureHandling.OPTIONAL)) {
                                'count ulang table pada confins'
                                variable = DriverFactory.getWebDriver().findElements(By.cssSelector('#CustomerAssetSection > div:nth-child(2) > table > tbody tr'))
                            } else {
                                'add asset type failed kedalam array'
                                assettypefaileddelete.add(assettypebefore + assetdescbefore)
                            }
                        } else {
                            'get asset type setelah delete'
                            assettypeafter = WebUI.getText(modifyNewcustomeassetType)

                            'get asset desc setelah delete'
                            assetdescAfter = WebUI.getText(modifyNewcustomeassetDesc)

                            if (WebUI.verifyNotMatch(assettypeafter, assettypebefore, false, FailureHandling.OPTIONAL) && 
                            WebUI.verifyNotMatch(assetdescAfter, assetdescbefore, false, FailureHandling.OPTIONAL)) {
                                'count ulang table pada confins'
                                variable = DriverFactory.getWebDriver().findElements(By.cssSelector('#CustomerAssetSection > div:nth-child(2) > table > tbody tr'))
                            } else {
                                'add asset type failed kedalam array'
                                assettypefaileddelete.add(assettypebefore + assetdescbefore)

                                continue
                            }
                        }
                        
                        i--
                    }
                }
            }
        }
    }
    
    if (assettypefaileddelete.size() > 0) {
        'write to excel status warning'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '5.CustomerAsset', 0, GlobalVariable.NumofColm - 
            1, GlobalVariable.StatusWarning)

        'write to excel reason failed delete'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '5.CustomerAsset', 1, GlobalVariable.NumofColm - 
            1, GlobalVariable.ReasonFailedDelete + assettypefaileddelete)

        (GlobalVariable.FlagWarning)++
    }
    
    'count ulang table customer asset row di confins'
    variable = DriverFactory.getWebDriver().findElements(By.cssSelector('#CustomerAssetSection > div:nth-child(2) > table > tbody tr'))

    for (asset = 1; asset <= assettypearray.size(); asset++) {
        for (i = 1; i <= variable.size(); i++) {
            'modify object customer asset type'
            modifyNewcustomeassetType = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerAsset - Personal/td_assettype'), 
                'xpath', 'equals', ('//*[@id="CustomerAssetSection"]/div[2]/table/tbody/tr[' + i) + ']/td[1]', true)

            'verify if asset type beda'
            if (!(WebUI.getText(modifyNewcustomeassetType).equalsIgnoreCase(assettypearray[(asset - 1)]) || WebUI.verifyElementNotPresent(
                modifyNewcustomeassetType, 5, FailureHandling.OPTIONAL))) {
                if (i == variable.size()) {
                    'click button add'
                    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerAsset - Personal/button_Add'))

					'call function input asset data'
                    inputAssetData()
                }
            } else if (WebUI.getText(modifyNewcustomeassetType).equalsIgnoreCase(assettypearray[(asset - 1)])) {
                break
            }
        }
    }
} else if (copyapp.equalsIgnoreCase('No')) {
    if (GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofColm, 12) == 'Yes') {
        for (asset = 1; asset <= assettypearray.size(); asset++) {
            'click button add'
            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerAsset - Personal/button_Add'))

            'call function input asset data'
            inputAssetData()
        }
    }
}

'click button save and continue'
WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerAsset - Personal/button_Save  Continue'))

if ((Integer.parseInt(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofColm, 4)) == 0)) {
    'Check alert'
    GlobalVariable.FlagFailed = CustomKeywords.'checkSaveProcess.checkSaveProcess.checkAlert'(GlobalVariable.NumofColm, 
        '5.CustomerAsset')
}

if (GlobalVariable.FlagFailed == 0) {
    'Check save Process write to excel'
    CustomKeywords.'checkSaveProcess.checkSaveProcess.checkStatus'(Integer.parseInt(GlobalVariable.FindDataFile.getValue(
                GlobalVariable.NumofColm, 4)), findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/button_Debtor Group_'), 
        GlobalVariable.NumofColm, '5.CustomerAsset')

    if (Integer.parseInt(GlobalVariable.FindDataFile.getValue(GlobalVariable.NumofColm, 4)) == 0) {
        'Check error validasi'
        CustomKeywords.'checkSaveProcess.checkSaveProcess.checkValidasi'(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/errorvalidasi'), 
            GlobalVariable.NumofColm, '5.CustomerAsset')
    }
}

'check if flagwarning > 0'
if (GlobalVariable.FlagWarning > 0) {
    'write to excel status warning'
    CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '5.CustomerAsset', 0, GlobalVariable.NumofColm - 
        1, GlobalVariable.StatusWarning)

    'wrtie to excel reason failed'
    CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '5.CustomerAsset', 1, GlobalVariable.NumofColm - 
        1, GlobalVariable.ReasonFailedInputData + faileddata)
}

'check if th customer asset is present'
if (WebUI.verifyElementPresent(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerAsset - Personal/th_Customer Asset Type'), 
    5, FailureHandling.OPTIONAL)) {
    'click button back'
    WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerAsset - Personal/button_Back'))
}

'check if role == testing & store DB = Yes'
if ((GlobalVariable.RoleCompany == 'Testing') && (GlobalVariable.CheckVerifStoreDBCompany == 'Yes')) {
    GlobalVariable.NumofVerifStore = GlobalVariable.NumofColm

    'Call test case verify customer asset store data'
    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/NAP4VerifyStoreData/Company/TabCustomerAssetVerifStoreDataDB'), 
        [:], FailureHandling.CONTINUE_ON_FAILURE)
}

def inputAssetData(){
	'pilih asset type'
	WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerAsset - Personal/select_MobilMotorRumah'),
		assettypearray[(asset - 1)], false)

	'input asset description'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerAsset - Personal/input_Asset Description_'),
		assetdescriptionarray[(asset - 1)])

	'input asset value'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerAsset - Personal/input_Asset Value_'),
		assetvaluearray[(asset - 1)])

	'input asset quantity'
	WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerAsset - Personal/input_Asset Quantity_'),
		assetquantityarray[(asset - 1)])

	'click button save'
	WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerAsset - Personal/button_Save'))

	if (WebUI.verifyElementPresent(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/CustomerAsset - Company/button_Cancel'),
		5, FailureHandling.OPTIONAL)) {
		'click button cancel'
		WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/CustomerAsset - Company/button_Cancel'))

		'add failed delete asset to array'
		faileddata.add(assettypearray[(asset - 1)])

		'flagwarning +1'
		(GlobalVariable.FlagWarning)++
	}
}