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
import groovy.sql.Sql as Sql
import org.openqa.selenium.WebElement as WebElement
import org.openqa.selenium.WebDriver as WebDriver
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.By as By
import org.openqa.selenium.support.ui.Select as Select

'get data file path'
GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'(GlobalVariable.PathCompany)

'Klik tab referantor'
WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/buttonTabReferantor'))

'get applaststep dari confins'
String appLastStep = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/label_AppLastStep'))

if ((!(appLastStep.equalsIgnoreCase('GUARANTOR')) && !(appLastStep.equalsIgnoreCase('NAP DETAIL'))) && (GlobalVariable.FirstTimeEntry == 
'Yes')) {
    GlobalVariable.FirstTimeEntry = 'No'
}

'declare datafileCustomerCompany'
datafileCustomerCompany = findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData')

if (GlobalVariable.RoleCompany == 'Testing') {
    'verify application step'
    checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/ApplicationCurrentStep')), 
            'REFERANTOR', false, FailureHandling.OPTIONAL))
}

'declare datafileReferantor'
datafileReferantor = findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData')

countReferantor = datafileReferantor.getColumnNumbers()

Sql sqlConnectionFOU = CustomKeywords.'dbConnection.connectDB.connectFOU'()

'call function check ddl'
checkDDL(sqlConnectionFOU)

WebDriver driver = DriverFactory.getWebDriver()

ArrayList<WebElement> variable

'Ambil text original office dari confins'
String officeName = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/label_OriginalOffice'))

//pengecekan pada excel data referantor ada lebih dari atau sama dengan 1
'Looping untuk mencari nilai colm yang menunjukkan colm appno'
for (GlobalVariable.NumofReferantor = GlobalVariable.StartIndex; GlobalVariable.NumofReferantor <= (countReferantor - 1); (GlobalVariable.NumofReferantor)++) {
    if (datafileReferantor.getValue(GlobalVariable.NumofReferantor, 12) == datafileCustomerCompany.getValue(GlobalVariable.NumofColm, 
        13)) {
        if (WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/CheckboxReferantor'), 
            'aria-checked') == 'false') {
            'click referantor checkbox'
            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/input_CheckboxReferantor'))
        }
        
        variable = driver.findElements(By.cssSelector('#accessoriesData > div.table-responsive > table > tbody > tr'))

        break
    } else if (datafileReferantor.getValue(GlobalVariable.NumofReferantor + 1, 12) != datafileCustomerCompany.getValue(GlobalVariable.NumofColm, 
        13)) {
        if (WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/CheckboxReferantor'), 
            'aria-checked') == 'true') {
            'click referantor checkbox'
            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/input_CheckboxReferantor'))
        }
        
        variable = []
    }
}

