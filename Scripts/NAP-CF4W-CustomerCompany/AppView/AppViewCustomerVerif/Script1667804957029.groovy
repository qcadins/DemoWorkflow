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

'Assign directori file excel ke global variabel'
String userDir = System.getProperty('user.dir')

'Assign directori file excel ke global variabel'
String filePath = userDir + GlobalVariable.PathAppInquiryCompany

'Assign directori file excel ke global variabel'
GlobalVariable.DataFilePath = filePath

String servername = findTestData('Login/Login').getValue(1, 9)

String instancename = findTestData('Login/Login').getValue(2, 9)

String username = findTestData('Login/Login').getValue(3, 9)

String password = findTestData('Login/Login').getValue(4, 9)

String database = findTestData('Login/Login').getValue(5, 9)

String driverclassname = findTestData('Login/Login').getValue(6, 9)

String url = (((servername + ';instanceName=') + instancename) + ';databaseName=') + database

'connect DB'
Sql sqlconnection = CustomKeywords.'dbconnection.connectDB.connect'(url, username, password, driverclassname)

appno = WebUI.getText(findTestObject('Object Repository/AppView/MainInformation/Label App No'))

'Verif Customer Main Data'
ArrayList<String> resultCustomerMainData = CustomKeywords.'dbconnection.VerifyAppView.checkCustomerMainDataCompany'(sqlconnection, appno)

int index = 0

println(resultCustomerMainData)

'verify cust name'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/CustomerMainData/CustomerName')).toString().toUpperCase(), 
    (resultCustomerMainData[index++]).toString().toUpperCase(), false))

'verify industry type'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/CustomerMainData/IndustryType')).toString().toUpperCase(),
	(resultCustomerMainData[index++]).toString().toUpperCase(), false))

'verify num of employee'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/CustomerMainData/NumofEmployee')).toString().toUpperCase(),
	(resultCustomerMainData[index++]).toString().toUpperCase(), false))

'verify affiliation MF'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/CustomerMainData/AffiliationMF')).toString().toUpperCase(),
	(resultCustomerMainData[index++]).toString().toUpperCase(), false))

'verify is VIP'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/CustomerMainData/isVIP')).toString().toUpperCase(),
	(resultCustomerMainData[index++]).toString().toUpperCase(), false))

'verify Cust model'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/CustomerMainData/CustomerModel')).toString().toUpperCase(), (resultCustomerMainData[index++]).toString().toUpperCase(), false))

'verify company type'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/CustomerMainData/CompanyType')).toString().toUpperCase(),
		(resultCustomerMainData[index++]).toString().toUpperCase(), false))

'verify NPWP'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/CustomerMainData/NPWP')).toString().toUpperCase(),
		(resultCustomerMainData[index++]).toString().toUpperCase(), false))

'verify Establishment date'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/CustomerMainData/EstablishmentDate')).toString().toUpperCase(),
		(resultCustomerMainData[index++]).toString().toUpperCase(), false))


'verify Address'
ArrayList<String> resultAddress = CustomKeywords.'dbconnection.VerifyAppView.checkAddrData'(sqlconnection, appno)

'count address table'
ArrayList<WebElement> variableData = DriverFactory.getWebDriver().findElements(By.cssSelector('#CustAddress > table > tbody tr'))

index = 0

