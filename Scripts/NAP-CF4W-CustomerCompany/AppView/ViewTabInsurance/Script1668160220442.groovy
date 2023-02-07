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
GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'(GlobalVariable.PathAppInquiryCompany)

GlobalVariable.FlagWarning = 0

'connect DB los'
Sql sqlconnection = CustomKeywords.'dbConnection.connectDB.connectLOS'()

'click tab Insurance'
WebUI.click(findTestObject('Object Repository/AppView/Insurance/InsuranceTab'))

'Verif tidak ada alert yang muncul'
if(WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerPersonal/div_erroralert'), GlobalVariable.TimeOut)==false){
	GlobalVariable.FlagWarning = 1
	'write to excel status warning'
	CustomKeywords.'checkSaveProcess.checkSaveProcess.writeWarningAppView'(GlobalVariable.NumofColm,'7. Insurance')
}

'get appno from confins'
appno = WebUI.getText(findTestObject('Object Repository/AppView/MainInformation/Label App No'))

'get insurance data from db'
ArrayList<String> resultInsurance = CustomKeywords.'appView.verifyAppView.checkNewInsurance'(sqlconnection, appno)

index = 0

'verify asset name'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/NewInsurance/AssetName')).toUpperCase(),
		(resultInsurance[index++]).toUpperCase(), false))

'verify insured by'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/NewInsurance/InsuredBy')).toUpperCase(),
		(resultInsurance[index++]).toUpperCase(), false))

'verify paid by'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/NewInsurance/PaidBy')).toUpperCase(),
		(resultInsurance[index++]).toUpperCase(), false))

'verify insurance company'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/NewInsurance/InsCo')).toUpperCase(),
		(resultInsurance[index++]).toUpperCase(), false))

'verify payment type'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/NewInsurance/PaymentType')).replace(',','').toUpperCase(),
		(resultInsurance[index++]).toUpperCase(), false))

'verify cover period'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/NewInsurance/CoverPeriod')).toUpperCase(),
		(resultInsurance[index++]).toUpperCase(), false))

'verify insurance length'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/NewInsurance/InsuranceLength')).toUpperCase(),
		(resultInsurance[index++]).toUpperCase(), false))

'verify main premi'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/NewInsurance/MainPremi')).replace(",","").toUpperCase(),
		(resultInsurance[index++]).toUpperCase(), false))

'verify additional premi'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/NewInsurance/AdditionalPremi')).replace(",","").toUpperCase(),
		(resultInsurance[index++]).toUpperCase(), false))

'verify fee cust'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/NewInsurance/CustomerAdminFee')).replace(",","").toUpperCase(),
		(resultInsurance[index++]).toUpperCase(), false))

'Klik Button View Detail'
WebUI.click(findTestObject('Object Repository/AppView/Insurance/NewInsurance/ButtonViewDetail'))

'get insured by from db'
String resultInsuredBy = CustomKeywords.'appView.verifyAppView.checkInsuredBy'(sqlconnection, appno)

'Pengecekan insured by'
if(resultInsuredBy.equalsIgnoreCase('Customer')){
	'get arraylist Insurance Customer from db'
	ArrayList<String> resultInsuranceCustomer = CustomKeywords.'appView.verifyAppView.checkInsuranceCustomer'(sqlconnection, appno)
	
	index = 0
	
	'verify insured by'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/NewInsurance/ViewDetail/Cust/InsuredBy')).toUpperCase(),
			(resultInsuranceCustomer[index++]).toUpperCase(), false))
	
	'verify policy no'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/NewInsurance/ViewDetail/Cust/PolicyNo')).toUpperCase(),
			(resultInsuranceCustomer[index++]).toUpperCase(), false))
	
	'verify insurance branch name'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/NewInsurance/ViewDetail/Cust/InscoBranchName')).toUpperCase(),
			(resultInsuranceCustomer[index++]).toUpperCase(), false))
	
	'verify policy name'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/NewInsurance/ViewDetail/Cust/PolicyName')).toUpperCase(),
			(resultInsuranceCustomer[index++]).toUpperCase(), false))
	
	'verify cvg amount'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/NewInsurance/ViewDetail/Cust/cvgAmt')).replace(',','').toUpperCase(),
			(resultInsuranceCustomer[index++]).toUpperCase(), false))
	
	'verify start date'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/NewInsurance/ViewDetail/Cust/startDate')).toUpperCase(),
			(resultInsuranceCustomer[index++]).toUpperCase(), false))
	
	'verify insurance note'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/NewInsurance/ViewDetail/Cust/InsNote')).toUpperCase(),
			(resultInsuranceCustomer[index++]).toUpperCase(), false))
	
	'verify end date'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/NewInsurance/ViewDetail/Cust/endDate')).toUpperCase(),
			(resultInsuranceCustomer[index++]).toUpperCase(), false))
	
}else if(resultInsuredBy.equalsIgnoreCase('Multifinance')){

'get arraylist Insurance Multifinance from db'
ArrayList<String> resultInsuranceMultifinance = CustomKeywords.'appView.verifyAppView.checkInsuranceMultifinance'(sqlconnection, appno)

index = 0

'verify insured by'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/NewInsurance/ViewDetail/MF/Insuredby')).toUpperCase(),
		(resultInsuranceMultifinance[index++]).toUpperCase(), false))

