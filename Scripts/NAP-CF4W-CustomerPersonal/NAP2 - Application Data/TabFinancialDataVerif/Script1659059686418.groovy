import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import java.math.RoundingMode as RoundingMode
import org.openqa.selenium.By as By
import org.openqa.selenium.WebDriver as WebDriver
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
import groovy.sql.Sql as Sql

'get data file path'
GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'("\\Excel\\"+ GlobalVariable.LOB +"\\2.1 DataFile_NAP_CF4W.xlsx")

'Ambil appno dari confins'
String appNo = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/span_appNo'))

'koneksi db los'
Sql sqlConnectionLOS = CustomKeywords.'dbConnection.connectDB.connectLOS'()

'declare excelPathTabFinancial'
excelPathTabFinancial = 'NAP-'+ GlobalVariable.LOB +'-CustomerPersonal/NAP2-ApplicationData/TabFinancialData'

'declare excelPathTabApplication'
excelPathTabApplication = 'NAP-'+ GlobalVariable.LOB +'-CustomerPersonal/NAP2-ApplicationData/TabApplicationData'

'get data file path simulasi'
def datafilepathsim = CustomKeywords.'dbConnection.connectDB.getExcelPath'(GlobalVariable.PathSimulasiFinancial)

'write total expense'
CustomKeywords.'customizeKeyword.writeExcel.writeToExcelDecimal'(datafilepathsim,'Gross Yield (CF)',7,7,0)

'write total expense'
CustomKeywords.'customizeKeyword.writeExcel.writeToExcelDecimal'(datafilepathsim,'Gross Yield (CF)',8,7,0)

'declare NTFforProvisionCalc Value'
BigDecimal NTFforProvisionCalc

'get total asset price'
def TotalAssetPrice = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/label_TotalAssetPrice(InclAccessory)'), 
    FailureHandling.OPTIONAL).replace(',', '').replaceAll('\\s', '').replace('.00', '')

'get DPAssetAccessory'
def DPAssetAccessory = (WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/label_DP ASSET  ACCESSORY  () DP'), 
    FailureHandling.OPTIONAL).replace(',', '').replaceAll('\\s', '').split('/')[0]).replace('.00', '')

'get total fee capitalized value'
TotalfeeCapitalized = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/label_TOTAL FEE CAPITALIZED'), 
    FailureHandling.OPTIONAL).replace(',', '').replace('.00', '')

'get provision fee capitalized value'
ProvisionFeeCapitalize = WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/input_Provision Fee Capitalize'), 
    'value', FailureHandling.OPTIONAL).replace(',', '').replace('.00', '')

'get total insurancecap value'
TotalInsuranceCap = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/label_TOTAL INSURANCE CAPITALIZED'), 
    FailureHandling.OPTIONAL).replace(',', '').replace('.00', '')

'get provision percentage value'
ProvisionFeePercentage = WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/input_Provision Fee Percentage'), 
    'value', FailureHandling.OPTIONAL).replace('%', '').replaceAll('\\s', '')

'get provisionfee amount value'
strProvisionFeeAmount = WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/input_Provision Fee Amount'), 
    'value', FailureHandling.OPTIONAL).replace(',', '')

'get installment amount value'
strInstallmentAmount = WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/input_Installment Amount'), 
    'value', FailureHandling.OPTIONAL).replace(',', '')

'get attribute admin fee'
def AdminFeeValue = WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/input_Admin Fee'), 
    'value', FailureHandling.OPTIONAL).split(',').join()

'get attribute additional admin fee'
def AdditionalAdminFeeValue = WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/input_Additional Admin'), 
    'value', FailureHandling.OPTIONAL).split(',').join()

'get attribute notary fee'
def NotaryFeeValue = WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/input_Notary Fee'), 
    'value', FailureHandling.OPTIONAL).split(',').join()

'get attribute other fee'
def OtherFeeValue = WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/input_Other Fee'), 
    'value', FailureHandling.OPTIONAL).split(',').join()

'get attribute fiducia fee'
def FiduciaFeeAmount = WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/input_Fiducia Fee'), 
    'value', FailureHandling.OPTIONAL).split(',').join()

'convert Total Asset Price to BigDecimal'
BigDecimal BDTotalAssetPrice = new BigDecimal(TotalAssetPrice)

'convert DP Asset Accessory to BigDecimal'
BigDecimal BDDPAssetAccessory = new BigDecimal(DPAssetAccessory)

'convert total fee capitalize to BigDecimal'
BigDecimal BDTotalfeeCapitalized = new BigDecimal(TotalfeeCapitalized)

'convert provision fee capitalized to BigDecimal'
BigDecimal BDProvisionFeeCapitalize = new BigDecimal(ProvisionFeeCapitalize)

'convert Total insurance cap to BigDecimal'
BigDecimal BDTotalInsuranceCap = new BigDecimal(TotalInsuranceCap)

'convert AdminFeeValue to BigDecimal'
BigDecimal BDAdminFeeValue = new BigDecimal(AdminFeeValue)

'convert AdditionalAdminFeeValue to BigDecimal'
BigDecimal BDAdditionalAdminFeeValue = new BigDecimal(AdditionalAdminFeeValue)

'convert NotaryFeeValue to BigDecimal'
BigDecimal BDNotaryFeeValue = new BigDecimal(NotaryFeeValue)

'convert OtherFeeValue to BigDecimal'
BigDecimal BDOtherFeeValue = new BigDecimal(OtherFeeValue)

'convert FiduciaFeeAmount to BigDecimal'
BigDecimal BDFiduciaFeeAmount = new BigDecimal(FiduciaFeeAmount)

'convert provision fee amount to BigDecimal'
BigDecimal ProvisionFeeAmount = new BigDecimal(strProvisionFeeAmount)

'convert provision percentage to BigDecimal'
BigDecimal ProvisionPercentage = new BigDecimal(ProvisionFeePercentage)

'convert installment amount to BigDecimal'
BigDecimal BDInstallmentAmount = new BigDecimal(strInstallmentAmount)

//'calculate value for NTFprovisioncalc'
//NTFforProvisionCalc = BDTotalAssetPrice.subtract(BDDPAssetAccessory)
//
//'calculate value for total fee cap Exclude provision fee cap'
//BigDecimal TotalFeeCapEXCProvCap = BDTotalfeeCapitalized.subtract(BDProvisionFeeCapitalize)
//
//'calculate value NTFProvision add Total insurance Cap'
//BigDecimal NTFValueADDInsuranceCap = NTFforProvisionCalc.add(BDTotalInsuranceCap)
//
//'calculate NTFProvisioncalc Add Total fee Cap Exclude Provision fee cap'
//BigDecimal NTFValueADDCapEXCProvCap = NTFValueADDInsuranceCap.add(TotalFeeCapEXCProvCap)
//
//'calculate NTFValue multiply percentage'
//BigDecimal NTFValueFinal = Math.round(NTFValueADDCapEXCProvCap.multiply(ProvisionPercentage / 100))