//Jika copy app edit
if (datafileReferantor.getValue(GlobalVariable.StartIndex, 10).equalsIgnoreCase('Edit')) {
    'arraylist referantor name yang gagal'
    ArrayList<WebElement> referantorfaileddelete = new ArrayList<WebElement>()

    //Edit jika ada data pada confins dan datanya sesuai dengan excel
    'Pengecekan ada data referantor pada confins'
    if (WebUI.verifyNotMatch(WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/TableReferantornodata'), 
            FailureHandling.OPTIONAL), 'NO DATA AVAILABLE', false, FailureHandling.OPTIONAL)) {
        'Looping confins referantor'
        for (int i = 1; i <= variable.size(); i++) {
            'modify input referantor name'
            modifyObjectReferantorName = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/input_referantorName'), 
                'xpath', 'equals', ('//*[@id=\'accessoriesData\']/div[2]/table/tbody/tr[' + i) + ']/td[3]/lib-uclookupgeneric/div/div/input', 
                true)

            'modify button referantor lookup'
            modifyObjectButtonReferantor = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/button_LookupReferantor'), 
                'xpath', 'equals', ('//*[@id="accessoriesData"]/div[2]/table/tbody/tr[' + i) + ']/td[3]/lib-uclookupgeneric/div/div/div[1]/span/button', 
                true)

            'modify select referantor category'
            modifyObjectSelectReferantorCategory = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/select_ReferantorCategory'), 
                'xpath', 'equals', ('//*[@id="accessoriesData"]/div[2]/table/tbody/tr[' + i) + ']/td[2]/select', true)

            'modify button delete'
            modifyButtonDelete = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/Button Delete'), 
                'xpath', 'equals', ('//*[@id="accessoriesData"]/div[2]/table/tbody/tr[' + i) + ']/td[9]/a/i', true)

            'modify select tax calculation'
            modifySelectTaxCalcualtion = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/select_TaxCalculationMethod'), 
                'xpath', 'equals', ('//*[@id="accessoriesData"]/div[2]/table/tbody/tr[' + i) + ']/td[6]/select', true)

            'modify select bank account'
            modifySelectBankAccount = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/select_BankAccount'), 
                'xpath', 'equals', ('//*[@id="accessoriesData"]/div[2]/table/tbody/tr[' + i) + ']/td[5]/select', true)
			
			modifycheckboxVAT = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabReferantorData/input_VAT'),'xpath','equals',"//*[@id='accessoriesData']/div[2]/table/tbody/tr["+i+"]/td[7]/input",true)
			 
			modifyReferantorType = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabReferantorData/td_ReferantorType'),'xpath','equals',"//*[@id='accessoriesData']/div[2]/table/tbody/tr["+i+"]/td[4]",true)
			
            'check if no data available'
            if (WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/TableReferantornodata'), 
                    FailureHandling.OPTIONAL), 'NO DATA AVAILABLE', false, FailureHandling.OPTIONAL)) {
                break
            }
            
            Select selectedRefCategory = new Select(DriverFactory.getWebDriver().findElement(By.xpath(('//*[@id="accessoriesData"]/div[2]/table/tbody/tr[' + 
                    i) + ']/td[2]/select')))

			Select selectedTaxCalculation = new Select(DriverFactory.getWebDriver().findElement(By.xpath(('//*[@id="accessoriesData"]/div[2]/table/tbody/tr[' + i) + ']/td[6]/select')))
			
            'Ambil text referantor category yang dipilih pada confins'
            String textRefCategory = selectedRefCategory.getFirstSelectedOption().getText()

			'Ambil text referantor tax calculation yang dipilih pada confins'
			String textTaxCalculation = selectedTaxCalculation.getFirstSelectedOption().getText()
			
            'Looping excel referantor'
            for (GlobalVariable.NumofReferantor = GlobalVariable.StartIndex; GlobalVariable.NumofReferantor <= (countReferantor - 
            1); (GlobalVariable.NumofReferantor)++) {
                if (datafileReferantor.getValue(GlobalVariable.NumofReferantor, 12) == datafileCustomerCompany.getValue(
                    GlobalVariable.NumofColm, 13)) {
				
					if (textRefCategory.equalsIgnoreCase(datafileReferantor.getValue(GlobalVariable.NumofReferantor, 13)) &&
						WebUI.getAttribute(modifyObjectReferantorName, 'value').equalsIgnoreCase(datafileReferantor.getValue(
						GlobalVariable.NumofReferantor, 14)) && textTaxCalculation.equalsIgnoreCase(datafileReferantor.getValue(
						GlobalVariable.NumofReferantor, 17))) {
						'call function gettext bankaccount'
						getTextBankAccount(('//*[@id="accessoriesData"]/div[2]/table/tbody/tr[' + i) + ']/td[5]/select')
						
						'write to excel SUCCESS'
						CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '5.TabReferantorData',
							0, GlobalVariable.NumofReferantor - 1, GlobalVariable.StatusSuccess)
						
						break
					}
				
                    'Pengecekan jika referantor category dan referantor name pada confins sesuai dengan excel datafile'
                    if (textRefCategory.equalsIgnoreCase(datafileReferantor.getValue(GlobalVariable.NumofReferantor, 13)) && 
                    WebUI.getAttribute(modifyObjectReferantorName, 'value').equalsIgnoreCase(datafileReferantor.getValue(
                            GlobalVariable.NumofReferantor, 14))) {
                        'select bank account'
                        WebUI.selectOptionByIndex(modifySelectBankAccount, datafileReferantor.getValue(GlobalVariable.NumofReferantor, 
                                16), FailureHandling.OPTIONAL)

                        'call function gettext bankaccount'
                        getTextBankAccount(('//*[@id="accessoriesData"]/div[2]/table/tbody/tr[' + i) + ']/td[5]/select')

                        'select tax calculation'
                        WebUI.selectOptionByLabel(modifySelectTaxCalcualtion, datafileReferantor.getValue(GlobalVariable.NumofReferantor, 
                                17), false, FailureHandling.OPTIONAL)
						
						referantorType = WebUI.getText(modifyReferantorType)
						
						if(referantorType.equalsIgnoreCase("Customer Company")){
							if(datafileReferantor.getValue(GlobalVariable.NumofReferantor, 18).equalsIgnoreCase("Yes") && WebUI.verifyElementNotChecked(modifycheckboxVAT,GlobalVariable.TimeOut, FailureHandling.OPTIONAL)){
							   WebUI.check(modifycheckboxVAT)
							}
							else if(datafileReferantor.getValue(GlobalVariable.NumofReferantor, 18).equalsIgnoreCase("No") && WebUI.verifyElementChecked(modifycheckboxVAT,GlobalVariable.TimeOut, FailureHandling.OPTIONAL)){
								WebUI.uncheck(modifycheckboxVAT)
							}
						}

                        'cek inputan mandatory apakah sudah terisi semua atau belum'
                        if ((WebUI.verifyOptionSelectedByIndex(modifyObjectSelectReferantorCategory, 0, GlobalVariable.TimeOut, 
                            FailureHandling.OPTIONAL) || WebUI.verifyOptionSelectedByIndex(modifySelectBankAccount, 0, GlobalVariable.TimeOut, 
                            FailureHandling.OPTIONAL)) || WebUI.verifyOptionSelectedByIndex(modifySelectTaxCalcualtion, 
                            0, GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                            'Write To Excel GlobalVariable.StatusWarning and GlobalVariable.StatusReasonMandatoryEmpty'
                            CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('5.TabReferantorData', 
                                GlobalVariable.NumofReferantor, GlobalVariable.StatusWarning, GlobalVariable.StatusReasonMandatoryEmpty)

                            'get referantor name'
                            referantornamebefore = WebUI.getAttribute(modifyObjectReferantorName, 'value', FailureHandling.OPTIONAL)

                            'Click delete'
                            WebUI.click(modifyButtonDelete, FailureHandling.OPTIONAL)

                            if (i == variable.size()) {
                                if (WebUI.verifyElementPresent(modifyObjectReferantorName, GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                                    'add cust name failed kedalam array'
                                    referantorfaileddelete.add(referantornamebefore)
                                }
                            } else {
                                'get cust name sebelum delete'
                                referantornameafter = WebUI.getAttribute(modifyObjectReferantorName, 'value', FailureHandling.OPTIONAL)

                                if (WebUI.verifyMatch(referantornameafter, referantornamebefore, false, FailureHandling.OPTIONAL)) {
                                    'add cust name failed kedalam array'
                                    referantorfaileddelete.add(referantornamebefore)
                                }
                                
                                variable = driver.findElements(By.cssSelector('#accessoriesData > div.table-responsive > table > tbody > tr'))
                            }
                            
                            i--
							continue
                        }
							'write to excel SUCCESS'
							CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '5.TabReferantorData',
								0, GlobalVariable.NumofReferantor - 1, GlobalVariable.StatusSuccess)
                        
                        break
                        //delete jika ada data pada confins, tetapi pada datafile tidak ada
                    } else {
                       	continue
                    }
                } else {
					'Jika pada confins ada datanya'
					if (WebUI.verifyNotMatch(WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/TableReferantornodata'),
							FailureHandling.OPTIONAL), 'NO DATA AVAILABLE', false, FailureHandling.OPTIONAL)) {
						'get referantor name'
						referantornamebefore = WebUI.getAttribute(modifyObjectReferantorName, 'value', FailureHandling.OPTIONAL)
	
						'Click delete'
						WebUI.click(modifyButtonDelete, FailureHandling.OPTIONAL)
	
						if (i == variable.size()) {
							if (WebUI.verifyElementPresent(modifyObjectReferantorName, GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
								'add cust name failed kedalam array'
								referantorfaileddelete.add(referantornamebefore)
							}
						} else {
							'get cust name sebelum delete'
							referantornameafter = WebUI.getAttribute(modifyObjectReferantorName, 'value', FailureHandling.OPTIONAL)
	
							if (WebUI.verifyMatch(referantornameafter, referantornamebefore, false, FailureHandling.OPTIONAL)) {
								'add cust name failed kedalam array'
								referantorfaileddelete.add(referantornamebefore)
							}
						}
						
						'count ulang table referantor'
						variable = driver.findElements(By.cssSelector('#accessoriesData > div.table-responsive > table > tbody > tr'))
	
						i--
	
						if ((i == variable.size()) && (datafileReferantor.getValue(GlobalVariable.NumofReferantor +
							1, 12) != datafileCustomerCompany.getValue(GlobalVariable.NumofColm, 13))) {
							break
						}
					} else {
						break
					}
                    break
                }
            }
        }
    }
    
    if (referantorfaileddelete.size() > 0) {
        'Write To Excel GlobalVariable.StatusWarning and GlobalVariable.ReasonFailedDelete'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('5.TabReferantorData', GlobalVariable.NumofReferantor, 
            GlobalVariable.StatusWarning, findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData').getValue(GlobalVariable.NumofColm, 2) + ';' + GlobalVariable.ReasonFailedDelete + referantorfaileddelete)

        (GlobalVariable.FlagWarning)++
    }
    
    //Add data jika pada confins tidak ada datanya (yang mau diadd), tetapi pada excel ada
    'Looping excel referantor'
    for (GlobalVariable.NumofReferantor = GlobalVariable.StartIndex; GlobalVariable.NumofReferantor <= (countReferantor - 
    1); (GlobalVariable.NumofReferantor)++) {

		ArrayList<WebElement> variableData = driver.findElements(By.cssSelector('#accessoriesData > div.table-responsive > table > tbody > tr'))

		int countData = variableData.size()
		
		int add = 0
		
        if (datafileReferantor.getValue(GlobalVariable.NumofReferantor, 12) == datafileCustomerCompany.getValue(GlobalVariable.NumofColm, 
            13)) {
            'Looping confins referantor'
            for (int j = 1; j <= countData; j++) {
                'modify input referantor name'
                modifyObjectReferantorName = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/input_referantorName'), 
                    'xpath', 'equals', ('//*[@id=\'accessoriesData\']/div[2]/table/tbody/tr[' + j) + ']/td[3]/lib-uclookupgeneric/div/div/input', 
                    true)

                'modify button referantor lookup'
                modifyObjectButtonReferantor = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/button_LookupReferantor'), 
                    'xpath', 'equals', ('//*[@id="accessoriesData"]/div[2]/table/tbody/tr[' + j) + ']/td[3]/lib-uclookupgeneric/div/div/div[1]/span/button', 
                    true)

                'modify select referantor category'
                modifyObjectSelectReferantorCategory = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/select_ReferantorCategory'), 
                    'xpath', 'equals', ('//*[@id="accessoriesData"]/div[2]/table/tbody/tr[' + j) + ']/td[2]/select', true)

                'modify button delete'
                modifyButtonDelete = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/Button Delete'), 
                    'xpath', 'equals', ('//*[@id="accessoriesData"]/div[2]/table/tbody/tr[' + j) + ']/td[9]/a/i', true)

                'modify select tax calculation'
                modifySelectTaxCalcualtion = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/select_TaxCalculationMethod'), 
                    'xpath', 'equals', ('//*[@id="accessoriesData"]/div[2]/table/tbody/tr[' + j) + ']/td[6]/select', true)

                'modify select bank account'
                modifySelectBankAccount = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/select_BankAccount'), 
                    'xpath', 'equals', ('//*[@id="accessoriesData"]/div[2]/table/tbody/tr[' + j) + ']/td[5]/select', true)
				
				modifycheckboxVAT = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabReferantorData/input_VAT'),'xpath','equals',"//*[@id='accessoriesData']/div[2]/table/tbody/tr["+j+"]/td[7]/input",true)
				 
				modifyReferantorType = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabReferantorData/td_ReferantorType'),'xpath','equals',"//*[@id='accessoriesData']/div[2]/table/tbody/tr["+j+"]/td[4]",true)
				
                String refCategory

                String referantorCode

                'Jika pada confins ada data referantor sebelumnya'
                if (WebUI.verifyNotMatch(WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/TableReferantornodata'), 
                        FailureHandling.OPTIONAL), 'NO DATA AVAILABLE', false, FailureHandling.OPTIONAL)) {
                    if (add == 1) {
                        'select referantor category'
                        WebUI.selectOptionByLabel(modifyObjectSelectReferantorCategory, datafileReferantor.getValue(GlobalVariable.NumofReferantor, 
                                13), false, FailureHandling.OPTIONAL)

                        'Ambil dan simpan nilai referantor category dari confins'
                        refCategory = WebUI.getAttribute(modifyObjectSelectReferantorCategory, 'value')

                        'click button referantor lookup'
                        WebUI.click(modifyObjectButtonReferantor, FailureHandling.OPTIONAL)

                        if (GlobalVariable.RoleCompany == 'Testing') {
                            if (refCategory != '') {
                                'click button search'
                                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/Button_SearchReferantor'))

                                'Cek total data referantor pada db'
                                Integer countReferantor = CustomKeywords.'referantorData.checkReferantor.countReferantorLookup'(
                                    sqlConnectionFOU, refCategory, officeName)

                                'Ambil nilai total data referantor pada lookup confins'
                                String[] textTotalDataReferantor = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/label_TotalDataOfficer')).replace(
                                    ' ', '').replace(':', ';').split(';')

                                'Parsing nilai total data referantor confins ke integer(angka)'
                                Integer totalDataReferantor = Integer.parseInt(textTotalDataReferantor[1])

                                'Verif total data referantor confins sesuai dengan db'
                                if (WebUI.verifyEqual(totalDataReferantor, countReferantor) == false) {
                                    writeReasonFailedLookup()
                                }
                            }
                        }
                        
                        'input referantor name'
                        WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/input_Referantor Name_referantorName'), 
                            datafileReferantor.getValue(GlobalVariable.NumofReferantor, 14))

                        'click button search'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/Button_SearchReferantor'))

                        'verify lookup'
                        if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/a_Select'), 
                            GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                            referantorCode = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/span_referantorCode'))

                            'click select'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/a_Select'))
                        } else {
                            'click button x'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/button_X'))

                            'Write To Excel GlobalVariable.StatusWarning and GlobalVariable.StatusReasonLookup'
                            CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('5.TabReferantorData', 
                                GlobalVariable.NumofReferantor, GlobalVariable.StatusWarning, GlobalVariable.StatusReasonLookup)

                            'Click delete'
                            WebUI.click(modifyButtonDelete, FailureHandling.OPTIONAL)

                            if (WebUI.verifyElementPresent(modifyButtonDelete, GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                                writeReasonFailedDelete()
                            }
                            
							
							
                            continue
                        }
                        
                        if (GlobalVariable.RoleCompany == 'Testing') {
                            'Pengecekan terdapat opsi ddl bank account selain select one'
                            if (WebUI.getNumberOfTotalOption(modifySelectBankAccount) > 1) {
                                'Arraylist untuk menampung ddl bank account dari db'
                                ArrayList<WebElement> BankAccount = new ArrayList<WebElement>()

                                'Ambil array teks bank account dari db'
                                BankAccount = CustomKeywords.'referantorData.checkReferantor.checkBankAccountDDL'(sqlConnectionFOU, 
                                    refCategory, officeName, referantorCode)

                                'Verifikasi array teks bank account dari db sesuai dengan ddl yang tampil pada confins'
                                if (WebUI.verifyOptionsPresent(modifySelectBankAccount, BankAccount) == false) {
                                    writeReasonFailedDDL()
                                }
                                
                                'Pengecekan referantor category yang dipilih customer atau agency'
                                if (refCategory.equalsIgnoreCase('Customer') || refCategory.equalsIgnoreCase('Agency')) {
                                    'Ambil teks default bank account dari db'
                                    String defaultBankAccount = CustomKeywords.'referantorData.checkReferantor.checkBankAccountDefault'(
                                        sqlConnectionFOU, refCategory, officeName, referantorCode)

                                    'Verifikasi opsi yang terpilih secara default pada confins sesuai dengan db'
                                    if (WebUI.verifyOptionSelectedByLabel(modifySelectBankAccount, '(?i)' + defaultBankAccount, 
                                        true, GlobalVariable.TimeOut) == false) {
                                        writeReasonFailedDDL()
                                    }
                                }
                            }
                        }
                        
                        'select bank account'
                        WebUI.selectOptionByIndex(modifySelectBankAccount, datafileReferantor.getValue(GlobalVariable.NumofReferantor, 
                                16), FailureHandling.OPTIONAL)

                        'select tax calculation method'
                        WebUI.selectOptionByLabel(modifySelectTaxCalcualtion, datafileReferantor.getValue(GlobalVariable.NumofReferantor, 
                                17), false, FailureHandling.OPTIONAL)
						
						referantorType = WebUI.getText(modifyReferantorType)
						
						if(referantorType.equalsIgnoreCase("Customer Company")){
							if(datafileReferantor.getValue(GlobalVariable.NumofReferantor, 18).equalsIgnoreCase("Yes") && WebUI.verifyElementNotChecked(modifycheckboxVAT,GlobalVariable.TimeOut, FailureHandling.OPTIONAL)){
							   WebUI.check(modifycheckboxVAT)
							}
							else if(datafileReferantor.getValue(GlobalVariable.NumofReferantor, 18).equalsIgnoreCase("No") && WebUI.verifyElementChecked(modifycheckboxVAT,GlobalVariable.TimeOut, FailureHandling.OPTIONAL)){
								WebUI.uncheck(modifycheckboxVAT)
							}
						}

                        'cek inputan mandatory apakah sudah terisi semua atau belum'
                        if ((WebUI.verifyOptionSelectedByIndex(modifyObjectSelectReferantorCategory, 0, GlobalVariable.TimeOut, 
                            FailureHandling.OPTIONAL) || WebUI.verifyOptionSelectedByIndex(modifySelectBankAccount, 0, GlobalVariable.TimeOut, 
                            FailureHandling.OPTIONAL)) || WebUI.verifyOptionSelectedByIndex(modifySelectTaxCalcualtion, 
                            0, GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                            'Write To Excel GlobalVariable.StatusWarning and GlobalVariable.StatusReasonMandatoryEmpty'
                            CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('5.TabReferantorData', 
                                GlobalVariable.NumofReferantor, GlobalVariable.StatusWarning, GlobalVariable.StatusReasonMandatoryEmpty)

                            'Click delete'
                            WebUI.click(modifyButtonDelete, FailureHandling.OPTIONAL)

                            if (WebUI.verifyElementPresent(modifyButtonDelete, GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                                writeReasonFailedDelete()
                            }

						
							
                            continue
                        }else{
						'call function gettext bankaccount'
						getTextBankAccount(('//*[@id="accessoriesData"]/div[2]/table/tbody/tr[' + j) + ']/td[5]/select')
                        }
							'write to excel SUCCESS'
							CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '5.TabReferantorData',
								0, GlobalVariable.NumofReferantor - 1, GlobalVariable.StatusSuccess)
                        
                        add = 0
                    }
                    
                    Select selectedRefCategory = new Select(DriverFactory.getWebDriver().findElement(By.xpath(('//*[@id="accessoriesData"]/div[2]/table/tbody/tr[' + 
                            j) + ']/td[2]/select')))

                    'Ambil referantor category yang dipilih pada confins'
                    String textRefCategory = selectedRefCategory.getFirstSelectedOption().getText()

                    if (!(datafileReferantor.getValue(GlobalVariable.NumofReferantor, 13).equalsIgnoreCase(textRefCategory)) || 
                    !(datafileReferantor.getValue(GlobalVariable.NumofReferantor, 14).equalsIgnoreCase(WebUI.getAttribute(
                            modifyObjectReferantorName, 'value')))) {
                        if (countData == j) {
                            'click button add'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/button_Add'))

                            variableData = driver.findElements(By.cssSelector('#accessoriesData > div.table-responsive > table > tbody > tr'))

                            countData = variableData.size()

                            add = 1
                        }
                    } else {
                        break
                    }
                    //jika pada confins tidak ada data referantor
                } else if (WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/TableReferantornodata'), 
                        FailureHandling.OPTIONAL), 'NO DATA AVAILABLE', false, FailureHandling.OPTIONAL)) {
                    'click button add'
                    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/button_Add'))

                    'select referantor category'
                    WebUI.selectOptionByLabel(modifyObjectSelectReferantorCategory, datafileReferantor.getValue(GlobalVariable.NumofReferantor, 
                            13), false, FailureHandling.OPTIONAL)

                    'Ambil dan simpan nilai referantor category dari confins'
                    refCategory = WebUI.getAttribute(modifyObjectSelectReferantorCategory, 'value')

                    'click button referantor lookup'
                    WebUI.click(modifyObjectButtonReferantor, FailureHandling.OPTIONAL)

                    if (GlobalVariable.RoleCompany == 'Testing') {
                        if (refCategory != '') {
                            'click button search'
                            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/Button_SearchReferantor'))

                            'Cek total data referantor pada db'
                            Integer countReferantor = CustomKeywords.'referantorData.checkReferantor.countReferantorLookup'(
                                sqlConnectionFOU, refCategory, officeName)

                            'Ambil nilai total data referantor pada lookup confins'
                            String[] textTotalDataReferantor = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/label_TotalDataOfficer')).replace(
                                ' ', '').replace(':', ';').split(';')

                            'Parsing nilai total data referantor confins ke integer(angka)'
                            Integer totalDataReferantor = Integer.parseInt(textTotalDataReferantor[1])

                            'Verif total data referantor confins sesuai dengan db'
                            if (WebUI.verifyEqual(totalDataReferantor, countReferantor) == false) {
                                writeReasonFailedLookup()
                            }
                        }
                    }
                    
                    'input referantor name'
                    WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/input_Referantor Name_referantorName'), 
                        datafileReferantor.getValue(GlobalVariable.NumofReferantor, 14))

                    'click button search'
                    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/Button_SearchReferantor'))

                    'verify lookup'
                    if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/a_Select'), 
                        GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                        referantorCode = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/span_referantorCode'))

                        'click select'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/a_Select'))
                    } else {
                        'click button x'
                        WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/button_X'))

                        'Write To Excel GlobalVariable.StatusWarning and GlobalVariable.StatusReasonLookup'
                        CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('5.TabReferantorData', GlobalVariable.NumofReferantor, 
                            GlobalVariable.StatusWarning, GlobalVariable.StatusReasonLookup)

                        'get referantor name'
                        referantornamebefore = WebUI.getAttribute(modifyObjectReferantorName, 'value', FailureHandling.OPTIONAL)

                        'Click delete'
                        WebUI.click(modifyButtonDelete, FailureHandling.OPTIONAL)

                        if (WebUI.verifyElementPresent(modifyButtonDelete, GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                            writeReasonFailedDelete()
                        }
                        
                        continue
                    }
                    
                    if (GlobalVariable.RoleCompany == 'Testing') {
                        'Pengecekan terdapat opsi ddl bank account selain select one'
                        if (WebUI.getNumberOfTotalOption(modifySelectBankAccount) > 1) {
                            'Arraylist untuk menampung ddl bank account dari db'
                            ArrayList<WebElement> BankAccount = new ArrayList<WebElement>()

                            'Ambil array teks bank account dari db'
                            BankAccount = CustomKeywords.'referantorData.checkReferantor.checkBankAccountDDL'(sqlConnectionFOU, 
                                refCategory, officeName, referantorCode)

                            'Verifikasi array teks bank account dari db sesuai dengan ddl yang tampil pada confins'
                            if (WebUI.verifyOptionsPresent(modifySelectBankAccount, BankAccount) == false) {
                                writeReasonFailedDDL()
                            }
                            
                            'Pengecekan referantor category yang dipilih customer atau agency'
                            if (refCategory.equalsIgnoreCase('Customer') || refCategory.equalsIgnoreCase('Agency')) {
                                'Ambil teks default bank account dari db'
                                String defaultBankAccount = CustomKeywords.'referantorData.checkReferantor.checkBankAccountDefault'(
                                    sqlConnectionFOU, refCategory, officeName, referantorCode)

                                'Verifikasi opsi yang terpilih secara default pada confins sesuai dengan db'
                                if (WebUI.verifyOptionSelectedByLabel(modifySelectBankAccount, '(?i)' + defaultBankAccount, 
                                    true, GlobalVariable.TimeOut) == false) {
                                    writeReasonFailedDDL()
                                }
                            }
                        }
                    }
                    
                    'select bank account'
                    WebUI.selectOptionByIndex(modifySelectBankAccount, datafileReferantor.getValue(GlobalVariable.NumofReferantor, 
                            16), FailureHandling.OPTIONAL)

                    'select tax calculation method'
                    WebUI.selectOptionByLabel(modifySelectTaxCalcualtion, datafileReferantor.getValue(GlobalVariable.NumofReferantor, 
                            17), false, FailureHandling.OPTIONAL)

					referantorType = WebUI.getText(modifyReferantorType)
					
					if(referantorType.equalsIgnoreCase("Customer Company")){
						if(datafileReferantor.getValue(GlobalVariable.NumofReferantor, 18).equalsIgnoreCase("Yes") && WebUI.verifyElementNotChecked(modifycheckboxVAT,GlobalVariable.TimeOut, FailureHandling.OPTIONAL)){
						   WebUI.check(modifycheckboxVAT)
						}
						else if(datafileReferantor.getValue(GlobalVariable.NumofReferantor, 18).equalsIgnoreCase("No") && WebUI.verifyElementChecked(modifycheckboxVAT,GlobalVariable.TimeOut, FailureHandling.OPTIONAL)){
							WebUI.uncheck(modifycheckboxVAT)
						}
					}
					
                    'cek inputan mandatory apakah sudah terisi semua atau belum'
                    if ((WebUI.verifyOptionSelectedByIndex(modifyObjectSelectReferantorCategory, 0, GlobalVariable.TimeOut, 
                        FailureHandling.OPTIONAL) || WebUI.verifyOptionSelectedByIndex(modifySelectBankAccount, 0, GlobalVariable.TimeOut, 
                        FailureHandling.OPTIONAL)) || WebUI.verifyOptionSelectedByIndex(modifySelectTaxCalcualtion, 0, GlobalVariable.TimeOut, 
                        FailureHandling.OPTIONAL)) {
                        'Write To Excel GlobalVariable.StatusWarning and GlobalVariable.StatusReasonMandatoryEmpty'
                        CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('5.TabReferantorData', GlobalVariable.NumofReferantor, 
                            GlobalVariable.StatusWarning, GlobalVariable.StatusReasonMandatoryEmpty)

                        'Click delete'
                        WebUI.click(modifyButtonDelete, FailureHandling.OPTIONAL)

                        if (WebUI.verifyElementPresent(modifyButtonDelete, GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                            writeReasonFailedDelete()
                        }
                        
                        break
                    }else{
					'call function gettext bankaccount'
					getTextBankAccount(('//*[@id="accessoriesData"]/div[2]/table/tbody/tr[' + j) + ']/td[5]/select')
                    }
                    
                    'write to excel SUCCESS'
                    CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '5.TabReferantorData', 
                        0, GlobalVariable.NumofReferantor - 1, GlobalVariable.StatusSuccess)
                }
            }
        } else {
            break
        }
    }
}