for(addrindex = 1; addrindex <= variableData.size(); addrindex++){
	
	'modify object address type'
	modifyNewAddressType = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'),
		'xpath', 'equals', ('//*[@id="CustAddress"]/table/tbody/tr[' + addrindex) + ']/td[1]', true)
	
	'modify object address'
	modifyNewAddress = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'),
		'xpath', 'equals', ('//*[@id="CustAddress"]/table/tbody/tr[' + addrindex) + ']/td[2]', true)
	
	'modify object building ownership'
	modifyNewBuildingOwnership = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'),
		'xpath', 'equals', ('//*[@id="CustAddress"]/table/tbody/tr[' + addrindex) + ']/td[3]', true)
	
	'modify object phone 1'
	modifyNewPhone1 = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'),
		'xpath', 'equals', ('//*[@id="CustAddress"]/table/tbody/tr[' + addrindex) + ']/td[4]', true)
	
	'modify object phone 2'
	modifyNewPhone2 = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'),
		'xpath', 'equals', ('//*[@id="CustAddress"]/table/tbody/tr[' + addrindex) + ']/td[5]', true)
	
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewAddressType).toUpperCase(), resultAddress[index++].toUpperCase(), false))
	
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewAddress).toUpperCase(), resultAddress[index++].toUpperCase(), false))
	
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewBuildingOwnership).toUpperCase(), resultAddress[index++].toUpperCase(), false))
	
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewPhone1).toUpperCase(), resultAddress[index++].toUpperCase(), false))
	
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewPhone2).toUpperCase(), resultAddress[index++].toUpperCase(), false))
	
}

'verify MS'
ArrayList<String> resultMS = CustomKeywords.'dbconnection.VerifyAppView.checkMSData'(sqlconnection, appno)

'count MS table'
variableData = DriverFactory.getWebDriver().findElements(By.cssSelector('#CustShareholder > table > tbody tr'))

index = 0

for(MSindex = 1; MSindex <= variableData.size(); MSindex++){
	
	'modify object MS Name'
	modifyNewMSName = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'),
		'xpath', 'equals', ('//*[@id="CustShareholder"]/table/tbody/tr[' + MSindex) + ']/td[2]', true)
	
	'modify object address'
	modifyNewMSType = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'),
		'xpath', 'equals', ('//*[@id="CustShareholder"]/table/tbody/tr[' + MSindex) + ']/td[3]', true)
	
	'modify object share'
	modifyNewShare = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'),
		'xpath', 'equals', ('//*[@id="CustShareholder"]/table/tbody/tr[' + MSindex) + ']/td[4]', true)
	
	'modify object isactive'
	modifyNewisActive = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'),
		'xpath', 'equals', ('//*[@id="CustShareholder"]/table/tbody/tr[' + MSindex) + ']/td[5]', true)
	
	'modify object is owner'
	modifyNewisOwner = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'),
		'xpath', 'equals', ('//*[@id="CustShareholder"]/table/tbody/tr[' + MSindex) + ']/td[6]', true)
	
	'modify object is signer'
	modifyNewisSigner = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'),
		'xpath', 'equals', ('//*[@id="CustShareholder"]/table/tbody/tr[' + MSindex) + ']/td[7]', true)
	
	
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewMSName).toUpperCase(), resultMS[index++].toUpperCase(), false))
	
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewMSType).toUpperCase(), resultMS[index++].toUpperCase(), false))
	
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewShare).replace(' %', '').toUpperCase(), resultMS[index++].toUpperCase(), false))
	
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewisActive).toUpperCase(), resultMS[index++].toUpperCase(), false))
	
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewisOwner).toUpperCase(), resultMS[index++].toUpperCase(), false))
	
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewisSigner).toUpperCase(), resultMS[index++].toUpperCase(), false))
	
}

'verify Contact person'
ArrayList<String> resultCP = CustomKeywords.'dbconnection.VerifyAppView.checkContactPersonData'(sqlconnection, appno)


for(cpIndex = 1; cpIndex <= resultCP.size(); cpIndex++){
	
	'modify object contact person'
	modifyNewcontactpersonobject = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'),
		'xpath', 'equals', ('//*[@id="AppCustComapnyContactPersonId"]/div/div[' + cpIndex) + ']/span/div/div[2]', true)
	
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewcontactpersonobject).toUpperCase(), resultCP[cpIndex - 1].toUpperCase(), false))
	
}



'verify financial data'
ArrayList<String> resultFindata = CustomKeywords.'dbconnection.VerifyAppView.checkFinancialData'(sqlconnection, appno)

'count financial data table'
variableData = DriverFactory.getWebDriver().findElements(By.cssSelector('#ListCustFinData > table > tbody tr'))

