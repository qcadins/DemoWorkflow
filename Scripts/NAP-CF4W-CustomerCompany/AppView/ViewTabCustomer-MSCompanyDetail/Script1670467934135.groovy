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

'connect DB LOS'
Sql sqlconnectionLOS = CustomKeywords.'dbConnection.connectDB.connectLOS'()

'get text appno'
appno = WebUI.getText(findTestObject('Object Repository/AppView/MainInformation/Label App No'))

'Verif Customer Main Data'
ArrayList<String> resultCustomerMainData = CustomKeywords.'appView.verifyAppView.checkCustomerMainDataCompany'(sqlconnectionLOS, 
    appno, GlobalVariable.MSName)

int index = 0

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
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/CustomerMainData/CustomerModel')).toString().toUpperCase(), 
        (resultCustomerMainData[index++]).toString().toUpperCase(), false))

'verify company type'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/CustomerMainData/CompanyType')).toString().toUpperCase(), 
        (resultCustomerMainData[index++]).toString().toUpperCase(), false))

'verify NPWP'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/CustomerMainData/NPWP')).toString().toUpperCase(), 
        (resultCustomerMainData[index++]).toString().toUpperCase(), false))

'verify Establishment date'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/AppView/CustomerMainData/EstablishmentDate')).toString().toUpperCase(), 
        (resultCustomerMainData[index++]).toString().toUpperCase(), false))

'get arraylist from db'
ArrayList<String> resultAddress = CustomKeywords.'appView.verifyAppView.checkAddrData'(sqlconnectionLOS, appno, GlobalVariable.MSName)

'count address table'
ArrayList<String> variableData = DriverFactory.getWebDriver().findElements(By.cssSelector('#CustAddress > table > tbody tr'))

index = 0

'looping address appview'
for (addrindex = 1; addrindex <= variableData.size(); addrindex++) {
    'modify object address type'
    modifyNewAddressType = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 'equals', 
        ('//*[@id="CustAddress"]/table/tbody/tr[' + addrindex) + ']/td[1]', true)

    'modify object address'
    modifyNewAddress = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 'equals', 
        ('//*[@id="CustAddress"]/table/tbody/tr[' + addrindex) + ']/td[2]', true)

    'modify object building ownership'
    modifyNewBuildingOwnership = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 
        'equals', ('//*[@id="CustAddress"]/table/tbody/tr[' + addrindex) + ']/td[3]', true)

    'modify object phone 1'
    modifyNewPhone1 = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 'equals', 
        ('//*[@id="CustAddress"]/table/tbody/tr[' + addrindex) + ']/td[4]', true)

    'modify object phone 2'
    modifyNewPhone2 = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 'equals', 
        ('//*[@id="CustAddress"]/table/tbody/tr[' + addrindex) + ']/td[5]', true)

    'verify Address type'
    checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewAddressType).toUpperCase(), (resultAddress[index++]).toUpperCase(), 
            false))

    'verify Address'
    checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewAddress).toUpperCase(), (resultAddress[index++]).toUpperCase(), 
            false))

    'verify ownership'
    checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewBuildingOwnership).toUpperCase(), (resultAddress[index++]).toUpperCase(), 
            false))

    'verify phone 1'
    checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewPhone1).toUpperCase()+ ' -', (resultAddress[index++]).toUpperCase(), 
            false))

    'verify phone 2'
    checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewPhone2).toUpperCase()+ ' -', (resultAddress[index++]).toUpperCase(), 
            false))
}

'get arraylist contact person from db'
ArrayList<String> resultCP = CustomKeywords.'appView.verifyAppView.checkContactPersonData'(sqlconnectionLOS, appno, GlobalVariable.MSName)

'looping contact person appview'
for (cpIndex = 1; cpIndex <= resultCP.size(); cpIndex++) {
    'modify object contact person'
    modifyNewcontactpersonobject = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 
        'equals', ('//*[@id="AppCustComapnyContactPersonId"]/div/div[' + cpIndex) + ']/span/div/div[2]', true)

    'verify contact person'
    checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewcontactpersonobject).toUpperCase(), (resultCP[(cpIndex - 
            1)]).toUpperCase(), false))
}

'get arraylist financial data from db'
ArrayList<String> resultFindata = CustomKeywords.'appView.verifyAppView.checkFinancialData'(sqlconnectionLOS, appno, GlobalVariable.MSName)

'count financial data table'
variableData = DriverFactory.getWebDriver().findElements(By.cssSelector('#ListCustFinData > table > tbody tr'))

