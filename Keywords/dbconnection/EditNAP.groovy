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

import groovy.sql.Sql
import internal.GlobalVariable

public class EditNAP {

	@Keyword
	public CustomerDataPersonal (Sql instance, String appno){
		String customerdata
		ArrayList<String> listcustdata = new ArrayList<>()
		instance.eachRow(("Select CUST_NAME, rml.REF_MASTER_NAME, FORMAT(BIRTH_DT, 'MM/dd/yyyy') , ID_NO, acp.MOTHER_MAIDEN_NAME FROM APP_CUST ac WITH(NOLOCK) JOIN APP a WITH(NOLOCK) ON ac.APP_ID = a.APP_ID JOIN APP_CUST_PERSONAL acp WITH(NOLOCK) ON acp.APP_CUST_ID = ac.APP_CUST_ID JOIN REF_MASTER_LOS rml WITH(NOLOCK) ON rml.REF_MASTER_CODE = MR_ID_TYPE_CODE WHERE APP_NO = '"+ appno +"' AND IS_CUSTOMER = 1 AND rml.REF_MASTER_TYPE_CODE = 'ID_TYPE' AND rml.IS_ACTIVE = '1'"), {  row ->

			customerdata = (row[0])
			listcustdata.add(customerdata)
			customerdata = (row[1])
			listcustdata.add(customerdata)
			customerdata = (row[2])
			listcustdata.add(customerdata)
			customerdata = (row[3])
			listcustdata.add(customerdata)
			customerdata = (row[4])
			listcustdata.add(customerdata)
		})
		return listcustdata
	}

	@Keyword
	public FamilyDataPersonal (Sql instance, String appno){
		String customerdata
		ArrayList<String> listcustdata = new ArrayList<>()
		instance.eachRow(("Select CUST_NAME, rml.REF_MASTER_NAME, FORMAT(BIRTH_DT, 'MM/dd/yyyy') , ID_NO, acp.MOTHER_MAIDEN_NAME FROM APP_CUST ac WITH(NOLOCK) JOIN APP a WITH(NOLOCK) ON ac.APP_ID = a.APP_ID JOIN APP_CUST_PERSONAL acp WITH(NOLOCK) ON acp.APP_CUST_ID = ac.APP_CUST_ID JOIN REF_MASTER_LOS rml WITH(NOLOCK) ON rml.REF_MASTER_CODE = MR_ID_TYPE_CODE WHERE APP_NO = '"+ appno +"' AND IS_FAMILY = 1 AND rml.REF_MASTER_TYPE_CODE = 'ID_TYPE'"), {  row ->

			customerdata = (row[0])
			listcustdata.add(customerdata)
			customerdata = (row[1])
			listcustdata.add(customerdata)
			customerdata = (row[2])
			listcustdata.add(customerdata)
			customerdata = (row[3])
			listcustdata.add(customerdata)
			customerdata = (row[4])
			listcustdata.add(customerdata)
		})
		return listcustdata
	}

	@Keyword
	public GuarantorDataPersonal (Sql instance, String appno){
		String customerdata
		ArrayList<String> listcustdata = new ArrayList<>()
		instance.eachRow(("Select CUST_NAME, rml.REF_MASTER_NAME, FORMAT(BIRTH_DT, 'MM/dd/yyyy') , ID_NO, acp.MOTHER_MAIDEN_NAME FROM APP_CUST ac WITH(NOLOCK) JOIN APP a WITH(NOLOCK) ON ac.APP_ID = a.APP_ID JOIN APP_CUST_PERSONAL acp WITH(NOLOCK) ON acp.APP_CUST_ID = ac.APP_CUST_ID JOIN REF_MASTER_LOS rml WITH(NOLOCK) ON rml.REF_MASTER_CODE = MR_ID_TYPE_CODE WHERE APP_NO = '"+ appno +"' AND IS_GUARANTOR = 1 AND rml.REF_MASTER_TYPE_CODE = 'ID_TYPE'"), {  row ->

			customerdata = (row[0])
			listcustdata.add(customerdata)
			customerdata = (row[1])
			listcustdata.add(customerdata)
			customerdata = (row[2])
			listcustdata.add(customerdata)
			customerdata = (row[3])
			listcustdata.add(customerdata)
			customerdata = (row[4])
			listcustdata.add(customerdata)
		})
		return listcustdata
	}

