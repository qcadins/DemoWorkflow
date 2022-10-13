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

'Assign directori file excel ke global variabel'
String userDir = System.getProperty('user.dir')

'Assign directori file excel ke global variabel'
String filePath = userDir + GlobalVariable.PathCompany

'Assign directori file excel ke global variabel'
GlobalVariable.DataFilePath = filePath

String servername = findTestData('Login/Login').getValue(1, 9)

String instancename = findTestData('Login/Login').getValue(2, 9)

String username = findTestData('Login/Login').getValue(3, 9)

String password = findTestData('Login/Login').getValue(4, 9)

String database = findTestData('Login/Login').getValue(5, 9)

String driverclassname = findTestData('Login/Login').getValue(6, 9)

String url = (((servername + ';instanceName=') + instancename) + ';databaseName=') + database

String appno = findTestData('NAP-CF4W-CustomerCompany/NAP1-CustomerData-Company/TabCustomerData').getValue(
        GlobalVariable.NumofColm, 13)

ArrayList<Boolean> arrayMatch = new ArrayList<>()

'connect DB'
Sql sqlconnection = CustomKeywords.'dbconnection.connectDB.connect'(url, username, password, driverclassname)

def commissionData = findTestData('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData')

'Mencari jumlah supplier, supplier employee, dan referantor yang muncul dalam commission'
ArrayList<Integer> countData = CustomKeywords.'dbconnection.CustomerDataVerif.countCommissionRecipientDB'(sqlconnection, appno)

int suppsize = countData.get(0)
int suppempsize = countData.get(1)
int refsize = countData.get(2)

Integer suppSource
ArrayList<Integer> suppEmpSource = new ArrayList<Integer>()
ArrayList<Integer> refSource = new ArrayList<Integer>()

if(suppsize>0){
	'Mencari jumlah commission source dari masing-masing supplier'
	suppSource = CustomKeywords.'dbconnection.CustomerDataVerif.countCommissionSourceSupplierDB'(sqlconnection, appno)
}
if(suppempsize>0){
	'Mencari jumlah commission source dari masing-masing supplier employee'
	suppEmpSource = CustomKeywords.'dbconnection.CustomerDataVerif.countCommissionSourceSupplierEmpDB'(sqlconnection, appno)
}
if(refsize>0){
	'Mencari jumlah commission source dari masing-masing referantor'
	refSource = CustomKeywords.'dbconnection.CustomerDataVerif.countCommissionSourceReferantorDB'(sqlconnection, appno)
}

'Mengambil nilai row keberapa dimulai data supplier commission pada excel'
def supRow = CustomKeywords.'excelGetRow.getRow.getExcelRow'(GlobalVariable.DataFilePath, '12.TabCommissionData', 'Supplier Commission Data') +
1

'Mengambil nilai row keberapa dimulai data supplier employee commission pada excel'
def suppEmpRow = CustomKeywords.'excelGetRow.getRow.getExcelRow'(GlobalVariable.DataFilePath, '12.TabCommissionData', 'Supplier Employee Commission Data') +
1

'Mengambil nilai row keberapa dimulai data referantor commission pada excel'
def refRow = CustomKeywords.'excelGetRow.getRow.getExcelRow'(GlobalVariable.DataFilePath, '12.TabCommissionData', 'Referantor Commission Data') +
1

'Mencari nilai commission amount dari setiap commission source dari supplier, supplier employee, dan referantor'
HashMap<String,ArrayList> result = CustomKeywords.'dbconnection.CustomerDataVerif.NAP3CommissionDataStoreDB'(sqlconnection, appno,commissionData.getValue(GlobalVariable.NumofColm,12))
ArrayList<String> comSupp = result.get("Supp")
ArrayList<String> comSuppEmp = result.get("SuppEmp")
ArrayList<String> comRef = result.get("Ref")

