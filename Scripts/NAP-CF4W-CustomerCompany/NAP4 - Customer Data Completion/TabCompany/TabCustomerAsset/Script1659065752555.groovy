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

if(GlobalVariable.NAP4 == 'CUSTOMER'){
	'get data file path'
	GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'(GlobalVariable.DataFileCustomerCompany)
	
	'declare data file Global variable'
	GlobalVariable.FindDataFile = findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerCompany/CustomerAsset')
}else if(GlobalVariable.NAP4 == 'SHAREHOLDER COMPANY'){
	'get data file path'
	GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'(GlobalVariable.DataFileManagementShareholderCompany)
			
	'declare data file Global variable'
	GlobalVariable.FindDataFile = findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementshareholderCompany/CustomerAsset')
}else if(GlobalVariable.NAP4 == 'GUARANTOR COMPANY'){
	'get data file path'
	GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'(GlobalVariable.DataFileGuarantorCompanyCompany)
	
	'declare data file Global variable'
	GlobalVariable.FindDataFile = findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/GuarantorCompany/CustomerAsset')
}

ArrayList<String> assettypefaileddelete = new ArrayList<>()

ArrayList<String> faileddata = new ArrayList<>()

ArrayList<String> assettypearray = GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 13).split(';', -1)

ArrayList<String> assetdescriptionarray = GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 14).split(';', -1)

ArrayList<String> assetvaluearray = GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 15).split(';', -1)

ArrayList<String> assetquantityarray = GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 16).split(';', -1)

'copyapp'
copyapp = findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerDataCompletion').getValue(GlobalVariable.ColmNAP4, 
    10)

