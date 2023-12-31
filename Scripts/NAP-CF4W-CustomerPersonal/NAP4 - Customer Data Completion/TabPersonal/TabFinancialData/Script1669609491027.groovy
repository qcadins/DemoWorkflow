import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import java.text.SimpleDateFormat as SimpleDateFormat
import java.util.ArrayList as ArrayList
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import java.awt.TexturePaintContext.Byte as Byte
import org.openqa.selenium.By as By
import org.openqa.selenium.WebElement as WebElement
import org.openqa.selenium.support.ui.Select as Select
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

GlobalVariable.StartIndex = 0

getDataFile()

'get count colm'
countcolm = findTestData(GlobalVariable.excelPath).getColumnNumbers()

ArrayList<String> financialDateDelete = new ArrayList<String>()

ArrayList<String> bankAccDelete = new ArrayList<String>()

'untuk mendapatkan posisi copy app dari excel'
for (int index = 2; index < (countcolm + 1); index++) {
	if (findTestData(GlobalVariable.excelPath).getValue(index, 9).equalsIgnoreCase(findTestData(excelPathCustDetail).getValue(GlobalVariable.ColmNAP4,
			12)) && findTestData(GlobalVariable.excelPath).getValue(index, 10).equalsIgnoreCase(findTestData(excelPathCustDetail).getValue(
			GlobalVariable.ColmNAP4, 13))) {
		GlobalVariable.StartIndex = index

		GlobalVariable.NumofVerifStore = index

		break
	}
}

'copyapp'
copyapp = findTestData('NAP-'+ GlobalVariable.LOB +'-CustomerPersonal/NAP4-CustomerDataCompletion/CustomerDataCompletion').getValue(
    GlobalVariable.NumofColm, 10)

String maritalStatus = WebUI.getText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData/label_MaritalStatus'))

'Check if Edit Untuk Financial Data'
if (copyapp.equalsIgnoreCase('Edit')) {
	
	'count untuk check if ada financial data'
	variableData = DriverFactory.getWebDriver().findElements(By.xpath('//*[@id="ListCustFinData"]/table/tbody/tr/td'))

	if (variableData.size() != 1) {
		'count table financialdata confins'
		ArrayList<WebElement> variable = DriverFactory.getWebDriver().findElements(By.cssSelector('#ListCustFinData > table > tbody tr'))

		for (int i = 1; i <= variable.size(); i++) {
			'modify object Date'
			modifyNewDate = WebUI.modifyObjectProperty(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation/select_addressType'),
				'xpath', 'equals', ('//*[@id="ListCustFinData"]/table/tbody/tr[' + i) + ']/td[1]', true)

			for (financialdata = GlobalVariable.StartIndex; financialdata < (countcolm + 1); financialdata++) {
				GlobalVariable.FlagFailed = 0

			
					if (findTestData(GlobalVariable.excelPath).getValue(financialdata, 9).equalsIgnoreCase(findTestData(excelPathCustDetail).getValue(
							GlobalVariable.ColmNAP4, 12)) && findTestData(GlobalVariable.excelPath).getValue(financialdata, 10).equalsIgnoreCase(
						findTestData(excelPathCustDetail).getValue(GlobalVariable.ColmNAP4, 13))) {
					
						'convert date '
						String converteddate = CustomKeywords.'customizeKeyword.convertDate.convertDateSdf'(findTestData(GlobalVariable.excelPath).getValue(financialdata, 17))

						if (WebUI.getText(modifyNewDate).replace('-', ' ').equalsIgnoreCase(converteddate)) {
							'modify object button edit'
							modifyNewbuttonedit = WebUI.modifyObjectProperty(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation/select_addressType'),
								'xpath', 'equals', ('//*[@id="ListCustFinData"]/table/tbody/tr[' + i) + ']/td[2]/a/i', true)

							'click button edit'
							WebUI.click(modifyNewbuttonedit)

							'call function input data financial'
							inputFinancialData(maritalStatus)

							break
						} else {
							if (findTestData(GlobalVariable.excelPath).getValue(financialdata + 1, 9).length() == 0) {
								'modify object button delete'
								modifyNewbuttondelete = WebUI.modifyObjectProperty(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation/select_addressType'),
									'xpath', 'equals', ('//*[@id="ListCustFinData"]/table/tbody/tr[' + i) + ']/td[2]/a[2]/i',
									true)

								'click button delete'
								WebUI.click(modifyNewbuttondelete)

								'accept alert'
								WebUI.acceptAlert(FailureHandling.OPTIONAL)

								if (i == variable.size()) {
									if (WebUI.verifyElementPresent(modifyNewDate, GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
										'add cust name failed kedalam array'
										financialDateDelete.add(converteddate)
									}
								} else {
									'get cust name sesudah delete'
									modifyDateNewAfter = WebUI.getText(modifyNewDate).replace('-', ' ')

									if (WebUI.verifyMatch(modifyDateNewAfter, converteddate, false, FailureHandling.OPTIONAL)) {
										'add cust name failed kedalam array'
										financialDateDelete.add(converteddate)
									}
								}
								
								'count ulang table financial data setelah delete'
								variable = DriverFactory.getWebDriver().findElements(By.cssSelector('#ListCustFinData > table > tbody tr'))

								i--
							}
						}
					}
					else {
						break
					}
				
			}
			
			'count untuk check if ada financial data'
			variableData = DriverFactory.getWebDriver().findElements(By.xpath('//*[@id="ListCustFinData"]/table/tbody/tr/td'))

			if (variableData.size() == 1) {
				break
			}
		}
	}
	
	if (financialDateDelete.size() > 0) {

		'Write To Excel GlobalVariable.StatusWarning and GlobalVariable.ReasonFailedDelete'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('5.FinancialData', GlobalVariable.StartIndex, GlobalVariable.StatusWarning, findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.StartIndex, 2) + ';' +GlobalVariable.ReasonFailedDelete + financialDateDelete)

		(GlobalVariable.FlagWarning)++
	}
	
	if (variableData.size() != 1) {
		'count ulang table financial data setelah edit/delete'
		variable = DriverFactory.getWebDriver().findElements(By.cssSelector('#ListCustFinData > table > tbody tr'))

		for (financialdata = GlobalVariable.StartIndex; financialdata < (countcolm + 1); financialdata++) {
			GlobalVariable.FlagFailed = 0

			if (findTestData(GlobalVariable.excelPath).getValue(financialdata, 9).equalsIgnoreCase(findTestData(excelPathCustDetail).getValue(
				GlobalVariable.ColmNAP4, 12)) && findTestData(GlobalVariable.excelPath).getValue(financialdata, 10).equalsIgnoreCase(
			findTestData(excelPathCustDetail).getValue(GlobalVariable.ColmNAP4, 13))) {
			
				for (int i = 1; i <= variable.size(); i++) {
					'modify object Date'
					modifyNewDate = WebUI.modifyObjectProperty(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation/select_addressType'),
						'xpath', 'equals', ('//*[@id="ListCustFinData"]/table/tbody/tr[' + i) + ']/td[1]', true)

						if (findTestData(GlobalVariable.excelPath).getValue(financialdata, 12).length() > 0) {
							String converteddate = CustomKeywords.'customizeKeyword.convertDate.convertDateSdf'(findTestData(GlobalVariable.excelPath).getValue(financialdata, 17))

							'verify date beda'
							if (!(WebUI.getText(modifyNewDate, FailureHandling.OPTIONAL).replace('-', ' ').equalsIgnoreCase(
								converteddate))) {
								if (i == variable.size()) {
									'click button add'
									WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData/button_Add'))

									'call function input financial data'
									inputFinancialData(maritalStatus)

									'count ulang table financial setelah add financial data baru'
									variable = DriverFactory.getWebDriver().findElements(By.cssSelector('#ListCustFinData > table > tbody tr'))

									break
								}
							} else if (WebUI.getText(modifyNewDate).replace('-', ' ').equalsIgnoreCase(converteddate)) {
								break
							}
						}
				}
			}
			else {
				break
			}
		}
	} else {
		for (financialdata = GlobalVariable.StartIndex; financialdata < (countcolm + 1); financialdata++) {
			
			if (findTestData(GlobalVariable.excelPath).getValue(financialdata, 9).equalsIgnoreCase(findTestData(excelPathCustDetail).getValue(GlobalVariable.ColmNAP4,
				12)) && findTestData(GlobalVariable.excelPath).getValue(financialdata, 10).equalsIgnoreCase(findTestData(excelPathCustDetail).getValue(
				GlobalVariable.ColmNAP4, 13))) {
				if (findTestData(GlobalVariable.excelPath).getValue(financialdata, 12).length() > 0) {
				
				'click button add'
				WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData/button_Add'))
	
				'call function input financial data'
				inputFinancialData(maritalStatus)
				
				}
			}
			else{
				break
			}
		
		}
	}
} else if (copyapp == 'No') {
	for (financialdata = GlobalVariable.StartIndex; financialdata < (countcolm + 1); financialdata++) {
			if (findTestData(GlobalVariable.excelPath).getValue(financialdata, 9).equalsIgnoreCase(findTestData(excelPathCustDetail).getValue(GlobalVariable.ColmNAP4,
					12)) && findTestData(GlobalVariable.excelPath).getValue(financialdata, 10).equalsIgnoreCase(findTestData(excelPathCustDetail).getValue(
					GlobalVariable.ColmNAP4, 13))) {
				if (findTestData(GlobalVariable.excelPath).getValue(financialdata, 12).length() > 0) {
					'click button add'
					WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerCompany/FinancialData/button_Add'))

					'call funtion input financial data'
					inputFinancialData(maritalStatus)
				}
			}
			else {
				break
			}
		 
	}
}

