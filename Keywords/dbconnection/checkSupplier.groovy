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

public class checkSupplier {
	
	@Keyword
	public checkAdminHead(Sql instance, String suppName){
		ArrayList<String> adminHead = new ArrayList<String>()
		instance.eachRow(("select vendor_emp_name from vendor a join vendor_emp b on a.vendor_id = b.vendor_id where mr_vendor_category_code = 'SUPPLIER' and b.is_active = 1 and a.is_active = 1 and vendor_name = '"+suppName+"' and mr_vendor_emp_position_code='ADMIN_HEAD'"), { def row ->
			adminHead.add(row[0].toUpperCase())
		})
		return adminHead
		
	}
	
	@Keyword
	public checkSalesPerson(Sql instance, String suppName){
		ArrayList<String> salesPerson = new ArrayList<String>()
		instance.eachRow(("select vendor_emp_name from vendor a join vendor_emp b on a.vendor_id = b.vendor_id where mr_vendor_category_code = 'SUPPLIER' and b.is_active = 1 and a.is_active = 1 and vendor_name = '"+suppName+"' and mr_vendor_emp_position_code='SALES_PERSON'"), { def row ->
			salesPerson.add(row[0].toUpperCase())
		})
		return salesPerson
	}
	
	@Keyword
	public checkSupplierScheme(Sql instance, String poname){
		String suppschm
		instance.eachRow(("select distinct compnt_value_desc from prod_offering a join prod_offering_h b on a.PROD_OFFERING_ID = b.PROD_OFFERING_ID join prod_offering_d c on c.PROD_OFFERING_H_ID = b.PROD_OFFERING_H_ID where prod_offering_name = '"+poname+"' and REF_PROD_COMPNT_CODE ='SUPPLSCHM' "), { def row ->
			suppschm = (row[0])
		})
		return suppschm
		
	}
	
	@Keyword
	public countSupplierData(Sql instance, String suppschm, String officeName){
		Integer countData
		instance.eachRow(("select count(*) from vendor_schm a join vendor_schm_mbr b on a.vendor_schm_id = b.vendor_schm_id join vendor c on c.vendor_id = b.vendor_id join vendor_office_mbr d on c.vendor_id = d.vendor_id join ref_office e on e.ref_office_id = d.ref_office_id where vendor_schm_name = '"+suppschm+"' and c.mr_VENDOR_category_code = 'SUPPLIER' AND OFFICE_NAME = '"+officeName+"' AND b.IS_ACTIVE = 1"), { def row ->
			countData = (row[0])
		})
		return countData
	}
	
	
}
