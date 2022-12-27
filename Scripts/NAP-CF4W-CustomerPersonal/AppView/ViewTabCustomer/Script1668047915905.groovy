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
GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'(GlobalVariable.PathAppInquiryPersonal)

GlobalVariable.FlagWarning = 0

'connect DB los'
Sql sqlconnectionLOS = CustomKeywords.'dbConnection.connectDB.connectLOS'()

'get appno from confins'
appno = WebUI.getText(findTestObject('Object Repository/AppView/MainInformation/Label App No'))

'get custname'
custname = WebUI.getText(findTestObject('AppView/CustomerMainData/attributelistPersonal/CustomerName'))

'get cust main data arraylist from db'
ArrayList<String> resultCustomerMainData = CustomKeywords.'appView.verifyAppView.checkCustomerMainDataPersonal'(sqlconnectionLOS, 
    appno, custname)

'declare index'
int index = 0

'looping customer data'
for (custIndex = 1; custIndex <= resultCustomerMainData.size(); custIndex++) {
    if (custIndex == resultCustomerMainData.size()) {
        custIndex++
    }
    
    'modify object Cust Main'
    modifyNewCustMain = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 'equals', 
        ('//*[@id="CustPersonalMainDataId"]/div/div[' + custIndex) + ']/span/div/div[2]', true)

    'Verif Customer Main Data'
    checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewCustMain).toString().toUpperCase(), (resultCustomerMainData[
            index++]).toUpperCase(), false))
}

'get address arraylist from db'
ArrayList<String> resultAddress = CustomKeywords.'appView.verifyAppView.checkAddrData'(sqlconnectionLOS, appno, custname)

'count address table'
ArrayList<WebElement> variableData = DriverFactory.getWebDriver().findElements(By.cssSelector('#CustAddress > table > tbody tr'))

index = 0

'looping cust address'
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

    'verify Address phone 1'
    checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewPhone1).toUpperCase(), (resultAddress[index++]).toUpperCase(), 
            false))

    'verify Address phone 2'
    checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewPhone2).toUpperCase(), (resultAddress[index++]).toUpperCase(), 
            false))
}

'get arraylist family from db'
ArrayList<String> resultFam = CustomKeywords.'appView.verifyAppView.checkFamData'(sqlconnectionLOS, appno)

'count fam table'
variableData = DriverFactory.getWebDriver().findElements(By.cssSelector('#Family > table > tbody tr'))

index = 0

'looping family data'
for (Famindex = 1; Famindex <= variableData.size(); Famindex++) {
    'modify object fam Name'
    modifyNewFamName = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 'equals', 
        ('//*[@id="Family"]/table/tbody/tr[' + Famindex) + ']/td[2]', true)

    'modify object relationship'
    modifyNewRelationship = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 'equals', 
        ('//*[@id="Family"]/table/tbody/tr[' + Famindex) + ']/td[3]', true)

    'modify object birth place'
    modifyNewBirthplace = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 'equals', 
        ('//*[@id="Family"]/table/tbody/tr[' + Famindex) + ']/td[4]', true)

    'modify object birth date'
    modifyNewBirthDate = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 'equals', 
        ('//*[@id="Family"]/table/tbody/tr[' + Famindex) + ']/td[5]', true)

    'modify object button detail'
    modifyNewButtonDetail = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 'equals', 
        ('//*[@id="Family"]/table/tbody/tr[' + Famindex) + ']/td[6]/a/i', true)

    'verify FAM Name'
    checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewFamName).toUpperCase(), (resultFam[index++]).toUpperCase(), 
            false))

    'verify FAM relationship'
    checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewRelationship).toUpperCase(), (resultFam[index++]).toUpperCase(), 
            false))

    'verify FAM birth place'
    checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewBirthplace).toUpperCase(), (resultFam[index++]).toUpperCase(), 
            false))

    'verify FAM birth date'
    checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewBirthDate).toUpperCase(), (resultFam[index++]).toUpperCase(), 
            false))

    GlobalVariable.CustDetailName = WebUI.getText(modifyNewFamName)

    'click button detail'
    WebUI.click(modifyNewButtonDetail)

    'call test case verify app view family detail'
    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/AppView/ViewTabCustomer-SubjectPersonalDetail'), [:], FailureHandling.CONTINUE_ON_FAILURE)
	
	'clik button back'
	WebUI.click(findTestObject('AppView/CustomerMainData/attributelistPersonal/buttonBackFamily'))
}

