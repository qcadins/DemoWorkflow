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

'Custom keyword untuk mengatur path file yang akan didownload ke dalam folder project'
CustomKeywords.'dbconnection.DownloadRule.setDownloadPath'()

'open Browser'
WebUI.openBrowser('')

'Maximize window'
WebUI.maximizeWindow()

'input web Rule '
WebUI.navigateToUrl(findTestData('Login/Login').getValue(1,3))

'input username'
WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/input_Username'), 
    findTestData('Login/Login').getValue(2,3))

'input Password'
WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/input_password'), 
    findTestData('Login/Login').getValue(3,3))

'click button login'
WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_login'))

'Pilih role (action)'
WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_actionRolePick'))

'Klik rule file'
WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/a_RuleFile'))

Integer countFileCode = Integer.parseInt(GlobalVariable.CountFileCode)

for(int i=1;i<=countFileCode;i++){
	
	'Input file code'
	WebUI.setText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/input_FileCode'), 
	    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DownloadRuleFileCode').getValue(1,i))
	
	'Klik button search'
	WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/button_searchRule'))
	
	'Ambil filename dari web rule'
	String filenameWithExtension = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/span_FileName'))
	
	'Menentukan nama baru file hasil download'
	String newFileName = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/DownloadRuleFileCode').getValue(2,i)
	
	'Custom keyword untuk cek apakah ada file lama hasil download yang memiliki nama yang sama dengan file baru hasil download. Jika ada, delete file yang lama'
	CustomKeywords.'dbconnection.DownloadRule.deleteFile'(newFileName)
	
	'Klik download'
	WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/DuplicateChecking/a_Download'))
	
	'Delay 5 detik untuk menunggu proses download selesai (parameterize)'
	WebUI.delay(5)
	
	'Custom keyword untuk rename file hasil download dengan nama yang baru yang sudah diset sebelumnya'
	CustomKeywords.'dbconnection.DownloadRule.renameFile'(filenameWithExtension,newFileName)



}
