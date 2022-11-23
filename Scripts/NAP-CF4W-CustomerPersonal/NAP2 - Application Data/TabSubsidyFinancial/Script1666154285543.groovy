import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import java.math.RoundingMode as RoundingMode
import java.text.NumberFormat as NumberFormat
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
import org.openqa.selenium.By as By
import org.openqa.selenium.Keys as Keys
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import groovy.sql.Sql as Sql

ArrayList <String> subsidyfaileddelete = new ArrayList<>()

def datafilefinancial = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabFinancialData')

def SubsidyTypeArray = datafilefinancial.getValue(GlobalVariable.NumofColm, 12).split(';')

def SubsidyfromValueArray = datafilefinancial.getValue(GlobalVariable.NumofColm, 13).split(';')

def AllocationformArray = datafilefinancial.getValue(GlobalVariable.NumofColm, 14).split(';')

def SubsidySourceArray = datafilefinancial.getValue(GlobalVariable.NumofColm, 15).split(';')

def SubsidyValueTypeArray = datafilefinancial.getValue(GlobalVariable.NumofColm, 16).split(';')

def SubsidyValueAmountArray = datafilefinancial.getValue(GlobalVariable.NumofColm, 17).split(';')

def SubsidyValuePercentageArray = datafilefinancial.getValue(GlobalVariable.NumofColm, 18).split(';')

'Mengambil nilai row keberapa dimulai data additional premi rate pada excel'
def TotalPremium = CustomKeywords.'customizeKeyword.getRow.getExcelRow'(GlobalVariable.DataFilePath, '8.TabInsuranceData', 'Total Premium') +
1

if (datafilefinancial.getValue(GlobalVariable.NumofColm, 51).equalsIgnoreCase('Yes')) {
	for (int i = 0; i < AllocationformArray.size(); i++) {
		if ((AllocationformArray[i]).equalsIgnoreCase('Discount Insurance')) {
			(SubsidyValueAmountArray[i]) = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabInsuranceData').getValue(
				GlobalVariable.NumofColm, TotalPremium + 2)
		}
		
		String overrideSubsidyValueAmountArray = SubsidyValueAmountArray.join(';')

		CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '10.TabFinancialData',
			16, GlobalVariable.NumofColm - 1, overrideSubsidyValueAmountArray)
	}
}

WebDriver driver = DriverFactory.getWebDriver()

ArrayList<WebElement> variable = driver.findElements(By.cssSelector('#FinData_Subsidy > div.table-responsive > table > tbody tr'))

int varsize = variable.size()

Sql sqlConnectionLOS = CustomKeywords.'dbConnection.connectDB.connectLOS'()

Sql sqlConnectionFOU = CustomKeywords.'dbConnection.connectDB.connectFOU'()

'Ambil appno dari confins'
String appNo = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/span_appNo'))