'get arraylist emergency contact from db'
ArrayList<String> resultEC = CustomKeywords.'appView.verifyAppView.checkEmergencyContactData'(sqlconnectionLOS, appno, 
    custname)

'looping emergency contact'
for (ecIndex = 1; ecIndex <= resultEC.size(); ecIndex++) {
    'modify object emergency contact '
    modifyNewEmergencycontact = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 
        'equals', ('//*[@id="AppCustEmrgncCntctId"]/div/div[' + ecIndex) + ']/span/div/div[2]', true)

    'verify Emergency Contact person'
    checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewEmergencycontact).toUpperCase(), (resultEC[(ecIndex - 
            1)]).toUpperCase(), false))
}

'get arraylist financial data from db'
ArrayList<String> resultFindata = CustomKeywords.'appView.verifyAppView.checkFinancialDataPersonal'(sqlconnectionLOS, 
    appno, custname)

'count financial data table'
variableData = DriverFactory.getWebDriver().findElements(By.cssSelector('#ListCustFinData > table > tbody tr'))

'looping financial data'
for (finIndex = 1; finIndex <= variableData.size(); finIndex++) {
    'modify object financial data date'
    modifyNewfinancialDataDate = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 
        'equals', ('//*[@id="ListCustFinData"]/table/tbody/tr[' + finIndex) + ']/td[1]', true)

    'modify object financial data button detail'
    modifyNewButtonDetail = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 'equals', 
        ('//*[@id="ListCustFinData"]/table/tbody/tr[' + finIndex) + ']/td[2]/a/i', true)

    findate = WebUI.getText(modifyNewfinancialDataDate)

    'verify financial data date'
    checkVerifyEqualOrMatch(WebUI.verifyMatch(findate, (resultFindata[(finIndex - 1)]).toUpperCase(), false))

    'click button detail'
    WebUI.click(modifyNewButtonDetail)

    'get arraylist financial data detail'
    ArrayList<String> resultFindataDetail = CustomKeywords.'appView.verifyAppView.checkFinancialDetailPersonal'(sqlconnectionLOS, 
        appno, custname, findate)

    findetailindex = 0

    for (int detailindex = 1; detailindex <= ((resultFindataDetail.size() + 1) / 2); detailindex++) {
        'modify object financial detail kiri'
        modifyNewfinancialkiri = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 
            'equals', ('//*[@id="FinId"]/div/div[' + detailindex) + ']/label[2]', true)

        'modify object financial detail kanan'
        modifyNewfinancialkanan = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 
            'equals', ('//*[@id="FinId"]/div/div[' + detailindex) + ']/label[4]', true)

        'verify financial data kiri'
        checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewfinancialkiri).replace(',', ''), (resultFindataDetail[
                findetailindex++]).toUpperCase(), false))

        if (detailindex == ((resultFindataDetail.size() + 1) / 2)) {
            break
        }
        
        'verify financial data kanan'
        checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewfinancialkanan).replace(',', ''), (resultFindataDetail[
                findetailindex++]).toUpperCase(), false))
    }
    
    'click button back'
    WebUI.click(findTestObject('AppView/CustomerMainData/attributelistPersonal/buttonBackFinancial'))
}

'get arraylist fin data attr from db'
ArrayList<String> resultFindataattr = CustomKeywords.'appView.verifyAppView.checkFinancialAttrData'(sqlconnectionLOS, 
    appno, custname)

