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
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.WebElement as WebElement
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.By as By
import groovy.sql.Sql as Sql
import org.codehaus.groovy.ast.stmt.ContinueStatement

if(WebUI.verifyElementNotVisible(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabCommissionData/a_Commission Reserved Fund'),FailureHandling.OPTIONAL)){
	
	'click menu Consumer finance 4w'
	WebUI.click(findTestObject('Object Repository/LoginR3BranchManagerSuperuser/a_Consumer Finance'))
	
}


'Klik Commission Reserved Fund'
WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabCommissionData/a_Commission Reserved Fund'))

WebUI.delay(5)
'Input Appno'
WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabCommissionData/input_Application No_AppNoId'), 
    findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
        GlobalVariable.NumofColm, 3))
//Unit Testing dilakukan dengan menggunakan app no '0002APP20211201243'

'Klik Search'
WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabCommissionData/button_Search'))

'Klik Select'
WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabCommissionData/i_Select'))

WebUI.delay(5)

'Assign directori file excel ke global variabel'
String userDir = System.getProperty('user.dir')

'Assign directori file excel ke global variabel'
String filePath = userDir + GlobalVariable.PathPersonal

'Assign directori file excel ke global variabel'
GlobalVariable.DataFilePath = filePath

'Koneksi database'
String servername = findTestData('Login/Login').getValue(1, 8)
String instancename = findTestData('Login/Login').getValue(2, 8)
String username = findTestData('Login/Login').getValue(3, 8)
String password = findTestData('Login/Login').getValue(4, 8)
String database = findTestData('Login/Login').getValue(5, 8)
String driverclassname = findTestData('Login/Login').getValue(6, 8)
String url = servername+';instanceName='+instancename+';databaseName='+database
Sql sqlConnection = CustomKeywords.'dbconnection.connectDB.connect'(url, username,password,driverclassname)

'Inisialisasi driver'
WebDriver driver = DriverFactory.getWebDriver()

'Arraylist untuk menampung Income Information'
ArrayList<WebElement> varIncomeInfo = driver.findElements(By.cssSelector("#viewIncomeInfo label"))

'Inisialisasi Variabel untuk menghitung jumlah baris pada Income Information, dibagi 2 karena countincomeinfo menghitung label beserta amountnya, sedangkan yang dibutuhkan untuk dihitung/dicount adalah labelnya'
int countIncomeInfo = varIncomeInfo.size()/2

'Mengambil nilai allocation type dari excel'
allocationType = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/CommissionReservedFund/TabCommissionData').getValue(GlobalVariable.NumofColm, 2)

'Select dropdownlist allocation type'
WebUI.selectOptionByLabel(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabCommissionData/select_AmountPercentage'), 
    allocationType, false, FailureHandling.OPTIONAL)

//Supplier
'Arraylist untuk menampung List Supplier'
ArrayList<WebElement> variableSupp = driver.findElements(By.cssSelector('#formInformationSupplier h4'))

