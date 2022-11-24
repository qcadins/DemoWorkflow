import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
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

int modifyObjectIndex=1

'get data file path'
GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'(GlobalVariable.PathCompany)

'declare datafileTabAsset'
datafileTabAsset = findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData')

'declare datafileAccessories'
datafileAccessories = findTestData('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/Accessories')

GlobalVariable.AssetPrice = 0.00

GlobalVariable.TotalAccessoriesPrice = 0.00

WebDriver driver = DriverFactory.getWebDriver()

'Jika copy app edit'
if(datafileTabAsset.getValue(
	GlobalVariable.NumofColm, 10).equalsIgnoreCase("Edit")){

	'arraylist accessories name yang gagal'
	ArrayList <String> accessoriesnamefaileddelete = new ArrayList<>()

	ArrayList<WebElement> variable = driver.findElements(By.cssSelector('#accessoriesData > div.table-responsive > table > tbody > tr'))
	
	//Edit & Delete Acc, edit jika ada data pada excel dan confins yang sesuai, delete jika ada data pada confins tetapi tidak ada datanya pada excel
	'Pengecekan pada confins ada data accessories sebelumnya'
	if (WebUI.verifyNotMatch(WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/TableAccessoriesnodata'),FailureHandling.OPTIONAL),
		'NO DATA AVAILABLE', false, FailureHandling.OPTIONAL)) {
		'Looping confins accessories'
		for(int i =1;i<=variable.size();i++){
			
			'modify object supplier name'
			modifyObjectSupplierName = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_supplierAccName'),'xpath','equals',"//*[@id='accessoriesData']/div[2]/table/tbody/tr["+i+"]/td[2]/lib-uclookupgeneric/div/div/input",true)
			
			'modify object acc name'
			modifyObjectAccName = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_AccName'),'xpath','equals',"//*[@id='accessoriesData']/div[2]/table/tbody/tr["+i+"]/td[3]/lib-uclookupgeneric/div/div/input",true)
			
			'modify button supplier lookup'
			modifyObjectButtonSupplier = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/button_Lookup Supplier'),
						 'xpath', 'equals', ('//*[@id="accessoriesData"]/div[2]/table/tbody/tr[' + i) + ']/td[2]/lib-uclookupgeneric/div/div/div[1]/span/button', true)
			 
			'modify button accessories lookup'
			modifyObjectButtonAccessories = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/button_Lookup Accessories'),
						 'xpath', 'equals', ('//*[@id="accessoriesData"]/div[2]/table/tbody/tr[' + i) + ']/td[3]/lib-uclookupgeneric/div/div/div[1]/span/button', true)
			 
			'modify input accessories price amount'
			modifyObjectAccessoriesPrice = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_accessoryPriceAmt0'),
						 'xpath', 'equals', ('//*[@id="accessoryPriceAmt' + (i - 1)) + '"]', true)
			 
			'modify select security deposit type'
			modifyObjectDownPaymentType = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/select_-Select One- Amount  Percentage'),
						 'xpath', 'equals', ('//*[@id="accessoriesData"]/div[2]/table/tbody/tr[' + i) + ']/td[5]/select', true)
			 
			'modify input security deposit percentage'
			modifyObjectInputPercentage = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_AccessoryDownPaymentPrcnt0'),
						 'xpath', 'equals', ('//*[@id="AccessoryDownPaymentPrcnt' + (i - 1)) + '"]', true)
			 
			 'modify input security deposit amount'
			 modifyObjectInputAmount = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_AccessoryDownPaymentAmt0'),
						 'xpath', 'equals', ('//*[@id="AccessoryDownPaymentAmt' + (i - 1)) + '"]', true)
			 
			 'modify input note'
			 modifyObjectInputNoteAccessories = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/textarea_Note Accessories'),
						 'xpath', 'equals', ('//*[@id="accessoriesData"]/div[2]/table/tbody/tr[' + i) + ']/td[7]/textarea', true)
			 
			  'modify button delete'
			  modifyObjectButtonDelete = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/button_Delete'),
						 'xpath', 'equals', ('//*[@id="accessoriesData"]/div[2]/table/tbody/tr[' + i) + ']/td[8]/a/i', true)

			  'Looping excel datafile accessories'
			  for (GlobalVariable.NumofAccessories = 2; GlobalVariable.NumofAccessories <= (Integer.parseInt(GlobalVariable.CountofAccessoriesCompany) +
				  2); (GlobalVariable.NumofAccessories)++) {
			  
				  if (datafileAccessories.getValue(
					  GlobalVariable.NumofAccessories, 12).equalsIgnoreCase(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
						  GlobalVariable.NumofColm, 13))) {
					  'Pengecekan jika supplier name dan accessories name pada confins sesuai dengan excel'
					  if (WebUI.getAttribute(modifyObjectSupplierName,'value').equalsIgnoreCase(datafileAccessories.getValue(
						  GlobalVariable.NumofAccessories, 14)) && WebUI.getAttribute(modifyObjectAccName,'value').equalsIgnoreCase(datafileAccessories.getValue(
						  GlobalVariable.NumofAccessories, 16))) {
							  
						  'input accessories price'
						  WebUI.setText(modifyObjectAccessoriesPrice, datafileAccessories.getValue(
								  GlobalVariable.NumofAccessories, 17), FailureHandling.OPTIONAL)
				  
						  'select security deposit type'
						  WebUI.selectOptionByLabel(modifyObjectDownPaymentType, datafileAccessories.getValue(
								  GlobalVariable.NumofAccessories, 18), false, FailureHandling.OPTIONAL)
				  
						  if (datafileAccessories.getValue(
							  GlobalVariable.NumofAccessories, 18) == 'Percentage') {
							  'input security deposit percentage'
							  WebUI.sendKeys(modifyObjectInputPercentage, Keys.chord(Keys.CONTROL, 'a'), FailureHandling.OPTIONAL)
				  
							  WebUI.sendKeys(modifyObjectInputPercentage, Keys.chord(Keys.BACK_SPACE), FailureHandling.OPTIONAL)
				  
							  WebUI.sendKeys(modifyObjectInputPercentage, Keys.chord(Keys.HOME), FailureHandling.OPTIONAL)
				  
							  WebUI.sendKeys(modifyObjectInputPercentage, Keys.chord(Keys.RIGHT, datafileAccessories.getValue(
										  GlobalVariable.NumofAccessories, 19)), FailureHandling.OPTIONAL)
							  
						  } else if (datafileAccessories.getValue(
							  GlobalVariable.NumofAccessories, 18) == 'Amount') {
							  'input security deposit amount'
							  WebUI.setText(modifyObjectInputAmount, datafileAccessories.getValue(
									  GlobalVariable.NumofAccessories, 20), FailureHandling.OPTIONAL)
						  }
						  
						  'input accessories notes'
						  WebUI.setText(modifyObjectInputNoteAccessories, datafileAccessories.getValue(
								  GlobalVariable.NumofAccessories, 21), FailureHandling.OPTIONAL)
				  
						  if (((WebUI.getAttribute(modifyObjectAccessoriesPrice, 'value') == '') || (WebUI.getAttribute(modifyObjectInputPercentage,
							  'value') == '')) || (WebUI.getAttribute(modifyObjectInputAmount, 'value') == '')) {
							  'write to excel WARNING'
							  CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '7a.Accessories',
								  0, GlobalVariable.NumofAccessories - 1, GlobalVariable.StatusWarning)
							  
							  'Write To Excel GlobalVariable.StatusReasonLookup'
							  CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '7a.Accessories',
								  1, GlobalVariable.NumofAccessories - 1, GlobalVariable.StatusReasonMandatoryEmpty)
							  
							 'get accessories name'
							 accessoriesnamebefore = WebUI.getAttribute(modifyObjectAccName, 'value', FailureHandling.OPTIONAL)
							
							 'Click delete'
							 WebUI.click(modifyObjectButtonDelete, FailureHandling.OPTIONAL)

							 'click ok pada alert'
							 WebUI.acceptAlert(FailureHandling.OPTIONAL)

							 if(i == variable.size()){
								 if(WebUI.verifyElementNotPresent(modifyObjectAccName, 5, FailureHandling.OPTIONAL)){
									 variable = driver.findElements(By.cssSelector('#accessoriesData > div.table-responsive > table > tbody > tr'))
								 }else{
									'add accessories name failed kedalam array'
									accessoriesnamefaileddelete.add(accessoriesnamebefore)
									continue
								 }
							 }else{
									'get accessories name sebelum delete'
									accessoriesnameafter = WebUI.getAttribute(modifyObjectAccName, 'value', FailureHandling.OPTIONAL)
										
										if(WebUI.verifyNotMatch(accessoriesnameafter, accessoriesnamebefore, false, FailureHandling.OPTIONAL)){
											variable = driver.findElements(By.cssSelector('#accessoriesData > div.table-responsive > table > tbody > tr'))
										}else{
											'add accessories name failed kedalam array'
											accessoriesnamefaileddelete.add(accessoriesnamebefore)
											continue
										}
							 }
							 i--
				  
							 continue
						  }
							  if(GlobalVariable.RoleCompany=="Testing"){
								  NumberFormat decimalFormatAccessories = NumberFormat.getPercentInstance()
								  
								  def AccessoriesPrice = WebUI.getAttribute(modifyObjectAccessoriesPrice, 'value').split(',').join()
						  
								  def AccessoriesInputPrctg = WebUI.getAttribute(modifyObjectInputPercentage, 'value').replaceAll('\\s', '')
						  
								  def AccessoriesInputAmt = WebUI.getAttribute(modifyObjectInputAmount, 'value').split(',').join()
						  
								  BigDecimal BDAccessoriesPrice = Integer.parseInt(AccessoriesPrice)
						  
								  float floatBDAccessoriesInputPrctg = decimalFormatAccessories.parse(AccessoriesInputPrctg).floatValue()
						  
								  Number NumberBDAccessoriesInputPrctg = decimalFormatAccessories.parse(AccessoriesInputPrctg)
						  
								  BigDecimal BDAccessoriesInputAmt = Integer.parseInt(AccessoriesInputAmt)
						  
								  if (datafileAccessories.getValue(
									  GlobalVariable.NumofAccessories, 18) == 'Percentage') {
									  int multiplyAccessoriesPricexDownPaymentPrctg = BDAccessoriesPrice * NumberBDAccessoriesInputPrctg
						  
									  'verify securitydeposit value equal'
									  checkVerifyEqualOrMatch(WebUI.verifyEqual(multiplyAccessoriesPricexDownPaymentPrctg, BDAccessoriesInputAmt))
								  } else if (datafileAccessories.getValue(
									  GlobalVariable.NumofAccessories, 18) == 'Amount') {
									  float divideDownPaymentAmtAccessoriesPrice = BDAccessoriesInputAmt / BDAccessoriesPrice
						  
									  'verify securitydeposit value equal'
									  checkVerifyEqualOrMatch(WebUI.verifyEqual(divideDownPaymentAmtAccessoriesPrice, floatBDAccessoriesInputPrctg))
								  }
								  GlobalVariable.TotalAccessoriesPrice += BDAccessoriesPrice.doubleValue()
							  }
							  
						  'write to excel success'
						  CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '7a.Accessories', 0,
								  GlobalVariable.NumofAccessories - 1, GlobalVariable.StatusSuccess)
						  break
					  }
				  }
				  else{
						  if(GlobalVariable.NumofAccessories == (Integer.parseInt(GlobalVariable.CountofAccessoriesCompany)+2)){
							  'Jika pada confins accessories ada data seblumnya'
							  if (WebUI.verifyNotMatch(WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/TableAccessoriesnodata'),FailureHandling.OPTIONAL),
								  'NO DATA AVAILABLE', false, FailureHandling.OPTIONAL)){
								  
								   'get accessories name'
								 accessoriesnamebefore = WebUI.getAttribute(modifyObjectAccName, 'value', FailureHandling.OPTIONAL)
									
								 'Click delete'
								  WebUI.click(modifyObjectButtonDelete, FailureHandling.OPTIONAL)
								  
								  'click ok pada alert'
								  WebUI.acceptAlert(FailureHandling.OPTIONAL)
								  
								  if(i == variable.size()){
										if(WebUI.verifyElementNotPresent(modifyObjectAccName, 5, FailureHandling.OPTIONAL)){
												 variable = driver.findElements(By.cssSelector('#accessoriesData > div.table-responsive > table > tbody > tr'))
										}else{
											'add accessories name failed kedalam array'
											accessoriesnamefaileddelete.add(accessoriesnamebefore)
											continue
										}
								  }else{
										'get accessories name sebelum delete'
										accessoriesnameafter = WebUI.getAttribute(modifyObjectAccName, 'value', FailureHandling.OPTIONAL)
												
										if(WebUI.verifyNotMatch(accessoriesnameafter, accessoriesnamebefore, false, FailureHandling.OPTIONAL)){
												 variable = driver.findElements(By.cssSelector('#accessoriesData > div.table-responsive > table > tbody > tr'))
										}else{
											'add accessories name failed kedalam array'
											accessoriesnamefaileddelete.add(accessoriesnamebefore)
											continue
										}
								  }
								  i--
							  }else{
								  break
							  }
						  }
				  }
			  }
		}
	}
	
	if(accessoriesnamefaileddelete.size() > 0){
			CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath,
					'7a.Accessories', 0, GlobalVariable.CopyAppColm - 1, GlobalVariable.StatusWarning)
			
			CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath,
				 '7a.Accessories', 1, GlobalVariable.CopyAppColm - 1, GlobalVariable.ReasonFailedDelete + accessoriesnamefaileddelete)
			
			GlobalVariable.FlagWarning++
	}
		
	ArrayList<WebElement> variableData = driver.findElements(By.cssSelector('#accessoriesData > div.table-responsive > table > tbody > tr'))
	int countData = variableData.size()
	int add=0
	
	//Add Acc jika ada data pada excel, tetapi data tersebut tidak ditemukan pada confins
	'Looping excel accessories'
	for (GlobalVariable.NumofAccessories = 2; GlobalVariable.NumofAccessories <= (Integer.parseInt(GlobalVariable.CountofAccessoriesCompany) +
		2); (GlobalVariable.NumofAccessories)++) {
			
			if (datafileAccessories.getValue(
					  GlobalVariable.NumofAccessories, 12).equalsIgnoreCase(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
					  GlobalVariable.NumofColm, 13))) {
				'Looping confins accessories'
				for(int j = 1;j<=countData;j++){
	
					 'modify object supplier name'
					 modifyObjectSupplierName = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_supplierAccName'),'xpath','equals',"//*[@id='accessoriesData']/div[2]/table/tbody/tr["+j+"]/td[2]/lib-uclookupgeneric/div/div/input",true)
					 
					 'modify object acc name'
					 modifyObjectAccName = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_AccName'),'xpath','equals',"//*[@id='accessoriesData']/div[2]/table/tbody/tr["+j+"]/td[3]/lib-uclookupgeneric/div/div/input",true)
					 
					 'modify button supplier lookup'
					 modifyObjectButtonSupplier = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/button_Lookup Supplier'),
								  'xpath', 'equals', ('//*[@id="accessoriesData"]/div[2]/table/tbody/tr[' + j) + ']/td[2]/lib-uclookupgeneric/div/div/div[1]/span/button', true)
					  
					 'modify button accessories lookup'
					 modifyObjectButtonAccessories = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/button_Lookup Accessories'),
								  'xpath', 'equals', ('//*[@id="accessoriesData"]/div[2]/table/tbody/tr[' + j) + ']/td[3]/lib-uclookupgeneric/div/div/div[1]/span/button', true)
					  
					 'modify input accessories price amount'
					 modifyObjectAccessoriesPrice = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_accessoryPriceAmt0'),
								  'xpath', 'equals', ('//*[@id="accessoryPriceAmt' + (j - 1)) + '"]', true)
					  
					 'modify select security deposit type'
					 modifyObjectDownPaymentType = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/select_-Select One- Amount  Percentage'),
								  'xpath', 'equals', ('//*[@id="accessoriesData"]/div[2]/table/tbody/tr[' + j) + ']/td[5]/select', true)
					  
					 'modify input security deposit percentage'
					 modifyObjectInputPercentage = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_AccessoryDownPaymentPrcnt0'),
								  'xpath', 'equals', ('//*[@id="AccessoryDownPaymentPrcnt' + (j - 1)) + '"]', true)
					  
					 'modify input security deposit amount'
					  modifyObjectInputAmount = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_AccessoryDownPaymentAmt0'),
								  'xpath', 'equals', ('//*[@id="AccessoryDownPaymentAmt' + (j - 1)) + '"]', true)
					  
					 'modify input note'
					  modifyObjectInputNoteAccessories = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/textarea_Note Accessories'),
								  'xpath', 'equals', ('//*[@id="accessoriesData"]/div[2]/table/tbody/tr[' + j) + ']/td[7]/textarea', true)
					  
					 'modify button delete'
					   modifyObjectButtonDelete = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/button_Delete'),
								  'xpath', 'equals', ('//*[@id="accessoriesData"]/div[2]/table/tbody/tr[' + j) + ']/td[8]/a/i', true)
					'Pengecekan jika ada data accessories sebelumnya'
					if (WebUI.verifyNotMatch(WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/TableAccessoriesnodata'),FailureHandling.OPTIONAL),
						'NO DATA AVAILABLE', false, FailureHandling.OPTIONAL)){
						   if(add==1){
							   'click lookup supplier'
							   WebUI.click(modifyObjectButtonSupplier, FailureHandling.OPTIONAL)
					   
							   'input Supplier Code'
							   WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_SupplierCodeAccessories'),
								   datafileAccessories.getValue(
									   GlobalVariable.NumofAccessories, 13))
					   
							   'input supplier name'
							   WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_Supplier Name_supplierName'),
								   datafileAccessories.getValue(
									   GlobalVariable.NumofAccessories, 14))
					   
							   'click button search'
							   WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/button_Search (1)'))
					   
							   if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/a_Select'),
								   5, FailureHandling.OPTIONAL)) {
								   'click select'
								   WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/a_Select'))
							   } else {
								   'click button x'
								   WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/button_XAccessories'))
					   
								   CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '7a.Accessories',
									   0, GlobalVariable.NumofAccessories - 1, GlobalVariable.StatusWarning)
								   
								   'Write To Excel GlobalVariable.StatusReasonLookup'
								   CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '7a.Accessories',
									   1, GlobalVariable.NumofAccessories - 1, GlobalVariable.StatusReasonLookup)
					   
								   'click delete'
								   WebUI.click(modifyObjectButtonDelete, FailureHandling.OPTIONAL)
					   
								   'click ok pada alert'
								   WebUI.acceptAlert(FailureHandling.OPTIONAL)
								   
								   if(WebUI.verifyElementPresent(modifyObjectButtonDelete, 5, FailureHandling.OPTIONAL)){
									   'Write To Excel GlobalVariable.ReasonFailedDelete'
									   CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath,
										   '7a.Accessories', 1, GlobalVariable.NumofAccessories - 1, GlobalVariable.ReasonFailedDelete)
								   }
					   
								   continue
							   }
							   
							   'click button accessories lookup'
							   WebUI.click(modifyObjectButtonAccessories, FailureHandling.OPTIONAL)
					   
							   'input Accessories Code'
							   WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_AssetAccessoriesCode'),
								   datafileAccessories.getValue(
									   GlobalVariable.NumofAccessories, 15))
					   
							   'input accessories name'
							   WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_Asset Accessory Name_accessoryName'),
								   datafileAccessories.getValue(
									   GlobalVariable.NumofAccessories, 16))
					   
							   'click button search'
							   WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/button_Search (1)'))
					   
							   if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/a_Select'),
								   5, FailureHandling.OPTIONAL)) {
								   'click select'
								   WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/a_Select'))
							   } else {
								   'click button x'
								   WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/button_XAccessories'))
					   
								   CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '7a.Accessories',
									   0, GlobalVariable.NumofAccessories - 1, GlobalVariable.StatusWarning)
								   
								   'Write To Excel GlobalVariable.StatusReasonLookup'
								   CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '7a.Accessories',
									   1, GlobalVariable.NumofAccessories - 1, GlobalVariable.StatusReasonLookup)
					   
								   'click delete'
								   WebUI.click(modifyObjectButtonDelete, FailureHandling.OPTIONAL)
					   
								   'click ok pada alert'
								   WebUI.acceptAlert(FailureHandling.OPTIONAL)
								   
								   if(WebUI.verifyElementPresent(modifyObjectButtonDelete, 5, FailureHandling.OPTIONAL)){
									   'Write To Excel GlobalVariable.ReasonFailedDelete'
									   CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath,
										   '7a.Accessories', 1, GlobalVariable.NumofAccessories - 1, GlobalVariable.ReasonFailedDelete)
								   }
					   
								   continue
							   }
							   
							   'input accessories price'
							   WebUI.setText(modifyObjectAccessoriesPrice, datafileAccessories.getValue(
									   GlobalVariable.NumofAccessories, 17), FailureHandling.OPTIONAL)
					   
							   'select security deposit type'
							   WebUI.selectOptionByLabel(modifyObjectDownPaymentType, datafileAccessories.getValue(
									   GlobalVariable.NumofAccessories, 18), false, FailureHandling.OPTIONAL)
					   
							   if (datafileAccessories.getValue(
								   GlobalVariable.NumofAccessories, 18) == 'Percentage') {
								   'input security deposit percentage'
								   WebUI.sendKeys(modifyObjectInputPercentage, Keys.chord(Keys.CONTROL, 'a'), FailureHandling.OPTIONAL)
					   
								   WebUI.sendKeys(modifyObjectInputPercentage, Keys.chord(Keys.BACK_SPACE), FailureHandling.OPTIONAL)
					   
								   WebUI.sendKeys(modifyObjectInputPercentage, Keys.chord(Keys.HOME), FailureHandling.OPTIONAL)
					   
								   WebUI.sendKeys(modifyObjectInputPercentage, Keys.chord(Keys.RIGHT, datafileAccessories.getValue(
											   GlobalVariable.NumofAccessories, 19)), FailureHandling.OPTIONAL)
							   } else if (datafileAccessories.getValue(
								   GlobalVariable.NumofAccessories, 18) == 'Amount') {
								   'input security deposit amount'
								   WebUI.setText(modifyObjectInputAmount, datafileAccessories.getValue(
										   GlobalVariable.NumofAccessories, 20), FailureHandling.OPTIONAL)
							   }
							   
							   'input accessories notes'
							   WebUI.setText(modifyObjectInputNoteAccessories, datafileAccessories.getValue(
									   GlobalVariable.NumofAccessories, 21), FailureHandling.OPTIONAL)
					   
							   if (((WebUI.getAttribute(modifyObjectAccessoriesPrice, 'value') == '') || (WebUI.getAttribute(modifyObjectInputPercentage,
								   'value') == '')) || (WebUI.getAttribute(modifyObjectInputAmount, 'value') == '')) {
								   'write to excel WARNING'
								   CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '7a.Accessories',
									   0, GlobalVariable.NumofAccessories - 1, GlobalVariable.StatusWarning)
								   
								   'Write To Excel GlobalVariable.StatusReasonLookup'
								   CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '7a.Accessories',
									   1, GlobalVariable.NumofAccessories - 1, GlobalVariable.StatusReasonMandatoryEmpty)
								   
								   'click delete'
								   WebUI.click(modifyObjectButtonDelete, FailureHandling.OPTIONAL)
					   
								   'click ok pada alert'
								   WebUI.acceptAlert(FailureHandling.OPTIONAL)
								   
								   if(WebUI.verifyElementPresent(modifyObjectButtonDelete, 5, FailureHandling.OPTIONAL)){
									   'Write To Excel GlobalVariable.ReasonFailedDelete'
									   CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath,
										   '7a.Accessories', 1, GlobalVariable.NumofAccessories - 1, GlobalVariable.ReasonFailedDelete)
								   }
					   
								   continue
							   }
								 def AccPrice = WebUI.getAttribute(modifyObjectAccessoriesPrice, 'value').split(',').join()
								BigDecimal BDAccPrice = Integer.parseInt(AccPrice)
								GlobalVariable.TotalAccessoriesPrice += BDAccPrice.doubleValue()
								
							   'write to excel success'
							   CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '7a.Accessories', 0,
									   GlobalVariable.NumofAccessories - 1, GlobalVariable.StatusSuccess)
							   add=0
						   }
						   
						   if(!datafileAccessories.getValue(
							   GlobalVariable.NumofAccessories, 14).equalsIgnoreCase(WebUI.getAttribute(modifyObjectSupplierName,'value')) || !datafileAccessories.getValue(
							   GlobalVariable.NumofAccessories, 16).equalsIgnoreCase(WebUI.getAttribute(modifyObjectAccName,'value')) ){
							   if(countData==j){
								   'click button add'
								   WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/button_Add'))
				
								   variableData = driver.findElements(By.cssSelector('#accessoriesData > div.table-responsive > table > tbody > tr'))
								   countData = variableData.size()
								   add=1
							   }
						   }
						   else{
							   break
						   }
					}
						//pengecekan jika tidak ada data accesories sebelumnya
					else if(WebUI.verifyMatch(WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/TableAccessoriesnodata'),FailureHandling.OPTIONAL),
							'NO DATA AVAILABLE', false, FailureHandling.OPTIONAL)){
						
						'click button add'
						WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/button_Add'))
						
						'click lookup supplier'
						WebUI.click(modifyObjectButtonSupplier, FailureHandling.OPTIONAL)
				
						'input Supplier Code'
						WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_SupplierCodeAccessories'),
							datafileAccessories.getValue(
								GlobalVariable.NumofAccessories, 13))
				
						'input supplier name'
						WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_Supplier Name_supplierName'),
							datafileAccessories.getValue(
								GlobalVariable.NumofAccessories, 14))
				
						'click button search'
						WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/button_Search (1)'))
				
						if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/a_Select'),
							5, FailureHandling.OPTIONAL)) {
							'click select'
							WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/a_Select'))
						} else {
							'click button x'
							WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/button_XAccessories'))
				
							CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '7a.Accessories',
								0, GlobalVariable.NumofAccessories - 1, GlobalVariable.StatusWarning)
							
							'Write To Excel GlobalVariable.StatusReasonLookup'
							CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '7a.Accessories',
								1, GlobalVariable.NumofAccessories - 1, GlobalVariable.StatusReasonLookup)
				
							'click delete'
							WebUI.click(modifyObjectButtonDelete, FailureHandling.OPTIONAL)
				
							'click ok pada alert'
							WebUI.acceptAlert(FailureHandling.OPTIONAL)
							
							if(WebUI.verifyElementPresent(modifyObjectButtonDelete, 5, FailureHandling.OPTIONAL)){
								'Write To Excel GlobalVariable.ReasonFailedDelete'
								CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath,
									'7a.Accessories', 1, GlobalVariable.NumofAccessories - 1, GlobalVariable.ReasonFailedDelete)
							}
				
							continue
						}
						
						'click button accessories lookup'
						WebUI.click(modifyObjectButtonAccessories, FailureHandling.OPTIONAL)
				
						'input Accessories Code'
						WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_AssetAccessoriesCode'),
							datafileAccessories.getValue(
								GlobalVariable.NumofAccessories, 15))
				
						'input accessories name'
						WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_Asset Accessory Name_accessoryName'),
							datafileAccessories.getValue(
								GlobalVariable.NumofAccessories, 16))
				
						'click button search'
						WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/button_Search (1)'))
				
						if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/a_Select'),
							5, FailureHandling.OPTIONAL)) {
							'click select'
							WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/a_Select'))
						} else {
							'click button x'
							WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/button_XAccessories'))
				
							CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '7a.Accessories',
								0, GlobalVariable.NumofAccessories - 1, GlobalVariable.StatusWarning)
							
							'Write To Excel GlobalVariable.StatusReasonLookup'
							CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '7a.Accessories',
								1, GlobalVariable.NumofAccessories - 1, GlobalVariable.StatusReasonLookup)
				
							'click delete'
							WebUI.click(modifyObjectButtonDelete, FailureHandling.OPTIONAL)
				
							'click ok pada alert'
							WebUI.acceptAlert(FailureHandling.OPTIONAL)
							
							if(WebUI.verifyElementPresent(modifyObjectButtonDelete, 5, FailureHandling.OPTIONAL)){
								'Write To Excel GlobalVariable.ReasonFailedDelete'
								CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath,
									'7a.Accessories', 1, GlobalVariable.NumofAccessories - 1, GlobalVariable.ReasonFailedDelete)
							}
				
							continue
						}
						
						'input accessories price'
						WebUI.setText(modifyObjectAccessoriesPrice, datafileAccessories.getValue(
								GlobalVariable.NumofAccessories, 17), FailureHandling.OPTIONAL)
				
						'select security deposit type'
						WebUI.selectOptionByLabel(modifyObjectDownPaymentType, datafileAccessories.getValue(
								GlobalVariable.NumofAccessories, 18), false, FailureHandling.OPTIONAL)
				
						if (datafileAccessories.getValue(
							GlobalVariable.NumofAccessories, 18) == 'Percentage') {
							'input security deposit percentage'
							WebUI.sendKeys(modifyObjectInputPercentage, Keys.chord(Keys.CONTROL, 'a'), FailureHandling.OPTIONAL)
				
							WebUI.sendKeys(modifyObjectInputPercentage, Keys.chord(Keys.BACK_SPACE), FailureHandling.OPTIONAL)
				
							WebUI.sendKeys(modifyObjectInputPercentage, Keys.chord(Keys.HOME), FailureHandling.OPTIONAL)
				
							WebUI.sendKeys(modifyObjectInputPercentage, Keys.chord(Keys.RIGHT, datafileAccessories.getValue(
										GlobalVariable.NumofAccessories, 19)), FailureHandling.OPTIONAL)
						} else if (datafileAccessories.getValue(
							GlobalVariable.NumofAccessories, 18) == 'Amount') {
							'input security deposit amount'
							WebUI.setText(modifyObjectInputAmount, datafileAccessories.getValue(
									GlobalVariable.NumofAccessories, 20), FailureHandling.OPTIONAL)
						}
						
						'input accessories notes'
						WebUI.setText(modifyObjectInputNoteAccessories, datafileAccessories.getValue(
								GlobalVariable.NumofAccessories, 21), FailureHandling.OPTIONAL)
				
						if (((WebUI.getAttribute(modifyObjectAccessoriesPrice, 'value') == '') || (WebUI.getAttribute(modifyObjectInputPercentage,
							'value') == '')) || (WebUI.getAttribute(modifyObjectInputAmount, 'value') == '')) {
							'write to excel WARNING'
							CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '7a.Accessories',
								0, GlobalVariable.NumofAccessories - 1, GlobalVariable.StatusWarning)
							
							'Write To Excel GlobalVariable.StatusReasonLookup'
							CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '7a.Accessories',
								1, GlobalVariable.NumofAccessories - 1, GlobalVariable.StatusReasonMandatoryEmpty)
							
							'click delete'
							WebUI.click(modifyObjectButtonDelete, FailureHandling.OPTIONAL)
				
							'click ok pada alert'
							WebUI.acceptAlert(FailureHandling.OPTIONAL)
							
							if(WebUI.verifyElementPresent(modifyObjectButtonDelete, 5, FailureHandling.OPTIONAL)){
								'Write To Excel GlobalVariable.ReasonFailedDelete'
								CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath,
									'7a.Accessories', 1, GlobalVariable.NumofAccessories - 1, GlobalVariable.ReasonFailedDelete)
							}
				
							continue
						}
							
						if(GlobalVariable.RoleCompany=="Testing"){
								NumberFormat decimalFormatAccessories = NumberFormat.getPercentInstance()
								
								def AccessoriesPrice = WebUI.getAttribute(modifyObjectAccessoriesPrice, 'value').split(',').join()
						
								def AccessoriesInputPrctg = WebUI.getAttribute(modifyObjectInputPercentage, 'value').replaceAll('\\s', '')
						
								def AccessoriesInputAmt = WebUI.getAttribute(modifyObjectInputAmount, 'value').split(',').join()
						
								BigDecimal BDAccessoriesPrice = Integer.parseInt(AccessoriesPrice)
						
								float floatBDAccessoriesInputPrctg = decimalFormatAccessories.parse(AccessoriesInputPrctg).floatValue()
						
								Number NumberBDAccessoriesInputPrctg = decimalFormatAccessories.parse(AccessoriesInputPrctg)
						
								BigDecimal BDAccessoriesInputAmt = Integer.parseInt(AccessoriesInputAmt)
						
								if (datafileAccessories.getValue(
									GlobalVariable.NumofAccessories, 18) == 'Percentage') {
									int multiplyAccessoriesPricexDownPaymentPrctg = BDAccessoriesPrice * NumberBDAccessoriesInputPrctg
						
									'verify securitydeposit value equal'
									checkVerifyEqualOrMatch(WebUI.verifyEqual(multiplyAccessoriesPricexDownPaymentPrctg, BDAccessoriesInputAmt))
								} else if (datafileAccessories.getValue(
									GlobalVariable.NumofAccessories, 18) == 'Amount') {
									float divideDownPaymentAmtAccessoriesPrice = BDAccessoriesInputAmt / BDAccessoriesPrice
						
									'verify securitydeposit value equal'
									checkVerifyEqualOrMatch(WebUI.verifyEqual(divideDownPaymentAmtAccessoriesPrice, floatBDAccessoriesInputPrctg))
								}
								GlobalVariable.TotalAccessoriesPrice += BDAccessoriesPrice.doubleValue()
						}
						'write to excel success'
						CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '7a.Accessories', 0,
							GlobalVariable.NumofAccessories - 1, GlobalVariable.StatusSuccess)
						  
					}
				}
			}
	}
}
	