'looping fin data attr'
for (finIndex = 1; finIndex <= resultFindataattr.size(); finIndex++) {
    'modify object financial data attr'
    modifyNewfinancialDataAttr = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 
        'equals', ('//*[@id="FinAttrInfoId"]/div[' + finIndex) + ']/label[2]', true)

    'verify financial data attr'
    checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewfinancialDataAttr).replace(',', '').replace('.00', 
                '').toUpperCase(), (resultFindataattr[(finIndex - 1)]).toUpperCase(), false))
}

'get arraylist bank acc from db'
ArrayList<String> resultBankAcc = CustomKeywords.'appView.verifyAppView.checkBankAcc'(sqlconnectionLOS, appno, custname)

index = 0

'looping bank acc'
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

    'verify Bank Acc beginning balance'
    checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewBeginningBalance).replace('.00', '').toUpperCase(), 
            (resultBankAcc[index++]).toUpperCase(), false))
}

'count bank statement table'
variableDataBank = DriverFactory.getWebDriver().findElements(By.cssSelector('#BankAccount > div lib-ucsubsection'))

'looping bank statement'
for (int bankIndex = 0; bankIndex < variableDataBank.size(); bankIndex++) {
    variableDataBankStatement = DriverFactory.getWebDriver().findElements(By.cssSelector(('#BankAccInfo' + bankIndex) + 
            '> div:nth-child(2) > table > tbody tr'))

    if (variableDataBankStatement.size() > 1) {
        'modify object bank name - bank acc no - acc name'
        modifyNewBankDetail = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 
            'equals', ('//*[@id="BankAccount"]/div/div[' + (bankIndex + 1)) + ']/lib-ucsubsection/div/form/div/h4', true)

        'get bankdetail from confins'
        bankDetail = WebUI.getText(modifyNewBankDetail).split(' - ')

        'verify Bank Acc Statement'
        ArrayList<String> resultBankAccStatement = CustomKeywords.'appView.verifyAppView.checkBankStatData'(sqlconnectionLOS, 
            appno, bankDetail[1], custname)

        index = 0

        'looping bank acc statement'
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

            'verify Bank Acc statement balance amount'
            checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewBalanceAmount).replace(',', '').toUpperCase(), 
                    (resultBankAccStatement[index++]).toUpperCase(), false))
        }
    }
}

'verify jika ada customer group'
if (WebUI.verifyNotMatch(WebUI.getText(findTestObject('AppView/CustomerMainData/ModifyObj')), 'NO DATA AVAILABLE', false, 
    FailureHandling.OPTIONAL)) {
    'count customer group table'
    variableData = DriverFactory.getWebDriver().findElements(By.cssSelector('#CustGrp > table > tbody tr'))

    'get cust group from db'
    ArrayList<String> resultCustGroup = CustomKeywords.'appView.verifyAppView.checkCustGroupData'(sqlconnectionLOS, 
        appno, custname)

    'looping cust group'
    for (custGroupindex = 1; custGroupindex <= resultCustGroup.size(); custGroupindex++) {
        'modify object cust group name'
        modifyNewcustGroupName = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 
            'equals', ('//*[@id="CustGrp"]/table/tbody/tr[' + custGroupindex) + ']/td[2]', true)

        'verif customer group'
        checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewcustGroupName).toUpperCase(), (resultCustGroup[
                (custGroupindex - 1)]).toUpperCase(), false))
    }
}

'get arraylist other info from db'
ArrayList<String> resultOtherInfo = CustomKeywords.'appView.verifyAppView.checkOtherInfoData'(sqlconnectionLOS, appno, 
    custname)

