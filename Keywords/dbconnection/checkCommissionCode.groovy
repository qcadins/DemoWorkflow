package dbconnection

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import groovy.sql.Sql as Sql
import internal.GlobalVariable

public class checkCommissionCode {

	@Keyword
	public checkSupplierCode(Sql instance, String supplierName){
		String supplierCode
		instance.eachRow(('select VENDOR_CODE from VENDOR where MR_VENDOR_CATEGORY_CODE = \'SUPPLIER\' and VENDOR_NAME = \'' +
				supplierName) + '\'', { def row ->
					supplierCode = (row[0])
				})
		return supplierCode
	}

	@Keyword
	public checkSupplierEmployeeCode(Sql instance,String supplierEmployeeName, String supplierEmpPos, String supplierName){
		String supplierEmployeeCode
		instance.eachRow(("select vendor_emp_no from VENDOR_EMP a join VENDOR b on a.VENDOR_ID = b.VENDOR_ID where b.MR_VENDOR_CATEGORY_CODE = 'SUPPLIER' and vendor_emp_name = '"+supplierEmployeeName+"' and MR_VENDOR_EMP_POSITION_CODE ='"+supplierEmpPos.replaceAll(" ","_")+"' and VENDOR_NAME = '"+supplierName+"'"), { def row ->
			supplierEmployeeCode = (row[0])
		})
		return supplierEmployeeCode
	}

	@Keyword
	public checkReferantorCode(Sql instance,String referantorName){
		String referantorCode
		instance.eachRow(("select CUST_NO from CUST where cust_name ='"+referantorName+"'"), { def row ->
			referantorCode = (row[0])
		})
		if(referantorCode==null){
			instance.eachRow(("select EMP_NO from REF_EMP where emp_name ='"+referantorName+"'"), { def row ->
				referantorCode = (row[0])
			})
		}
		if(!referantorCode){
			instance.eachRow(("select VENDOR_CODE from VENDOR where MR_VENDOR_CATEGORY_CODE in ('AGENCY_PERSONAL','AGENCY_COMPANY') AND vendor_name ='"+referantorName+"'"), { def row ->
				referantorCode = (row[0])
			})
		}
		return referantorCode
	}
}