'verify isnurance paid by'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/NewInsurance/ViewDetail/MF/PaidBy')).toUpperCase(),
		(resultInsuranceMultifinance[index++]).toUpperCase(), false))

'verify asset region'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/NewInsurance/ViewDetail/MF/AssetReg')).toUpperCase(),
		(resultInsuranceMultifinance[index++]).toUpperCase(), false))

'verify insurance company'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/NewInsurance/ViewDetail/MF/Insco')).toUpperCase(),
		(resultInsuranceMultifinance[index++]).toUpperCase(), false))

'verify payment type'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/NewInsurance/ViewDetail/MF/PaymentType')).toUpperCase(),
		(resultInsuranceMultifinance[index++]).toUpperCase(), false))

'verify cover period'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/NewInsurance/ViewDetail/MF/CoverPeriod')).toUpperCase(),
		(resultInsuranceMultifinance[index++]).toUpperCase(), false))

'verify coverage amount'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/NewInsurance/ViewDetail/MF/CvgAmt')).replace(',','').toUpperCase(),
		(resultInsuranceMultifinance[index++]).toUpperCase(), false))

'verify insurance length'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/NewInsurance/ViewDetail/MF/InsLength')).toUpperCase(),
		(resultInsuranceMultifinance[index++]).toUpperCase(), false))

'verify insurance company admin fee'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/NewInsurance/ViewDetail/MF/InscoAdmin')).replace(',','').toUpperCase(),
		(resultInsuranceMultifinance[index++]).toUpperCase(), false))

'verify insurance company stampduty fee'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/NewInsurance/ViewDetail/MF/InscoStampduty')).replace(',','').toUpperCase(),
		(resultInsuranceMultifinance[index++]).toUpperCase(), false))

'verify isnurance note'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/NewInsurance/ViewDetail/MF/InsNote')).toUpperCase(),
		(resultInsuranceMultifinance[index++]).toUpperCase(), false))





}else if(resultInsuredBy.equalsIgnoreCase('Customer - Multifinance')){

'get arraylist Insurance Cust-Multifinance from db'
ArrayList<String> resultInsuranceHybrid = CustomKeywords.'appView.verifyAppView.checkInsuranceCustMf'(sqlconnection, appno)

index = 0

'verify insured by'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/NewInsurance/ViewDetail/CustMF/InsuredBy')).toUpperCase(),
		(resultInsuranceHybrid[index++]).toUpperCase(), false))

'verify policy no'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/NewInsurance/ViewDetail/CustMF/PolicyNo')).toUpperCase(),
		(resultInsuranceHybrid[index++]).toUpperCase(), false))

'verify insurance branch name'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/NewInsurance/ViewDetail/CustMF/InscoBranchName')).toUpperCase(),
		(resultInsuranceHybrid[index++]).toUpperCase(), false))

'verify policy name'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/NewInsurance/ViewDetail/CustMF/PolicyName')).toUpperCase(),
		(resultInsuranceHybrid[index++]).toUpperCase(), false))

'verify cvg amount'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/NewInsurance/ViewDetail/CustMF/CvgAmt')).replace(',','').toUpperCase(),
		(resultInsuranceHybrid[index++]).toUpperCase(), false))

'verify start date'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/NewInsurance/ViewDetail/CustMF/StartDate')).toUpperCase(),
		(resultInsuranceHybrid[index++]).toUpperCase(), false))

'verify insurance note'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/NewInsurance/ViewDetail/CustMF/InsuranceNotes')).toUpperCase(),
		(resultInsuranceHybrid[index++]).toUpperCase(), false))

'verify end date'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/NewInsurance/ViewDetail/CustMF/EndDate')).toUpperCase(),
		(resultInsuranceHybrid[index++]).toUpperCase(), false))