'Pengecekan Jika terdapat minimal 1 List Supplier'
if(variableSupp.size()>0){
	
	'Mengambil nilai supplier name dari web confins'
	String supplierName = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabCommissionData/label_SupplierName'))
	
	'Mengambil nilai supplier name yang akan didelete dari excel'
	String deleteSupp = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/CommissionReservedFund/TabCommissionData').getValue(GlobalVariable.NumofColm, 4)
	
	'Pengecekan jika supplier name pada confins sama dengan supplier name yang akan didelete'
	if (supplierName.equalsIgnoreCase(deleteSupp)) {
		'Click icon delete (tempat sampah)'
		WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabCommissionData/i_deletesupp'))
		'Click OK pada alert'
		WebUI.acceptAlert()
	} else {
		String supplierCode
		
		'Select dropdownlist bank account pada supplier'
		WebUI.selectOptionByIndex(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabCommissionData/select_BankAccountSupplier'),
			findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/CommissionReservedFund/TabCommissionData').getValue(GlobalVariable.NumofColm, 5), FailureHandling.OPTIONAL)
		
		'Query Select pada database untuk mengambil nilai supplier code'
		supplierCode = CustomKeywords.'dbconnection.checkCommissionCode.checkSupplierCode'(sqlConnection, supplierName)
	
		'Inisialisasi variabel Arraylist untuk menampung allocate commission from sebelah kiri'
		ArrayList<WebElement> variableAllocateCommissionLeft  = driver.findElements(By.cssSelector(('#ListAllocated' + supplierCode) + ' > div > div > div:nth-child(1) label.col-md-4.adInsLabel'))
	
		'Inisialisasi variabel Arraylist untuk menampung allocate commission from sebelah kanan'
		ArrayList<WebElement> variableAllocateCommissionRight  = driver.findElements(By.cssSelector(('#ListAllocated' + supplierCode) + ' > div > div > div:nth-child(2) label.col-md-4.adInsLabel'))
	
		'Menghitung count (size dari variabel) sebelah kiri'
		int countAllocateCommissionLeft  = variableAllocateCommissionLeft.size()
		
		
		
		'Menghitung count (size dari variabel) sebelah kanan'
		int countAllocateCommissionRight  = variableAllocateCommissionRight.size()
		
		
		
		'Menghitung count (size dari variabel) gabungan kiri dan kanan'
		int countAllocateCommission  = countAllocateCommissionLeft  + countAllocateCommissionRight 
		
		'Looping Jumlah Allocate Commission From'
		for (int i = 1; i <= countAllocateCommission; i++) {
			String newxpathCommissionAmt, newxpathCommissionPercentage
			String newxpathAllocateCommission
			
			'Pengecekan jika looping masih berada pada allocate commission from sebelah kiri'
			if (i <= countAllocateCommissionLeft) {
				newxpathCommissionAmt = (((('//*[@id=\'ListAllocated' + supplierCode) + '\']/div/div/div[1]/div[') + i) + ']/div/div[1]/input')
				newxpathCommissionPercentage = (((('//*[@id=\'ListAllocated' + supplierCode) + '\']/div/div/div[1]/div[') + i) + ']/div/div[2]/input')
				newxpathAllocateCommission = (((('//*[@id=\'ListAllocated' + supplierCode) + '\']/div/div/div[1]/div[') + i) + ']/div/label[1]')
			}
			else {
				newxpathCommissionAmt = (((('//*[@id=\'ListAllocated' + supplierCode) + '\']/div/div/div[2]/div[') + i) + ']/div/div[1]/input')
				newxpathCommissionPercentage = (((('//*[@id=\'ListAllocated' + supplierCode) + '\']/div/div/div[2]/div[') + i) + ']/div/div[2]/input')
				newxpathAllocateCommission = (((('//*[@id=\'ListAllocated' + supplierCode) + '\']/div/div/div[2]/div[') + i) + ']/div/label[1]')
			}
			
			
			modifyObjectAllocateCommission = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabCommissionData/label_Allocate Commission Supp'),
				'xpath', 'equals', newxpathAllocateCommission, true)
			'Menyimpan nama fee/income allocate commission from dari web confins'
			String allocateCommission = WebUI.getText(modifyObjectAllocateCommission).replace(" (AMT/%)","").replaceAll("\\s","")
			
			modifyObjectCommissionAmt = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabCommissionData/input_Admin Fee SUPP (Amt)'),
				'xpath', 'equals', newxpathCommissionAmt, true)
			modifyObjectCommissionPercentage = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabCommissionData/input__AdminFee SUPP (Percentage)'),
				'xpath', 'equals', newxpathCommissionPercentage, true)
			
			'Pengecekan allocation type jika amount'
			if(allocationType == "Amount"){
				
				if(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/CommissionReservedFund/TabCommissionData').getValue(GlobalVariable.NumofColm,
						(2 * i) + 4)!=""){
					'Input Amount, 2i+4, +4 berdasarkan perhitungan dari baris di excel, contoh admin fee dibaca saat i = 1, maka nilai ada di baris ke 2*1+4 = 6  pada excel dan seterusnya. Supaya katalon dapat membaca tambahan label fee/income pada list masing-masing dibawah fee/income terakhir'
					WebUI.setText(modifyObjectCommissionAmt, findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/CommissionReservedFund/TabCommissionData').getValue(GlobalVariable.NumofColm,
							(2 * i) + 4), FailureHandling.OPTIONAL)
				}
				
				'Klik pada inputan percentage untuk merefresh/merubah nilai percentage'
				WebUI.click(modifyObjectCommissionPercentage)
			}
			else{
				
				if(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/CommissionReservedFund/TabCommissionData').getValue(GlobalVariable.NumofColm,
						(2 * i) + 5)!=""){
					'Input Percentage, 2i+5, +5 berdasarkan perhitungan dari baris di excel, contoh admin fee dibaca saat i = 1, maka nilai ada di baris ke 2*1+5 = 7  pada excel dan seterusnya. Supaya katalon dapat membaca tambahan label fee/income pada list masing-masing dibawah fee/income terakhir'
					WebUI.setText(modifyObjectCommissionPercentage, findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/CommissionReservedFund/TabCommissionData').getValue(GlobalVariable.NumofColm,
							(2 * i) + 5), FailureHandling.OPTIONAL)
				}
				
				'Klik pada inputan amount untuk merefresh/merubah nilai amount'
				WebUI.click(modifyObjectCommissionAmt)
			}
			
			'Variabel string untuk mengambil dan menampung nilai amount dari web confins yang sudah diinput'
			String textAmount = WebUI.getAttribute(modifyObjectCommissionAmt,'value').replace(",","")
			
			'Variabel string untuk mengambil dan menampung nilai percentage dari web confins yang sudah diinput'
			String textPercentage = WebUI.getAttribute(modifyObjectCommissionPercentage,'value')
			
			'Parsing string amount menjadi bentuk double'
			BigDecimal amt = Double.parseDouble(textAmount)
			
			'Parsing string percentage menjadi bentuk double'
			BigDecimal pctg = Double.parseDouble(textPercentage)
			
			String textIncomeInfoAmt
			BigDecimal incomeInfoAmt
			
			'Pengecekan jika amount dan percentage keduanya tidak bernilai nol'
			if(amt!=0 && pctg!=0){
				'Looping Income Information'
				for(int j = 1;j<=countIncomeInfo;j++){
					newxpathIncomeInfo = "//*[@id=\"viewIncomeInfo\"]/div["+j+"]/div/div[1]/label"
					newxpathIncomeInfoAmt = "//*[@id=\"viewIncomeInfo\"]/div["+j+"]/div/div[2]/label"
					modifyObjectIncomeInfo = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabCommissionData/label_Upping Rate'),
						'xpath', 'equals', newxpathIncomeInfo, true)
					
					'Varibel String untuk mengambil dan menampung income information'
					String textIncomeInfo = WebUI.getText(modifyObjectIncomeInfo).replaceAll("\\s","")
					modifyObjectIncomeInfoAmt = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabCommissionData/label_IncomeUppingRate'),
						'xpath', 'equals', newxpathIncomeInfoAmt, true)
					
					'Pengecekan income information yang sesuai dengan fee/income allocate commission from'
					if(textIncomeInfo==allocateCommission){
						'Variabel untuk mengambil amount income information yang sesuai'
						textIncomeInfoAmt = WebUI.getText(modifyObjectIncomeInfoAmt).replace(",","")
						
						'Parsing string amount income information menjadi bentuk double'
						incomeInfoAmt = Double.parseDouble(textIncomeInfoAmt)
						
						'Keluar dari looping income information jika ditemukan income information yang sesuai dengan fee/income allocate commission from'
						break;
					}
				}
			}
			
			'Pengecekan jika amount dan percentage keduanya bernilai 0 atau tidak'
			if(amt!=0 && pctg!=0){
				'Verify hasil hitungan percentage dengan amount'
				WebUI.verifyEqual(Math.round(pctg/100*incomeInfoAmt),amt, FailureHandling.OPTIONAL)
				
				'Verify hasil hitungan amount dengan percentage'
				WebUI.verifyEqual(Math.round(amt/incomeInfoAmt*100*100)/100,Math.round(pctg*100)/100, FailureHandling.OPTIONAL)
			}
			else if(amt==0 || pctg==0){
				'Verify amount dan percentage keduanya bernilai sama yaitu nol'
				WebUI.verifyEqual(Math.round(amt),Math.round(pctg), FailureHandling.OPTIONAL)
			}
			
			
		}
	}
}

//Supplier Employee

'Mengambil nilai row keberapa dimulai data supplier employee commission pada excel'
def suppEmpRow = CustomKeywords.'getRow.getExcelRow'(GlobalVariable.DataFilePath, '13.TabCommissionData', 'Supplier Employee Commission Data')+1

