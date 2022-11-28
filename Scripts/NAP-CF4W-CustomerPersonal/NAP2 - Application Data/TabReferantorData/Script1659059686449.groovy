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
GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'(GlobalVariable.PathPersonal)

'declare datafileCustomerPersonal'
datafileCustomerPersonal = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData')

String appLastStep = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/label_AppLastStep'))

if(!appLastStep.equalsIgnoreCase("GUARANTOR") && !appLastStep.equalsIgnoreCase("NAP DETAIL") && GlobalVariable.FirstTimeEntry=="Yes"){
	GlobalVariable.FirstTimeEntry = "No"
}

if (GlobalVariable.Role == 'Testing') {
    'verify application step'
    checkVerifyEqualOrMatch(WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/ApplicationCurrentStep')), 
        'REFERANTOR', false, FailureHandling.OPTIONAL))
}

'declare datafileReferantor'
datafileReferantor = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP2-ApplicationData/TabReferantorData')

Sql sqlConnection = CustomKeywords.'dbConnection.connectDB.connectFOU'()

WebDriver driver = DriverFactory.getWebDriver()

'Ambil text original office dari confins'
String officeName = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/label_OriginalOffice'))

//pengecekan pada excel data referantor ada lebih dari atau sama dengan 1
if (Integer.parseInt(GlobalVariable.CountofReferantor) >= 1) {
	'Pengecekan checkbox sebelumnya tidak tercentang'
	if (WebUI.getAttribute(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/CheckboxReferantor'),
			'aria-checked') == "false") {
		'click referantor checkbox'
		WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabReferantorData/input_CheckboxReferantor'))
	}
}

