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
GlobalVariable.DataFilePath = CustomKeywords.'dbconnection.connectDB.getExcelPath'(GlobalVariable.PathAppInquiryCompany)

'connect DB LOS'
Sql sqlconnectionLOS = CustomKeywords.'dbconnection.connectDB.connectLOS'()

GlobalVariable.FlagWarning = 0

'click tab Insurance'
WebUI.click(findTestObject('Object Repository/AppView/Insurance/InsuranceTab'))

'Verif tidak ada alert yang muncul'
if(WebUI.verifyElementNotPresent(findTestObject('NAP-CF4W-CustomerPersonal/div_erroralert'), 2)==false){
	GlobalVariable.FlagWarning = 1
	
	'write status warning'
	CustomKeywords.'checkSaveProcess.checkSaveProcess.writeWarningAppView'(GlobalVariable.NumofColm,'7. Insurance')
}

appno = WebUI.getText(findTestObject('Object Repository/AppView/MainInformation/Label App No'))

'get insured by from db'
String resultInsuredBy = CustomKeywords.'dbconnection.VerifyAppView.checkInsuredBy'(sqlconnectionLOS, appno)

println(resultInsuredBy)

if(resultInsuredBy.equalsIgnoreCase('Customer')){
	'get arraylist Insurance Customer from db'
	ArrayList<String> resultInsuranceCustomer = CustomKeywords.'dbconnection.VerifyAppView.checkInsuranceCustomer'(sqlconnectionLOS, appno)
	
	index = 0
	
	'verify insured by'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/Customer/insuredby')).toUpperCase(),
			(resultInsuranceCustomer[index++]).toUpperCase(), false))
	
	'verify policy no'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/Customer/policyno')).toUpperCase(),
			(resultInsuranceCustomer[index++]).toUpperCase(), false))
	
	'verify insurance branch name'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/Customer/insurancebranchname')).toUpperCase(),
			(resultInsuranceCustomer[index++]).toUpperCase(), false))
	
	'verify policy name'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/Customer/policyname')).toUpperCase(),
			(resultInsuranceCustomer[index++]).toUpperCase(), false))
	
	'verify cvg amount'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/Customer/cvgamount')).replace(',','').toUpperCase(),
			(resultInsuranceCustomer[index++]).toUpperCase(), false))
	
	'verify start date'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/Customer/startdate')).toUpperCase(),
			(resultInsuranceCustomer[index++]).toUpperCase(), false))
	
	'verify insurance note'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/Customer/insurancenote')).toUpperCase(),
			(resultInsuranceCustomer[index++]).toUpperCase(), false))
	
	'verify end date'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/Customer/enddate')).toUpperCase(),
			(resultInsuranceCustomer[index++]).toUpperCase(), false))
	
}else if(resultInsuredBy.equalsIgnoreCase('Multifinance')){

'get arraylist Insurance Multifinance from db'
ArrayList<String> resultInsuranceMultifinance = CustomKeywords.'dbconnection.VerifyAppView.checkInsuranceMultifinance'(sqlconnectionLOS, appno)

index = 0

'verify insured by'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/Multifinance/Insuredby')).toUpperCase(),
		(resultInsuranceMultifinance[index++]).toUpperCase(), false))

'verify isnurance paid by'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/Multifinance/insurancepaidby')).toUpperCase(),
		(resultInsuranceMultifinance[index++]).toUpperCase(), false))

'verify insurance company'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/Multifinance/insurancecompany')).toUpperCase(),
		(resultInsuranceMultifinance[index++]).toUpperCase(), false))

'verify payment type'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/Multifinance/paymenttype')).toUpperCase(),
		(resultInsuranceMultifinance[index++]).toUpperCase(), false))

'verify cover period'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/Multifinance/coverperiod')).toUpperCase(),
		(resultInsuranceMultifinance[index++]).toUpperCase(), false))

'verify coverage amount'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/Multifinance/coverageamount')).replace(',','').toUpperCase(),
		(resultInsuranceMultifinance[index++]).toUpperCase(), false))

'verify insurance length'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/Multifinance/insurancelength')).toUpperCase(),
		(resultInsuranceMultifinance[index++]).toUpperCase(), false))

'verify insurance admin fee'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/Multifinance/insuranceadminfee')).replace(',','').toUpperCase(),
		(resultInsuranceMultifinance[index++]).toUpperCase(), false))

'verify isnurance note'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/Multifinance/insurancenote')).toUpperCase(),
		(resultInsuranceMultifinance[index++]).toUpperCase(), false))

'verify asset region'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/Multifinance/assetregion')).toUpperCase(),
		(resultInsuranceMultifinance[index++]).toUpperCase(), false))

}else if(resultInsuredBy.equalsIgnoreCase('Customer - Multifinance')){

'get arraylist Insurance Cust-Multifinance from db'
ArrayList<String> resultInsuranceCustMF = CustomKeywords.'dbconnection.VerifyAppView.checkInsuranceCustMf'(sqlconnectionLOS, appno)

index = 0

'verify policy no'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/cust-multifinance/policyno')).toUpperCase(),
		(resultInsuranceCustMF[index++]).toUpperCase(), false))