if (copyapp.equalsIgnoreCase('Edit')) {
    if (WebUI.verifyNotMatch(WebUI.getText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/CustomerAsset/td_assettype')), 'NO DATA AVAILABLE', false, FailureHandling.OPTIONAL)) {
        'count table customer asset row di confins'
        ArrayList<Boolean> variable = DriverFactory.getWebDriver().findElements(By.cssSelector('#CustomerAssetSection > div:nth-child(2) > table > tbody tr'))

        for (i = 1; i <= variable.size(); i++) {
            'modify object customer asset type'
            modifyNewcustomeassetType = WebUI.modifyObjectProperty(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/CustomerAsset/td_assettype'), 
                'xpath', 'equals', ('//*[@id="CustomerAssetSection"]/div[2]/table/tbody/tr[' + i) + ']/td[1]', true)

            'modify object customer asset desc'
            modifyNewcustomeassetDesc = WebUI.modifyObjectProperty(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/CustomerAsset/td_assettype'), 
                'xpath', 'equals', ('//*[@id="CustomerAssetSection"]/div[2]/table/tbody/tr[' + i) + ']/td[2]', true)
			
            for (asset = 1; asset <= assettypearray.size(); asset++) {
                
                'verify if asset type sama'
                if (WebUI.getText(modifyNewcustomeassetType).equalsIgnoreCase(assettypearray[(asset - 1)]) && WebUI.getText(modifyNewcustomeassetDesc).equalsIgnoreCase(assetdescriptionarray[(asset - 1)])) {
					
					'modify object button edit'
					modifyNewbuttonedit = WebUI.modifyObjectProperty(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/CustomerAsset/buttonedit'),
						'xpath', 'equals', ('//*[@id="CustomerAssetSection"]/div[2]/table/tbody/tr[' + i) + ']/td[6]/a/i', true)
					
                    'click button edit'
                    WebUI.click(modifyNewbuttonedit)

					'call function input asset data'
					inputAssetData()
                    
                    break
                } else {
                    if (asset == assettypearray.size()) {
						'modify object button delete'
						modifyNewbuttondelete = WebUI.modifyObjectProperty(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/CustomerAsset/buttondelete'),
							'xpath', 'equals', ('//*[@id="CustomerAssetSection"]/div[2]/table/tbody/tr[' + i) + ']/td[7]/a/i',
							true)
						
                        'get asset type sebelum delete'
                        assettypebefore = WebUI.getText(modifyNewcustomeassetType)

                        'get asset desc sebelum delete'
                        assetdescbefore = WebUI.getText(modifyNewcustomeassetDesc)

                        'click button delete'
                        WebUI.click(modifyNewbuttondelete)

                        'acceptalert'
                        WebUI.acceptAlert(FailureHandling.OPTIONAL)

                        if (i == variable.size()) {
                            if (WebUI.verifyElementPresent(modifyNewcustomeassetType, GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                                'add asset type failed kedalam array'
                                assettypefaileddelete.add(assettypebefore + assetdescbefore)
                            }
                        } else {
                            'get asset type setelah delete'
                            assettypeafter = WebUI.getText(modifyNewcustomeassetType)

                            'get asset desc setelah delete'
                            assetdescAfter = WebUI.getText(modifyNewcustomeassetDesc)

                            if (WebUI.verifyMatch(assettypeafter, assettypebefore, false, FailureHandling.OPTIONAL) && 
                            WebUI.verifyMatch(assetdescAfter, assetdescbefore, false, FailureHandling.OPTIONAL)) {
                                'add asset type failed kedalam array'
                                assettypefaileddelete.add(assettypebefore + assetdescbefore)
                            }
                        }
						
						'count ulang table pada confins'
						variable = DriverFactory.getWebDriver().findElements(By.cssSelector('#CustomerAssetSection > div:nth-child(2) > table > tbody tr'))
                        
                        i--
                    }
                }
            }
			
			'verify jika table confins no data maka looping akan di skip'
			if(WebUI.getText(modifyNewcustomeassetType).equalsIgnoreCase('NO DATA AVAILABLE')){
				break
			}
        }
    }
    
    if (assettypefaileddelete.size() > 0) {
		
		'Write To Excel GlobalVariable.StatusWarning and GlobalVariable.ReasonFailedDelete'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('5.CustomerAsset', GlobalVariable.ColmNAP4, GlobalVariable.StatusWarning, GlobalVariable.ReasonFailedDelete + assettypefaileddelete)

        (GlobalVariable.FlagWarning)++
    }
    
    'count ulang table customer asset row di confins'
    variable = DriverFactory.getWebDriver().findElements(By.cssSelector('#CustomerAssetSection > div:nth-child(2) > table > tbody tr'))
	
    for (asset = 1; asset <= assettypearray.size(); asset++) {
        for (i = 1; i <= variable.size(); i++) {
            'modify object customer asset type'
            modifyNewcustomeassetType = WebUI.modifyObjectProperty(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/CustomerAsset/td_assettype'), 
                'xpath', 'equals', ('//*[@id="CustomerAssetSection"]/div[2]/table/tbody/tr[' + i) + ']/td[1]', true)
			
			'modify object customer asset desc'
			modifyNewcustomeassetDesc = WebUI.modifyObjectProperty(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/CustomerAsset/td_assettype'),
				'xpath', 'equals', ('//*[@id="CustomerAssetSection"]/div[2]/table/tbody/tr[' + i) + ']/td[2]', true)

            'verify if asset type beda'
            if (!(WebUI.getText(modifyNewcustomeassetType).equalsIgnoreCase(assettypearray[(asset - 1)]) && WebUI.getText(modifyNewcustomeassetDesc).equalsIgnoreCase(assetdescriptionarray[(asset - 1)])) || WebUI.verifyElementNotPresent(
                modifyNewcustomeassetType, GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                if (i == variable.size()) {
                    'click button add'
                    WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/CustomerAsset/button_Add'))

					'call function input asset data'
                    inputAssetData()
					
					'count ulang table customer asset setelah add customer asset baru'
					variable = DriverFactory.getWebDriver().findElements(By.cssSelector('#CustomerAssetSection > div:nth-child(2) > table > tbody tr'))
                }
            } else if (WebUI.getText(modifyNewcustomeassetType).equalsIgnoreCase(assettypearray[(asset - 1)]) && WebUI.getText(modifyNewcustomeassetDesc).equalsIgnoreCase(assetdescriptionarray[(asset - 1)])) {
                break
            }
        }
    }
} else if (copyapp.equalsIgnoreCase('No')) {
    if (GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 12) == 'Yes') {
        for (asset = 1; asset <= assettypearray.size(); asset++) {
            'click button add'
            WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/CustomerAsset/button_Add'))

            'call function input asset data'
            inputAssetData()
        }
    }
}

'click button save and continue'
WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/CustomerAsset/button_Save  Continue'))

if ((Integer.parseInt(GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 4)) == 0)) {
    'Check alert'
    GlobalVariable.FlagFailed = CustomKeywords.'checkSaveProcess.checkSaveProcess.checkAlert'(GlobalVariable.ColmNAP4, 
        '5.CustomerAsset')
}

if (GlobalVariable.FlagFailed == 0) {
    'Check save Process write to excel'
    CustomKeywords.'checkSaveProcess.checkSaveProcess.checkStatus'(Integer.parseInt(GlobalVariable.FindDataFile.getValue(
                GlobalVariable.ColmNAP4, 4)), findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion/CustomerCompany/OtherAttribute/button_Debtor Group_'), 
        GlobalVariable.ColmNAP4, '5.CustomerAsset')

    if (Integer.parseInt(GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 4)) == 0) {
        'Check error validasi'
        CustomKeywords.'checkSaveProcess.checkSaveProcess.checkValidasi'(findTestObject('NAP/NAP4-CustomerDataCompletion/errorvalidasi'), 
            GlobalVariable.ColmNAP4, '5.CustomerAsset')
    }
}