if (findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.StartIndex, 21).length() > 1) {
	'input jumlah harta'
	WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData/input_Jumlah Harta'),
		findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.StartIndex, 21))
}

'Check if Edit Untuk Bank Account dan Bank Statement'
if (copyapp.equalsIgnoreCase('Edit')) {
	
	'count table bank acc confins'
	variable = DriverFactory.getWebDriver().findElements(By.cssSelector('#CustBankAccSection > div > div table'))

	if (variable.size() > 0) {
		for (int i = 1; i <= variable.size(); i++) {
			'modify bank name - branch - bank no'
			modifyNewbankaccdetail = WebUI.modifyObjectProperty(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation/select_addressType'),
				'xpath', 'equals', ('//*[@id="CustBankAccSection"]/div/div/div[' + i) + ']/div[1]/h5', true)

			BankDetail = WebUI.getText(modifyNewbankaccdetail)

			for (financialdata = GlobalVariable.StartIndex; financialdata < (countcolm + 1); financialdata++) {
				int flagFailed = 0

					if (findTestData(GlobalVariable.excelPath).getValue(financialdata, 9).equalsIgnoreCase(findTestData(excelPathCustDetail).getValue(
							GlobalVariable.ColmNAP4, 12)) && findTestData(GlobalVariable.excelPath).getValue(financialdata, 10).equalsIgnoreCase(
						findTestData(excelPathCustDetail).getValue(
							GlobalVariable.ColmNAP4, 13))) {
						if (findTestData(GlobalVariable.excelPath).getValue(financialdata, 24).length() > 0) {
							bankdetailexcel = ((((((('- ' + findTestData(GlobalVariable.excelPath).getValue(financialdata, 24)) + ' - ') +
							findTestData(GlobalVariable.excelPath).getValue(financialdata, 25)) + ' - ') + findTestData(GlobalVariable.excelPath).getValue(
								financialdata, 27)) + ' - ') + findTestData(GlobalVariable.excelPath).getValue(financialdata, 26))

							if (BankDetail.equalsIgnoreCase(bankdetailexcel)) {
								'modify button edit'
								modifyNewbuttonedit = WebUI.modifyObjectProperty(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation/select_addressType'),
									'xpath', 'equals', ('//*[@id="CustBankAccSection"]/div/div/div[' + i) + ']/div[1]/div/button[1]',
									true)

								'click button edit'
								WebUI.click(modifyNewbuttonedit)

								'call function input bank'
								inputBank(copyapp, variable, flagFailed)

								break
							} else {
								if (findTestData(GlobalVariable.excelPath).getValue(financialdata + 1, 10).length() == 0) {
									'modify button delete'
									modifyNewbuttondelete = WebUI.modifyObjectProperty(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation/select_addressType'),
										'xpath', 'equals', ('//*[@id="CustBankAccSection"]/div/div/div[' + i) + ']/div[1]/div/button[2]',
										true)

									'click button delete'
									WebUI.click(modifyNewbuttondelete)

									'accept alert'
									WebUI.acceptAlert(FailureHandling.OPTIONAL)

									if (i == variable.size()) {
										if (WebUI.verifyElementNotPresent(modifyNewbankaccdetail, GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
											'add cust name failed kedalam array'
											bankAccDelete.add(BankDetail)
										}
									} else {
										'get cust name sebelum delete'
										modifyBankaccAfter = WebUI.getText(modifyNewbankaccdetail)

										if (WebUI.verifyNotMatch(modifyBankaccAfter, BankDetail, false, FailureHandling.OPTIONAL)) {
											'add cust name failed kedalam array'
											bankAccDelete.add(BankDetail)
										}
									}
									
									'count ulang table bank acc'
									variable = DriverFactory.getWebDriver().findElements(By.cssSelector('#CustBankAccSection > div > div table'))

									i--
								}
							}
						}
					}
					else {
						break
					}
			}
			
			'check if table bank acc sudah 0'
			if(variable.size() == 0){
				break
			}
		}
	}
	
	if (bankAccDelete.size() > 0) {

		'Write To Excel GlobalVariable.StatusWarning and GlobalVariable.ReasonFailedDelete'
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('5.FinancialData', GlobalVariable.StartIndex, GlobalVariable.StatusWarning, findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.StartIndex, 2) + ';' +GlobalVariable.ReasonFailedDelete + financialDateDelete)

		(GlobalVariable.FlagWarning)++
	}
	
	'count ulang table bank acc'
	variable = DriverFactory.getWebDriver().findElements(By.cssSelector('#CustBankAccSection > div > div table'))

	if(variable.size() > 0){
	for (financialdata = GlobalVariable.StartIndex; financialdata < (countcolm + 1); financialdata++) {
		int flagFailed = 0
		if (findTestData(GlobalVariable.excelPath).getValue(financialdata, 9).equalsIgnoreCase(findTestData(excelPathCustDetail).getValue(
			GlobalVariable.ColmNAP4, 12)) && findTestData(GlobalVariable.excelPath).getValue(financialdata, 10).equalsIgnoreCase(
			findTestData(excelPathCustDetail).getValue(
			GlobalVariable.ColmNAP4, 13))) {
				for (int i = 1; i <= variable.size(); i++) {
					'modify bank name - branch - bank no'
					modifyNewbankaccdetail = WebUI.modifyObjectProperty(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/AddressInformation/select_addressType'),
						'xpath', 'equals', ('//*[@id="CustBankAccSection"]/div/div/div[' + i) + ']/div[1]/h5', true)

					'get text bank acc detail'
					BankDetail = WebUI.getText(modifyNewbankaccdetail)

					
						if (findTestData(GlobalVariable.excelPath).getValue(financialdata, 24).length() > 0) {
							bankdetailexcel = ((((((('- ' + findTestData(GlobalVariable.excelPath).getValue(financialdata, 24)) + ' - ') +
							findTestData(GlobalVariable.excelPath).getValue(financialdata, 25)) + ' - ') + findTestData(GlobalVariable.excelPath).getValue(
								financialdata, 27)) + ' - ') + findTestData(GlobalVariable.excelPath).getValue(financialdata, 26))

							'verify date beda'
							if (!(BankDetail.equalsIgnoreCase(bankdetailexcel))) {
								if (i == variable.size()) {
									'click button add bank'
									WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData/button_AddBank'))

									'call function input bank'
									inputBank(copyapp, variable, flagFailed)
								}
							} else if (BankDetail.equalsIgnoreCase(bankdetailexcel)) {
								break
							}
						}

				}
			}
			else {
				break
			}
	}
	}else{
		for (financialdata = GlobalVariable.StartIndex; financialdata < (countcolm + 1); financialdata++) {
			int flagFailed = 0
	
				if (findTestData(GlobalVariable.excelPath).getValue(financialdata, 9).equalsIgnoreCase(findTestData(excelPathCustDetail).getValue(
                        GlobalVariable.ColmNAP4, 12)) && findTestData(GlobalVariable.excelPath).getValue(financialdata, 10).equalsIgnoreCase(findTestData(excelPathCustDetail).getValue(
						GlobalVariable.ColmNAP4, 13))) {
					if (findTestData(GlobalVariable.excelPath).getValue(financialdata, 24).length() > 0) {
						'click button add bank'
						WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData/button_AddBank'))
	
						'call function input bank'
						inputBank(copyapp, variable, flagFailed)
					}
				}
				else {
					break
				}
		}
	}
} else if (copyapp.equalsIgnoreCase('No')) {

	'count table bank acc confins'
	variable = DriverFactory.getWebDriver().findElements(By.cssSelector('#CustBankAccSection > div > div table'))
	for (financialdata = GlobalVariable.StartIndex; financialdata < (countcolm + 1); financialdata++) {
		int flagFailed = 0

			if (findTestData(GlobalVariable.excelPath).getValue(financialdata, 9).equalsIgnoreCase(findTestData(excelPathCustDetail).getValue(
                        GlobalVariable.ColmNAP4, 12)) && findTestData(GlobalVariable.excelPath).getValue(financialdata, 10).equalsIgnoreCase(findTestData(excelPathCustDetail).getValue(
					GlobalVariable.ColmNAP4, 13))) {
				if (findTestData(GlobalVariable.excelPath).getValue(financialdata, 24).length() > 0) {
					'click button add bank'
					WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData/button_AddBank'))

					'call function input bank'
					inputBank(copyapp, variable, flagFailed)
				}
			}
			else {
				break
			}
		
	}
}