//'check if calculate based is OTR-DP'
//if (findTestData(excelPathTabFinancial).getValue(
//    GlobalVariable.NumofColm, 37).equalsIgnoreCase('OTR-DP')) {
//    'check if provision fee type Percentage'
//    if (findTestData(excelPathTabFinancial).getValue(
//        GlobalVariable.NumofColm, 36) == 'Percentage') {
//        'calculate NTFProvisionCalc multiply percentage'
//        BigDecimal NTFValuexPercentage = NTFforProvisionCalc.multiply(ProvisionPercentage / 100)
//
//        'convert NTFValueXPercentage to string'
//        String strNTFValuexPercentage = NTFValuexPercentage.toString()
//
//        'verify match strNTFValueXPercentage and strProvisionAmount'
//        checkVerifyEqualOrMatch(WebUI.verifyMatch(strNTFValuexPercentage.replace('.000000', ''), strProvisionFeeAmount, false, FailureHandling.OPTIONAL))
//    } else if(findTestData(excelPathTabFinancial).getValue(
//        GlobalVariable.NumofColm, 36) == 'Amount'){
//        'calculate Provisionfeeamount divide NTFforprovisioncalc'
//        BigDecimal ProvisionFeeAmountDivideNTFValue = ProvisionFeeAmount.divide(NTFforProvisionCalc, 8, RoundingMode.HALF_EVEN)
//
//        'verify equal provisionfeeamountdiveNTFProvision and provision percentage'
//        checkVerifyEqualOrMatch(WebUI.verifyEqual(ProvisionFeeAmountDivideNTFValue, ProvisionPercentage / 100, FailureHandling.OPTIONAL))
//    }
//} else if(findTestData(excelPathTabFinancial).getValue(
//    GlobalVariable.NumofColm, 37).equalsIgnoreCase('OTR-DP + Ins Cptlz + Fee Cptlz(Excl. Provision)')) {
//    'check if provision fee type Percentage'
//    if (findTestData(excelPathTabFinancial).getValue(
//        GlobalVariable.NumofColm, 36) == 'Percentage') {
//        'convert NTFValueFinal to string'
//        String strNTFValueFinal = NTFValueFinal.toString()
//
//        'verify matvh NTFValueFinal and provision amount'
//        checkVerifyEqualOrMatch(WebUI.verifyMatch(strNTFValueFinal.replace('.000000', ''), strProvisionFeeAmount, false, FailureHandling.OPTIONAL))
//    } else if(findTestData(excelPathTabFinancial).getValue(
//        GlobalVariable.NumofColm, 36) == 'Amount'){
//        'calculate provisionfeeamount divide NTFValueexcludeprovisionfeecap'
//        ProvisionFeeAmountDivideNTFValueExcProv = ProvisionFeeAmount.divide(NTFValueADDCapEXCProvCap, 8, RoundingMode.HALF_EVEN)
//
//        'verify matvh provisionfeemaountdivideNTFValueexcludeprovisionfeecap and provision percentage'
//        checkVerifyEqualOrMatch(WebUI.verifyMatch(ProvisionFeeAmountDivideNTFValueExcProv.toString(), (ProvisionPercentage / 100).toString(), false, 
//            FailureHandling.OPTIONAL))
//    }
//}

'check if provision fee type Percentage/amount'
if (findTestData(excelPathTabFinancial).getValue(
        GlobalVariable.NumofColm, 36) == 'Percentage') {

        'verify match provision amount'
        checkVerifyEqualOrMatch(WebUI.verifyMatch(findTestData(excelPathTabFinancial).getValue(GlobalVariable.NumofColm,64).replace(".00",""), strProvisionFeeAmount, false, FailureHandling.OPTIONAL))
} else if(findTestData(excelPathTabFinancial).getValue(
        GlobalVariable.NumofColm, 36) == 'Amount'){

        'verify match provision percentage'
        checkVerifyEqualOrMatch(WebUI.verifyMatch(findTestData(excelPathTabFinancial).getValue(GlobalVariable.NumofColm,63), ProvisionPercentage.toString(), false,
            FailureHandling.OPTIONAL))
}


'get attribute admincapitalizevalue'
def AdminCapitalizeValue = WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/input_Admin Fee Capitalize_'), 
    'value', FailureHandling.OPTIONAL).split(',').join()

'get attribute AdditionalAdminFeeValue'
def AdditionalAdminFeeCapValue = WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/input_Additional Admin Capitalize'), 
    'value', FailureHandling.OPTIONAL).split(',').join()

'get attribute NotaryFeeCapitalizeValue'
def NotaryFeeCapitalizeValue = WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/input_Notary Fee Capitalize'), 
    'value', FailureHandling.OPTIONAL).split(',').join()

'get attribute OtherFeeCapitalize'
def OtherFeeCapitalize = WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/input_Other Fee Capitalize'), 
    'value', FailureHandling.OPTIONAL).split(',').join()

'get attribute FiduciaFeeCapitalize'
def FiduciaFeeCapitalize = WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/input_Fiducia Fee Capitalize'), 
    'value', FailureHandling.OPTIONAL).split(',').join()

'get attribute ProvisionFeeCapitalize'
def ProvisionFeeCapitalize = WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/input_Provision Fee Capitalize'), 
    'value', FailureHandling.OPTIONAL).split(',').join()

'get attribute NTF'
def NTF = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/label_NTF'), 
    FailureHandling.OPTIONAL).replace(',', '').replace('.00', '')

'get attribute DPAssetAccessoryValue'
def DPAssetAccessoryValue = (WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/label_DP ASSET  ACCESSORY  () DP'), 
    FailureHandling.OPTIONAL).replace(',', '').replaceAll('\\s', '').split('/')[0]).replace('.00', '')

'get attribute DPAssetAccessoryMinSubValue'
def DPAssetAccessoryMinSubValue = (WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/label_DP ASSET  ACCESSORY - SUBSIDY DP  () DP NETT'), 
    FailureHandling.OPTIONAL).replace(',', '').replaceAll('\\s', '').split('/')[0]).replace('.00', '')

'get attribute TotalAssetAccessoryPriceValue'
def TotalAssetAccessoryPriceValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/label_TOTAL ASSET ACCESSORY PRICE')).replace(
    ',', '').replace('.00', '')

'get attribute TotalARValue'
def TotalARValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/label_TOTAL AR')).replace(
    ',', '').replace('.00', '')

'get attribute Total InterestAmountValue'
def InterestAmountValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/label_TOTAL INTEREST'), 
    FailureHandling.OPTIONAL).replace(',', '').replace('.00', '')

'get attribute OS Principal Amount'
def OSPrincipalAmount = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/td_OSPrincipalAmount')).replace(
    ',', '').replace('.00', '')

'get attribute Principal Amount'
def PrincipalAmountValueSeq1 = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/td_PrincipalAmount')).replace(
    ',', '').replace('.00', '')

'get attribute OS Interest Amount'
def OSInterestAmount = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/td_OSInterestAmount')).replace(
    ',', '').replace('.00', '')