//Jika copy app no
else if(datafileTabAsset.getValue(
	GlobalVariable.NumofColm, 10).equalsIgnoreCase("No")){
	for (GlobalVariable.NumofAccessories = 2; GlobalVariable.NumofAccessories <= (Integer.parseInt(GlobalVariable.CountofAccessoriesCompany) +
	1); (GlobalVariable.NumofAccessories)++) {
		if (datafileAccessories.getValue(
			GlobalVariable.NumofAccessories, 12).equalsIgnoreCase(findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
				GlobalVariable.NumofColm, 13))) {
			'click button add'
			WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/button_Add'))
	
			'modify button supplier lookup'
			modifyObjectButtonSupplier = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/button_Lookup Supplier'),
				'xpath', 'equals', ('//*[@id="accessoriesData"]/div[2]/table/tbody/tr[' + modifyObjectIndex) + ']/td[2]/lib-uclookupgeneric/div/div/div[1]/span/button', true)
	
			'modify button accessories lookup'
			modifyObjectButtonAccessories = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/button_Lookup Accessories'),
				'xpath', 'equals', ('//*[@id="accessoriesData"]/div[2]/table/tbody/tr[' + modifyObjectIndex) + ']/td[3]/lib-uclookupgeneric/div/div/div[1]/span/button', true)
	
			'modify input accessories price amount'
			modifyObjectAccessoriesPrice = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_accessoryPriceAmt0'),
				'xpath', 'equals', ('//*[@id="accessoryPriceAmt' + (modifyObjectIndex - 1)) + '"]', true)
	
			'modify select security deposit type'
			modifyObjectDownPaymentType = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/select_-Select One- Amount  Percentage'),
				'xpath', 'equals', ('//*[@id="accessoriesData"]/div[2]/table/tbody/tr[' + modifyObjectIndex) + ']/td[5]/select', true)
	
			'modify input security deposit percentage'
			modifyObjectInputPercentage = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_AccessoryDownPaymentPrcnt0'),
				'xpath', 'equals', ('//*[@id="AccessoryDownPaymentPrcnt' + (modifyObjectIndex - 1)) + '"]', true)
	
			'modify input security deposit amount'
			modifyObjectInputAmount = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_AccessoryDownPaymentAmt0'),
				'xpath', 'equals', ('//*[@id="AccessoryDownPaymentAmt' + (modifyObjectIndex - 1)) + '"]', true)
	
			'modify input note'
			modifyObjectInputNoteAccessories = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/textarea_Note Accessories'),
				'xpath', 'equals', ('//*[@id="accessoriesData"]/div[2]/table/tbody/tr[' + modifyObjectIndex) + ']/td[7]/textarea', true)
	
			'modify button delete'
			modifyObjectButtonDelete = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/button_Delete'),
				'xpath', 'equals', ('//*[@id="accessoriesData"]/div[2]/table/tbody/tr[' + modifyObjectIndex) + ']/td[8]/a/i', true)
	
			'click lookup supplier'
			WebUI.click(modifyObjectButtonSupplier, FailureHandling.OPTIONAL)
	
			'input Supplier Code'
			WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_SupplierCodeAccessories'),
				datafileAccessories.getValue(
					GlobalVariable.NumofAccessories, 13))
	
			'input supplier name'
			WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_Supplier Name_supplierName'),
				datafileAccessories.getValue(
					GlobalVariable.NumofAccessories, 14))
	
			'click button search'
			WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/button_Search (1)'))
	
			if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/a_Select'),
				5, FailureHandling.OPTIONAL)) {
				'click select'
				WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/a_Select'))
			} else {
				'click button x'
				WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/button_XAccessories'))
	
				CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '7a.Accessories',
					0, GlobalVariable.NumofAccessories - 1, GlobalVariable.StatusWarning)
				
				'Write To Excel GlobalVariable.StatusReasonLookup'
				CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '7a.Accessories',
					1, GlobalVariable.NumofAccessories - 1, GlobalVariable.StatusReasonLookup)
	
				'click delete'
				WebUI.click(modifyObjectButtonDelete, FailureHandling.OPTIONAL)
	
				'click ok pada alert'
				WebUI.acceptAlert(FailureHandling.OPTIONAL)
				
				if(WebUI.verifyElementPresent(modifyObjectButtonDelete, 5, FailureHandling.OPTIONAL)){
					'Write To Excel GlobalVariable.ReasonFailedDelete'
					CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath,
							'7a.Accessories', 1, GlobalVariable.NumofAccessories - 1, GlobalVariable.ReasonFailedDelete)
				}
				continue
			}
			
			'click button accessories lookup'
			WebUI.click(modifyObjectButtonAccessories, FailureHandling.OPTIONAL)
	
			'input Accessories Code'
			WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_AssetAccessoriesCode'),
				datafileAccessories.getValue(
					GlobalVariable.NumofAccessories, 15))
	
			'input accessories name'
			WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_Asset Accessory Name_accessoryName'),
				datafileAccessories.getValue(
					GlobalVariable.NumofAccessories, 16))
	
			'click button search'
			WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/button_Search (1)'))
	
			if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/a_Select'),
				5, FailureHandling.OPTIONAL)) {
				'click select'
				WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/a_Select'))
			} else {
				'click button x'
				WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/button_XAccessories'))
	
				CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '7a.Accessories',
					0, GlobalVariable.NumofAccessories - 1, GlobalVariable.StatusWarning)
				
				'Write To Excel GlobalVariable.StatusReasonLookup'
				CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '7a.Accessories',
					1, GlobalVariable.NumofAccessories - 1, GlobalVariable.StatusReasonLookup)
	
				'click delete'
				WebUI.click(modifyObjectButtonDelete, FailureHandling.OPTIONAL)
	
				'click ok pada alert'
				WebUI.acceptAlert(FailureHandling.OPTIONAL)
	
				if(WebUI.verifyElementPresent(modifyObjectButtonDelete, 5, FailureHandling.OPTIONAL)){
					'Write To Excel GlobalVariable.ReasonFailedDelete'
					CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath,
							'7a.Accessories', 1, GlobalVariable.NumofAccessories - 1, GlobalVariable.ReasonFailedDelete)
				}

				continue
			}
			
			'input accessories price'
			WebUI.setText(modifyObjectAccessoriesPrice, datafileAccessories.getValue(
					GlobalVariable.NumofAccessories, 17), FailureHandling.OPTIONAL)
	
			'select security deposit type'
			WebUI.selectOptionByLabel(modifyObjectDownPaymentType, datafileAccessories.getValue(
					GlobalVariable.NumofAccessories, 18), false, FailureHandling.OPTIONAL)
	
			if (datafileAccessories.getValue(
				GlobalVariable.NumofAccessories, 18) == 'Percentage') {
				'input security deposit percentage'
				WebUI.sendKeys(modifyObjectInputPercentage, Keys.chord(Keys.CONTROL, 'a'), FailureHandling.OPTIONAL)
	
				WebUI.sendKeys(modifyObjectInputPercentage, Keys.chord(Keys.BACK_SPACE), FailureHandling.OPTIONAL)
	
				WebUI.sendKeys(modifyObjectInputPercentage, Keys.chord(Keys.HOME), FailureHandling.OPTIONAL)
	
				WebUI.sendKeys(modifyObjectInputPercentage, Keys.chord(Keys.RIGHT, datafileAccessories.getValue(
							GlobalVariable.NumofAccessories, 19)), FailureHandling.OPTIONAL)
			} else if (datafileAccessories.getValue(
				GlobalVariable.NumofAccessories, 18) == 'Amount') {
				'input security deposit amount'
				WebUI.setText(modifyObjectInputAmount, datafileAccessories.getValue(
						GlobalVariable.NumofAccessories, 20), FailureHandling.OPTIONAL)
			}
			
			'input accessories notes'
			WebUI.setText(modifyObjectInputNoteAccessories, datafileAccessories.getValue(
					GlobalVariable.NumofAccessories, 21), FailureHandling.OPTIONAL)
	
			if (((WebUI.getAttribute(modifyObjectAccessoriesPrice, 'value') == '') || (WebUI.getAttribute(modifyObjectInputPercentage,
				'value') == '')) || (WebUI.getAttribute(modifyObjectInputAmount, 'value') == '')) {
				'write to excel WARNING'
				CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '7a.Accessories',
					0, GlobalVariable.NumofAccessories - 1, GlobalVariable.StatusWarning)
				
				'Write To Excel GlobalVariable.StatusReasonLookup'
				CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '7a.Accessories',
					1, GlobalVariable.NumofAccessories - 1, GlobalVariable.StatusReasonMandatoryEmpty)
				
				'click delete'
				WebUI.click(modifyObjectButtonDelete, FailureHandling.OPTIONAL)
	
				'click ok pada alert'
				WebUI.acceptAlert(FailureHandling.OPTIONAL)
				
				if(WebUI.verifyElementPresent(modifyObjectButtonDelete, 5, FailureHandling.OPTIONAL)){
					'Write To Excel GlobalVariable.ReasonFailedDelete'
					CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath,
							'7a.Accessories', 1, GlobalVariable.NumofAccessories - 1, GlobalVariable.ReasonFailedDelete)
				}

				continue
			}
			
			modifyObjectIndex++
	
			if(GlobalVariable.RoleCompany=="Testing"){
				NumberFormat decimalFormatAccessories = NumberFormat.getPercentInstance()
				
				def AccessoriesPrice = WebUI.getAttribute(modifyObjectAccessoriesPrice, 'value').split(',').join()
		
				def AccessoriesInputPrctg = WebUI.getAttribute(modifyObjectInputPercentage, 'value').replaceAll('\\s', '')
		
				def AccessoriesInputAmt = WebUI.getAttribute(modifyObjectInputAmount, 'value').split(',').join()
		
				BigDecimal BDAccessoriesPrice = Integer.parseInt(AccessoriesPrice)
		
				float floatBDAccessoriesInputPrctg = decimalFormatAccessories.parse(AccessoriesInputPrctg).floatValue()
		
				Number NumberBDAccessoriesInputPrctg = decimalFormatAccessories.parse(AccessoriesInputPrctg)
		
				BigDecimal BDAccessoriesInputAmt = Integer.parseInt(AccessoriesInputAmt)
		
				if (datafileAccessories.getValue(
					GlobalVariable.NumofAccessories, 18) == 'Percentage') {
					int multiplyAccessoriesPricexDownPaymentPrctg = BDAccessoriesPrice * NumberBDAccessoriesInputPrctg
		
					'verify securitydeposit value equal'
					checkVerifyEqualOrMatch(WebUI.verifyEqual(multiplyAccessoriesPricexDownPaymentPrctg, BDAccessoriesInputAmt))
				} else if (datafileAccessories.getValue(
					GlobalVariable.NumofAccessories, 18) == 'Amount') {
					float divideDownPaymentAmtAccessoriesPrice = BDAccessoriesInputAmt / BDAccessoriesPrice
		
					'verify securitydeposit value equal'
					checkVerifyEqualOrMatch(WebUI.verifyEqual(divideDownPaymentAmtAccessoriesPrice, floatBDAccessoriesInputPrctg))
				}
				GlobalVariable.TotalAccessoriesPrice += BDAccessoriesPrice.doubleValue()
			}
			
			'write to excel success'
			CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '7a.Accessories', 0,
				GlobalVariable.NumofAccessories - 1, GlobalVariable.StatusSuccess)
		}
	}
}
	
public checkVerifyEqualOrMatch(Boolean isMatch){
	if(isMatch==false && GlobalVariable.FlagFailed==0){
			CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '7a.Accessories',
					0, GlobalVariable.NumofAccessories-1, GlobalVariable.StatusFailed)
	
			CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '7a.Accessories',
					1, GlobalVariable.NumofAccessories-1, GlobalVariable.ReasonFailedVerifyEqualOrMatch)
	
			GlobalVariable.FlagFailed=1
	}
}