'Arraylist untuk menampung List Supplier Employee'
ArrayList<WebElement> variableSuppEmp = driver.findElements(By.cssSelector('#formInformationSupplierEmployee h4'))

'Pengecekan jika terdapat minimal 1 Supplier Employee'
if(variableSuppEmp.size()>0){
	'Looping Supplier Employee List'
	for (int i = 1; i <= variableSuppEmp.size(); i++) {
		
		'Variabel untuk flagging ketika berhasil melakukan delete'
		int flagdelSuppEmployee=0
		
		newxpathSupplierEmpName = ('//*[@id=\'formInformationSupplierEmployee\']/div[' + (i + 1) + ']/div/div[1]/div[2]/div/div[1]/label')
	
		modifyObjectSuppEmpName = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabCommissionData/label_SuppEmpName'),
			'xpath', 'equals', newxpathSupplierEmpName, true)
		
		'Variabel string untuk mengambil nama dari supplier employee ke-i dari web confins'
		String supplierEmployeeName = WebUI.getText(modifyObjectSuppEmpName)
	
		newxpathSuppName = ('//*[@id=\'formInformationSupplierEmployee\']/div[' + (i + 1) + ']/div/div[2]/div[4]/label')
	
		modifyObjectSupplierName = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabCommissionData/label_SupplierNameSuppEmp'),
			'xpath', 'equals', newxpathSuppName, true)
	
		'Variabel string untuk mengambil nama dari supplier ke-i dari web confins'
		supplierName = WebUI.getText(modifyObjectSupplierName)
		
		newxpathSupplierEmpPos = ('//*[@id=\'formInformationSupplierEmployee\']/div[' + (i + 1) + ']/div/div[2]/div[2]/label')
		
		modifyObjectSupplierEmpPos = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabCommissionData/label_SuppEmpPosition'),
			'xpath', 'equals', newxpathSupplierEmpPos, true)
	
		
		'Variabel string untuk mengambil posisi dari supplier employee ke-i dari web confins'
		String supplierEmpPos = WebUI.getText(modifyObjectSupplierEmpPos)
		
		'Mengambil dan menyimpan nama supplier employee dari excel yang akan didelete'
		def deleteSuppEmpName = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/CommissionReservedFund/TabCommissionData').getValue(
				GlobalVariable.NumofColm, suppEmpRow+1)
	
		'Mengambil dan menyimpan posisi dari supplier employee dari excel yang akan didelete'
		def deleteSuppEmpPos = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/CommissionReservedFund/TabCommissionData').getValue(
				GlobalVariable.NumofColm, suppEmpRow+2)
	
		'Pengecekan pada excel jika ada supplier employee yang ingin didelete'
		if(deleteSuppEmpName.length()>0 && deleteSuppEmpPos.length()>0){
			'Proses splitting menggunakan delimiter ; digunakan jika ingin mendelete lebih dari 1 supplier employee'
			deleteSuppEmpPos = deleteSuppEmpPos.split(";")
			
			'Proses splitting menggunakan delimiter ; digunakan jika ingin mendelete lebih dari 1 posisi dari supplier employee'
			deleteSuppEmpName = deleteSuppEmpName.split(";")
			
			'Looping nama supplier employee yang akan didelete pada excel'
			for(j=1;j<=deleteSuppEmpName.size();j++){
				
				'Pengecekan jika nama supplier employee pada web confins sama dengan nama supplier employee dari excel yang akan didelete dan posisinya pada web confins sama dengan posisi dari supplier employee dari excel yang akan didelete'
				if(supplierEmployeeName.equalsIgnoreCase(deleteSuppEmpName[j-1]) && supplierEmpPos.equalsIgnoreCase(deleteSuppEmpPos[j-1])){
					newxpathDelSuppEmp = "//*[@id='formInformationSupplierEmployee']/div[" + (i + 1) + "]/div/div[1]/div[2]/div/div[2]/button/i"
					modifyDelSuppEmp = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabCommissionData/i_deleteSuppEmp'),
						 'xpath', 'equals', newxpathDelSuppEmp, true)
					'Klik icon delete pada supplier employee tersebut (tempat sampah)'
					WebUI.click(modifyDelSuppEmp)
					
					'Klik OK pada alert yang muncul'
					WebUI.acceptAlert()
					
					'Digunakan untuk menghitung jumlah list supplier employee setelah operasi delete selesai dilakukan'
					variableSuppEmp = driver.findElements(By.cssSelector('#formInformationSupplierEmployee h4'))
					i=i-1
					flagdelSuppEmployee++
				}
			}
			
		}
		'Pengecekan jika flag delete tidak sama dengan nol, terjadi setelah proses delete dilakukan'
		if(flagdelSuppEmployee!=0){
			'Lanjut ke i/supplier Employee berikutnya'
			continue;
		}
			String supplierEmployeeCode;
			newxpathBankAccount = ('//*[@id=\'formInformationSupplierEmployee\']/div[' + (i + 1) + ']/div/div[3]/div[1]/div[2]/select')
			 
			modifyObjectBankAccount = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabCommissionData/select_BankAccountSuppEmp'),
						 'xpath', 'equals', newxpathBankAccount, true)
					 
			'Mengambil nama bank account supplier employee pada excel'
			def bankAccSuppEmp = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/CommissionReservedFund/TabCommissionData').getValue(
							 GlobalVariable.NumofColm, suppEmpRow+3).split(";")
			
			'Select dropdownlist bank account dari supplier employee'
			WebUI.selectOptionByIndex(modifyObjectBankAccount, bankAccSuppEmp[i-1] , FailureHandling.OPTIONAL)
			
			'Query select pada database untuk mengambil nilai supplier employee no'
			supplierEmployeeCode = CustomKeywords.'dbconnection.checkCommissionCode.checkSupplierEmployeeCode'(sqlConnection, supplierEmployeeName, supplierEmpPos, supplierName)
			
			
			'Inisialisasi variabel Arraylist untuk menampung allocate commission from sebelah kiri'
			variableAllocateCommissionLeft = driver.findElements(By.cssSelector('#ListAllocated' + supplierEmployeeCode.replace(" ","\\ ")+ ' > div > div > div:nth-child(1) label.col-md-4.adInsLabel'))
			
			'Inisialisasi variabel Arraylist untuk menampung allocate commission from sebelah kanan'
			variableAllocateCommissionRight = driver.findElements(By.cssSelector('#ListAllocated' + supplierEmployeeCode.replace(" ","\\ ")+ ' > div > div > div:nth-child(2) label.col-md-4.adInsLabel'))
	
			'Menghitung count (size dari variabel) sebelah kiri'
			int countAllocateCommissionLeft = variableAllocateCommissionLeft.size()
			
			
			'Menghitung count (size dari variabel) sebelah kanan'
			int countAllocateCommissionRight = variableAllocateCommissionRight.size()
		
			
			'Menghitung count (size dari variabel) gabungan kiri dan kanan'
			int countAllocateCommission = countAllocateCommissionLeft + countAllocateCommissionRight
			
			'Looping Allocate Commission From'
			for (int j = 1; j <= countAllocateCommission; j++) {
				String newxpathCommissionAmt, newxpathCommissionPercentage
				String newxpathAllocateCommission
				
				'Pengecekan jika looping masih berada pada allocate commission from sebelah kiri'
				if (j <= countAllocateCommissionLeft) {
					newxpathCommissionAmt = (((('//*[@id=\'ListAllocated' + supplierEmployeeCode) + '\']/div/div/div[1]/div[') + j) + ']/div/div[1]/input')
					newxpathCommissionPercentage = (((('//*[@id=\'ListAllocated' + supplierEmployeeCode) + '\']/div/div/div[1]/div[') + j) + ']/div/div[2]/input')
					newxpathAllocateCommission = (((('//*[@id=\'ListAllocated' + supplierEmployeeCode) + '\']/div/div/div[1]/div[') + j) + ']/div/label[1]')
					
				} else {
					newxpathCommissionAmt = (((('//*[@id=\'ListAllocated' + supplierEmployeeCode) + '\']/div/div/div[2]/div[') + j) + ']/div/div[1]/input')
					newxpathCommissionPercentage = (((('//*[@id=\'ListAllocated' + supplierEmployeeCode) + '\']/div/div/div[2]/div[') + j) + ']/div/div[2]/input')
					newxpathAllocateCommission = (((('//*[@id=\'ListAllocated' + supplierEmployeeCode) + '\']/div/div/div[2]/div[') + j) + ']/div/label[1]')
					
				}
				modifyObjectAllocateCommission = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabCommissionData/label_Allocate Commission Supp Emp'),
					'xpath', 'equals', newxpathAllocateCommission, true)
				
				'Menyimpan nama fee/income allocate commission from dari web confins'
				String allocateCommission = WebUI.getText(modifyObjectAllocateCommission).replace(" (AMT/%)","").replaceAll("\\s","")
	
				modifyObjectCommissionAmt = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabCommissionData/input_Admin Fee supp emp (Amt)'),
					'xpath', 'equals', newxpathCommissionAmt, true)
				modifyObjectCommissionPercentage = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabCommissionData/input__AdminFee()SuppEmp'),
					'xpath', 'equals', newxpathCommissionPercentage, true)
				
				'Pengecekan allocation type jika amount'
				if(allocationType == "Amount"){
					
					'Mengambil nilai amount dari excel, 2j+2, +2 berdasarkan perhitungan dari baris di excel, contoh admin fee dibaca saat j = 1, maka nilai ada di baris ke 2*1+2+16(suppEmpRow) = 20  pada excel dan seterusnya. Supaya katalon dapat membaca tambahan label fee/income pada list masing-masing dibawah fee/income terakhir'
					value = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/CommissionReservedFund/TabCommissionData').getValue(GlobalVariable.NumofColm,(2 * j) + 2 + suppEmpRow).split(";",-1)
					if(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/CommissionReservedFund/TabCommissionData').getValue(GlobalVariable.NumofColm,(2 * j) + 2 + suppEmpRow).length()>0){
						
						if(value[i-1]!=""){
							'Input Amount'
							WebUI.setText(modifyObjectCommissionAmt, value[i-1] , FailureHandling.OPTIONAL)
						}
					}
					
					'Klik pada inputan percentage untuk merefresh/merubah nilai percentage'
					WebUI.click(modifyObjectCommissionPercentage)
				}
				else{
					
					'Mengambil nilai percentage dari excel, 2j+3, +3 berdasarkan perhitungan dari baris di excel, contoh admin fee dibaca saat j = 1, maka nilai ada di baris ke 2*1+3+16(suppEmpRow) = 21  pada excel dan seterusnya. Supaya katalon dapat membaca tambahan label fee/income pada list masing-masing dibawah fee/income terakhir'
					value = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/CommissionReservedFund/TabCommissionData').getValue(GlobalVariable.NumofColm,(2 * j) + 3 + suppEmpRow).split(";",-1)
					if(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/CommissionReservedFund/TabCommissionData').getValue(GlobalVariable.NumofColm,(2 * j) + 3 + suppEmpRow).length()>0){
						
						if(value[i-1]!=""){
							'Input Percentage'
							WebUI.setText(modifyObjectCommissionPercentage, value[i-1] , FailureHandling.OPTIONAL)
						}
					}
					
					'Klik pada inputan amount untuk merefresh/merubah nilai amount'
					WebUI.click(modifyObjectCommissionAmt)
				}
				
				'Variabel string untuk mengambil dan menampung nilai amount dari web confins yang sudah diinput'
				String textAmount = WebUI.getAttribute(modifyObjectCommissionAmt,'value').replace(",","")
				
				'Variabel string untuk mengambil dan menampung nilai percentage dari web confins yang sudah diinput'
				String textPercentage = WebUI.getAttribute(modifyObjectCommissionPercentage,'value')
	
				'Parsing string amount menjadi bentuk double'
				BigDecimal amt = Double.parseDouble(textAmount)
				
				'Parsing string percentage menjadi bentuk double'
				BigDecimal pctg = Double.parseDouble(textPercentage)
				
				String textIncomeInfoAmt
				BigDecimal incomeInfoAmt
				
				'Pengecekan jika amount dan percentage keduanya tidak bernilai nol'
				if(amt!=0 && pctg!=0){
					
					'Looping Income Information'
					for(int k = 1;k<=countIncomeInfo;k++){
						newxpathIncomeInfo = "//*[@id=\"viewIncomeInfo\"]/div["+k+"]/div/div[1]/label"
						newxpathIncomeInfoAmt = "//*[@id=\"viewIncomeInfo\"]/div["+k+"]/div/div[2]/label"
						modifyObjectIncomeInfo = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabCommissionData/label_Upping Rate'),
							'xpath', 'equals', newxpathIncomeInfo, true)
						
						'Varibel String untuk mengambil dan menampung income information'
						String textIncomeInfo = WebUI.getText(modifyObjectIncomeInfo).replaceAll("\\s","")
						modifyObjectIncomeInfoAmt = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabCommissionData/label_IncomeUppingRate'),
							'xpath', 'equals', newxpathIncomeInfoAmt, true)
						
						'Pengecekan income information yang sesuai dengan fee/income allocate commission from'
						if(textIncomeInfo==allocateCommission){
							
							'Variabel untuk mengambil amount income information yang sesuai'
							textIncomeInfoAmt = WebUI.getText(modifyObjectIncomeInfoAmt).replace(",","")
							
							'Parsing string amount income information menjadi bentuk double'
							incomeInfoAmt = Double.parseDouble(textIncomeInfoAmt)
							
							'Keluar dari looping income information jika ditemukan income information yang sesuai dengan fee/income allocate commission from'
							break;
						}
					}
				}
				
				'Pengecekan jika amount dan percentage keduanya bernilai 0 atau tidak'
				if(amt!=0 && pctg!=0){
					'Verify hasil hitungan percentage dengan amount'
					WebUI.verifyEqual(Math.round(pctg/100*incomeInfoAmt),amt, FailureHandling.OPTIONAL)
					
					'Verify hasil hitungan amount dengan percentage'
					WebUI.verifyEqual(Math.round(amt/incomeInfoAmt*100*100)/100,Math.round(pctg*100)/100, FailureHandling.OPTIONAL)
				}
				else if(amt==0 || pctg==0){
					'Verify amount dan percentage keduanya bernilai sama yaitu nol'
					WebUI.verifyEqual(Math.round(amt),Math.round(pctg), FailureHandling.OPTIONAL)
				}
				
			}
	}
}