//Verif default subsidy based on rule, urutan masih statis
if(GlobalVariable.Role=="Testing"  && GlobalVariable.CheckRulePersonal=="Yes" && GlobalVariable.FirstTimeEntry == "Yes"){
	
	'Hashmap untuk mengambil arraylist-arraylist nilai result subsidy dari rule subsidy berdasarkan kondisi-kondisi'
	HashMap<String,ArrayList> result = CustomKeywords.'financialData.verifySubsidy.verifySubsidyDefault'(sqlConnectionLOS, sqlConnectionFOU,appNo)
	
	ArrayList<String> SubsidyFromType, SubsidyFromValue, SubsidyAlloc, SubsidySource, SubsidyValueType, SubsidyValue
	SubsidyFromType = result.get("FT")
	SubsidyFromValue = result.get("FV")
	SubsidyAlloc = result.get("Alloc")
	SubsidySource = result.get("Source")
	SubsidyValueType = result.get("Type")
	SubsidyValue= result.get("Value")
	
	'Jika pada confins tidak ada data'
	if (WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/tablesubsidynodata'),FailureHandling.OPTIONAL),
			'NO DATA AVAILABLE', false, FailureHandling.OPTIONAL)) {
			varsize = 0
	}
			
	'Verif jumlah data subsidy yang muncul pada confins sesuai dengan rule'
	WebUI.verifyEqual(varsize,SubsidyFromType.size())
	
	'Pengecekan jika jumlah data pada confins lebih dari 0 dan jumlah data subsidy pada confins sesuai dengan rule'
	if(varsize==SubsidyFromType.size() && varsize > 0){
		'Looping data subsidi pada confins'
		for (int i = 1; i <= varsize; i++) {
			 
			modifySubsidy()
			
			'Verif subsidy from type sesuai rule'
			if(WebUI.verifyMatch(CustomKeywords.'financialData.verifySubsidy.checkSubsidyFromTypeCode'(sqlConnectionLOS, WebUI.getText(modifyNewFromTypeName)),SubsidyFromType.get(i-1),false)==false){
				writeReasonFailedVerifRule()
			}
			
			'Verif subsidy from value sesuai rule'
			if(WebUI.verifyMatch(WebUI.getText(modifyNewFromValueName),SubsidyFromValue.get(i-1),false)==false){
				writeReasonFailedVerifRule()
			}
			
			'Verif subsidy allocation sesuai rule'
			if(WebUI.verifyMatch(CustomKeywords.'financialData.verifySubsidy.checkSubsidyAllocCode'(sqlConnectionLOS, WebUI.getText(modifyNewSubsidyAllocation)),SubsidyAlloc.get(i-1),false)==false){
				writeReasonFailedVerifRule()
			}
			
			'Verif subsidy value type sesuai rule'
			if(WebUI.verifyMatch(CustomKeywords.'financialData.verifySubsidy.checkSubsidyValueTypeCode'(sqlConnectionLOS, WebUI.getText(modifyNewSubsidyValueType)),SubsidyValueType.get(i-1),false)==false){
				writeReasonFailedVerifRule()
			}
			
			'Verif subsidy source sesuai rule'
			if(WebUI.verifyMatch(CustomKeywords.'financialData.verifySubsidy.checkSubsidySourceCode'(sqlConnectionLOS, WebUI.getText(modifyNewSubsidySource)),SubsidySource.get(i-1),false)==false){
				writeReasonFailedVerifRule()
			}
			
			'Pengecekan value type pada confins bernilai percentage'
			if(WebUI.getText(modifyNewSubsidyValueType).equalsIgnoreCase("Percentage")){
				
				'Verif subsidy percentage sesuai rule'
				if(WebUI.verifyEqual(Double.parseDouble(WebUI.getText(modifyNewSubsidyPercentage).replace(" %","")),Double.parseDouble(SubsidyValue.get(i-1)))==false){
					writeReasonFailedVerifRule()
				}
			}
			//Pengecekan value type pada confins bernilai amount
			else if(WebUI.getText(modifyNewSubsidyValueType).equalsIgnoreCase("Amount")){
				
				'Verif subsidy amount sesuai rule'
				if(WebUI.verifyEqual(Double.parseDouble(WebUI.getText(modifyNewSubsidyAmount).replace(",","")),Double.parseDouble(SubsidyValue.get(i-1)))==false){
					writeReasonFailedVerifRule()
				}
			}
		}
	}
}