//Jika copy app no
if (datafileReferantor.getValue(GlobalVariable.StartIndex, 10).equalsIgnoreCase('No')) {
    int modifyObjectIndex = 1

    'looping referantor'
    for (GlobalVariable.NumofReferantor = GlobalVariable.StartIndex; GlobalVariable.NumofReferantor <= (countReferantor - 
    1); (GlobalVariable.NumofReferantor)++) {
        if (datafileReferantor.getValue(GlobalVariable.NumofReferantor, 12) == datafileCustomerCompany.getValue(GlobalVariable.NumofColm, 
            13)) {
            'click button add'
            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/button_Add'))

            'modify button referantor lookup'
            modifyObjectButtonReferantor = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/button_LookupReferantor'), 
                'xpath', 'equals', ('//*[@id="accessoriesData"]/div[2]/table/tbody/tr[' + modifyObjectIndex) + ']/td[3]/lib-uclookupgeneric/div/div/div[1]/span/button', 
                true)

            'modify select referantor category'
            modifyObjectSelectReferantorCategory = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/select_ReferantorCategory'), 
                'xpath', 'equals', ('//*[@id="accessoriesData"]/div[2]/table/tbody/tr[' + modifyObjectIndex) + ']/td[2]/select', 
                true)

            'modify select tax calculation'
            modifySelectTaxCalcualtion = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/select_TaxCalculationMethod'), 
                'xpath', 'equals', ('//*[@id="accessoriesData"]/div[2]/table/tbody/tr[' + modifyObjectIndex) + ']/td[6]/select', 
                true)

            'modify select bank account'
            modifySelectBankAccount = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/select_BankAccount'), 
                'xpath', 'equals', ('//*[@id="accessoriesData"]/div[2]/table/tbody/tr[' + modifyObjectIndex) + ']/td[5]/select', 
                true)

            'modify button delete'
            modifyButtonDelete = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/Button Delete'), 
                'xpath', 'equals', ('//*[@id="accessoriesData"]/div[2]/table/tbody/tr[' + modifyObjectIndex) + ']/td[9]/a/i', 
                true)
			
			modifycheckboxVAT = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabReferantorData/input_VAT'),'xpath','equals',"//*[@id='accessoriesData']/div[2]/table/tbody/tr["+modifyObjectIndex+"]/td[7]/input",true)
			 
			modifyReferantorType = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabReferantorData/td_ReferantorType'),'xpath','equals',"//*[@id='accessoriesData']/div[2]/table/tbody/tr["+modifyObjectIndex+"]/td[4]",true)
			

            'select referantor category'
            WebUI.selectOptionByLabel(modifyObjectSelectReferantorCategory, datafileReferantor.getValue(GlobalVariable.NumofReferantor, 
                    13), false, FailureHandling.OPTIONAL)

            'Ambil dan simpan nilai referantor category dari confins'
            String refCategory = WebUI.getAttribute(modifyObjectSelectReferantorCategory, 'value')

            'click button referantor lookup'
            WebUI.click(modifyObjectButtonReferantor, FailureHandling.OPTIONAL)

            if (GlobalVariable.RoleCompany == 'Testing') {
                if (refCategory != '') {
                    'click button search'
                    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/Button_SearchReferantor'))

                    'Cek total data referantor pada db'
                    Integer countReferantor = CustomKeywords.'referantorData.checkReferantor.countReferantorLookup'(sqlConnectionFOU, 
                        refCategory, officeName)

                    'Ambil nilai total data referantor pada lookup confins'
                    String[] textTotalDataReferantor = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabApplicationData/label_TotalDataOfficer')).replace(
                        ' ', '').replace(':', ';').split(';')

                    'Parsing nilai total data referantor confins ke integer(angka)'
                    Integer totalDataReferantor = Integer.parseInt(textTotalDataReferantor[1])

                    'Verif total data referantor confins sesuai dengan db'
                    if (WebUI.verifyEqual(totalDataReferantor, countReferantor) == false) {
                        writeReasonFailedLookup()
                    }
                }
            }
            
            'input referantor name'
            WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/input_Referantor Name_referantorName'), 
                datafileReferantor.getValue(GlobalVariable.NumofReferantor, 14))

            'click button search'
            WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/Button_SearchReferantor'))

            String referantorCode

            'verify lookup'
            if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/a_Select'), 
                GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                referantorCode = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/span_referantorCode'))

                'click select'
                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/a_Select'))
            } else {
                'click button x'
                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/button_X'))

                'Write To Excel GlobalVariable.StatusWarning and GlobalVariable.StatusReasonLookup'
                CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('5.TabReferantorData', GlobalVariable.NumofReferantor, 
                    GlobalVariable.StatusWarning, GlobalVariable.StatusReasonLookup)

                'Click delete'
                WebUI.click(modifyButtonDelete, FailureHandling.OPTIONAL)

                if (WebUI.verifyElementPresent(modifyButtonDelete, GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                    writeReasonFailedDelete()
                }
                
                continue
            }
            
            if (GlobalVariable.RoleCompany == 'Testing') {
                'Pengecekan terdapat opsi ddl bank account selain select one'
                if (WebUI.getNumberOfTotalOption(modifySelectBankAccount) > 1) {
                    'Arraylist untuk menampung ddl bank account dari db'
                    ArrayList<WebElement> BankAccount = new ArrayList<WebElement>()

                    'Ambil array teks bank account dari db'
                    BankAccount = CustomKeywords.'referantorData.checkReferantor.checkBankAccountDDL'(sqlConnectionFOU, refCategory, 
                        officeName, referantorCode)

                    'Verifikasi array teks bank account dari db sesuai dengan ddl yang tampil pada confins'
                    if (WebUI.verifyOptionsPresent(modifySelectBankAccount, BankAccount) == false) {
                        writeReasonFailedDDL()
                    }
                    
                    'Pengecekan referantor category yang dipilih customer atau agency'
                    if (refCategory.equalsIgnoreCase('Customer') || refCategory.equalsIgnoreCase('Agency')) {
                        'Ambil teks default bank account dari db'
                        String defaultBankAccount = CustomKeywords.'referantorData.checkReferantor.checkBankAccountDefault'(
                            sqlConnectionFOU, refCategory, officeName, referantorCode)

                        'Verifikasi opsi yang terpilih secara default pada confins sesuai dengan db'
                        if (WebUI.verifyOptionSelectedByLabel(modifySelectBankAccount, '(?i)' + defaultBankAccount, true, 
                            GlobalVariable.TimeOut) == false) {
                            writeReasonFailedDDL()
                        }
                    }
                }
            }
            
            'select bank account'
            WebUI.selectOptionByIndex(modifySelectBankAccount, datafileReferantor.getValue(GlobalVariable.NumofReferantor, 
                    16), FailureHandling.OPTIONAL)

            'call function gettext bankaccount'
            getTextBankAccount(('//*[@id="accessoriesData"]/div[2]/table/tbody/tr[' + modifyObjectIndex) + ']/td[5]/select')

            'select tax calculation method'
            WebUI.selectOptionByLabel(modifySelectTaxCalcualtion, datafileReferantor.getValue(GlobalVariable.NumofReferantor, 
                    17), false, FailureHandling.OPTIONAL)
			
			referantorType = WebUI.getText(modifyReferantorType)
			
			if(referantorType.equalsIgnoreCase("Customer Company")){
				if(datafileReferantor.getValue(GlobalVariable.NumofReferantor, 18).equalsIgnoreCase("Yes") && WebUI.verifyElementNotChecked(modifycheckboxVAT,GlobalVariable.TimeOut, FailureHandling.OPTIONAL)){
				   WebUI.check(modifycheckboxVAT)
				}
				else if(datafileReferantor.getValue(GlobalVariable.NumofReferantor, 18).equalsIgnoreCase("No") && WebUI.verifyElementChecked(modifycheckboxVAT,GlobalVariable.TimeOut, FailureHandling.OPTIONAL)){
					WebUI.uncheck(modifycheckboxVAT)
				}
			}

            'cek inputan mandatory apakah sudah terisi semua atau belum'
            if ((WebUI.verifyOptionSelectedByIndex(modifyObjectSelectReferantorCategory, 0, GlobalVariable.TimeOut, FailureHandling.OPTIONAL) || 
            WebUI.verifyOptionSelectedByIndex(modifySelectBankAccount, 0, GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) || 
            WebUI.verifyOptionSelectedByIndex(modifySelectTaxCalcualtion, 0, GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                'Write To Excel GlobalVariable.StatusWarning and GlobalVariable.StatusReasonMandatoryEmpty'
                CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('5.TabReferantorData', GlobalVariable.NumofReferantor, 
                    GlobalVariable.StatusWarning, GlobalVariable.StatusReasonMandatoryEmpty)

                'Click delete'
                WebUI.click(modifyButtonDelete, FailureHandling.OPTIONAL)

                if (WebUI.verifyElementPresent(modifyButtonDelete, GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
                    writeReasonFailedDelete()
                }
                
                continue
            }
            
            //Testing
            if (GlobalVariable.RoleCompany == 'Testing') {
                ArrayList<WebElement> referantorDetail = new ArrayList<WebElement>()

                String newButtonViewDetail = ('//*[@id="accessoriesData"]/div[2]/table/tbody/tr[' + modifyObjectIndex) + 
                ']/td[8]/a/i'

                'modify button view detail'
                modifyButtonViewDetail = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/i_ViewDetail'), 
                    'xpath', 'equals', newButtonViewDetail, true)

                'click button view detail'
                WebUI.click(modifyButtonViewDetail)

                'Pengecekan referantor category (customer, agency, atau mf employee)'
                if (refCategory.equalsIgnoreCase('CUSTOMER')) {
                    'pengecekan ke db dan simpan data-data detail referantor yang dibutuhkan dari db'
                    referantorDetail = CustomKeywords.'referantorData.checkReferantorDetail.checkCustomerReferantor'(sqlConnectionFOU, 
                        referantorCode)
                } else if (refCategory.equalsIgnoreCase('AGENCY')) {
                    'Pengecekan ke db dan simpan data-data detail referantor yang dibutuhkan dari db'
                    referantorDetail = CustomKeywords.'referantorData.checkReferantorDetail.checkAgencyReferantor'(sqlConnectionFOU, 
                        referantorCode)
                } else if (refCategory.equalsIgnoreCase('MF_EMP')) {
                    'Pengecekan ke db dan simpan data-data detail referantor yang dibutuhkan dari db'
                    referantorDetail = CustomKeywords.'referantorData.checkReferantorDetail.checkMFEmployeeReferantor'(sqlConnectionFOU, 
                        referantorCode)
                }
                
                ArrayList<WebElement> arrayMatch = adddatatoarraylist(referantorDetail)

                'Jika nilai di confins ada yang tidak sesuai dengan db'
                if (arrayMatch.contains(false)) {
                    writeToExcelTidakSesuaiDB()

                    modifyObjectIndex++

                    continue
                }
                
                'click x'
                WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/span_XViewDetail'))
            }
            
            'write to excel SUCCESS'
            CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '5.TabReferantorData', 
                0, GlobalVariable.NumofReferantor - 1, GlobalVariable.StatusSuccess)

            modifyObjectIndex++
        } else {
            break
        }
    }
}