//Referantor
'Mengambil nilai row keberapa dimulai data referantor commission pada excel'
def refRow = CustomKeywords.'getRow.getExcelRow'(GlobalVariable.DataFilePath, '13.TabCommissionData', 'Referantor Commission Data')+1

'Arraylist untuk menampung List Referantor'
ArrayList<WebElement> variableRef = driver.findElements(By.cssSelector('#formInformationReferantor h4'))

'Pengecekan jika terdapat minimal 1 Referantor'
if(variableRef.size()>0){
	
	'Looping Referantor List'
	for (int i = 1; i <= variableRef.size(); i++) {
		
		'Variabel untuk flagging ketika berhasil melakukan delete'
		int flagdelReferantor=0
		
		newxpathRefName = ('//*[@id=\'formInformationReferantor\']/div[' + (i + 1) + ']/div/div[1]/div[2]/div/div[1]/label')
		modifyObjectRefName = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabCommissionData/label_RefName'),
			'xpath', 'equals', newxpathRefName, true)

		'Variabel string untuk mengambil nama dari referantor ke-i dari web confins'
		String refName = WebUI.getText(modifyObjectRefName)
		
		'Mengambil dan menyimpan nama referantor dari excel yang akan didelete'
		def deleteRefName = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/CommissionReservedFund/TabCommissionData').getValue(
				GlobalVariable.NumofColm, refRow+1)
		
		'Pengecekan pada excel jika ada referantor yang ingin didelete'
		if(deleteRefName.length()>0){
			
			'Proses splitting menggunakan delimiter ; digunakan jika ingin mendelete lebih dari 1 referantor'
			deleteRefName = deleteRefName.split(";")
			
			'Looping nama referantor dari excel yang akan didelete'
			for(j=1;j<=deleteRefName.size();j++){
				
				'Pengecekan jika nama referantor pada web confins sama dengan nama referantor dari excel yang akan didelete'
				if(refName.equalsIgnoreCase(deleteRefName[j-1])){
					newxpathDelRef = "//*[@id='formInformationReferantor']/div[" + (i + 1) + "]/div/div[1]/div[2]/div/div[2]/button/i"
					modifyDelRef = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabCommissionData/i_deleteref'),
						 'xpath', 'equals', newxpathDelRef, true)
					
					'Klik icon delete pada referantor tersebut (tempat sampah)'
					WebUI.click(modifyDelRef)
					
					'Klik OK pada alert yang muncul'
					WebUI.acceptAlert()
					
					'Digunakan untuk menghitung jumlah list referantor setelah operasi delete selesai dilakukan'
					variableRef = driver.findElements(By.cssSelector('#formInformationReferantor h4'))
					i=i-1
					flagdelReferantor++
				}
			}
		}
		'Pengecekan jika flag delete tidak sama dengan nol, terjadi setelah proses delete dilakukan'
		if(flagdelReferantor!=0){
			'Lanjut ke i/referantor berikutnya'
			continue;
		}
		
			String refCode;
			newxpathBankAccount = ('//*[@id=\'formInformationReferantor\']/div[' + (i + 1) + ']/div/div[2]/div[1]/div[2]/select')
			 
			modifyObjectBankAccount = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabCommissionData/select_bankAccRef'),
						 'xpath', 'equals', newxpathBankAccount, true)
					 
			'Mengambil nama bank account referantor pada excel'		 
			def bankAccRef = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/CommissionReservedFund/TabCommissionData').getValue(
							 GlobalVariable.NumofColm, refRow+2).split(";")
			
			'Select dropdownlist bank account referantor'
			WebUI.selectOptionByIndex(modifyObjectBankAccount, bankAccRef[i-1] , FailureHandling.OPTIONAL)
			
			'Query select pada database untuk mengambil nilai referantor code/nomor'
			refCode = CustomKeywords.'dbconnection.checkCommissionCode.checkReferantorCode'(sqlConnection, refName)
			
			
			'Inisialisasi variabel Arraylist untuk menampung allocate commission from sebelah kiri'
			variableAllocateCommissionLeft = driver.findElements(By.cssSelector('#ListAllocated' + refCode+ ' > div > div > div:nth-child(1) label.col-md-4.adInsLabel'))
	
			'Inisialisasi variabel Arraylist untuk menampung allocate commission from sebelah kanan'
			variableAllocateCommissionRight = driver.findElements(By.cssSelector('#ListAllocated' + refCode+ ' > div > div > div:nth-child(2) label.col-md-4.adInsLabel'))
	
			'Menghitung count (size dari variabel) sebelah kiri'
			int countAllocateCommissionLeft = variableAllocateCommissionLeft.size()

			'Menghitung count (size dari variabel) sebelah kanan'
			int countAllocateCommissionRight = variableAllocateCommissionRight.size()
			
			'Menghitung count (size dari variabel) gabungan kiri dan kanan'
			int countAllocateCommission = countAllocateCommissionLeft + countAllocateCommissionRight
			
			'Looping Allocate Commission From'
			for (int j = 1; j <= countAllocateCommission; j++) {
				String newxpathCommissionAmt, newxpathCommissionPercentage
				String newxpathAllocateCommission
				
				'Pengecekan jika looping masih berada pada allocate commission from sebelah kiri'
				if (j <= countAllocateCommissionLeft) {
					newxpathCommissionAmt = (((('//*[@id=\'ListAllocated' + refCode) + '\']/div/div/div[1]/div[') + j) + ']/div/div[1]/input')
					newxpathCommissionPercentage = (((('//*[@id=\'ListAllocated' + refCode) + '\']/div/div/div[1]/div[') + j) + ']/div/div[2]/input')
					newxpathAllocateCommission = (((('//*[@id=\'ListAllocated' + refCode) + '\']/div/div/div[1]/div[') + j) + ']/div/label[1]')
				} else {
					newxpathCommissionAmt = (((('//*[@id=\'ListAllocated' + refCode) + '\']/div/div/div[2]/div[') + j) + ']/div/div[1]/input')
					newxpathCommissionPercentage = (((('//*[@id=\'ListAllocated' + refCode) + '\']/div/div/div[2]/div[') + j) + ']/div/div[2]/input')
					newxpathAllocateCommission = (((('//*[@id=\'ListAllocated' + refCode) + '\']/div/div/div[2]/div[') + j) + ']/div/label[1]')
				}
				
				modifyObjectAllocateCommission = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabCommissionData/label_Allocate Commission Ref'),
					'xpath', 'equals', newxpathAllocateCommission, true)
				
				'Menyimpan nama fee/income allocate commission from dari web confins'
				String allocateCommission = WebUI.getText(modifyObjectAllocateCommission).replace(" (AMT/%)","").replaceAll("\\s","")

				modifyObjectCommissionAmt = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabCommissionData/input_Admin Fee Ref(Amt)'),
					'xpath', 'equals', newxpathCommissionAmt, true)
				modifyObjectCommissionPercentage = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabCommissionData/input__AdminFee Ref ()'),
					'xpath', 'equals', newxpathCommissionPercentage, true)
				
				'Pengecekan allocation type jika amount'
				if(allocationType == "Amount"){
					'Mengambil nilai amount dari excel, 2j+1, +1 berdasarkan perhitungan dari baris di excel, contoh admin fee dibaca saat j = 1, maka nilai ada di baris ke 2*1+1+32(refRow) = 35  pada excel dan seterusnya. Supaya katalon dapat membaca tambahan label fee/income pada list masing-masing dibawah fee/income terakhir'
					value = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/CommissionReservedFund/TabCommissionData').getValue(GlobalVariable.NumofColm,(2 * j) + 1 + refRow).split(";",-1)
					
					if(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/CommissionReservedFund/TabCommissionData').getValue(GlobalVariable.NumofColm,(2 * j) + 1 + refRow).length()>0){
						if(value[i-1]!=""){
							'Input Amount'
							WebUI.setText(modifyObjectCommissionAmt, value[i-1] , FailureHandling.OPTIONAL)
						}
						
					}
					
					'Klik pada inputan percentage untuk merefresh/merubah nilai percentage'
					WebUI.click(modifyObjectCommissionPercentage)
				}
				else{
					'Mengambil nilai percentage dari excel, 2j+2, +2 berdasarkan perhitungan dari baris di excel, contoh admin fee dibaca saat j = 1, maka nilai ada di baris ke 2*1+2+32(refRow) = 36  pada excel dan seterusnya. Supaya katalon dapat membaca tambahan label fee/income pada list masing-masing dibawah fee/income terakhir'
					value = findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/CommissionReservedFund/TabCommissionData').getValue(GlobalVariable.NumofColm,(2 * j) + 2 + refRow).split(";",-1)
					
					if(findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/CommissionReservedFund/TabCommissionData').getValue(GlobalVariable.NumofColm,(2 * j) + 2 + refRow).length()>0){
						if(value[i-1]!=""){
							'Input Percentage'
							WebUI.setText(modifyObjectCommissionPercentage, value[i-1] , FailureHandling.OPTIONAL)
						}
					}
					
					
					'Klik pada inputan amount untuk merefresh/merubah nilai amount'
					WebUI.click(modifyObjectCommissionAmt)
				}
				
				'Variabel string untuk mengambil dan menampung nilai amount dari web confins yang sudah diinput'
				String textAmount = WebUI.getAttribute(modifyObjectCommissionAmt,'value').replace(",","")
				
				'Variabel string untuk mengambil dan menampung nilai percentage dari web confins yang sudah diinput'
				String textPercentage = WebUI.getAttribute(modifyObjectCommissionPercentage,'value')

				'Parsing string amount menjadi bentuk double'
				BigDecimal amt = Double.parseDouble(textAmount)
				
				'Parsing string percentage menjadi bentuk double'
				BigDecimal pctg = Double.parseDouble(textPercentage)
				
				String textIncomeInfoAmt
				BigDecimal incomeInfoAmt
				
				'Pengecekan jika amount dan percentage keduanya tidak bernilai nol'
				if(amt!=0 && pctg!=0){
					'Looping Income Information'
					for(int k = 1;k<=countIncomeInfo;k++){
						newxpathIncomeInfo = "//*[@id=\"viewIncomeInfo\"]/div["+k+"]/div/div[1]/label"
						newxpathIncomeInfoAmt = "//*[@id=\"viewIncomeInfo\"]/div["+k+"]/div/div[2]/label"
						modifyObjectIncomeInfo = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabCommissionData/label_Upping Rate'),
							'xpath', 'equals', newxpathIncomeInfo, true)
						
						'Varibel String untuk mengambil dan menampung income information'
						String textIncomeInfo = WebUI.getText(modifyObjectIncomeInfo).replaceAll("\\s","")
						modifyObjectIncomeInfoAmt = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabCommissionData/label_IncomeUppingRate'),
							'xpath', 'equals', newxpathIncomeInfoAmt, true)
						
						'Pengecekan income information yang sesuai dengan fee/income allocate commission from'
						if(textIncomeInfo==allocateCommission){
							
							'Variabel untuk mengambil amount income information yang sesuai'
							textIncomeInfoAmt = WebUI.getText(modifyObjectIncomeInfoAmt).replace(",","")
							
							'Parsing string amount income information menjadi bentuk double'
							incomeInfoAmt = Double.parseDouble(textIncomeInfoAmt)
							
							'Keluar dari looping income information jika ditemukan income information yang sesuai dengan fee/income allocate commission from'
							break;
						}
					}
				}
				
				'Pengecekan jika amount dan percentage keduanya bernilai 0 atau tidak'
				if(amt!=0 && pctg!=0){
					
					'Verify hasil hitungan percentage dengan amount'
					WebUI.verifyEqual(Math.round(pctg/100*incomeInfoAmt),amt, FailureHandling.OPTIONAL)
					
					'Verify hasil hitungan amount dengan percentage'
					WebUI.verifyEqual(Math.round(amt/incomeInfoAmt*100*100)/100,Math.round(pctg*100)/100, FailureHandling.OPTIONAL)
				}
				else if(amt==0 || pctg==0){
					
					'Verify amount dan percentage keduanya bernilai sama yaitu nol'
					WebUI.verifyEqual(Math.round(amt),Math.round(pctg), FailureHandling.OPTIONAL)
				}
			}
		}
	
}