if (WebUI.verifyNotMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/tablesubsidynodata'),FailureHandling.OPTIONAL),
	'NO DATA AVAILABLE', false, FailureHandling.OPTIONAL)) {
	for (int i = 1; i <= variable.size(); i++) {

		modifySubsidy()

		for (int subsidyarray = 1; subsidyarray <= SubsidyTypeArray.size(); subsidyarray++) {
			if (((WebUI.getText(modifyNewFromTypeName).equalsIgnoreCase(SubsidyTypeArray[(subsidyarray - 1)]) && WebUI.getText(
				modifyNewFromValueName).equalsIgnoreCase(SubsidyfromValueArray[(subsidyarray - 1)])) && WebUI.getText(modifyNewSubsidyAllocation).equalsIgnoreCase(
				AllocationformArray[(subsidyarray - 1)])) && WebUI.getText(modifyNewSubsidySource).equalsIgnoreCase(SubsidySourceArray[
				(subsidyarray - 1)])) {
			
				'modify object button edit'
				modifyNewButtonEdit = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/Button_Edit'),
				'xpath', 'equals', ('//*[@id="FinData_Subsidy"]/div[2]/table/tbody/tr[' + i) + ']/td[8]/a[1]/i', true)
			
				WebUI.click(modifyNewButtonEdit, FailureHandling.OPTIONAL)

				'select multifinance / supplier'
				WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/select_--Select--MultifinanceSupplier'),
					SubsidyTypeArray[(subsidyarray - 1)], false)

				if ((SubsidyTypeArray[(subsidyarray - 1)]).equalsIgnoreCase('Supplier') || (SubsidyTypeArray[(subsidyarray - 1)]).equalsIgnoreCase('Multifinance')) {
					
					int i = 2
										
					if((SubsidyTypeArray[(subsidyarray - 1)]).equalsIgnoreCase('Supplier')){
						
						subsidyfromvalue = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/select_AllocationFromSupplier'),
							'xpath', 'equals', '/html/body/ngb-modal-window/div/div/app-subsidy-add-edit/form/div[2]/div/div['+ i++ +']/div/select', true)
						
					'select subsidy from value'
					WebUI.selectOptionByLabel(subsidyfromvalue,
						SubsidyfromValueArray[(subsidyarray - 1)], false)
					}
					
					subsidyallocationfrom = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/select_AllocationFromSupplier'),
						'xpath', 'equals', '/html/body/ngb-modal-window/div/div/app-subsidy-add-edit/form/div[2]/div/div['+ i++ +']/div/select', true)
					
					subsidyvaluesource = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/select_SubsidySourceSupplier'),
						'xpath', 'equals', '/html/body/ngb-modal-window/div/div/app-subsidy-add-edit/form/div[2]/div/div['+ i++ +']/div/select', true)
					
					subsidyvaluetype = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/Select_SubsidyValueTypeSupplier'),
						'xpath', 'equals', '/html/body/ngb-modal-window/div/div/app-subsidy-add-edit/form/div[2]/div/div['+ i++ +']/div/select', true)
					
					subsidyvalue = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/input_SubsidyValueSupplier'),
						'xpath', 'equals', '/html/body/ngb-modal-window/div/div/app-subsidy-add-edit/form/div[2]/div/div['+ i++ +']/div/input', true)
					
					'select subsidy allocation from '
					WebUI.selectOptionByLabel(subsidyallocationfrom,
						AllocationformArray[(subsidyarray - 1)], false)

					'select subsidy source'
					WebUI.selectOptionByLabel(subsidyvaluesource,
						SubsidySourceArray[(subsidyarray - 1)], false)

					'select subsidy value type'
					WebUI.selectOptionByLabel(subsidyvaluetype,
						SubsidyValueTypeArray[(subsidyarray - 1)], false)

					if ((SubsidyValueTypeArray[(subsidyarray - 1)]).equalsIgnoreCase('Amount')) {
						'input subsidy value amount'
						WebUI.setText(subsidyvalue,
							SubsidyValueAmountArray[(subsidyarray - 1)])
					} else if ((SubsidyValueTypeArray[(subsidyarray - 1)]).equalsIgnoreCase('Percentage')) {
						'input subsidy value percentage'
						WebUI.setText(subsidyvalue,
							SubsidyValuePercentageArray[(subsidyarray - 1)])
					}
				}
				
				'click button save subsidy'
				WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/button_SaveSubsidy'))

				'Verify gagal add'
				if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/select_--Select--MultifinanceSupplier'),
					10, FailureHandling.OPTIONAL)) {
					'Click cancel'
					WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/button_CancelSubsidy'))
				}
				
				break
			} else {
			
				'modify object button Delete'
				modifyNewButtonDelete = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/Button_Delete'),
						'xpath', 'equals', ('//*[@id="FinData_Subsidy"]/div[2]/table/tbody/tr[' + i) + ']/td[8]/a[2]/i', true)
				
				if (subsidyarray == SubsidyTypeArray.size()) {
					if (WebUI.verifyNotMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/tablesubsidynodata'),FailureHandling.OPTIONAL),
							'NO DATA AVAILABLE', false, FailureHandling.OPTIONAL)) {
						
						'get subsidy allocation'
						subsidyallocationnamebefore = WebUI.getText(modifyNewSubsidyAllocation)
								
						'click button delete'
						WebUI.click(modifyNewButtonDelete, FailureHandling.OPTIONAL)
								
						WebUI.acceptAlert(FailureHandling.OPTIONAL)
								
						if(i == variable.size()){
								if(WebUI.verifyElementNotPresent(modifyNewSubsidyAllocation, 5, FailureHandling.OPTIONAL)){
										variable = driver.findElements(By.cssSelector('#FinData_Subsidy > div.table-responsive > table > tbody tr'))
								}else{
										'add cust name failed kedalam array'
										subsidyfaileddelete.add(subsidyallocationnamebefore)
										continue
								}
									
						}else{
								'get cust name sebelum delete'
								subsidyallocationnameAfter = WebUI.getText(modifyNewSubsidyAllocation)
											
								if(WebUI.verifyNotMatch(subsidyallocationnameAfter, subsidyallocationnamebefore, false, FailureHandling.OPTIONAL)){
										variable = driver.findElements(By.cssSelector('#FinData_Subsidy > div.table-responsive > table > tbody tr'))
								}else{
										'add cust name failed kedalam array'
										subsidyfaileddelete.add(subsidyallocationnamebefore)
										continue
								}
						}
						
						i--
					} else {
						break
					}
				}
			}
		}
	}
}