//Jika copy app edit
if (datafileReferantor.getValue(GlobalVariable.CopyAppColm, 10).equalsIgnoreCase('Edit')) {
	
	'arraylist referantor name yang gagal'
	ArrayList<String> referantorfaileddelete = new ArrayList<String>()
    
    ArrayList<String> variable = driver.findElements(By.cssSelector('#accessoriesData > div.table-responsive > table > tbody > tr'))

	//Edit jika ada data pada confins dan datanya sesuai dengan excel
	'Pengecekan ada data referantor pada confins'
	if (WebUI.verifyNotMatch(WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/TableReferantornodata'),FailureHandling.OPTIONAL),
		'NO DATA AVAILABLE', false, FailureHandling.OPTIONAL)) {
	
		'Looping confins referantor'
		for(int i =1;i<=variable.size();i++){
			
			'modify input referantor name'
			modifyObjectReferantorName = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/input_referantorName'),'xpath','equals',"//*[@id='accessoriesData']/div[2]/table/tbody/tr["+i+"]/td[3]/lib-uclookupgeneric/div/div/input", true)
	
			'modify button referantor lookup'
			 modifyObjectButtonReferantor = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/button_LookupReferantor'),
				 'xpath', 'equals', ('//*[@id="accessoriesData"]/div[2]/table/tbody/tr[' + i) +']/td[3]/lib-uclookupgeneric/div/div/div[1]/span/button', true)
	
			'modify select referantor category'
			 modifyObjectSelectReferantorCategory = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/select_ReferantorCategory'),
				 'xpath', 'equals', ('//*[@id="accessoriesData"]/div[2]/table/tbody/tr[' + i) +
			 ']/td[2]/select', true)
			
			 'modify button delete'
			 modifyButtonDelete = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/Button Delete'),
				 'xpath', 'equals', ('//*[@id="accessoriesData"]/div[2]/table/tbody/tr[' + i) + ']/td[9]/a/i', true)
			 
			  'modify select tax calculation'
			  modifySelectTaxCalcualtion = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/select_TaxCalculationMethod'),
				  'xpath', 'equals', ('//*[@id="accessoriesData"]/div[2]/table/tbody/tr[' + i) +
			  ']/td[6]/select', true)
	
			  'modify select bank account'
			  modifySelectBankAccount = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/select_BankAccount'),
				  'xpath', 'equals', ('//*[@id="accessoriesData"]/div[2]/table/tbody/tr[' + i) + ']/td[5]/select', true)
			  
			  Select selectedRefCategory =  new Select(DriverFactory.getWebDriver().findElement(By.xpath(('//*[@id="accessoriesData"]/div[2]/table/tbody/tr[' + i) +
			 ']/td[2]/select')))
			  
			  'Ambil text referantor category yang dipilih pada confins'
			  String textRefCategory = selectedRefCategory.getFirstSelectedOption().getText()
			  
			  'Looping excel referantor'
			  for (GlobalVariable.NumofReferantor = GlobalVariable.CopyAppColm; GlobalVariable.NumofReferantor <= (Integer.parseInt(GlobalVariable.CountofReferantor) +
				  2); (GlobalVariable.NumofReferantor)++) {
				  if (datafileReferantor.getValue(
					  GlobalVariable.NumofReferantor, 12) == datafileCustomerPersonal.getValue(
					  GlobalVariable.NumofColm, 13)) {
						
						'Pengecekan jika referantor category dan referantor name pada confins sesuai dengan excel datafile'
					  if (textRefCategory.equalsIgnoreCase(datafileReferantor.getValue(
						  GlobalVariable.NumofReferantor, 13)) && WebUI.getAttribute(modifyObjectReferantorName,'value').equalsIgnoreCase(datafileReferantor.getValue(
						  GlobalVariable.NumofReferantor, 14))) {
							  
							'select bank account'
						  WebUI.selectOptionByIndex(modifySelectBankAccount, datafileReferantor.getValue(
						  GlobalVariable.NumofReferantor, 16),FailureHandling.OPTIONAL)
						  
						  'call function gettext bankaccount'
						  getTextBankAccount(('//*[@id="accessoriesData"]/div[2]/table/tbody/tr[' + i) + ']/td[5]/select')
						  
						  'select tax calculation'
						  WebUI.selectOptionByLabel(modifySelectTaxCalcualtion, datafileReferantor.getValue(
						  GlobalVariable.NumofReferantor, 17),false,FailureHandling.OPTIONAL)
					  
						  'cek inputan mandatory apakah sudah terisi semua atau belum'
						  if ((WebUI.verifyOptionSelectedByIndex(modifyObjectSelectReferantorCategory, 0, 3, FailureHandling.OPTIONAL) ||
								  WebUI.verifyOptionSelectedByIndex(modifySelectBankAccount, 0, 3, FailureHandling.OPTIONAL)) || WebUI.verifyOptionSelectedByIndex(
										  modifySelectTaxCalcualtion, 0, 3, FailureHandling.OPTIONAL)) {
							  'write to excel WARNING'
							  CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '5.TabReferantorData',
									  0, GlobalVariable.NumofReferantor - 1, GlobalVariable.StatusWarning)
							  
							  'Write To Excel GlobalVariable.StatusReasonLookup'
							  CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '5.TabReferantorData',
									  1, GlobalVariable.NumofReferantor - 1, GlobalVariable.StatusReasonMandatoryEmpty)
							  
							  'get referantor name'
							  referantornamebefore = WebUI.getAttribute(modifyObjectReferantorName, 'value', FailureHandling.OPTIONAL)
							  
							  'Click delete'
							  WebUI.click(modifyButtonDelete, FailureHandling.OPTIONAL)
							  
							  if(i == variable.size()){
								  if(WebUI.verifyElementNotPresent(modifyObjectReferantorName, 5, FailureHandling.OPTIONAL)){
									  variable = driver.findElements(By.cssSelector('#accessoriesData > div.table-responsive > table > tbody > tr'))
								  }else{
									  'add cust name failed kedalam array'
									  referantorfaileddelete.add(referantornamebefore)
									  continue
								  }
							  }else{
								  'get cust name sebelum delete'
								  referantornameafter = WebUI.getAttribute(modifyObjectReferantorName, 'value', FailureHandling.OPTIONAL)
										  
								  if(WebUI.verifyNotMatch(referantornameafter, referantornamebefore, false, FailureHandling.OPTIONAL)){
											  variable = driver.findElements(By.cssSelector('#accessoriesData > div.table-responsive > table > tbody > tr'))
								  }else{
										'add cust name failed kedalam array'
										referantorfaileddelete.add(referantornamebefore)
										continue
								  }
							  }
							  i--
						  }
						  break
					  }
				  }
				  //delete jika ada data pada confins, tetapi pada datafile tidak ada
				  else{
						  if(GlobalVariable.NumofReferantor == (Integer.parseInt(GlobalVariable.CountofReferantor)+2)){
							  'Jika pada confins ada datanya'
							  if (WebUI.verifyNotMatch(WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/TableReferantornodata'),FailureHandling.OPTIONAL),
								  'NO DATA AVAILABLE', false, FailureHandling.OPTIONAL)){
									  
								 'get referantor name'
								referantornamebefore = WebUI.getAttribute(modifyObjectReferantorName, 'value', FailureHandling.OPTIONAL)
							  
								'Click delete'
								WebUI.click(modifyButtonDelete, FailureHandling.OPTIONAL)
								
								if(i == variable.size()){
									if(WebUI.verifyElementNotPresent(modifyObjectReferantorName, 5, FailureHandling.OPTIONAL)){
										variable = driver.findElements(By.cssSelector('#accessoriesData > div.table-responsive > table > tbody > tr'))
									}else{
										'add cust name failed kedalam array'
										referantorfaileddelete.add(referantornamebefore)
										continue
									}
								}else{
									'get cust name sebelum delete'
									referantornameafter = WebUI.getAttribute(modifyObjectReferantorName, 'value', FailureHandling.OPTIONAL)
								
									if(WebUI.verifyNotMatch(referantornameafter, referantornamebefore, false, FailureHandling.OPTIONAL)){
										variable = driver.findElements(By.cssSelector('#accessoriesData > div.table-responsive > table > tbody > tr'))
									}else{
										'add cust name failed kedalam array'
										referantorfaileddelete.add(referantornamebefore)
										continue
									}
								}
								i--
							  }
							  else{
								  break
							  }
						  }
				  }
			  }
		}
	}
	
	if(referantorfaileddelete.size() > 0){
			CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath,
				'5.TabReferantorData', 0, GlobalVariable.CopyAppColm - 1, GlobalVariable.StatusWarning)
			
			CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '5.TabReferantorData',
					1, GlobalVariable.CopyAppColm - 1, GlobalVariable.ReasonFailedDelete + referantorfaileddelete)
			
			GlobalVariable.FlagWarning++
	}
		
	ArrayList<WebElement> variableData = driver.findElements(By.cssSelector('#accessoriesData > div.table-responsive > table > tbody > tr'))
	int countData = variableData.size()
	int add=0
	
	//Add data jika pada confins tidak ada datanya (yang mau diadd), tetapi pada excel ada
	'Looping excel referantor'
	for (GlobalVariable.NumofReferantor = GlobalVariable.CopyAppColm; GlobalVariable.NumofReferantor <= (Integer.parseInt(GlobalVariable.CountofReferantor) +
		2); (GlobalVariable.NumofReferantor)++) {
			
			if (datafileReferantor.getValue(
				GlobalVariable.NumofReferantor, 12) == datafileCustomerPersonal.getValue(
				GlobalVariable.NumofColm, 13)) {
				
				'Looping confins referantor'
				for(int j = 1;j<=countData;j++){
				
					'modify input referantor name'
					modifyObjectReferantorName = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/input_referantorName'),'xpath','equals',"//*[@id='accessoriesData']/div[2]/table/tbody/tr["+j+"]/td[3]/lib-uclookupgeneric/div/div/input", true)
			 
					'modify button referantor lookup'
					modifyObjectButtonReferantor = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/button_LookupReferantor'),
						  'xpath', 'equals', ('//*[@id="accessoriesData"]/div[2]/table/tbody/tr[' + j) +
					  ']/td[3]/lib-uclookupgeneric/div/div/div[1]/span/button', true)
			 
					'modify select referantor category'
					modifyObjectSelectReferantorCategory = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/select_ReferantorCategory'),
						  'xpath', 'equals', ('//*[@id="accessoriesData"]/div[2]/table/tbody/tr[' + j) +
					  ']/td[2]/select', true)
					
					'modify button delete'
					modifyButtonDelete = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/Button Delete'),
						  'xpath', 'equals', ('//*[@id="accessoriesData"]/div[2]/table/tbody/tr[' + j) + ']/td[9]/a/i', true)
					  
					'modify select tax calculation'
					modifySelectTaxCalcualtion = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/select_TaxCalculationMethod'),
						   'xpath', 'equals', ('//*[@id="accessoriesData"]/div[2]/table/tbody/tr[' + j) +
					   ']/td[6]/select', true)
			 
					'modify select bank account'
					modifySelectBankAccount = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/select_BankAccount'),
						   'xpath', 'equals', ('//*[@id="accessoriesData"]/div[2]/table/tbody/tr[' + j) + ']/td[5]/select', true)
					
					String refCategory, referantorCode
					
					'Jika pada confins ada data referantor sebelumnya'
					if (WebUI.verifyNotMatch(WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/TableReferantornodata'),FailureHandling.OPTIONAL),
						'NO DATA AVAILABLE', false, FailureHandling.OPTIONAL)){
						   if(add==1){
							   'select referantor category'
							   WebUI.selectOptionByLabel(modifyObjectSelectReferantorCategory, datafileReferantor.getValue(
									   GlobalVariable.NumofReferantor, 13), false, FailureHandling.OPTIONAL)
							   
							   'Ambil dan simpan nilai referantor category dari confins'
							   refCategory = WebUI.getAttribute(modifyObjectSelectReferantorCategory, 'value')
							   
							   'click button referantor lookup'
							   WebUI.click(modifyObjectButtonReferantor, FailureHandling.OPTIONAL)
							   
							   if(GlobalVariable.Role=="Testing"){
								   if(refCategory!=""){
									   'click button search'
									   WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/Button_SearchReferantor'))
									   
									   'Cek total data referantor pada db'
									   Integer countReferantor = CustomKeywords.'referantorData.checkReferantor.countReferantorLookup'(sqlConnection, refCategory, officeName)
									   
									   'Ambil nilai total data referantor pada lookup confins'
									   String[] textTotalDataReferantor = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/label_TotalDataOfficer')).replace(
										   ' ', '').replace(':', ';').split(';')
							   
									   'Parsing nilai total data referantor confins ke integer(angka)'
									   Integer totalDataReferantor = Integer.parseInt(textTotalDataReferantor[1])
							   
									   'Verif total data referantor confins sesuai dengan db'
									   if(WebUI.verifyEqual(totalDataReferantor, countReferantor)==false){
										   writeReasonFailedLookup()
									   }
								   }
							   }
							   
							   'input referantor name'
							   WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/input_Referantor Name_referantorName'),
								   datafileReferantor.getValue(
									   GlobalVariable.NumofReferantor, 14))
				   
							   'click button search'
							   WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/Button_SearchReferantor'))
							   
							   'verify lookup'
							   if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/a_Select'),
									5, FailureHandling.OPTIONAL)) {
									referantorCode = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/span_referantorCode'))
											   
									'click select'
									WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/a_Select'))
							   } else {
									'click button x'
									WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/button_X'))
							   
									'write to excel WARNING'
									CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '5.TabReferantorData',
											0, GlobalVariable.NumofReferantor - 1, GlobalVariable.StatusWarning)
											   
									'Write To Excel GlobalVariable.StatusReasonLookup'
									CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '5.TabReferantorData',
											1, GlobalVariable.NumofReferantor - 1, GlobalVariable.StatusReasonLookup)
											   
									'Click delete'
									WebUI.click(modifyButtonDelete, FailureHandling.OPTIONAL)
									
									if(WebUI.verifyElementPresent(modifyButtonDelete, 5, FailureHandling.OPTIONAL)){
										writeReasonFailedDelete()
									}
									
									continue
								}
								 
							   if(GlobalVariable.Role=="Testing"){
								   'Pengecekan terdapat opsi ddl bank account selain select one'
								   if(WebUI.getNumberOfTotalOption(modifySelectBankAccount)>1){
									   
									   'Arraylist untuk menampung ddl bank account dari db'
									   ArrayList<String> BankAccount = new ArrayList<String>()
									   
									   'Ambil array teks bank account dari db'
									   BankAccount = CustomKeywords.'referantorData.checkReferantor.checkBankAccountDDL'(sqlConnection, refCategory, officeName, referantorCode)
									   
									   'Verifikasi array teks bank account dari db sesuai dengan ddl yang tampil pada confins'
									   if(WebUI.verifyOptionsPresent(modifySelectBankAccount, BankAccount)==false){
										   writeReasonFailedDDL()
									   }
									   
									   'Pengecekan referantor category yang dipilih customer atau agency'
									   if(refCategory.equalsIgnoreCase("Customer")||refCategory.equalsIgnoreCase("Agency")){
										   
										   'Ambil teks default bank account dari db'
										   String defaultBankAccount = CustomKeywords.'referantorData.checkReferantor.checkBankAccountDefault'(sqlConnection, refCategory, officeName, referantorCode)
										   
										   'Verifikasi opsi yang terpilih secara default pada confins sesuai dengan db'
										   if(WebUI.verifyOptionSelectedByLabel(modifySelectBankAccount,"(?i)"+defaultBankAccount,true,2)==false){
											   writeReasonFailedDDL()
										   }
									   }
								   }
							   }
							   
							   'select bank account'
							   WebUI.selectOptionByIndex(modifySelectBankAccount, datafileReferantor.getValue(
									   GlobalVariable.NumofReferantor, 16), FailureHandling.OPTIONAL)
				   
							   'call function gettext bankaccount'
							   getTextBankAccount(('//*[@id="accessoriesData"]/div[2]/table/tbody/tr[' + j) + ']/td[5]/select')
							   
							   'select tax calculation method'
							   WebUI.selectOptionByLabel(modifySelectTaxCalcualtion, datafileReferantor.getValue(
									   GlobalVariable.NumofReferantor, 17), false, FailureHandling.OPTIONAL)
				   
							   'cek inputan mandatory apakah sudah terisi semua atau belum'
							   if ((WebUI.verifyOptionSelectedByIndex(modifyObjectSelectReferantorCategory, 0, 3, FailureHandling.OPTIONAL) ||
							   WebUI.verifyOptionSelectedByIndex(modifySelectBankAccount, 0, 3, FailureHandling.OPTIONAL)) || WebUI.verifyOptionSelectedByIndex(
								   modifySelectTaxCalcualtion, 0, 3, FailureHandling.OPTIONAL)) {
								   'write to excel WARNING'
								   CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '5.TabReferantorData',
									   0, GlobalVariable.NumofReferantor - 1, GlobalVariable.StatusWarning)
				   
								   'Write To Excel GlobalVariable.StatusReasonLookup'
								   CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '5.TabReferantorData',
									   1, GlobalVariable.NumofReferantor - 1, GlobalVariable.StatusReasonMandatoryEmpty)
								   
								   'Click delete'
								   WebUI.click(modifyButtonDelete, FailureHandling.OPTIONAL)
								   
								   if(WebUI.verifyElementPresent(modifyButtonDelete, 5, FailureHandling.OPTIONAL)){
									   writeReasonFailedDelete()
								   }
								   
								   continue
							   }
							   add=0
						   }
						   
						   Select selectedRefCategory =  new Select(DriverFactory.getWebDriver().findElement(By.xpath(('//*[@id="accessoriesData"]/div[2]/table/tbody/tr[' + j) +
					  ']/td[2]/select')))
						   
						   'Ambil referantor category yang dipilih pada confins'
						   String textRefCategory = selectedRefCategory.getFirstSelectedOption().getText()
						   
						   if(!datafileReferantor.getValue(
							   GlobalVariable.NumofReferantor, 13).equalsIgnoreCase(textRefCategory) || !datafileReferantor.getValue(
							   GlobalVariable.NumofReferantor, 14).equalsIgnoreCase(WebUI.getAttribute(modifyObjectReferantorName,'value')) ){
							   if(countData==j){
								   'click button add'
								   WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/button_Add'))
				
								   variableData = driver.findElements(By.cssSelector('#accessoriesData > div.table-responsive > table > tbody > tr'))
								   countData = variableData.size()
								   add=1
							   }
						   }
						   else{
							   break
						   }
					}
					//jika pada confins tidak ada data referantor
					else if(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/TableReferantornodata'),FailureHandling.OPTIONAL),
							'NO DATA AVAILABLE', false, FailureHandling.OPTIONAL)){
						
						'click button add'
						WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/button_Add'))
						
						'select referantor category'
						WebUI.selectOptionByLabel(modifyObjectSelectReferantorCategory, datafileReferantor.getValue(
								GlobalVariable.NumofReferantor, 13), false, FailureHandling.OPTIONAL)
						
						'Ambil dan simpan nilai referantor category dari confins'
						refCategory = WebUI.getAttribute(modifyObjectSelectReferantorCategory, 'value')
						
						'click button referantor lookup'
						WebUI.click(modifyObjectButtonReferantor, FailureHandling.OPTIONAL)
						
						if(GlobalVariable.Role=="Testing"){
							if(refCategory!=""){
								'click button search'
								WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/Button_SearchReferantor'))
								
								'Cek total data referantor pada db'
								Integer countReferantor = CustomKeywords.'referantorData.checkReferantor.countReferantorLookup'(sqlConnection, refCategory, officeName)
								
								'Ambil nilai total data referantor pada lookup confins'
								String[] textTotalDataReferantor = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/label_TotalDataOfficer')).replace(
									' ', '').replace(':', ';').split(';')
						
								'Parsing nilai total data referantor confins ke integer(angka)'
								Integer totalDataReferantor = Integer.parseInt(textTotalDataReferantor[1])
						
								'Verif total data referantor confins sesuai dengan db'
								if(WebUI.verifyEqual(totalDataReferantor, countReferantor)==false){
									writeReasonFailedLookup()
								}
							}
						}
						
						'input referantor name'
						WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/input_Referantor Name_referantorName'),
							datafileReferantor.getValue(
								GlobalVariable.NumofReferantor, 14))
			
						'click button search'
						WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/Button_SearchReferantor'))
						
						'verify lookup'
						if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/a_Select'),
							 5, FailureHandling.OPTIONAL)) {
							 referantorCode = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/span_referantorCode'))
										
							 'click select'
							 WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/a_Select'))
						} else {
							 'click button x'
							 WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/button_X'))
						
							 'write to excel WARNING'
							 CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '5.TabReferantorData',
									 0, GlobalVariable.NumofReferantor - 1, GlobalVariable.StatusWarning)
										
							 'Write To Excel GlobalVariable.StatusReasonLookup'
							 CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '5.TabReferantorData',
									 1, GlobalVariable.NumofReferantor - 1, GlobalVariable.StatusReasonLookup)
							
							 'get referantor name'
							 referantornamebefore = WebUI.getAttribute(modifyObjectReferantorName, 'value', FailureHandling.OPTIONAL)
						   
							 'Click delete'
							 WebUI.click(modifyButtonDelete, FailureHandling.OPTIONAL)
							 
							 if(WebUI.verifyElementPresent(modifyButtonDelete, 5, FailureHandling.OPTIONAL)){
								 writeReasonFailedDelete()
							 }
							 continue
						 }
						 
						if(GlobalVariable.Role=="Testing"){
							'Pengecekan terdapat opsi ddl bank account selain select one'
							if(WebUI.getNumberOfTotalOption(modifySelectBankAccount)>1){
								
								'Arraylist untuk menampung ddl bank account dari db'
								ArrayList<String> BankAccount = new ArrayList<String>()
								
								'Ambil array teks bank account dari db'
								BankAccount = CustomKeywords.'referantorData.checkReferantor.checkBankAccountDDL'(sqlConnection, refCategory, officeName, referantorCode)
								
								'Verifikasi array teks bank account dari db sesuai dengan ddl yang tampil pada confins'
								if(WebUI.verifyOptionsPresent(modifySelectBankAccount, BankAccount)==false){
									writeReasonFailedDDL()
								}
								
								'Pengecekan referantor category yang dipilih customer atau agency'
								if(refCategory.equalsIgnoreCase("Customer")||refCategory.equalsIgnoreCase("Agency")){
									
									'Ambil teks default bank account dari db'
									String defaultBankAccount = CustomKeywords.'referantorData.checkReferantor.checkBankAccountDefault'(sqlConnection, refCategory, officeName, referantorCode)
									
									'Verifikasi opsi yang terpilih secara default pada confins sesuai dengan db'
									if(WebUI.verifyOptionSelectedByLabel(modifySelectBankAccount,"(?i)"+defaultBankAccount,true,2)==false){
										writeReasonFailedDDL()
									}
								}
							}
						}
						
						'select bank account'
						WebUI.selectOptionByIndex(modifySelectBankAccount, datafileReferantor.getValue(
								GlobalVariable.NumofReferantor, 16), FailureHandling.OPTIONAL)
			
						'call function gettext bankaccount'
						getTextBankAccount(('//*[@id="accessoriesData"]/div[2]/table/tbody/tr[' + j) + ']/td[5]/select')
						
						'select tax calculation method'
						WebUI.selectOptionByLabel(modifySelectTaxCalcualtion, datafileReferantor.getValue(
								GlobalVariable.NumofReferantor, 17), false, FailureHandling.OPTIONAL)
			
						'cek inputan mandatory apakah sudah terisi semua atau belum'
						if ((WebUI.verifyOptionSelectedByIndex(modifyObjectSelectReferantorCategory, 0, 3, FailureHandling.OPTIONAL) ||
						WebUI.verifyOptionSelectedByIndex(modifySelectBankAccount, 0, 3, FailureHandling.OPTIONAL)) || WebUI.verifyOptionSelectedByIndex(
							modifySelectTaxCalcualtion, 0, 3, FailureHandling.OPTIONAL)) {
							'write to excel WARNING'
							CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '5.TabReferantorData',
								0, GlobalVariable.NumofReferantor - 1, GlobalVariable.StatusWarning)
			
							'Write To Excel GlobalVariable.StatusReasonLookup'
							CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '5.TabReferantorData',
								1, GlobalVariable.NumofReferantor - 1, GlobalVariable.StatusReasonMandatoryEmpty)
							
							'Click delete'
							WebUI.click(modifyButtonDelete, FailureHandling.OPTIONAL)
							
							if(WebUI.verifyElementPresent(modifyButtonDelete, 5, FailureHandling.OPTIONAL)){
								writeReasonFailedDelete()
							}
							
							'get referantor name'
								referantornamebefore = WebUI.getAttribute(modifyObjectReferantorName, 'value', FailureHandling.OPTIONAL)
							  
								variable = driver.findElements(By.cssSelector('#accessoriesData > div.table-responsive > table > tbody > tr'))
								
								'Click delete'
								WebUI.click(modifyButtonDelete, FailureHandling.OPTIONAL)
								
								if(j == variable.size()){
									if(WebUI.verifyElementNotPresent(modifyObjectReferantorName, 5, FailureHandling.OPTIONAL)){
										continue
									}else{
									'add cust name failed kedalam array'
									referantorfaileddelete.add(referantornamebefore)
									}
								}else{
									'get cust name sebelum delete'
									referantornameafter = WebUI.getAttribute(modifyObjectReferantorName, 'value', FailureHandling.OPTIONAL)
								
									if(WebUI.verifyNotMatch(referantornameafter, referantornamebefore, false, FailureHandling.OPTIONAL)){
										continue
									}else{
										'add cust name failed kedalam array'
										referantorfaileddelete.add(referantornamebefore)
									}
								}
								j--
								continue
						}
					}
					
					'write to excel SUCCESS'
					CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '5.TabReferantorData',
						0, GlobalVariable.NumofReferantor - 1, GlobalVariable.StatusSuccess)
							  
				}
			}
	}
}