'looping financial appview'
for (finIndex = 1; finIndex <= variableData.size(); finIndex++) {
    'modify object financial data date'
    modifyNewfinancialDataDate = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 
        'equals', ('//*[@id="ListCustFinData"]/table/tbody/tr[' + finIndex) + ']/td[1]', true)

    'verify financial data date'
    checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewfinancialDataDate).toUpperCase(), (resultFindata[(finIndex - 
            1)]).toUpperCase(), false))
}

'get arraylist fin data attr from db'
ArrayList<String> resultFindataattr = CustomKeywords.'appView.verifyAppView.checkFinancialAttrData'(sqlconnectionLOS, appno, 
    GlobalVariable.MSName)

'looping financial atr appview'
for (finIndex = 1; finIndex <= resultFindataattr.size(); finIndex++) {
    'modify object financial data attr'
    modifyNewfinancialDataAttr = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 
        'equals', ('//*[@id="FinAttrInfoId"]/div[' + finIndex) + ']/label[2]', true)

    'verify financial data attr'
    checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewfinancialDataAttr).replace('.00', '').replace(',','').toUpperCase(), 
            (resultFindataattr[(finIndex - 1)]).toUpperCase(), false))
}

'get arraylist bank acc from db'
ArrayList<String> resultBankAcc = CustomKeywords.'appView.verifyAppView.checkBankAcc'(sqlconnectionLOS, appno, GlobalVariable.MSName)

index = 0

'looping bankacc appview'
for (int bankIndex = 0; bankIndex < (resultBankAcc.size() / 3); bankIndex++) {
    'modify object bank name - bank acc no - acc name'
    modifyNewBankDetail = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 'equals', 
        ('//*[@id="BankAccount"]/div/div[' + (bankIndex + 1)) + ']/lib-ucsubsection/div/form/div/h4', true)

    'modify object bank Branch'
    modifyNewBankBranch = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 'equals', 
        ('//*[@id="BankAccInfo' + bankIndex) + '"]/div[1]/div/div/div/div[2]/label', true)

    'modify object beggining balance'
    modifyNewBeginningBalance = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 
        'equals', ('//*[@id="BankAccInfo' + bankIndex) + '"]/div[2]/table/tbody/tr[1]/td[7]', true)

    'verify Bank Acc detail'
    checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewBankDetail).toUpperCase(), (resultBankAcc[index++]).toUpperCase(), 
            false))

    'verify Bank Acc branch'
    checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewBankBranch).toUpperCase(), (resultBankAcc[index++]).toUpperCase(), 
            false))

    'verify Bank Acc beginnning balance'
    checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewBeginningBalance).replace('.00', '').toUpperCase(), 
            (resultBankAcc[index++]).toUpperCase(), false))
}

'count bank statement table'
variableDataBank = DriverFactory.getWebDriver().findElements(By.cssSelector('#BankAccount > div lib-ucsubsection'))

