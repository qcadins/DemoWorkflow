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
import org.openqa.selenium.support.ui.Select

import groovy.sql.Sql as Sql

int modifyObjectIndex=1

'get data file path'
GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'("\\Excel\\"+ GlobalVariable.LOB +"\\2.2 DataFile_NAP_"+ GlobalVariable.LOB +"_Company.xlsx")

'declare excelPathTabAsset'
excelPathTabAsset = 'NAP-'+ GlobalVariable.LOB +'-CustomerCompany/NAP2-ApplicationData/TabAssetData'

'declare excelPathAccessories'
excelPathAccessories = 'NAP-'+ GlobalVariable.LOB +'-CustomerCompany/NAP2-ApplicationData/Accessories'

'declare excelPathCustomerCompany'
excelPathCustomerCompany = 'NAP-'+ GlobalVariable.LOB +'-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData'

countAccessories = findTestData(excelPathAccessories).getColumnNumbers()

WebDriver driver = DriverFactory.getWebDriver()

'Looping untuk mencari nilai colm yang menunjukkan colm appno'
for (GlobalVariable.NumofAccessories = 2; GlobalVariable.NumofAccessories <= (findTestData(excelPathAccessories).getColumnNumbers() - 1); (GlobalVariable.NumofAccessories)++) {
	if (findTestData(excelPathAccessories).getValue(GlobalVariable.NumofAccessories, 12) == findTestData(excelPathCustomerCompany).getValue(GlobalVariable.NumofColm, 13)) {
			GlobalVariable.StartIndex = GlobalVariable.NumofAccessories
			break
	}
}

