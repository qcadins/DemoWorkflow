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
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.WebElement as WebElement
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.By as By

GlobalVariable.CopyAppColm = 0

String userDir = System.getProperty('user.dir')

String filePath = userDir + GlobalVariable.PathPersonal

GlobalVariable.DataFilePath = filePath

'Koneksi database'
String servername = findTestData('Login/Login').getValue(1, 8)

String instancename = findTestData('Login/Login').getValue(2, 8)

String username = findTestData('Login/Login').getValue(3, 8)

String password = findTestData('Login/Login').getValue(4, 8)

String databaseLOS = findTestData('Login/Login').getValue(5, 9)

String driverclassname = findTestData('Login/Login').getValue(6, 8)

String urlLOS = (((servername + ';instanceName=') + instancename) + ';databaseName=') + databaseLOS

Sql sqlConnectionLOS = CustomKeywords.'dbconnection.connectDB.connect'(urlLOS, username, password, driverclassname)

String POStat

'click Menu customer main data'
WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA'))

if(GlobalVariable.Role=="Testing" && GlobalVariable.CheckSortPagingPersonal=="Yes"){
	ArrayList<String> listString = new ArrayList<String>()
	'click button search'
	WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/button_Search'))
	
	'Inisialisasi driver'
	WebDriver driver = DriverFactory.getWebDriver()
	
	'Inisialisasi variabel'
	ArrayList<WebElement> rowData = driver.findElements(By.cssSelector('body > app-root > app-full-layout > div > div.main-panel > div > div > div > div > cust-main-data-paging > lib-ucpaging > lib-ucgridview > div > table > tbody > tr'))

	'Klik header office'
	WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/span_Office'))
	'Verif tidak ada alert yang muncul'
	WebUI.verifyElementNotPresent(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/div_erroralert'), 2)
	
	'Klik header appno'
	WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/span_applicationNo'))
	
	'Verify alert tidak muncul'
	WebUI.verifyElementNotPresent(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/div_erroralert'), 2)
	
	for(int i=1;i<=rowData.size();i++){
		appNoObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/appNo')
				,'xpath','equals',"/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/cust-main-data-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr["+i+"]/td[4]/span/a",true)
		
		listString.add(WebUI.getText(appNoObject))
	}
	'Verif sort appno ascending'
	Boolean isSorted = CustomKeywords.'sortPaging.verifySortPaging.verifySortAscending'(listString)
	WebUI.verifyEqual(isSorted,true)
	
	listApp = new ArrayList<String>()
	'Klik header appno'
	WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/span_applicationNo'))
	for(int i=1;i<=rowData.size();i++){
		appNoObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/appNo')
				,'xpath','equals',"/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/cust-main-data-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr["+i+"]/td[4]/span/a",true)
		
		listApp.add(WebUI.getText(appNoObject))
		
	}
	'verif sort appno descending'
	isSorted = CustomKeywords.'sortPaging.verifySortPaging.verifySortDescending'(listApp)
	WebUI.verifyEqual(isSorted,true)
	
	'Klik header custname 2x supaya descending'
	WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/span_custName'))
	WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/span_custName'))
	
	'Verify alert tidak muncul'
	WebUI.verifyElementNotPresent(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/div_erroralert'), 2)
	
	listString = new ArrayList<String>()
	for(int i=1;i<=rowData.size();i++){
		appNoObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/custName')
				,'xpath','equals',"/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/cust-main-data-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr["+i+"]/td[5]/span",true)
		
		listString.add(WebUI.getText(appNoObject))

	}
	'verif sort custname descending'
	isSorted = CustomKeywords.'sortPaging.verifySortPaging.verifySortDescending'(listString)
	WebUI.verifyEqual(isSorted,true)
	
	'Klik header poname'
	WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/span_POName'))
	
	'Verify alert tidak muncul'
	WebUI.verifyElementNotPresent(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/div_erroralert'), 2)
	
	listString = new ArrayList<String>()
	for(int i=1;i<=rowData.size();i++){
		appNoObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/POName')
				,'xpath','equals',"/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/cust-main-data-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr["+i+"]/td[6]/span",true)
		
		listString.add(WebUI.getText(appNoObject))

	}
	'verif sort poname ascending'
	isSorted = CustomKeywords.'sortPaging.verifySortPaging.verifySortAscending'(listString)
	WebUI.verifyEqual(isSorted,true)
	
	'Klik header poname'
	WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/span_POName'))
	listString = new ArrayList<String>()
	for(int i=1;i<=rowData.size();i++){
		appNoObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/POName')
				,'xpath','equals',"/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/cust-main-data-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr["+i+"]/td[6]/span",true)
		
		listString.add(WebUI.getText(appNoObject))
	
	}
	'Verif sort poname descending'
	isSorted = CustomKeywords.'sortPaging.verifySortPaging.verifySortDescending'(listString)
	WebUI.verifyEqual(isSorted,true)
	
	'Klik 2x header appno supaya urutan descending'
	WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/span_applicationNo'))
	WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/span_applicationNo'))
	
	'Ambil count data dari confins'
	String[] textCountData = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/countData')).replace(' ', '').replace(':', ';').split(';')
	Integer countDt = Integer.parseInt(textCountData[1])
	
	'Jika count data secara keseluruhan dari confins lebih besar daripada data di page 1'
	if(countDt>rowData.size()){
		'Klik page 2'
		WebUI.click(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/nextPage'))
		
		'Verify page 2 active'
		WebUI.verifyElementHasAttribute(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/nextPage'),'aria-current',2)
		
		rowData = driver.findElements(By.cssSelector('body > app-root > app-full-layout > div > div.main-panel > div > div > div > div > cust-main-data-paging > lib-ucpaging > lib-ucgridview > div > table > tbody > tr'))
		
		listString = new ArrayList<String>()
		for(int i=1;i<=rowData.size();i++){
			appNoObject = WebUI.modifyObjectProperty(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/appNo')
					,'xpath','equals',"/html/body/app-root/app-full-layout/div/div[2]/div/div/div/div/cust-main-data-paging/lib-ucpaging/lib-ucgridview/div/table/tbody/tr["+i+"]/td[4]/span",true)
			
			listString.add(WebUI.getText(appNoObject))
		}
		'Verif appno yang ada di page 2 tidak ada di page 1'
		Boolean isPaging = CustomKeywords.'sortPaging.verifySortPaging.verifyPaging'(listApp,listString)
		WebUI.verifyEqual(isPaging,true)
		
	}
}

'click button add'
WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/button_Add'))

'Ambil nilai office login dari confins'
String[] officeLogin = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/label_OfficeLocLogin')).replace(
    ',', ';').split(';')