'Klik Calculate'
WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabCommissionData/button_Calculate'))

variableSupp = driver.findElements(By.cssSelector('#formInformationSupplier h4'))
variableSuppEmp = driver.findElements(By.cssSelector('#formInformationSupplierEmployee h4'))
variableRef = driver.findElements(By.cssSelector('#formInformationReferantor h4'))

GlobalVariable.TotalCommissionAmt = 0.00
GlobalVariable.TotalCommissionAmtAftTax = 0.00
GlobalVariable.TotalTax = 0.00
GlobalVariable.TotalVat = 0.00
GlobalVariable.TotalDisburseAmt = 0.00
GlobalVariable.TotalExpenseAmt = 0.00

'Looping data Supplier'
for (int i = 1; i<=variableSupp.size(); i++){
	 newxpathCommAmt = '//*[@id="formInformationSupplier"]/div['+(i+1)+']/div/div[2]/div[2]/div[2]/label'
	 newxpathAmtAftTax = '//*[@id="formInformationSupplier"]/div['+(i+1)+']/div/div[2]/div[3]/div[1]/label'
	 newxpathTax = '//*[@id="formInformationSupplier"]/div['+(i+1)+']/div/div[2]/div[1]/div[3]/label'
	 newxpathVat = '//*[@id="formInformationSupplier"]/div['+(i+1)+']/div/div[2]/div[2]/div[3]/label'
	 newxpathDisburse = '//*[@id="formInformationSupplier"]/div['+(i+1)+']/div/div[2]/div[3]/div[2]/label'
	 newxpathExpense = '//*[@id="formInformationSupplier"]/div['+(i+1)+']/div/div[2]/div[4]/div[3]/label'
	 
	 'modify object total commission amount'
	 modifyObjectCommAmt = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabCommissionData/label_TotalCommissionAmtSupplier'),
		 'xpath', 'equals', newxpathCommAmt, true)
	 
	 'modify object total commission after tax amount'
	 modifyObjectAmtAftTax = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabCommissionData/label_TotalCommisssionAfterTaxAmtSupplier'),
		 'xpath', 'equals', newxpathAmtAftTax, true)
	 
	 'modify object withholding tax amount'
	 modifyObjectTax = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabCommissionData/label_WithHoldingTaxAmtSupp'),
		 'xpath', 'equals', newxpathTax, true)
	 
	 'modify object vat amount'
	 modifyObjectVat = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabCommissionData/label_VatAmtSupp'),
		 'xpath', 'equals', newxpathVat, true)
	 
	 'modify object disburse amount'
	 modifyObjectDisburse = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabCommissionData/label_DisburseAmtSupp'),
		 'xpath', 'equals', newxpathDisburse, true)
	 
	 'modify object expense amount'
	 modifyObjectExpense = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabCommissionData/label_ExpenseAmtSupp'),
		 'xpath', 'equals', newxpathExpense, true)
	 
	 'Menghitung total amount untuk verify summary'
	 CustomKeywords.'commissionData.commission.count'(modifyObjectCommAmt, modifyObjectAmtAftTax, modifyObjectTax, modifyObjectVat, modifyObjectDisburse, modifyObjectExpense)
	
}