if(subsidyfaileddelete.size() > 0){
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath,
			'10.TabFinancialData', 0, GlobalVariable.CopyAppColm - 1, GlobalVariable.StatusWarning)
	
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath,
			'10.TabFinancialData', 1, GlobalVariable.CopyAppColm - 1, GlobalVariable.ReasonFailedDelete + subsidyfaileddelete)
	
	GlobalVariable.FlagWarning++
}

ArrayList<WebElement> variableData = DriverFactory.getWebDriver().findElements(By.cssSelector('#FinData_Subsidy > div.table-responsive > table > tbody tr'))

int countData = variableData.size()

for (int s = 1; s <= SubsidyTypeArray.size(); s++) {
	for (int SubsidyCheck = 1; SubsidyCheck <= countData; SubsidyCheck++) {
		if (WebUI.verifyNotMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/tablesubsidynodata'),FailureHandling.OPTIONAL),
			'NO DATA AVAILABLE', false, FailureHandling.OPTIONAL)) {

			'modify object from type name'
			modifyNewFromTypeName = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/FromTypeName'),
				'xpath', 'equals', ('//*[@id="FinData_Subsidy"]/div[2]/table/tbody/tr[' + SubsidyCheck) + ']/td[1]', true)

			'modify object from value name'
			modifyNewFromValueName = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/FromValueName'),
				'xpath', 'equals', ('//*[@id="FinData_Subsidy"]/div[2]/table/tbody/tr[' + SubsidyCheck) + ']/td[2]', true)


			'modify object subsidy allocation'
			modifyNewSubsidyAllocation = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/SubsidyAllocation'),
				'xpath', 'equals', ('//*[@id="FinData_Subsidy"]/div[2]/table/tbody/tr[' + SubsidyCheck) + ']/td[3]', true)

			'modify object subsidy source'
			modifyNewSubsidySource = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/SubsidySource'),
				'xpath', 'equals', ('//*[@id="FinData_Subsidy"]/div[2]/table/tbody/tr[' + SubsidyCheck) + ']/td[5]', true)

			if (((!((SubsidyTypeArray[(s - 1)]).equalsIgnoreCase(WebUI.getText(modifyNewFromTypeName))) || !((SubsidyfromValueArray[
			(s - 1)]).equalsIgnoreCase(WebUI.getText(modifyNewFromValueName)))) || !((AllocationformArray[(s - 1)]).equalsIgnoreCase(
				WebUI.getText(modifyNewSubsidyAllocation)))) || !((SubsidySourceArray[(s - 1)]).equalsIgnoreCase(WebUI.getText(
					modifyNewSubsidySource)))) {
				if (countData == SubsidyCheck) {
					'click button add subsidy'
					WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/button_Add Subsidy'))

					'select multifinance / supplier'
					WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/select_--Select--MultifinanceSupplier'),
						'(?i)' + (SubsidyTypeArray[(s - 1)]), true)

					if ((SubsidyTypeArray[(s - 1)]).equalsIgnoreCase('Supplier')) {

						'select subsidy from value'
						WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/select_SubsidyFromValueSupplier'),
							'(?i)' + (SubsidyfromValueArray[(s - 1)]), true)
						
						'select subsidy allocation from '
						WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/select_AllocationFromSupplier'),
							'(?i)' + (AllocationformArray[(s - 1)]), true)
					
						'select subsidy source'
						WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/select_SubsidySourceSupplier'),
							'(?i)' + (SubsidySourceArray[(s - 1)]), true)
					
						'select subsidy value type'
						WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/Select_SubsidyValueTypeSupplier'),
							'(?i)' + (SubsidyValueTypeArray[(s - 1)]), true)
					
						if ((SubsidyValueTypeArray[(s - 1)]).equalsIgnoreCase('Amount')) {
							'input subsidy value amount'
							WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/input_SubsidyValueSupplier'),
								SubsidyValueAmountArray[(s - 1)])
						} else if ((SubsidyValueTypeArray[(s - 1)]).equalsIgnoreCase('Percentage')) {
							'input subsidy value percentage'
							WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/input_SubsidyValueSupplier'),
								SubsidyValuePercentageArray[(s - 1)])
						}
						
					} else if ((SubsidyTypeArray[(s - 1)]).equalsIgnoreCase('Multifinance')) {
						'select subsidy allocation from'
						WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/select_AllocationFromMultifinance'),
							'(?i)' + (AllocationformArray[(s - 1)]), true)
					
						'select subsidy source'
						WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/select_SubsidySourceMultifinance'),
							'(?i)' + (SubsidySourceArray[(s - 1)]), true)
					
						'select subsidy value type'
						WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/select_SubsidyValueTypeMultifinance'),
							'(?i)' + (SubsidyValueTypeArray[(s - 1)]), true)
					
						if ((SubsidyValueTypeArray[(s - 1)]).equalsIgnoreCase('Amount')) {
							'input subsidy value amount'
							WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/input_SubsidyValueAmountMultifinance'),
								SubsidyValueAmountArray[(s - 1)])
						} else if ((SubsidyValueTypeArray[(s - 1)]).equalsIgnoreCase('Percentage')) {
							'input subsidy value percentage'
							WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/input_SubsidyValueAmountMultifinance'),
								SubsidyValuePercentageArray[(s - 1)])
						}
					}
					
					'click button save subsidy'
					WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/button_SaveSubsidy'))

					'Verify gagal add'
					if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/select_--Select--MultifinanceSupplier'),
						10, FailureHandling.OPTIONAL)) {
						'Click cancel'
						WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/button_CancelSubsidy'))
					}
				}
			} else {
				break
			}
		} else if (WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/tablesubsidynodata'),FailureHandling.OPTIONAL),
			'NO DATA AVAILABLE', false, FailureHandling.OPTIONAL)) {
			if (datafilefinancial.getValue(GlobalVariable.NumofColm, 12) != '') {
				if (SubsidyTypeArray.size() > 0) {
					'click button add subsidy'
					WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/button_Add Subsidy'))

					'select multifinance / supplier'
					WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/select_--Select--MultifinanceSupplier'),
						'(?i)' + (SubsidyTypeArray[(s - 1)]), true)

					if ((SubsidyTypeArray[(s - 1)]).equalsIgnoreCase('Supplier')) {
						'select subsidy from value'
						WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/select_SubsidyFromValueSupplier'),
							'(?i)' + (SubsidyfromValueArray[(s - 1)]), true)
						
						'select subsidy allocation from '
						WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/select_AllocationFromSupplier'),
							'(?i)' + (AllocationformArray[(s - 1)]), true)
					
						'select subsidy source'
						WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/select_SubsidySourceSupplier'),
							'(?i)' + (SubsidySourceArray[(s - 1)]), true)
					
						'select subsidy value type'
						WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/Select_SubsidyValueTypeSupplier'),
							'(?i)' + (SubsidyValueTypeArray[(s - 1)]), true)
					
						if ((SubsidyValueTypeArray[(s - 1)]).equalsIgnoreCase('Amount')) {
							'input subsidy value amount'
							WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/input_SubsidyValueSupplier'),
								SubsidyValueAmountArray[(s - 1)])
						} else if ((SubsidyValueTypeArray[(s - 1)]).equalsIgnoreCase('Percentage')) {
							'input subsidy value percentage'
							WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/input_SubsidyValueSupplier'),
								SubsidyValuePercentageArray[(s - 1)])
						}
					} else if ((SubsidyTypeArray[(s - 1)]).equalsIgnoreCase('Multifinance')) {
						'select subsidy allocation from'
						WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/select_AllocationFromMultifinance'),
							'(?i)' + (AllocationformArray[(s - 1)]), true)
					
						'select subsidy source'
						WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/select_SubsidySourceMultifinance'),
							'(?i)' + (SubsidySourceArray[(s - 1)]), true)
					
						'select subsidy value type'
						WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/select_SubsidyValueTypeMultifinance'),
							'(?i)' + (SubsidyValueTypeArray[(s - 1)]), true)
					
						if ((SubsidyValueTypeArray[(s - 1)]).equalsIgnoreCase('Amount')) {
							'input subsidy value amount'
							WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/input_SubsidyValueAmountMultifinance'),
								SubsidyValueAmountArray[(s - 1)])
						} else if ((SubsidyValueTypeArray[(s - 1)]).equalsIgnoreCase('Percentage')) {
							'input subsidy value percentage'
							WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/input_SubsidyValueAmountMultifinance'),
								SubsidyValuePercentageArray[(s - 1)])
						}
					}
					
					'click button save subsidy'
					WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/button_SaveSubsidy'))

					'Verify gagal add'
					if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/select_--Select--MultifinanceSupplier'),
						10, FailureHandling.OPTIONAL)) {
						'Click cancel'
						WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/button_CancelSubsidy'))
					}
				}
			}
		}
	}
}