'looping bank statement app view'
for (int bankIndex = 0; bankIndex < variableDataBank.size(); bankIndex++) {
    variableDataBankStatement = DriverFactory.getWebDriver().findElements(By.cssSelector(('#BankAccInfo' + bankIndex) + 
            '> div:nth-child(2) > table > tbody tr'))

    if (variableDataBankStatement.size() > 1) {
        'modify object bank name - bank acc no - acc name'
        modifyNewBankDetail = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 
            'equals', ('//*[@id="BankAccount"]/div/div[' + (bankIndex + 1)) + ']/lib-ucsubsection/div/form/div/h4', true)

        bankDetail = WebUI.getText(modifyNewBankDetail).split(' - ')

        'verify Bank Acc Statement'
        ArrayList<String> resultBankAccStatement = CustomKeywords.'appView.verifyAppView.checkBankStatData'(sqlconnectionLOS, 
            appno, bankDetail[1], GlobalVariable.MSName)

        index = 0

        for (bankstatIndex = 1; bankstatIndex <= (resultBankAccStatement.size() / 7); bankstatIndex++) {
            'modify object Month'
            modifyNewMonth = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 'equals', 
                ((('//*[@id="BankAccInfo' + bankIndex) + '"]/div[2]/table/tbody/tr[') + (bankstatIndex + 1)) + ']/td[1]', 
                true)

            'modify object Year'
            modifyNewYear = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 'equals', 
                ((('//*[@id="BankAccInfo' + bankIndex) + '"]/div[2]/table/tbody/tr[') + (bankstatIndex + 1)) + ']/td[2]', 
                true)

            'modify object Debit Trsction count'
            modifyNewDebitTransaction = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 
                'xpath', 'equals', ((('//*[@id="BankAccInfo' + bankIndex) + '"]/div[2]/table/tbody/tr[') + (bankstatIndex + 
                1)) + ']/td[3]', true)

            'modify object Debit'
            modifyNewDebit = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 'equals', 
                ((('//*[@id="BankAccInfo' + bankIndex) + '"]/div[2]/table/tbody/tr[') + (bankstatIndex + 1)) + ']/td[4]', 
                true)

            'modify object Credit Trsction count'
            modifyNewCreditTransaction = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 
                'xpath', 'equals', ((('//*[@id="BankAccInfo' + bankIndex) + '"]/div[2]/table/tbody/tr[') + (bankstatIndex + 
                1)) + ']/td[5]', true)

            'modify object Credit'
            modifyNewCredit = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 
                'equals', ((('//*[@id="BankAccInfo' + bankIndex) + '"]/div[2]/table/tbody/tr[') + (bankstatIndex + 1)) + 
                ']/td[6]', true)

            'modify object Balance amount'
            modifyNewBalanceAmount = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 
                'equals', ((('//*[@id="BankAccInfo' + bankIndex) + '"]/div[2]/table/tbody/tr[') + (bankstatIndex + 1)) + 
                ']/td[7]', true)

            'verify Bank Acc statement month'
            checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewMonth).toUpperCase(), (resultBankAccStatement[
                    index++]).toUpperCase(), false))

            'verify Bank Acc statement year'
            checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewYear).toUpperCase(), (resultBankAccStatement[
                    index++]).toUpperCase(), false))

            'verify Bank Acc statement debit transaction'
            checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewDebitTransaction).toUpperCase(), (resultBankAccStatement[
                    index++]).toUpperCase(), false))

            'verify Bank Acc statement debit'
            checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewDebit).replace('.00', '').toUpperCase(), (resultBankAccStatement[
                    index++]).toUpperCase(), false))

            'verify Bank Acc statement credit transaction'
            checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewCreditTransaction).toUpperCase(), (resultBankAccStatement[
                    index++]).toUpperCase(), false))

            'verify Bank Acc statement credit'
            checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewCredit).replace('.00', '').toUpperCase(), (resultBankAccStatement[
                    index++]).toUpperCase(), false))

            'verify Bank Acc'
            checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewBalanceAmount).replace(',', '').toUpperCase(), 
                    (resultBankAccStatement[index++]).toUpperCase(), false))
        }
    }
}

'get arraylist legal doc from db'
ArrayList<String> resultLegalDoc = CustomKeywords.'appView.verifyAppView.checkLegalDocData'(sqlconnectionLOS, appno, GlobalVariable.MSName)

'count legal doc table'
variableData = DriverFactory.getWebDriver().findElements(By.cssSelector('#CustLegalDoc > table > tbody tr'))

index = 0

'looping legal index appview'
for (Legalindex = 1; Legalindex <= variableData.size(); Legalindex++) {
    'modify object Legal Doc Type'
    modifyNewLegalDocType = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 'equals', 
        ('//*[@id="CustLegalDoc"]/table/tbody/tr[' + Legalindex) + ']/td[1]', true)

    'modify object doc No'
    modifyNewLegalDocNo = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 'equals', 
        ('//*[@id="CustLegalDoc"]/table/tbody/tr[' + Legalindex) + ']/td[2]', true)

    'modify object Issue Date'
    modifyNewIssueDate = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 'equals', 
        ('//*[@id="CustLegalDoc"]/table/tbody/tr[' + Legalindex) + ']/td[3]', true)

    'modify object expired date'
    modifyNewExpiredDate = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 'equals', 
        ('//*[@id="CustLegalDoc"]/table/tbody/tr[' + Legalindex) + ']/td[4]', true)

    'modify object notary name'
    modifyNewNotaryName = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 'equals', 
        ('//*[@id="CustLegalDoc"]/table/tbody/tr[' + Legalindex) + ']/td[5]', true)

    'verify legal doc type'
    checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewLegalDocType).toUpperCase(), (resultLegalDoc[index++]).toUpperCase(), 
            false))

    'verify legal doc no'
    checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewLegalDocNo).toUpperCase(), (resultLegalDoc[index++]).toUpperCase(), 
            false))

    'verify legal doc issue date'
    checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewIssueDate).toUpperCase(), (resultLegalDoc[index++]).toUpperCase(), 
            false))

    'verify legal doc expired date'
    checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewExpiredDate).toUpperCase(), (resultLegalDoc[index++]).toUpperCase(), 
            false))

    'verify legal doc notary name'
    checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewNotaryName).toUpperCase(), (resultLegalDoc[index++]).toUpperCase(), 
            false))
}