'Looping data supplier employee'
for (int j = 1; j <= variableSuppEmp.size(); j++) {
	newxpathCommAmt = '//*[@id="formInformationSupplierEmployee"]/div['+(j+1)+']/div/div[3]/div[2]/div[2]/label'
	newxpathAmtAftTax = '//*[@id="formInformationSupplierEmployee"]/div['+(j+1)+']/div/div[3]/div[3]/div[1]/label'
	newxpathTax = '//*[@id="formInformationSupplierEmployee"]/div['+(j+1)+']/div/div[3]/div[1]/div[3]/label'
	newxpathVat = '//*[@id="formInformationSupplierEmployee"]/div['+(j+1)+']/div/div[3]/div[2]/div[3]/label'
	newxpathDisburse = '//*[@id="formInformationSupplierEmployee"]/div['+(j+1)+']/div/div[3]/div[3]/div[2]/label'
	newxpathExpense = '//*[@id="formInformationSupplierEmployee"]/div['+(j+1)+']/div/div[3]/div[4]/div[3]/label'
	
	'modify object total commission amount'
	modifyObjectCommAmt = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabCommissionData/label_TotalCommissionAmtSuppEmp'),
		'xpath', 'equals', newxpathCommAmt, true)
	
	'modify object total commission after tax amount'
	modifyObjectAmtAftTax = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabCommissionData/label_TotalCommissionAfterTaxAmtSuppEmp'),
		'xpath', 'equals', newxpathAmtAftTax, true)
	
	'modify object withholding tax amount'
	modifyObjectTax = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabCommissionData/label_WithHoldingTaxAmtSuppEmp'),
		'xpath', 'equals', newxpathTax, true)
	
	'modify object vat amount'
	modifyObjectVat = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabCommissionData/label_VatAmtSuppEmp'),
		'xpath', 'equals', newxpathVat, true)
	
	'modify object disburse amount'
	modifyObjectDisburse = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabCommissionData/label_DisburseAmtSuppEmp'),
		'xpath', 'equals', newxpathDisburse, true)
	
	'modify object expense amount'
	modifyObjectExpense = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabCommissionData/label_ExpenseAmtSuppEmp'),
		'xpath', 'equals', newxpathExpense, true)
	
	'Menghitung total amount untuk verify summary'
	CustomKeywords.'commissionData.commission.count'(modifyObjectCommAmt, modifyObjectAmtAftTax, modifyObjectTax, modifyObjectVat, modifyObjectDisburse, modifyObjectExpense)

}

