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

int flagWarning = 0

GlobalVariable.FlagFailed = 0

String userDir = System.getProperty('user.dir')

String filePath = userDir + GlobalVariable.DataFileManagementShareholderPersonal

GlobalVariable.DataFilePath = filePath

GlobalVariable.findDataFile = findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderPersonal/CustomerAsset - Company - ManagementShareholderPersonal')

def assettypearray = findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderPersonal/CustomerAsset - Company - ManagementShareholderPersonal').getValue(
    GlobalVariable.NumofFamily, 13).split(';', -1)

def assetdescriptionarray = findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderPersonal/CustomerAsset - Company - ManagementShareholderPersonal').getValue(
    GlobalVariable.NumofFamily, 14).split(';', -1)

def assetvaluearray = findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderPersonal/CustomerAsset - Company - ManagementShareholderPersonal').getValue(
    GlobalVariable.NumofFamily, 15).split(';', -1)

def assetquantityarray = findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderPersonal/CustomerAsset - Company - ManagementShareholderPersonal').getValue(
    GlobalVariable.NumofFamily, 16).split(';', -1)

'copyapp'
copyapp = findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerDataCompletion').getValue(GlobalVariable.NumofColm, 
    10)

ArrayList<WebElement> variable

if (copyapp.equalsIgnoreCase('Edit')) {
    if (WebUI.verifyElementPresent(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerAsset - Personal/buttonedit'), 
        5, FailureHandling.OPTIONAL)) {
        variable = DriverFactory.getWebDriver().findElements(By.cssSelector('#CustomerAssetSection > div:nth-child(2) > table > tbody tr'))

        for (i = 1; i <= variable.size(); i++) {
            'modify object customer asset type'
            modifyNewcustomeassetType = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerAsset - Personal/td_assettype'), 
                'xpath', 'equals', ('//*[@id="CustomerAssetSection"]/div[2]/table/tbody/tr[' + i) + ']/td[1]', true)

            'modify object button edit'
            modifyNewbuttonedit = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerAsset - Personal/buttonedit'), 
                'xpath', 'equals', ('//*[@id="CustomerAssetSection"]/div[2]/table/tbody/tr[' + i) + ']/td[6]/a/i', true)

            'modify object button delete'
            modifyNewbuttondelete = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerAsset - Personal/buttondelete'), 
                'xpath', 'equals', ('//*[@id="CustomerAssetSection"]/div[2]/table/tbody/tr[' + i) + ']/td[7]/a/i', true)

            for (asset = 1; asset <= assettypearray.size(); asset++) {
                if (WebUI.verifyElementPresent(modifyNewbuttonedit, 5, FailureHandling.OPTIONAL)) {
                    'verify if asset type sama'
                    if (WebUI.getText(modifyNewcustomeassetType).equalsIgnoreCase(assettypearray[(asset - 1)])) {
                        'click button edit'
                        WebUI.click(modifyNewbuttonedit)

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

                            flagWarning++
                        }
                        
                        break
                    } else {
                        if (asset == assettypearray.size()) {
                            'click button delete'
                            WebUI.click(modifyNewbuttondelete)

                            'acceptalert'
                            WebUI.acceptAlert(FailureHandling.OPTIONAL)

                            i--
                        }
                    }
                } else {
                    break
                }
            }
        }
    }
    
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

                        flagWarning++
                    }
                }
            } else if (WebUI.getText(modifyNewcustomeassetType).equalsIgnoreCase(assettypearray[(asset - 1)])) {
                break
            }
        }
    }
} else if (copyapp.equalsIgnoreCase('No')) {
    if (findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderPersonal/CustomerAsset - Company - ManagementShareholderPersonal').getValue(
        GlobalVariable.NumofFamily, 12) == 'Yes') {
        for (asset = 1; asset <= assettypearray.size(); asset++) {
            'click button add'
            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerAsset - Personal/button_Add'))

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

                flagWarning++
            }
        }
    }
}

'click button save and continue'
WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerAsset - Personal/button_Save  Continue'))

if (Integer.parseInt(findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderPersonal/CustomerAsset - Company - ManagementShareholderPersonal').getValue(
        GlobalVariable.NumofFamily, 4)) == 0) {
    'Check alert'
    CustomKeywords.'checkSaveProcess.checkSaveProcess.checkAlert'(GlobalVariable.NumofFamily, '6.CustomerAsset')
}

if (GlobalVariable.FlagFailed == 0) {
    'Check save Process write to excel'
    CustomKeywords.'checkSaveProcess.checkSaveProcess.checkStatus'(Integer.parseInt(findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderPersonal/CustomerAsset - Company - ManagementShareholderPersonal').getValue(
                GlobalVariable.NumofFamily, 4)), findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/OtherAttribute - Personal/button_Debtor Group_'), 
        GlobalVariable.NumofFamily, '6.CustomerAsset')

    if (Integer.parseInt(findTestData('NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/ManagementShareholderPersonal/CustomerAsset - Company - ManagementShareholderPersonal').getValue(
            GlobalVariable.NumofFamily, 4)) == 0) {
        'Check error validasi'
        CustomKeywords.'checkSaveProcess.checkSaveProcess.checkValidasi'(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/errorvalidasi'), 
            GlobalVariable.NumofFamily, '6.CustomerAsset')
    }
}

if (flagWarning > 0) {
    CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '6.CustomerAsset', 0, GlobalVariable.NumofFamily - 
        1, GlobalVariable.StatusWarning)
}

if (WebUI.verifyElementPresent(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerAsset - Personal/th_Customer Asset Type'), 
    5, FailureHandling.OPTIONAL)) {
    'click button back'
    WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP4-CustomerDataCompletion-Company/CustomerPersonal/CustomerAsset - Personal/button_Back'))
}

if(GlobalVariable.RoleCompany == 'Testing' && GlobalVariable.CheckVerifStoreDBPersonalCompany=="Yes"){
GlobalVariable.NumofVerifStore = GlobalVariable.NumofFamily
		
'call test case verify customer asset store data'
WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP4 - Customer Data Completion/NAP4VerifyStoreData/Personal/TabCustomerAssetVerifStoreDataDB'), 
    [:], FailureHandling.CONTINUE_ON_FAILURE)
}