'verify jika ada customer group'
if (WebUI.verifyNotMatch(WebUI.getText(findTestObject('AppView/CustomerMainData/ModifyObj')), 'NO DATA AVAILABLE', false, 
    FailureHandling.OPTIONAL)) {
    'count customer group table'
    variableData = DriverFactory.getWebDriver().findElements(By.cssSelector('#CustGrp > table > tbody tr'))

    ArrayList<String> resultCustGroup = CustomKeywords.'appView.verifyAppView.checkCustGroupData'(sqlconnectionLOS, appno, 
        GlobalVariable.MSName)

    for (int custGroupindex = 1; custGroupindex <= resultCustGroup.size(); custGroupindex++) {
        'modify object cust group name'
        modifyNewcustGroupName = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 
            'equals', ('//*[@id="CustGrp"]/table/tbody/tr[' + custGroupindex) + ']/td[2]', true)

        'verif customer group'
        checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewcustGroupName).toUpperCase(), (resultCustGroup[
                (custGroupindex - 1)]).toUpperCase(), false))
    }
}

'get arraylist other info from db'
ArrayList<String> resultOtherInfo = CustomKeywords.'appView.verifyAppView.checkOtherInfoData'(sqlconnectionLOS, appno, GlobalVariable.MSName)

'looping other attribute appview'
for (OthIndex = 1; OthIndex <= resultOtherInfo.size(); OthIndex++) {
    'modify object other info'
    modifyNewothinfoobject = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 'equals', 
        ('//*[@id="otherInfo"]/div[' + OthIndex) + ']/label[2]', true)

    'verify Other Info'
    checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewothinfoobject).toUpperCase(), (resultOtherInfo[(OthIndex - 
            1)]).toUpperCase(), false))
}

'get arraylist other attr list from db'
ArrayList<String> resultOtherAttrList = CustomKeywords.'appView.verifyAppView.checkOtherAttrData'(sqlconnectionLOS, appno, 
    GlobalVariable.MSName)

'verify Business period AML'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('AppView/CustomerMainData/attributelist Company/Business Period AML')).toUpperCase(), 
        (resultOtherAttrList[9]).toUpperCase(), false))

'verify Business Source AML'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('AppView/CustomerMainData/attributelist Company/Business Source AML')).toUpperCase(), 
        (resultOtherAttrList[8]).toUpperCase(), false))

'verify CSP USL Source AML'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('AppView/CustomerMainData/attributelist Company/CSP USL Source')).toUpperCase(), 
        (resultOtherAttrList[6]).toUpperCase(), false))

'verify Payment Type'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('AppView/CustomerMainData/attributelist Company/Payment Type')).toUpperCase(), 
        (resultOtherAttrList[7]).toUpperCase(), false))

'verify KYC Report'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('AppView/CustomerMainData/attributelist Company/KYC Report AMl')).toUpperCase(), 
        (resultOtherAttrList[5]).toUpperCase(), false))

'verify Exceed BMPK'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('AppView/CustomerMainData/attributelist Company/Exceed BMPK')).toUpperCase(), 
        (resultOtherAttrList[3]).toUpperCase(), false))

'verify Violate BMPK'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('AppView/CustomerMainData/attributelist Company/Violate BMPK')).toUpperCase(), 
        (resultOtherAttrList[2]).toUpperCase(), false))

'clik button back'
WebUI.click(findTestObject('Object Repository/AppView/CustomerMainData/buttonBack'))

if ((GlobalVariable.FlagWarning == 0) && (GlobalVariable.FlagFailed == 0)) {
    'write to excel success'
    CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '2. Customer', 0, GlobalVariable.NumofColm - 
        1, GlobalVariable.StatusSuccess)
}

def checkVerifyEqualOrMatch(Boolean isMatch) {
    if ((isMatch == false) && (GlobalVariable.FlagFailed == 0)) {
        'write to excel failed'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '2. Customer', 0, GlobalVariable.NumofColm - 
            1, GlobalVariable.StatusFailed)

        'write to excel reason failed verify equal or match'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '2. Customer', 1, GlobalVariable.NumofColm - 
            1, GlobalVariable.ReasonFailedVerifyEqualOrMatch)

        GlobalVariable.FlagFailed = 1
    }
}