//Jika copy app no
if(datafileReferantor.getValue(
		GlobalVariable.CopyAppColm, 10).equalsIgnoreCase("No")){
	
	if (Integer.parseInt(GlobalVariable.CountofReferantor) >= 1) {
		
		int modifyObjectIndex = 1
		
		'looping referantor'
		for (GlobalVariable.NumofReferantor = 2; GlobalVariable.NumofReferantor <= (Integer.parseInt(GlobalVariable.CountofReferantor) +
		1); (GlobalVariable.NumofReferantor)++) {
			
			if (datafileReferantor.getValue(
				GlobalVariable.NumofReferantor, 12) == datafileCustomerPersonal.getValue(
				GlobalVariable.NumofColm, 13)) {
				'click button add'
				WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/button_Add'))
	
				'modify button referantor lookup'
				modifyObjectButtonReferantor = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/button_LookupReferantor'),
					'xpath', 'equals', ('//*[@id="accessoriesData"]/div[2]/table/tbody/tr[' + modifyObjectIndex) +
				']/td[3]/lib-uclookupgeneric/div/div/div[1]/span/button', true)
	
				'modify select referantor category'
				modifyObjectSelectReferantorCategory = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/select_ReferantorCategory'),
					'xpath', 'equals', ('//*[@id="accessoriesData"]/div[2]/table/tbody/tr[' + modifyObjectIndex) +
				']/td[2]/select', true)
	
				'modify select tax calculation'
				modifySelectTaxCalcualtion = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/select_TaxCalculationMethod'),
					'xpath', 'equals', ('//*[@id="accessoriesData"]/div[2]/table/tbody/tr[' + modifyObjectIndex) +
				']/td[6]/select', true)
	
				'modify select bank account'
				modifySelectBankAccount = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/select_BankAccount'),
					'xpath', 'equals', ('//*[@id="accessoriesData"]/div[2]/table/tbody/tr[' + modifyObjectIndex) + ']/td[5]/select', true)
	
				'modify button delete'
				modifyButtonDelete = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/Button Delete'),
					'xpath', 'equals', ('//*[@id="accessoriesData"]/div[2]/table/tbody/tr[' + modifyObjectIndex) + ']/td[9]/a/i', true)
	
				'select referantor category'
				WebUI.selectOptionByLabel(modifyObjectSelectReferantorCategory, datafileReferantor.getValue(
						GlobalVariable.NumofReferantor, 13), false, FailureHandling.OPTIONAL)
				
				'Ambil dan simpan nilai referantor category dari confins'
				String refCategory = WebUI.getAttribute(modifyObjectSelectReferantorCategory, 'value')
				
				'click button referantor lookup'
				WebUI.click(modifyObjectButtonReferantor, FailureHandling.OPTIONAL)
				
				if(GlobalVariable.Role=="Testing"){
					if(refCategory!=""){
						'click button search'
						WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/Button_SearchReferantor'))
						
						'Cek total data referantor pada db'
						Integer countReferantor = CustomKeywords.'referantorData.checkReferantor.countReferantorLookup'(sqlConnection, refCategory, officeName)
						
						'Ambil nilai total data referantor pada lookup confins'
						String[] textTotalDataReferantor = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/label_TotalDataOfficer')).replace(
							' ', '').replace(':', ';').split(';')
				
						'Parsing nilai total data referantor confins ke integer(angka)'
						Integer totalDataReferantor = Integer.parseInt(textTotalDataReferantor[1])
				
						'Verif total data referantor confins sesuai dengan db'
						if(WebUI.verifyEqual(totalDataReferantor, countReferantor)==false){
							writeReasonFailedLookup()
						}
					}
				}
				
				'input referantor name'
				WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/input_Referantor Name_referantorName'),
					datafileReferantor.getValue(
						GlobalVariable.NumofReferantor, 14))
	
				'click button search'
				WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/Button_SearchReferantor'))
	
				String referantorCode
	
				'verify lookup'
				if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/a_Select'),
					5, FailureHandling.OPTIONAL)) {
					referantorCode = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/span_referantorCode'))
					
					'click select'
					WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/a_Select'))
				} else {
					'click button x'
					WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/button_X'))
	
					'write to excel WARNING'
					CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '5.TabReferantorData',
						0, GlobalVariable.NumofReferantor - 1, GlobalVariable.StatusWarning)
					
					'Write To Excel GlobalVariable.StatusReasonLookup'
					CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '5.TabReferantorData',
						1, GlobalVariable.NumofReferantor - 1, GlobalVariable.StatusReasonLookup)
					
					'Click delete'
					WebUI.click(modifyButtonDelete, FailureHandling.OPTIONAL)
					
					if(WebUI.verifyElementPresent(modifyButtonDelete, 5, FailureHandling.OPTIONAL)){
						writeReasonFailedDelete()
					}
					continue
				}
				
				if(GlobalVariable.Role=="Testing"){
					'Pengecekan terdapat opsi ddl bank account selain select one'
					if(WebUI.getNumberOfTotalOption(modifySelectBankAccount)>1){
						
						'Arraylist untuk menampung ddl bank account dari db'
						ArrayList<String> BankAccount = new ArrayList<String>()
						
						'Ambil array teks bank account dari db'
						BankAccount = CustomKeywords.'referantorData.checkReferantor.checkBankAccountDDL'(sqlConnection, refCategory, officeName, referantorCode)
						
						'Verifikasi array teks bank account dari db sesuai dengan ddl yang tampil pada confins'
						if(WebUI.verifyOptionsPresent(modifySelectBankAccount, BankAccount)==false){
							writeReasonFailedDDL()
						}
						
						'Pengecekan referantor category yang dipilih customer atau agency'
						if(refCategory.equalsIgnoreCase("Customer")||refCategory.equalsIgnoreCase("Agency")){
							
							'Ambil teks default bank account dari db'
							String defaultBankAccount = CustomKeywords.'referantorData.checkReferantor.checkBankAccountDefault'(sqlConnection, refCategory, officeName, referantorCode)
							
							'Verifikasi opsi yang terpilih secara default pada confins sesuai dengan db'
							if(WebUI.verifyOptionSelectedByLabel(modifySelectBankAccount,"(?i)"+defaultBankAccount,true,2)==false){
								writeReasonFailedDDL()
							}
						}
					}
				}
				
				'select bank account'
				WebUI.selectOptionByIndex(modifySelectBankAccount, datafileReferantor.getValue(
						GlobalVariable.NumofReferantor, 16), FailureHandling.OPTIONAL)
	
				'call function gettext bankaccount'
				getTextBankAccount(('//*[@id="accessoriesData"]/div[2]/table/tbody/tr[' + modifyObjectIndex) + ']/td[5]/select')
				
				'select tax calculation method'
				WebUI.selectOptionByLabel(modifySelectTaxCalcualtion, datafileReferantor.getValue(
						GlobalVariable.NumofReferantor, 17), false, FailureHandling.OPTIONAL)
	
				'cek inputan mandatory apakah sudah terisi semua atau belum'
				if ((WebUI.verifyOptionSelectedByIndex(modifyObjectSelectReferantorCategory, 0, 3, FailureHandling.OPTIONAL) ||
				WebUI.verifyOptionSelectedByIndex(modifySelectBankAccount, 0, 3, FailureHandling.OPTIONAL)) || WebUI.verifyOptionSelectedByIndex(
					modifySelectTaxCalcualtion, 0, 3, FailureHandling.OPTIONAL)) {
					'write to excel WARNING'
					CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '5.TabReferantorData',
						0, GlobalVariable.NumofReferantor - 1, GlobalVariable.StatusWarning)
	
					'Write To Excel GlobalVariable.StatusReasonLookup'
					CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '5.TabReferantorData',
						1, GlobalVariable.NumofReferantor - 1, GlobalVariable.StatusReasonMandatoryEmpty)
					
					'Click delete'
					WebUI.click(modifyButtonDelete, FailureHandling.OPTIONAL)
					
					if(WebUI.verifyElementPresent(modifyButtonDelete, 5, FailureHandling.OPTIONAL)){
						writeReasonFailedDelete()
					}
					
					continue
				}
				
				//Testing
				if(GlobalVariable.Role=="Testing"){
					
					ArrayList<String> referantorDetail = new ArrayList<String>()
					String newButtonViewDetail = ('//*[@id="accessoriesData"]/div[2]/table/tbody/tr[' + modifyObjectIndex) + ']/td[8]/a/i'
					
					'modify button view detail'
					modifyButtonViewDetail = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/i_ViewDetail'),
						'xpath', 'equals', newButtonViewDetail, true)
		
					'click button view detail'
					WebUI.click(modifyButtonViewDetail)
		
					'Pengecekan referantor category (customer, agency, atau mf employee)'
					if (refCategory.equalsIgnoreCase('CUSTOMER')) {
						'pengecekan ke db dan simpan data-data detail referantor yang dibutuhkan dari db'
						 referantorDetail = CustomKeywords.'referantorData.checkReferantorDetail.checkCustomerReferantor'(
							sqlConnection, referantorCode)
		
					} else if (refCategory.equalsIgnoreCase('AGENCY')) {
						'Pengecekan ke db dan simpan data-data detail referantor yang dibutuhkan dari db'
						referantorDetail = CustomKeywords.'referantorData.checkReferantorDetail.checkAgencyReferantor'(
							sqlConnection, referantorCode)
		
					} else if(refCategory.equalsIgnoreCase('MF_EMP')){
						'Pengecekan ke db dan simpan data-data detail referantor yang dibutuhkan dari db'
						referantorDetail = CustomKeywords.'referantorData.checkReferantorDetail.checkMFEmployeeReferantor'(
							sqlConnection, referantorCode)
						
					}
					
					ArrayList <Boolean> arrayMatch = adddatatoarraylist(referantorDetail)
					
					'Jika nilai di confins ada yang tidak sesuai dengan db'
					if (arrayMatch.contains(false)) {
						writeToExcelTidakSesuaiDB()
						modifyObjectIndex++
						continue
					}
					
					'click x'
					WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/span_XViewDetail'))
				}
				
				'write to excel SUCCESS'
				CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '5.TabReferantorData',
					0, GlobalVariable.NumofReferantor - 1, GlobalVariable.StatusSuccess)
	
				modifyObjectIndex++
			}
		}
	}
}