'Jika copy app edit'
if(findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofColm, 10).equalsIgnoreCase("Edit")){

	'arraylist accessories name yang gagal'
	ArrayList <String> accessoriesnamefaileddelete = new ArrayList<>()

	ArrayList<WebElement> variable = driver.findElements(By.cssSelector('#accessoriesData > div.table-responsive > table > tbody > tr'))
	
	//Edit & Delete Acc, edit jika ada data pada excel dan confins yang sesuai, delete jika ada data pada confins tetapi tidak ada datanya pada excel
	'Pengecekan pada confins ada data accessories sebelumnya'
	if (WebUI.verifyNotMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/TableAccessoriesnodata'),FailureHandling.OPTIONAL),
		'NO DATA AVAILABLE', false, FailureHandling.OPTIONAL)) {
		'Looping confins accessories'
		for(int i =1;i<=variable.size();i++){
			
			if (WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/TableAccessoriesnodata'),FailureHandling.OPTIONAL),
				'NO DATA AVAILABLE', false, FailureHandling.OPTIONAL)){
				break
			}
				
			'modify object supplier name'
			modifyObjectSupplierName = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_supplierAccName'),'xpath','equals',"//*[@id='accessoriesData']/div[2]/table/tbody/tr["+i+"]/td[2]/lib-uclookupgeneric/div/div/input",true)
			
			'modify object acc name'
			modifyObjectAccName = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_AccName'),'xpath','equals',"//*[@id='accessoriesData']/div[2]/table/tbody/tr["+i+"]/td[3]/lib-uclookupgeneric/div/div/input",true)
			
			'modify button supplier lookup'
			modifyObjectButtonSupplier = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/button_Lookup Supplier'),
						 'xpath', 'equals', ('//*[@id="accessoriesData"]/div[2]/table/tbody/tr[' + i) + ']/td[2]/lib-uclookupgeneric/div/div/div[1]/span/button', true)
			 
			'modify button accessories lookup'
			modifyObjectButtonAccessories = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/button_Lookup Accessories-CF4W'),
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

			  Select selectedDPType = new Select(DriverFactory.getWebDriver().findElement(By.xpath(('//*[@id="accessoriesData"]/div[2]/table/tbody/tr[' + i) + ']/td[5]/select')))
			   
			  'Ambil text DP Type yang dipilih pada confins'
			  String textDPType = selectedDPType.getFirstSelectedOption().getText()
			   
			  'Looping excel datafile accessories'
			  for (GlobalVariable.NumofAccessories = 2; GlobalVariable.NumofAccessories <= (countAccessories - 1); (GlobalVariable.NumofAccessories)++) {
			  
				  if (findTestData(excelPathAccessories).getValue(GlobalVariable.NumofAccessories, 12).equalsIgnoreCase(findTestData(excelPathCustomerCompany).getValue(
						  GlobalVariable.NumofColm, 13))) {
					  
					  if ((WebUI.getAttribute(modifyObjectSupplierName,'value').equalsIgnoreCase(findTestData(excelPathAccessories).getValue(
						  GlobalVariable.NumofAccessories, 14)) && WebUI.getAttribute(modifyObjectAccName,'value').equalsIgnoreCase(findTestData(excelPathAccessories).getValue(
						  GlobalVariable.NumofAccessories, 16)) && WebUI.getAttribute(modifyObjectAccessoriesPrice,'value').equalsIgnoreCase(findTestData(excelPathAccessories).getValue(
						  GlobalVariable.NumofAccessories, 17)) && textDPType.equalsIgnoreCase(findTestData(excelPathAccessories).getValue(
						  GlobalVariable.NumofAccessories, 18)) && WebUI.getAttribute(modifyObjectInputNoteAccessories,'value').equalsIgnoreCase(findTestData(excelPathAccessories).getValue(
						  GlobalVariable.NumofAccessories, 21))) && (WebUI.getAttribute(modifyObjectInputPercentage,'value').replace(' %','').equalsIgnoreCase(findTestData(excelPathAccessories).getValue(
						  GlobalVariable.NumofAccessories, 19)) || WebUI.getAttribute(modifyObjectInputAmount,'value').equalsIgnoreCase(findTestData(excelPathAccessories).getValue(
						  GlobalVariable.NumofAccessories, 20)))) {
						  break
					  }
						  
					  'Pengecekan jika supplier name dan accessories name pada confins sesuai dengan excel'
					  if (WebUI.getAttribute(modifyObjectSupplierName,'value').equalsIgnoreCase(findTestData(excelPathAccessories).getValue(
						  GlobalVariable.NumofAccessories, 14)) && WebUI.getAttribute(modifyObjectAccName,'value').equalsIgnoreCase(findTestData(excelPathAccessories).getValue(
						  GlobalVariable.NumofAccessories, 16))) {
							  
						  'input accessories price'
						  WebUI.setText(modifyObjectAccessoriesPrice, findTestData(excelPathAccessories).getValue(
								  GlobalVariable.NumofAccessories, 17), FailureHandling.OPTIONAL)
				  
						  'select security deposit type'
						  WebUI.selectOptionByLabel(modifyObjectDownPaymentType, findTestData(excelPathAccessories).getValue(
								  GlobalVariable.NumofAccessories, 18), false, FailureHandling.OPTIONAL)
				  
						  if (findTestData(excelPathAccessories).getValue(GlobalVariable.NumofAccessories, 18) == 'Percentage') {
							  'input security deposit percentage'
							  WebUI.sendKeys(modifyObjectInputPercentage, Keys.chord(Keys.CONTROL, 'a'), FailureHandling.OPTIONAL)
				  
							  WebUI.sendKeys(modifyObjectInputPercentage, Keys.chord(Keys.BACK_SPACE), FailureHandling.OPTIONAL)
				  
							  WebUI.sendKeys(modifyObjectInputPercentage, Keys.chord(Keys.HOME), FailureHandling.OPTIONAL)
				  
							  WebUI.sendKeys(modifyObjectInputPercentage, Keys.chord(Keys.RIGHT, findTestData(excelPathAccessories).getValue(
										  GlobalVariable.NumofAccessories, 19)), FailureHandling.OPTIONAL)
							  
						  } else if (findTestData(excelPathAccessories).getValue(GlobalVariable.NumofAccessories, 18) == 'Amount') {
							  'input security deposit amount'
							  WebUI.setText(modifyObjectInputAmount, findTestData(excelPathAccessories).getValue(
									  GlobalVariable.NumofAccessories, 20), FailureHandling.OPTIONAL)
						  }
						  
						  'input accessories notes'
						  WebUI.setText(modifyObjectInputNoteAccessories, findTestData(excelPathAccessories).getValue(
								  GlobalVariable.NumofAccessories, 21), FailureHandling.OPTIONAL)
				  
						  if (((WebUI.getAttribute(modifyObjectAccessoriesPrice, 'value') == '') || (WebUI.getAttribute(modifyObjectInputPercentage,
							  'value') == '')) || (WebUI.getAttribute(modifyObjectInputAmount, 'value') == '')) {
							  							  
							  'Write To Excel GlobalVariable.StatusWarning and GlobalVariable.StatusReasonMandatoryEmpty'
							  CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('7a.Accessories', GlobalVariable.NumofAccessories, GlobalVariable.StatusWarning, GlobalVariable.StatusReasonMandatoryEmpty)
							  
							 'get accessories name'
							 accessoriesnamebefore = WebUI.getAttribute(modifyObjectAccName, 'value', FailureHandling.OPTIONAL)
							
							 'Click delete'
							 WebUI.click(modifyObjectButtonDelete, FailureHandling.OPTIONAL)

							 'click ok pada alert'
							 WebUI.acceptAlert(FailureHandling.OPTIONAL)

							 if(i == variable.size()){
								 if(WebUI.verifyElementNotPresent(modifyObjectAccName, GlobalVariable.TimeOut, FailureHandling.OPTIONAL)){
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
						  }

						  'call function count accessries DP'						
						  countAccessoriesDP()
							  
						  'write to excel success'
						  CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '7a.Accessories', 0,
								  GlobalVariable.NumofAccessories - 1, GlobalVariable.StatusSuccess)
						  break
					  }
				  else{
					if(findTestData(excelPathAccessories).getValue(GlobalVariable.NumofAccessories + 1, 12) != findTestData(excelPathCustomerCompany).getValue(
						  GlobalVariable.NumofColm, 13)){
							  'Jika pada confins accessories ada data seblumnya'
							  if (WebUI.verifyNotMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/TableAccessoriesnodata'),FailureHandling.OPTIONAL),
								  'NO DATA AVAILABLE', false, FailureHandling.OPTIONAL)){
								  
								   'get accessories name'
								   accessoriesnamebefore = WebUI.getAttribute(modifyObjectAccName, 'value', FailureHandling.OPTIONAL)
									
								 'Click delete'
								  WebUI.click(modifyObjectButtonDelete, FailureHandling.OPTIONAL)
								  
								  'click ok pada alert'
								  WebUI.acceptAlert(FailureHandling.OPTIONAL)
								  
								  if(i == variable.size()){
										if(WebUI.verifyElementPresent(modifyObjectAccName, GlobalVariable.TimeOut, FailureHandling.OPTIONAL)){
											'add accessories name failed kedalam array'
											accessoriesnamefaileddelete.add(accessoriesnamebefore)
										}
								  }else{
										'get accessories name sebelum delete'
										accessoriesnameafter = WebUI.getAttribute(modifyObjectAccName, 'value', FailureHandling.OPTIONAL)
												
										if(WebUI.verifyMatch(accessoriesnameafter, accessoriesnamebefore, false, FailureHandling.OPTIONAL)){
											'add accessories name failed kedalam array'
											accessoriesnamefaileddelete.add(accessoriesnamebefore)
										}
								  }
								 
								  'count table accessories setelah delete'
								  variable = driver.findElements(By.cssSelector('#accessoriesData > div.table-responsive > table > tbody > tr'))
								 
								  i--
							  }else{
								  break
							  }
				  }
				  }
				  }else{
					  break
				  }
			  }
			  
			  if(i == variable.size() && findTestData(excelPathAccessories).getValue(GlobalVariable.NumofAccessories+1, 12) != findTestData(excelPathCustomerCompany).getValue(GlobalVariable.NumofColm, 13)){
				  break
			  }
				  
			'count table accessories setelah delete'
			variableData = DriverFactory.getWebDriver().findElements(By.xpath('//*[@id="accessoriesData"]/div[2]/table/tbody/tr/td'))
			
			if(variableData.size() == 1){
				break
			}
		}
	}
	
	if(accessoriesnamefaileddelete.size() > 0){
			
			'Write To Excel GlobalVariable.StatusWarning and GlobalVariable.ReasonFailedDelete'
			CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('7a.Accessories', GlobalVariable.NumofAccessories, GlobalVariable.StatusWarning, findTestData(excelPathAccessories).getValue(GlobalVariable.NumofAccessories, 2) + ';' + GlobalVariable.ReasonFailedDelete + accessoriesnamefaileddelete)
			
			GlobalVariable.FlagWarning++
	}
		
	ArrayList<WebElement> variableData = driver.findElements(By.cssSelector('#accessoriesData > div.table-responsive > table > tbody > tr'))
	int countData = variableData.size()
	int add=0
	
	//Add Acc jika ada data pada excel, tetapi data tersebut tidak ditemukan pada confins
	'Looping excel accessories'
	for (GlobalVariable.NumofAccessories = 2; GlobalVariable.NumofAccessories <= (countAccessories - 1); (GlobalVariable.NumofAccessories)++) {
			
			if (findTestData(excelPathAccessories).getValue(GlobalVariable.NumofAccessories, 12).equalsIgnoreCase(findTestData(excelPathCustomerCompany).getValue(
					  GlobalVariable.NumofColm, 13))) {
				'Looping confins accessories'
				for(int j = 1;j<=countData;j++){
	
					 'modify object supplier name'
					 modifyObjectSupplierName = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_supplierAccName'),'xpath','equals',"//*[@id='accessoriesData']/div[2]/table/tbody/tr["+j+"]/td[2]/lib-uclookupgeneric/div/div/input",true)
					 
					 'modify object acc name'
					 modifyObjectAccName = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_AccName'),'xpath','equals',"//*[@id='accessoriesData']/div[2]/table/tbody/tr["+j+"]/td[3]/lib-uclookupgeneric/div/div/input",true)
					 
					 'modify button supplier lookup'
					 modifyObjectButtonSupplier = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/button_Lookup Supplier'),
								  'xpath', 'equals', ('//*[@id="accessoriesData"]/div[2]/table/tbody/tr[' + j) + ']/td[2]/lib-uclookupgeneric/div/div/div[1]/span/button', true)
					  
					 'modify button accessories lookup'
					 modifyObjectButtonAccessories = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/button_Lookup Accessories-CF4W'),
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
					if (WebUI.verifyNotMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/TableAccessoriesnodata'),FailureHandling.OPTIONAL),
						'NO DATA AVAILABLE', false, FailureHandling.OPTIONAL)){
						   if(add==1){
							   'click lookup supplier'
							   WebUI.click(modifyObjectButtonSupplier, FailureHandling.OPTIONAL)
					   
							   'input Supplier Code'
							   WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_SupplierCodeAccessories'),
								   findTestData(excelPathAccessories).getValue(
									   GlobalVariable.NumofAccessories, 13))
					   
							   'input supplier name'
							   WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_Supplier Name_supplierName'),
								   findTestData(excelPathAccessories).getValue(
									   GlobalVariable.NumofAccessories, 14))
					   
							   'click button search'
							   WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/button_Search (1)'))
					   
							   if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/a_Select'),
								  GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
								   'click select'
								   WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/a_Select'))
							   } else {
								   'click button x'
								   WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/button_XAccessories'))
					   
								   'Write To Excel GlobalVariable.StatusWarning and GlobalVariable.StatusReasonLookup'
								   CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('7a.Accessories', GlobalVariable.NumofAccessories, GlobalVariable.StatusWarning, GlobalVariable.StatusReasonLookup)
					   
								   'click delete'
								   WebUI.click(modifyObjectButtonDelete, FailureHandling.OPTIONAL)
					   
								   'click ok pada alert'
								   WebUI.acceptAlert(FailureHandling.OPTIONAL)
								   
								   if(WebUI.verifyElementPresent(modifyObjectButtonDelete, GlobalVariable.TimeOut, FailureHandling.OPTIONAL)){
									   'Write To Excel GlobalVariable.ReasonFailedDelete'
									   CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath,
										   '7a.Accessories', 1, GlobalVariable.NumofAccessories - 1, findTestData(excelPathAccessories).getValue(GlobalVariable.NumofAccessories, 2) + ';' + GlobalVariable.ReasonFailedDelete)
								   }
					   
								   continue
							   }
							   
							   'click button accessories lookup'
							   WebUI.click(modifyObjectButtonAccessories, FailureHandling.OPTIONAL)
					   
							   'input Accessories Code'
							   WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_AssetAccessoriesCode'),
								   findTestData(excelPathAccessories).getValue(
									   GlobalVariable.NumofAccessories, 15))
					   
							   'input accessories name'
							   WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_Asset Accessory Name_accessoryName'),
								   findTestData(excelPathAccessories).getValue(
									   GlobalVariable.NumofAccessories, 16))
					   
							   'click button search'
							   WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/button_Search (1)'))
					   
							   if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/a_Select'),
								   GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
								   'click select'
								   WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/a_Select'))
							   } else {
								   'click button x'
								   WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/button_XAccessories'))
					   								   
								   'Write To Excel GlobalVariable.StatusWarning and GlobalVariable.StatusReasonLookup'
								   CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('7a.Accessories', GlobalVariable.NumofAccessories, GlobalVariable.StatusWarning, GlobalVariable.StatusReasonLookup)
					   
								   'click delete'
								   WebUI.click(modifyObjectButtonDelete, FailureHandling.OPTIONAL)
					   
								   'click ok pada alert'
								   WebUI.acceptAlert(FailureHandling.OPTIONAL)
								   
								   if(WebUI.verifyElementPresent(modifyObjectButtonDelete, GlobalVariable.TimeOut, FailureHandling.OPTIONAL)){
									   'Write To Excel GlobalVariable.ReasonFailedDelete'
									   CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath,
										   '7a.Accessories', 1, GlobalVariable.NumofAccessories - 1, findTestData(excelPathAccessories).getValue(GlobalVariable.NumofAccessories, 2) + ';' + GlobalVariable.ReasonFailedDelete)
								   }
					   
								   continue
							   }
							   
							   'input accessories price'
							   WebUI.setText(modifyObjectAccessoriesPrice, findTestData(excelPathAccessories).getValue(
									   GlobalVariable.NumofAccessories, 17), FailureHandling.OPTIONAL)
					   
							   'select security deposit type'
							   WebUI.selectOptionByLabel(modifyObjectDownPaymentType, findTestData(excelPathAccessories).getValue(
									   GlobalVariable.NumofAccessories, 18), false, FailureHandling.OPTIONAL)
					   
							   if (findTestData(excelPathAccessories).getValue(
								   GlobalVariable.NumofAccessories, 18) == 'Percentage') {
								   'input security deposit percentage'
								   WebUI.sendKeys(modifyObjectInputPercentage, Keys.chord(Keys.CONTROL, 'a'), FailureHandling.OPTIONAL)
					   
								   WebUI.sendKeys(modifyObjectInputPercentage, Keys.chord(Keys.BACK_SPACE), FailureHandling.OPTIONAL)
					   
								   WebUI.sendKeys(modifyObjectInputPercentage, Keys.chord(Keys.HOME), FailureHandling.OPTIONAL)
					   
								   WebUI.sendKeys(modifyObjectInputPercentage, Keys.chord(Keys.RIGHT, findTestData(excelPathAccessories).getValue(
											   GlobalVariable.NumofAccessories, 19)), FailureHandling.OPTIONAL)
							   } else if (findTestData(excelPathAccessories).getValue(
								   GlobalVariable.NumofAccessories, 18) == 'Amount') {
								   'input security deposit amount'
								   WebUI.setText(modifyObjectInputAmount, findTestData(excelPathAccessories).getValue(
										   GlobalVariable.NumofAccessories, 20), FailureHandling.OPTIONAL)
							   }
							   
							   'input accessories notes'
							   WebUI.setText(modifyObjectInputNoteAccessories, findTestData(excelPathAccessories).getValue(
									   GlobalVariable.NumofAccessories, 21), FailureHandling.OPTIONAL)
					   
							   if (((WebUI.getAttribute(modifyObjectAccessoriesPrice, 'value') == '') || (WebUI.getAttribute(modifyObjectInputPercentage,
								   'value') == '')) || (WebUI.getAttribute(modifyObjectInputAmount, 'value') == '')) {
								   
								   'Write To Excel GlobalVariable.StatusWarning and GlobalVariable.StatusReasonMandatoryEmpty'
								   CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('7a.Accessories', GlobalVariable.NumofAccessories, GlobalVariable.StatusWarning, GlobalVariable.StatusReasonMandatoryEmpty)
								   
								   'click delete'
								   WebUI.click(modifyObjectButtonDelete, FailureHandling.OPTIONAL)
					   
								   'click ok pada alert'
								   WebUI.acceptAlert(FailureHandling.OPTIONAL)
								   
								   if(WebUI.verifyElementPresent(modifyObjectButtonDelete, GlobalVariable.TimeOut, FailureHandling.OPTIONAL)){
									   'Write To Excel GlobalVariable.ReasonFailedDelete'
									   CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath,
										   '7a.Accessories', 1, GlobalVariable.NumofAccessories - 1, findTestData(excelPathAccessories).getValue(GlobalVariable.NumofAccessories, 2) + ';' + GlobalVariable.ReasonFailedDelete)
								   }
					   
								   continue
							   }
								 								
							   'write to excel success'
							   CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '7a.Accessories', 0,
									   GlobalVariable.NumofAccessories - 1, GlobalVariable.StatusSuccess)
							   add=0
						   }
						   
						   if(!findTestData(excelPathAccessories).getValue(
							   GlobalVariable.NumofAccessories, 14).equalsIgnoreCase(WebUI.getAttribute(modifyObjectSupplierName,'value')) || !findTestData(excelPathAccessories).getValue(
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
					else if(WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/TableAccessoriesnodata'),FailureHandling.OPTIONAL),
							'NO DATA AVAILABLE', false, FailureHandling.OPTIONAL)){
						
						'click button add'
						WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/button_Add'))
						
						'click lookup supplier'
						WebUI.click(modifyObjectButtonSupplier, FailureHandling.OPTIONAL)
				
						'input Supplier Code'
						WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_SupplierCodeAccessories'),
							findTestData(excelPathAccessories).getValue(
								GlobalVariable.NumofAccessories, 13))
				
						'input supplier name'
						WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_Supplier Name_supplierName'),
							findTestData(excelPathAccessories).getValue(
								GlobalVariable.NumofAccessories, 14))
				
						'click button search'
						WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/button_Search (1)'))
				
						if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/a_Select'),
							GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
							'click select'
							WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/a_Select'))
						} else {
							'click button x'
							WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/button_XAccessories'))
				
							'Write To Excel GlobalVariable.StatusWarning and GlobalVariable.StatusReasonLookup'
							CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('7a.Accessories', GlobalVariable.NumofAccessories, GlobalVariable.StatusWarning, GlobalVariable.StatusReasonLookup)
				
							'click delete'
							WebUI.click(modifyObjectButtonDelete, FailureHandling.OPTIONAL)
				
							'click ok pada alert'
							WebUI.acceptAlert(FailureHandling.OPTIONAL)
							
							if(WebUI.verifyElementPresent(modifyObjectButtonDelete, GlobalVariable.TimeOut, FailureHandling.OPTIONAL)){
								'Write To Excel GlobalVariable.ReasonFailedDelete'
								CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath,
									'7a.Accessories', 1, GlobalVariable.NumofAccessories - 1, findTestData(excelPathAccessories).getValue(GlobalVariable.NumofAccessories, 2) + ';' + GlobalVariable.ReasonFailedDelete)
							}
				
							continue
						}
						
						'click button accessories lookup'
						WebUI.click(modifyObjectButtonAccessories, FailureHandling.OPTIONAL)
				
						'input Accessories Code'
						WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_AssetAccessoriesCode'),
							findTestData(excelPathAccessories).getValue(
								GlobalVariable.NumofAccessories, 15))
				
						'input accessories name'
						WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_Asset Accessory Name_accessoryName'),
							findTestData(excelPathAccessories).getValue(
								GlobalVariable.NumofAccessories, 16))
				
						'click button search'
						WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/button_Search (1)'))
				
						if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/a_Select'),
							GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
							'click select'
							WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/a_Select'))
						} else {
							'click button x'
							WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/button_XAccessories'))
				
							'Write To Excel GlobalVariable.StatusWarning and GlobalVariable.StatusReasonLookup'
							CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('7a.Accessories', GlobalVariable.NumofAccessories, GlobalVariable.StatusWarning, GlobalVariable.StatusReasonLookup)
				
							'click delete'
							WebUI.click(modifyObjectButtonDelete, FailureHandling.OPTIONAL)
				
							'click ok pada alert'
							WebUI.acceptAlert(FailureHandling.OPTIONAL)
							
							if(WebUI.verifyElementPresent(modifyObjectButtonDelete, GlobalVariable.TimeOut, FailureHandling.OPTIONAL)){
								'Write To Excel GlobalVariable.ReasonFailedDelete'
								CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath,
									'7a.Accessories', 1, GlobalVariable.NumofAccessories - 1, findTestData(excelPathAccessories).getValue(GlobalVariable.NumofAccessories, 2) + ';' + GlobalVariable.ReasonFailedDelete)
							}
				
							continue
						}
						
						'input accessories price'
						WebUI.setText(modifyObjectAccessoriesPrice, findTestData(excelPathAccessories).getValue(
								GlobalVariable.NumofAccessories, 17), FailureHandling.OPTIONAL)
				
						'select security deposit type'
						WebUI.selectOptionByLabel(modifyObjectDownPaymentType, findTestData(excelPathAccessories).getValue(
								GlobalVariable.NumofAccessories, 18), false, FailureHandling.OPTIONAL)
				
						if (findTestData(excelPathAccessories).getValue(
							GlobalVariable.NumofAccessories, 18) == 'Percentage') {
							'input security deposit percentage'
							WebUI.sendKeys(modifyObjectInputPercentage, Keys.chord(Keys.CONTROL, 'a'), FailureHandling.OPTIONAL)
				
							WebUI.sendKeys(modifyObjectInputPercentage, Keys.chord(Keys.BACK_SPACE), FailureHandling.OPTIONAL)
				
							WebUI.sendKeys(modifyObjectInputPercentage, Keys.chord(Keys.HOME), FailureHandling.OPTIONAL)
				
							WebUI.sendKeys(modifyObjectInputPercentage, Keys.chord(Keys.RIGHT, findTestData(excelPathAccessories).getValue(
										GlobalVariable.NumofAccessories, 19)), FailureHandling.OPTIONAL)
						} else if (findTestData(excelPathAccessories).getValue(
							GlobalVariable.NumofAccessories, 18) == 'Amount') {
							'input security deposit amount'
							WebUI.setText(modifyObjectInputAmount, findTestData(excelPathAccessories).getValue(
									GlobalVariable.NumofAccessories, 20), FailureHandling.OPTIONAL)
						}
						
						'input accessories notes'
						WebUI.setText(modifyObjectInputNoteAccessories, findTestData(excelPathAccessories).getValue(
								GlobalVariable.NumofAccessories, 21), FailureHandling.OPTIONAL)
				
						if (((WebUI.getAttribute(modifyObjectAccessoriesPrice, 'value') == '') || (WebUI.getAttribute(modifyObjectInputPercentage,
							'value') == '')) || (WebUI.getAttribute(modifyObjectInputAmount, 'value') == '')) {
														
							'Write To Excel GlobalVariable.StatusWarning and GlobalVariable.StatusReasonMandatoryEmpty'
							CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('7a.Accessories', GlobalVariable.NumofAccessories, GlobalVariable.StatusWarning, GlobalVariable.StatusReasonMandatoryEmpty)
							
							'click delete'
							WebUI.click(modifyObjectButtonDelete, FailureHandling.OPTIONAL)
				
							'click ok pada alert'
							WebUI.acceptAlert(FailureHandling.OPTIONAL)
							
							if(WebUI.verifyElementPresent(modifyObjectButtonDelete, GlobalVariable.TimeOut, FailureHandling.OPTIONAL)){
								'Write To Excel GlobalVariable.ReasonFailedDelete'
								CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath,
									'7a.Accessories', 1, GlobalVariable.NumofAccessories - 1, findTestData(excelPathAccessories).getValue(GlobalVariable.NumofAccessories, 2) + ';' + GlobalVariable.ReasonFailedDelete)
							}
				
							continue
						}
							
						'call function count accessries DP'
						countAccessoriesDP()
							
						'write to excel success'
						CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '7a.Accessories', 0,
							GlobalVariable.NumofAccessories - 1, GlobalVariable.StatusSuccess)
						  
					}
				}
			}else{
			break
			}
	}
}
	
