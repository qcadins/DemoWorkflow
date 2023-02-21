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
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable

'call tc login'
WebUI.callTestCase(findTestCase('Login/LoginR3BranchManagerSuperuser - NEW'), [:], FailureHandling.CONTINUE_ON_FAILURE)

'get data file path'
GlobalVariable.DataFilePath = CustomKeywords.'dbConnection.connectDB.getExcelPath'("\\Excel\\"+ GlobalVariable.LOB +"\\2.1 DataFile_NAP_"+ GlobalVariable.LOB +".xlsx")

'declare excelPathCustomerPersonal'
excelPathCustomerPersonal = 'NAP-'+ GlobalVariable.LOB +'-CustomerPersonal/NAP1-CustomerData/TabCustomerData'

countCustomerPersonal = findTestData(excelPathCustomerPersonal).getColumnNumbers()

'Jika role == data entry'
if (GlobalVariable.Role == 'Data Entry') {
    'looping countnumofcust'
    for (GlobalVariable.NumofColm; GlobalVariable.NumofColm <= (countCustomerPersonal - 1); (GlobalVariable.NumofColm)++) {
        if (CustomKeywords.'customizeKeyword.function.checkNAPExist'(findTestData(excelPathCustomerPersonal)) == true) {
            break
        }
        
        'Jika status pada excel bukan unexecuted'
        if (!(findTestData(excelPathCustomerPersonal).getValue(GlobalVariable.NumofColm, 1).equalsIgnoreCase('Unexecuted')) || (findTestData(excelPathCustomerPersonal).getValue(
            GlobalVariable.NumofColm, 12).length() == 0)) {
            'skip ke appno selanjutnya'
            continue
        }
        
        'jika edit appno tidak kosong'
        if (findTestData(excelPathCustomerPersonal).getValue(GlobalVariable.NumofColm, 8) != '') {
            'call tc edit nap'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/EditNAP'), [:], FailureHandling.CONTINUE_ON_FAILURE //Jika edit appno kosong
                ) //jika role == testing
        } //jika edit appno kosong
        else {
            'call tc main nap1'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP1 - Customer Data/MAIN_NAP1_CustomerData'), [:], 
                FailureHandling.CONTINUE_ON_FAILURE)

            'call tc custdupcheck'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/DuplicateChecking/CustomerDuplicateChecking'), [:], 
                FailureHandling.CONTINUE_ON_FAILURE)

            'call tc main nap2'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/MAIN_NAP2_ApplicationData'), 
                [:], FailureHandling.CONTINUE_ON_FAILURE)

            'call tc main comresfund'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/CommissionReservedFund/MAINComResvFund'), [:], FailureHandling.CONTINUE_ON_FAILURE)

            'call tc nap4 cdc'
            WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/CustomerDataCompletion'), 
                [:], FailureHandling.CONTINUE_ON_FAILURE)
        }
    } 
	
	//                'Jika flag checkappviewpersonal bernilai yes'
    //                if (GlobalVariable.CheckAppViewPersonal == 'Yes') {
    //                    'call test case verify app view'
    //                    WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/AppView/ApplicationInquiry'), [:], FailureHandling.CONTINUE_ON_FAILURE)
    //                }
} else {
    'looping countnumofcust'
    for (GlobalVariable.NumofColm; GlobalVariable.NumofColm <= (countCustomerPersonal - 1); (GlobalVariable.NumofColm)++) {
		GlobalVariable.IsDataCancel = 0
		
        if (CustomKeywords.'customizeKeyword.function.checkNAPExist'(findTestData(excelPathCustomerPersonal)) == true) {
            break
        }
        
        'Jika status pada excel bukan unexecuted'
        if (!(findTestData(excelPathCustomerPersonal).getValue(GlobalVariable.NumofColm, 1).equalsIgnoreCase('Unexecuted')) || (findTestData(excelPathCustomerPersonal).getValue(
            GlobalVariable.NumofColm, 12).length() == 0)) {
            'skip ke appno selanjutnya'
            continue
        }
        
        'Jika edit appno pada excel tidak kosong'
        if (findTestData(excelPathCustomerPersonal).getValue(GlobalVariable.NumofColm, 8) != '') {
			
				'call tc edit nap'
				WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/EditNAP'), [:], FailureHandling.CONTINUE_ON_FAILURE)
			
			
        } else {
				if (GlobalVariable.IsDataCancel == 1) {
					continue
				}
            
                'call tc main nap1'
                WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP1 - Customer Data/MAIN_NAP1_CustomerData'), 
                    [:], FailureHandling.CONTINUE_ON_FAILURE)
				
				if (GlobalVariable.IsDataCancel == 1) {
					continue
				}

                'call tc custdupcheck'
                WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/DuplicateChecking/CustomerDuplicateChecking'), 
                		[:], FailureHandling.CONTINUE_ON_FAILURE)                
				
				if (GlobalVariable.IsDataCancel == 1) {
					continue
				}
                
                'call tc main nap2'
                WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP2 - Application Data/MAIN_NAP2_ApplicationData'), 
                    [:], FailureHandling.CONTINUE_ON_FAILURE)
				
				if (GlobalVariable.IsDataCancel == 1) {
					continue
				}

                'call tc main comresfund'
                WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/CommissionReservedFund/MAINComResvFund'), [:], 
                    FailureHandling.CONTINUE_ON_FAILURE)

                'declare datafileCommission'
                datafileCommission = findTestData('NAP-'+ GlobalVariable.LOB +'-CustomerPersonal/CommissionReservedFund/TabCommissionData')

                'declare datafileReservedFund'
                datafileReservedFund = findTestData('NAP-'+ GlobalVariable.LOB +'-CustomerPersonal/CommissionReservedFund/TabReservedFundData')

                'Mengambil nilai row keberapa dimulai data return pada excel'
                def returnRowCom = CustomKeywords.'customizeKeyword.getRow.getExcelRow'(GlobalVariable.DataFilePath, '13.TabCommissionData', 
                    'Return Commission & Reserved Fund') + 1

                'Mengambil nilai row keberapa dimulai data return pada excel'
                def returnRowRsv = CustomKeywords.'customizeKeyword.getRow.getExcelRow'(GlobalVariable.DataFilePath, '14.TabReservedFundData', 
                    'Return Commission & Reserved Fund') + 1

                'Looping hingga return flag pada sheet commission dan reserved fund tidak bernilai "yes" lagi'
                for (int i = 1; i <= i; i++) {
                    if (datafileCommission.getValue(GlobalVariable.NumofColm, returnRowCom + 1).equalsIgnoreCase('Yes') || 
                    datafileReservedFund.getValue(GlobalVariable.NumofColm, returnRowRsv + 1).equalsIgnoreCase('Yes')) {
                        if (datafileCommission.getValue(GlobalVariable.NumofColm, returnRowCom + 1).equalsIgnoreCase('Yes')) {
                            'write to excel flag return done'
                            CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '13.TabCommissionData', 
                                returnRowCom, GlobalVariable.NumofColm - 1, 'Done')
                        } else if (datafileReservedFund.getValue(GlobalVariable.NumofColm, returnRowRsv + 1).equalsIgnoreCase(
                            'Yes')) {
                            'write to excel flag return done'
                            CustomKeywords.'customizeKeyword.writeExcel.writeToExcel'(GlobalVariable.DataFilePath, '14.TabReservedFundData', 
                                returnRowRsv, GlobalVariable.NumofColm - 1, 'Done')
                        }
                        
                        'call tc main comresfund'
                        WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/CommissionReservedFund/MAINComResvFund'), 
                            [:], FailureHandling.CONTINUE_ON_FAILURE)
                    } else {
                        break
                    }
                }
                
				if (GlobalVariable.IsDataCancel == 1) {
					continue
				}
				
                'call tc nap4 cdc'
                WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/NAP4 - Customer Data Completion/CustomerDataCompletion'), 
                    [:], FailureHandling.CONTINUE_ON_FAILURE)
				
				if (GlobalVariable.IsDataCancel == 1) {
					continue
				}
				'check if verif Appview = yes'
				if (GlobalVariable.CheckAppViewPersonal == 'Yes') {
					'call test case verify app view'
					WebUI.callTestCase(findTestCase('NAP-CF4W-CustomerPersonal/AppView/ApplicationInquiry'), [:], FailureHandling.CONTINUE_ON_FAILURE)
				}
            
            
        }
    }
}