	@Keyword
	public GuarantorDataCompany (Sql instance, String appno){
		String customerdata
		ArrayList<String> listcustdata = new ArrayList<>()
		instance.eachRow(("Select CUST_NAME, TAX_ID_NO FROM APP_CUST ac WITH(NOLOCK) JOIN APP a WITH(NOLOCK) ON ac.APP_ID = a.APP_ID JOIN APP_CUST_COMPANY acp WITH(NOLOCK) ON acp.APP_CUST_ID = ac.APP_CUST_ID WHERE APP_NO = '"+ appno +"' AND IS_GUARANTOR = 1"), {  row ->

			customerdata = (row[0])
			listcustdata.add(customerdata)
			customerdata = (row[1])
			listcustdata.add(customerdata)
		})
		return listcustdata
	}
	
	@Keyword
	public CustomerDataCompany (Sql instance, String appno){
		String customerdata
		ArrayList<String> listcustdata = new ArrayList<>()
		instance.eachRow(("Select CUST_NAME, TAX_ID_NO FROM APP_CUST ac WITH(NOLOCK) JOIN APP a WITH(NOLOCK) ON ac.APP_ID = a.APP_ID JOIN APP_CUST_COMPANY acp WITH(NOLOCK) ON acp.APP_CUST_ID = ac.APP_CUST_ID WHERE APP_NO = '"+ appno +"' AND IS_CUSTOMER = 1"), {  row ->

			customerdata = (row[0])
			listcustdata.add(customerdata)
			customerdata = (row[1])
			listcustdata.add(customerdata)
		})
		return listcustdata
	}

	@Keyword
	public ShareholderDataPersonal (Sql instance, String appno){
		String customerdata
		ArrayList<String> listcustdata = new ArrayList<>()
		instance.eachRow(("Select CUST_NAME, rml.REF_MASTER_NAME, FORMAT(BIRTH_DT, 'MM/dd/yyyy') , ID_NO, acp.MOTHER_MAIDEN_NAME FROM APP_CUST ac WITH(NOLOCK) JOIN APP a WITH(NOLOCK) ON ac.APP_ID = a.APP_ID JOIN APP_CUST_PERSONAL acp WITH(NOLOCK) ON acp.APP_CUST_ID = ac.APP_CUST_ID JOIN REF_MASTER_LOS rml WITH(NOLOCK) ON rml.REF_MASTER_CODE = MR_ID_TYPE_CODE WHERE APP_NO = '"+ appno +"' AND IS_SHAREHOLDER = 1 AND rml.REF_MASTER_TYPE_CODE = 'ID_TYPE' AND rml.IS_ACTIVE = '1'"), {  row ->

			customerdata = (row[0])
			listcustdata.add(customerdata)
			customerdata = (row[1])
			listcustdata.add(customerdata)
			customerdata = (row[2])
			listcustdata.add(customerdata)
			customerdata = (row[3])
			listcustdata.add(customerdata)
			customerdata = (row[4])
			listcustdata.add(customerdata)
		})
		return listcustdata
	}

	@Keyword
	public ShareholderDataCompany (Sql instance, String appno){
		String customerdata
		ArrayList<String> listcustdata = new ArrayList<>()
		instance.eachRow(("Select CUST_NAME, TAX_ID_NO FROM APP_CUST ac WITH(NOLOCK) JOIN APP a WITH(NOLOCK) ON ac.APP_ID = a.APP_ID JOIN APP_CUST_COMPANY acp WITH(NOLOCK) ON acp.APP_CUST_ID = ac.APP_CUST_ID WHERE APP_NO = '"+ appno +"' AND IS_SHAREHOLDER = 1"), {  row ->

			customerdata = (row[0])
			listcustdata.add(customerdata)
			customerdata = (row[1])
			listcustdata.add(customerdata)
		})
		return listcustdata
	}
}