//Jika copy app no
else if(findTestData(excelPathTabAsset).getValue(GlobalVariable.NumofColm, 10).equalsIgnoreCase("No")){
	for (GlobalVariable.NumofAccessories = 2; GlobalVariable.NumofAccessories <= (countAccessories - 1); (GlobalVariable.NumofAccessories)++) {
		if (findTestData(excelPathAccessories).getValue(
			GlobalVariable.NumofAccessories, 12).equalsIgnoreCase(findTestData(excelPathCustomerCompany).getValue(
				GlobalVariable.NumofColm, 13))) {
			'click button add'
			WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/button_Add'))
	
			'modify button supplier lookup'
			modifyObjectButtonSupplier = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/button_Lookup Supplier'),
				'xpath', 'equals', ('//*[@id="accessoriesData"]/div[2]/table/tbody/tr[' + modifyObjectIndex) + ']/td[2]/lib-uclookupgeneric/div/div/div[1]/span/button', true)
	
			'modify button accessories lookup'
			modifyObjectButtonAccessories = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/button_Lookup Accessories-CF4W'),
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
				findTestData(excelPathAccessories).getValue(
					GlobalVariable.NumofAccessories, 13))
	
			'input supplier name'
			WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_Supplier Name_supplierName'),
				findTestData(excelPathAccessories).getValue(
					GlobalVariable.NumofAccessories, 14))
	
			'click button search'
			WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/button_Search (1)'))
	
			if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/a_Select'),
				GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
				'click select'
				WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/a_Select'))
			} else {
				'click button x'
				WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/button_XAccessories'))
		
				'Write To Excel GlobalVariable.StatusWarning and GlobalVariable.StatusReasonLookup'
				CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('7a.Accessories', GlobalVariable.NumofAccessories, GlobalVariable.StatusWarning, GlobalVariable.StatusReasonLookup)
				
				'click delete'
				WebUI.click(modifyObjectButtonDelete, FailureHandling.OPTIONAL)
	
				'click ok pada alert'
				WebUI.acceptAlert(FailureHandling.OPTIONAL)
				
				if(WebUI.verifyElementPresent(modifyObjectButtonDelete, GlobalVariable.TimeOut, FailureHandling.OPTIONAL)){
					'Write To Excel GlobalVariable.ReasonFailedDelete'
					CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath,
							'7a.Accessories', 1, GlobalVariable.NumofAccessories - 1, findTestData(excelPathAccessories).getValue(GlobalVariable.NumofAccessories, 2) + ';' + GlobalVariable.ReasonFailedDelete)
				}
				continue
			}
			
			'click button accessories lookup'
			WebUI.click(modifyObjectButtonAccessories, FailureHandling.OPTIONAL)
	
			'input Accessories Code'
			WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_AssetAccessoriesCode'),
				findTestData(excelPathAccessories).getValue(
					GlobalVariable.NumofAccessories, 15))
	
			'input accessories name'
			WebUI.setText(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/input_Asset Accessory Name_accessoryName'),
				findTestData(excelPathAccessories).getValue(
					GlobalVariable.NumofAccessories, 16))
	
			'click button search'
			WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/button_Search (1)'))
	
			if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/a_Select'),
				GlobalVariable.TimeOut, FailureHandling.OPTIONAL)) {
				'click select'
				WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/a_Select'))
			} else {
				'click button x'
				WebUI.click(findTestObject('NAP-CF4W-CustomerCompany/NAP2-ApplicationData/TabAssetData/button_XAccessories'))
		
				'Write To Excel GlobalVariable.StatusWarning and GlobalVariable.StatusReasonLookup'
				CustomKeywords.'customizeKeyword.writeExcel.writeToExcelStatusReason'('7a.Accessories', GlobalVariable.NumofAccessories, GlobalVariable.StatusWarning, GlobalVariable.StatusReasonLookup)
				
				'click delete'
				WebUI.click(modifyObjectButtonDelete, FailureHandling.OPTIONAL)
	
				'click ok pada alert'
				WebUI.acceptAlert(FailureHandling.OPTIONAL)
	
				if(WebUI.verifyElementPresent(modifyObjectButtonDelete, GlobalVariable.TimeOut, FailureHandling.OPTIONAL)){
					'Write To Excel GlobalVariable.ReasonFailedDelete'
					CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath,
							'7a.Accessories', 1, GlobalVariable.NumofAccessories - 1, findTestData(excelPathAccessories).getValue(GlobalVariable.NumofAccessories, 2) + ';' + GlobalVariable.ReasonFailedDelete)
				}

				continue
			}
			
			'input accessories price'
			WebUI.setText(modifyObjectAccessoriesPrice, findTestData(excelPathAccessories).getValue(
					GlobalVariable.NumofAccessories, 17), FailureHandling.OPTIONAL)
	
			'select security deposit type'
			WebUI.selectOptionByLabel(modifyObjectDownPaymentType, findTestData(excelPathAccessories).getValue(
					GlobalVariable.NumofAccessories, 18), false, FailureHandling.OPTIONAL)
	
			if (findTestData(excelPathAccessories).getValue(
				GlobalVariable.NumofAccessories, 18) == 'Percentage') {
				'input security deposit percentage'
				WebUI.sendKeys(modifyObjectInputPercentage, Keys.chord(Keys.CONTROL, 'a'), FailureHandling.OPTIONAL)
	
				WebUI.sendKeys(modifyObjectInputPercentage, Keys.chord(Keys.BACK_SPACE), FailureHandling.OPTIONAL)
	
				WebUI.sendKeys(modifyObjectInputPercentage, Keys.chord(Keys.HOME), FailureHandling.OPTIONAL)
	
				WebUI.sendKeys(modifyObjectInputPercentage, Keys.chord(Keys.RIGHT, findTestData(excelPathAccessories).getValue(
							GlobalVariable.NumofAccessories, 19)), FailureHandling.OPTIONAL)
			} else if (findTestData(excelPathAccessories).getValue(
				GlobalVariable.NumofAccessories, 18) == 'Amount') {
				'input security deposit amount'
				WebUI.setText(modifyObjectInputAmount, findTestData(excelPathAccessories).getValue(
						GlobalVariable.NumofAccessories, 20), FailureHandling.OPTIONAL)
			}
			
			'input accessories notes'
			WebUI.setText(modifyObjectInputNoteAccessories, findTestData(excelPathAccessories).getValue(
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
				
				if(WebUI.verifyElementPresent(modifyObjectButtonDelete, GlobalVariable.TimeOut, FailureHandling.OPTIONAL)){
					'Write To Excel GlobalVariable.ReasonFailedDelete'
					CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath,
							'7a.Accessories', 1, GlobalVariable.NumofAccessories - 1, findTestData(excelPathAccessories).getValue(GlobalVariable.NumofAccessories, 2) + ';' + GlobalVariable.ReasonFailedDelete)
				}

				continue
			}
			
			modifyObjectIndex++
	
			'call function count accessries DP'
			countAccessoriesDP()
			
			'write to excel success'
			CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '7a.Accessories', 0,
				GlobalVariable.NumofAccessories - 1, GlobalVariable.StatusSuccess)
		}
	}
}
	