'get attribute Interest Amount'
def InterestAmountValueSeq1 = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/td_InterestAmount')).replace(
    ',', '').replace('.00', '')

'convert admincapitalizedvalue to BigDecimal'
BigDecimal intAdminCapitalizeValue = Integer.parseInt(AdminCapitalizeValue)

'convert AdditionalAdminFeeValue to BigDecimal'
BigDecimal intAdditionalAdminFeeCapValue = Integer.parseInt(AdditionalAdminFeeCapValue)

'convert NotaryFeeCapitalizeValue to BigDecimal'
BigDecimal intNotaryFeeCapitalizeValue = Integer.parseInt(NotaryFeeCapitalizeValue)

'convert OtherFeeCapitalize to BigDecimal'
BigDecimal intOtherFeeCapitalize = Integer.parseInt(OtherFeeCapitalize)

'convert FiduciaFeeCapitalize to BigDecimal'
BigDecimal intFiduciaFeeCapitalize = Integer.parseInt(FiduciaFeeCapitalize)

'convert ProvisionFeeCapitalize to BigDecimal'
BigDecimal intProvisionFeeCapitalize = Integer.parseInt(ProvisionFeeCapitalize)

'convert NTF to BigDecimal'
BigDecimal intNTF = Math.round(Double.parseDouble(NTF))

'convert DPAssetAccessoryValue to BigDecimal'
BigDecimal intDPAssetAccessoryValue = Integer.parseInt(DPAssetAccessoryValue)

'convert DPAssetAccessoryMinSubValue to BigDecimal'
BigDecimal intDPAssetAccessoryMinSubValue = Integer.parseInt(DPAssetAccessoryMinSubValue)

'convert TotalAssetAccessoryPriceValue to BigDecimal'
BigDecimal intTotalAssetAccessoryPriceValue = Integer.parseInt(TotalAssetAccessoryPriceValue)

'convert TotalAR to BigDecimal'
BigDecimal intTotalAR = Math.round(Double.parseDouble(TotalARValue))

'convert InterestAmountValue to BigDecimal'
BigDecimal intInterestAmountValue = Math.round(Double.parseDouble(InterestAmountValue))

'convert OSPrincipalAmount to BigDecimal'
BigDecimal intOSPrincipalAmountSeq1 = Math.round(Double.parseDouble(OSPrincipalAmount))

'convert PrincipalAmountValueSeq1 to BigDecimal'
BigDecimal intPrincipalAmountValueSeq1 = Math.round(Double.parseDouble(PrincipalAmountValueSeq1))

'convert OSInterestAmount to BigDecimal'
BigDecimal intOSInterestAmountSeq1 = Math.round(Double.parseDouble(OSInterestAmount))

'convert InterestAmountValueSeq1 to BigDecimal'
BigDecimal intInterestAmountValueSeq1 = Math.round(Double.parseDouble(InterestAmountValueSeq1))

'get value total fee capitalize'
int TotalFeeCapitalize = Integer.parseInt(WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/label_TOTAL FEE CAPITALIZED')).replace(
        '.00', '').split(',').join())

//'calculate total fee capitalize'
//int TotalCapitalizeValue = ((((intAdminCapitalizeValue + intAdditionalAdminFeeCapValue) + intNotaryFeeCapitalizeValue) + 
//intOtherFeeCapitalize) + intFiduciaFeeCapitalize) + intProvisionFeeCapitalize

'verify equal totalfeecapitalize'
checkVerifyEqualOrMatch(WebUI.verifyEqual(TotalFeeCapitalize, Integer.parseInt(findTestData(excelPathTabFinancial).getValue(GlobalVariable.NumofColm,59).replace(".00","").replace(",",""))))

'click button calculate'
WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/button_Calculate'))

'verify equal dp asset accessory'
checkVerifyEqualOrMatch(WebUI.verifyEqual(Integer.parseInt(findTestData(excelPathTabFinancial).getValue(GlobalVariable.NumofColm,61).replace(".00","").replace(",","")), intDPAssetAccessoryValue,
	FailureHandling.CONTINUE_ON_FAILURE))

'verify equal DPAssetAccessory minus subsidy DP = DPAssetAccessoryMinSubsidyDP'
checkVerifyEqualOrMatch(WebUI.verifyEqual(Integer.parseInt(findTestData(excelPathTabFinancial).getValue(GlobalVariable.NumofColm,62).replace(".00","").replace(",","")), intDPAssetAccessoryMinSubValue, 
    FailureHandling.CONTINUE_ON_FAILURE))

'Get value Total insurance value'
String TotalInsuranceValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/label_TOTAL INSURANCE')).replace(",","")

'get value total insurance capitalized value'
def TotalInsuranceCapitalizeValue = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/label_TOTAL INSURANCE CAPITALIZED')).replace(
    '.00', '')

'convert total insurance capitalize value to BigDecimal'
BigDecimal intTotalInsurancevalue = new BigDecimal(TotalInsuranceCapitalizeValue.replace(',', ''))

'verify match Total insurance(from tab insruance) and total insurance (from tab financial)'
checkVerifyEqualOrMatch(WebUI.verifyMatch(CustomKeywords.'financialData.checkRefYieldItem.checkInsCust'(sqlConnectionLOS, appNo), TotalInsuranceValue, false))

'verify match Insurance capitalize amount (from tab insurance) and total insurance capitalize (from tab financial)'
checkVerifyEqualOrMatch(WebUI.verifyMatch(findTestData(excelPathTabFinancial).getValue(GlobalVariable.NumofColm,60).replace(".00",""), TotalInsuranceCapitalizeValue, false))

'get total life insurance value'
def TotalLifeInsurance = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/label_TOTAL LIFE INSURANCE')).replace(
    '.00', '').split(',').join()

'get total life insurance capitalize'
def TotalLifeInsuranceCapitalize = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/label_TOTAL LIFE INSURANCE CAPITALIZED')).replace(
    '.00', '').split(',').join()

'convert total life insurance to BigDecimal'
BigDecimal intTotalLifeInsurance = Math.round(Double.parseDouble(TotalLifeInsurance))

'convert total life insurance capitalized to BigDecimal'
BigDecimal intTotalLifeInsuranceCapitalize = Math.round(Double.parseDouble(TotalLifeInsuranceCapitalize))

'convert capitalize premium percentage as Number'
Number CapitalizePremiumPercentage = GlobalVariable.CapitalizePremiumPercentage

'calculate total life insurance x capitalize premium percentage'
int multiplyTotalLifeInsurancexPercentage = Math.round(intTotalLifeInsurance * CapitalizePremiumPercentage)

'verify equal total life insurance cap = multiplyTotalLifeInsurancexPercentage'
checkVerifyEqualOrMatch(WebUI.verifyEqual(intTotalLifeInsuranceCapitalize, Math.round(Double.parseDouble(CustomKeywords.'financialData.checkRefYieldItem.checkLifeInsuranceCapitalize'(sqlConnectionLOS,appNo)))))