addArrayVAT()

'click button save'
WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/Button Save'))

Integer iscompleteMandatory = Integer.parseInt(datafileReferantor.getValue(GlobalVariable.NumofReferantor - 1, 4))

if ((iscompleteMandatory == 0) && (GlobalVariable.FlagFailed == 0)) {
    'cek alert'
    GlobalVariable.FlagFailed = CustomKeywords.'checkSaveProcess.checkSaveProcess.checkAlert'(GlobalVariable.NumofReferantor, 
        '5.TabReferantorData')
}

if (WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/ApplicationCurrentStep')), 
    'REFERANTOR', false, FailureHandling.OPTIONAL)) {
    'click button cancel'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/button_Cancel'))
	
	GlobalVariable.IsDataCancel = 1
}else{
	'check if role = testing & check store db = yes & status = success'
	if (((GlobalVariable.RoleCompany == 'Testing') && (GlobalVariable.CheckVerifStoreDBCompany == 'Yes'))) {
	    'call test case store db referantor data'
	    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerCompany/NAP2 - Application Data/TabReferantorDataStoreDBVerif'), [:], 
	        FailureHandling.CONTINUE_ON_FAILURE)
	}
}

def adddatatoarraylist(ArrayList<WebElement> referantorDetail) {
    'Ambil dan simpan npwp no dari confins'
    String textNPWPNo = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/label_NPWPNo'))

    'Ambil dan simpan npwp name dari confins'
    String textNPWPName = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/label_NPWPName'))

    'Ambil dan simpan address dari confins'
    String textAddress = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/label_Address'))

    'ambil dan simpan rt dari confins'
    String textRT = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/label_RT')).replace(
        ' ', '')

    'ambil dan simpan rw dari confins'
    String textRW = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/label_RW')).replace(
        '/ ', '')

    'ambil dan simpan kelurahan dari confins'
    String textKelurahan = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/label_Kelurahan'))

    'ambil dan simpan kecamatan dari confins'
    String textKecamatan = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/label_Kecamatan'))

    'ambil dan simpan kota dari confins'
    String textCity = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/label_Kota'))

    'ambil dan simpan zipcode dari confins'
    String textZipcode = WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/label_Zipcode'))

    'arraylist boolean untuk menampung hasil dari verififikasi data dari confins sesuai dengan db atau tidak (true atau false)'
    ArrayList<WebElement> arrayMatch = new ArrayList<WebElement>()

    arrayMatch.add(WebUI.verifyMatch(textNPWPNo, '(?i)' + (referantorDetail[0]), true, FailureHandling.OPTIONAL))

    arrayMatch.add(WebUI.verifyMatch(textNPWPName, '(?i)' + (referantorDetail[1]), true, FailureHandling.OPTIONAL))

    arrayMatch.add(WebUI.verifyMatch(textAddress, '(?i)' + (referantorDetail[2]), true, FailureHandling.OPTIONAL))

    arrayMatch.add(WebUI.verifyMatch(textRT, '(?i)' + (referantorDetail[3]), true, FailureHandling.OPTIONAL))

    arrayMatch.add(WebUI.verifyMatch(textRW, '(?i)' + (referantorDetail[4]), true, FailureHandling.OPTIONAL))

    arrayMatch.add(WebUI.verifyMatch(textKelurahan, '(?i)' + (referantorDetail[5]), true, FailureHandling.OPTIONAL))

    arrayMatch.add(WebUI.verifyMatch(textKecamatan, '(?i)' + (referantorDetail[6]), true, FailureHandling.OPTIONAL))

    arrayMatch.add(WebUI.verifyMatch(textCity, '(?i)' + (referantorDetail[7]), true, FailureHandling.OPTIONAL))

    arrayMatch.add(WebUI.verifyMatch(textZipcode, '(?i)' + (referantorDetail[8]), true, FailureHandling.OPTIONAL))

    return arrayMatch
}