for (int SubsidyCheck = 1; SubsidyCheck <= variableData.size(); SubsidyCheck++) {
	if (WebUI.verifyNotMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/tablesubsidynodata'),FailureHandling.OPTIONAL),
		'NO DATA AVAILABLE', false, FailureHandling.OPTIONAL)) {
		String NewSubsidyAllocation = ('//*[@id="FinData_Subsidy"]/div[2]/table/tbody/tr[' + SubsidyCheck) + ']/td[3]'

		'modify object subsidy allocation'
		modifyNewSubsidyAllocation = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/SubsidyAllocation'),
			'xpath', 'equals', NewSubsidyAllocation, true)

		String NewSubsidyAmount = ('//*[@id="FinData_Subsidy"]/div[2]/table/tbody/tr[' + SubsidyCheck) + ']/td[7]'

		'modify object SubsidyAmount'
		modifyNewSubsidyAmount = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/SubsidyAmount'),
			'xpath', 'equals', NewSubsidyAmount, true)

		if (WebUI.getText(modifyNewSubsidyAllocation, FailureHandling.OPTIONAL).equalsIgnoreCase('SUBSIDY DP')) {
			GlobalVariable.SubsidyDPValue = WebUI.getText(modifyNewSubsidyAmount, FailureHandling.CONTINUE_ON_FAILURE).replace(
				',', '').replace('.00', '')
		}
	}
}