'click button save and continue'
WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData/button_SaveFinancial'))

if (WebUI.verifyElementPresent(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData/th_Date'),
	GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
	'click button back'
	WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerDataCompletion/button_Back'))
}

if ((GlobalVariable.Role == 'Testing') && (GlobalVariable.CheckVerifStoreDBPersonal == 'Yes')) {
    'call test case verify store data financial'
    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/NAP4VerifyStoreData/Personal/TabFinancialDataVerifStoreDataDB'), 
        [:], FailureHandling.CONTINUE_ON_FAILURE)
}

def inputFinancialData(String maritalStatus) {
    GlobalVariable.FlagWarning = 0

    'input monthly income'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData/input_Monthly Income_'), 
        findTestData(GlobalVariable.excelPath).getValue(financialdata, 12))

    'input monthly other income'
    WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData/input_Other Income_'), 
        findTestData(GlobalVariable.excelPath).getValue(financialdata, 13))

    'pilih source monthly other income'
    WebUI.selectOptionByLabel(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData/select_BusinessOnline ShopSalaryStock Trading'), 
        findTestData(GlobalVariable.excelPath).getValue(financialdata, 14), false)

    monthlyInstallment = findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData/input_Monthly Installment Amount_')

    monthlyExpense = findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData/input_Monthly Expense Amount_')

    datesAsOf = findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData/input_Date As Of_form-')

    buttonCalculate = findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData/button_Calculate')

    buttonSave = findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData/button_Save')

    'Pengecekan jika maritalstatus married'
    if (maritalStatus.equalsIgnoreCase('Married')) {
        monthlyInstallment = WebUI.modifyObjectProperty(monthlyInstallment, 'xpath', 'equals', '/html/body/ngb-modal-window/div/div/form/div[2]/div/div[7]/div/input', 
            true)

        monthlyExpense = WebUI.modifyObjectProperty(monthlyExpense, 'xpath', 'equals', '/html/body/ngb-modal-window/div/div/form/div[2]/div/div[8]/div/input', 
            true)

        datesAsOf = WebUI.modifyObjectProperty(datesAsOf, 'xpath', 'equals', '/html/body/ngb-modal-window/div/div/form/div[2]/div/div[1]/div/input', 
            true)

        buttonCalculate = WebUI.modifyObjectProperty(buttonCalculate, 'xpath', 'equals', '/html/body/ngb-modal-window/div/div/form/div[2]/div/div[11]/div/button', 
            true)

        'Pengecekan spouse monthly income pada excel terisi'
        if (findTestData(GlobalVariable.excelPath).getValue(financialdata, 18).length() >= 1) {
            'Input spouse monthly income'
            WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData/input_SpouseMonthlyIncome'), 
                findTestData(GlobalVariable.excelPath).getValue(financialdata, 18))
        }
        
        'Pengecekan isjoinincome bernilai yes atau no pada excel'
        if (findTestData(GlobalVariable.excelPath).getValue(financialdata, 19).equalsIgnoreCase('Yes')) {
            'Jika is join income belum tercentang'
            if (WebUI.verifyElementNotChecked(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData/input_checkboxIsJoinIncome'), 
                GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                'centang is join income'
                WebUI.check(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData/input_checkboxIsJoinIncome'))
            }
        } else if (findTestData(GlobalVariable.excelPath).getValue(financialdata, 19).equalsIgnoreCase('No')) {
            'jika is join income sudah tercentang'
            if (WebUI.verifyElementChecked(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData/input_checkboxIsJoinIncome'), 
                GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                'Un-centang is join income'
                WebUI.uncheck(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData/input_checkboxIsJoinIncome'))
            }
        }
    }
    
    'input monthly installment'
    WebUI.setText(monthlyInstallment, findTestData(GlobalVariable.excelPath).getValue(financialdata, 15))

    'input monthly expense'
    WebUI.setText(monthlyExpense, findTestData(GlobalVariable.excelPath).getValue(financialdata, 16))

    'input dates as of'
    WebUI.setText(datesAsOf, findTestData(GlobalVariable.excelPath).getValue(financialdata, 17))

    'click button calculate'
    WebUI.click(buttonCalculate)

    'click button save'
    WebUI.click(buttonSave)
}

def inputBank(String copyapp, ArrayList<String> variable, int flagFailed) {
    GlobalVariable.FlagWarning = 0

    if (findTestData(GlobalVariable.excelPath).getValue(financialdata, 24).length() > 1) {
        'click lookup bank'
        WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData/button_Bank Name_btn btn-raised btn-primary'))

        'input bank code'
        WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData/input_Bank Code_BankCode'), 
            findTestData(GlobalVariable.excelPath).getValue(financialdata, 23))

        'click button search bank'
        WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData/button_Search'))

        'verify input error'
        if (WebUI.verifyElementPresent(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData/a_Select'), 
            GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
            'click select'
            WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData/a_Select'))
        } else {
            'click X'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabApplicationData/Button_X'))

            'click cancel'
            WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData/button_Cancel'))
			
			'Write To Excel GlobalVariable.StatusWarning and GlobalVariable.StatusReasonLookup'
			CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('5.FinancialData', financialdata, GlobalVariable.StatusWarning, findTestData(GlobalVariable.excelPath).getValue(GlobalVariable.StartIndex, 2) + ';' +GlobalVariable.StatusReasonLookup)

            (GlobalVariable.FlagWarning)++
        }
        
        'input bank branch name'
        WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData/input_Bank Branch_'), 
            findTestData(GlobalVariable.excelPath).getValue(financialdata, 25))

        'input bank account name'
        WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData/input_Account Name_'), 
            findTestData(GlobalVariable.excelPath).getValue(financialdata, 26))

        'input bank account number'
        WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData/input_Account No_'), 
            findTestData(GlobalVariable.excelPath).getValue(financialdata, 27))

        if (WebUI.verifyElementNotChecked(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData/input_Is Default_ng-untouched ng-pristine ng-valid'), 
            GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
            'checkbox default'
            if (findTestData(GlobalVariable.excelPath).getValue(financialdata, 28) == 'Yes') {
                WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData/input_Is Default_ng-untouched ng-pristine ng-valid'))
            }
        } else if (WebUI.verifyElementChecked(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData/input_Is Default_ng-untouched ng-pristine ng-valid'), 
            GlobalVariable.TimeOut)) {
            'checkbox default'
            if (findTestData(GlobalVariable.excelPath).getValue(financialdata, 28) == 'No') {
                WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData/input_Is Default_ng-untouched ng-pristine ng-valid'))
            }
        }
        
        if (WebUI.verifyElementNotChecked(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData/input_Is Active_ng-untouched ng-pristine ng-valid'), 
            GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
            'checkbox active'
            if (findTestData(GlobalVariable.excelPath).getValue(financialdata, 29) == 'Yes') {
                WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData/input_Is Active_ng-untouched ng-pristine ng-valid'))
            }
        } else if (WebUI.verifyElementChecked(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData/input_Is Active_ng-untouched ng-pristine ng-valid'), 
            GlobalVariable.TimeOut)) {
            'checkbox active'
            if (findTestData(GlobalVariable.excelPath).getValue(financialdata, 29) == 'No') {
                WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData/input_Is Active_ng-untouched ng-pristine ng-valid'))
            }
        }
        
        'input begining balance'
        WebUI.setText(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData/input_Beginning Balance_'), 
            findTestData(GlobalVariable.excelPath).getValue(financialdata, 30))

        'Month array'
        montharray = findTestData(GlobalVariable.excelPath).getValue(financialdata, 31).split(';', -1)

        'Year array'
        yeararray = findTestData(GlobalVariable.excelPath).getValue(financialdata, 32).split(';', -1)

        'Debit Transaction count array'
        debitTransactionarray = findTestData(GlobalVariable.excelPath).getValue(financialdata, 33).split(';', -1)

        'debit array'
        debitarray = findTestData(GlobalVariable.excelPath).getValue(financialdata, 34).split(';', -1)

        'credit transaction count array'
        creditTransactionArray = findTestData(GlobalVariable.excelPath).getValue(financialdata, 35).split(';', -1)

        'credit array'
        creditarray = findTestData(GlobalVariable.excelPath).getValue(financialdata, 36).split(';', -1)

        if (copyapp.equalsIgnoreCase('Edit')) {
            variablebankstatement = DriverFactory.getWebDriver().findElements(By.xpath('//*[@id="CustBankAccDetailSection"]/div[3]/div[1]/table/tbody[2]/tr'))

            if (variablebankstatement.size() > 0) {
                for (BSindex = 1; BSindex <= variablebankstatement.size(); BSindex++) {
                    'modify object from input credit'
                    modifyNewinputCredit = WebUI.modifyObjectProperty(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData/input_credit'), 
                        'xpath', 'equals', ('//*[@id="CustBankAccDetailSection"]/div[3]/div[1]/table/tbody[2]/tr[' + BSindex) + 
                        ']/td[6]/input', true)

                    'modify object from input credit transaction count'
                    modifyNewinputCreditTransactioncount = WebUI.modifyObjectProperty(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData/input_credittransactioncount'), 
                        'xpath', 'equals', ('//*[@id="CustBankAccDetailSection"]/div[3]/div[1]/table/tbody[2]/tr[' + BSindex) + 
                        ']/td[5]/input', true)

                    'modify object from input debit'
                    modifyNewinputDebit = WebUI.modifyObjectProperty(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData/input_debit'), 
                        'xpath', 'equals', ('//*[@id="CustBankAccDetailSection"]/div[3]/div[1]/table/tbody[2]/tr[' + BSindex) + 
                        ']/td[4]/input', true)

                    'modify object from input debit transaction count'
                    modifyNewinputDebitTransactioncount = WebUI.modifyObjectProperty(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData/input_debittransactionaccount'), 
                        'xpath', 'equals', ('//*[@id="CustBankAccDetailSection"]/div[3]/div[1]/table/tbody[2]/tr[' + BSindex) + 
                        ']/td[3]/input', true)

                    'modify object from input year'
                    modifyNewinputYear = WebUI.modifyObjectProperty(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData/input_Year'), 
                        'xpath', 'equals', ('//*[@id="CustBankAccDetailSection"]/div[3]/div[1]/table/tbody[2]/tr[' + BSindex) + 
                        ']/td[2]/input', true)

                    'modify object from select month'
                    modifyNewselectMonth = WebUI.modifyObjectProperty(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData/select_Month'), 
                        'xpath', 'equals', ('//*[@id="CustBankAccDetailSection"]/div[3]/div[1]/table/tbody[2]/tr[' + BSindex) + 
                        ']/td[1]/select', true)

                    'modify object delete button'
                    modifyNewbuttondelete = WebUI.modifyObjectProperty(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData/select_Month'), 
                        'xpath', 'equals', ('//*[@id="CustBankAccDetailSection"]/div[3]/div[1]/table/tbody[2]/tr[' + BSindex) + 
                        ']/td[8]/a/i', true)

                    Select selectmonth = new Select(DriverFactory.getWebDriver().findElement(By.xpath(('//*[@id="CustBankAccDetailSection"]/div[3]/div[1]/table/tbody[2]/tr[' + 
                            BSindex) + ']/td[1]/select')))

                    month = selectmonth.getFirstSelectedOption().getText()

                    year = WebUI.getAttribute(modifyNewinputYear, 'value')

                    if ((((((findTestData(GlobalVariable.excelPath).getValue(financialdata, 31).length() > 0) || (findTestData(GlobalVariable.excelPath).getValue(
                        financialdata, 32).length() > 0)) || (findTestData(GlobalVariable.excelPath).getValue(financialdata, 33).length() > 
                    0)) || (findTestData(GlobalVariable.excelPath).getValue(financialdata, 34).length() > 0)) || (findTestData(GlobalVariable.excelPath).getValue(
                        financialdata, 35).length() > 0)) || (findTestData(GlobalVariable.excelPath).getValue(financialdata, 36).length() > 
                    0)) {
                        for (int excelindex = 0; excelindex < montharray.size(); excelindex++) {
                            if ((montharray[excelindex]).equalsIgnoreCase(month) && (yeararray[excelindex]).equalsIgnoreCase(
                                year)) {
								'call function input bank statement'
                                inputBankStatement(excelindex + 1)

                                break
                            } else {
                                if (excelindex == (montharray.size() - 1)) {
                                    if (WebUI.verifyElementPresent(modifyNewbuttondelete, GlobalVariable.TimeOut)) {
                                        'click delete button'
                                        WebUI.click(modifyNewbuttondelete)

                                        'acc alert'
                                        WebUI.acceptAlert()

                                        variablebankstatement = DriverFactory.getWebDriver().findElements(By.xpath('//*[@id="CustBankAccDetailSection"]/div[3]/div[1]/table/tbody[2]/tr'))

                                        BSindex--
                                    } else {
                                        break
                                    }
                                }
                            }
                        }
                    }
                }
            }
            
            for (int excelindex = 0; excelindex < montharray.size(); excelindex++) {
                'add data yang ada di excel dan belom ada di confins'
                variablebankstatement = DriverFactory.getWebDriver().findElements(By.xpath('//*[@id="CustBankAccDetailSection"]/div[3]/div[1]/table/tbody[2]/tr'))

                flagFailed = 0

                if (variablebankstatement.size() > 0) {
                    for (BSindex = 1; BSindex <= variablebankstatement.size(); BSindex++) {
                        'modify object from input credit'
                        modifyNewinputCredit = WebUI.modifyObjectProperty(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData/input_credit'), 
                            'xpath', 'equals', ('//*[@id="CustBankAccDetailSection"]/div[3]/div[1]/table/tbody[2]/tr[' + 
                            BSindex) + ']/td[6]/input', true)

                        'modify object from input credit transaction count'
                        modifyNewinputCreditTransactioncount = WebUI.modifyObjectProperty(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData/input_credittransactioncount'), 
                            'xpath', 'equals', ('//*[@id="CustBankAccDetailSection"]/div[3]/div[1]/table/tbody[2]/tr[' + 
                            BSindex) + ']/td[5]/input', true)

                        'modify object from input debit'
                        modifyNewinputDebit = WebUI.modifyObjectProperty(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData/input_debit'), 
                            'xpath', 'equals', ('//*[@id="CustBankAccDetailSection"]/div[3]/div[1]/table/tbody[2]/tr[' + 
                            BSindex) + ']/td[4]/input', true)

                        'modify object from input debit transaction count'
                        modifyNewinputDebitTransactioncount = WebUI.modifyObjectProperty(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData/input_debittransactionaccount'), 
                            'xpath', 'equals', ('//*[@id="CustBankAccDetailSection"]/div[3]/div[1]/table/tbody[2]/tr[' + 
                            BSindex) + ']/td[3]/input', true)

                        'modify object from input year'
                        modifyNewinputYear = WebUI.modifyObjectProperty(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData/input_Year'), 
                            'xpath', 'equals', ('//*[@id="CustBankAccDetailSection"]/div[3]/div[1]/table/tbody[2]/tr[' + 
                            BSindex) + ']/td[2]/input', true)

                        'modify object from select month'
                        modifyNewselectMonth = WebUI.modifyObjectProperty(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData/select_Month'), 
                            'xpath', 'equals', ('//*[@id="CustBankAccDetailSection"]/div[3]/div[1]/table/tbody[2]/tr[' + 
                            BSindex) + ']/td[1]/select', true)

                        'modify object delete button'
                        modifyNewbuttondelete = WebUI.modifyObjectProperty(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData/select_Month'), 
                            'xpath', 'equals', ('//*[@id="CustBankAccDetailSection"]/div[3]/div[1]/table/tbody[2]/tr[' + 
                            BSindex) + ']/td[8]/a/i', true)

                        Select selectmonth = new Select(DriverFactory.getWebDriver().findElement(By.xpath(('//*[@id="CustBankAccDetailSection"]/div[3]/div[1]/table/tbody[2]/tr[' + 
                                BSindex) + ']/td[1]/select')))

                        month = selectmonth.getFirstSelectedOption().getText()

                        year = WebUI.getAttribute(modifyNewinputYear, 'value')

                        if ((((((findTestData(GlobalVariable.excelPath).getValue(financialdata, 31).length() > 0) || (findTestData(GlobalVariable.excelPath).getValue(
                            financialdata, 32).length() > 0)) || (findTestData(GlobalVariable.excelPath).getValue(financialdata, 33).length() > 
                        0)) || (findTestData(GlobalVariable.excelPath).getValue(financialdata, 34).length() > 0)) || (findTestData(GlobalVariable.excelPath).getValue(
                            financialdata, 35).length() > 0)) || (findTestData(GlobalVariable.excelPath).getValue(financialdata, 36).length() > 
                        0)) {
                            'verify month year beda'
                            if (!((montharray[excelindex]).equalsIgnoreCase(month) && (yeararray[excelindex]).equalsIgnoreCase(
                                year)) || WebUI.verifyElementNotPresent(modifyNewbuttondelete, GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                                if (BSindex == variablebankstatement.size()) {
                                    'click add new button'
                                    WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData/button_banknewrow'))

                                    BSindex++

                                    'modify object from input credit'
                                    modifyNewinputCredit = WebUI.modifyObjectProperty(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData/input_credit'), 
                                        'xpath', 'equals', ('//*[@id="CustBankAccDetailSection"]/div[3]/div[1]/table/tbody[2]/tr[' + 
                                        BSindex) + ']/td[6]/input', true)

                                    'modify object from input credit transaction count'
                                    modifyNewinputCreditTransactioncount = WebUI.modifyObjectProperty(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData/input_credittransactioncount'), 
                                        'xpath', 'equals', ('//*[@id="CustBankAccDetailSection"]/div[3]/div[1]/table/tbody[2]/tr[' + 
                                        BSindex) + ']/td[5]/input', true)

                                    'modify object from input debit'
                                    modifyNewinputDebit = WebUI.modifyObjectProperty(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData/input_debit'), 
                                        'xpath', 'equals', ('//*[@id="CustBankAccDetailSection"]/div[3]/div[1]/table/tbody[2]/tr[' + 
                                        BSindex) + ']/td[4]/input', true)

                                    'modify object from input debit transaction count'
                                    modifyNewinputDebitTransactioncount = WebUI.modifyObjectProperty(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData/input_debittransactionaccount'), 
                                        'xpath', 'equals', ('//*[@id="CustBankAccDetailSection"]/div[3]/div[1]/table/tbody[2]/tr[' + 
                                        BSindex) + ']/td[3]/input', true)

                                    'modify object from input year'
                                    modifyNewinputYear = WebUI.modifyObjectProperty(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData/input_Year'), 
                                        'xpath', 'equals', ('//*[@id="CustBankAccDetailSection"]/div[3]/div[1]/table/tbody[2]/tr[' + 
                                        BSindex) + ']/td[2]/input', true)

                                    'modify object from select month'
                                    modifyNewselectMonth = WebUI.modifyObjectProperty(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData/select_Month'), 
                                        'xpath', 'equals', ('//*[@id="CustBankAccDetailSection"]/div[3]/div[1]/table/tbody[2]/tr[' + 
                                        BSindex) + ']/td[1]/select', true)

                                    'modify object delete button'
                                    modifyNewbuttondelete = WebUI.modifyObjectProperty(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData/select_Month'), 
                                        'xpath', 'equals', ('//*[@id="CustBankAccDetailSection"]/div[3]/div[1]/table/tbody[2]/tr[' + 
                                        BSindex) + ']/td[8]/a/i', true)

									'call function input bank statement'
                                    inputBankStatement(excelindex + 1)
                                }
                            } else if ((montharray[excelindex]).equalsIgnoreCase(month) && (yeararray[excelindex]).equalsIgnoreCase(
                                year)) {
                                break
                            }
                        }
                    }
                } else {
					'call function input bank statement from empty'
                    inputBankStatementFromEmpty()

                    break
                }
            }
        } else if (copyapp.equalsIgnoreCase('No')) {
			'call function input bank statement from empty'
            inputBankStatementFromEmpty()
        }
        
        'click button calculate bank'
        WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData/button_CalculateBank'))

        'click button save'
        WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData/button_SaveBank'))

        Integer iscompleteMandatory = Integer.parseInt(findTestData(GlobalVariable.excelPath).getValue(financialdata, 4))

        if ((iscompleteMandatory == 0) && (GlobalVariable.FlagFailed == 0)) {
            'cek alert'
            GlobalVariable.FlagFailed = CustomKeywords.'checkSaveProcess.checkSaveProcess.checkAlert'(financialdata, '5.FinancialData')
        }
        
        if (GlobalVariable.FlagFailed == 0) {
            'Check save Process write to excel'
            CustomKeywords.'checkSaveProcess.checkSaveProcess.checkStatus'(iscompleteMandatory, findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData/button_SaveFinancial'), 
                financialdata, '5.FinancialData')

            if (iscompleteMandatory == 0) {
                errorValObject = findTestObject('NAP-CF4W-CustomerPersonal/NAP1-CustomerData/TabCustomerData/div_errorvalidation')

                'cek validasi'
                CustomKeywords.'checkSaveProcess.checkSaveProcess.checkValidasi'(errorValObject, financialdata, '5.FinancialData')
            }
        }
        
        if (WebUI.verifyElementNotPresent(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData/button_SaveFinancial'), 
            GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
            'click button Cancel'
            WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData/button_Cancel'))

            (GlobalVariable.FlagWarning)++
        }
        
        if (GlobalVariable.FlagWarning > 0) {
			'write to excel status warning'
            CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '5.FinancialData', 0, 
                financialdata - 1, GlobalVariable.StatusWarning)
        }
        
        WebUI.delay(5)
    }
}

def inputBankStatement(int index) {
    'select month'
    WebUI.selectOptionByLabel(modifyNewselectMonth, montharray[(index - 1)], false, FailureHandling.OPTIONAL)

    'input year'
    WebUI.setText(modifyNewinputYear, yeararray[(index - 1)])

    'input debit transaction'
    WebUI.setText(modifyNewinputDebitTransactioncount, debitTransactionarray[(index - 1)])

    'input debit balance'
    WebUI.setText(modifyNewinputDebit, debitarray[(index - 1)])

    'input credit transaction'
    WebUI.setText(modifyNewinputCreditTransactioncount, creditTransactionArray[(index - 1)])

    'input credit balance'
    WebUI.setText(modifyNewinputCredit, creditarray[(index - 1)])
}

def inputBankStatementFromEmpty() {
    'Month array'
    montharray = findTestData(GlobalVariable.excelPath).getValue(financialdata, 31).split(';', -1)

    'Year array'
    yeararray = findTestData(GlobalVariable.excelPath).getValue(financialdata, 32).split(';', -1)

    'Debit Transaction count array'
    debitTransactionarray = findTestData(GlobalVariable.excelPath).getValue(financialdata, 33).split(';', -1)

    'debit array'
    debitarray = findTestData(GlobalVariable.excelPath).getValue(financialdata, 34).split(';', -1)

    'credit transaction count array'
    creditTransactionArray = findTestData(GlobalVariable.excelPath).getValue(financialdata, 35).split(';', -1)

    'credit array'
    creditarray = findTestData(GlobalVariable.excelPath).getValue(financialdata, 36).split(';', -1)

    if ((((((findTestData(GlobalVariable.excelPath).getValue(financialdata, 31).length() > 0) || (findTestData(GlobalVariable.excelPath).getValue(
        financialdata, 32).length() > 0)) || (findTestData(GlobalVariable.excelPath).getValue(financialdata, 33).length() > 0)) || 
    (findTestData(GlobalVariable.excelPath).getValue(financialdata, 34).length() > 0)) || (findTestData(GlobalVariable.excelPath).getValue(financialdata, 
        35).length() > 0)) || (findTestData(GlobalVariable.excelPath).getValue(financialdata, 36).length() > 0)) {
        for (BSindex = 1; BSindex <= montharray.size(); BSindex++) {
            'modify object from input credit'
            modifyNewinputCredit = WebUI.modifyObjectProperty(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData/input_credit'), 
                'xpath', 'equals', ('//*[@id="CustBankAccDetailSection"]/div[3]/div[1]/table/tbody[2]/tr[' + BSindex) + 
                ']/td[6]/input', true)

            'modify object from input credit transaction count'
            modifyNewinputCreditTransactioncount = WebUI.modifyObjectProperty(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData/input_credittransactioncount'), 
                'xpath', 'equals', ('//*[@id="CustBankAccDetailSection"]/div[3]/div[1]/table/tbody[2]/tr[' + BSindex) + 
                ']/td[5]/input', true)

            'modify object from input debit'
            modifyNewinputDebit = WebUI.modifyObjectProperty(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData/input_debit'), 
                'xpath', 'equals', ('//*[@id="CustBankAccDetailSection"]/div[3]/div[1]/table/tbody[2]/tr[' + BSindex) + 
                ']/td[4]/input', true)

            'modify object from input debit transaction count'
            modifyNewinputDebitTransactioncount = WebUI.modifyObjectProperty(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData/input_debittransactionaccount'), 
                'xpath', 'equals', ('//*[@id="CustBankAccDetailSection"]/div[3]/div[1]/table/tbody[2]/tr[' + BSindex) + 
                ']/td[3]/input', true)

            'modify object from input year'
            modifyNewinputYear = WebUI.modifyObjectProperty(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData/input_Year'), 
                'xpath', 'equals', ('//*[@id="CustBankAccDetailSection"]/div[3]/div[1]/table/tbody[2]/tr[' + BSindex) + 
                ']/td[2]/input', true)

            'modify object from select month'
            modifyNewselectMonth = WebUI.modifyObjectProperty(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData/select_Month'), 
                'xpath', 'equals', ('//*[@id="CustBankAccDetailSection"]/div[3]/div[1]/table/tbody[2]/tr[' + BSindex) + 
                ']/td[1]/select', true)

            'click add new button'
            WebUI.click(findTestObject('NAP/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData/button_banknewrow'))

			'call function input bank statement'
            inputBankStatement(BSindex)
        }
    }
}

def getDataFile(){
	if(GlobalVariable.APPSTEP == 'CUSTOMER'){
		'get data file path'
		GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'("\\Excel\\"+ GlobalVariable.LOB +"\\2.1.1 CustomerDataCompletion-Personal - Customer.xlsm")
	
		'declare findTestData(excelPathCustDetail)'
		excelPathCustDetail = 'NAP-'+ GlobalVariable.LOB +'-CustomerPersonal/NAP4-CustomerDataCompletion/CustomerPersonal/CustomerDetail'
		
		'declare data file Global variable'
		GlobalVariable.excelPath = 'NAP-'+ GlobalVariable.LOB +'-CustomerPersonal/NAP4-CustomerDataCompletion/CustomerPersonal/FinancialData'
	}else if(GlobalVariable.APPSTEP == 'FAMILY'){
		'get data file path'
		GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'("\\Excel\\"+ GlobalVariable.LOB +"\\2.1.2 CustomerDataCompletion-Personal - Family.xlsm")
		
		'declare findTestData(excelPathCustDetail)'
		excelPathCustDetail = 'NAP-'+ GlobalVariable.LOB +'-CustomerPersonal/NAP4-CustomerDataCompletion/FamilyPersonal/CustomerDetail'
		
		'declare data file Global variable'
		GlobalVariable.excelPath = 'NAP-'+ GlobalVariable.LOB +'-CustomerPersonal/NAP4-CustomerDataCompletion/FamilyPersonal/FinancialData'
	}else if(GlobalVariable.APPSTEP == 'GUARANTOR PERSONAL'){
		'get data file path'
		GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'("\\Excel\\"+ GlobalVariable.LOB +"\\2.1.3 CustomerDataCompletion-Personal - GuarantorPersonal.xlsm")
		
		'declare findTestData(excelPathCustDetail)'
		excelPathCustDetail = 'NAP-'+ GlobalVariable.LOB +'-CustomerPersonal/NAP4-CustomerDataCompletion/GuarantorPersonal/CustomerDetail'
		
		GlobalVariable.excelPath = 'NAP-'+ GlobalVariable.LOB +'-CustomerPersonal/NAP4-CustomerDataCompletion/GuarantorPersonal/FinancialData'
		
	}
}