'verify isnurance paid by'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/NewInsurance/ViewDetail/CustMF/PaidBy')).toUpperCase(),
		(resultInsuranceHybrid[index++]).toUpperCase(), false))

'verify asset region'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/NewInsurance/ViewDetail/CustMF/AssetReg')).toUpperCase(),
		(resultInsuranceHybrid[index++]).toUpperCase(), false))

'verify insurance company'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/NewInsurance/ViewDetail/CustMF/InsCo')).toUpperCase(),
		(resultInsuranceHybrid[index++]).toUpperCase(), false))

'verify cover period'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/NewInsurance/ViewDetail/CustMF/CoverPeriod')).toUpperCase(),
		(resultInsuranceHybrid[index++]).toUpperCase(), false))

'verify coverage amount'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/NewInsurance/ViewDetail/CustMF/CvgAmountMF')).replace(',','').toUpperCase(),
		(resultInsuranceHybrid[index++]).toUpperCase(), false))

'verify insurance length'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/NewInsurance/ViewDetail/CustMF/InsLength')).toUpperCase(),
		(resultInsuranceHybrid[index++]).toUpperCase(), false))

'verify insurance company admin fee'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/NewInsurance/ViewDetail/CustMF/InscoAdm')).replace(',','').toUpperCase(),
		(resultInsuranceHybrid[index++]).toUpperCase(), false))

'verify insurance company stampduty fee'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/NewInsurance/ViewDetail/CustMF/InscoStampduty')).replace(',','').toUpperCase(),
		(resultInsuranceHybrid[index++]).toUpperCase(), false))

'verify isnurance note'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/NewInsurance/ViewDetail/CustMF/InsNotesMF')).toUpperCase(),
		(resultInsuranceHybrid[index++]).toUpperCase(), false))



}else if(resultInsuredBy.equalsIgnoreCase('Off System')){
'verify Insured by'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/NewInsurance/ViewDetail/Cust/InsuredBy')).toUpperCase(),
		(resultInsuredBy).toUpperCase(), false))
}