'click button save'
WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/Button Save'))

Integer iscompleteMandatory = Integer.parseInt(datafileReferantor.getValue(GlobalVariable.CopyAppColm, 4))

if (iscompleteMandatory == 0 && GlobalVariable.FlagFailed==0) {
    'cek alert'
    GlobalVariable.FlagFailed = CustomKeywords.'checkSaveProcess.checkSaveProcess.checkAlert'(GlobalVariable.CopyAppColm, 
        '5.TabReferantorData')
}

if (WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/ApplicationCurrentStep')), 
    'REFERANTOR', false, FailureHandling.OPTIONAL)) {
    'click button cancel'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/button_Cancel'))
}

if (((GlobalVariable.Role == 'Testing') && (GlobalVariable.CheckVerifStoreDBPersonal == 'Yes')) && (GlobalVariable.FlagFailed == 
0)) {
    'call test case store db referantor data'
    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/TabReferantorDataStoreDBVerif'), 
        [:], FailureHandling.CONTINUE_ON_FAILURE)
}

def adddatatoarraylist(ArrayList<String> referantorDetail) {
    'Ambil dan simpan npwp no dari confins'
    String textNPWPNo = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/label_NPWPNo'))

    'Ambil dan simpan npwp name dari confins'
    String textNPWPName = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/label_NPWPName'))

    'Ambil dan simpan address dari confins'
    String textAddress = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/label_Address'))

    'ambil dan simpan rt dari confins'
    String textRT = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/label_RT')).replace(
        ' ', '')

    'ambil dan simpan rw dari confins'
    String textRW = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/label_RW')).replace(
        '/ ', '')

    'ambil dan simpan kelurahan dari confins'
    String textKelurahan = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/label_Kelurahan'))

    'ambil dan simpan kecamatan dari confins'
    String textKecamatan = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/label_Kecamatan'))

    'ambil dan simpan kota dari confins'
    String textCity = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/label_Kota'))

    'ambil dan simpan zipcode dari confins'
    String textZipcode = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/label_Zipcode'))

    'arraylist boolean untuk menampung hasil dari verififikasi data dari confins sesuai dengan db atau tidak (true atau false)'
    ArrayList<String> arrayMatch = new ArrayList<String>()

    arrayMatch.add(WebUI.verifyMatch(textNPWPNo, '(?i)' + (referantorDetail[0]), true, FailureHandling.OPTIONAL))

    arrayMatch.add(WebUI.verifyMatch(textNPWPName, '(?i)' + (referantorDetail[1]), true, FailureHandling.OPTIONAL))

    arrayMatch.add(WebUI.verifyMatch(textAddress, '(?i)' + (referantorDetail[2]), true, FailureHandling.OPTIONAL))

    arrayMatch.add(WebUI.verifyMatch(textRT, '(?i)' + (referantorDetail[3]), true, FailureHandling.OPTIONAL))

    arrayMatch.add(WebUI.verifyMatch(textRW, '(?i)' + (referantorDetail[4]), true, FailureHandling.OPTIONAL))

    arrayMatch.add(WebUI.verifyMatch(textKelurahan, '(?i)' + (referantorDetail[5]), true, FailureHandling.OPTIONAL))

    arrayMatch.add(WebUI.verifyMatch(textKecamatan, '(?i)' + (referantorDetail[6]), true, FailureHandling.OPTIONAL))

    arrayMatch.add(WebUI.verifyMatch(textCity, '(?i)' + (referantorDetail[7]), true, FailureHandling.OPTIONAL))

	arrayMatch.add(WebUI.verifyMatch(textZipcode, "(?i)"+referantorDetail[8], true, FailureHandling.OPTIONAL))
	
	return arrayMatch
}

