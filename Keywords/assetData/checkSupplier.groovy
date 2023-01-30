package assetData

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

	//keyword check admin head
	@Keyword
	public checkAdminHead(Sql instance, String suppName){
		ArrayList<String> adminHead = new ArrayList<String>()
		instance.eachRow(("select vendor_emp_name from vendor v WITH(NOLOCK) join vendor_emp vEmp on v.vendor_id = vEmp.vendor_id where mr_vendor_category_code = 'SUPPLIER' and vEmp.is_active = 1 and v.is_active = 1 and vendor_name = '"+suppName+"' and mr_vendor_emp_position_code='ADMIN_HEAD'"), { def row ->
			adminHead.add(row[0].toUpperCase())
		})
		return adminHead
	}

	//keyword check sales person
	@Keyword
	public checkSalesPerson(Sql instance, String suppName){
		ArrayList<String> salesPerson = new ArrayList<String>()
		instance.eachRow(("select vendor_emp_name from vendor v WITH(NOLOCK) join vendor_emp vEmp on v.vendor_id = vEmp.vendor_id where mr_vendor_category_code = 'SUPPLIER' and vEmp.is_active = 1 and v.is_active = 1 and vendor_name = '"+suppName+"' and mr_vendor_emp_position_code='SALES_PERSON'"), { def row ->
			salesPerson.add(row[0].toUpperCase())
		})
		return salesPerson
	}

	//keyword check supplier scheme
	@Keyword
	public checkSupplierScheme(Sql instance, String poname){
		String suppschm
		instance.eachRow(("select distinct compnt_value_desc from prod_offering po WITH(NOLOCK) join prod_offering_h poHead on po.PROD_OFFERING_ID = poHead.PROD_OFFERING_ID join prod_offering_d poDetail on poDetail.PROD_OFFERING_H_ID = poHead.PROD_OFFERING_H_ID where prod_offering_name = '"+poname+"' and REF_PROD_COMPNT_CODE ='SUPPLSCHM'"), { def row ->
			suppschm = (row[0])
		})
		return suppschm
	}

	//keyword count supplier data
	@Keyword
	public countSupplierData(Sql instance, String suppschm, String officeName){
		Integer countData
		instance.eachRow(("select count(*) from vendor_schm vSchm WITH(NOLOCK) join vendor_schm_mbr vsMbr on vsMbr.vendor_schm_id = vSchm.vendor_schm_id join vendor v on v.vendor_id = vsMbr.vendor_id join vendor_office_mbr vOffice on v.vendor_id = vOffice.vendor_id join ref_office refoffice on refoffice.ref_office_id = vOffice.ref_office_id where vendor_schm_CODE = '"+suppschm+"' and v.mr_VENDOR_category_code = 'SUPPLIER' AND OFFICE_NAME = '"+officeName+"' AND vsMbr.IS_ACTIVE = 1"), { def row ->
			countData = (row[0])
		})
		return countData
	}
}