public checkVerifyEqualOrMatch(Boolean isMatch){
	if(isMatch==false && GlobalVariable.FlagFailed==0){
			'write to excel status failed'
			CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '7a.Accessories',
					0, GlobalVariable.NumofAccessories-1, GlobalVariable.StatusFailed)
	
			'write to excel verify equal or match'
			CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '7a.Accessories',
					1, GlobalVariable.NumofAccessories-1, findTestData(excelPathAccessories).getValue(GlobalVariable.NumofAccessories, 2) + ';' + GlobalVariable.ReasonFailedVerifyEqualOrMatch)
	
			GlobalVariable.FlagFailed=1
	}
}

def countAccessoriesDP(){
	if(GlobalVariable.RoleCompany=="Testing"){
		'get attribute accessories price dari confins'
		AccessoriesPrice = WebUI.getAttribute(modifyObjectAccessoriesPrice, 'value').split(',').join()
						  
		'convert accessories price > integer'
		BigDecimal BDAccessoriesPrice = Integer.parseInt(AccessoriesPrice)
								  
						  if (findTestData(excelPathAccessories).getValue(GlobalVariable.NumofAccessories, 18) == 'Percentage') {
				  
							  'get attribute accessories input amount dari confins'
							  AccessoriesInputAmt = WebUI.getAttribute(modifyObjectInputAmount, 'value')
							  
							  'get value accessories dari excel'
							  AccessoriesAmountExcel = findTestData(excelPathAccessories).getValue(GlobalVariable.NumofAccessories, 24)
							  
							  'verify securitydeposit value equal'
							  checkVerifyEqualOrMatch(WebUI.verifyMatch(AccessoriesInputAmt, AccessoriesAmountExcel, false))
							  
						  } else if (findTestData(excelPathAccessories).getValue(GlobalVariable.NumofAccessories, 18) == 'Amount') {
				  
							  'get attribute accessories input percentage dari confins'
							  AccessoriesInputPrctg = WebUI.getAttribute(modifyObjectInputPercentage, 'value').replace(' %', '')

							  'get value accessories value dari excel'
							  AccessoriesPrctgExcel = findTestData(excelPathAccessories).getValue(GlobalVariable.NumofAccessories, 23)
							  
							  'verify securitydeposit value equal'
							  checkVerifyEqualOrMatch(WebUI.verifyEqual(Math.round(Double.parseDouble(AccessoriesInputPrctg)), Math.round(Double.parseDouble(AccessoriesPrctgExcel))))
						  }
	}
}