public writeReasonFailedDelete(){
	'Write To Excel GlobalVariable.ReasonFailedDelete'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '5.TabReferantorData',
			1, GlobalVariable.NumofReferantor - 1, GlobalVariable.ReasonFailedDelete)
}

public writeReasonFailedLookup(){
	'Write To Excel GlobalVariable.StatusFailed'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '5.TabReferantorData',
		0, GlobalVariable.NumofReferantor - 1, GlobalVariable.StatusFailed)

	'Write To Excel GlobalVariable.ReasonFailedDataLookup'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '5.TabReferantorData',
		1, GlobalVariable.NumofReferantor, GlobalVariable.ReasonFailedDataLookup)
	
	GlobalVariable.FlagFailed=1
}

public writeReasonFailedDDL(){
	'Write To Excel GlobalVariable.StatusFailed'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '5.TabReferantorData',
		0, GlobalVariable.NumofReferantor - 1, GlobalVariable.StatusFailed)

	'Write To Excel GlobalVariable.ReasonFailedDataLookup'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '5.TabReferantorData',
		1, GlobalVariable.NumofReferantor, GlobalVariable.ReasonFailedDDL)
	
	GlobalVariable.FlagFailed=1
}

public writeToExcelTidakSesuaiDB(){
	'write to excel WARNING'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '5.TabReferantorData',
		0, GlobalVariable.NumofReferantor - 1, GlobalVariable.StatusWarning)
	
	'Write To Excel GlobalVariable.StatusReason'
	CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '5.TabReferantorData',
		1, GlobalVariable.NumofReferantor - 1, GlobalVariable.StatusReasonTidakSesuaiDB)

	'click cancel'
	WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabReferantorData/button_CancelViewDetail'))
}

public getTextBankAccount(String newSelectBankaccount){
	if(GlobalVariable.BankAccount == ''){
		Select selectBankAcc =  new Select(DriverFactory.getWebDriver().findElement(By.xpath(newSelectBankaccount)))
		'Ambil text bank account yang dipilih pada confins'
		GlobalVariable.BankAccount = selectBankAcc.getFirstSelectedOption().getText()
	}else{
		Select selectBankAcc =  new Select(DriverFactory.getWebDriver().findElement(By.xpath(newSelectBankaccount)))
		'Ambil text bank account yang dipilih pada confins'
		GlobalVariable.BankAccount = GlobalVariable.BankAccount+' - ' +selectBankAcc.getFirstSelectedOption().getText()
	}
}

public checkVerifyEqualOrMatch(Boolean isMatch){
	if(isMatch==false && GlobalVariable.FlagFailed==0){
		CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '5.TabReferantorData',
				0, GlobalVariable.NumofReferantor-1, GlobalVariable.StatusFailed)

		CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '5.TabReferantorData',
				1, GlobalVariable.NumofReferantor-1, GlobalVariable.ReasonFailedVerifyEqualOrMatch)

		GlobalVariable.FlagFailed=1
	}
}