if (GlobalVariable.Role == 'Data Entry') {
    if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
        GlobalVariable.NumofColm, 10).equalsIgnoreCase('No') || findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
        GlobalVariable.NumofColm, 10).equalsIgnoreCase('Edit')) {
        if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
            GlobalVariable.NumofColm, 10).equalsIgnoreCase('Edit')) {
            'click button lookup copy app'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/button_LookupCopyApp'))

            'input app no'
            WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/input_AppNoCopyApp'), 
                findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
                    GlobalVariable.NumofColm, 9))

            'click button search'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/button_Search'))

			POStat = CustomKeywords.'dbconnection.checkPOStat.checkCopyAppPOStat'(sqlConnectionLOS,findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
                    GlobalVariable.NumofColm, 9))
			
            'verify input error'
            if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/a_Select'), 
                10, FailureHandling.OPTIONAL)) {
                'click select'
                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/a_Select'))
            } else {
                'click X'
                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/button_X'))

                'Click Button Cancel'
                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/button_Cancel'))

                'Write To Excel GlobalVariable.StatusFailed'
                CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
                    0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)

                'Write To Excel GlobalVariable.StatusReasonLookup'
                CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
                    1, GlobalVariable.NumofColm - 1, GlobalVariable.StatusReasonLookup)

                'Flagfailed =1 karena lookup gagal'
                flagFailed = 1

                'Pengecekan jika new consumer finance belum diexpand'
                if (WebUI.verifyElementNotVisible(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA'), 
                    FailureHandling.OPTIONAL)) {
                    'Klik new consumer finance'
                    WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_New Consumer Finance'))
                }
            }
			if(POStat=="DEACT"||POStat=="EXP"){
				WebUI.verifyElementPresent(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/div_erroralert'),2,FailureHandling.OPTIONAL)
			}
			
        } else if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
            GlobalVariable.NumofColm, 10).equalsIgnoreCase('No')) {
            'click button lookup product offering'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/button_Product Offering Name_btn btn-raised btn-primary'))

            if (GlobalVariable.Role == 'Testing') {
                

                String office = officeLogin[0]

                'click button search'
                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/button_Search'))

                'Cek total data product offering pada db'
                Integer countPO = CustomKeywords.'dbconnection.checkProdOffering.countProdOffering'(sqlConnectionLOS, office)

                'Ambil nilai total data product offering pada lookup confins'
                String[] textTotalDataPO = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/label_TotalDataOfficer')).replace(
                    ' ', '').replace(':', ';').split(';')

                'Parsing nilai total data PO confins ke integer(angka)'
                Integer totalDataPO = Integer.parseInt(textTotalDataPO[1])

                'Verif total data product offering confins sesuai dengan db'
                WebUI.verifyEqual(totalDataPO, countPO)
            }
            
            'input product offering code'
            WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/input_ProductOfferingCode'), 
                findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
                    GlobalVariable.NumofColm, 12))

            'click button search'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/button_Search'))

            'verify input error'
            if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/a_Select'), 
                10, FailureHandling.OPTIONAL)) {
                'click select'
                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/a_Select'))
            } else {
                'click X'
                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabNewApplication/Button_X'))

                'Click Button Cancel'
                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabNewApplication/Button_Cancel'))

                'Write To Excel GlobalVariable.StatusFailed'
                CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
                    0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)

                'Write To Excel GlobalVariable.StatusReasonLookup'
                CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
                    1, GlobalVariable.NumofColm - 1, GlobalVariable.StatusReasonLookup)

                flagFailed = 1

                'Pengecekan jika new consumer finance belum diexpand'
                if (WebUI.verifyElementNotVisible(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA'), 
                    FailureHandling.OPTIONAL)) {
                    'Klik new consumer finance'
                    WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_New Consumer Finance'))
                }
            }
        }
        
        'click button next'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/button_Next'))

        'call Testcase tab Customer data'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1 - Customer Data/TabCustomerData'), 
            [:], FailureHandling.CONTINUE_ON_FAILURE)
    } else if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
        GlobalVariable.NumofColm, 10).equalsIgnoreCase('Yes')) {
        'click button lookup copy app'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/button_LookupCopyApp'))

        'input app no'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/input_AppNoCopyApp'), 
            findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
                GlobalVariable.NumofColm, 9))

        'click button search'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/button_Search'))

		POStat = CustomKeywords.'dbconnection.checkPOStat.checkCopyAppPOStat'(sqlConnectionLOS,findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
			GlobalVariable.NumofColm, 9))
		
        'verify input error'
        if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/a_Select'), 
            10, FailureHandling.OPTIONAL)) {
            'click select'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/a_Select'))
        } else {
            'click X'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/button_X'))

            'Click Button Cancel'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/button_Cancel'))

            'Write To Excel GlobalVariable.StatusFailed'
            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
                0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)

            'Write To Excel GlobalVariable.StatusReasonLookup'
            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
                1, GlobalVariable.NumofColm - 1, GlobalVariable.StatusReasonLookup)

            'Flagfailed=1 karena lookup gagal'
            flagFailed = 1

            'Pengecekan jika new consumer finance belum diexpand'
            if (WebUI.verifyElementNotVisible(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA'), FailureHandling.OPTIONAL)) {
                'Klik new consumer finance'
                WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_New Consumer Finance'))
            }
        }
		
		if(POStat=="DEACT"||POStat=="EXP"){
			WebUI.verifyElementPresent(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/div_erroralert'),2,FailureHandling.OPTIONAL)
		}
        
        'click button next'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/button_Next'))

        'Get Appno'
        String appNo = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/Applicant No'))

        'Write to excel Appno'
        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
            12, GlobalVariable.NumofColm - 1, appNo)

        'click button save'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/button_Save'))

        'Write to excel success'
        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
            0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusSuccess)

        'customer added +1'
        (GlobalVariable.countNumofCustomer)++

        'verify fail'
        if (WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/ApplicationCurrentStep')), 
            'CUSTOMER', false, FailureHandling.OPTIONAL)) {
            'Write to excel failed'
            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
                0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)

            'customer added -1'
            (GlobalVariable.countNumofCustomer)--

            'Write to excel failed reason'
            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
                1, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailedCopyApp)

            'click menu Customer main'
            WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA'))

            'Pengecekan jika new consumer finance belum diexpand'
            if (WebUI.verifyElementNotVisible(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA'), FailureHandling.OPTIONAL)) {
                'Klik new consumer finance'
                WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_New Consumer Finance'))
            }
        }
    }
    
    'untuk mendapatkan posisi copy app dari excel'
    for (GlobalVariable.NumofFamily = 2; GlobalVariable.NumofFamily <= (Integer.parseInt(GlobalVariable.CountAFamily) + 
    1); (GlobalVariable.NumofFamily)++) {
        if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData').getValue(
            GlobalVariable.NumofFamily, 12) == findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
            GlobalVariable.NumofColm, 13)) {
            GlobalVariable.CopyAppColm = GlobalVariable.NumofFamily

            break
        }
    }
    
    if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
            GlobalVariable.NumofColm, 10).equalsIgnoreCase('No')) {
        'call test case tab family data'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1 - Customer Data/TabFamilyData'), 
            [:], FailureHandling.CONTINUE_ON_FAILURE)
    } else if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
            GlobalVariable.NumofColm, 10).equalsIgnoreCase('Edit')) {
        'call TC family copy app'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1 - Customer Data/TabFamilyCopyApp'), 
            [:], FailureHandling.STOP_ON_FAILURE)
    } else if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
            GlobalVariable.NumofColm, 10).equalsIgnoreCase('Yes')) {
        'click button save and continue'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabFamilyData/button_Save and continue'))

        'Write to excel success'
        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
            0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusSuccess)

        'verify fail'
        if (WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/ApplicationCurrentStep')), 
            'FAMILY', false, FailureHandling.OPTIONAL)) {
            'Write to excel failed'
            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
            0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)

            'customer added -1'
            (GlobalVariable.countNumofCustomer)--

            'Write to excel failed reason'
            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
            0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailedCopyApp)

            'click menu Customer main'
            WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA'))

            'Pengecekan jika new consumer finance belum diexpand'
            if (WebUI.verifyElementNotVisible(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA'), FailureHandling.OPTIONAL)) {
                'Klik new consumer finance'
                WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_New Consumer Finance'))
            }
        }
    }
    
    'untuk mendapatkan posisi copy app dari excel'
    for (GlobalVariable.NumofGuarantorPersonal = 2; GlobalVariable.NumofGuarantorPersonal <= (Integer.parseInt(GlobalVariable.CountAGuarantorPersonal) + 
    1); (GlobalVariable.NumofGuarantorPersonal)++) {
        if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabGuarantorDataPersonal').getValue(
            GlobalVariable.NumofGuarantorPersonal, 12) == findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
            GlobalVariable.NumofColm, 13)) {
            GlobalVariable.CopyAppColm = GlobalVariable.NumofGuarantorPersonal

            break
        }
    }
    
    if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
            GlobalVariable.NumofColm, 10).equalsIgnoreCase('No')) {
		'call TC Guarantor Tanpa Copy App'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1 - Customer Data/TabGuarantorData'), 
            [:], FailureHandling.CONTINUE_ON_FAILURE)
    } else if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
            GlobalVariable.NumofColm, 10).equalsIgnoreCase('Edit')) {
        'call TC copy app Guarantor'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1 - Customer Data/TabGuarantorPersonalCopyApp'), 
            [:], FailureHandling.CONTINUE_ON_FAILURE)
    } else if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
            GlobalVariable.NumofColm, 10).equalsIgnoreCase('Yes')) {
        'click button save and continue'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_Save and continue'))

        'Write to excel success'
        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
            0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusSuccess)

        'verify fail'
        if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/ApplicationCurrentStep'), 
            5, FailureHandling.OPTIONAL)) {
            'Write to excel failed'
            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
            0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)

            'customer added -1'
            (GlobalVariable.countNumofCustomer)--

            'Write to excel failed reason'
            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
            0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailedCopyApp)

            'click menu Customer main'
            WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA'))

            'Pengecekan jika new consumer finance belum diexpand'
            if (WebUI.verifyElementNotVisible(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA'), FailureHandling.OPTIONAL)) {
                'Klik new consumer finance'
                WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_New Consumer Finance'))
            }
        }
    }
    
			WebUI.delay(3)
			
		'call TC verify App setelah submit'
    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1 - Customer Data/VerifyApplicant'), 
        [:], FailureHandling.CONTINUE_ON_FAILURE)
} else {
    if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
        GlobalVariable.NumofColm, 10).equalsIgnoreCase('No') || findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
        GlobalVariable.NumofColm, 10).equalsIgnoreCase('Edit')) {
        if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
            GlobalVariable.NumofColm, 10).equalsIgnoreCase('Edit')) {
            'click button lookup copy app'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/button_LookupCopyApp'))

            'input app no'
            WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/input_AppNoCopyApp'), 
                findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
                    GlobalVariable.NumofColm, 9))

            'click button search'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/button_Search'))

            'verify input error'
            if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/a_Select'), 
                10, FailureHandling.OPTIONAL)) {
                'click select'
                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/a_Select'))
            } else {
                'click X'
                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/button_X'))

                'Click Button Cancel'
                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/button_Cancel'))

                'Write To Excel GlobalVariable.StatusFailed'
                CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
                    0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)

                'Write To Excel GlobalVariable.StatusReasonLookup'
                CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
                    1, GlobalVariable.NumofColm - 1, GlobalVariable.StatusReasonLookup)

                'Flagfailed =1 karena lookup gagal'
                flagFailed = 1

                'Pengecekan jika new consumer finance belum diexpand'
                if (WebUI.verifyElementNotVisible(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA'), 
                    FailureHandling.OPTIONAL)) {
                    'Klik new consumer finance'
                    WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_New Consumer Finance'))
                }
            }
        } else if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
            GlobalVariable.NumofColm, 10).equalsIgnoreCase('No')) {
            'click button lookup product offering'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/button_Product Offering Name_btn btn-raised btn-primary'))

            if (GlobalVariable.Role == 'Testing') {
                

                String office = officeLogin[0]

                'click button search'
                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/button_Search'))

                'Cek total data product offering pada db'
                Integer countPO = CustomKeywords.'dbconnection.checkProdOffering.countProdOffering'(sqlConnectionLOS, office)

                'Ambil nilai total data product offering pada lookup confins'
                String[] textTotalDataPO = WebUI.getText(findTestObject('Object Repository/NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP2-ApplicationData/TabApplicationData/label_TotalDataOfficer')).replace(
                    ' ', '').replace(':', ';').split(';')

                'Parsing nilai total data PO confins ke integer(angka)'
                Integer totalDataPO = Integer.parseInt(textTotalDataPO[1])

                'Verif total data product offering confins sesuai dengan db'
                WebUI.verifyEqual(totalDataPO, countPO)
            }
            
            'input product offering code'
            WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/input_ProductOfferingCode'), 
                findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
                    GlobalVariable.NumofColm, 12))

            'click button search'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/button_Search'))

            'verify input error'
            if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/a_Select'), 
                10, FailureHandling.OPTIONAL)) {
                'click select'
                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/a_Select'))
            } else {
                'click X'
                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabNewApplication/Button_X'))

                'Click Button Cancel'
                WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabNewApplication/Button_Cancel'))

                'Write To Excel GlobalVariable.StatusFailed'
                CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
                    0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)

                'Write To Excel GlobalVariable.StatusReasonLookup'
                CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
                    1, GlobalVariable.NumofColm - 1, GlobalVariable.StatusReasonLookup)

                flagFailed = 1

                'Pengecekan jika new consumer finance belum diexpand'
                if (WebUI.verifyElementNotVisible(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA'), 
                    FailureHandling.OPTIONAL)) {
                    'Klik new consumer finance'
                    WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_New Consumer Finance'))
                }
            }
        }
        
        'click button next'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/button_Next'))

        'call test case tab customer data'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1 - Customer Data/TabCustomerData'), 
            [:], FailureHandling.STOP_ON_FAILURE)
    } else if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
        GlobalVariable.NumofColm, 10).equalsIgnoreCase('Yes')) {
        'click button lookup copy app'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/button_LookupCopyApp'))

        'input app no'
        WebUI.setText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/input_AppNoCopyApp'), 
            findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
                GlobalVariable.NumofColm, 9))

        'click button search'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/button_Search'))

        'verify input error'
        if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/a_Select'), 
            10, FailureHandling.OPTIONAL)) {
            'click select'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/a_Select'))
        } else {
            'click X'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/button_X'))

            'Click Button Cancel'
            WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/button_Cancel'))

            'Write To Excel GlobalVariable.StatusFailed'
            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
                0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)

            'Write To Excel GlobalVariable.StatusReasonLookup'
            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
                1, GlobalVariable.NumofColm - 1, GlobalVariable.StatusReasonLookup)

            'Flagfailed=1 karena lookup gagal'
            flagFailed = 1

            'Pengecekan jika new consumer finance belum diexpand'
            if (WebUI.verifyElementNotVisible(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA'), FailureHandling.OPTIONAL)) {
                'Klik new consumer finance'
                WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_New Consumer Finance'))
            }
        }
        
        'click button next'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/button_Next'))

        'Get Appno'
        String appNo = WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/Applicant No'))

        'Write to excel Appno'
        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
            12, GlobalVariable.NumofColm - 1, appNo)

        'click button save'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabCustomerData/button_Save'))

        'Write to excel success'
        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
            0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusSuccess)

        'customer added +1'
        (GlobalVariable.countNumofCustomer)++

        'verify fail'
        if (WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/ApplicationCurrentStep')), 
            'CUSTOMER', false, FailureHandling.OPTIONAL)) {
            'Write to excel failed'
            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
                0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)

            'customer added -1'
            (GlobalVariable.countNumofCustomer)--

            'Write to excel failed reason'
            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
                1, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailedCopyApp)

            'click menu Customer main'
            WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA'))

            'Pengecekan jika new consumer finance belum diexpand'
            if (WebUI.verifyElementNotVisible(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA'), FailureHandling.OPTIONAL)) {
                'Klik new consumer finance'
                WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_New Consumer Finance'))
            }
        }
    }
    
    'untuk mendapatkan posisi copy app dari excel'
    for (GlobalVariable.NumofFamily = 2; GlobalVariable.NumofFamily <= (Integer.parseInt(GlobalVariable.CountAFamily) + 
    1); (GlobalVariable.NumofFamily)++) {
        if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabFamilyData').getValue(
            GlobalVariable.NumofFamily, 12) == findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
            GlobalVariable.NumofColm, 13)) {
            GlobalVariable.CopyAppColm = GlobalVariable.NumofFamily

            break
        }
    }
    
    if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
        GlobalVariable.NumofColm, 10).equalsIgnoreCase('No')) {
        'call test case tab family data'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1 - Customer Data/TabFamilyData'), 
            [:], FailureHandling.STOP_ON_FAILURE)
    } else if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
        GlobalVariable.NumofColm, 10).equalsIgnoreCase('Edit')) {
        'call TC family copy app'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1 - Customer Data/TabFamilyCopyApp'), 
            [:], FailureHandling.STOP_ON_FAILURE)
    } else if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
        GlobalVariable.NumofColm, 10).equalsIgnoreCase('Yes')) {
        'click button save and continue'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabFamilyData/button_Save and continue'))

        'Write to excel success'
        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
            0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusSuccess)

        'verify fail'
        if (WebUI.verifyMatch(WebUI.getText(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/ApplicationCurrentStep')), 
            'FAMILY', false, FailureHandling.OPTIONAL)) {
            'Write to excel failed'
            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
            0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)

            'customer added -1'
            (GlobalVariable.countNumofCustomer)--

            'Write to excel failed reason'
            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
            0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailedCopyApp)

            'click menu Customer main'
            WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA'))

            'Pengecekan jika new consumer finance belum diexpand'
            if (WebUI.verifyElementNotVisible(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA'), FailureHandling.OPTIONAL)) {
                'Klik new consumer finance'
                WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_New Consumer Finance'))
            }
        }
    }
    
    'untuk mendapatkan posisi copy app dari excel'
    for (GlobalVariable.NumofGuarantorPersonal = 2; GlobalVariable.NumofGuarantorPersonal <= (Integer.parseInt(GlobalVariable.CountAGuarantorPersonal) + 
    1); (GlobalVariable.NumofGuarantorPersonal)++) {
        if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabGuarantorDataPersonal').getValue(
            GlobalVariable.NumofGuarantorPersonal, 12) == findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
            GlobalVariable.NumofColm, 13)) {
            GlobalVariable.CopyAppColm = GlobalVariable.NumofGuarantorPersonal

            break
        }
    }
    
    if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
        GlobalVariable.NumofColm, 10).equalsIgnoreCase('No')) {
		'Call TC Guarantor tanpa Copy App'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1 - Customer Data/TabGuarantorData'), 
            [:], FailureHandling.STOP_ON_FAILURE)
    } else if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
        GlobalVariable.NumofColm, 10).equalsIgnoreCase('Edit')) {
        'call TC copy app Guarantor'
        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1 - Customer Data/TabGuarantorPersonalCopyApp'), 
            [:], FailureHandling.STOP_ON_FAILURE)
    } else if (findTestData('NAP-CF4W-CustomerPersonal/NAP-CF4W-CustomerPersonalSingle/NAP1-CustomerData/TabCustomerData').getValue(
        GlobalVariable.NumofColm, 10).equalsIgnoreCase('Yes')) {
        'click button save and continue'
        WebUI.click(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/TabGuarantorData/GuarantorDataPersonal/button_Save and continue'))

        'Write to excel success'
        CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
            0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusSuccess)

        'verify fail'
        if (WebUI.verifyElementPresent(findTestObject('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1-CustomerData/ApplicationCurrentStep'), 
            5, FailureHandling.OPTIONAL)) {
            'Write to excel failed'
            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
            0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailed)

            'customer added -1'
            (GlobalVariable.countNumofCustomer)--

            'Write to excel failed reason'
            CustomKeywords.'writetoexcel.writeToExcel.writeToExcelFunction'(GlobalVariable.DataFilePath, '1.TabCustomerMainData', 
            0, GlobalVariable.NumofColm - 1, GlobalVariable.StatusFailedCopyApp)

            'click menu Customer main'
            WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA'))

            'Pengecekan jika new consumer finance belum diexpand'
            if (WebUI.verifyElementNotVisible(findTestObject('LoginR3BranchManagerSuperuser/a_CUSTOMER MAIN DATA'), FailureHandling.OPTIONAL)) {
                'Klik new consumer finance'
                WebUI.click(findTestObject('LoginR3BranchManagerSuperuser/a_New Consumer Finance'))
            }
        }
    }
    
		WebUI.delay(3)
		
	'call TC verify App setelah submit'
    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP-CF4W-Personal/NAP1 - Customer Data/VerifyApplicant'), 
        [:], FailureHandling.STOP_ON_FAILURE)
}