//'verify NTF Value'
//checkVerifyEqualOrMatch(WebUI.verifyEqual(((NTFforProvisionCalc + TotalFeeCapitalize) + intTotalInsurancevalue) + intTotalLifeInsuranceCapitalize, 
//    intNTF, FailureHandling.OPTIONAL))

'import webdriver'
WebDriver driver = DriverFactory.getWebDriver()

'get table row'
ArrayList<WebElement> counttdInstallment = driver.findElements(By.cssSelector('#FinData_FinData > form > div.ng-star-inserted > table > tbody tr'))

'declare installmentamountvalue, principalamountvalue, interestamountvalue'
Double installmentamountvalue, principalamountvalue, interestamountvalue

'get grace period method from confins'
String gracePeriodMethod = WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/select_--Select--Interest OnlyRoll Over'),'value')

'get grace period num from confins and parse it to integer'
int gracePeriodNum = Integer.parseInt(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/input_Grace Period'),'value'))

'loop for tabel amortisasi '
for (int InstallmentSchemecount = 1; InstallmentSchemecount <= counttdInstallment.size(); InstallmentSchemecount++) {

    'modify object installmentamount'
    modifyNewInstallmentAmount = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/td_InstallmentAmount'), 
        'xpath', 'equals', ('//*[@id="FinData_FinData"]/form/div[3]/table/tbody/tr[' + InstallmentSchemecount) + 
    ']/td[2]', true)

	'get text installment amount'
    String strInstallmentamount = WebUI.getText(modifyNewInstallmentAmount, FailureHandling.OPTIONAL).replace(',', '').replace(
        '.00', '')

    'modify object principal amount'
    modifyNewPrincipalAmount = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/td_PrincipalAmount'), 
        'xpath', 'equals', ('//*[@id="FinData_FinData"]/form/div[3]/table/tbody/tr[' + InstallmentSchemecount) + ']/td[3]', true)

	'get principal amount'
    String strPrincipalamount = WebUI.getText(modifyNewPrincipalAmount, FailureHandling.OPTIONAL).replace(',', '').replace(
        '.00', '')

    'modify object interest amount'
    modifyNewInterestAmount = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/td_InterestAmount'), 
        'xpath', 'equals', ('//*[@id="FinData_FinData"]/form/div[3]/table/tbody/tr[' + InstallmentSchemecount) + ']/td[4]', true)

	'get interest amount'
    String strInterestamount = WebUI.getText(modifyNewInterestAmount, FailureHandling.OPTIONAL).replace(',', '').replace(
        '.00', '')
	
    'modify object OS Principal amount'
    modifyNewOSPrincipalAmount = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/td_OSPrincipalAmount'), 
        'xpath', 'equals', ('//*[@id="FinData_FinData"]/form/div[3]/table/tbody/tr[' + InstallmentSchemecount) + 
    ']/td[5]', true)

	'get os principal amount'
    String strOSPrincipalAmount = WebUI.getText(modifyNewOSPrincipalAmount, FailureHandling.OPTIONAL).replace(',', '').replace(
        '.00', '')

    'modify object OS Interest amount'
    modifyNewOSInterestAmount = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/td_OSInterestAmount'), 
        'xpath', 'equals', ('//*[@id="FinData_FinData"]/form/div[3]/table/tbody/tr[' + InstallmentSchemecount) + ']/td[6]', true)

	'get os interest amount'
    String strOSInterestAmount = WebUI.getText(modifyNewOSInterestAmount, FailureHandling.OPTIONAL).replace(',', '').replace(
        '.00', '')

    'count total of installment amount'
    if (installmentamountvalue == null) {
        installmentamountvalue = Double.parseDouble(strInstallmentamount)
    } else {
        installmentamountvalue = (installmentamountvalue + Double.parseDouble(strInstallmentamount))
    }
    
    'count total of principal amount'
    if (principalamountvalue == null) {
        principalamountvalue = Double.parseDouble(strPrincipalamount)
    } else {
        principalamountvalue = (principalamountvalue + Double.parseDouble(strPrincipalamount))
    }
    
    'count total of interest amount'
    if (interestamountvalue == null) {
        interestamountvalue = Double.parseDouble(strInterestamount)
    } else {
        interestamountvalue = (interestamountvalue + Double.parseDouble(strInterestamount))
    }
    
    'verify value not minus(-)'
    WebUI.verifyGreaterThanOrEqual(Double.parseDouble(strInstallmentamount), 0)

	'Jika grace period method bukan rollover atau jika rollover & seqno>graceperiodnum'
	if(!gracePeriodMethod.equalsIgnoreCase("ROLLOVER")|| InstallmentSchemecount>gracePeriodNum){
		'verify value not minus(-)'
		WebUI.verifyGreaterThanOrEqual(Double.parseDouble(strPrincipalamount), 0)
	}
    
    'verify value not minus(-)'
    WebUI.verifyGreaterThanOrEqual(Double.parseDouble(strInterestamount), 0)

    'verify value not minus(-)'
    WebUI.verifyGreaterThanOrEqual(Double.parseDouble(strOSPrincipalAmount), 0)

    'verify value not minus(-)'
    WebUI.verifyGreaterThanOrEqual(Double.parseDouble(strOSInterestAmount), 0)

	'Jika grace period method bukan rollover dan bukan interest only dan graceperiodnum = 0'
    if(!gracePeriodMethod.equalsIgnoreCase("ROLLOVER")&&!gracePeriodMethod.equalsIgnoreCase("INTEREST_ONLY")&&gracePeriodNum==0){
		'get first row installment amount value'
		if (InstallmentSchemecount == 1) {
			'Verify installment amount = installment amount seq1'
			checkVerifyEqualOrMatch(WebUI.verifyEqual(BDInstallmentAmount, Double.parseDouble(strInstallmentamount)))
		}
	}//jika grace period method = rollover atau interest only dan grace period num bukan 0
	else if((gracePeriodMethod.equalsIgnoreCase("ROLLOVER")||gracePeriodMethod.equalsIgnoreCase("INTEREST_ONLY"))&&gracePeriodNum!=0){
		if(InstallmentSchemecount==gracePeriodNum+1){
			'Verify installment amount = installment amount seq graceperiod+1'
			checkVerifyEqualOrMatch(WebUI.verifyEqual(BDInstallmentAmount, Double.parseDouble(strInstallmentamount)))
		}
	}
    
    'get last row value'
    if ((InstallmentSchemecount - counttdInstallment.size()) == 0) {
        'verify value is 0'
        checkVerifyEqualOrMatch(WebUI.verifyEqual(Double.parseDouble(strOSPrincipalAmount), 0))

        'verify value is 0'
        checkVerifyEqualOrMatch(WebUI.verifyEqual(Double.parseDouble(strOSInterestAmount), 0))
    }
}

'verify equal Interest amount seq 1 + os interest amount seq1 = Total Interest'
checkVerifyEqualOrMatch(WebUI.verifyEqual(intOSInterestAmountSeq1 + intInterestAmountValueSeq1, intInterestAmountValue, FailureHandling.OPTIONAL))