'verify insurance branch name'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/cust-multifinance/insurancebranch')).toUpperCase(),
		(resultInsuranceCustMF[index++]).toUpperCase(), false))

'verify policy name'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/cust-multifinance/policyname')).toUpperCase(),
		(resultInsuranceCustMF[index++]).toUpperCase(), false))

'verify cvg amount'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/cust-multifinance/custcvgamount')).replace(',','').toUpperCase(),
		(resultInsuranceCustMF[index++]).toUpperCase(), false))

'verify start date'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/cust-multifinance/startdate')).toUpperCase(),
		(resultInsuranceCustMF[index++]).toUpperCase(), false))

'verify insurance note'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/cust-multifinance/custnotes')).toUpperCase(),
		(resultInsuranceCustMF[index++]).toUpperCase(), false))

'verify end date'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/cust-multifinance/enddate')).toUpperCase(),
		(resultInsuranceCustMF[index++]).toUpperCase(), false))

'verify isnurance paid by'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/cust-multifinance/insurancepaidby')).toUpperCase(),
		(resultInsuranceCustMF[index++]).toUpperCase(), false))

'verify insurance company'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/cust-multifinance/insurancecompany')).toUpperCase(),
		(resultInsuranceCustMF[index++]).toUpperCase(), false))

'verify cover period'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/cust-multifinance/coverperiod')).toUpperCase(),
		(resultInsuranceCustMF[index++]).toUpperCase(), false))

'verify coverage amount'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/cust-multifinance/mfcvgamount')).replace(',','').toUpperCase(),
		(resultInsuranceCustMF[index++]).toUpperCase(), false))

'verify insurance length'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/cust-multifinance/insurancelength')).toUpperCase(),
		(resultInsuranceCustMF[index++]).toUpperCase(), false))

'verify insurance admin fee'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/cust-multifinance/insuranceadminfee')).replace(',','').toUpperCase(),
		(resultInsuranceCustMF[index++]).toUpperCase(), false))

'verify isnurance note'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/cust-multifinance/mfnotes')).toUpperCase(),
		(resultInsuranceCustMF[index++]).toUpperCase(), false))

'verify asset region'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/cust-multifinance/assetregion')).toUpperCase(),
		(resultInsuranceCustMF[index++]).toUpperCase(), false))

}else if(resultInsuredBy.equalsIgnoreCase('Off System')){
'verify Insured by'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/OffSystem')).toUpperCase(),
		(resultInsuredBy).toUpperCase(), false))
}