public modifySubsidy(){
	'modify object from type name'
	modifyNewFromTypeName = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/FromTypeName'),
		'xpath', 'equals', ('//*[@id="FinData_Subsidy"]/div[2]/table/tbody/tr[' + i) + ']/td[1]', true)
	 
	'modify object from value name'
	modifyNewFromValueName = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/FromValueName'),
		'xpath', 'equals', ('//*[@id="FinData_Subsidy"]/div[2]/table/tbody/tr[' + i) + ']/td[2]', true)
	 
	'modify object subsidy allocation'
	modifyNewSubsidyAllocation = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/SubsidyAllocation'),
		'xpath', 'equals', ('//*[@id="FinData_Subsidy"]/div[2]/table/tbody/tr[' + i) + ']/td[3]', true)
	
	'modify object subsidy value type'
	modifyNewSubsidyValueType = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/td_SubsidyValueType'),
		'xpath','equals',"//*[@id='FinData_Subsidy']/div[2]/table/tbody/tr["+i+"]/td[4]",true)
	 
	'modify object subsidy source'
	modifyNewSubsidySource = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/SubsidySource'),
		'xpath', 'equals', ('//*[@id="FinData_Subsidy"]/div[2]/table/tbody/tr[' + i) + ']/td[5]', true)
	
	'modify object subsidy percentage'
	modifyNewSubsidyPercentage = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/td_SubsidyPercentage'),'xpath','equals',"//*[@id='FinData_Subsidy']/div[2]/table/tbody/tr["+i+"]/td[6]",true)
	
	'modify object subsidy amount'
	modifyNewSubsidyAmount = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabFinancialData/td_SubsidyAmount'),'xpath','equals',"//*[@id='FinData_Subsidy']/div[2]/table/tbody/tr["+i+"]/td[7]",true)
}

def writeReasonFailedVerifRule() {
	'write to excel failed'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '10.TabFinancialData',
		0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)

	'Write To Excel GlobalVariable.StatusReason'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '10.TabFinancialData',
		1, GlobalVariable.NumofColm - 1, GlobalVariable.ReasonFailedVerifyRule)

	GlobalVariable.FlagFailed = 1
}