'Looping data referantor'
for(int k=1; k <= variableRef.size(); k++){
	 newxpathCommAmt = '//*[@id="formInformationReferantor"]/div['+(k+1)+']/div/div[2]/div[2]/div[2]/label'
	 newxpathAmtAftTax = '//*[@id="formInformationReferantor"]/div['+(k+1)+']/div/div[2]/div[3]/div[1]/label'
	 newxpathTax = '//*[@id="formInformationReferantor"]/div['+(k+1)+']/div/div[2]/div[1]/div[3]/label'
	 newxpathVat = '//*[@id="formInformationReferantor"]/div['+(k+1)+']/div/div[2]/div[2]/div[3]/label'
	 newxpathDisburse = '//*[@id="formInformationReferantor"]/div['+(k+1)+']/div/div[2]/div[3]/div[2]/label'
	 newxpathExpense = '//*[@id="formInformationReferantor"]/div['+(k+1)+']/div/div[2]/div[4]/div[3]/label'
	 
	 'modify object total commission amount'
	 modifyObjectCommAmt = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabCommissionData/label_TotalCommissionAmtRef'),
		 'xpath', 'equals', newxpathCommAmt, true)
	 
	 'modify object total commission after tax amount'
	 modifyObjectAmtAftTax = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabCommissionData/label_TotalCommissionAfterTaxAmtRef'),
		 'xpath', 'equals', newxpathAmtAftTax, true)
	 
	 'modify object withholding tax amount'
	 modifyObjectTax = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabCommissionData/label_WithHoldingTaxAmtRef'),
		 'xpath', 'equals', newxpathTax, true)
	 
	 'modify object vat amount'
	 modifyObjectVat = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabCommissionData/label_VatAmtRef'),
		 'xpath', 'equals', newxpathVat, true)
	 
	 'modify object disburse amount'
	 modifyObjectDisburse = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabCommissionData/label_DisburseAmtRef'),
		 'xpath', 'equals', newxpathDisburse, true)
	 
	 'modify object expense amount'
	 modifyObjectExpense = WebUI.modifyObjectProperty(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabCommissionData/label_ExpenseAmtRef'),
		 'xpath', 'equals', newxpathExpense, true)
	 
	 'Menghitung total amount untuk verify summary'
	 CustomKeywords.'commissionData.commission.count'(modifyObjectCommAmt, modifyObjectAmtAftTax, modifyObjectTax, modifyObjectVat, modifyObjectDisburse, modifyObjectExpense)
	
}