for(finIndex = 1; finIndex <= variableData.size(); finIndex++){
	
	'modify object financial data date'
	modifyNewfinancialDataDate = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'),
		'xpath', 'equals', ('//*[@id="ListCustFinData"]/table/tbody/tr[' + finIndex) + ']/td[1]', true)
	
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewfinancialDataDate).toUpperCase(), resultFindata[finIndex - 1].toUpperCase(), false))
	
}


'verify financial data attr'
ArrayList<String> resultFindataattr = CustomKeywords.'dbconnection.VerifyAppView.checkFinancialAttrData'(sqlconnection, appno)

for(finIndex = 1; finIndex <= resultFindataattr.size(); finIndex++){
	
	'modify object financial data attr'
	modifyNewfinancialDataAttr = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'),
		'xpath', 'equals', ('//*[@id="FinAttrInfoId"]/div[' + finIndex) + ']/label[2]', true)
	
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewfinancialDataAttr).replace(".00",'').toUpperCase(), resultFindataattr[finIndex - 1].toUpperCase(), false))
	
}

'verify Bank Acc'
ArrayList<String> resultBankAcc = CustomKeywords.'dbconnection.VerifyAppView.checkBankAcc'(sqlconnection, appno)

index = 0

for(int bankIndex = 0; bankIndex < resultBankAcc.size()/3; bankIndex++){
	'modify object bank name - bank acc no - acc name'
	modifyNewBankDetail = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'),
		'xpath', 'equals', ('//*[@id="BankAccount"]/div/div[' + (bankIndex + 1) + ']/lib-ucsubsection/div/form/div/h4'), true)
	
	'modify object bank Branch'
	modifyNewBankBranch = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'),
		'xpath', 'equals', ('//*[@id="BankAccInfo' + bankIndex) + '"]/div[1]/div/div/div/div[2]/label', true)
	
	'modify object beggining balance'
	modifyNewBeginningBalance = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'),
		'xpath', 'equals', ('//*[@id="BankAccInfo' + bankIndex) + '"]/div[2]/table/tbody/tr[1]/td[7]', true)
	
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewBankDetail).toUpperCase(), resultBankAcc[index++].toUpperCase(), false))
	
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewBankBranch).toUpperCase(), resultBankAcc[index++].toUpperCase(), false))
	
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewBeginningBalance).replace(".00",'').toUpperCase(), resultBankAcc[index++].toUpperCase(), false))
}

'count bank statement table'
variableDataBank = DriverFactory.getWebDriver().findElements(By.cssSelector('#BankAccount > div lib-ucsubsection'))

