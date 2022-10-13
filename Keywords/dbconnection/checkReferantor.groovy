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

public class checkReferantor {

	@Keyword
	public countReferantorLookup(Sql instance, String refCategory, String originOfficeName){
		Integer countData
		if(refCategory.equalsIgnoreCase("Customer")){
			instance.eachRow("select count(*) from cust c WITH(NOLOCK) join cust_addr ca WITH(NOLOCK) on c.cust_id = ca.cust_id where mr_cust_addr_type_code = 'legal'", { def row ->
				countData = row[0]
			})
		}
		else if(refCategory.equalsIgnoreCase("Agency")){
			instance.eachRow("select count(*) from vendor v WITH(NOLOCK) join vendor_addr va WITH(NOLOCK) on v.vendor_id = va.vendor_id join vendor_office_mbr vom WITH(NOLOCK) on v.vendor_id = vom.vendor_id join ref_office ro WITH(NOLOCK) on ro.ref_office_id = vom.ref_office_id where mr_addr_type_code = 'legal'  and v.is_active=1 and (mr_vendor_category_code = 'AGENCY_PERSONAL' or mr_vendor_category_code = 'AGENCY_COMPANY') and office_name = '"+originOfficeName+"'", { def row ->
				countData = row[0]
			})
		}
		else{
			instance.eachRow("select count(*) from ref_emp WITH(NOLOCK) where is_active=1", { def row ->
				countData = row[0]
			})
		}
		return countData
	}

	@Keyword
	public checkBankAccountDDL(Sql instance, String refCategory, String officeName, String refCode){
		ArrayList<String> BankAccount = new ArrayList<String>()
		if(refCategory.equalsIgnoreCase("Customer")){
			instance.eachRow("select [Bank Account] = bank_name+' - '+bank_acc_name+' - '+bank_acc_No from cust c WITH(NOLOCK) join cust_addr ca WITH(NOLOCK) on c.cust_id = ca.cust_id join cust_bank_acc cba WITH(NOLOCK) on c.cust_id = cba.cust_id join ref_bank rb WITH(NOLOCK) on cba.ref_bank_id = rb.ref_bank_id where mr_cust_addr_type_code = 'legal' and cba.is_active = 1 and cust_no = '"+refCode+"'", { def row ->
				BankAccount.add(row[0])
			})
		}
		else if(refCategory.equalsIgnoreCase("Agency")){
			instance.eachRow("select [Bank Account] = bank_name+' - '+bank_account_name+' - '+bank_account_No from vendor v WITH(NOLOCK) join vendor_addr va WITH(NOLOCK) on v.vendor_id = va.vendor_id join vendor_office_mbr vom WITH(NOLOCK) on v.vendor_id = vom.vendor_id join ref_office ro WITH(NOLOCK) on vom.ref_office_id = ro.ref_office_id join vendor_bank_acc vbc WITH(NOLOCK) on v.vendor_id = vbc.vendor_id join ref_bank rb WITH(NOLOCK) on vbc.ref_bank_id = rb.ref_bank_id where mr_addr_type_code = 'legal'  and v.is_active=1 and (mr_vendor_category_code = 'AGENCY_PERSONAL' or mr_vendor_category_code = 'AGENCY_COMPANY') and office_name = '"+officeName+"' and vbc.is_active = 1 and vendor_code = '"+refCode+"'", { def row ->
				BankAccount.add(row[0])
			})
		}
		else{
			instance.eachRow("select [Bank Account] = bank_name+' - '+bank_acc_name+' - '+bank_acc_No from ref_emp a join emp_bank_acc b on a.ref_emp_id = b.ref_emp_id join ref_bank c on c.ref_bank_id = b.ref_bank_id where a.is_active=1 and emp_no = '"+refCode+"'", { def row ->
				BankAccount.add(row[0])
			})
		}
		return BankAccount
	}

	@Keyword
	public checkBankAccountDefault(Sql instance, String refCategory, String officeName, String refCode){
		String defaultBankAccount
		if(refCategory.equalsIgnoreCase("Customer")){
			instance.eachRow("select [Bank Account] = bank_name+' - '+bank_acc_name+' - '+bank_acc_No from cust a join cust_addr b on a.cust_id = b.cust_id join cust_bank_acc c on a.cust_id = c.cust_id join ref_bank d on c.ref_bank_id = d.ref_bank_id where mr_cust_addr_type_code = 'legal' and c.is_active = 1 and cust_no = '"+refCode+"' and is_default = 1", { def row ->
				defaultBankAccount = row[0]
			})
		}
		else{
			instance.eachRow("select [Bank Account] = bank_name+' - '+bank_account_name+' - '+bank_account_No from vendor a join vendor_addr b on a.vendor_id = b.vendor_id join vendor_office_mbr c on a.vendor_id = c.vendor_id join ref_office d on c.ref_office_id = d.ref_office_id join vendor_bank_acc e on a.vendor_id = e.vendor_id join ref_bank f on e.ref_bank_id = f.ref_bank_id where mr_addr_type_code = 'legal'  and a.is_active=1 and (mr_vendor_category_code = 'AGENCY_PERSONAL' or mr_vendor_category_code = 'AGENCY_COMPANY') and office_name = '"+officeName+"' and e.is_active = 1 and vendor_code = '"+refCode+"' and is_default=1", { def row ->
				defaultBankAccount = row[0]
			})
		}
		return defaultBankAccount
	}
}