'looping other info'
for (OthIndex = 1; OthIndex <= resultOtherInfo.size(); OthIndex++) {
    'modify object other info'
    modifyNewothinfoobject = WebUI.modifyObjectProperty(findTestObject('AppView/CustomerMainData/ModifyObj'), 'xpath', 'equals', 
        ('//*[@id="otherInfo"]/div[' + OthIndex) + ']/label[2]', true)

    'verify Other Info'
    checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(modifyNewothinfoobject).toUpperCase(), (resultOtherInfo[(OthIndex - 
            1)]).toUpperCase(), false))
}

'get arraylist other attr list from db'
ArrayList<String> resultOtherAttrList = CustomKeywords.'appView.verifyAppView.checkOtherAttrData'(sqlconnectionLOS, 
    appno, custname)

'verify Business period AML'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('AppView/CustomerMainData/attributelistPersonal/Business Period AML')).toUpperCase(), 
        (resultOtherAttrList[11]).toUpperCase(), false))

'verify Business source AML'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('AppView/CustomerMainData/attributelistPersonal/Business Source AML')).toUpperCase(), 
        (resultOtherAttrList[10]).toUpperCase(), false))

'verify CSP USL Source AML'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('AppView/CustomerMainData/attributelistPersonal/CSP USL Source')).toUpperCase(), 
        (resultOtherAttrList[6]).toUpperCase(), false))

'verify Department AML'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('AppView/CustomerMainData/attributelistPersonal/Department AML')).toUpperCase(), 
        (resultOtherAttrList[5]).toUpperCase(), false))

'verify Department AML'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('AppView/CustomerMainData/attributelistPersonal/Department AML2')).toUpperCase(), 
        (resultOtherAttrList[5]).toUpperCase(), false))

'verify Payment Type'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('AppView/CustomerMainData/attributelistPersonal/Payment Type')).toUpperCase(), 
        (resultOtherAttrList[7]).toUpperCase(), false))

'verify KYC Report'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('AppView/CustomerMainData/attributelistPersonal/KYC Report AML')).toUpperCase(), 
        (resultOtherAttrList[8]).toUpperCase(), false))

'verify Attend'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('AppView/CustomerMainData/attributelistPersonal/Attend')).toUpperCase(), 
        (resultOtherAttrList[9]).toUpperCase(), false))

'verify Aff with MF'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('AppView/CustomerMainData/attributelistPersonal/Aff With MF SLIK')).toUpperCase(), 
        (resultOtherAttrList[4]).toUpperCase(), false))

'verify Debtor Group SLIK'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('AppView/CustomerMainData/attributelistPersonal/Debtor Group SLIK')).toUpperCase(), 
        (resultOtherAttrList[1]).toUpperCase(), false))

'verify Exceed BMPK'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('AppView/CustomerMainData/attributelistPersonal/Exceed BMPK')).toUpperCase(), 
        (resultOtherAttrList[3]).toUpperCase(), false))

'verify Is Split Treasure'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('AppView/CustomerMainData/attributelistPersonal/Is Split Treasure')).toUpperCase(), 
        (resultOtherAttrList[0]).toUpperCase(), false))

'verify Violate BMPK'
checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('AppView/CustomerMainData/attributelistPersonal/Violate BMPK')).toUpperCase(), 
        (resultOtherAttrList[2]).toUpperCase(), false))

if ((GlobalVariable.FlagWarning == 0) && (GlobalVariable.FlagFailed == 0)) {
    'write to excel status success'
    CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '2. Customer', 0, GlobalVariable.NumofColm - 
        1, GlobalVariable.StatusSuccess)
}

def checkVerifyEqualOrMatch(Boolean isMatch) {
    if ((isMatch == false) && (GlobalVariable.FlagFailed == 0)) {
        'write to excel status failed'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '2. Customer', 0, GlobalVariable.NumofColm - 
            1, GlobalVariable.StatusFailed)

        'write to excel reason failed verify equal or match'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '2. Customer', 1, GlobalVariable.NumofColm - 
            1, GlobalVariable.ReasonFailedVerifyEqualOrMatch)

        GlobalVariable.FlagFailed = 1
    }
}