'Pengecekan insured by'
if(resultInsuredBy.equalsIgnoreCase('Customer - Multifinance')){
	
	'get arraylist Insurance cvg from db'
	ArrayList<String> resultInsuranceMainCoverage = CustomKeywords.'appView.verifyAppView.checkInsuranceMainCoverage'(sqlconnection, appno)
	
	'count insurance cvg table'
	variableData = DriverFactory.getWebDriver().findElements(By.cssSelector('#CoverageCompanySection > table > tbody tr'))
	
	index = 0
	
	'looping insurance cvg table'
	for(cvgindex = 1; cvgindex <= variableData.size(); cvgindex++){
		'modify object Main cvg'
		modifyNewMainCvg = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 'equals',
			"//*[@id='CoverageCompanySection']/table/tbody/tr["+cvgindex+"]/td[2]", true)
		
		'modify object additional cvg'
		modifyNewAddCvg = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 'equals',
			"//*[@id='CoverageCompanySection']/table/tbody/tr["+cvgindex+"]/td[3]", true)
		
		'modify object main premi to cust'
		modifyNewMainPremitoCust = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 'equals',
			"//*[@id='CoverageCompanySection']/table/tbody/tr["+cvgindex+"]/td[4]", true)
		
		'modify object add premi to cust'
		modifyNewAddPremitoCust = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 'equals',
			"//*[@id='CoverageCompanySection']/table/tbody/tr["+cvgindex+"]/td[5]", true)
		
		'modify object main premi to ins'
		modifyNewMainPremitoInsco = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 'equals',
			"//*[@id='CoverageCompanySection']/table/tbody/tr["+cvgindex+"]/td[6]", true)
		
		'modify object additional premi to insco'
		modifyNewAddPremitoInsco = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 'equals',
			"//*[@id='CoverageCompanySection']/table/tbody/tr["+cvgindex+"]/td[7]", true)
		
		'verify Main cvg'
		checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewMainCvg).toUpperCase(), (resultInsuranceMainCoverage[index++]).toUpperCase(), false))
		
		'get arraylist Insurance add cvg from db'
		ArrayList<String> resultAddtionalCoverage = CustomKeywords.'appView.verifyAppView.checkAdditionalCoverage'(sqlconnection, appno, cvgindex)
		
		'verif additional coverage confins x db'
		checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewAddCvg).toUpperCase(), (resultAddtionalCoverage[0]).toUpperCase(), false))

		'verify Main premi to cust'
		checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewMainPremitoCust).replace(',','').toUpperCase(), (resultInsuranceMainCoverage[index++]).toUpperCase(), false))
		
		'verify add premi to cust'
		checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewAddPremitoCust).replace(',','').toUpperCase(), (resultInsuranceMainCoverage[index++]).toUpperCase(), false))
	
		'verify Main premi to insco'
		checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewMainPremitoInsco).replace(',','').toUpperCase(), (resultInsuranceMainCoverage[index++]).toUpperCase(), false))
	
		'verify add premi to insco'
		checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewAddPremitoInsco).replace(',','').toUpperCase(), (resultInsuranceMainCoverage[index++]).toUpperCase(), false))
		
		index+=2
	}
	
	
	'get arraylist Insurance summary from db'
	ArrayList<String> resultInsuranceSummary = CustomKeywords.'appView.verifyAppView.checkInsuranceSummary'(sqlconnection, appno)
	
	index = 0
	
	'verify TOTAL MAIN PREMI TO CUSTOMER'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/cust-multifinance/totalmainpremitocustomer')).replace(',','').toUpperCase(),
			(resultInsuranceSummary[index++]).toUpperCase(), false))
	
	'verify TOTAL MAIN PREMI FROM INSCO'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/cust-multifinance/totalmainpremifrominsco')).replace(',','').toUpperCase(),
			(resultInsuranceSummary[index++]).toUpperCase(), false))
	
	'verify TOTAL ADDITIONAL PREMI TO CUSTOMER'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/cust-multifinance/totaladditionalpremitocustomer')).replace(',','').toUpperCase(),
			(resultInsuranceSummary[index++]).toUpperCase(), false))
	
	'verify TOTAL ADDITIONAL PREMI FROM INSCO'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/cust-multifinance/totaladditionalpremifrominsco')).replace(',','').toUpperCase(),
			(resultInsuranceSummary[index++]).toUpperCase(), false))
	
	'verify TOTAL FEE TO CUSTOMER'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/cust-multifinance/totalfeetocustomer')).replace(',','').toUpperCase(),
			(resultInsuranceSummary[index++]).toUpperCase(), false))
	
	'verify TOTAL FEE FROM INSCO'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/cust-multifinance/totalfeefrominsco')).replace(',','').toUpperCase(),
			(resultInsuranceSummary[index++]).toUpperCase(), false))
	
	'verify TOTAL PREMI TO CUSTOMER'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/cust-multifinance/totalpremitocustomer')).replace(',','').toUpperCase(),
			(resultInsuranceSummary[index++]).toUpperCase(), false))
	
	'verify TOTAL PREMI FROM INSCO'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/cust-multifinance/totalpremifrominsco')).replace(',','').toUpperCase(),
			(resultInsuranceSummary[index++]).toUpperCase(), false))
	
	'verify DISCOUNT AMOUNT'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/cust-multifinance/discountamount')).replace(',','').toUpperCase(),
			(resultInsuranceSummary[index++]).toUpperCase(), false))
	
	'verify TOTAL PREMI TO CUSTOMER AFTER DISCOUNT'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/cust-multifinance/totalpremitocustafterdisc')).replace(',','').toUpperCase(),
			(resultInsuranceSummary[index++]).toUpperCase(), false))
	
	'verify CAPITALIZE AMOUNT'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/cust-multifinance/capitalizeamount')).replace(',','').toUpperCase(),
			(resultInsuranceSummary[index++]).toUpperCase(), false))
	
}
else if(resultInsuredBy.equalsIgnoreCase('Multifinance')){
	'get arraylist Insurance cvg from db'
	ArrayList<String> resultInsuranceMainCoverage = CustomKeywords.'appView.verifyAppView.checkInsuranceMainCoverageMultifinance'(sqlconnection, appno)
	
	'count insurance cvg table'
	variableData = DriverFactory.getWebDriver().findElements(By.cssSelector('#CoverageCompanySection > table > tbody tr'))
	
	index = 0
	
	'looping insurance cvg table'
	for(cvgindex = 1; cvgindex <= variableData.size(); cvgindex++){
		'modify object Main cvg'
		modifyNewMainCvg = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 'equals',
			"//*[@id='CoverageCompanySection']/table/tbody/tr["+cvgindex+"]/td[2]", true)
		
		'modify object additional cvg'
		modifyNewAddCvg = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 'equals',
			"//*[@id='CoverageCompanySection']/table/tbody/tr["+cvgindex+"]/td[3]", true)
		
		'modify object main premi to cust'
		modifyNewMainPremitoCust = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 'equals',
			"//*[@id='CoverageCompanySection']/table/tbody/tr["+cvgindex+"]/td[4]", true)
		
		'modify object main premi to cust rate'
		modifyNewMainPremitoCustRate = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 'equals',
			"//*[@id='CoverageCompanySection']/table/tbody/tr["+cvgindex+"]/td[5]", true)
		
		'modify object additional premi to cust'
		modifyNewAddPremitoCust = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 'equals',
			"//*[@id='CoverageCompanySection']/table/tbody/tr["+cvgindex+"]/td[6]", true)
		
		'modify object additional premi to cust rate'
		modifyNewAddPremitoCustRate = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 'equals',
			"//*[@id='CoverageCompanySection']/table/tbody/tr["+cvgindex+"]/td[7]", true)
		
		'modify object main premi to ins'
		modifyNewMainPremitoInsco = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 'equals',
			"//*[@id='CoverageCompanySection']/table/tbody/tr["+cvgindex+"]/td[8]", true)
		
		'modify object main premi to ins Rate'
		modifyNewMainPremitoInscoRate = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 'equals',
			"//*[@id='CoverageCompanySection']/table/tbody/tr["+cvgindex+"]/td[9]", true)
		
		'modify object additional premi to insco'
		modifyNewAddPremitoInsco = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 'equals',
			"//*[@id='CoverageCompanySection']/table/tbody/tr["+cvgindex+"]/td[10]", true)
		
		'modify object additional premi to insco Rate'
		modifyNewAddPremitoInscoRate = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 'equals',
			"//*[@id='CoverageCompanySection']/table/tbody/tr["+cvgindex+"]/td[11]", true)
		
		'modify object paid by'
		modifyNewPaidBy = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 'equals',
			"//*[@id='CoverageCompanySection']/table/tbody/tr["+cvgindex+"]/td[12]", true)
		
		'modify object is capitalized'
		modifyNewisCapitalized = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 'equals',
			"//*[@id='CoverageCompanySection']/table/tbody/tr["+cvgindex+"]/td[13]", true)
		
		'verify Main cvg'
		checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewMainCvg).toUpperCase(), (resultInsuranceMainCoverage[index++]).toUpperCase(), false))
		
		'get arraylist Insurance add cvg from db'
		ArrayList<String> resultAddtionalCoverage = CustomKeywords.'appView.verifyAppView.checkAdditionalCoverage'(sqlconnection, appno, cvgindex)
		
		'verif additional coverage confins x db'
		checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewAddCvg).toUpperCase(), (resultAddtionalCoverage[0]).toUpperCase(), false))
		
		'verify Main premi to cust'
		checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewMainPremitoCust).replace(',','').toUpperCase(), (resultInsuranceMainCoverage[index++]).toUpperCase(), false))
		
		'verify Main premi rate to cust'
		checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewMainPremitoCustRate).toUpperCase(), (resultInsuranceMainCoverage[index++]).toUpperCase(), false))
		
		'verify add premi to cust'
		checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewAddPremitoCust).replace(',','').toUpperCase(), (resultInsuranceMainCoverage[index++]).toUpperCase(), false))
	
		'verif additional premi to cust rate confins x db'
		checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewAddPremitoCustRate).toUpperCase(), (resultAddtionalCoverage[1]).toUpperCase(), false))
		
		'verify Main premi to insco'
		checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewMainPremitoInsco).replace(',','').toUpperCase(), (resultInsuranceMainCoverage[index++]).toUpperCase(), false))
	
		'verify Main premi to insco rate'
		checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewMainPremitoInscoRate).toUpperCase(), (resultInsuranceMainCoverage[index++]).toUpperCase(), false))
		
		'verify add premi to insco'
		checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewAddPremitoInsco).replace(',','').toUpperCase(), (resultInsuranceMainCoverage[index++]).toUpperCase(), false))
		
		'verif additional premi to insco rate confins x db'
		checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewAddPremitoInscoRate).toUpperCase(), (resultAddtionalCoverage[2]).toUpperCase(), false))
		
		'verify paid by'
		checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewPaidBy).toUpperCase(), (resultInsuranceMainCoverage[index++]).toUpperCase(), false))
		
		'verify capitalized'
		checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewisCapitalized).toUpperCase(), (resultInsuranceMainCoverage[index++]).toUpperCase(), false))
	}
	
	
	'get arraylist Insurance summary from db'
	ArrayList<String> resultInsuranceSummary = CustomKeywords.'appView.verifyAppView.checkInsuranceSummary'(sqlconnection, appno)
	
	index = 0
	
	'verify TOTAL MAIN PREMI TO CUSTOMER'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/cust-multifinance/totalmainpremitocustomer')).replace(',','').toUpperCase(),
			(resultInsuranceSummary[index++]).toUpperCase(), false))
	
	'verify TOTAL MAIN PREMI FROM INSCO'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/cust-multifinance/totalmainpremifrominsco')).replace(',','').toUpperCase(),
			(resultInsuranceSummary[index++]).toUpperCase(), false))
	
	'verify TOTAL ADDITIONAL PREMI TO CUSTOMER'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/cust-multifinance/totaladditionalpremitocustomer')).replace(',','').toUpperCase(),
			(resultInsuranceSummary[index++]).toUpperCase(), false))
	
	'verify TOTAL ADDITIONAL PREMI FROM INSCO'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/cust-multifinance/totaladditionalpremifrominsco')).replace(',','').toUpperCase(),
			(resultInsuranceSummary[index++]).toUpperCase(), false))
	
	'verify TOTAL FEE TO CUSTOMER'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/cust-multifinance/totalfeetocustomer')).replace(',','').toUpperCase(),
			(resultInsuranceSummary[index++]).toUpperCase(), false))
	
	'verify TOTAL FEE FROM INSCO'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/cust-multifinance/totalfeefrominsco')).replace(',','').toUpperCase(),
			(resultInsuranceSummary[index++]).toUpperCase(), false))
	
	'verify TOTAL PREMI TO CUSTOMER'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/cust-multifinance/totalpremitocustomer')).replace(',','').toUpperCase(),
			(resultInsuranceSummary[index++]).toUpperCase(), false))
	
	'verify TOTAL PREMI FROM INSCO'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/cust-multifinance/totalpremifrominsco')).replace(',','').toUpperCase(),
			(resultInsuranceSummary[index++]).toUpperCase(), false))
	
	'verify DISCOUNT AMOUNT'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/cust-multifinance/discountamount')).replace(',','').toUpperCase(),
			(resultInsuranceSummary[index++]).toUpperCase(), false))
	
	'verify TOTAL PREMI TO CUSTOMER AFTER DISCOUNT'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/cust-multifinance/totalpremitocustafterdisc')).replace(',','').toUpperCase(),
			(resultInsuranceSummary[index++]).toUpperCase(), false))
	
	'verify CAPITALIZE AMOUNT'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/cust-multifinance/capitalizeamount')).replace(',','').toUpperCase(),
			(resultInsuranceSummary[index++]).toUpperCase(), false))
}

