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
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

'open Browser'
WebUI.openBrowser('')

WebUI.maximizeWindow()

'input web R3 LOS'
WebUI.navigateToUrl(findTestData('Login/Login').getValue(1, 3))

'input username'
WebUI.setText(findTestObject('LoginR3BranchManagerSuperuser/input_Loan Origination System_inputUser'), findTestData('Login/Login').getValue(
        2, 3))

'input Password'
WebUI.setText(findTestObject('LoginR3BranchManagerSuperuser/input_Loan Origination System_inputPass'), findTestData('Login/Login').getValue(
        3, 3))

'click button login'
WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/button_LOGIN'))

WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/NewLogin/input_Choose Your Role_Office'))

WebUI.selectOptionByLabel(findTestObject('LoginR3BranchManagerSuperuser/NewLogin/select_Role'), findTestData('Login/Login').getValue(
        4, 3), false)

WebUI.sendKeys(findTestObject('LoginR3BranchManagerSuperuser/NewLogin/select_Role'), Keys.chord(Keys.ENTER))

WebUI.click(findTestObject('Object Repository/LoginR3BranchManagerSuperuser/NewLogin/input_JobTitle'))

WebUI.selectOptionByLabel(findTestObject('Object Repository/LoginR3BranchManagerSuperuser/NewLogin/select_JobTitle'),findTestData('Login/Login').getValue(
        5, 3),false)

WebUI.sendKeys(findTestObject('Object Repository/LoginR3BranchManagerSuperuser/NewLogin/select_JobTitle'), Keys.chord(Keys.ENTER))

WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/NewLogin/button_Choose Role'))

'click menu consumer finance'
WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_Consumer Finance'))

'click menu new consumer finance'
WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_New Consumer Finance'))

'click menu customer main data'
WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA'))