'Mengambil nilai summary total commission amount dari confins'
String textTotalCommAmt = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabCommissionData/label_TotalCommisionAmt')).replace(",","")

'Mengambil nilai summary total commission after tax amount dari confins'
String textTotalCommAmtAftTax = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabCommissionData/label_TotalCommissionAfterTaxAmt')).replace(",","")

'Mengambil nilai summary total disburse amount dari confins'
String textTotalDisburseAmt = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabCommissionData/label_TotalDisburseAmt')).replace(",","")

'Mengambil nilai summary total expense amount dari confins'
String textTotalExpenseAmt = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabCommissionData/label_TotalExpenseAmt')).replace(",","")

'Mengambil nilai summary total tax dari confins'
String textTotalTax = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabCommissionData/label_TotalTax')).replace(",","")

'Mengambil nilai summary total vat dari confins'
String textTotalVat = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabCommissionData/label_TotalVat')).replace(",","")

'Verify nilai pada section summary sesuai dengan perhitungan'
WebUI.verifyMatch(textTotalCommAmt, String.format("%.2f", GlobalVariable.TotalCommissionAmt),false)

'Verify nilai pada section summary sesuai dengan perhitungan'
WebUI.verifyMatch(textTotalCommAmtAftTax, String.format("%.2f",GlobalVariable.TotalCommissionAmtAftTax),false)

'Verify nilai pada section summary sesuai dengan perhitungan'
WebUI.verifyMatch(textTotalDisburseAmt, String.format("%.2f",GlobalVariable.TotalDisburseAmt),false)

'Verify nilai pada section summary sesuai dengan perhitungan'
WebUI.verifyMatch(textTotalExpenseAmt, String.format("%.2f",GlobalVariable.TotalExpenseAmt),false)

'Verify nilai pada section summary sesuai dengan perhitungan'
WebUI.verifyMatch(textTotalTax, String.format("%.2f",GlobalVariable.TotalTax),false)

'Verify nilai pada section summary sesuai dengan perhitungan'
WebUI.verifyMatch(textTotalVat, String.format("%.2f",GlobalVariable.TotalVat),false)

'Klik save'
WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabCommissionData/button_Save'))

WebUI.delay(3)

'Pengecekan jika setelah klik save, dropdownlist allocation type masih bisa diklik/dipilih'
if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabCommissionData/select_AmountPercentage'), 
    5, FailureHandling.OPTIONAL)) {
    
	'Klik cancel'
    WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/CommissionReservedFund/TabCommissionData/button_Cancel'))

    'Write to Excel FAILED'
    CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '13.TabCommissionData', 0, GlobalVariable.NumofColm - 
        1, GlobalVariable.StatusFailed)
	
	'Pengecekan jika new consumer finance belum diexpand'
	if(WebUI.verifyElementNotVisible(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA'), FailureHandling.OPTIONAL)){
		
		'Klik new consumer finance'
		WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_Consumer Finance'))
	}
} else {
    
	'Write to Excel SUCCESS'
    CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '13.TabCommissionData', 0, GlobalVariable.NumofColm - 
        1, GlobalVariable.StatusSuccess)
}



