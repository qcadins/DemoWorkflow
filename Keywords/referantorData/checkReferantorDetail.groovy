package referantorData

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

public class checkReferantorDetail {

	@Keyword
	public checkCustomerReferantor(Sql instance,String referantorCode){
		ArrayList<String> referantorDetail = new ArrayList<String>()
		instance.eachRow('select top(1) tax_id_no, cust_name, addr, area_code_4, area_code_3, area_code_2, area_code_1, city, zipcode from cust c WITH(NOLOCK) join cust_addr ca WITH(NOLOCK) on c.cust_id = ca.cust_id where cust_no = \''+referantorCode+'\' and mr_cust_addr_type_code = \'legal\'', { def row ->
			referantorDetail.add(row[0])
			referantorDetail.add(row[1])
			referantorDetail.add(row[2])
			referantorDetail.add(row[3])
			referantorDetail.add(row[4])
			referantorDetail.add(row[5])
			referantorDetail.add(row[6])
			referantorDetail.add(row[7])
			referantorDetail.add(row[8])
		})
		return referantorDetail
	}

	@Keyword
	public checkAgencyReferantor(Sql instance,String referantorCode){
		ArrayList<String> referantorDetail = new ArrayList<String>()
		instance.eachRow('select top(1) tax_id_no, taxpayer_name, addr, area_code_4, area_code_3, area_code_2, area_code_1, city, zipcode from vendor v WITH(NOLOCK) join vendor_addr va WITH(NOLOCK) on v.vendor_id = va.vendor_id where mr_addr_type_code = \'legal\' and vendor_code = \''+referantorCode+'\' and is_active=1 and (mr_vendor_category_code = \'AGENCY_PERSONAL\' or mr_vendor_category_code = \'AGENCY_COMPANY\')', { def row ->
			referantorDetail.add(row[0])
			referantorDetail.add(row[1])
			referantorDetail.add(row[2])
			referantorDetail.add(row[3])
			referantorDetail.add(row[4])
			referantorDetail.add(row[5])
			referantorDetail.add(row[6])
			referantorDetail.add(row[7])
			referantorDetail.add(row[8])
		})
		return referantorDetail
	}

	@Keyword
	public checkMFEmployeeReferantor(Sql instance, String referantorCode){
		ArrayList<String> referantorDetail = new ArrayList<String>()
		instance.eachRow("select top(1) ISNULL(tax_id_no,'') AS [Taxidno], emp_name, addr, area_code_4, area_code_3, area_code_2, area_code_1, city, zipcode from ref_emp WITH(NOLOCK) where emp_no='"+referantorCode+"' and is_active=1", { def row ->
			referantorDetail.add(row[0])
			referantorDetail.add(row[1])
			referantorDetail.add(row[2])
			referantorDetail.add(row[3])
			referantorDetail.add(row[4])
			referantorDetail.add(row[5])
			referantorDetail.add(row[6])
			referantorDetail.add(row[7])
			referantorDetail.add(row[8])
		})
		return referantorDetail
	}
}