if(commissionData.getValue(GlobalVariable.NumofColm,12).equalsIgnoreCase("Amount")){
	if(suppSource!=null){
		for(int i = 1;i<=suppSource;i++){
			'2i+1, +1 berdasarkan perhitungan dari baris di excel, contoh admin fee dibaca saat i = 1, maka nilai ada di baris ke 2*1+1 = 3+supRow  pada excel dan seterusnya. Supaya katalon dapat membaca tambahan label fee/income pada list masing-masing dibawah fee/income terakhir'
			if(findTestData('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData').getValue(
                    GlobalVariable.NumofColm, (2 * i) + 1 + supRow)!='' && comSupp.get(i-1)!="-1"){
					
					'Verif amount commmision supplier db dengan excel'
					arrayMatch.add(WebUI.verifyEqual(Double.parseDouble(comSupp.get(i-1).toString()),Double.parseDouble(findTestData('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData').getValue(
							GlobalVariable.NumofColm, (2 * i) + 1 + supRow))))
			}
			
		}
	}
	int count = 0
	if(suppEmpSource!=null){
		for (int i = 1; i <= suppempsize; i++) {
			if(i>1){
				count +=suppEmpSource.get(i-2)
			}
			for (int j = 1; j <= suppEmpSource.get(i-1); j++) {
				value = findTestData('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData').getValue(
                    GlobalVariable.NumofColm, ((2 * j) + 2) + suppEmpRow).split(';', -1)
				'2j+2, +2 berdasarkan perhitungan dari baris di excel, contoh admin fee dibaca saat j = 1, maka nilai ada di baris ke 2*1+2+16(suppEmpRow) = 20  pada excel dan seterusnya. Supaya katalon dapat membaca tambahan label fee/income pada list masing-masing dibawah fee/income terakhir'
				if(findTestData('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData').getValue(
                    GlobalVariable.NumofColm, ((2 * j) + 2) + suppEmpRow).length() > 0){
					if(value[i-1]!='' && comSuppEmp.get(count+(j-1))!="-1"){
						'Verif amount commmision supplier employee db dengan excel'
						arrayMatch.add(WebUI.verifyEqual(Double.parseDouble(comSuppEmp.get(count+(j-1)).toString()),Double.parseDouble(value[i-1])))
					}
				}
				
			}
		}
	}
	int countRf=0
	if(refSource!=null){
		for (int i = 1; i <= refsize; i++) {
			if(i>1){
				countRf +=refSource.get(i-2)
			}
			for (int j = 1; j <= refSource.get(i-1); j++) {
				 value = findTestData('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData').getValue(
                    GlobalVariable.NumofColm, ((2 * j) + 1) + refRow).split(';', -1)
				'2j+1, +1 berdasarkan perhitungan dari baris di excel, contoh admin fee dibaca saat j = 1, maka nilai ada di baris ke 2*1+1+32(refRow) = 35  pada excel dan seterusnya. Supaya katalon dapat membaca tambahan label fee/income pada list masing-masing dibawah fee/income terakhir'
				if(findTestData('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData').getValue(
                    GlobalVariable.NumofColm, ((2 * j) + 1) + refRow).length() > 0){
					if(value[i-1]!='' && comRef.get(countRf+(j-1))!="-1"){
						'Verif amount commmision referantor db dengan excel'
						arrayMatch.add(WebUI.verifyEqual(Double.parseDouble(comRef.get(countRf+(j-1)).toString()),Double.parseDouble(value[i-1])))
					}
				}
				
			}
		}
	}
	
	
}
else if(commissionData.getValue(GlobalVariable.NumofColm,12).equalsIgnoreCase("Percentage")){
	if(suppSource!=null){
		for(int i = 1;i<=suppSource;i++){
			'2i+2, +2 berdasarkan perhitungan dari baris di excel, contoh admin fee dibaca saat i = 1, maka nilai ada di baris ke 2*1+2 = 4+supRow  pada excel dan seterusnya. Supaya katalon dapat membaca tambahan label fee/income pada list masing-masing dibawah fee/income terakhir'
			if(findTestData('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData').getValue(
				GlobalVariable.NumofColm, (2 * i) + 2 + supRow)!='' && comSupp.get(i-1)!="-1"){
				'Verif percentage commmision supplier db dengan excel'
				arrayMatch.add(WebUI.verifyEqual(Math.round(Double.parseDouble(comSupp.get(i-1).toString())*100)/100,Double.parseDouble(findTestData('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData').getValue(
					GlobalVariable.NumofColm, (2 * i) + 2 + supRow))))
			}
			
		}
	}
	int count=0
	if(suppEmpSource!=null){
		for (int i = 1; i <= suppempsize; i++) {
			if(i>1){
				count +=suppEmpSource.get(i-2)
			}
			for (int j = 1; j <= suppEmpSource.get(i-1); j++) {
				value = findTestData('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData').getValue(
                    GlobalVariable.NumofColm, ((2 * j) + 3) + suppEmpRow).split(';', -1)
				'2j+3, +3 berdasarkan perhitungan dari baris di excel, contoh admin fee dibaca saat j = 1, maka nilai ada di baris ke 2*1+3+16(suppEmpRow) = 21  pada excel dan seterusnya. Supaya katalon dapat membaca tambahan label fee/income pada list masing-masing dibawah fee/income terakhir'
				if(findTestData('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData').getValue(
                    GlobalVariable.NumofColm, ((2 * j) + 3) + suppEmpRow).length()>0){
					if(value[i-1]!='' && comSuppEmp.get(count+(j-1))!="-1"){
						'Verif percentage commmision supplier employee db dengan excel'
						arrayMatch.add(WebUI.verifyEqual(Math.round(Double.parseDouble(comSuppEmp.get(count+(j-1)).toString())*100)/100,Double.parseDouble(value[i-1])))
					}
				}
				
			}
		}
	}
	int countRf=0
	if(refSource!=null){
		for (int i = 1; i <= refsize; i++) {
			if(i>1){
				countRf +=refSource.get(i-2)
			}
			for (int j = 1; j <= refSource.get(i-1); j++) {
				value = findTestData('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData').getValue(
                    GlobalVariable.NumofColm, ((2 * j) + 2) + refRow).split(';', -1)
				'2j+2, +2 berdasarkan perhitungan dari baris di excel, contoh admin fee dibaca saat j = 1, maka nilai ada di baris ke 2*1+2+32(refRow) = 36  pada excel dan seterusnya. Supaya katalon dapat membaca tambahan label fee/income pada list masing-masing dibawah fee/income terakhir'
				if(findTestData('NAP-CF4W-CustomerCompany/CommissionReservedFund/TabCommissionData').getValue(
                    GlobalVariable.NumofColm, ((2 * j) + 2) + refRow).length() > 0){
					if(value[i-1]!='' && comRef.get(countRf+(j-1))!="-1"){
						'Verif percentage commmision referantor db dengan excel'
						arrayMatch.add(WebUI.verifyEqual(Math.round(Double.parseDouble(comRef.get(countRf+(j-1)).toString())*100)/100,Double.parseDouble(value[i-1])))
					}
				}
				
			}
		}
	}
}

'jika nilai di confins tidak sesuai dengan db'
if(arrayMatch.contains(false)){
	'Write To Excel GlobalVariable.StatusFailed'
	CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '12.TabCommissionData',
		0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)

	'Write To Excel GlobalVariable.ReasonFailedStoredDB'
	CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '12.TabCommissionData',
		1, GlobalVariable.NumofColm - 1, GlobalVariable.ReasonFailedStoredDB)
}