def writeReasonFailedDelete() {
    'Write To Excel GlobalVariable.ReasonFailedDelete'
    CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '5.TabReferantorData', 1, GlobalVariable.NumofReferantor - 
        1, findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData').getValue(GlobalVariable.NumofColm, 2) + ';' + GlobalVariable.ReasonFailedDelete)
}

def writeReasonFailedLookup() {
    'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDataLookup'
    CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('5.TabReferantorData', GlobalVariable.NumofReferantor, 
        GlobalVariable.StatusFailed, findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData').getValue(GlobalVariable.NumofColm, 2) + ';' + GlobalVariable.ReasonFailedDataLookup)

    GlobalVariable.FlagFailed = 1
}

def writeReasonFailedDDL() {
    'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDDL'
    CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('5.TabReferantorData', GlobalVariable.NumofReferantor, 
        GlobalVariable.StatusFailed, findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData').getValue(GlobalVariable.NumofColm, 2) + ';' + GlobalVariable.ReasonFailedDDL)

    GlobalVariable.FlagFailed = 1
}

def writeToExcelTidakSesuaiDB() {
    'Write To Excel GlobalVariable.StatusWarning and GlobalVariable.StatusReasonTidakSesuaiDB'
    CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('5.TabReferantorData', GlobalVariable.NumofReferantor, 
        GlobalVariable.StatusWarning, GlobalVariable.StatusReasonTidakSesuaiDB)

    'click cancel'
    WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/button_CancelViewDetail'))
}

def getTextBankAccount(String newSelectBankaccount) {
    if (GlobalVariable.BankAccount == '') {
        Select selectBankAcc = new Select(DriverFactory.getWebDriver().findElement(By.xpath(newSelectBankaccount)))

        'Ambil text bank account yang dipilih pada confins'
        GlobalVariable.BankAccount = selectBankAcc.getFirstSelectedOption().getText()
    } else {
        Select selectBankAcc = new Select(DriverFactory.getWebDriver().findElement(By.xpath(newSelectBankaccount)))

        'Ambil text bank account yang dipilih pada confins'
        GlobalVariable.BankAccount = ((GlobalVariable.BankAccount + ' - ') + selectBankAcc.getFirstSelectedOption().getText())
    }
}

def checkVerifyEqualOrMatch(Boolean isMatch) {
    if ((isMatch == false) && (GlobalVariable.FlagFailed == 0)) {
        'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedVerifyEqualOrMatch'
        CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('5.TabReferantorData', GlobalVariable.NumofReferantor, 
            GlobalVariable.StatusFailed, findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData').getValue(GlobalVariable.NumofColm, 2) + ';' + GlobalVariable.ReasonFailedVerifyEqualOrMatch)

        GlobalVariable.FlagFailed = 1
    }
}

public addArrayVAT(){
	ArrayList<Boolean> isVat = new ArrayList<>()
	'declare driver'
	WebDriver driver = DriverFactory.getWebDriver()
	def variable = driver.findElements(By.cssSelector('#accessoriesData > div.table-responsive > table > tbody > tr'))
	for(int i = 1;i<=variable.size();i++){
		def modifycheckboxVAT = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP2-ApplicationData/TabReferantorData/input_VAT'),'xpath','equals',"//*[@id='accessoriesData']/div[2]/table/tbody/tr["+i+"]/td[7]/input",true)
		if(WebUI.verifyElementChecked(modifycheckboxVAT,GlobalVariable.TimeOut,FailureHandling.OPTIONAL)){
			isVat.add(true)
		}
		else{
			isVat.add(false)
		}
	}
	GlobalVariable.ReferantorVAT = isVat
}

def checkDDL(Sql sqlConnectionFOU){
	if(GlobalVariable.RoleCompany == 'Testing'){
		int click
		if (WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/CheckboxReferantor'),
			'aria-checked') == 'false') {
			'click referantor checkbox'
			WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/input_CheckboxReferantor'))

			'click button add'
			WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/button_Add'))
			
			click = 1
		}
		
		'get ReferantorType ddl value from db'
		ArrayList<String> ReferantorType = CustomKeywords.'referantorData.checkReferantor.checkReferantorType'(sqlConnectionFOU)
		
		'get total label from ddl ReferantorType'
		int totalddlReferantorType = WebUI.getNumberOfTotalOption(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/select_ReferantorType'))
	
		'verify total ddl ReferantorType confins = total ddl db'
		WebUI.verifyEqual(totalddlReferantorType - 1, ReferantorType.size())
		
		'verify isi ddl ReferantorType confins = db'
		if (WebUI.verifyOptionsPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/select_ReferantorType'),
			ReferantorType) == false) {
	
			'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDDL'
			CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('5.TabReferantorData', GlobalVariable.NumofColm, GlobalVariable.StatusFailed, findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData').getValue(GlobalVariable.NumofColm, 2) + ';' + GlobalVariable.ReasonFailedDDL + 'ReferantorType')
	
			(GlobalVariable.FlagFailed)++
		}
		
		
		'get taxcalcu ddl value from db'
		ArrayList<String> taxcalcu = CustomKeywords.'referantorData.checkReferantor.checkTaxCalculation'(sqlConnectionFOU)
		
		'get total label from ddl taxcalcu'
		int totalddltaxcalcu = WebUI.getNumberOfTotalOption(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/select_TaxCalculationMethod'))
	
		'verify total ddl taxcalcu confins = total ddl db'
		WebUI.verifyEqual(totalddltaxcalcu - 1, taxcalcu.size())
		
		'verify isi ddl taxcalcu confins = db'
		if (WebUI.verifyOptionsPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/select_TaxCalculationMethod'),
			taxcalcu) == false) {
	
			'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedDDL'
			CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('5.TabReferantorData', GlobalVariable.NumofColm, GlobalVariable.StatusFailed, findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData').getValue(GlobalVariable.NumofColm, 2) + ';' + GlobalVariable.ReasonFailedDDL + 'tax calculation')
	
			(GlobalVariable.FlagFailed)++
		}
			
			if(click == 1){
				'click referantor checkbox'
				WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/input_CheckboxReferantor'))
			}
		}
}