for(int bankIndex = 0; bankIndex < variableDataBank.size(); bankIndex++){
	variableDataBankStatement = DriverFactory.getWebDriver().findElements(By.cssSelector('#BankAccInfo' + (bankIndex) + '> div:nth-child(2) > table > tbody tr'))
			
	if(variableDataBankStatement.size() > 1){
		
		'modify object bank name - bank acc no - acc name'
		modifyNewBankDetail = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'),
			'xpath', 'equals', ('//*[@id="BankAccount"]/div/div[' + (bankIndex+1) + ']/lib-ucsubsection/div/form/div/h4'), true)
		
		bankDetail = WebUI.getText(modifyNewBankDetail).split(' - ')
		
		'verify Bank Acc Statement'
		ArrayList<String> resultBankAccStatement = CustomKeywords.'dbconnection.VerifyAppView.checkBankStatData'(sqlconnection, appno, bankDetail[1])
		
		index = 0
		
		for(bankstatIndex = 1; bankstatIndex <= resultBankAccStatement.size()/7; bankstatIndex++){
			'modify object Month'
			modifyNewMonth = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'),
				'xpath', 'equals', ('//*[@id="BankAccInfo'+ bankIndex +'"]/div[2]/table/tbody/tr[' + (bankstatIndex+1) + ']/td[1]'), true)
			
			'modify object Year'
			modifyNewYear = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'),
				'xpath', 'equals', ('//*[@id="BankAccInfo'+ bankIndex +'"]/div[2]/table/tbody/tr[' + (bankstatIndex+1) + ']/td[2]'), true)
			
			'modify object Debit Trsction count'
			modifyNewDebitTransaction = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'),
				'xpath', 'equals', ('//*[@id="BankAccInfo'+ bankIndex +'"]/div[2]/table/tbody/tr[' + (bankstatIndex+1) + ']/td[3]'), true)
			
			'modify object Debit'
			modifyNewDebit = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'),
				'xpath', 'equals', ('//*[@id="BankAccInfo'+ bankIndex +'"]/div[2]/table/tbody/tr[' + (bankstatIndex+1) + ']/td[4]'), true)
			
			'modify object Credit Trsction count'
			modifyNewCreditTransaction = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'),
				'xpath', 'equals', ('//*[@id="BankAccInfo'+ bankIndex +'"]/div[2]/table/tbody/tr[' + (bankstatIndex+1) + ']/td[5]'), true)
			
			'modify object Credit'
			modifyNewCredit = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'),
				'xpath', 'equals', ('//*[@id="BankAccInfo'+ bankIndex +'"]/div[2]/table/tbody/tr[' + (bankstatIndex+1) + ']/td[6]'), true)
			
			'modify object Balance amount'
			modifyNewBalanceAmount = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'),
				'xpath', 'equals', ('//*[@id="BankAccInfo'+ bankIndex +'"]/div[2]/table/tbody/tr[' + (bankstatIndex+1) + ']/td[7]'), true)
			
			checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewMonth).toUpperCase(), resultBankAccStatement[index++].toUpperCase(), false))
			
			checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewYear).toUpperCase(), resultBankAccStatement[index++].toUpperCase(), false))
			
			checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewDebitTransaction).toUpperCase(), resultBankAccStatement[index++].toUpperCase(), false))
			
			checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewDebit).replace('.00','').toUpperCase(), resultBankAccStatement[index++].toUpperCase(), false))
			
			checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewCreditTransaction).toUpperCase(), resultBankAccStatement[index++].toUpperCase(), false))
			
			checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewCredit).replace('.00','').toUpperCase(), resultBankAccStatement[index++].toUpperCase(), false))
			
			checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewBalanceAmount).replace(',','').toUpperCase(), resultBankAccStatement[index++].toUpperCase(), false))
		}
		println(resultBankAccStatement)
	}
}

'verify legal doc'
ArrayList<String> resultLegalDoc = CustomKeywords.'dbconnection.VerifyAppView.checkLegalDocData'(sqlconnection, appno)

'count legal doc table'
variableData = DriverFactory.getWebDriver().findElements(By.cssSelector('#CustLegalDoc > table > tbody tr'))

index = 0

for(Legalindex = 1; Legalindex <= variableData.size(); Legalindex++){

	'modify object Legal Doc Type'
	modifyNewLegalDocType = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'),
		'xpath', 'equals', ('//*[@id="CustLegalDoc"]/table/tbody/tr[' + Legalindex) + ']/td[1]', true)

	'modify object doc No'
	modifyNewLegalDocNo = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'),
		'xpath', 'equals', ('//*[@id="CustLegalDoc"]/table/tbody/tr[' + Legalindex) + ']/td[2]', true)

	'modify object Issue Date'
	modifyNewIssueDate = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'),
		'xpath', 'equals', ('//*[@id="CustLegalDoc"]/table/tbody/tr[' + Legalindex) + ']/td[3]', true)

	'modify object expired date'
	modifyNewExpiredDate = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'),
		'xpath', 'equals', ('//*[@id="CustLegalDoc"]/table/tbody/tr[' + Legalindex) + ']/td[4]', true)

	'modify object notary name'
	modifyNewNotaryName = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'),
		'xpath', 'equals', ('//*[@id="CustLegalDoc"]/table/tbody/tr[' + Legalindex) + ']/td[5]', true)


	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewLegalDocType).toUpperCase(), resultLegalDoc[index++].toUpperCase(), false))

	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewLegalDocNo).toUpperCase(), resultLegalDoc[index++].toUpperCase(), false))

	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewIssueDate).toUpperCase(), resultLegalDoc[index++].toUpperCase(), false))

	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewExpiredDate).toUpperCase(), resultLegalDoc[index++].toUpperCase(), false))

	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewNotaryName).toUpperCase(), resultLegalDoc[index++].toUpperCase(), false))
}