'verify equal principal amount seq 1 + os principal amount seq1 = NTF'
checkVerifyEqualOrMatch(WebUI.verifyEqual(intOSPrincipalAmountSeq1 + intPrincipalAmountValueSeq1, intNTF, FailureHandling.OPTIONAL))

'verify equal principal amount seq 1 + os principal amount seq1 + Interest amount seq 1 + os interest amount seq1 = Total AR'
checkVerifyEqualOrMatch(WebUI.verifyEqual(((intOSPrincipalAmountSeq1 + intPrincipalAmountValueSeq1) + intOSInterestAmountSeq1) + intInterestAmountValueSeq1, 
    intTotalAR, FailureHandling.OPTIONAL))

'verify pincipal amount value = NTF'
checkVerifyEqualOrMatch(WebUI.verifyEqual(principalamountvalue, intNTF, FailureHandling.OPTIONAL))

//'verity interest amount value - total interest'
//checkVerifyEqualOrMatch(WebUI.verifyEqual(interestamountvalue, intInterestAmountValue, FailureHandling.OPTIONAL))

'verify PrincipalAmount+InteresetAmount == AR Value'
checkVerifyEqualOrMatch(WebUI.verifyEqual(principalamountvalue + interestamountvalue, intTotalAR, FailureHandling.OPTIONAL))

'verify installmentamountvalue == AR Value'
checkVerifyEqualOrMatch(WebUI.verifyEqual(installmentamountvalue, intTotalAR, FailureHandling.OPTIONAL))

//'verify intNTF + intInterestAmountValue == AR Value'
//checkVerifyEqualOrMatch(WebUI.verifyEqual(intNTF + intInterestAmountValue, intTotalAR, FailureHandling.OPTIONAL))

'verify equal principal amount seq 1 + os principal amount seq1 + Interest amount seq 1 + os interest amount seq1 = principalamountvalue + interestamountvalue'
checkVerifyEqualOrMatch(WebUI.verifyEqual(((intOSPrincipalAmountSeq1 + intPrincipalAmountValueSeq1) + intOSInterestAmountSeq1) + intInterestAmountValueSeq1, 
    principalamountvalue + interestamountvalue, FailureHandling.OPTIONAL))

'verify equal principal amount seq 1 + os principal amount seq1 + Interest amount seq 1 + os interest amount seq1 = installmentamountvalue'
checkVerifyEqualOrMatch(WebUI.verifyEqual(((intOSPrincipalAmountSeq1 + intPrincipalAmountValueSeq1) + intOSInterestAmountSeq1) + intInterestAmountValueSeq1, 
    installmentamountvalue, FailureHandling.OPTIONAL))

'verify equal principal amount seq 1 + os principal amount seq1 + Interest amount seq 1 + os interest amount seq1 = intNTF + intInterestAmountValue'
checkVerifyEqualOrMatch(WebUI.verifyEqual(((intOSPrincipalAmountSeq1 + intPrincipalAmountValueSeq1) + intOSInterestAmountSeq1) + intInterestAmountValueSeq1, 
    intNTF + intInterestAmountValue, FailureHandling.OPTIONAL))

'verify rumus 1 == rumus 2'
checkVerifyEqualOrMatch(WebUI.verifyEqual(principalamountvalue + interestamountvalue, installmentamountvalue, FailureHandling.OPTIONAL))

'verify rumus 1 == rumus 3'
checkVerifyEqualOrMatch(WebUI.verifyEqual(principalamountvalue + interestamountvalue, intNTF + intInterestAmountValue, FailureHandling.OPTIONAL))

'verify rumus 2 == rumus 3'
checkVerifyEqualOrMatch(WebUI.verifyEqual(installmentamountvalue, intNTF + intInterestAmountValue, FailureHandling.OPTIONAL))

'Ambil dan simpan nilai asset price dari confins tab financial data'
String textAssetPrice = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/label_TOTAL ASSET PRICE')).replace(
    ',', '')

'Ambil dan simpan nilai asset accessory price dari confins tab financial data'
String textAssetAccessoryPrice = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/label_TOTAL ASSET ACCESSORY PRICE')).replace(
    ',', '')

'Ambil dan simpan nilai asset price incl accessory dari confins tab financial data'
String textAssetPriceInclAccessory = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/label_TotalAssetPrice(InclAccessory)')).replace(
    ',', '')

'Verifikasi perhitungan asset price'
checkVerifyEqualOrMatch(WebUI.verifyMatch(textAssetPrice, findTestData(excelPathTabFinancial).getValue(GlobalVariable.NumofColm,56).replace(",",""), false))

'Verifikasi perhitungan asset price incl accessories'
checkVerifyEqualOrMatch(WebUI.verifyMatch(textAssetPriceInclAccessory, findTestData(excelPathTabFinancial).getValue(GlobalVariable.NumofColm,55).replace(",",""), 
    false))

'Verifikasi perhitungan asset accessory price'
checkVerifyEqualOrMatch(WebUI.verifyMatch(textAssetAccessoryPrice, findTestData(excelPathTabFinancial).getValue(GlobalVariable.NumofColm,57).replace(",",""), false))

'write rounding'
CustomKeywords.'customizeKeyword.writeExcel.writeToExcelDecimal'(datafilepathsim,'Regular Fixed',14,1, Double.parseDouble(findTestData(excelPathTabFinancial).getValue(GlobalVariable.NumofColm, 52)))

'write asset price incl acc'
CustomKeywords.'customizeKeyword.writeExcel.writeToExcelDecimal'(datafilepathsim,'Gross Yield (CF)',4,1, Double.parseDouble(findTestData(excelPathTabFinancial).getValue(GlobalVariable.NumofColm, 55).replace(",","")))

'write dp asset accessory nett'
CustomKeywords.'customizeKeyword.writeExcel.writeToExcelDecimal'(datafilepathsim,'Gross Yield (CF)',1,1, Double.parseDouble(findTestData(excelPathTabFinancial).getValue(GlobalVariable.NumofColm, 62).replace(",","")))

'write dp asset accessory '
CustomKeywords.'customizeKeyword.writeExcel.writeToExcelDecimal'(datafilepathsim,'Gross Yield (CF)',5,1, Double.parseDouble(findTestData(excelPathTabFinancial).getValue(GlobalVariable.NumofColm, 61).replace(",","")))

Integer DiffRateAmt = Integer.parseInt(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/DiffRateAmt'),"value").replace(",","")) - Integer.parseInt(WebUI.getAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/SubsidyDiffRate'),"value").replace(",",""))

if(DiffRateAmt>=0){
	'write diff rate amt'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcelNumber'(datafilepathsim,'Gross Yield (CF)',38,1, 0)
}
else{
'write diff rate amt'
CustomKeywords.'customizeKeyword.writeExcel.writeToExcelNumber'(datafilepathsim,'Gross Yield (CF)',38,1,DiffRateAmt*(-1))
}