'Klik button cancel'
WebUI.click(findTestObject('Object Repository/AppView/Insurance/NewInsurance/ViewDetail/ButtonCancel'))

'get arraylist Insurance summary from db'
ArrayList<String> resultNewInsuranceSummary = CustomKeywords.'appView.verifyAppView.checkNewInsuranceSummary'(sqlconnection, appno)

index = 0

'verify TOTAL PREMI TO CUSTOMER'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/NewInsurance/TotalPremiToCust')).replace(',','').toUpperCase(),
		(resultNewInsuranceSummary[index++]).toUpperCase(), false))

'verify PAID AMOUNT BY CUSTOMER'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/NewInsurance/PaidAmountByCust')).replace(',','').toUpperCase(),
		(resultNewInsuranceSummary[index++]).toUpperCase(), false))

'verify CAPITALIZED AMOUNT'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/NewInsurance/CapitalizeAmt')).replace(',','').toUpperCase(),
		(resultNewInsuranceSummary[index++]).toUpperCase(), false))


if ((GlobalVariable.FlagWarning == 0) && (GlobalVariable.FlagFailed == 0)) {
	'write to excel status success'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '7. Insurance', 0, GlobalVariable.NumofColm -
		1, GlobalVariable.StatusSuccess)
}

def checkVerifyEqualOrMatch(Boolean isMatch) {
	if ((isMatch == false) && (GlobalVariable.FlagFailed == 0)) {
		'write to excel status failed'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '7. Insurance', 0, GlobalVariable.NumofColm -
			1, GlobalVariable.StatusFailed)

		'write to excel reason failed verify equal or match'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '7. Insurance', 1, GlobalVariable.NumofColm -
			1, GlobalVariable.ReasonFailedVerifyEqualOrMatch)

		GlobalVariable.FlagFailed = 1
	}
}