'verif customer group'
if(WebUI.verifyNotMatch(WebUI.getText(findTestObject('AppView/CustomerMainData/ModifyObj')), 'NO DATA AVAILABLE', false, FailureHandling.OPTIONAL)){

	'count customer group table'
	variableData = DriverFactory.getWebDriver().findElements(By.cssSelector('#CustGrp > table > tbody tr'))
	
	ArrayList<String> resultCustGroup = CustomKeywords.'dbconnection.VerifyAppView.checkCustGroupData'(sqlconnection, appno)
	
	for(custGroupindex = 1; custGroupindex <= resultCustGroup.size(); custGroupindex++){
		'modify object cust group name'
		modifyNewcustGroupName = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'),
			'xpath', 'equals', ('//*[@id="CustGrp"]/table/tbody/tr[' + custGroupindex) + ']/td[2]', true)
		
		checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewcustGroupName).toUpperCase(), resultCustGroup[custGroupindex - 1].toUpperCase(), false))
	}
}

'verify Other Info'
ArrayList<String> resultOtherInfo = CustomKeywords.'dbconnection.VerifyAppView.checkOtherInfoData'(sqlconnection, appno)


for(OthIndex = 1; OthIndex <= resultOtherInfo.size(); OthIndex++){

	'modify object other info'
	modifyNewothinfoobject = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'),
		'xpath', 'equals', ('//*[@id="otherInfo"]/div[' + OthIndex) + ']/label[2]', true)

	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewothinfoobject).toUpperCase(), resultOtherInfo[OthIndex - 1].toUpperCase(), false))

}

'verify Other Info'
ArrayList<String> resultOtherAttrList = CustomKeywords.'dbconnection.VerifyAppView.checkOtherAttrData'(sqlconnection, appno)


checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('AppView/CustomerMainData/attributelist Company/Business Period AML')).toUpperCase(), resultOtherAttrList[12].toUpperCase(), false))

checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('AppView/CustomerMainData/attributelist Company/Business Source AML')).toUpperCase(), resultOtherAttrList[11].toUpperCase(), false))

checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('AppView/CustomerMainData/attributelist Company/CSP USL Source')).toUpperCase(), resultOtherAttrList[9].toUpperCase(), false))

checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('AppView/CustomerMainData/attributelist Company/Payment Type')).toUpperCase(), resultOtherAttrList[10].toUpperCase(), false))

checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('AppView/CustomerMainData/attributelist Company/KYC Report AMl')).toUpperCase(), resultOtherAttrList[8].toUpperCase(), false))

checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('AppView/CustomerMainData/attributelist Company/Exceed BMPK')).toUpperCase(), resultOtherAttrList[2].toUpperCase(), false))

checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('AppView/CustomerMainData/attributelist Company/Violate BMPK')).toUpperCase(), resultOtherAttrList[3].toUpperCase(), false))

public checkVerifyEqualOrMatch(Boolean isMatch){
	if(isMatch==false && GlobalVariable.FlagFailed==0){
		(new writetoexcel.writeToExcel()).writeToExcelFunction(GlobalVariable.DataFilePath, '2. Customer',
				0, GlobalVariable.NumofColm-1, GlobalVariable.StatusFailed)

		(new writetoexcel.writeToExcel()).writeToExcelFunction(GlobalVariable.DataFilePath, '2. Customer',
				1, GlobalVariable.NumofColm-1, GlobalVariable.ReasonFailedVerifyEqualOrMatch)

		GlobalVariable.FlagFailed=1
}
}


WebUI.switchToWindowIndex('1')