'check if flagwarning > 0'
if (GlobalVariable.FlagWarning > 0) {

	'Write To Excel GlobalVariable.StatusWarning and GlobalVariable.ReasonFailedInputData'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('5.CustomerAsset', GlobalVariable.ColmNAP4, GlobalVariable.StatusWarning, GlobalVariable.ReasonFailedInputData + faileddata)
}

'check if th customer asset is present'
if (WebUI.verifyElementPresent(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/CustomerAsset/th_Customer Asset Type'), 
    GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
    'click button back'
    WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerDataCompletion/button_Back'))
}

'check if role == testing & store DB = Yes'
if ((GlobalVariable.RoleCompany == 'Testing') && (GlobalVariable.CheckVerifStoreDBCompany == 'Yes')) {
    GlobalVariable.NumofVerifStore = GlobalVariable.ColmNAP4

    'Call test case verify customer asset store data'
    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/NAP4VerifyStoreData/Company/TabCustomerAssetVerifStoreDataDB'), 
        [:], FailureHandling.CONTINUE_ON_FAILURE)
}

def inputAssetData(){
	ArrayList<String> assettypearray = GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 13).split(';', -1)
	
	ArrayList<String> assetdescriptionarray = GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 14).split(';', -1)
	
	ArrayList<String> assetvaluearray = GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 15).split(';', -1)
	
	ArrayList<String> assetquantityarray = GlobalVariable.FindDataFile.getValue(GlobalVariable.ColmNAP4, 16).split(';', -1)
	
	'pilih asset type'
	WebUI.selectOptionByLabel(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/CustomerAsset/select_MobilMotorRumah'),
		assettypearray[(asset - 1)], false)

	'input asset description'
	WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/CustomerAsset/input_Asset Description'),
		assetdescriptionarray[(asset - 1)])

	'input asset value'
	WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/CustomerAsset/input_Asset Value'),
		assetvaluearray[(asset - 1)])

	'input asset quantity'
	WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/CustomerAsset/input_Asset Quantity'),
		assetquantityarray[(asset - 1)])

	'click button save'
	WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/CustomerAsset/button_Save'))

	if (WebUI.verifyElementPresent(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/CustomerAsset/button_Cancel'),
		GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
		'click button cancel'
		WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/CustomerAsset/button_Cancel'))

		'add failed delete asset to array'
		faileddata.add(assettypearray[(asset - 1)])

		'flagwarning +1'
		(GlobalVariable.FlagWarning)++
	}
}