'write effective rate'
CustomKeywords.'customizeKeyword.writeExcel.writeToExcelDecimal'(datafilepathsim,'Gross Yield (CF)',7,1, Double.parseDouble(findTestData(excelPathTabFinancial).getValue(GlobalVariable.NumofColm, 44))/100)

'write tenor'
CustomKeywords.'customizeKeyword.writeExcel.writeToExcelDecimal'(datafilepathsim,'Gross Yield (CF)',8,1, Double.parseDouble(findTestData(excelPathTabApplication).getValue(GlobalVariable.NumofColm, 20).replace(",","")))

'get payment frequency from excel datafile'
String payFreq = findTestData(excelPathTabApplication).getValue(GlobalVariable.NumofColm, 19)

def num

'convert payment frequency to number untuk penghitungan simulasi'
if (payFreq == 'Monthly') {
	num = "1"
} else if (payFreq == 'Bimonthly') {
	num = "2"
} else if (payFreq == 'Quarterly') {
	num = "3"
} else if (payFreq == 'Trimester') {
	num = "4"
} else if (payFreq == 'Semi Annualy') {
	num = "6"
} else if (payFreq == 'Annualy') {
	num = "12"
}

'write payment frequency'
CustomKeywords.'customizeKeyword.writeExcel.writeToExcelDecimal'(datafilepathsim,'Gross Yield (CF)',9,1, Double.parseDouble(num.replace(",","")))

'write ins cust premi'
CustomKeywords.'customizeKeyword.writeExcel.writeToExcelDecimal'(datafilepathsim,'Gross Yield (CF)',5,4, Double.parseDouble(CustomKeywords.'financialData.checkRefYieldItem.checkInsCust'(sqlConnectionLOS, appNo).replace(",","")))

println(Double.parseDouble(CustomKeywords.'financialData.checkRefYieldItem.checkLifeInsCust'(sqlConnectionLOS, appNo).replace(",","")))
'write lifeins cust premi'
CustomKeywords.'customizeKeyword.writeExcel.writeToExcelDecimal'(datafilepathsim,'Gross Yield (CF)',6,4, Double.parseDouble(CustomKeywords.'financialData.checkRefYieldItem.checkLifeInsCust'(sqlConnectionLOS, appNo).replace(",","")))

'write admin fee'
CustomKeywords.'customizeKeyword.writeExcel.writeToExcelDecimal'(datafilepathsim,'Gross Yield (CF)',7,4, Double.parseDouble(CustomKeywords.'financialData.checkRefYieldItem.checkAdminFee'(sqlConnectionLOS, findTestData(excelPathTabFinancial)).replace(",","")))

'write provision fee'
CustomKeywords.'customizeKeyword.writeExcel.writeToExcelDecimal'(datafilepathsim,'Gross Yield (CF)',8,4, Double.parseDouble(CustomKeywords.'financialData.checkRefYieldItem.checkProvisionFee'(sqlConnectionLOS, findTestData(excelPathTabFinancial)).replace(",","")))

'write fiducia fee'
CustomKeywords.'customizeKeyword.writeExcel.writeToExcelDecimal'(datafilepathsim,'Gross Yield (CF)',9,4, Double.parseDouble(CustomKeywords.'financialData.checkRefYieldItem.checkFiduciaFee'(sqlConnectionLOS, findTestData(excelPathTabFinancial)).replace(",","")))

'write notary fee'
CustomKeywords.'customizeKeyword.writeExcel.writeToExcelDecimal'(datafilepathsim,'Gross Yield (CF)',10,4, Double.parseDouble(CustomKeywords.'financialData.checkRefYieldItem.checkNotaryFee'(sqlConnectionLOS, findTestData(excelPathTabFinancial)).replace(",","")))

'write other fee'
CustomKeywords.'customizeKeyword.writeExcel.writeToExcelDecimal'(datafilepathsim,'Gross Yield (CF)',12,4, Double.parseDouble(CustomKeywords.'financialData.checkRefYieldItem.checkOtherFee'(sqlConnectionLOS, findTestData(excelPathTabFinancial)).replace(",","")))

'write additional admin fee'
CustomKeywords.'customizeKeyword.writeExcel.writeToExcelDecimal'(datafilepathsim,'Gross Yield (CF)',13,4, Double.parseDouble(findTestData(excelPathTabFinancial).getValue(GlobalVariable.NumofColm, 22).replace(",","")))

'write ins premi insco'
CustomKeywords.'customizeKeyword.writeExcel.writeToExcelDecimal'(datafilepathsim,'Gross Yield (CF)',5,7, Double.parseDouble(CustomKeywords.'financialData.checkRefYieldItem.checkInsInsco'(sqlConnectionLOS, appNo).replace(",","")))

'write lifeins premi insco'
CustomKeywords.'customizeKeyword.writeExcel.writeToExcelDecimal'(datafilepathsim,'Gross Yield (CF)',6,7, Double.parseDouble(CustomKeywords.'financialData.checkRefYieldItem.checkLifeInsInsco'(sqlConnectionLOS, appNo).replace(",","")))

'write insurance capitalize'
CustomKeywords.'customizeKeyword.writeExcel.writeToExcelDecimal'(datafilepathsim,'Gross Yield (CF)',23,3, Double.parseDouble(findTestData(excelPathTabFinancial).getValue(GlobalVariable.NumofColm, 60).replace(",","")))

'write lifeins cap'
CustomKeywords.'customizeKeyword.writeExcel.writeToExcelDecimal'(datafilepathsim,'Gross Yield (CF)',24,3, Double.parseDouble(CustomKeywords.'financialData.checkRefYieldItem.checkLifeInsuranceCapitalize'(sqlConnectionLOS, appNo).replace(",","")))

'write admin fee cap'
CustomKeywords.'customizeKeyword.writeExcel.writeToExcelDecimal'(datafilepathsim,'Gross Yield (CF)',25,3, Double.parseDouble(findTestData(excelPathTabFinancial).getValue(GlobalVariable.NumofColm, 27).replace(",","")))

'write prov fee cap'
CustomKeywords.'customizeKeyword.writeExcel.writeToExcelDecimal'(datafilepathsim,'Gross Yield (CF)',26,3, Double.parseDouble(findTestData(excelPathTabFinancial).getValue(GlobalVariable.NumofColm, 41).replace(",","")))

'write fiducia fee cap'
CustomKeywords.'customizeKeyword.writeExcel.writeToExcelDecimal'(datafilepathsim,'Gross Yield (CF)',27,3, Double.parseDouble(findTestData(excelPathTabFinancial).getValue(GlobalVariable.NumofColm, 35).replace(",","")))

'write notary fee cap'
CustomKeywords.'customizeKeyword.writeExcel.writeToExcelDecimal'(datafilepathsim,'Gross Yield (CF)',28,3, Double.parseDouble(findTestData(excelPathTabFinancial).getValue(GlobalVariable.NumofColm, 31).replace(",","")))

'write other fee cap'
CustomKeywords.'customizeKeyword.writeExcel.writeToExcelDecimal'(datafilepathsim,'Gross Yield (CF)',30,3, Double.parseDouble(findTestData(excelPathTabFinancial).getValue(GlobalVariable.NumofColm, 33).replace(",","")))