if(resultInsuredBy.equalsIgnoreCase('Customer - Multifinance') || resultInsuredBy.equalsIgnoreCase('Multifinance')){
	
	'get arraylist Insurance cvg from db'
	ArrayList<String> resultInsuranceMainCoverage = CustomKeywords.'dbconnection.VerifyAppView.checkInsuranceMainCoverage'(sqlconnectionLOS, appno)
	
	'count insurance cvg table'
	variableData = DriverFactory.getWebDriver().findElements(By.cssSelector('#coverage > lib-ucgridview > div > table > tbody tr'))
	
	index = 0
	
	for(cvgindex = 1; cvgindex <= variableData.size(); cvgindex++){
		'modify object Main cvg'
		modifyNewMainCvg = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 'equals',
			('//*[@id="coverage"]/lib-ucgridview/div/table/tbody/tr[' + cvgindex) + ']/td[2]', true)
		
		'modify object additional cvg'
		modifyNewAddCvg = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 'equals',
			('//*[@id="coverage"]/lib-ucgridview/div/table/tbody/tr[' + cvgindex) + ']/td[3]', true)
		
		'modify object main premi to cust'
		modifyNewMainPremitoCust = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 'equals',
			('//*[@id="coverage"]/lib-ucgridview/div/table/tbody/tr[' + cvgindex) + ']/td[4]', true)
		
		'modify object add premi to cust'
		modifyNewAddPremitoCust = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 'equals',
			('//*[@id="coverage"]/lib-ucgridview/div/table/tbody/tr[' + cvgindex) + ']/td[5]', true)
		
		'modify object main premi to ins'
		modifyNewMainPremitoInsco = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 'equals',
			('//*[@id="coverage"]/lib-ucgridview/div/table/tbody/tr[' + cvgindex) + ']/td[6]', true)
		
		'modify object additional premi to insco'
		modifyNewAddPremitoInsco = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 'equals',
			('//*[@id="coverage"]/lib-ucgridview/div/table/tbody/tr[' + cvgindex) + ']/td[7]', true)
		
		'modify object paid by'
		modifyNewPaidBy = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 'equals',
			('//*[@id="coverage"]/lib-ucgridview/div/table/tbody/tr[' + cvgindex) + ']/td[8]', true)
		
		'modify object is capitalized'
		modifyNewisCapitalized = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 'equals',
			('//*[@id="coverage"]/lib-ucgridview/div/table/tbody/tr[' + cvgindex) + ']/td[9]', true)
		
		'verify Main cvg'
		checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewMainCvg).toUpperCase(), (resultInsuranceMainCoverage[index++]).toUpperCase(), false))
		
		ArrayList<String> addtionalcvg = WebUI.getText(modifyNewAddCvg).split(', ')
		
		
		'get arraylist Insurance add cvg from db'
		ArrayList<String> resultAddtionalCoverage = CustomKeywords.'dbconnection.VerifyAppView.checkAdditionalCoverage'(sqlconnectionLOS, appno, cvgindex)
		
		checkVerifyEqualOrMatch(addtionalcvg.containsAll(resultAddtionalCoverage))
		
		
		'verify Main premi to cust'
		checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewMainPremitoCust).replace(',','').toUpperCase(), (resultInsuranceMainCoverage[index++]).toUpperCase(), false))
		
		'verify add premi to cust'
		checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewAddPremitoCust).replace(',','').toUpperCase(), (resultInsuranceMainCoverage[index++]).toUpperCase(), false))
	
		'verify Main premi to insco'
		checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewMainPremitoInsco).replace(',','').toUpperCase(), (resultInsuranceMainCoverage[index++]).toUpperCase(), false))
	
		'verify add premi to insco'
		checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewAddPremitoInsco).replace(',','').toUpperCase(), (resultInsuranceMainCoverage[index++]).toUpperCase(), false))
		
		'verify paid by'
		checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewPaidBy).toUpperCase(), (resultInsuranceMainCoverage[index++]).toUpperCase(), false))
		
		'verify capitalized'
		checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewisCapitalized).toUpperCase(), (resultInsuranceMainCoverage[index++]).toUpperCase(), false))
	}
	
	'get arraylist Insurance summary from db'
	ArrayList<String> resultInsuranceSummary = CustomKeywords.'dbconnection.VerifyAppView.checkInsuranceSummary'(sqlconnectionLOS, appno)
	
	index = 0
	
	'verify TOTAL MAIN PREMI TO CUSTOMER'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/cust-multifinance/totalmainpremitocustomer')).replace(',','').toUpperCase(),
			(resultInsuranceSummary[index++]).toUpperCase(), false))
	
	'verify TOTAL ADDITIONAL PREMI TO CUSTOMER'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/cust-multifinance/totaladditionalpremitocustomer')).replace(',','').toUpperCase(),
			(resultInsuranceSummary[index++]).toUpperCase(), false))
	
	'verify TOTAL FEE TO CUSTOMER'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/cust-multifinance/totalfeetocustomer')).replace(',','').toUpperCase(),
			(resultInsuranceSummary[index++]).toUpperCase(), false))
	
	'verify TOTAL PREMI TO CUSTOMER'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/cust-multifinance/totalpremitocustomer')).replace(',','').toUpperCase(),
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
	
	'verify TOTAL MAIN PREMI FROM INSCO'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/cust-multifinance/totalmainpremifrominsco')).replace(',','').toUpperCase(),
			(resultInsuranceSummary[index++]).toUpperCase(), false))
	
	'verify TOTAL ADDITIONAL PREMI FROM INSCO'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/cust-multifinance/totaladditionalpremifrominsco')).replace(',','').toUpperCase(),
			(resultInsuranceSummary[index++]).toUpperCase(), false))
	
	'verify TOTAL FEE FROM INSCO'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/cust-multifinance/totalfeefrominsco')).replace(',','').toUpperCase(),
			(resultInsuranceSummary[index++]).toUpperCase(), false))
	
	'verify TOTAL PREMI FROM INSCO'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/Insurance/cust-multifinance/totalpremifrominsco')).replace(',','').toUpperCase(),
			(resultInsuranceSummary[index++]).toUpperCase(), false))
	
}


if ((GlobalVariable.FlagWarning == 0) && (GlobalVariable.FlagFailed == 0)) {
	new writetoexcel.writeToExcel().writeToExcelFunction(GlobalVariable.DataFilePath, '7. Insurance', 0, GlobalVariable.NumofColm -
		1, GlobalVariable.StatusSuccess)
}

def checkVerifyEqualOrMatch(Boolean isMatch) {
    if ((isMatch == false) && (GlobalVariable.FlagFailed == 0)) {
        new writetoexcel.writeToExcel().writeToExcelFunction(GlobalVariable.DataFilePath, '7. Insurance', 0, GlobalVariable.NumofColm - 
            1, GlobalVariable.StatusFailed)

        new writetoexcel.writeToExcel().writeToExcelFunction(GlobalVariable.DataFilePath, '7. Insurance', 1, GlobalVariable.NumofColm - 
            1, GlobalVariable.ReasonFailedVerifyEqualOrMatch)

        GlobalVariable.FlagFailed = 1
    }
}