'write additional admin cap'
CustomKeywords.'customizeKeyword.writeExcel.writeToExcelDecimal'(datafilepathsim,'Gross Yield (CF)',37,3, Double.parseDouble(findTestData(excelPathTabFinancial).getValue(GlobalVariable.NumofColm, 29).replace(",","")))



'Ambil nilai tdp dan simpan dari confins financial data'
String textTDP = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/label_TDP')).replace(
	',', '')

String totalAR, totalInterest

def instAmtRounded, GrossYieldVal,TDP, NTFVal

'Pengecekan first installment type'
if (WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/label_FIRST INSTALLMENT TYPE')).equalsIgnoreCase(
	'ADVANCE')) {
	'write advance value'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcelNumber'(datafilepathsim,'Gross Yield (CF)',15,1, 1)
	
	'write to excel grace period number'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcelNumber'(datafilepathsim,'Gross Yield (CF)',15,4, 0)
	
	'get total ar from simulation'
	totalAR = findTestData('Simulasi/Simulasi Angsuran Regular Fixed').getValue(5, 80)
	
	'get total interest from simulation'
	totalInterest = findTestData('Simulasi/Simulasi Angsuran Regular Fixed').getValue(5, 79)
	
	'get installment amount from simulation'
	installmentAmt = findTestData('Simulasi/Simulasi Angsuran Regular Fixed').getValue(2, 80)
	
	'write total AR ke simualasi'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcelDecimal'(datafilepathsim,'Gross Yield (CF)',2,1, Double.parseDouble(totalAR.replace(",","")))
	
	'write besar angsuran ke simualasi'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcelDecimal'(datafilepathsim,'Gross Yield (CF)',16,1, Double.parseDouble(installmentAmt.replace(",","")))
	
	CustomKeywords.'customizeKeyword.openCloseExcel.openCloseFileWithRefreshVal'(datafilepathsim)
	
	WebUI.delay(5)
	
	'get ntf value from simulation'
	NTFVal = findTestData('Simulasi/Simulasi Gross Yield').getValue(2, 7)
	
	'get tdp from simulation'
	TDP = findTestData('Simulasi/Simulasi Gross Yield').getValue(2, 15)
	
	'write total AR ke datafile'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcelDecimal'(GlobalVariable.DataFilePath,'10.TabFinancialData',69,GlobalVariable.NumofColm-1, Double.parseDouble(totalAR.replace(",","")))
	
	'write total Interest ke datafile'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcelDecimal'(GlobalVariable.DataFilePath,'10.TabFinancialData',68,GlobalVariable.NumofColm-1, Double.parseDouble(totalInterest.replace(",","")))
	
	'get installment amount yang telah dibulatkan nilainya'
	instAmtRounded  = findTestData('Simulasi/Simulasi Gross Yield').getValue(2, 18)
		
	'write installment amount'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcelDecimal'(GlobalVariable.DataFilePath,'10.TabFinancialData',67,GlobalVariable.NumofColm-1, Double.parseDouble(instAmtRounded.replace(",","")))
	
	'write NTF'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcelDecimal'(GlobalVariable.DataFilePath,'10.TabFinancialData',66,GlobalVariable.NumofColm-1, Double.parseDouble(NTFVal.replace(",","")))
	
	'write TDP'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcelDecimal'(GlobalVariable.DataFilePath,'10.TabFinancialData',65,GlobalVariable.NumofColm-1, Double.parseDouble(TDP.replace(",","")))
	
	'get nilai gross yield from simulation'
	GrossYieldVal = findTestData('Simulasi/Simulasi Gross Yield').getValue(10, 84)
	
	'write gross yield'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcelDecimal'(GlobalVariable.DataFilePath,'10.TabFinancialData',70,GlobalVariable.NumofColm-1, Double.parseDouble(GrossYieldVal.replace(",","")))
	
} else if (WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/label_FIRST INSTALLMENT TYPE')).equalsIgnoreCase(
	'ARREAR')) {
	'write arrear value'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcelNumber'(datafilepathsim,'Gross Yield (CF)',15,1, 0)
	
	'Pengecekan grace period number dan type'
	if(!gracePeriodMethod.equalsIgnoreCase("ROLLOVER")&&!gracePeriodMethod.equalsIgnoreCase("INTEREST_ONLY")&&gracePeriodNum==0){
		
		'write to excel grace period number'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcelNumber'(datafilepathsim,'Gross Yield (CF)',15,4, 0)
		
		'get total ar'
		totalAR = findTestData('Simulasi/Simulasi Angsuran Regular Fixed').getValue(5, 14)
		
		'get total interest'
		totalInterest = findTestData('Simulasi/Simulasi Angsuran Regular Fixed').getValue(5, 15)
		
		'get installment amount'
		installmentAmt = findTestData('Simulasi/Simulasi Angsuran Regular Fixed').getValue(2, 14)
		
	}
	else if(gracePeriodMethod.equalsIgnoreCase("ROLLOVER")&&gracePeriodNum>0){
		
		'write to excel grace period number'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcelNumber'(datafilepathsim,'Gross Yield (CF)',15,4, gracePeriodNum)
		
		'write to excel installment amount for segno grace period'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcelNumber'(datafilepathsim,'Gross Yield (CF)',14,4, 0)
		
		'write to excel grace period number'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcelNumber'(datafilepathsim,'Regular Fixed',211,1, gracePeriodNum)
		
		'get total ar'
		totalAR = findTestData('Simulasi/Simulasi Angsuran Regular Fixed').getValue(5, 214)
		
		'get total interest'
		totalInterest = findTestData('Simulasi/Simulasi Angsuran Regular Fixed').getValue(5, 213)
		
		'get installment amount'
		installmentAmt = findTestData('Simulasi/Simulasi Angsuran Regular Fixed').getValue(2, 214)
		
		
	}
	else if(gracePeriodMethod.equalsIgnoreCase("INTEREST_ONLY")&&gracePeriodNum>0){
		'write to excel grace period number'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcelNumber'(datafilepathsim,'Gross Yield (CF)',15,4, gracePeriodNum)
		
		'write to excel installment amount for segno grace period'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcelDecimal'(datafilepathsim,'Gross Yield (CF)',14,4, Double.parseDouble(findTestData('Simulasi/Simulasi Angsuran Regular Fixed').getValue(3, 151).replace(",","")))
		
		'write to excel grace period number'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcelNumber'(datafilepathsim,'Regular Fixed',145,1, gracePeriodNum)
		
		'get total ar'
		totalAR = findTestData('Simulasi/Simulasi Angsuran Regular Fixed').getValue(5, 148)
		
		'get total interest'
		totalInterest = findTestData('Simulasi/Simulasi Angsuran Regular Fixed').getValue(5, 147)
		
		'get installment amount'
		installmentAmt = findTestData('Simulasi/Simulasi Angsuran Regular Fixed').getValue(2, 148)
		
	}
	
	'write total AR ke simualasi'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcelDecimal'(datafilepathsim,'Gross Yield (CF)',2,1, Double.parseDouble(totalAR.replace(",","")))
	
	'write besar angsuran ke simualasi'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcelDecimal'(datafilepathsim,'Gross Yield (CF)',16,1, Double.parseDouble(installmentAmt.replace(",","")))
	
	CustomKeywords.'customizeKeyword.openCloseExcel.openCloseFileWithRefreshVal'(datafilepathsim)
	
	WebUI.delay(5)
	
	'get ntf value from excel'
	NTFVal = findTestData('Simulasi/Simulasi Gross Yield').getValue(2, 7)
	
	'get tdp from excel'
	TDP = findTestData('Simulasi/Simulasi Gross Yield').getValue(2, 15)
	
	'write total AR ke datafile'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcelDecimal'(GlobalVariable.DataFilePath,'10.TabFinancialData',69,GlobalVariable.NumofColm-1, Double.parseDouble(totalAR.replace(",","")))
	
	'write total Interest ke datafile'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcelDecimal'(GlobalVariable.DataFilePath,'10.TabFinancialData',68,GlobalVariable.NumofColm-1, Double.parseDouble(totalInterest.replace(",","")))
	
	'get installment amount rounded'
	instAmtRounded  = findTestData('Simulasi/Simulasi Gross Yield').getValue(2, 18)
	
	'write installment amount'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcelDecimal'(GlobalVariable.DataFilePath,'10.TabFinancialData',67,GlobalVariable.NumofColm-1, Double.parseDouble(instAmtRounded.replace(",","")))
	
	'write NTF'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcelDecimal'(GlobalVariable.DataFilePath,'10.TabFinancialData',66,GlobalVariable.NumofColm-1, Double.parseDouble(NTFVal.replace(",","")))
	
	'write TDP'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcelDecimal'(GlobalVariable.DataFilePath,'10.TabFinancialData',65,GlobalVariable.NumofColm-1, Double.parseDouble(TDP.replace(",","")))
	
	'get gross yield value'
	GrossYieldVal = findTestData('Simulasi/Simulasi Gross Yield').getValue(10, 84)
	
	'write gross yield'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcelDecimal'(GlobalVariable.DataFilePath,'10.TabFinancialData',70,GlobalVariable.NumofColm-1, Double.parseDouble(GrossYieldVal.replace(",","")))
	
	
}

'verify interest amount confins x excel with tolerance'
checkVerifyEqualOrMatch(WebUI.verifyLessThanOrEqual(Math.abs(Double.parseDouble(InterestAmountValue.replace(",",""))-Double.parseDouble(totalInterest.replace(",",""))),10))
	
'verify total ar confins x excel'
checkVerifyEqualOrMatch(WebUI.verifyLessThanOrEqual(Math.abs(Double.parseDouble(TotalARValue.replace(",",""))-Double.parseDouble(totalAR.replace(",",""))), 10))
	
'verify installment amount (rounded) confins x excel'
checkVerifyEqualOrMatch(WebUI.verifyMatch(strInstallmentAmount, instAmtRounded.replace(",","").replace(".00",""), false))
	
'verify tdp confins x excel'
checkVerifyEqualOrMatch(WebUI.verifyMatch(textTDP, TDP.replace(",",""), false))
	
'verify ntf confins x excel'
checkVerifyEqualOrMatch(WebUI.verifyMatch(NTF, NTFVal.replace(",","").replace(".00",""), false))
	
'verify gross yield confins x excel'
checkVerifyEqualOrMatch(WebUI.verifyEqual(Math.round(Double.parseDouble(WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/label_GROSS YIELD')).replace(" %",""))), Math.round(Double.parseDouble(GrossYieldVal))))
	
'verify Flat rate'
checkVerifyEqualOrMatch(WebUI.verifyLessThanOrEqual(Double.parseDouble(findTestData('Simulasi/Simulasi Gross Yield').getValue(2, 19)) - Double.parseDouble(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabFinancialData/input_Flat Rate'),'value').replace(' %','')),0.5))

'Ambil nilai total fee dan simpan dari confins financial datas'
String textTotalFee = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/label_TOTAL FEE')).replace(
    ',', '')

'Convert total insurance dari string ke tipe data angka'
BigDecimal totalInsurance = Double.parseDouble(TotalInsuranceValue.replace(',', ''))

'Convert total fee dari string ke tipe data angka'
BigDecimal totalFee = Double.parseDouble(textTotalFee)

//'Perhitungan tdp'
//BigDecimal TDP = (((((intDPAssetAccessoryMinSubValue + totalFee) + totalInsurance) + intTotalLifeInsurance) - BDTotalfeeCapitalized) - 
//BDTotalInsuranceCap) - intTotalLifeInsuranceCapitalize

'verify total fee = all fees'
checkVerifyEqualOrMatch(WebUI.verifyEqual(Double.parseDouble(findTestData(excelPathTabFinancial).getValue(GlobalVariable.NumofColm,58).replace(",","")), totalFee))

//'Pengecekan jika installment type advance'
//if (WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/label_FIRST INSTALLMENT TYPE')).equalsIgnoreCase(
//    'Advance')) {
//    'Ambil installment amount segno 1'
//    BigDecimal firstInstallmentAmount = Double.parseDouble(WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/td_InstallmentAmount')).replace(
//            ',', ''))
//
//    'Nilai tdp ditambahkan dengan installment amount segno 1'
//    TDP += firstInstallmentAmount
//}

//'Verifikasi tdp pada confins sesuai perhitungan'
//checkVerifyEqualOrMatch(WebUI.verifyMatch(textTDP, String.format('%.2f', TDP), false))

'Verifikasi tdp paid at mf <= tdp'
WebUI.verifyLessThanOrEqual(Double.parseDouble(WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/input_TDP Paid at MF'), 
            'value').replace(',', '')), Double.parseDouble(textTDP.replace('.00', '')))

if (WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/label_FIRST INSTALLMENT TYPE')).equalsIgnoreCase(
	'ADVANCE')) {
	'verify interest amount "0.00" if insttalment type Advance'
	checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/td_InterestAmount')),
		'0.00', false))
} else if (WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/label_FIRST INSTALLMENT TYPE')).equalsIgnoreCase(
	'ARREAR')) {
	'verify interest amount NOT "0.00" if installment type arrear'
	checkVerifyEqualOrMatch(WebUI.verifyNotMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabFinancialData/td_InterestAmount')),
		'0.00', false))
}
	
public checkVerifyEqualOrMatch(Boolean isMatch){
		if(isMatch==false && GlobalVariable.FlagFailed==0){
	
			'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedVerifyEqualOrMatch'
			CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('10.TabFinancialData', GlobalVariable.NumofColm, GlobalVariable.StatusFailed, findTestData(excelPathTabFinancial).getValue(GlobalVariable.NumofColm, 2) + ';'+GlobalVariable.ReasonFailedVerifyEqualOrMatch)
			
			GlobalVariable.FlagFailed=1
		}
}